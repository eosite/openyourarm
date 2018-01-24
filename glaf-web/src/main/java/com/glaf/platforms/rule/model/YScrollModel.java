package com.glaf.platforms.rule.model;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.mchange.io.FileUtils;

public class YScrollModel extends CommonModel implements IRule, CssRule, AttrRule {
	
	private static final long serialVersionUID = 1L;
	protected String templateScript = "" ;
	//增加模板
	public String getTemplateScript(){
		String template = "<script type='text/javascript' src='${contextPath}/scripts/jquery.SuperSlide.2.1.1.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/jquery.yScroll.plugin.js'></script>" ;
		scriptMap.put("yscroll", template );
		
		
		String path = SystemProperties.getConfigRootPath()
				+ "/conf/templates/form/yscroll.css.tmp";
		File file = new File(path);
		String style = "" ;
		try {
			// 修改备注   只有第一次控件id为video的时候才加载   如果页面有多个video控件的时候 则不加载当前JS
			style = "<style type='text/css'>" + FileUtils.getContentsAsString(file, "UTF-8").replaceAll("[\\t\\n\\r]", "") +"</style>";
		} catch (IOException e) {
			e.printStackTrace();
		}
		templateScript+=style;
		templateScript+=tempScript();
		
		
		return templateScript;
	}
	public void addTemplate(String subTemplate){
		this.templateScript += subTemplate ;
	}


	@Override
	public String getElementTagName() {
		return "div";
	}

	@Override
	public String getElementHtml() {
		return null ;
	}

	private boolean notEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true ;
		}
		return false ;
	}
	@Override
	public String getBind() {
		return null;
	}
	
	private String tempScript(){
		String mode = this.getMode() ;
		String path = "\\${contextPath}";
		if(!"General".equalsIgnoreCase(mode)){
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
		String  script = "<script type=\"text/x-kendo-template\" id=\""+this.getId()+"template\"><div class=\"yscrollclass\">"
				+ definedHtml.replaceAll("&amp;", "&")
				+"</div></script>";
		
		return script ;
	}
	
	private String htmlDefined(){
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
	
	/**
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
	}

	
	private String regex(String str,String path){
		String regex = "((<img)(\\s+(\\w*=(\"|\')\\w*(\"|\'))*\\s*src=\"))" ; 
		String zoom = this.zoomView();
		String zoomClass = "" ;
		if(notEmpty(zoom)){
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
	
	/**
	 * 滚动个数
	 * @return
	 */
	public String getScroll(){
		return source.get("scroll");
	}
	
	/**
	 * 自动播放
	 * @return
	 */
	public String getAutoPlay(){
		return source.get("autoPlay");
	}
	
	/**
	 * 显示个数
	 * @return
	 */
	public String getVis(){
		return source.get("vis");
	}
	private String zoomView(){
		return source.get("zoomView"); 
	}
	
	/***
	 * 切图时间
	 * @return
	 */
	public String getSpeed(){
		return getSource("speed", "2000");
	}
}
