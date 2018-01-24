package com.glaf.form.core.service.impl;


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
import com.glaf.form.core.domain.UserSqliteUpload;
import com.glaf.form.core.mapper.UserSqliteUploadMapper;
import com.glaf.form.core.query.UserSqliteUploadQuery;
import com.glaf.form.core.service.UserSqliteUploadService;


@Service("com.glaf.form.core.service.userSqliteUploadService")
@Transactional(readOnly = true) 
public class UserSqliteUploadServiceImpl implements UserSqliteUploadService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());
 
        protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected UserSqliteUploadMapper userSqliteUploadMapper;

	public UserSqliteUploadServiceImpl() {

	}


        @Transactional
	public void bulkInsert(List<UserSqliteUpload> list) {
		for (UserSqliteUpload userSqliteUpload : list) {
		   if (StringUtils.isEmpty(userSqliteUpload.getId())) {
			userSqliteUpload.setId(idGenerator.getNextId("USER_SQLITE_UPLOAD_"));
		   }
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
//			userSqliteUploadMapper.bulkInsertUserSqliteUpload_oracle(list);
		} else {
//			userSqliteUploadMapper.bulkInsertUserSqliteUpload(list);
		}
	}


	@Transactional
	public void deleteById(String id) {
	     if(id != null ){
		userSqliteUploadMapper.deleteUserSqliteUploadById(id);
	     }
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
	    if(ids != null && !ids.isEmpty()){
		for(String id : ids){
		    userSqliteUploadMapper.deleteUserSqliteUploadById(id);
		}
	    }
	}

	public int count(UserSqliteUploadQuery query) {
		return userSqliteUploadMapper.getUserSqliteUploadCount(query);
	}

	public List<UserSqliteUpload> list(UserSqliteUploadQuery query) {
		List<UserSqliteUpload> list = userSqliteUploadMapper.getUserSqliteUploads(query);
		return list;
	}

    /**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */     
	public int getUserSqliteUploadCountByQueryCriteria(UserSqliteUploadQuery query) {
		return userSqliteUploadMapper.getUserSqliteUploadCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<UserSqliteUpload> getUserSqliteUploadsByQueryCriteria(int start, int pageSize,
			UserSqliteUploadQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<UserSqliteUpload> rows = sqlSessionTemplate.selectList(
				"getUserSqliteUploads", query, rowBounds);
		return rows;
	}


	public UserSqliteUpload getUserSqliteUpload(String id) {
	        if(id == null){
		    return null;
		}
		UserSqliteUpload userSqliteUpload = userSqliteUploadMapper.getUserSqliteUploadById(id);
		return userSqliteUpload;
	}

	@Transactional
	public void save(UserSqliteUpload userSqliteUpload) {
           if (StringUtils.isEmpty(userSqliteUpload.getId())) {
	        userSqliteUpload.setId(idGenerator.getNextId("USER_SQLITE_UPLOAD_"));
		//userSqliteUpload.setCreateDate(new Date());
		//userSqliteUpload.setDeleteFlag(0);
		userSqliteUploadMapper.insertUserSqliteUpload(userSqliteUpload);
	       } else {
		userSqliteUploadMapper.updateUserSqliteUpload(userSqliteUpload);
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

	@javax.annotation.Resource(name = "com.glaf.form.core.mapper.UserSqliteUploadMapper")
	public void setUserSqliteUploadMapper(UserSqliteUploadMapper userSqliteUploadMapper) {
		this.userSqliteUploadMapper = userSqliteUploadMapper;
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
