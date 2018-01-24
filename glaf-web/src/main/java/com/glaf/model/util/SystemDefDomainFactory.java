package com.glaf.model.util;

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
public class SystemDefDomainFactory {

    public static final String TABLENAME = "SYSTEM_DEF_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("sysId", "SYS_ID_");
        columnMap.put("sysName", "SYS_NAME_");
        columnMap.put("sysCode", "SYS_CODE_");
        columnMap.put("sysDesc", "SYS_DESC_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");
        columnMap.put("version", "VERSION_");
        columnMap.put("publisher", "PUBLISHER_");
        columnMap.put("publishTime", "PUBLISHTIME_");

	javaTypeMap.put("sysId", "String");
        javaTypeMap.put("sysName", "String");
        javaTypeMap.put("sysCode", "String");
        javaTypeMap.put("sysDesc", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createTime", "Date");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateTime", "Date");
        javaTypeMap.put("deleteFlag", "Integer");
        javaTypeMap.put("version", "String");
        javaTypeMap.put("publisher", "String");
        javaTypeMap.put("publishTime", "Date");
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
        tableDefinition.setName("SystemDef");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("sysId");
        idColumn.setColumnName("SYS_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition sysName = new ColumnDefinition();
        sysName.setName("sysName");
        sysName.setColumnName("SYS_NAME_");
        sysName.setJavaType("String");
        sysName.setLength(150);
        tableDefinition.addColumn(sysName);

        ColumnDefinition sysCode = new ColumnDefinition();
        sysCode.setName("sysCode");
        sysCode.setColumnName("SYS_CODE_");
        sysCode.setJavaType("String");
        sysCode.setLength(50);
        tableDefinition.addColumn(sysCode);

        ColumnDefinition sysDesc = new ColumnDefinition();
        sysDesc.setName("sysDesc");
        sysDesc.setColumnName("SYS_DESC_");
        sysDesc.setJavaType("String");
        sysDesc.setLength(500);
        tableDefinition.addColumn(sysDesc);

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

        ColumnDefinition version = new ColumnDefinition();
        version.setName("version");
        version.setColumnName("VERSION_");
        version.setJavaType("String");
        version.setLength(30);
        tableDefinition.addColumn(version);

        ColumnDefinition publisher = new ColumnDefinition();
        publisher.setName("publisher");
        publisher.setColumnName("PUBLISHER_");
        publisher.setJavaType("String");
        publisher.setLength(30);
        tableDefinition.addColumn(publisher);

        ColumnDefinition publishTime = new ColumnDefinition();
        publishTime.setName("publishTime");
        publishTime.setColumnName("PUBLISHTIME_");
        publishTime.setJavaType("Date");
        tableDefinition.addColumn(publishTime);

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

    private SystemDefDomainFactory() {

    }

}
