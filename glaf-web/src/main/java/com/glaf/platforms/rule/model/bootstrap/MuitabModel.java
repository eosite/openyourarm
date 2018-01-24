package com.glaf.platforms.rule.model.bootstrap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class MuitabModel extends CommonModel implements IRule, CssRule, AttrRule {

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
	
	@Override
	public String getElementHtml() {
		return null;
	}
	
	public String getId() {
		//// System.out.println(source.get("id"));
		return source.get("id");
	}
	
	public String getTemplateScript() {
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/muitab/muitab.extend.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/muitab/jquery.muitab.extend.js'></script>";
		 
		scriptMap.put("mui", "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<script  type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js' />");
		
		scriptMap.put("muitab", script);
		return templateScript;
	}
}
