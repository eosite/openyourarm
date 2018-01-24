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
import com.glaf.core.util.*;

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.TreeTableSyntheticService;
import com.glaf.datamgr.util.TreeTableCountDomainFactory;

@Service("treeTableSyntheticService")
@Transactional(readOnly = true)
public class TreeTableSyntheticServiceImpl implements TreeTableSyntheticService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected TreeTableSyntheticMapper treeTableSyntheticMapper;

	protected TreeTableSyntheticRuleMapper treeTableSyntheticRuleMapper;

	public TreeTableSyntheticServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<TreeTableSynthetic> list) {
		for (TreeTableSynthetic treeTableSynthetic : list) {
			if (treeTableSynthetic.getId() == null) {
				treeTableSynthetic.setId(idGenerator.nextId("SYS_TREETABLE_SYNTHETIC"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			treeTableSyntheticMapper.bulkInsertTreeTableSynthetic_oracle(list);
		} else {
			treeTableSyntheticMapper.bulkInsertTreeTableSynthetic(list);
		}
	}

	public int count(TreeTableSyntheticQuery query) {
		return treeTableSyntheticMapper.getTreeTableSyntheticCount(query);
	}

	@Transactional
	public void deleteById(long id) {
		if (id != 0) {
			treeTableSyntheticRuleMapper.deleteTreeTableSyntheticRulesBySyntheticId(id);
			treeTableSyntheticMapper.deleteTreeTableSyntheticById(id);
		}
	}

	@Transactional
	public void deleteRuleById(long id) {
		treeTableSyntheticRuleMapper.deleteTreeTableSyntheticRuleById(id);
	}

	public TreeTableSynthetic getTreeTableSynthetic(long id) {
		if (id == 0) {
			return null;
		}
		TreeTableSynthetic treeTableSynthetic = treeTableSyntheticMapper.getTreeTableSyntheticById(id);
		if (treeTableSynthetic != null) {
			List<TreeTableSyntheticRule> rules = treeTableSyntheticRuleMapper
					.getTreeTableSyntheticRulesBySyntheticId(id);
			treeTableSynthetic.setRules(rules);
		}
		return treeTableSynthetic;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getTreeTableSyntheticCountByQueryCriteria(TreeTableSyntheticQuery query) {
		return treeTableSyntheticMapper.getTreeTableSyntheticCount(query);
	}

	public TreeTableSyntheticRule getTreeTableSyntheticRule(long ruleId) {
		return treeTableSyntheticRuleMapper.getTreeTableSyntheticRuleById(ruleId);
	}

	public List<TreeTableSyntheticRule> getTreeTableSyntheticRulesByTableName(String tableName) {
		return treeTableSyntheticRuleMapper.getTreeTableSyntheticRulesByTableName(tableName);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<TreeTableSynthetic> getTreeTableSyntheticsByQueryCriteria(int start, int pageSize,
			TreeTableSyntheticQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<TreeTableSynthetic> rows = sqlSessionTemplate.selectList("getTreeTableSynthetics", query, rowBounds);
		return rows;
	}

	public List<TreeTableSynthetic> list(TreeTableSyntheticQuery query) {
		List<TreeTableSynthetic> list = treeTableSyntheticMapper.getTreeTableSynthetics(query);
		return list;
	}

	@Transactional
	public void resetAllTreeTableSyntheticStatus() {
		treeTableSyntheticMapper.resetAllTreeTableSyntheticStatus();
	}

	@Transactional
	public void save(TreeTableSynthetic treeTableSynthetic) {
		if (treeTableSynthetic.getId() == null) {
			treeTableSynthetic.setId(idGenerator.nextId("SYS_TREETABLE_SYNTHETIC"));
			treeTableSynthetic.setCreateTime(new Date());
			if (StringUtils.isEmpty(treeTableSynthetic.getTargetTableName())) {
				treeTableSynthetic
						.setTargetTableName(TreeTableCountDomainFactory.TABLENAME + treeTableSynthetic.getId());
			}
			treeTableSyntheticMapper.insertTreeTableSynthetic(treeTableSynthetic);
		} else {
			treeTableSyntheticMapper.updateTreeTableSynthetic(treeTableSynthetic);
		}
	}

	/**
	 * 另存一条记录
	 * 
	 * @return
	 */
	@Transactional
	public void saveAs(TreeTableSynthetic treeTableSynthetic) {
		long oldId = treeTableSynthetic.getId();
		treeTableSynthetic.setId(idGenerator.nextId("SYS_TREETABLE_SYNTHETIC"));
		treeTableSynthetic.setCreateTime(new Date());
		treeTableSynthetic.setTargetTableName(TreeTableCountDomainFactory.TABLENAME + treeTableSynthetic.getId());
		treeTableSyntheticMapper.insertTreeTableSynthetic(treeTableSynthetic);

		List<TreeTableSyntheticRule> rules = treeTableSyntheticRuleMapper
				.getTreeTableSyntheticRulesBySyntheticId(oldId);
		if (rules != null && !rules.isEmpty()) {
			for (TreeTableSyntheticRule rule : rules) {
				rule.setId(idGenerator.nextId("SYS_TREETABLE_SYNTHETIC_RULE"));
				rule.setSyntheticId(treeTableSynthetic.getId());
				rule.setColumnName("TTC_" + treeTableSynthetic.getId() + "_COL"
						+ idGenerator.nextId("TREETABLE_COL_" + rule.getSyntheticId()));
				treeTableSyntheticRuleMapper.insertTreeTableSyntheticRule(rule);
			}
		}
	}

	@Transactional
	public void saveRule(TreeTableSyntheticRule rule) {
		if (rule.getId() == 0) {
			rule.setId(idGenerator.nextId("SYS_TREETABLE_SYNTHETIC_RULE"));
			if (StringUtils.isEmpty(rule.getColumnName())) {
				TreeTableSynthetic bean = this.getTreeTableSynthetic(rule.getSyntheticId());
				rule.setColumnName(
						"TTC_" + bean.getId() + "_COL" + idGenerator.nextId("TREETABLE_COL_" + rule.getSyntheticId()));
			}
			treeTableSyntheticRuleMapper.insertTreeTableSyntheticRule(rule);
		} else {
			treeTableSyntheticRuleMapper.updateTreeTableSyntheticRule(rule);
		}
	}

	/**
	 * 保存同步规则
	 * 
	 * @param syntheticId
	 * @param rules
	 */
	@Transactional
	public void saveRules(long syntheticId, List<TreeTableSyntheticRule> rules) {
		if (rules != null && !rules.isEmpty()) {
			treeTableSyntheticRuleMapper.deleteTreeTableSyntheticRulesBySyntheticId(syntheticId);
			for (TreeTableSyntheticRule rule : rules) {
				rule.setId(idGenerator.nextId("SYS_TREETABLE_SYNTHETIC_RULE"));
				rule.setSyntheticId(syntheticId);
				treeTableSyntheticRuleMapper.insertTreeTableSyntheticRule(rule);
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
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource
	public void setTreeTableSyntheticMapper(TreeTableSyntheticMapper treeTableSyntheticMapper) {
		this.treeTableSyntheticMapper = treeTableSyntheticMapper;
	}

	@javax.annotation.Resource
	public void setTreeTableSyntheticRuleMapper(TreeTableSyntheticRuleMapper treeTableSyntheticRuleMapper) {
		this.treeTableSyntheticRuleMapper = treeTableSyntheticRuleMapper;
	}

	@Transactional
	public void updateTreeTableSyntheticStatus(TreeTableSynthetic model) {
		model.setSyncTime(new java.util.Date());
		treeTableSyntheticMapper.updateTreeTableSyntheticStatus(model);
	}

}
