package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

import bsh.This;


public class MetrolistModel extends CommonModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Map<String, String> scriptMap;

	public String getBind() {
		return null;
	}
	

	public String getElementTagName() {
		return null;
	}
	
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		if ("false".equals(this.isVisible())) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		if("false".equals(this.isTitle())){
			ele.getElementsByClass("mt-list-head").remove();
		}
		return ele;
	}
	
	public String getElementHtml() {
		Element ele = getElement();
		return ele.toString();
	}
	
	public String isPageable() {
		return this.getSource("pageable", "false");
	}
	/**
	 * 页显示条数
	 * 
	 * @return
	 */
	public String getPageSize() {
		String pageSize = null;
		if (source.get("pageable") != null && "true".equals(source.get("pageable"))) {
			pageSize = source.get("pageSize");
			if (StringUtils.isEmpty(pageSize)) {
				pageSize = "10";
			}
		}
		return pageSize;
	}
	
	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
		StringBuffer SB = new StringBuffer();
		SB.append("<link rel='stylesheet' href='${contextPath}/scripts/plugins/bootstrap/list/css/list.extends.css'/>");
		SB.append("<script src=\"${contextPath}/scripts/jquery-ui-1.9.2.min.js\"></script>");
		SB.append("<script src=\"${contextPath}/scripts/plugins/bootstrap/list/jquery.list.extends.js\"></script>");

		this.scriptMap.put("metrolist", SB.toString());
	}
	
	
	

	public String isTitle() {
		return this.getSource("isTitle", "true");
	}
	
	// ***************  标题    ***************
	/**
	 * 标题文本
	 * @return
	 */
	public String getMainTitle(){
		//"<p>我的</p>"
		String text =  source.get("mainTitle") ;
		String retAry = "" ;
		if(StringUtils.isNotEmpty(text) && !"".equals(text.trim())){
			try {
				JSONArray array = JSON.parseArray(text);
				if(array!=null && !array.isEmpty()){
					JSONObject obj = array.getJSONObject(0);
					JSONObject subObj = obj.getJSONObject("htmldata") ;
					retAry = subObj.getString("htmlVal").replace("\"", "'");
				}
			} catch (Exception e) {
				retAry = text; 
			}
		}
		if(!StringUtils.isNotEmpty(retAry)){
			retAry = "" ;
		}
		return retAry;
	}
	/**
	 * 标题对齐方式
	 * @return
	 */
	public String getAlign(){
		return source.get("align");
	}
	
	
	//获取数据集字段名称
	public String getColumnsMap(){
		JSONObject coordInfo = new JSONObject() ;
		String dataset = source.get("columns");
		if(!StringUtils.isEmpty(dataset)){
			JSONArray columns = JSON.parseArray(dataset);
			if(columns!=null && !columns.isEmpty()){
				JSONObject colunm = null ;
				String ctype = null ;
				for (Object object : columns) {
					colunm = (JSONObject) object ;
					ctype = colunm.getString("columnType") ;
					if(!StringUtils.isEmpty(ctype)){
						coordInfo.put(ctype, colunm.getString("ColumnName"));
					}
				}
			}
		}
		return coordInfo.toJSONString();
	}
	
	/**
	 * 列
	 * 
	 * @return
	 */
	public String getColumns() {
	
		// 返回参数
		JSONArray retArray = new JSONArray();
		JSONObject jsonObj =  new JSONObject();
		
		// 模板引擎转换方法
		StringBuffer expScript = new StringBuffer("<script type='text/javascript'>");
		StringBuffer headTemp = new StringBuffer("<script type='text/javascript'>");

		// 封装数据源配置 列信息
		Map<String, JSONObject> dataSourceSetColumnMap = new HashMap<>();
		String dataSourceSet = source.get("dataSourceSet");// 获取数据源配置
		if (StringUtils.isNotEmpty(dataSourceSet)) {
			JSONArray dataSourceSetJSONArray = (JSONArray) JSON.parse(dataSourceSet);
			if (dataSourceSetJSONArray != null && dataSourceSetJSONArray.size() > 0) {
				for (Object object : dataSourceSetJSONArray) {
					JSONObject dataSourceSetJSONObject = (JSONObject) object;
					JSONArray dataSourceSetColumnsJSONArray = dataSourceSetJSONObject.getJSONArray("columns");
					if (dataSourceSetColumnsJSONArray != null && dataSourceSetColumnsJSONArray.size() > 0) {
						for (Object object2 : dataSourceSetColumnsJSONArray) {
							JSONObject dataSourceSetColumnJSONObject = (JSONObject) object2;
							
							// push 自定义结构 
							pushDomElement(dataSourceSetColumnJSONObject.getString("hidLinkImg"));
							
							buildColumnTemplate(dataSourceSetColumnJSONObject, retArray, expScript, headTemp);
							
							dataSourceSetColumnMap.put(dataSourceSetColumnJSONObject.getString("columnName"), dataSourceSetColumnJSONObject);
						}
					}
				}
			
				// 自定义按钮事件定义
				addHrefRole();
			}
		}


		// 数据列事件
		String columnsEvent = source.get("columnsEvent");
		if (StringUtils.isNotEmpty(columnsEvent)) {
			JSONArray columnsEventJsonArray = JSON.parseArray(columnsEvent);
			if (columnsEventJsonArray.size() > 0) {
				JSONArray array = new JSONArray();
				JSONObject obj;
				for (Object object : columnsEventJsonArray) {
					JSONObject columnsEventJSONObject = (JSONObject) object;
					obj = new JSONObject();
					obj.put("name", columnsEventJSONObject.getString("name"));
					obj.put("text", columnsEventJSONObject.getString("text"));
					array.add(obj);
				}
				jsonObj = new JSONObject();
				jsonObj.put("command", array);
				jsonObj.put("width", StringUtils.isNotEmpty(source.get("columnsEventWidth")) ? Integer.parseInt(source.get("columnsEventWidth")) : 120);
				retArray.add(jsonObj);
			}
		}



		expScript.append("</script>");
		this.addTemplate(expScript.toString().replaceAll("row_0_", ""));

		headTemp.append("</script>");
		this.addTemplate(headTemp.toString().replaceAll("row_0_", ""));


		return retArray.toJSONString().replaceAll("\"", "\'");
	}
	
	
	
	protected String buildColumnTemplate(JSONObject jsonObj, JSONArray retArray, StringBuffer expScript, StringBuffer headTemp) {

		JSONObject retJson = new JSONObject();


		retJson.put("columnId", jsonObj.get("id"));
		retJson.put("title", jsonObj.get("title"));
		retJson.put("field", jsonObj.get("columnName"));
	
		boolean flag = true;
		if (jsonObj != null) {
			// String prefixTableName =
			// datasetColumn.getString("tableName")+"_0_";
			// 编辑 start
			String editor = jsonObj.getString("editor");
			retJson.put("editor", editerBuild(jsonObj));
			
//			String widgetSourceType = jsonObj.getString("widgetSourceType");
//			if (StringUtils.isNotEmpty(widgetSourceType)) { // 控件数据源类型
//				String widgetSource = jsonObj.getString("widgetSource");
//				switch (widgetSourceType) {
//				case "10": // 字典数据
//					List<Dictory> dictory = BaseDataManager.getInstance().getDictoryList(widgetSource);
//					if (("char".equalsIgnoreCase(jsonObj.getString("FieldType")) && "1".equalsIgnoreCase(jsonObj.getString("FieldLength"))) || "checkbox".equalsIgnoreCase(jsonObj.getString("editor"))) {
//						retJson.put("editor", editerBuild(jsonObj));
//					} else {
//						retJson.put("editor", editerBuild(jsonObj));
//					}
//					break;
//				case "20": // 数据表
//					retJson.put("editor", editerBuild(jsonObj));
//					break;
//				case "30": // 用户自定义
//					JSONArray widgetSourceArray = JSON.parseArray(widgetSource);
//					if (("char".equalsIgnoreCase(jsonObj.getString("FieldType")) && "1".equalsIgnoreCase(jsonObj.getString("FieldLength"))) || "checkbox".equalsIgnoreCase(jsonObj.getString("editor"))) {
//						retJson.put("editor", editerBuild(jsonObj));
//					} else {
//						retJson.put("editor", editerBuild(jsonObj));
//					}
//					break;
//				default:
//					break;
//				}
//
//			} else { // 如果未选择数据源，则直接根据editor类型生成对应的规则 需要排除布尔类型和普通文本输入类型
//				retJson.put("editor", editerBuild(jsonObj));
//			}
			// 编辑 end


			// 样式 alignment attributes: {"class": "table-cell",style:
			// "text-align: right; font-size: 14px"}
			String alignment = jsonObj.getString("alignment");
			if (StringUtils.isNotEmpty(alignment) && !"left".equalsIgnoreCase(alignment)) { // 默认为left
																							// 故不添加
				JSONObject styleJson = new JSONObject();
				styleJson.put("style", "text-align:" + alignment + ";");
				retJson.put("attributes", styleJson);
			}

			// isSortable 是否排序 默认true
			String isSortable = jsonObj.getString("isSortable");
			if (!"true".equalsIgnoreCase(isSortable)) {
				retJson.put("sortable", false);
			}
			// isSortable 是否排序 默认true
			String isEditor = jsonObj.getString("isEditor");
			if (!"false".equalsIgnoreCase(isEditor)) {
				retJson.put("isEditor", true);
			}
			// isMenu 是否可隐藏 默认true
			String isMenu = jsonObj.getString("isMenu");
			if (!"true".equalsIgnoreCase(isMenu)) {
				retJson.put("menu", false);
			}


			String formatValue = jsonObj.getString("formatValue");//显示格式
			if (StringUtils.isNotEmpty(formatValue)) { 
				retJson.put("format",formatValue);
			}

			// 处理模板编辑
			String templateScript = buildExpression(jsonObj, retJson.getString("template"), expScript);
			if (StringUtils.isNotEmpty(templateScript)) {
				retJson.put("template", "##=" + templateScript + "=##");
			}

			String columnWidth = jsonObj.getString("columnWidth");
			try{
				int columnWidthNum = Integer.parseInt(columnWidth);
				retJson.put("width", StringUtils.isNotEmpty(columnWidth) ? columnWidthNum : 120);
			}catch(Exception e){
				retJson.put("width", StringUtils.isNotEmpty(columnWidth) ? columnWidth : 120);
			}
//			retJson.put("width", StringUtils.isNotEmpty(columnWidth) ? Integer.parseInt(columnWidth) : 120);
//			retJson.put("width", StringUtils.isNotEmpty(columnWidth) ? Integer.parseInt(columnWidth) : 120);

			String isShowList = jsonObj.getString("isShowList"); // 隐藏显示
			if (!"true".equalsIgnoreCase(isShowList)) {
				// retJson.put("hidden", true);
				flag = false;
			}
		}
		if (!flag)
			return null;
		retArray.add(retJson);
		return null;
	}
	
	/**
	 * list中的链接图片等 样式转换
	 * @param datasetColumn
	 * @param templateName
	 * @param expScript
	 * @return
	 */
	protected String buildExpression(JSONObject datasetColumn, String templateName, StringBuffer expScript) {

		// 显示样式模板
		String hidLinkImg = datasetColumn.getString("hidLinkImg");
		JSONArray hidLinkImgJSONArray = JSON.parseArray(hidLinkImg);
		if (hidLinkImgJSONArray != null && hidLinkImgJSONArray.size() > 0) {
			JSONObject hidLinkImgJSONObject = null;
			String expressionString = "";
			String htmlExpressionString = "";
			expScript.append(" function " + this.getId() + datasetColumn.getString("columnName") + "Func(dataItem){");
			for (Object object : hidLinkImgJSONArray) {
				hidLinkImgJSONObject = (JSONObject) object;
				expressionString = hidLinkImgJSONObject.getString("expression"); // 表达式编辑器
				String tableNStr = datasetColumn.getString("tableName")==null?"":(datasetColumn.getString("tableName")+ "_0_") ;
				String es = ExpressionConvertUtil.expressionConvert(expressionString, ExpressionConvertUtil.JAVASCRIPT_TYPE, "dataItem." + tableNStr);
				expScript.append("if(" + es + "){");

				htmlExpressionString = hidLinkImgJSONObject.getString("htmlExpression");// html编辑器
				String hs = HTMLExpressionConvertUtil.htmlConvert(htmlExpressionString, templateName).replaceAll("\t", "").replaceAll("\n", "");
				expScript.append("return \"" + hs + "\" ;");
				expScript.append("}");
			}
			expScript.append(" return '' ; }");
			templateName = this.getId() + datasetColumn.getString("columnName") + "Func";
		}
		return templateName;
	}
	
	
	/**
	 * editor编辑器构建
	 * @param jsonObj
	 * @return
	 */
	protected String editerBuild(JSONObject jsonObj) {
		JSONObject obj = new JSONObject();
		// 必填
		if (StringUtils.isNotEmpty(jsonObj.getString("isRequired")) && "true".equalsIgnoreCase(jsonObj.getString("isRequired"))) {
			obj.put("required",true);
			obj.put("data-required-msg", jsonObj.getString("title") + " 为必填项");
		}
		// 正则验证(范围不算)
		if (StringUtils.isNotEmpty(jsonObj.getString("dataValidation")) && !"range".equalsIgnoreCase(jsonObj.getString("dataValidation"))) {
			obj.put("pattern", jsonObj.getString("dataValidation"));
			obj.put("validateMsg", jsonObj.getString("validateMsg"));
		}
		// min(最要针对日期)
		String minValue = "";
		if (StringUtils.isNotEmpty(jsonObj.getString("dateMinValue"))) {
			String min = jsonObj.getString("dateMinValue");
			if ("DateTimePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 日期时间类型
				// 2015-05-26 03:00:00
				String[] mins = min.split(" ");
				String[] date = mins[0].split("-");
				String[] time = mins[1].split(":");
				minValue = "new Date(" + date[0] + "," + date[1] + "," + date[2] + "," + time[0] + "," + time[1] + "," + time[2] + ")";
			} else if ("DatePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 日期类型
				String[] date = min.split("-");
				minValue = "new Date(" + date[0] + "," + date[1] + "," + date[2] + ")";
			} else if ("TimePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 时间类型
				String[] mins = min.split(" ");
				String[] date = mins[0].split("-");
				String[] time = mins[1].split(":");
				minValue = "new Date(" + date[0] + "," + date[1] + "," + date[2] + "," + time[0] + "," + time[1] + "," + time[2] + ")";
			}
		}
		// max(最要针对日期)
		String maxValue = "";
		if (StringUtils.isNotEmpty(jsonObj.getString("dateMaxValue"))) {
			String max = jsonObj.getString("dateMaxValue");
			if ("DateTimePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 日期时间类型
				// 2015-05-26 03:00:00
				String[] maxs = max.split(" ");
				String[] date = maxs[0].split("-");
				String[] time = maxs[1].split(":");
				maxValue = "new Date(" + date[0] + "," + date[1] + "," + date[2] + "," + time[0] + "," + time[1] + "," + time[2] + ")";
			} else if ("DatePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 日期类型
				String[] date = max.split("-");
				maxValue = "new Date(" + date[0] + "," + date[1] + "," + date[2] + ")";
			} else if ("TimePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 时间类型
				String[] maxs = max.split(" ");
				String[] date = maxs[0].split("-");
				String[] time = maxs[1].split(":");
				maxValue = "new Date(" + date[0] + "," + date[1] + "," + date[2] + "," + time[0] + "," + time[1] + "," + time[2] + ")";
			}
		}

		obj.put("component", jsonObj.getString("editor"));
		datasourceStr(jsonObj,obj);
		
		String role = KendoEnum.getClassName(jsonObj.getString("editor")) == null ? "kendoMaskedTextBox" : KendoEnum.getClassName(jsonObj.getString("editor"));
		if ("kendoDateTimePicker".equalsIgnoreCase(role)) {
			obj.put("format", "yyyy-MM-dd HH:mm:ss");
		} else if ("kendoDatePicker".equalsIgnoreCase(role)) {
			obj.put("format", "yyyy-MM-dd");
		} else if ("kendoTimePicker".equalsIgnoreCase(role)) {
			obj.put("format", "HH:mm:ss");
		}

		if (StringUtils.isNotEmpty(minValue)) {
			obj.put("min", minValue);
		}
		if (StringUtils.isNotEmpty(maxValue)) {
			obj.put("max", maxValue);
		}

		return "##="+obj.toJSONString()+"=##";
	}
	
	/**
	 * 控件 数据源
	 * 
	 * @param jsonObj
	 * @return
	 */
	protected void datasourceStr(JSONObject jsonObj,JSONObject obj) {

		String widgetSourceType = jsonObj.getString("widgetSourceType");
		String widgetSource = jsonObj.getString("widgetSource");
		if ("20".equalsIgnoreCase(widgetSourceType)) { // 如果为数据源
																														// 并且是autocomplate
			JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
			JSONObject params = new JSONObject();
			params.put("dataSetId",  widgetSourceJson.getString("dataSetId")); 
			params.put("databaseId",  widgetSourceJson.getString("databaseId")); 
			params.put("text",  widgetSourceJson.getString("widgetSourceTableText")); 
			params.put("value",  widgetSourceJson.getString("widgetSourceTableValue"));
			obj.put("url", "contextPath/mx/form/data/widgetSource"); 
			obj.put("params", params);	
			
		} else if ("10".equalsIgnoreCase(widgetSourceType)) {// 如果是字典数据
			List<Dictory> dictory = BaseDataManager.getInstance().getDictoryList(widgetSource);
			JSONArray ja = new JSONArray();
			JSONObject jo = null;
			for (int i=0;i<dictory.size();i++) {
				Dictory d = dictory.get(i);
				jo = new JSONObject();
				jo.put("id", d.getCode());
				jo.put("text", d.getName());
				jo.put("value", d.getCode());
				ja.add(jo);
			}
			obj.put("dataSource", ja); 
		} else if ("30".equalsIgnoreCase(widgetSourceType)) {// 自定义数据
			JSONArray widgetSourceArray = JSON.parseArray(widgetSource);
			for (int i=0;i<widgetSourceArray.size();i++) {
				JSONObject jobj = (JSONObject) widgetSourceArray.get(i);
				jobj.put("id", jobj.get("value"));
			}
			obj.put("dataSource", widgetSourceArray);
		}
	
	}
	
	
	
	
	
	
	private String prefixStr = "metrolist";
	
	private Elements as = new Elements();
	
	/**
	 * push 所有到集合中
	 */
	public void pushDomElement(String hidLinkImg){
		if(StringUtils.isNotEmpty(hidLinkImg)){
			JSONArray hids = JSON.parseArray(hidLinkImg);
			if(hids!=null && !hids.isEmpty()){
				JSONObject hid ;
				String e ;
				for (Object object : hids) {
					hid = (JSONObject) object ;
					e = hid.getString("htmlExpression");
					if(StringUtils.isNotEmpty(e)){
						Document doc = Jsoup.parse(e);
						as.addAll(doc.select("a[t-events=true]"));
					}
				}
			}
		}
	}
	
	/**
	 * 动态生成按钮
	 */
	public void addHrefRole() {
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
		Elements eles = doc.select("#" + prefixStr+this.getId()+"-" + this.getRuleId());
		if (eles != null) {
			eles.remove();
		}
	}
	
	
	public String getDraggable(){
		return this.source.getOrDefault("draggable","false");
	}
	
	public String getDroppable(){
		return this.source.getOrDefault("droppable","false");
	}
	
	
//	public String getOptions() {
//		JSONObject json = new JSONObject();
//		String keys[] = { "mainTitle", "otherTitle", "id"};
//		for (String key : keys) {
//			json.put(key, source.get(key));
//		}
//		return json.toJSONString().replaceAll("\"", "\'");
//	}
//	
//	public String getDataType() {
//		//Element el = getElement();
//		//return el.hasClass("__treelist__")?"treelist":"data";
//		return "treelist";
//	}

}
