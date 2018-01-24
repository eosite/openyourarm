package com.glaf.dep.base.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

@Transactional(readOnly = true)
public interface DepBasePropScopeService {

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DepBasePropScope> list(DepBasePropScopeQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepBasePropScopeCountByQueryCriteria(DepBasePropScopeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepBasePropScope> getDepBasePropScopesByQueryCriteria(int start,
			int pageSize, DepBasePropScopeQuery query);


	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepBasePropScope depBasePropScope);

	/**
	 * 根据RuleId获取对象
	 * 
	 * @param ruleId
	 * @return
	 */
	List<DepBasePropScope> getDepBasePropScopesByRuleId(String ruleId);

	/**
	 * 根据UI的Id获取对象
	 * 
	 * @param uiId
	 * @return
	 */
	List<DepBasePropScope> getDepBasePropScopesByUIId(String uiId);

	/**
	 * 根据规则属性ruleId删除与UI的关系
	 * 
	 * @param ruleId
	 */
	@Transactional
	void deleteByRuleId(String ruleId);

	/**
	 * 根据UI的id删除与规则属性的关联
	 * 
	 * @param uiId
	 */
	@Transactional
	void deleteByUIId(String uiId);
}
