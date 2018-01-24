package com.glaf.form.core.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.form.core.domain.*;
import com.glaf.form.core.query.*;

@Transactional(readOnly = true)
public interface FormPageHistoryService {

	@Transactional
	void bulkInsert(List<FormPageHistory> list);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	FormPageHistory getFormPageHistory(String id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getFormPageHistoryCountByQueryCriteria(FormPageHistoryQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<FormPageHistory> getFormPageHistorysByQueryCriteria(int start, int pageSize, FormPageHistoryQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<FormPageHistory> list(FormPageHistoryQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(FormPageHistory formPageHistory);
	
	@Transactional
	void deleteOldVersion(String pageId, int num);

}
