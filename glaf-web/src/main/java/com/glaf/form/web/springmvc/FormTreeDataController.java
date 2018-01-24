package com.glaf.form.web.springmvc;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.metamodel.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.dubbo.container.Main;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.security.LoginContext;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.core.util.ResponseUtils;
import com.glaf.datamgr.jdbc.DataSetBuilder;
import com.glaf.datamgr.service.DataSetService;
import com.glaf.form.core.service.FormRulePropertyService;
import com.glaf.form.core.util.MutilDatabaseBean;
import com.glaf.form.rule.Global;
import com.glaf.form.rule.ParamsSqlHelper;
import com.glaf.form.rule.RequestSendRedirect;
import com.glaf.form.rule.util.InjectUtils;

/**
 * 通用树结构数据调用
 * 
 * @author Administrator
 *
 */
@Controller("/form/treeData")
@RequestMapping("/form/treeData")
public class FormTreeDataController {
	protected static final Log logger = LogFactory.getLog(FormTreeDataController.class);

	protected MutilDatabaseBean mutilDatabaseBean;

	protected FormRulePropertyService formRulePropertyService;
	
	@Autowired
	protected DataSetService dataSetService;

	public FormTreeDataController() {

	}

	/**
	 * 拖拽
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/drop")
	public @ResponseBody byte[] drop(HttpServletRequest request) {
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		// 获取参数
		String rid = RequestUtils.getString(request, "rid", null);// 规则id
		String treeNodeIds = RequestUtils.getString(request, "treeNodeIds", null);// 拖拽的节点ID
		String targetNodeId = RequestUtils.getString(request, "targetNodeId", null);// 拖拽的目标节点ID
		// 拖拽位置 "inner"：成为子节点，"prev"：成为同级前一个节点，"next"：成为同级后一个节点
		String moveType = RequestUtils.getString(request, "moveType", null);
		Boolean isCopy = RequestUtils.getBoolean(request, "isCopy");// 拖拽类型
																	// true：复制；false：移动

		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则
		// 获取表名 start
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
		if (StringUtils.isNotEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || tablesJSONArray.size() != 1) { // 如果是多表的
																		// 不支持操作
			return null;
		}
		String tableName = tablesJSONArray.getString(0);
		// 获取表名 end
		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");

		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(tableName);

		ColumnDefinition idColumn = null;

		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
				break;
			}
		}

		if (idColumn == null) {
			return ResponseUtils.responseJsonResult(false, "未提供主键，不能进行业务操作。");
		}

		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();

		for (ColumnDefinition column : columns) {
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}
		
		boolean isOracle = StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType());

		try {
			TableModel model = new TableModel();
			model.setTableName(tableName); // 表名

			// 查询拖拽节点
			TableModel model1 = new TableModel();
			model1.setTableName(tableName); // 表名
			ColumnModel idCol = new ColumnModel();
			idCol.setColumnName(idColumn.getColumnName());
			idCol.setJavaType(idColumn.getJavaType());
			idCol.setValue(treeNodeIds);
			model1.setIdColumn(idCol);
			Map<String, Object> tableMap = mutilDatabaseBean.getTableDataByPrimaryKey(model1, databaseId);

			Map<String, Object> tableMap2 = null;
			if (targetNodeId != null) {
				// 查询拖拽节点
				TableModel model2 = new TableModel();
				model2.setTableName(tableName);
				ColumnModel idCol2 = new ColumnModel();
				idCol2.setColumnName(idColumn.getColumnName());
				idCol2.setJavaType(idColumn.getJavaType());
				idCol2.setValue(targetNodeId);
				model2.setIdColumn(idCol2);
				tableMap2 = mutilDatabaseBean.getTableDataByPrimaryKey(model2, databaseId);
			}
			ColumnModel column = new ColumnModel();
			ColumnDefinition col = new ColumnDefinition();
			String tagerTreeid = "";
			String thisTreeid = tableMap.get(isOracle&& getRuleMapValueByKey(ruleMap, "idKey")!=null ?getRuleMapValueByKey(ruleMap, "idKey").toUpperCase() : getRuleMapValueByKey(ruleMap, "idKey")) + "";
			if ("inner".equalsIgnoreCase(moveType)) { // 成为子节点,需要改变pidkey
														// treeid
				// 改变treeid
				column = new ColumnModel();
				col = columnMap.get(getRuleMapValueByKey(ruleMap, "idKey"));
				column.setColumnName(col.getColumnName());
				column.setJavaType(col.getJavaType());
				if (tableMap2 != null) {// tableMap2 为null 说明移动到跟节点
					tagerTreeid = tableMap2.get(isOracle&& getRuleMapValueByKey(ruleMap, "idKey")!=null ?getRuleMapValueByKey(ruleMap, "idKey").toUpperCase() : getRuleMapValueByKey(ruleMap, "idKey") )
							+ "" + tableMap.get(isOracle&& getRuleMapValueByKey(ruleMap, "indexKey")!=null ?getRuleMapValueByKey(ruleMap, "indexKey").toUpperCase() : getRuleMapValueByKey(ruleMap, "indexKey")) + "|";
					column.setValue(tagerTreeid);
				} else {
					tagerTreeid = tableMap.get(isOracle&&getRuleMapValueByKey(ruleMap, "indexKey")!=null?getRuleMapValueByKey(ruleMap, "indexKey").toUpperCase():getRuleMapValueByKey(ruleMap, "indexKey")) + "|";
					column.setValue(tagerTreeid);
				}
				model.addColumn(column);

				// 改变父ID
				column = new ColumnModel();
				col = columnMap.get(getRuleMapValueByKey(ruleMap, "pIdKey"));
				column.setColumnName(col.getColumnName());
				column.setJavaType(col.getJavaType());
				column.setValue(tableMap2 == null ? "-1" : tableMap2.get(isOracle&&getRuleMapValueByKey(ruleMap, "indexKey")!=null?getRuleMapValueByKey(ruleMap, "indexKey").toUpperCase():getRuleMapValueByKey(ruleMap, "indexKey")));
				model.addColumn(column);

				// 排序字段
				column = new ColumnModel();
				col = columnMap.get("listno");
				column.setColumnName(col.getColumnName());
				column.setJavaType(col.getJavaType());
				column.setValue(1);
				model.addColumn(column);

			} else if ("prev".equalsIgnoreCase(moveType) || "next".equalsIgnoreCase(moveType)) { // 前一个节点
																									// or
																									// 下一个节点

				// 改变treeid
				column = new ColumnModel();
				col = columnMap.get(getRuleMapValueByKey(ruleMap, "idKey"));
				column.setColumnName(col.getColumnName());
				column.setJavaType(col.getJavaType());
				if (tableMap2 != null) {// tableMap2 为null 说明移动到跟节点
					String treeId = tableMap2.get(isOracle&&getRuleMapValueByKey(ruleMap, "idKey")!=null?getRuleMapValueByKey(ruleMap, "idKey").toUpperCase():getRuleMapValueByKey(ruleMap, "idKey")).toString();
					String tree = treeId.substring(0, treeId.substring(0, treeId.length() - 1).lastIndexOf("|") + 1);
					tagerTreeid = tree + "" + tableMap.get(isOracle&&getRuleMapValueByKey(ruleMap, "indexKey")!=null?getRuleMapValueByKey(ruleMap, "indexKey").toUpperCase():getRuleMapValueByKey(ruleMap, "indexKey")) + "|";
					column.setValue(tagerTreeid);
				} else {
					tagerTreeid = "";
					column.setValue(tableMap.get(isOracle&&getRuleMapValueByKey(ruleMap, "indexKey")!=null?getRuleMapValueByKey(ruleMap, "indexKey").toUpperCase():getRuleMapValueByKey(ruleMap, "indexKey")) + "|");
				}
				model.addColumn(column);

				// 改变父ID
				column = new ColumnModel();
				col = columnMap.get(getRuleMapValueByKey(ruleMap, "pIdKey"));
				column.setColumnName(col.getColumnName());
				column.setJavaType(col.getJavaType());
				column.setValue(tableMap2 == null ? "-1" : tableMap2.get(isOracle&&getRuleMapValueByKey(ruleMap, "pIdKey")!=null?getRuleMapValueByKey(ruleMap, "pIdKey").toUpperCase():getRuleMapValueByKey(ruleMap, "pIdKey")));
				model.addColumn(column);

				// 排序字段
				column = new ColumnModel();
				col = columnMap.get("listno");
				column.setColumnName(col.getColumnName());
				column.setJavaType(col.getJavaType());
				if ("prev".equalsIgnoreCase(moveType)) { // 前一个节点
					column.setValue(tableMap2 == null ? "1" : tableMap2.get(isOracle?"LISTNO":"listno"));
				} else { // 后一个节点
					column.setValue(tableMap2 == null ? "1" : ((int) tableMap2.get(isOracle?"LISTNO":"listno") + 1));
				}
				model.addColumn(column);

				/*
				 * 更新排序号
				 */
				TableModel sortModel = new TableModel();
				sortModel.setTableName(tableName);

