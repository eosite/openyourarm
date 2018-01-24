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

package com.glaf.matrix.data.sync.service;

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

import com.glaf.matrix.data.sync.mapper.*;
import com.glaf.matrix.data.sync.domain.*;
import com.glaf.matrix.data.sync.query.*;

@Service("com.glaf.matrix.data.sync.service.combineHistoryService")
@Transactional(readOnly = true)
public class CombineHistoryServiceImpl implements CombineHistoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CombineHistoryMapper combineHistoryMapper;

	public CombineHistoryServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			combineHistoryMapper.deleteCombineHistoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				combineHistoryMapper.deleteCombineHistoryById(id);
			}
		}
	}

	public int count(CombineHistoryQuery query) {
		return combineHistoryMapper.getCombineHistoryCount(query);
	}

	public List<CombineHistory> list(CombineHistoryQuery query) {
		List<CombineHistory> list = combineHistoryMapper.getCombineHistorys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getCombineHistoryCountByQueryCriteria(CombineHistoryQuery query) {
		return combineHistoryMapper.getCombineHistoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<CombineHistory> getCombineHistorysByQueryCriteria(int start, int pageSize, CombineHistoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CombineHistory> rows = sqlSessionTemplate.selectList("getCombineHistorys", query, rowBounds);
		return rows;
	}

	public CombineHistory getCombineHistory(Long id) {
		if (id == null) {
			return null;
		}
		CombineHistory combineHistory = combineHistoryMapper.getCombineHistoryById(id);
		return combineHistory;
	}

	@Transactional
	public void save(CombineHistory combineHistory) {
		if (combineHistory.getId() == 0) {
			combineHistory.setId(idGenerator.nextId("SYS_COMBINE_HISTORY"));
			combineHistory.setCreateTime(new Date());

			combineHistoryMapper.insertCombineHistory(combineHistory);
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

	@javax.annotation.Resource(name = "com.glaf.matrix.data.sync.mapper.CombineHistoryMapper")
	public void setCombineHistoryMapper(CombineHistoryMapper combineHistoryMapper) {
		this.combineHistoryMapper = combineHistoryMapper;
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
