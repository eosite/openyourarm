package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.ComboboxModel;

public class IcheckradioModel extends ComboboxModel {

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
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
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
		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/icheck/jquery.radio.extends.js\"></script>");

		this.scriptMap.put("icheckradio", SB.toString());
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}
	
	public String getStatic(){
		return source.get("static");
	}
	
	public String getDefaultIndex(){
		return source.get("selectedIndex");
	}
}
