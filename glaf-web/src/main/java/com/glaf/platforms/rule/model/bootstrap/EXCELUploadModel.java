package com.glaf.platforms.rule.model.bootstrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.core.context.ApplicationContext;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.model.UploadModel;

public class EXCELUploadModel extends UploadModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getSaveUrl() {
		return super.getSaveUrl()+"&excel=true&ruleid="+super.getRuleId()+"&randomParent="/*+super.getRandomParent()*/;
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
		StringBuffer sb = new StringBuffer();
		sb.append("<script src='${contextPath}/scripts/jquery.excelUpload.plugin.js'></script>");
		sb.append(
				"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>");
		sb.append(
				"<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>");

		scriptMap.put("excelupload", sb.toString());
		return templateScript;
	}
	
	private boolean getBooleanValue(String key, boolean defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(source.get(key));
		}
	}
	
	public String getAbleChangeRule(){
		return source.getOrDefault("ableChangeRule", "false");
	}
	
	public String getExcelRule(){
		return source.getOrDefault("excelRule", "[]");
	}
	
	public String getFileListMaxH(){
		return source.get("fileListMaxH");
//		return source.getOrDefault("fileListMaxH", "100px");
	}
}
