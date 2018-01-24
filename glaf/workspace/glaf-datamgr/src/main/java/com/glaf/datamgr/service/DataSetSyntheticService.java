package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface DataSetSyntheticService {

	@Transactional
	void bulkInsert(List<DataSetSynthetic> list);

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
	DataSetSynthetic getDataSetSynthetic(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDataSetSyntheticCountByQueryCriteria(DataSetSyntheticQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DataSetSynthetic> getDataSetSyntheticsByQueryCriteria(int start, int pageSize, DataSetSyntheticQuery query);

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<DataSetSynthetic> list(DataSetSyntheticQuery query);
	
	@Transactional
	void resetAllDataSetSyntheticStatus();

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DataSetSynthetic dataSetSynthetic);
	
	@Transactional
	void updateDataSetSyntheticStatus(DataSetSynthetic model);

}
