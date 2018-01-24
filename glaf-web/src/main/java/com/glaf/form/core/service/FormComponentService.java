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

package com.glaf.form.core.service;

import java.io.UnsupportedEncodingException;
import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormComponentService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FormComponent> list(FormComponentQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormComponentCountByQueryCriteria(FormComponentQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormComponent> getFormComponentsByQueryCriteria(int start,
			int pageSize, FormComponentQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormComponent getFormComponent(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormComponent formComponent);

	public List<FormComponent> getComponentPropertyList(FormComponentQuery query);
	/**
	 * 创建控件模板渲染js
	 */
	void createRenderjs(String contextPath) throws Exception;
	/**
	 * 创建用于页面设计器右侧控件模板区的静态HTML
	 * @param webRootPath
	 * @param contextPath
	 */
	void createStyleTemplate(String webRootPath,String contextPath)throws Exception ;
	/**
	 * 生成页面设计器控件静态模板HTML文件
	 * @param webRootPath
	 */
	void createStyleTemplateContent(String webRootPath) throws UnsupportedEncodingException;

	void updateBaseComp( Long id, int baseComp);
}
