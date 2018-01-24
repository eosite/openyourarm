package com.glaf.form.core.util;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormDataset;
import com.glaf.form.core.domain.FormDatasetRelation;
import com.glaf.form.core.domain.FormOutexpRelation;
import com.glaf.form.core.domain.FormParams;
import com.glaf.form.core.domain.FormParamsRelation;
import com.glaf.form.core.query.FormDatasetQuery;
import com.glaf.form.core.query.FormDatasetRelationQuery;
import com.glaf.form.core.query.FormOutexpRelationQuery;
import com.glaf.form.core.query.FormParamsRelationQuery;
import com.glaf.form.core.service.FormDatasetRelationService;
import com.glaf.form.core.service.FormDatasetService;
import com.glaf.form.core.service.FormOutexpRelationService;
import com.glaf.form.core.service.FormParamsRelationService;
import com.glaf.form.core.service.FormParamsService;

@Component("formParamsUtil")
public class FormParamsUtil {

	public FormParamsUtil() {

	}

	// 事件定义器
	private static final String EVENTS_DEFINED = "eventsDefined";
	// 数据集
	private static final String DATASOURCESET = "dataSourceSet";
	//数据集(特殊的)
	private static final String DATASOURCESETBYVIDEOS = "dataSourceSetByVideos";
	
	private static final String DATASOURCESETBYDIAGRAM = "dataSourceSetByDiagram";
	// 输入参数定义
	private static final String IN_PARAM_DEFINED = "inParamDefined";
	// 输入表达式定义
	private static final String IN_PARAMMETERS = "inParameters";
	// HTML样式定义
	private static final String HTML_DEFINED = "HTMLDefined";
	// 值字段
	private static final String DATAVALUEFIELD = "datavaluefield";
	// 显示字段
	private static final String DATATEXTFIELD = "datatextfield";
	// 数据样式定义
	private static final String DATALINKPAGE = "dataLinkPage";
	// 工作流信息
	private static final String DATASOURCESETBYFLOW = "dataSourceSetByFlow";
	// 控件定义
	private static final String CUSTOMDEFINED = "customDefined";
	// 控件定义
	private static final String STEPDEFINED = "stepDefined";
	// 固定数据集
	private static final String FIXDATASOURCE = "fixDataSource";
	// 验证器定义
	private static final String VALIDATE = "validate";
	// 输入输出关系定义
	private static final String PARATYPE = "paraType";

	// （引用数据集）
	public final static String[] FORM_RELATION = { IN_PARAM_DEFINED, IN_PARAMMETERS, EVENTS_DEFINED, DATASOURCESET,
			HTML_DEFINED, DATAVALUEFIELD, DATATEXTFIELD, DATALINKPAGE, DATASOURCESETBYFLOW, CUSTOMDEFINED,
			FIXDATASOURCE, STEPDEFINED, VALIDATE, PARATYPE, DATASOURCESETBYVIDEOS, DATASOURCESETBYDIAGRAM };

	@Autowired
	protected FormParamsService formParamsService;
	@Autowired
	protected FormParamsRelationService formParamsRelationService;
	@Autowired
	protected FormDatasetService formDatasetService;
	@Autowired
	protected FormDatasetRelationService formDatasetRelationService;
	//扩展信息的引用关系，主要位于事件定义器中。
	@Autowired
	protected FormOutexpRelationService formOutexpRelationService;

