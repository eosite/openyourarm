package com.glaf.dep.base.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

@Transactional(readOnly = true)
public interface DepBaseComponentService {

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
	List<DepBaseComponent> list(DepBaseComponentQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepBaseComponentCountByQueryCriteria(DepBaseComponentQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepBaseComponent> getDepBaseComponentsByQueryCriteria(int start,
			int pageSize, DepBaseComponentQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DepBaseComponent getDepBaseComponent(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepBaseComponent depBaseComponent);

}
