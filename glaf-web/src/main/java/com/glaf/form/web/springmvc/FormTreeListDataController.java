package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.base.modules.sys.model.SysTree;
import com.glaf.base.modules.sys.service.DictoryService;
import com.glaf.base.modules.sys.service.SysTreeService;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.DataRequest.SortDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.config.Environment;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.Database;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.service.IDatabaseService;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.ParamUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.datamgr.bean.TreeTableAggregateBean;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.form.core.helper.SqlHelper;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.DataSourceRequest;
import com.glaf.form.rule.Global;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.util.BulkInsertExtBean;
import com.glaf.form.rule.util.InjectUtils;

/**
 * 通用数据调用
 * 
 */
@Controller("/form/treelist")
@RequestMapping("/form/treelist")
public class FormTreeListDataController {
	protected static final Log logger = LogFactory.getLog(FormTreeListDataController.class);

	protected FormRulePropertyService formRulePropertyService;
	protected MutilDatabaseBean mutilDatabaseBean;
	@Autowired
	protected IDatabaseService databaseService;

	@Autowired
	protected DataSetService dataSetService;
	

	protected DictoryService dictoryService;

	protected SysTreeService sysTreeService;


	public FormTreeListDataController() {

	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/createData")
	public @ResponseBody byte[] createData(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest) throws IOException {
		LoginContext loginContext = RequestUtils.getLoginContext(request);

		// String prid = dataSourceRequest.getPrid();// 父规则id

		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id
		String parentId = dataSourceRequest.getParentId();// 父id
		List<Map<String, Object>> models = (List<Map<String, Object>>) dataSourceRequest.getModels();
		if (models == null || models.size() <= 0) {
			return null;
		}
		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		String tables = datasourceSetJSONObject.getString("tables");
		JSONArray tablesJSONArray = null;
		if (!isNullOrEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || tablesJSONArray.size() != 1) { // 如果是多表的
																		// 不支持操作
			return null;
		}
		String tableName = tablesJSONArray.getString(0);
		// end

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		String params = dataSourceRequest.getParams();
		JSONObject paramsObj = new JSONObject();
		if(StringUtils.isNotEmpty(params)){
			paramsObj = JSONObject.parseObject(params);
			InjectUtils.escapeSql(paramsObj);
		}
		if (!paramsObj.isEmpty()) {
			try {
				Long dbid = paramsObj.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(tableName);

		ColumnDefinition idColumn = null;

		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
				break;
			}
		}

		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")), mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}

		//
		Map<String, String> keyMap = getKeyMap(ruleMap);
		String oIdKey = keyMap.get("idKey");
		String oIndexKey = keyMap.get("indexKey");
		String oPIdKey = keyMap.get("pIdKey");

		String sIdKey = Global.getOriginalColumnName(mapping, oIdKey);
		String sIndexKey = Global.getOriginalColumnName(mapping, oIndexKey);
		String sPIdKey = Global.getOriginalColumnName(mapping, oPIdKey);

		String idkey = sIdKey == null ? "treeid" : sIdKey.split("_0_")[1];
		String indexkey = sIndexKey == null ? "index_id" : sIndexKey.split("_0_")[1];
		String pidkey = sPIdKey == null ? "parent_id" : sPIdKey.split("_0_")[1];

		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();

		for (ColumnDefinition column : columns) {
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		boolean isTreepinfo = "treepinfo".equalsIgnoreCase(tableName) || "s_treewbs".equalsIgnoreCase(tableName); // treepinfo
																													// 特殊处理

		String inParameters = ruleMap.get("inParameters");

		for (Map<String, Object> map : models) {
			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);

			String prefixTableName = tableName + "_0_";
			// 获取页面传递参数
			Map<String, String> paramsMap = ParamsSqlHelper.getBindParamMap(paramsObj.toJSONString(), inParameters);
			Set<String> set = paramsMap.keySet();
			for (String key : set) {
				String colkey = key.substring(key.indexOf(".") + 1);
				if (columnMap.containsKey(colkey.toLowerCase().replace(prefixTableName.toLowerCase(), ""))) {
					ColumnDefinition column = columnMap.get(colkey);
					ColumnModel cm = new ColumnModel();
					cm.setColumnName(column.getColumnName());
					cm.setJavaType(column.getJavaType());
					cm.setValue(paramsMap.get(key));
					tableModel.addColumn(cm);
				}
			}

			Set<String> keys = map.keySet();

			boolean updateFlag = false;
			boolean hasId = map.get("startId") != null && StringUtils.isNotEmpty(map.get("startId").toString());
			/*
			 * boolean hasTreepinfoId = map.get("treepinfo_0_index_id") != null
			 * &&
			 * StringUtils.isNotEmpty(map.get("treepinfo_0_index_id").toString()
			 * ) && "0".equals(map.get("treepinfo_0_index_id")); if
			 * ("s_treewbs".equalsIgnoreCase(tableName)) { hasTreepinfoId =
			 * map.get("s_treewbs_0_index_id") != null &&
			 * StringUtils.isNotEmpty(map.get("s_treewbs_0_index_id").toString()
			 * ) && "0".equals(map.get("s_treewbs_0_index_id")); }
			 */
			/*
			 * if ((isTreepinfo && hasTreepinfoId) || hasId) {// id不为null时
			 * updateFlag = true; for (String key : keys) { if (isTreepinfo) {
			 * if ("treepinfo_0_index_id".equalsIgnoreCase(key) ||
			 * "s_treewbs_0_index_id".equalsIgnoreCase(key)) { ColumnModel idCol
			 * = new ColumnModel();
			 * idCol.setColumnName(idColumn.getColumnName());
			 * idCol.setJavaType(idColumn.getJavaType());
			 * idCol.setValue(map.get(key)); tableModel.setIdColumn(idCol);
			 * continue; } } else { if ("startId".equalsIgnoreCase(key)) {
			 * ColumnModel idCol = new ColumnModel();
			 * idCol.setColumnName(idColumn.getColumnName());
			 * idCol.setJavaType(idColumn.getJavaType());
			 * idCol.setValue(map.get(key)); tableModel.setIdColumn(idCol);
			 * continue; } else if ("__row_number__".equalsIgnoreCase(key) ||
			 * "id".equalsIgnoreCase(key)) { continue; } } String theKey =
			 * key.toLowerCase().indexOf("_0_") != -1 ?
			 * key.toLowerCase().split("_0_")[1] : key.toLowerCase(); if
			 * (columnMap.containsKey(theKey)) { ColumnDefinition column =
			 * columnMap.get(theKey); ColumnModel col = new ColumnModel();
			 * col.setColumnName(column.getColumnName());
			 * col.setJavaType(column.getJavaType()); if
			 * (StringUtils.equalsIgnoreCase(column.getJavaType(), "Integer")) {
			 * col.setValue(ParamUtils.getIntValue(map, key)); } else if
			 * (StringUtils.equalsIgnoreCase(column.getJavaType(), "Long")) {
			 * col.setValue(ParamUtils.getLongValue(map, key)); } else if
			 * (StringUtils.equalsIgnoreCase(column.getJavaType(), "Double")) {
			 * col.setValue(ParamUtils.getDoubleValue(map, key)); } else if
			 * (StringUtils.equalsIgnoreCase(column.getJavaType(), "Date")) {
			 * col.setValue(ParamUtils.getDate(map, key)); } else {
			 * col.setValue(map.get(key)); } tableModel.addColumn(col); } } }
			 * else {
			 */
			int parentid = -1;
			String key = null, fkey = null, nextId = null, ookey = null;
			for (String skey : keys) {
				fkey = Global.getOriginalColumnName(mapping, skey).toLowerCase();
				key = fkey.indexOf("_0_") != -1 ? fkey.split("_0_")[1] : fkey;
				if (key.equalsIgnoreCase("treepinfo_0_index_id") || key.equalsIgnoreCase("s_treewbs_0_index_id") || key.equalsIgnoreCase(idColumn.getColumnName())
						|| key.equalsIgnoreCase("startId")) {
					ookey = skey;
					if (map.get(skey) != null && (StringUtils.isNotEmpty(map.get(skey).toString()) || "0".equals(map.get(skey)) || hasId)) {
						updateFlag = true;
						if (isTreepinfo) {
							if ("treepinfo_0_index_id".equalsIgnoreCase(key) || "s_treewbs_0_index_id".equalsIgnoreCase(key)) {
								ColumnModel idCol = new ColumnModel();
								idCol.setColumnName(idColumn.getColumnName());
								idCol.setJavaType(idColumn.getJavaType());
								idCol.setValue(map.get(skey));
								tableModel.setIdColumn(idCol);
								continue;
							}
						} else {
							if ((!"_id_".equalsIgnoreCase(idColumn.getColumnName()) && "startId".equalsIgnoreCase(key))
									|| ("_id_".equalsIgnoreCase(idColumn.getColumnName()) && "_id_".equalsIgnoreCase(key))) {
								ColumnModel idCol = new ColumnModel();
								idCol.setColumnName(idColumn.getColumnName());
								idCol.setJavaType(idColumn.getJavaType());
								idCol.setValue(map.get(skey));
								tableModel.setIdColumn(idCol);
								continue;
							} else if ("__row_number__".equalsIgnoreCase(key) || "id".equalsIgnoreCase(key)) {
								continue;
							}
						}
					} else {
						// 创建ID
						nextId = mutilDatabaseBean.getNextId(tableName, idColumn.getColumnName(), loginContext.getActorId(), databaseId);
						ColumnModel idCol = new ColumnModel();
						idCol.setColumnName(idColumn.getColumnName());
						idCol.setJavaType(idColumn.getJavaType());
						idCol.setValue(nextId);
						tableModel.addColumn(idCol);
					}
				} else if (columnMap.containsKey(key.toLowerCase()) && !idkey.equalsIgnoreCase(key)
						&& !indexkey.equalsIgnoreCase(
								key) /* && !pidkey.equalsIgnoreCase(key) */
						&& !"listno".equalsIgnoreCase(key) && !"id".equalsIgnoreCase(key)) {
					ColumnDefinition column = columnMap.get(key.toLowerCase());
					ColumnModel col = new ColumnModel();
					col.setColumnName(column.getColumnName());
					col.setJavaType(column.getJavaType());
					if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Integer")) {
						if (pidkey.equalsIgnoreCase(key)) {
							parentid = ParamUtils.getIntValue(map, skey) == null ? -1 : ParamUtils.getIntValue(map, skey);
							col.setValue(parentid);
						} else {
							col.setValue(ParamUtils.getIntValue(map, skey));
						}
					} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Long")) {
						col.setValue(ParamUtils.getLongValue(map, skey));
					} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Double")) {
						if (pidkey.equalsIgnoreCase(key)) {
							parentid = ParamUtils.getDoubleValue(map, skey) == null ? -1 : ParamUtils.getDoubleValue(map, skey).intValue();
							col.setValue(parentid);
						} else {
							col.setValue(ParamUtils.getDoubleValue(map, skey));
						}
					} else if (StringUtils.equalsIgnoreCase(column.getJavaType(), "Date")) {
						col.setValue(ParamUtils.getDate(map, skey));
					} else {
						col.setValue(map.get(skey));
					}
					tableModel.addColumn(col);
				}
			}

