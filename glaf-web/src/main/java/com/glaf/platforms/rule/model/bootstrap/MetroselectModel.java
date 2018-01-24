package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.ComboboxModel;

public class MetroselectModel extends ComboboxModel {

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
		Document doc = //Jsoup.parseBodyFragment("<div id=\""+this.getId()+"\" data-role=\"metroselect\"></div>"); 
				Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		//ele = ele.empty();
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		Elements eles = ele.getElementsByTag("select");
		eles.empty();
		Elements eles2 = ele.getElementsByTag("style");
		ele.empty();
		ele.append(eles.outerHtml());
		ele.append(eles2.outerHtml());
		
		return ele;
		/*Element ele = doc.getElementById(this.getId());
		ele = ele.empty();
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		return ele;*/
	}

	public String getElementTagName() {
		return null;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}
	
	public String getSelectFirst(){
		return source.getOrDefault("selectFirst", "false");
	}
	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();

//		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js\"></script>");
		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/select/jquery.metroselect.extends.js?v=1.1\"></script>");

		this.scriptMap.put("metroselect", SB.toString());
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

}
