package com.glaf.etl.util;

import java.util.*;
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
public class EtlTransferTaskTargetDomainFactory {

    public static final String TABLENAME = "ETL_TRANSFER_TASK_TARGET_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id_", "ID_");
        columnMap.put("taskId_", "TASK_ID_");
        columnMap.put("targetId_", "TARGET_ID_");
        columnMap.put("preSQL_", "PRE_SQL_");
        columnMap.put("postSQL_", "POST_SQL_");
        columnMap.put("taskConnId_", "TASK_CONN_ID_");
        columnMap.put("taskDatabaseName_", "TASK_DATABASENAME_");
        columnMap.put("taskTableName_", "TASK_TABLENAME_");
        columnMap.put("tableNamePrefix_", "TABLENAME_PREFIX_");
        columnMap.put("preTuncateFlag_", "PRE_TRUNCATE_FLAG_");
        columnMap.put("treatment_methd_", "TREATMENT_METHD_");
        columnMap.put("createBy_", "CREATEBY_");
        columnMap.put("createTime_", "CREATETIME_");
        columnMap.put("updateBy_", "UPDATEBY_");
        columnMap.put("updateTime_", "UPDATETIME_");

	javaTypeMap.put("id_", "String");
        javaTypeMap.put("taskId_", "String");
        javaTypeMap.put("targetId_", "String");
        javaTypeMap.put("preSQL_", "byte[]");
        javaTypeMap.put("postSQL_", "byte[]");
        javaTypeMap.put("taskConnId_", "Long");
        javaTypeMap.put("taskDatabaseName_", "String");
        javaTypeMap.put("taskTableName_", "String");
        javaTypeMap.put("tableNamePrefix_", "String");
        javaTypeMap.put("preTuncateFlag_", "Integer");
        javaTypeMap.put("treatment_methd_", "Integer");
        javaTypeMap.put("createBy_", "String");
        javaTypeMap.put("createTime_", "Date");
        javaTypeMap.put("updateBy_", "String");
        javaTypeMap.put("updateTime_", "Date");
    }


    public static Map<String, String> getColumnMap() {
	return columnMap;
    }

    public static Map<String, String> getJavaTypeMap() {
	return javaTypeMap;
    }

    public static TableDefinition getTableDefinition(){
        return getTableDefinition(TABLENAME);
    }

    public static TableDefinition getTableDefinition(String tableName) {
        tableName = tableName.toUpperCase();
        TableDefinition tableDefinition = new TableDefinition();
        tableDefinition.setTableName(tableName);
        tableDefinition.setName("EtlTransferTaskTarget");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id_");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(64);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition taskId_ = new ColumnDefinition();
        taskId_.setName("taskId_");
        taskId_.setColumnName("TASK_ID_");
        taskId_.setJavaType("String");
        taskId_.setLength(64);
        tableDefinition.addColumn(taskId_);

        ColumnDefinition targetId_ = new ColumnDefinition();
        targetId_.setName("targetId_");
        targetId_.setColumnName("TARGET_ID_");
        targetId_.setJavaType("String");
        targetId_.setLength(64);
        tableDefinition.addColumn(targetId_);

        ColumnDefinition preSQL_ = new ColumnDefinition();
        preSQL_.setName("preSQL_");
        preSQL_.setColumnName("PRE_SQL_");
        preSQL_.setJavaType("byte[]");
        tableDefinition.addColumn(preSQL_);

        ColumnDefinition postSQL_ = new ColumnDefinition();
        postSQL_.setName("postSQL_");
        postSQL_.setColumnName("POST_SQL_");
        postSQL_.setJavaType("byte[]");
        tableDefinition.addColumn(postSQL_);

        ColumnDefinition taskConnId_ = new ColumnDefinition();
        taskConnId_.setName("taskConnId_");
        taskConnId_.setColumnName("TASK_CONN_ID_");
        taskConnId_.setJavaType("Long");
        tableDefinition.addColumn(taskConnId_);

        ColumnDefinition taskDatabaseName_ = new ColumnDefinition();
        taskDatabaseName_.setName("taskDatabaseName_");
        taskDatabaseName_.setColumnName("TASK_DATABASENAME_");
        taskDatabaseName_.setJavaType("String");
        taskDatabaseName_.setLength(30);
        tableDefinition.addColumn(taskDatabaseName_);

        ColumnDefinition taskTableName_ = new ColumnDefinition();
        taskTableName_.setName("taskTableName_");
        taskTableName_.setColumnName("TASK_TABLENAME_");
        taskTableName_.setJavaType("String");
        taskTableName_.setLength(50);
        tableDefinition.addColumn(taskTableName_);

        ColumnDefinition tableNamePrefix_ = new ColumnDefinition();
        tableNamePrefix_.setName("tableNamePrefix_");
        tableNamePrefix_.setColumnName("TABLENAME_PREFIX_");
        tableNamePrefix_.setJavaType("String");
        tableNamePrefix_.setLength(20);
        tableDefinition.addColumn(tableNamePrefix_);

        ColumnDefinition preTuncateFlag_ = new ColumnDefinition();
        preTuncateFlag_.setName("preTuncateFlag_");
        preTuncateFlag_.setColumnName("PRE_TRUNCATE_FLAG_");
        preTuncateFlag_.setJavaType("Integer");
        tableDefinition.addColumn(preTuncateFlag_);

        ColumnDefinition treatment_methd_ = new ColumnDefinition();
        treatment_methd_.setName("treatment_methd_");
        treatment_methd_.setColumnName("TREATMENT_METHD_");
        treatment_methd_.setJavaType("Integer");
        tableDefinition.addColumn(treatment_methd_);

        ColumnDefinition createBy_ = new ColumnDefinition();
        createBy_.setName("createBy_");
        createBy_.setColumnName("CREATEBY_");
        createBy_.setJavaType("String");
        createBy_.setLength(30);
        tableDefinition.addColumn(createBy_);

        ColumnDefinition createTime_ = new ColumnDefinition();
        createTime_.setName("createTime_");
        createTime_.setColumnName("CREATETIME_");
        createTime_.setJavaType("Date");
        tableDefinition.addColumn(createTime_);

        ColumnDefinition updateBy_ = new ColumnDefinition();
        updateBy_.setName("updateBy_");
        updateBy_.setColumnName("UPDATEBY_");
        updateBy_.setJavaType("String");
        updateBy_.setLength(30);
        tableDefinition.addColumn(updateBy_);

        ColumnDefinition updateTime_ = new ColumnDefinition();
        updateTime_.setName("updateTime_");
        updateTime_.setColumnName("UPDATETIME_");
        updateTime_.setJavaType("Date");
        tableDefinition.addColumn(updateTime_);

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
				dataRequest.getFilter().setJavaType(
						javaTypeMap.get(dataRequest.getFilter().getField()));
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

    private EtlTransferTaskTargetDomainFactory() {

    }

}
