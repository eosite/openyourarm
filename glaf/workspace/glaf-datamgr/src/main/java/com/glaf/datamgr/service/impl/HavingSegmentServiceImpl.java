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
import com.glaf.datamgr.service.HavingSegmentService;

@Service("havingSegmentService")
@Transactional(readOnly = true)
public class HavingSegmentServiceImpl implements HavingSegmentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected HavingSegmentMapper havingSegmentMapper;

	public HavingSegmentServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			havingSegmentMapper.deleteHavingSegmentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				havingSegmentMapper.deleteHavingSegmentById(id);
			}
		}
	}

	public int count(HavingSegmentQuery query) {
		return havingSegmentMapper.getHavingSegmentCount(query);
	}

	public List<HavingSegment> list(HavingSegmentQuery query) {
		List<HavingSegment> list = havingSegmentMapper.getHavingSegments(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getHavingSegmentCountByQueryCriteria(HavingSegmentQuery query) {
		return havingSegmentMapper.getHavingSegmentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<HavingSegment> getHavingSegmentsByQueryCriteria(int start,
			int pageSize, HavingSegmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<HavingSegment> rows = sqlSessionTemplate.selectList(
				"getHavingSegments", query, rowBounds);
		return rows;
	}

	public HavingSegment getHavingSegment(Long id) {
		if (id == null) {
			return null;
		}
		HavingSegment havingSegment = havingSegmentMapper
				.getHavingSegmentById(id);
		return havingSegment;
	}

	@Transactional
	public void save(HavingSegment havingSegment) {
		if (havingSegment.getId() == null) {
			havingSegment.setId(idGenerator.nextId("SYS_DATA_HAVING"));
			// havingSegment.setCreateDate(new Date());
			// havingSegment.setDeleteFlag(0);
			havingSegmentMapper.insertHavingSegment(havingSegment);
		} else {
			havingSegmentMapper.updateHavingSegment(havingSegment);
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
	public void setHavingSegmentMapper(HavingSegmentMapper havingSegmentMapper) {
		this.havingSegmentMapper = havingSegmentMapper;
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
