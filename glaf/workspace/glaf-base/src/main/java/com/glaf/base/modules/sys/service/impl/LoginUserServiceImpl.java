package com.glaf.base.modules.sys.service.impl;

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
import com.glaf.core.util.DBUtils;

import com.glaf.base.modules.sys.mapper.*;
import com.glaf.base.modules.sys.model.LoginUser;
import com.glaf.base.modules.sys.query.*;
import com.glaf.base.modules.sys.service.LoginUserService;

@Service("loginUserService")
@Transactional(readOnly = true)
public class LoginUserServiceImpl implements LoginUserService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected IdGenerator idGenerator;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected LoginUserMapper loginUserMapper;

	public LoginUserServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<LoginUser> list) {
		for (LoginUser loginUser : list) {
			if (StringUtils.isEmpty(loginUser.getId())) {
				loginUser.setId(idGenerator.getNextId("LOGIN_USER"));
			}
		}
		if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
			loginUserMapper.bulkInsertLoginUser_oracle(list);
		} else {
			loginUserMapper.bulkInsertLoginUser(list);
		}
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			loginUserMapper.deleteLoginUserById(id);
		}
	}

	@Transactional
	public void deleteByIds(List<String> ids) {
		if (ids != null && !ids.isEmpty()) {
			for (String id : ids) {
				loginUserMapper.deleteLoginUserById(id);
			}
		}
	}

	public int count(LoginUserQuery query) {
		return loginUserMapper.getLoginUserCount(query);
	}

	public List<LoginUser> list(LoginUserQuery query) {
		List<LoginUser> list = loginUserMapper.getLoginUsers(query);
		return list;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getLoginUserCountByQueryCriteria(LoginUserQuery query) {
		return loginUserMapper.getLoginUserCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<LoginUser> getLoginUsersByQueryCriteria(int start, int pageSize, LoginUserQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<LoginUser> rows = sqlSessionTemplate.selectList("getLoginUsers", query, rowBounds);
		return rows;
	}

	public LoginUser getLoginUser(String id) {
		if (id == null) {
			return null;
		}
		LoginUser loginUser = loginUserMapper.getLoginUserById(id);
		return loginUser;
	}

	@Transactional
	public void save(LoginUser loginUser) {
		if (StringUtils.isEmpty(loginUser.getId())) {
			loginUser.setId(idGenerator.getNextId("LOGIN_USER"));
			// loginUser.setCreateDate(new Date());
			// loginUser.setDeleteFlag(0);
			loginUserMapper.insertLoginUser(loginUser);
		} else {
			loginUserMapper.updateLoginUser(loginUser);
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
	public void setLoginUserMapper(LoginUserMapper loginUserMapper) {
		this.loginUserMapper = loginUserMapper;
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
