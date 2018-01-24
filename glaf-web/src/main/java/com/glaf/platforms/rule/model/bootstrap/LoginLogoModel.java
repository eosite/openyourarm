package com.glaf.platforms.rule.model.bootstrap;

import java.util.Iterator;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class LoginLogoModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//logo图片
	public String getLogo(){
		return super.getSource("logo", "");
	}
	
	public String getElementTagName() {
		return null;
	}
	
	@Override
	public String getElementHtml() {
		return null;
	}
	
	
	 @Override
    public void setScriptMap(Map<String, String> scriptMap) {
    
    }

	@Override
	public String getBind() {
		// TODO Auto-generated method stub
		return null;
	}

}
