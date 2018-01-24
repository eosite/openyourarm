package com.glaf.form.core.service;


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

import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Service("com.glaf.form.core.service.formNodeMessageHistoryService")
@Transactional(readOnly = true) 
public class FormNodeMessageHistoryServiceImpl implements FormNodeMessageHistoryService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormNodeMessageHistoryMapper formNodeMessageHistoryMapper;

	public FormNodeMessageHistoryServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormNodeMessageHistory> list) {
		for (FormNodeMessageHistory formNodeMessageHistory : list) {
		    if ( formNodeMessageHistory.getId()  == null) {
			formNodeMessageHistory.setId(idGenerator.nextId("FORM_NODE_MESSAGE_HISTORY"));
		    }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			formNodeMessageHistoryMapper.bulkInsertFormNodeMessageHistory_oracle(list);
		} else {
//			formNodeMessageHistoryMapper.bulkInsertFormNodeMessageHistory(list);
		}
	}


	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		formNodeMessageHistoryMapper.deleteFormNodeMessageHistoryById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    formNodeMessageHistoryMapper.deleteFormNodeMessageHistoryById(id);
		}
	    }
	}

	public int count(FormNodeMessageHistoryQuery query) {
		return formNodeMessageHistoryMapper.getFormNodeMessageHistoryCount(query);
	}

	public List<FormNodeMessageHistory> list(FormNodeMessageHistoryQuery query) {
		List<FormNodeMessageHistory> list = formNodeMessageHistoryMapper.getFormNodeMessageHistorys(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormNodeMessageHistoryCountByQueryCriteria(FormNodeMessageHistoryQuery query) {
		return formNodeMessageHistoryMapper.getFormNodeMessageHistoryCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormNodeMessageHistory> getFormNodeMessageHistorysByQueryCriteria(int start, int pageSize,
			FormNodeMessageHistoryQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormNodeMessageHistory> rows = sqlSessionTemplate.selectList(
				"getFormNodeMessageHistorys", query, rowBounds);
		return rows;
	}


	public FormNodeMessageHistory getFormNodeMessageHistory(Long id) {
	        if(id == null){
		    return null;
		}
		FormNodeMessageHistory formNodeMessageHistory = formNodeMessageHistoryMapper.getFormNodeMessageHistoryById(id);
		return formNodeMessageHistory;
	}

	@Transactional
	public void save(FormNodeMessageHistory formNodeMessageHistory) {
            if ( formNodeMessageHistory.getId()  == null) {
	        formNodeMessageHistory.setId(idGenerator.nextId("FORM_NODE_MESSAGE_HISTORY"));
		//formNodeMessageHistory.setCreateDate(new Date());
		//formNodeMessageHistory.setDeleteFlag(0);
		formNodeMessageHistoryMapper.insertFormNodeMessageHistory(formNodeMessageHistory);
	       } else {
		formNodeMessageHistoryMapper.updateFormNodeMessageHistory(formNodeMessageHistory);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormNodeMessageHistoryMapper")
	public void setFormNodeMessageHistoryMapper(FormNodeMessageHistoryMapper formNodeMessageHistoryMapper) {
		this.formNodeMessageHistoryMapper = formNodeMessageHistoryMapper;
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
