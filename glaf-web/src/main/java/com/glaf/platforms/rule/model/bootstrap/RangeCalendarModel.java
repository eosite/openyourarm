package com.glaf.platforms.rule.model.bootstrap;

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

public class RangeCalendarModel extends CommonModel implements IRule, CssRule, AttrRule {

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
	/**
	 * 显示控件
	 * 
	 * @return
	 */
	public boolean isVisible2() {
		return Boolean.valueOf(source.get("visible"));
	}
	
	@Override
	public String getElementHtml() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml); 
		Element ele = doc.getElementById(this.getId());
		Elements styles = ele.getElementsByTag("style").clone();
		
		ele.empty().append(styles.outerHtml());
		if(!this.isVisible2()){
			ele.attr("style","display:none;");
		}
		return ele.outerHtml();
	}
	
	public String getId() {
		//// System.out.println(source.get("id"));
		return source.get("id");
	}
	
	public String getTemplateScript() {
		scriptMap.put("jquerytouchui", "<script src='${contextPath}/scripts/jquery-ui-1.9.2.min.js'></script>"
				+"<script type='text/javascript' src='${contextPath}/scripts/jsPlumb/js/jquery.ui.touch-punch.min.js'></script>");
		scriptMap.put("moment", "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/rangecalendar/js/moment-with-locales.js'></script>");
		
		scriptMap.put("rangecalendar", "<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/rangecalendar/css/rangecalendar.css'>"
				+"<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/rangecalendar/css/rangecalendar.extend.css'>"
				+"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/rangecalendar/js/jquery.rangecalendar.js'></script>"
				+"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/rangecalendar/ext/jquery.rangecalendar.extend.js'></script>");
		
		return templateScript;
	}
	
	public String getStartDate() {
		return source.get("startDate");
	}
	
	public String getDefaultDate() {
		return source.get("defaultDate");
	}
	public String getEndDate() {
		return source.get("endDate");
	}
	
	public String getStartRangeWidth() {
		return source.get("startRangeWidth");
	}
	
	public String getMinRangeWidth() {
		return source.get("minRangeWidth");
	}
	
	public String getMaxRangeWidth() {
		return source.get("maxRangeWidth");
	}
	
	public String getHideMonths() {
		return source.get("hideMonths");
	}
	
	public String getAutoHideMonths() {
		return source.get("autoHideMonths");
	}
	
	/**
	 * 简单模式 显示名称
	 * 
	 * @return
	 */
	public String getDateKeyName() {
		return columnsValue("dateKeyName");
	}
	/**
	 * 获取值
	 * 
	 * @param columnType
	 * @return
	 */
	private String columnsValue(String columnType) {
		String columns = source.get("columns");
		JSONArray columnsArray = JSON.parseArray(columns);
		JSONObject jo = null,values = null;
		String str = null;
		if(null!=columnsArray){
			values = columnsArray.getJSONObject(0);
			columnsArray = values.getJSONArray("values");
			for (Object object : columnsArray) {
				jo = (JSONObject) object;
				if (columnType.equalsIgnoreCase(jo.getString("columnType"))) {
					str = jo.getString("ColumnName");
				}
		  }
		}
		return str;
	}
	
	/**
	 * 是否使用分页
	 * @return
	 */
	public String getPaging(){
		return super.getSource("pageable", "false");
	}
}
