package com.glaf.platforms.rule.model.bootstrap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class PhoneDateTimePickerModel extends CommonModel implements IRule, CssRule, AttrRule {
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
		String script = /*"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/demos/css/demos.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>"				
				+*/ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/phoneDateTimePicker/js/jquery.phoneDateTimePicker.extends.js'></script>";
				

		scriptMap.put("phoneDateTimePicker", script);
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
	public boolean isClearBtn() {
		return Boolean.parseBoolean(this.getSource("clearBtn", "false"));
	}
	
	public String getPosition() {
		 String str = super.getSource("position", "right top");
		 //str = str.replace('M', 'm');
		 return str;
	}
	
	public String getFormat() {
		 String str = super.getSource("format", "yyyy-mm-dd hh:ii:ss");
		 return str;
	}

	public String getDefaultSystemDate() {
		if (source.get("defaultSystemDate") == null || !Boolean.parseBoolean(source.get("defaultSystemDate"))) {
			return "null";
		} else {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		}
	}
}
