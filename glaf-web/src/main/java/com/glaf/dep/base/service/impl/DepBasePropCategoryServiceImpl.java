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
import com.glaf.dep.base.service.DepBasePropCategoryService;

@Service("com.glaf.dep.base.service.depBasePropCategoryService")
@Transactional(readOnly = true)
public class DepBasePropCategoryServiceImpl implements
		DepBasePropCategoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBasePropCategoryMapper depBasePropCategoryMapper;

	public DepBasePropCategoryServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			depBasePropCategoryMapper.deleteDepBasePropCategoryById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ruleIds) {
		if (ruleIds != null && !ruleIds.isEmpty()) {
			for (String id : ruleIds) {
				depBasePropCategoryMapper.deleteDepBasePropCategoryById(id);
			}
		}
	}

	public int count(DepBasePropCategoryQuery query) {
		return depBasePropCategoryMapper.getDepBasePropCategoryCount(query);
	}

	public List<DepBasePropCategory> list(DepBasePropCategoryQuery query) {
		List<DepBasePropCategory> list = depBasePropCategoryMapper
				.getDepBasePropCategorys(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBasePropCategoryCountByQueryCriteria(
			DepBasePropCategoryQuery query) {
		return depBasePropCategoryMapper.getDepBasePropCategoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBasePropCategory> getDepBasePropCategorysByQueryCriteria(
			int start, int pageSize, DepBasePropCategoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBasePropCategory> rows = sqlSessionTemplate.selectList(
				"getDepBasePropCategorys", query, rowBounds);
		return rows;
	}

	public DepBasePropCategory getDepBasePropCategory(String ruleId,
			Long categoryId) {
		if (ruleId == null || categoryId == null) {
			return null;
		}
		DepBasePropCategoryQuery query = new DepBasePropCategoryQuery();
		query.setRuleId(ruleId);
		query.setDepBaseCategoryId(categoryId);
		DepBasePropCategory depBasePropCategory = depBasePropCategoryMapper
				.getDepBasePropCategory(query);
		return depBasePropCategory;
	}

	@Transactional
	public void save(DepBasePropCategory depBasePropCategory) {
		DepBasePropCategory model = this.getDepBasePropCategory(
				depBasePropCategory.getRuleId(),
				depBasePropCategory.getDepBaseCategoryId());
		if (model == null) {
			depBasePropCategoryMapper
					.insertDepBasePropCategory(depBasePropCategory);
		}
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBasePropCategoryMapper")
	public void setDepBasePropCategoryMapper(
			DepBasePropCategoryMapper depBasePropCategoryMapper) {
		this.depBasePropCategoryMapper = depBasePropCategoryMapper;
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
	public List<DepBasePropCategory> getDepBasePropCatgorysByCategoryId(
			Long categoryId) {
		DepBasePropCategoryQuery query = new DepBasePropCategoryQuery();
		query.setDepBaseCategoryId(categoryId);
		return this.list(query);
	}

	@Override
	public void deleteByRuleId(String ruleId) {
		depBasePropCategoryMapper.deleteByRuleId(ruleId);
	}

}
