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
public class WYVideoModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<link href=\"http://nos.netease.com/vod163/nep.min.css\" rel=\"stylesheet\">");
		sb.append("<script src=\"http://nos.netease.com/vod163/nep.min.js\"></script>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/wyvideo/ext/jquery.wyvideo.ext.js'></script>");
		scriptMap.put("wyvideo", sb.toString());
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
	
	@Override
	public String getElementHtml() {
		Element ele = new org.jsoup.nodes.Element("video");
		ele.attr("id", this.getId());
		ele.attr("data-role", "wyvideo");
		ele.attr("class","video-js vjs-fluid");  //video-js必须要加载的样式 vjs-fluid自适应容器
		ele.attr("x-webkit-airplay","allow");
		ele.attr("webkit-playsinline",true);
		ele.attr("controls",true);//控制条
		//x-webkit-airplay="allow" webkit-playsinline controls
		/*<video id="my-video" class="video-js" x-webkit-airplay="allow" webkit-playsinline controls poster="poster.png" preload="auto" width="640" height="360" >
		    <source src="MY_VIDEO.mp4" type="video/mp4">
		</video>*/
		return ele.outerHtml();
	}

	public static void main(String[] args) {
		Element ele = new org.jsoup.nodes.Element("video");
		ele.attr("id", "a");
		ele.attr("data-role", "wyvideo");
		ele.attr("class","video-js");
		System.out.println(ele.outerHtml());
	}
}
