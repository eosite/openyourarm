package com.glaf.base.modules.uis.util;

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
public class UisAppRegisterDomainFactory {

    public static final String TABLENAME = "UIS_APP_REGISTER";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("appId", "APP_ID_");
        columnMap.put("appName", "APP_NAME_");
        columnMap.put("loginAddress", "LOGIN_ADDRESS_");
        columnMap.put("ssoServiceId", "SSO_SERVICE_ID_");
        columnMap.put("desc", "DESC_");
        columnMap.put("logoPic", "LOGO_PIC_");
        columnMap.put("status", "STATUS_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("appId", "String");
        javaTypeMap.put("appName", "String");
        javaTypeMap.put("loginAddress", "String");
        javaTypeMap.put("ssoServiceId", "String");
        javaTypeMap.put("desc", "Clob");
        javaTypeMap.put("logoPic", "Blob");
        javaTypeMap.put("status", "Integer");
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
        tableDefinition.setName("UisAppRegister");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("appId");
        idColumn.setColumnName("APP_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition appName = new ColumnDefinition();
        appName.setName("appName");
        appName.setColumnName("APP_NAME_");
        appName.setJavaType("String");
        appName.setLength(150);
        tableDefinition.addColumn(appName);

        ColumnDefinition loginAddress = new ColumnDefinition();
        loginAddress.setName("loginAddress");
        loginAddress.setColumnName("LOGIN_ADDRESS_");
        loginAddress.setJavaType("String");
        loginAddress.setLength(300);
        tableDefinition.addColumn(loginAddress);

        ColumnDefinition ssoServiceId = new ColumnDefinition();
        ssoServiceId.setName("ssoServiceId");
        ssoServiceId.setColumnName("SSO_SERVICE_ID_");
        ssoServiceId.setJavaType("String");
        ssoServiceId.setLength(300);
        tableDefinition.addColumn(ssoServiceId);

        ColumnDefinition desc = new ColumnDefinition();
        desc.setName("desc");
        desc.setColumnName("DESC_");
        desc.setJavaType("Clob");
        tableDefinition.addColumn(desc);

        ColumnDefinition logoPic = new ColumnDefinition();
        logoPic.setName("logoPic");
        logoPic.setColumnName("LOGO_PIC_");
        logoPic.setJavaType("Blob");
        tableDefinition.addColumn(logoPic);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS_");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

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

    private UisAppRegisterDomainFactory() {

    }

}
