package com.glaf.platforms.rule.model.bootstrap;

import java.util.UUID;

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

public class ImagesExtModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}
	
	public String getmaxnum(){
		return this.source.get("maxnum");
	}

	@Override
	public String getElementTagName() {
		return null;
	}
	
	@Override
	public String getElementHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div data-role='imagesext' id='" + this.getId() + "' style='width:" + this.getWidth() + ";height:" + this.getHeight() + "' ></div>");
		return sb.toString();
	}
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/imagesext/imagesext.extend.css?v=1.1'/>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/imagesext/jquery.imagesext.extend.js?v=1.1'></script>");
		scriptMap.put("imagesext", sb.toString());
		return templateScript;
	}
	
	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {
		return source.get("height");
	}
	
	public String getRamdomParent() {
		return UUID.randomUUID().toString();
	}
	
	public String getSaveOperation(){
		return source.get("saveOperation");
	}
	
	public String getHeightauto(){
		return source.get("heightauto");
	}
	
	public String getColnum(){
		return source.get("colnum");
	}
	
	public String getRowheight(){
		return source.get("rowheight");
	}
	
	public String getRowwidth(){
		return source.get("rowwidth");
	}
	
	public String getPaddingvalue(){
		return source.get("paddingvalue");
	}
}
