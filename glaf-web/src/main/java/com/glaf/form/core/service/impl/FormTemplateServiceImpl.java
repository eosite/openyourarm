package com.glaf.form.core.service.impl;

import java.util.List;

import org.apache.commons.codec.binary.StringUtils;
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
import com.glaf.form.core.domain.FormTemplate;
import com.glaf.form.core.mapper.FormTemplateMapper;
import com.glaf.form.core.query.FormTemplateQuery;
import com.glaf.form.core.service.FormTemplateService;

@Service("formTemplateService")
@Transactional(readOnly = true)
public class FormTemplateServiceImpl implements FormTemplateService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormTemplateMapper formTemplateMapper;

	public FormTemplateServiceImpl() {

	}

	@Transactional
	public void deleteById(Integer id) {
		if (id != null) {
			formTemplateMapper.deleteFormTemplateById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<Integer> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (Integer id : ids) {
				formTemplateMapper.deleteFormTemplateById(id);
			}
		}
	}

	public int count(FormTemplateQuery query) {
		return formTemplateMapper.getFormTemplateCount(query);
	}

	public List<FormTemplate> list(FormTemplateQuery query) {
		List<FormTemplate> list = null ;
		if(StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())){
			list = formTemplateMapper.getFormTemplates_oracle(query);
		}else{
			list = formTemplateMapper.getFormTemplates(query);
		}
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormTemplateCountByQueryCriteria(FormTemplateQuery query) {
		return formTemplateMapper.getFormTemplateCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormTemplate> getFormTemplatesByQueryCriteria(int start, int pageSize, FormTemplateQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		if(StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())){
			return sqlSessionTemplate.selectList("getFormTemplates_oracle", query, rowBounds);
		}
		List<FormTemplate> rows = sqlSessionTemplate.selectList("getFormTemplates", query, rowBounds);
		return rows;
	}

	public FormTemplate getFormTemplate(Integer id) {
		if (id == null) {
			return null;
		}
		if(StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())){
			return formTemplateMapper.getFormTemplateById_oracle(id);
		}
		FormTemplate formTemplate = formTemplateMapper.getFormTemplateById(id);
		return formTemplate;
	}

	@Transactional
	public void save(FormTemplate formTemplate) {
		if (formTemplate.getId() == null) {
			formTemplate.setId(idGenerator.nextId("FORM_TEMPLATE").intValue());
			// formTemplate.setCreateDate(new Date());
			// formTemplate.setDeleteFlag(0);
			formTemplateMapper.insertFormTemplate(formTemplate);
		} else {
			formTemplateMapper.updateFormTemplate(formTemplate);
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

	@javax.annotation.Resource
	public void setFormTemplateMapper(FormTemplateMapper formTemplateMapper) {
		this.formTemplateMapper = formTemplateMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	public FormTemplate getTemplateImage(Integer id) {
		return formTemplateMapper.getTemplateImage(id);
	}

}
