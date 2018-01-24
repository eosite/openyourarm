package com.glaf.form.core.util;

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
public class MsgValidHistoryDomainFactory {

    public static final String TABLENAME = "MSGVALIDHISTORY_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("telephone", "TELEPHONE_");
        columnMap.put("sendDate", "SENDDATE_");
        columnMap.put("type", "TYPE_");
        columnMap.put("typeName", "TYPENAME_");
        columnMap.put("url", "URL_");
        columnMap.put("msg", "MSG_");
        columnMap.put("status", "STATUS_");
        columnMap.put("statusName", "STATUSNAME_");
        columnMap.put("result", "RESULT_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("telephone", "String");
        javaTypeMap.put("sendDate", "Date");
        javaTypeMap.put("type", "Integer");
        javaTypeMap.put("typeName", "String");
        javaTypeMap.put("url", "String");
        javaTypeMap.put("msg", "String");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("statusName", "String");
        javaTypeMap.put("result", "String");
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
        tableDefinition.setName("MsgValidHistory");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition telephone = new ColumnDefinition();
        telephone.setName("telephone");
        telephone.setColumnName("TELEPHONE_");
        telephone.setJavaType("String");
        telephone.setLength(50);
        tableDefinition.addColumn(telephone);

        ColumnDefinition sendDate = new ColumnDefinition();
        sendDate.setName("sendDate");
        sendDate.setColumnName("SENDDATE_");
        sendDate.setJavaType("Date");
        tableDefinition.addColumn(sendDate);

        ColumnDefinition type = new ColumnDefinition();
        type.setName("type");
        type.setColumnName("TYPE_");
        type.setJavaType("Integer");
        tableDefinition.addColumn(type);

        ColumnDefinition typeName = new ColumnDefinition();
        typeName.setName("typeName");
        typeName.setColumnName("TYPENAME_");
        typeName.setJavaType("String");
        typeName.setLength(50);
        tableDefinition.addColumn(typeName);

        ColumnDefinition url = new ColumnDefinition();
        url.setName("url");
        url.setColumnName("URL_");
        url.setJavaType("String");
        url.setLength(600);
        tableDefinition.addColumn(url);

        ColumnDefinition msg = new ColumnDefinition();
        msg.setName("msg");
        msg.setColumnName("MSG_");
        msg.setJavaType("String");
        msg.setLength(500);
        tableDefinition.addColumn(msg);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS_");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition statusName = new ColumnDefinition();
        statusName.setName("statusName");
        statusName.setColumnName("STATUSNAME_");
        statusName.setJavaType("String");
        statusName.setLength(500);
        tableDefinition.addColumn(statusName);

        ColumnDefinition result = new ColumnDefinition();
        result.setName("result");
        result.setColumnName("RESULT_");
        result.setJavaType("String");
        result.setLength(50);
        tableDefinition.addColumn(result);

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

    private MsgValidHistoryDomainFactory() {

    }

}
