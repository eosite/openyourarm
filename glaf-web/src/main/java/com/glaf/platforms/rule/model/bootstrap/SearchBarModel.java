package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class SearchBarModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";
	
	// 增加模板
	public String getTemplateScript() {
		String script = /*"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/demos/css/demos.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>"				
				+ */"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/searchbar/js/jquery.searchbar.extends.js'></script>";
		
		//如果是通用的组件 加载的话可以所有都放在通用里面避免重复加载
		String weuiScript = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/demos/css/demos.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>";	
		scriptMap.put("weui", weuiScript);
		
		scriptMap.put("searchbar", script);
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
