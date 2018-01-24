package com.glaf.isdp.service;

import java.util.*;
import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.*;
import com.glaf.isdp.query.*;

@Transactional(readOnly = true)
public interface RdataTableService {

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
	List<RdataTable> list(RdataTableQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getRdataTableCountByQueryCriteria(RdataTableQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<RdataTable> getRdataTablesByQueryCriteria(int start, int pageSize, RdataTableQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	RdataTable getRdataTable(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(RdataTable rdataTable);
	/**
	 * 保存接口表
	 * @param rdataTable
	 */
	@Transactional
	void saveImpTable(RdataTable rdataTable);
	/**
	 * 获取创建表名
	 * 
	 * @return
	 */
	public String getNextTableName();

	/**
	 * 获取创建表名最大id
	 * 
	 * @return
	 */
	public int getNextMaxUser();

	void modifyColumn(String tablename, String columnType, RdataField rdataField, Rinterface fieldInterface);

	void addColumn(String tablename, String fieldName, String columnType, RdataField rdataField,
			Rinterface fieldInterface);

	void deleteColumn(String tablename, String dname, String listId, String fieldId);

	void deleteTable(String tableid);

	RdataTable getRdataTableByTableName(String tableName);

}
