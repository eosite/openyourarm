package com.glaf.platforms.rule.model.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.GridModel;

public class GanttModel extends GridModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Map<String, String> scriptMap;
	
	protected FormPage formPage;
	
	private static List<String> ganttDates = new ArrayList<String>(){{
		add("datepicker");
		add("datetimepicker");
		add("timepicker");
	}};
	
	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

	@Override
	public String getBind() {
		return super.getBind();
	}

	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;

		StringBuffer SB = new StringBuffer();
		//SB.append(
		//		"<script src='${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js' type='text/javascript'></script>");
		SB.append(
				"<script src='${contextPath}/scripts/plugins/bootstrap/gantt/js/iframeGantt.js' type='text/javascript'></script>");
		scriptMap.put("gantt", SB.toString());
	}
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		return ele;
	}

	public String getElementTagName() {
		return null;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}
	
	public String getComponentId(){
		Element ele = getElement();
		return ele.attr("componentid");
	}
	
	public JSONObject datasetColumns(){
		JSONObject retObj = new JSONObject();
		String dataSourceSet = source.get("dataSourceSet");
		if(dataSourceSet!=null && !dataSourceSet.isEmpty()){
			JSONArray ary = JSON.parseArray(dataSourceSet);
			JSONObject obj = ary.getJSONObject(0);
			JSONArray columns = obj.getJSONArray("columns");
			JSONObject column ;
			for (Object object : columns) {
				column = (JSONObject) object;
				retObj.put(column.getString("columnName"), column);
			}
		}
		return retObj;
	}
	
	public void convert(JSONObject targerObj,JSONObject sourceObjs,JSONObject sourceObjs2){
		targerObj.put("id", sourceObjs.getString("id"));
		targerObj.put("title", sourceObjs.getString("name"));
		targerObj.put("field", sourceObjs.getString("columnName"));
		
		targerObj.put("tableName", sourceObjs2.getString("tableName"));
		
		targerObj.put("dType", ganttDates.contains(sourceObjs2.getString("editor"))?"datetime":sourceObjs2.getString("FieldType"));
		targerObj.put("isHidden", sourceObjs2.getBoolean("isShowList")?"":"true");
		
		targerObj.put("columnWidth", sourceObjs2.getString("columnWidth"));
		targerObj.put("alignment", sourceObjs2.getString("alignment"));
		targerObj.put("isEditor", sourceObjs2.getString("isEditor"));
		targerObj.put("formatValue", sourceObjs2.getString("formatValue"));
	}
	/**
	 * 显示列映射
	 */
	@Override
	public String getColumns(){
		Map<String, String> keyMap = getKeyMap();
		/*String superColumns = super.getColumns();
		JSONArray superAry = JSON.parseArray(superColumns);
		JSONObject superObj ;
		for (Object object : superAry) {
			superObj = (JSONObject) object ;
			String columnName = superObj.getString("field");
			superObj.put("fieldCode",keyMap.containsKey(columnName)? keyMap.get(columnName).replace("Key", ""):columnName);
			superObj.put("isHidden", "");
			superObj.put("dType", "");
		}
		return superAry.toJSONString();*/
		//表头模板
		JSONArray columnTemplate = columnTemplateJSONArray();
		JSONObject datasetColumns = datasetColumns();
		JSONArray retAry = new JSONArray();
		JSONObject retObj ;
		JSONObject columnObj ;
		for (Object object : columnTemplate) {
			retObj = new JSONObject() ;
			columnObj = (JSONObject) object ; 
			convert(retObj, columnObj,datasetColumns.getJSONObject(columnObj.getString("columnName")));
			String columnName = retObj.getString("field");
			retObj.put("fieldCode",keyMap.containsKey(columnName)? keyMap.get(columnName).replace("Key", ""):columnName);
			retAry.add(retObj);
		}
		
		return retAry.toJSONString();
	}
	/**
	 * 更新集映射
	 * @return
	 */
	public String getMapping() {
		Map<String, String> keyMap = getKeyMap();
		String dataSourceSetUpdate = this.source.get("dataSourceSetUpdate");
		JSONArray dataSourceSetUpdateJSONArray = JSON.parseArray(dataSourceSetUpdate);
		JSONObject dataSourceSetUpdateJSONObject = dataSourceSetUpdateJSONArray.getJSONObject(0);
		String tableMsgString = dataSourceSetUpdateJSONObject.getString("tableMsg");
		JSONObject tableMsg = JSON.parseArray(tableMsgString).getJSONObject(0);
		String tableString = tableMsg.getString("table");
		JSONObject table = JSON.parseObject(tableString);
		
		Iterator<Object> it = getCols(tableMsg).iterator();
		
		Map<Integer, JSONObject> colsMap = new HashMap<Integer, JSONObject>();
		while(it.hasNext()){
			JSONObject obj = (JSONObject) it.next();
			colsMap.put(obj.getInteger("id"), obj);
		}
		
		JSONArray colsJSON = new JSONArray();
		
		JSONArray columnTemplateJSONArray =  columnTemplateJSONArray();
		for(Object o : columnTemplateJSONArray){
			JSONObject colTmpl = (JSONObject) o;
			Integer id = colTmpl.getInteger("id");
			String columnName =  colTmpl.getString("columnName");
			if(colsMap.containsKey(id)){
				JSONObject col = new JSONObject();
				JSONObject obj = colsMap.get(id);
				
				String key = keyMap.get(columnName);
				key = key != null? key : "c"+id;
				//key = key.equals("indexKey")?"id":key;
				key = key.replace("Key", "");
				
				col.put("isHidden", colTmpl.get("isHidden"));
				col.put("protoCode", id);
				col.put("fieldCode", key);
				col.put("title", colTmpl.get("name"));
				col.put("dType", obj.getString("dType"));
				col.put("fieldName", obj.getString("fieldName"));
				col.put("columnName", columnName);
				col.put("tableName", table.get("tableName"));
				colsJSON.add(col);
				
			}
		}
		
		return colsJSON.toJSONString();
	}
	
	/**
	 * 表头模板
	 * @return
	 */
	private JSONArray columnTemplateJSONArray() {
		String columnTemplate = columnTemplate();
		if(columnTemplate!=null && !columnTemplate.isEmpty()){
			return JSON.parseArray(columnTemplate);
		}
		return new JSONArray();
	}
	
	public String columnTemplate() {
		return this.source.get("columnTemplate");
	}
	
	
	/**
	 * 数据字段
	 * @param tableMsg
	 * @return
	 */
	private JSONArray getCols(JSONObject tableMsg) {
		JSONArray columns = tableMsg.getJSONArray("columns");
		if(columns!=null){
			return columns;
		}
		return new JSONArray();
	}
	
	protected Map<String, String> getKeyMap() {
		Map<String, String> map = new HashMap<>();
		String treelistColumns = this.source.get("columns");
		JSONArray treelistJSONArray = new JSONArray();
		if (StringUtils.isNotEmpty(treelistColumns)) {
			treelistJSONArray = JSON.parseArray(treelistColumns);
			for (Object object : treelistJSONArray) {
				JSONObject treelistJSONObject = (JSONObject) object;
				map.put(treelistJSONObject.getString("ColumnName"), treelistJSONObject.getString("columnType"));
			}
		}
		return map;
	}
	
	private String gMapBykey(String key){
		String treelistColumns = this.source.get("columns");
		JSONArray treelistJSONArray = new JSONArray();
		if (StringUtils.isNotEmpty(treelistColumns)) {
			treelistJSONArray = JSON.parseArray(treelistColumns);
			for (Object object : treelistJSONArray) {
				JSONObject treelistJSONObject = (JSONObject) object;
				if(key.equalsIgnoreCase(treelistJSONObject.getString("columnType"))){
					return treelistJSONObject.getString("ColumnName");
				}
			}
		}
		return null;
	}
	
	public String getIdKey(){
		return gMapBykey("indexKey");
	}
	
	public String getParentKey(){
		return gMapBykey("pIdKey");
	}
	
	//是否异步加载
	public String getIsSync(){
		return source.get("isSync");
	}
	
	//是否启用
	public String getCanwrite(){
		String enabled = source.get("enabled") ;
		return enabled==null?"false":enabled;
	}
	
	//显示系统工具栏
	public String getShowSysMenu(){
		return source.get("showSysMenu");
	}

}
