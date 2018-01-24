package com.glaf.bim;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.ColumnModel;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.factory.TableFactory;
import com.glaf.core.identity.User;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.RequestUtils;
import com.glaf.form.core.util.MutilDatabaseBean;

public class BimUtils {
	
	public void saveSource(HttpServletRequest request,Map<String, String> ruleMap, JSONArray ary,MutilDatabaseBean mutilDatabaseBean) throws Exception {
		User user = RequestUtils.getUser(request);

		String dataSourceSet = ruleMap.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONObject datasourceSetJSONObject = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
		}
		if (datasourceSetJSONObject == null) {
			return;
		}

		JSONArray tablesAry = datasourceSetJSONObject.getJSONArray("selectDatasource");
		if (tablesAry == null || tablesAry.size() != 1) {
			return;
		}
		Long databaseId = tablesAry.getJSONObject(0).getLong("databaseId");
		if(databaseId == null){
			databaseId = 0l;
		}

		JSONArray columnAry = datasourceSetJSONObject.getJSONArray("selectColumns");

		JSONObject firstObj = columnAry.getJSONObject(0);
		String tableName = firstObj.getString("tableName");
		
		if (!DBUtils.isAllowedTable(tableName)) {
			throw new RuntimeException("不允许访问系统表。");
		}

		List<ColumnDefinition> columns = TableFactory.getColumnDefinitions(databaseId, tableName);
		
		Map<String, ColumnDefinition> columnMap = new HashMap<String, ColumnDefinition>();
		
		ColumnDefinition idColumn = null;
		for (ColumnDefinition column : columns) {
			if (column.isPrimaryKey()) {
				idColumn = column;
			}
			columnMap.put(column.getColumnName().toLowerCase(), column);
		}
		
		String urn = null;  // 唯一码
		String formId = null;// 表单ID
		String attrId = null;// 文件ID
		String name = null; // 文件名称
		
		JSONObject columnObj  = null ;
		for (Object object : columnAry) {
			columnObj = (JSONObject) object;
			String ctype = columnObj.getString("ctype");
			String columnName = columnObj.getString("columnName");
			String columnNameSimple = columnName.replace(tableName+"_0_", "");
			if("urn".equalsIgnoreCase(ctype)){
				urn = columnNameSimple ;
			}else if("formId".equalsIgnoreCase(ctype)){
				formId = columnNameSimple;
			}else if("attrId".equalsIgnoreCase(ctype)){
				attrId = columnNameSimple;
			}else if("name".equalsIgnoreCase(ctype)){
				name = columnNameSimple;
			}
		}
		ColumnDefinition column = null;
		ColumnModel colModel = null ;
		JSONObject obj = null;
		for (Object object : ary) {
			obj = (JSONObject) object;
			
			TableModel tableModel = new TableModel();
			tableModel.setTableName(tableName);
			
			String nextId = mutilDatabaseBean.getNextId(tableName, "id", user.getActorId(), databaseId);
			ColumnModel idCol = new ColumnModel();
			idCol.setColumnName(idColumn.getColumnName());
			idCol.setJavaType(idColumn.getJavaType());
			idCol.setValue(nextId);
			tableModel.addColumn(idCol);
			
			
			column = columnMap.get(urn.toLowerCase());
			if(column != null){
				colModel = new ColumnModel();
				colModel.setColumnName(column.getColumnName());
				colModel.setJavaType(column.getJavaType());
				colModel.setValue(obj.getString("urn"));
				tableModel.addColumn(colModel);
			}
			
			column = columnMap.get(formId.toLowerCase());
			if(column != null){
				colModel = new ColumnModel();
				colModel.setColumnName(column.getColumnName());
				colModel.setJavaType(column.getJavaType());
				colModel.setValue(obj.getString("formId"));
				tableModel.addColumn(colModel);
			}
			
			column = columnMap.get(name.toLowerCase());
			colModel = new ColumnModel();
			colModel.setColumnName(column.getColumnName());
			colModel.setJavaType(column.getJavaType());
			colModel.setValue(obj.getString("name"));
			tableModel.addColumn(colModel);
			
			column = columnMap.get(attrId.toLowerCase());
			if(column != null){
				colModel = new ColumnModel();
				colModel.setColumnName(column.getColumnName());
				colModel.setJavaType(column.getJavaType());
				colModel.setValue(obj.getString("attrId"));
				tableModel.addColumn(colModel);
			}

			try {
				mutilDatabaseBean.insertTableData(tableModel, databaseId);
			} catch (Exception e) {
				throw new Exception("save error" + e.getMessage());
			}
		}

	}
}
