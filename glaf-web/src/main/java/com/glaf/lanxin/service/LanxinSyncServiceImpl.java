package com.glaf.lanxin.service;

import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.lanxin.utils.JSONFindUtils;
import com.glaf.teim.domain.EimBaseInfo;
import com.glaf.teim.domain.EimServerTmp;
import com.glaf.teim.mapper.EimBaseInfoMapper;
import com.glaf.teim.mapper.EimServerTmpMapper;

@Service("com.glaf.lanxin.service.lanxinSyncService")
@Transactional(readOnly = true)
public class LanxinSyncServiceImpl implements LanxinSyncService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
	protected LanxinService lanxinService;
	protected EimBaseInfoMapper eimBaseInfoMapper;
	protected EimServerTmpMapper eimServerTmpMapper;
	private static final ThreadLocal<Integer> count = new ThreadLocal<Integer>();
	private static final ThreadLocal<List<String>> recursiveList = new ThreadLocal<List<String>>();
	@Override
	public void sync(String baseId, String tmpId, String targetDatabase, String targetTableName,
			JSONObject columnMapping, JSONObject paramsMap) throws Exception {
		EimBaseInfo eimBaseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		if (eimBaseInfo == null) {
			return;
		}
		EimServerTmp eimServerTmp = eimServerTmpMapper.getEimServerTmpById(tmpId);
		if (eimServerTmp == null) {
			return;
		}
		String pagingContent = eimServerTmp.getPagingContent();
		JSONObject pagingContentJson = null;
		JSONObject recursiveContentJson = null;
		LinkedHashMap<String,String> recursiveContentMap = new LinkedHashMap<String,String>();
		int currentPage = 0;
		if (StringUtils.isNotEmpty(pagingContent)) {
			pagingContentJson = JSONObject.parseObject(pagingContent);
			if (StringUtils.isNotEmpty(pagingContentJson.getString("currentPage")))
				currentPage = 1;
		}
		String recursiveContent = eimServerTmp.getRecursiveContent();
		if (StringUtils.isNotEmpty(recursiveContent)) {
			recursiveContentJson = JSONObject.parseObject(recursiveContent);
			for (Entry<String, Object> entry : recursiveContentJson.entrySet()) {
				recursiveContentMap.put(entry.getKey(), (String)entry.getValue());
			}
		}
		Connection conn = null;
		PreparedStatement cmd = null;
		try {
			if (StringUtils.isNotEmpty(targetDatabase)) {
				conn = DBConnectionFactory.getConnection(targetDatabase);
			} else {
				conn = DBConnectionFactory.getConnection();
			}
			conn.setAutoCommit(false);
			String response_ = eimServerTmp.getResponse_();
			// 获取返回记录元数据信息
			JSONObject metaData = JSONObject.parseObject(response_).getJSONObject("data").getJSONObject("dataitem");
			// 返回数据列顺序 用于插入数据时设置值的位置
			List<String[]> returnDataColumns = new ArrayList<String[]>();
			// 创建插入语句
			String insertSQL = createInsertSQL(targetTableName, columnMapping, metaData, returnDataColumns);
			cmd = conn.prepareStatement(insertSQL);
			logger.info(
					"开始将应用实例为" + baseId + ",模板实例为" + tmpId + "的服务数据同步到目标库" + targetDatabase + ",数据表" + targetTableName);
			// 获取数据所在路径
			String response = eimServerTmp.getResponse_();
			JSONObject reponseJson = JSONObject.parseObject(response);
			String datapos = reponseJson.getJSONObject("data").getString("datapos");
			if (StringUtils.isEmpty(datapos)) {
				datapos = "data";
			}
			importData(eimBaseInfo, eimServerTmp, pagingContentJson, recursiveContentMap, currentPage, paramsMap,
					returnDataColumns, datapos, conn, cmd);
			cmd.executeBatch();
			conn.commit();
		} catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;
		} finally {
			try {
				if (cmd != null)
					cmd.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
				throw e;
			}
		}

	}
	public void sync(EimBaseInfo eimBaseInfo, EimServerTmp eimServerTmp, String targetDatabase, String targetTableName,
			JSONObject columnMapping, JSONObject paramsMap) throws Exception {

		String pagingContent = eimServerTmp.getPagingContent();
		JSONObject pagingContentJson = null;
		JSONObject recursiveContentJson = null;
		LinkedHashMap<String,String> recursiveContentMap = new LinkedHashMap<String,String>();
		int currentPage = 0;
		if (StringUtils.isNotEmpty(pagingContent)) {
			pagingContentJson = JSONObject.parseObject(pagingContent);
			if (StringUtils.isNotEmpty(pagingContentJson.getString("currentPage")))
				currentPage = 1;
		}
		String recursiveContent = eimServerTmp.getRecursiveContent();
		if (StringUtils.isNotEmpty(recursiveContent)) {
			recursiveContentJson = JSONObject.parseObject(recursiveContent);
			for (Entry<String, Object> entry : recursiveContentJson.entrySet()) {
				recursiveContentMap.put(entry.getKey(), (String)entry.getValue());
			}
		}
		Connection conn = null;
		PreparedStatement cmd = null;
		try {
			if (StringUtils.isNotEmpty(targetDatabase)) {
				conn = DBConnectionFactory.getConnection(targetDatabase);
			} else {
				conn = DBConnectionFactory.getConnection();
			}
			conn.setAutoCommit(false);
			String response_ = eimServerTmp.getResponse_();
			// 获取返回记录元数据信息
			JSONObject metaData = JSONObject.parseObject(response_).getJSONObject("data").getJSONObject("dataitem");
			// 返回数据列顺序 用于插入数据时设置值的位置
			List<String[]> returnDataColumns = new ArrayList<String[]>();
			// 创建插入语句
			String insertSQL = createInsertSQL(targetTableName, columnMapping, metaData, returnDataColumns);
			cmd = conn.prepareStatement(insertSQL);
			logger.info(
					"开始将应用实例为" + eimBaseInfo.getId() + ",模板实例为" + eimServerTmp.getTmpId() + "的服务数据同步到目标库" + targetDatabase + ",数据表" + targetTableName);
			// 获取数据所在路径
			String response = eimServerTmp.getResponse_();
			JSONObject reponseJson = JSONObject.parseObject(response);
			String datapos = reponseJson.getJSONObject("data").getString("datapos");
			if (StringUtils.isEmpty(datapos)) {
				datapos = "data";
			}
			importData(eimBaseInfo, eimServerTmp, pagingContentJson, recursiveContentMap, currentPage, paramsMap,
					returnDataColumns, datapos, conn, cmd);
			cmd.executeBatch();
			conn.commit();
		} catch (SQLException | IllegalAccessException | InstantiationException | InvocationTargetException
				| NoSuchMethodException e) {
			// TODO Auto-generated catch block
			logger.error(e.getMessage());
			throw e;
		} finally {
			try {
				if (cmd != null)
					cmd.close();
				if (conn != null)
					conn.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				logger.error(e.getMessage());
			}
		}

	}

	/**
	 * 生成插入语句
	 * 
	 * @param columnMapping
	 *            列映射
	 * @param metaData
	 *            返回数据行元数据
	 * @return
	 */
	public String createInsertSQL(String targetTableName, JSONObject columnMapping, JSONObject metaData,
			List<String[]> returnDataColumns) {
		String sql = "insert into " + targetTableName + "(";
		String columnSQL = null;
		String valueSQL = null;
		// 返回数据列名称
		String columnName = null;
		// 数据库表列名
		String dbColumnName = null;
		// 返回数据列类型
		String dType = null;
		JSONObject columnJson = null;
		String[] dbColumnArray = null;
		for (Entry<String, Object> columnMeta : metaData.entrySet()) {
			columnName = columnMeta.getKey();
			columnJson = (JSONObject) columnMeta.getValue();
			dType = columnJson.getString("type");
			if (columnMapping != null && columnMapping.getString("columnName") != null) {
				dbColumnName = columnMapping.getString("columnName");
			} else {
				dbColumnName = columnName;
			}
			if (columnSQL == null) {
				columnSQL = dbColumnName;
				valueSQL = "?";
			}

			else {
				columnSQL += "," + dbColumnName;
				valueSQL += "," + "?";
			}
			dbColumnArray = new String[2];
			dbColumnArray[0] = dbColumnName;
			dbColumnArray[1] = dType;
			returnDataColumns.add(dbColumnArray);
		}
		// 补充处理状态导入时间列
		columnSQL += ",status,importtime";
		valueSQL += ",?,?";
		sql = sql + columnSQL + ") values (" + valueSQL + ")";
		return sql;
	}

	/**
	 * 导入数据
	 * 
	 * @param eimBaseInfo
	 *            应用基础信息
	 * @param eimServerTmp
	 *            服务模板
	 * @param pagingContentJson
	 *            分页规则
	 * @param recursiveContentJson
	 *            递归规则
	 * @param currentPage
	 *            当前页
	 * @param paramsMap
	 *            参数
	 * @param returnDataColumns
	 *            返回数据列名称和类型
	 * @param cmd
	 *            PreparedStatement
	 * @throws IllegalAccessException
	 * @throws InstantiationException
	 * @throws InvocationTargetException
	 * @throws NoSuchMethodException
	 * @throws SQLException
	 */
	public void importData(EimBaseInfo eimBaseInfo, EimServerTmp eimServerTmp, JSONObject pagingContentJson,
			LinkedHashMap<String, String> recursiveContentMap, int currentPage, JSONObject paramsMap,
			List<String[]> returnDataColumns, String datapos, Connection conn, PreparedStatement cmd)
					throws IllegalAccessException, InstantiationException, InvocationTargetException,
					NoSuchMethodException, SQLException {
		if (currentPage > 0) {
			paramsMap.put("currentPage", currentPage);
			logger.info(
					"开始导入第" + currentPage+"页数据！参数为："+paramsMap.toJSONString());
		}
		JSONObject returnJSON = lanxinService.callService(eimServerTmp.getTmpId(), eimBaseInfo.getId(), paramsMap);
		int statusCode = returnJSON.getIntValue("statusCode");
		if (statusCode == 200) {
			// 获取返回错误状态码
			JSONObject returnDataJson = returnJSON.getJSONObject("return");
			if (returnDataJson != null) {
				Integer errcode = returnDataJson.getInteger("errcode");
				if (errcode == 0) {
					if (currentPage == 1) {
						// 获取总记录数
						int totalCount = returnDataJson.getIntValue("totalCount");
						// 获取单页记录数
						int pageSize = returnDataJson.getIntValue("pageSize");
						// 获取总页数
						int totalPage = (totalCount + pageSize - 1) / pageSize;
						for (int i = 2; i <= totalPage; i++) {
							JSONObject paramsMapClone = new JSONObject();
							paramsMapClone.putAll(paramsMap);
							importData(eimBaseInfo, eimServerTmp, pagingContentJson, recursiveContentMap, i,
									paramsMapClone, returnDataColumns, datapos, conn, cmd);
						}
					}
					JSONArray data = new JSONArray();
					String[] posArray = datapos.split(",");
					for (String pos : posArray) {
						JSONFindUtils.getJSONArrayByPath(data, returnDataJson, pos);
					}
					JSONObject rowJson = null;
					String colName = null;
					String dType = null;
					Object colVal = null;
					String paramName = null;
					String paramColumnName = null;
					String paramVal = null;
					int currPage = 0;
					if (data != null && data.size() > 0) {
						for (int j = 0; j < data.size(); j++) {
							colName = null;
							dType = null;
							colVal = null;
							rowJson = data.getJSONObject(j);
							// 循环赋值
							int k = 1;
							for (String[] column : returnDataColumns) {
								colName = column[0];
								dType = column[1];
								if (dType.equals("string")) {
									colVal = rowJson.getString(colName);
									cmd.setString(k, (String) colVal);
								} else if (dType.equals("int")) {
									colVal = rowJson.getIntValue(colName);
									cmd.setInt(k, (Integer) colVal);
								}else if (dType.equals("jsonarr")) {
									colVal = rowJson.getJSONArray(colName);
									cmd.setString(k, colVal!=null?((JSONArray)colVal).toJSONString():null);
								}else if (dType.equals("json")) {
									colVal = rowJson.getJSONObject(colName);
									cmd.setString(k, JSONObject.toJSONString((JSONObject)colVal));
								}
								k++;
							}
							// 补充处理状态导入时间数据
							cmd.setInt(k, 0);
							cmd.setTimestamp(k + 1, new Timestamp(new Date().getTime()));
							execute(conn, cmd);

							// 递归参数赋值
							if (recursiveContentMap != null&&recursiveContentMap.size()>0) {
								JSONObject paramsMapClone = new JSONObject();
								paramsMapClone.putAll(paramsMap);
								String val = null;
								for (Entry<String, String> entry : recursiveContentMap.entrySet()) {
									paramName = entry.getKey();
									paramColumnName = entry.getValue();
									paramVal = rowJson.getString(paramColumnName);
									paramsMapClone.put(paramName, paramVal);
									if(val==null){
										val="";
									}
									val += paramName + paramVal;
								}
								if (StringUtils.isNotEmpty(val)&&!getRecursivelist().contains(val)) {
									getRecursivelist().add(val);
									if (StringUtils.isNotEmpty(pagingContentJson.getString("currentPage"))) {
										currPage = 1;
									}
									importData(eimBaseInfo, eimServerTmp, pagingContentJson, recursiveContentMap,
											currPage, paramsMapClone, returnDataColumns, datapos, conn, cmd);
								}
							}
						}
					}
				}
			}
		}

	}

	public void execute(Connection conn, PreparedStatement cmd) throws SQLException {
		cmd.addBatch();
		int n = getCount();
		n++;
		if (n % 100 == 0) {
			cmd.executeBatch();
			conn.commit();
			cmd.clearBatch();
		}
		count.set(n);
	}

	public static int getCount() {
		if (count.get() == null) {
			count.set(1);
			return 1;
		} else {
			return count.get();
		}
	}

	public static List<String> getRecursivelist() {
		if (recursiveList.get() == null) {
			ArrayList<String> list = new ArrayList<String>();
			recursiveList.set(list);
			return list;
		} else {
			return recursiveList.get();
		}
	}

	@Resource(name = "com.glaf.lanxin.service.lanxinService")
	public void setLanxinService(LanxinService lanxinService) {
		this.lanxinService = lanxinService;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimBaseInfoMapper")
	public void setEimBaseInfoMapper(EimBaseInfoMapper eimBaseInfoMapper) {
		this.eimBaseInfoMapper = eimBaseInfoMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimServerTmpMapper")
	public void setEimServerTmpMapper(EimServerTmpMapper eimServerTmpMapper) {
		this.eimServerTmpMapper = eimServerTmpMapper;
	}
}
