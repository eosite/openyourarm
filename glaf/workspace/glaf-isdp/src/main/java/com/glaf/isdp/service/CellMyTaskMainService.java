package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.CellMyTaskMain;
import com.glaf.isdp.query.CellMyTaskMainQuery;

@Transactional(readOnly = true)
public interface CellMyTaskMainService {

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
	List<CellMyTaskMain> list(CellMyTaskMainQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellMyTaskMainCountByQueryCriteria(CellMyTaskMainQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellMyTaskMain> getCellMyTaskMainsByQueryCriteria(int start,
			int pageSize, CellMyTaskMainQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellMyTaskMain getCellMyTaskMain(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellMyTaskMain cellMyTaskMain);

	/**
	 * 扩展process_d_id和process_id两个字段
	 * 
	 * @param query
	 * @return
	 */
	List<CellMyTaskMain> selectCellMyTaskMainExtProcess(
			CellMyTaskMainQuery query);

	/**
	 * 根据流程ID查询对象
	 * 
	 * @param processId
	 * @return
	 */
	CellMyTaskMain selectCellMyTaskMainByProcessId(String processId);
}
