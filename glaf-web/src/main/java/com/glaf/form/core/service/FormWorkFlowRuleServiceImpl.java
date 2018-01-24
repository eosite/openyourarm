package com.glaf.form.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.JdbcUtils;
import com.glaf.form.core.domain.FormWorkFlowRule;
import com.glaf.form.core.mapper.FormWorkFlowRuleMapper;
import com.glaf.form.core.query.FormWorkFlowRuleQuery;

@Service("com.glaf.form.core.service.formWorkFlowRuleService")
@Transactional(readOnly = true)
public class FormWorkFlowRuleServiceImpl implements FormWorkFlowRuleService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormWorkFlowRuleMapper formWorkFlowRuleMapper;

	public FormWorkFlowRuleServiceImpl() {

	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			formWorkFlowRuleMapper.deleteFormWorkFlowRuleById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				formWorkFlowRuleMapper.deleteFormWorkFlowRuleById(id);
			}
		}
	}

	public int count(FormWorkFlowRuleQuery query) {
		return formWorkFlowRuleMapper.getFormWorkFlowRuleCount(query);
	}

	public List<FormWorkFlowRule> list(FormWorkFlowRuleQuery query) {
		List<FormWorkFlowRule> list = formWorkFlowRuleMapper.getFormWorkFlowRules(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormWorkFlowRuleCountByQueryCriteria(FormWorkFlowRuleQuery query) {
		return formWorkFlowRuleMapper.getFormWorkFlowRuleCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormWorkFlowRule> getFormWorkFlowRulesByQueryCriteria(int start, int pageSize,
			FormWorkFlowRuleQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormWorkFlowRule> rows = sqlSessionTemplate.selectList("getFormWorkFlowRules", query, rowBounds);
		return rows;
	}

	public FormWorkFlowRule getFormWorkFlowRule(Long id) {
		if (id == null) {
			return null;
		}
		FormWorkFlowRule formWorkFlowRule = formWorkFlowRuleMapper.getFormWorkFlowRuleById(id);
		return formWorkFlowRule;
	}

	@Transactional
	public void save(FormWorkFlowRule formWorkFlowRule) {
		if (formWorkFlowRule.getId() == null) {
			formWorkFlowRule.setId(idGenerator.nextId("FORM_WORKFLOW_RULE"));
			formWorkFlowRule.setCreateDatetime(new Date());
			formWorkFlowRuleMapper.insertFormWorkFlowRule(formWorkFlowRule);
		} else {
			formWorkFlowRuleMapper.updateFormWorkFlowRule(formWorkFlowRule);
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

	@Override
	public Integer getNextVersionByPageId(String pageId) {
		Integer v = this.formWorkFlowRuleMapper.getNextVersionByPageId(pageId);
		if (v == null)
			v = 0;
		return v + 1;
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormWorkFlowRuleMapper")
	public void setFormWorkFlowRuleMapper(FormWorkFlowRuleMapper formWorkFlowRuleMapper) {
		this.formWorkFlowRuleMapper = formWorkFlowRuleMapper;
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
