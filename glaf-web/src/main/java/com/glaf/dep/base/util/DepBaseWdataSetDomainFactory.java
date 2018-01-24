package com.glaf.dep.base.util;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Resources;
import com.glaf.core.xml.XmlMappingReader;

/**
 * 
 * 实体数据工厂类
 *
 */
public class DepBaseWdataSetDomainFactory {

	public static final String TABLENAME = "DEP_BASE_WDATASET";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID_");
		columnMap.put("dataSetCode", "DATASET_CODE_");
		columnMap.put("dataSetName", "DATASET_NAME_");
		columnMap.put("nodeId", "NODEID_");
		columnMap.put("dataSetDesc", "DATASET_DESC_");
		columnMap.put("ruleJson", "RULEJSON_");
		columnMap.put("tableName", "TABLE_NAME_");
		columnMap.put("dataTableName", "DATATABLE_NAME_");
		columnMap.put("wtype", "WTYPE_");
		columnMap.put("ver", "VER_");
		columnMap.put("creator", "CREATOR_");
		columnMap.put("createDatetime", "CREATEDATETIME_");
		columnMap.put("modifier", "MODIFIER_");
		columnMap.put("modifyDatetime", "MODIFYDATETIME_");
		columnMap.put("delFlag", "DELFLAG_");

