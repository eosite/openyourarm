package com.glaf.platforms.rule.util;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.context.ContextFactory;
import com.glaf.form.core.domain.FormEvent;
import com.glaf.form.core.query.FormEventQuery;
import com.glaf.form.core.service.FormEventService;

public class EventUtil {
	/**
	 * 前缀
	 */
	private static final String PERFIX = "bpmn2:" ;
	/**
	 * 开始节点
	 */
	private static final String START_EVENT = "startEvent" ;
	/**
	 * 结束节点
	 */
	private static final String END_EVENT = "endEvent" ;
	/**
	 * 连接线
	 */
	private static final String SEQUENCE_FLOW = "sequenceFlow" ;
	/**
	 * 网关
	 */
	private static final String EXCLUSIVE_GATEWAY = "exclusiveGateway" ;
	
	private static final String OUT_GOING = "outgoing" ;
	
	private static final String IN_WIDGET = "sendTask" ;
	private static final String WIDGET = "serviceTask" ;
	private static final String OUT_WIDGET = "receiveTask" ;
	private static final String SCRIPT_TASK = "scriptTask" ;
	
	public EventUtil() {
	}


	/**
	 * 事件定义器初始化
	 * 
	 * @param pageMap
	 * @param pageJs
	 */
	@SuppressWarnings("unchecked")
	public static boolean eventInit(Map<String, String> pageMap, StringBuffer pageJs,Map<String, String> initScriptMap,String pageId,Map<String,Object> otherMap) {
		
		FormEventService formEventService = ContextFactory.getBean("com.glaf.form.core.service.formEventService");
		FormEventQuery query = new FormEventQuery();
		query.setPageId(pageId);
		List<FormEvent> lists = formEventService.list(query);
		FormEvent formEvent = null; 
		if(lists!=null && !lists.isEmpty()){
			formEvent =  lists.get(0);
			String diagramXml = formEvent.getDiagram();
			String ruleStr = formEvent.getRule();
			JSONArray retAry = new JSONArray(); 
			try {
				JSONObject rule = JSON.parseObject(ruleStr);
				
				Document document = DocumentHelper.parseText(diagramXml);
				Element rootElement = document.getRootElement();
				//获取开始节点
				List<Element> startEvents = rootElement.selectNodes("//bpmn2:startEvent");
				
				Element startEvent = startEvents.get(0);
				
				//获取连接线
				Iterator it = startEvent.elementIterator();
				Element element = null ;
				String eleId = null;
				List<Element> eles = null;
				JSONObject retObj = null;
				while(it.hasNext()){
					element = (Element) it.next();
					eleId = element.getText();
					eles = document.selectNodes("//*[@id='"+eleId+"']");
					//获取支线节点 
					if(eles!=null && !eles.isEmpty()){
						retObj = new JSONObject();
						element = eles.get(0);
						//获取最新
						getNextElement(document, element,rule,retObj,false,false);
						if(!retObj.isEmpty()){
							retAry.add(retObj);
						}
					}
				}
				
				if (retAry != null && !retAry.isEmpty()) {
					JSONArray retEdv = PublishUtil.getEvent(retAry,initScriptMap,otherMap);
					if(retEdv!=null && !retEdv.isEmpty()){
						String eventWeighting = pageMap.get("eventWeighting");
						pageJs.append("<script>pageed = '" + retEdv.toJSONString().replaceAll("'", "\\\\'") + "',isWeighting="+(StringUtils.isNotEmpty(eventWeighting)?eventWeighting:false)+";</script>");
					}
				}
				return true;
			} catch (DocumentException e) {
				e.printStackTrace();
			}
		}
		
		return false;
	}
	
