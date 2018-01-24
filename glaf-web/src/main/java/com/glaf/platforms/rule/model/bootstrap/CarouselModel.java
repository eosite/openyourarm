package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class CarouselModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, String> scriptMap;
	protected FormPage formPage;

	public String getBind() {
		return null;
	}
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		return ele;
	}

	public String getElementTagName() {
		return null;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}
	
	
	public String getOStyle(){
		Element ele = getElement();
		String retStr = ele.attr("style");
		if(StringUtils.isEmpty(retStr)){
			retStr = " " ;
		}
		return retStr;
	}
	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();
		SB.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/carousel/css/carousel.css'/>");
		//SB.append("<script src='${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js' type='text/javascript'></script>");
		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/carousel/js/jquery.carousel.extends.js\"></script>");
		this.scriptMap.put("carousel", SB.toString());
	}

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}

	/**
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
	}
	
	/**
	 * 自动播放
	 * @return
	 */
	public String getAutoPlay(){
		return source.get("autoPlay");
	}

	/**
	 * 放大显示
	 * @return
	 */
	public String getZoomView(){
		return source.get("zoomView");
	}

	public void setSource(Map<String, String> source) {
		super.setSource(source);
		String json = source.get("FTPField");
		json = json==null||json.isEmpty()? "[]":json;
		JSONArray arr = JSON.parseArray(json);
		if(!arr.isEmpty()){
			JSONObject obj = arr.getJSONObject(0);
			JSONObject datas = obj.getJSONObject("datas");
			Iterator<String> it = datas.keySet().iterator();
			while(it.hasNext()){
				String key = it.next();
				source.put(key, datas.getString(key));
			}
			source.put("data-interval", source.get("speed"));
		}
	}
	
	/**
	 * 自适应高度
	 * @return
	 */
	public String getAdaptive(){
		return this.getSource("adaptive", "template");
	}
	
	
	public String getHtmlTemplate(){
		String mode = this.getMode() ;
		String path = "\\${contextPath}";
		if("SOA".equalsIgnoreCase(mode)){
			String soaServer = this.source.get("SOAServer");
			if(soaServer != null && !soaServer.isEmpty()){
				JSONObject soaObj = JSON.parseArray(soaServer).getJSONObject(0);
				path = "http://"+soaObj.getString("host")+":"+soaObj.getString("port")+"/"+soaObj.getString("path");
			}
		}else if(!"General".equalsIgnoreCase(mode)){
			path = "\\${contextPath}/mx/form/imageUpload?method=download2&databaseId=#:databaseId#&mode="+mode+"&id="+this.getRuleId()+"&rp=" ;
		}else{
			String ftpFieldStr = source.get("FTPField");
			if(StringUtils.isNotEmpty(ftpFieldStr)){
				JSONArray fields = JSON.parseArray(ftpFieldStr);
				JSONObject fieldObj = fields.getJSONObject(0);
				JSONObject datasObj = fieldObj.getJSONObject("datas");
				String idField = datasObj.getString("idField");
				String ftpField = datasObj.getString("ftpField");
				if(StringUtils.isNotEmpty(idField) && StringUtils.isNotEmpty(ftpField)){
					path = "\\${contextPath}/mx/form/imageUpload?method=download2&databaseId=#:databaseId#&mode="+mode+"&id="+this.getRuleId()+"&rp=" ;
				}
			}
		}
		
		String definedHtml = this.htmlDefined().replaceAll("\\\\\"", "\"") ;/*.replaceAll("<img src=\"", "<img src=\""+path)  ;*/
		definedHtml = this.regex(definedHtml, path);
		
		return definedHtml.replaceAll("&amp;", "&") ;
	}
	
	public String htmlDefined(){
		String htmlDefined = source.get("HTMLDefined");
		if(!isNullOrEmpty(htmlDefined)){
			JSONArray htmlAry = JSON.parseArray(htmlDefined.replace("#", "\\\\#"));
			if(htmlAry!=null && !htmlAry.isEmpty()){
				JSONObject htmlObj = htmlAry.getJSONObject(0);
				JSONObject htmldataObj = htmlObj.getJSONObject("htmldata");
				String htmlVal = htmldataObj.getString("htmlVal");
				Map<String,String> map = this.getDataSource();
				String hs = HTMLExpressionConvertUtil.kendoTemplateConvert(htmlVal,map);
				return hs ;
			}
		}
		return "" ;
	}
	
	private String regex(String str,String path){
		String regex = "((<img)(\\s+(\\w*=(\"|\')\\w*(\"|\'))*\\s*src=\"))" ; 
		String zoom = this.getZoomView();
		String zoomClass = "" ;
		if(StringUtils.isNotEmpty(zoom)){
			String isWin = source.get("isWin");
			String showWin = "" ; 
			if(isWin!=null && "true".equals(isWin)){
				showWin = "showWin" ;
			}
			zoomClass = "class=\""+zoom+"Img "+showWin+"\"";
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer(); //替换后的字符串
		while(matcher.find()) {   
			matcher.appendReplacement(sb, matcher.group(2)+" "+zoomClass+" "+matcher.group(3)+path); 
		}  
		matcher.appendTail(sb);
		return sb.toString();
	}
	
	public Map<String,String> getDataSource(){
		Map<String,String> map = new HashMap<>();
		String dataSourceSet = source.get("dataSourceSet");
		if(!isNullOrEmpty(dataSourceSet)){
			JSONArray dsAry = JSON.parseArray(dataSourceSet);
			if(dsAry!=null && !dsAry.isEmpty()){
				JSONObject jo = dsAry.getJSONObject(0);
				JSONArray columns = jo.getJSONArray("columns");
				if(columns!=null && !columns.isEmpty()){
					JSONObject col = null ;
					for (Object object : columns) {
						col = (JSONObject) object ;
						map.put(col.getString("code"), col.getString("columnName"));
					}
				}
			}
		}
		return  map ;
	}
	public String getScroll(){
		return source.get("scroll");
	}
	public String getVis(){
		return source.get("vis");
	}
}
