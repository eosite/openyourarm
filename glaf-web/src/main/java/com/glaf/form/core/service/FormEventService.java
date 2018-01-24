package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.FormEvent;
import com.glaf.form.core.query.FormEventQuery;

@Transactional(readOnly = true)
public interface FormEventService {

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
	List<FormEvent> list(FormEventQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormEventCountByQueryCriteria(FormEventQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormEvent> getFormEventsByQueryCriteria(int start, int pageSize, FormEventQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormEvent getFormEvent(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormEvent formEvent);

}
