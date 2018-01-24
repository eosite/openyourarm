package com.glaf.platforms.rule.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.glaf.core.util.UUID32;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;

public class DefinedTableModel extends CommonModel implements IRule, CssRule,
		AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String prefixStr = "definedTable";
	private Elements as = new Elements();

	public String getElementTagName() {
		return null;
	}
	
	public String getScrollload(){
		return this.source.get("scrollload");
	}

	public String getdownRefresh(){
		return this.source.get("downRefresh");
	}
	public String getTemplateScript() {

		// scriptMap.put("bootstrap-style",
		// "<link rel='stylesheet' type='text/css' href='${contextPath }/scripts/bootstrap/css/bootstrap.min.css'>");
		if("kendo".equalsIgnoreCase(formPage.getUiType())){
			scriptMap
			.put("combine",
					"<script src='${contextPath}/webfile/js/combine.js' type='text/javascript'></script>");
		}

		scriptMap.put("mui", "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/mui/css/mui.min.css' />"
				+ "<script  type='text/javascript' src='${contextPath}/scripts/mui/js/mui.min.js' />");
		return "";
	}

	@Override
	public String getElementHtml() {

		Element ele = new Element(Tag.valueOf("div"), "");

		ele.attr("id", super.getId());

		ele.attr("data-role", "definedTable");
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;");
		}
		
		ele.addClass(getLitterPage());
		ele.addClass(getHiddenTotal());
		ele.addClass(getHiddenSize());
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml); 
		Element originEle = doc.getElementById(this.getId());
		Elements styles = originEle.getElementsByTag("style").clone();
		ele.append(styles.outerHtml());
		String str = ele.toString();

		return str;
	}

	public String getTemplate() {

		String e = getHTMLDefined();

//		e = ExpressionConvertUtil.expressionConvert(e,
//				ExpressionConvertUtil.DATABASE_TYPE);

		Document doc = Jsoup.parse(e);
		this.addHrefRole(doc);
		Elements eles = doc.select(".list");
		JSONObject job = new JSONObject();
		populateData(job);
		String html = doc.body().html();
		if (eles != null) {
			Element list = eles.first();
			if (list != null) {
				String listTemplate = list.toString();
				String split = UUID32.getUUID(), template;
				list.after(split);
				Element td;
				Elements tds = list.select(".td-list");
				if (tds.size() > 0) {
					td = tds.get(0);
					td.html("{0}");
					template = td.outerHtml();
					job.put("tdBodyTemplate", template);
				}
				eles.remove();
				tds = doc.body().select(".td-list");
				if (tds.size() > 0) {
					td = tds.get(0);
					td.html("{0}");
					template = td.outerHtml();
					job.put("tdHeaderTemplate", template);
					td.after("{0}");
					tds.remove();
				}
				html = doc.body().html();
				String[] strs = html.split(split);
				if (strs.length > 0) {
					String header = strs[0];
					job.put("header", header);
					if (strs.length > 1) {
						String footer = strs[1];
						job.put("footer", footer);
					}
				}
				job.put("listTemplate", listTemplate);
			}
		}
		job.put("htmldata", html);

		this.initColumnDefinetion(job);

		String str = job.toJSONString();

		return str;
	}

	private void populateData(JSONObject job) {
		String[] keys = new String[] { "combine", "colspan", "rowspan",
				"spanLevel","pageable", "serverPaging", "pageSize" };
		for (String key : keys)
			job.put(key, source.get(key));
	}

	/**
	 * 动态生成按钮
	 */
	private void addHrefRole(Document doc) {

		String html = formPage.getFormHtml();
		if (html != null) {

			Document fromHtml = Jsoup.parse(html);

			this.deleteHrefRole(fromHtml);

			Elements as = doc.select("a[t-events=true]");
			/**
			 * 
			 */
			if (as != null && as.size() > 0) {
				Element div = new Element(Tag.valueOf("div"), ""), button;
				div.attr("id", "div-" + this.getRuleId());
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
					div.appendChild(button);
				}
				fromHtml.body().appendChild(div);
				formPage.setFormHtml(fromHtml.html());
			}
		}
	}

	/**
	 * 删除自动生成的按钮
	 */
	public void deleteHrefRole(Document doc) {
		Elements eles = doc.select("#div-" + this.getRuleId());
		if (eles != null) {
			eles.remove();
		}
	}
	
	/**
	 * 删除自动生成的按钮
	 */
	public void deleteHrefRole2(Document doc) {
		Elements eles = doc.select("#"+prefixStr +this.getId() +"-"+ this.getRuleId());
		if (eles != null) {
			eles.remove();
		}
		eles = doc.select("#"+prefixStr +"-"+ this.getRuleId());
		if (eles != null) {
			eles.remove();
		}
	}

	/**
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
	}
	
	public String getHTMLDefined() {
		String mode = this.getMode() ;
		String path = "\\${contextPath}";
		if(!"General".equalsIgnoreCase(mode)){
			path = "\\${contextPath}/mx/form/imageUpload?method=download2&databaseId=~F{databaseId}&mode="+mode+"&id="+this.getRuleId()+"&rp=" ;
		}else{
			String ftpFieldStr = source.get("FTPField");
			if(StringUtils.isNotEmpty(ftpFieldStr)){
				JSONArray fields = JSON.parseArray(ftpFieldStr);
				JSONObject fieldObj = fields.getJSONObject(0);
				JSONObject datasObj = fieldObj.getJSONObject("datas");
				String idField = datasObj.getString("idField");
				String ftpField = datasObj.getString("ftpField");
				if(StringUtils.isNotEmpty(idField) && StringUtils.isNotEmpty(ftpField)){
					path = "\\${contextPath}/mx/form/imageUpload?method=download2&databaseId=~F{databaseId}&mode="+mode+"&id="+this.getRuleId()+"&rp=" ;
				}
			}
		}
		
		String HTMLDefinedStr = super.getSource("HTMLDefined", "");
		if (!isNullOrEmpty(HTMLDefinedStr)) {
			JSONArray defineds = JSONArray.parseArray(HTMLDefinedStr);
			JSONObject j = defineds.getJSONObject(0);
			String htmlVal = j.getJSONObject("htmldata").getString("htmlVal");
			HTMLDefinedStr = htmlVal;
//			return htmlVal;
		}
		
		HTMLDefinedStr = this.regex(HTMLDefinedStr, path);
		
		return HTMLDefinedStr.replaceAll("&amp;", "&") ;
		
	}
	
	private String regex(String str,String path){
		String regex = "((<img)(\\s+(\\w*=(\"|\')\\w*(\"|\'))*\\s*src=\"))" ; 
		String zoomClass = "" ;
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(str);
		StringBuffer sb = new StringBuffer(); //替换后的字符串
		while(matcher.find()) {   
			matcher.appendReplacement(sb, matcher.group(2)+" "+zoomClass+" "+matcher.group(3)+path); 
		}  
		matcher.appendTail(sb);
		return sb.toString();
	}

	private void initColumnDefinetion(JSONObject j) {
		String key = "dataSourceSet";
		String dataSourceSetStr = super.getSource(key, "");
		if (!StringUtils.isBlank(dataSourceSetStr)) {
			JSONArray jArray = JSONArray.parseArray(dataSourceSetStr);
			if (jArray != null && jArray.size() > 0) {
				JSONObject jObject = jArray.getJSONObject(0);
				jArray = jObject.getJSONArray("columns");
				String columnName = "";
				List<String> latitudes = new ArrayList<String>();
				if (jArray != null && jArray.size() > 0) {
					for (int i = 0; i < jArray.size(); i++) {
						jObject = jArray.getJSONObject(i);
						columnName = jObject.getString("columnName");
						if (jObject.containsKey("latitude")
								&& jObject.getBooleanValue("latitude")) {
							latitudes.add(columnName);
						} else if (jObject.containsKey("dynamic")
								&& jObject.getBooleanValue("dynamic")) {
							j.put("dynamicColumn", columnName);
							j.put("dynamic", true);
						} else {
							j.put("valueKey", columnName);
						}
					}
					if (latitudes.size() > 0)
						j.put("latitudes", latitudes);
				}
			}
		}
	}

	@Override
	public String getBind() {
		return null;
	}
	
	
	public String getLitterPage(){
		String value = source.getOrDefault("litterPage", "false");
		if(value != null && value.equalsIgnoreCase("true")){
			return "mt_little_page";
		}
		return "";
	}
	public String getHiddenTotal(){
		String value = source.getOrDefault("hiddenTotal", "false");
		if(value != null && value.equalsIgnoreCase("true")){
			return "mt_hidden_page_size";
		}
		return "";
	}
	public String getHiddenSize(){
		String value = source.getOrDefault("hiddenSize", "false");
		if(value != null && value.equalsIgnoreCase("true")){
			return "mt_hidden_page_total";
		}
		return "";
	}

	public String createPath(){
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
					htmlExpression = this.regex(htmlExpression, path);
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
	 * 动态生成按钮
	 */
	public void addHrefRole(Elements as) {
		String html = formPage.getFormHtml();
		if (html != null) {
			Document fromHtml = Jsoup.parse(html);
			this.deleteHrefRole2(fromHtml);
			/**
			 * 
			 */
			if (as != null && as.size() > 0) {
				Element div = new Element(Tag.valueOf("div"), ""), button;
				div.attr("id", prefixStr +"-"+ this.getRuleId());
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
}
