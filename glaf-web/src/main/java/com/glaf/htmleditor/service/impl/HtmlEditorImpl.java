package com.glaf.htmleditor.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wltea.expression.ExpressionRuntimeException;
import org.wltea.expression.datameta.Variable;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.expression.core.util.SysConstant;
import com.glaf.htmleditor.service.HtmlEditorService;

@Service("htmlEditorService")
@Transactional(readOnly = true)
public class HtmlEditorImpl implements HtmlEditorService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public String getPreviewHtml(String htmlTemplate, JSONArray htmlParamJson,
			SysConstant constant) {
		int execFlag = 0;
		String message = null;
		if (htmlTemplate != null && htmlTemplate.trim().length() > 0) {
			try {

				List<Variable> variables = getVariablesByVariableJson(htmlParamJson);
				if (variables == null) {
					variables = new ArrayList<Variable>();
				} else {
					// HTML模板替换表达式
					for (Variable variable : variables) {
						//包含特殊字符替换使用Apache StringUtils
						htmlTemplate=StringUtils.replace(htmlTemplate, variable.getVariableName(), variable.getDataValueText());
					}
				}
				message="执行成功";
				execFlag = 1;
				if (message.equals("null")) {
					execFlag = 0;
					message = "参数未设置";
				}
			} catch (final ExpressionRuntimeException e) {
				execFlag = 0;
				message = e.getMessage();
			}
		}
	    //logger.debug("htmlTemplate" + htmlTemplate
			//	+ " Test Result:::::::::::::" + message);
		return htmlTemplate;
	}

	/**
	 * 表达式变量赋值
	 * 
	 * @param expVariableJson
	 * @return
	 */
	public List<Variable> getVariablesByVariableJson(
			final JSONArray expVariableJson) {
		// 给表达式中的变量付上下文的值
		List<Variable> variables = null;
		if (expVariableJson != null && expVariableJson.size() > 0) {
			variables = new ArrayList<Variable>();
			JSONObject jsonObject = null;
			String variableName = null;
			String dataType = null;
			for (int i = 0; i < expVariableJson.size(); i++) {
				jsonObject = expVariableJson.getJSONObject(i);
				if (jsonObject != null) {
					variableName = jsonObject.getString("variableName");
					dataType = jsonObject.getString("dataType");
					if (dataType != null && dataType.trim().length() > 0) {
						switch (dataType) {
						case "string":
							variables.add(Variable.createVariable(variableName,
									"test"));
							break;
						case "int":
							variables.add(Variable.createVariable(variableName,
									1));
							break;
						case "long":
							variables.add(Variable.createVariable(variableName,
									1l));
							break;
						case "double":
							variables.add(Variable.createVariable(variableName,
									1.11d));
							break;
						case "float":
							variables.add(Variable.createVariable(variableName,
									1.1f));
							break;
						case "date":
							variables.add(Variable.createVariable(variableName,
									new Date()));
							break;
						default:
							break;
						}
					}
				}
			}
		}
		return variables;
	}
}
