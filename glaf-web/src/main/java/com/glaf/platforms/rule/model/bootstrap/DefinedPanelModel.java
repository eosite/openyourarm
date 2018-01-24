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


public class DefinedPanelModel extends CommonModel {
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
	
	/**
	 * treelist 特有 递归主键
	 * 
	 * @return
	 */
	public String getIdKey() {
		String treelistColumns = source.get("columns");
		JSONArray treelistJSONArray = new JSONArray();
		String idkey = "index_id";
		if (StringUtils.isNotEmpty(treelistColumns)) {
			treelistJSONArray = JSON.parseArray(treelistColumns);
			for (Object object : treelistJSONArray) {
				JSONObject treelistJSONObject = (JSONObject) object;
				if ("indexKey".equalsIgnoreCase(treelistJSONObject.getString("columnType"))) {
					idkey = treelistJSONObject.getString("ColumnName");
				}
			}
		}
		return idkey;
	}
	
	protected JSONObject getSchemaJSON() {
		String treelistColumns = source.get("columns");
		JSONArray treelistJSONArray = new JSONArray();
		String pidkey = "parent_id";
		if (StringUtils.isNotEmpty(treelistColumns)) {
			treelistJSONArray = JSON.parseArray(treelistColumns);
			for (Object object : treelistJSONArray) {
				JSONObject treelistJSONObject = (JSONObject) object;
				if ("pIdKey".equalsIgnoreCase(treelistJSONObject.getString("columnType"))) {
					pidkey = treelistJSONObject.getString("ColumnName");
				}
			}
		}
		String dataSourceSet = source.get("dataSourceSet");
		JSONObject returnJson = new JSONObject();
		JSONObject jsonObj = null;
		if (StringUtils.isNotEmpty(dataSourceSet)) {
			JSONArray dataSourceSetJSONArray = JSON.parseArray(dataSourceSet);
			for (Object object : dataSourceSetJSONArray) {
				JSONObject dataSourceSetJSONObject = (JSONObject) object;
				JSONArray jsonArray = dataSourceSetJSONObject.getJSONArray("columns");
				if (jsonArray != null && jsonArray.size() > 0) {
					// 序号
					if (StringUtils.isNotEmpty(source.get("sortNo")) && "true".equalsIgnoreCase(source.get("sortNo"))) {
						JSONObject indexJson = new JSONObject();
						indexJson.put("editable", false);
						returnJson.put("startIndex", indexJson);
					}
					// 父节点必须的
					JSONObject parentJson = new JSONObject();
					parentJson.put("type", "number");
					parentJson.put("field", pidkey);
					parentJson.put("nullable", true);
					returnJson.put("parentId", parentJson);

					for (Object o : jsonArray) {
						jsonObj = (JSONObject) o;

						String fieldType = jsonObj.getString("FieldType");
						JSONObject jsonSub = new JSONObject();
						if ("datetime".equalsIgnoreCase(fieldType)) {
							jsonSub.put("type", "date");// 时间
						} else if ("i4".equalsIgnoreCase(fieldType) || "r8".equalsIgnoreCase(fieldType)
								|| "int".equalsIgnoreCase(fieldType)) {
							jsonSub.put("type", "number");// 数字
						} else if ("char".equalsIgnoreCase(fieldType)
								&& "1".equalsIgnoreCase(jsonObj.getString("FieldLength"))) {
							jsonSub.put("type", "boolean");// 布尔
							jsonSub.put("parse", "##=function(v){if(v==1){return true;}else {return false;}}=##");// 布尔类型转换

						} else {
							jsonSub.put("type", "string");
						}

						// 如果是虚拟字段 则根据选择类型判断
						if ("string".equalsIgnoreCase(jsonSub.getString("type"))) {
							String typeEidtor = jsonObj.getString("editor");
							if ("numerictextbox".equalsIgnoreCase(typeEidtor)) {
								jsonSub.put("type", "number");// 数字
							} else if ("checkbox".equalsIgnoreCase(typeEidtor)) {
								jsonSub.put("type", "boolean");// 布尔
								jsonSub.put("parse", "##=function(v){if(v==1){return true;}else {return false;}}=##");// 布尔类型转换
							} else if (typeEidtor.indexOf("picker") != -1) {
								jsonSub.put("type", "date");// 时间
							}
						}

						String required = jsonObj.getString("isRequired");// 是否必填
						JSONObject requiredObject = new JSONObject();
						requiredObject.put("required",
								StringUtils.isNotEmpty(required) ? Boolean.parseBoolean(required) : false);
						requiredObject.put("data-required-msg", jsonObj.getString("title") + " 为必填项 !");
						String validateMsg = jsonObj.getString("validateMsg");
						String dataPatternMsg = jsonObj.getString("title") + " 非法输入!";
						if (StringUtils.isNotEmpty(validateMsg)) {
							dataPatternMsg = validateMsg;
						}
						requiredObject.put("data-pattern-msg", dataPatternMsg);
						String editor = jsonObj.getString("editor");
						String dataValidation = jsonObj.getString("dataValidation");
						if ("MaskedTextBox".equalsIgnoreCase(editor) && !"textfield".equalsIgnoreCase(editor)
								&& StringUtils.isNotEmpty(dataValidation)
								&& !"range".equalsIgnoreCase(dataValidation)) { // 普通文本输入验证规则
							requiredObject.put("pattern", dataValidation); // 验证规则
																			// (正则匹配)
						}
						if ("NumericTextBox".equalsIgnoreCase(editor)) { // 数值输入验证
							requiredObject.put("min", jsonObj.getString("numberMinValue")); // 验证规则
																							// (最小值)
							requiredObject.put("max", jsonObj.getString("numberMaxValue")); // 验证规则
																							// (最大值)
						}

						jsonSub.put("validation", requiredObject);

						String editable = jsonObj.getString("isEditor");// 是否可编辑
						jsonSub.put("editable",
								StringUtils.isNotEmpty(editable) ? Boolean.parseBoolean(editable) : true);

						String defaultValue = jsonObj.getString("defaultValue");// 默认值
						if (StringUtils.isNotEmpty(defaultValue)) {
							jsonSub.put("defaultValue", "boolean".equalsIgnoreCase(jsonSub.getString("type"))
									? Boolean.parseBoolean(defaultValue) : defaultValue);
						}

						returnJson.put(jsonObj.get("columnName").toString(), jsonSub);
					}
				}
			}
		}

		if (returnJson == null || returnJson.size() == 0) {
			return null;
		}

		return returnJson;

	}
	
