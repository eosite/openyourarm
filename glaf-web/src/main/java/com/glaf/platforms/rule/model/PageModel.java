package com.glaf.platforms.rule.model;

import org.apache.commons.lang.StringUtils;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;


public class PageModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String templateScript = "" ;
	// 增加模板
	public String getTemplateScript() {
		String designerJson = formPage.getDesignerJson();
		if(StringUtils.isNotEmpty(designerJson)){
			templateScript += "<script> var globalStyle = "+ designerJson+" ;</script>";
		}
		return templateScript;
	}

	@Override
	public String getBind() {
		return null;
	}

	public String getElementTagName() {
		return null;
	}

	@Override
	public String getElementHtml() {
		return null;
	}

	@Override
	public Element getElement() {
		return null;
	}

	@Override
	public boolean isEnabled() {
		return Boolean.parseBoolean(this.source.get("enabled"));
	}

	// 背景图
	public String getBgImage() {
		return super.getSource("bgImage", "");
	}

	// 背景图切换时间
	public String getFade() {
		return super.getSource("fade", "");
	}

	// 背景图切换间隔时间
	public String getDuration() {
		return super.getSource("duration", "");
	}

}
