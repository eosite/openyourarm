package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
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
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class DefinedCardModel extends CommonModel implements IRule, CssRule, AttrRule {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String prefixStr = "definedCard";

	@Override
	public String getBind() {
		return null;
	}

	@Override
	public String getElementTagName() {
		return null;
	}
	
	@Override
	public String getElementHtml() {
		// TODO Auto-generated method stub
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		ele.addClass("definedCardExt");
		ele.getElementsByClass("definedCardExt_list").empty();
		ele.getElementsByClass("definedCardExt_header").empty();
//		Elements els = ele.getElementsByClass("definedCardExt_list");
		
//		ele.removeAttr("src");
//		String html = ele.outerHtml();
//		//将img转换为div
//		html = html.replace("<img", "<div");
//		html += "</div>";
				
		return ele.toString();
	}
	
	public String getTemplateScript() {
		/*StringBuffer sb = new StringBuffer();
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/definedCard/css/definedCard.css'/>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/definedCard/ext/jquery.definedCard.extend.js'></script>");
		scriptMap.put("definedcard", sb.toString());*/
		return templateScript;
	}
	
	/**
	 * 获取标题样式
	 * @return
	 */
	public String getMainTitle(){
		
		String HTMLDefinedStr = super.getSource("mainTitle", "");
		if (!isNullOrEmpty(HTMLDefinedStr)) {
			JSONArray defineds = JSONArray.parseArray(HTMLDefinedStr);
			JSONObject j = defineds.getJSONObject(0);
			String htmlVal = j.getJSONObject("htmldata").getString("htmlVal");
			HTMLDefinedStr = htmlVal;
		}
		
		return HTMLDefinedStr ;
	}
	
	public String isTitle() {
		return this.getSource("isTitle", "true");
	}
	
	/**
	 * 获取单页条数
	 * @return
	 */
	public String getPageSize(){
		return super.getSource("pageSize", "5") ;
	}
	
	/**
	 * 获取是否分页
	 * @return
	 */
	public String getPagingServer(){
		return super.getSource("serverPaging", "true") ;
	}
	
	/**
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
	}
	
	/**
	 * 这个是获取HTML样式定义
	 * @return
	 */
	public String getHtmlTemplate(){
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
		
		Elements as = new Elements();
		pushDomElement(definedHtml,as);
		addHrefRole(as);
		
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
	
	/**
	 * 放大显示
	 * @return
	 */
	public String getZoomView(){
		return source.get("zoomView");
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
	
	
	public String getRailColor(){
		return source.getOrDefault("railColor", "#333");
	}
	
	public String getSlimscrollcolor(){
		return source.getOrDefault("slimscrollcolor", "#333");
	}
	
	public String getSlimscrollrailsize(){
		return source.getOrDefault("slimscrollrailsize", "10");
	}
	
	public String getSlimScrollWheelStep(){
		return source.getOrDefault("slimScrollWheelStep", "10");
	}
	
	public String getShowTip(){
		return source.getOrDefault("showTip", "false");
	}
	
	public String getShowMoreBtn(){
		return source.getOrDefault("showMoreBtn", "false");
	}
}
