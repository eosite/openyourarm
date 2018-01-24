package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.CellMyTask;
import com.glaf.isdp.query.CellMyTaskQuery;

@Transactional(readOnly = true)
public interface CellMyTaskService {

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
	List<CellMyTask> list(CellMyTaskQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellMyTaskCountByQueryCriteria(CellMyTaskQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellMyTask> getCellMyTasksByQueryCriteria(int start, int pageSize,
			CellMyTaskQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellMyTask getCellMyTask(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellMyTask cellMyTask);

}