		javaTypeMap.put("id", "Long");
		javaTypeMap.put("dataSetCode", "String");
		javaTypeMap.put("dataSetName", "String");
		javaTypeMap.put("nodeId", "String");
		javaTypeMap.put("dataSetDesc", "String");
		javaTypeMap.put("ruleJson", "String");
		javaTypeMap.put("tableName", "String");
		javaTypeMap.put("dataTableName", "String");
		javaTypeMap.put("wtype", "String");
		javaTypeMap.put("ver", "Integer");
		javaTypeMap.put("creator", "String");
		javaTypeMap.put("createDatetime", "Date");
		javaTypeMap.put("modifier", "String");
		javaTypeMap.put("modifyDatetime", "Date");
		javaTypeMap.put("delFlag", "String");
	}

	public static Map<String, String> getColumnMap() {
		return columnMap;
	}

	public static Map<String, String> getJavaTypeMap() {
		return javaTypeMap;
	}

	public static TableDefinition getTableDefinition() {
		return getTableDefinition(TABLENAME);
	}

	public static TableDefinition getTableDefinition(String tableName) {
		tableName = tableName.toUpperCase();
		TableDefinition tableDefinition = new TableDefinition();
		tableDefinition.setTableName(tableName);
		tableDefinition.setName("DepBaseWdataSet");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID_");
		idColumn.setJavaType("Long");
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition dataSetCode = new ColumnDefinition();
		dataSetCode.setName("dataSetCode");
		dataSetCode.setColumnName("DATASET_CODE_");
		dataSetCode.setJavaType("String");
		dataSetCode.setLength(50);
		tableDefinition.addColumn(dataSetCode);

		ColumnDefinition dataSetName = new ColumnDefinition();
		dataSetName.setName("dataSetName");
		dataSetName.setColumnName("DATASET_NAME_");
		dataSetName.setJavaType("String");
		dataSetName.setLength(50);
		tableDefinition.addColumn(dataSetName);

		ColumnDefinition dataSetDesc = new ColumnDefinition();
		dataSetDesc.setName("dataSetDesc");
		dataSetDesc.setColumnName("DATASET_DESC_");
		dataSetDesc.setJavaType("String");
		dataSetDesc.setLength(150);
		tableDefinition.addColumn(dataSetDesc);

		ColumnDefinition ruleJson = new ColumnDefinition();
		ruleJson.setName("ruleJson");
		ruleJson.setColumnName("RULEJSON_");
		ruleJson.setJavaType("String");
		ruleJson.setLength(255);
		tableDefinition.addColumn(ruleJson);

		ColumnDefinition tableName0 = new ColumnDefinition();
		tableName0.setName("tableName");
		tableName0.setColumnName("TABLE_NAME_");
		tableName0.setJavaType("String");
		tableName0.setLength(50);
		tableDefinition.addColumn(tableName0);

		ColumnDefinition dataTableName = new ColumnDefinition();
		dataTableName.setName("dataTableName");
		dataTableName.setColumnName("DATATABLE_NAME_");
		dataTableName.setJavaType("String");
		dataTableName.setLength(30);
		tableDefinition.addColumn(dataTableName);

		ColumnDefinition wtype = new ColumnDefinition();
		wtype.setName("wtype");
		wtype.setColumnName("WTYPE_");
		wtype.setJavaType("String");
		wtype.setLength(1);
		tableDefinition.addColumn(wtype);

		ColumnDefinition ver = new ColumnDefinition();
		ver.setName("ver");
		ver.setColumnName("VER_");
		ver.setJavaType("Integer");
		tableDefinition.addColumn(ver);

		ColumnDefinition creator = new ColumnDefinition();
		creator.setName("creator");
		creator.setColumnName("CREATOR_");
		creator.setJavaType("String");
		creator.setLength(20);
		tableDefinition.addColumn(creator);

		ColumnDefinition createDatetime = new ColumnDefinition();
		createDatetime.setName("createDatetime");
		createDatetime.setColumnName("CREATEDATETIME_");
		createDatetime.setJavaType("Date");
		tableDefinition.addColumn(createDatetime);

		ColumnDefinition modifier = new ColumnDefinition();
		modifier.setName("modifier");
		modifier.setColumnName("MODIFIER_");
		modifier.setJavaType("String");
		modifier.setLength(20);
		tableDefinition.addColumn(modifier);

		ColumnDefinition modifyDatetime = new ColumnDefinition();
		modifyDatetime.setName("modifyDatetime");
		modifyDatetime.setColumnName("MODIFYDATETIME_");
		modifyDatetime.setJavaType("Date");
		tableDefinition.addColumn(modifyDatetime);

		ColumnDefinition delFlag = new ColumnDefinition();
		delFlag.setName("delFlag");
		delFlag.setColumnName("DELFLAG_");
		delFlag.setJavaType("String");
		delFlag.setLength(1);
		tableDefinition.addColumn(delFlag);

		return tableDefinition;
	}

	public static TableDefinition createTable() {
		TableDefinition tableDefinition = getTableDefinition(TABLENAME);
		if (!DBUtils.tableExists(TABLENAME)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static TableDefinition createTable(String tableName) {
		TableDefinition tableDefinition = getTableDefinition(tableName);
		if (!DBUtils.tableExists(tableName)) {
			DBUtils.createTable(tableDefinition);
		} else {
			DBUtils.alterTable(tableDefinition);
		}
		return tableDefinition;
	}

	public static void processDataRequest(DataRequest dataRequest) {
		if (dataRequest != null) {
			if (dataRequest.getFilter() != null) {
				if (dataRequest.getFilter().getField() != null) {
					dataRequest.getFilter().setColumn(columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter().setJavaType(javaTypeMap.get(dataRequest.getFilter().getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter().getFilters();
				for (FilterDescriptor filter : filters) {
					filter.setParent(dataRequest.getFilter());
					if (filter.getField() != null) {
						filter.setColumn(columnMap.get(filter.getField()));
						filter.setJavaType(javaTypeMap.get(filter.getField()));
					}

					List<FilterDescriptor> subFilters = filter.getFilters();
					for (FilterDescriptor f : subFilters) {
						f.setColumn(columnMap.get(f.getField()));
						f.setJavaType(javaTypeMap.get(f.getField()));
						f.setParent(filter);
					}
				}
			}
		}
	}

	public static TableModel readTableModel() {
		if (tableModel == null) {
			XmlMappingReader reader = new XmlMappingReader();
			InputStream inputStream = null;
			try {
				inputStream = Resources.getResourceAsStream("com/glaf/dep/base/domain/DepBaseWdataSet.mapping.xml");
				tableModel = reader.read(inputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		TableModel bean = new TableModel();
		try {
			BeanUtils.copyProperties(bean, tableModel);
		} catch (Exception ex) {
			org.springframework.beans.BeanUtils.copyProperties(tableModel, bean);
		}
		return bean;
	}

	private DepBaseWdataSetDomainFactory() {

	}

}
