package com.glaf.base.usertask.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.usertask.domain.*;
import com.glaf.base.usertask.query.*;

@Transactional(readOnly = true)
public interface UserTaskTotalService {

	@Transactional
	void bulkInsert(List<UserTaskTotal> list);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	 
	/**
	 * 根据数据库编号删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByDatabaseId(long databaseId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	UserTaskTotal getUserTaskTotal(String id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getUserTaskTotalCountByQueryCriteria(UserTaskTotalQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<UserTaskTotal> getUserTaskTotalsByQueryCriteria(int start, int pageSize, UserTaskTotalQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<UserTaskTotal> list(UserTaskTotalQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(UserTaskTotal userTaskTotal);

}
