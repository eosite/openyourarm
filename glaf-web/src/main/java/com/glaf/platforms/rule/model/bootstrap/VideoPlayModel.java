package com.glaf.platforms.rule.model.bootstrap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class VideoPlayModel extends CommonModel implements IRule, CssRule, AttrRule {

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
	
	public String getMode(){
		return source.getOrDefault("mode", "common");
	}
	
	public String getSoa(){
		String soaServer = this.source.get("SOAServer");
		String path = "";
		if(soaServer != null && !soaServer.isEmpty()){
			JSONObject soaObj = JSON.parseArray(soaServer).getJSONObject(0);
			path = "http://"+soaObj.getString("host")+":"+soaObj.getString("port");
			if(!soaObj.getString("path").isEmpty()){
				path += "/"+soaObj.getString("path");
			}
		}
		return path;
	}
	
	@Override
	public String getElementHtml() {
		// TODO Auto-generated method stub
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		ele.removeAttr("src");
		String html = ele.outerHtml();
		//将img转换为div
		html = html.replace("<img", "<div");
		html += "</div>";
		return html;
	}
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/video/video-js.min.css'/>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/video/videojs-ie8.min.js'></script>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/video/video.min.js'></script>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/video/ext/jquery.video.extend.js'></script>");
		scriptMap.put("videoplay", sb.toString());
		return templateScript;
	}
	
	public String getVideoSrc(){
		return source.getOrDefault("videoSrc", "");
	}
	
	public String getPreImgSrc(){
		return source.getOrDefault("preImgSrc", "");
	}
}
