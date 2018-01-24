package com.glaf.core.service.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.service.DaoService;

@Service("daoService")
@Transactional(readOnly = true)
public class DaoServiceImpl implements DaoService {

	protected SqlSessionTemplate sqlSessionTemplate;
	@Override
	public List<Object> selectList(String sqlid, Object query) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectList(sqlid,query);
	}

	@Override
	public Object selectOne(String sqlid, Object query) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.selectOne(sqlid,query);
	}

	@Transactional
	@Override
	public int insert(String sqlid, Object query) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.insert(sqlid,query);
	}

	@Transactional
	@Override
	public int update(String sqlid, Object query) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.update(sqlid,query);
	}

	@Transactional
	@Override
	public int delete(String sqlid, Object query) {
		// TODO Auto-generated method stub
		return sqlSessionTemplate.delete(sqlid,query);
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

}
