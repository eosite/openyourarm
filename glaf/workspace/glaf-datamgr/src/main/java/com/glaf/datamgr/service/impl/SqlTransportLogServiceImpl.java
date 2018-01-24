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
import com.glaf.datamgr.service.SqlTransportLogService;

@Service("sqlTransportLogService")
@Transactional(readOnly = true)
public class SqlTransportLogServiceImpl implements SqlTransportLogService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SqlTransportLogMapper sqlTransportLogMapper;

	public SqlTransportLogServiceImpl() {

	}

	public int count(SqlTransportLogQuery query) {
		return sqlTransportLogMapper.getSqlTransportLogCount(query);
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			sqlTransportLogMapper.deleteSqlTransportLogById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				sqlTransportLogMapper.deleteSqlTransportLogById(id);
			}
		}
	}

	public SqlTransportLog getSqlTransportLog(Long id) {
		if (id == null) {
			return null;
		}
		SqlTransportLog sqlTransportLog = sqlTransportLogMapper.getSqlTransportLogById(id);
		return sqlTransportLog;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSqlTransportLogCountByQueryCriteria(SqlTransportLogQuery query) {
		return sqlTransportLogMapper.getSqlTransportLogCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SqlTransportLog> getSqlTransportLogsByQueryCriteria(int start, int pageSize,
			SqlTransportLogQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SqlTransportLog> rows = sqlSessionTemplate.selectList("getSqlTransportLogs", query, rowBounds);
		return rows;
	}

	public List<SqlTransportLog> list(SqlTransportLogQuery query) {
		List<SqlTransportLog> list = sqlTransportLogMapper.getSqlTransportLogs(query);
		return list;
	}

	@Transactional
	public void save(SqlTransportLog sqlTransportLog) {
		if (sqlTransportLog.getId() == null) {
			sqlTransportLog.setId(idGenerator.nextId("SQL_TRANS_LOG"));
			sqlTransportLogMapper.insertSqlTransportLog(sqlTransportLog);
		} else {
			sqlTransportLogMapper.updateSqlTransportLog(sqlTransportLog);
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

	@javax.annotation.Resource
	public void setSqlTransportLogMapper(SqlTransportLogMapper sqlTransportLogMapper) {
		this.sqlTransportLogMapper = sqlTransportLogMapper;
	}

}
