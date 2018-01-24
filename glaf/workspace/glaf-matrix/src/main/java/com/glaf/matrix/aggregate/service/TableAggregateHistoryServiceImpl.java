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

package com.glaf.matrix.aggregate.service;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.matrix.aggregate.domain.TableAggregateHistory;
import com.glaf.matrix.aggregate.mapper.TableAggregateHistoryMapper;
import com.glaf.matrix.aggregate.query.TableAggregateHistoryQuery;

@Service("com.glaf.matrix.aggregate.service.tableAggregateHistoryService")
@Transactional(readOnly = true)
public class TableAggregateHistoryServiceImpl implements TableAggregateHistoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableAggregateHistoryMapper tableAggregateHistoryMapper;

	public TableAggregateHistoryServiceImpl() {

	}

	public int count(TableAggregateHistoryQuery query) {
		return tableAggregateHistoryMapper.getTableAggregateHistoryCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			tableAggregateHistoryMapper.deleteTableAggregateHistoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				tableAggregateHistoryMapper.deleteTableAggregateHistoryById(id);
			}
		}
	}

	public TableAggregateHistory getTableAggregateHistory(Long id) {
		if (id == null) {
			return null;
		}
		TableAggregateHistory tableAggregateHistory = tableAggregateHistoryMapper.getTableAggregateHistoryById(id);
		return tableAggregateHistory;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableAggregateHistoryCountByQueryCriteria(TableAggregateHistoryQuery query) {
		return tableAggregateHistoryMapper.getTableAggregateHistoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableAggregateHistory> getTableAggregateHistorysByQueryCriteria(int start, int pageSize,
			TableAggregateHistoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableAggregateHistory> rows = sqlSessionTemplate.selectList("getTableAggregateHistorys", query, rowBounds);
		return rows;
	}

	public TableAggregateHistory getLatestTableAggregateHistory(long syncId, long databaseId) {
		TableAggregateHistoryQuery query = new TableAggregateHistoryQuery();
		query.syncId(syncId);
		query.databaseId(databaseId);
		List<TableAggregateHistory> list = tableAggregateHistoryMapper.getTableAggregateHistorys(query);
		if (list != null && !list.isEmpty()) {
			return list.get(0);
		}
		return null;
	}

	public List<TableAggregateHistory> list(TableAggregateHistoryQuery query) {
		List<TableAggregateHistory> list = tableAggregateHistoryMapper.getTableAggregateHistorys(query);
		return list;
	}

	@Transactional
	public void save(TableAggregateHistory tableAggregateHistory) {
		if (tableAggregateHistory.getId() == 0) {
			tableAggregateHistory.setId(idGenerator.nextId("SYS_TABLE_AGGR_HISTORY"));
			tableAggregateHistory.setCreateTime(new Date());

			tableAggregateHistoryMapper.insertTableAggregateHistory(tableAggregateHistory);
		} else {
			tableAggregateHistoryMapper.updateTableAggregateHistory(tableAggregateHistory);
		}
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.aggregate.mapper.TableAggregateHistoryMapper")
	public void setTableAggregateHistoryMapper(TableAggregateHistoryMapper tableAggregateHistoryMapper) {
		this.tableAggregateHistoryMapper = tableAggregateHistoryMapper;
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

}
