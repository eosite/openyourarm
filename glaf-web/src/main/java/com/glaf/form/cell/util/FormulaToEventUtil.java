package com.glaf.form.cell.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.convert.domain.ConvertElemTmpl;
import com.glaf.convert.domain.ConvertElemTmplForml;
import com.glaf.convert.domain.ConvertElemTmplRef;
import com.glaf.convert.query.ConvertElemTmplFormlQuery;
import com.glaf.convert.query.ConvertElemTmplQuery;
import com.glaf.convert.query.ConvertElemTmplRefQuery;
import com.glaf.convert.service.ConvertElemTmplFormlService;
import com.glaf.convert.service.ConvertElemTmplRefService;
import com.glaf.convert.service.ConvertElemTmplService;
import com.glaf.form.cell.service.CellConvertService;
import com.glaf.form.core.domain.FormPage;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.service.FormComponentPropertyService;
import com.glaf.form.core.service.FormPageService;
import com.glaf.form.core.service.FormRuleService;
/**
 * 
 * @author J
 *
 */
@Component
public class FormulaToEventUtil {
	
	@Autowired
	protected ConvertElemTmplFormlService convertElemTmplFormlService ;
	
	@Autowired
	protected ConvertElemTmplService convertElemTmplService ;
	
	@Autowired
	protected FormPageService formPageService ;
	
	@Autowired
	protected FormComponentPropertyService formComponentPropertyService ;
	
	@Autowired
	protected FormRuleService formRuleService ;
	
	@Autowired
	protected ConvertElemTmplRefService convertElemTmplRefService;
	
	public FormulaToEventUtil() {
		
	}
	/**
	 * test
	 */
	public void init(String pageId,Long cvtId){
		List<FormRule> rules = formRuleService.getRules(pageId);
		for (FormRule formRule : rules) {
			if(pageId.equalsIgnoreCase(formRule.getName())){
					String value = formRule.getValue();
					if(value!=null && !"".equals(value)){
						JSONObject valObj = JSON.parseObject(value);
						//1438
						valObj.put("1438", convertToEvents(cvtId));
						formRule.setValue(valObj.toString());
					}
			}
		}
		
		formRuleService.saveRules(pageId, rules);
	}
	/**
	 * 当前页面转换为事件定义器事件
	 * @param formulaArray {eleId：CVT_ELEM_TMPL_FORML 中的 CVT_ELEM_ID_ ,varList：公式变量集合 如：textbox_11_22,resultFormula：转换后的公式} 
	 * @return
	 */
	public String convertToEvents(Long cvtId/*,JSONArray formulaArray*/){
		JSONArray retAry = new JSONArray();
		JSONObject retObj = new JSONObject();
		JSONArray values = new JSONArray();
		JSONObject value = null ;
		JSONObject jo = null ;
		//获取当前页面所有控件 事件定义树
		JSONArray events = this.getEventTree(CellConvertService.prefix + cvtId);
			
		//获取此页面的所有控件
		ConvertElemTmplQuery query = new ConvertElemTmplQuery();
		query.setCvtId(cvtId);
		List<ConvertElemTmpl> conlist = convertElemTmplService.list(query);
		
	//	for (Object object : formulaArray) {
	//		jo = (JSONObject) object ;
		List<ConvertElemTmplForml> convertElemTmplFormls = convertElemTmplFormlService.getConvertElemTmplFormlsByCvtId(cvtId);
		String content ;
		String resultFormula = null ;
		JSONObject cellCriterionObj = getCellCriterionObj(conlist, events);
		boolean flag = false; 
		for (ConvertElemTmplForml convertElemTmplForml : convertElemTmplFormls) {
			content = convertElemTmplForml.getCvtFormlContent();
			if(content!=null && !"".equals(content)){
				jo = JSON.parseObject(content);
				//转换后的公式 
				resultFormula = jo.getString("resultFormula");
				//引用参数设置
				if(resultFormula.startsWith("SETVARIABLE") || resultFormula.startsWith("CRITERIONSTAT")){
					flag = true ;
					this.getCellCriterionEvent(cellCriterionObj,jo, conlist, events);
				}else{
					value = this.convertToEvent(jo, conlist,events);
					if(value != null ){
						values.add(value);
					}
				}
			}
		}
		if(flag){
			values.add(cellCriterionObj);
		}
		
		/**
		 * 引用其它数据表数据：排除CVT_ELEM_TMPL_REF表中REF_TYPE_字段类型为criterion 和 criterionParam 
		 * 在页面初始化的时候执行，赋值给单元格内
		 *
		 */
		//List<ConvertElemTmplRef> convertElemTmplRefs = convertElemTmplRefService.getConvertElemTmplRefsByCvtId(cvtId);
		//values.add();
		
		retObj.put("name", "事件定义器");
		retObj.put("values", values);
		retAry.add(retObj);
	//	//System.out.println("自动生成事件定义器："+retAry);
		
		return retAry.toJSONString() ;
	}
	
