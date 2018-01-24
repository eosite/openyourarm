package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormWorkflowTreeService {

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
	List<FormWorkflowTree> list(FormWorkflowTreeQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormWorkflowTreeCountByQueryCriteria(FormWorkflowTreeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormWorkflowTree> getFormWorkflowTreesByQueryCriteria(int start, int pageSize, FormWorkflowTreeQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormWorkflowTree getFormWorkflowTree(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormWorkflowTree formWorkflowTree);

	void deleteByPdefId(String defId);

}
