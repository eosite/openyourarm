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

@Service("com.glaf.form.core.service.formTaskService")
@Transactional(readOnly = true) 
public class FormTaskServiceImpl implements FormTaskService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormTaskMapper formTaskMapper;

	public FormTaskServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormTask> list) {
		for (FormTask formTask : list) {
		    if ( formTask.getId()  == null) {
			formTask.setId(idGenerator.nextId("FORM_TASK"));
		    }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
		//	formTaskMapper.bulkInsertFormTask_oracle(list);
		} else {
	//		formTaskMapper.bulkInsertFormTask(list);
		}
	}


	@Transactional
	public void deleteById(Long id) {
	     if(id != null ){
		formTaskMapper.deleteFormTaskById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<Long> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(Long id : ids){
		    formTaskMapper.deleteFormTaskById(id);
		}
	    }
	}

	public int count(FormTaskQuery query) {
		return formTaskMapper.getFormTaskCount(query);
	}

	public List<FormTask> list(FormTaskQuery query) {
		List<FormTask> list = formTaskMapper.getFormTasks(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormTaskCountByQueryCriteria(FormTaskQuery query) {
		return formTaskMapper.getFormTaskCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormTask> getFormTasksByQueryCriteria(int start, int pageSize,
			FormTaskQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormTask> rows = sqlSessionTemplate.selectList(
				"getFormTasks", query, rowBounds);
		return rows;
	}


	public FormTask getFormTask(Long id) {
	        if(id == null){
		    return null;
		}
		FormTask formTask = formTaskMapper.getFormTaskById(id);
		return formTask;
	}

	@Transactional
	public void save(FormTask formTask) {
            if ( formTask.getId()  == null) {
	        formTask.setId(idGenerator.nextId("FORM_TASK"));
		//formTask.setCreateDate(new Date());
		//formTask.setDeleteFlag(0);
		formTaskMapper.insertFormTask(formTask);
	       } else {
		formTaskMapper.updateFormTask(formTask);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormTaskMapper")
	public void setFormTaskMapper(FormTaskMapper formTaskMapper) {
		this.formTaskMapper = formTaskMapper;
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
