package com.glaf.report.core.util;

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
public class ReportCategoryDomainFactory {

    public static final String TABLENAME = "REPORT_CATEGORY";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("parentId_", "PARENT_ID_");
        columnMap.put("code", "CODE_");
        columnMap.put("name", "NAME_");
        columnMap.put("treeId", "TREE_ID_");
        columnMap.put("level", "LEVEL_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDatetime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDatetime", "MODIFYDATETIME_");

	javaTypeMap.put("id", "Integer");
        javaTypeMap.put("parentId_", "Integer");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("treeId", "String");
        javaTypeMap.put("level", "Integer");
        javaTypeMap.put("deleteFlag", "Integer");
        javaTypeMap.put("creator", "String");
        javaTypeMap.put("createDatetime", "Date");
        javaTypeMap.put("modifier", "String");
        javaTypeMap.put("modifyDatetime", "Date");
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
        tableDefinition.setName("ReportCategory");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Integer");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition parentId_ = new ColumnDefinition();
        parentId_.setName("parentId_");
        parentId_.setColumnName("PARENT_ID_");
        parentId_.setJavaType("Integer");
        tableDefinition.addColumn(parentId_);

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
        name.setLength(150);
        tableDefinition.addColumn(name);

        ColumnDefinition treeId = new ColumnDefinition();
        treeId.setName("treeId");
        treeId.setColumnName("TREE_ID_");
        treeId.setJavaType("String");
        treeId.setLength(300);
        tableDefinition.addColumn(treeId);

        ColumnDefinition level = new ColumnDefinition();
        level.setName("level");
        level.setColumnName("LEVEL_");
        level.setJavaType("Integer");
        tableDefinition.addColumn(level);

        ColumnDefinition deleteFlag = new ColumnDefinition();
        deleteFlag.setName("deleteFlag");
        deleteFlag.setColumnName("DELETE_FLAG_");
        deleteFlag.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag);

        ColumnDefinition creator = new ColumnDefinition();
        creator.setName("creator");
        creator.setColumnName("CREATOR_");
        creator.setJavaType("String");
        creator.setLength(20);
        tableDefinition.addColumn(creator);

        ColumnDefinition createDatetime = new ColumnDefinition();
        createDatetime.setName("createDatetime");
        createDatetime.setColumnName("CREATEDATETIME_");
        createDatetime.setJavaType("Date");
        tableDefinition.addColumn(createDatetime);

        ColumnDefinition modifier = new ColumnDefinition();
        modifier.setName("modifier");
        modifier.setColumnName("MODIFIER_");
        modifier.setJavaType("String");
        modifier.setLength(20);
        tableDefinition.addColumn(modifier);

        ColumnDefinition modifyDatetime = new ColumnDefinition();
        modifyDatetime.setName("modifyDatetime");
        modifyDatetime.setColumnName("MODIFYDATETIME_");
        modifyDatetime.setJavaType("Date");
        tableDefinition.addColumn(modifyDatetime);

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

    private ReportCategoryDomainFactory() {

    }

}