	public String getParentKey() {
		JSONObject fields = getSchemaJSON();
		String parentKey = "parentId";
		if (fields != null && fields.containsKey(parentKey)) {
			return fields.getJSONObject(parentKey).getString("field");
		}
		return null;
	}
	
	public String getTemplateScript() {
		/*StringBuffer sb = new StringBuffer();
		sb.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/definedPanel/definedPanel.extend.css'/>");
		sb.append("<script src='${contextPath}/scripts/plugins/bootstrap/definedPanel/ext/jquery.definedPanel.extend.js'></script>");
		scriptMap.put("definedpanel", sb.toString());*/
		return templateScript;
	}
	
	/**
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
	}
	
	/**
	 * 内容自定义
	 * @return
	 */
	public String getContentHtmlDefined(){
		String customDefined = source.get("contentHtmlDefined");
		return getCustomDefined(customDefined);
	}
	/**
	 * 标题栏自定义
	 * @return
	 */
	public String getTitleHtmlDefined(){
		String customDefined = source.get("titleHtmlDefined");
		return getCustomDefined(customDefined);
	}
	
	private String getCustomDefined(String customDefined){
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
		}else if("common".equalsIgnoreCase(mode)){
			//默认不修改
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
	
	public String getSysmenu(){
		return source.get("sysmenu");
	}
}
