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

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.UUID32;
import com.glaf.matrix.combination.domain.SqlToTableApp;
import com.glaf.matrix.combination.domain.SqlToTableItem;
import com.glaf.matrix.combination.mapper.SqlToTableAppMapper;
import com.glaf.matrix.combination.mapper.SqlToTableItemMapper;
import com.glaf.matrix.combination.query.SqlToTableAppQuery;
import com.glaf.matrix.combination.query.SqlToTableItemQuery;

@Service("com.glaf.matrix.combination.service.sqlToTableAppService")
@Transactional(readOnly = true)
public class SqlToTableAppServiceImpl implements SqlToTableAppService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SqlToTableAppMapper sqlToTableAppMapper;

	protected SqlToTableItemMapper sqlToTableItemMapper;

	public SqlToTableAppServiceImpl() {

	}

	public int count(SqlToTableAppQuery query) {
		return sqlToTableAppMapper.getSqlToTableAppCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sqlToTableAppMapper.deleteSqlToTableAppById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				sqlToTableAppMapper.deleteSqlToTableAppById(id);
			}
		}
	}

	public SqlToTableApp getSqlToTableApp(Long syncId) {
		if (syncId == null) {
			return null;
		}
		SqlToTableApp sqlToTableApp = sqlToTableAppMapper.getSqlToTableAppById(syncId);
		if (sqlToTableApp != null) {
			SqlToTableItemQuery query = new SqlToTableItemQuery();
			query.syncId(syncId);
			List<SqlToTableItem> items = sqlToTableItemMapper.getSqlToTableItems(query);
			sqlToTableApp.setItems(items);
		}
		return sqlToTableApp;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSqlToTableAppCountByQueryCriteria(SqlToTableAppQuery query) {
		return sqlToTableAppMapper.getSqlToTableAppCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlToTableApp> getSqlToTableAppsByQueryCriteria(int start, int pageSize, SqlToTableAppQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SqlToTableApp> rows = sqlSessionTemplate.selectList("getSqlToTableApps", query, rowBounds);
		return rows;
	}

	public List<SqlToTableApp> list(SqlToTableAppQuery query) {
		List<SqlToTableApp> list = sqlToTableAppMapper.getSqlToTableApps(query);
		return list;
	}

	@Transactional
	public void save(SqlToTableApp sqlToTableApp) {
		if (sqlToTableApp.getId() == 0) {
			sqlToTableApp.setId(idGenerator.nextId("SYS_SQL_TO_TABLE_APP"));
			sqlToTableApp.setCreateTime(new Date());
			if (StringUtils.isEmpty(sqlToTableApp.getDeploymentId())) {
				sqlToTableApp.setDeploymentId(UUID32.getUUID());
			}
			sqlToTableAppMapper.insertSqlToTableApp(sqlToTableApp);
		} else {
			sqlToTableAppMapper.updateSqlToTableApp(sqlToTableApp);
		}
	}

	@Transactional
	public long saveAs(long syncId, String createBy) {
		SqlToTableApp model = this.getSqlToTableApp(syncId);
		if (model != null) {
			model.setId(idGenerator.nextId("SYS_SQL_TO_TABLE_APP"));
			model.setCreateTime(new Date());
			model.setCreateBy(createBy);
			sqlToTableAppMapper.insertSqlToTableApp(model);

			if (model.getItems() != null && !model.getItems().isEmpty()) {
				for (SqlToTableItem item : model.getItems()) {
					item.setId(idGenerator.nextId("SYS_SQL_TO_TABLE_ITEM"));
					item.setCreateTime(new Date());
					item.setCreateBy(createBy);
					item.setSyncId(model.getId());
					sqlToTableItemMapper.insertSqlToTableItem(item);
				}
			}

			return model.getId();
		}
		return -1;
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

	@javax.annotation.Resource(name = "com.glaf.matrix.combination.mapper.SqlToTableAppMapper")
	public void setSqlToTableAppMapper(SqlToTableAppMapper sqlToTableAppMapper) {
		this.sqlToTableAppMapper = sqlToTableAppMapper;
	}

	@javax.annotation.Resource(name = "com.glaf.matrix.combination.mapper.SqlToTableItemMapper")
	public void setSqlToTableItemMapper(SqlToTableItemMapper sqlToTableItemMapper) {
		this.sqlToTableItemMapper = sqlToTableItemMapper;
	}

}
