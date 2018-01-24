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
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;
import com.glaf.form.core.mapper.*;
import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.core.service.FormDatasetService;

@Service("com.glaf.form.core.service.formDatasetService")
@Transactional(readOnly = true) 
public class FormDatasetServiceImpl implements FormDatasetService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormDatasetMapper formDatasetMapper;

	public FormDatasetServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<FormDataset> list) {
		for (FormDataset formDataset : list) {
		   if (StringUtils.isEmpty(formDataset.getId())) {
			formDataset.setId(idGenerator.getNextId("FORM_DATASET"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			//formDatasetMapper.bulkInsertFormDataset_oracle(list);
		} else {
			//formDatasetMapper.bulkInsertFormDataset(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		formDatasetMapper.deleteFormDatasetById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    formDatasetMapper.deleteFormDatasetById(id);
		}
	    }
	}

	public int count(FormDatasetQuery query) {
		return formDatasetMapper.getFormDatasetCount(query);
	}

	public List<FormDataset> list(FormDatasetQuery query) {
		List<FormDataset> list = formDatasetMapper.getFormDatasets(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getFormDatasetCountByQueryCriteria(FormDatasetQuery query) {
		return formDatasetMapper.getFormDatasetCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormDataset> getFormDatasetsByQueryCriteria(int start, int pageSize,
			FormDatasetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormDataset> rows = sqlSessionTemplate.selectList(
				"getFormDatasets", query, rowBounds);
		return rows;
	}


	public FormDataset getFormDataset(String id) {
	        if(id == null){
		    return null;
		}
		FormDataset formDataset = formDatasetMapper.getFormDatasetById(id);
		return formDataset;
	}

	@Transactional
	public void save(FormDataset formDataset) {
           if (StringUtils.isEmpty(formDataset.getId())) {
	        formDataset.setId(idGenerator.getNextId("FORM_DATASET"));
		//formDataset.setCreateDate(new Date());
		//formDataset.setDeleteFlag(0);
		formDatasetMapper.insertFormDataset(formDataset);
	       } else {
		formDatasetMapper.updateFormDataset(formDataset);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormDatasetMapper")
	public void setFormDatasetMapper(FormDatasetMapper formDatasetMapper) {
		this.formDatasetMapper = formDatasetMapper;
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
	public void saveOrUpdate(FormDataset formDataset) {
		FormDatasetQuery query = new FormDatasetQuery();
		query.setColumnName(formDataset.getColumnName());
		query.setWidgetId(formDataset.getWidgetId());
		int count = formDatasetMapper.getFormDatasetCount(query);
		if(count>0){
			List<FormDataset> list = formDatasetMapper.getFormDatasets(query);
			formDataset.setId(list.get(0).getId());
			//formDatasetMapper.updateFormParamsByParam(formParams);
		}else{
			formDataset.setId(UUID32.getUUID());
			formDatasetMapper.insertFormDataset(formDataset);
		}
	}


	@Override
	public void deleteByColumns(List<String> params, String ruleId) {
		formDatasetMapper.deleteByColumns(params,ruleId);
	}

}
