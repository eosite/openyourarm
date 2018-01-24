package com.glaf.convert.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;

import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;

/**
 * 
 * 实体数据工厂类
 *
 */
public class CvtRunHisDomainFactory {

	public static final String TABLENAME = "CVT_RUN_HIS";

	public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

	public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

	static {
		columnMap.put("taskCode", "TASKCODE_");
		columnMap.put("taskName", "TASKNAME_");
		columnMap.put("runTime", "RUNTIME_");
		columnMap.put("runFlag", "RUNFLAG_");
		columnMap.put("succCount", "SUCCCOUNT_");
		columnMap.put("failCount", "FAILCOUNT_");
		columnMap.put("log", "LOG_");

		javaTypeMap.put("taskCode", "String");
		javaTypeMap.put("taskName", "String");
		javaTypeMap.put("runTime", "Date");
		javaTypeMap.put("runFlag", "Integer");
		javaTypeMap.put("succCount", "Integer");
		javaTypeMap.put("failCount", "Integer");
		javaTypeMap.put("log", "String");
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
		tableDefinition.setName("CvtRunHis");

		ColumnDefinition idColumn = new ColumnDefinition();
		idColumn.setName("taskCode");
		idColumn.setColumnName("TASKCODE_");
		idColumn.setJavaType("String");
		idColumn.setLength(30);
		tableDefinition.setIdColumn(idColumn);

		ColumnDefinition taskName = new ColumnDefinition();
		taskName.setName("taskName");
		taskName.setColumnName("TASKNAME_");
		taskName.setJavaType("String");
		taskName.setLength(50);
		tableDefinition.addColumn(taskName);

		ColumnDefinition runTime = new ColumnDefinition();
		runTime.setName("runTime");
		runTime.setColumnName("RUNTIME_");
		runTime.setJavaType("Date");
		tableDefinition.addColumn(runTime);

		ColumnDefinition runFlag = new ColumnDefinition();
		runFlag.setName("runFlag");
		runFlag.setColumnName("RUNFLAG_");
		runFlag.setJavaType("Integer");
		tableDefinition.addColumn(runFlag);

		ColumnDefinition succCount = new ColumnDefinition();
		succCount.setName("succCount");
		succCount.setColumnName("SUCCCOUNT_");
		succCount.setJavaType("Integer");
		tableDefinition.addColumn(succCount);

		ColumnDefinition failCount = new ColumnDefinition();
		failCount.setName("failCount");
		failCount.setColumnName("FAILCOUNT_");
		failCount.setJavaType("Integer");
		tableDefinition.addColumn(failCount);

		ColumnDefinition log = new ColumnDefinition();
		log.setName("log");
		log.setColumnName("LOG_");
		log.setJavaType("String");
		log.setLength(0);
		tableDefinition.addColumn(log);

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

	private CvtRunHisDomainFactory() {

	}

}
