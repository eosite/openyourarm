package com.glaf.platforms.rule.model.bootstrap;

import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class EditorBtModel extends CommonModel implements IRule, CssRule, AttrRule {

	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	@Override
	public String getRuleId() {
		return source.get("ruleId");
	}
	
	public String getVisible() {
		if ("true".equalsIgnoreCase(source.get("visible"))) {
			return "block";
		}
		return "none";
	}
	@Override
	public String getWidth() {
		return super.getWidth().replace("px", "");
	}

	@Override
	public String getHeight() {
		return super.getHeight().replace("px", "");
	}
	
	
	public String getElementTagName() {
		return null;
	}
	//获取第一层
	@Override
	public String getElementHtml() {
		String rid = getRuleId();
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
//		ele.removeAttr("contenteditable");
		ele.html("");
		Element div = new Element(Tag.valueOf("div"), "");
		if("img".equals(ele.tagName())){
			Attributes attrs= ele.attributes();
			Iterator<Attribute> it = attrs.iterator();
			while(it.hasNext()){
				Attribute attr= it.next();
				if(!"src".equals(attr.getKey())){
					div.attr(attr.getKey(),attr.getValue());
				}			
			}
		}else{
			div=ele;
		}
//		if ("false".equals(this.isVisible())) {
//			div.attr("style", "display:none;"+ele.attr("style"));
//		}
		return div.toString();
	}
	

	private String getStringValue(String key, String defaultValue) {
		return source.get(key) == null ? defaultValue : source.get(key);
	}

	private boolean getBooleanValue(String key, boolean defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(source.get(key));
		}
	}

	private int getIntValue(String key, Integer defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(source.get(key));
		}
	}

	private double getDoubleValue(String key, Double defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Double.parseDouble(source.get(key));
		}
	}
	
	
	//增加模板
	public String getTemplateScript(){
		String  script = 
			 "<link type='text/css' rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/editor/css/summernote.css' />"
		    +"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/editor/js/summernote.min.js'></script>";
			
		scriptMap.put("editorbt", script);
		return templateScript;
	}
}
