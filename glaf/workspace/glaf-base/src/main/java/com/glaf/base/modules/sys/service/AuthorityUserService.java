package com.glaf.base.modules.sys.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

@Transactional(readOnly = true)
public interface AuthorityUserService {

	@Transactional
	void bulkInsert(List<AuthorityUser> list);

	@Transactional
	void deleteByActorId(String actorId);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	@Transactional
	void deleteByRoleId(long roleId);

	List<String> getActorIdsByRoleId(long roleId);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	AuthorityUser getAuthorityUser(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getAuthorityUserCountByQueryCriteria(AuthorityUserQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<AuthorityUser> getAuthorityUsersByQueryCriteria(int start, int pageSize, AuthorityUserQuery query);

	List<Long> getRoleIdsByActorId(String actorId);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<AuthorityUser> list(AuthorityUserQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(AuthorityUser authorityUser);

	@Transactional
	void saveAll(Long roleId, List<AuthorityUser> authorityUsers);

	@Transactional
	void saveAll(String actorId, List<AuthorityUser> authorityUsers);

}
