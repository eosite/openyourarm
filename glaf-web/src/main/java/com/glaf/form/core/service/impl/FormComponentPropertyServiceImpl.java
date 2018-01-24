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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.id.*;
import com.glaf.core.cache.CacheFactory;
import com.glaf.core.config.SystemConfig;
import com.glaf.core.dao.*;
import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormComponentPropertyService;
import com.glaf.form.core.util.FormComponentPropertyJsonFactory;

@Service("formComponentPropertyService")
@Transactional(readOnly = true)
public class FormComponentPropertyServiceImpl implements FormComponentPropertyService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormComponentPropertyMapper formComponentPropertyMapper;

	public FormComponentPropertyServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			formComponentPropertyMapper.deleteFormComponentPropertyById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				formComponentPropertyMapper.deleteFormComponentPropertyById(id);
			}
		}
	}

	public int count(FormComponentPropertyQuery query) {
		return formComponentPropertyMapper.getFormComponentPropertyCount(query);
	}

	public List<FormComponentProperty> list(FormComponentPropertyQuery query) {
		List<FormComponentProperty> list = formComponentPropertyMapper.getFormComponentPropertyList(query);
		return list;
	}

	public List<FormComponentProperty> getFormComponentProperties(String kendoComponent) {
		String cacheKey = "form_component_properties_" + kendoComponent;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("form_component", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONArray array = JSON.parseArray(text);
					return FormComponentPropertyJsonFactory.arrayToList(array);
				} catch (Exception ex) {
				}
			}
		}

		List<FormComponentProperty> props = formComponentPropertyMapper.getFormComponentProperties(kendoComponent);
		if (props != null && !props.isEmpty()) {
			if (SystemConfig.getBoolean("use_query_cache")) {
				JSONArray array = FormComponentPropertyJsonFactory.listToArray(props);
				CacheFactory.put("form_component", cacheKey, array.toJSONString());
			}
		}
		return props;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormComponentPropertyCountByQueryCriteria(FormComponentPropertyQuery query) {
		return formComponentPropertyMapper.getFormComponentPropertyCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormComponentProperty> getFormComponentPropertysByQueryCriteria(int start, int pageSize,
			FormComponentPropertyQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormComponentProperty> rows = sqlSessionTemplate.selectList("getFormComponentPropertyList", query,
				rowBounds);
		return rows;
	}

	public FormComponentProperty getFormComponentProperty(Long id) {
		if (id == null) {
			return null;
		}
		String cacheKey = "form_component_" + id;
		if (SystemConfig.getBoolean("use_query_cache")) {
			String text = CacheFactory.getString("form_component", cacheKey);
			if (StringUtils.isNotEmpty(text)) {
				try {
					JSONObject result = JSON.parseObject(text);
					return FormComponentPropertyJsonFactory.jsonToObject(result);
				} catch (Exception ex) {
				}
			}
		}

		FormComponentProperty formComponentProperty = formComponentPropertyMapper.getFormComponentPropertyById(id);
		if (formComponentProperty != null) {
			CacheFactory.put("form_component", cacheKey, formComponentProperty.toJsonObject().toJSONString());
		}
		return formComponentProperty;
	}

	@Transactional
	public void save(FormComponentProperty formComponentProperty) {
		if (formComponentProperty.getId() == 0) {
			formComponentProperty.setId(idGenerator.nextId("FORM_COMPONENT_PROPERTY"));
			formComponentProperty.setCreateDate(new Date());
			formComponentProperty.setVersion(1);
			formComponentPropertyMapper.insertFormComponentProperty(formComponentProperty);
		} else {
			formComponentProperty.setUpdateDate(new Date());
			formComponentProperty.setVersion(formComponentProperty.getVersion() + 1);
			formComponentPropertyMapper.updateFormComponentProperty(formComponentProperty);

			String cacheKey = "form_property_" + formComponentProperty.getId();
			CacheFactory.remove("form_component", cacheKey);
		}
		String cacheKey = "form_component_properties_" + formComponentProperty.getKendoComponent();
		CacheFactory.remove("form_component", cacheKey);
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
	public void setFormComponentPropertyMapper(FormComponentPropertyMapper formComponentPropertyMapper) {
		this.formComponentPropertyMapper = formComponentPropertyMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public List<Map<String, Object>> getEventsProperty(Integer eventFlag) {
		List<Map<String, Object>> list = formComponentPropertyMapper.getEventsProperty(eventFlag);
		return list;
	}

}
