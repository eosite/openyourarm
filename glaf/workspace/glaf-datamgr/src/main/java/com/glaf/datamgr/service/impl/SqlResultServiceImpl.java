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
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.DateUtils;
import com.glaf.core.dao.*;
import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.SqlResultService;
import com.glaf.datamgr.util.SqlExecutionThreadLocal;

@Service("sqlResultService")
@Transactional(readOnly = true)
public class SqlResultServiceImpl implements SqlResultService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SqlResultMapper sqlResultMapper;

	public SqlResultServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<SqlResult> list) {
		for (SqlResult sqlResult : list) {
			if (sqlResult.getId() == null) {
				sqlResult.setId(idGenerator.nextId("SYS_SQL_RESULT"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			sqlResultMapper.bulkInsertSqlResult_oracle(list);
		} else {
			sqlResultMapper.bulkInsertSqlResult(list);
		}
	}

	public int count(SqlResultQuery query) {
		return sqlResultMapper.getSqlResultCount(query);
	}

	@Transactional
	public void deleteAll(long sqlDefId, String type, int runDay) {
		SqlResultQuery query = new SqlResultQuery();
		query.sqlDefId(sqlDefId);
		query.setRunDay(runDay);
		query.type(type);
		query.setSuffix(String.valueOf(sqlDefId));
		sqlResultMapper.deleteSqlResults(query);
	}

	@Transactional
	public void deleteByDatabaseId(Long databaseId) {
		SqlResultQuery query = new SqlResultQuery();
		query.setDatabaseId(databaseId);
		sqlResultMapper.deleteByDatabaseId(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			SqlResultQuery query = new SqlResultQuery();
			query.setId(id);
			sqlResultMapper.deleteSqlResultById(query);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				SqlResultQuery query = new SqlResultQuery();
				query.setId(id);
				sqlResultMapper.deleteSqlResultById(query);
			}
		}
	}

	@Transactional
	public void deleteBySqlDefId(Long sqlDefId) {
		SqlResultQuery query = new SqlResultQuery();
		query.setSqlDefId(sqlDefId);
		sqlResultMapper.deleteBySqlDefId(query);
	}

	public SqlResult getSqlResult(Long id) {
		if (id == null) {
			return null;
		}
		SqlResultQuery query = new SqlResultQuery();
		query.setId(id);
		SqlResult sqlResult = sqlResultMapper.getSqlResultById(query);
		return sqlResult;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSqlResultCountByQueryCriteria(SqlResultQuery query) {
		return sqlResultMapper.getSqlResultCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlResult> getSqlResultsByQueryCriteria(int start, int pageSize, SqlResultQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SqlResult> rows = sqlSessionTemplate.selectList("getSqlResults", query, rowBounds);
		return rows;
	}

	@Transactional
	public void insertAll(long sqlDefId, String type, int runDay, List<SqlResult> resultList) {
		if (resultList != null && !resultList.isEmpty()) {
			for (SqlResult sqlResult : resultList) {
				sqlResult.setSqlDefId(sqlDefId);
				sqlResult.setType(type);
				sqlResult.setRunDay(runDay);
				sqlResult.setSuffix(String.valueOf(sqlDefId));
				this.save(sqlResult);
			}
		}
	}

	public List<SqlResult> list(SqlResultQuery query) {
		List<SqlResult> list = sqlResultMapper.getSqlResults(query);
		return list;
	}

	@Transactional
	public void save(SqlResult sqlResult) {
		if (sqlResult.getCreateBy() != null) {
			sqlResult.setId(idGenerator.nextId("SYS_SQL_RESULT", "ID_"));
			sqlResult.setCreateTime(new Date());

			Calendar calendar = Calendar.getInstance();
			calendar.setTime(new Date());
			int year = calendar.get(Calendar.YEAR);
			int month = calendar.get(Calendar.MONTH) + 1;
			int week = calendar.get(Calendar.WEEK_OF_YEAR);

			sqlResult.setRunYear(year);
			sqlResult.setRunMonth(month);
			sqlResult.setRunWeek(week);
			sqlResult.setRunDay(DateUtils.getNowYearMonthDay());

			if (month <= 3) {
				sqlResult.setRunQuarter(1);
			} else if (month > 3 && month <= 6) {
				sqlResult.setRunQuarter(2);
			}
			if (month > 6 && month <= 9) {
				sqlResult.setRunQuarter(3);
			}
			if (month > 9) {
				sqlResult.setRunQuarter(4);
			}

			if (SqlExecutionThreadLocal.getJobNo() != null
					&& StringUtils.isNotEmpty(SqlExecutionThreadLocal.getJobNo())) {
				sqlResult.setJobNo(SqlExecutionThreadLocal.getJobNo());
			}
			sqlResultMapper.insertSqlResult(sqlResult);
		}
	}

	@Transactional
	public void saveAll(long sqlDefId, String type, int runDay, List<SqlResult> resultList) {
		SqlResultQuery query = new SqlResultQuery();
		query.sqlDefId(sqlDefId);
		query.setRunDay(runDay);
		query.type(type);
		query.setSuffix(String.valueOf(sqlDefId));
		sqlResultMapper.deleteSqlResults(query);
		if (resultList != null && !resultList.isEmpty()) {
			for (SqlResult sqlResult : resultList) {
				sqlResult.setSqlDefId(sqlDefId);
				sqlResult.setType(type);
				sqlResult.setRunDay(runDay);
				sqlResult.setSuffix(String.valueOf(sqlDefId));
				this.save(sqlResult);
			}
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
	public void setSqlResultMapper(SqlResultMapper sqlResultMapper) {
		this.sqlResultMapper = sqlResultMapper;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
