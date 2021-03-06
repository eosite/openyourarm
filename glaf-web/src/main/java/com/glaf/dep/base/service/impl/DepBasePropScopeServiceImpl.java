package com.glaf.dep.base.service.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.glaf.core.dao.*;
import com.glaf.core.util.JdbcUtils;
import com.glaf.dep.base.mapper.*;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;
import com.glaf.dep.base.service.DepBasePropScopeService;

@Service("com.glaf.dep.base.service.depBasePropScopeService")
@Transactional(readOnly = true)
public class DepBasePropScopeServiceImpl implements DepBasePropScopeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBasePropScopeMapper depBasePropScopeMapper;

	public DepBasePropScopeServiceImpl() {

	}

	public int count(DepBasePropScopeQuery query) {
		return depBasePropScopeMapper.getDepBasePropScopeCount(query);
	}

	public List<DepBasePropScope> list(DepBasePropScopeQuery query) {
		List<DepBasePropScope> list = depBasePropScopeMapper
				.getDepBasePropScopes(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBasePropScopeCountByQueryCriteria(
			DepBasePropScopeQuery query) {
		return depBasePropScopeMapper.getDepBasePropScopeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBasePropScope> getDepBasePropScopesByQueryCriteria(
			int start, int pageSize, DepBasePropScopeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBasePropScope> rows = sqlSessionTemplate.selectList(
				"getDepBasePropScopes", query, rowBounds);
		return rows;
	}

	@Transactional
	public void save(DepBasePropScope depBasePropScope) {
		depBasePropScopeMapper.insertDepBasePropScope(depBasePropScope);
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate
					.getDataSource());
			psmt = connection.prepareStatement(sql);
			for (int i = 0; i < 2; i++) {
				psmt.addBatch();
			}
			psmt.executeBatch();
			psmt.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("run batch error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end run-------------------");
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBasePropScopeMapper")
	public void setDepBasePropScopeMapper(
			DepBasePropScopeMapper depBasePropScopeMapper) {
		this.depBasePropScopeMapper = depBasePropScopeMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@Override
	public List<DepBasePropScope> getDepBasePropScopesByRuleId(String ruleId) {
		return depBasePropScopeMapper.getDepBasePropScopesByRuleId(ruleId);
	}

	@Override
	public List<DepBasePropScope> getDepBasePropScopesByUIId(String uiId) {
		return depBasePropScopeMapper.getDepBasePropScopesByUIId(uiId);
	}

	@Transactional
	@Override
	public void deleteByRuleId(String ruleId) {
		depBasePropScopeMapper.deleteByRuleId(ruleId);
	}

	@Transactional
	@Override
	public void deleteByUIId(String uiId) {
		depBasePropScopeMapper.deleteByUIId(uiId);
	}
}
