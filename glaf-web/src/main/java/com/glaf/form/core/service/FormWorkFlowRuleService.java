package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormWorkFlowRuleService {

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
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FormWorkFlowRule> list(FormWorkFlowRuleQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormWorkFlowRuleCountByQueryCriteria(FormWorkFlowRuleQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormWorkFlowRule> getFormWorkFlowRulesByQueryCriteria(int start, int pageSize, FormWorkFlowRuleQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormWorkFlowRule getFormWorkFlowRule(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormWorkFlowRule formWorkFlowRule);

	Integer getNextVersionByPageId(String pageId);

}
