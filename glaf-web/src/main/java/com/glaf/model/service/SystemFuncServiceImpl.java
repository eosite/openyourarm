package com.glaf.model.service;

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

import com.glaf.model.mapper.*;
import com.glaf.model.domain.*;
import com.glaf.model.query.*;

@Service("com.glaf.model.service.systemFuncService")
@Transactional(readOnly = true)
public class SystemFuncServiceImpl implements SystemFuncService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SystemFuncMapper systemFuncMapper;

	public SystemFuncServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			systemFuncMapper.deleteSystemFuncById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> funcIds) {
		if (funcIds != null && !funcIds.isEmpty()) {
			for (String id : funcIds) {
				systemFuncMapper.deleteSystemFuncById(id);
			}
		}
	}

	public int count(SystemFuncQuery query) {
		return systemFuncMapper.getSystemFuncCount(query);
	}

	public List<SystemFunc> list(SystemFuncQuery query) {
		List<SystemFunc> list = systemFuncMapper.getSystemFuncs(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getSystemFuncCountByQueryCriteria(SystemFuncQuery query) {
		return systemFuncMapper.getSystemFuncCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<SystemFunc> getSystemFuncsByQueryCriteria(int start, int pageSize, SystemFuncQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<SystemFunc> rows = sqlSessionTemplate.selectList("getSystemFuncs", query, rowBounds);
		return rows;
	}

	public SystemFunc getSystemFunc(String id) {
		if (id == null) {
			return null;
		}
		SystemFunc systemFunc = systemFuncMapper.getSystemFuncById(id);
		return systemFunc;
	}

	@Transactional
	public void save(SystemFunc systemFunc) {
		if (systemFunc.getFuncId() == null) {
			systemFunc.setFuncId(UUID32.getUUID());
			// systemFunc.setCreateDate(new Date());
			// systemFunc.setDeleteFlag(0);
			systemFuncMapper.insertSystemFunc(systemFunc);
		} else {
			systemFuncMapper.updateSystemFunc(systemFunc);
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

	@javax.annotation.Resource(name = "com.glaf.model.mapper.SystemFuncMapper")
	public void setSystemFuncMapper(SystemFuncMapper systemFuncMapper) {
		this.systemFuncMapper = systemFuncMapper;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}
	@Transactional
	@Override
	public void updateFuncNameType(SystemFunc systemFunc) {
		// TODO Auto-generated method stub
		systemFuncMapper.updateFuncNameType(systemFunc);
	}

}
