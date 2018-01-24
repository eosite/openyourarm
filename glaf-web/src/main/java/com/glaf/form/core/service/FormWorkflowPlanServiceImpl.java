package com.glaf.form.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.*;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.hibernate.mapping.Array;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.id.*;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.glaf.core.dao.*;
import com.glaf.core.util.JdbcUtils;

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Service("com.glaf.form.core.service.formWorkflowPlanService")
@Transactional(readOnly = true)
public class FormWorkflowPlanServiceImpl implements FormWorkflowPlanService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormWorkflowPlanMapper formWorkflowPlanMapper;

	public FormWorkflowPlanServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formWorkflowPlanMapper.deleteFormWorkflowPlanById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formWorkflowPlanMapper.deleteFormWorkflowPlanById(id);
			}
		}
	}

	public int count(FormWorkflowPlanQuery query) {
		return formWorkflowPlanMapper.getFormWorkflowPlanCount(query);
	}

	public List<FormWorkflowPlan> list(FormWorkflowPlanQuery query) {
		List<FormWorkflowPlan> list = formWorkflowPlanMapper.getFormWorkflowPlans(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormWorkflowPlanCountByQueryCriteria(FormWorkflowPlanQuery query) {
		return formWorkflowPlanMapper.getFormWorkflowPlanCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormWorkflowPlan> getFormWorkflowPlansByQueryCriteria(int start, int pageSize,
			FormWorkflowPlanQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormWorkflowPlan> rows = sqlSessionTemplate.selectList("getFormWorkflowPlans", query, rowBounds);
		return rows;
	}

	public FormWorkflowPlan getFormWorkflowPlan(String id) {
		if (id == null) {
			return null;
		}
		FormWorkflowPlan formWorkflowPlan = formWorkflowPlanMapper.getFormWorkflowPlanById(id);
		return formWorkflowPlan;
	}

	@Transactional
	public void save(FormWorkflowPlan formWorkflowPlan) {
		if (StringUtils.isEmpty(formWorkflowPlan.getId())) {
			formWorkflowPlan.setId(idGenerator.getNextId("FORM_WORKFLOW_PLAN"));
			formWorkflowPlan.setCreateDateTime(new Date());

			formWorkflowPlanMapper.insertFormWorkflowPlan(formWorkflowPlan);
		} else {
			formWorkflowPlan.setModifyDateTime(new Date());

			formWorkflowPlanMapper.updateFormWorkflowPlan(formWorkflowPlan);
		}
	}

	@Override
	public Integer getNextVersionByDefId(String defId) {

		Integer maxVersion = formWorkflowPlanMapper.getMaxVersionByDefId(defId);

		return maxVersion == null ? 1 : (maxVersion + 1);
	}

	public Map<String, Set<String>> getRelationPage(String defId) {
		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
		query.setDefId(defId);
		query.setVersion(this.getNextVersionByDefId(defId) - 1);
		return this.getRelationPage(this.list(query));
	}

	public Map<String, Set<String>> getRelationPage(List<FormWorkflowPlan> formWorkflowPlans) {
		Map<String, Set<String>> map = new HashMap<>();
		if (CollectionUtils.isEmpty(formWorkflowPlans)) {
			return map;
		}
		JSONObject json;
		for (FormWorkflowPlan plan : formWorkflowPlans) {
			if (StringUtils.isEmpty(plan.getBytes())) {
				continue;
			}
			json = JSON.parseArray(plan.getBytes()).getJSONObject(0);
			String pageId = plan.getPageId(), href = json.getString("href");
			if (StringUtils.isNotBlank(href)) {
				if (!map.containsKey(href)) {
					map.put(href, new HashSet<>());
					if (!map.containsKey(pageId)) {
						map.put(pageId, map.get(href));
					}
					map.get(href).add(href);
					map.get(href).add(pageId);
				}
			} else if (!map.containsKey(pageId)) {
				map.put(pageId, new HashSet<>());
				map.get(pageId).add(pageId);
			}
		}
		return map;
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormWorkflowPlanMapper")
	public void setFormWorkflowPlanMapper(FormWorkflowPlanMapper formWorkflowPlanMapper) {
		this.formWorkflowPlanMapper = formWorkflowPlanMapper;
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
	public List<FormWorkflowPlan> getFormWorkflowPlansWithTree(Map<String, Object> params) {
		return this.formWorkflowPlanMapper.getFormWorkflowPlansWithTree(params);
	}

	@Override
	public List<FormWorkflowPlan> getPlans(String defId) {
		Integer version = this.getNextVersionByDefId(defId);
		FormWorkflowPlanQuery query = new FormWorkflowPlanQuery();
		query.setDefId(defId);
		query.setVersion(version - 1);
		return this.list(query);
	}

}
