package com.glaf.platforms.rule.model.bootstrap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class LabelModel extends CommonModel implements IRule, CssRule, AttrRule {

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

	
	
	@Override
	public String getElementHtml() {

		String formHtml = formPage.getFormHtml();

		Document doc = Jsoup.parse(formHtml);

		Element ele = doc.getElementById(this.getId());
		
//		ele.removeAttr("contenteditable");
		
		if("false".equals(super.isVisible())){
			ele.attr("style", "display: none;" + ele.attr("style"));
		}
		
		String html = ele.outerHtml();

		return html;
	}


}
