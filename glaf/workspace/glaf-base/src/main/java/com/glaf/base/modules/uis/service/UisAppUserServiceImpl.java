package com.glaf.base.modules.uis.service;


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

import com.glaf.base.modules.uis.mapper.*;
import com.glaf.base.modules.uis.domain.*;
import com.glaf.base.modules.uis.query.*;

@Service("com.glaf.base.modules.uis.service.uisAppUserService")
@Transactional(readOnly = true) 
public class UisAppUserServiceImpl implements UisAppUserService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected UisAppUserMapper uisAppUserMapper;

	public UisAppUserServiceImpl() {

	}

	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		uisAppUserMapper.deleteUisAppUserById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    uisAppUserMapper.deleteUisAppUserById(id);
		}
	    }
	}

	public int count(UisAppUserQuery query) {
		return uisAppUserMapper.getUisAppUserCount(query);
	}

	public List<UisAppUser> list(UisAppUserQuery query) {
		List<UisAppUser> list = uisAppUserMapper.getUisAppUsers(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getUisAppUserCountByQueryCriteria(UisAppUserQuery query) {
		return uisAppUserMapper.getUisAppUserCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<UisAppUser> getUisAppUsersByQueryCriteria(int start, int pageSize,
			UisAppUserQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<UisAppUser> rows = sqlSessionTemplate.selectList(
				"getUisAppUsers", query, rowBounds);
		return rows;
	}


	public UisAppUser getUisAppUser(String id) {
	        if(id == null){
		    return null;
		}
		UisAppUser uisAppUser = uisAppUserMapper.getUisAppUserById(id);
		return uisAppUser;
	}

	@Transactional
	public void save(UisAppUser uisAppUser) {
           if (StringUtils.isEmpty(uisAppUser.getId())) {
	        uisAppUser.setId(idGenerator.getNextId("UIS_APP_USER"));
		//uisAppUser.setCreateDate(new Date());
		//uisAppUser.setDeleteFlag(0);
		uisAppUserMapper.insertUisAppUser(uisAppUser);
	       } else {
		uisAppUserMapper.updateUisAppUser(uisAppUser);
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

	@javax.annotation.Resource(name = "com.glaf.base.modules.uis.mapper.UisAppUserMapper")
	public void setUisAppUserMapper(UisAppUserMapper uisAppUserMapper) {
		this.uisAppUserMapper = uisAppUserMapper;
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
