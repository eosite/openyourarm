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

package com.glaf.datamgr.service.impl;

import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.SqlConditionService;

@Service("sqlConditionService")
@Transactional(readOnly = true)
public class SqlConditionServiceImpl implements SqlConditionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SqlConditionMapper sqlConditionMapper;

	public SqlConditionServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sqlConditionMapper.deleteSqlConditionById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				sqlConditionMapper.deleteSqlConditionById(id);
			}
		}
	}

	public int count(SqlConditionQuery query) {
		return sqlConditionMapper.getSqlConditionCount(query);
	}

	public List<SqlCondition> list(SqlConditionQuery query) {
		List<SqlCondition> list = sqlConditionMapper.getSqlConditions(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSqlConditionCountByQueryCriteria(SqlConditionQuery query) {
		return sqlConditionMapper.getSqlConditionCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlCondition> getSqlConditionsByQueryCriteria(int start,
			int pageSize, SqlConditionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SqlCondition> rows = sqlSessionTemplate.selectList(
				"getSqlConditions", query, rowBounds);
		return rows;
	}

	public SqlCondition getSqlCondition(Long id) {
		if (id == null) {
			return null;
		}
		SqlCondition sqlCondition = sqlConditionMapper.getSqlConditionById(id);
		return sqlCondition;
	}

	@Transactional
	public void save(SqlCondition sqlCondition) {
		if (sqlCondition.getId() == null) {
			sqlCondition.setId(idGenerator.nextId("SYS_SQL_CONDITION"));
			sqlCondition.setCreateTime(new Date());
			sqlCondition.setDeleteFlag(0);
			sqlConditionMapper.insertSqlCondition(sqlCondition);
		} else {
			sqlConditionMapper.updateSqlCondition(sqlCondition);
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
	public void setSqlConditionMapper(SqlConditionMapper sqlConditionMapper) {
		this.sqlConditionMapper = sqlConditionMapper;
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