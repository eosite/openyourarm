package com.glaf.platforms.rule.model;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class RadioModel extends CommonModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public String getElementTagName() {
		return null ;
	}

	@Override
	public boolean isEnabled() {
		return Boolean.parseBoolean(this.source.get("enabled"));
	}
	
	@Override
	public String getElementHtml() {
		
		String formHtml = formPage.getFormHtml();
		
		Document doc = Jsoup.parse(formHtml);
		
		Element ele = doc.getElementById(super.getId());
		
		this.attrEnable(ele);
		this.attrVisible(ele);
		
		String html = ele.outerHtml();
		
		return html;
	}

	private void attrEnable(Element ele) {
		if (!this.isEnabled()) {
			ele.attr("disabled", "disabled");
		}
	}
	
	private void attrVisible(Element ele) {
		if (!this.isVisible().equals("true")) {
			ele.attr("style", "display: none;" + ele.attr("style"));
		}
	}

	@Override
	public String getBind() {
		return null;
	}
}
