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
import com.glaf.core.mapper.ColumnDefinitionMapper;
import com.glaf.core.dao.*;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.DataPermissionService;
import com.glaf.datamgr.service.SqlDefinitionService;

@Service("sqlDefinitionService")
@Transactional(readOnly = true)
public class SqlDefinitionServiceImpl implements SqlDefinitionService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected ColumnDefinitionMapper columnDefinitionMapper;

	protected SqlDefinitionMapper sqlDefinitionMapper;

	protected SqlParameterMapper sqlParameterMapper;

	protected SqlResultMapper sqlResultMapper;

	protected DataPermissionService dataPermissionService;

	public SqlDefinitionServiceImpl() {

	}

	public int count(SqlDefinitionQuery query) {
		return sqlDefinitionMapper.getSqlDefinitionCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			SqlResultQuery query = new SqlResultQuery();
			query.setId(id);
			sqlResultMapper.deleteBySqlDefId(query);
			sqlParameterMapper.deleteSqlParameterBySqlDefId(id);
			sqlDefinitionMapper.deleteSqlDefinitionById(id);

		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				SqlResultQuery query = new SqlResultQuery();
				query.setId(id);
				sqlResultMapper.deleteBySqlDefId(query);
				sqlParameterMapper.deleteSqlParameterBySqlDefId(id);
				sqlDefinitionMapper.deleteSqlDefinitionById(id);
			}
		}
	}

	public SqlDefinition getSqlDefinition(Long id) {
		if (id == null) {
			return null;
		}
		SqlDefinition sqlDefinition = sqlDefinitionMapper.getSqlDefinitionById(id);
		if (sqlDefinition != null) {
			List<SqlParameter> parameters = sqlParameterMapper.getSqlParametersBySqlDefId(id);
			sqlDefinition.setParameters(parameters);
		}
		return sqlDefinition;
	}

	public SqlDefinition getSqlDefinitionByUUID(String uuid) {
		if (uuid == null) {
			return null;
		}
		SqlDefinition sqlDefinition = sqlDefinitionMapper.getSqlDefinitionByUUID(uuid);
		if (sqlDefinition != null) {
			List<SqlParameter> parameters = sqlParameterMapper.getSqlParametersBySqlDefId(sqlDefinition.getId());
			sqlDefinition.setParameters(parameters);
		}
		return sqlDefinition;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSqlDefinitionCountByQueryCriteria(SqlDefinitionQuery query) {
		return sqlDefinitionMapper.getSqlDefinitionCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlDefinition> getSqlDefinitions(String actorId) {
		List<SqlDefinition> list = new ArrayList<SqlDefinition>();
		SqlDefinitionQuery query = new SqlDefinitionQuery();
		query.setLocked(0);
		query.setShareFlag("Y");
		List<SqlDefinition> rows01 = sqlDefinitionMapper.getSqlDefinitions(query);
		if (rows01 != null && !rows01.isEmpty()) {
			list.addAll(rows01);
		}

		SqlDefinitionQuery query2 = new SqlDefinitionQuery();
		query2.setLocked(0);
		query2.setCreateBy(actorId);

		List<SqlDefinition> rows02 = sqlDefinitionMapper.getSqlDefinitions(query2);
		if (rows02 != null && !rows02.isEmpty()) {
			for (SqlDefinition m : rows02) {
				if (!list.contains(m)) {
					list.add(m);
				}
			}
		}

		return list;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlDefinition> getSqlDefinitionsByUserId(String userId) {
		List<SqlDefinition> list = new ArrayList<SqlDefinition>();
		DataPermissionQuery query = new DataPermissionQuery();
		query.userId(userId);
		List<DataPermission> perms = dataPermissionService.list(query);
		List<Long> businessKeys = new ArrayList<Long>();
		if (perms != null && !perms.isEmpty()) {
			for (DataPermission p : perms) {
				businessKeys.add(Long.parseLong(p.getBusinessKey()));
			}
		}

		SqlDefinitionQuery q = new SqlDefinitionQuery();
		q.setLocked(0);
		q.setIds(businessKeys);
		List<SqlDefinition> rows01 = sqlDefinitionMapper.getSqlDefinitions(q);
		if (rows01 != null && !rows01.isEmpty()) {
			list.addAll(rows01);
		}

		return list;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @param actorId
	 * @param operation
	 *            查询类型，如select、update
	 * @return
	 * @author Rocky
	 */
	@Override
	public List<SqlDefinition> getSqlDefinitions(String actorId, String operation) {
		List<SqlDefinition> list = new ArrayList<SqlDefinition>();
		SqlDefinitionQuery query = new SqlDefinitionQuery();
		query.setLocked(0);
		query.setShareFlag("Y");
		query.setOperation(operation);
		List<SqlDefinition> rows01 = sqlDefinitionMapper.getSqlDefinitions(query);
		if (rows01 != null && !rows01.isEmpty()) {
			list.addAll(rows01);
		}

		SqlDefinitionQuery query2 = new SqlDefinitionQuery();
		query2.setLocked(0);
		query2.setCreateBy(actorId);
		query2.setOperation(operation);

		List<SqlDefinition> rows02 = sqlDefinitionMapper.getSqlDefinitions(query2);
		if (rows02 != null && !rows02.isEmpty()) {
			for (SqlDefinition m : rows02) {
				if (!list.contains(m)) {
					list.add(m);
				}
			}
		}

		return list;
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlDefinition> getSqlDefinitionsByQueryCriteria(int start, int pageSize, SqlDefinitionQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SqlDefinition> rows = sqlSessionTemplate.selectList("getSqlDefinitions", query, rowBounds);
		return rows;
	}

	public List<SqlDefinition> list(SqlDefinitionQuery query) {
		List<SqlDefinition> list = sqlDefinitionMapper.getSqlDefinitions(query);
		return list;
	}

	@Transactional
	public void save(SqlDefinition sqlDefinition) {
		if (sqlDefinition.getId() == null) {
			sqlDefinition.setId(idGenerator.nextId("SYS_SQL"));
			sqlDefinition.setCreateTime(new Date());
			sqlDefinitionMapper.insertSqlDefinition(sqlDefinition);
		} else {
			sqlDefinition.setUpdateTime(new Date());
			sqlDefinitionMapper.updateSqlDefinition(sqlDefinition);
		}
	}

	@Transactional
	public void saveColumn(long sqlDefId, ColumnDefinition columnDefinition) {
		String id = "sql_ref_" + sqlDefId + "_" + columnDefinition.getColumnName().toLowerCase();
		ColumnDefinition col = columnDefinitionMapper.getColumnDefinitionById(id);
		if (col == null) {
			columnDefinition.setId(id);
			columnDefinition.setTargetId("sql_ref_" + sqlDefId);
			columnDefinitionMapper.insertColumnDefinition(columnDefinition);
		} else {
			columnDefinitionMapper.updateColumnDefinition(columnDefinition);
		}
	}

	@Transactional
	public void saveColumns(long sqlDefId, List<ColumnDefinition> columns) {
		for (ColumnDefinition column : columns) {
			this.saveColumn(sqlDefId, column);
		}
	}

	@javax.annotation.Resource
	public void setColumnDefinitionMapper(ColumnDefinitionMapper columnDefinitionMapper) {
		this.columnDefinitionMapper = columnDefinitionMapper;
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
	public void setSqlDefinitionMapper(SqlDefinitionMapper sqlDefinitionMapper) {
		this.sqlDefinitionMapper = sqlDefinitionMapper;
	}

	@javax.annotation.Resource
	public void setSqlParameterMapper(SqlParameterMapper sqlParameterMapper) {
		this.sqlParameterMapper = sqlParameterMapper;
	}

	@javax.annotation.Resource
	public void setSqlResultMapper(SqlResultMapper sqlResultMapper) {
		this.sqlResultMapper = sqlResultMapper;
	}

	@javax.annotation.Resource
	public void setDataPermissionService(DataPermissionService dataPermissionService) {
		this.dataPermissionService = dataPermissionService;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
