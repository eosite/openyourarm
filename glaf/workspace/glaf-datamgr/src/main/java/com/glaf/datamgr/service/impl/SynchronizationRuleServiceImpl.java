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
import com.glaf.core.util.UUID32;
import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.SynchronizationRuleService;

@Service("synchronizationRuleService")
@Transactional(readOnly = true)
public class SynchronizationRuleServiceImpl implements SynchronizationRuleService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SynchronizationRuleMapper synchronizationRuleMapper;

	public SynchronizationRuleServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<SynchronizationRule> list) {
		for (SynchronizationRule synchronizationRule : list) {
			if (StringUtils.isEmpty(synchronizationRule.getId())) {
				synchronizationRule.setId(idGenerator.getNextId("SYSSYNCHRONIZATION"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			synchronizationRuleMapper.bulkInsertSynchronizationRule_oracle(list);
		} else {
			synchronizationRuleMapper.bulkInsertSynchronizationRule(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			synchronizationRuleMapper.deleteSynchronizationRuleById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				synchronizationRuleMapper.deleteSynchronizationRuleById(id);
			}
		}
	}

	public int count(SynchronizationRuleQuery query) {
		return synchronizationRuleMapper.getSynchronizationRuleCount(query);
	}

	public List<SynchronizationRule> list(SynchronizationRuleQuery query) {
		List<SynchronizationRule> list = synchronizationRuleMapper.getSynchronizationRules(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSynchronizationRuleCountByQueryCriteria(SynchronizationRuleQuery query) {
		return synchronizationRuleMapper.getSynchronizationRuleCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SynchronizationRule> getSynchronizationRulesByQueryCriteria(int start, int pageSize,
			SynchronizationRuleQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SynchronizationRule> rows = sqlSessionTemplate.selectList("getSynchronizationRules", query, rowBounds);
		return rows;
	}

	public SynchronizationRule getSynchronizationRule(String id) {
		if (id == null) {
			return null;
		}
		SynchronizationRule synchronizationRule = synchronizationRuleMapper.getSynchronizationRuleById(id);
		return synchronizationRule;
	}

	public SynchronizationRule getSynchronizationRuleByTargetTable(String targetTable) {
		if (targetTable == null) {
			return null;
		}
		targetTable = "[" + targetTable + "]";
		SynchronizationRule synchronizationRule = synchronizationRuleMapper
				.getSynchronizationRuleByTargetTable(targetTable);
		return synchronizationRule;
	}

	@Transactional
	public void save(SynchronizationRule synchronizationRule) {
		if (StringUtils.isEmpty(synchronizationRule.getId())) {
			synchronizationRule.setId(UUID32.getUUID());
			synchronizationRuleMapper.insertSynchronizationRule(synchronizationRule);
		} else {
			synchronizationRuleMapper.updateSynchronizationRule(synchronizationRule);
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
	public void setSynchronizationRuleMapper(SynchronizationRuleMapper synchronizationRuleMapper) {
		this.synchronizationRuleMapper = synchronizationRuleMapper;
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
