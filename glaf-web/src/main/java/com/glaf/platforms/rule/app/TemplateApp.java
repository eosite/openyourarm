package com.glaf.platforms.rule.app;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.platforms.rule.FormPageParserUtil;

/**
 * 用于处理数据源规则
 * 
 */
public class TemplateApp {
	protected static final Log logger = LogFactory.getLog(TemplateApp.class);

	public TemplateApp() {
	}

	public static String getTempStr(Object model, Method[] methods, String attrTemplate, String dataTemplate, StringBuffer attrStr, StringBuffer dataStr) {
		String attrTempStr = attrTemplate.toString();
		String dataTempStr = dataTemplate.toString();
		// 正则替换对应的信息
		String regex = "";
		Object widgetId = "";
		Object widgetName = "";
		String name = "";
		String methodName = "";
		Object methodReturnValue = null;
		try {
			Object sourceMap = null;
			for (Method m : methods) {
				methodName = m.getName();
				if ((m.getName().startsWith("get") || m.getName().startsWith("is")) && !"getTemplateScript".equalsIgnoreCase(m.getName())) {
					Object[] args = null;
					methodReturnValue = m.invoke(model, args);
					if (m.getName().equalsIgnoreCase("getSource")) {
						sourceMap = methodReturnValue;
					}
					if (m.getName().equalsIgnoreCase("getId")) {
						widgetId = methodReturnValue;
					}
					if (m.getName().equalsIgnoreCase("getName")) {
						widgetName = methodReturnValue;
					}
					if (methodReturnValue != null && !"".equals(methodReturnValue.toString())) {
						name = (m.getName().startsWith("get") ? m.getName().replaceFirst("get", "") : m.getName().replaceFirst("is", "")).toLowerCase();
						regex = "\\{\\{" + name + "\\}\\}";
						attrTempStr = FormPageParserUtil.replaceByRegex(attrTempStr, regex, methodReturnValue.toString());

						dataTempStr = FormPageParserUtil.replaceByRegex(dataTempStr, regex, methodReturnValue.toString());
					}
				}
			}
			try {
				if (StringUtils.isNotBlank(attrTempStr) && sourceMap != null) {
					JSONObject jsonObject = JSON.parseObject(attrTempStr);
					@SuppressWarnings("unchecked")
					Map<String, String> map = (Map<String, String>) sourceMap;
					for (String key : jsonObject.keySet()) {
						if (map.containsKey(key)) {
							if (jsonObject.get(key) == null || (jsonObject.get(key) != null && jsonObject.getString(key).startsWith("{{"))) {
								jsonObject.put(key, map.get(key));
							}
						}
					}
					attrTempStr = jsonObject.toJSONString();
				}
			} catch (Exception e) {
				logger.info("属性自动匹配出错，不影响程序运行!");
			}
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			logger.error("DataSourceApp " + widgetId + "<->" + widgetName + "\n" + methodName + "<->" + (methodReturnValue != null ? methodReturnValue.toString() : null) + ":\n "
					+ e.getMessage());
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
			logger.error("DataSourceApp " + widgetId + "<->" + widgetName + "\n" + methodName + "<->" + (methodReturnValue != null ? methodReturnValue.toString() : null) + ":\n "
					+ e.getMessage());
		} catch (InvocationTargetException e) {
			e.printStackTrace();
			logger.error("DataSourceApp " + widgetId + "<->" + widgetName + "\n" + methodName + "<->" + (methodReturnValue != null ? methodReturnValue.toString() : null) + ":\n "
					+ e.getMessage());
		}
		// 正则匹配 匹配 xxx : "{{xxxx}}"
		regex = "\\\"?\\w+\\-?\\w+\\-?\\w+\\\"? *\\:\\ *\\\"?\\{\\{\\w+\\}\\}\\\"?,?";
		attrTempStr = FormPageParserUtil.replaceByRegex(attrTempStr, regex, "");
		regex = "\\w+\\ *\\:\\ *\\\"?\\{\\{\\w+\\}\\}\\\"?,?";
		dataTempStr = FormPageParserUtil.replaceByRegex(dataTempStr, regex, "");
		regex = "\\\"?\\{\\{\\w+\\}\\}";
		attrTempStr = FormPageParserUtil.replaceByRegex(attrTempStr, regex, "");
		dataTempStr = FormPageParserUtil.replaceByRegex(dataTempStr, regex, "");
		regex = "'##=";
		attrTempStr = FormPageParserUtil.replaceByRegex(attrTempStr, regex, "");
		dataTempStr = FormPageParserUtil.replaceByRegex(dataTempStr, regex, "");
		regex = "=##'";
		attrTempStr = FormPageParserUtil.replaceByRegex(attrTempStr, regex, "");
		dataTempStr = FormPageParserUtil.replaceByRegex(dataTempStr, regex, "");
		attrStr.append(attrTempStr);
		dataStr.append(dataTempStr);
		/*
		 * regex = "\\\"?\\{\\{\\w+\\}\\}" ; attrTempStr =
		 * FormPageParserUtil.replaceByRegex(attrTempStr, regex, "");
		 * attrTempStr = FormPageParserUtil.replaceByRegex(attrTempStr, regex,
		 * ""); regex = "'##=" ; attrTempStr =
		 * FormPageParserUtil.replaceByRegex(attrTempStr, regex, "");
		 * attrTempStr = FormPageParserUtil.replaceByRegex(attrTempStr, regex,
		 * ""); regex = "=##'" ; attrTempStr =
		 * FormPageParserUtil.replaceByRegex(attrTempStr, regex, "");
		 * attrTempStr = FormPageParserUtil.replaceByRegex(attrTempStr, regex,
		 * "");
		 */

		return null;
	}
}
