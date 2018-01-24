package com.glaf.platforms.rule.model.bootstrap;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class DhxGanttModel extends GanttModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	@Override
	public String getElementTagName() {
		return null;
	}
	
	public String getIsSync() {
		return source.getOrDefault("isSync", "false");
	}
	
	@Override
	public String getElementHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div style='width:" + this.getWidth() + ";height:" + this.getHeight() + "' >");
		sb.append("<div id='" + this.getId() + "' style='width:100%;height:100%' ></div>");
		
		
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml); 
		Element ele = doc.getElementById(this.getId());
		Elements styles = ele.getElementsByTag("style").clone();
		
		sb.append(styles.outerHtml());
		sb.append("</div>");
		
		return sb.toString();
	}
	public String getshowtip(){
		return this.source.getOrDefault("showTip", "true");
	}
	public String getMapping() {
		return null;
	}
	
	public String getExpandChild(){
		return this.source.getOrDefault("expandChild", "0");
	}
	
	public String getResizeColumn(){
		return this.source.getOrDefault("resizeColumn", "true");
	}
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();

		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/dhxgantt/codebase/dhtmlxgantt.css' type='text/css' media='screen' title='no title' charset='utf-8'>");
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/dhxgantt/codebase/skins/dhtmlxgantt_contrast_white.css' type='text/css' media='screen' title='no title' charset='utf-8'>");
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/dhxgantt/ext/dhxgantt.extend.css' type='text/css' media='screen' title='no title' charset='utf-8'>");
		
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/dhxgantt/codebase/sources/dhtmlxgantt.js' type='text/javascript' charset='utf-8'></script>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/dhxgantt/ext/dhxgantt_export_api.js' type='text/javascript' charset='utf-8'></script>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/dhxgantt/ext/jquery.dhxgantt.extend.js' type='text/javascript' charset='utf-8'></script>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/dhxgantt/ext/dhtmlxgantt_quick_info.extend.js' type='text/javascript' charset='utf-8'></script>");
		
		scriptMap.put("dhxgantt", sb.toString());
		return templateScript;
	}
	
	private static List<String> ganttDates = new ArrayList<String>(){{
		add("datepicker");
		add("datetimepicker");
		add("timepicker");
	}};
	
	public String getKeyMapping(){
		return JSON.toJSONString(super.getKeyMap());
	}
	
	public void convert(JSONObject targerObj,JSONObject sourceObjs,JSONObject sourceObjs2){
		
		targerObj.put("name", sourceObjs.getString("columnName"));
		targerObj.put("label", sourceObjs.getString("name"));
		targerObj.put("width", sourceObjs2.getString("columnWidth"));
		targerObj.put("align", sourceObjs2.getString("alignment"));
		
//		targerObj.put("id", sourceObjs.getString("id"));
//		targerObj.put("title", sourceObjs.getString("name"));
		targerObj.put("field", sourceObjs.getString("columnName"));
//		
//		targerObj.put("tableName", sourceObjs2.getString("tableName"));
//		
		targerObj.put("dType", ganttDates.contains(sourceObjs2.getString("editor"))?"datetime":sourceObjs2.getString("FieldType"));
//		targerObj.put("isHidden", sourceObjs2.getBoolean("isShowList")?"":"true");
//		
//		targerObj.put("columnWidth", sourceObjs2.getString("columnWidth"));
//		targerObj.put("alignment", sourceObjs2.getString("alignment"));
//		targerObj.put("isEditor", sourceObjs2.getString("isEditor"));
//		targerObj.put("formatValue", sourceObjs2.getString("formatValue"));
	}
	
	
	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {
		return source.get("height");
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
	
	/**
	 * 显示列映射
	 */
	@Override
	public String getColumns(){
		Map<String, String> keyMap = getKeyMap();
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
	
	protected String buildColumnTemplate(Map<String, JSONObject> dataSourceSetColumnMap, JSONObject jsonObj, JSONArray retArray, StringBuffer popScriptTemp,
			StringBuffer filterableScriptTemp, StringBuffer expScript, StringBuffer headTemp) {
		
		return null;
	}
	
	public String getGridWidth(){
		return source.get("gridWidth");
	}
}
