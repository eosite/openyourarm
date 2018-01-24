package com.glaf.platforms.rule.model.bootstrap;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.dubbo.container.Main;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

/**
 * 网易直播客户端
 * @author J
 *
 */
public class WebChatModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<link href=\"${contextPath}/scripts/plugins/bootstrap/webchat/ext/webchat.css\" rel=\"stylesheet\">");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/webchat/ext/jquery.webchat.ext.js'></script>");
		scriptMap.put("webchat", sb.toString());
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
	public boolean isEnabled() {
		return Boolean.parseBoolean(this.source.get("enabled"));
	}
	
	//控件名称
	public String getHtml(){
		String title = super.getSource("html", "");
		return title;
	}
	
	public String getIsList(){
		String str = super.getSource("isList","false");
		return str;
	}
	public String getHTMLDefined(){
		String str = super.getSource("HTMLDefined","");
		return str;
	}
	public String getHTMLTitle(){
		String str = super.getSource("HTMLTitle","");
		return str;
	}
	public String getHTMLMessage(){
		String str = super.getSource("HTMLMessage","");
		return str;
	}
	
	@Override
	public String getElementHtml() {
		Element ele = this.getElement();
		ele.select(".mt_webchat_chat").empty();
		ele.select(".mt_webchat_user").remove();
		return ele.outerHtml();
	}
	
	/**
	 * 默认房间号 
	 * @return
	 */
	public String getDefaultRoomId(){
		return source.getOrDefault("defaultRoomId", null);
	}
	
	/**
	 * 默认服务地址
	 * @return
	 */
	public String getDefaultHost(){
		return source.getOrDefault("defaultHost", null);
	}
    public String getIsVisible(){
    	return source.get("isvisible");
    }
}
