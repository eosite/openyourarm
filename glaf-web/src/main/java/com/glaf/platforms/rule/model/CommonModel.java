package com.glaf.platforms.rule.model;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;

public class CommonModel {

	protected Map<String, String> source;
	protected Map<String, String> scriptMap;
	protected String templateScript = "";

	protected List<FormRule> formRules;

	protected List<FormComponent> formComponents;

	protected FormPage formPage;

	protected Element element;

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

	public CommonModel() {

	}

	public Map<String, String> getSource() {
		return source;
	}

	public void setSource(Map<String, String> source) {
		this.source = source;
		// formPageService = ContextFactory.getBean("formPageService");
		// this.formPage = formPageService.getFormPage(this.getSource("pageId",
		// ""));
	}

	public String getRuleId() {
		return source.get("ruleId");
	}

	public String getId() {
		//// System.out.println(source.get("id"));
		return source.get("id");
	}

	public String getName() {
		return source.get("name");
	}

	public String getValue() {
		return source.get("value");
	}

	public boolean isReadable() {
		return Boolean.parseBoolean(this.getSource("readable", "true"));
	}

	public boolean isRequired() {
		return Boolean.parseBoolean(this.getSource("required", "true"));
	}

	public boolean isWritable() {
		return Boolean.parseBoolean(this.getSource("writable", "true"));
	}

	public boolean isEnabled() {
		return Boolean.parseBoolean(this.getSource("enabled", "true"));
	}

	public String isVisible() {
		return this.getSource("visible", "true");
	}

	public String getWidth() {
		return this.getSource("width", "180px");
	}

	public String getStyle() {

		String style = "width:" + this.getWidth();

		return style;
	}

	public String getHeight() {
		return this.getSource("height", "");
	}

	public String getElementTagName() {
		return "div";
	}

	public String getElementHtml() {
		return this.getSource("html", "");
	}

	protected String getSource(String key, String defVal) {
		key = source.get(key);
		return isNullOrEmpty(key) ? defVal : key;
	}

	public void setFormRules(List<FormRule> formRules) {
		this.formRules = formRules;
	}

	public void setFormComponents(List<FormComponent> formComponents) {
		this.formComponents = formComponents;
	}

	protected static boolean isNullOrEmpty(String str) {
		return str == null || str.trim().length() == 0;
	}

	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
	}

	/**
	 * 获取HTML元素
	 * 
	 * @return
	 */
	public Element getElement() {
		/*
		 * String formHtml = formPage.getFormHtml(); Document doc =
		 * Jsoup.parse(formHtml); Element ele = null;
		 * if(StringUtils.isNotEmpty(this.getId())){ ele =
		 * doc.getElementById(this.getId()); }
		 */
		return element;
	}

	public void setElement(Element element) {
		this.element = element;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	public String getTemplateScript() {
		return templateScript;
	}

	// 回调函数参数
	protected String dbRules() {
		String gisParams = this.source.get("gis-param");
		if (gisParams != null) {
			JSONArray ary = JSON.parseArray(gisParams);
			if (ary != null && !ary.isEmpty()) {
				JSONObject obj = ary.getJSONObject(0);
				JSONArray valary = obj.getJSONArray("value");
				return valary.toJSONString().replace("\"", "\'");
			}
		}
		return "";
	}
}
