package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.expression.core.util.ExpressionConvertUtil;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.KendoEnum;
import com.glaf.platforms.rule.model.CommonModel;


public class GridListModel extends CommonModel {
	private String prefixStr = "gridlist";
	private Elements as = new Elements();
	
	//获取主键id名称
	public String getSchemaId() {
		String dataSourceSet = source.get("dataSourceSet");// 获取数据源配置
		if (StringUtils.isNotEmpty(dataSourceSet)) {
			JSONArray dataSourceSetArray = JSON.parseArray(dataSourceSet);
			if (!dataSourceSetArray.isEmpty()) {
				JSONObject dataSourceSetObj = dataSourceSetArray.getJSONObject(0);
				JSONArray tablesArray = dataSourceSetObj.getJSONArray("tables");
				if(tablesArray!=null && !tablesArray.isEmpty()){
					return tablesArray.getString(0) + "_0_id";
				}
			}
		}
		return "id";
	}
	
	public String getAblecheckbox(){
		return this.source.getOrDefault("ablecheckbox", "false");
	}
	
	public String getDraggable(){
		return this.source.getOrDefault("draggable","false");
	}
	
	public String getDroppable(){
		return this.source.getOrDefault("droppable","false");
	}
	
	/**
	 * 行个数
	 * 
	 * @return
	 */
	public String getRows() {
		return this.getSource("rows", "1");
	}
	
	/**
	 * 列个数
	 * 
	 * @return
	 */
	public String getRowColumns() {
		return this.getSource("rowColumns", "4");
	}
	
	/**
	 * 距离大小
	 * 
	 * @return
	 */
	public String getDistance() {
		return this.getSource("distance", "10");
	}
	
	/**
	 * 距离颜色
	 * 
	 * @return
	 */
	public String getDistanceColor() {
		return this.getSource("distanceColor", "#ccc");
	}
	
	/**
	 * 距离悬浮颜色
	 * 
	 * @return
	 */
	public String getHoverDistanceColor() {
		return this.getSource("hoverDistanceColor", "#ccc");
	}
	
	/**
	 * 获取数据
	 * @return
	 */
	public String gridlistdatas(){
		return null;
	}
	
	/**
	 * 获取自适应
	 * @return
	 */
	public String getAdapt(){
		return super.getSource("adapt", "false");
	}
	public String getDelayTime(){
		String str = super.getSource("delayTime","700");
		return str;
	}
	/**
	 * 获取是否显示空格
	 * @return
	 */
	public String getIsbank(){
		return super.getSource("isbank", "false");
	}
	
	public String getScale(){
		return super.getSource("scale", "16/9");
	}
	
	public String getSlideType(){
		return super.getSource("slideType","leftRight");
	}
	/**
	 * 是否使用分页
	 * @return
	 */
	public String getPaging(){
		return super.getSource("pageable", "false");
	}
	
	/**
	 * 多选
	 * 
	 * @return
	 */
	public String getSelectable() {
		return source.get("selectable");
	}
	
	/**
	 * 是否使用服务器分页
	 * @return
	 */
	public String getServerPaging(){
		return super.getSource("serverPaging", "true");
	}
	
	/**
	 * 是否允许图片放大
	 * @return
	 */
	public String getIsOpenImg(){
		return super.getSource("isOpenImg", "true");
	}
	
	public String getElementHtml() {
		
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml); 
		Element ele = doc.getElementById(this.getId());
		Elements styles = ele.getElementsByTag("style").clone();
		ele = ele.empty();
		if(!ele.attr("style").isEmpty()){
			ele.attr("style","");
		}
		ele.append(styles.outerHtml());
		return ele.toString();
	}
	
	public String getElementTagName() {
		return null;
	}
	
	public String getTemplateScript() {
		StringBuffer sb = new StringBuffer();
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/gridList/css/gridList.extends.css'/>");
//		图片弹窗
//		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/fancybox/source/jquery.fancybox.css?v=2.1.5' media='screen'/>");
		
		String droppable = this.source.getOrDefault("droppable","false");
		String draggable = this.source.getOrDefault("draggable","false");
		if(droppable.equals("true") || draggable.equals("true")){
			sb.append("<script src=\"${contextPath}/scripts/jquery-ui-1.9.2.min.js\"></script>");
		}
		
//		sb.append("<script src='${contextPath}/scripts/fancybox/source/jquery.fancybox.pack.js?v=2.1.5'></script>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/gridList/ext/jquery.gridList.extends.js'></script>");
		scriptMap.put("gridlist", sb.toString());
		return templateScript;
	}
	
	/**
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
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
	
	/**
	 * 这个是获取HTML样式定义
	 * @return
	 */
	public String getHtmlTemplate(){
		String path = createPath();
		
		String definedHtml = this.htmlDefined().replaceAll("\\\\\"", "\"") ;/*.replaceAll("<img src=\"", "<img src=\""+path)  ;*/
		definedHtml = this.regex(definedHtml, path);
		
		
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
	
	/**
	 * 放大显示
	 * @return
	 */
	public String getZoomView(){
		return source.get("zoomView");
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
	
	public String getButtonCount(){
		return this.source.getOrDefault("buttonCount", "3");
	}
	
	public String getPagePanelState(){
		String pagePanelState = this.source.get("pagePanelState");
		if(pagePanelState == null || pagePanelState.isEmpty()){
			pagePanelState = "onehide";
		}
		return pagePanelState;
	}
}
