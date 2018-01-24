package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.CellUTableTree;
import com.glaf.isdp.query.CellUTableTreeQuery;

@Transactional(readOnly = true)
public interface CellUTableTreeService {

	/**
	 * 根据主键删除记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIndexId(Integer id);

	/**
	 * 根据主键删除多条记录
	 * 
	 * @return
	 */
	@Transactional
	void deleteByIndexIds(List<Integer> indexIds);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<CellUTableTree> list(CellUTableTreeQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellUTableTreeCountByQueryCriteria(CellUTableTreeQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellUTableTree> getCellUTableTreesByQueryCriteria(int start, int pageSize, CellUTableTreeQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellUTableTree getCellUTableTree(Integer indexId);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellUTableTree cellUTableTree);

	/**
	 * 根据indexId获得所有下层数据
	 * 
	 * @return
	 */
	List<CellUTableTree> getAllChildCellUTableTrees(Integer indexId);

	List<CellUTableTree> getCellUTableTreesAndChildCountByTableType(Integer tableType, Integer level, Integer parentId);
}
