package com.glaf.dep.base.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

@Transactional(readOnly = true)
public interface DepBaseCompScopeService {

	@Transactional
	void deleteByComponentId(String componentId);

	@Transactional
	void deleteByUIId(String uiid);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DepBaseCompScope> list(DepBaseCompScopeQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepBaseCompScopeCountByQueryCriteria(DepBaseCompScopeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepBaseCompScope> getDepBaseCompScopesByQueryCriteria(int start,
			int pageSize, DepBaseCompScopeQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DepBaseCompScope getDepBaseCompScope(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepBaseCompScope depBaseCompScope);

	/**
	 * 根据组件的ID获取组件适用范围
	 * 
	 * @param componentId
	 * @return
	 */
	List<DepBaseCompScope> getDepBaseCompScopesByComponentId(String componentId);

	/**
	 * 根据UI的ID获取组件适用范围
	 * 
	 * @param uiId
	 * @return
	 */
	List<DepBaseCompScope> getDepBaseCompScopesByUIId(String uiId);

}
