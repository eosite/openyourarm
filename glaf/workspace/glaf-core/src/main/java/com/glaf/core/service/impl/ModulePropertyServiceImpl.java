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

package com.glaf.core.service.impl;

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
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;

import com.glaf.core.mapper.*;
import com.glaf.core.domain.*;
import com.glaf.core.query.*;
import com.glaf.core.service.ModulePropertyService;

@Service("modulePropertyService")
@Transactional(readOnly = true)
public class ModulePropertyServiceImpl implements ModulePropertyService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ModulePropertyMapper modulePropertyMapper;

	public ModulePropertyServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<ModuleProperty> list) {
		for (ModuleProperty moduleProperty : list) {
			if (StringUtils.isEmpty(moduleProperty.getId())) {
				moduleProperty.setId(idGenerator.getNextId("SYS_MODULE_PROPERTY"));
			}
			moduleProperty.setCreateTime(new Date());
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			modulePropertyMapper.bulkInsertModuleProperty_oracle(list);
		} else {
			modulePropertyMapper.bulkInsertModuleProperty(list);
		}
	}

	public int count(ModulePropertyQuery query) {
		return modulePropertyMapper.getModulePropertyCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			modulePropertyMapper.deleteModulePropertyById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				modulePropertyMapper.deleteModulePropertyById(id);
			}
		}
	}

	/**
	 * 根据分类获取属性
	 * 
	 * @param category
	 * @return
	 */
	public List<ModuleProperty> getModuleProperties(String category) {
		ModulePropertyQuery query = new ModulePropertyQuery();
		query.category(category);
		return this.list(query);
	}

	/**
	 * 根据分类及地域获取属性
	 * 
	 * @param category
	 * @param locale
	 * @return
	 */
	public List<ModuleProperty> getModuleProperties(String category, String locale) {
		ModulePropertyQuery query = new ModulePropertyQuery();
		query.category(category);
		query.locale(locale);
		return this.list(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<ModuleProperty> getModulePropertiesByQueryCriteria(int start, int pageSize, ModulePropertyQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<ModuleProperty> rows = sqlSessionTemplate.selectList("getModuleProperties", query, rowBounds);
		return rows;
	}

	public ModuleProperty getModuleProperty(String id) {
		if (id == null) {
			return null;
		}
		ModuleProperty moduleProperty = modulePropertyMapper.getModulePropertyById(id);
		return moduleProperty;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getModulePropertyCountByQueryCriteria(ModulePropertyQuery query) {
		return modulePropertyMapper.getModulePropertyCount(query);
	}

	public List<ModuleProperty> list(ModulePropertyQuery query) {
		List<ModuleProperty> list = modulePropertyMapper.getModuleProperties(query);
		return list;
	}

	@Transactional
	public void save(ModuleProperty moduleProperty) {
		if (StringUtils.isEmpty(moduleProperty.getId())) {
			moduleProperty.setId(idGenerator.getNextId("SYS_MODULE_PROPERTY"));
			moduleProperty.setCreateTime(new Date());

			modulePropertyMapper.insertModuleProperty(moduleProperty);
		} else {
			modulePropertyMapper.updateModuleProperty(moduleProperty);
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
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setModulePropertyMapper(ModulePropertyMapper modulePropertyMapper) {
		this.modulePropertyMapper = modulePropertyMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
