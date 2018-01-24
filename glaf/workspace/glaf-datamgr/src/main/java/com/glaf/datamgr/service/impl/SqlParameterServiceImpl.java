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
import com.glaf.datamgr.service.SqlParameterService;

@Service("sqlParameterService")
@Transactional(readOnly = true)
public class SqlParameterServiceImpl implements SqlParameterService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SqlParameterMapper sqlParameterMapper;

	public SqlParameterServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sqlParameterMapper.deleteSqlParameterById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				sqlParameterMapper.deleteSqlParameterById(id);
			}
		}
	}

	public int count(SqlParameterQuery query) {
		return sqlParameterMapper.getSqlParameterCount(query);
	}

	public List<SqlParameter> list(SqlParameterQuery query) {
		List<SqlParameter> list = sqlParameterMapper.getSqlParameters(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSqlParameterCountByQueryCriteria(SqlParameterQuery query) {
		return sqlParameterMapper.getSqlParameterCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlParameter> getSqlParametersByQueryCriteria(int start,
			int pageSize, SqlParameterQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SqlParameter> rows = sqlSessionTemplate.selectList(
				"getSqlParameters", query, rowBounds);
		return rows;
	}

	public SqlParameter getSqlParameter(Long id) {
		if (id == null) {
			return null;
		}
		SqlParameter sqlParameter = sqlParameterMapper.getSqlParameterById(id);
		return sqlParameter;
	}

	@Transactional
	public void save(SqlParameter sqlParameter) {
		if (sqlParameter.getId() == null) {
			sqlParameter.setId(idGenerator.nextId("SYS_SQL_PARAM"));
			sqlParameterMapper.insertSqlParameter(sqlParameter);
		} else {
			sqlParameterMapper.updateSqlParameter(sqlParameter);
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
	public void setSqlParameterMapper(SqlParameterMapper sqlParameterMapper) {
		this.sqlParameterMapper = sqlParameterMapper;
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
