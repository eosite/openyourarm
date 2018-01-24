package com.glaf.platforms.rule.model.bootstrap;

import java.util.Map;
import java.util.Set;

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

public class BmapHeatModel extends BmapExtModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public String getBind() {
		return null;
	}

	public String getInitname(){
		return this.source.get("initname");
	}
	
	@Override
	public String getElementTagName() {
		return null;
	}
	public String getScrollable(){
		return this.source.getOrDefault("scrollable", "false");
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
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/baidumap/ext/jquery.bmapheat.extend.js'></script>");
		sb.append("<script type='text/javascript' src='http://api.map.baidu.com/library/Heatmap/2.0/src/Heatmap_min.js'></script>");
		scriptMap.put("bmapext", sb.toString());
		scriptMap.put("bmap", "<script src='http://api.map.baidu.com/api?v=2.0&ak=e6cI087zNfhZ9zE7P5gClQTkhK7T13W2'></script>");
		return templateScript;
	}
	
	public String getTranslatetype(){
		return this.source.get("translatetype");
	}
	
	public String getMapping(){
		String dataSourceSet = source.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray datasourceColumnsJSONArray = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			datasourceColumnsJSONArray = datasourceSetJSONObject.getJSONArray("selectColumns");
		}
		//根据seq分组
		JSONObject o,columnObj,retJSONObject=new JSONObject();
		String a;
		for (Object object : datasourceColumnsJSONArray) {
			// 赋值excel表内容
			columnObj = (JSONObject) object;
			a = columnObj.getString("ctype");
			if(StringUtils.isNotEmpty(a)){
				o = new JSONObject();
				o.put("cn", columnObj.getString("title"));
				o.put("en", columnObj.getString("columnLabel"));
				o.put("type", columnObj.getString("FieldType"));
				retJSONObject.put(a, o); //数值
			}
		}
		return retJSONObject.toJSONString();
	}
	
	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {
		return source.get("height");
	}
}
