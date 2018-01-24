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

@Service("com.glaf.form.core.service.formOutexpRelationService")
@Transactional(readOnly = true) 
public class FormOutexpRelationServiceImpl implements FormOutexpRelationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormOutexpRelationMapper formOutexpRelationMapper;

	public FormOutexpRelationServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormOutexpRelation> list) {
		for (FormOutexpRelation formOutexpRelation : list) {
		   if (StringUtils.isEmpty(formOutexpRelation.getId())) {
			formOutexpRelation.setId(idGenerator.getNextId("FORM_OUTEXP_RELATION"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			formOutexpRelationMapper.bulkInsertFormOutexpRelation_oracle(list);
		} else {
//			formOutexpRelationMapper.bulkInsertFormOutexpRelation(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formOutexpRelationMapper.deleteFormOutexpRelationById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formOutexpRelationMapper.deleteFormOutexpRelationById(id);
		}
	    }
	}

	public int count(FormOutexpRelationQuery query) {
		return formOutexpRelationMapper.getFormOutexpRelationCount(query);
	}

	public List<FormOutexpRelation> list(FormOutexpRelationQuery query) {
		List<FormOutexpRelation> list = formOutexpRelationMapper.getFormOutexpRelations(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormOutexpRelationCountByQueryCriteria(FormOutexpRelationQuery query) {
		return formOutexpRelationMapper.getFormOutexpRelationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormOutexpRelation> getFormOutexpRelationsByQueryCriteria(int start, int pageSize,
			FormOutexpRelationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormOutexpRelation> rows = sqlSessionTemplate.selectList(
				"getFormOutexpRelations", query, rowBounds);
		return rows;
	}


	public FormOutexpRelation getFormOutexpRelation(String id) {
	        if(id == null){
		    return null;
		}
		FormOutexpRelation formOutexpRelation = formOutexpRelationMapper.getFormOutexpRelationById(id);
		return formOutexpRelation;
	}

	@Transactional
	public void save(FormOutexpRelation formOutexpRelation) {
           if (StringUtils.isEmpty(formOutexpRelation.getId())) {
	        formOutexpRelation.setId(idGenerator.getNextId("FORM_OUTEXP_RELATION"));
		//formOutexpRelation.setCreateDate(new Date());
		//formOutexpRelation.setDeleteFlag(0);
		formOutexpRelationMapper.insertFormOutexpRelation(formOutexpRelation);
	       } else {
		formOutexpRelationMapper.updateFormOutexpRelation(formOutexpRelation);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormOutexpRelationMapper")
	public void setFormOutexpRelationMapper(FormOutexpRelationMapper formOutexpRelationMapper) {
		this.formOutexpRelationMapper = formOutexpRelationMapper;
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
		public void delete(FormOutexpRelationQuery delete2Query) {
			// TODO Auto-generated method stub
			formOutexpRelationMapper.delete(delete2Query);
		}

}
