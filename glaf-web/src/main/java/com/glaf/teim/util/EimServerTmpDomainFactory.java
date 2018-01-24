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
public class EimServerTmpDomainFactory {

    public static final String TABLENAME = "EIM_SERVER_TMP";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("tmpId", "TMP_ID_");
        columnMap.put("categoryId", "CATEGORY_ID_");
        columnMap.put("name", "NAME_");
        columnMap.put("path_", "PATH_");
        columnMap.put("reqUrlParam", "REQ_URL_PARAM_");
        columnMap.put("reqType", "REQ_TYPE_");
        columnMap.put("reqHeader", "REQ_HEADER_");
        columnMap.put("reqContentType", "REQ_CONTENT_TYPE_");
        columnMap.put("resContentType", "RES_CONTENT_TYPE_");
        columnMap.put("reqBody", "REQ_BODY_");
        columnMap.put("response_", "RESPONSE_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("tmpId", "String");
        javaTypeMap.put("categoryId", "Long");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("path_", "String");
        javaTypeMap.put("reqUrlParam", "String");
        javaTypeMap.put("reqType", "String");
        javaTypeMap.put("reqHeader", "String");
        javaTypeMap.put("reqContentType", "String");
        javaTypeMap.put("resContentType", "String");
        javaTypeMap.put("reqBody", "String");
        javaTypeMap.put("response_", "String");
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
        tableDefinition.setName("EimServerTmp");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("tmpId");
        idColumn.setColumnName("TMP_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition categoryId = new ColumnDefinition();
        categoryId.setName("categoryId");
        categoryId.setColumnName("CATEGORY_ID_");
        categoryId.setJavaType("Long");
        tableDefinition.addColumn(categoryId);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition path_ = new ColumnDefinition();
        path_.setName("path_");
        path_.setColumnName("PATH_");
        path_.setJavaType("String");
        path_.setLength(50);
        tableDefinition.addColumn(path_);

        ColumnDefinition reqUrlParam = new ColumnDefinition();
        reqUrlParam.setName("reqUrlParam");
        reqUrlParam.setColumnName("REQ_URL_PARAM_");
        reqUrlParam.setJavaType("String");
        reqUrlParam.setLength(0);
        tableDefinition.addColumn(reqUrlParam);

        ColumnDefinition reqType = new ColumnDefinition();
        reqType.setName("reqType");
        reqType.setColumnName("REQ_TYPE_");
        reqType.setJavaType("String");
        reqType.setLength(20);
        tableDefinition.addColumn(reqType);

        ColumnDefinition reqHeader = new ColumnDefinition();
        reqHeader.setName("reqHeader");
        reqHeader.setColumnName("REQ_HEADER_");
        reqHeader.setJavaType("String");
        reqHeader.setLength(0);
        tableDefinition.addColumn(reqHeader);

        ColumnDefinition reqContentType = new ColumnDefinition();
        reqContentType.setName("reqContentType");
        reqContentType.setColumnName("REQ_CONTENT_TYPE_");
        reqContentType.setJavaType("String");
        reqContentType.setLength(100);
        tableDefinition.addColumn(reqContentType);

        ColumnDefinition resContentType = new ColumnDefinition();
        resContentType.setName("resContentType");
        resContentType.setColumnName("RES_CONTENT_TYPE_");
        resContentType.setJavaType("String");
        resContentType.setLength(100);
        tableDefinition.addColumn(resContentType);

        ColumnDefinition reqBody = new ColumnDefinition();
        reqBody.setName("reqBody");
        reqBody.setColumnName("REQ_BODY_");
        reqBody.setJavaType("String");
        reqBody.setLength(0);
        tableDefinition.addColumn(reqBody);

        ColumnDefinition response_ = new ColumnDefinition();
        response_.setName("response_");
        response_.setColumnName("RESPONSE_");
        response_.setJavaType("String");
        response_.setLength(0);
        tableDefinition.addColumn(response_);

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

    private EimServerTmpDomainFactory() {

    }

}
