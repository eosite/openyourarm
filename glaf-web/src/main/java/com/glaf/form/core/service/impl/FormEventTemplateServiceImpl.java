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
import com.glaf.form.core.service.FormEventTemplateService;

@Service("com.glaf.form.core.service.formEventTemplateService")
@Transactional(readOnly = true)
public class FormEventTemplateServiceImpl implements FormEventTemplateService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormEventTemplateMapper formEventTemplateMapper;

	public FormEventTemplateServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<FormEventTemplate> list) {
		for (FormEventTemplate formEventTemplate : list) {
			if (StringUtils.isEmpty(formEventTemplate.getId())) {
				formEventTemplate.setId(idGenerator.getNextId("FORM_EVENT_TEMPLATE"));
			}
		}

		int batch_size = 100;
		List<FormEventTemplate> rows = new ArrayList<FormEventTemplate>(batch_size);

		for (FormEventTemplate bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					//formEventTemplateMapper.bulkInsertFormEventTemplate_oracle(list);
				} else {
					//formEventTemplateMapper.bulkInsertFormEventTemplate(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//formEventTemplateMapper.bulkInsertFormEventTemplate_oracle(list);
			} else {
				//formEventTemplateMapper.bulkInsertFormEventTemplate(list);
			}
			rows.clear();
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formEventTemplateMapper.deleteFormEventTemplateById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formEventTemplateMapper.deleteFormEventTemplateById(id);
			}
		}
	}

	public int count(FormEventTemplateQuery query) {
		return formEventTemplateMapper.getFormEventTemplateCount(query);
	}

	public List<FormEventTemplate> list(FormEventTemplateQuery query) {
		List<FormEventTemplate> list = formEventTemplateMapper.getFormEventTemplates(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormEventTemplateCountByQueryCriteria(FormEventTemplateQuery query) {
		return formEventTemplateMapper.getFormEventTemplateCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormEventTemplate> getFormEventTemplatesByQueryCriteria(int start, int pageSize, FormEventTemplateQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormEventTemplate> rows = sqlSessionTemplate.selectList("getFormEventTemplates", query, rowBounds);
		return rows;
	}

	public FormEventTemplate getFormEventTemplate(String id) {
		if (id == null) {
			return null;
		}
		FormEventTemplate formEventTemplate = formEventTemplateMapper.getFormEventTemplateById(id);
		return formEventTemplate;
	}

	@Transactional
	public void save(FormEventTemplate formEventTemplate) {
		if (StringUtils.isEmpty(formEventTemplate.getId())) {
			formEventTemplate.setId(idGenerator.getNextId("FORM_EVENT_TEMPLATE"));
			// formEventTemplate.setCreateDate(new Date());
			// formEventTemplate.setDeleteFlag(0);
			formEventTemplateMapper.insertFormEventTemplate(formEventTemplate);
		} else {
			formEventTemplateMapper.updateFormEventTemplate(formEventTemplate);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormEventTemplateMapper")
	public void setFormEventTemplateMapper(FormEventTemplateMapper formEventTemplateMapper) {
		this.formEventTemplateMapper = formEventTemplateMapper;
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
