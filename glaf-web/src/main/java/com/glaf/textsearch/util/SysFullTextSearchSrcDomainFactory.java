package com.glaf.textsearch.util;

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
public class SysFullTextSearchSrcDomainFactory {

    public static final String TABLENAME = "SYS_FULLTEXTSEARCH_SRC";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("serviceName_", "SERVICE_NAME_");
        columnMap.put("serviceAddress_", "SERVICE_ADDRESS_");
        columnMap.put("rule_", "RULE_");
        columnMap.put("fullTextServer_", "FULLTEXT_SERVER_");
        columnMap.put("indexName_", "INDEX_NAME_");
        columnMap.put("typeName_", "TYPE_NAME_");
        columnMap.put("createBy_", "CREATEBY_");
        columnMap.put("createTime_", "CREATETIME_");
        columnMap.put("updateBy_", "UPDATEBY_");
        columnMap.put("updateTime_", "UPDATETIME_");
        columnMap.put("deleteFlag_", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("serviceName_", "String");
        javaTypeMap.put("serviceAddress_", "String");
        javaTypeMap.put("rule_", "Clob");
        javaTypeMap.put("fullTextServer_", "String");
        javaTypeMap.put("indexName_", "String");
        javaTypeMap.put("typeName_", "String");
        javaTypeMap.put("createBy_", "String");
        javaTypeMap.put("createTime_", "Date");
        javaTypeMap.put("updateBy_", "String");
        javaTypeMap.put("updateTime_", "Date");
        javaTypeMap.put("deleteFlag_", "Integer");
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
        tableDefinition.setName("SysFullTextSearchSrc");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition serviceName_ = new ColumnDefinition();
        serviceName_.setName("serviceName_");
        serviceName_.setColumnName("SERVICE_NAME_");
        serviceName_.setJavaType("String");
        serviceName_.setLength(100);
        tableDefinition.addColumn(serviceName_);

        ColumnDefinition serviceAddress_ = new ColumnDefinition();
        serviceAddress_.setName("serviceAddress_");
        serviceAddress_.setColumnName("SERVICE_ADDRESS_");
        serviceAddress_.setJavaType("String");
        serviceAddress_.setLength(200);
        tableDefinition.addColumn(serviceAddress_);

        ColumnDefinition rule_ = new ColumnDefinition();
        rule_.setName("rule_");
        rule_.setColumnName("RULE_");
        rule_.setJavaType("Clob");
        tableDefinition.addColumn(rule_);

        ColumnDefinition fullTextServer_ = new ColumnDefinition();
        fullTextServer_.setName("fullTextServer_");
        fullTextServer_.setColumnName("FULLTEXT_SERVER_");
        fullTextServer_.setJavaType("String");
        fullTextServer_.setLength(200);
        tableDefinition.addColumn(fullTextServer_);

        ColumnDefinition indexName_ = new ColumnDefinition();
        indexName_.setName("indexName_");
        indexName_.setColumnName("INDEX_NAME_");
        indexName_.setJavaType("String");
        indexName_.setLength(100);
        tableDefinition.addColumn(indexName_);

        ColumnDefinition typeName_ = new ColumnDefinition();
        typeName_.setName("typeName_");
        typeName_.setColumnName("TYPE_NAME_");
        typeName_.setJavaType("String");
        typeName_.setLength(50);
        tableDefinition.addColumn(typeName_);

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

        ColumnDefinition deleteFlag_ = new ColumnDefinition();
        deleteFlag_.setName("deleteFlag_");
        deleteFlag_.setColumnName("DELETE_FLAG_");
        deleteFlag_.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag_);

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

    private SysFullTextSearchSrcDomainFactory() {

    }

}
