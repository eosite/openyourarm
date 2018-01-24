package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class VectorDrawModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		String script ="<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/VectorDraw/css/vectorDraw.css'/>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/VectorDraw/js/commands.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/VectorDraw/js/vdWebControl.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/VectorDraw/ext/jquery.vectorDraw.extends.js'></script>";
			
		scriptMap.put("cellWebCab", script);
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}
	public String getRuleId() {
		return source.get("ruleId");
	}
	@Override
	public String getBind() {

		return null;
	}

	@Override
	public String getWidth() {
		return source.get("width");
	}
	
	@Override
	public String getHeight() {

		return source.get("height");
	}
	
	
	@Override
	public void setSource(Map<String, String> source) {
		this.source = source;
	}

	@Override
	public String getElementTagName() {
		// return "ul";
		return null;
	}
	private Elements as = new Elements();
	public String getElementHtml() {
//		Element ele = getElement();
//		return ele.toString();
		return null;
	}
	
	@Override
	public String getId() {
		return source.get("id");
	}
    public String getPreviewId(){
    	return source.get("previewId");
    }
	@Override
	public String getName() {
		return source.get("name");
	}
	
	
	/**
	 * 这个是获取HTML样式定义
	 * @return
	 */
	public String getHtmlTemplate(){
		String path = createPath();
		
		String definedHtml = this.htmlDefined().replaceAll("\\\\\"", "\"") ;/*.replaceAll("<img src=\"", "<img src=\""+path)  ;*/
		definedHtml = this.regex(definedHtml, path);
		
		
		pushDomElement(definedHtml,as);
		
		return definedHtml.replaceAll("&amp;", "&") ;
	}
	

	/**
	 * 解析html定义获取所有的动态事件控件，push 所有到集合中
	 */
	public void pushDomElement(String hidLinkImg,Elements as){
		if(StringUtils.isNotEmpty(hidLinkImg)){
			Document doc = Jsoup.parse(hidLinkImg);
			as.addAll(doc.select("a[t-events=true]"));
		}
	}
	public String createPath(){
		String mode = this.getMode() ;
		String path = "\\${contextPath}";
		if(StringUtils.isEmpty(mode) || "common".equalsIgnoreCase(mode)){
			return "";
		}
		if("SOA".equalsIgnoreCase(mode)){
			String soaServer = this.source.get("SOAServer");
			if(soaServer != null && !soaServer.isEmpty()){
				JSONObject soaObj = JSON.parseArray(soaServer).getJSONObject(0);
				path = "http://"+soaObj.getString("host")+":"+soaObj.getString("port")+"/"+soaObj.getString("path");
			}
		}else if(!"General".equalsIgnoreCase(mode)){
			path = "\\${contextPath}/mx/form/imageUpload?method=download2&databaseId=#:databaseId#&mode="+mode+"&id="+this.getRuleId()+"&rp=" ;
		}else {
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
		return path;
	}
	/**
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
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
	private String regex(String str,String path){
		String regex = "((<img)(\\s+(\\w*=(\"|\')\\w*(\"|\'))*\\s*src=\"))" ; 
		String zoom = "";
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
	
	
	/**
	 * treelist 特有 递归主键
	 * 
	 * @return
	 */
	public String getColumnKey() {
		String tableSource = source.get("tableSource");	//获取物理表设置中的数据，若是选择物理表则优先。
		JSONObject json = new JSONObject();
		if(StringUtils.isNotEmpty(tableSource)){
			JSONArray tableSourceJSONArray = (JSONArray) JSON.parse(tableSource);
			if (tableSourceJSONArray != null && tableSourceJSONArray.size() > 0) {
				JSONObject tableSourceJSON = tableSourceJSONArray.getJSONObject(0);
				JSONArray columnData = tableSourceJSON.getJSONArray("columnData");
				for(Object column : columnData){
					JSONObject columnJSON = (JSONObject)column;
					if ("indexKey".equalsIgnoreCase(columnJSON.getString("columnType"))) {
						json.put("idKey", columnJSON.getString("ColumnName"));
					}
					if ("pIdKey".equalsIgnoreCase(columnJSON.getString("columnType"))) {
						json.put("parentKey", columnJSON.getString("ColumnName"));
					}
					if ("idKey".equalsIgnoreCase(columnJSON.getString("columnType"))) {
						json.put("treeKey", columnJSON.getString("ColumnName"));
					}
				}
				return json.toJSONString();
			}
		}
		
		
		String treelistColumns = source.get("columns");
		JSONArray treelistJSONArray = new JSONArray();
		String idkey = "index_id";
		if (StringUtils.isNotEmpty(treelistColumns)) {
			treelistJSONArray = JSON.parseArray(treelistColumns);
			for (Object object : treelistJSONArray) {
				JSONObject treelistJSONObject = (JSONObject) object;
				if ("indexKey".equalsIgnoreCase(treelistJSONObject.getString("columnType"))) {
					json.put("idKey", treelistJSONObject.getString("ColumnName"));
				}
				if ("pIdKey".equalsIgnoreCase(treelistJSONObject.getString("columnType"))) {
					json.put("parentKey", treelistJSONObject.getString("ColumnName"));
				}
				if ("idKey".equalsIgnoreCase(treelistJSONObject.getString("columnType"))) {
					json.put("treeKey", treelistJSONObject.getString("ColumnName"));
				}
			}
		}
		return json.toJSONString();
	}
	
	public String getVectorXField(){
	    return source.get("vectorXField");	
	}
	public String getVectorYField(){
		return source.get("vectorYField");	
	}
	public String getVectorEndField(){
		return source.get("vectorEndField");
	}
	public String getVectorStartField(){
		return source.get("vectorStartField");
	}
	public String getTakesPart(){
		return source.get("takesPart");
	}
	public String getVectorParam(){
		return source.get("vectorParam");
		
	}
}
