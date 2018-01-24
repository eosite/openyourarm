package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.ButtonModel;
import com.glaf.platforms.rule.model.CommonModel;

public class SlideOutsModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";
	// 增加模板
	public String getTemplateScript() { 
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js'></script>";
		scriptMap.put("mui", script);
		script = "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/slideout/js/jquery.slideouts.extends.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/kendo/bootstrap/slideouts.extend.js'></script>";
		scriptMap.put("slideouts", script);
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	@Override
	public String getBind() {

		return null;
	}

	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {

		return source.get("height");
	}
	
	
	@Override
	public void setSource(Map<String, String> source) {
		this.source = source;
	}

	@Override
	public String getElementTagName() {
		// return "ul";
		return null;
	}

	public String getElementHtml() {
		Element ele = getElement();
		ele.attr("style","overflow:visible");
		ele.getElementById("offCanvasSide").attr("style","position:absolute;visibility:hidden;min-height:100%");
		ele.getElementById("offCanvasContentScroll").attr("style","position:absolute;");
//	    ele.getElementById("offCanvasSideScroll").attr("style","position:absolute");
		return ele.toString();
	}
	
	@Override
	public String getId() {
		return source.get("id");
	}

	@Override
	public String getName() {
		return source.get("name");
	}
	public String getColumnDataSet(){
		return source.get("columns");
		
	}
	
	
}
