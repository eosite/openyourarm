package com.glaf.platforms.rule.model.bootstrap;

import org.jsoup.nodes.Element;

import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

/**
 * 网易直播客户端
 * @author J
 *
 */
public class ALiVideoModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<link rel=\"stylesheet\" href=\"//g.alicdn.com/de/prismplayer/1.9.9/skins/default/index.css\" />");
		sb.append("<script src=\"https://g.alicdn.com/de/prismplayer/1.9.9/prism-min.js\"></script>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/alivideo/ext/jquery.alivideo.ext.js'></script>");
		scriptMap.put("alivideo", sb.toString());
		return templateScript;
	}


	@Override
	public String getBind() {
		return null;
	}
	
	public String getIsLive() {
		return source.getOrDefault("isLive", "false");
	}
	
	
	public String getAutoPlay() {
		return source.getOrDefault("autoplay", "false");
	}
	
	/**
	 * 域名
	 * @return
	 */
	public String getDomainName() {
		return source.getOrDefault("domainName",null);
	}
	/**
	 * 应用名
	 * @return
	 */
	public String getAppName() {
		return source.getOrDefault("appName", null);
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
	
	@Override
	public String getElementHtml() {
		Element ele = new org.jsoup.nodes.Element("div");
		ele.attr("id", this.getId());
		ele.attr("data-role", "alivideo");
		ele.attr("class","prism-player"); 
		return ele.outerHtml();
	}

}
