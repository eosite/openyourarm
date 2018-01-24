package com.glaf.form.core.service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;
import com.glaf.form.exception.FormPageDesignerException;

@Transactional(readOnly = true)
public interface FormPageService {

	@Transactional
	void updateThemeId(String id,String themeId);
	
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
	List<FormPage> list(FormPageQuery query);

	List<FormPage> getChildren(String parentId);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormPageCountByQueryCriteria(FormPageQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormPage> getFormPagesByQueryCriteria(int start, int pageSize,
			FormPageQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormPage getFormPage(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormPage formPage) throws FormPageDesignerException;

	List<FormPage> getFormPageTree(FormPageQuery query);

	List<Map<String, Object>> getPageElementsById(String pageId);
	
	/**
	 * 启用禁用
	 * @param x
	 */
	@Transactional
	void enabledDisable(String id,int locked);

	/**
	 * 更新parentid
	 * @param parentid
	 * @param idArr
	 */
	@Transactional
	void updateFormPageParentId(String parentid, List<String> ids,String actorId);

	FormPage getFormPage(String id,boolean useCache);
}
