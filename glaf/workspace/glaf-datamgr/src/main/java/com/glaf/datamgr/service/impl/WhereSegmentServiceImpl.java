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
import com.glaf.datamgr.service.WhereSegmentService;

@Service("whereSegmentService")
@Transactional(readOnly = true)
public class WhereSegmentServiceImpl implements WhereSegmentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected WhereSegmentMapper whereSegmentMapper;

	public WhereSegmentServiceImpl() {

	}

	public int count(WhereSegmentQuery query) {
		return whereSegmentMapper.getWhereSegmentCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			whereSegmentMapper.deleteWhereSegmentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				whereSegmentMapper.deleteWhereSegmentById(id);
			}
		}
	}

	public WhereSegment getWhereSegment(Long id) {
		if (id == null) {
			return null;
		}
		WhereSegment whereSegment = whereSegmentMapper.getWhereSegmentById(id);
		return whereSegment;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getWhereSegmentCountByQueryCriteria(WhereSegmentQuery query) {
		return whereSegmentMapper.getWhereSegmentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<WhereSegment> getWhereSegmentsByQueryCriteria(int start,
			int pageSize, WhereSegmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<WhereSegment> rows = sqlSessionTemplate.selectList(
				"getWhereSegments", query, rowBounds);
		return rows;
	}

	public List<WhereSegment> list(WhereSegmentQuery query) {
		List<WhereSegment> list = whereSegmentMapper.getWhereSegments(query);
		return list;
	}

	@Transactional
	public void save(WhereSegment whereSegment) {
		if (whereSegment.getId() == null) {
			whereSegment.setId(idGenerator.nextId("SYS_DATA_WHERE"));
			whereSegment.setCreateTime(new Date());
			// whereSegment.setDeleteFlag(0);
			whereSegmentMapper.insertWhereSegment(whereSegment);
		} else {
			whereSegmentMapper.updateWhereSegment(whereSegment);
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setWhereSegmentMapper(WhereSegmentMapper whereSegmentMapper) {
		this.whereSegmentMapper = whereSegmentMapper;
	}

}
