package com.glaf.base.modules.sys.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.*;
import com.glaf.base.modules.sys.query.*;

@Transactional(readOnly = true)
public interface DeptRoleService {

	@Transactional
	void bulkInsert(List<DeptRole> list);

	@Transactional
	void deleteByDeptId(long deptId);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DeptRole getDeptRole(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDeptRoleCountByQueryCriteria(DeptRoleQuery query);

	List<DeptRole> getDeptRolesByDeptId(long deptId);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DeptRole> getDeptRolesByQueryCriteria(int start, int pageSize, DeptRoleQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DeptRole> list(DeptRoleQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DeptRole deptRole);

	@Transactional
	void saveAll(long deptId, int menuFlag, List<DeptRole> rows);

}
