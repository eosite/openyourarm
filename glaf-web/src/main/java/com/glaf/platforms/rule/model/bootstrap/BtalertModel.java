package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.CommonModel;

public class BtalertModel extends CommonModel {
	protected Map<String, String> scriptMap;
	protected FormPage formPage;
	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();

		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/btalert/jquery.btalert.extends.js\"></script>");

		this.scriptMap.put("btalert", SB.toString());
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		return ele;
	}

	public String getElementTagName() {
		return null;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}

}