	/**
	 * 当前控件事件转换为事件定义器事件
	 * @param eleId   CVT_ELEM_TMPL_FORML 中的 CVT_ELEM_ID_
	 * @param varList 公式变量集合 如：textbox_11_22
	 * @param resultFormula 转换后的公式
	 * @return
	 */
	private JSONObject convertToEvent(JSONObject jo,List<ConvertElemTmpl> conlist,JSONArray events){
		//公式中的变量集合
		JSONArray varList = jo.getJSONArray("varList"); 
		//转换后的公式
		String resultFormula = jo.getString("resultFormula");
		
		//输出控件
		ConvertElemTmpl convertElemTmpl = convertElemTmplService.getConvertElemTmpl(jo.getLong("eleId"));
		
		//{eleId,varList,resultFormula}
		JSONObject retObj = new JSONObject();
		//事件名称
		retObj.put("cname", convertElemTmpl.getElemName()); 
		// 输入控件名称
		retObj.put("inWidgetName", null); 
		// 输入控件 
		retObj.put("inWidget", null); 
		
		/********************触发事件控件定义*******************/
		//需要检测当前 事件触发控件是否为变长区
		ConvertElemTmpl varCon = null ;
		String varName = "" ;
		String widgetName = "";
		JSONArray widgetAry = new JSONArray();
		JSONObject eventObj = null ;
		String funName = "";
		if(varList !=null && !varList.isEmpty()){
			for (Object object : varList) {
				varName = (String) object ;
				varCon = getConvertElemTmplByVarName(conlist,varName);
				if(varCon!=null){
					Integer endColIndex = varCon.getEndColIndex();
					Integer endRowIndex = varCon.getEndRowIndex();
					//检测是否为变长区  如果是变长区
					if(endRowIndex!=null && endColIndex>0 && endRowIndex!=null && endRowIndex>0){
						funName = "changeByName" ;
						widgetName += "内容变更触发(变长)," ;
					}else{//如果不是变长区
						funName = "change" ;
						widgetName += "内容变更触发," ;
					}
					eventObj = this.getEvent(varCon.getElemId(), funName , events);
					if(eventObj!=null){
						widgetAry.add(eventObj);
					}
				}
			}
			//事件触发控件名称  
			retObj.put("widgetName", widgetName); 
			//事件触发
			retObj.put("widget", widgetAry.toJSONString());  
		}
		
		/********************输出控件定义*******************/
		//输出控件事件编辑器
		JSONArray widgetEventAry = new JSONArray();
		JSONObject widgetEventObj = new JSONObject();
		funName = "setValue" ;
		String outWidgetName = "赋值" ;
		Integer endColIndex = convertElemTmpl.getEndColIndex();
		Integer endRowIndex = convertElemTmpl.getEndRowIndex();
		//变长区
		if(endRowIndex!=null && endColIndex>0 && endRowIndex!=null && endRowIndex>0){
			//列
			Integer colIndex =  convertElemTmpl.getColIndex() ; 
			//行
			Integer rowIndex =  convertElemTmpl.getRowIndex() ; 
			//根据变长动态赋值
			funName = "setValueByDynamic" ; 
			outWidgetName = "赋值(华表变长)" ;
			JSONObject cell = new JSONObject();
			cell.put("ec",endColIndex);
			cell.put("er",endRowIndex);
			cell.put("c", colIndex);
			cell.put("r", rowIndex);
			//direction 方向   horizontal横  vertical纵
			cell.put("d",endColIndex.equals(colIndex)?"v":"h");
			// cell  变长区数据
			widgetEventObj.put("cell", cell); 
		}
		//表达式
		widgetEventObj.put("exp", "");
		//表达式数据
		widgetEventObj.put("expdata", null);
		//输出控件名称
		widgetEventObj.put("outWidgetName", outWidgetName);
		
		JSONArray outWidgetAry = new JSONArray();
		eventObj = this.getEvent(convertElemTmpl.getElemId(), funName , events);
		if(eventObj!=null){
			outWidgetAry.add(eventObj);
		}
		//输出控件
		widgetEventObj.put("outWidget", outWidgetAry.toJSONString());
		//cell表达式
		widgetEventObj.put("cellExp", resultFormula); 
		//{"expVal":"true","expActVal":"true","varVal":[]}
		JSONObject expdata = new JSONObject();
		expdata.put("expVal", resultFormula);
		expdata.put("expActVal", resultFormula);
		expdata.put("varVal", new JSONArray());
		//cell表达式数据
		widgetEventObj.put("cellExpdata", expdata.toJSONString());  
		widgetEventAry.add(widgetEventObj);
		
		retObj.put("widgetEvent", widgetEventAry.toJSONString());
		
		return retObj ;
	}
	
