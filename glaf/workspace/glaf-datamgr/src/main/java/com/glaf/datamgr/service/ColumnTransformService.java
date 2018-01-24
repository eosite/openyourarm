package com.glaf.datamgr.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.datamgr.domain.*;
import com.glaf.datamgr.query.*;

@Transactional(readOnly = true)
public interface ColumnTransformService {

	@Transactional
	void bulkInsert(List<ColumnTransform> list);

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
	ColumnTransform getColumnTransform(Long id);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getColumnTransformCountByQueryCriteria(ColumnTransformQuery query);

	List<ColumnTransform> getColumnTransforms(String tableName);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<ColumnTransform> getColumnTransformsByQueryCriteria(int start, int pageSize, ColumnTransformQuery query);

	/**
	 * 获取转换的表集合
	 * 
	 * @return
	 */
	List<String> getTransformTableNames();

	/**
	 * 根据查询参数获取记录列表
	 * 
	 * @return
	 */
	List<ColumnTransform> list(ColumnTransformQuery query);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(ColumnTransform columnTransform);

}
