package com.glaf.teim.util;

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
public class EimServerDataImpDomainFactory {

    public static final String TABLENAME = "EIM_SERVER_DATA_IMP";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("name", "NAME_");
        columnMap.put("appId", "APP_ID_");
        columnMap.put("tmpId", "TMP_ID_");
        columnMap.put("emptyTable", "EMPTY_TABLE_");
        columnMap.put("preSql", "PRE_SQL_");
        columnMap.put("incrementFlag", "INCREMENT_FLAG_");
        columnMap.put("params", "PARAMS_");
        columnMap.put("targetDatabase", "TARGET_DATABASE_");
        columnMap.put("targetTable", "TARGET_TABLE_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("appId", "String");
        javaTypeMap.put("tmpId", "String");
        javaTypeMap.put("emptyTable", "Integer");
        javaTypeMap.put("preSql", "Clob");
        javaTypeMap.put("incrementFlag", "Integer");
        javaTypeMap.put("params", "Clob");
        javaTypeMap.put("targetDatabase", "String");
        javaTypeMap.put("targetTable", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createTime", "Date");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateTime", "Date");
        javaTypeMap.put("deleteFlag", "Integer");
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
        tableDefinition.setName("EimServerDataImp");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(150);
        tableDefinition.addColumn(name);

        ColumnDefinition appId = new ColumnDefinition();
        appId.setName("appId");
        appId.setColumnName("APP_ID_");
        appId.setJavaType("String");
        appId.setLength(50);
        tableDefinition.addColumn(appId);

        ColumnDefinition tmpId = new ColumnDefinition();
        tmpId.setName("tmpId");
        tmpId.setColumnName("TMP_ID_");
        tmpId.setJavaType("String");
        tmpId.setLength(50);
        tableDefinition.addColumn(tmpId);

        ColumnDefinition emptyTable = new ColumnDefinition();
        emptyTable.setName("emptyTable");
        emptyTable.setColumnName("EMPTY_TABLE_");
        emptyTable.setJavaType("Integer");
        tableDefinition.addColumn(emptyTable);

        ColumnDefinition preSql = new ColumnDefinition();
        preSql.setName("preSql");
        preSql.setColumnName("PRE_SQL_");
        preSql.setJavaType("Clob");
        tableDefinition.addColumn(preSql);

        ColumnDefinition incrementFlag = new ColumnDefinition();
        incrementFlag.setName("incrementFlag");
        incrementFlag.setColumnName("INCREMENT_FLAG_");
        incrementFlag.setJavaType("Integer");
        tableDefinition.addColumn(incrementFlag);

        ColumnDefinition params = new ColumnDefinition();
        params.setName("params");
        params.setColumnName("PARAMS_");
        params.setJavaType("Clob");
        tableDefinition.addColumn(params);

        ColumnDefinition targetDatabase = new ColumnDefinition();
        targetDatabase.setName("targetDatabase");
        targetDatabase.setColumnName("TARGET_DATABASE_");
        targetDatabase.setJavaType("String");
        targetDatabase.setLength(50);
        tableDefinition.addColumn(targetDatabase);

        ColumnDefinition targetTable = new ColumnDefinition();
        targetTable.setName("targetTable");
        targetTable.setColumnName("TARGET_TABLE_");
        targetTable.setJavaType("String");
        targetTable.setLength(50);
        tableDefinition.addColumn(targetTable);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY_");
        createBy.setJavaType("String");
        createBy.setLength(30);
        tableDefinition.addColumn(createBy);

        ColumnDefinition createTime = new ColumnDefinition();
        createTime.setName("createTime");
        createTime.setColumnName("CREATETIME_");
        createTime.setJavaType("Date");
        tableDefinition.addColumn(createTime);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY_");
        updateBy.setJavaType("String");
        updateBy.setLength(30);
        tableDefinition.addColumn(updateBy);

        ColumnDefinition updateTime = new ColumnDefinition();
        updateTime.setName("updateTime");
        updateTime.setColumnName("UPDATETIME_");
        updateTime.setJavaType("Date");
        tableDefinition.addColumn(updateTime);

        ColumnDefinition deleteFlag = new ColumnDefinition();
        deleteFlag.setName("deleteFlag");
        deleteFlag.setColumnName("DELETE_FLAG_");
        deleteFlag.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag);

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

    private EimServerDataImpDomainFactory() {

    }

}
