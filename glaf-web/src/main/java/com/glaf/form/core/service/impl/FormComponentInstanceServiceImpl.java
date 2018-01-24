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
package com.glaf.form.core.service.impl;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormComponentInstanceService;

@Service("formComponentInstanceService")
@Transactional(readOnly = true)
public class FormComponentInstanceServiceImpl implements
		FormComponentInstanceService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormComponentInstanceMapper formComponentInstanceMapper;

	public FormComponentInstanceServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formComponentInstanceMapper.deleteFormComponentInstanceById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formComponentInstanceMapper.deleteFormComponentInstanceById(id);
			}
		}
	}

	public int count(FormComponentInstanceQuery query) {
		return formComponentInstanceMapper.getFormComponentInstanceCount(query);
	}

	public List<FormComponentInstance> list(FormComponentInstanceQuery query) {
		List<FormComponentInstance> list = formComponentInstanceMapper
				.getFormComponentInstances(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormComponentInstanceCountByQueryCriteria(
			FormComponentInstanceQuery query) {
		return formComponentInstanceMapper.getFormComponentInstanceCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormComponentInstance> getFormComponentInstancesByQueryCriteria(
			int start, int pageSize, FormComponentInstanceQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormComponentInstance> rows = sqlSessionTemplate.selectList(
				"getFormComponentInstances", query, rowBounds);
		return rows;
	}

	public FormComponentInstance getFormComponentInstance(String id) {
		if (id == null) {
			return null;
		}
		FormComponentInstance formComponentInstance = formComponentInstanceMapper
				.getFormComponentInstanceById(id);
		return formComponentInstance;
	}

	@Transactional
	public void save(FormComponentInstance formComponentInstance) {
		if (StringUtils.isEmpty(formComponentInstance.getId())) {
			formComponentInstance.setId(idGenerator
					.getNextId("FORM_COMPONENT_INSTANCE"));
			formComponentInstance.setCreateDate(new Date());
			formComponentInstanceMapper
					.insertFormComponentInstance(formComponentInstance);
		} else {
			formComponentInstanceMapper
					.updateFormComponentInstance(formComponentInstance);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setFormComponentInstanceMapper(
			FormComponentInstanceMapper formComponentInstanceMapper) {
		this.formComponentInstanceMapper = formComponentInstanceMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
