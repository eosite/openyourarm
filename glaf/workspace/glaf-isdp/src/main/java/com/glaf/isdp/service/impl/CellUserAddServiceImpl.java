package com.glaf.isdp.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.config.DBConfiguration;
import com.glaf.core.dao.EntityDAO;
import com.glaf.core.dialect.Dialect;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.query.TablePageQuery;
import com.glaf.isdp.service.CellUserAddService;

@Service("com.glaf.isdp.service.cellUserAddService")
@Transactional(readOnly = true)
public class CellUserAddServiceImpl implements CellUserAddService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	public CellUserAddServiceImpl() {

	}

	@Override
	public int getDataCount(String tableName, String whereCause) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from ").append(tableName);
		if (whereCause != null) {
			if (whereCause.toLowerCase().indexOf("where") > 0) {
				sql.append(whereCause);
			} else {
				sql.append(" where ").append(whereCause);
			}
		}
		logger.debug(sql.toString());

		int count = jdbcTemplate.queryForObject(sql.toString(), Integer.class);

		return count;
	}

	@Override
	public int getDataCountBySql(String sql) {
		logger.debug(sql);
		int count = jdbcTemplate.queryForObject(sql, Integer.class);
		return count;
	}

	@Override
	public List<Map<String, Object>> getDataList(String tableName, String whereCause, String orderBy) {
		StringBuffer sql = new StringBuffer();
		sql.append("select * from ").append(tableName);

		if (whereCause != null) {
			if (whereCause.toLowerCase().indexOf("where") > 0) {
				sql.append(whereCause);
			} else {
				sql.append(" where ").append(whereCause);
			}
		}
		if (StringUtils.isNotEmpty(orderBy)) {
			sql.append(" order by ").append(orderBy);
		}

		logger.debug(sql.toString());

		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql.toString());

		return results;
	}

	@Override
	public List<Map<String, Object>> getDataListByQueryCriteria(String sql, int start, int pageSize) {
		logger.debug(sql);
		Map<String, String> queryMap = new HashMap<String, String>();
		queryMap.put("queryString", sql);
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Map<String, Object>> results = sqlSessionTemplate.selectList("getSqlQueryList", queryMap, rowBounds);
		return results;
	}

	@Override
	public List<Map<String, Object>> getDataListBySqlCriteria(String sql, int start, int pageSize) {
		Dialect dialect = DBConfiguration.getCurrentDialect();
		sql = dialect.getLimitString(sql, start, pageSize);
		logger.debug(sql);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql.toString());
		return results;
	}

	@Override
	public List<Map<String, Object>> getDataListByQueryCriteria(String tableName, String whereCause, String orderBy,
			int start, int pageSize) {
		TablePageQuery tablePageQuery = new TablePageQuery();
		tablePageQuery.tableName(tableName);
		if (whereCause != null && whereCause.trim().length() > 0) {
			tablePageQuery.setWhere(whereCause);
		}
		if (orderBy != null) {
			tablePageQuery.orderAsc(orderBy);
		}
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<Map<String, Object>> results = sqlSessionTemplate.selectList("getTableData", tablePageQuery, rowBounds);
		return results;
	}

	@Override
	public List<Map<String, Object>> getDataListBySql(String sql) {
		logger.debug(sql);
		List<Map<String, Object>> results = jdbcTemplate.queryForList(sql);
		return results;
	}

	@Override
	public Map<String, Object> getDataMapBySql(String sql) {
		Map<String, Object> map = null;
		try {
			map = jdbcTemplate.queryForMap(sql);
		} catch (Exception ex) {
			logger.debug(sql);
			logger.error(ex.getMessage());
		}
		return map;
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
