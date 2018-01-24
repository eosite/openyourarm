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
import com.glaf.dep.base.service.DepBaseCompScopeService;

@Service("com.glaf.dep.base.service.depBaseCompScopeService")
@Transactional(readOnly = true)
public class DepBaseCompScopeServiceImpl implements DepBaseCompScopeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBaseCompScopeMapper depBaseCompScopeMapper;

	public DepBaseCompScopeServiceImpl() {

	}

	@Transactional
	@Override
	public void deleteByComponentId(String componentId) {
		depBaseCompScopeMapper.deleteByComponentId(componentId);
	}

	@Transactional
	@Override
	public void deleteByUIId(String uiid) {
		depBaseCompScopeMapper.deleteByUIId(uiid);
	}

	public int count(DepBaseCompScopeQuery query) {
		return depBaseCompScopeMapper.getDepBaseCompScopeCount(query);
	}

	public List<DepBaseCompScope> list(DepBaseCompScopeQuery query) {
		List<DepBaseCompScope> list = depBaseCompScopeMapper
				.getDepBaseCompScopes(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBaseCompScopeCountByQueryCriteria(
			DepBaseCompScopeQuery query) {
		return depBaseCompScopeMapper.getDepBaseCompScopeCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseCompScope> getDepBaseCompScopesByQueryCriteria(
			int start, int pageSize, DepBaseCompScopeQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseCompScope> rows = sqlSessionTemplate.selectList(
				"getDepBaseCompScopes", query, rowBounds);
		return rows;
	}

	public DepBaseCompScope getDepBaseCompScope(String id) {
		if (id == null) {
			return null;
		}
		DepBaseCompScope depBaseCompScope = depBaseCompScopeMapper
				.getDepBaseCompScopeById(id);
		return depBaseCompScope;
	}

	@Transactional
	public void save(DepBaseCompScope depBaseCompScope) {
		depBaseCompScopeMapper.insertDepBaseCompScope(depBaseCompScope);
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseCompScopeMapper")
	public void setDepBaseCompScopeMapper(
			DepBaseCompScopeMapper depBaseCompScopeMapper) {
		this.depBaseCompScopeMapper = depBaseCompScopeMapper;
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
	public List<DepBaseCompScope> getDepBaseCompScopesByComponentId(
			String componentId) {
		return depBaseCompScopeMapper
				.getDepBaseCompScopesByComponentId(componentId);
	}

	@Override
	public List<DepBaseCompScope> getDepBaseCompScopesByUIId(String uiId) {
		return depBaseCompScopeMapper.getDepBaseCompScopesByUIId(uiId);
	}

}
