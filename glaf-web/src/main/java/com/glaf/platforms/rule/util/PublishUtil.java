package com.glaf.platforms.rule.util;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.expression.core.util.ExpressionConvertUtil;

public class PublishUtil {

	public PublishUtil() {
	}

	/**
	 * 屏幕查询事件初始化
	 * 
	 * @param pageMap
	 * @param pageJs
	 */
	public static void screenInit(Map<String, String> pageMap, StringBuffer pageJs) {
		String screenZoom = pageMap.get("screenZoom");
		JSONArray screenZoomArray = JSON.parseArray(screenZoom);
		if (screenZoomArray != null && !screenZoomArray.isEmpty()) {
			JSONObject screenZoomObject = screenZoomArray.getJSONObject(0);
			JSONArray valArray = screenZoomObject.getJSONArray("values");
			if (valArray != null && !valArray.isEmpty()) {
				pageJs.append("<style type='text/css'>");
				JSONObject jo = null;
				for (Object object : valArray) {
					jo = (JSONObject) object;
					pageJs.append(" @media only screen ");
					String maxWidth = jo.getString("maxWidth");
					if (maxWidth != null && StringUtils.isNotEmpty(maxWidth)) {
						pageJs.append(" and (max-width : " + maxWidth + "px) ");
					}
					String minWidth = jo.getString("minWidth");
					if (minWidth != null && StringUtils.isNotEmpty(minWidth)) {
						pageJs.append(" and (min-width : " + minWidth + "px) ");
					}
					String maxHeight = jo.getString("maxHeight");
					if (maxHeight != null && StringUtils.isNotEmpty(maxHeight)) {
						pageJs.append(" and (max-height : " + maxHeight + "px) ");
					}
					String minHeight = jo.getString("minHeight");
					if (minHeight != null && StringUtils.isNotEmpty(minHeight)) {
						pageJs.append(" and (min-height : " + minHeight + "px) ");
					}
					pageJs.append("{body {width: " + jo.getString("widtha") + ";height: " + jo.getString("heighta") + ";}} ");
				}
				pageJs.append("</style>");
			}
		}
	}

	/**
	 * 事件定义器初始化
	 * 
	 * @param pageMap
	 * @param pageJs
	 */
	public static void eventInit(Map<String, String> pageMap, StringBuffer pageJs, Map<String, String> initScriptMap, Map<String, Object> otherMap) {
		String eventsDefined = pageMap.get("eventsDefined");
		JSONArray eventsDefinedArray = JSON.parseArray(eventsDefined);
		if (eventsDefinedArray != null && !eventsDefinedArray.isEmpty()) {
			JSONArray edvArray = eventsDefinedArray.getJSONObject(0).getJSONArray("values");
			JSONArray retEdv = getEvent(edvArray, initScriptMap, otherMap);
			if (retEdv != null && !retEdv.isEmpty()) {
				String eventWeighting = pageMap.get("eventWeighting");
				pageJs.append("<script>pageed = '" + retEdv.toJSONString().replaceAll("'", "\\\\'") + "',isWeighting="
						+ (StringUtils.isNotEmpty(eventWeighting) ? eventWeighting : false) + ";</script>");
			}
		}
	}

	/**
	 * 遍历节点
	 * 
	 * @param weAry
	 * @return
	 */
	protected static JSONArray getEvent(JSONArray edvArray, Map<String, String> initScriptMap, Map<String, Object> otherMap) {
		JSONObject varObj = (JSONObject) otherMap.get("_var_");

		JSONArray retEdv = new JSONArray();
		if (edvArray != null && !edvArray.isEmpty()) {
			JSONObject retEdvObj = new JSONObject();
			JSONObject edvObj = null;
			JSONArray childops;
			for (Object object : edvArray) {
				edvObj = (JSONObject) object;
				retEdvObj = new JSONObject();
				// 处理输入控件
				getInWidget(edvObj, retEdvObj, varObj);
				// 处理触发事件
				getWidget(edvObj, retEdvObj, varObj);
				// 表达式事件定义函数
				String widgetEvent = edvObj.getString("widgetEvent");
				if (widgetEvent != null && !"".equals(widgetEvent)) {
					JSONArray weAry = JSON.parseArray(widgetEvent);
					if (weAry != null && !weAry.isEmpty()) {
						JSONArray weRetAry = getWidgetEvent(weAry, initScriptMap, varObj);
						retEdvObj.put("ops", weRetAry);
					}
				}
				// 子节点
				JSONArray childs = edvObj.getJSONArray("childs");
				if (childs != null && !childs.isEmpty()) {
					childops = getEvent(childs, initScriptMap, otherMap);
					if (childops != null && !childops.isEmpty()) {
						retEdvObj.put("childs", childops);
					}
				}
				retEdv.add(retEdvObj);
			}
		}
		return retEdv;
	}

