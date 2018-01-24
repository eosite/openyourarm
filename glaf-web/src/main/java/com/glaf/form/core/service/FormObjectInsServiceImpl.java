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

@Service("com.glaf.form.core.service.formObjectInsService")
@Transactional(readOnly = true) 
public class FormObjectInsServiceImpl implements FormObjectInsService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormObjectInsMapper formObjectInsMapper;

	public FormObjectInsServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormObjectIns> list) {
		for (FormObjectIns formObjectIns : list) {
		   if (StringUtils.isEmpty(formObjectIns.getId())) {
			formObjectIns.setId(idGenerator.getNextId("FORM_OBJECT_INS"));
		   }
		}
		
		int batch_size = 100;
                List<FormObjectIns> rows = new ArrayList<FormObjectIns>(batch_size);

		for (FormObjectIns bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//	formObjectInsMapper.bulkInsertFormObjectIns_oracle(list);
				} else {
				//	formObjectInsMapper.bulkInsertFormObjectIns(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//	formObjectInsMapper.bulkInsertFormObjectIns_oracle(list);
			} else {
			//	formObjectInsMapper.bulkInsertFormObjectIns(list);
			}
			rows.clear();
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formObjectInsMapper.deleteFormObjectInsById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formObjectInsMapper.deleteFormObjectInsById(id);
		}
	    }
	}

	public int count(FormObjectInsQuery query) {
		return formObjectInsMapper.getFormObjectInsCount(query);
	}

	public List<FormObjectIns> list(FormObjectInsQuery query) {
		List<FormObjectIns> list = formObjectInsMapper.getFormObjectInss(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormObjectInsCountByQueryCriteria(FormObjectInsQuery query) {
		return formObjectInsMapper.getFormObjectInsCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormObjectIns> getFormObjectInssByQueryCriteria(int start, int pageSize,
			FormObjectInsQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormObjectIns> rows = sqlSessionTemplate.selectList(
				"getFormObjectInss", query, rowBounds);
		return rows;
	}


	public FormObjectIns getFormObjectIns(String id) {
	        if(id == null){
		    return null;
		}
		FormObjectIns formObjectIns = formObjectInsMapper.getFormObjectInsById(id);
		return formObjectIns;
	}

	@Transactional
	public void save(FormObjectIns formObjectIns) {
           if (StringUtils.isEmpty(formObjectIns.getId())) {
	        formObjectIns.setId(idGenerator.getNextId("FORM_OBJECT_INS"));
		//formObjectIns.setCreateDate(new Date());
		//formObjectIns.setDeleteFlag(0);
		formObjectInsMapper.insertFormObjectIns(formObjectIns);
	       } else {
		formObjectInsMapper.updateFormObjectIns(formObjectIns);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormObjectInsMapper")
	public void setFormObjectInsMapper(FormObjectInsMapper formObjectInsMapper) {
		this.formObjectInsMapper = formObjectInsMapper;
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
