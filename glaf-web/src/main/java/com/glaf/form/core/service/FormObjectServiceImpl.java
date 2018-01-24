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

@Service("com.glaf.form.core.service.formObjectService")
@Transactional(readOnly = true) 
public class FormObjectServiceImpl implements FormObjectService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormObjectMapper formObjectMapper;

	public FormObjectServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormObject> list) {
		for (FormObject formObject : list) {
		   if (StringUtils.isEmpty(formObject.getId())) {
			formObject.setId(idGenerator.getNextId("FORM_OBJECT"));
		   }
		}
		
		int batch_size = 100;
                List<FormObject> rows = new ArrayList<FormObject>(batch_size);

		for (FormObject bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//		formObjectMapper.bulkInsertFormObject_oracle(list);
				} else {
			//		formObjectMapper.bulkInsertFormObject(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//	formObjectMapper.bulkInsertFormObject_oracle(list);
			} else {
			//	formObjectMapper.bulkInsertFormObject(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formObjectMapper.deleteFormObjectById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formObjectMapper.deleteFormObjectById(id);
		}
	    }
	}

	public int count(FormObjectQuery query) {
		return formObjectMapper.getFormObjectCount(query);
	}

	public List<FormObject> list(FormObjectQuery query) {
		List<FormObject> list = formObjectMapper.getFormObjects(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormObjectCountByQueryCriteria(FormObjectQuery query) {
		return formObjectMapper.getFormObjectCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormObject> getFormObjectsByQueryCriteria(int start, int pageSize,
			FormObjectQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormObject> rows = sqlSessionTemplate.selectList(
				"getFormObjects", query, rowBounds);
		return rows;
	}


	public FormObject getFormObject(String id) {
	        if(id == null){
		    return null;
		}
		FormObject formObject = formObjectMapper.getFormObjectById(id);
		return formObject;
	}

	@Transactional
	public void save(FormObject formObject) {
           if (StringUtils.isEmpty(formObject.getId())) {
	        formObject.setId(idGenerator.getNextId("FORM_OBJECT"));
		//formObject.setCreateDate(new Date());
		//formObject.setDeleteFlag(0);
		formObjectMapper.insertFormObject(formObject);
	       } else {
		formObjectMapper.updateFormObject(formObject);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormObjectMapper")
	public void setFormObjectMapper(FormObjectMapper formObjectMapper) {
		this.formObjectMapper = formObjectMapper;
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
