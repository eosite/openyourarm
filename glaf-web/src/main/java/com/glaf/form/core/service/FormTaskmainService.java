package com.glaf.form.core.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.FormTaskmain;
import com.glaf.form.core.query.FormTaskmainQuery;

@Transactional(readOnly = true)
public interface FormTaskmainService {

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
	List<FormTaskmain> list(FormTaskmainQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormTaskmainCountByQueryCriteria(FormTaskmainQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormTaskmain> getFormTaskmainsByQueryCriteria(int start, int pageSize, FormTaskmainQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormTaskmain getFormTaskmain(Long id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormTaskmain formTaskmain);

	FormTaskmain getFormTaskMainByPageIdAndIdValue(String pageId, String idValue);

	/**
	 * 修改流程状态
	 * 
	 * @return
	 */
	@Transactional
	void updateStatus(Integer status, String processId);

	/**
	 * 获取当前流程的父流程信息
	 * 
	 * @param processId
	 * @return
	 */
	FormTaskmain getParentFormTaskmainBySubProcessId(String processId);
}
