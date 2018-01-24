package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Attribute;
import org.jsoup.nodes.Attributes;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Tag;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IRule;
import com.glaf.platforms.rule.model.CommonModel;

public class DiagramBtModel extends CommonModel implements IRule, CssRule, AttrRule {

	private static final long serialVersionUID = 1L;
	
	private String prefixStr = "diagrambt";
	private Elements as = new Elements();

	@Override
	public String getBind() {
		return null;
	}

	@Override
	public String getRuleId() {
		return source.get("ruleId");
	}
	
	public String getElementTagName() {
		return null;
	}
	//获取第一层
	@Override
	public String getElementHtml() {
		String rid = getRuleId();
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
//		ele.removeAttr("contenteditable");
		ele.html("");
		Element div = new Element(Tag.valueOf("div"), "");
		if("img".equals(ele.tagName())){
			Attributes attrs= ele.attributes();
			Iterator<Attribute> it = attrs.iterator();
			while(it.hasNext()){
				Attribute attr= it.next();
				if(!"src".equals(attr.getKey())){
					div.attr(attr.getKey(),attr.getValue());
				}			
			}
		}else{
			div=ele;
		}
		if ("false".equals(this.isVisible())) {
			div.attr("style", "display:none;"+ele.attr("style"));
		}
		return div.toString();
	}

	//展示类型？
	public String getLayoutType() {
		return this.getStringValue("layoutType", "tree");
	}
	//展示方向
	public String getSubType() {
		return this.getStringValue("subType", "t2b");
	}
	//显示层级数
	public String getDepth() {
		return this.getStringValue("depth", "");
	}
	
	//形状类型
	public String getShapeType() {
		return this.getStringValue("shapeType", "rectangle");
	}
	//形状宽度
	public int getShapeWidth() {
		return this.getIntValue("shapeWidth", 100);
	}
	//形状高度
	public int getShapeHeight() {
		return this.getIntValue("shapeHeight", 50);
	}
	//形状颜色
	public String getShapeColor() {
		return this.getStringValue("shapeColor", "");
	}
	//是否显示内容
	public boolean getShowContent() {
		return this.getBooleanValue("showContent", false);
	}
	//内容高度
	public int getContentHeight() {
		return this.getIntValue("contentHeight", 50);
	}
	//是否拖放
	public boolean getDrag() {
		return this.getBooleanValue("drag", false);
	}
	
	//是否显示图片
	public boolean getShowImage() {
		return this.getBooleanValue("showImage", true);
	}
	
	//图层可拖动
	public boolean getPan() {
		return this.getBooleanValue("pan", false);
	}
	//图层可缩放
	public boolean getZoom() {
		return this.getBooleanValue("zoom", false);
	}
	


	public String getClickFunction() {
		String linkPage = source.get("linkPage"); // 联动页面
		JSONArray linkPages = JSON.parseArray(linkPage);
		JSONObject params = new JSONObject();
		if (linkPages != null && !linkPages.isEmpty()) {
			String linkstr = linkPages.getJSONObject(0).toJSONString();
			params.put("link", linkstr);
		}

		String jumpType = source.get("jumpType"); // 跳转类型
		params.put("jumpType", jumpType);
		String title = source.get("windowTitle"); // 窗口名称
		params.put("title", title);
		String model = source.get("windowModal"); // 是否为模态窗口
		params.put("model", model);
		String width = source.get("windowWidth"); // 窗口宽度
		params.put("width", width);
		String height = source.get("windowHeight");// 窗口高度
		params.put("height", height);

		String paraType = source.get("paraType"); // 规则
		JSONArray paraTypes = JSON.parseArray(paraType);
		String rules = null;
		if (paraTypes != null && !paraTypes.isEmpty()) {
			rules = paraTypes.getJSONObject(0).getJSONObject("datas").toJSONString();
		}

		String clickType = source.get("click");// 事件类型

		StringBuffer sb = new StringBuffer();
		sb.append("function(e){");
		sb.append("pubsub.pub('" + clickType + "'," + rules + "," + params.toJSONString() + ",e.item.dataItem)");
		sb.append("}");

		return sb.toString();
	}
	
	
	
	

	private String getStringValue(String key, String defaultValue) {
		return source.get(key) == null ? defaultValue : source.get(key);
	}

	private boolean getBooleanValue(String key, boolean defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Boolean.parseBoolean(source.get(key));
		}
	}

	private int getIntValue(String key, Integer defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Integer.parseInt(source.get(key));
		}
	}

	private double getDoubleValue(String key, Double defaultValue) {
		if (source.get(key) == null) {
			return defaultValue;
		} else {
			return Double.parseDouble(source.get(key));
		}
	}
	
	
	//增加模板
	public String getTemplateScript(){
		String  script = 
			 "<link type='text/css' rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/diagrambt/css/jquery.orgchart.css' />"
			+"<link type='text/css' rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/diagrambt/css/orgchart.extends.css' />"
		    +"<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/diagrambt/js/jquery.orgchart.js'></script>";
			
		scriptMap.put("diagrambt", script);
		return templateScript;
	}
	
	public String getViewState(){
		return source.getOrDefault("viewState", "false");
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
		return path;
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
	
	public Map<String,String> getDataSource(){
		Map<String,String> map = new HashMap<>();
		String dataSourceSet = source.get("dataSourceSet");
		if(!isNullOrEmpty(dataSourceSet)){
			JSONArray dsAry = JSON.parseArray(dataSourceSet);
			if(dsAry!=null && !dsAry.isEmpty()){
				JSONObject jo = dsAry.getJSONObject(0);
				JSONArray columns = jo.getJSONArray("columns");
				if(columns == null || columns.isEmpty()){
					columns = jo.getJSONArray("selectColumns");
				}
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
}
