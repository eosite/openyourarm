package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface DataSetMappingItemService {

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
	List<DataSetMappingItem> list(DataSetMappingItemQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDataSetMappingItemCountByQueryCriteria(DataSetMappingItemQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DataSetMappingItem> getDataSetMappingItemsByQueryCriteria(int start, int pageSize,
			DataSetMappingItemQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DataSetMappingItem getDataSetMappingItem(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DataSetMappingItem dataSetMappingItem);

	/**
	 * 保存N条记录
	 * 
	 * @return
	 */
	@Transactional
	void saveBatch(List<DataSetMappingItem> dataSetMappingItems);

	@Transactional
	void deleteByParentId(String parentId);

}
