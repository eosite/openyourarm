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
import com.glaf.form.core.service.FormDatasetRelationService;

@Service("com.glaf.form.core.service.formDatasetRelationService")
@Transactional(readOnly = true)
public class FormDatasetRelationServiceImpl implements FormDatasetRelationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormDatasetRelationMapper formDatasetRelationMapper;

	protected FormDatasetMapper formDatasetMapper;

	public FormDatasetRelationServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<FormDatasetRelation> list) {
		for (FormDatasetRelation formDatasetRelation : list) {
			if (StringUtils.isEmpty(formDatasetRelation.getId())) {
				formDatasetRelation.setId(idGenerator.getNextId("FORM_DATASET_RELATION"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// formDatasetRelationMapper.bulkInsertFormDatasetRelation_oracle(list);
		} else {
			// formDatasetRelationMapper.bulkInsertFormDatasetRelation(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formDatasetRelationMapper.deleteFormDatasetRelationById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formDatasetRelationMapper.deleteFormDatasetRelationById(id);
			}
		}
	}

	public int count(FormDatasetRelationQuery query) {
		return formDatasetRelationMapper.getFormDatasetRelationCount(query);
	}

	public List<FormDatasetRelation> list(FormDatasetRelationQuery query) {
		List<FormDatasetRelation> list = formDatasetRelationMapper.getFormDatasetRelations(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormDatasetRelationCountByQueryCriteria(FormDatasetRelationQuery query) {
		return formDatasetRelationMapper.getFormDatasetRelationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormDatasetRelation> getFormDatasetRelationsByQueryCriteria(int start, int pageSize,
			FormDatasetRelationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormDatasetRelation> rows = sqlSessionTemplate.selectList("getFormDatasetRelations", query, rowBounds);
		return rows;
	}

	public FormDatasetRelation getFormDatasetRelation(String id) {
		if (id == null) {
			return null;
		}
		FormDatasetRelation formDatasetRelation = formDatasetRelationMapper.getFormDatasetRelationById(id);
		return formDatasetRelation;
	}

	@Transactional
	public void save(FormDatasetRelation formDatasetRelation) {
		if (StringUtils.isEmpty(formDatasetRelation.getId())) {
			formDatasetRelation.setId(idGenerator.getNextId("FORM_DATASET_RELATION"));
			// formDatasetRelation.setCreateDate(new Date());
			// formDatasetRelation.setDeleteFlag(0);
			formDatasetRelationMapper.insertFormDatasetRelation(formDatasetRelation);
		} else {
			formDatasetRelationMapper.updateFormDatasetRelation(formDatasetRelation);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormDatasetRelationMapper")
	public void setFormDatasetRelationMapper(FormDatasetRelationMapper formDatasetRelationMapper) {
		this.formDatasetRelationMapper = formDatasetRelationMapper;
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
	public void setFormDatasetMapper(FormDatasetMapper formDatasetMapper) {
		this.formDatasetMapper = formDatasetMapper;
	}

	@Transactional
	public void saveOrUpdate(FormDatasetRelation formDatasetRelation) {
		FormDatasetRelationQuery query = new FormDatasetRelationQuery();
		query.setPid(formDatasetRelation.getPid());
		query.setWidgetId(formDatasetRelation.getWidgetId());
		query.setPageId(formDatasetRelation.getPageId());
		query.setAttrName(formDatasetRelation.getAttrName());

		int count = formDatasetRelationMapper.getFormDatasetRelationCount(query);
		if (count > 0) {
			//
		} else {
			formDatasetRelation.setId(UUID32.getUUID());
			formDatasetRelationMapper.insertFormDatasetRelation(formDatasetRelation);
		}

	}

	@Transactional
	public void deleteByColumns(List<String> pids, String ruleId, String attrName) {
		formDatasetRelationMapper.deleteByColumns(pids, ruleId, attrName);
	}

	@Override
	@Transactional
	public void saveByParam(FormDatasetRelation formDatasetRelation, String datasetId, String pageId, String ruleId,
			String inId, String columnName) {
		List<FormDataset> list = null;
		
		try {
			if(StringUtils.isNotBlank(columnName) && StringUtils.isNotBlank(pageId)&& StringUtils.isNotBlank(inId))
			list = formDatasetMapper.queryByParam(datasetId, pageId, ruleId, inId, columnName);
		} catch(Exception ex){
			logger.error(ex.getMessage());
			list = null;
		}
		if (list != null && list.size() == 1) {

			FormDataset formDataset = list.get(0);
			/*
			 * FormDatasetRelationQuery query = new FormDatasetRelationQuery();
			 * query.setPid(formDataset.getId());
			 * query.setWidgetId(formDatasetRelation.getWidgetId());
			 * query.setPageId(formDatasetRelation.getPageId());
			 * query.setAttrName(formDatasetRelation.getAttrName());
			 * 
			 * int count =
			 * formDatasetRelationMapper.getFormDatasetRelationCount(query); if
			 * (count > 0) { // } else {
			 */
			formDatasetRelation.setPid(formDataset.getId());
			formDatasetRelation.setId(UUID32.getUUID());
			formDatasetRelationMapper.insertFormDatasetRelation(formDatasetRelation);
			// }

		}

	}

	@Override
	@Transactional
	public void delete(FormDatasetRelationQuery deleteQuery) {
		formDatasetRelationMapper.delete(deleteQuery);
	}

}
