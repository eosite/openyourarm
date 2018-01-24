package com.glaf.datamgr.service.impl;

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

import com.glaf.datamgr.mapper.*;
import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;
import com.glaf.datamgr.service.FieldSetService;

@Service("fieldSetService")
@Transactional(readOnly = true)
public class FieldSetServiceImpl implements FieldSetService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected FieldSetMapper fieldSetMapper;

	public FieldSetServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			fieldSetMapper.deleteFieldSetById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				fieldSetMapper.deleteFieldSetById(id);
			}
		}
	}

	public int count(FieldSetQuery query) {
		return fieldSetMapper.getFieldSetCount(query);
	}

	public List<FieldSet> list(FieldSetQuery query) {
		List<FieldSet> list = fieldSetMapper.getFieldSets(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getFieldSetCountByQueryCriteria(FieldSetQuery query) {
		return fieldSetMapper.getFieldSetCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<FieldSet> getFieldSetsByQueryCriteria(int start, int pageSize, FieldSetQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<FieldSet> rows = sqlSessionTemplate.selectList("getFieldSets", query, rowBounds);
		return rows;
	}

	public FieldSet getFieldSet(String id) {
		if (id == null) {
			return null;
		}
		FieldSet fieldSet = fieldSetMapper.getFieldSetById(id);
		return fieldSet;
	}

	@Transactional
	public void save(FieldSet fieldSet) {
		if (StringUtils.isEmpty(fieldSet.getId())) {
			fieldSet.setId(idGenerator.getNextId("SYS_FIELD_SET"));
			fieldSet.setCreateTime(new Date());
			// fieldSet.setDeleteFlag(0);
			fieldSetMapper.insertFieldSet(fieldSet);
		} else {
			fieldSet.setUpdateTime(new Date());
			fieldSetMapper.updateFieldSet(fieldSet);
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
	public void setFieldSetMapper(FieldSetMapper fieldSetMapper) {
		this.fieldSetMapper = fieldSetMapper;
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