	/**
	 * 获取事件定义编辑器数据
	 * 
	 * @param weAry
	 * @return
	 */
	private static JSONArray getWidgetEvent(JSONArray weAry, Map<String, String> initScriptMap, JSONObject varObj) {
		JSONArray weRetAry = new JSONArray();
		if (weAry != null && !weAry.isEmpty()) {
			JSONObject weRetObj; //
			JSONObject weObj;
			JSONArray childs;
			JSONArray childops;
			for (Object we : weAry) {
				weObj = (JSONObject) we;
				weRetObj = new JSONObject();
				// 表达式
				getExp(weObj, weRetObj, varObj);
				// 处理输出控件
				getOutWidget(weObj, weRetObj, initScriptMap, varObj);
				// param参数
				getParam(weObj, weRetObj, varObj);
				// 华表公式
				getCellExp(weObj, weRetObj);
				// 下级节点 childs
				childs = weObj.getJSONArray("childs");
				if (childs != null && !childs.isEmpty()) {
					childops = getWidgetEvent(childs, initScriptMap, varObj);
					if (childops != null && !childops.isEmpty()) {
						weRetObj.put("childs", childops);
					}
				}
				weRetAry.add(weRetObj);
			}
		}
		return weRetAry;
	}

	/***
	 * gridbt里面联动的控件
	 * 
	 * @param editor
	 * @return
	 */
	private static String getProtoEditor(String editor) {
		String[] eds = { "metroselect", "metroselect_m" };
		List<String> list = Arrays.asList(eds);
		if (list.contains(editor)) {
			String[] proEds = { "select", "select2" };
			return proEds[list.indexOf(editor)];
		}
		return editor;
	}

