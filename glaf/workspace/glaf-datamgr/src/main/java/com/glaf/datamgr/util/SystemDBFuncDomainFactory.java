package com.glaf.datamgr.util;

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
public class SystemDBFuncDomainFactory {

    public static final String TABLENAME = "SYSTEM_DB_FUNC_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("code", "CODE_");
        columnMap.put("name", "NAME_");
        columnMap.put("publicParams", "PUBLIC_PARAMS_");
        columnMap.put("desc", "DESC_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("publicParams", "String");
        javaTypeMap.put("desc", "String");
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
        tableDefinition.setName("SystemDBFunc");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition code = new ColumnDefinition();
        code.setName("code");
        code.setColumnName("CODE_");
        code.setJavaType("String");
        code.setLength(50);
        tableDefinition.addColumn(code);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition publicParams = new ColumnDefinition();
        publicParams.setName("publicParams");
        publicParams.setColumnName("PUBLIC_PARAMS_");
        publicParams.setJavaType("String");
        publicParams.setLength(0);
        tableDefinition.addColumn(publicParams);

        ColumnDefinition desc = new ColumnDefinition();
        desc.setName("desc");
        desc.setColumnName("DESC_");
        desc.setJavaType("String");
        desc.setLength(150);
        tableDefinition.addColumn(desc);

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

    private SystemDBFuncDomainFactory() {

    }

}
