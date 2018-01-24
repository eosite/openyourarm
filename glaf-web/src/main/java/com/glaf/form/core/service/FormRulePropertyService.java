package com.glaf.form.core.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.glaf.form.core.domain.FormRule;
import com.glaf.form.core.domain.FormRuleProperty;
import com.glaf.form.core.query.FormRulePropertyQuery;

@Transactional(readOnly = true)
public interface FormRulePropertyService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(String id);

	/**
	 * 根据规则主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByRuleId(String rid);

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
	List<FormRuleProperty> list(FormRulePropertyQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormRulePropertyCountByQueryCriteria(FormRulePropertyQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormRuleProperty> getFormRulePropertysByQueryCriteria(int start,
			int pageSize, FormRulePropertyQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormRuleProperty getFormRuleProperty(String id);
	/**
	 * 获取某个页面某个控件某个规则项的规则
	 * @param pageId
	 * @param dataRole
	 * @param propName
	 * @return
	 */
	FormRuleProperty getPageComponentPropertyRule(String pageId,String dataRole,String propName,String id);
	/**
	 * 根据规则ID 获取规则Map
	 * 
	 * @return
	 */
	Map<String, String> getRuleMap(String rid);

	/**
	 * 根据规则ID 获取规则JSONObject(原始数据，未翻译)
	 * 
	 * @return
	 */
	JSONObject getRuleJSON(String rid);

	/**
	 * 根据规则ID 获取规则List
	 * 
	 * @return
	 */
	List<Map<String, Object>> getFormRuleListByRid(String rid);
	
	/**
	 * 页面id 获取所有参数list
	 * 
	 * @return
	 */
	List<Map<String,Object>> getParametersByPageId(String pageId);
	
	/**
	 * 页面id 获取所有参数list
	 * 
	 * @return
	 */
	List<Map<String,Object>> getStuffByPageId(String pageId);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormRuleProperty formRuleProperty);

	void isRuleProperties(FormRule formRule);
	
	@Transactional
	void saveComExt(FormRule fr);

	List<FormRuleProperty> getAttributeDatasByPageId(String pageId, String attrName);

	/**
	 * 根据规则ID进行批量删除
	 * @param ids
	 */
	@Transactional
	void deleteByRuleIds(List<String> ids);
	
	/**
	 * 根据属性名称获取规则
	 * @param pageId
	 * @param widgetId
	 * @return
	 */
	FormRuleProperty getRuleByName(String pageId, String widgetId,String name);

	List<Map<String, Object>> getParametersByPageIds(List<String> pageIdList);
	
	List<Map<String,Object>> getStuffByPageIds(List<String> pageIdList);
}
