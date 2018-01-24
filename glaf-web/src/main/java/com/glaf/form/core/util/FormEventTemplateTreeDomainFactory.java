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
public class FormEventTemplateTreeDomainFactory {

    public static final String TABLENAME = "FORM_EVENT_TEMPLATE_TREE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("indexId", "INDEXID_");
        columnMap.put("parentId", "PARENTID_");
        columnMap.put("treeId", "TREEID_");
        columnMap.put("type", "TYPE_");
        columnMap.put("name", "NAME_");
        columnMap.put("deleteFlag", "DELETEFLAG_");
        columnMap.put("createDate", "CREATEDATE_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateDate", "UPDATEDATE_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("indexId", "Integer");
        javaTypeMap.put("parentId", "Integer");
        javaTypeMap.put("treeId", "String");
        javaTypeMap.put("type", "Integer");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("deleteFlag", "Integer");
        javaTypeMap.put("createDate", "Date");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateDate", "Date");
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
        tableDefinition.setName("FormEventTemplateTree");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition indexId = new ColumnDefinition();
        indexId.setName("indexId");
        indexId.setColumnName("INDEXID_");
        indexId.setJavaType("Integer");
        tableDefinition.addColumn(indexId);

        ColumnDefinition parentId = new ColumnDefinition();
        parentId.setName("parentId");
        parentId.setColumnName("PARENTID_");
        parentId.setJavaType("Integer");
        tableDefinition.addColumn(parentId);

        ColumnDefinition treeId = new ColumnDefinition();
        treeId.setName("treeId");
        treeId.setColumnName("TREEID_");
        treeId.setJavaType("String");
        treeId.setLength(200);
        tableDefinition.addColumn(treeId);

        ColumnDefinition type = new ColumnDefinition();
        type.setName("type");
        type.setColumnName("TYPE_");
        type.setJavaType("Integer");
        tableDefinition.addColumn(type);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition deleteFlag = new ColumnDefinition();
        deleteFlag.setName("deleteFlag");
        deleteFlag.setColumnName("DELETEFLAG_");
        deleteFlag.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE_");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY_");
        createBy.setJavaType("String");
        createBy.setLength(50);
        tableDefinition.addColumn(createBy);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY_");
        updateBy.setJavaType("String");
        updateBy.setLength(50);
        tableDefinition.addColumn(updateBy);

        ColumnDefinition updateDate = new ColumnDefinition();
        updateDate.setName("updateDate");
        updateDate.setColumnName("UPDATEDATE_");
        updateDate.setJavaType("Date");
        tableDefinition.addColumn(updateDate);

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

    private FormEventTemplateTreeDomainFactory() {

    }

}
