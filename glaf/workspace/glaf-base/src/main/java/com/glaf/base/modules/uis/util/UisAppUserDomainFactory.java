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
public class UisAppUserDomainFactory {

    public static final String TABLENAME = "UIS_APP_USER";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("userId", "USER_ID_");
        columnMap.put("appId", "APP_ID_");
        columnMap.put("userName", "USER_NAME_");
        columnMap.put("email", "EMAIL_");
        columnMap.put("tel", "TEL_");
        columnMap.put("mobile", "MOBILE_");
        columnMap.put("age", "AGE_");
        columnMap.put("desc", "DESC_");
        columnMap.put("sex", "SEX_");
        columnMap.put("qq", "QQ_");
        columnMap.put("weq", "WEQ_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("userId", "String");
        javaTypeMap.put("appId", "String");
        javaTypeMap.put("userName", "String");
        javaTypeMap.put("email", "String");
        javaTypeMap.put("tel", "String");
        javaTypeMap.put("mobile", "String");
        javaTypeMap.put("age", "Integer");
        javaTypeMap.put("desc", "Clob");
        javaTypeMap.put("sex", "Integer");
        javaTypeMap.put("qq", "String");
        javaTypeMap.put("weq", "String");
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
        tableDefinition.setName("UisAppUser");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition userId = new ColumnDefinition();
        userId.setName("userId");
        userId.setColumnName("USER_ID_");
        userId.setJavaType("String");
        userId.setLength(50);
        tableDefinition.addColumn(userId);

        ColumnDefinition appId = new ColumnDefinition();
        appId.setName("appId");
        appId.setColumnName("APP_ID_");
        appId.setJavaType("String");
        appId.setLength(50);
        tableDefinition.addColumn(appId);

        ColumnDefinition userName = new ColumnDefinition();
        userName.setName("userName");
        userName.setColumnName("USER_NAME_");
        userName.setJavaType("String");
        userName.setLength(50);
        tableDefinition.addColumn(userName);

        ColumnDefinition email = new ColumnDefinition();
        email.setName("email");
        email.setColumnName("EMAIL_");
        email.setJavaType("String");
        email.setLength(50);
        tableDefinition.addColumn(email);

        ColumnDefinition tel = new ColumnDefinition();
        tel.setName("tel");
        tel.setColumnName("TEL_");
        tel.setJavaType("String");
        tel.setLength(30);
        tableDefinition.addColumn(tel);

        ColumnDefinition mobile = new ColumnDefinition();
        mobile.setName("mobile");
        mobile.setColumnName("MOBILE_");
        mobile.setJavaType("String");
        mobile.setLength(30);
        tableDefinition.addColumn(mobile);

        ColumnDefinition age = new ColumnDefinition();
        age.setName("age");
        age.setColumnName("AGE_");
        age.setJavaType("Integer");
        tableDefinition.addColumn(age);

        ColumnDefinition desc = new ColumnDefinition();
        desc.setName("desc");
        desc.setColumnName("DESC_");
        desc.setJavaType("Clob");
        tableDefinition.addColumn(desc);

        ColumnDefinition sex = new ColumnDefinition();
        sex.setName("sex");
        sex.setColumnName("SEX_");
        sex.setJavaType("Integer");
        tableDefinition.addColumn(sex);

        ColumnDefinition qq = new ColumnDefinition();
        qq.setName("qq");
        qq.setColumnName("QQ_");
        qq.setJavaType("String");
        qq.setLength(20);
        tableDefinition.addColumn(qq);

        ColumnDefinition weq = new ColumnDefinition();
        weq.setName("weq");
        weq.setColumnName("WEQ_");
        weq.setJavaType("String");
        weq.setLength(50);
        tableDefinition.addColumn(weq);

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

    private UisAppUserDomainFactory() {

    }

}