				ColumnModel sortColumn = new ColumnModel();
				col = columnMap.get("listno");
				sortColumn.setColumnName(col.getColumnName());
				sortColumn.setJavaType("Prototype");
				sortColumn.setValue(col.getColumnName() + "+1");
				sortModel.addColumn(sortColumn);

				sortColumn = new ColumnModel();
				col = columnMap.get(getRuleMapValueByKey(ruleMap, "idKey"));
				sortColumn.setColumnName(col.getColumnName());
				sortColumn.setJavaType(col.getJavaType());
				sortColumn.setOperator("like");
				String treeId = tableMap2.get(isOracle&&getRuleMapValueByKey(ruleMap, "idKey")!=null?getRuleMapValueByKey(ruleMap, "idKey").toUpperCase():getRuleMapValueByKey(ruleMap, "idKey")).toString();
				sortColumn.setValue(treeId.substring(0, treeId.substring(0, treeId.length()).lastIndexOf("|") + 1) + "%");
				sortModel.addWhereColumn(sortColumn);

				sortColumn = new ColumnModel();
				col = columnMap.get("listno");
				sortColumn.setColumnName(col.getColumnName());
				sortColumn.setJavaType(col.getJavaType());
				if ("prev".equalsIgnoreCase(moveType)) { // 前一个节点
					sortColumn.setOperator(">=");
					sortColumn.setValue(tableMap2 == null ? "1" : tableMap2.get(isOracle?"LISTNO":"listno"));
				} else { // 后一个节点
					sortColumn.setOperator(">");
					sortColumn.setValue(tableMap2 == null ? "1" : ((int) tableMap2.get(isOracle?"LISTNO":"listno") + 1));
				}
				sortModel.addWhereColumn(sortColumn);

