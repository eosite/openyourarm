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

public class ReviewAreaModel extends CommonModel implements IRule, CssRule, AttrRule {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
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
	
	private String prefixStr = "reviewArea";
	private Elements as = new Elements();

	protected String templateScript = "";
	// 增加模板
	public String getTemplateScript() {
		//如果是通用的组件 加载的话可以所有都放在通用里面避免重复加载
//				String weuiScript = "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.min.css' />"
//						+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.css' />"
//						+ "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/jquery-weui-master/src/lib/weui.css' />"
//						+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/jquery-weui.min.js'></script>"
//						+ "<script type='text/javascript' src='${contextPath}/scripts/jquery-weui-master/src/lib/zepto.js'></script>";	
//				scriptMap.put("weui", weuiScript);
		
		String script =  "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/reviewArea/css/zyComment.css?v=1.8' />"
				+ "<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/reviewArea/js/jquery.reviewArea.extends.js?v=2.0'></script>";
			
		scriptMap.put("reviewArea", script);
		return templateScript;
		//return null;
	}
	
	public String getScrollload() {
		return source.get("scrollload");
	}
	public String getExpandauto() {
		return source.get("expandauto");
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	@Override
	public String getBind() {

		return null;
	}

	@Override
	public String getWidth() {
		return source.get("width");
	}

	public String getRuleId() {
		return source.get("ruleId");
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
//		Element ele = getElement();
//		return ele.toString();
		return null;
	}
	
	@Override
	public String getId() {
		return source.get("id");
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
	public String getImageFlag(){
		return source.get("imageFlag");
	}
	public String getPhoneDataSource(){
		if(true)
			return "";
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
				if("title".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					retJSONObject.put(a, o); //数值
				}else if("id".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					ja.add(o);
					retJSONObject.put(a,ja);
				}else if("parentid".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					ja.add(o);
					retJSONObject.put(a,ja);
				}else if("value".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					ja.add(o);
					retJSONObject.put(a,ja);
				}else if("datetime".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					ja.add(o);
					retJSONObject.put(a,ja);
				} else if("imgUrl".equalsIgnoreCase(a)){
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					retJSONObject.put(a, o); //数值
				}else if("imgName".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					ja.add(o);
					retJSONObject.put(a,ja);
				} else if("datasetId".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					ja.add(o);
					retJSONObject.put(a,ja);
				} else if("number".equalsIgnoreCase(a)){
					JSONArray ja = retJSONObject.getJSONArray(a);
					if(ja==null || ja.isEmpty()){
						ja = new JSONArray();
					}
					o = new JSONObject();
					o.put("cn", j.getString("title"));
					o.put("en", j.getString("columnLabel"));
					
					ja.add(o);
					retJSONObject.put(a,ja);
				}
				
			}
			
			retJSONArray.add(retJSONObject);
		}
		return retJSONArray.toString() ;
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
	
	/**
	 * treelist 特有 递归主键
	 * 
	 * @return
	 */
	public String getSchemaId() {
		String tableSource = source.get("tableSource");	//获取物理表设置中的数据，若是选择物理表则优先。
		if(StringUtils.isNotEmpty(tableSource)){
			JSONArray tableSourceJSONArray = (JSONArray) JSON.parse(tableSource);
			if (tableSourceJSONArray != null && tableSourceJSONArray.size() > 0) {
				JSONObject tableSourceJSON = tableSourceJSONArray.getJSONObject(0);
				JSONArray columnData = tableSourceJSON.getJSONArray("columnData");
				for(Object column : columnData){
					JSONObject columnJSON = (JSONObject)column;
					if(columnJSON.getString("columnType").equals("indexKey")){
						return columnJSON.getString("columnName");
					}
				}
				return "id";
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
					idkey = treelistJSONObject.getString("ColumnName");
				}
			}
		}
		return idkey;
	}

	public String getParentKey() {
		String tableSource = source.get("tableSource");	//获取物理表设置中的数据，若是选择物理表则优先。
		if(StringUtils.isNotEmpty(tableSource)){
			JSONArray tableSourceJSONArray = (JSONArray) JSON.parse(tableSource);
			if (tableSourceJSONArray != null && tableSourceJSONArray.size() > 0) {
				JSONObject tableSourceJSON = tableSourceJSONArray.getJSONObject(0);
				JSONArray columnData = tableSourceJSON.getJSONArray("columnData");
				for(Object column : columnData){
					JSONObject columnJSON = (JSONObject)column;
					if(columnJSON.getString("columnType").equals("pIdKey")){
						return columnJSON.getString("columnName");
					}
				}
				return "index_id";
			}
		}
		
		JSONObject fields = getSchemaJSON();
		String parentKey = "parentId";
		if (fields != null && fields.containsKey(parentKey)) {
			return fields.getJSONObject(parentKey).getString("field");
		}
		return null;
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
	
	public String getPageSize(){
		return source.get("pageSize");
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
	 * 模式
	 * @return
	 */
	public String getMode(){
		return source.get("mode");
	}

}
