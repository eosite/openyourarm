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
import com.glaf.core.util.UUID32;
import com.glaf.dep.base.mapper.*;
import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;
import com.glaf.dep.base.service.DepBasePropService;

@Service("com.glaf.dep.base.service.depBasePropService")
@Transactional(readOnly = true)
public class DepBasePropServiceImpl implements DepBasePropService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected DepBasePropMapper depBasePropMapper;

	protected DepBaseCategoryMapper depBaseCategoryMapper;

	public DepBasePropServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			depBasePropMapper.deleteDepBasePropById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ruleIds) {
		if (ruleIds != null && !ruleIds.isEmpty()) {
			for (String id : ruleIds) {
				depBasePropMapper.deleteDepBasePropById(id);
			}
		}
	}

	public int count(DepBasePropQuery query) {
		return depBasePropMapper.getDepBasePropCount(query);
	}

	public List<DepBaseProp> list(DepBasePropQuery query) {
		List<DepBaseProp> list = depBasePropMapper.getDepBaseProps(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getDepBasePropCountByQueryCriteria(DepBasePropQuery query) {
		return depBasePropMapper.getDepBasePropCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<DepBaseProp> getDepBasePropsByQueryCriteria(int start, int pageSize, DepBasePropQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<DepBaseProp> rows = sqlSessionTemplate.selectList("getDepBaseProps", query, rowBounds);
		return rows;
	}

	public DepBaseProp getDepBaseProp(String id) {
		if (id == null) {
			return null;
		}
		DepBaseProp depBaseProp = depBasePropMapper.getDepBasePropById(id);
		return depBaseProp;
	}

	@Transactional
	public void save(DepBaseProp depBaseProp) {
		if (StringUtils.isEmpty(depBaseProp.getRuleId())) {
			depBaseProp.setRuleId(UUID32.getUUID());
			depBasePropMapper.insertDepBaseProp(depBaseProp);
		} else {
			depBasePropMapper.updateDepBaseProp(depBaseProp);
		}
	}

	@Transactional
	public void runBatch() {
		logger.debug("-------------------start run-------------------");
		String sql = "  ";// 要运行的SQL语句
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
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

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBasePropMapper")
	public void setDepBasePropMapper(DepBasePropMapper depBasePropMapper) {
		this.depBasePropMapper = depBasePropMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource(name = "com.glaf.dep.base.mapper.DepBaseCategoryMapper")
	public void setDepBaseCategoryMapper(DepBaseCategoryMapper depBaseCategoryMapper) {
		this.depBaseCategoryMapper = depBaseCategoryMapper;
	}

	@Override
	public String getNextRuleCode(Long categoryId) {
		DepBaseCategory category = depBaseCategoryMapper.getDepBaseCategoryById(categoryId);

		String nextRuleCode = "";
		String maxRuleCode = depBasePropMapper.getMaxRuleCodeByCategoryCode(category.getCode() + "-%");
		if (StringUtils.isNotEmpty(maxRuleCode)) {
			maxRuleCode = maxRuleCode.replaceAll(category.getCode() + "-", "");
			maxRuleCode = (Integer.parseInt(maxRuleCode) + 1) + "";
			if (maxRuleCode.length() == 1) {
				maxRuleCode = "00" + maxRuleCode;
			} else if (maxRuleCode.length() == 2) {
				maxRuleCode = "0" + maxRuleCode;
			}
			nextRuleCode = category.getCode() + "-" + maxRuleCode;
		} else {
			nextRuleCode = category.getCode() + "-001";
		}
		return nextRuleCode;
	}

	@Override
	public int getNextOrderNo() {
		return depBasePropMapper.getNextOrderNo();
	}

	@Override
	public List<DepBaseProp> getDepBasePropsByCategoryCode(String categoryCode) {
		
		return depBasePropMapper.getDepBasePropsByCategoryCode(categoryCode);
	}

}
