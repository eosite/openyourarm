package com.glaf.platforms.rule.model.bootstrap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class FieldsetModel extends CommonModel implements IRule, CssRule, AttrRule {

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
	/**
	 * 用于初始化控件信息。
	 * 即显示的时候控件的样式需要初始化的时候，就这样执行。
	 */
	public String getElementHtml() {

		return null;
	}
	
	

	
	
}
