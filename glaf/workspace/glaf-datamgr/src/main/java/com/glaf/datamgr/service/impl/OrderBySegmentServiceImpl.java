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
import com.glaf.datamgr.service.OrderBySegmentService;

@Service("orderBySegmentService")
@Transactional(readOnly = true)
public class OrderBySegmentServiceImpl implements OrderBySegmentService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected OrderBySegmentMapper orderBySegmentMapper;

	public OrderBySegmentServiceImpl() {

	}

	public int count(OrderBySegmentQuery query) {
		return orderBySegmentMapper.getOrderBySegmentCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			orderBySegmentMapper.deleteOrderBySegmentById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				orderBySegmentMapper.deleteOrderBySegmentById(id);
			}
		}
	}

	public OrderBySegment getOrderBySegment(Long id) {
		if (id == null) {
			return null;
		}
		OrderBySegment orderBySegment = orderBySegmentMapper
				.getOrderBySegmentById(id);
		return orderBySegment;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getOrderBySegmentCountByQueryCriteria(OrderBySegmentQuery query) {
		return orderBySegmentMapper.getOrderBySegmentCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<OrderBySegment> getOrderBySegmentsByQueryCriteria(int start,
			int pageSize, OrderBySegmentQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<OrderBySegment> rows = sqlSessionTemplate.selectList(
				"getOrderBySegments", query, rowBounds);
		return rows;
	}

	public List<OrderBySegment> list(OrderBySegmentQuery query) {
		List<OrderBySegment> list = orderBySegmentMapper
				.getOrderBySegments(query);
		return list;
	}

	@Transactional
	public void save(OrderBySegment orderBySegment) {
		if (orderBySegment.getId() == null) {
			orderBySegment.setId(idGenerator.nextId("SYS_DATA_ORDERBY"));
			orderBySegment.setCreateTime(new Date());
			// orderBySegment.setDeleteFlag(0);
			orderBySegmentMapper.insertOrderBySegment(orderBySegment);
		} else {
			orderBySegmentMapper.updateOrderBySegment(orderBySegment);
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
	public void setOrderBySegmentMapper(
			OrderBySegmentMapper orderBySegmentMapper) {
		this.orderBySegmentMapper = orderBySegmentMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
