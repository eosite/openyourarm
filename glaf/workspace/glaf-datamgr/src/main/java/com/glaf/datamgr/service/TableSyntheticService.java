package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface TableSyntheticService {

	@Transactional
	void bulkInsert(List<TableSynthetic> list);

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
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	TableSynthetic getTableSynthetic(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getTableSyntheticCountByQueryCriteria(TableSyntheticQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<TableSynthetic> getTableSyntheticsByQueryCriteria(int start, int pageSize, TableSyntheticQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<TableSynthetic> list(TableSyntheticQuery query);
	
	@Transactional
	void resetAllTableSyntheticStatus();

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(TableSynthetic tableSynthetic);
	
	@Transactional
	void updateTableSyntheticStatus(TableSynthetic model);

}
