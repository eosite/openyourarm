package com.glaf.form.core.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Table;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
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
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.JdbcUtils;
import com.glaf.form.core.domain.FormTask;
import com.glaf.form.core.domain.FormTaskmain;
import com.glaf.form.core.mapper.FormTaskmainMapper;
import com.glaf.form.core.query.FormTaskQuery;
import com.glaf.form.core.query.FormTaskmainQuery;
import com.google.gwt.user.client.rpc.core.java.util.Arrays;

@Service("com.glaf.form.core.service.formTaskmainService")
@Transactional(readOnly = true)
public class FormTaskmainServiceImpl implements FormTaskmainService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormTaskmainMapper formTaskmainMapper;

	protected FormTaskService formTaskService;

	protected FormWorkflowPlanService formWorkflowPlanService;

	public FormTaskmainServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<FormTaskmain> list) {
		for (FormTaskmain formTaskmain : list) {
			if (formTaskmain.getId() == null) {
				formTaskmain.setId(idGenerator.nextId("FORM_TASKMAIN"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// formTaskmainMapper.bulkInsertFormTaskmain_oracle(list);
		} else {
			// formTaskmainMapper.bulkInsertFormTaskmain(list);
		}
	}

	@Transactional
	public void deleteById(Long id) {
		if (id != null) {
			formTaskmainMapper.deleteFormTaskmainById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Long id : ids) {
				formTaskmainMapper.deleteFormTaskmainById(id);
			}
		}
	}

	public int count(FormTaskmainQuery query) {
		return formTaskmainMapper.getFormTaskmainCount(query);
	}

	public List<FormTaskmain> list(FormTaskmainQuery query) {
		List<FormTaskmain> list = formTaskmainMapper.getFormTaskmains(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormTaskmainCountByQueryCriteria(FormTaskmainQuery query) {
		return formTaskmainMapper.getFormTaskmainCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormTaskmain> getFormTaskmainsByQueryCriteria(int start, int pageSize, FormTaskmainQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormTaskmain> rows = sqlSessionTemplate.selectList("getFormTaskmains", query, rowBounds);
		return rows;
	}

	public FormTaskmain getFormTaskmain(Long id) {
		if (id == null) {
			return null;
		}
		FormTaskmain formTaskmain = formTaskmainMapper.getFormTaskmainById(id);
		return formTaskmain;
	}

	@Transactional
	public void save(FormTaskmain formTaskmain) {
		if (formTaskmain.getId() == null) {
			formTaskmain.setId(idGenerator.nextId("FORM_TASKMAIN"));
			// formTaskmain.setCreateDate(new Date());
			// formTaskmain.setDeleteFlag(0);
			formTaskmainMapper.insertFormTaskmain(formTaskmain);
		} else {
			formTaskmainMapper.updateFormTaskmain(formTaskmain);
		}
	}

	@Override
	public FormTaskmain getFormTaskMainByPageIdAndIdValue(String pageId, String idValue) {
		FormTaskmain main = null;
		if (StringUtils.isBlank(pageId) || StringUtils.isBlank(idValue))
			return main;
		FormTaskQuery query = new FormTaskQuery();
		query.setPageId(pageId);
		query.setIdValue(idValue);

		List<FormTask> fts = this.formTaskService.list(query);
		if (CollectionUtils.isNotEmpty(fts)) {
			main = this.getFormTaskmain(fts.get(0).getTmId());
		}
		return main;
	}

	/**
	 * status 0:流程正常提交； 1:流程结束； -1:退回
	 */
	@Override
	public void updateStatus(Integer status, String processId) {
		logger.debug("-------------------start updateStatus-------------------");
		Connection connection = null;
		PreparedStatement psmt = null;
		try {
			String tableName = FormTaskmain.class.getDeclaredAnnotation(Table.class).name();
			String sql = String.format("UPDATE %s SET STATUS_ = ? WHERE PROCESSID_ = ?", tableName);// 要运行的SQL语句
			connection = DataSourceUtils.getConnection(jdbcTemplate.getDataSource());
			psmt = connection.prepareStatement(sql);

			List<Object> params = new ArrayList<Object>();
			params.add(status);
			params.add(processId);

			JdbcUtils.fillStatement(psmt, params);

			psmt.execute();
		} catch (Exception ex) {
			ex.printStackTrace();
			logger.error("updateStatus error", ex);
			throw new RuntimeException(ex);
		} finally {
			JdbcUtils.close(psmt);
		}
		logger.debug("-------------------end updateStatus-------------------");
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormTaskmainMapper")
	public void setFormTaskmainMapper(FormTaskmainMapper formTaskmainMapper) {
		this.formTaskmainMapper = formTaskmainMapper;
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
	public void setFormTaskService(FormTaskService formTaskService) {
		this.formTaskService = formTaskService;
	}

	@javax.annotation.Resource
	public void setFormWorkflowPlanService(FormWorkflowPlanService formWorkflowPlanService) {
		this.formWorkflowPlanService = formWorkflowPlanService;
	}

	@Override
	public FormTaskmain getParentFormTaskmainBySubProcessId(String processId) {

		/**
		 * 获取当前流程实例
		 */
		FormTaskmainQuery query = new FormTaskmainQuery();
		query.setProcessId(processId);
		List<FormTaskmain> list = this.list(query);
		if (CollectionUtils.isEmpty(list)) {
			return null;
		}

		FormTaskmain main = list.get(0);

		if (StringUtils.isBlank(main.getP_processId())) {
			return null;
		}

		/**
		 * 获取当前父流程实例
		 */
		query.setProcessId(main.getP_processId());
		list = this.list(query);

		if (CollectionUtils.isNotEmpty(list)) {
			return list.get(0);
		}

		return null;
	}

}
