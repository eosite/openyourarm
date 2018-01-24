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
import com.glaf.isdp.domain.HintList;
import com.glaf.isdp.mapper.HintListMapper;
import com.glaf.isdp.query.HintListQuery;
import com.glaf.isdp.service.HintListService;

@Service("com.glaf.isdp.service.hintListService")
@Transactional(readOnly = true)
public class HintListServiceImpl implements HintListService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected HintListMapper hintListMapper;

	public HintListServiceImpl() {

	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.isdp.mapper.HintListMapper")
	public void setHintListMapper(HintListMapper hintListMapper) {
		this.hintListMapper = hintListMapper;
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
	public HintList getHintById(String id) {
		return hintListMapper.getHintListById(id);
	}

	@Override
	public List<HintList> getHintLists(HintListQuery query) {
		return hintListMapper.getHintLists(query);
	}

	@Override
	public int getHintListCount(HintListQuery query) {
		return hintListMapper.getHintLitCount(query);
	}

}
