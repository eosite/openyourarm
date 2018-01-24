package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.ComboboxModel;

public class MetroselectmModel extends ComboboxModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, String> scriptMap;
	protected FormPage formPage;

	public String getBind() {
		return null;
	}
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = //Jsoup.parseBodyFragment("<div id=\""+this.getId()+"\" data-role=\"metroselect_m\"></div>");
				Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		//ele = ele.empty();
		Elements eles = ele.getElementsByTag("select");
		eles.empty();
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		Elements eles2 = ele.getElementsByTag("style");
		ele.empty();
		ele.append(eles.outerHtml());
		ele.append(eles2.outerHtml());
		
		return ele;
	}

	public String getElementTagName() {
		return null;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}
	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();

		//SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js\"></script>");
		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/select/jquery.metroselect_m.extends.js\"></script>");

		this.scriptMap.put("metroselect_m", SB.toString());
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

}
