package com.glaf.platforms.rule.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;

public class TreeListModel extends GridModel {

	private static final long serialVersionUID = 1L;

	/**
	 * 重写此方法 与grid 统计列 需要 aggregate : [ { 'field' : 'cell_useradd7_user3',
	 * 'aggregate' : 'min' } ]支持 footerTemplate: 'Min: #:
	 * data.cell_useradd7_user3 ? data.cell_useradd7_user3.min : 0 #' <--- grid
	 * footerTemplate: 'Min: #: data.min #' <--- treelist
	 */
	protected String buildColunmDataStat(String dataColunmStat, JSONObject jsonObj, JSONObject datasetColumn) {
		List<FormDictory> dictorys = FormDictoryFactory.getInstance().getFormDictoryListByTreeCode("function");
		Map<String, FormDictory> dictorysMap = new HashMap<>();
		for (FormDictory dictory : dictorys) {
			dictorysMap.put(dictory.getCode(), dictory);
		}
		String name = this.getId() + "tstat" + jsonObj.get("columnName");
		String key = "{0:#.00}";
		String format = datasetColumn.getString("formatValue");
		if (format != null && !"".equals(format)) {
			key = "{0:" + format + "}";
		}
		String[] dataColunmStats = dataColunmStat.split(",");
		StringBuffer sb = new StringBuffer();
		sb.append("<script type='text/javascript'>function " + name + "(data){ return ");
		int count = 0;
		for (String string : dataColunmStats) {
			if (count > 0) {
				sb.append(" + ");
			}
			sb.append("' " + dictorysMap.get(string).getName() + ":'+(data").append(" ? kendo.format(")
					.append("'" + key + "'").append(",data.").append(string).append("): 0 ) ");
			count++;
			// sb.append(dictorysMap.get(string).getName()).append(": #:
			// data").append(" ? data.").append(string).append(" : 0 # ");
		}
		sb.append(";}</script>");
		sb.toString();
		this.addTemplate(sb.toString());
		return "##=" + name + "=##";

	}

	// treelist 要覆盖此方法。treelist 不能这样构建外部定义
	protected void buildFilterable(JSONObject datasetColumn, JSONObject retJson, String editor,
			String widgetSourceType) {
		String isFilterable = datasetColumn.getString("isFilterable");
		if (!"true".equalsIgnoreCase(isFilterable)) {
			retJson.put("filterable", false);
		} else if ("true".equalsIgnoreCase(isFilterable)) {
			if (Boolean.parseBoolean(source.get("filterable"))) { // 只有在开启过滤的时候才设置
				if (StringUtils.isNotEmpty(editor)) { // 显示过滤器中的编辑器类型
					JSONObject uiJson = new JSONObject();
					if (StringUtils.isNotEmpty(widgetSourceType)) {
					} else {
						// uiJson.put("ui",
						// "##= function(e){e.kendoDateTimePicker({min : new
						// Date(2015,05,26,03,00,00),max : new
						// Date(2015,05,27,02,00,00)});} =##");
						uiJson.put("ui", editor);
						retJson.put("filterable", uiJson);
					}
				}
			}
		}
	}

	/**
	 * 用于显示数据格式 需要覆盖
	 * 
	 * @return
	 */
	public String getSchemaFields() {

		JSONObject returnJson = getSchemaJSON();

		return returnJson == null ? null : returnJson.toJSONString().replaceAll("\"", "\'");
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

	/**
	 * 查询后自动展开事件
	 */
	public String getOnDataBound() {
		// if (source.get("checkboxPage") != null &&
		// "true".equalsIgnoreCase(source.get("checkboxPage"))) {
		return "glafGrid.treelistExpand";
		// }
		// return null;
	}

	/**
	 * 绑定数据源 、 绑定事件等
	 */
	@Override
	public String getBind() {
		// 绑定数据源
		StringBuilder sb = new StringBuilder("visible: isVisible,source:products,");

		// 绑定事件
		StringBuilder eventSb = new StringBuilder("");
		if (source.get("click") != null && !"".equals(source.get("click"))) {
			String isDbl = source.get("isDbl");
			if (isDbl == null || !"true".equalsIgnoreCase(isDbl)) {
				eventSb.append("change:onChange");
			}
		}
		// if (source.get("checkboxPage") != null &&
		// "true".equals(source.get("checkboxPage"))) {
		if (eventSb != null && eventSb.length() > 0) {
			eventSb.append(",");
		}
		eventSb.append("dataBound:treeListOnDataBound");
		// }
		if (eventSb != null && eventSb.length() > 0) {
			sb.append("events:{");
			sb.append(eventSb.toString());
			sb.append("}");
		}

		return sb.toString();
	}
	
	/**
	 * 查询展开
	 * @return
	 */
	public String getExpand(){
		return source.get("isExpand")==null?"true":source.get("isExpand").toString();
	}
}
