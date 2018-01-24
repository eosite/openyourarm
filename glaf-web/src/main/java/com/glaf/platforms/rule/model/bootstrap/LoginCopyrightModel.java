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

public class LoginCopyrightModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	
	//验证提示信息
	public String getCopyright(){
		return super.getSource("copyright", "");
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
