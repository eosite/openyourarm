package com.glaf.platforms.rule.model.bootstrap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class DatePickerBtModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return null;
	}

	public String getElementHtml() {
		
		
		String formHtml = formPage.getFormHtml();
		
		Document doc = Jsoup.parse(formHtml);
		
		//用于解决 弹出层与 百度ueditor 上下层级关系
		if(doc.getElementsByAttributeValue("data-role", "editor").size()>0){
			Element originalEle = this.getElement();
			Element parent = originalEle.parent();
			parent.attr("style","z-index:1000;"+parent.attr("style").replaceAll("z-index:1000;", ""));
		}
		
		Element ele = doc.getElementById(this.getId());
//		ele.removeAttr("contenteditable");
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		String html = ele.outerHtml();
		return html;

	}  
	public String getPosition() {
		 String str = super.getSource("position", "right top");
		 //str = str.replace('M', 'm');
		 return str;
	}
	
	public String getFormat() {
		 String str = super.getSource("format", "yyyy-MM-dd");
		 //str = str.replace('M', 'm');
		 return str;
	}

	public String getDefaultSystemDate() {
		if (source.get("defaultSystemDate") == null || !Boolean.parseBoolean(source.get("defaultSystemDate"))) {
			return "null";
		} else {
			return new SimpleDateFormat(super.getSource("format", "yyyy-MM-dd")).format(new Date());
		}
	}
	
	public String getInputable(){
		return super.getSource("inputable", "false");
	}

	
//    @Override
//    public void setScriptMap(Map<String, String> scriptMap) {
//    	this.scriptMap = scriptMap;
//		StringBuffer SB = new StringBuffer();
//
//		SB.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/datepicker/css/bootstrap-datepicker3.min.css\" rel=\"stylesheet\" type=\"text/css\" />");
//		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/datepicker/js/bootstrap-datepicker.min.js\"></script> ");
//		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/datepicker/ext/jquery.datepicker.extends.js\"></script>");
//		this.scriptMap.put("datepickerbt", SB.toString());
//    }
	
}