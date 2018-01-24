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

public class LoginPageModel extends CommonModel implements IRule, CssRule, AttrRule {

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
	
	
	//背景图
	public String getBgImage(){
		return super.getSource("bgImage", "");
	}
	
	//背景图切换时间
	public String getFade(){
		return super.getSource("fade", "");
	}
	
	//背景图切换间隔时间
	public String getDuration(){
		return super.getSource("duration", "");
	}

	@Override
	public String getElementHtml() {
		
		String formHtml = formPage.getFormHtml();

		Document doc = Jsoup.parse(formHtml);

		Element ele = doc.getElementById(this.getId());
		
//		ele.removeAttr("contenteditable");
		
		//初始化checkbox
		Elements checkDivs = ele.getElementsByClass("checker");
		if(!checkDivs.isEmpty()){
			Iterator<Element> it = checkDivs.iterator();
			while(it.hasNext()){
				Element checkDiv = it.next();
				Element input = checkDiv.getElementsByTag("input").first();
				checkDiv.before(input);
				checkDiv.remove();
			}	
		}
		
		String html = ele.outerHtml();
		return html;
	}
	
//	 @Override
//    public void setScriptMap(Map<String, String> scriptMap) {
//    	this.scriptMap = scriptMap;
//		StringBuffer SB = new StringBuffer();
//		SB.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"${contextPath}/scripts/plugins/bootstrap/login/css/login.css\"/>");
//		SB.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"${contextPath}/scripts/plugins/bootstrap/login/css/login-2.css\"/>");
//		SB.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"${contextPath}/scripts/plugins/bootstrap/login/css/login-3.css\"/>");
//		SB.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"${contextPath}/scripts/plugins/bootstrap/login/css/login-4.css\"/>");
//		SB.append("<link rel=\"stylesheet\" type=\"text/css\" href=\"${contextPath}/scripts/plugins/bootstrap/login/css/login-5.css\"/>");
//			
//
//		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/login/js/jquery.backstretch.min.js\"></script>");
//		SB.append("<script type=\"text/javascript\" src=\"${contextPath}/scripts/plugins/bootstrap/login/ext/jquery.loginpage.extends.js\"></script>");
//		this.scriptMap.put("login_page", SB.toString());
//    }

}
