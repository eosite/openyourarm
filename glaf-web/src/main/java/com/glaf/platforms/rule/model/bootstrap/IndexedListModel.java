package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.ButtonModel;
import com.glaf.platforms.rule.model.CommonModel;

public class IndexedListModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.indexedlist.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/mui/js/mui.indexedlist.js'></script>";
		scriptMap.put("mui", script);
		script =  "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/indexedlist/js/jquery.indexedlist.extends.js'></script>"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/indexedlist/js/convertCh.js'></script>";

		scriptMap.put("indexedlist", script);
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
	private JSONObject  findSourceByKey(String key,JSONArray selectDatasourceJSONArray){
		JSONObject selectDatasourceJSONObject = null ;
		for (Object object : selectDatasourceJSONArray) {
			selectDatasourceJSONObject = (JSONObject) object ;
			if(key.equalsIgnoreCase(selectDatasourceJSONObject.getString("id"))){
				return selectDatasourceJSONObject ;
			}
		}
		return null ;
	}
	/**
	 * 分组数据源
	 * @param datasourceColumnsJSONArray
	 * @return
	 */
	private Map<String, JSONArray> sortSource(JSONArray datasourceColumnsJSONArray){
		Map<String, JSONArray> hamap = new HashMap();
		for (Object object : datasourceColumnsJSONArray) {
			JSONObject columns = (JSONObject) object;
			String ctype = columns.getString("ctype");
			String cstack = columns.getString("chartStack");
			String seq = columns.getString("seq"); 
			if((ctype!=null && !"".equals(ctype)) || (cstack!=null && !"".equals(cstack) && "true".equals(cstack)) ){
				if(hamap.containsKey(seq)){
					JSONArray array = hamap.get(seq);
					array.add(columns);
				}else{
					JSONArray array = new JSONArray();
					array.add(columns);
					hamap.put(columns.getString("seq"),array);
				}
			}
		}
		return hamap ;
	}
	/**
	 * 数据源
	 * @return 
	 */
	
	public String getIndexDataSource(){
		String dataSourceSet = source.get("dataSourceSet");
		JSONArray datasourceSetJSONArray = JSON.parseArray(dataSourceSet);
		JSONArray selectDatasourceJSONArray = null ;
		JSONArray datasourceColumnsJSONArray = null;
		if (datasourceSetJSONArray != null && datasourceSetJSONArray.size() > 0) {
			JSONObject datasourceSetJSONObject = (JSONObject) datasourceSetJSONArray.get(0);
			selectDatasourceJSONArray = datasourceSetJSONObject.getJSONArray("selectDatasource") ;
			datasourceColumnsJSONArray = datasourceSetJSONObject.getJSONArray("selectColumns");
		}
		JSONArray retJSONArray = new JSONArray();
		//根据seq分组
		Map<String, JSONArray> hamap = this.sortSource(datasourceColumnsJSONArray);	
		Set<String> keys = hamap.keySet();
		JSONObject o = null ;
		JSONObject dataset = null ;
		for (String key : keys) {
			JSONArray jjj = hamap.get(key);
			JSONObject retJSONObject = new JSONObject();
			dataset = findSourceByKey(key, selectDatasourceJSONArray);		
			for (Object object : jjj) {
				JSONObject j = (JSONObject) object;
				String a = j.getString("ctype");
				if("indexContent".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					retJSONObject.put(a, o); //数值
				}
				
			}
			
			retJSONArray.add(retJSONObject);
		}
		return retJSONArray.toString() ;
	}
	private boolean notEmpty(String str){
		if(str!=null && !"".equals(str.trim())){
			return true ;
		}
		return false ;
	}
	private void setOBJ(JSONObject zhouObj,JSONObject retObj,String key,String getVlaue,String type){
		if("string".equalsIgnoreCase(type)){
			String str = zhouObj.getString(getVlaue) ;
			if(notEmpty(str)){
				retObj.put(key ,str);
			}
		}else if("number".equalsIgnoreCase(type)){
			Double str = zhouObj.getDouble(getVlaue) ;
			if(str!=null){
				retObj.put(key ,str);
			}
		}
	}
	public String getdexColumn(){
		return source.get("indexColumn");
	}
	public String getfinedColumn(){
		String column = source.get("definedColumn");
		JSONArray columnArray = JSON.parseArray(column);
		JSONArray cArray  = null ;
		JSONObject cObj  = null ;
		JSONArray retArray = new JSONArray();
		JSONObject retObj  = null ;
		if(columnArray!=null && !columnArray.isEmpty()){
			cArray = columnArray.getJSONObject(0).getJSONArray("values");
			if(cArray!=null && !cArray.isEmpty()){
				for (Object object : cArray) {
					cObj =  (JSONObject) object ;
					retObj = new JSONObject();
					
					setOBJ(cObj, retObj, "indexColumn", "indexColumn","string");
					retArray.add(retObj);
				}
				return retArray.toJSONString() ;
			}
		}
		
		return retArray.toJSONString() ;
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
}
