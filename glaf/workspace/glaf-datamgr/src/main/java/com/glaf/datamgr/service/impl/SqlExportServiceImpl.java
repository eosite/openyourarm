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
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.SqlExportService;

@Service("sqlExportService")
@Transactional(readOnly = true)
public class SqlExportServiceImpl implements SqlExportService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SqlExportMapper sqlExportMapper;

	public SqlExportServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<SqlExport> list) {
		for (SqlExport sqlExport : list) {
			if (sqlExport.getId() == null) {
				sqlExport.setId(idGenerator.nextId("SYS_SQL_EXPORT"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			sqlExportMapper.bulkInsertSqlExport_oracle(list);
		} else {
			sqlExportMapper.bulkInsertSqlExport(list);
		}
	}

	public int count(SqlExportQuery query) {
		return sqlExportMapper.getSqlExportCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sqlExportMapper.deleteSqlExportById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				sqlExportMapper.deleteSqlExportById(id);
			}
		}
	}

	public SqlExport getSqlExport(Long id) {
		if (id == null) {
			return null;
		}
		SqlExport sqlExport = sqlExportMapper.getSqlExportById(id);
		return sqlExport;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSqlExportCountByQueryCriteria(SqlExportQuery query) {
		return sqlExportMapper.getSqlExportCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlExport> getSqlExportsByQueryCriteria(int start, int pageSize, SqlExportQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SqlExport> rows = sqlSessionTemplate.selectList("getSqlExports", query, rowBounds);
		return rows;
	}

	public List<SqlExport> list(SqlExportQuery query) {
		List<SqlExport> list = sqlExportMapper.getSqlExports(query);
		return list;
	}

	@Transactional
	public void save(SqlExport sqlExport) {
		if (sqlExport.getId() == null) {
			sqlExport.setId(idGenerator.nextId("SYS_SQL_EXPORT"));
			sqlExport.setCreateTime(new Date());
			sqlExportMapper.insertSqlExport(sqlExport);
		} else {
			sqlExport.setUpdateTime(new Date());
			sqlExportMapper.updateSqlExport(sqlExport);
		}
	}
	
	@Transactional
	public void updateExportStatus(SqlExport model){
		sqlExportMapper.updateSqlExportExportStatus(model);
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
	public void setSqlExportMapper(SqlExportMapper sqlExportMapper) {
		this.sqlExportMapper = sqlExportMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
