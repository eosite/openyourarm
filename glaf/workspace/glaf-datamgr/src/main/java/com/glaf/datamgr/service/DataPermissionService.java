package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface DataPermissionService {

	@Transactional
	void bulkInsert(List<DataPermission> list);

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Long id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Long> ids);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DataPermission getDataPermission(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDataPermissionCountByQueryCriteria(DataPermissionQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DataPermission> getDataPermissionsByQueryCriteria(int start, int pageSize, DataPermissionQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DataPermission> list(DataPermissionQuery query);

	/**
	 * 保存多条记录
	 * 
	 * @return
	 */
	@Transactional
	void saveAll(DataPermissionQuery query, List<DataPermission> dataPermissions);

}
