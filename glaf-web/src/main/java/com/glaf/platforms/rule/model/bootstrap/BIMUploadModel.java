package com.glaf.platforms.rule.model.bootstrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.platforms.rule.model.UploadModel;

public class BIMUploadModel extends UploadModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getSaveUrl() {
		return super.getSaveUrl()+"&bim=true&brid="+super.getRuleId()+"&randomParent="/*+super.getRandomParent()*/;
	}
	
	public String getFileExtensionValue() {
		String value = super.getFileExtensionValue();
		value = value.replaceAll("\\.", "").replaceAll("\\;", "|");
		value = value.length()>0? "/(\\.|\\/)("+value+")$/i":"''";
		return value;
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
	
	public boolean isReadOnly() {
		return Boolean.parseBoolean(this.getSource("readOnly", "false"));
	}
	
	// 增加模板
	public String getTemplateScript() {
		String script = "";
		scriptMap.put("bimupload", script);
		return templateScript;
	}
	
}
