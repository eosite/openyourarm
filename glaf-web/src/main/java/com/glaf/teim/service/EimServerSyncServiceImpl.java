package com.glaf.teim.service;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.utils.HttpClientUtil;
import com.glaf.core.base.Scheduler;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.service.ISysSchedulerService;
import com.glaf.core.util.DateUtils;
import com.glaf.lanxin.utils.JSONFindUtils;
import com.glaf.teim.domain.EimBaseInfo;
import com.glaf.teim.domain.EimServerDataImp;
import com.glaf.teim.domain.EimServerTmp;
import com.glaf.teim.mapper.EimBaseInfoMapper;
import com.glaf.teim.mapper.EimServerDataImpMapper;
import com.glaf.teim.mapper.EimServerTmpMapper;

@Service("com.glaf.teim.service.eimServerSyncService")
@Transactional(readOnly = true)
public class EimServerSyncServiceImpl implements EimServerSyncService {
	protected final Log logger = LogFactory.getLog(getClass());
	private EimServerTmpService eimServerTmpService;
	protected EimBaseInfoMapper eimBaseInfoMapper;
	protected EimServerTmpMapper eimServerTmpMapper;
	private ISysSchedulerService sysSchedulerService;
	private EimServerDataImpMapper eimServerDataImpMapper;
	private static final ThreadLocal<Integer> count = new ThreadLocal<Integer>();
	private static final ThreadLocal<List<String>> recursiveList = new ThreadLocal<List<String>>();