	/**
	 * 获取输入输出参数定义
	 * 
	 * @param weObj
	 * @param weRetObj
	 */
	private static void getParam(JSONObject weObj, JSONObject weRetObj, JSONObject varObj) {
		JSONObject outExtObj = null;
		String useId = null;
		try {
			outExtObj = weObj.getJSONObject("outExt");
			if (outExtObj != null && !outExtObj.isEmpty()) {
				useId = outExtObj.getString("value");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		JSONArray retCallbackAry = new JSONArray();
		JSONArray callbackAry = weObj.getJSONArray("callback");
		if (callbackAry != null && !callbackAry.isEmpty()) {
			JSONObject callbackObj = callbackAry.getJSONObject(0).getJSONObject("datas");
			if (callbackObj != null && !callbackObj.isEmpty()) {
				Set<String> callbackKeys = callbackObj.keySet();
				for (String callbackKey : callbackKeys) {
					JSONArray callAry = callbackObj.getJSONArray(callbackKey);
					for (Object object : callAry) {
						JSONObject callObj = (JSONObject) object;
						JSONObject retCallbackObj = new JSONObject();
						retCallbackObj.put("columnName", callObj.getString("columnName"));
						retCallbackObj.put("param", callObj.getString("param"));
						retCallbackObj.put("dataId", callObj.getString("dataId"));
						varObj.put(callObj.getString("columnName"), callObj.getString("inname"));
						varObj.put(callObj.getString("param"), callObj.getString("outname"));
						retCallbackAry.add(retCallbackObj);
					}
				}
			}
		}

		String param = weObj.getString("param");
		if (param != null && !"".equals(param)) {
			JSONArray paramAry = JSON.parseArray(param);
			if (paramAry != null && !paramAry.isEmpty()) {
				JSONObject paramDataObj = paramAry.getJSONObject(0).getJSONObject("datas");
				JSONObject retParamObj = new JSONObject();

				if (paramDataObj != null && !paramDataObj.isEmpty()) {
					Set<String> paramKeys = paramDataObj.keySet();
					JSONArray eleInOutParams = null;
					JSONObject eleInOutParam = null;
					JSONArray retEleInOutParams = null;
					JSONObject retEleInOutParam = null;
					for (String paramKey : paramKeys) {
						eleInOutParams = paramDataObj.getJSONArray(paramKey);
						retEleInOutParams = new JSONArray();
						if (eleInOutParams != null && !eleInOutParams.isEmpty()) {
							for (Object object2 : eleInOutParams) {
								eleInOutParam = (JSONObject) object2;
								retEleInOutParam = new JSONObject();
								if (eleInOutParam.getString("cssClass") != null) { // 表达式
									JSONArray ins = new JSONArray();
									String inDataStr = eleInOutParam.getString("data");
									if (inDataStr != null && !"".equals(inDataStr)) {
										JSONObject inDataObj = JSON.parseObject(inDataStr);
										String insExp = inDataObj.getString("expActVal");
										// retEleInOutParam.put("insExp",
										// ExpressionConvertUtil.expressionConvert(insExp.replaceAll("\"",
										// "\\\\\\\""),
										// ExpressionConvertUtil.JAVASCRIPT_TYPE).replace("\n",
										// "").replace("\r", ""));
										// ExpressionConvertUtil.constantConvert
										retEleInOutParam.put("insExp",
												ExpressionConvertUtil
														.getFuncConvert(ExpressionConvertUtil.constantConvert(insExp.replaceAll("\"", "\\\\\\\""),
																ExpressionConvertUtil.JAVASCRIPT_TYPE, null), ExpressionConvertUtil.JAVASCRIPT_TYPE)
														.replace("\n", "").replace("\r", ""));
										JSONArray varValAry = inDataObj.getJSONArray("varVal");
										if (varValAry != null && !varValAry.isEmpty()) {
											JSONObject varVal = null;
											JSONObject varValObj = null;
											JSONObject val = null;
											for (Object object3 : varValAry) {
												varVal = (JSONObject) object3;
												val = varVal.getJSONObject("value");
												if (val.getString("eleId") != null && !"".equals(val.getString("eleId"))) {
													varValObj = new JSONObject();
													varValObj.put("inid", val.getString("eleId"));
													varValObj.put("inlev", val.getString("lev"));
													varValObj.put("inpage", val.getString("pageId"));
													varValObj.put("columnName", val.getString("columnName"));
													varValObj.put("type", val.getString("fnType"));
													varValObj.put("param", val.getString("code"));
													varValObj.put("idatasetId", val.getString("datasetId"));
													// varValObj.put("itype",
													// val.getString("otype"));
													varValObj.put("iid", val.getString("otype"));
													varValObj.put("idataId", val.getString("idataId"));
													varValObj.put("ie", getProtoEditor(val.getString("iEditor")));
													ins.add(varValObj);
													varObj.put(eleInOutParam.getString("columnName"), eleInOutParam.getString("inname"));
												}
											}
											retEleInOutParam.put("ins", ins);
										}
									}
								} else {
									retEleInOutParam.put("inid", eleInOutParam.getString("inid"));
									retEleInOutParam.put("inlev", eleInOutParam.getString("inlev"));
									retEleInOutParam.put("inpage", eleInOutParam.getString("inpage"));
									retEleInOutParam.put("columnName", eleInOutParam.getString("columnName"));
									retEleInOutParam.put("type", eleInOutParam.getString("type"));
									// retEleInOutParam.put("itype",
									// eleInOutParam.getString("itype"));
									retEleInOutParam.put("idatasetId", eleInOutParam.getString("idatasetId"));
									retEleInOutParam.put("iid", eleInOutParam.getString("itype"));
									retEleInOutParam.put("idataId", eleInOutParam.getString("idataId"));
									retEleInOutParam.put("ie", getProtoEditor(eleInOutParam.getString("iEditor")));
									varObj.put(eleInOutParam.getString("columnName"), eleInOutParam.getString("inname"));
								}
								retEleInOutParam.put("outid", eleInOutParam.getString("outid"));
								retEleInOutParam.put("outlev", eleInOutParam.getString("outlev"));
								retEleInOutParam.put("outpage", eleInOutParam.getString("outpage"));
								retEleInOutParam.put("eventType", eleInOutParam.getString("eventType"));
								retEleInOutParam.put("param", eleInOutParam.getString("param"));
								// retEleInOutParam.put("otype",
								// eleInOutParam.getString("otype"));
								retEleInOutParam.put("oid", eleInOutParam.getString("otype"));
								retEleInOutParam.put("useId", useId);
								retEleInOutParam.put("srcUrl", eleInOutParam.getString("srcUrl"));
								retEleInOutParam.put("dataId", eleInOutParam.getString("dataId"));
								retEleInOutParam.put("oe", getProtoEditor(eleInOutParam.getString("oEditor")));
								varObj.put(eleInOutParam.getString("param"), eleInOutParam.getString("outname"));
								if (!retCallbackAry.isEmpty())
									retEleInOutParam.put("callback", JSON.parseArray(retCallbackAry.toJSONString()));

								retEleInOutParams.add(retEleInOutParam);
							}
							retParamObj.put(paramKey, retEleInOutParams);
						}
					}
				}
				weRetObj.put("param", retParamObj);
			}
		}
	}

	/**
	 * 华表表达式
	 * 
	 * @param weObj
	 * @param weRetObj
	 */
	private static void getCellExp(JSONObject weObj, JSONObject weRetObj) {
		String cell = weObj.getString("cell");
		if (cell != null && !"".equals(cell)) {
			// cellExpdata
			weRetObj.put("cell", JSON.parseObject(cell));
		}

		String cellExp = weObj.getString("cellExp");
		if (cellExp != null && !"".equals(cellExp)) {
			// cellExpdata
			JSONObject cellexp = new JSONObject();
			cellexp.put("exp", cellExp.replaceAll("\"", "\\\\\"").replaceAll("'", "\\\\\""));
			weRetObj.put("cellExp", cellexp);
		}
	}

	/**
	 * 事件定义器表达式
	 * 
	 * @param weObj
	 * @param weRetObj
	 */
	private static void getExp(JSONObject weObj, JSONObject weRetObj, JSONObject var_Obj) {
		JSONObject exp = new JSONObject();
		String expdata = weObj.getString("expdata");
		String expStr = "";
		// 表达式内判断参数
		if (expdata != null && !"".equals(expdata)) {
			JSONObject expdataObj = JSON.parseObject(expdata);
			if (expdataObj != null) {
				expStr = expdataObj.getString("expActVal");
				JSONArray varVal = expdataObj.getJSONArray("varVal");
				if (varVal != null && !varVal.isEmpty()) {
					JSONObject varObj = null;
					JSONObject valdata = null;
					JSONArray expAry = new JSONArray();
					for (Object object2 : varVal) {
						varObj = (JSONObject) object2;
						valdata = varObj.getJSONObject("value");
						if (valdata != null) {
							JSONObject expobj = new JSONObject();
							if (valdata.getString("eleId") != null && !"".equals(valdata.getString("eleId"))) {
								expobj.put("inpage", valdata.getString("pageId"));
								expobj.put("inid", valdata.getString("eleId"));
								expobj.put("type", valdata.getString("fnType"));
								expobj.put("isFn", valdata.getString("isFn"));
								expobj.put("columnName", valdata.getString("columnName"));
								expobj.put("idatasetId", valdata.getString("datasetId"));
								expobj.put("param", valdata.getString("code"));
								expobj.put("inlev", valdata.getString("lev"));
								
								expobj.put("iid", valdata.getString("otype"));
								expobj.put("ie", valdata.getString("oEditor"));
								var_Obj.put(valdata.getString("columnName"), valdata.getString("inname"));
								expAry.add(expobj);
							}
						}

					}
					exp.put("expdata", expAry);
				}
			}
		}
		exp.put("exp", ExpressionConvertUtil.getFuncConvert(ExpressionConvertUtil.constantConvert(expStr.replaceAll("\"", "\\\\\\\""), ExpressionConvertUtil.JAVASCRIPT_TYPE, null),
				ExpressionConvertUtil.JAVASCRIPT_TYPE).replace("\n", "").replace("\r", ""));
		// exp.put("exp", expStr.replaceAll("\"", "\\\\\""));
		weRetObj.put("expdata", exp);
	}

	/**
	 * 输出控件
	 * 
	 * @param weObj
	 * @param weRetObj
	 */
	private static void getOutWidget(JSONObject weObj, JSONObject weRetObj, Map<String, String> initScriptMap, JSONObject varObj) {
		JSONObject outExtObj = null;
		String useId = null;
		if (weObj.containsKey("outExt")) {
			try {
				outExtObj = weObj.getJSONObject("outExt");
				if (outExtObj != null && !outExtObj.isEmpty()) {
					useId = outExtObj.getString("value");
				}
			} catch (Exception e) {
			}
		}

		String outWidget = weObj.getString("outWidget");
		if (outWidget != null && !"".equals(outWidget)) {
			JSONArray outAry = JSON.parseArray(outWidget);
			if (outAry != null && !outAry.isEmpty()) {
				JSONArray outRetAry = new JSONArray();
				JSONObject outObj;
				String pobj;
				JSONObject opObj;
				JSONObject outRetObj;
				for (Object outo : outAry) {
					outObj = (JSONObject) outo;
					outRetObj = new JSONObject();
					String eventType = outObj.getString("eName");
					outRetObj.put("eventType", eventType);
					pobj = outObj.getString("pObj");
					if (StringUtils.isNotEmpty(pobj)) {
						if (!outObj.getBooleanValue("isPage")) {
							opObj = JSON.parseObject(pobj);
							outRetObj.put("outlev", opObj.getString("level"));
							outRetObj.put("outpage", opObj.getString("pageId"));
							outRetObj.put("outid", opObj.getString("eleId"));
							outRetObj.put("oe", getProtoEditor(opObj.getString("oEditor")));
							varObj.put(opObj.getString("eleId"), opObj.getString("name"));
							if ("_init_".equalsIgnoreCase(eventType)) {
								initScriptMap.put(opObj.getString("eleId"), null);
							}
							if (opObj.getString("otype") != null) {
								outRetObj.put("otype", opObj.getString("otype"));
								String kobj = opObj.getString("pObj");
								JSONObject kObj = JSON.parseObject(kobj);
								outRetObj.put("oid", kObj.getString("eleId"));
							}
						} else { // 为弹出页面
							outRetObj.put("outid", outObj.getString("id"));
							outRetObj.put("srcUrl", outObj.getString("srcUrl"));
							outRetObj.put("outpage", outObj.getString("id"));
							varObj.put(outObj.getString("id"), outObj.getString("name"));
						}
					}
					outRetObj.put("useId", useId);
					outRetAry.add(outRetObj);
				}
				weRetObj.put("out", outRetAry);
			}
		}
	}

	/**
	 * 触发控件
	 * 
	 * @param edvObj
	 * @param retEdvObj
	 */
	private static void getWidget(JSONObject edvObj, JSONObject retEdvObj, JSONObject varObj) {
		String widget = edvObj.getString("widget");
		if (widget != null && !"".equals(widget)) {
			JSONArray wAry = JSON.parseArray(widget);
			if (wAry != null && !wAry.isEmpty()) {
				JSONArray wRetAry = new JSONArray();
				JSONObject wObj;
				String wobj;
				JSONObject wpObj;
				JSONObject wRetObj;
				for (Object w : wAry) {
					wObj = (JSONObject) w;
					wobj = wObj.getString("pObj");
					wpObj = JSON.parseObject(wobj);
					wRetObj = new JSONObject();
					wRetObj.put("level", wpObj.getString("level"));
					wRetObj.put("pageId", wpObj.getString("pageId"));
					wRetObj.put("eleId", wpObj.getString("eleId"));
					wRetObj.put("eventType", wObj.getString("eName"));
					varObj.put(wpObj.getString("eleId"), wpObj.getString("name"));
					varObj.put(wObj.getString("eName"), wObj.getString("name"));
					if (wpObj.getString("otype") != null) {
						wRetObj.put("otype", wpObj.getString("otype"));
						String kobj = wpObj.getString("pObj");
						JSONObject kObj = JSON.parseObject(kobj);
						wRetObj.put("oid", kObj.getString("eleId"));
					}
					wRetAry.add(wRetObj);
				}
				retEdvObj.put("trigger", wRetAry);
			}
		}
	}

	/**
	 * 获取输入控件定义
	 * 
	 * @param edvObj
	 * @param retEdvObj
	 */
	private static void getInWidget(JSONObject edvObj, JSONObject retEdvObj, JSONObject varObj) {
		String inWidget = edvObj.getString("inWidget");
		if (inWidget != null && !"".equals(inWidget)) {
			JSONArray inAry = JSON.parseArray(inWidget);
			if (inAry != null && !inAry.isEmpty()) {
				JSONArray inRetAry = new JSONArray();
				JSONObject inObj;
				JSONObject inRetObj;
				for (Object ino : inAry) {
					inObj = (JSONObject) ino;
					inRetObj = new JSONObject();
					inRetObj.put("level", inObj.getString("level"));
					inRetObj.put("pageId", inObj.getString("pageId"));
					inRetObj.put("eleId", inObj.getString("eleId"));
					varObj.put(inObj.getString("eleId"), inObj.getString("name"));
					if (inObj.getString("otype") != null) {
						inRetObj.put("otype", inObj.getString("otype"));
						String kobj = inObj.getString("pObj");
						JSONObject kObj = JSON.parseObject(kobj);
						inRetObj.put("oid", kObj.getString("eleId"));
					}
					inRetAry.add(inRetObj);
				}
				retEdvObj.put("in", inRetAry);
			}
		}
	}

	/**
	 * 验证器规则初始化
	 * 
	 * @param pageMap
	 * @param pageJs
	 */
	public static void validateInit(Map<String, String> pageMap, StringBuffer pageJs) {
		JSONArray retArray = new JSONArray();
		String validate = pageMap.get("validate");
		if (validate != null && !"".equals(validate)) {
			JSONArray validateArray = JSON.parseArray(validate);
			if (validateArray != null && !validateArray.isEmpty()) {
				JSONArray nodes = validateArray.getJSONObject(0).getJSONArray("nodes");
				if (nodes != null && !nodes.isEmpty()) {
					JSONObject node;
					JSONObject retObj;
					for (Object object : nodes) {
						node = (JSONObject) object;
						if (node != null) {
							retObj = getEffectiveData(node);
							retArray.add(retObj);
						}
					}
				}
			}
		}
		pageJs.append("<script>mtValidate = '" + retArray.toJSONString() + "';</script>");
	}

	/**
	 * 获取有效数据
	 * 
	 * @param node
	 * @return
	 */
	private static JSONObject getEffectiveData(JSONObject node) {
		JSONObject retObj = new JSONObject();
		if (!node.isEmpty()) {
			// 输入参数
			// String inputData = node.getString("inputData");
			// 执行表达式
			String execExpData = node.getString("execExpData");
			if (execExpData != null && !"".equals(execExpData)) {
				JSONObject execExpDataObj = getExp(execExpData);
				retObj.put("execExp", execExpDataObj.getString("expActVal").replaceAll("\"", "\\\\\""));
				retObj.put("execExpData", execExpDataObj.getJSONArray("expAry"));
			}

			// 验证表达式
			String expData = node.getString("expData");
			if (expData != null && !"".equals(expData)) {
				JSONObject expDataObj = getExp(expData);
				retObj.put("exp", expDataObj.getString("expActVal").replaceAll("\"", "\\\\\""));
				retObj.put("expData", expDataObj.getJSONArray("expAry"));
			}

			// 验证项
			String triggerData = node.getString("triggerData");
			if (triggerData != null && !"".equals(triggerData)) {
				JSONObject triObj = JSON.parseObject(triggerData);
				JSONArray nodes = triObj.getJSONArray("nodes");
				retObj.put("trigger", getControl(nodes.toJSONString()));
			}

			// 验证类型
			retObj.put("type", node.getString("type"));

			// 验证提示
			retObj.put("tip", node.getString("tip"));

			// 验证提示类型
			retObj.put("tipType", node.getString("tipType"));

		}
		return retObj;
	}

	/**
	 * 获取控件
	 * 
	 * @param widget
	 * @return
	 */
	private static JSONArray getControl(String widget) {
		JSONArray retAry = new JSONArray();
		if (widget != null && !"".equals(widget)) {
			JSONArray inAry = JSON.parseArray(widget);
			if (inAry != null && !inAry.isEmpty()) {
				JSONObject retObj;
				JSONObject jsonObj;
				JSONObject pObj;
				String pObjStr = null;
				for (Object ino : inAry) {
					jsonObj = (JSONObject) ino;
					pObjStr = jsonObj.getString("pObj");
					retObj = new JSONObject();
					retObj.put("level", jsonObj.getString("level"));
					retObj.put("inlev", jsonObj.getString("klevel"));
					retObj.put("itype", jsonObj.getString("otype"));
					if (StringUtils.isNotEmpty(pObjStr)) {
						pObj = JSON.parseObject(pObjStr);
						retObj.put("iid", pObj.getString("eleId"));
					}
					retObj.put("inpage", jsonObj.getString("pageId"));
					retObj.put("inid", jsonObj.getString("eleId"));
					retAry.add(retObj);
				}
			}
		}
		return retAry;
	}

	/**
	 * 表达式转换
	 * 
	 * @param expdata
	 * @return
	 */
	private static JSONObject getExp(String expdata) {
		JSONObject ko = new JSONObject();
		// 表达式内判断参数
		if (expdata != null && !"".equals(expdata)) {
			JSONObject expdataObj = JSON.parseObject(expdata);
			if (expdataObj != null) {
				String expActVal = ExpressionConvertUtil.vaildateFunConvert(expdataObj.getString("expActVal"), "k");
				ko.put("expActVal", expActVal);
				JSONArray varVal = expdataObj.getJSONArray("varVal");
				if (varVal != null && !varVal.isEmpty()) {
					JSONObject varObj = null;
					JSONObject valdata = null;
					JSONArray expAry = new JSONArray();
					for (Object object2 : varVal) {
						varObj = (JSONObject) object2;
						valdata = varObj.getJSONObject("value");
						if (valdata != null) {
							JSONObject expobj = new JSONObject();
							if (valdata.getString("eleId") != null && !"".equals(valdata.getString("eleId"))) {
								expobj.put("inpage", valdata.getString("pageId"));
								expobj.put("inid", valdata.getString("eleId"));
								expobj.put("type", valdata.getString("fnType"));
								expobj.put("isFn", valdata.getString("isFn"));
								expobj.put("columnName", valdata.getString("columnName"));
								expobj.put("param", valdata.getString("code"));
								expobj.put("inlev", valdata.getString("lev"));
								expobj.put("otype", valdata.getString("otype"));
								expobj.put("iid", valdata.getString("otype"));
								expobj.put("ie", getProtoEditor(valdata.getString("iEditor")));
								expAry.add(expobj);
							}
						}

					}
					ko.put("expAry", expAry);
				}
			}
		}
		return ko;
	}
}
