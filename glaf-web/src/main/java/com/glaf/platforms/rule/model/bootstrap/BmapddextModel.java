package com.glaf.platforms.rule.model.bootstrap;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class BmapddextModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	@Override
	public String getElementTagName() {
		return null;
	}
	public String getSearchRange() {
		return source.get("searchRange");
	}
	//平移缩放
	public String getNavigation(){
		return source.get("navigation");
	}
	//比例尺控件
	public String getScaleControl(){
		return source.get("scaleControl");
	}
	//缩略图控件
	public String getOverviewMap(){
		return source.get("overviewMap");
	}
	//定位控件
	public String getGeolocation(){
		return source.get("geolocation");
	}
	//模型控件，三维，卫星等
	public String getMapTypeControl(){
		return source.get("mapTypeControl");
	}
	//微调范围
	public String getDragRang(){
		return source.get("dragRang");
	}
	public String getSearchWords(){
		return source.get("searchWords");
	}
	//是否允许微调
	public String getDragAble(){
		return source.get("dragAble");
	}
	//是否使用热力图
	public String getUseheat(){
		return source.get("useheat");
	}
	//缩放比例
	public String getZoom(){
		return source.get("zoom");
	}
	public String getShowSearchPanel(){
		return source.get("showSearchPanel");
	}
	@Override
	public String getElementHtml() {
		StringBuffer sb = new StringBuffer();
		sb.append("<div style='width:" + this.getWidth() + ";height:" + this.getHeight() + "' >");
		sb.append("<div id='" + this.getId() + "' style='width:100%;height:100%' ></div>");
		sb.append("</div>");
		return sb.toString();
	}
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/baidumap/ext/baidumap.extend.css'/>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/baidumap/ext/jquery.bmapdd.extend.js'></script>");
		scriptMap.put("bmapddext", sb.toString());
		scriptMap.put("bmap", "<script src='http://api.map.baidu.com/api?v=2.0&ak=e6cI087zNfhZ9zE7P5gClQTkhK7T13W2'></script>");
		return templateScript;
	}
	
	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {
		return source.get("height");
	}
	
	public String getRefreshms(){
		return source.get("refreshms");
	}
	
	public String getShowSelectPanel(){
		return source.get("showSelectPanel");
	}
}
