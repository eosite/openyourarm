package com.glaf.platforms.rule.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
import com.glaf.form.core.domain.FormEventComplex;
import com.glaf.form.core.query.FormEventComplexQuery;
import com.glaf.form.core.query.FormEventQuery;
import com.glaf.form.core.service.FormEventComplexService;
import com.glaf.form.core.service.FormEventService;

public class EventUtil2 {
	/**
	 * 前缀
	 */
	private static final String PERFIX = "bpmn2:";
	/**
	 * 属性
	 */
	/**
	 * node
	 */
	private static final String OUT_GOING = "outgoing";
	private static final String IN_COMING = "incoming";
	/**
	 * flow
	 */
	private static final String SOURCE = "sourceRef";
	private static final String TARGET = "targetRef";
	private static final String HAS_EVETN = "hasEvent";
	private static final String HAS_PARAM = "hasParam";
	private static final String IS_IN_PARAM = "isInParam";
	/**
	 * 连接线
	 */
	private static final String SEQUENCE_FLOW = "chinaissSeqFlow";
	/**
	 * 辅助线
	 */
	private static final String ASSIST_FLOW = "chinaissAssistFlow";
	/**
	 * 网关
	 */
	private static final String EXCLUSIVE_GATEWAY = "chinaissExclusiveGateway";
	/**
	 * 辅助节点
	 */
	private static final String CHINAISS_ASSIST_NODE = "chinaissAssistNode";
	/**
	 * 容器节点
	 */
	private static final String CHINAISS_CONTAINERS = "chinaissContainers";
	/**
	 * 页面节点
	 */
	private static final String CHINAISS_PAGE_NODE = "chinaissPageNode";
	/**
	 * 数据节点
	 */
	private static final String CHINAISS_DATA_NODE = "chinaissDataNode";
	/**
	 * 复合构件
	 */
	private static final String CHINAISS_CHUNK = "chinaissChunk";
	/**
	 * 数据操作节点、快捷操作（查询服务 cud等）
	 */
	private static final String CHINAISS_DATA_OPERATE = "chinaissDataOperate";
	/**
	 * 多数据获取节点
	 */
	private static final String CHINAISS_GET_NODE = "chinaissGetNode";

	private static final String VERSION = "2.0";

	public EventUtil2() {
	}

