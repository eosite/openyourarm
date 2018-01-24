package com.glaf.form.core.service.impl;

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

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.PageProcessInstanceService;

@Service("pageProcessInstanceService")
@Transactional(readOnly = true)
public class PageProcessInstanceServiceImpl implements PageProcessInstanceService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected PageProcessInstanceMapper pageProcessInstanceMapper;

	public PageProcessInstanceServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<PageProcessInstance> list) {
		for (PageProcessInstance pageProcessInstance : list) {
			if (StringUtils.isEmpty(pageProcessInstance.getProcessInstanceId())) {
				pageProcessInstance.setProcessInstanceId(idGenerator.getNextId("PAGE_PROCESSINSTANCE"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			pageProcessInstanceMapper.bulkInsertPageProcessInstance_oracle(list);
		} else {
			pageProcessInstanceMapper.bulkInsertPageProcessInstance(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			pageProcessInstanceMapper.deletePageProcessInstanceById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> processInstanceIds) {
		if (processInstanceIds != null && !processInstanceIds.isEmpty()) {
			for (String id : processInstanceIds) {
				pageProcessInstanceMapper.deletePageProcessInstanceById(id);
			}
		}
	}

	public int count(PageProcessInstanceQuery query) {
		return pageProcessInstanceMapper.getPageProcessInstanceCount(query);
	}

	public List<PageProcessInstance> list(PageProcessInstanceQuery query) {
		List<PageProcessInstance> list = pageProcessInstanceMapper.getPageProcessInstances(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getPageProcessInstanceCountByQueryCriteria(PageProcessInstanceQuery query) {
		return pageProcessInstanceMapper.getPageProcessInstanceCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<PageProcessInstance> getPageProcessInstancesByQueryCriteria(int start, int pageSize,
			PageProcessInstanceQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<PageProcessInstance> rows = sqlSessionTemplate.selectList("getPageProcessInstances", query, rowBounds);
		return rows;
	}

	public PageProcessInstance getPageProcessInstance(String id) {
		if (id == null) {
			return null;
		}
		PageProcessInstance pageProcessInstance = pageProcessInstanceMapper.getPageProcessInstanceById(id);
		return pageProcessInstance;
	}

	@Transactional
	public void save(PageProcessInstance pageProcessInstance) {
		PageProcessInstance model = this.getPageProcessInstance(pageProcessInstance.getProcessInstanceId());
		if (model == null) {
			pageProcessInstance.setCreateTime(new Date());
			pageProcessInstance.setDeleteFlag(0);
			pageProcessInstanceMapper.insertPageProcessInstance(pageProcessInstance);
		} else {
			pageProcessInstanceMapper.updatePageProcessInstance(pageProcessInstance);
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
	public void setPageProcessInstanceMapper(PageProcessInstanceMapper pageProcessInstanceMapper) {
		this.pageProcessInstanceMapper = pageProcessInstanceMapper;
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
