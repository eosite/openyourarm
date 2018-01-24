package com.glaf.dep.base.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.dep.base.domain.DepBasePropCategory;
import com.glaf.dep.base.query.DepBasePropCategoryQuery;

@Transactional(readOnly = true)
public interface DepBasePropCategoryService {

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
	void deleteByIds(List<String> ruleIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DepBasePropCategory> list(DepBasePropCategoryQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDepBasePropCategoryCountByQueryCriteria(
			DepBasePropCategoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DepBasePropCategory> getDepBasePropCategorysByQueryCriteria(int start,
			int pageSize, DepBasePropCategoryQuery query);

	DepBasePropCategory getDepBasePropCategory(String ruleId, Long categoryId);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DepBasePropCategory depBasePropCategory);

	List<DepBasePropCategory> getDepBasePropCatgorysByCategoryId(Long categoryId);

	/**
	 * 根据ruleId删除数据
	 * 
	 * @param ruleId
	 */
	void deleteByRuleId(String ruleId);

}
