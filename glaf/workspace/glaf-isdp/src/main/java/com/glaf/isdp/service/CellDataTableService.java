package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.query.CellDataTableQuery;

@Transactional(readOnly = true)
public interface CellDataTableService {

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
	List<CellDataTable> list(CellDataTableQuery query);

	/**
	 * 根据查询参数获取记录总数
	 * 
	 * @return
	 */
	int getCellDataTableCountByQueryCriteria(CellDataTableQuery query);

	/**
	 * 根据查询参数获取一页的数据
	 * 
	 * @return
	 */
	List<CellDataTable> getCellDataTablesByQueryCriteria(int start,
			int pageSize, CellDataTableQuery query);

	/**
	 * 根据主键获取一条记录
	 * 
	 * @return
	 */
	CellDataTable getCellDataTable(String id);

	/**
	 * 保存一条记录
	 * 
	 * @return
	 */
	@Transactional
	void save(CellDataTable cellDataTable);

	List<CellDataTable> getCellDataTablesByTreedotIndexId(int start,
			int pageSize,CellDataTableQuery query);
	
	int getCellDataTablesCountByTreedotIndexId(CellDataTableQuery query);
	
	int getNextMaxUser();

	CellDataTable getCellDataTableByTableName(String tablename);
	
	/**
	 * 获取一页数据，包括子表数据
	 * @param start
	 * @param pageSize
	 * @param query
	 * @return
	 */
	List<CellDataTable> getCellDataTablesAndChildTablesByQueryCriteria(int start,
			int pageSize, CellDataTableQuery query);
	
	/**
	 * 获取包括子表总记录数
	 * @param query
	 * @return
	 */
	int getCellDataTableCountAndChildTablesByQueryCriteria(CellDataTableQuery query);
}