	private JSONObject getCellCriterionObj(List<ConvertElemTmpl> conlist,JSONArray events){
		/*
		 *  页面触发 -->输出控件(当前id) 
		 */
		JSONObject retObj = new JSONObject();
		/*****************输入控件*******************/
		//事件名称
		retObj.put("cname", "引用规范参数汇总"); 
		// 输入控件名称
		retObj.put("inWidgetName", null); 
		// 输入控件 
		retObj.put("inWidget", null); 
		
		/*****************触发控件*******************/
		JSONArray widgetAry = new JSONArray();
		String pageId = CellConvertService.prefix + conlist.get(0).getCvtId();
		JSONObject eventObj = this.getEvent(pageId, "pageInit" , events);
		if(eventObj!=null){
			widgetAry.add(eventObj);
		}
		//事件触发控件名称  
		retObj.put("widgetName", "CELL表初始化"); 
		//事件触发
		retObj.put("widget", widgetAry.toJSONString());  
		
		/*****************输出控件*******************/
		JSONArray widgetEventAry = new JSONArray();
		retObj.put("widgetEvent", widgetEventAry.toJSONString());
		
		return retObj ;
	}
	
	/**
	 * 引用参数定义
	 * @param jo
	 * @param conlist
	 * @param events
	 * @return
	 */
	private JSONObject getCellCriterionEvent(JSONObject retObj,JSONObject jo,List<ConvertElemTmpl> conlist,JSONArray events){
		//公式中的变量集合
		JSONArray varList = jo.getJSONArray("varList"); 
		//转换后的公式
		String resultFormula = jo.getString("resultFormula");
		/*
		 *  页面触发 -->输出控件(当前id) 
		 */
		//JSONObject retObj = new JSONObject();
		/*****************输入控件*******************/
		//retObj.put("cname", "引用规范参数汇总"); //事件名称
		//retObj.put("inWidgetName", null); // 输入控件名称
		//retObj.put("inWidget", null); // 输入控件 
		
		/*****************触发控件*******************/
		//JSONArray widgetAry = new JSONArray();
		//String pageId = CellConvertService.prefix + conlist.get(0).getCvtId();
		//JSONObject eventObj = this.getEvent(pageId, "pageInit" , events);
		//if(eventObj!=null)
		//	widgetAry.add(eventObj);
		//retObj.put("widgetName", "CELL表初始化"); //事件触发控件名称  
		//retObj.put("widget", widgetAry.toJSONString());  //事件触发
		
		/*****************输出控件*******************/
		//输出控件
		ConvertElemTmpl convertElemTmpl = convertElemTmplService.getConvertElemTmpl(jo.getLong("eleId"));
		JSONArray widgetEventAry = new JSONArray();
		JSONObject widgetEventObj = new JSONObject();
		
		//表达式
		widgetEventObj.put("exp", "");
		//表达式数据
		widgetEventObj.put("expdata", null);
		//输出控件名称
		widgetEventObj.put("outWidgetName", convertElemTmpl.getElemName()+"-华表引用");
		
		JSONArray outWidgetAry = new JSONArray();
		JSONObject eventObj = this.getEvent(convertElemTmpl.getElemId(), "cellCriterion" , events);
		if(eventObj!=null){
			outWidgetAry.add(eventObj);
		}
		//输出控件
		widgetEventObj.put("outWidget", outWidgetAry.toJSONString());
		//cell表达式
		widgetEventObj.put("cellExp", resultFormula); 
		//{"expVal":"true","expActVal":"true","varVal":[]}
		JSONObject expdata = new JSONObject();
		expdata.put("expVal", resultFormula);
		expdata.put("expActVal", resultFormula);
		expdata.put("varVal", new JSONArray());
		//cell表达式数据
		widgetEventObj.put("cellExpdata", expdata.toJSONString());  
		widgetEventAry.add(widgetEventObj);
		
		String outWidget = retObj.getString("widgetEvent");
		JSONArray outWidgetArray = JSON.parseArray(outWidget);
		outWidgetArray.addAll(widgetEventAry);
		retObj.put("widgetEvent", outWidgetArray.toJSONString());
		
		return retObj ;
	}
	/**
	 * 获取当前控件的事件定义对象
	 * @param id
	 * @param funName
	 * @param events
	 * @return
	 */
	private JSONObject getEvent(String id,String funName,JSONArray events){
		JSONObject event = null ;
		String pObjId = null ;
		String eName = null ;
		for (Object object : events) {
			event = (JSONObject) object;
			pObjId = event.getString("pObjId");
			eName = event.getString("eName");
			if(pObjId!=null && id.equalsIgnoreCase(pObjId) && eName!=null && funName.equalsIgnoreCase(eName)){
				return event ;
			}
		}
		return null ;
	}
	/**
	 * 根据eleId（label_11_15） 获取当前的元素对象
	 * @param conlist
	 * @param varName
	 * @return
	 */
	private ConvertElemTmpl getConvertElemTmplByVarName(List<ConvertElemTmpl> conlist, String varName) {
		if(conlist !=null && conlist.size() > 0 && varName !=null){
			for (ConvertElemTmpl convertElemTmpl : conlist) {
				if(varName.equalsIgnoreCase(convertElemTmpl.getElemId())){
					return convertElemTmpl;
				}
			}
		}
		return null;
	}
	/**
	 *  获取事件树
	 * @param showEvent
	 * @param pageId
	 * @return
	 */
	private JSONArray getEventTree(String pageId){
		Map<String, List<Map<String, Object>>> propertyMap = getPropertys();
		JSONArray retAry = new JSONArray();
		// 获取根页面的html 根节点
		int level = 1;
		FormPage formPage = formPageService.getFormPage(pageId);
		JSONObject retObj = new JSONObject();
		retObj.put("id", pageId + level);
		retObj.put("pId", level);
		retObj.put("level", level);
		retObj.put("eleId", pageId);
		retObj.put("pageId", pageId);
		retObj.put("name", formPage.getName());
		retAry.add(retObj);
		this.addEventsTo(propertyMap, "page", retAry, retObj);

		// 获取所有data-role的元素 元素节点
		Document doc = Jsoup.parse(formPage.getFormHtml());
		Elements elements = doc.getElementsByAttribute("data-role");
		String role = null;

		for (Element element : elements) {
			// 不是layout  布局的元素
			if (element.attr("crtltype").startsWith("kendo")) { 
				// 页面中的 元素
				retObj = new JSONObject();
				retObj.put("id", element.attr("id") + level);
				retObj.put("pId", pageId + level);
				retObj.put("level", level);
				retObj.put("eleId", element.attr("id"));
				retObj.put("icon", "/images/cfg.png");
				retObj.put("pageId", pageId);
				retObj.put("name", org.apache.commons.lang.StringUtils.isNotEmpty(element.attr("title"))
						? element.attr("title") : element.attr("name"));
				retAry.add(retObj);
				role = element.attr("data-role");
				this.addEventsTo(propertyMap, role, retAry, retObj);
			}
		}
		return retAry ;
	}
	
