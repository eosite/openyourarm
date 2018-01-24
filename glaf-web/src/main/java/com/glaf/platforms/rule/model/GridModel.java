package com.glaf.platforms.rule.model;

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
import com.glaf.form.core.domain.FormComponent;
import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.util.FormDictoryFactory;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.KendoEnum;
import com.glaf.platforms.rule.interfaces.AttrRule;
import com.glaf.platforms.rule.interfaces.CssRule;
import com.glaf.platforms.rule.interfaces.IGridRule;

public class GridModel implements IGridRule, CssRule, AttrRule {

	private static final long serialVersionUID = 1L;
	protected Map<String, String> source;
	protected String templateScript = "";

	@Override
	public void setSource(Map<String, String> source) {
		this.source = source;
	}

	@Override
	public String getElementTagName() {
		return "div";
	}

	@Override
	public String getRuleId() {
		return source.get("ruleId");
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
		return source.get("value");
	}

	@Override
	public boolean isReadable() {
		return Boolean.getBoolean(source.get("readable"));
	}

	@Override
	public boolean isRequired() {
		return Boolean.getBoolean(source.get("required"));
	}

	@Override
	public boolean isWritable() {
		return Boolean.getBoolean(source.get("writable"));
	}

	/**
	 * 显示控件
	 * 
	 * @return
	 */
	public boolean isVisible() {
		return Boolean.valueOf(source.get("visible"));
	}

	@Override
	public String getWidth() {
		return source.get("width");
	}

	@Override
	public String getHeight() {
		return source.get("height");
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
		if (source.get("checkboxPage") != null && "true".equals(source.get("checkboxPage"))) {
			if (eventSb != null && eventSb.length() > 0) {
				eventSb.append(",");
			}
			eventSb.append("dataBound:onDataBound");
		}
		if (eventSb != null && eventSb.length() > 0) {
			sb.append("events:{");
			sb.append(eventSb.toString());
			sb.append("}");
		}

		return sb.toString();
	}

