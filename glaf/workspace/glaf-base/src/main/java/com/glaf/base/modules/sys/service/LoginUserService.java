package com.glaf.base.modules.sys.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.LoginUser;
import com.glaf.base.modules.sys.query.*;

@Transactional(readOnly = true)
public interface LoginUserService {

	@Transactional
	void bulkInsert(List<LoginUser> list);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<String> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<LoginUser> list(LoginUserQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getLoginUserCountByQueryCriteria(LoginUserQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<LoginUser> getLoginUsersByQueryCriteria(int start, int pageSize, LoginUserQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	LoginUser getLoginUser(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(LoginUser loginUser);

}
