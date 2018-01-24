package com.glaf.isdp.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.glaf.base.modules.sys.model.FieldInterface;
import com.glaf.isdp.domain.CellDataField;
import com.glaf.isdp.domain.CellDataTable;

@Transactional(readOnly = true)
public interface TableActionService {

	@Transactional
	void insertTableByWhereCause(String tableName, String fieldString, String valueString);

	@Transactional
	void updateTableByWhereCause(String tableName, String fieldString, String whereCondition);

	@Transactional
	void deleteTableByWhereCause(String tableName, String whereCondition);

	@Transactional
	void createDefaultTable(String tableName);

	@Transactional
	void dropTable(String tableName);

	@Transactional
	void alterColumn(String tableName, String alterType, String columnName, String columnType);

	@Transactional
	void executeSQL(String sql, String tableName);

	// 添加字段，并在cell_data_field和interface表中添加记录
	@Transactional
	void addColumn(String tableName, String fieldName, String columnType, CellDataField cellDataField,
			FieldInterface fieldInterface);

	// 删除字段，并删除cell_data_field和interface表中的记录
	@Transactional
	void deleteColumn(String tableName, String dname, String listId, String fieldId);

	// 修改字段，并修改cell_data_field和interface表中的记录
	@Transactional
	void modifyColumn(String tableName, String columnType, CellDataField cellDataField, FieldInterface fieldInterface);

	// 删除表，并删除cell_data_field和interface表中的记录
	@Transactional
	void deleteTable(List<String> tableids, List<String> tableNames);

	@Transactional
	void createTable(String tableName, CellDataTable cellDataTable);

	// 判断表是否存在
	boolean isExistsTable(String tableName);
}
