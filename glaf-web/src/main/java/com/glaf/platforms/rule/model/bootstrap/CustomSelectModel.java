package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
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

public class CustomSelectModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery.mobile/css/jquery.mobile-1.4.5.min.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery.mobile/js/jquery.mobile-1.4.5.js'></script>"			
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/customSelect/js/jquery.customSelect.extends.js'></script>";
			
		scriptMap.put("customSelect", script);
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
	public String getEnumValue() {
		return super.getSource("enumValue", "");
	}
	public String getDataTextField() {
		//return super.getSource("datatextfield", this.getColumnName("name"));
		return this.getDataField("datatextfield", "name");
	}

	public String getDataValueField() {
		//return super.getSource("datavaluefield", this.getColumnName("code"));
		return this.getDataField("datavaluefield", "code");
	}
	
	private String getDataField(String type,String defVal){
		String enumValue = getEnumValue();
		if (StringUtils.isNotEmpty(enumValue)) {
			return defVal;
		} else {
			String str = super.getSource(type, "");
			if(StringUtils.isNotEmpty(str)){
				List<JSONObject> jsonArray = JSON.parseArray(str,
						JSONObject.class);
				if (jsonArray.size() > 0) {
					return jsonArray.get(0).getString("columnName");
				}
			}
		}
		return defVal;
	}
	public String getVisible(){
		return source.get("visible");
	}
	public String getEnabled(){
		return source.get("enabled");
	}
}
