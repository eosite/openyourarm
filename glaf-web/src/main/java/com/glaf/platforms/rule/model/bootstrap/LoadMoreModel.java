package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.ButtonModel;
import com.glaf.platforms.rule.model.CommonModel;

public class LoadMoreModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		//如果是通用的组件 加载的话可以所有都放在通用里面避免重复加载
				String weuiScript = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />"
						+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />"
						+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/demos/css/demos.css' />"
						+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.css' />"
						+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.min.js'></script>"
						+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>";	
				scriptMap.put("weui", weuiScript);
		
		String script = /*"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/demos/css/demos.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>"				
				+ */"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/loadMore/js/jquery.loadMore.extends.js'></script>";
			
		scriptMap.put("loadMore", script);
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	@Override
	public String getBind() {

		return null;
	}
	public String getVisible(){
		
		return source.get("visible");
		
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
//		Element ele = getElement();
//		return ele.toString();
		return null;
	}
	
	@Override
	public String getId() {
		return source.get("id");
	}

	@Override
	public String getName() {
		return source.get("name");
	}


}
