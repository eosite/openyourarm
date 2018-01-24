package com.glaf.theme.util;

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
public class SysThemeTmpDomainFactory {

    public static final String TABLENAME = "SYS_THEME_TMP_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("themeTmpId", "THEME_TMP_ID_");
        columnMap.put("themeTmpName", "THEME_TMP_NAME_");
        columnMap.put("themeTmpCode", "THEME_TMP_CODE_");
        columnMap.put("thumbnail", "THUMBNAIL_");
        columnMap.put("ui", "UI_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");
        columnMap.put("publisher", "PUBLISHER_");
        columnMap.put("publishTime", "PUBLISH_TIME_");
        columnMap.put("status", "STATUS_");
        columnMap.put("ver", "VER_");
        columnMap.put("defaultFlag", "DEFAULT_FLAG_");

	javaTypeMap.put("themeTmpId", "String");
        javaTypeMap.put("themeTmpName", "String");
        javaTypeMap.put("themeTmpCode", "String");
        javaTypeMap.put("thumbnail", "Blob");
        javaTypeMap.put("ui", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createTime", "Date");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateTime", "Date");
        javaTypeMap.put("deleteFlag", "Integer");
        javaTypeMap.put("publisher", "String");
        javaTypeMap.put("publishTime", "Date");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("ver", "Integer");
        javaTypeMap.put("defaultFlag", "Integer");
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
        tableDefinition.setName("SysThemeTmp");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("themeTmpId");
        idColumn.setColumnName("THEME_TMP_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition themeTmpName = new ColumnDefinition();
        themeTmpName.setName("themeTmpName");
        themeTmpName.setColumnName("THEME_TMP_NAME_");
        themeTmpName.setJavaType("String");
        themeTmpName.setLength(50);
        tableDefinition.addColumn(themeTmpName);

        ColumnDefinition themeTmpCode = new ColumnDefinition();
        themeTmpCode.setName("themeTmpCode");
        themeTmpCode.setColumnName("THEME_TMP_CODE_");
        themeTmpCode.setJavaType("String");
        themeTmpCode.setLength(30);
        tableDefinition.addColumn(themeTmpCode);

        ColumnDefinition thumbnail = new ColumnDefinition();
        thumbnail.setName("thumbnail");
        thumbnail.setColumnName("THUMBNAIL_");
        thumbnail.setJavaType("Blob");
        tableDefinition.addColumn(thumbnail);

        ColumnDefinition ui = new ColumnDefinition();
        ui.setName("ui");
        ui.setColumnName("UI_");
        ui.setJavaType("String");
        ui.setLength(20);
        tableDefinition.addColumn(ui);

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

        ColumnDefinition publisher = new ColumnDefinition();
        publisher.setName("publisher");
        publisher.setColumnName("PUBLISHER_");
        publisher.setJavaType("String");
        publisher.setLength(30);
        tableDefinition.addColumn(publisher);

        ColumnDefinition publishTime = new ColumnDefinition();
        publishTime.setName("publishTime");
        publishTime.setColumnName("PUBLISH_TIME_");
        publishTime.setJavaType("Date");
        tableDefinition.addColumn(publishTime);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS_");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition ver = new ColumnDefinition();
        ver.setName("ver");
        ver.setColumnName("VER_");
        ver.setJavaType("Integer");
        tableDefinition.addColumn(ver);
        
        ColumnDefinition defaultFlag = new ColumnDefinition();
        defaultFlag.setName("defaultFlag");
        defaultFlag.setColumnName("DEFAULT_FLAG_");
        defaultFlag.setJavaType("Integer");
        tableDefinition.addColumn(defaultFlag);

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

    private SysThemeTmpDomainFactory() {

    }

}