	/**
	 * 
	 * @param document
	 * @param element	当前元素 
	 * @param rule		所有规则
	 * @param retObj
	 * @param hasGateWay
	 * @param hasChild
	 * @return
	 */
	private static void getNextElement(Document document,Element element,JSONObject rule,JSONObject retObj,boolean hasGateWay,boolean hasChild){
		List<Element> eles = null ;
		String eleId = null;
		boolean isReturn = false;
		boolean isOut = false;
		JSONObject outWidgetRetObj = new JSONObject();
		JSONObject rObj = rule.getJSONObject(element.attributeValue("id"));
		switch (element.getName()) {
		case SEQUENCE_FLOW:
			eleId = element.attributeValue("targetRef");
			if(hasGateWay){
				if(rObj!=null){
					retObj.put("expdata", rObj.getJSONObject("expression_data")); //表达式判断
				}
			}
			break;
		case OUT_GOING:
			eleId = element.getText();
			break;
		case EXCLUSIVE_GATEWAY:  //是否有分支
			List<Element> celes = element.selectNodes("child::bpmn2:outgoing");
			JSONArray outWidgetRetAry = new JSONArray(); 
			for (Element elem : celes) {
				outWidgetRetObj = new JSONObject();
				outWidgetRetAry.add(outWidgetRetObj);
				getNextElement(document, elem, rule, outWidgetRetObj, true,false);
			}
			if(hasChild){
				retObj.put("childs", outWidgetRetAry);
			}else{
				retObj.put("widgetEvent", outWidgetRetAry);
			}
			isReturn = true;
			break;
		case IN_WIDGET:
			eles = element.selectNodes("child::bpmn2:outgoing");
			if(rObj!=null){
				retObj.put("inWidget", rObj.getJSONArray("input_data"));
			}
			break;
		case WIDGET:
			eles = element.selectNodes("child::bpmn2:outgoing");
			if(rObj!=null){
				retObj.put("widget", rObj.getJSONArray("event_data"));
			}
			break;
		case OUT_WIDGET :
		case SCRIPT_TASK :
			eles = element.selectNodes("child::bpmn2:outgoing");
			if(rObj!=null){
				if(!hasGateWay && !hasChild){
					isOut = true ;
					outWidgetRetAry = new JSONArray();
					outWidgetRetObj = new JSONObject();
					outWidgetRetObj.put("outWidget", rObj.getJSONArray(SCRIPT_TASK.equalsIgnoreCase(element.getName())?"output_page_data":"output_data"));
					outWidgetRetObj.put("outExt", rObj.getJSONObject("out_ext_data"));
					outWidgetRetObj.put("param", rObj.getJSONArray("inoutparams_data"));
					outWidgetRetObj.put("callback", rObj.getJSONArray("callback_data"));
					outWidgetRetAry.add(outWidgetRetObj);
					retObj.put("widgetEvent", outWidgetRetAry);
				}else{
					if(hasChild){
						isOut = true ;
						outWidgetRetAry = new JSONArray();
						outWidgetRetObj = new JSONObject();
						outWidgetRetObj.put("outWidget", rObj.getJSONArray(SCRIPT_TASK.equalsIgnoreCase(element.getName())?"output_page_data":"output_data"));
						outWidgetRetObj.put("outExt", rObj.getJSONObject("out_ext_data"));
						outWidgetRetObj.put("param", rObj.getJSONArray("inoutparams_data"));
						outWidgetRetObj.put("callback", rObj.getJSONArray("callback_data"));
						outWidgetRetAry.add(outWidgetRetObj);
						retObj.put("childs", outWidgetRetAry);
					}else{
						retObj.put("outWidget", rObj.getJSONArray(SCRIPT_TASK.equalsIgnoreCase(element.getName())?"output_page_data":"output_data"));
						retObj.put("outExt", rObj.getJSONObject("out_ext_data"));
						//retObj.put("expdata", rObj.getJSONArray("event_data")); //这个在线中
						retObj.put("param", rObj.getJSONArray("inoutparams_data"));
						retObj.put("callback", rObj.getJSONArray("callback_data"));
					}
				}
				hasGateWay = false;
				hasChild = true;
			}
			break;
		case END_EVENT:
			isReturn = true;
			break;
		default:
			//eles = element.selectNodes("//bpmn2:outgoing");
			isReturn = true;
			break;
		}
		
		if(isReturn){
			return ;
		}
		
		if(eleId!=null){
			eles = document.selectNodes("//*[@id='"+eleId+"']");
		}
		if(eles!=null && !eles.isEmpty()){
			for (Element element2 : eles) {
				getNextElement(document,  element2, rule, isOut?outWidgetRetObj:retObj, hasGateWay,hasChild);
			}
		}
		
	}
}
