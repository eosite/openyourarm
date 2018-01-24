package com.glaf.datamgr.jdbc;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.druid.pool.DruidPooledConnection;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.BaseConfiguration;
import com.glaf.core.config.Configuration;
import com.glaf.core.security.Authentication;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.ExecutionLog;
import com.glaf.datamgr.util.ExecutionLogFactory;

public class DataSetConnectionFactory {
	public class CheckConnectionTask implements Runnable {
		public void run() {
			if (!connectionMap.isEmpty()) {
				logger.debug("检测数据集连接......");

				Collection<ConnectionInfo> connectionList = connectionMap.values();
				Collection<ConnectionInfo> list = new ArrayList<ConnectionInfo>();
				if (connectionList != null && !connectionList.isEmpty()) {
					for (ConnectionInfo info : connectionList) {
						if (info.getConnection() != null) {
							list.add(info);
						}
					}
				}

				int size = list.size();
				long nowTime = System.currentTimeMillis();
				JSONObject json = new JSONObject();
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(new Date());
				ExecutionLog log = null;
				List<String> rowIds = new ArrayList<String>();

				logger.debug("检测到数据集连接数目:" + size);
				for (ConnectionInfo info : list) {
					// 强制关闭超过30秒的连接
					if ((nowTime - info.getStartTime()) > conf.getInt("dataset_query_timeout", 30000)) {
						Connection connection = info.getConnection();
						try {
							if (connection != null && !connection.isClosed()) {
								logger.debug("connection impl class:" + connection.getClass().getName());
								logger.warn("close dataset connection:" + info.toString());
								if (connection instanceof DruidPooledConnection) {
									logger.warn("准备关闭物理连接......");
									DruidPooledConnection conn = (DruidPooledConnection) connection;
									conn.abandond();// 丢弃连接，标识后连接池会自动回收。
									conn.getConnection().close();
									conn.close();
									conn = null;
									info.setConnection(null);
									connectionMap.remove(info.getId());
									logger.warn("物理连接已经关闭。");
								} else {
									connection.close();
									connection = null;
									info.setConnection(null);
									connectionMap.remove(info.getId());
									logger.warn("物理连接已经关闭。");
								}
								rowIds.add(info.getId());
								log = new ExecutionLog();
								log.setStartTime(new Date(info.getStartTime()));
								json.put("datasetId", info.getDatasetId());
								log.setBusinessKey(info.getDatasetId());
								log.setContent(json.toJSONString());
								log.setCreateBy(info.getActorId());
								log.setEndTime(new Date());
								log.setRunTime(nowTime - info.getStartTime());
								log.setRunHour(calendar.get(Calendar.HOUR));
								log.setRunDay(DateUtils.getNowYearMonthDay());
								log.setJobNo("dataset_fatal_error_" + info.getDatasetId() + "_"
										+ DateUtils.getNowYearMonthDayHHmmss());
								log.setStatus(-1);
								log.setType("dataset_fatal_error");
								ExecutionLogFactory.getInstance().addLog(log);
								break;
							}
						} catch (Exception ex) {
							logger.error(ex);
						}
					}
					/**
					 * 执行时间超过3秒，记录到日志系统
					 */
					if ((nowTime - info.getStartTime()) > 3000) {
						log = new ExecutionLog();
						log.setStartTime(new Date(info.getStartTime()));
						json.put("datasetId", info.getDatasetId());
						log.setBusinessKey(info.getDatasetId());
						log.setContent(json.toJSONString());
						log.setCreateBy(info.getActorId());
						log.setEndTime(new Date());
						log.setRunTime(nowTime - info.getStartTime());
						log.setRunHour(calendar.get(Calendar.HOUR));
						log.setRunDay(DateUtils.getNowYearMonthDay());
						log.setJobNo(
								"dataset_slow_" + info.getDatasetId() + "_" + DateUtils.getNowYearMonthDayHHmmss());
						log.setStatus(0);
						log.setType("dataset_slow");
						ExecutionLogFactory.getInstance().addLog(log);
					}
				}

				if (rowIds.size() > 0) {
					for (String rowId : rowIds) {
						connectionMap.remove(rowId);
					}
				}
			}
		}
	}

	private static class DataSetConnectionFactoryHolder {
		public static DataSetConnectionFactory instance = new DataSetConnectionFactory();
	}

	protected static final Log logger = LogFactory.getLog(DataSetConnectionFactory.class);

	protected static final ConcurrentMap<String, ConnectionInfo> connectionMap = new ConcurrentHashMap<String, ConnectionInfo>();

	protected static volatile Configuration conf = BaseConfiguration.create();

	public static DataSetConnectionFactory getInstance() {
		return DataSetConnectionFactoryHolder.instance;
	}

	protected volatile boolean startScheduler = false;

	protected ScheduledExecutorService scheduledThreadPool = Executors.newSingleThreadScheduledExecutor();

	private DataSetConnectionFactory() {
		if (!startScheduler) {
			this.startScheduler();
		}
	}

	public Collection<ConnectionInfo> getConnections() {
		return connectionMap.values();
	}

	/**
	 * 注册连接信息
	 * 
	 * @param datasetId
	 *            数据集编号
	 * @param ts
	 *            开始时间戳
	 * @param connection
	 *            数据库连接
	 */
	public void register(String datasetId, long ts, Connection connection) {
		String id = datasetId + "_" + ts + "_" + DigestUtils.sha1Hex(connection.toString());
		ConnectionInfo info = new ConnectionInfo();
		info.setId(id);
		info.setConnection(connection);
		info.setDatasetId(datasetId);
		info.setStartTime(System.currentTimeMillis());
		if (Authentication.getAuthenticatedActorId() != null) {
			info.setActorId(Authentication.getAuthenticatedActorId());
		}
		connectionMap.put(id, info);
	}

	public void startScheduler() {
		CheckConnectionTask command = new CheckConnectionTask();
		scheduledThreadPool.scheduleAtFixedRate(command, 20, 2, TimeUnit.SECONDS);
		startScheduler = true;
	}

	/**
	 * 释放连接信息
	 * 
	 * @param datasetId
	 *            数据集编号
	 * @param ts
	 *            开始时间戳
	 * @param connection
	 *            数据库连接
	 */
	public void unregister(String datasetId, long ts, Connection connection) {
		String id = datasetId + "_" + ts + "_" + DigestUtils.sha1Hex(connection.toString());
		if (!connectionMap.isEmpty()) {
			connectionMap.remove(id);
		}
	}

}
