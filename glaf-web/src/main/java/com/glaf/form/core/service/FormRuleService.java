package com.glaf.form.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormRuleService {

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
	List<FormRule> list(FormRuleQuery query);

	/**
	 * 获取某个页面的规则
	 * 
	 * @return
	 */
	List<FormRule> getRules(String pageId);
	List<FormRule> getRules(String pageId,boolean userCache);

	List<FormRule> getRulesByEleId(String pageId, String eleId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormRuleCountByQueryCriteria(FormRuleQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormRule> getFormRulesByQueryCriteria(int start, int pageSize, FormRuleQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormRule getFormRule(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormRule formRule);

	/**
	 * 保存某个页面的规则集合
	 * 
	 * @param pageId
	 * @param formRules
	 */
	@Transactional
	void saveRules(String pageId, List<FormRule> formRules);

	/**
	 * 根据页面id 获取主表名称
	 * 
	 * @param pageId
	 * @return
	 */
	String getTableNameByPageId(String pageId);

	/**
	 * 获取页面主 参数 名称
	 * 
	 * @param pageId
	 * @param idKey
	 * @return
	 */
	String getIdVariable(String pageId, String idKey);

	/**
	 * 获取页面级别规则
	 * 
	 * @param pageId
	 * @return
	 */
	Map<String, String> getRuleByPageId(String pageId);
	
	/**
	 * 批量删除，使用in操作
	 * @param ids
	 */
	void deleteByRuleIds(List<String> ids);
}
