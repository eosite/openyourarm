package com.glaf.form.handler;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;

import com.alibaba.fastjson.JSONObject;

public class SimpleFormHandler implements FormHandler {

	public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
		JSONObject json = new JSONObject();
		Enumeration<?> enumeration = request.getParameterNames();
		while (enumeration.hasMoreElements()) {
			String paramName = (String) enumeration.nextElement();
			String paramValue = request.getParameter(paramName);
			if (StringUtils.isNotEmpty(paramName) && StringUtils.isNotEmpty(paramValue)) {
				if (paramValue.equalsIgnoreCase("null")) {
					continue;
				}
				json.put(paramName, paramValue);
			}
		}
		return json;
	}

}
