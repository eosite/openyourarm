package com.glaf.platforms.rule.model.bootstrap;

import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.CommonModel;

public class PortletModel extends CommonModel {
	protected Map<String, String> scriptMap;
	protected FormPage formPage;
	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		//StringBuffer SB = new StringBuffer();

		//SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/portlet/jquery.portlet.extends.js\"></script>");

		//this.scriptMap.put("portlet", SB.toString());
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}
	
	public String getDataUrl() {
		String linkPageStr = super.getSource("linkPage", "");
		if (!isNullOrEmpty(linkPageStr)) {
			List<JSONObject> list = JSON.parseArray(linkPageStr, JSONObject.class);
			if (!list.isEmpty()) {
				return list.get(0).getString("url");
			}
		}
		return null;
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
