package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

public class ModelBimModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String templateScript = "";
	// 增加模板
	
	public String getTemplateScript() {
		String script ="<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/modelbim/css/Toolbar.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/modelbim/css/Common.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/modelbim/css/Desktop.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/modelbim/css/NoteText.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/modelbim/css/jquery-ui.min.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/modelbim/css/ViewerSettings.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/modelbim/css/jquery.minicolors.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/modelbim/js/jquery-ui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/modelbim/js/BaseView1031.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/kendo/bootstrap/modelbim.extend.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/modelbim/js/jquery.minicolors.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/modelbim/js/Server_Integration.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/modelbim/js/CarsView.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/modelbim/js/CarsViewUi.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/modelbim/ext/jquery.modelbim.extends.js'></script>";
				
        scriptMap.put("modelbim", script);
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

    public String getIsToolBar(){
    	return source.get("istoolBar");
    }

}
