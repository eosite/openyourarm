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

public class SwitchModel extends CommonModel implements IRule, CssRule, AttrRule {

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
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getId() {
		//// System.out.println(source.get("id"));
		return source.get("id");
	}
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/css/bootstrap-switch.min.css'/>");
		sb.append("<script src='${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-switch/js/bootstrap-switch.min.js'></script>");
		 
//		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/video/video-js.min.css'/>");
//		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/video/videojs-ie8.min.js'></script>");
//		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/video/video.min.js'></script>");
//		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/video/ext/jquery.video.extend.js'></script>");
		scriptMap.put("switch", sb.toString());
		return templateScript;
	}
}
