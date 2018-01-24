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
import com.glaf.datamgr.service.JoinSegmentService;

@Service("joinSegmentService")
@Transactional(readOnly = true)
public class JoinSegmentServiceImpl implements JoinSegmentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected JoinSegmentMapper joinSegmentMapper;

	public JoinSegmentServiceImpl() {

	}

	public int count(JoinSegmentQuery query) {
		return joinSegmentMapper.getJoinSegmentCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			joinSegmentMapper.deleteJoinSegmentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				joinSegmentMapper.deleteJoinSegmentById(id);
			}
		}
	}

	public JoinSegment getJoinSegment(Long id) {
		if (id == null) {
			return null;
		}
		JoinSegment joinSegment = joinSegmentMapper.getJoinSegmentById(id);
		return joinSegment;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getJoinSegmentCountByQueryCriteria(JoinSegmentQuery query) {
		return joinSegmentMapper.getJoinSegmentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<JoinSegment> getJoinSegmentsByQueryCriteria(int start,
			int pageSize, JoinSegmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<JoinSegment> rows = sqlSessionTemplate.selectList(
				"getJoinSegments", query, rowBounds);
		return rows;
	}

	public List<JoinSegment> list(JoinSegmentQuery query) {
		List<JoinSegment> list = joinSegmentMapper.getJoinSegments(query);
		return list;
	}

	@Transactional
	public void save(JoinSegment joinSegment) {
		if (joinSegment.getId() == null) {
			joinSegment.setId(idGenerator.nextId("SYS_DATA_JOIN"));
			joinSegment.setCreateTime(new Date());
			// joinSegment.setDeleteFlag(0);
			joinSegmentMapper.insertJoinSegment(joinSegment);
		} else {
			joinSegmentMapper.updateJoinSegment(joinSegment);
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
	public void setJoinSegmentMapper(JoinSegmentMapper joinSegmentMapper) {
		this.joinSegmentMapper = joinSegmentMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
