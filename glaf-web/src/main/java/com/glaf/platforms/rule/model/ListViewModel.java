package com.glaf.platforms.rule.model;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class ListViewModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected String templateScript = "" ;
	
	//增加模板
	public String getTemplateScript(){
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
		
		
		String definedHtml = this.htmlDefined().replaceAll("\\\\\"", "\"");/*.replaceAll("<img src=\"", "<img src=\""+path)  ;*/
		definedHtml = this.regex(definedHtml, path);
		String  script = "<script type=\"text/x-kendo-template\" id=\""+this.getId()+"template\"><div class=\""+this.getId()+"class\">"
				+ definedHtml
				+"</div></script>";
		float rowCount = Float.parseFloat(this.getRowCount());
		float pageSize = Float.parseFloat(this.getPageSize());
		String linkStyle = "<style>."+this.getId()+"class {float: left;position: relative;width: "+(100/Math.ceil(pageSize/rowCount))+"%;height: "+(100/rowCount)+"%; margin: 0 0px;padding: 0;}</style>" ;
		templateScript += linkStyle + script ;
		return templateScript;
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
	
	public void addTemplate(String subTemplate){
		this.templateScript += subTemplate ;
	}
	@Override
	public String getBind() {
		return null;
	}
	
	public String getElementTagName(){
		return null ;
	}
	
	public String getElementHtml(){
		//style=\"width:"+this.getWidth()+";height:"+this.getHeight()+";\"
		String display = "" ;
		if("false".equalsIgnoreCase(this.isVisible())){
			display = "display:none" ;
		}
		String ret = "<div style=\"width:"+this.getWidth()+";"+display+"\" class=\"listview_div\"><div id=\""+this.getId()+"\"  style=\"width:"+this.getWidth()+";\"></div><div id=\""+this.getId()+"pager\" class=\"k-pager-wrap\"></div></div>";
		return ret ;
	}
	
	public String getPageSize(){
		return source.get("pageSize") ;
	}  
	
	public String getRowCount(){
		return source.get("rowCount") ;
	}
	
	
	public String getWidth(){
		String w =  source.get("width") ;
		return isNullOrEmpty(w)? "100%" : w ;
	}  
	
	public String getHeight(){
		String w =  source.get("height") ;
		return isNullOrEmpty(w)? "100%" : w ;
	}
	
	/***
	 * 服务端分页
	 * 
	 * @return
	 */
	public String getServerPaging() {
		return source.get("serverPaging");
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
		//String regex = "(<img\\s+(\\w*=(\"|\')\\w*(\"|\'))*\\s*src=\")" ; 
		String zoom = this.zoomView();
		String zoomClass = "" ;
		if(zoom!=null && !"".equals(zoom)){
			String isWin = source.get("isWin");
			String showWin = "" ; 
			if(isWin!=null && "true".equals(isWin)){
				showWin = "showWin" ;
			}
			zoomClass = "class=\""+zoom+"Imgl "+showWin+"\"";
		}
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer(); //替换后的字符串
		while(matcher.find()) {   
			//matcher.appendReplacement(sb, matcher.group()+path); 
			matcher.appendReplacement(sb, matcher.group(2)+" "+zoomClass+" "+matcher.group(3)+path); 
		}  
		matcher.appendTail(sb);
		return sb.toString();
	}
	private String zoomView(){
		return source.get("zoomView"); 
	}
	public String getDataBound(){
		String zoom = this.zoomView();
		if(zoom!=null && !"".equals(zoom)){
			String ret = "function(e){" ;
			if("clickView".equalsIgnoreCase(zoom)){
				ret += "$('."+zoom+"Imgl').on('click',zoomViewImg);";
			}else{
				ret += "$('."+zoom+"Imgl').on('dblclick',zoomViewImg);";
			}
			ret += "}";
			return ret ;
		}
		return null ;
	}
	/**
	 * 选择模式
	 * @return
	 */
	public String getSelectable(){
		return source.get("selectable") ;
	}
	
	/**
	 * 本地化
	 * @return
	 */
	public String getLocalFile(){
		return source.get("localFile");
	}
}
