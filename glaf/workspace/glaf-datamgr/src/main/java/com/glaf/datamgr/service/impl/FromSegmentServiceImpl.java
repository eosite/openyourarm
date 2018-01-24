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
import com.glaf.datamgr.service.FromSegmentService;

@Service("fromSegmentService")
@Transactional(readOnly = true)
public class FromSegmentServiceImpl implements FromSegmentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FromSegmentMapper fromSegmentMapper;

	public FromSegmentServiceImpl() {

	}

	public int count(FromSegmentQuery query) {
		return fromSegmentMapper.getFromSegmentCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			fromSegmentMapper.deleteFromSegmentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				fromSegmentMapper.deleteFromSegmentById(id);
			}
		}
	}

	public FromSegment getFromSegment(Long id) {
		if (id == null) {
			return null;
		}
		FromSegment fromSegment = fromSegmentMapper.getFromSegmentById(id);
		return fromSegment;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFromSegmentCountByQueryCriteria(FromSegmentQuery query) {
		return fromSegmentMapper.getFromSegmentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FromSegment> getFromSegmentsByQueryCriteria(int start,
			int pageSize, FromSegmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FromSegment> rows = sqlSessionTemplate.selectList(
				"getFromSegments", query, rowBounds);
		return rows;
	}

	public List<FromSegment> list(FromSegmentQuery query) {
		List<FromSegment> list = fromSegmentMapper.getFromSegments(query);
		return list;
	}

	@Transactional
	public void save(FromSegment fromSegment) {
		if (fromSegment.getId() == null) {
			fromSegment.setId(idGenerator.nextId("SYS_DATA_FROM"));
			fromSegment.setCreateTime(new Date());
			// fromSegment.setDeleteFlag(0);
			fromSegmentMapper.insertFromSegment(fromSegment);
		} else {
			fromSegmentMapper.updateFromSegment(fromSegment);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFromSegmentMapper(FromSegmentMapper fromSegmentMapper) {
		this.fromSegmentMapper = fromSegmentMapper;
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
