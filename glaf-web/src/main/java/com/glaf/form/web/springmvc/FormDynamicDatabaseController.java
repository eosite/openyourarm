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

package com.glaf.form.web.springmvc;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller("/form/dataset")
@RequestMapping("/form/dataset")
public class FormDynamicDatabaseController {
	private static final Log logger = LogFactory.getLog(FormDynamicDatabaseController.class);

	/**
	 * 跳转到动态数据集 中
	 * 
	 * @param request
	 * @param modelMap
	 * @return
	 */
	@RequestMapping("/dynamicDatabase")
	public ModelAndView dynamicDatabase(HttpServletRequest request, ModelMap modelMap) {

		return new ModelAndView("/datamgr/dataset/dynamicDatabase");
	}

}