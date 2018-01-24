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
import com.glaf.matrix.aggregate.domain.TableAggregateItem;
import com.glaf.matrix.aggregate.mapper.TableAggregateItemMapper;
import com.glaf.matrix.aggregate.query.TableAggregateItemQuery;

@Service("com.glaf.matrix.aggregate.service.tableAggregateItemService")
@Transactional(readOnly = true)
public class TableAggregateItemServiceImpl implements TableAggregateItemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TableAggregateItemMapper tableAggregateItemMapper;

	public TableAggregateItemServiceImpl() {

	}

	public int count(TableAggregateItemQuery query) {
		return tableAggregateItemMapper.getTableAggregateItemCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			tableAggregateItemMapper.deleteTableAggregateItemById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				tableAggregateItemMapper.deleteTableAggregateItemById(id);
			}
		}
	}

	public TableAggregateItem getTableAggregateItem(Long id) {
		if (id == null) {
			return null;
		}
		TableAggregateItem tableAggregateItem = tableAggregateItemMapper.getTableAggregateItemById(id);
		return tableAggregateItem;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTableAggregateItemCountByQueryCriteria(TableAggregateItemQuery query) {
		return tableAggregateItemMapper.getTableAggregateItemCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TableAggregateItem> getTableAggregateItemsByQueryCriteria(int start, int pageSize, TableAggregateItemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TableAggregateItem> rows = sqlSessionTemplate.selectList("getTableAggregateItems", query, rowBounds);
		return rows;
	}

	public List<TableAggregateItem> list(TableAggregateItemQuery query) {
		List<TableAggregateItem> list = tableAggregateItemMapper.getTableAggregateItems(query);
		return list;
	}

	@Transactional
	public void save(TableAggregateItem tableAggregateItem) {
		if (tableAggregateItem.getId() == 0) {
			tableAggregateItem.setId(idGenerator.nextId("SYS_TABLE_AGGR_ITEM"));
			tableAggregateItem.setCreateTime(new Date());

			tableAggregateItemMapper.insertTableAggregateItem(tableAggregateItem);
		} else {
			tableAggregateItemMapper.updateTableAggregateItem(tableAggregateItem);
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

	@javax.annotation.Resource(name = "com.glaf.matrix.aggregate.mapper.TableAggregateItemMapper")
	public void setTableAggregateItemMapper(TableAggregateItemMapper tableAggregateItemMapper) {
		this.tableAggregateItemMapper = tableAggregateItemMapper;
	}

}
