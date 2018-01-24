package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.CellTreeDot;
import com.glaf.isdp.query.CellTreeDotQuery;

@Transactional(readOnly = true)
public interface CellTreeDotService {

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
	void deleteByIds(List<Integer> indexIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<CellTreeDot> list(CellTreeDotQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellTreeDotCountByQueryCriteria(CellTreeDotQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellTreeDot> getCellTreeDotsByQueryCriteria(int start, int pageSize,
			CellTreeDotQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellTreeDot getCellTreeDot(Integer id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellTreeDot cellTreeDot);

}
