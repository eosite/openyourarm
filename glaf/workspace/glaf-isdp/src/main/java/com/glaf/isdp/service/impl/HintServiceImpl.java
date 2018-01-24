package com.glaf.isdp.service.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.isdp.domain.Hint;
import com.glaf.isdp.mapper.HintMapper;
import com.glaf.isdp.query.HintQuery;
import com.glaf.isdp.service.HintService;

@Service("com.glaf.isdp.service.hintService")
@Transactional(readOnly = true)
public class HintServiceImpl implements HintService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected HintMapper hintMapper;

	public HintServiceImpl() {

	}


	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.HintMapper")
	public void setHintMapper(HintMapper hintMapper) {
		this.hintMapper = hintMapper;
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
	public Hint getHintById(String id) {
		return hintMapper.getHintById(id);
	}


	@Override
	public List<Hint> getHints(HintQuery query) {
		return hintMapper.getHints(query);
	}


	@Override
	public int getHintCount(HintQuery query) {
		return hintMapper.getHintCount(query);
	}

}