	/**
	 * 保存形参引用参数列表
	 * 
	 * @param list
	 * @param ruleId
	 */
	private void saveFormParams(List<FormParams> list, String ruleId) {
		List<String> params = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			for (FormParams formParams : list) {
				if(formParams.getParamName()!=null){
					params.add(formParams.getParamName());
				}
				formParamsService.saveOrUpdate(formParams);
			}
		}
		formParamsService.deleteByParams(params, ruleId);
	}

	/**
	 * 保存数据集参数列列表
	 * 
	 * @param list
	 * @param ruleId
	 * @param relationList
	 * @param attrName
	 */
	private void saveFormDataset(List<FormDataset> list, String ruleId, List<String> relationList, String attrName) {
		List<String> params = new ArrayList<String>();
		List<String> pids = new ArrayList<String>();
		if (list != null && !list.isEmpty()) {
			FormDatasetRelation formDatasetRelation = null;
			for (FormDataset formDataset : list) {
				params.add(formDataset.getColumnName());
				formDatasetService.saveOrUpdate(formDataset);
				if (relationList != null && !relationList.isEmpty()) {
					if (relationList.contains(formDataset.getColumnName())) {
						formDatasetRelation = new FormDatasetRelation();
						pids.add(formDataset.getId());
						formDatasetRelation.setPageId(formDataset.getPageId());
						formDatasetRelation.setPid(formDataset.getId());
						formDatasetRelation.setWidgetId(formDataset.getWidgetId());
						formDatasetRelation.setAttrName(attrName);
						formDatasetRelationService.saveOrUpdate(formDatasetRelation);
					}
				}
			}
		}
		if (!pids.isEmpty()) {
			formDatasetRelationService.deleteByColumns(pids, ruleId, attrName);
		}
		if (!params.isEmpty()) {
			formDatasetService.deleteByColumns(params, ruleId);
		}
	}

	/**
	 * 主初始化方法调用
	 * 
	 * @param key
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 * @return
	 */
	public boolean init(String key, String rule, String ruleId, String pageId) {
		try {
			Class<?> clazz = /* FormParamsUtil.class */this.getClass();
			Object[] args = { rule, ruleId, pageId };
			Method tagName = ReflectionUtils.findMethod(clazz, key, String.class, String.class, String.class);
			if (tagName != null) {
				// Object model = clazz.newInstance();
				tagName.invoke(this, args);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public static void main(String[] args) {
		FormParamsUtil formParamsUtil = new FormParamsUtil();
		formParamsUtil.init("inParamDefined", "[{\"param\":111}]", null, null);
	}

	/**
	 * 输入形参定义
	 * 
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void inParamDefined(String rule, String ruleId, String pageId) {
		List<FormParams> list = new ArrayList<FormParams>();
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			FormParams formParams = null;
			for (Object object : ary) {
				obj = (JSONObject) object;
				formParams = new FormParams();
				formParams.setWidgetId(ruleId);
				formParams.setPageId(pageId);
				formParams.setParamName(obj.getString("param"));
				formParams.setDatasetId(obj.getString("datasetId"));
				list.add(formParams);
			}
			saveFormParams(list, ruleId);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 解析表达式引用
	 * 
	 * @param expressionStr
	 * @param relationList
	 */
	public void parseExpression(String expressionStr, List<String> relationList, String styleName) {
		if (StringUtils.isNotEmpty(expressionStr)) {
			JSONObject expObj = JSON.parseObject(expressionStr);
			String htmlVal = expObj.getString(styleName);
			JSONArray varValAry = expObj.getJSONArray("varVal");
			if (varValAry != null && !varValAry.isEmpty() && htmlVal != null) {
				JSONObject varValObj = null;
				JSONObject valObj = null;
				String code = null;
				for (Object object : varValAry) {
					varValObj = (JSONObject) object;
					valObj = varValObj.getJSONObject("value");
					code = valObj.getString("code");
					if (htmlVal.indexOf(code) > -1) {
						relationList.add(code.replace("~V{", "~F{"));
					}
				}
			}
		}
	}

	/**
	 * 数据集
	 * 
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void dataSourceSetByDiagram(String rule, String ruleId, String pageId) {
		List<FormDataset> list = new ArrayList<FormDataset>();
		List<String> relationList = new ArrayList<String>();
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray columns = new JSONArray();
			JSONObject column = null;
			FormDataset formDataset = null;
			boolean flag = true;
			for (Object object : ary) {
				obj = (JSONObject) object;
				columns = obj.getJSONArray("columns");
				if (columns == null) {
					columns = obj.getJSONArray("selectColumns");
					flag = false;
				}
				for (Object colObj : columns) {
					column = (JSONObject) colObj;
					formDataset = new FormDataset();
					formDataset.setWidgetId(ruleId);
					formDataset.setPageId(pageId);
					formDataset.setColumnName(column.getString("code"));
					if (flag) {
						formDataset.setDatasetId(obj.getString("datasetId"));
					} else {
						formDataset.setDatasetId(column.getString("datasetId"));
					}
					list.add(formDataset);

					String hidLinkImg = column.getString("hidLinkImg");
					if (StringUtils.isNotEmpty(hidLinkImg)) {
						JSONArray hidAry = JSON.parseArray(hidLinkImg);
						if (hidAry != null && !hidAry.isEmpty()) {
							JSONObject hidObj = null;
							String expdataStr = null;
							String htmldataStr = null;
							for (Object object2 : hidAry) {
								hidObj = (JSONObject) object2;
								expdataStr = hidObj.getString("expdata");
								parseExpression(expdataStr, relationList, "expActVal");
								htmldataStr = hidObj.getString("htmldata");
								parseExpression(htmldataStr, relationList, "htmlVal");
							}
						}
					}
				}
				saveFormDataset(list, ruleId, relationList, DATASOURCESETBYDIAGRAM);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * 数据集
	 * 
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void dataSourceSetByVideos(String rule, String ruleId, String pageId) {
		List<FormDataset> list = new ArrayList<FormDataset>();
		List<String> relationList = new ArrayList<String>();
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray columns = new JSONArray();
			JSONObject column = null;
			FormDataset formDataset = null;
			boolean flag = true;
			for (Object object : ary) {
				obj = (JSONObject) object;
				columns = obj.getJSONArray("columns");
				if (columns == null) {
					columns = obj.getJSONArray("selectColumns");
					flag = false;
				}
				for (Object colObj : columns) {
					column = (JSONObject) colObj;
					formDataset = new FormDataset();
					formDataset.setWidgetId(ruleId);
					formDataset.setPageId(pageId);
					formDataset.setColumnName(column.getString("code"));
					if (flag) {
						formDataset.setDatasetId(obj.getString("datasetId"));
					} else {
						formDataset.setDatasetId(column.getString("datasetId"));
					}
					list.add(formDataset);

					String hidLinkImg = column.getString("hidLinkImg");
					if (StringUtils.isNotEmpty(hidLinkImg)) {
						JSONArray hidAry = JSON.parseArray(hidLinkImg);
						if (hidAry != null && !hidAry.isEmpty()) {
							JSONObject hidObj = null;
							String expdataStr = null;
							String htmldataStr = null;
							for (Object object2 : hidAry) {
								hidObj = (JSONObject) object2;
								expdataStr = hidObj.getString("expdata");
								parseExpression(expdataStr, relationList, "expActVal");
								htmldataStr = hidObj.getString("htmldata");
								parseExpression(htmldataStr, relationList, "htmlVal");
							}
						}
					}
				}
				saveFormDataset(list, ruleId, relationList, DATASOURCESETBYVIDEOS);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 数据集
	 * 
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void dataSourceSet(String rule, String ruleId, String pageId) {
		List<FormDataset> list = new ArrayList<FormDataset>();
		List<String> relationList = new ArrayList<String>();
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray columns = new JSONArray();
			JSONObject column = null;
			FormDataset formDataset = null;
			boolean flag = true;
			for (Object object : ary) {
				obj = (JSONObject) object;
				columns = obj.getJSONArray("columns");
				if (columns == null) {
					columns = obj.getJSONArray("selectColumns");
					flag = false;
				}
				for (Object colObj : columns) {
					column = (JSONObject) colObj;
					formDataset = new FormDataset();
					formDataset.setWidgetId(ruleId);
					formDataset.setPageId(pageId);
					formDataset.setColumnName(column.getString("code"));
					if (flag) {
						formDataset.setDatasetId(obj.getString("datasetId"));
					} else {
						formDataset.setDatasetId(column.getString("datasetId"));
					}
					list.add(formDataset);

					String hidLinkImg = column.getString("hidLinkImg");
					if (StringUtils.isNotEmpty(hidLinkImg)) {
						JSONArray hidAry = JSON.parseArray(hidLinkImg);
						if (hidAry != null && !hidAry.isEmpty()) {
							JSONObject hidObj = null;
							String expdataStr = null;
							String htmldataStr = null;
							for (Object object2 : hidAry) {
								hidObj = (JSONObject) object2;
								expdataStr = hidObj.getString("expdata");
								parseExpression(expdataStr, relationList, "expActVal");
								htmldataStr = hidObj.getString("htmldata");
								parseExpression(htmldataStr, relationList, "htmlVal");
							}
						}
					}
				}
				saveFormDataset(list, ruleId, relationList, DATASOURCESET);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存数据集引用关系
	 * 
	 * @param inid
	 * @param inpage
	 * @param idatasetId
	 * @param columnName
	 * @param pageId
	 * @param ruleId
	 */
	private void saveDatasetRelation(String inid, String inpage, String idatasetId, String columnName, String pageId,
			String ruleId) {
		FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
		formDatasetRelation.setPageId(pageId);
		formDatasetRelation.setWidgetId(ruleId);
		formDatasetRelation.setAttrName(EVENTS_DEFINED);
		formDatasetRelationService.saveByParam(formDatasetRelation, idatasetId, inpage, null, inid, columnName);
	}

	/**
	 * 保存输出控件事件编辑器
	 * 
	 * @param widgetEventObj
	 * @param pageId
	 * @param ruleId
	 */
	private void saveWidgetEventObj(JSONArray widgetEventAry, String pageId, String ruleId) {
		JSONObject widgetEventObj = null;
		JSONObject paramObj = null;
		JSONObject datasObj = null;
		JSONArray inOutParams = null;
		JSONObject inOutParamObj = null;
		JSONObject outExt = null; //扩展信息，如更新集等信息
		for (Object object2 : widgetEventAry) {
			widgetEventObj = (JSONObject) object2;
			//扩展信息
			outExt = widgetEventObj.getJSONObject("outExt");
			if(outExt!=null && !outExt.isEmpty()){
				//扩展信息不为空时保存对应的扩展信息
				String type = outExt.getString("type");	//扩展类型
				String value = outExt.getString("value");	//对应的扩展值
				FormOutexpRelation formOutexpRelation = new FormOutexpRelation();
				formOutexpRelation.setPageId(pageId);
				formOutexpRelation.setWidgetId(ruleId);
				formOutexpRelation.setType(type);
				formOutexpRelation.setValue(value);
				formOutexpRelationService.save(formOutexpRelation);
			}
			
			// 表达式
			String expdataStr = widgetEventObj.getString("expdata");
			if (StringUtils.isNotEmpty(expdataStr)) {
				JSONObject expObj = JSON.parseObject(expdataStr);
				String htmlVal = expObj.getString("expActVal");
				JSONArray varValAry = expObj.getJSONArray("varVal");
				if (varValAry != null && !varValAry.isEmpty() && htmlVal != null) {
					JSONObject varValObj = null;
					JSONObject valObj = null;
					String code = null;
					for (Object oo : varValAry) {
						varValObj = (JSONObject) oo;
						valObj = varValObj.getJSONObject("value");
						code = valObj.getString("code");
						String datasetId = valObj.getString("datasetId");
						if (htmlVal.indexOf(code) > -1 && StringUtils.isNotEmpty(datasetId)) {
							saveDatasetRelation(valObj.getString("eleId"), valObj.getString("pageId"), datasetId, code,
									pageId, ruleId);
						}
					}
				}
			}

			// 输入输出参数
			String paramStr = widgetEventObj.getString("param");
			JSONArray paramAry = JSON.parseArray(paramStr);
			if (paramAry != null && !paramAry.isEmpty()) {
				for (Object object3 : paramAry) {
					paramObj = (JSONObject) object3;
					datasObj = paramObj.getJSONObject("datas");
					Set<String> keys = datasObj.keySet();
					for (String key : keys) {
						inOutParams = datasObj.getJSONArray(key);
						for (Object object4 : inOutParams) {
							inOutParamObj = (JSONObject) object4;
							String inid = inOutParamObj.getString("inid");
							// inOutParamObj.getString("columnName");
							if(inid != null && !inid.equals("")){
								String inpage = inOutParamObj.getString("inpage");
								String idatasetId = inOutParamObj.getString("idatasetId");
								String columnName = inOutParamObj.getString("icode");
								saveDatasetRelation(inid, inpage, idatasetId, columnName, pageId, ruleId);
							}else{
								JSONObject data = inOutParamObj.getJSONObject("data");
								JSONArray varValAry = data.getJSONArray("varVal");
								JSONObject varValObj = null;
								JSONObject valObj = null;
								String code = null;
								for (Object oo : varValAry) {
									varValObj = (JSONObject) oo;
									valObj = varValObj.getJSONObject("value");
									code = valObj.getString("code");
									String datasetId = valObj.getString("datasetId");
									saveDatasetRelation(valObj.getString("eleId"), valObj.getString("pageId"), datasetId, code,
												pageId, ruleId);
								}
								
							}
							// 参数引用
							String outid = inOutParamObj.getString("outid");
							String param = inOutParamObj.getString("param");
							String outPage = inOutParamObj.getString("outpage");
							FormParamsRelation fromParamsRelation = new FormParamsRelation();
							fromParamsRelation.setPageId(pageId);
							fromParamsRelation.setWidgetId(ruleId);
							fromParamsRelation.setAttrName(EVENTS_DEFINED);
							formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, param,
									null);
						}
					}
				}
			}
			// 下级事件
			JSONArray childs = widgetEventObj.getJSONArray("childs");
			if (childs != null && !childs.isEmpty()) {
				saveWidgetEventObj(childs, pageId, ruleId);
			}
		}
	}

	/**
	 * 清除某属性的引用规则
	 * 
	 * @param ruleId
	 * @param pageId
	 * @param attrName
	 *            属性名称
	 */
	private void clear(String ruleId, String pageId, String attrName) {
		FormDatasetRelationQuery deleteQuery = new FormDatasetRelationQuery();
		deleteQuery.setAttrName(attrName);
		deleteQuery.setPageId(pageId);
		deleteQuery.setWidgetId(ruleId);
		formDatasetRelationService.delete(deleteQuery);
		FormParamsRelationQuery delete2Query = new FormParamsRelationQuery();
		delete2Query.setAttrName(attrName);
		delete2Query.setPageId(pageId);
		delete2Query.setWidgetId(ruleId);
		formParamsRelationService.delete(delete2Query);
		
		FormOutexpRelationQuery formOutexpRelationQuery = new FormOutexpRelationQuery();
		formOutexpRelationQuery.setPageId(pageId);
		formOutexpRelationQuery.setWidgetId(ruleId);
		formOutexpRelationService.delete(formOutexpRelationQuery);
	}

	/**
	 * 事件定义器
	 * 
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void eventsDefined(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, EVENTS_DEFINED);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray values = null;
			JSONObject value = null;
			String widgetEventStr = null;
			JSONArray widgetEventAry = null;
			for (Object object : ary) {
				obj = (JSONObject) object;
				values = obj.getJSONArray("values");
				for (Object colObj : values) {
					value = (JSONObject) colObj;
					widgetEventStr = value.getString("widgetEvent");
					widgetEventAry = JSON.parseArray(widgetEventStr);
					if (widgetEventAry != null && !widgetEventAry.isEmpty()) {
						saveWidgetEventObj(widgetEventAry, pageId, ruleId);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * HTML样式定义
	 * 无联动，只引用当前控件的数据集的字段信息
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void HTMLDefined(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, HTML_DEFINED);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray values = null;
			JSONObject value = null;
			for (Object object : ary) {
				obj = (JSONObject) object;
				values = obj.getJSONObject("htmldata").getJSONArray("varVal");
				for (Object colObj : values) {
					// pid由formDatasetRelationService.saveByParam
					// 的时候自动获取，故不需要fromDataSetRelation不需要pid参数
					// inid为控件Id，用于查找到对应的规则ID
					value = ((JSONObject) colObj).getJSONObject("value");
					// 数据集Id
					String datasetId = value.getString("datasetId");
					// 数据集字段
					String columnName = value.getString("code");
					// 保存引用关系
					FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
					formDatasetRelation.setPageId(pageId);
					formDatasetRelation.setWidgetId(ruleId);
					formDatasetRelation.setAttrName(HTML_DEFINED);
					formDatasetRelationService.saveByParam(formDatasetRelation, datasetId, pageId, ruleId, "",
							columnName);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输入表达式
	 * 引用当前控件的数据集字段及输入参数
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void inParameters(String rule, String ruleId, String pageId){
		clear(ruleId, pageId, IN_PARAMMETERS);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray collections = null;	//表达式集
			JSONObject collection = null;	//表达式集
			JSONObject fieldData = null;	//数据
			JSONObject paramData = null;	//数据
			JSONArray values = null;
			JSONObject value = null;
			for (Object object : ary) {
				obj = (JSONObject) object;
				
				collections = obj.getJSONArray("collection");
				for(Object objItem : collections){
					collection = (JSONObject)objItem;
					fieldData = collection.getJSONObject("fieldData");
					paramData = collection.getJSONObject("paramData");
					
					if(fieldData != null){
						values = fieldData.getJSONArray("varVal");
						for (Object colObj : values) {
							// pid由formDatasetRelationService.saveByParam
							// 的时候自动获取，故不需要fromDataSetRelation不需要pid参数
							// inid为控件Id，用于查找到对应的规则ID
							value = ((JSONObject) colObj).getJSONObject("value");
							
							String datasetId = value.getString("datasetId");
							
							if(datasetId != null && !datasetId.equals("")){
								//有datasetId保存数据集字段引用
								// 数据集字段
								String columnName = value.getString("code");
								// 保存引用关系
								FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
								formDatasetRelation.setPageId(pageId);
								formDatasetRelation.setWidgetId(ruleId);
								formDatasetRelation.setAttrName(IN_PARAMMETERS);
								formDatasetRelationService.saveByParam(formDatasetRelation, null, pageId, ruleId, "",
										columnName);
							}else{
								//保存输入参数引用
								// 保存参数引用
								// 参数引用
								String outid = "";
								String param = value.getString("param");
								String outPage = pageId;
								FormParamsRelation fromParamsRelation = new FormParamsRelation();
								fromParamsRelation.setPageId(pageId);
								fromParamsRelation.setWidgetId(ruleId);
								fromParamsRelation.setAttrName(IN_PARAMMETERS);
								formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, param, ruleId);
							}
						}
					}
					
					if(paramData != null){
						values = paramData.getJSONArray("varVal");
						for (Object colObj : values) {
							// pid由formDatasetRelationService.saveByParam
							// 的时候自动获取，故不需要fromDataSetRelation不需要pid参数
							// inid为控件Id，用于查找到对应的规则ID
							value = ((JSONObject) colObj).getJSONObject("value");
							
							String datasetId = value.getString("datasetId");
							
							if(datasetId != null && !datasetId.equals("")){
								//有datasetId保存数据集字段引用
								// 数据集字段
								String columnName = value.getString("code");
								// 保存引用关系
								FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
								formDatasetRelation.setPageId(pageId);
								formDatasetRelation.setWidgetId(ruleId);
								formDatasetRelation.setAttrName(IN_PARAMMETERS);
								formDatasetRelationService.saveByParam(formDatasetRelation, null, pageId, ruleId, "",
										columnName);
							}else{
								//保存输入参数引用
								// 保存参数引用
								// 参数引用
								String outid = "";
								String param = value.getString("param");
								String outPage = pageId;
								FormParamsRelation fromParamsRelation = new FormParamsRelation();
								fromParamsRelation.setPageId(pageId);
								fromParamsRelation.setWidgetId(ruleId);
								fromParamsRelation.setAttrName(IN_PARAMMETERS);
								formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, param, ruleId);
							}
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 值字段
	 * 
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void datavaluefield(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, DATAVALUEFIELD);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			for (Object object : ary) {
				obj = (JSONObject) object;
				// pid由formDatasetRelationService.saveByParam
				// 的时候自动获取，故不需要fromDataSetRelation不需要pid参数
				// inid为控件Id，用于查找到对应的规则ID
				// 数据集Id
				String datasetId = obj.getString("datasetId");
				// 数据集字段
				String columnName = obj.getString("code");
				// 保存引用关系
				FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
				formDatasetRelation.setPageId(pageId);
				formDatasetRelation.setWidgetId(ruleId);
				formDatasetRelation.setAttrName(DATAVALUEFIELD);
				formDatasetRelationService.saveByParam(formDatasetRelation, datasetId, pageId, ruleId, "", columnName);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 显示字段
	 * 无联动，只引用当前控件的数据集的字段信息
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void datatextfield(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, DATATEXTFIELD);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray values = null;
			JSONObject value = null;
			for (Object object : ary) {
				obj = (JSONObject) object;
				// pid由formDatasetRelationService.saveByParam
				// 的时候自动获取，故不需要fromDataSetRelation不需要pid参数
				// inid为控件Id，用于查找到对应的规则ID
				// 数据集Id
				String datasetId = obj.getString("datasetId");
				// 数据集字段
				String columnName = obj.getString("code");
				// 保存引用关系
				FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
				formDatasetRelation.setPageId(pageId);
				formDatasetRelation.setWidgetId(ruleId);
				formDatasetRelation.setAttrName(DATATEXTFIELD);
				formDatasetRelationService.saveByParam(formDatasetRelation, datasetId, pageId, ruleId, "", columnName);
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 工作流信息
	 * 保存数据集信息
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void dataSourceSetByFlow(String rule, String ruleId, String pageId) {
		List<FormDataset> list = new ArrayList<FormDataset>();
		List<String> relationList = new ArrayList<String>();
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray columns = new JSONArray();	//数据集列表
			JSONObject column = null;	//数据集
			FormDataset formDataset = null;
			for (Object object : ary) {
				obj = (JSONObject) object;
				columns = obj.getJSONArray("selectColumns");
				for (Object colObj : columns) {
					column = (JSONObject) colObj;
					formDataset = new FormDataset();
					formDataset.setWidgetId(ruleId);
					formDataset.setPageId(pageId);
					formDataset.setColumnName(column.getString("code"));
					formDataset.setDatasetId(obj.getString("datasetId"));
					list.add(formDataset);

					String hidLinkImg = column.getString("hidLinkImg");
					if (StringUtils.isNotEmpty(hidLinkImg)) {
						JSONArray hidAry = JSON.parseArray(hidLinkImg);
						if (hidAry != null && !hidAry.isEmpty()) {
							JSONObject hidObj = null;
							String expdataStr = null;
							String htmldataStr = null;
							for (Object object2 : hidAry) {
								hidObj = (JSONObject) object2;
								expdataStr = hidObj.getString("expdata");
								parseExpression(expdataStr, relationList, "expActVal");
								htmldataStr = hidObj.getString("htmldata");
								parseExpression(htmldataStr, relationList, "htmlVal");
							}
						}
					}
				}
				saveFormDataset(list, ruleId, relationList, DATASOURCESETBYFLOW);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 固定数据集
	 * 只引用当前控件的输入参数信息
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void fixDataSource(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, FIXDATASOURCE);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray values = null;
			JSONObject value = null;
			JSONObject contentdata = null;// 内容区
			JSONObject numberdata = null; // 序号区
			for (Object object : ary) {
				obj = (JSONObject) object;

				contentdata = obj.getJSONObject("contentdata");
				numberdata = obj.getJSONObject("numberdata");

				if (contentdata != null) {
					values = contentdata.getJSONArray("varVal");
					for (Object item : values) {
						value = ((JSONObject) item).getJSONObject("value");
						// 保存参数引用
						// 参数引用
						String outid = "";
						String param = value.getString("param");
						String outPage = pageId;
						FormParamsRelation fromParamsRelation = new FormParamsRelation();
						fromParamsRelation.setPageId(pageId);
						fromParamsRelation.setWidgetId(ruleId);
						fromParamsRelation.setAttrName(FIXDATASOURCE);
						formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, param, ruleId);
					}
				}

				if (numberdata != null) {
					values = numberdata.getJSONArray("varVal");
					for (Object item : values) {
						value = ((JSONObject) item).getJSONObject("value");
						// 保存参数引用
						// 参数引用
						String outid = "";
						String param = value.getString("param");
						String outPage = pageId;
						FormParamsRelation fromParamsRelation = new FormParamsRelation();
						fromParamsRelation.setPageId(pageId);
						fromParamsRelation.setWidgetId(ruleId);
						fromParamsRelation.setAttrName(FIXDATASOURCE);
						formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, param, ruleId);
					}
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 控件定义
	 * 只引用当前控件的数据集信息
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void stepDefined(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, STEPDEFINED);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONArray obj = null;
			JSONObject item = null;
			JSONObject expdata = null;// 表达式定义
			JSONObject htmlstyledata = null;// 序号区HTML样式定义
			JSONObject htmldata = null;// 内容区HTML样式定义
			JSONArray values = null;
			JSONObject value = null;
			for (Object object : ary) {
				obj = ((JSONObject) object).getJSONArray("value");

				for (Object model : obj) {
					item = (JSONObject) model;
					expdata = item.getJSONObject("expdata");
					htmlstyledata = item.getJSONObject("htmlstyledata");
					htmldata = item.getJSONObject("htmldata");

					if (expdata != null) {
						values = expdata.getJSONArray("varVal");
						for (Object objectItem : values) {
							value = ((JSONObject) objectItem).getJSONObject("value");
							// pid由formDatasetRelationService.saveByParam
							// 的时候自动获取，故不需要fromDataSetRelation不需要pid参数
							// inid为控件Id，用于查找到对应的规则ID
							// 数据集Id
							String datasetId = value.getString("datasetId");
							// 数据集字段
							String columnName = value.getString("code");
							// 保存引用关系
							FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
							formDatasetRelation.setPageId(pageId);
							formDatasetRelation.setWidgetId(ruleId);
							formDatasetRelation.setAttrName(STEPDEFINED);
							formDatasetRelationService.saveByParam(formDatasetRelation, datasetId, pageId, ruleId, "",
									columnName);
						}
					}

					if (htmlstyledata != null) {
						values = htmlstyledata.getJSONArray("varVal");
						for (Object objectItem : values) {
							value = ((JSONObject) objectItem).getJSONObject("value");
							// pid由formDatasetRelationService.saveByParam
							// 的时候自动获取，故不需要fromDataSetRelation不需要pid参数
							// inid为控件Id，用于查找到对应的规则ID
							// 数据集Id
							String datasetId = value.getString("datasetId");
							// 数据集字段
							String columnName = value.getString("code");
							// 保存引用关系
							FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
							formDatasetRelation.setPageId(pageId);
							formDatasetRelation.setWidgetId(ruleId);
							formDatasetRelation.setAttrName(STEPDEFINED);
							formDatasetRelationService.saveByParam(formDatasetRelation, datasetId, pageId, ruleId, "",
									columnName);
						}
					}

					if (htmldata != null) {
						values = htmldata.getJSONArray("varVal");
						for (Object objectItem : values) {
							value = ((JSONObject) objectItem).getJSONObject("value");
							// pid由formDatasetRelationService.saveByParam
							// 的时候自动获取，故不需要fromDataSetRelation不需要pid参数
							// inid为控件Id，用于查找到对应的规则ID
							// 数据集Id
							String datasetId = value.getString("datasetId");
							// 数据集字段
							String columnName = value.getString("code");
							// 保存引用关系
							FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
							formDatasetRelation.setPageId(pageId);
							formDatasetRelation.setWidgetId(ruleId);
							formDatasetRelation.setAttrName(STEPDEFINED);
							formDatasetRelationService.saveByParam(formDatasetRelation, datasetId, pageId, ruleId, "",
									columnName);
						}
					}
				}

			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 自定义控件的控件定义（保存参数引用关系） 只引用当前控件的输入参数信息
	 * 
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void customDefined(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, CUSTOMDEFINED);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONArray columns = new JSONArray();
			JSONObject obj = null;
			JSONObject column = null;
			JSONObject expdata = null; // 表达式
			JSONObject htmldata = null; // html定义
			JSONArray values = null;
			JSONObject value = null;
			for (Object object : ary) {
				obj = (JSONObject) object;
				columns = obj.getJSONArray("value");

				for (Object objItem : columns) {
					column = (JSONObject) objItem;
					expdata = column.getJSONObject("expdata");
					htmldata = column.getJSONObject("htmldata");

					if (expdata != null) {
						values = expdata.getJSONArray("varVal");
						for (Object item : values) {
							value = ((JSONObject) item).getJSONObject("value");
							// 保存参数引用
							// 参数引用
							String outid = "";
							String param = value.getString("param");
							String outPage = pageId;
							FormParamsRelation fromParamsRelation = new FormParamsRelation();
							fromParamsRelation.setPageId(pageId);
							fromParamsRelation.setWidgetId(ruleId);
							fromParamsRelation.setAttrName(CUSTOMDEFINED);
							formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, param,
									ruleId);
						}
					}

					if (htmldata != null) {
						values = expdata.getJSONArray("varVal");
						for (Object item : values) {
							value = ((JSONObject) item).getJSONObject("value");
							// 保存参数引用
							// 参数引用
							String outid = "";
							String param = value.getString("param");
							String outPage = pageId;
							FormParamsRelation fromParamsRelation = new FormParamsRelation();
							fromParamsRelation.setPageId(pageId);
							fromParamsRelation.setWidgetId(ruleId);
							fromParamsRelation.setAttrName(CUSTOMDEFINED);
							formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, param,
									ruleId);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 数据样式定义
	 * 表达式引用当前控件的数据集信息
	 * 输入输出关系引用输入控件或输出空间的数据集信息或参数信息
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void dataLinkPage(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, DATALINKPAGE);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONObject expdata = null; // 表达式
			JSONArray param = null; // 输入形参
			JSONArray values = null;
			JSONObject value = null;
			JSONObject datas = null;	//输入输出关系
			for (Object object : ary) {
				obj = (JSONObject) object;

				expdata = obj.getJSONObject("expdata");
				param = obj.getJSONArray("param");

				if (expdata != null) {
					values = expdata.getJSONArray("varVal");
					for (Object objectItem : values) {
						value = ((JSONObject) objectItem).getJSONObject("value");
						FormDatasetQuery dataSetQuery = new FormDatasetQuery();
						String columnName = value.getString("code");
						dataSetQuery.setPageId(pageId);
						dataSetQuery.setColumnName(columnName);
						dataSetQuery.setWidgetId(ruleId);
						// 获取数据集id
						List<FormDataset> list = this.formDatasetService.list(dataSetQuery);
						if (list != null && list.size() != 0) {
							String dataSetId = list.get(0).getDatasetId();
							// 保存引用关系
							FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
							formDatasetRelation.setPageId(pageId);
							formDatasetRelation.setWidgetId(ruleId);
							formDatasetRelation.setAttrName(DATALINKPAGE);
							formDatasetRelationService.saveByParam(formDatasetRelation, dataSetId, pageId, ruleId, "",
									columnName);
						}
					}
				}

				// 输入输出参数
				if (param != null) {
					for (Object objectItem : param) {
						datas = ((JSONObject) objectItem).getJSONObject("datas");
						Set<String> keys = datas.keySet();
						for (String key : keys) {
							values = datas.getJSONArray(key);

							for (Object item : values) {
								value = (JSONObject) item;

								// 数据集引用
								String inid = value.getString("inid");
								String outPage = pageId;
								
								String columnName = value.getString("incode");
								
								//保存输入控件的数据集
								// 保存数据集引用关系
								FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
								formDatasetRelation.setPageId(pageId);
								formDatasetRelation.setWidgetId(ruleId);
								formDatasetRelation.setAttrName(DATALINKPAGE);
								formDatasetRelationService.saveByParam(formDatasetRelation, null, pageId, null, inid,
										columnName);

								// 保存输出参数（即当前控件）引用
								String outid = value.getString("outid");
								// 参数code
								String paramValue = value.getString("param");

								// 保存参数引用关系
								FormParamsRelation fromParamsRelation = new FormParamsRelation();
								fromParamsRelation.setPageId(pageId);
								fromParamsRelation.setWidgetId(ruleId);
								fromParamsRelation.setAttrName(DATALINKPAGE);
								formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, paramValue,
										null);
							}
						}
					}
				}

			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 验证器定义
	 * 表达式引用了指定控件的数据集信息
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void validate(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, VALIDATE);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;
			JSONArray nodes = null; // 验证器
			JSONObject node = null;
			JSONObject execExpData = null; // 前置表达式
			JSONObject expData = null; // 验证表达式
			JSONArray values = null;
			JSONObject value = null;
			String initId = null;
			String columnName = null; // 输出控件数据集字段名
			for (Object object : ary) {
				obj = (JSONObject) object;
				nodes = obj.getJSONArray("nodes");
				for (Object objItem : nodes) {
					node = (JSONObject) objItem;

					execExpData = node.getJSONObject("execExpData");
					expData = node.getJSONObject("expData");

					if (execExpData != null) {
						values = execExpData.getJSONArray("varVal");
						for (Object item : values) {
							value = ((JSONObject) item).getJSONObject("value");

							initId = value.getString("eleId");
							columnName = value.getString("code");

							// 保存引用关系
							FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
							formDatasetRelation.setPageId(pageId);
							formDatasetRelation.setWidgetId(ruleId);
							formDatasetRelation.setAttrName(VALIDATE);
							formDatasetRelationService.saveByParam(formDatasetRelation, null, pageId, null, initId,
									columnName);
						}
					}

					if (expData != null) {
						values = expData.getJSONArray("varVal");
						for (Object item : values) {
							value = ((JSONObject) item).getJSONObject("value");

							initId = value.getString("eleId");
							columnName = value.getString("code");

							// 保存引用关系
							FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
							formDatasetRelation.setPageId(pageId);
							formDatasetRelation.setWidgetId(ruleId);
							formDatasetRelation.setAttrName(VALIDATE);
							formDatasetRelationService.saveByParam(formDatasetRelation, null, pageId, null, initId,
									columnName);
						}
					}
				}
			}
		} catch (

		Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 输入输出关系定义
	 * 输入输出关系引用输入控件或输出空间的数据集信息或参数信息
	 * @param rule
	 * @param ruleId
	 * @param pageId
	 */
	public void paraType(String rule, String ruleId, String pageId) {
		clear(ruleId, pageId, PARATYPE);
		try {
			JSONArray ary = JSON.parseArray(rule);
			JSONObject obj = null;

			JSONObject datas = null;
			JSONArray values = null;
			JSONObject value = null;
			for (Object object : ary) {
				obj = (JSONObject) object;

				datas = obj.getJSONObject("datas");

				Set<String> keys = datas.keySet();
				for (String key : keys) {
					values = datas.getJSONArray(key);

					for (Object item : values) {
						value = (JSONObject) item;

						// 数据集引用
						String inid = value.getString("inid");
						String outPage = pageId;

						String columnName = value.getString("incode");

						// 保存数据集引用关系
						FormDatasetRelation formDatasetRelation = new FormDatasetRelation();
						formDatasetRelation.setPageId(pageId);
						formDatasetRelation.setWidgetId(ruleId);
						formDatasetRelation.setAttrName(PARATYPE);
						formDatasetRelationService.saveByParam(formDatasetRelation, null, pageId, null, inid,
								columnName);

						// 参数引用
						String outid = value.getString("outid");
						// 参数code
						String paramValue = value.getString("param");

						// 保存参数引用关系
						FormParamsRelation fromParamsRelation = new FormParamsRelation();
						fromParamsRelation.setPageId(pageId);
						fromParamsRelation.setWidgetId(ruleId);
						fromParamsRelation.setAttrName(PARATYPE);
						formParamsRelationService.saveByParam(fromParamsRelation, null, outPage, outid, paramValue,
								null);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
