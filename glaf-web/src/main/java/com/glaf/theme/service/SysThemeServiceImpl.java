package com.glaf.theme.service;

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
import com.alibaba.fastjson.JSONArray;
import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.theme.mapper.*;
import com.glaf.theme.domain.*;
import com.glaf.theme.query.*;

@Service("com.glaf.theme.service.sysThemeService")
@Transactional(readOnly = true)
public class SysThemeServiceImpl implements SysThemeService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected SysThemeTmpMapper sysThemeTmpMapper;

	public SysThemeServiceImpl() {

	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setIdGenerator(IdGenerator idGenerator) {
		this.idGenerator = idGenerator;
	}

	@javax.annotation.Resource(name = "com.glaf.theme.mapper.SysThemeTmpMapper")
	public void setSysThemeTmpMapper(SysThemeTmpMapper sysThemeTmpMapper) {
		this.sysThemeTmpMapper = sysThemeTmpMapper;
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
	public JSONArray getThemeTreeByThemeId(String id) {
		
		SysThemeTmp sysThemeTmp = sysThemeTmpMapper.getSysThemeTmpById(id);
		return null;
	}

}
