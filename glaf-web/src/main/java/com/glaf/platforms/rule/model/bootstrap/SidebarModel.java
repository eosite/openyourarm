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

public class SidebarModel extends CommonModel implements IRule, CssRule, AttrRule {

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
		ele.removeClass("nlayout_elem");
		//ele.attr("style","display:none");
		//String html = ele.outerHtml();
		//System.out.println(this.getId());
		
		return null;
	}
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/sidebar/ext/jquery.sidebar.extends.js'></script>");
		scriptMap.put("sidebar", sb.toString());
		return templateScript;
	}
	
	public String getDelayTime(){
		String str = super.getSource("delayTime","300");
		return str;
	}
	public String getSidebarClick(){
		String str = super.getSource("sidebarClick","false");
		return str;
	}
	
	public String getCartoon(){
		String str = super.getSource("cartoon","leftPlace");
		return str;
	}
	
	public String getDistance(){
		String str = super.getSource("distance","0");
		return str;
	}
	
	public String getTop(){
		String str = super.getSource("top","0");
		return str;
	}
	
}
