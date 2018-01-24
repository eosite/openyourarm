package com.glaf.platforms.rule.model.bootstrap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class PopoverModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return null;
	}

	@Override
	/**
	 * 用于初始化控件信息。
	 * 即显示的时候控件的样式需要初始化的时候，就这样执行。
	 */
	public String getElementHtml() {

		Element ele = this.getElement();
		ele.removeClass("nlayout_elem").addClass("popover");
		
		return null;
	}
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/popover/jquery.popover.extends.js'></script>");
		scriptMap.put("popover", sb.toString());
		return templateScript;
	}
	
	
	public String getPromptmessage(){
		
		String str = source.getOrDefault("promptmessage", "right");
		return str;
	}
	public String getTitile(){
		
		String str = source.getOrDefault("titile", "");
		return str;
	}
	public String getContent(){
		
		String str = source.getOrDefault("content", "");
		return str;
	}
	
	
	
	
}
