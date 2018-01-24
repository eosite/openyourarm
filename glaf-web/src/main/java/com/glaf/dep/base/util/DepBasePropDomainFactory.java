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
public class DepBasePropDomainFactory {

	public static final String TABLENAME = "DEP_BASE_PROP";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("ruleId", "RULEID_");
		columnMap.put("ruleCode", "RULECODE");
		columnMap.put("ruleName", "RULENAME_");
		columnMap.put("ruleDesc", "RULEDESC");
		columnMap.put("sysCategory", "SYSCATEGORY_");
		columnMap.put("useCategory", "USECATEGORY_");
		columnMap.put("openFlag", "OPENFLAG_");
		columnMap.put("orderNo", "ORDERNO_");
		columnMap.put("readOnly", "READONLY_");
		columnMap.put("repeatFlag", "REPEATFLAG_");
		columnMap.put("notNull", "NOTNULL_");
		columnMap.put("inputType", "INPUTTYPE_");
		columnMap.put("defaultVal", "DEFAULTVAL_");
		columnMap.put("extJson", "EXTJSON_");
		columnMap.put("creator", "CREATOR_");
		columnMap.put("createDateTime", "CREATEDATETIME_");
		columnMap.put("modifier", "MODIFIER_");
		columnMap.put("modifyDateTime", "MODIFYDATETIME_");
		columnMap.put("delFlag", "DELFLAG_");

		javaTypeMap.put("ruleId", "String");
		javaTypeMap.put("ruleCode", "String");
		javaTypeMap.put("ruleName", "String");
		javaTypeMap.put("ruleDesc", "String");
		javaTypeMap.put("sysCategory", "String");
		javaTypeMap.put("useCategory", "String");
		javaTypeMap.put("openFlag", "String");
		javaTypeMap.put("orderNo", "Integer");
		javaTypeMap.put("readOnly", "String");
		javaTypeMap.put("repeatFlag", "String");
		javaTypeMap.put("notNull", "String");
		javaTypeMap.put("inputType", "String");
		javaTypeMap.put("defaultVal", "String");
		javaTypeMap.put("extJson", "String");
		javaTypeMap.put("creator", "String");
		javaTypeMap.put("createDateTime", "Date");
		javaTypeMap.put("modifier", "String");
		javaTypeMap.put("modifyDateTime", "Date");
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
		tableDefinition.setName("DepBaseProp");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("ruleId");
		idColumn.setColumnName("RULEID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition ruleCode = new ColumnDefinition();
		ruleCode.setName("ruleCode");
		ruleCode.setColumnName("RULECODE");
		ruleCode.setJavaType("String");
		ruleCode.setLength(50);
		tableDefinition.addColumn(ruleCode);

		ColumnDefinition ruleName = new ColumnDefinition();
		ruleName.setName("ruleName");
		ruleName.setColumnName("RULENAME_");
		ruleName.setJavaType("String");
		ruleName.setLength(50);
		tableDefinition.addColumn(ruleName);

		ColumnDefinition ruleDesc = new ColumnDefinition();
		ruleDesc.setName("ruleDesc");
		ruleDesc.setColumnName("RULEDESC");
		ruleDesc.setJavaType("String");
		ruleDesc.setLength(300);
		tableDefinition.addColumn(ruleDesc);

		ColumnDefinition sysCategory = new ColumnDefinition();
		sysCategory.setName("sysCategory");
		sysCategory.setColumnName("SYSCATEGORY_");
		sysCategory.setJavaType("String");
		sysCategory.setLength(50);
		tableDefinition.addColumn(sysCategory);

		ColumnDefinition useCategory = new ColumnDefinition();
		useCategory.setName("useCategory");
		useCategory.setColumnName("USECATEGORY_");
		useCategory.setJavaType("String");
		useCategory.setLength(50);
		tableDefinition.addColumn(useCategory);

		ColumnDefinition openFlag = new ColumnDefinition();
		openFlag.setName("openFlag");
		openFlag.setColumnName("OPENFLAG_");
		openFlag.setJavaType("String");
		openFlag.setLength(1);
		tableDefinition.addColumn(openFlag);

		ColumnDefinition orderNo = new ColumnDefinition();
		orderNo.setName("orderNo");
		orderNo.setColumnName("ORDERNO_");
		orderNo.setJavaType("Integer");
		tableDefinition.addColumn(orderNo);

		ColumnDefinition readOnly = new ColumnDefinition();
		readOnly.setName("readOnly");
		readOnly.setColumnName("READONLY_");
		readOnly.setJavaType("String");
		readOnly.setLength(1);
		tableDefinition.addColumn(readOnly);

		ColumnDefinition repeatFlag = new ColumnDefinition();
		repeatFlag.setName("repeatFlag");
		repeatFlag.setColumnName("REPEATFLAG_");
		repeatFlag.setJavaType("String");
		repeatFlag.setLength(1);
		tableDefinition.addColumn(repeatFlag);

		ColumnDefinition notNull = new ColumnDefinition();
		notNull.setName("notNull");
		notNull.setColumnName("NOTNULL_");
		notNull.setJavaType("String");
		notNull.setLength(1);
		tableDefinition.addColumn(notNull);

		ColumnDefinition inputType = new ColumnDefinition();
		inputType.setName("inputType");
		inputType.setColumnName("INPUTTYPE_");
		inputType.setJavaType("String");
		inputType.setLength(20);
		tableDefinition.addColumn(inputType);

		ColumnDefinition defaultVal = new ColumnDefinition();
		defaultVal.setName("defaultVal");
		defaultVal.setColumnName("DEFAULTVAL_");
		defaultVal.setJavaType("String");
		defaultVal.setLength(100);
		tableDefinition.addColumn(defaultVal);

		ColumnDefinition extJson = new ColumnDefinition();
		extJson.setName("extJson");
		extJson.setColumnName("EXTJSON_");
		extJson.setJavaType("String");
		extJson.setLength(255);
		tableDefinition.addColumn(extJson);

		ColumnDefinition creator = new ColumnDefinition();
		creator.setName("creator");
		creator.setColumnName("CREATOR_");
		creator.setJavaType("String");
		creator.setLength(20);
		tableDefinition.addColumn(creator);

		ColumnDefinition createDateTime = new ColumnDefinition();
		createDateTime.setName("createDateTime");
		createDateTime.setColumnName("CREATEDATETIME_");
		createDateTime.setJavaType("Date");
		tableDefinition.addColumn(createDateTime);

		ColumnDefinition modifier = new ColumnDefinition();
		modifier.setName("modifier");
		modifier.setColumnName("MODIFIER_");
		modifier.setJavaType("String");
		modifier.setLength(20);
		tableDefinition.addColumn(modifier);

		ColumnDefinition modifyDateTime = new ColumnDefinition();
		modifyDateTime.setName("modifyDateTime");
		modifyDateTime.setColumnName("MODIFYDATETIME_");
		modifyDateTime.setJavaType("Date");
		tableDefinition.addColumn(modifyDateTime);

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
					dataRequest.getFilter().setColumn(
							columnMap.get(dataRequest.getFilter().getField()));
					dataRequest.getFilter()
							.setJavaType(
									javaTypeMap.get(dataRequest.getFilter()
											.getField()));
				}

				List<FilterDescriptor> filters = dataRequest.getFilter()
						.getFilters();
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
				inputStream = Resources
						.getResourceAsStream("com/glaf/dep/base/domain/DepBaseProp.mapping.xml");
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
			org.springframework.beans.BeanUtils
					.copyProperties(tableModel, bean);
		}
		return bean;
	}

	private DepBasePropDomainFactory() {

	}

}
