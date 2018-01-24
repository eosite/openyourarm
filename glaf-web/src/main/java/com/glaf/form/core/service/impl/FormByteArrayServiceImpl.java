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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.dao.*;
import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormByteArrayService;

@Service("formByteArrayService")
@Transactional(readOnly = true)
public class FormByteArrayServiceImpl implements FormByteArrayService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormByteArrayMapper formByteArrayMapper;

	public FormByteArrayServiceImpl() {

	}

	public int count(FormByteArrayQuery query) {
		return formByteArrayMapper.getFormByteArrayCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formByteArrayMapper.deleteFormByteArrayById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formByteArrayMapper.deleteFormByteArrayById(id);
			}
		}
	}

	public FormByteArray getFormByteArrayById(String id) {
		if (id == null) {
			return null;
		}
		FormByteArray formByteArray = null;
		if (StringUtils.equals(DBUtils.POSTGRESQL,
				DBConnectionFactory.getDatabaseType())) {
			formByteArray = formByteArrayMapper
					.getFormByteArrayById_postgres(id);
		} else {
			formByteArray = formByteArrayMapper.getFormByteArrayById(id);
		}
		return formByteArray;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormByteArrayCountByQueryCriteria(FormByteArrayQuery query) {
		return formByteArrayMapper.getFormByteArrayCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormByteArray> getFormByteArraysByQueryCriteria(int start,
			int pageSize, FormByteArrayQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormByteArray> rows = sqlSessionTemplate.selectList(
				"getFormByteArrays", query, rowBounds);
		return rows;
	}

	public List<FormByteArray> list(FormByteArrayQuery query) {
		List<FormByteArray> list = formByteArrayMapper.getFormByteArrays(query);
		return list;
	}

	@Transactional
	public void save(FormByteArray formByteArray) {
		if (StringUtils.isEmpty(formByteArray.getId())) {
			formByteArray.setId(idGenerator.getNextId());
			formByteArray.setCreateDate(new Date());
			if (StringUtils.equals(DBUtils.POSTGRESQL,
					DBConnectionFactory.getDatabaseType())) {
				formByteArrayMapper.insertFormByteArray_postgres(formByteArray);
			} else {
				formByteArrayMapper.insertFormByteArray(formByteArray);
			}
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFormByteArrayMapper(FormByteArrayMapper formByteArrayMapper) {
		this.formByteArrayMapper = formByteArrayMapper;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
