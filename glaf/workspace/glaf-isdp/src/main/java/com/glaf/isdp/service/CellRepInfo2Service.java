package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.CellRepInfo2;
import com.glaf.isdp.query.CellRepInfo2Query;

@Transactional(readOnly = true)
public interface CellRepInfo2Service {

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
	List<CellRepInfo2> list(CellRepInfo2Query query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellRepInfo2CountByQueryCriteria(CellRepInfo2Query query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellRepInfo2> getCellRepInfo2sByQueryCriteria(int start, int pageSize,
			CellRepInfo2Query query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellRepInfo2 getCellRepInfo2(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellRepInfo2 cellRepInfo2);

}
