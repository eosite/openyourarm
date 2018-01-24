package com.glaf.form.core.service.impl;


import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.UUID32;
import com.glaf.form.core.domain.FormParams;
import com.glaf.form.core.mapper.FormParamsMapper;
import com.glaf.form.core.query.FormParamsQuery;
import com.glaf.form.core.service.FormParamsService;

@Service("com.glaf.form.core.service.formParamsService")
@Transactional(readOnly = true) 
public class FormParamsServiceImpl implements FormParamsService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormParamsMapper formParamsMapper;

	public FormParamsServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormParams> list) {
		for (FormParams formParams : list) {
		   if (StringUtils.isEmpty(formParams.getId())) {
			formParams.setId(idGenerator.getNextId("FORM_PARAMS"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//formParamsMapper.bulkInsertFormParams_oracle(list);
		} else {
			//formParamsMapper.bulkInsertFormParams(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formParamsMapper.deleteFormParamsById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formParamsMapper.deleteFormParamsById(id);
		}
	    }
	}

	public int count(FormParamsQuery query) {
		return formParamsMapper.getFormParamsCount(query);
	}

	public List<FormParams> list(FormParamsQuery query) {
		List<FormParams> list = formParamsMapper.getFormParamss(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormParamsCountByQueryCriteria(FormParamsQuery query) {
		return formParamsMapper.getFormParamsCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormParams> getFormParamssByQueryCriteria(int start, int pageSize,
			FormParamsQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormParams> rows = sqlSessionTemplate.selectList(
				"getFormParamss", query, rowBounds);
		return rows;
	}


	public FormParams getFormParams(String id) {
	        if(id == null){
		    return null;
		}
		FormParams formParams = formParamsMapper.getFormParamsById(id);
		return formParams;
	}

	@Transactional
	public void save(FormParams formParams) {
           if (StringUtils.isEmpty(formParams.getId())) {
	        formParams.setId(idGenerator.getNextId("FORM_PARAMS"));
		//formParams.setCreateDate(new Date());
		//formParams.setDeleteFlag(0);
		formParamsMapper.insertFormParams(formParams);
	       } else {
		formParamsMapper.updateFormParams(formParams);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormParamsMapper")
	public void setFormParamsMapper(FormParamsMapper formParamsMapper) {
		this.formParamsMapper = formParamsMapper;
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
	@Transactional
	public void saveOrUpdate(FormParams formParams) {
		FormParamsQuery query = new FormParamsQuery();
		query.setParamName(formParams.getParamName());
		query.setWidgetId(formParams.getWidgetId());
		int count = formParamsMapper.getFormParamsCount(query);
		if(count>0){
			//formParamsMapper.updateFormParamsByParam(formParams);
		}else{
			formParams.setId(UUID32.getUUID());
			formParamsMapper.insertFormParams(formParams);
		}
		
	}


	@Override
	public void deleteByParams(List<String> params, String id) {
		formParamsMapper.deleteByParams(params, id);
	}

}
