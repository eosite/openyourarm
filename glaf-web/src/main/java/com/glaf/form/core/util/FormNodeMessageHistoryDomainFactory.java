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
public class FormNodeMessageHistoryDomainFactory {

    public static final String TABLENAME = "FORM_NODE_MESSAGE_HISTORY";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("telephone", "TELEPHONE_");
        columnMap.put("msg", "MSG_");
        columnMap.put("state", "STATE_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDate", "CREATEDATE_");
        columnMap.put("result", "RESULT_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("telephone", "String");
        javaTypeMap.put("msg", "String");
        javaTypeMap.put("state", "Integer");
        javaTypeMap.put("creator", "String");
        javaTypeMap.put("createDate", "Date");
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
        tableDefinition.setName("FormNodeMessageHistory");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition telephone = new ColumnDefinition();
        telephone.setName("telephone");
        telephone.setColumnName("TELEPHONE_");
        telephone.setJavaType("String");
        telephone.setLength(30);
        tableDefinition.addColumn(telephone);

        ColumnDefinition msg = new ColumnDefinition();
        msg.setName("msg");
        msg.setColumnName("MSG_");
        msg.setJavaType("String");
        msg.setLength(100);
        tableDefinition.addColumn(msg);

        ColumnDefinition state = new ColumnDefinition();
        state.setName("state");
        state.setColumnName("STATE_");
        state.setJavaType("Integer");
        tableDefinition.addColumn(state);

        ColumnDefinition creator = new ColumnDefinition();
        creator.setName("creator");
        creator.setColumnName("CREATOR_");
        creator.setJavaType("String");
        creator.setLength(50);
        tableDefinition.addColumn(creator);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE_");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition result = new ColumnDefinition();
        result.setName("result");
        result.setColumnName("RESULT_");
        result.setJavaType("String");
        result.setLength(100);
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

    private FormNodeMessageHistoryDomainFactory() {

    }

}
