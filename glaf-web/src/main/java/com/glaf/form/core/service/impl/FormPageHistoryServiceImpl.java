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
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.dao.*;
import com.glaf.core.util.DBUtils;

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormPageHistoryService;

@Service("formPageHistoryService")
@Transactional(readOnly = true)
public class FormPageHistoryServiceImpl implements FormPageHistoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormPageHistoryMapper formPageHistoryMapper;

	public FormPageHistoryServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<FormPageHistory> list) {
		for (FormPageHistory formPageHistory : list) {
			if (StringUtils.isEmpty(formPageHistory.getId())) {
				formPageHistory.setId(idGenerator.getNextId("FORM_PAGE_HISTORY"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			formPageHistoryMapper.bulkInsertFormPageHistory_oracle(list);
		} else {
			formPageHistoryMapper.bulkInsertFormPageHistory(list);
		}
	}

	public int count(FormPageHistoryQuery query) {
		return formPageHistoryMapper.getFormPageHistoryCount(query);
	}

	public FormPageHistory getFormPageHistory(String id) {
		if (id == null) {
			return null;
		}
		FormPageHistory formPageHistory = formPageHistoryMapper.getFormPageHistoryById(id);
		return formPageHistory;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormPageHistoryCountByQueryCriteria(FormPageHistoryQuery query) {
		return formPageHistoryMapper.getFormPageHistoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormPageHistory> getFormPageHistorysByQueryCriteria(int start, int pageSize,
			FormPageHistoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormPageHistory> rows = sqlSessionTemplate.selectList("getFormPageHistorys", query, rowBounds);
		return rows;
	}

	public List<FormPageHistory> list(FormPageHistoryQuery query) {
		List<FormPageHistory> list = formPageHistoryMapper.getFormPageHistorys(query);
		return list;
	}

	@Transactional
	public void save(FormPageHistory formPageHistory) {
		Integer version = formPageHistoryMapper.getMaxVersionFormPageHistory(formPageHistory.getPageId());
		if (version == null) {
			version = 0;
		}
		version = version + 1;
		formPageHistory.setVersion(version);
		formPageHistory.setId(formPageHistory.getPageId() + "_" + version);
		formPageHistory.setCreateDate(new Date());
		formPageHistoryMapper.insertFormPageHistory(formPageHistory);
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setFormPageHistoryMapper(FormPageHistoryMapper formPageHistoryMapper) {
		this.formPageHistoryMapper = formPageHistoryMapper;
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

	@Override
	public void deleteOldVersion(String pageId, int num) {
		formPageHistoryMapper.deleteOldVersion(pageId,num);
		
	}

}
