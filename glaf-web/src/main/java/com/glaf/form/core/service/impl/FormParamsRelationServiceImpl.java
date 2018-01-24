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
import com.glaf.form.core.service.FormParamsRelationService;

@Service("com.glaf.form.core.service.formParamsRelationService")
@Transactional(readOnly = true)
public class FormParamsRelationServiceImpl implements FormParamsRelationService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormParamsRelationMapper formParamsRelationMapper;

	protected FormParamsMapper formParamsMapper;

	public FormParamsRelationServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<FormParamsRelation> list) {
		for (FormParamsRelation formParamsRelation : list) {
			if (StringUtils.isEmpty(formParamsRelation.getId())) {
				formParamsRelation.setId(idGenerator.getNextId("FORM_PARAMS_RELATION"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			// formParamsRelationMapper.bulkInsertFormParamsRelation_oracle(list);
		} else {
			// formParamsRelationMapper.bulkInsertFormParamsRelation(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formParamsRelationMapper.deleteFormParamsRelationById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formParamsRelationMapper.deleteFormParamsRelationById(id);
			}
		}
	}

	public int count(FormParamsRelationQuery query) {
		return formParamsRelationMapper.getFormParamsRelationCount(query);
	}

	public List<FormParamsRelation> list(FormParamsRelationQuery query) {
		List<FormParamsRelation> list = formParamsRelationMapper.getFormParamsRelations(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormParamsRelationCountByQueryCriteria(FormParamsRelationQuery query) {
		return formParamsRelationMapper.getFormParamsRelationCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormParamsRelation> getFormParamsRelationsByQueryCriteria(int start, int pageSize, FormParamsRelationQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormParamsRelation> rows = sqlSessionTemplate.selectList("getFormParamsRelations", query, rowBounds);
		return rows;
	}

	public FormParamsRelation getFormParamsRelation(String id) {
		if (id == null) {
			return null;
		}
		FormParamsRelation formParamsRelation = formParamsRelationMapper.getFormParamsRelationById(id);
		return formParamsRelation;
	}

	@Transactional
	public void save(FormParamsRelation formParamsRelation) {
		if (StringUtils.isEmpty(formParamsRelation.getId())) {
			formParamsRelation.setId(idGenerator.getNextId("FORM_PARAMS_RELATION"));
			// formParamsRelation.setCreateDate(new Date());
			// formParamsRelation.setDeleteFlag(0);
			formParamsRelationMapper.insertFormParamsRelation(formParamsRelation);
		} else {
			formParamsRelationMapper.updateFormParamsRelation(formParamsRelation);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormParamsRelationMapper")
	public void setFormParamsRelationMapper(FormParamsRelationMapper formParamsRelationMapper) {
		this.formParamsRelationMapper = formParamsRelationMapper;
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
	public void setFormParamsMapper(FormParamsMapper formParamsMapper) {
		this.formParamsMapper = formParamsMapper;
	}

	@Override
	@Transactional
	public void saveByParam(FormParamsRelation formParamsRelation, String odatasetId, String outPage, String outid, String param, String ruleId) {
		List<FormParams> list = null;
		if (ruleId == null || ruleId.equals("")) {
			try {
				list = formParamsMapper.queryByParam(odatasetId, outPage, outid, param);
			} catch (Exception e) {
				logger.error(e.getMessage());
				list = null;
			}

		} else {
			if (param != null) {
				list = formParamsMapper.queryByParamWithRuleId(odatasetId, outPage, ruleId, param);
			}
		}

		if (list != null && list.size() == 1) {
			FormParams formParams = list.get(0);
			formParamsRelation.setPid(formParams.getId());
			formParamsRelation.setId(UUID32.getUUID());
			formParamsRelationMapper.insertFormParamsRelation(formParamsRelation);
		}

	}

	@Override
	@Transactional
	public void delete(FormParamsRelationQuery delete2Query) {
		formParamsRelationMapper.delete(delete2Query);
	}

	@Override
	public List<Map<String, Object>> queryParamRelation(String pageId, String widgetRuleId, String paramName, String databaseId) {
		return formParamsRelationMapper.queryParamRelation(pageId,widgetRuleId,paramName,databaseId);
	}

}
