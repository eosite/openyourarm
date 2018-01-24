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
import com.glaf.dep.base.service.DepBaseCompPropService;

@Service("com.glaf.dep.base.service.depBaseCompPropService")
@Transactional(readOnly = true)
public class DepBaseCompPropServiceImpl implements DepBaseCompPropService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBaseCompPropMapper depBaseCompPropMapper;

	public DepBaseCompPropServiceImpl() {

	}

	@Transactional
	public void deleteByRuleId(String id) {
		if (id != null) {
			depBaseCompPropMapper.deleteDepBaseCompPropByRuleId(id);
		}
	}

	public int count(DepBaseCompPropQuery query) {
		return depBaseCompPropMapper.getDepBaseCompPropCount(query);
	}

	public List<DepBaseCompProp> list(DepBaseCompPropQuery query) {
		List<DepBaseCompProp> list = depBaseCompPropMapper
				.getDepBaseCompProps(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBaseCompPropCountByQueryCriteria(DepBaseCompPropQuery query) {
		return depBaseCompPropMapper.getDepBaseCompPropCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseCompProp> getDepBaseCompPropsByQueryCriteria(int start,
			int pageSize, DepBaseCompPropQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseCompProp> rows = sqlSessionTemplate.selectList(
				"getDepBaseCompProps", query, rowBounds);
		return rows;
	}

	public DepBaseCompProp getDepBaseCompProp(String id) {
		if (id == null) {
			return null;
		}
		DepBaseCompProp depBaseCompProp = depBaseCompPropMapper
				.getDepBaseCompPropById(id);
		return depBaseCompProp;
	}

	@Transactional
	public void save(DepBaseCompProp depBaseCompProp) {
		// if (StringUtils.isEmpty(depBaseCompProp.getId())) {
		// depBaseCompProp.setId(idGenerator.getNextId("DEP_BASE_COMPPROP"));
		// depBaseCompProp.setCreateDate(new Date());
		// depBaseCompProp.setDeleteFlag(0);
		depBaseCompPropMapper.insertDepBaseCompProp(depBaseCompProp);
		// } else {
		// depBaseCompPropMapper.updateDepBaseCompProp(depBaseCompProp);
		// }
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseCompPropMapper")
	public void setDepBaseCompPropMapper(
			DepBaseCompPropMapper depBaseCompPropMapper) {
		this.depBaseCompPropMapper = depBaseCompPropMapper;
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
	public List<DepBaseCompProp> getDepBaseCompPropsByRuleId(String ruleId) {
		DepBaseCompPropQuery query = new DepBaseCompPropQuery();
		query.setRuleId(ruleId);
		return this.list(query);
	}

	@Override
	public void deleteByPrimaryKey(String componentId, String ruleId) {
		depBaseCompPropMapper.deleteByPrimaryKey(componentId,ruleId);
	}

}
