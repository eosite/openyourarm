package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.CellRepInfo;
import com.glaf.isdp.query.CellRepInfoQuery;

@Transactional(readOnly = true)
public interface CellRepInfoService {

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
	void deleteByIds(List<String> listIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<CellRepInfo> list(CellRepInfoQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellRepInfoCountByQueryCriteria(CellRepInfoQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellRepInfo> getCellRepInfosByQueryCriteria(int start, int pageSize,
			CellRepInfoQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellRepInfo getCellRepInfo(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellRepInfo cellRepInfo);

}
