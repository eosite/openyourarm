package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class WSliderModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		String script ="<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/wslider/css/slideshow.css'/>"
				+ "<script  src='${contextPath}/scripts/plugins/bootstrap/wslider/ext/jquery.wslider.extends.js'></script>";
			
		scriptMap.put("acslider", script);
		
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}
	public String getRuleId() {
		return source.get("ruleId");
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
	private Elements as = new Elements();
	public String getElementHtml() {
//		Element ele = getElement();
//		return ele.toString();
		return null;
	}
	
	@Override
	public String getId() {
		return source.get("id");
	}
    public String getPreviewId(){
    	return source.get("previewId");
    }
	@Override
	public String getName() {
		return source.get("name");
	}
	public String getSliderImage(){
	    return source.get("sliderImage");	
	}
	public String getSliderTitle(){
		return source.get("sliderTitle");	
	}
	public String getPhotoWidth(){
		return source.get("photoWidth");
	}
	public String getPhotoHeight(){
		return source.get("photoHeight");
	}
	public String getPosition(){
		return source.get("position");
	}
}
