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

@Service("com.glaf.form.core.service.formNodeMessageService")
@Transactional(readOnly = true) 
public class FormNodeMessageServiceImpl implements FormNodeMessageService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormNodeMessageMapper formNodeMessageMapper;

	public FormNodeMessageServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormNodeMessage> list) {
		for (FormNodeMessage formNodeMessage : list) {
		    if ( formNodeMessage.getId()  == null) {
			formNodeMessage.setId(idGenerator.nextId("FORM_NODE_MESSAGE"));
		    }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			formNodeMessageMapper.bulkInsertFormNodeMessage_oracle(list);
		} else {
//			formNodeMessageMapper.bulkInsertFormNodeMessage(list);
		}
	}


	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		formNodeMessageMapper.deleteFormNodeMessageById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    formNodeMessageMapper.deleteFormNodeMessageById(id);
		}
	    }
	}

	public int count(FormNodeMessageQuery query) {
		return formNodeMessageMapper.getFormNodeMessageCount(query);
	}

	public List<FormNodeMessage> list(FormNodeMessageQuery query) {
		List<FormNodeMessage> list = formNodeMessageMapper.getFormNodeMessages(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormNodeMessageCountByQueryCriteria(FormNodeMessageQuery query) {
		return formNodeMessageMapper.getFormNodeMessageCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormNodeMessage> getFormNodeMessagesByQueryCriteria(int start, int pageSize,
			FormNodeMessageQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormNodeMessage> rows = sqlSessionTemplate.selectList(
				"getFormNodeMessages", query, rowBounds);
		return rows;
	}


	public FormNodeMessage getFormNodeMessage(Long id) {
	        if(id == null){
		    return null;
		}
		FormNodeMessage formNodeMessage = formNodeMessageMapper.getFormNodeMessageById(id);
		return formNodeMessage;
	}

	@Transactional
	public void save(FormNodeMessage formNodeMessage) {
            if ( formNodeMessage.getId()  == null) {
	        formNodeMessage.setId(idGenerator.nextId("FORM_NODE_MESSAGE"));
		//formNodeMessage.setCreateDate(new Date());
		//formNodeMessage.setDeleteFlag(0);
		formNodeMessageMapper.insertFormNodeMessage(formNodeMessage);
	       } else {
		formNodeMessageMapper.updateFormNodeMessage(formNodeMessage);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormNodeMessageMapper")
	public void setFormNodeMessageMapper(FormNodeMessageMapper formNodeMessageMapper) {
		this.formNodeMessageMapper = formNodeMessageMapper;
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
