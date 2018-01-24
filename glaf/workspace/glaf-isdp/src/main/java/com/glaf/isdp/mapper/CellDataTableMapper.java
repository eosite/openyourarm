package com.glaf.isdp.mapper;

import java.util.List;

import org.springframework.stereotype.Component;

import com.glaf.isdp.domain.CellDataTable;
import com.glaf.isdp.query.CellDataTableQuery;

/**
 * 
 * Mapper接口
 *
 */

@Component("com.glaf.isdp.mapper.CellDataTableMapper")
public interface CellDataTableMapper {

	void deleteCellDataTables(CellDataTableQuery query);

	void deleteCellDataTableById(String id);

	CellDataTable getCellDataTableById(String id);

	int getCellDataTableCount(CellDataTableQuery query);
	
	int getCellDataTableAndChildTablesCount(CellDataTableQuery query);

	List<CellDataTable> getCellDataTables(CellDataTableQuery query);

	void insertCellDataTable(CellDataTable model);

	void updateCellDataTable(CellDataTable model);

	List<CellDataTable> getCellDataTablesByTreedotIndexId(Integer treedotIndexId);
	
	int getCellDataTablesCountByTreedotIndexId(CellDataTableQuery query);
	
	int getNextMaxUser();

	CellDataTable getCellDataTableByTableName(String tableName);
}
