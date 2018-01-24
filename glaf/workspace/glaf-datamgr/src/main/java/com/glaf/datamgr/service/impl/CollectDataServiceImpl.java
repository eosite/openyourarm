package com.glaf.datamgr.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.EntityDAO;
import com.glaf.core.id.IdGenerator;
import com.glaf.datamgr.domain.CollectData;
import com.glaf.datamgr.mapper.CollectDataMapper;
import com.glaf.datamgr.query.CollectDataQuery;
import com.glaf.datamgr.service.CollectDataService;

@Service("collectDataService")
@Transactional(readOnly = true)
public class CollectDataServiceImpl implements CollectDataService {

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected CollectDataMapper collectDataMapper;

	@Override
	public List<CollectData> getCollectDatasByQueryCriteria(int start, int pageSize, CollectDataQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<CollectData> rows = sqlSessionTemplate.selectList("selectCollectDatas", query, rowBounds);
		return rows;
	}

	@Override
	public List<CollectData> list(CollectDataQuery query) {
		return collectDataMapper.selectCollectDatas(query);
	}

	@Override
	public int getCollectDataCount(CollectDataQuery query) {
		return collectDataMapper.selectCollectDataCount(query);
	}

	@Override
	public int getCollectProjectCount(CollectDataQuery query) {
		return collectDataMapper.selectCollectProjectCount(query);
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
	public void setCollectDataMapper(CollectDataMapper collectDataMapper) {
		this.collectDataMapper = collectDataMapper;
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