	/**
	 * grid中的链接图片等 样式转换
	 * 
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
				if(!"".equals(tableNStr) && datasetColumn.getString("columnName").indexOf("_0_") != -1 ){
					tableNStr = datasetColumn.getString("columnName").split("_0_")[0] + "_0_" ;
				}
				String es = ExpressionConvertUtil.expressionConvert2(expressionString, ExpressionConvertUtil.JAVASCRIPT_TYPE, "dataItem." + tableNStr);
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
	 * treelist 需要覆盖此方法 统计列 需要 aggregate : [ { 'field' : 'cell_useradd7_user3',
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
		String[] dataColunmStats = dataColunmStat.split(",");
		StringBuffer sb = new StringBuffer();
		// function(data){return "Min:" + data.cell_useradd7_user3 ?
		// data.cell_useradd7_user3.min : 0 }
		String name = this.getId() + "dstat" + jsonObj.get("columnName");
		String key = "{0:#.##}";
		String format = datasetColumn.getString("formatValue");
		if (format != null && !"".equals(format)) {
			key = "{0:" + format + "}";
		}
		sb.append("<script type='text/javascript'>function " + name + "(data){ return ");
		int count = 0;
		for (String string : dataColunmStats) {
			if (count > 0) {
				sb.append(" + ");
			}
			sb.append("' " + dictorysMap.get(string).getName() + ":'+(data.").append(jsonObj.get("columnName")).append(" ? kendo.format(").append("'" + key + "'").append(",data.")
					.append(jsonObj.get("columnName")).append(".").append(string).append("): 0) ");
			count++;
			/*
			 * sb.append(dictorysMap.get(string).getName()).append(": #: data.").
			 * append
			 * (jsonObj.get("columnName")).append(" ? data.").append(jsonObj
			 * .get("columnName"))
			 * .append(".").append(string).append(" : 0 # ");
			 */
		}
		sb.append(";}</script>");
		sb.toString();
		this.addTemplate(sb.toString().replaceAll("row_0_", ""));
		return "##=" + name + "=##";

	}

	protected String buildColumnTemplate(Map<String, JSONObject> dataSourceSetColumnMap, JSONObject jsonObj, JSONArray retArray, StringBuffer popScriptTemp,
			StringBuffer filterableScriptTemp, StringBuffer expScript, StringBuffer headTemp) {

		JSONObject retJson = new JSONObject();
		JSONObject datasetColumn = dataSourceSetColumnMap.get(jsonObj.getString("columnName"));

		retJson.put("id", "c"+jsonObj.get("id"));
		retJson.put("title", jsonObj.get("name"));
		retJson.put("field", jsonObj.get("columnName"));
		// locked: true,lockable:true, 冻结列
		String locked = jsonObj.getString("isLock");
		if (StringUtils.isNotEmpty(locked)) {
			retJson.put("locked", true);
			retJson.put("lockable", true);
		}
		// 表头样式
		String link = jsonObj.getString("link");
		if (StringUtils.isNotEmpty(link) && StringUtils.isNotEmpty(link.trim())) {
			String headTempName = this.getId() + (jsonObj.get("columnName") != null ? jsonObj.get("columnName") : "N" + jsonObj.get("id")) + "headTempFn";
			headTemp.append(" function " + headTempName + "(){");
			String hs = HTMLExpressionConvertUtil.htmlConvert(link, null).replaceAll("\t", "").replaceAll("\n", "");
			headTemp.append(" return \"" + hs + "\";");
			headTemp.append("} ");
			retJson.put("headerTemplate", "##=" + headTempName + "=##");
			// retJson.put("headerTemplate",
			// "<a href=\\'#\\' link=\\'"+link+"\\' onclick=\\'glafGrid.openLink(this,contextPath)\\' title=\\'"+jsonObj.getString("linkPageName")+"\\'>"+jsonObj.get("name")+"</a>");
		}
		// 隐藏
		String hidden = jsonObj.getString("isHidden");
		if (StringUtils.isNotEmpty(hidden)) {
			retJson.put("hidden", true);
		}

		// 统计列 需要 aggregate : [ { 'field' : 'cell_useradd7_user3', 'aggregate' :
		// 'min' } ]支持
		// footerTemplate: 'Min: #: data.cell_useradd7_user3 ?
		// data.cell_useradd7_user3.min : 0 #'
		String dataColunmStat = jsonObj.getString("dataColunmStat");
		if (StringUtils.isNotEmpty(dataColunmStat)) {
			retJson.put("footerTemplate", buildColunmDataStat(dataColunmStat, jsonObj, datasetColumn));
		}

		// 下级节点
		JSONArray children = jsonObj.getJSONArray("children");
		if (children != null && children.size() > 0) {
			JSONArray childJSONArray = new JSONArray();
			for (Object object : children) {
				JSONObject childrenJSONObject = (JSONObject) object;
				this.buildColumnTemplate(dataSourceSetColumnMap, childrenJSONObject, childJSONArray, popScriptTemp, filterableScriptTemp, expScript, headTemp);
			}
			retJson.put("columns", childJSONArray);
		}
		boolean flag = true;
		if (datasetColumn != null) {
			// String prefixTableName =
			// datasetColumn.getString("tableName")+"_0_";
			// 编辑 start
			String editor = datasetColumn.getString("editor");

			String widgetSourceType = datasetColumn.getString("widgetSourceType");
			if (StringUtils.isNotEmpty(widgetSourceType)) { // 控件数据源类型
				String widgetSource = datasetColumn.getString("widgetSource");
				switch (widgetSourceType) {
				case "10": // 字典数据
					List<Dictory> dictory = BaseDataManager.getInstance().getDictoryList(widgetSource);
					if (("char".equalsIgnoreCase(datasetColumn.getString("FieldType")) && "1".equalsIgnoreCase(datasetColumn.getString("FieldLength"))) || "checkbox".equalsIgnoreCase(datasetColumn.getString("editor"))) {
						// 假如为布尔值（特殊处理）
						String functionName = this.getId() + datasetColumn.getString("columnName");
						String template = "function " + functionName + "(dataItem){ return dataItem." + datasetColumn.get("columnName") + "== '"
								+ ("true".equals(dictory.get(0).getValue()) ? "1" : "0") + "' ? '" + dictory.get(0).getName() + "' : '" + dictory.get(1).getName() + "'}";
						expScript.append(template);
						retJson.put("template", functionName);
					} else {
						retJson.put("editor", editerBuild(datasetColumn));
						JSONArray ja = new JSONArray();
						JSONObject jo = null;
						for (Dictory d : dictory) {
							jo = new JSONObject();
							jo.put("text", d.getName());
							jo.put("value", d.getCode());
							ja.add(jo);
						}

						String functionName = this.getId() + datasetColumn.getString("columnName");
						String template = "function " + functionName + "(dataItem){ var values = " + ja.toJSONString() + " ; " + " for(var i=0;i<values.length;i++){"
								+ " var va = values[i] ;" + " if(va.value == dataItem." + datasetColumn.getString("columnName") + "){" + "  return va.text ;}" + "} return '' ;}";
						if ("multiselect".equalsIgnoreCase(datasetColumn.getString("editor"))) {
							template = " function " + functionName + "(dataItem) {  var values = " + ja.toJSONString() + " ;  " + " var ret = [] ,k = dataItem."
									+ datasetColumn.getString("columnName") + ".split(','); " + "for(var i=0;i<values.length;i++){ " + " var va = values[i] ;"
									+ "if(jQuery.inArray(va.value,k)!=-1){ " + "ret.push( va.text ) ;} } return ret.join(',') ; }";
						}
						expScript.append(template);
						retJson.put("template", functionName);
						// TODO 不能用啦
						// retJson.put("values", ja);
					}
					break;
				case "20": // 数据表
					retJson.put("editor", editerBuild(datasetColumn));
					if ("dropdownlist".equalsIgnoreCase(datasetColumn.getString("editor"))) {
						String v = this.getId() + datasetColumn.getString("columnName") + "data";
						String functionName = this.getId() + datasetColumn.getString("columnName");
						JSONObject widgetSourceObject = JSON.parseObject(widgetSource);
						String template = " function " + functionName + "(dataItem) { " + "for(var i=0;i<" + v + ".length;i++){ " + "if(dataItem."
								+ datasetColumn.getString("columnName") + "==" + v + "[i]." + widgetSourceObject.getString("widgetSourceTableValue") + "){ " + "return " + v
								+ "[i]." + widgetSourceObject.getString("widgetSourceTableText") + ";} } return '' ; }";
						expScript.append(template);
						retJson.put("template", functionName);
					} else if ("multiselect".equalsIgnoreCase(datasetColumn.getString("editor"))) {
						String v = this.getId() + datasetColumn.getString("columnName") + "data";
						String functionName = this.getId() + datasetColumn.getString("columnName");
						JSONObject widgetSourceObject = JSON.parseObject(widgetSource);
						String template = " function " + functionName + "(dataItem) { " + " var ret = [] ,k = dataItem." + datasetColumn.getString("columnName") + ".split(','); "
								+ "for(var i=0;i<" + v + ".length;i++){ " + "if(jQuery.inArray(" + v + "[i]." + widgetSourceObject.getString("widgetSourceTableValue")
								+ ",k )!=-1){ " + "ret.push( " + v + "[i]." + widgetSourceObject.getString("widgetSourceTableText") + ");} } return ret.join(',') ; }";
						expScript.append(template);
						retJson.put("template", functionName);
					}
					break;
				case "30": // 用户自定义
					JSONArray widgetSourceArray = JSON.parseArray(widgetSource);
					if (("char".equalsIgnoreCase(datasetColumn.getString("FieldType")) && "1".equalsIgnoreCase(datasetColumn.getString("FieldLength"))) || "checkbox".equalsIgnoreCase(datasetColumn.getString("editor"))) {
						// 假如为布尔值（特殊处理）
						String functionName = this.getId() + datasetColumn.getString("columnName");
						String template = "function " + functionName + "(dataItem){ return dataItem." + datasetColumn.get("columnName") + "== '"
								+ ("true".equals(widgetSourceArray.getJSONObject(0).get("value")) ? "1" : "0") + "' ? '" + widgetSourceArray.getJSONObject(0).get("text") + "' : '"
								+ widgetSourceArray.getJSONObject(1).get("text") + "'}";
						expScript.append(template);

						retJson.put("template", functionName);
					} else {
						retJson.put("editor", editerBuild(datasetColumn));
						// retJson.put("values", widgetSourceArray);
						String functionName = this.getId() + datasetColumn.getString("columnName");
						String template = "function " + functionName + "(dataItem){ var values = " + widgetSourceArray.toJSONString() + " ; "
								+ " for(var i=0;i<values.length;i++){" + " var va = values[i] ;" + " if(va.value == dataItem." + datasetColumn.getString("columnName") + "){"
								+ "  return va.text ;}" + "} return '' ;}";
						if ("multiselect".equalsIgnoreCase(datasetColumn.getString("editor"))) {
							template = " function " + functionName + "(dataItem) {  var values = " + widgetSourceArray.toJSONString() + " ;  " + " var ret = [] ,k = dataItem."
									+ datasetColumn.getString("columnName") + ".split(','); " + "for(var i=0;i<values.length;i++){ " + " var va = values[i] ;"
									+ "if(jQuery.inArray(va.value,k)!=-1){ " + "ret.push( va.text ) ;} } return ret.join(',') ; }";
						}
						expScript.append(template);
						retJson.put("template", functionName);
					}
					break;
				default:
					break;
				}

			} else { // 如果未选择数据源，则直接根据editor类型生成对应的规则 需要排除布尔类型和普通文本输入类型
				if (StringUtils.isNotEmpty(editor) && !"char".equalsIgnoreCase(datasetColumn.getString("FieldType"))
						&& !"1".equalsIgnoreCase(datasetColumn.getString("FieldLength")) && !"MaskedTextBox".equalsIgnoreCase(editor) && !"textfield".equalsIgnoreCase(editor)
						&& !"NumericTextBox".equalsIgnoreCase(editor)) {
					retJson.put("editor", editerBuild(datasetColumn));
				}
			}
			// 编辑 end

			// 弹出编辑页面 模板 strat
			editPageBuild(datasetColumn, popScriptTemp, filterableScriptTemp);
			// 弹出编辑页面 模板 end

			// 样式 alignment attributes: {"class": "table-cell",style:
			// "text-align: right; font-size: 14px"}
			String alignment = datasetColumn.getString("alignment");
			if (StringUtils.isNotEmpty(alignment) && !"left".equalsIgnoreCase(alignment)) { // 默认为left
																							// 故不添加
				JSONObject styleJson = new JSONObject();
				styleJson.put("style", "text-align:" + alignment + ";");
				retJson.put("attributes", styleJson);
			}

			// isSortable 是否排序 默认true
			String isSortable = datasetColumn.getString("isSortable");
			if (!"true".equalsIgnoreCase(isSortable)) {
				retJson.put("sortable", false);
			}
			// isMenu 是否可隐藏 默认true
			String isMenu = datasetColumn.getString("isMenu");
			if (!"true".equalsIgnoreCase(isMenu)) {
				retJson.put("menu", false);
			}

			buildFilterable(datasetColumn, retJson, editor, widgetSourceType);

			String formatValue = datasetColumn.getString("formatValue");
			if (StringUtils.isNotEmpty(formatValue)) { // 格式化时间、日期数字
				retJson.put("format", "{0:" + formatValue + "}");
			}

			// 处理模板编辑
			String templateScript = buildExpression(datasetColumn, retJson.getString("template"), expScript);
			if (StringUtils.isNotEmpty(templateScript)) {
				retJson.put("template", "##=" + templateScript + "=##");
			}

			String columnWidth = datasetColumn.getString("columnWidth");
			retJson.put("width", StringUtils.isNotEmpty(columnWidth) ? Integer.parseInt(columnWidth.replaceAll("px", "").replaceAll("%", "")) : 120);

			String isShowList = datasetColumn.getString("isShowList"); // 隐藏显示
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

	// treelist 要覆盖此方法。treelist 不能这样构建外部定义
	protected void buildFilterable(JSONObject datasetColumn, JSONObject retJson, String editor, String widgetSourceType) {
		if ("gridTitle".equalsIgnoreCase(source.get("filterType"))) {// 表头过滤
			// isFilterable 是否过滤 默认true
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
							// "##= function(e){e.kendoDateTimePicker({min : new Date(2015,05,26,03,00,00),max : new Date(2015,05,27,02,00,00)});} =##");
							uiJson.put("ui", editor);
							retJson.put("filterable", uiJson);
						}
					}
				}
			}
		} else {
			retJson.put("filterable", false);
		}
	}

	/**
	 * 列
	 * 
	 * @return
	 */
	public String getColumns() {
		/*
		 * 编辑模板 如果为弹窗模式 则自动生成编辑模板 <script id="popup-editor"
		 * type="text/x-kendo-template"> <h3>Edit Person</h3> <p>
		 * <label>Name:<input data-bind="value:name" /></label> </p> <p>
		 * <label>Age:<input data-role="numerictextbox" data-bind="value:age"
		 * /></label> </p> </script>
		 */
		StringBuffer popScriptTemp = new StringBuffer("<script id='" + this.getId() + "PopupEditor' type='text/x-kendo-template'><table>");
		/* style='width:\"+$('#grid_txt').width()+\"px;' */
		// 外部过滤查询javascript脚本构建
		StringBuffer filterableScriptTemp = new StringBuffer("<script> $(document).ready(function() { $('#" + this.getId() + "').before(\"<div id='" + this.getId()
				+ "_toolbar' style='width:" + (StringUtils.isNotEmpty(this.getWidth()) ? this.getWidth() : "99%") + ";display:none;' grid='" + this.getId() + "'  ></div>\"); ");
		filterableScriptTemp.append("$('#" + this.getId() + "_toolbar').kendoToolBar({ resizable: false,items: [");
		// 模板引擎转换方法
		StringBuffer expScript = new StringBuffer("<script type='text/javascript'>");
		StringBuffer headTemp = new StringBuffer("<script type='text/javascript'>");

		// 封装数据源配置 列信息
		Map<String, JSONObject> dataSourceSetColumnMap = new HashMap<>();
		String dataSourceSet = source.get("dataSourceSet");// 获取数据源配置
		if (StringUtils.isNotEmpty(dataSourceSet)) {
			JSONArray dataSourceSetJSONArray = (JSONArray) JSON.parse(dataSourceSet);
			if (dataSourceSetJSONArray != null && dataSourceSetJSONArray.size() > 0) {
				Elements as = new Elements();
				for (Object object : dataSourceSetJSONArray) {
					JSONObject dataSourceSetJSONObject = (JSONObject) object;
					JSONArray dataSourceSetColumnsJSONArray = dataSourceSetJSONObject.getJSONArray("columns");
					if (dataSourceSetColumnsJSONArray != null && dataSourceSetColumnsJSONArray.size() > 0) {
						for (Object object2 : dataSourceSetColumnsJSONArray) {
							JSONObject dataSourceSetColumnJSONObject = (JSONObject) object2;
							// push 自定义结构 
							pushDomElement(dataSourceSetColumnJSONObject.getString("hidLinkImg"),as);
							
							dataSourceSetColumnMap.put(dataSourceSetColumnJSONObject.getString("columnName"), dataSourceSetColumnJSONObject);
						}
					}
				}
				// 自定义按钮事件定义
				addHrefRole(as);
			}
		}
		// 返回参数
		JSONArray retArray = new JSONArray();

		String columnTemplate = source.get("columnTemplate");// 获取表头模板信息
		JSONObject jsonObj = null;
		JSONObject co = null;
		String locked = null;
		Boolean isLock = false;
		if (StringUtils.isNotEmpty(columnTemplate)) {
			JSONArray columnTemplateJSONArray = (JSONArray) JSON.parse(columnTemplate);
			if (columnTemplateJSONArray != null && columnTemplateJSONArray.size() > 0) {
				for (Object object : columnTemplateJSONArray) {
					co = (JSONObject) object;
					locked = co.getString("isLock");
					if (StringUtils.isNotEmpty(locked)) {
						isLock = true;
					}
				}

				// checkbox
				if (StringUtils.isNotEmpty(source.get("checkbox")) && "true".equalsIgnoreCase(source.get(""))) {
					jsonObj = new JSONObject();
					jsonObj.put("title", "<input type=\\'\\' class=\\'" + this.getId() + "__All\\' />");
					jsonObj.put("template", "<input type=\\'\\' class=\\'" + this.getId() + "_\\' />");
					jsonObj.put("width", "40px");
					jsonObj.put("menu", false);

					JSONObject attributes = new JSONObject();
					attributes.put("style", "text-align: center");
					jsonObj.put("attributes", attributes);

					JSONObject headerAttributes = new JSONObject();
					headerAttributes.put("style", "text-align: center");
					jsonObj.put("headerAttributes", headerAttributes);
					if (isLock) {
						jsonObj.put("locked", true);
						jsonObj.put("lockable", true);
					}
					retArray.add(jsonObj);
				}
				// 序号
				if (StringUtils.isNotEmpty(source.get("sortNo")) && "true".equalsIgnoreCase(source.get("sortNo"))) {
					jsonObj = new JSONObject();
					jsonObj.put("title", "序号");
					jsonObj.put("field", "startIndex");
					jsonObj.put("width", "60px");
					jsonObj.put("sortable", false);// 禁止排序
					jsonObj.put("filterable", false);// 禁止过滤
					jsonObj.put("menu", false); // 禁止隐藏
					JSONObject attributes = new JSONObject();
					String sortNoAlign = source.get("sortNoAlign");
					attributes.put("style", "text-align: "+(sortNoAlign==null?"right":sortNoAlign));
					jsonObj.put("attributes", attributes);
					jsonObj.put("headerAttributes", attributes.clone());
					if (isLock) {
						jsonObj.put("locked", true);
						jsonObj.put("lockable", true);
					}
					retArray.add(jsonObj);
				}
				// 循环遍历列节点
				for (Object object : columnTemplateJSONArray) {
					jsonObj = (JSONObject) object;

					buildColumnTemplate(dataSourceSetColumnMap, jsonObj, retArray, popScriptTemp, filterableScriptTemp, expScript, headTemp);

				}

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

		popScriptTemp.append("</table></script>");
		if (StringUtils.isNotBlank(source.get("editable")) && "popup".equalsIgnoreCase(source.get("editable"))) {
			this.addTemplate(popScriptTemp.toString());
		}

		expScript.append("</script>");
		this.addTemplate(expScript.toString().replaceAll("row_0_", ""));

		headTemp.append("</script>");
		this.addTemplate(headTemp.toString().replaceAll("row_0_", ""));

		// 外部定义查询条件
		filterableScriptTemp.append("{type: 'button', text: '查询' ,click : glafGrid.searchGrid },{type: 'button', text: '重置' ,click : glafGrid.searchClear }]});kendo.bind($('#"
				+ this.getId() + "_toolbar'), kendo.observable({}));});</script> ");
		if ("gridExternal".equalsIgnoreCase(source.get("filterType"))) {
			this.addTemplate(filterableScriptTemp.toString());
		}

		return retArray.toJSONString().replaceAll("\"", "\'");
	}

	/**
	 * 自定义编辑页面验证输入框构建
	 */
	protected void editPageBuild(JSONObject jsonObj, StringBuffer popScriptTemp, StringBuffer filterableScriptTemp) {
		// <p><label>备注:<input data-role='dropdownlist' data-text-field='name'
		// data-value-field='id'
		// data-source="{data: grid_txtcell_useradd7028_user2data}"
		// data-bind="value:cell_useradd7028_user2" /></p>
		String widgetSource = jsonObj.getString("widgetSource");
		String widgetSourceType = jsonObj.getString("widgetSourceType");
		// 是否可编辑
		if (!"true".equalsIgnoreCase(jsonObj.getString("isEditor"))) { // 不可编辑
			String formatValue = jsonObj.getString("formatValue");
			String columnName = jsonObj.getString("columnName");
			if (StringUtils.isNotEmpty(formatValue)) { // 格式化时间、日期数字
				columnName = "(kendo.format('{0:" + formatValue.replaceAll("#", "\\\\#") + "}'," + jsonObj.getString("columnName") + "))";
			}

			// 数据源
			if ("20".equalsIgnoreCase(widgetSourceType)
					&& ("dropdownlist".equalsIgnoreCase(jsonObj.getString("editor")) || "multiselect".equalsIgnoreCase(jsonObj.getString("editor")))) {
				JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
				columnName = "glafGrid.getGridDataSourceValue(" + this.getId() + jsonObj.getString("columnName") + "data," + jsonObj.getString("columnName") + "" + ",'"
						+ widgetSourceJson.getString("widgetSourceTableText") + "','" + widgetSourceJson.getString("widgetSourceTableValue") + "')";
			} else if ("10".equalsIgnoreCase(widgetSourceType)) {
				List<Dictory> dictory = BaseDataManager.getInstance().getDictoryList(widgetSource);
				JSONArray ja = new JSONArray();
				JSONObject jo = null;
				for (Dictory d : dictory) {
					jo = new JSONObject();
					jo.put("text", d.getName());
					jo.put("value", d.getCode());
					ja.add(jo);
				}
				columnName = "glafGrid.getGridDataSourceValue(" + ja.toJSONString() + "," + jsonObj.getString("columnName") + ")";
			} else if ("30".equalsIgnoreCase(widgetSourceType)) {
				JSONArray widgetSourceArray = JSON.parseArray(widgetSource);
				columnName = "glafGrid.getGridDataSourceValue(" + widgetSourceArray.toJSONString() + "," + jsonObj.getString("columnName") + ")";
			}

			popScriptTemp.append("<tr><td>" + jsonObj.get("title") + "</td><td> &nbsp;&nbsp;#= " + jsonObj.getString("columnName") + " ? " + columnName + " : ''   #</td></tr> ");
		} else { // 可编辑
			String popScriptType = "text";
			if (("char".equalsIgnoreCase(jsonObj.getString("FieldType")) && "1".equalsIgnoreCase(jsonObj.getString("FieldLength"))) || "checkbox".equalsIgnoreCase(jsonObj.getString("editor"))) {
				popScriptType = "";
			}
			String editorRole = jsonObj.getString("editor").toLowerCase();
			if ("10".equalsIgnoreCase(widgetSourceType) || "30".equalsIgnoreCase(widgetSourceType)) {
				if (!"multiselect".equalsIgnoreCase(editorRole)) {
					editorRole = "dropdownlist";
				}
			}

			if ("textfield".equalsIgnoreCase(editorRole) || StringUtils.isEmpty(editorRole)) {
				editorRole = "maskedtextbox";
			}
			popScriptTemp.append("<tr><td>" + jsonObj.get("title") + "</td><td>  <input " + ("".equalsIgnoreCase(popScriptType) ? " " : "data-role='" + editorRole + "'")
					+ " type='" + popScriptType + "' ");
			// 如果有数据源
			if ("20".equalsIgnoreCase(widgetSourceType) && "dropdownlist".equalsIgnoreCase(jsonObj.getString("editor"))) {
				JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
				popScriptTemp.append(" data-source=\"{data:" + this.getId() + jsonObj.getString("columnName") + "data}\" ");
				popScriptTemp.append(" data-text-field='" + widgetSourceJson.getString("widgetSourceTableText") + "' ");
				popScriptTemp.append(" data-value-field='" + widgetSourceJson.getString("widgetSourceTableValue") + "' ");
			} else if ("20".equalsIgnoreCase(widgetSourceType) && "multiselect".equalsIgnoreCase(jsonObj.getString("editor"))) {
				JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
				popScriptTemp.append(" data-source=\"{data:" + this.getId() + jsonObj.getString("columnName") + "data}\" ");
				popScriptTemp.append(" data-text-field='" + widgetSourceJson.getString("widgetSourceTableText") + "' ");
				popScriptTemp.append(" data-value-field='" + widgetSourceJson.getString("widgetSourceTableValue") + "' ");
			} else if ("20".equalsIgnoreCase(widgetSourceType) && "autocomplete".equalsIgnoreCase(jsonObj.getString("editor"))) {
				JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
				String newDataSource = "new kendo.data.DataSource({" + "transport: {" + "read: {" + "url: contextPath+'/mx/form/data/widgetSource', " + "dataType: 'json',"
						+ "data:{dataSetId:'" + widgetSourceJson.getString("dataSetId") + "'," + "databaseId : '" + widgetSourceJson.getString("databaseId") + "'," + "text : '"
						+ widgetSourceJson.getString("widgetSourceTableText") + "' }," + "type : 'POST'" + "}" + "}" + "})";
				popScriptTemp.append(" data-text-field='" + widgetSourceJson.getString("widgetSourceTableText") + "' ");
				popScriptTemp.append(" data-source=\"{data: " + newDataSource + "}\" ");
			} else if ("10".equalsIgnoreCase(widgetSourceType)) {
				List<Dictory> dictory = BaseDataManager.getInstance().getDictoryList(widgetSource);
				JSONArray ja = new JSONArray();
				JSONObject jo = null;
				for (Dictory d : dictory) {
					jo = new JSONObject();
					jo.put("text", d.getName());
					jo.put("value", d.getCode());
					ja.add(jo);
				}
				popScriptTemp.append(" data-text-field='text' ");
				popScriptTemp.append(" data-value-field='value' ");
				popScriptTemp.append(" data-source=\"{data:" + ja.toJSONString().replaceAll("\"", "'") + "}\" ");
			} else if ("30".equalsIgnoreCase(widgetSourceType)) {
				JSONArray widgetSourceArray = JSON.parseArray(widgetSource);
				popScriptTemp.append(" data-text-field='text' ");
				popScriptTemp.append(" data-value-field='value' ");
				popScriptTemp.append(" data-source=\"{data:" + widgetSourceArray.toJSONString().replaceAll("\"", "'") + "}\" ");
			}
			popScriptTemp.append(" data-bind='" + ("checkbox".equalsIgnoreCase(popScriptType) ? "checked" : "value") + ":" + jsonObj.get("columnName") + "' ");

			popScriptTemp.append(" name='" + jsonObj.getString("columnName") + "' ");

			// 必填
			if (StringUtils.isNotEmpty(jsonObj.getString("isRequired")) && "true".equalsIgnoreCase(jsonObj.getString("isRequired"))) {
				popScriptTemp.append(" required='required' data-required-msg='" + jsonObj.getString("title") + " 为必填项' ");
			}

			// 正则验证(范围不算)
			if (StringUtils.isNotEmpty(jsonObj.getString("dataValidation")) && !"range".equalsIgnoreCase(jsonObj.getString("dataValidation"))) {
				String validateMsg = jsonObj.getString("validateMsg");
				String dataPatternMsg = jsonObj.getString("title") + " 非法输入";
				if (StringUtils.isNotEmpty(validateMsg)) {
					dataPatternMsg = validateMsg;
				}
				popScriptTemp.append(" pattern='" + jsonObj.getString("dataValidation") + "' data-pattern-msg='" + dataPatternMsg + "' ");
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
					minValue = "#:new Date(" + date[0] + "," + (Integer.parseInt(date[1]) - 1) + "," + date[2] + "," + time[0] + "," + time[1] + "," + time[2] + ")#"; // 使用mvvm模板属性中
																																										// 月份不知为何会与通常月份大1.
				} else if ("DatePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 日期类型
					String[] date = min.split("-");
					minValue = "#:new Date(" + date[0] + "," + (Integer.parseInt(date[1]) - 1) + "," + date[2] + ")#";
				} else if ("TimePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 时间类型
					String[] mins = min.split(" ");
					String[] date = mins[0].split("-");
					String[] time = mins[1].split(":");
					minValue = "#:new Date(" + date[0] + "," + (Integer.parseInt(date[1]) - 1) + "," + date[2] + "," + time[0] + "," + time[1] + "," + time[2] + ")#";
				}
			} else if (StringUtils.isNotEmpty(jsonObj.getString("numberMinValue"))) {
				// minValue = min ;
				minValue = jsonObj.getString("numberMinValue");
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
					maxValue = "#:new Date(" + date[0] + "," + (Integer.parseInt(date[1]) - 1) + "," + date[2] + "," + time[0] + "," + time[1] + "," + time[2] + ")#";
				} else if ("DatePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 日期类型
					String[] date = max.split("-");
					maxValue = "#:new Date(" + date[0] + "," + (Integer.parseInt(date[1]) - 1) + "," + date[2] + ")#";
				} else if ("TimePicker".equalsIgnoreCase(jsonObj.getString("editor"))) { // 时间类型
					String[] maxs = max.split(" ");
					String[] date = maxs[0].split("-");
					String[] time = maxs[1].split(":");
					maxValue = "#:new Date(" + date[0] + "," + (Integer.parseInt(date[1]) - 1) + "," + date[2] + "," + time[0] + "," + time[1] + "," + time[2] + ")#";
				}
			} else if (StringUtils.isNotEmpty(jsonObj.getString("numberMaxValue"))) {
				// maxValue = max ;
				maxValue = jsonObj.getString("numberMaxValue");
			}

			String role = KendoEnum.getClassName(jsonObj.getString("editor")) == null ? "kendoMaskedTextBox" : KendoEnum.getClassName(jsonObj.getString("editor"));
			if ("kendoDateTimePicker".equalsIgnoreCase(role)) {
				popScriptTemp.append(" data-format='yyyy-MM-dd HH:mm:ss' data-culture='zh-CN' ");
			} else if ("kendoDatePicker".equalsIgnoreCase(role)) {
				popScriptTemp.append(" data-format='yyyy-MM-dd' data-culture='zh-CN' ");
			} else if ("kendoTimePicker".equalsIgnoreCase(role)) {
				popScriptTemp.append(" data-format='HH:mm:ss' data-culture='zh-CN' ");
			}

			if (StringUtils.isNotEmpty(minValue)) {
				popScriptTemp.append(" data-min='" + minValue + "' ");
			}
			if (StringUtils.isNotEmpty(maxValue)) {
				popScriptTemp.append(" data-max='" + maxValue + "' ");
			}

			popScriptTemp.append(" />");
			// 验证提示
			popScriptTemp.append("<span class='k-invalid-msg' data-for='" + jsonObj.getString("columnName") + "' ></span>");
			popScriptTemp.append(" </td></tr>");

		}

		// 过滤
		if ("true".equalsIgnoreCase(jsonObj.getString("isFilterable"))) {
			String popScriptType = "text";
			if (("char".equalsIgnoreCase(jsonObj.getString("FieldType")) && "1".equalsIgnoreCase(jsonObj.getString("FieldLength"))) || "checkbox".equalsIgnoreCase(jsonObj.getString("editor"))) {
				popScriptType = "checkbox";
			}
			String editorRole = jsonObj.getString("editor").toLowerCase();
			if ("10".equalsIgnoreCase(widgetSourceType) || "30".equalsIgnoreCase(widgetSourceType)) {
				if (!"multiselect".equalsIgnoreCase(editorRole)) {
					editorRole = "dropdownlist";
				}
			}

			if ("textfield".equalsIgnoreCase(editorRole) || StringUtils.isEmpty(editorRole)) {
				editorRole = "maskedtextbox";
			}
			StringBuffer dataSource = new StringBuffer();
			// 如果有数据源
			if ("20".equalsIgnoreCase(widgetSourceType)
					&& ("dropdownlist".equalsIgnoreCase(jsonObj.getString("editor")) || "multiselect".equalsIgnoreCase(jsonObj.getString("editor")))) {
				JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
				dataSource.append(" data-source=\\\"{data:" + this.getId() + jsonObj.getString("columnName") + "data}\\\" ");
				dataSource.append(" data-text-field='" + widgetSourceJson.getString("widgetSourceTableText") + "' ");
				dataSource.append(" data-value-field='" + widgetSourceJson.getString("widgetSourceTableValue") + "' ");
				// data-option-label=\"{text:'请选择',value:''}\"
				dataSource.append(" data-option-label=\\\"{" + widgetSourceJson.getString("widgetSourceTableText") + ":'请选择',"
						+ widgetSourceJson.getString("widgetSourceTableValue") + ":'' }\\\" ");
			} else if ("20".equalsIgnoreCase(widgetSourceType) && "autocomplete".equalsIgnoreCase(jsonObj.getString("editor"))) {
				JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
				String newDataSource = "new kendo.data.DataSource({" + "transport: {" + "read: {" + "url: contextPath+'/mx/form/data/widgetSource', " + "dataType: 'json',"
						+ "data:{dataSetId:'" + widgetSourceJson.getString("dataSetId") + "'," + "databaseId : '" + widgetSourceJson.getString("databaseId") + "'," + "text : '"
						+ widgetSourceJson.getString("widgetSourceTableText") + "' }," + "type : 'POST'" + "}" + "}" + "})";
				// dataSource.append(" data-text-field='"+widgetSourceJson.getString("widgetSourceTableText")+"' ")
				// ;
				// dataSource.append(" data-source=\\\"{data: "+newDataSource+"}\\\" ")
				// ;
			} else if ("10".equalsIgnoreCase(widgetSourceType)) {
				List<Dictory> dictory = BaseDataManager.getInstance().getDictoryList(widgetSource);
				JSONArray ja = new JSONArray();
				JSONObject jo = null;
				for (Dictory d : dictory) {
					jo = new JSONObject();
					jo.put("text", d.getName());
					jo.put("value", d.getCode());
					ja.add(jo);
				}
				dataSource.append(" data-text-field='text' ");
				dataSource.append(" data-value-field='value' ");
				dataSource.append(" data-source=\\\"{data:" + ja.toJSONString().replaceAll("\"", "'") + "}\\\" ");
				dataSource.append(" data-option-label=\\\"{text:'请选择',value:'' }\\\" ");
			} else if ("30".equalsIgnoreCase(widgetSourceType)) {
				JSONArray widgetSourceArray = JSON.parseArray(widgetSource);
				dataSource.append(" data-text-field='text' ");
				dataSource.append(" data-value-field='value' ");
				dataSource.append(" data-source=\\\"{data:" + widgetSourceArray.toJSONString().replaceAll("\"", "'") + "}\\\" ");
				dataSource.append(" data-option-label=\\\"{text:'请选择',value:'' }\\\" ");
			}
			// filterableScriptTemp.append("{ template: \"<label>"+jsonObj.get("name")+":</label><input "+("checkbox".equalsIgnoreCase(popScriptType)?" ":"data-role='"+editorRole+"'")
			filterableScriptTemp.append("{ template: \"<label>" + jsonObj.get("title") + ":</label><input data-role='" + editorRole + "' ft='" + popScriptType + "'" + " cn='"
					+ jsonObj.get("columnName") + "' type='text' " + dataSource.toString() + " />\",overflow: 'never' }, ");
		}
	}

	/**
	 * cell单元格editor编辑器构建
	 * 
	 * @param jsonObj
	 * @return
	 */
	protected String editerBuild(JSONObject jsonObj) {
		StringBuffer editorFun = new StringBuffer("##=function(container, options){");
		String name = "name=\\''+options.field+'\\'";
		if ("multiselect".equalsIgnoreCase(jsonObj.getString("editor"))) { // 多选下拉框
			name = "";
			editorFun.append("$('#" + this.getId() + "').data('_tr',$(container).parents('tr')) ; ");
		}
		editorFun.append("$('<input type=\\'text\\' " + name + "/>')");
		// editorFun.append("input.attr('name', options.field);");
		// 必填
		if (StringUtils.isNotEmpty(jsonObj.getString("isRequired")) && "true".equalsIgnoreCase(jsonObj.getString("isRequired"))) {
			editorFun.append(".attr('required', 'required')");
			editorFun.append(".attr('data-required-msg', '" + jsonObj.getString("title") + " 为必填项')");
		}
		// 正则验证(范围不算)
		if (StringUtils.isNotEmpty(jsonObj.getString("dataValidation")) && !"range".equalsIgnoreCase(jsonObj.getString("dataValidation"))) {
			editorFun.append(".attr('pattern', '" + jsonObj.getString("dataValidation") + "')");
			String validateMsg = jsonObj.getString("validateMsg");
			String dataPatternMsg = jsonObj.getString("title") + " 非法输入";
			if (StringUtils.isNotEmpty(validateMsg)) {
				dataPatternMsg = validateMsg;
			}
			editorFun.append(".attr('data-pattern-msg', '" + dataPatternMsg + "')");
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

		String dataSource = "{" + datasourceStr(jsonObj);

		editorFun.append(".appendTo(container)");
		String role = KendoEnum.getClassName(jsonObj.getString("editor")) == null ? "kendoMaskedTextBox" : KendoEnum.getClassName(jsonObj.getString("editor"));
		if ("kendoDateTimePicker".equalsIgnoreCase(role)) {
			dataSource += "format : 'yyyy-MM-dd HH:mm:ss' , culture: 'zh-CN',";
		} else if ("kendoDatePicker".equalsIgnoreCase(role)) {
			dataSource += "format : 'yyyy-MM-dd' , culture: 'zh-CN',";
		} else if ("kendoTimePicker".equalsIgnoreCase(role)) {
			dataSource += "format : 'HH:mm:ss' , culture: 'zh-CN',";
		}

		if (StringUtils.isNotEmpty(minValue)) {
			dataSource += "min : " + minValue + ",";
		}
		if (StringUtils.isNotEmpty(maxValue)) {
			dataSource += "max : " + maxValue + ",";
		}
		dataSource += "}";
		editorFun.append("." + role + "(" + dataSource + ");");
		editorFun.append("$('<span class=\\'k-invalid-msg\\' " + "data-for=\\''+options.field+'\\'  ></span>').appendTo(container);");
		editorFun.append("}=##");

		return editorFun.toString();
	}

	/**
	 * 验证控件 数据源
	 * 
	 * @param jsonObj
	 * @return
	 */
	protected String datasourceStr(JSONObject jsonObj) {
		String dataSource = "";
		String widgetSourceType = jsonObj.getString("widgetSourceType");
		String widgetSource = jsonObj.getString("widgetSource");
		if ("20".equalsIgnoreCase(widgetSourceType) && "AutoComplete".equalsIgnoreCase(jsonObj.getString("editor"))) { // 如果为数据源
																														// 并且是autocomplate
			JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
			dataSource = "dataSource : new kendo.data.DataSource({" + "transport: {" + "read: {" + "url: contextPath+'/mx/form/data/widgetSource', " + "dataType: 'json',"
					+ "data:{dataSetId:'" + widgetSourceJson.getString("dataSetId") + "'," + "databaseId : '" + widgetSourceJson.getString("databaseId") + "'," + "text : '"
					+ widgetSourceJson.getString("widgetSourceTableText") + "'," + "value : '" + widgetSourceJson.getString("widgetSourceTableValue") + "' }," + "type : 'POST'"
					+ "}" + "}" + "})," + "dataTextField : '" + widgetSourceJson.getString("widgetSourceTableText") + "'," + "dataValueField : '"
					+ widgetSourceJson.getString("widgetSourceTableValue") + "',";
		} else if ("20".equalsIgnoreCase(widgetSourceType) && "dropdownlist".equalsIgnoreCase(jsonObj.getString("editor"))) {// 数据源
																																// 控件为
																																// dropdownlist
			JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
			dataSource = "dataSource : " + this.getId() + jsonObj.getString("columnName") + "data ," + "dataTextField : '" + widgetSourceJson.getString("widgetSourceTableText")
					+ "'," + "dataValueField : '" + widgetSourceJson.getString("widgetSourceTableValue") + "',";
			String templates = "<script>" + this.getId() + jsonObj.getString("columnName") + "data = [] ;"
					+ "$.ajax({type : 'POST',url:  contextPath+'/mx/form/data/widgetSource'," + "dataType: 'json', async:false, data:{dataSetId:'"
					+ widgetSourceJson.getString("dataSetId") + "'," + "databaseId : '" + widgetSourceJson.getString("databaseId") + "'," + "text : '"
					+ widgetSourceJson.getString("widgetSourceTableText") + "',value : '" + widgetSourceJson.getString("widgetSourceTableValue") + "' },"
					+ "success: function(result) {" + this.getId() + jsonObj.getString("columnName") + "data = result ;}}); </script>";
			this.addTemplate(templates);
		} else if ("20".equalsIgnoreCase(widgetSourceType) && "multiselect".equalsIgnoreCase(jsonObj.getString("editor"))) {// 数据源
																															// 控件为
																															// multiselect
			JSONObject widgetSourceJson = JSON.parseObject(widgetSource);
			dataSource = "dataSource : " + this.getId() + jsonObj.getString("columnName") + "data ," + "dataTextField : '" + widgetSourceJson.getString("widgetSourceTableText")
					+ "'," + "dataValueField : '" + widgetSourceJson.getString("widgetSourceTableValue") + "',"
					+ "value:options.model[options.field].split(','),change : function(e){var grid = $('#" + this.getId() + "'),"
					+ "gridk = kendo.widgetInstance(grid);var dataItem = gridk.dataItem(grid.data('_tr'));dataItem." + jsonObj.getString("columnName")
					+ " = this.value().join(',');" + "dataItem.dirty = true ;}";
			String templates = "<script>" + this.getId() + jsonObj.getString("columnName") + "data = [] ;"
					+ "$.ajax({type : 'POST',url:  contextPath+'/mx/form/data/widgetSource'," + "dataType: 'json', async:false, data:{dataSetId:'"
					+ widgetSourceJson.getString("dataSetId") + "'," + "databaseId : '" + widgetSourceJson.getString("databaseId") + "'," + "text : '"
					+ widgetSourceJson.getString("widgetSourceTableText") + "',value : '" + widgetSourceJson.getString("widgetSourceTableValue") + "' },"
					+ "success: function(result) {" + this.getId() + jsonObj.getString("columnName") + "data = result ;}}); </script>";
			this.addTemplate(templates);
		} else if ("10".equalsIgnoreCase(widgetSourceType)) {// 如果是字典数据
			List<Dictory> dictory = BaseDataManager.getInstance().getDictoryList(widgetSource);
			JSONArray ja = new JSONArray();
			JSONObject jo = null;
			for (Dictory d : dictory) {
				jo = new JSONObject();
				jo.put("text", d.getName());
				jo.put("value", d.getCode());
				ja.add(jo);
			}
			dataSource = "dataSource:" + ja.toJSONString() + ",dataTextField :'text',dataValueField:'value',";
			if ("multiselect".equalsIgnoreCase(jsonObj.getString("editor"))) {
				dataSource += "value:options.model[options.field].split(','),change : function(e){var grid = $('#" + this.getId() + "'),"
						+ "gridk = kendo.widgetInstance(grid);var dataItem = gridk.dataItem(grid.data('_tr'));dataItem." + jsonObj.getString("columnName")
						+ " = this.value().join(',');" + "dataItem.dirty = true ;}";
			}

		} else if ("30".equalsIgnoreCase(widgetSourceType)) {// 自定义数据
			JSONArray widgetSourceArray = JSON.parseArray(widgetSource);
			dataSource = "dataSource:" + widgetSourceArray.toJSONString() + ",dataTextField :'text',dataValueField:'value',";
			if ("multiselect".equalsIgnoreCase(jsonObj.getString("editor"))) {
				dataSource += "value:options.model[options.field].split(','),change : function(e){var grid = $('#" + this.getId() + "'),"
						+ "gridk = kendo.widgetInstance(grid);var dataItem = gridk.dataItem(grid.data('_tr'));dataItem." + jsonObj.getString("columnName")
						+ " = this.value().join(',');" + "dataItem.dirty = true ;}";
			}
		}
		return dataSource;
	}

	/**
	 * 用于显示数据格式 treelist 需要覆盖此方法
	 * 
	 * @return
	 */
	public String getSchemaFields() {
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
					for (Object o : jsonArray) {
						jsonObj = (JSONObject) o;

						String fieldType = jsonObj.getString("FieldType");
						JSONObject jsonSub = new JSONObject();
						if (fieldType != null && !"".equals(fieldType)) {
							if ("datetime".equalsIgnoreCase(fieldType)) {
								jsonSub.put("type", "date");// 时间
							} else if ("i4".equalsIgnoreCase(fieldType) || "r8".equalsIgnoreCase(fieldType) || "int".equalsIgnoreCase(fieldType)) {
								jsonSub.put("type", "number");// 数字
							} else if (("char".equalsIgnoreCase(fieldType) && "1".equalsIgnoreCase(jsonObj.getString("FieldLength")))|| "checkbox".equalsIgnoreCase(jsonObj.getString("editor"))) {
								jsonSub.put("type", "boolean");// 布尔
								jsonSub.put("parse", "##=function(v){if(v==1){return true;}else {return false;}}=##");// 布尔类型转换
							} else {
								jsonSub.put("type", "string");
							}
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
						requiredObject.put("required", StringUtils.isNotEmpty(required) ? Boolean.parseBoolean(required) : false);
						requiredObject.put("data-required-msg", jsonObj.getString("title") + " 为必填项 !");
						String validateMsg = jsonObj.getString("validateMsg");
						String dataPatternMsg = jsonObj.getString("title") + " 非法输入!";
						if (StringUtils.isNotEmpty(validateMsg)) {
							dataPatternMsg = validateMsg;
						}
						requiredObject.put("data-pattern-msg", dataPatternMsg);
						String editor = jsonObj.getString("editor");
						String dataValidation = jsonObj.getString("dataValidation");
						if ("MaskedTextBox".equalsIgnoreCase(editor) && !"textfield".equalsIgnoreCase(editor) && StringUtils.isNotEmpty(dataValidation)
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
						jsonSub.put("editable", StringUtils.isNotEmpty(editable) ? Boolean.parseBoolean(editable) : true);

						String defaultValue = jsonObj.getString("defaultValue");// 默认值
						if (StringUtils.isNotEmpty(defaultValue)) {
							jsonSub.put("defaultValue", "boolean".equalsIgnoreCase(jsonSub.getString("type")) ? Boolean.parseBoolean(defaultValue) : defaultValue);
						}

						returnJson.put(jsonObj.get("columnName").toString(), jsonSub);
					}
				}
			}
		}

		if (returnJson == null || returnJson.size() == 0) {
			return null;
		}

		return returnJson.toJSONString().replaceAll("\"", "\'");
	}

	/**
	 * 翻页组件
	 * 
	 * @return
	 */
	public String getPageable() {
		/*
		 * { refresh: true, pageSizes: [5, 10, 15, 20, 25, 50, 100, 200, 500],
		 * buttonCount: 2, info:true, previousNext: true, input:true, numeric:
		 * false, selectTemplate: '<li><span
		 * style=\'color:red\'>#=text#</span></li>', linkTemplate: '<li><a
		 * href=\'\\#\' class=\'k-link\'
		 * data-#=ns#page=\'#=idx#\'><strong>#=text#</strong></a></li>',
		 * messages:{ page: '第' , of: '页，共 {0}页' , itemsPerPage: '每页条数' , empty:
		 * '没有数据', display:'显示{0}-{1}来自{2}', first: '去到首页' , last:'去到末页',
		 * next:'去下一页' , previous: '去上一页' , refresh: '刷新这个表', morePages: '更多页',
		 * } }
		 */
		if (source.get("pageable") != null && "true".equals(source.get("pageable"))) {
			String pageRefresh = source.get("pageRefresh"); // 分页刷新按钮 boolean
			String pageSizes = StringUtils.isNotEmpty(source.get("pageSizes")) ? source.get("pageSizes") : "5, 10, 15, 20, 25, 50, 100, 200, 500"; // 分页数量选择框
			String pageButtonCount = StringUtils.isNotEmpty(source.get("pageButtonCount")) ? source.get("pageButtonCount") : "10"; // 数字分页链接显示数量
			String pageInfo = source.get("pageInfo"); // 分页信息显示 boolean
			String pagePreviousNext = source.get("previousNext"); // 分页翻页按钮
																	// boolean
			String pageNumeric = source.get("pageNumeric"); // 数字翻页按钮 boolean
			String pageInput = source.get("pageInput"); // 输入框翻页 boolean

			String pageMessagesFirst = source.get("pageMessagesFirst"); // 首页按钮提示文本
			String pageMessagesLast = source.get("pageMessagesLast"); // 未页按钮提示文本
			String pageMessagesNext = source.get("pageMessagesNext"); // 下一页按钮提示文本
			String pageMessagesPrevious = source.get("pageMessagesPrevious"); // 上一页按钮提示文本
			String pageMessagesEmpty = source.get("pageMessagesEmpty"); // 空数据提示文本

			StringBuffer pageable = new StringBuffer("{");
			if (!"false".equals(pageRefresh)) // 默认为false 则不添加
				pageable.append("refresh:" + pageRefresh + ",");

			pageable.append("pageSizes:[" + pageSizes + "],");
			pageable.append("buttonCount:" + pageButtonCount + ",");

			StringBuffer messages = new StringBuffer("messages:{");

			if (!"true".equals(pageInfo)) // 默认为true 则不添加
				pageable.append("info:" + pageInfo + ",");

			if (!"true".equals(pageNumeric)) // 默认为true 则不添加
				pageable.append("numeric:" + pageNumeric + ",");

			if (!"false".equals(pageInput)) // 默认为false 则不添加
				pageable.append("input:" + pageInput + ",");

			if (!"true".equals(pagePreviousNext)) // 默认为true 则不添加
				pageable.append("previousNext:" + pagePreviousNext + ",");
			else {
				if (StringUtils.isNotEmpty(pageMessagesFirst))
					messages.append("first:'" + pageMessagesFirst + "',");
				if (StringUtils.isNotEmpty(pageMessagesLast))
					messages.append("last:'" + pageMessagesLast + "',");
				if (StringUtils.isNotEmpty(pageMessagesNext))
					messages.append("next:'" + pageMessagesNext + "',");
				if (StringUtils.isNotEmpty(pageMessagesFirst))
					messages.append("previous:'" + pageMessagesPrevious + "',");
			}

			if (StringUtils.isNotEmpty(pageMessagesEmpty))
				messages.append("empty:'" + pageMessagesEmpty + "',");

			messages.append("}");
			pageable.append(messages.toString());
			pageable.append("}");
			return pageable.toString();
		} else {
			return "false";
		}
	}

	/**
	 * 一页显示条数
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

	/**
	 * 是否可编辑
	 * 
	 * @return
	 */
	public String getEditable() {
		/*
		 * editable: { mode: "popup", template:
		 * kendo.template($("#popup-editor").html()) }
		 */
		String editable = source.get("editable");
		if (StringUtils.isNotEmpty(editable) && "popup".equalsIgnoreCase(editable)) {
			StringBuffer editableRet = new StringBuffer("{");
			editableRet.append("mode:'" + editable + "',");
			editableRet.append("template:kendo.template($('#" + this.getId() + "PopupEditor').html()) ");
			editableRet.append("}");
			return editableRet.toString();
		}
		return editable;
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
	 * 滚动条
	 * 
	 * @return
	 */
	public String getScrollable() {
		return source.get("scrollable");
	}

	/**
	 * 列排列
	 * 
	 * @return
	 */
	public String getReorderable() {
		return source.get("reorderable");
	}

	/**
	 * 排序
	 * 
	 * @return
	 */
	public String getSortable() {
		String sortable = source.get("sortable");
		StringBuffer sortableRet = new StringBuffer("{");
		if (StringUtils.isNotEmpty(sortable)) {
			sortableRet.append("mode:'" + sortable + "'");
		}
		sortableRet.append("}");
		return sortableRet.toString();
	}

	/**
	 * 分组
	 * 
	 * @return
	 */
	public String getGroupable() {
		return source.get("groupable");
	}

	/**
	 * 过滤
	 * 
	 * @return
	 */
	public String getFilterable() {
		return source.get("filterable");
	}

	/**
	 * 列菜单
	 * 
	 * @return
	 */
	public String getColumnMenu() {
		return source.get("columnMenu");
	}

	/***
	 * 服务端分页
	 * 
	 * @return
	 */
	public String getServerPaging() {
		return source.get("serverPaging");
	}

	/**
	 * 服务端排序
	 * 
	 * @return
	 */
	public String getServerSorting() {
		return source.get("serverSorting");
	}

	/**
	 * 服务端分组
	 * 
	 * @return
	 */
	public String getServerGrouping() {
		return source.get("serverGrouping");
	}

	/**
	 * 服务端过滤
	 * 
	 * @return
	 */
	public String getServerFiltering() {
		return source.get("serverFiltering");
	}

	/**
	 * 单击行事件
	 * 
	 * @return
	 */
	public String getClick() {
		return source.get("click");
	}

	/**
	 * 单击行事件所执行的方法
	 */
	public String getClickFunction() {
		// 返回事件方法 function(e){changeFunction(e.sender,a);}
		// return source.get("changeFunction");
		// clickFunction单击执行方法 参数一：为grid本身，参数二：为点击事件类型，参数三：为联动目标对象
		if (source.get("click") != null && !"".equals(source.get("click"))) {
			// return "function(e){glafGrid.clickFunction(e.sender,'" +
			// source.get("click") + "',"
			// +
			// (StringUtils.isNotEmpty(source.get("linkageControl").toString())
			// ? source.get("linkageControl") : "''") + ");}";
			// 方法类型
			String type = StringUtils.isNotEmpty(source.get("click").toString()) ? source.get("click") : "''";
			// 获取规则 -->输入表达式定义
			String paraType = source.get("paraType");
			JSONObject paraTypeObject = null;
			if (StringUtils.isNotEmpty(paraType)) {
				JSONArray paraTypeArray = JSON.parseArray(paraType);
				if (paraTypeArray != null && !paraTypeArray.isEmpty()) {
					paraTypeObject = paraTypeArray.getJSONObject(0);
				}
			}

			// 联动页面需要的参数
			String linkPage = source.get("linkPage"); // 联动页面
			JSONObject params = new JSONObject();
			if (StringUtils.isNotEmpty(linkPage)) {
				JSONArray linkPages = JSON.parseArray(linkPage);
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
				String maximize = source.get("maximize");
				params.put("maximize", maximize);
				String isDbl = source.get("isDbl");
				if (isDbl != null && "true".equalsIgnoreCase(isDbl)) {
					return null;
				}
			}
			return "function(e){ $('#" + this.getId() + "').kendoGridClick('" + this.getId() + "','" + type + "',"
					+ (paraTypeObject == null ? "''" : paraTypeObject.getString("datas")) + "," + params.toJSONString() + ");}";
			// return
			// "function(e){ pubsub.pub('"+type+"',"+(paraTypeObject==null?"''":paraTypeObject.getString("datas"))+","+params.toJSONString()+");}"
			// ;
		} else {
			return null;
		}

	}

	/**
	 * 联动控件
	 * 
	 * @return
	 */
	public String getLinkageControl() {
		return source.get("linkageControl");
	}

	/**
	 * 工具栏
	 * 
	 * @return
	 */
	public String getToolbar() {
		String toolbar = source.get("toolbar");
		JSONArray returnArray = new JSONArray();
		String template = "";
		JSONObject rJson = new JSONObject();
		if (StringUtils.isNotEmpty(toolbar)) {
			JSONArray toolbarArray = JSON.parseArray(toolbar);
			for (Object object : toolbarArray) {
				JSONObject jo = (JSONObject) object;
				if ("enable".equalsIgnoreCase(jo.getString("name"))) {
					template += "<a class=\\\"k-button\\\" gid=\\\"" + this.getId()
							+ "\\\" onclick=\\\"return glafGrid.toolbar_enable(1,this)\\\"><span class=\\\"k-icon k-update\\\"></span> 启用</a>";
				} else if ("disable".equalsIgnoreCase(jo.getString("name"))) {
					template += "<a class=\\\"k-button\\\" gid=\\\"" + this.getId()
							+ "\\\" onclick=\\\"return glafGrid.toolbar_enable(0,this)\\\"><span class=\\\"k-icon k-cancel\\\"></span> 禁用</a>";
				} else {
					rJson = new JSONObject();
					rJson.put("name", jo.getString("name"));
					rJson.put("text", jo.getString("text"));
					returnArray.add(rJson);
				}
			}
			if ("gridExternal".equalsIgnoreCase(source.get("filterType"))) {
				// 外部查询条件
				template += "<a class=\\\"k-button\\\" gid=\\\"" + this.getId() + "\\\"  style=\\\"float:right\\\"  onclick=\\\"glafGrid.hiddenSearchToolbar(this)\\\">显示</a>";
			}

			if (StringUtils.isNotEmpty(template)) {
				rJson = new JSONObject();
				rJson.put("template", template);
				returnArray.add(rJson);
			}
		}
		if (returnArray.size() == 0) {
			return null;
		}
		return returnArray.toJSONString().replaceAll("\"", "'");
	}

	/**
	 * 调整列宽
	 * 
	 * @return
	 */
	public String getResizable() {
		return source.get("resizable");
	}

	/**
	 * 数据列统计
	 * 
	 * @return
	 */
	public String getDataColunmStat() {
		String columnTemplate = source.get("columnTemplate");// 获取表头模板信息
		if (StringUtils.isNotEmpty(columnTemplate)) {
			JSONArray columnTemplateJSONArray = (JSONArray) JSON.parse(columnTemplate);
			if (columnTemplateJSONArray != null && columnTemplateJSONArray.size() > 0) {
				// 返回参数
				JSONArray retArray = new JSONArray();
				buildDataColunmStat(columnTemplateJSONArray, retArray);
				if (retArray.size() > 0) {
					return retArray.toString().replaceAll("\"", "'");
				}
			}
		}
		return null;
	}

	protected void buildDataColunmStat(JSONArray columnTemplateJSONArray, JSONArray retArray) {
		for (Object object : columnTemplateJSONArray) {
			JSONObject columnTemplateJSONObject = (JSONObject) object;
			// 子节点
			String childrens = columnTemplateJSONObject.getString("children");
			if (StringUtils.isNotEmpty(childrens)) {
				JSONArray childrensJSONArray = JSON.parseArray(childrens);
				if (childrensJSONArray != null && childrensJSONArray.size() > 0) {
					this.buildDataColunmStat(childrensJSONArray, retArray);
				}
			}
			// 当前节点的统计列
			String dataColunmStat = columnTemplateJSONObject.getString("dataColunmStat");
			if (StringUtils.isNotEmpty(dataColunmStat)) {
				JSONObject obj;
				String[] dataColunmStats = dataColunmStat.split(",");
				for (String str : dataColunmStats) {
					obj = new JSONObject();
					obj.put("field", columnTemplateJSONObject.get("columnName"));
					obj.put("aggregate", str);
					retArray.add(obj);
				}
			}
		}
	}

	/**
	 * checkbox翻页记忆选择
	 * 
	 * @return
	 */
	public String getOnDataBound() {
		if (source.get("checkboxPage") != null && "true".equalsIgnoreCase(source.get("checkboxPage"))) {
			return this.getId() + "OnDataBound";
		}
		return null;
	}

	/**
	 * 提示
	 * 
	 * @return
	 */
	public String getShowTooltip() {
		if (StringUtils.isNotEmpty(source.get("showTooltip")) && "true".equalsIgnoreCase(source.get("showTooltip"))) {
			return "glafGrid.showTooltip('" + this.getId() + "');";
		}
		return null;
	}

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

	public String getTemplateScript() {
		return this.templateScript;
	}

	public void addTemplate(String subTemplate) {
		this.templateScript += subTemplate;
	}

	@Override
	public String getElementHtml() {
		return null;
	}

	@Override
	public void setFormRules(List<FormRule> formRules) {

	}

	@Override
	public void setFormComponents(List<FormComponent> formComponents) {

	}

	/**
	 * 双击事件方法
	 */
	public String getDblClickFunction() {
		if (source.get("click") != null && !"".equals(source.get("click"))) {
			String type = StringUtils.isNotEmpty(source.get("click").toString()) ? source.get("click") : "''";
			// 获取规则 -->输入表达式定义
			String paraType = source.get("paraType");
			JSONObject paraTypeObject = null;
			if (StringUtils.isNotEmpty(paraType)) {
				JSONArray paraTypeArray = JSON.parseArray(paraType);
				if (paraTypeArray != null && !paraTypeArray.isEmpty()) {
					paraTypeObject = paraTypeArray.getJSONObject(0);
				}
			}
			// 联动页面需要的参数
			String linkPage = source.get("linkPage"); // 联动页面
			JSONObject params = new JSONObject();
			if (StringUtils.isNotEmpty(linkPage)) {
				JSONArray linkPages = JSON.parseArray(linkPage);
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
				String maximize = source.get("maximize");
				params.put("maximize", maximize);
			}
			String isDbl = source.get("isDbl");
			if (isDbl != null && "true".equalsIgnoreCase(isDbl)) {
				return "$('#" + this.getId() + "').kendoGridDblClickByType('" + this.getId() + "','" + type + "',"+ (paraTypeObject == null ? "''" : paraTypeObject.getString("datas")) + "," + params.toJSONString() + ");";
			}
			return null;
		} else {
			return null;
		}

	}
	
	
	/***
	 *  以下为自定义事件按钮事件功能 
	 */
	
	protected FormPage formPage;

	public void setFormPage(FormPage formPage) {
		this.formPage = formPage;
	}
	protected Map<String, String> scriptMap;

	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;
	}

	protected List<FormRule> formRules;

	protected List<FormComponent> formComponents;
	
	private String prefixStr = "grid";
	
	
	
	/**
	 * push 所有到集合中
	 */
	public void pushDomElement(String hidLinkImg,Elements as){
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
	
	public String getSortBy(){
		return source.get("sortBy");
	}

	public Map<String, String> getSource() {
		return source;
	}

}