			if (!updateFlag) {
				if (nextId != null) {
					models.get(models.indexOf(map)).put(ookey, nextId);
					models.get(models.indexOf(map)).put(idColumn.getColumnName(), nextId);
				}
				// 创建parent_id
				/*
				 * ColumnModel parentidCol = new ColumnModel();
				 * models.get(models.indexOf(map)).put(pidkey, -1);
				 * parentidCol.setColumnName(pidkey);
				 * parentidCol.setJavaType(columnMap.get(pidkey).getJavaType());
				 * parentidCol.setValue(-1); tableModel.addColumn(parentidCol);
				 */
				// 创建index_id
				StringBuffer sb = new StringBuffer();
				sb.append(" select max( ");
				sb.append(indexkey);
				sb.append(" ) as \"maxIndex\" from ");
				sb.append(tableName);
				List<Map<String, Object>> maps = mutilDatabaseBean.getDataListBySql(sb.toString(), databaseId);
				int index_id = 1;

				if (maps != null && maps.size() > 0 && maps.get(0).get("maxIndex") != null) {
					Object indexObj = maps.get(0).get("maxIndex");
					if (indexObj instanceof BigDecimal) {
						index_id = ((BigDecimal) indexObj).intValue() + 1;
					} else {
						index_id = ((int) indexObj) + 1;
					}
				}
				ColumnModel indexidCol = new ColumnModel();
				models.get(models.indexOf(map)).put(tableName + "_0_" + indexkey, index_id);
				models.get(models.indexOf(map)).put(oIndexKey, index_id);
				indexidCol.setColumnName(indexkey);
				indexidCol.setJavaType(columnMap.get(indexkey).getJavaType());
				indexidCol.setValue(index_id);
				tableModel.addColumn(indexidCol);
				// 排序号
				ColumnModel listnoCol = new ColumnModel();
				models.get(models.indexOf(map)).put(tableName + "_0_" + "listno", index_id);
				models.get(models.indexOf(map)).put(Global.getOriginalColumnName(mapping, tableName + "_0_" + "listno"), index_id);
				listnoCol.setColumnName(columnMap.get("listno").getColumnName());
				listnoCol.setJavaType(columnMap.get("listno").getJavaType());
				listnoCol.setValue(index_id);
				tableModel.addColumn(listnoCol);
				// 创建treeid
				String treeid = index_id + "|";
				if (parentid != -1) {
					StringBuffer sbb = new StringBuffer();
					sbb.append(" select max( ");
					sbb.append(idkey);
					sbb.append(" ) as \"treeid\" from ");
					sbb.append(tableName);
					sbb.append(" where ");
					sbb.append(indexkey);
					sbb.append(" = ");
					sbb.append(parentid);
					List<Map<String, Object>> maps2 = mutilDatabaseBean.getDataListBySql(sbb.toString(), databaseId);
					if (maps2 != null && maps.size() > 0 && maps2.get(0).get("treeid") != null) {
						treeid = maps2.get(0).get("treeid").toString() + index_id + "|";
					}
					models.get(models.indexOf(map)).put(tableName + "_0_" + pidkey, parentid);
					models.get(models.indexOf(map)).put(pidkey, parentid);
				}
				ColumnModel treeidCol = new ColumnModel();
				models.get(models.indexOf(map)).put(tableName + "_0_" + idkey, treeid);
				models.get(models.indexOf(map)).put(idkey, treeid);
				treeidCol.setColumnName(idkey);
				treeidCol.setJavaType(columnMap.get(idkey).getJavaType());
				treeidCol.setValue(treeid);
				tableModel.addColumn(treeidCol);
				// TODO 关系键 (要考虑如何设置判断)
				if (columnMap.containsKey("topid") && StringUtils.isNotEmpty(parentId)) {
					ColumnModel topIdCol = new ColumnModel();
					topIdCol.setColumnName("topid");
					topIdCol.setJavaType("String");
					topIdCol.setValue(parentId);
					tableModel.addColumn(topIdCol);
				}
			}
			// }
			try {
				// 执行创建 或者 更新操作
				if (updateFlag) {
					// 执行更新操作
					mutilDatabaseBean.updateTableData(tableModel, databaseId);
				} else {
					// 执行创建操作
					mutilDatabaseBean.insertTableData(tableModel, databaseId);
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("异常 ：" + e.getMessage());
				return null;
			}
		}

