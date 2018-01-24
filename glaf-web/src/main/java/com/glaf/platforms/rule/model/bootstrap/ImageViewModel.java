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

public class ImageViewModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		/*String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/imageview/css/imageview.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.previewimage.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.zoom.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/imageview/js/jquery.imageview.extends.js'></script>";

		scriptMap.put("photoSwipeModel", script);
		return templateScript;*/
		return null;
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
    public String getPreviewId(){
    	return source.get("previewId");
    }
	@Override
	public String getName() {
		return source.get("name");
	}

}
