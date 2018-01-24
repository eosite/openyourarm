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

package com.glaf.matrix.combination.service;

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
import com.glaf.matrix.combination.domain.SqlToTableItem;
import com.glaf.matrix.combination.mapper.SqlToTableItemMapper;
import com.glaf.matrix.combination.query.SqlToTableItemQuery;

@Service("com.glaf.matrix.combination.service.sqlToTableItemService")
@Transactional(readOnly = true)
public class SqlToTableItemServiceImpl implements SqlToTableItemService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SqlToTableItemMapper sqlToTableItemMapper;

	public SqlToTableItemServiceImpl() {

	}

	public int count(SqlToTableItemQuery query) {
		return sqlToTableItemMapper.getSqlToTableItemCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sqlToTableItemMapper.deleteSqlToTableItemById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				sqlToTableItemMapper.deleteSqlToTableItemById(id);
			}
		}
	}

	public SqlToTableItem getSqlToTableItem(Long id) {
		if (id == null) {
			return null;
		}
		SqlToTableItem sqlToTableItem = sqlToTableItemMapper.getSqlToTableItemById(id);
		return sqlToTableItem;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSqlToTableItemCountByQueryCriteria(SqlToTableItemQuery query) {
		return sqlToTableItemMapper.getSqlToTableItemCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlToTableItem> getSqlToTableItemsByQueryCriteria(int start, int pageSize, SqlToTableItemQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SqlToTableItem> rows = sqlSessionTemplate.selectList("getSqlToTableItems", query, rowBounds);
		return rows;
	}

	public List<SqlToTableItem> list(SqlToTableItemQuery query) {
		List<SqlToTableItem> list = sqlToTableItemMapper.getSqlToTableItems(query);
		return list;
	}

	@Transactional
	public void save(SqlToTableItem sqlToTableItem) {
		if (sqlToTableItem.getId() == 0) {
			sqlToTableItem.setId(idGenerator.nextId("SYS_SQL_TO_TABLE_ITEM"));
			sqlToTableItem.setCreateTime(new Date());

			sqlToTableItemMapper.insertSqlToTableItem(sqlToTableItem);
		} else {
			sqlToTableItemMapper.updateSqlToTableItem(sqlToTableItem);
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

	@javax.annotation.Resource(name = "com.glaf.matrix.combination.mapper.SqlToTableItemMapper")
	public void setSqlToTableItemMapper(SqlToTableItemMapper sqlToTableItemMapper) {
		this.sqlToTableItemMapper = sqlToTableItemMapper;
	}

}
