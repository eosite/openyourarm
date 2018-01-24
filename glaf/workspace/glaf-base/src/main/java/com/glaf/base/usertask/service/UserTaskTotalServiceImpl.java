package com.glaf.base.usertask.service;

import java.util.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.ibatis.session.RowBounds;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.core.dao.*;
import com.glaf.core.jdbc.DBConnectionFactory;
import com.glaf.core.util.*;

import com.glaf.base.usertask.mapper.*;
import com.glaf.base.usertask.domain.*;
import com.glaf.base.usertask.query.*;

@Service("com.glaf.base.usertask.service.userTaskTotalService")
@Transactional(readOnly = true)
public class UserTaskTotalServiceImpl implements UserTaskTotalService {
	protected final Logger logger = LoggerFactory.getLogger(getClass());

	protected EntityDAO entityDAO;

	protected JdbcTemplate jdbcTemplate;

	protected SqlSessionTemplate sqlSessionTemplate;

	protected UserTaskTotalMapper userTaskTotalMapper;

	public UserTaskTotalServiceImpl() {

	}

	@Transactional
	public void bulkInsert(List<UserTaskTotal> list) {
		for (UserTaskTotal userTaskTotal : list) {
			if (StringUtils.isEmpty(userTaskTotal.getId())) {
				userTaskTotal.setId(UUID32.getUUID());
				userTaskTotal.setCreateTime(new Date());
			}
		}

		int batch_size = 50;
		List<UserTaskTotal> tasks = new ArrayList<UserTaskTotal>(batch_size);

		for (UserTaskTotal task : list) {
			tasks.add(task);
			if (tasks.size() > 0 && tasks.size() % batch_size == 0) {
				if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
					userTaskTotalMapper.bulkInsertUserTaskTotal_oracle(tasks);
				} else {
					userTaskTotalMapper.bulkInsertUserTaskTotal(tasks);
				}
				tasks.clear();
			}
		}

		if (tasks.size() > 0) {
			if (StringUtils.equals(DBUtils.ORACLE, DBConnectionFactory.getDatabaseType())) {
				userTaskTotalMapper.bulkInsertUserTaskTotal_oracle(tasks);
			} else {
				userTaskTotalMapper.bulkInsertUserTaskTotal(tasks);
			}
			tasks.clear();
		}
	}

	public int count(UserTaskTotalQuery query) {
		return userTaskTotalMapper.getUserTaskTotalCount(query);
	}

	@Transactional
	public void deleteById(String id) {
		if (id != null) {
			userTaskTotalMapper.deleteUserTaskTotalById(id);
		}
	}

	/**
	 * 根据数据库编号删除记录
	 * 
	 * @return
	 */
	@Transactional
	public void deleteByDatabaseId(long databaseId) {
		if (databaseId > 0) {
			userTaskTotalMapper.deleteByDatabaseId(databaseId);
		}
	}

	public UserTaskTotal getUserTaskTotal(String id) {
		if (id == null) {
			return null;
		}
		UserTaskTotal userTaskTotal = userTaskTotalMapper.getUserTaskTotalById(id);
		return userTaskTotal;
	}

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	public int getUserTaskTotalCountByQueryCriteria(UserTaskTotalQuery query) {
		return userTaskTotalMapper.getUserTaskTotalCount(query);
	}

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	public List<UserTaskTotal> getUserTaskTotalsByQueryCriteria(int start, int pageSize, UserTaskTotalQuery query) {
		RowBounds rowBounds = new RowBounds(start, pageSize);
		List<UserTaskTotal> rows = sqlSessionTemplate.selectList("getUserTaskTotals", query, rowBounds);
		return rows;
	}

	public List<UserTaskTotal> list(UserTaskTotalQuery query) {
		List<UserTaskTotal> list = userTaskTotalMapper.getUserTaskTotals(query);
		return list;
	}

	@Transactional
	public void save(UserTaskTotal userTaskTotal) {
		if (StringUtils.isEmpty(userTaskTotal.getId())) {
			userTaskTotal.setId(UUID32.getUUID());
			userTaskTotal.setCreateTime(new Date());
			userTaskTotalMapper.insertUserTaskTotal(userTaskTotal);
		} else {
			userTaskTotalMapper.updateUserTaskTotal(userTaskTotal);
		}
	}

	@javax.annotation.Resource
	public void setEntityDAO(EntityDAO entityDAO) {
		this.entityDAO = entityDAO;
	}

	@javax.annotation.Resource
	public void setJdbcTemplate(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	@javax.annotation.Resource
	public void setSqlSessionTemplate(SqlSessionTemplate sqlSessionTemplate) {
		this.sqlSessionTemplate = sqlSessionTemplate;
	}

	@javax.annotation.Resource(name = "com.glaf.base.usertask.mapper.UserTaskTotalMapper")
	public void setUserTaskTotalMapper(UserTaskTotalMapper userTaskTotalMapper) {
		this.userTaskTotalMapper = userTaskTotalMapper;
	}

}
