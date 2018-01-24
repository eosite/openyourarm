package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.ButtonModel;

public class DefinedButtonModel extends ButtonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, String> scriptMap;
	protected FormPage formPage;
	
	public void setScriptMap(Map<String, String> scriptMap) {
//		this.scriptMap = scriptMap;
//		StringBuffer SB = new StringBuffer();
//
//		SB.append("<script src=\"${contextPath}/scripts/kendo/bootstrap/mtbutton.extend.js\"></script>");
//
//		this.scriptMap.put("mtbutton", SB.toString());
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
//		ele.removeAttr("contenteditable");
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		return ele;
	}

	
	public Boolean getSureDialog() {
		return Boolean.parseBoolean(super.getSource("sureDialog", "false"));
	}
	
	public String getDialogPosition() {
		return super.getSource("dialogPosition", "right");
	}

	
	public String getElementTagName() {
		return null;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}

}