	/**
	 * 页面控件参数
	 * 
	 * @param propertyMap
	 * @param role
	 * @param retAry
	 * @param retObj
	 */
	private void addEventsTo(Map<String, List<Map<String, Object>>> propertyMap, String role, JSONArray retAry,
			JSONObject retObj) {
		List<Map<String, Object>> mapList = propertyMap.get(role);
		if (mapList != null) {
			String title = null;
			String name = null;
			JSONObject eleObj = null;
			for (Map<String, Object> map : mapList) {
				title = map.get("TITLE_").toString();
				name = map.get("NAME_").toString();
				eleObj = new JSONObject();
				eleObj.put("id", retObj.getString("id") + name);
				eleObj.put("pId", retObj.getString("id"));
				eleObj.put("name", title);
				eleObj.put("icon", "/images/event.png");
				eleObj.put("eName", name);
				eleObj.put("pObj", retObj.toJSONString());
				eleObj.put("pObjId", retObj.getString("eleId"));
				retAry.add(eleObj);
			}
		}
	}
	
	/**
	 * 获取事件属性
	 * 
	 * @return
	 */
	private Map<String, List<Map<String, Object>>> getPropertys() {
		List<Map<String, Object>> proList = formComponentPropertyService.getEventsProperty(null);
		String role = null;
		List<Map<String, Object>> mapList = null;
		Map<String, List<Map<String, Object>>> retMap = new HashMap<>(50);
		for (Map<String, Object> map : proList) {
			role = map.get("DATAROLE_").toString();
			if (retMap.containsKey(role)) {
				retMap.get(role).add(map);
			} else {
				mapList = new ArrayList<>();
				mapList.add(map);
				retMap.put(role, mapList);
			}
		}

		return retMap;
	}
}
