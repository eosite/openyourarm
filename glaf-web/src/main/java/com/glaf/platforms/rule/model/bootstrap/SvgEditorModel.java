package com.glaf.platforms.rule.model.bootstrap;

import org.jsoup.nodes.Element;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class SvgEditorModel extends CommonModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/svgEditor/js/svgEditor.js'></script>");
		scriptMap.put("svgEditor", sb.toString());
		return templateScript;
	}
	
	public String getElementTagName(){
		attrVisible();
		return null;
	}
	public String getElementHtml(){
		return null;
	}
	
	/**
	 * 显示隐藏
	 * @param ele
	 */
	private void attrVisible() {
		Element ele = super.getElement();
		if (!this.isVisible().equals("true")) {
			ele.attr("style", "display: none;" + ele.attr("style"));
		}
	}
	@Override
	public String getBind() {
		return null;
	}

}
