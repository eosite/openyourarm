package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.ButtonModel;
import com.glaf.platforms.rule.model.CommonModel;

public class TabbarModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";
	
	// 增加模板
	public String getTemplateScript() {
		//如果是通用的组件 加载的话可以所有都放在通用里面避免重复加载
		String weuiScript = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/demos/css/demos.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>";	
		scriptMap.put("weui", weuiScript);
		String muiscript = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js'></script>";
		scriptMap.put("mui", muiscript);
		String script = "<script type='text/javascript' src='${contextPath}/scripts/swipe/swiper-3.4.2.min.js'></script>"
						+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/tabbar/js/jquery.tabbar.extends.js'></script>";

		scriptMap.put("tabbar", script);
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
		Element ele = getElement();
//		ele.attr("style","overflow:visible");
//		ele.getElementById("offCanvasSide").attr("style","position:absolute;visibility:hidden;min-height:100%");
//		ele.getElementById("offCanvasContentScroll").attr("style","position:absolute;");
//	    ele.getElementById("offCanvasSideScroll").attr("style","position:absolute");
//		if(source.get("isvisible").equals("false")){
//			
//			Elements tabbarEles = ele.getElementsByClass("weui-tabbar__item");
//			Elements tabbarElesAct = ele.getElementsByClass("weui-tab__bd-item--active");
//			Elements tabbarElesOn = ele.getElementsByClass("weui-bar__item--on");
//			tabbarElesAct.removeClass("weui-tab__bd-item--active");
//			tabbarElesOn.removeClass("weui-bar__item--on");
//			tabbarEles.attr("style","display:none;");
//			
//		}
//		
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

   public String getColor(){
	   return source.get("selectedColor");
   }
   public String getVisible(){
	   return source.get("isvisible");
   }
}
