package com.glaf.form.core.service.impl;

import java.util.ArrayList;
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
import com.glaf.form.core.domain.FormEvent;
import com.glaf.form.core.mapper.FormEventMapper;
import com.glaf.form.core.query.FormEventQuery;
import com.glaf.form.core.service.FormEventService;

@Service("com.glaf.form.core.service.formEventService")
@Transactional(readOnly = true)
public class FormEventServiceImpl implements FormEventService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FormEventMapper formEventMapper;

	public FormEventServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<FormEvent> list) {
		for (FormEvent formEvent : list) {
			if (StringUtils.isEmpty(formEvent.getId())) {
				formEvent.setId(idGenerator.getNextId("FORM_EVENT"));
			}
		}

		int batch_size = 100;
		List<FormEvent> rows = new ArrayList<FormEvent>(batch_size);

		for (FormEvent bean : list) {
			rows.add(bean);
			if (rows.size() > 0 && rows.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					//formEventMapper.bulkInsertFormEvent_oracle(list);
				} else {
					//formEventMapper.bulkInsertFormEvent(list);
				}
				rows.clear();
			}
		}

		if (rows.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				//formEventMapper.bulkInsertFormEvent_oracle(list);
			} else {
				//formEventMapper.bulkInsertFormEvent(list);
			}
			rows.clear();
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			formEventMapper.deleteFormEventById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				formEventMapper.deleteFormEventById(id);
			}
		}
	}

	public int count(FormEventQuery query) {
		return formEventMapper.getFormEventCount(query);
	}

	public List<FormEvent> list(FormEventQuery query) {
		List<FormEvent> list = formEventMapper.getFormEvents(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFormEventCountByQueryCriteria(FormEventQuery query) {
		return formEventMapper.getFormEventCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FormEvent> getFormEventsByQueryCriteria(int start, int pageSize, FormEventQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FormEvent> rows = sqlSessionTemplate.selectList("getFormEvents", query, rowBounds);
		return rows;
	}

	public FormEvent getFormEvent(String id) {
		if (id == null) {
			return null;
		}
		FormEvent formEvent = formEventMapper.getFormEventById(id);
		return formEvent;
	}

	@Transactional
	public void save(FormEvent formEvent) {
		if (StringUtils.isEmpty(formEvent.getId())) {
			formEvent.setId(idGenerator.getNextId("FORM_EVENT"));
			// formEvent.setCreateDate(new Date());
			// formEvent.setDeleteFlag(0);
			formEventMapper.insertFormEvent(formEvent);
		} else {
			formEventMapper.updateFormEvent(formEvent);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.FormEventMapper")
	public void setFormEventMapper(FormEventMapper formEventMapper) {
		this.formEventMapper = formEventMapper;
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
