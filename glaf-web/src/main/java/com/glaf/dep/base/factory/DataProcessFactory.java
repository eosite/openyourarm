package com.glaf.dep.base.factory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.util.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.Environment;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.domain.Database;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.JdbcUtils;
import com.glaf.dep.base.domain.DepBaseWdataSet;
import com.glaf.dep.base.service.DepBaseWdataSetService;
import com.glaf.dep.base.util.WdataSetBiluder;

import io.netty.util.concurrent.FastThreadLocal;

public class DataProcessFactory {

	protected static FastThreadLocal<String> systemNameThreadLocal = new FastThreadLocal<String>();

	private static class SingletonHolder {
		public static DataProcessFactory instance = new DataProcessFactory();
	}

	public static DataProcessFactory getInstance() {
		return SingletonHolder.instance;
	}

	protected static final Logger logger = LoggerFactory.getLogger(DataProcessFactory.class);

	protected DepBaseWdataSetService depBaseWdataSetService;

	private DataProcessFactory() {

	}

	private void exec(JSONObject ruleJson, final Map<String, Object> param) {
		this.execBatch(ruleJson, new ArrayList<Map<String, Object>>(1) {
			private static final long serialVersionUID = 1L;
			{
				add(param);
			}
		});
	}

	public void exec(Long id, Map<String, Object> param) {
		this.exec(this.getRuleJson(id), param);
	}

	private void execBatch(JSONObject ruleJson, List<Map<String, Object>> params) {
		if (params != null && params.size() > 0) {
			// WdataSetBiluder wds = new WdataSetBiluder(ruleJson);
			this.autoExec(ruleJson, params.get(0), new AutoExchange() {
				public Object exec(Connection connection, WdataSetBiluder wds) {
					try {
						wds.execute(connection, params);
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("execBatch error", ex);
						throw new RuntimeException(ex);
					}
					return null;
				}
			});
		}
	}

	public void execBatch(Long id, List<Map<String, Object>> params) {
		this.execBatch(this.getRuleJson(id), params);
	}

	@SuppressWarnings("unchecked")
	public void execDynamic(Long wdataSetId, Map<String, Object> param) {
		JSONObject ruleJson = this.getRuleJson(wdataSetId);
		List<Map<String, Object>> params = CollectionUtils.asList(param);
		if (params != null && params.size() > 0) {
			// WdataSetBiluder wds = new WdataSetBiluder(ruleJson);
			this.autoExec(ruleJson, param, new AutoExchange() {
				public Object exec(Connection connection, WdataSetBiluder wds) {
					try {
						wds.execDynamic(connection, params, new HashSet<String>(param.keySet()));
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("execBatch error", ex);
						throw new RuntimeException(ex);
					}
					return null;
				}
			});
		}
	}

	public void execRemove(Long id, List<Map<String, Object>> params) {
		if (params != null && params.size() > 0) {
			JSONObject ruleJson = this.getRuleJson(id);

			// WdataSetBiluder wds = new WdataSetBiluder(ruleJson);

			this.autoExec(ruleJson, params.get(0), new AutoExchange() {
				public Object exec(Connection connection, WdataSetBiluder wds) {
					try {
						wds.executeRemove(connection, params);
					} catch (Exception ex) {
						ex.printStackTrace();
						logger.error("execBatch error", ex);
						throw new RuntimeException(ex);
					}
					return null;
				}
			});

		}

	}

	/**
	 * 获取/切换数据源
	 * 
	 * @param ruleJson
	 * @param param
	 * @return
	 */
	protected Connection getConnection(JSONObject ruleJson, Map<String, Object> param) {
		String systemName = null, databaseId = "databaseId";
		if (ruleJson != null) {
			systemName = ruleJson.getJSONObject("table").getString("systemName");
			if (param.containsKey(databaseId)) {
				String def = systemName;
				systemName = this.getSystemName("", MapUtils.getLong(param, databaseId, 0L));// 外部传进来的优先级高
				if (systemName.equalsIgnoreCase(Environment.DEFAULT_SYSTEM_NAME)) {
					systemName = def; // 默认切回原始保存的标段
				}
			}
		}
		if (StringUtils.isEmpty(systemName)) {
			systemName = Environment.DEFAULT_SYSTEM_NAME;
		}
		logger.debug("更新集操作:[" + systemName + "]标段");

		systemNameThreadLocal.set(systemName);
		Connection connection = DBConnectionFactory.getConnection(systemName);

		return connection;
	}

	/**
	 * 自动切库
	 * 
	 * @param ruleJson
	 * @param param
	 * @param ae
	 */
	protected void autoExec(final JSONObject ruleJson, final Map<String, Object> param, AutoExchange ae) {

		String systemName = Environment.getCurrentSystemName();

		Connection connection = null;
		try {
			connection = this.getConnection(ruleJson, param);
			connection.setAutoCommit(true);

			/**
			 * 需要切换当前环境
			 */
			Environment.setCurrentSystemName(systemNameThreadLocal.get());
			ae.exec(connection, new WdataSetBiluder(ruleJson));

			// connection.commit();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error(String.format("更新集id:%s 操作出错!", ruleJson.get("wdataSetId")), ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(connection);
			Environment.setCurrentSystemName(systemName);
		}

	}

	static interface AutoExchange {
		Object exec(Connection conn, WdataSetBiluder wds);
	}

	public DepBaseWdataSet getDepBaseWdataSet(Long id) {
		if (id == null) {
			return null;
		}
		DepBaseWdataSet depBaseWdataSet = getDepBaseWdataSetService().getDepBaseWdataSet(id);
		return depBaseWdataSet;
	}

	public DepBaseWdataSetService getDepBaseWdataSetService() {
		if (depBaseWdataSetService == null) {
			depBaseWdataSetService = ContextFactory.getBean("com.glaf.dep.base.service.depBaseWdataSetService");
		}
		return depBaseWdataSetService;
	}

	private JSONObject getRuleJson(Long id) {
		DepBaseWdataSet wdataSet = this.getDepBaseWdataSet(id);
		JSONObject json = null;
		if (StringUtils.isNotBlank(wdataSet.getRuleJson())) {
			json = JSON.parseObject(wdataSet.getRuleJson());
			json.put("wdataSetId", id);
		}

		return json;
	}

	private String getSystemName(String systemName, Long databaseId) {
		if ((StringUtils.isEmpty(systemName) || Environment.DEFAULT_SYSTEM_NAME.equalsIgnoreCase(systemName))
				&& (databaseId != null && databaseId > 0L)) {
			IDatabaseService databaseService = ContextFactory.getBean("databaseService");
			Database database = databaseService.getDatabaseById(databaseId);
			if (database != null) {
				systemName = database.getName();
			}
		}
		return systemName == null ? Environment.DEFAULT_SYSTEM_NAME : systemName;
	}

}