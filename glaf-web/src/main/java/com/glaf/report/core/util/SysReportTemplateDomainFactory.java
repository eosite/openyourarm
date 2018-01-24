package com.glaf.report.core.util;

import java.io.InputStream;
import java.util.List;
import java.util.Map;
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
public class SysReportTemplateDomainFactory {

	public static final String TABLENAME = "SYSREPORTTEMPLATE";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	protected static TableModel tableModel;

	static {
		columnMap.put("id", "ID");
		columnMap.put("reportTemplateName", "REPORT_TEMPLATE_NAME");
		columnMap.put("reportTemplateFile", "REPORT_TEMPLATE_FILE");
		columnMap.put("ctime", "CTIME");
		columnMap.put("utime", "UTIME");

		javaTypeMap.put("id", "String");
		javaTypeMap.put("reportTemplateName", "String");
		javaTypeMap.put("reportTemplateFile", "Blob");
		javaTypeMap.put("ctime", "Date");
		javaTypeMap.put("utime", "Date");
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
		tableDefinition.setName("SysReportTemplate");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("id");
		idColumn.setColumnName("ID");
		idColumn.setJavaType("String");
		idColumn.setLength(100);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition reportTemplateName = new ColumnDefinition();
		reportTemplateName.setName("reportTemplateName");
		reportTemplateName.setColumnName("REPORT_TEMPLATE_NAME");
		reportTemplateName.setJavaType("String");
		reportTemplateName.setLength(100);
		tableDefinition.addColumn(reportTemplateName);

		ColumnDefinition reportTemplateFile = new ColumnDefinition();
		reportTemplateFile.setName("reportTemplateFile");
		reportTemplateFile.setColumnName("REPORT_TEMPLATE_FILE");
		reportTemplateFile.setJavaType("Blob");
		tableDefinition.addColumn(reportTemplateFile);

		ColumnDefinition ctime = new ColumnDefinition();
		ctime.setName("ctime");
		ctime.setColumnName("CTIME");
		ctime.setJavaType("Date");
		tableDefinition.addColumn(ctime);

		ColumnDefinition utime = new ColumnDefinition();
		utime.setName("utime");
		utime.setColumnName("UTIME");
		utime.setJavaType("Date");
		tableDefinition.addColumn(utime);

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
						.getResourceAsStream("com/glaf/report/domain/SysReportTemplate.mapping.xml");
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

	private SysReportTemplateDomainFactory() {

	}

}
