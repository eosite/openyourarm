package com.glaf.platforms.rule.model;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.model.bootstrap.GridBtModel;
import com.google.gson.JsonArray;

public class TreeListBtModel extends GridBtModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Map<String, String> scriptMap;
	

	@Override
	public String getBind() {
		return super.getBind();
	}
	
	public String getIsSync() {
		return source.getOrDefault("isSync", null);
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

	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;

		StringBuffer SB = new StringBuffer();
		if("kendo".equals(formPage.getUiType())){
			SB.append(
					"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/bootstrap/css/bootstrap.min.css'>");
			SB.append(
					"<script src='${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js' type='text/javascript'></script>");
			SB.append(
					"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>");
			SB.append(
					"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/treelist/css/treelist.css'>");
			SB.append(
					"<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>");
			SB.append(
					"<script src='${contextPath}/scripts/plugins/bootstrap/treelist/js/jquery.treelist.extends.js' type='text/javascript'></script>");
		}
		scriptMap.put("jquery.treelist.extends.js", SB.toString());
	}
	
	public Element getElement() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		return ele;
	}
	
	//table-style="default"
	public String getTabStyle(){
		Element ele = getElement();
		return ele.attr("table-style");
	}

	public String getElementTagName() {
		return null;
	}
	
	public String getElementHtml() {
		String formHtml = formPage.getFormHtml();
		Document doc = Jsoup.parse(formHtml);
		Element ele = doc.getElementById(this.getId());
		Elements styles = ele.getElementsByTag("style").clone();
		ele.empty();
		ele.append(styles.outerHtml());
//		ele.removeAttr("contenteditable");
		if (!this.isVisible()) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		return ele.toString();
	}
	
	/**
	 * 显示控件
	 * 
	 * @return
	 */
	public boolean getDeleteChildren() {
		return Boolean.valueOf(source.get("deleteChildren"));
	}
	
	
	/* (non-Javadoc)
	 * @see com.glaf.platforms.rule.model.bootstrap.GridBtModel#getDefaultTimePicker()
	 * 默认日期
	 */
	public String getDefaultTimePicker(){
		return source.get("defaultTimePicker");
		
	}
	/**
	 * 用于显示数据格式 需要覆盖
	 * 
	 * @return
	 */
	public String getSchemaFields() {
		String tableSource = source.get("tableSource");	//获取物理表设置中的数据，若是选择物理表则优先。
		if(StringUtils.isNotEmpty(tableSource)){
			JSONArray tableSourceJSONArray = (JSONArray) JSON.parse(tableSource);
			if (tableSourceJSONArray != null && tableSourceJSONArray.size() > 0) {
				return "";
			}
		}
		

		JSONObject returnJson = getSchemaJSON();

		return returnJson == null ? null : returnJson.toJSONString().replaceAll("\"", "\'");
	}
	
	public String getExpandChilds(){
		return source.getOrDefault("expandChilds", "0");
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
	 * 内容不折行
	 * @return
	 */
	public String getShowInLine(){
		String showInLine = source.get("showInLine");
		return showInLine != null && "false".equalsIgnoreCase(showInLine) ? "false" : "true";
	}
	
	/**
	 * 获取输入形参规则中的对象规则
	 * @return
	 */
	public String getInParamObjRule(){
		String inPamranDefinedStr = this.source.get("inParamDefined");
		JSONObject ret = new JSONObject();
		if(StringUtils.isNotEmpty(inPamranDefinedStr)){
			JSONArray inPamranDefinedArray = JSON.parseArray(inPamranDefinedStr);
			if(inPamranDefinedArray!= null && !inPamranDefinedArray.isEmpty()){
				JSONObject obj = inPamranDefinedArray.getJSONObject(0);
				ret.put("objSource", obj.getJSONArray("objSource"));
				JSONArray arySource = obj.getJSONArray("arySource");
				if(arySource!= null && !arySource.isEmpty()){
					for(Object aryObj : arySource){
						JSONObject aryJson = (JSONObject)aryObj;
						JSONArray aryChildrenArray = aryJson.getJSONArray("child");
						for(Object aryChildObj : aryChildrenArray){
							JSONObject aryChildJson = (JSONObject)aryChildObj;
							aryChildJson.remove("child");
						}
						aryJson.put("child", aryChildrenArray);
					}
					ret.put("arySource", arySource);
				}
				return ret.toJSONString();
			}
		}
		return "[]";
	}
	
	/**
	 * 
	*    
	* 项目名称：glaf-web   
	* 类描述：   
	* 创建人：Administrator   
	* 创建时间：2017年5月31日 下午1:53:00   
	* 修改人：Administrator   
	* 修改时间：2017年5月31日 下午1:53:00   
	* 修改备注：   
	* @version    
	*
	 */
	public String getColorDefined(){
		JSONObject Jso=new JSONObject();
		
	    String defineColor = source.get("colorDefine");
	    JSONArray parseArray = JSON.parseArray(defineColor);
	    if(null!=parseArray){
	    	 JSONObject object = (JSONObject) parseArray.get(0);
	    	 if(null!=object){
			     JSONArray object2 = (JSONArray) object.get("value");
			     for(int i=0;i<object2.size();i++){
			    	JSONObject object3 = (JSONObject) object2.get(i);
			    	JSONObject rejson=new JSONObject();
			    	Object object4 = object3.get("expression");
			    	Object object5 = object3.get("color");
			    	rejson.put("exp",object4);
			    	rejson.put("color", object5);
			    	Jso.put(""+i, rejson);
			     }
	    	 }
	    }
		return Jso.toString();
	}
	public String getEditColor(){
		return source.get("editColor");
	}
	public String getEndColor(){
		return source.get("endColor");
	}
	public String getPurpleColor(){
		return source.get("purpleColor");
	}
	public String getCancelColor(){
		return source.get("cancelColor");
	}
	public String getTextAlign(){
		return source.get("textAlign");
	}
}
