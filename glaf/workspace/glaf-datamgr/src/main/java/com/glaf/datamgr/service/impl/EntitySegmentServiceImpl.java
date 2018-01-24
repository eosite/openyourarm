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

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.EntitySegmentService;

@Service("entitySegmentService")
@Transactional(readOnly = true)
public class EntitySegmentServiceImpl implements EntitySegmentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected EntitySegmentMapper entitySegmentMapper;

	public EntitySegmentServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<EntitySegment> list) {
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			entitySegmentMapper.bulkInsertEntitySegment_oracle(list);
		} else {
			entitySegmentMapper.bulkInsertEntitySegment(list);
		}
	}

	public int count(EntitySegmentQuery query) {
		return entitySegmentMapper.getEntitySegmentCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			entitySegmentMapper.deleteEntitySegmentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				entitySegmentMapper.deleteEntitySegmentById(id);
			}
		}
	}

	public EntitySegment getEntitySegment(String id) {
		if (id == null) {
			return null;
		}
		EntitySegment entitySegment = entitySegmentMapper.getEntitySegmentById(id);
		return entitySegment;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getEntitySegmentCountByQueryCriteria(EntitySegmentQuery query) {
		return entitySegmentMapper.getEntitySegmentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<EntitySegment> getEntitySegmentsByQueryCriteria(int start, int pageSize, EntitySegmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<EntitySegment> rows = sqlSessionTemplate.selectList("getEntitySegments", query, rowBounds);
		return rows;
	}

	public List<EntitySegment> list(EntitySegmentQuery query) {
		List<EntitySegment> list = entitySegmentMapper.getEntitySegments(query);
		return list;
	}

	@Transactional
	public void save(EntitySegment entitySegment) {
		if (this.getEntitySegment(entitySegment.getId()) == null) {
			entitySegmentMapper.insertEntitySegment(entitySegment);
		} else {
			entitySegmentMapper.updateEntitySegment(entitySegment);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setEntitySegmentMapper(EntitySegmentMapper entitySegmentMapper) {
		this.entitySegmentMapper = entitySegmentMapper;
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
