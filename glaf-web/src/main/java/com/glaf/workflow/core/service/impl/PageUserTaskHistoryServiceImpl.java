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
import com.glaf.workflow.core.domain.PageUserTaskHistory;
import com.glaf.workflow.core.mapper.PageUserTaskHistoryMapper;
import com.glaf.workflow.core.query.PageUserTaskHistoryQuery;
import com.glaf.workflow.core.service.IPageUserTaskHistoryService;

@Service("pageUserTaskHistoryService")
@Transactional(readOnly = true)
public class PageUserTaskHistoryServiceImpl implements IPageUserTaskHistoryService {
	protected final Logger logger = LoggerFactory.getLogger(PageUserTaskHistoryServiceImpl.class);
	
	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;
	
	protected PageUserTaskHistoryMapper pageUserTaskHistoryMapper;
	
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
	public void setPageUserTaskHistoryMapper(PageUserTaskHistoryMapper pageUserTaskHistoryMapper) {
		this.pageUserTaskHistoryMapper = pageUserTaskHistoryMapper;
	}


	@Override
	@Transactional
	public void deleteById(String id) {
		this.pageUserTaskHistoryMapper.deletePageUserTaskHistoryById(id);
	}


	@Override
	@Transactional
	public void deleteByIds(List<String> rowIds) {
		PageUserTaskHistoryQuery query = new PageUserTaskHistoryQuery();
		query.setRowIds(rowIds);
		this.pageUserTaskHistoryMapper.deletePageUserTaskHistorys(query);
	}

	@Override
	@Transactional
	public void save(PageUserTaskHistory pageUserTask) {
		if(pageUserTask.getId()==null){
			pageUserTask.setId(UUID32.getUUID());
			this.pageUserTaskHistoryMapper.insertPageUserTaskHistory(pageUserTask);
		}else{
			this.pageUserTaskHistoryMapper.updatePageUserTaskHistory(pageUserTask);
		}
	}

	@Override
	public List<PageUserTaskHistory> list(PageUserTaskHistoryQuery query) {
		return this.pageUserTaskHistoryMapper.getPageUserTaskHistorys(query);
	}

	@Override
	public int getPageUserTaskHistoryCountByQueryCriteria(PageUserTaskHistoryQuery query) {
		return this.pageUserTaskHistoryMapper.getPageUserTaskHistoryCount(query);
	}

	@Override
	public List<PageUserTaskHistory> getPageUserTaskHistorysByQueryCriteria(int start, int pageSize, PageUserTaskHistoryQuery query) {
		RowBounds rowBounds = new RowBounds(start,pageSize);
		List<PageUserTaskHistory> list = this.sqlSessionTemplate.selectList("getPageUserTaskHistorys", query, rowBounds);
		return list;
	}

	@Override
	public PageUserTaskHistory getPageUserTaskHistoryById(String id) {
		return this.pageUserTaskHistoryMapper.getPageUserTaskHistoryById(id);
	}

}
