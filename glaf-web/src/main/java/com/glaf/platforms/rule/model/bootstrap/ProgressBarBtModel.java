package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class ProgressBarBtModel extends CommonModel implements IRule, CssRule, AttrRule {
	
	private static final long serialVersionUID = 1L;
//	protected String templateScript = "" ;
//	//增加模板
//	public String getTemplateScript(){
//		return templateScript;
//	}
//	public void addTemplate(String subTemplate){
//		this.templateScript += subTemplate ;
//	}
	@Override
	public String getElementTagName() {
		return null;
	}

	public String getElementHtml() {
		
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
//		ele.removeAttr("contenteditable");
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		String html = ele.outerHtml();
		return html;
	}
	@Override
	public String getBind() {
		return null;
	}
	/**
	 * 进度条模式
	 * @return
	 */
	public String getType(){
		return source.get("type") ;
	}
	/**
	 * 最小值
	 * @return
	 */
	public String getMin(){
		return source.get("min");
	}
	/**
	 * 最大值
	 * @return
	 */
	public String getMax(){
		return source.get("max");
	}
	/**
	 * 固定值
	 */
	public String getValue(){
		return source.get("value");
	}
	
	/**
	 * 显示数字状态
	 * @return
	 */
	public String getShowStatus(){
		return source.get("showStatus");
	}
	/**
	 * 是否激活条纹
	 * @return
	 */
	public String getActive(){
		return source.get("active");
	}
	
//	 @Override
//    public void setScriptMap(Map<String, String> scriptMap) {
//    	this.scriptMap = scriptMap;
//		StringBuffer SB = new StringBuffer();
//
//		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/progressbar/ext/jquery.progressbar.extends.js\"></script>");
//		this.scriptMap.put("progressbarbt", SB.toString());
//    }

	
}
