package com.glaf.platforms.rule.model.bootstrap;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.glaf.form.core.domain.FormDictory;
import com.glaf.form.core.util.FormDictoryFactory;
import org.apache.commons.lang.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.base.modules.BaseDataManager;
import com.glaf.base.modules.sys.model.Dictory;
import com.glaf.form.core.domain.FormPage;
import com.glaf.platforms.rule.HTMLExpressionConvertUtil;
import com.glaf.platforms.rule.KendoEnum;
import com.glaf.platforms.rule.model.GridModel;

public class GridBtModel extends GridModel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	protected Map<String, String> scriptMap;


	@Override
	public String getBind() {
		return super.getBind();
	}

	public void setScriptMap(Map<String, String> scriptMap) {
		this.scriptMap = scriptMap;

		/*StringBuffer SB = new StringBuffer();
		SB.append(
				"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/plugins/bootstrap/grid/css/grid.css'>");
		SB.append(
				"<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-select/css/bootstrap-select.css'>");
		SB.append("<script src='${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/bootstrap-select/js/bootstrap-select.js' type='text/javascript'></script>");
//		SB.append(
//				"<script src='${contextPath}/scripts/plugins/bootstrap/jquery.core.extends.js' type='text/javascript'></script>");
		SB.append(
				"<script src='${contextPath}/scripts/plugins/bootstrap/grid/js/jquery.grid.extends.js' type='text/javascript'></script>");

//		if (this.getPagination()) {
//			SB.append(
//					"<script src='${contextPath}/scripts/plugins/bootstrap/pagination/js/jquery.pagination.extends.js' type='text/javascript'></script>");
//		}

		scriptMap.put("gridbt", SB.toString());*/
	}

	public Boolean getPaging() {
		return Boolean.parseBoolean(source.get("serverPaging"));
	}

	public Boolean getClickUpdate() {
		return Boolean.parseBoolean(source.get("clickUpdate"));
	}
	
	public Boolean getOccupy() {
		return Boolean.parseBoolean(source.get("occupy"));
	}

	public String getElementTagName() {
		return null;
	}
	public String getCellColor() {	
		return source.get("cellcolor");
	}
	public String getClickCell() {
		if ("true".equalsIgnoreCase(source.get("clickcell"))) {
			return "true";
		}
		return "false";
	}
	public String getElementHtml() {
		
		String formHtml = formPage.getFormHtml();
		//Document doc2 = Jsoup.parse(formHtml);
		//Element ele2 = doc2.getElementById(this.getId());
		
		Document doc = Jsoup.parse(formHtml); 
				//Jsoup.parseBodyFragment("<div id=\""+this.getId()+"\" class=\""+ele2.attr("class")+"\" data-role=\"gridbt\"></div>");
		Element ele = doc.getElementById(this.getId());
//		ele.removeAttr("contenteditable");
		Elements styles = ele.getElementsByTag("style").clone();
		ele = ele.empty();
		ele.append(styles.outerHtml());
		if (!this.isVisible()) {
			ele.attr("style", "display:none;"+ele.attr("style"));
		}
		return ele.toString();
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
		
		
		
		String tableSource = source.get("tableSource");	//获取物理表设置中的数据，若是选择物理表则优先。
		String columnTemplate = source.get("columnTemplate");// 获取表头模板信息
		if(StringUtils.isNotEmpty(tableSource)){
			JSONArray tableSourceJSONArray = (JSONArray) JSON.parse(tableSource);
			if (tableSourceJSONArray != null && tableSourceJSONArray.size() > 0) {
				columnTemplate = ((JSONObject)tableSourceJSONArray.get(0)).getString("headerColumn");
			}
		}
		
		JSONObject jsonObj = null;
		JSONObject co = null;
		String locked = null;
		Boolean isLock = false;
		
		String ablecheckbox = this.source.getOrDefault("ablecheckbox", "false");
		if(ablecheckbox.equals("true")){
			jsonObj = new JSONObject();
			jsonObj.put("width", "50px");
			jsonObj.put("name", "inner_checkbox");
			jsonObj.put("type", "inner_checkbox");
			jsonObj.put("style", "text-align:left;");
			retArray.add(jsonObj);
		}
		
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
				jsonObj.put("width", StringUtils.isNotEmpty(source.get("columnsEventWidth")) ? source.get("columnsEventWidth") : "120px");
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
//						JSONObject obj = new JSONObject();
//						obj.put("columnName", datasetColumn.getString("columnName"));
//						obj.put("firstVal", ("true".equals(dictory.get(0).getValue()) ? "1" : "0"));
//						obj.put("dictoryName0",  dictory.get(0).getName());
//						obj.put("dictoryName1", dictory.get(1).getName());
//						retJson.put("editorConfig", obj);
						retJson.put("editor", editerBuild(datasetColumn));
					} else {
						retJson.put("editor", editerBuild(datasetColumn));
					}
					break;
				case "20": // 数据表
					retJson.put("editor", editerBuild(datasetColumn));
					break;
				case "30": // 用户自定义
					JSONArray widgetSourceArray = JSON.parseArray(widgetSource);
					if (("char".equalsIgnoreCase(datasetColumn.getString("FieldType")) && "1".equalsIgnoreCase(datasetColumn.getString("FieldLength"))) || "checkbox".equalsIgnoreCase(datasetColumn.getString("editor"))) {
						// 假如为布尔值（特殊处理）					
//						JSONObject obj = new JSONObject();
//						obj.put("columnName", datasetColumn.getString("columnName"));
//						obj.put("firstVal", "true".equals(widgetSourceArray.getJSONObject(0).get("value")) ? "1" : "0");
//						obj.put("dictoryName0", widgetSourceArray.getJSONObject(0).get("text"));
//						obj.put("dictoryName1", widgetSourceArray.getJSONObject(1).get("text"));
//						retJson.put("editorConfig", obj);
						
						retJson.put("editor", editerBuild(datasetColumn));
					} else {
						retJson.put("editor", editerBuild(datasetColumn));
					}
					break;
				default:
					break;
				}

			} else { // 如果未选择数据源，则直接根据editor类型生成对应的规则 需要排除布尔类型和普通文本输入类型
				retJson.put("editor", editerBuild(datasetColumn));
			}
			// 编辑 end

			// 弹出编辑页面 模板 strat
			editPageBuild(datasetColumn, popScriptTemp, filterableScriptTemp);
			// 弹出编辑页面 模板 end

			// 样式 alignment attributes: {"class": "table-cell",style:
			// "text-align: right; font-size: 14px"}
			String alignment = datasetColumn.getString("alignment");
			
			
			String styleStr = "";
			if (StringUtils.isNotEmpty(alignment) && !"left".equalsIgnoreCase(alignment)) { // 默认为left
//				JSONObject styleJson = new JSONObject();
//				styleJson.put("style", "text-align:" + alignment + ";");
//				retJson.put("attributes", styleJson);
				styleStr = "text-align:" + alignment + ";";
				if(StringUtils.equals(alignment, "center")){
					styleStr += "vertical-align:middle;";
				}
			}else{
//				JSONObject styleJson = new JSONObject();
//				styleJson.put("style", "text-align:left;");
//				retJson.put("attributes", styleJson);
				styleStr = "text-align:left;";
			}

			// isSortable 是否排序 默认true
			String isSortable = datasetColumn.getString("isSortable");
			if (!"true".equalsIgnoreCase(isSortable)) {
				retJson.put("sortable", false);
			}
			// isSortable 是否排序 默认true
			String isEditor = datasetColumn.getString("isEditor");
			if (!"false".equalsIgnoreCase(isEditor)) {
				retJson.put("isEditor", true);
			}
			// isMenu 是否可隐藏 默认true
			String isMenu = datasetColumn.getString("isMenu");
			if (!"true".equalsIgnoreCase(isMenu)) {
				retJson.put("menu", false);
			}

			buildFilterable(datasetColumn, retJson, editor, widgetSourceType);

			String formatValue = datasetColumn.getString("formatValue");//显示格式
			if (StringUtils.isNotEmpty(formatValue)) { 
				retJson.put("format",formatValue);
			}

			// 处理模板编辑
			String templateScript = buildExpression(datasetColumn, retJson.getString("template"), expScript);
			if (StringUtils.isNotEmpty(templateScript)) {
				retJson.put("template", "##=" + templateScript + "=##");
			}

			String columnWidth = datasetColumn.getString("columnWidth");
			Integer icolumnWidth = null ;
			try {
				icolumnWidth = Integer.parseInt(columnWidth) ;
			} catch (Exception e) {
			}
			if(icolumnWidth!=null){
				columnWidth += "%";
			}
			retJson.put("width", StringUtils.isNotEmpty(columnWidth) ? columnWidth : "120px");
			
			//加上列宽调整,非滚动条
			if(jsonObj.get("width") != null){
				styleStr += "width:" + jsonObj.getString("width")+";";
			};
			
			JSONObject styleJson = new JSONObject();
			styleJson.put("style", styleStr);
			retJson.put("attributes", styleJson);

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
			sb.append("' " + dictorysMap.get(string).getName() + ":'+(data.").append(jsonObj.get("columnName")).append(" ? hmtdUtils.format(").append("'" + key + "'").append(",data.")
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
	
	/**
	 * cell单元格editor编辑器构建
	 * @param jsonObj
	 * @return
	 */
	protected String editerBuild(JSONObject jsonObj) {
		JSONObject obj = new JSONObject();
		//默认选中项
		if(StringUtils.isNotEmpty(jsonObj.getString("defaultIndex"))){
			obj.put("defaultIndex", jsonObj.getString("defaultIndex"));
		}
		// 必填
		if (StringUtils.isNotEmpty(jsonObj.getString("isRequired")) && "true".equalsIgnoreCase(jsonObj.getString("isRequired"))) {
//			editorFun.append(".attr('required', 'required')");
//			editorFun.append(".attr('data-required-msg', '" + jsonObj.getString("title") + " 为必填项')");
			obj.put("required",true);
			obj.put("data-required-msg", jsonObj.getString("title") + " 为必填项");
		}
		// 正则验证(范围不算)
		if (StringUtils.isNotEmpty(jsonObj.getString("dataValidation")) && !"range".equalsIgnoreCase(jsonObj.getString("dataValidation"))) {
//			editorFun.append(".attr('pattern', '" + jsonObj.getString("dataValidation") + "')");
//			String validateMsg = jsonObj.getString("validateMsg");
//			String dataPatternMsg = jsonObj.getString("title") + " 非法输入";
//			if (StringUtils.isNotEmpty(validateMsg)) {
//				dataPatternMsg = validateMsg;
//			}
//			editorFun.append(".attr('data-pattern-msg', '" + dataPatternMsg + "')");
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
	 * 验证控件 数据源
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
	
	/**
	 * 内容不折行
	 * @return
	 */
	public String getShowInLine(){
		String showInLine = source.get("showInLine");
		return showInLine != null && "false".equalsIgnoreCase(showInLine) ? "false" : "true";
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
	public String getDefaultTimePicker(){
		return source.get("defaultTimePicker");
		
	}
	public String getAscColor(){
		return source.get("ascColor");
	}
	public String getDescColor(){
		return source.get("descColor");
	}
	public String getDragEnable(){
		return source.getOrDefault("dragEnable", "false");
		
	}
	/**
	 * 是否反序 用于调整节点顺序时候处理
	 * @return
	 */
	public String getSortDesc(){
		return source.get("isDesc");
	}
	
	
	public String getCombineAble(){
		return source.getOrDefault("combineAble", "flase");
	}
	
	public String getCombinedField(){
		return source.getOrDefault("combinedField", "");
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
	public String getPagePosition(){
		return source.get("pagePostion");
	}
	
	public String getNopersistent(){
		return source.get("nopersistent");
	}
}
