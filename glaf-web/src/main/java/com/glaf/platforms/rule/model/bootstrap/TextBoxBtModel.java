package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class TextBoxBtModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	public String getTemplateScript() {
//		//如果是通用的组件 加载的话可以所有都放在通用里面避免重复加载
////		String muiScript = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css'/>"
////				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js'></script>";	
////		scriptMap.put("mui", muiScript);
//		String script = /*"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />"
//				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />"
//				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/demos/css/demos.css' />"
//				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.min.js'></script>"
//				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>"				
//				+*/ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/textbox/ext/jquery.textbox.extends.js'></script>";
//				
//
//		scriptMap.put("textboxbt", script);
//		return templateScript;
//	}
	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return null;
	}

	public boolean getOndblClick() {
		return Boolean.parseBoolean(super.getSource("dblclick-gis", "false"));
	}

	@Override
	public boolean isEnabled() {
		return Boolean.parseBoolean(this.source.get("enabled"));
	}
	
	public boolean isReadable() {
		return Boolean.parseBoolean(this.getSource("readable", "false"));
	}
	
	//控件名称
	public String getHtml(){
		String title = super.getSource("html", "");
		return title;
	}
	
	//默认值参数
	public String getDefaultVal(){
		return super.getSource("defaultVal", "");
	}
	
	//提示信息
	public String getMessage(){
		return super.getSource("message", "");
	}
	
	@Override
	public String getElementHtml() {

		String formHtml = formPage.getFormHtml();

		Document doc = Jsoup.parse(formHtml);

		Element ele = doc.getElementById(this.getId());
		
//		ele.removeAttr("contenteditable");
		
		this.attrDblclick(ele);
		
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		
		String html = ele.outerHtml();

		return html;
	}

	// 规则参数
	protected String dbRules() {
		String gisParams = this.source.get("gis-param");
		if (gisParams != null) {
			JSONArray ary = JSON.parseArray(gisParams);
			if (ary != null && !ary.isEmpty()) {
				JSONObject obj = ary.getJSONObject(0);
				JSONArray valary = obj.getJSONArray("value");
				return valary.toJSONString().replace("\"", "\'");
			}
		}
		return "";
	}

	private void attrDblclick(Element ele) {		
		Element input = ele;
		if (this.getOndblClick()) {
			input.attr("ondblclick", "odblClick_(this)");
			input.attr("dbrule", this.dbRules());
		} else if (!isNullOrEmpty(this.source.get("linkPage"))) {
			input.attr("ondblclick", "odblClick_(this)");
			input.attr("dbrule", this.dbRules());
			String selectPageStr = this.source.get("linkPage");
			if(!isNullOrEmpty(selectPageStr)){
				JSONArray selectPageAry = JSONArray.parseArray(selectPageStr);
				if(selectPageAry!=null && !selectPageAry.isEmpty()){
					String url = selectPageAry.getJSONObject(0).getString("url");
					input.attr("surl", url);
				}
			}
		}
	}
	
	
	/**
	 * 模板值
	 * 
	 * @return
	 */
	public String getTmpval() {
		return super.getSource("tmpVal", "false");
	}
	
	
//	 @Override
//    public void setScriptMap(Map<String, String> scriptMap) {
//    	this.scriptMap = scriptMap;
//		StringBuffer SB = new StringBuffer();
//
//		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/textbox/ext/jquery.textbox.extends.js\"></script>");
//		this.scriptMap.put("textboxbt", SB.toString());
//    }

}
