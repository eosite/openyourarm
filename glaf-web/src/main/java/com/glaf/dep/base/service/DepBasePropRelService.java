package com.glaf.dep.base.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.dep.base.domain.*;
import com.glaf.dep.base.query.*;

@Transactional(readOnly = true)
public interface DepBasePropRelService {

	@Transactional
	void deleteByRuleId(String id);
	
	@Transactional
	void deleteByPrimaryKey(String ruleId,String relRuleId);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DepBasePropRel> list(DepBasePropRelQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepBasePropRelCountByQueryCriteria(DepBasePropRelQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepBasePropRel> getDepBasePropRelsByQueryCriteria(int start,
			int pageSize, DepBasePropRelQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DepBasePropRel getDepBasePropRel(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepBasePropRel depBasePropRel);

	/**
	 * 针对ruleId，保存多条对于ruleId的规则关系
	 * @param ruleId 
	 * @param saveList
	 */
	@Transactional
	void save(String ruleId,List<DepBasePropRel> saveList);

	@Transactional
	void insertDepBasePropRel(DepBasePropRel depBasePropRel);
	
	@Transactional
	void updateDepBasePropRel(DepBasePropRel depBasePropRel);

	DepBasePropRel getDepBasePropRelByPrimaryKey(String ruleId, String relRuleId);

	List<DepBasePropRel> getDepBasePropRelsByRuleId(String ruleId, int start,
			int pageSize);
}
