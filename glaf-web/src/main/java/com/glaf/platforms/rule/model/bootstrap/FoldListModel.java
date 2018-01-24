package com.glaf.platforms.rule.model.bootstrap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class FoldListModel extends CommonModel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String prefixStr = "foldlist";
	private Elements as = new Elements();

	protected String templateScript = "";

	// 增加模板
	public String getTemplateScript() {
		String script = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/foldlist/ext/foldlist.extend.css' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/foldlist/ext/jquery.foldlist.extend.js'></script>";

		scriptMap.put("foldlist", script);
		
		scriptMap.put("mui", "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<script  type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js' />");
		return templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

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

	public String getElementHtml() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml); 
		Element ele = doc.getElementById(this.getId());
		Elements styles = ele.getElementsByTag("style").clone();
		
		ele.empty().append(styles.outerHtml());
		
		return ele.outerHtml();
	}

	@Override
	public String getId() {
		return source.get("id");
	}

	@Override
	public String getName() {
		return source.get("name");
	}

	@Override
	public String getValue() {

		return null;
	}

	@Override
	public boolean isReadable() {
		return false;
	}

	@Override
	public boolean isRequired() {
		return false;
	}

	@Override
	public boolean isWritable() {
		return false;
	}

	@Override
	public String getRuleId() {
		return source.get("ruleId");
	}


	/**
	 * 是否异步
	 * 
	 * @return
	 */
	public String getIsSync() {
		String isSync = source.get("isSync");
		return isSync == null ? "false" : isSync;
	}
	
	public String getStyleRule() {
		String dataLinkPage = source.get("dataLinkPage");
		StringBuffer styleSb = new StringBuffer();
		JSONObject retObj = new JSONObject();
		if (!isNullOrEmpty(dataLinkPage)) {
			JSONArray dataLinkPageAry = JSONArray.parseArray(dataLinkPage);
			if (!dataLinkPageAry.isEmpty()) {
				JSONObject dataLinkPageObj = dataLinkPageAry.getJSONObject(0);
				JSONObject valuesObj = dataLinkPageObj.getJSONObject("values");
				
				//开关选中
				JSONArray checkAry = valuesObj.getJSONArray("check");
				if (!checkAry.isEmpty()) {
					JSONObject checkObj;
					JSONObject check ;
					List<JSONObject> list = new ArrayList<>();
					String type;
					for (Object object : checkAry) {
						checkObj = (JSONObject) object;
						check = new JSONObject();
						this.convertObj(checkObj, check,"type");
						String expdata = checkObj.getString("expdata");
						if(!isNullOrEmpty(expdata)){
							JSONObject expdataObj = JSON.parseObject(expdata);
							String es = HTMLExpressionConvertUtil.htmlConvertForZtree(expdataObj.getString("expActVal"));
							check.put("expression", es);
						}
						list.add(check);
					}
					JSONArray jo = (JSONArray) JSON.toJSON(list);
					retObj.put("check", jo);
				}
			}
		}
		return retObj.toJSONString();
	}
	
	/**
	 * 拷贝属性
	 * @param fromObj
	 * @param toObj
	 * @param arys
	 */
	private void convertObj(JSONObject fromObj,JSONObject toObj,String... arys){
		if(arys.length>0){
			for (String key : arys) {
				toObj.put(key, fromObj.get(key));
			}
		}
	}
	
	/**
	 * 获取值
	 * 
	 * @param columnType
	 * @return
	 */
	private String columnsValue(String columnType) {
		String columns = source.get("columns");
		JSONArray columnsArray = JSON.parseArray(columns);
		JSONObject jo = null,values = null;
		String str = null;
		if(null!=columnsArray){
			values = columnsArray.getJSONObject(0);
			columnsArray = values.getJSONArray("values");
			for (Object object : columnsArray) {
				jo = (JSONObject) object;
				if (columnType.equalsIgnoreCase(jo.getString("columnType"))) {
					str = jo.getString("ColumnName");
				}
		  }
		}
		return str;
	}
	
	public String getPageSize(){
		return source.get("pageSize");
	}
	
	/**
	 * 是否使用分页
	 * @return
	 */
	public String getPaging(){
		return super.getSource("pageable", "false");
	}
	
	/**
	 * 是否使用服务器分页
	 * @return
	 */
	public String getServerPaging(){
		return super.getSource("serverPaging", "true");
	}
	
	/**
	 * 简单模式 显示名称
	 * 
	 * @return
	 */
	public String getNameKey() {
		return columnsValue("nameKey");
	}
	
	/**
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
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
	
	public String getCustomDefined(){
		String customDefined = source.get("customDefined");
		String path = createPath();
		JSONArray htmlAry = null;
		Map<String,String> map = this.getDataSource();
		if(!isNullOrEmpty(customDefined)){
			htmlAry = JSON.parseArray(customDefined);
			if(htmlAry!=null && !htmlAry.isEmpty()){
				JSONObject htmlObj = htmlAry.getJSONObject(0);
				JSONArray htmldataObjArray = htmlObj.getJSONArray("value");
				
				for(Object htmldataObj : htmldataObjArray){
					JSONObject htmldataJsonObj = (JSONObject)htmldataObj;
					String htmlExpression = htmldataJsonObj.getString("htmlExpression");
					htmlExpression = HTMLExpressionConvertUtil.kendoTemplateConvert(htmlExpression,map);
					
					String expression = htmldataJsonObj.getString("expression");
					expression = HTMLExpressionConvertUtil.kendoTemplateConvert(expression,map);
					
					htmlExpression = htmlExpression.replaceAll("\\\\\"", "\"") ;/*.replaceAll("<img src=\"", "<img src=\""+path)  ;*/
					if(StringUtils.isNotEmpty(path)){
						htmlExpression = this.regex(htmlExpression, path);
					}
					htmlExpression = htmlExpression.replaceAll("&amp;", "&") ;
					
					pushDomElement(htmlExpression,as);
					addHrefRole(as);
					
					htmldataJsonObj.put("expression", expression);
					htmldataJsonObj.put("htmlExpression", htmlExpression);
				}
				
			}
		}
		if(htmlAry != null){
			return htmlAry.toJSONString();
		}
		return "[]";
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
	
	/**
	 * 删除自动生成的按钮
	 */
	public void deleteHrefRole(Document doc) {
		Elements eles = doc.select("#" + prefixStr+this.getId()+"-" + this.getRuleId());
		if (eles != null) {
			eles.remove();
		}
	}
	
	/**
	 * 动态生成按钮
	 */
	public void addHrefRole(Elements as) {
		String html = formPage.getFormHtml();
		if (html != null) {
			Document fromHtml = Jsoup.parse(html);
			this.deleteHrefRole(fromHtml);
			/**
			 * 
			 */
			if (as != null && as.size() > 0) {
				Element div = new Element(Tag.valueOf("div"), ""), button;
				div.attr("id", prefixStr +this.getId()+"-"+ this.getRuleId());
				div.attr("style", "display:none;");
				for (Element a : as) {
					a.attr("href", "javascript:void(0);");
					button = new Element(Tag.valueOf("button"), "");
					// button.attr("class", "k-button");
					button.text(a.attr("title"));
					button.attr("id", a.attr("btn-id"));
					button.attr("t-events", "true");
					button.attr("data-role", "button");
					button.attr("crtltype", "kendo");
					button.attr("title", a.attr("title"));
					button.attr("cname", a.text());
					div.appendChild(button);
				}
				fromHtml.body().appendChild(div);
				formPage.setFormHtml(fromHtml.html());
			}
		}
	}
	
	public String getScrollload() {
		return source.get("scrollload");
	}
}
