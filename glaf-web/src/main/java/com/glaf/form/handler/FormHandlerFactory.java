/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.form.handler;

import java.util.Enumeration;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.util.ClassUtils;

public class FormHandlerFactory {

	private static volatile ConcurrentMap<String, FormHandler> handlerMap = new ConcurrentHashMap<String, FormHandler>();

	private final static Log logger = LogFactory.getLog(FormHandlerFactory.class);

	private final static JSONObject emptyJson = new JSONObject();

	private final static String HANLER_NAME = "_handler_";

	private static class FormHandlerHolder {
		public static FormHandlerFactory instance = new FormHandlerFactory();
	}

	public static FormHandlerFactory getInstance() {
		return FormHandlerHolder.instance;
	}

	private FormHandlerFactory() {
		load();
	}

	private void load() {
		Properties properties = HandlerProperties.getProperties();
		Enumeration<?> e = properties.keys();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = properties.getProperty(key);
			try {
				JSONObject json = JSON.parseObject(value);
				String title = json.getString("title");
				String className = json.getString("className");
				if (StringUtils.isNotEmpty(className)) {
					Class<?> clazz = ClassUtils.classForName(className);
					Object object = BeanUtils.instantiateClass(clazz);
					if (object instanceof FormHandler) {
						FormHandler handler = (FormHandler) object;
						handlerMap.put(key, handler);
						logger.info("[" + key + "]" + title + "表单处理器已经成功加载");
					}
				}
			} catch (Exception ex) {
			}
		}
	}

	/**
	 * 执行业务处理
	 * 
	 * @param request
	 * @param response
	 * @return 返回结果为JSON对象
	 */
	public JSONObject execute(HttpServletRequest request, HttpServletResponse response) {
		String _handler_ = request.getParameter(HANLER_NAME);
		if (StringUtils.isNotEmpty(_handler_)) {
			FormHandler handler = handlerMap.get(_handler_);
			if (handler != null) {
				return handler.execute(request, response);
			}
		}
		return emptyJson;
	}
}