	@Override
	public void syncNoToken(String baseId, String tmpId, String targetDatabase, String targetTableName,
			JSONObject columnMapping, JSONObject paramsMap, String[] extColumnStr) throws Exception {
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
		LinkedHashMap<String, String> recursiveContentMap = new LinkedHashMap<String, String>();
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
				recursiveContentMap.put(entry.getKey(), (String) entry.getValue());
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
			String insertSQL = createInsertSQL(targetTableName, columnMapping, metaData, returnDataColumns,
					extColumnStr);
			cmd = conn.prepareStatement(insertSQL);
			logger.info(
					"开始将应用实例为" + baseId + ",模板实例为" + tmpId + "的服务数据同步到目标库" + targetDatabase + ",数据表" + targetTableName);
			// 获取数据所在路径
			String response = eimServerTmp.getResponse_();
			JSONObject reponseJson = JSONObject.parseObject(response);
			String datapos = reponseJson.getJSONObject("data").getString("datapos");
			if (StringUtils.isEmpty(datapos)) {
				datapos = "root";
			}
			importData(eimBaseInfo, eimServerTmp, pagingContentJson, recursiveContentMap, currentPage, paramsMap,
					returnDataColumns, datapos, conn, cmd);
			cmd.executeBatch();
			conn.commit();
			// 更新记录的导入状态
			cmd = conn.prepareStatement("update " + targetTableName + " set status=1 where status=0");
			cmd.execute();
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

	public void syncNoToken(EimBaseInfo eimBaseInfo, EimServerTmp eimServerTmp, String targetDatabase,
			String targetTableName, JSONObject columnMapping, JSONObject paramsMap, String[] extColumnStr)
					throws Exception {

		String pagingContent = eimServerTmp.getPagingContent();
		JSONObject pagingContentJson = null;
		JSONObject recursiveContentJson = null;
		LinkedHashMap<String, String> recursiveContentMap = new LinkedHashMap<String, String>();
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
				recursiveContentMap.put(entry.getKey(), (String) entry.getValue());
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
			String insertSQL = createInsertSQL(targetTableName, columnMapping, metaData, returnDataColumns,
					extColumnStr);
			cmd = conn.prepareStatement(insertSQL);
			logger.info("开始将应用实例为" + eimBaseInfo.getId() + ",模板实例为" + eimServerTmp.getTmpId() + "的服务数据同步到目标库"
					+ targetDatabase + ",数据表" + targetTableName);
			// 获取数据所在路径
			String response = eimServerTmp.getResponse_();
			JSONObject reponseJson = JSONObject.parseObject(response);
			String datapos = reponseJson.getJSONObject("data").getString("datapos");
			if (StringUtils.isEmpty(datapos)) {
				datapos = "root";
			}
			importData(eimBaseInfo, eimServerTmp, pagingContentJson, recursiveContentMap, currentPage, paramsMap,
					returnDataColumns, datapos, conn, cmd);
			cmd.executeBatch();
			conn.commit();
			// 更新记录的导入状态
			cmd = conn.prepareStatement("update " + targetTableName + " set status=1 where status=0");
			cmd.execute();
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
			List<String[]> returnDataColumns, String[] extColumnStr) {
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
		String encryFlag = "false";
		for (Entry<String, Object> columnMeta : metaData.entrySet()) {
			encryFlag = "false";
			columnName = columnMeta.getKey();
			columnJson = (JSONObject) columnMeta.getValue();
			dType = columnJson.getString("type");
			if (columnMapping != null && columnMapping.getString(columnName) != null) {
				dbColumnName = columnMapping.getString(columnName);
			} else {
				dbColumnName = columnName;
			}
			if (columnSQL == null) {
				columnSQL = dbColumnName;
				valueSQL = "?";
			}

			else {
				if (dbColumnName.equals("order")) {
					columnSQL += "," + dbColumnName + "_";
				} else {
					columnSQL += "," + dbColumnName;
				}
				valueSQL += "," + "?";
			}
			dbColumnArray = new String[3];
			if (columnJson != null && columnJson.containsKey("encryFlag")) {
				encryFlag = columnJson.getString("encryFlag");
			}
			dbColumnArray[0] = columnName;
			dbColumnArray[1] = dType;
			dbColumnArray[2] = encryFlag;
			returnDataColumns.add(dbColumnArray);
		}
		// 补充处理状态导入时间列
		columnSQL += ",status,importtime";
		if (extColumnStr != null && StringUtils.isNotEmpty(extColumnStr[0])) {
			columnSQL += "," + extColumnStr[0];
		}
		valueSQL += ",?,?";
		if (extColumnStr != null && StringUtils.isNotEmpty(extColumnStr[1])) {
			valueSQL += "," + extColumnStr[1];
		}
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
			paramsMap.put(pagingContentJson.getString("currentPage"), currentPage);
			logger.info("开始导入第" + currentPage + "页数据！参数为：" + paramsMap.toJSONString());
		}
		JSONObject returnJSON = eimServerTmpService.callServiceNoToken(eimServerTmp.getTmpId(), eimBaseInfo.getId(),
				paramsMap);
		int statusCode = returnJSON.getIntValue("statusCode");
		if (statusCode == 200) {
			// 获取返回错误状态码
			Object returnDataJson = null;
			try {
				returnDataJson = returnJSON.getJSONObject("return");
			} catch (Exception e) {
				returnDataJson = null;
			}
			if (returnDataJson == null) {
				try {
					returnDataJson = returnJSON.getJSONArray("return");
				} catch (Exception e) {
					returnDataJson = null;
				}
			}
			if (returnDataJson != null) {
				JSONArray data = new JSONArray();
				String[] posArray = datapos.split(",");
				if (returnDataJson instanceof JSONObject) {
					JSONObject returnDataJsonObj = (JSONObject) returnDataJson;
					// Integer total = returnDataJsonObj.getInteger("total");
					// if (total != null && total > 0) {
					if (currentPage == 1) {
						// 获取总记录数
						String totalVal = pagingContentJson.getString("totalCount");
						int totalCount = 0;
						if (totalVal.startsWith("{")) {
							JSONObject totalPropJson = JSONObject.parseObject(totalVal);
							// 获取总记录数服务地址
							String countUrl = totalPropJson.getString("url");
							// 获取总记录数所在返回数据中的位置
							String countDataPos = totalPropJson.getString("datapos");
							String totalAddress = eimBaseInfo.getHost() + countUrl;
							try {
								JSONObject totalReturnJSON = HttpClientUtil.getRequest(totalAddress, null, null,
										"application/json", 30000);
								if (totalReturnJSON.containsKey("return")) {
									if (StringUtils.isNotEmpty(totalReturnJSON.getString("return"))) {
										JSONObject totalJson = JSONObject
												.parseObject(totalReturnJSON.getString("return"));
										totalCount = totalJson.getIntValue(countDataPos);
									}
								}
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
						} else {
							totalCount = returnDataJsonObj.getIntValue(totalVal);
						}
						// 获取单页记录数
						int pageSize = 10;
						String pageSizeParamName = pagingContentJson.getString("pageSize");
						if (returnDataJsonObj.containsKey(pageSizeParamName)
								&& returnDataJsonObj.get(pageSizeParamName) instanceof Integer) {
							pageSize = returnDataJsonObj.getIntValue(pageSizeParamName);
						} else if (paramsMap.containsKey(pageSizeParamName)) {
							pageSize = paramsMap.getIntValue(pageSizeParamName);
						}

						// 获取总页数
						int totalPage = (totalCount + pageSize - 1) / pageSize;
						for (int i = 2; i <= totalPage; i++) {
							JSONObject paramsMapClone = new JSONObject();
							paramsMapClone.putAll(paramsMap);
							importData(eimBaseInfo, eimServerTmp, pagingContentJson, recursiveContentMap, i,
									paramsMapClone, returnDataColumns, datapos, conn, cmd);
						}
					}
					// }
					for (String pos : posArray) {
						JSONFindUtils.getJSONArrayByPath(data, returnDataJsonObj, pos);
					}
				} else if (returnDataJson instanceof JSONArray && "root".equals(datapos)) {
					JSONArray returnDataJsonArr = (JSONArray) returnDataJson;
					data.addAll(returnDataJsonArr);
				}
				JSONObject rowJson = null;
				String colName = null;
				String dType = null;
				Object colVal = null;
				String encryFlag = null;
				String paramName = null;
				String paramColumnName = null;
				String paramVal = null;
				int currPage = 0;
				if (data != null && data.size() > 0) {
					for (int j = 0; j < data.size(); j++) {
						colName = null;
						dType = null;
						colVal = null;
						encryFlag = null;
						rowJson = data.getJSONObject(j);
						// 循环赋值
						int k = 1;
						for (String[] column : returnDataColumns) {
							colName = column[0];
							dType = column[1];
							encryFlag = column[2];
							if (dType.equals("string")) {
								colVal = rowJson.getString(colName);
								// 加密处理
								// if(encryFlag.equals("true")&&colVal!=null&&StringUtils.isNotEmpty((String)
								// colVal)){
								// colVal=FlkWebCryptUtils.getInstance().encrypt((String)
								// colVal, FlkWebCryptUtils.TYPE_CODER_BASE64);
								// }
								cmd.setString(k, (String) colVal);
							} else if (dType.equals("int")) {
								colVal = rowJson.getIntValue(colName);
								cmd.setInt(k, (Integer) colVal);
							} else if (dType.equals("jsonarr")) {
								colVal = rowJson.getJSONArray(colName);
								cmd.setString(k, colVal != null ? ((JSONArray) colVal).toJSONString() : null);
							} else if (dType.equals("json")) {
								colVal = rowJson.getJSONObject(colName);
								cmd.setString(k, JSONObject.toJSONString((JSONObject) colVal));
							}
							k++;
						}
						// 补充处理状态导入时间数据
						cmd.setInt(k, 0);
						cmd.setTimestamp(k + 1, new Timestamp(new Date().getTime()));
						execute(conn, cmd);

						// 递归参数赋值
						if (recursiveContentMap != null && recursiveContentMap.size() > 0) {
							JSONObject paramsMapClone = new JSONObject();
							paramsMapClone.putAll(paramsMap);
							String val = null;
							for (Entry<String, String> entry : recursiveContentMap.entrySet()) {
								paramName = entry.getKey();
								paramColumnName = entry.getValue();
								paramVal = rowJson.getString(paramColumnName);
								paramsMapClone.put(paramName, paramVal);
								if (val == null) {
									val = "";
								}
								val += paramName + paramVal;
							}
							if (StringUtils.isNotEmpty(val) && !getRecursivelist().contains(val)) {
								getRecursivelist().add(val);
								if (StringUtils.isNotEmpty(pagingContentJson.getString("currentPage"))) {
									currPage = 1;
								}
								importData(eimBaseInfo, eimServerTmp, pagingContentJson, recursiveContentMap, currPage,
										paramsMapClone, returnDataColumns, datapos, conn, cmd);
							}
						}
					}
				}
				// }
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

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimBaseInfoMapper")
	public void setEimBaseInfoMapper(EimBaseInfoMapper eimBaseInfoMapper) {
		this.eimBaseInfoMapper = eimBaseInfoMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.service.eimServerTmpService")
	public void setEimServerTmpService(EimServerTmpService eimServerTmpService) {
		this.eimServerTmpService = eimServerTmpService;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimServerTmpMapper")
	public void setEimServerTmpMapper(EimServerTmpMapper eimServerTmpMapper) {
		this.eimServerTmpMapper = eimServerTmpMapper;
	}

	@javax.annotation.Resource(name = "sysSchedulerService")
	public void setSysSchedulerService(ISysSchedulerService sysSchedulerService) {
		this.sysSchedulerService = sysSchedulerService;
	}

	@javax.annotation.Resource(name = "com.glaf.teim.mapper.EimServerDataImpMapper")
	public void setEimServerDataImpMapper(EimServerDataImpMapper eimServerDataImpMapper) {
		this.eimServerDataImpMapper = eimServerDataImpMapper;
	}

	@Override
	public void sysncDataByTaskId(String taskId) throws Exception {
		// 获取调度定义
		Scheduler model = sysSchedulerService.getSchedulerByTaskId(taskId);
		// 获取服务数据导入实例
		String serverDataImpInst = model.getAttribute();
		EimServerDataImp eimServerDataImp = eimServerDataImpMapper.getEimServerDataImpById(serverDataImpInst);
		// 应用ID
		String baseId = eimServerDataImp.getAppId();
		// 服务模板ID
		String tmpId = eimServerDataImp.getTmpId();
		EimBaseInfo eimBaseInfo = eimBaseInfoMapper.getEimBaseInfoById(baseId);
		if (eimBaseInfo == null) {
			return;
		}
		EimServerTmp eimServerTmp = eimServerTmpMapper.getEimServerTmpById(tmpId);
		if (eimServerTmp == null) {
			return;
		}
		// 目标库
		String targetDatabase = eimServerDataImp.getTargetDatabase();
		// 目标表
		String targetTable = eimServerDataImp.getTargetTable();
		// 字段映射
		String columnMapping = eimServerDataImp.getColumnMapping();
		JSONObject columnMappingJson = null;
		if (StringUtils.isNotEmpty(columnMapping)) {
			columnMappingJson = JSONObject.parseObject(columnMapping);
		}
		// 获取导入模式
		Integer incrementFlag = eimServerDataImp.getIncrementFlag();
		// 获取参数定义
		String params = eimServerDataImp.getParams();
		JSONObject paramsJson = new JSONObject();
		JSONObject defaultParamsJson = new JSONObject();
		// 获取头模板信息
		String reqHeaderTmp = eimServerTmp.getReqHeader();
		if (StringUtils.isNotEmpty(reqHeaderTmp)) {
			defaultParamsJson.putAll(JSONObject.parseObject(reqHeaderTmp));
		}
		// 获取URL请求参数模板
		String queryParamTmp = eimServerTmp.getReqUrlParam();
		if (StringUtils.isNotEmpty(queryParamTmp)) {
			defaultParamsJson.putAll(JSONObject.parseObject(queryParamTmp));
		}
		String reqBodyTmp = eimServerTmp.getReqBody();
		if (StringUtils.isNotEmpty(reqBodyTmp)) {
			defaultParamsJson.putAll(JSONObject.parseObject(reqBodyTmp));
		}
		Map<String, String> defaultParamsMap = createMapByParamsJson(defaultParamsJson);
		JSONObject paramValJson = new JSONObject();
		if (defaultParamsMap != null && defaultParamsMap.size() > 0) {
			paramValJson.putAll(defaultParamsMap);
		}
		if (params != null && params.length() > 0) {
			paramsJson.putAll(JSONObject.parseObject(params));
		}
		JSONObject paramJson = null;
		// 增量参数
		Integer incrementParam = null;
		// 参数值
		String paramVal = null;
		// 增量参数上限值
		String upperLimit = null;
		// 增量参数下限值
		String lowerLimit = null;
		// 增量参数数据类型
		String dType = null;
		// 增量参数名称
		String incrementParamName = null;
		for (String key : paramsJson.keySet()) {
			paramJson = paramsJson.getJSONObject(key);
			incrementParam = paramJson.getInteger("incrementParam");
			paramVal = paramJson.getString("lowerLimit");
			if (incrementParam == 1) {
				incrementParamName = key;
				dType = paramJson.getString("type");
				upperLimit = paramJson.getString("upperLimit");
				lowerLimit = paramJson.getString("lowerLimit");
			}
			if (paramVal.equals("${currentDate}")) {
				paramVal = DateUtils.getDate(new Date());
			}
			paramValJson.put(key, paramVal);
		}
		// 获取导入前清空记录标识
		Integer emptyTable = eimServerDataImp.getEmptyTable();
		if (emptyTable == 1) {
			emptyTable(targetDatabase, targetTable);
		}
		// 获取导入前执行语句
		// String preSQL=eimServerDataImp.getPreSql();
		// 增量模式
		if (incrementFlag == 1) {
			// 数值型
			if (dType.equals("int")) {
				int lowerLimitVal = Integer.parseInt(lowerLimit);
				int upperLimitVal = Integer.parseInt(upperLimit);
				int successFlag = 0;
				Date finishDate = null;
				for (int j = lowerLimitVal; j < upperLimitVal; j++) {
					paramValJson.put(incrementParamName, "" + j);
					try {
						syncNoToken(eimBaseInfo, eimServerTmp, targetDatabase, targetTable, columnMappingJson,
								paramValJson, null);
						// 更新增量参数上限值
						logger.info("开始导入增量参数" + incrementParamName + "值为" + j + "的服务接口数据！");
						paramsJson.getJSONObject(incrementParamName).put("lowerLimit", "" + (j + 1));
						// eimServerDataImp.setParams(paramsJson.toJSONString());
						logger.info("完成导入增量参数" + incrementParamName + "值为" + j + "的服务接口数据！");
						successFlag = 1;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error("导入增量参数" + incrementParamName + "值为" + j + "的服务接口数据出错！" + e.getMessage());
					} finally {
						// 设置导入状态
						// 设置导入完成时间
						finishDate = new Date();
						eimServerDataImpMapper.updateEimServerDataImpLog(serverDataImpInst, paramsJson.toJSONString(),
								successFlag, finishDate);
					}
				}
			} else if (dType.equals("date")) {
				Date upperLimitVal = null;
				if (upperLimit.equals("${currentDate}")) {
					upperLimitVal = new Date();
				} else {
					upperLimitVal = DateUtils.toDate(upperLimit);
				}
				Date lowerLimitVal = DateUtils.toDate(lowerLimit);
				// 获取相差天数
				int diffDays = DateUtils.getDaysBetween(lowerLimitVal, upperLimitVal);
				Date paramDate = null;
				String paramDateStr = null;
				int successFlag = 0;
				Date finishDate = null;
				for (int j = 0; j < diffDays; j++) {
					paramDate = DateUtils.getDateAfter(lowerLimitVal, j);
					paramDateStr = DateUtils.getDate(paramDate);
					paramValJson.put(incrementParamName, paramDateStr);
					try {
						syncNoToken(baseId, tmpId, targetDatabase, targetTable, columnMappingJson, paramValJson, null);
						// 更新增量参数上限值
						logger.info("开始导入增量参数" + incrementParamName + "值为" + paramDateStr + "的服务接口数据！");
						paramsJson.getJSONObject(incrementParamName).put("lowerLimit",
								DateUtils.getDate(DateUtils.getDateAfter(paramDate, 1)));
						// eimServerDataImp.setParams(paramsJson.toJSONString());
						logger.info("完成导入增量参数" + incrementParamName + "值为" + paramDateStr + "的服务接口数据！");
						successFlag = 1;
					} catch (Exception e) {
						// TODO Auto-generated catch block
						logger.error(
								"导入增量参数" + incrementParamName + "值为" + paramDateStr + "的服务接口数据出错！" + e.getMessage());
					} finally {
						// 设置导入状态
						// 设置导入完成时间
						finishDate = new Date();
						eimServerDataImpMapper.updateEimServerDataImpLog(serverDataImpInst, paramsJson.toJSONString(),
								successFlag, finishDate);
					}
				}
			}

		}
		// 全量模式
		else {
			try {
				syncNoToken(baseId, tmpId, targetDatabase, targetTable, columnMappingJson, paramValJson, null);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("导入服务接口数据出错！" + e.getMessage());
				throw e;
			}
		}

	}

	/**
	 * 创建默认参数集合
	 * 
	 * @param paramsJson
	 * @param tmpJsonStr
	 * @return
	 */
	public Map<String, String> createMapByParamsJson(JSONObject paramsJson) {
		Map<String, String> map = new HashMap<String, String>();
		String key = null;
		JSONObject itemJSON = null;
		String val = null;
		for (Entry<String, Object> entry : paramsJson.entrySet()) {
			key = entry.getKey();
			itemJSON = entry.getValue() != null ? (JSONObject) entry.getValue() : null;
			if (StringUtils.isNotEmpty(key) && itemJSON != null) {
				// 获取值
				val = null;
				if (StringUtils.isEmpty(val)) {
					val = itemJSON.getString("defaultval");
				}
				if (StringUtils.isNotEmpty(val)) {
					map.put(key, val);
				}
			}
		}
		return map;
	}

	/**
	 * 清空表数据
	 * 
	 * @param targetDatabase
	 * @param targetTable
	 * @throws SQLException
	 */
	private void emptyTable(String targetDatabase, String targetTable) throws SQLException {
		Connection conn = null;
		Statement cmd = null;
		try {
			if (StringUtils.isNotEmpty(targetDatabase)) {
				conn = DBConnectionFactory.getConnection(targetDatabase);

			} else {
				conn = DBConnectionFactory.getConnection();
			}
			conn.setAutoCommit(false);
			cmd = conn.createStatement();
			cmd.execute("delete from " + targetTable);
			conn.commit();
		} catch (Exception e) {
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
}