		JSONObject result = new JSONObject();
		result.put("data", models);
		result.put("total", models.size());
		return result.toJSONString().getBytes("UTF-8");
	}

	@SuppressWarnings("unchecked")
	@RequestMapping("/deleteData")
	public @ResponseBody byte[] deleteData(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id
		List<?> models = dataSourceRequest.getModels();
		if (models == null || models.size() <= 0) {
			return null;
		}
		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		String tables = datasourceSetJSONObject.getString("tables");
		JSONArray tablesJSONArray = null;
		if (!isNullOrEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || (tablesJSONArray != null && tablesJSONArray.size() > 1)) { // 如果是多表的
			// 不支持操作
			return null;
		}
		String tableName = tablesJSONArray.getString(0);

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		String params = dataSourceRequest.getParams();
		if (params != null && !"".equals(params)) {
			JSONObject jo = JSON.parseObject(params);
			try {
				Long dbid = jo.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		Map<String, Object> map = null;
		String delChildFlag = ruleMap.get("deleteChildren");
		String id = null;
		String idKey = "id";
		for (Object object : models) {
			map = (Map<String, Object>) object;
			id = map.get("startId").toString(); // startId 用来存放treelist的主键id值
			if (map.containsKey("_id_")) {
				idKey = "_id_";
				id = map.get("_id_").toString();
			}
			if (StringUtils.isNotEmpty(id)) {// id不为null时
				if (delChildFlag != null && "false".equalsIgnoreCase(delChildFlag)) {
					mutilDatabaseBean.deleteTableByWhereCause(tableName,
							" and " + idKey + " = '" + id + "' and (select count(*) from " + tableName + " where parent_id = " + map.get(tableName + "_0_index_id") + ") = 0",
							databaseId);
				} else {
					// mutilDatabaseBean.deleteTableByWhereCause(tableName, "
					// and id = '" + id + "'", databaseId);
					mutilDatabaseBean.deleteTableByWhereCause(tableName, " and treeid like '" + map.get(tableName + "_0_treeid") + "%' ", databaseId);
				}
			}
		}
		return this.getGridData(request, dataSourceRequest);
	}

	@RequestMapping("/gridData")
	public @ResponseBody byte[] getGridData(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();// 规则id

		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}
		String enumValue = ruleMap.get("enumValue");
		if (StringUtils.isNotEmpty(enumValue)) {// 字典里的枚举数据
			return jsonArray(enumValue);
		} 
		String tableSource = ruleMap.get("tableSource");
		JSONArray tableSourceJSONArray = JSON.parseArray(tableSource);
		// 若选择了物理表来源
		if (tableSourceJSONArray != null && tableSourceJSONArray.size() > 0) {
			JSONObject ret = getGridDataFromTable(ruleMap, tableSourceJSONArray.getJSONObject(0), dataSourceRequest);
			return ret.toJSONString().getBytes("UTF-8");
		}

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		String params = dataSourceRequest.getParams();
		JSONObject paramsObj = new JSONObject();
		String indexId = null;
		if(StringUtils.isNotEmpty(params)){
			paramsObj = JSONObject.parseObject(params);
			InjectUtils.escapeSql(paramsObj);
			indexId = paramsObj.getString("indexId");
		}
		if (!paramsObj.isEmpty()) {
			try {
				Long dbid = paramsObj.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")), mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}

		//查询出数据集中的表对应是哪个数据库，查询出数据库类型
		Database database1 = databaseService.getDatabaseById(databaseId);
		String systemName1 = Environment.DEFAULT_SYSTEM_NAME;
		if (database1 != null) {
			systemName1 = database1.getName();
		}
		Connection conn1 = DBConnectionFactory.getConnection(systemName1);
		boolean isOracle = StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType(conn1));
		boolean isDm = StringUtils.equals(DBUtils.DM_DBMS, DBConnectionFactory.getDatabaseType(conn1));
		conn1.close();
		//查询出数据集中的表对应是哪个数据库，查询出数据库类型
		String repStr = "_0_";
		String repToStr = ".";
		Map<String, String> keyMap = getKeyMap(ruleMap);
		String sIdKey = Global.getOriginalColumnName(mapping, keyMap.get("idKey"));
		String sIndexKey = Global.getOriginalColumnName(mapping, keyMap.get("indexKey"));
		String sPIdKey = Global.getOriginalColumnName(mapping, keyMap.get("pIdKey"));

		String idkey = sIdKey == null ? "treeid" : sIdKey.replaceFirst(repStr, repToStr);
		String indexkey = sIndexKey == null ? "index_id" : sIndexKey.replaceFirst(repStr, repToStr);
		String pidkey = sPIdKey == null ? "parent_id" : sPIdKey.replaceFirst(repStr, repToStr);

		String idKs = keyMap.get("idKey") == null ? "base.treeid" : ("base." + (isDm ? "\"" : "") + keyMap.get("idKey") + (isDm ? "\"" : ""));
		String indexKs = keyMap.get("indexKey") == null ? "base.index_id" : ("base." + (isDm ? "\"" : "") + keyMap.get("indexKey") + (isDm ? "\"" : ""));
		String parentKs = keyMap.get("pIdKey") == null ? "base.parent_id" : ("base." + (isDm ? "\"" : "") + keyMap.get("pIdKey") + (isDm ? "\"" : ""));
		Query baseQuery = null;

		LoginContext loginContext = RequestUtils.getLoginContext(request);

		// 构建联动语句 start
		String linkageSql = "";
		// 获取 输入表达式定义
		String inParameters = ruleMap.get("inParameters");
		// 获取页面传递参数
		linkageSql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters, datasourceSetJSONObject.getString("datasetId"));

		// 构建联动语句 end

		// 根据输入表达式定义 和 传递参数 获取到 parentidkey 的值 父节点值
		String parentIdKeyValue = ParamsSqlHelper.getTreeListPidkeyValue(pidkey, paramsObj.toJSONString(), inParameters);
		// 排序
		List<SortDescriptor> sorts = dataSourceRequest.getSort();
		String sortSql = "";
		if (sorts != null && sorts.size() > 0) {
			for (SortDescriptor sortDescriptor : sorts) {
				sortSql += sortDescriptor.getField() + " " + sortDescriptor.getDir() + ((sorts.indexOf(sortDescriptor) == sorts.size() - 1) ? " " : ", ");
			}
		}
		// 排序 end

		// where 查询 用于 过滤查询
		FilterDescriptor filterDescriptor = dataSourceRequest.getFilter();
		StringBuilder filterSql = new StringBuilder("");
		if (filterDescriptor != null && filterDescriptor.getFilters() != null && filterDescriptor.getFilters().size() > 0) {
			SqlHelper helper = new SqlHelper();
			helper.getWhereSql(filterSql, filterDescriptor, 1, false);
		}
		// where end

		DataSetBuilder builder = new DataSetBuilder();
		Map<String, Object> parameter = new HashMap<String, Object>();
		Map<String, Object> parameter2 = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		parameter2.put("HttpServletRequest", request);
		if (!paramsObj.isEmpty()) {
			parameter.putAll(paramsObj);
			parameter2.putAll(paramsObj);
		}

		JSONObject result = new JSONObject();// 返回信息
		JSONArray rowsJSON = new JSONArray();
		// 是否异步加载
		boolean isSync = Boolean.parseBoolean(ruleMap.get("isSync")==null?"true":ruleMap.get("isSync"));
		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();

		String qsql = linkageSql + (!isNullOrEmpty(linkageSql) && !isNullOrEmpty(filterSql.toString()) ? " and " : "") + filterSql.toString();
		String sql = "";

		/*
		 * 是否要调用实时汇总功能 start
		 * 
		 */
		boolean isFilter = false;
		if (!paramsObj.isEmpty()) {
			JSONObject newJo = JSON.parseObject(paramsObj.toJSONString());
			newJo.remove("databaseId");
			isFilter = newJo.keySet().size() > 0;
		}
		String tmpTable = null;
		String targetTable = null;
		String collectStr = ruleMap.get("collect");
		Boolean isCollect = StringUtils.isNotEmpty(collectStr) && (StringUtils.isNotEmpty(qsql) || isFilter);
		String id = dataSourceRequest.getId();
		id = InjectUtils.escapeSql(id);
		if (isCollect) {
			JSONArray collectAry = JSON.parseArray(collectStr);
			JSONObject collectObj = collectAry.getJSONObject(0);
			tmpTable = "_TREE_TABLE_AGGR" + collectObj.getString("id") + "_" + Math.abs(loginContext.getActorId().hashCode());
			targetTable = "TMP" + tmpTable;
			// 展开下级节点不需要重新汇总数据，只有查询的时候进行汇总统计
			if (StringUtils.isEmpty(id)) {
				// 查询出过滤条件的所有数据
				parameter.put("buildWhereAndHaving", true); // 如果此条件为不动态拼接where
															// sql
				baseQuery = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), null, null, parameter);
				String baseSql = baseQuery.toSql();
				if (!isOracle) {
					if (baseSql.indexOf("SELECT DISTINCT") == 0) {
						baseSql = baseSql.replaceFirst("SELECT DISTINCT ", "SELECT DISTINCT TOP 100000000000 ");
					} else {
						baseSql = baseSql.replaceFirst("SELECT", "SELECT TOP 100000000000 ");
					}
				}
				if (baseSql.indexOf("ORDER BY") != -1) {
					baseSql = baseSql.substring(0, baseSql.indexOf("ORDER BY"));
				}
				qsql += ("".equals(qsql.trim()) ? " " : " and ") + idkey + " like " + idKs + "+'%'";
				Query query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), "".equals(qsql.trim()) ? null : qsql, null, parameter2);
				String querySql = query.toSql();
				String orderby = "";
				if (querySql.indexOf("ORDER BY") != -1) {
					orderby = querySql.substring(querySql.indexOf("ORDER BY"));
				}
				String wrapSql = " select * from (" + baseSql + " " + orderby + ") base where exists ( ";
				StringBuilder syncSql = new StringBuilder(""); // 执行sql
				if (querySql.indexOf("ORDER BY") != -1) {
					wrapSql += querySql.substring(0, querySql.indexOf("ORDER BY"));
				} else {
					wrapSql += querySql;
				}
				syncSql.append(wrapSql + ")");
				sql = syncSql.toString();
				dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);

				// 检查并创建目标表
				Database database = databaseService.getDatabaseById(databaseId);
				String systemName = database != null ? database.getName() : Environment.DEFAULT_SYSTEM_NAME;
				List<ColumnDefinition> columns = DBUtils.getColumnDefinitions(systemName, collectObj.getString("targetTableName"));
				TableDefinition tableDefinition = new TableDefinition();
				tableDefinition.setTableName(targetTable);
				tableDefinition.setColumns(columns);
				if (!DBUtils.tableExists(systemName, targetTable)) {
					DBUtils.createTable(systemName, tableDefinition);
				}
				// 把原始数据写入到新的汇总表中
				BulkInsertExtBean bulkInsertBean = new BulkInsertExtBean();
				Connection conn = null;
				try {
					conn = DBConnectionFactory.getConnection(systemName);
					PreparedStatement ps = conn.prepareStatement("delete from " + targetTable);
					ps.executeUpdate();
					bulkInsertBean.setPrefix(collectObj.getString("targetTableName") + "_0_");
					bulkInsertBean.bulkInsert(conn, tableDefinition, dataMaps);
					conn.commit();
				} catch (Exception e) {
					e.printStackTrace();
					logger.error(e.getMessage());
				} finally {
					if (conn != null) {
						conn.close();
					}
				}

				// 执行汇总统计操作
				TreeTableAggregateBean treeTableAggregateBean = new TreeTableAggregateBean();
				treeTableAggregateBean.cloneAndExecute(databaseId, collectObj.getString("targetTableName"), loginContext.getActorId(), parameter);

			}
		}
		/*
		 * 是否要调用实时汇总功能 end
		 * 
		 */
		Query query = null;
		if(StringUtils.isNotEmpty(indexId)){
			query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), indexkey + "=" + indexId, "", parameter);
			sql = query.toSql();
		}else if(isSync){
			if (keyMap.get("idKey") == null) { // 如果没有设置 idkey 的值 走普通查询
				String defaultQuerySql = "(" + pidkey + " is null or  convert(varchar(50)," + pidkey + ") = '-1' or convert(varchar(50)," + pidkey + ") = '0' ) ";
				if (isOracle) {
					defaultQuerySql = "(" + pidkey + " is null or  to_char(" + pidkey + ") = '-1' or to_char(" + pidkey + ") = '0' ) ";
				}
				if(!isSync){
					defaultQuerySql = "";
				}
				if (StringUtils.isNotEmpty(id)) { // 如果当前id不为空
					// 则表明是异步展开节点
					defaultQuerySql = "convert(varchar(50)," + pidkey + ")='" + id + "'";
					if (isOracle  || isDm) {
						defaultQuerySql = "to_char(" + pidkey + ")='" + id + "'";
					}
				}else{
					if(ruleMap.containsKey("defaultParentId") && StringUtils.isNotEmpty(ruleMap.get("defaultParentId"))){
						String pid = ruleMap.get("defaultParentId");
						pid = InjectUtils.escapeSql(pid);
						defaultQuerySql = "convert(varchar(50),"+pidkey + ")='" + pid+"'" ;
						if(isOracle){
							defaultQuerySql = "to_char("+pidkey + ")='" + pid+"'" ;
						}
					}
				}
				query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), "".equals(qsql.trim()) ? defaultQuerySql : qsql, null, parameter);
				sql = query.toSql();
			} else {
				if (isCollect) {
					String queryTable = "TMP2" + tmpTable;
					parameter.put("tableName", queryTable);
					parameter2.put("tableName", queryTable);
				}
				parameter.put("buildWhereAndHaving", true); // 如果此条件为不动态拼接where sql
				baseQuery = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), null, null, parameter);
				String baseSql = baseQuery.toSql();
				// String orderby = baseSql.substring(baseSql.indexOf("ORDER
				// BY")).replaceAll("\\w+\\.", "base\\.") ;
				if (!isOracle) {
					if (baseSql.indexOf("SELECT DISTINCT") == 0) {
						baseSql = baseSql.replaceFirst("SELECT DISTINCT ", "SELECT DISTINCT TOP 100000000000 ");
					} else {
						baseSql = baseSql.replaceFirst("SELECT", "SELECT TOP 100000000000 ");
					}
				}
				if (baseSql.indexOf("ORDER BY") != -1) {
					baseSql = baseSql.substring(0, baseSql.indexOf("ORDER BY"));
				}
				// if (isSync) {// 使用异步获取数据
				// treepinfo.id like a.id+'%' and a.id =
				// convert(varchar,a.index_id)+'|') 有查询
				// treepinfo.id like a.id+'%' and a.parent_id = 30457
				//
				if (isOracle || isDm) {
					qsql += ("".equals(qsql.trim()) ? " " : " and ") + idkey + " like " + idKs + "||'%'";
				} else {
					qsql += ("".equals(qsql.trim()) ? " " : " and ") + idkey + " like " + idKs + "+'%'";
				}
				query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), "".equals(qsql.trim()) ? null : qsql, null, parameter2);
				String querySql = query.toSql();
				String orderby = "";
				if (querySql.indexOf("ORDER BY") != -1) {
					orderby = querySql.substring(querySql.indexOf("ORDER BY"));
				}
				String wrapSql = " select * from (" + baseSql + " " + orderby + ") base where exists ( ";
				StringBuilder syncSql = new StringBuilder(""); // 执行sql
				if (querySql.indexOf("ORDER BY") != -1) {
					wrapSql += querySql.substring(0, querySql.indexOf("ORDER BY"));
				} else {
					wrapSql += querySql;
				}
				if (StringUtils.isNotEmpty(id)) { // 如果当前id不为空
					// 则表明是异步展开节点
					wrapSql += ") and " + parentKs + "=" +id;
				} else {
					if (isOracle || isDm) {
						wrapSql += " ) and " + idKs + " = to_char(" + indexKs + ")||'|'";
					} else {
						wrapSql += " ) and " + idKs + " = convert(varchar," + indexKs + ")+'|'";
					}
				}
				syncSql.append(wrapSql/* +" "+orderby */);
				/*
				 * } else { // 一次加载所有数据 String noSyncSql = " "; if
				 * (!isNullOrEmpty(parentIdKeyValue)) { // 不为空时 noSyncSql +=
				 * indexkey + " = " + parentIdKeyValue; Query query =
				 * builder.buildQuery(datasourceSetJSONObject.getString("datasetId")
				 * , noSyncSql, null, parameter); String sql = query.toSql();
				 * List<Map<String, Object>> parentDataMaps =
				 * mutilDatabaseBean.getDataListBySql(sql, databaseId); if
				 * (parentDataMaps != null && parentDataMaps.size() > 0) { String
				 * treelike = parentDataMaps.get(0).get(idkey.replace(repToStr,
				 * repStr)).toString(); syncSql.append(" " + idkey + " like '" +
				 * treelike + "_%' "); } } }
				 */
				
				// 查找异步的下级节点
				/*
				 * Query query =
				 * builder.buildQuery(datasourceSetJSONObject.getString("datasetId")
				 * , syncSql.toString(), sortSql null, parameter);
				 */
				sql = /* query.toSql() */syncSql.toString();
			}
		}else{
			// 执行查询 start
			String wheresql = "";
			if (pidkey != null && id !=null && !"-1".equalsIgnoreCase(id) ) {
				// 构建sql start
				query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), indexkey + "=" + id, "", parameter);
				sql = query.toSql();
				List<Map<String, Object>> dm = mutilDatabaseBean.getDataListBySql(sql + qsql, databaseId);
				// 构建sql end
				if (dm != null && dm.size() > 0) {
					Map<String, Object> model = dm.get(0);
					if (isOracle || isDm) {
						wheresql = idkey + " like '" + idKs + "||'%'";
					} else {
						wheresql = idkey + " like '" + idKs + "_%'";
					}
				}
			}
			// 构建sql start
			query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), wheresql + qsql, "", parameter);
			sql = query.toSql();
			// 构建sql end
			
			// 客服端分页
			dataMaps = mutilDatabaseBean.getDataListBySql(sql/* " where parent_id = -1 " */, databaseId);
		}

		dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
		int total = dataMaps.size();
		if (total > 0) {
			// 过滤
			int count = 1;
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
				josnObj.put("startIndex", count);
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String[] kk = Global.getOriginalColumnName(mapping, key.toLowerCase()).split("_0_");
					String value = map.get(key) != null ? map.get(key).toString() : "";
					if (pidkey.replace(repToStr, repStr).equals(key) && ("-1".equalsIgnoreCase(value) || parentIdKeyValue.equalsIgnoreCase(value))) {
						josnObj.put(key.toLowerCase(), null);
						if (!key.equals(key.toLowerCase())) {
							josnObj.put(key, null);
						}
					} else if (kk.length > 1 && "id".equalsIgnoreCase(kk[1])) {
						josnObj.put("startId", value); // treelist隐藏的id
						josnObj.put(key.toLowerCase(), value);
						if (!key.equals(key.toLowerCase())) {
							josnObj.put(key, value);
						}
					} else {
						if (!key.equals(key.toLowerCase())) {
							josnObj.put(key, value);
						}
						josnObj.put(key.toLowerCase(), value);
					}
				}
				if (isSync) {
					josnObj.put("hasChildren", true); // 异步使用时需要
				}

				rowsJSON.add(josnObj);
				count++;
			}
			result.put("total", total);
			result.put("data", rowsJSON);
			return result.toJSONString().getBytes("UTF-8");
		}
		result.put("total", 0);
		result.put("data", rowsJSON);
		return result.toJSONString().getBytes("UTF-8");
	}
	private byte[] jsonArray(String category) {
		try {
			JSONArray array = new JSONArray();
			JSONObject resultJSONObject = new JSONObject();
			SysTree tree = sysTreeService.getSysTreeByCode(category);
			if (tree != null) {
				List<Dictory> list = dictoryService
						.getAvailableDictoryList(tree.getId());
				int index = 0;
				for (Dictory item : list) {
					index++;
					JSONObject json = item.toJsonObject();
					json.put("index", index);
					json.put("listno", index);
					array.add(json);
				}
				
			}
			resultJSONObject.put("data", array);
			return resultJSONObject.toString().getBytes("UTF-8");
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
	}
	/**
	 * 根据物理表直接获取treegrid数据
	 * 
	 * @param ruleMap
	 *            规则信息
	 * @param tableSource
	 *            物理表信息
	 * @param dataSourceRequest
	 *            页面参数
	 * @return
	 */
	private JSONObject getGridDataFromTable(Map<String, String> ruleMap, JSONObject tableSource, DataSourceRequest dataSourceRequest) {
		JSONObject result = new JSONObject();
		// 表格数据，包括表信息即字段信息
		JSONObject tableData = tableSource.getJSONObject("table");
		if (tableData == null || tableData.isEmpty()) {
			JSONArray rowsJSON = new JSONArray();
			result.put("data", rowsJSON);
			result.put("total", 0);
			logger.error("无物理表信息，无法查询");
			return result;
		}
		// 表信息
		JSONObject tableInformation = tableData.getJSONObject("table");
		// 物理表真实表名
		String tableName = tableInformation.getString("tableName");
		// 标段ID
		String systemName = tableData.getString("systemName");
		if (systemName == null) {
			systemName = Environment.DEFAULT_SYSTEM_NAME;
		}

		String id = dataSourceRequest.getId(); // 父ID
		id = InjectUtils.escapeSql(id);
		// 执行查询
		String sql = "select * from " + tableName;
		if (id != null && !id.isEmpty()) {
			sql += " where parent_id = " +id;
		} else {
			sql += " where treeid = convert(varchar,index_id)+'|'";
		}

		List<Map<String, Object>> dataMaps = mutilDatabaseBean.basesql(systemName, sql, null, null);
		int total = dataMaps.size();
		JSONArray rowsJSON = new JSONArray();
		if (total > 0) {
			// 过滤
			int count = 1;
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
				josnObj.put("startIndex", count);
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String value = map.get(key) != null ? map.get(key).toString() : "";
					josnObj.put(key.toLowerCase(), value);
				}
				// if (isSync) {
				josnObj.put("hasChildren", true); // 异步使用时需要
				// }

				rowsJSON.add(josnObj);
				count++;
			}
			result.put("total", total);
			result.put("data", rowsJSON);
			return result;
		}
		result.put("total", 0);
		result.put("data", rowsJSON);
		return result;
	}

	private JSONArray getTablesName(JSONObject datasourceSetJSONObject) {
		String tables = datasourceSetJSONObject.getString("tables");
		JSONArray tablesJSONArray = null;
		if (!isNullOrEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || (tablesJSONArray != null && tablesJSONArray.size() > 1)) { // 如果是多表的
			return null;
		}
		return tablesJSONArray;
	}

	private Map<String, String> getKeyMap(Map<String, String> ruleMap) {
		Map<String, String> map = new HashMap<>();
		String treelistColumns = ruleMap.get("columns");
		JSONArray treelistJSONArray = new JSONArray();
		if (StringUtils.isNotEmpty(treelistColumns)) {
			treelistJSONArray = JSON.parseArray(treelistColumns);
			for (Object object : treelistJSONArray) {
				JSONObject treelistJSONObject = (JSONObject) object;
				map.put(treelistJSONObject.getString("columnType"), treelistJSONObject.getString("ColumnName"));
			}
		}
		return map;
	}

	private static String trim(String str, String syn) {
		if (!isNullOrEmpty(str) && !isNullOrEmpty(syn)) {
			if (str.startsWith(syn))
				str = str.trim().substring(syn.length());
			if (str.endsWith(syn))
				str = str.trim().substring(0, str.length() - syn.length());
		}
		return str;
	}

	private static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	/**
	 * grid表单元格数据源获取
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/widgetSource")
	public @ResponseBody byte[] getWidgetSource(HttpServletRequest request) throws Exception {
		// 获取参数
		String tableName = RequestUtils.getString(request, "tableName", null);// 规则id
		String text = RequestUtils.getString(request, "text", null);// 父节点ID
		String value = RequestUtils.getString(request, "value", null);// 父节点ID

		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		String sql = " select distinct " + text + (value == null ? "" : ("," + value)) + " from " + tableName;
		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();

		// 客服端分页
		dataMaps = mutilDatabaseBean.getDataListBySql(sql, null);

		return JSON.toJSONString(dataMaps).getBytes("UTF-8");
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	private List<String> getTableNames(Map<String, String> ruleMap) {
		List<String> tableNames = new ArrayList<String>();
		// 获取表明
		String tables = ruleMap.get("table");
		JSONArray tablesArray = (JSONArray) JSON.parse(tables);
		JSONObject tableJson = null;
		for (Object object : tablesArray) {
			tableJson = (JSONObject) object;
			tableNames.add(tableJson.getString("TableName"));
		}
		return tableNames;
	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@javax.annotation.Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}

	@javax.annotation.Resource
	public void setSysTreeService(SysTreeService sysTreeService) {
		this.sysTreeService = sysTreeService;
	}
	@javax.annotation.Resource
	public void setDictoryService(DictoryService dictoryService) {
		this.dictoryService = dictoryService;
	}
	
	
	/**
	 * 排序
	 * 
	 * @param request
	 * @param dataSourceRequest
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/ward")
	public @ResponseBody byte[] ward(HttpServletRequest request, @RequestBody DataSourceRequest dataSourceRequest) throws Exception {
		// 获取参数
		String rid = dataSourceRequest.getRid();
		String paramType = dataSourceRequest.getParamType();
		List<?> models = dataSourceRequest.getModels();
		if (models == null || models.size() <= 0) {
			return null;
		}
		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则

		String dataSourceSet = ruleMap.get("dataSourceSet");
		// 装换成json数组
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		// 将json数组转化成json对象
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		String tables = datasourceSetJSONObject.getString("tables");
		JSONArray tablesJSONArray = null;
		if (!isNullOrEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || (tablesJSONArray != null && tablesJSONArray.size() > 1)) { // 如果是多表的
			// 不支持操作
			return null;
		}

		String tableName = tablesJSONArray.getString(0);

		String prefixTableName = tableName + "_0_";

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");

		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")), mapping = null;
		if (metadata != null) {
			mapping = metadata.getJSONObject("mapping");
		}

		String params = dataSourceRequest.getParams();
		// 将参数params转化成json对象
		if (params != null && !"".equals(params)) {
			JSONObject jo = JSON.parseObject(params);
			try {
				Long dbid = jo.getLong("databaseId");
				if (dbid != null) {
					databaseId = dbid;
				}
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("databaseId转换失败" + e.getMessage());
			}
		}

		String repStr = "_0_";
		String repToStr = ".";
		Map<String, String> keyMap = getKeyMap(ruleMap);
		String sIdKey = Global.getOriginalColumnName(mapping, keyMap.get("idKey"));
		String sIndexKey = Global.getOriginalColumnName(mapping, keyMap.get("indexKey"));
		String sPIdKey = Global.getOriginalColumnName(mapping, keyMap.get("pIdKey"));

		String idkey = sIdKey == null ? "treeid" : sIdKey.replace(repStr, repToStr);
		String indexkey = sIndexKey == null ? "index_id" : sIndexKey.replace(repStr, repToStr);
		String pidkey = sPIdKey == null ? "parent_id" : sPIdKey.replace(repStr, repToStr);

		Map<String, Object> map = null;
		String id = null;
		int listno = 0;
		String parentId = null;
		String parentIdStr = pidkey;
		try {
			for (Object object : models) {
				map = (Map<String, Object>) object;

				Set<String> keys = map.keySet();
				String key;
				String idKey = null;
				String listNoKey = null;
				String parentIdKey = sPIdKey;
				for (String skey : keys) {
					key = Global.getOriginalColumnName(mapping, skey);
					if (key.equalsIgnoreCase(prefixTableName + "id")) {
						idKey = skey;
					} else if (key.equalsIgnoreCase(prefixTableName + "listno")) {
						listNoKey = skey;
					} /*
						 * else if(key.equalsIgnoreCase(sPIdKey)){ parentIdKey =
						 * skey; }
						 */
				}
				if (idKey != null && listNoKey != null && parentIdKey != null) {
					id = map.get(idKey).toString();
					id = InjectUtils.escapeSql(id);
					listno = map.get(listNoKey) != null && !"".equals(map.get(listNoKey)) ? Integer.parseInt(map.get(listNoKey).toString()) : 1;
					parentId = map.get(parentIdKey) != null && !"".equals(map.get(parentIdKey)) ? map.get(parentIdKey).toString() : null;
					if (StringUtils.isNotEmpty(id)) {// id不为null时
						boolean isAsc = "true".equalsIgnoreCase(paramType);
						int nextlistno = isAsc ? (listno + 1) : (listno - 1);
						String sql = "update " + tableName + " set listno=" + listno + " where listno = (select " + (isAsc ? "min" : "max") + "(listno) from " + tableName
								+ " where listno " + (isAsc ? ">" : "<") + " (select listno from " + tableName + "  where id = '" + id + "')) and " + parentIdStr + "=" + parentId;
						mutilDatabaseBean.execSql(sql, tableName, databaseId);
						sql = "update " + tableName + " set listno=" + nextlistno + " where id='" + id + "'";
						mutilDatabaseBean.execSql(sql, tableName, databaseId);
					}
				}

			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("data delete" + e.getMessage());
			return null;
		}
		return this.getGridData(request, dataSourceRequest);
	}

	public static void main(String[] args) {
		// System.out.println("order by treep_info1.id
		// assc".replaceAll("(\\w+)\\.", "base\\.$1_0_"));
		String baseSql = "SELECT * FROM KKK ";
	}
}
