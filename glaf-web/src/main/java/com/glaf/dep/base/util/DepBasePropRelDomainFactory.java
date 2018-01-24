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
public class DepBasePropRelDomainFactory {

	public static final String TABLENAME = "DEP_BASE_PROPREL";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("ruleId", "RULEID_");
		columnMap.put("relRuleId", "RELRULEID_");
		columnMap.put("relType", "RELTYPE_");
		columnMap.put("creator", "CREATOR_");
		columnMap.put("createDateTime", "CREATEDATETIME_");

		javaTypeMap.put("ruleId", "String");
		javaTypeMap.put("relRuleId", "String");
		javaTypeMap.put("relType", "String");
		javaTypeMap.put("creator", "String");
		javaTypeMap.put("createDateTime", "Date");
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
		tableDefinition.setName("DepBasePropRel");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("ruleId");
		idColumn.setColumnName("RULEID_");
		idColumn.setJavaType("String");
		idColumn.setLength(50);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition relRuleId = new ColumnDefinition();
		relRuleId.setName("relRuleId");
		relRuleId.setColumnName("RELRULEID_");
		relRuleId.setJavaType("String");
		relRuleId.setLength(50);
		tableDefinition.addColumn(relRuleId);

		ColumnDefinition relType = new ColumnDefinition();
		relType.setName("relType");
		relType.setColumnName("RELTYPE_");
		relType.setJavaType("String");
		relType.setLength(20);
		tableDefinition.addColumn(relType);

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
						.getResourceAsStream("com/glaf/dep/base/domain/DepBasePropRel.mapping.xml");
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

	private DepBasePropRelDomainFactory() {

	}

}
