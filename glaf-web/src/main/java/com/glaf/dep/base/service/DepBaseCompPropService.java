package com.glaf.dep.base.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

@Transactional(readOnly = true)
public interface DepBaseCompPropService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByRuleId(String id);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DepBaseCompProp> list(DepBaseCompPropQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepBaseCompPropCountByQueryCriteria(DepBaseCompPropQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepBaseCompProp> getDepBaseCompPropsByQueryCriteria(int start,
			int pageSize, DepBaseCompPropQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DepBaseCompProp getDepBaseCompProp(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepBaseCompProp depBaseCompProp);

	/**
	 * 根据规则ruleId获取与组件的关系
	 * @param ruleId
	 * @return
	 */
	List<DepBaseCompProp> getDepBaseCompPropsByRuleId(String ruleId);

	/**
	 * 根据主键删除对象
	 * @param componentId
	 * @param ruleId
	 */
	void deleteByPrimaryKey(String componentId, String ruleId);

}
