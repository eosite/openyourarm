package com.glaf.workflow.core.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.util.UUID32;
import com.glaf.workflow.core.domain.PageUserTask;
import com.glaf.workflow.core.mapper.PageUserTaskMapper;
import com.glaf.workflow.core.query.PageUserTaskQuery;
import com.glaf.workflow.core.service.IPageUserTaskService;

@Service("pageUserTaskService")
@Transactional(readOnly = true)
public class PageUserTaskServiceImpl implements IPageUserTaskService {
	protected final Logger logger = LoggerFactory.getLogger(PageUserTaskServiceImpl.class);
	
	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;
	
	protected PageUserTaskMapper pageUserTaskMapper;
	
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
	public void setPageUserTaskMapper(PageUserTaskMapper pageUserTaskMapper) {
		this.pageUserTaskMapper = pageUserTaskMapper;
	}

	@Override
	@Transactional
	public void deleteById(String id) {
		this.pageUserTaskMapper.deletePageUserTaskById(id);
	}

	@Override
	@Transactional
	public void deleteByIds(List<String> rowIds) {
		PageUserTaskQuery query = new PageUserTaskQuery();
		query.setRowIds(rowIds);
		this.pageUserTaskMapper.deletePageUserTasks(query);
	}

	@Override
	@Transactional
	public void save(PageUserTask pageUserTask) {
		if(pageUserTask.getId()==null){
			pageUserTask.setId(UUID32.getUUID());
			this.pageUserTaskMapper.insertPageUserTask(pageUserTask);
		}else{
			this.pageUserTaskMapper.updatePageUserTask(pageUserTask);
		}
	}

	@Override
	public List<PageUserTask> list(PageUserTaskQuery query) {
		return this.pageUserTaskMapper.getPageUserTasks(query);
	}

	@Override
	public int getPageUserTaskCountByQueryCriteria(PageUserTaskQuery query) {
		return this.pageUserTaskMapper.getPageUserTaskCount(query);
	}

	@Override
	public List<PageUserTask> getPageUserTasksByQueryCriteria(int start, int pageSize, PageUserTaskQuery query) {
		RowBounds rowBounds = new RowBounds(start,pageSize);
		List<PageUserTask> list = this.sqlSessionTemplate.selectList("getPageUserTasks", query, rowBounds);
		return list;
	}

	@Override
	public PageUserTask getPageUserTaskById(String id) {
		return this.pageUserTaskMapper.getPageUserTaskById(id);
	}

}
