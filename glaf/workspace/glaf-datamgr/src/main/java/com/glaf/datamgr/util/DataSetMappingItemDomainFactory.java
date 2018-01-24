package com.glaf.datamgr.util;

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
public class DataSetMappingItemDomainFactory {

    public static final String TABLENAME = "SYS_DATASET_MAPPING_ITEM";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("name", "NAME_");
        columnMap.put("code", "CODE_");
        columnMap.put("mappingName", "MAPPINGNAME_");
        columnMap.put("mappingCode", "MAPPINGCODE_");
        columnMap.put("mappingType", "MAPPINGTYPE_");
        columnMap.put("treeId", "TREEID_");
        columnMap.put("topId", "TOPID_");
        columnMap.put("parentId", "PARENTID_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createDate", "CREATEDATE_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateDate", "UPDATEDATE_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("mappingName", "String");
        javaTypeMap.put("mappingCode", "String");
        javaTypeMap.put("mappingType", "String");
        javaTypeMap.put("treeId", "String");
        javaTypeMap.put("topId", "String");
        javaTypeMap.put("parentId", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createDate", "Date");
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
        tableDefinition.setName("DataSetMappingItem");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(255);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition code = new ColumnDefinition();
        code.setName("code");
        code.setColumnName("CODE_");
        code.setJavaType("String");
        code.setLength(50);
        tableDefinition.addColumn(code);

        ColumnDefinition mappingName = new ColumnDefinition();
        mappingName.setName("mappingName");
        mappingName.setColumnName("MAPPINGNAME_");
        mappingName.setJavaType("String");
        mappingName.setLength(50);
        tableDefinition.addColumn(mappingName);

        ColumnDefinition mappingCode = new ColumnDefinition();
        mappingCode.setName("mappingCode");
        mappingCode.setColumnName("MAPPINGCODE_");
        mappingCode.setJavaType("String");
        mappingCode.setLength(50);
        tableDefinition.addColumn(mappingCode);

        ColumnDefinition mappingType = new ColumnDefinition();
        mappingType.setName("mappingType");
        mappingType.setColumnName("MAPPINGTYPE_");
        mappingType.setJavaType("String");
        mappingType.setLength(10);
        tableDefinition.addColumn(mappingType);

        ColumnDefinition treeId = new ColumnDefinition();
        treeId.setName("treeId");
        treeId.setColumnName("TREEID_");
        treeId.setJavaType("String");
        treeId.setLength(50);
        tableDefinition.addColumn(treeId);

        ColumnDefinition topId = new ColumnDefinition();
        topId.setName("topId");
        topId.setColumnName("TOPID_");
        topId.setJavaType("String");
        topId.setLength(50);
        tableDefinition.addColumn(topId);

        ColumnDefinition parentId = new ColumnDefinition();
        parentId.setName("parentId");
        parentId.setColumnName("PARENTID_");
        parentId.setJavaType("String");
        parentId.setLength(50);
        tableDefinition.addColumn(parentId);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY_");
        createBy.setJavaType("String");
        createBy.setLength(30);
        tableDefinition.addColumn(createBy);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE_");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY_");
        updateBy.setJavaType("String");
        updateBy.setLength(30);
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

    private DataSetMappingItemDomainFactory() {

    }

}
