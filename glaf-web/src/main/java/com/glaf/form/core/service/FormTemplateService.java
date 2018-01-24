package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormTemplateService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteById(Integer id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIds(List<Integer> ids);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FormTemplate> list(FormTemplateQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormTemplateCountByQueryCriteria(FormTemplateQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormTemplate> getFormTemplatesByQueryCriteria(int start, int pageSize, FormTemplateQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormTemplate getFormTemplate(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormTemplate formTemplate);

	FormTemplate getTemplateImage(Integer id);

}
