package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONArray;
import com.glaf.isdp.domain.CellDataField;
import com.glaf.isdp.query.CellDataFieldQuery;

@Transactional(readOnly = true)
public interface CellDataFieldService {

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
	List<CellDataField> list(CellDataFieldQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellDataFieldCountByQueryCriteria(CellDataFieldQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellDataField> getCellDataFieldsByQueryCriteria(int start, int pageSize, CellDataFieldQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellDataField getCellDataField(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellDataField cellDataField);

	int getNextMaxUser(String tableId);

	CellDataField getCellDataFieldByFieldName(String fieldname);

	/**
	 * 获取表元数据
	 * 
	 * @param systemName
	 * @param tableName
	 * @param tableId
	 * @param R
	 * @return
	 */
	JSONArray getInterfaceColumnDefinitions(String systemName, String tableName, String tableId, Boolean R);

}
