package com.glaf.table.tool.service;

import java.util.List;
import java.util.Map;

import com.glaf.table.tool.model.FieldModel;
import com.glaf.table.tool.model.TableModel;

public interface IBaseTable2DefinedService {
	
	/**
	 * 根据表名查询表信息
	 * @param tableName
	 * @return
	 */
	TableModel getTable(String tableName);
	
	/**
	 * 根据条件查询所有符合条件的表
	 * @param params
	 * @return
	 */
	List<TableModel> getTables(Map<String,String> params);
	
	/**
	 * 根据表名获取字段列表
	 * 
	 * @param tableName
	 * @return
	 */
	List<FieldModel> getFieldsByTableName(String tableName);

	/**
	 * 根据表名获取字段
	 * 
	 * @param tableName
	 * @return Map<字段名,字段对象>
	 */
	Map<String, FieldModel> getFieldsMapByTableName(String tableName);

	/**
	 * 保存，生成表及字段映射关系
	 * @param tableName 表名
	 * @param tableDescription 表名描述，即表名中文信息
	 * @param saveList 字段保存列表
	 */
	void save(String tableName, String tableDescription,
			List<FieldModel> saveList);
	
	/**
	 * 清除表映射信息
	 * @param tableName
	 */
	void clearMapping(String tableName);
}
