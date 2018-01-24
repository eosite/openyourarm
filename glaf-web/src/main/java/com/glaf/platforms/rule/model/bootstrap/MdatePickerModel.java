package com.glaf.platforms.rule.model.bootstrap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class MdatePickerModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";
	
	// 增加模板
	public String getTemplateScript() {

		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js'></script>";
		scriptMap.put("mui", script);
		
		String kk =  "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/plugin/picker/dist/css/mui.picker.min.css' />"
					+ "<script type='text/javascript' src='${contextPath}/scripts/mui/plugin/picker/dist/js/mui.picker.all.js'></script>";
		scriptMap.put("mui-picker", kk);
		
		script =  "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/mdatepicker/js/jquery.mdatepicker.extends.js'></script>";

		scriptMap.put("mdatepicker", script);
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
		 String str = super.getSource("format", "yyyy-MM-dd HH:mm");
		 return str;
	}

	public String getDefaultSystemDate() {
		if (source.get("defaultSystemDate") == null || !Boolean.parseBoolean(source.get("defaultSystemDate"))) {
			return "null";
		} else {
			return new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		}
	}
	public String getMinDate(){
		return source.get("minDate");
	}
	public String getMaxDate(){
		return source.get("maxDate");
	}
}