				mutilDatabaseBean.updateTableDataByWhere(sortModel, databaseId);
			}
			if (isCopy) {// 复制节点
				StringBuffer sb = new StringBuffer();
				sb.append(" select max( ");
				sb.append(getRuleMapValueByKey(ruleMap, "indexKey"));
				sb.append(" ) as \"maxIndex\" from ");
				sb.append(tableName);
				List<Map<String, Object>> maps = mutilDatabaseBean.getDataListBySql(sb.toString(), databaseId);
				int index_id = 0;
				Object indexObj  = maps.get(0).get("maxIndex");
				if(indexObj instanceof BigDecimal){
					index_id = ((BigDecimal)indexObj).intValue() + 1 ;
				}else{
					index_id = ((int) indexObj) + 1;
				}

				column = new ColumnModel();
				col = columnMap.get(getRuleMapValueByKey(ruleMap, "indexKey"));
				column.setColumnName(col.getColumnName());
				column.setJavaType(col.getJavaType());
				column.setValue(index_id);
				model.addColumn(column);

				column = new ColumnModel();
				column.setColumnName(idColumn.getColumnName());
				column.setJavaType(idColumn.getJavaType());
				column.setValue(mutilDatabaseBean.getNextId(tableName, idColumn.getColumnName(), loginContext.getActorId(), databaseId));
				model.addColumn(column);

				mutilDatabaseBean.insertTableData(model, databaseId);
			} else {// 移动节点
				idCol = new ColumnModel();
				idCol.setColumnName(idColumn.getColumnName());
				idCol.setJavaType(idColumn.getJavaType());
				idCol.setValue(treeNodeIds);
				model.setIdColumn(idCol);
				mutilDatabaseBean.updateTableData(model, databaseId);
				// 批量更改 子节点的treeid
				// update table set treeid=#{tagerTreeid,jdbcType=VARCHAR}+
				// substring(treeid,len(#{thisTreeid,jdbcType=VARCHAR})+1,len(treeid)-len(#{thisTreeid,jdbcType=VARCHAR}))
				// where
				// treeid like #{thisTreeid,jdbcType=VARCHAR}+'%'
				model = new TableModel();
				model.setTableName(tableName);

				col = columnMap.get(getRuleMapValueByKey(ruleMap, "idKey"));
				column = new ColumnModel();
				column.setColumnName(col.getColumnName());
				column.setJavaType("Prototype");
				if(isOracle){
					column.setValue("'" +InjectUtils.escapeSql(tagerTreeid) + "'||substr(treeid,length('" + InjectUtils.escapeSql(thisTreeid) + "')+1,length(treeid)-length('" + InjectUtils.escapeSql(thisTreeid) + "'))");
				}else{
					column.setValue("'" + InjectUtils.escapeSql(tagerTreeid) + "'+substring(treeid,len('" + InjectUtils.escapeSql(thisTreeid) + "')+1,len(treeid)-len('" + InjectUtils.escapeSql(thisTreeid) + "'))");
				}
				model.addColumn(column);

				column = new ColumnModel();
				column.setColumnName(col.getColumnName());
				column.setOperator(" like ");
				column.setValue(thisTreeid + "%");
				model.addWhereColumn(column);
				mutilDatabaseBean.updateTableDataByWhere(model, databaseId);
			}

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return ResponseUtils.responseJsonResult(false);
		}
	}

	private Map<String, String> getRuleMap(String rid) {
		return this.formRulePropertyService.getRuleMap(rid);
	}

	private String getRuleMapValueByKey(Map<String, String> ruleMap, String key) {
		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")), 
				mapping = null; 
		if (metadata != null) { 
			mapping = metadata.getJSONObject("mapping"); 
		}
		String columnsStr = ruleMap.get("columns");
		JSONArray columnsArray = JSON.parseArray(columnsStr);
		JSONObject jo = null;
		String keyValue = null;
		for (Object object : columnsArray) {
			jo = (JSONObject) object;
			if (key.equalsIgnoreCase(jo.getString("columnType"))) {
				keyValue = Global.getOriginalColumnName(mapping, jo.getString("ColumnName")).split("_0_")[1];
			}
		}
		return keyValue;
	}

	/**
	 * 获取树节点数据
	 * 
	 * @param request
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/treeData")
	public @ResponseBody byte[] getTreeData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 获取参数
		String rid = RequestUtils.getString(request, "rid", null);// 规则id
		String pid = RequestUtils.getString(request, "pid", null);// 父节点ID
		rid = InjectUtils.escapeSql(rid);
		pid = InjectUtils.escapeSql(pid);
		// 获取规则MAP
		Map<String, String> ruleMap = getRuleMap(rid);
		if (ruleMap == null) {
			return null;
		}
		
		String params = RequestUtils.getString(request, "params", null);
		JSONObject paramsObj = new JSONObject();
		if(StringUtils.isNotEmpty(params)){
			paramsObj = JSONObject.parseObject(params);
			InjectUtils.escapeSql(paramsObj);
		}
		
		String __ret = RequestSendRedirect.redirect(ruleMap, paramsObj);
		if (StringUtils.isNotBlank(__ret)) {
			return __ret.getBytes("UTF-8");
		}

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray!=null && !datasourceSetJSONArray.isEmpty()) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return null;
		}
		
		JSONObject metadata = dataSetService.getDataSetMetadata(datasourceSetJSONObject.getString("datasetId")), 
				mapping = null; 
		if (metadata != null) { 
			mapping = metadata.getJSONObject("mapping"); 
		}
		
		//获取定义的数据字段 
		String prefix  = "_0_" ;
		String sPIdKey = null;
		String sIdKey = null;
		String sIndexKey = null;
		String pIdKey = null;
		String idKey = null;
		String indexKey = null;
		String columnsStr = ruleMap.get("columns");
		JSONArray columnsArray = JSON.parseArray(columnsStr);
		for (Object object : columnsArray) {
			JSONObject jo = (JSONObject) object;
			if ("pIdKey".equalsIgnoreCase(jo.getString("columnType"))) {
				sPIdKey = jo.getString("ColumnName");
				pIdKey = Global.getOriginalColumnName(mapping, sPIdKey).replaceFirst(prefix, ".");
			} else if ("idKey".equalsIgnoreCase(jo.getString("columnType"))) {
				sIdKey = jo.getString("ColumnName");
				idKey =  Global.getOriginalColumnName(mapping, sIdKey).replaceFirst(prefix, ".");
			} else if ("indexKey".equalsIgnoreCase(jo.getString("columnType"))) {
				sIndexKey = jo.getString("ColumnName");
				indexKey = Global.getOriginalColumnName(mapping, sIndexKey).replaceFirst(prefix, ".");
			}
		}
		// 构建sql start
		DataSetBuilder builder = new DataSetBuilder();
		
		Map<String, Object> parameter = new HashMap<String, Object>();
		Map<String, Object> parameter2 = new HashMap<String, Object>();
		parameter.put("HttpServletRequest", request);
		parameter2.put("HttpServletRequest", request);
		
//		String params = RequestUtils.getString(request, "params", null);
//		JSONObject paramsObj = new JSONObject();
//		if(StringUtils.isNotEmpty(params)){
//			paramsObj = JSONObject.parseObject(params);
//			InjectUtils.escapeSql(paramsObj);
//		}
		if (!paramsObj.isEmpty()) {
			parameter.putAll(paramsObj);
			parameter2.putAll(paramsObj);
		}
		
		Query query = null;
		String sql = null;

		String psql = "";
		// 获取 输入表达式定义
		String inParameters = ruleMap.get("inParameters");
		// 获取页面传递参数
		psql = ParamsSqlHelper.getParamSql(paramsObj.toJSONString(), inParameters, datasourceSetJSONObject.getString("datasetId"));
	
		Map<String, String> keyMap = getKeyMap(ruleMap);
		
		// 构建sql end

		List<Map<String, Object>> dataMaps = new ArrayList<Map<String, Object>>();

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
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
		
		
		
		boolean isOracle = StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType());
		
		//是否异步
		String isSync = ruleMap.get("isSync");
		if("true".equalsIgnoreCase(isSync)){
			sql = "" ;
			if(keyMap.get("idKey") == null){ //如果没有设置 idkey 的值 走普通查询
				String defaultQuerySql = "(" + pIdKey +" is null or  convert(varchar(50)," + pIdKey + ") = '-1' or convert(varchar(50),"+pIdKey+") = '0' ) " ;
				if(isOracle){
					defaultQuerySql = "(" + pIdKey +" is null or  to_char(" + pIdKey + ") = '-1' or to_char("+pIdKey+") = '0' ) " ;
				}
				if (StringUtils.isNotEmpty(pid)) { // 如果当前id不为空  则表明是异步展开节点
					defaultQuerySql = "convert(varchar(50),"+pIdKey + ")='" + pid+"'" ;
					if(isOracle){
						defaultQuerySql = "to_char("+pIdKey + ")='" + pid+"'" ;
					}
				}else{
					if(ruleMap.containsKey("defaultParentId") && StringUtils.isNotEmpty(ruleMap.get("defaultParentId"))){
						pid = ruleMap.get("defaultParentId");
						defaultQuerySql = "convert(varchar(50),"+pIdKey + ")='" + pid+"'" ;
						if(isOracle){
							defaultQuerySql = "to_char("+pIdKey + ")='" + pid+"'" ;
						}
					}
				}
				query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), "".equals(psql.trim())?defaultQuerySql:psql, null,parameter);
				sql = query.toSql() ;
			}else{
				String idKs = keyMap.get("idKey") == null ? "base.treeid" : ("base." + keyMap.get("idKey")) ;
				String indexKs = keyMap.get("indexKey") == null ? "base.index_id" : ("base." +keyMap.get("indexKey")) ;
				String parentKs = keyMap.get("pIdKey") == null ? "base.parent_id" : ("base." +keyMap.get("pIdKey")) ;
				parameter.put("buildWhereAndHaving", true); //如果此条件为不动态拼接where sql
				Query baseQuery = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), null, null,parameter);
				String baseSql = baseQuery.toSql();
				
				if(!isOracle){
					if(baseSql.indexOf("SELECT DISTINCT")==0){
						baseSql = baseSql.replaceFirst("SELECT DISTINCT ", "SELECT DISTINCT TOP 100000000000 ") ;
					}else{
						baseSql = baseSql.replaceFirst("SELECT", "SELECT TOP 100000000000 ") ;
					}
				}
				
				if(baseSql.indexOf("ORDER BY")!=-1){
					baseSql = baseSql.substring(0,baseSql.indexOf("ORDER BY"));
				}
				if(isOracle){
					psql += ("".equals(psql.trim())? " " : " and ") + idKey + " like " + idKs + "||'%'" ;
				}else{
					psql += ("".equals(psql.trim())? " " : " and ") + idKey + " like " + idKs + "+'%'" ;
				}
				query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), "".equals(psql.trim())?null:psql, null,parameter2);
				String querySql = query.toSql();
				String orderby = "" ;
				if(querySql.indexOf("ORDER BY")!=-1){
					orderby = querySql.substring(querySql.indexOf("ORDER BY")) ;
				}
				String wrapSql = " select * from (" + baseSql+" "+orderby + ") base where exists ( " ;
				StringBuilder syncSql = new StringBuilder("");  //执行sql
				if(querySql.indexOf("ORDER BY")!=-1){
					wrapSql += querySql.substring(0,querySql.indexOf("ORDER BY")) ;
				}else{
					wrapSql += querySql ;
				}
				if (StringUtils.isNotEmpty(pid)) { // 如果当前id不为空  则表明是异步展开节点
					wrapSql +=  ") and " + parentKs + "=" + pid ;
				}else{
					if(isOracle){
						wrapSql += " ) and " + idKs + " = to_char(" + indexKs + ")||'|'" ;
					}else{
						wrapSql += " ) and " + idKs + " = convert(varchar," + indexKs + ")+'|'" ;
					}
				}
				syncSql.append(wrapSql);
				sql = syncSql.toString();
			}
			
			dataMaps = mutilDatabaseBean.getDataListBySql(sql, databaseId);
			/*int total = dataMaps.size();
			if (total > 0) {
				return JSON.toJSONString(dataMaps).getBytes("UTF-8");
			}*/
		}else{
			// 执行查询 start
			String wheresql = "";
			if (pIdKey != null && !"-1".equalsIgnoreCase(pid) ) {
				// 构建sql start
				query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), indexKey + "=" + pid, "", parameter);
				sql = query.toSql();
				List<Map<String, Object>> dm = mutilDatabaseBean.getDataListBySql(sql + psql, databaseId);
				// 构建sql end
				if (dm != null && dm.size() > 0) {
					Map<String, Object> model = dm.get(0);
					wheresql = idKey + " like '" + model.get(idKey.replaceFirst(".", prefix)) + "_%'";
				}
			}
			// 构建sql start
			query = builder.buildQuery(datasourceSetJSONObject.getString("datasetId"), wheresql + psql, "", parameter);
			sql = query.toSql();
			// 构建sql end
			
			// 客服端分页
			dataMaps = mutilDatabaseBean.getDataListBySql(sql/* " where parent_id = -1 " */, databaseId);
			
			// 执行查询 end
		}
		if(dataMaps.size()>0){
			JSONArray rowsJSON = new JSONArray();
			for (Map<String, Object> map : dataMaps) {
				Set<String> keys = map.keySet();
				Iterator<String> iterator = keys.iterator();
				JSONObject josnObj = new JSONObject();
				while (iterator.hasNext()) {
					String key = (String) iterator.next();
					String originalName = Global.getOriginalColumnName(mapping, key.toLowerCase());
					if(originalName!=null){
						String[] globalKey = originalName.split("_0_");
						if(globalKey.length < 2){
							//格式不是XXXX_0_XXX的字段跳过
							continue;
						}
						if("id".equalsIgnoreCase(globalKey[1])){
							josnObj.put(originalName, map.get(key) != null ? map.get(key).toString() : "");
							josnObj.put((key.split("_0_")[0]+"_0_id").toLowerCase(), map.get(key) != null ? map.get(key).toString() : "");
						}
						if(!key.equals(key.toLowerCase())){
							josnObj.put(key, map.get(key) != null ? map.get(key).toString() : "");
						}
					}
					josnObj.put(key.toLowerCase(), map.get(key) != null ? map.get(key).toString() : "");
				}
				rowsJSON.add(josnObj);
			}
			return rowsJSON.toJSONString().getBytes("UTF-8");
		}else{
			return JSON.toJSONString(dataMaps).getBytes("UTF-8");
		}
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
	
	/**
	 * 删除节点
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/remove")
	public @ResponseBody byte[] remove(HttpServletRequest request) {
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		// 获取参数
		String rid = RequestUtils.getString(request, "rid", null);// 规则id
		String treeNodeIds = RequestUtils.getString(request, "treeNodeIds", null);// 规则id
		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则
		// 获取表名 start
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
		if (StringUtils.isNotEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || tablesJSONArray.size() != 1) { // 如果是多表的
																		// 不支持操作
			return null;
		}
		String tableName = tablesJSONArray.getString(0);
		// 获取表名 end
		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");

		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(tableName);

		ColumnDefinition idColumn = null;

		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
				break;
			}
		}


		try {
			StringTokenizer token = new StringTokenizer(treeNodeIds, ",");
			while (token.hasMoreTokens()) {
				String x = token.nextToken();
				TableModel model = new TableModel();
				model.setTableName(tableName);
				ColumnModel col = new ColumnModel();
				col.setColumnName(idColumn.getColumnName());
				col.setJavaType(idColumn.getJavaType());
				col.setValue(x);
				model.addColumn(col);
				model.setIdColumn(col);
				mutilDatabaseBean.deleteTableData(model, databaseId);
			}
			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return ResponseUtils.responseJsonResult(false);
		}

	}

	/**
	 * 重命名
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/rename")
	public @ResponseBody byte[] rename(HttpServletRequest request) {
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		// 获取参数
		String rid = RequestUtils.getString(request, "rid", null);// 规则id
		String treeNodeId = RequestUtils.getString(request, "treeNodeId", null);// 主键
		String newName = RequestUtils.getString(request, "newName", null);// 新名称

		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则
		// 获取表名 start
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
		if (StringUtils.isNotEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || tablesJSONArray.size() != 1) { // 如果是多表的
																		// 不支持操作
			return null;
		}
		String tableName = tablesJSONArray.getString(0);
		// 获取表名 end

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(tableName);

		ColumnDefinition idColumn = null;

		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
				break;
			}
		}

		if (idColumn == null) {
			return ResponseUtils.responseJsonResult(false, "未提供主键，不能进行业务操作。");
		}

		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();

		for (ColumnDefinition column : columns) {
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}


		try {
			TableModel model = new TableModel();
			model.setTableName(tableName); // 表名

			ColumnModel idCol = new ColumnModel();
			idCol.setColumnName(idColumn.getColumnName());
			idCol.setJavaType(idColumn.getJavaType());
			idCol.setValue(treeNodeId);
			model.setIdColumn(idCol);// 主键

			ColumnModel column = new ColumnModel();

			ColumnDefinition cd = columnMap.get(getRuleMapValueByKey(ruleMap, "nameKey"));
			column.setColumnName(cd.getColumnName());
			column.setJavaType(cd.getJavaType());
			column.setValue(newName);
			model.addColumn(column);
			mutilDatabaseBean.updateTableData(model, databaseId);

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			logger.error("" + e.getMessage());
			return ResponseUtils.responseJsonResult(false);
		}
	}

	/**
	 * 工具栏 新增 编辑
	 * 
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping("/save")
	public @ResponseBody byte[] save(HttpServletRequest request, HttpServletResponse response) throws IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		LoginContext loginContext = RequestUtils.getLoginContext(request);
		// 获取参数
		String rid = RequestUtils.getString(request, "rid", null);// 规则id
		String id = RequestUtils.getString(request, "id", null);// id
		String treeId = RequestUtils.getString(request, "idKey", "");// treeid
		String pid = RequestUtils.getString(request, "pid", "-1");// 父节点key
		String name = RequestUtils.getString(request, "name", null);// 名称
		String icon = RequestUtils.getString(request, "icon", null);// 显示图片
		String iconOpen = RequestUtils.getString(request, "iconOpen", null);// 显示图片
		String iconClose = RequestUtils.getString(request, "iconClose", null);// 显示图片

		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则
		// 获取表名 start
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
		if (StringUtils.isNotEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || tablesJSONArray.size() != 1) { // 如果是多表的
																		// 不支持操作
			return null;
		}
		String tableName = tablesJSONArray.getString(0);
		// 获取表名 end

		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(databaseId, tableName);

		ColumnDefinition idColumn = null;

		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
				break;
			}
		}

		if (idColumn == null) {
			return ResponseUtils.responseJsonResult(false, "未提供主键，不能进行业务操作。");
		}

		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();

		for (ColumnDefinition column : columns) {
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}

		try {
			TableModel model = new TableModel();
			model.setTableName(tableName); // 表名

			ColumnModel idCol = new ColumnModel();
			idCol.setColumnName(idColumn.getColumnName());
			idCol.setJavaType(idColumn.getJavaType());
			idCol.setValue(id == null ? mutilDatabaseBean.getNextId(tableName, "id", loginContext.getActorId(), databaseId) : id);

			ColumnModel column = new ColumnModel();
			ColumnDefinition cd = columnMap.get(getRuleMapValueByKey(ruleMap, "nameKey").toLowerCase());
			column.setColumnName(cd.getColumnName());
			column.setJavaType(cd.getJavaType());
			column.setValue(name);
			model.addColumn(column);

			if (StringUtils.isNotEmpty(icon)) {
				column = new ColumnModel();
				cd = columnMap.get(getRuleMapValueByKey(ruleMap, "icon").toLowerCase());
				column.setColumnName(cd.getColumnName());
				column.setJavaType(cd.getJavaType());
				column.setValue(icon);
				model.addColumn(column);
			}
			if (StringUtils.isNotEmpty(iconOpen)) {
				column = new ColumnModel();
				cd = columnMap.get(getRuleMapValueByKey(ruleMap, "iconOpen").toLowerCase());
				column.setColumnName(cd.getColumnName());
				column.setJavaType(cd.getJavaType());
				column.setValue(iconOpen);
				model.addColumn(column);
			}
			if (StringUtils.isNotEmpty(iconClose)) {
				column = new ColumnModel();
				cd = columnMap.get(getRuleMapValueByKey(ruleMap, "iconClose").toLowerCase());
				column.setColumnName(cd.getColumnName());
				column.setJavaType(cd.getJavaType());
				column.setValue(iconClose);
				model.addColumn(column);
			}

			if (StringUtils.isEmpty(id)) {// 新增节点
				StringBuffer sb = new StringBuffer();
				sb.append(" select max( ");
				sb.append(getRuleMapValueByKey(ruleMap, "indexKey"));
				sb.append(" ) as \"maxIndex\" from ");
				sb.append(tableName);
				List<Map<String, Object>> maps = mutilDatabaseBean.getDataListBySql(sb.toString(), databaseId);
				int index_id = 1;
				if (maps != null && maps.size() > 0 && maps.get(0).get("maxIndex") != null) {
					Object indexObj  = maps.get(0).get("maxIndex");
					if(indexObj instanceof BigDecimal){
						index_id = ((BigDecimal)indexObj).intValue() + 1 ;
					}else{
						index_id = ((int) indexObj) + 1;
					}
				}

				column = new ColumnModel();
				cd = columnMap.get(getRuleMapValueByKey(ruleMap, "indexKey").toLowerCase());
				column.setColumnName(cd.getColumnName());
				column.setJavaType(cd.getJavaType());
				column.setValue(index_id);
				model.addColumn(column);

				// 排序号
				column = new ColumnModel();
				cd = columnMap.get("listno");
				column.setColumnName(cd.getColumnName());
				column.setJavaType(cd.getJavaType());
				column.setValue(index_id);
				model.addColumn(column);

				column = new ColumnModel();
				cd = columnMap.get(getRuleMapValueByKey(ruleMap, "idKey").toLowerCase());
				column.setColumnName(cd.getColumnName());
				column.setJavaType(cd.getJavaType());
				column.setValue(treeId + index_id + "|");
				model.addColumn(column);

				column = new ColumnModel();
				cd = columnMap.get(getRuleMapValueByKey(ruleMap, "pIdKey").toLowerCase());
				column.setColumnName(cd.getColumnName());
				column.setJavaType(cd.getJavaType());
				column.setValue(pid);
				model.addColumn(column);

				model.addColumn(idCol);// 主键

				mutilDatabaseBean.insertTableData(model, databaseId);
			} else {// 更新节点
				model.setIdColumn(idCol);// 主键
				mutilDatabaseBean.updateTableData(model, databaseId);
			}

			return ResponseUtils.responseJsonResult(true);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("" + e.getMessage());
			return ResponseUtils.responseJsonResult(false);
		}
	}

	/**
	 * 工具条增加或者编辑节点
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/ztreeEdit")
	public ModelAndView ztreeEdit(HttpServletRequest request, ModelMap modelMap) {
		// LoginContext loginContext = RequestUtils.getLoginContext(request);
		// 获取参数
		String rid = RequestUtils.getString(request, "rid", null);// 规则id
		String id = RequestUtils.getString(request, "id", null);// 主键
		String pidkey = RequestUtils.getString(request, "pid", null);// 父节点标识
		String treeId = RequestUtils.getString(request, "treeId", null);// 父节点标识
		Boolean epac = RequestUtils.getBoolean(request, "epac");// 是否自动关闭窗口

		Map<String, String> ruleMap = getRuleMap(rid);// 获取所有规则
		JSONObject data = new JSONObject();
		// 获取表名 start
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
		if (StringUtils.isNotEmpty(tables)) {
			tablesJSONArray = JSON.parseArray(tables);
		}
		if (tablesJSONArray == null || tablesJSONArray.size() != 1) { // 如果是多表的
																		// 不支持操作
			return null;
		}
		String tableName = tablesJSONArray.getString(0);
		// 获取表名 end
		// 数据源ID
		Long databaseId = datasourceSetJSONObject.getLong("databaseId");
		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(databaseId,tableName);
		ColumnDefinition idColumn = null;
		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
				break;
			}
		}

		if (idColumn == null) {
			throw new RuntimeException("未提供主键，不能进行业务操作。");
		}

		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
		for (ColumnDefinition column : columns) {
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}
		
		boolean isOracle = org.apache.commons.lang3.StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType());

		if (StringUtils.isNotEmpty(id)) {
			TableModel model = new TableModel();
			model.setTableName(tableName); // 表名
			ColumnModel idCol = new ColumnModel();
			idCol.setColumnName(idColumn.getColumnName());
			idCol.setJavaType(idColumn.getJavaType());
			idCol.setValue(id);
			model.setIdColumn(idCol);// 主键

			Map<String, Object> tableMap = mutilDatabaseBean.getTableDataByPrimaryKey(model, databaseId);
			data.put("id", id);
			data.put("pIdKey", tableMap.get(isOracle&&getRuleMapValueByKey(ruleMap, "pIdKey")!=null?getRuleMapValueByKey(ruleMap, "pIdKey").toUpperCase():getRuleMapValueByKey(ruleMap, "pIdKey")));
			data.put("idKey", tableMap.get(isOracle?getRuleMapValueByKey(ruleMap, "idKey").toUpperCase():getRuleMapValueByKey(ruleMap, "idKey")));
			data.put("indexKey", tableMap.get(isOracle?getRuleMapValueByKey(ruleMap, "indexKey").toUpperCase():getRuleMapValueByKey(ruleMap, "indexKey")));
			if(tableMap.get(isOracle?getRuleMapValueByKey(ruleMap, "nameKey").toUpperCase():getRuleMapValueByKey(ruleMap, "nameKey")) != null){
				data.put("nameKey", tableMap.get(isOracle?getRuleMapValueByKey(ruleMap, "nameKey").toUpperCase():getRuleMapValueByKey(ruleMap, "nameKey")));
			}
			else{
				data.put("nameKey", tableMap.get(isOracle?getRuleMapValueByKey(ruleMap, "nameKey").toUpperCase():getRuleMapValueByKey(ruleMap, "nameKey").toLowerCase()));		
			}
			data.put("nameKey", tableMap.get(isOracle?getRuleMapValueByKey(ruleMap, "nameKey").toUpperCase():getRuleMapValueByKey(ruleMap, "nameKey").toLowerCase()));
			data.put("icon", tableMap.get(isOracle&&getRuleMapValueByKey(ruleMap, "icon")!=null?getRuleMapValueByKey(ruleMap, "icon").toUpperCase():getRuleMapValueByKey(ruleMap, "icon")));
			data.put("iconOpen", tableMap.get(isOracle&&getRuleMapValueByKey(ruleMap, "iconOpen")!=null?getRuleMapValueByKey(ruleMap, "iconOpen").toUpperCase():getRuleMapValueByKey(ruleMap, "iconOpen")));
			data.put("iconClose", tableMap.get(isOracle&&getRuleMapValueByKey(ruleMap, "iconClose")!=null?getRuleMapValueByKey(ruleMap, "iconClose").toUpperCase():getRuleMapValueByKey(ruleMap, "iconClose")));		
			} else {
			TableModel model = new TableModel();
			ColumnDefinition cd = columnMap.get(getRuleMapValueByKey(ruleMap, "indexKey"));
			model.setTableName(tableName); // 表名
			ColumnModel column = new ColumnModel();
			column.setColumnName(cd.getColumnName());
			column.setJavaType(cd.getJavaType());
			column.setValue(pidkey);
			model.setIdColumn(column);// 主键

			Map<String, Object> tableMap = mutilDatabaseBean.getTableDataByPrimaryKey(model, databaseId);
			if (tableMap != null) {
				if(isOracle){
					data.put("idKey", tableMap.get(getRuleMapValueByKey(ruleMap, "idKey").toUpperCase()));
				}else{
					String idKeyName = getRuleMapValueByKey(ruleMap, "idKey");
					if(!tableMap.containsKey(idKeyName)){
						idKeyName = idKeyName.toUpperCase();
					}
					data.put("idKey", tableMap.get(idKeyName));
				}
			}
			data.put("pIdKey", pidkey);
		}

		data.put("epac", epac);
		data.put("rid", rid);
		data.put("treeId", treeId);
		
		data.put("iconEnable", getRuleMapValueByKey(ruleMap, "icon") == null ? false : true);
		data.put("iconOpenEnable", getRuleMapValueByKey(ruleMap, "iconOpen") == null ? false : true);
		data.put("iconCloseEnable", getRuleMapValueByKey(ruleMap, "iconClose") == null ? false : true);

		request.setAttribute("data", data);
		return new ModelAndView("/form/defined/ztree/ztreeEdit", modelMap);
	}

	@javax.annotation.Resource
	public void setFormRulePropertyService(FormRulePropertyService formRulePropertyService) {
		this.formRulePropertyService = formRulePropertyService;
	}

	@javax.annotation.Resource
	public void setMutilDatabaseBean(MutilDatabaseBean mutilDatabaseBean) {
		this.mutilDatabaseBean = mutilDatabaseBean;
	}
	
public static void main(String[] args) {
	System.out.println("t0_0_cell_useradd1_0_treeid".replaceFirst("_0_", "."));
}
	
}
