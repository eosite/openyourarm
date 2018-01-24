package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormWorkflowPlanService {

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
	List<FormWorkflowPlan> list(FormWorkflowPlanQuery query);

	List<FormWorkflowPlan> getPlans(String defId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormWorkflowPlanCountByQueryCriteria(FormWorkflowPlanQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormWorkflowPlan> getFormWorkflowPlansByQueryCriteria(int start, int pageSize, FormWorkflowPlanQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormWorkflowPlan getFormWorkflowPlan(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormWorkflowPlan formWorkflowPlan);

	Integer getNextVersionByDefId(String defId);

	/**
	 * 获取页面分组关系
	 * 
	 * @param defId
	 * @return
	 */
	public Map<String, Set<String>> getRelationPage(String defId);

	/**
	 * 获取页面分组关系
	 * 
	 * @param defId
	 * @return
	 */
	public Map<String, Set<String>> getRelationPage(List<FormWorkflowPlan> formWorkflowPlans);

	List<FormWorkflowPlan> getFormWorkflowPlansWithTree(Map<String, Object> params);

}
