package com.glaf.chinaiss.data.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.chinaiss.data.domain.*;
import com.glaf.chinaiss.data.query.*;

@Transactional(readOnly = true)
public interface DataModelService {

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
	List<DataModel> list(DataModelQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getDataModelCountByQueryCriteria(DataModelQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<DataModel> getDataModelsByQueryCriteria(int start, int pageSize, DataModelQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	DataModel getDataModel(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(DataModel dataModel);

	List getDataModelsWithTree(Map<String, Object> parameterMap);

	void saveOrUpdateDetails(DataModel dataModel);

	List<Map<String, Object>> getDataModelTablesByTreeId(String treeId);

	List getDataModelColumns(String treeId, String tableName);

	/**
	 * 数据模型刷新表结构
	 * 
	 * @param id
	 */
	//@Transactional
	//void saveModel2db(String id);

	@Transactional
	void insertMapping(String string, String columnMapping, String string2, String mapingId);

	Map<String, DataModelMetadata> getMappingByTableName(String string);

	List<Map<String, Object>> getList(String sql, List<Object> parameters);
}