	/**
	 * 事件定义器初始化
	 * 
	 * @param pageMap
	 * @param pageJs
	 */
	@SuppressWarnings("unchecked")
	public static boolean eventInit(Map<String, String> pageMap, StringBuffer pageJs, Map<String, String> initScriptMap, String pageId, Map<String, Object> otherMap) {

		FormEventService formEventService = ContextFactory.getBean("com.glaf.form.core.service.formEventService");
		FormEventQuery query = new FormEventQuery();
		query.setPageId(pageId);
		query.setVersion(VERSION);
		List<FormEvent> lists = formEventService.list(query);
		JSONArray retAry = new JSONArray();
		if (lists != null && !lists.isEmpty()) {
			try {
				for (FormEvent formEvent : lists) {
					String diagramXml = formEvent.getDiagram();
					String ruleStr = formEvent.getRule();
					buildRule(retAry, diagramXml, ruleStr);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		/**
		 * 复合构件
		 */
		FormEventComplexService formEventComplexService = ContextFactory.getBean("com.glaf.form.core.service.formEventComplexService");
		FormEventComplexQuery complexQuery = new FormEventComplexQuery();
		complexQuery.setPageId(pageId);
		List<FormEventComplex> complexs = formEventComplexService.list(complexQuery);
		if(complexs != null && !complexs.isEmpty()){
			for (FormEventComplex formEventComplex : complexs) {
				String diagramXml = formEventComplex.getDiagram();
				String ruleStr = formEventComplex.getRule();
				buildRule(retAry, diagramXml, ruleStr);
			}
		}
		
		if (retAry != null && !retAry.isEmpty()) {
			JSONArray retEdv = PublishUtil.getEvent(retAry, initScriptMap, otherMap);
			if (retEdv != null && !retEdv.isEmpty()) {
				String eventWeighting = pageMap.get("eventWeighting");
				pageJs.append("<script>pageed = '" + retEdv.toJSONString().replaceAll("'", "\\\\'") + "',isWeighting="
						+ (StringUtils.isNotEmpty(eventWeighting) ? eventWeighting : false) + ";</script>");
			}
			return true;
		}
		return false;
	}
	
	/**
	 * 构造规则
	 * @param retAry
	 * @param diagramXml
	 * @param ruleStr
	 */
	private static void buildRule(JSONArray retAry, String diagramXml, String ruleStr) {
		JSONObject rule = JSON.parseObject(ruleStr);
		Document document = null;
		try {
			document = DocumentHelper.parseText(diagramXml);
		} catch (Exception e) {
			e.printStackTrace();
		}
		Element rootElement = document.getRootElement();

		List<String> triggerNodeIds = getAllTriggerNodeIds(rootElement);

		List<Element> eles = null;
		JSONObject retObj = null;
		Element element = null;
		for (String id : triggerNodeIds) {
			eles = document.selectNodes("//*[@id='" + id + "']");
			if (eles != null && !eles.isEmpty()) {
				element = eles.get(0);

				List<String> inputList = new ArrayList<>();
				Map<String, List<String>> triggerGroup = new HashMap<>();
				triggetGroup(rule, document, element, inputList, triggerGroup);
				/**
				 * { inWidget : [{}], widget:[{}], widgetEvent:[{
				 * outWidget:[{}], outExt:{}, expdata:[{}], param:[{}],
				 * callback:[{}], childs:[{},{}] }] }
				 */

				/**
				 * 根据触发事件类型分组
				 */
				Set<String> keySet = triggerGroup.keySet();
				JSONObject flowRule = null;
				List<String> triggerList = new ArrayList<>();
				for (String key : keySet) {
					retObj = new JSONObject();
					/**
					 * 输入控件
					 */
					retObj.put("inWidget", buildInWidget(rule, inputList));
					JSONArray widgetAry = new JSONArray();

					triggerList = triggerGroup.get(key);
					f1: for (String flowId : triggerList) {
						flowRule = rule.getJSONObject(flowId);
						/**
						 * 触发控件
						 */
						if (!retObj.containsKey("widget")) {
							retObj.put("widget", buildTiggerWidget(rule.getJSONObject(id), flowRule));
						}
						eles = document.selectNodes("//*[@id='" + flowId + "']");
						if (eles != null && !eles.isEmpty()) {
							JSONObject widgetObj = new JSONObject();
							// 获取线
							Element flowElement = eles.get(0);
							/**
							 * 输出控件
							 */
							String outTarget = flowElement.attributeValue(TARGET);
							List<Element> outEles = document.selectNodes("//*[@id='" + outTarget + "']");
							buildOutEvent(rule, document, widgetAry, widgetObj, flowRule, outEles);
						}
					}
					retObj.put("widgetEvent", widgetAry);
					if (!retObj.isEmpty()) {
						retAry.add(retObj);
					}
				}
			}
		}
	}

	/**
	 * 构建输出事件
	 * 
	 * @param rule
	 * @param document
	 * @param widgetAry
	 * @param widgetObj
	 * @param flowRule
	 * @param outEles
	 */
	private static void buildOutEvent(JSONObject rule, Document document, JSONArray widgetAry, JSONObject widgetObj, JSONObject flowRule, List<Element> outEles) {
		/**
		 * 输入输出参数
		 */
		JSONArray inoutParams = flowRule.getJSONArray("inoutparams_data");
		if (inoutParams != null && !inoutParams.isEmpty()) {
			widgetObj.put("param", inoutParams);
		}
		/**
		 * 表达式 expression_data
		 */
		JSONObject expression_data = flowRule.getJSONObject("expression_data");
		if (expression_data != null && !expression_data.isEmpty()) {
			widgetObj.put("expdata", expression_data);
		}

		Element outEle = null;
		String outName = null;
		JSONObject outRule;
		if (outEles != null && !outEles.isEmpty()) {
			outEle = outEles.get(0);
			outName = outEle.getName();
			outRule = rule.getJSONObject(outEle.attributeValue("id"));
			switch (outName) {
			case CHINAISS_PAGE_NODE: // 页面
				// output_page_data
				widgetObj.put("outWidget", outRule.getJSONArray("output_page_data"));
				break;
			case CHINAISS_CHUNK:
			case CHINAISS_CONTAINERS:// 控件
				widgetObj.put("outWidget", buildOutWidget(outRule, flowRule));
				buildOutExt(rule,document, outEle, widgetObj);
				buildChilds(rule, document, outEle, widgetObj);
				break;
			case CHINAISS_DATA_OPERATE: //快捷数据操作
				buildDataOperate(rule,document, outEle, widgetObj);
				buildChilds(rule, document, outEle, widgetObj);
				break;
			case EXCLUSIVE_GATEWAY:// 路由网关 有表达式的
				buildGateWay(rule, document, outEle, widgetAry);
				return;
			default:

				break;
			}
			widgetAry.add(widgetObj);
		}
	}

	/**
	 * 构造有网关分支条件的条件
	 * 
	 * @param rule
	 * @param document
	 * @param outEle
	 * @param widgetAry
	 * @param widgetObj
	 */
	private static void buildGateWay(JSONObject rule, Document document, Element gateWayEle, JSONArray widgetAry) {
		List<Element> outcomings = gateWayEle.selectNodes("child::bpmn2:outgoing");
		// 获取gateway输出线
		if (outcomings != null && !outcomings.isEmpty()) {
			String flowId;
			String outEleId;
			for (Element fEle : outcomings) {
				flowId = fEle.getText();
				List<Element> flowEles = document.selectNodes("//*[@id='" + flowId + "']");
				if (flowEles != null && !flowEles.isEmpty()) {
					for (Element element : flowEles) {
						outEleId = element.attributeValue(TARGET);
						List<Element> outEles = document.selectNodes("//*[@id='" + outEleId + "']");
						buildOutEvent(rule, document, widgetAry, new JSONObject(), rule.getJSONObject(flowId), outEles);
					}
				}
			}
		}

	}

	/**
	 * 构造下级事件
	 */
	private static void buildChilds(JSONObject rule, Document document, Element outEle, JSONObject widgetObj) {
		List<Element> outcomings = outEle.selectNodes("child::bpmn2:outgoing");
		String outChildName;
		JSONObject outChildRule;
		JSONObject flowRule;
		JSONArray widgetChildAry = new JSONArray();
		JSONObject widgetChildObj;
		String flowId;
		Element outChildEle;
		if (outcomings != null && outcomings.size() > 0) {
			for (Element element : outcomings) {
				widgetChildObj = new JSONObject();
				flowId = element.getText();
				if (StringUtils.containsIgnoreCase(flowId, SEQUENCE_FLOW)) {
					flowRule = rule.getJSONObject(flowId);
					if(flowRule!=null){
						List<Element> flowEles = document.selectNodes("//*[@id='" + flowId + "']");
						h1 : for (Element element2 : flowEles) {
							String outChildId = element2.attributeValue(TARGET);
							List<Element> outChildEles = document.selectNodes("//*[@id='" + outChildId + "']");
							if (outChildEles != null && !outChildEles.isEmpty()) {
								outChildEle = outChildEles.get(0);
								outChildName = outChildEle.getName();
								outChildRule = rule.getJSONObject(outChildId);
								// 回调参数
								if (flowRule.containsKey("callback_data")) {
									widgetObj.put("callback", flowRule.getJSONArray("callback_data"));
								}
								JSONArray inoutParams = flowRule.getJSONArray("inoutparams_data");
								if (inoutParams != null && !inoutParams.isEmpty()) {
									widgetChildObj.put("param", inoutParams);
								}
								switch (outChildName) {
								case CHINAISS_PAGE_NODE:
									widgetChildObj.put("outWidget", outChildRule.getJSONArray("output_page_data"));
									break;
								case CHINAISS_CHUNK:
								case CHINAISS_CONTAINERS:
									widgetChildObj.put("outWidget", buildOutWidget(outChildRule, flowRule));
									buildOutExt(rule,document, outChildEle, widgetChildObj);
									buildChilds(rule, document, outChildEle, widgetChildObj);
									break;
								case CHINAISS_DATA_OPERATE: //快捷数据操作
									buildDataOperate(rule,document, outChildEle, widgetChildObj);
									buildChilds(rule, document, outChildEle, widgetChildObj);
									break;
								case EXCLUSIVE_GATEWAY:
									buildGateWay(rule, document, outChildEle, widgetChildAry);
									break h1;
								default:
									break;
								}
								if(!widgetChildObj.isEmpty()){
									widgetChildAry.add(widgetChildObj);
								}
							}
						}
					}
				}
			}

			if (!widgetChildAry.isEmpty()) {
				widgetObj.put("childs", widgetChildAry);
			}
		}

	}
	
	/**
	 * 构建 快捷 数据操作
	 * 
	 * @param rule
	 * @param outEle
	 * @param outRule
	 * @param widgetObj
	 */
	private static void buildDataOperate(JSONObject rule,Document document, Element outEle, JSONObject widgetObj) {
		JSONObject dataOperateRule =rule.getJSONObject(outEle.attributeValue("id"));
		widgetObj.put("outWidget",dataOperateRule.getJSONArray("outWidget"));
		widgetObj.put("outExt", dataOperateRule.getJSONObject("out_ext_data"));
	}

	/**
	 * 获取所有触发控件节点
	 * 
	 * @param rootElement
	 * @return
	 */
	private static List<String> getAllTriggerNodeIds(Element rootElement) {
		List<Element> eventFlows = rootElement.selectNodes("//bpmn2:chinaissSeqFlow[@hasEvent='true']");
		String triggerNodeId = null;
		List<String> triggerNodeIds = new ArrayList<String>();
		for (Element element : eventFlows) {
			triggerNodeId = element.attributeValue(SOURCE);
			if (!triggerNodeIds.contains(triggerNodeId)) {
				triggerNodeIds.add(triggerNodeId);
			}
		}
		return triggerNodeIds;
	}

	/**
	 * 如有多输出线,根据触发事件分组
	 * 
	 * @param rule
	 * @param element
	 * @param inputList
	 * @param triggerGroup
	 */
	private static void triggetGroup(JSONObject rule, Document document, Element element, List<String> inputList, Map<String, List<String>> triggerGroup) {
		/*
		 * 获取子节点迭代器
		 */
		Iterator it = element.elementIterator();
		String eleName = null;
		String eleId = null;
		JSONObject eleRule = null;
		String triggerEvent = null;
		List<String> triggerList = new ArrayList<>();
		/**
		 * 分组
		 */
		while (it.hasNext()) {
			Element childElement = (Element) it.next();
			eleName = childElement.getName();
			eleId = childElement.getText();
			/**
			 * 输入控件
			 */
			if (IN_COMING.equalsIgnoreCase(eleName)) {
				List<Element> inEles = document.selectNodes("//*[@id='" + eleId + "']");
				if (inEles != null && !inEles.isEmpty()) {
					inputList.add(inEles.get(0).attributeValue(SOURCE));
				}
			} else if (OUT_GOING.equalsIgnoreCase(eleName)) {
				/**
				 * 输出分组
				 */
				if ((eleRule = rule.getJSONObject(eleId)) != null) {
					triggerEvent = eleRule.getString("triggrEvent");
					if (triggerGroup.containsKey(triggerEvent)) {
						triggerGroup.get(triggerEvent).add(eleId);
					} else {
						triggerGroup.put(triggerEvent, triggerList = new ArrayList<>());
						triggerList.add(eleId);
					}
				}
			}
		}
	}

	/**
	 * 构建输出扩展
	 * 
	 * @param rule
	 * @param outEle
	 * @param outRule
	 * @param widgetObj
	 */
	private static void buildOutExt(JSONObject rule,Document document, Element outEle, JSONObject widgetObj) {
		List<Element> incomings = outEle.selectNodes("child::bpmn2:incoming");
		String assistFlowId = null;
		for (Element element : incomings) {
			assistFlowId = element.getText();
			if (StringUtils.containsIgnoreCase(assistFlowId,ASSIST_FLOW)) {
				// out_ext_data
				List<Element> inEles = document.selectNodes("//*[@id='" + assistFlowId + "']");
				for (Element element2 : inEles) {
					String assisNodeId = element2.attributeValue(SOURCE);
					JSONObject assistRule = rule.getJSONObject(assisNodeId);
					widgetObj.put("outExt", assistRule.getJSONObject("out_ext_data"));
				}
				break;
			}
		}
	}

	/**
	 * 构造输出控件
	 * 
	 * @param eleRule
	 * @param flowRule
	 * @return
	 */
	private static JSONArray buildOutWidget(JSONObject eleRule, JSONObject flowRule) {
		JSONArray retAry = new JSONArray();
		JSONArray widgetAry = /* eleRule.getJSONArray("input_data") */getInputData(eleRule);
		JSONObject widgetObj = widgetAry.getJSONObject(0);

		widgetObj.put("level", widgetObj.get("klevel"));

		JSONObject retObj = new JSONObject();
		retObj.put("isEvn", true);
		retObj.put("eName", flowRule.getString("execEvent"));
		retObj.put("name", flowRule.getString("execEventName"));
		retObj.put("level", widgetObj.getString("klevel"));
		retObj.put("klevel", widgetObj.getString("klevel"));
		retObj.put("pObj", widgetObj.toJSONString());

		retAry.add(retObj);
		return retAry;
	}

	/**
	 * 构造触发控件
	 * 
	 * @param rule
	 * @param id
	 * @param eleRule
	 * @return
	 */
	private static JSONArray buildTiggerWidget(JSONObject eleRule, JSONObject flowRule) {
		JSONArray retAry = new JSONArray();
		// JSONArray widgetAry = eleRule.getJSONArray("input_data");
		JSONArray widgetAry = getInputData(eleRule);
		JSONObject widgetObj = widgetAry.getJSONObject(0);
		widgetObj.put("level", widgetObj.get("klevel"));

		JSONObject retObj = new JSONObject();
		retObj.put("isEvn", true);
		retObj.put("eName", flowRule.getString("triggrEvent"));
		retObj.put("name", flowRule.getString("triggrEventName"));
		retObj.put("level", widgetObj.getString("klevel"));
		retObj.put("klevel", widgetObj.getString("klevel"));
		retObj.put("pObj", widgetObj.toJSONString());

		retAry.add(retObj);
		return retAry;
	}

	/**
	 * 获取规则
	 * 
	 * @param ruleObj
	 * @return
	 */
	private static JSONArray getInputData(JSONObject ruleObj) {
		if (StringUtils.containsIgnoreCase(ruleObj.getString("id"), CHINAISS_CHUNK)) {
			return ruleObj.getJSONArray("complexRule").getJSONObject(0).getJSONArray("input_data");
		} else if(StringUtils.containsIgnoreCase(ruleObj.getString("id"), CHINAISS_GET_NODE)){
			return ruleObj.getJSONArray("minput_data");
		}else {
			return ruleObj.getJSONArray("input_data");
		}
	}

	/**
	 * 构造输入控件
	 * 
	 * @param inputList
	 * @param rule
	 * @return
	 */
	private static JSONArray buildInWidget(JSONObject rule, List<String> inputList) {
		JSONArray retAry = new JSONArray();
		JSONObject inRule = null;
		for (String inputId : inputList) {
			if ((inRule = rule.getJSONObject(inputId)) != null) {
				// retAry.addAll(inRule.getJSONArray("input_data"));
				retAry.addAll(getInputData(inRule));
			}
		}
		return retAry;
	}
}
