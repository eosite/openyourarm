package com.glaf.platforms.rule.model;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.config.SystemProperties;
import com.glaf.core.context.ContextFactory;
import com.glaf.dep.base.domain.DepBaseProp;
import com.glaf.dep.base.service.DepBasePropService;
import com.glaf.dep.report.domain.DepReportTemplate;
import com.glaf.dep.report.service.DepReportTemplateService;
import com.glaf.form.rule.Global;
import com.glaf.platforms.rule.model.cell.ICellModel;
import com.glaf.template.util.TemplateUtils;
import com.mchange.io.FileUtils;

public class CellModel extends CommonModel {

	private DepReportTemplateService depReportTemplateService = ContextFactory.getBean("com.glaf.dep.report.service.depReportTemplateService");

	private DepBasePropService depBasePropService = ContextFactory.getBean("com.glaf.dep.base.service.depBasePropService");

	/**
	 * 解析cell 表格
	 */
	void convert(StringBuffer SB) {

		Long cellPageId = this.getCellPageId();

		if (cellPageId != null) {
			DepReportTemplate depReportTemplate = depReportTemplateService.getDepReportTemplate(cellPageId);

			if (depReportTemplate != null) {

				String ruleJson = depReportTemplate.getRuleJson();

				if (StringUtils.isNotBlank(ruleJson)) {
					try {

						this.iteratorConvert(JSONObject.parseObject(ruleJson), SB);

					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	/**
	 * 递归cell 页面所有控件
	 * 
	 * @param ruleJsonObject
	 * @param SB
	 * @throws Exception
	 */
	private void iteratorConvert(JSONObject ruleJsonObject, StringBuffer SB) throws Exception {
		String type;
		JSONObject ctrlJson;
		Class<? extends ICellModel> clazz;
		String pageKey = "page";

		List<DepBaseProp> list = depBasePropService.getDepBasePropsByCategoryCode(Global.PROPERTIES);

		List<DepBaseProp> quoteList = depBasePropService.getDepBasePropsByCategoryCode(Global.QUOTE_PROPERTIES);

		// 由于规则没有拆分，引用关系需要做到页面上。暂时先这样处理
		JSONObject quoteRuleObj = new JSONObject();
		JSONObject pageObj = new JSONObject();
		for (String key : ruleJsonObject.keySet()) { // 遍历cell
			ctrlJson = ruleJsonObject.getJSONObject(key);
			if (StringUtils.isNotBlank(type = ctrlJson.getString(Global.TYPE))) {
				clazz = ICellModel.MODELS.get(type);
				if (clazz != null) {
					ICellModel cModel = clazz.newInstance();
					cModel.setDepBaseProps(list);
					cModel.setQuoteProps(quoteList);

					JSONObject o = cModel.convert(ctrlJson);
					if (o != null) {
						o.put("id", key);
						if (pageKey.equalsIgnoreCase(key)) {
							pageObj = o;
						} else {
							// 初始化各参数的引用汇集
							// initQuoteProps(ctrlJson, quoteRuleObj,
							// quoteList,key);
							SB.append(String.format("%sCell.call(s, %s);", type, o.toString()));
						}
					}
				}
			}
		}
		if (!quoteRuleObj.isEmpty()) {
			pageObj.put("quote", quoteRuleObj);
		}
		SB.append(String.format("%sCell.call(s, %s);", pageKey, pageObj.toString()));
	}

	/**
	 * 初始化引用
	 * 
	 * @param rule
	 * @param ret
	 */
	private void initQuoteProps(JSONObject rule, JSONObject quoteRuleObj, List<DepBaseProp> quoteProps, String key) {
		if (CollectionUtils.isNotEmpty(quoteProps)) {
			JSONArray ruleAry = null;
			for (DepBaseProp dbp : quoteProps) {
				String quoteRule = rule.getString(dbp.getRuleCode());
				if (StringUtils.isNotEmpty(quoteRule) && !"A001-2-006".equals(dbp.getRuleCode())) {
					ruleAry = JSON.parseArray(quoteRule);
					JSONObject ruleObj = ruleAry.getJSONObject(0);
					String dynamicId = ruleObj.getString("dynamicId");
					JSONObject datasObj = ruleObj.getJSONObject("datas");
					if (datasObj != null && !datasObj.isEmpty()) {
						for (String dataKey : datasObj.keySet()) {
							JSONArray dataAry = datasObj.getJSONArray(dataKey);
							if (CollectionUtils.isNotEmpty(dataAry)) {
								for (Object object : dataAry) {
									JSONObject dataObj = (JSONObject) object;
									// quoteObj.
									String datasetId = dataObj.getString("datasetId");
									String outid = dataObj.getString("outid");
									String value = dataObj.getString("value");
									JSONObject quote;
									JSONArray ary;
									if (quoteRuleObj.containsKey(dynamicId)) {
										quote = quoteRuleObj.getJSONObject(dynamicId);
										if (quote.containsKey(outid)) {
											ary = quote.getJSONArray(outid);
										} else {
											ary = new JSONArray();
										}
									} else {
										quote = new JSONObject();
										quoteRuleObj.put(dynamicId, quote);
										ary = new JSONArray();
									}
									JSONObject obj = new JSONObject();
									if (value == null) {
										obj.put("inid", dataObj.getString("inid"));
									} else {
										obj.put("value", value);
									}
									obj.put("id", key);
									// obj.put("param", outid);
									ary.add(obj);
									quote.put(outid, ary);
								}
							}
						}
						break;
					}
				}
			}
		}
	}

	public String getQueueFuncs() {
		StringBuffer sBuffer = new StringBuffer(String.format("$%sQueue.add(function(s){", super.getId()));

		this.convert(sBuffer);

		return sBuffer.append("});").toString();
	}

	public String getTemplateScript() {
		StringBuffer style = new StringBuffer();

		style.append("<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/spreadjs/html/lib/spread/gc.spread.sheets.excel2013white.10.0.1.css'>");
		style.append("<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/bootstrap/css/bootstrap.min.css'>");

		style.append("<script src='${contextPath}/scripts/spreadjs/html/lib/spread/gc.spread.sheets.all.10.0.1.min.js'></script>");
		style.append("<script src='${contextPath}/scripts/spreadjs/html/lib/spread/gc.spread.sheets.resources.zh.10.0.1.min.js'></script>");
		style.append("<script src='${contextPath}/scripts/spreadjs/html/lib/spread/gc.spread.sheets.print.10.0.1.min.js'></script>");

		style.append("<script src='${contextPath}/scripts/kendo/cell/spread.cell.extend.js'></script>");
		style.append("<script src='${contextPath}/scripts/spreadjs/spreadjs.ext.js'></script>");
		String path = SystemProperties.getConfigRootPath() + "/conf/templates/form/dynamicCellScript.tmp";

		File file = new File(path);
		String cellString = null;
		try {
			cellString = FileUtils.getContentsAsString(file);
		} catch (IOException e) {
			cellString = style.toString();
		}

		if ("true".equals(this.getReportModel())) {
			scriptMap.put("spreadjs-cell-css", "<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/spreadjs/spreadjs.cell.css'/>");
			StringBuffer sb = new StringBuffer();
			sb.append("<link rel='stylesheet' type='text/css' href='${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-minicolors/jquery.minicolors.css'/>");
			sb.append("<script src='${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/jquery-minicolors/jquery.minicolors.min.js'></script>");
			scriptMap.put("minicolors", sb.toString());

			StringBuffer rs = new StringBuffer();
			rs.append(
					"<link href=\"${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.css\" rel=\"stylesheet\" type=\"text/css\" />");
			rs.append(
					"<link href=\"${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/css/ion.rangeSlider.skinSimple.css\" rel=\"stylesheet\" type=\"text/css\" />");
			rs.append("<script src=\"${contextPath}/scripts/metronic/4.5.2/theme/assets/global/plugins/ion.rangeslider/js/ion.rangeSlider.min.js\" type=\"text/javascript\"/>");
			scriptMap.put("rangeSlider", rs.toString());
		}

		scriptMap.put("spreadjs-cell", cellString);
		
		scriptMap.put("exif","<script type='text/javascript' src='${contextPath}/scripts/plugins/bootstrap/exif/js/exif.js'></script>");

		return this.templateScript;
	}

	public Long getCellPageId() {
		String selectPageString = super.getSource("linkPage", "");
		if (StringUtils.isNotBlank(selectPageString)) {
			JSONArray jsonArray = JSONArray.parseArray(selectPageString);
			JSONObject jObject = jsonArray.getJSONObject(0);
			return jObject.getLongValue("id");
		}
		return null;
	}

	public String getElementTagName() {
		if ("true".equals(this.getReportModel())) {
			return null;
		}
		return "div";
	}

	public String getElementHtml() {
		if ("true".equals(this.getReportModel())) {
			return buildHtml();
		}
		return null;
	}

	/**
	 * 是否显示
	 * 
	 * @return
	 */
	public String getVisible() {
		if ("true".equalsIgnoreCase(getReportModel())) {
			return null;
		}
		String visible = source.get("visible");
		if ("false".equalsIgnoreCase(visible)) {
			return "none";
		}
		return null;
	}

	/**
	 * 报表模式
	 * 
	 * @return
	 */
	public String getReportModel() {
		return source.getOrDefault("reportModel", "false");
	}

	private String buildHtml() {
		String path = SystemProperties.getConfigRootPath() + "/conf/templates/cell/cell_tool.tmp";
		File file = new File(path);
		String cellString = null;
		try {
			cellString = FileUtils.getContentsAsString(file, "UTF8");
			Map<String, Object> context = new HashMap<>();
			context.put("id", this.getId());

			String style = " ";
			/*String width = source.getOrDefault("width", null);
			if (width != null) {
				style += "width:" + width + ";";
			}
			String height = source.getOrDefault("height", null);
			if (height != null) {
				style += "height:" + height + ";";
			}*/

			String visible = source.getOrDefault("visible", null);
			if ("false".equalsIgnoreCase(visible)) {
				style += "display:none;";
			}
			
			context.put("styles", style);
			context.put("toolbars", tools());
			cellString = TemplateUtils.process(context, cellString);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
		return cellString;
	}

	private JSONArray tools() {
		JSONArray toolBarAry = new JSONArray();
		
		String reportToolsStr = source.getOrDefault("reportTools", null);
		if(StringUtils.isNotEmpty(reportToolsStr)){
			JSONArray parseArray = JSON.parseArray(reportToolsStr);
			if(!parseArray.isEmpty()){
				return toolBarAry = parseArray.getJSONObject(0).getJSONArray("tools");
			}
		}

		String path = SystemProperties.getConfigRootPath() + "/conf/templates/cell/cell_default_tool.json";
		File file = new File(path);
		String celltool = null;
		try {
			celltool = FileUtils.getContentsAsString(file, "UTF8");
			toolBarAry = JSON.parseArray(celltool);
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}

		return toolBarAry;
	}

	@Override
	public String getWidth() {
		/*if ("true".equalsIgnoreCase(getReportModel())) {
			return "100%";
		}*/
		return super.getWidth();
	}

	@Override
	public String getHeight() {
		/*if ("true".equalsIgnoreCase(getReportModel())) {
			return "100%";
		}*/
		return super.getHeight();
	}

	public static void main(String[] args) {

		String path = SystemProperties.getConfigRootPath() + "/conf/templates/cell/cell_tool.tmp";
		File file = new File(path);
		String cellString = null;
		try {
			cellString = FileUtils.getContentsAsString(file);
		} catch (IOException e) {
			e.printStackTrace();
		}

		JSONArray ary = new JSONArray();
		JSONObject obj = new JSONObject();
		obj.put("toolBarName", "系统");

		JSONArray toolsAry = new JSONArray();

		JSONObject toolObj = new JSONObject();
		toolObj.put("icon", "save-icon");
		toolObj.put("toolName", "保存");
		toolsAry.add(toolObj);

		toolObj = new JSONObject();
		toolObj.put("icon", "first-icon");
		toolObj.put("toolName", "第一页");
		toolsAry.add(toolObj);

		obj.put("tools", toolsAry);

		ary.add(obj);
		ary.add(obj);
		ary.add(obj);

		Map<String, Object> context = new HashMap<>();
		context.put("id", "cell1");
		context.put("toolbars", ary);
		String oo = TemplateUtils.process(context, cellString);
		System.out.println(oo);

	}
}
