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
import com.glaf.form.core.service.UserSqliteService;

@Service("com.glaf.form.core.service.userSqliteService")
@Transactional(readOnly = true) 
public class UserSqliteServiceImpl implements UserSqliteService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected UserSqliteMapper userSqliteMapper;

	public UserSqliteServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<UserSqlite> list) {
		for (UserSqlite userSqlite : list) {
		   if (StringUtils.isEmpty(userSqlite.getId())) {
			userSqlite.setId(idGenerator.getNextId("USER_SQLITE_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			userSqliteMapper.bulkInsertUserSqlite_oracle(list);
		} else {
//			userSqliteMapper.bulkInsertUserSqlite(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		userSqliteMapper.deleteUserSqliteById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    userSqliteMapper.deleteUserSqliteById(id);
		}
	    }
	}

	public int count(UserSqliteQuery query) {
		return userSqliteMapper.getUserSqliteCount(query);
	}

	public List<UserSqlite> list(UserSqliteQuery query) {
		List<UserSqlite> list = userSqliteMapper.getUserSqlites(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getUserSqliteCountByQueryCriteria(UserSqliteQuery query) {
		return userSqliteMapper.getUserSqliteCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<UserSqlite> getUserSqlitesByQueryCriteria(int start, int pageSize,
			UserSqliteQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<UserSqlite> rows = sqlSessionTemplate.selectList(
				"getUserSqlites", query, rowBounds);
		return rows;
	}


	public UserSqlite getUserSqlite(String id) {
	        if(id == null){
		    return null;
		}
		UserSqlite userSqlite = userSqliteMapper.getUserSqliteById(id);
		return userSqlite;
	}

	@Transactional
	public void save(UserSqlite userSqlite) {
           if (StringUtils.isEmpty(userSqlite.getId())) {
	        userSqlite.setId(idGenerator.getNextId("USER_SQLITE_"));
		//userSqlite.setCreateDate(new Date());
		//userSqlite.setDeleteFlag(0);
		userSqliteMapper.insertUserSqlite(userSqlite);
	       } else {
		userSqliteMapper.updateUserSqlite(userSqlite);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.UserSqliteMapper")
	public void setUserSqliteMapper(UserSqliteMapper userSqliteMapper) {
		this.userSqliteMapper = userSqliteMapper;
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
