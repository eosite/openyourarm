package com.glaf.isdp.service;

import java.util.List;
import java.util.Map;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.CellFillForm;
import com.glaf.isdp.query.CellFillFormQuery;

@Transactional(readOnly = true)
public interface CellFillFormService {

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
	List<CellFillForm> list(CellFillFormQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellFillFormCountByQueryCriteria(CellFillFormQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellFillForm> getCellFillFormsByQueryCriteria(int start, int pageSize,
			CellFillFormQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellFillForm getCellFillForm(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellFillForm cellFillForm);
	List<Map<String,Integer>> selectCellSum(CellFillFormQuery query);
}
