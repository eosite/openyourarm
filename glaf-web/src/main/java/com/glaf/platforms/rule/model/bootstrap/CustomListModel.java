package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.KendoEnum;
import com.glaf.platforms.rule.model.CommonModel;


public class CustomListModel extends MetrolistModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, String> scriptMap;

	public String getBind() {
		return null;
	}
	

	public String getElementTagName() {
		return null;
	}
	
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		return ele;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}
	
//	public String isPageable() {
//		return this.getSource("pageable", "false");
//	}
	/**
	 * 行个数
	 * 
	 * @return
	 */
	public String getRows() {
		return this.getSource("rows", "1");
	}
	
	/**
	 * 列个数
	 * 
	 * @return
	 */
	public String getRowColumns() {
		return this.getSource("rowColumns", "4");
	}
	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();
		SB.append("<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/customList/customList.extends.css'>");
		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/customList/jquery.customList.extends.js\"></script>");
		this.scriptMap.put("customList", SB.toString());
	}
	
}
