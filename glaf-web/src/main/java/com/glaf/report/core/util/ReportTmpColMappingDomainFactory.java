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
public class ReportTmpColMappingDomainFactory {

    public static final String TABLENAME = "REPORT_TMP_COL_MAPPING";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("dataSetMappingId", "DATASET_MAPPING_ID_");
        columnMap.put("colCode", "COL_CODE_");
        columnMap.put("colName", "COL_NAME_");
        columnMap.put("colTitle", "COL_TITLE_");
        columnMap.put("colDtype", "COL_DTYPE_");
        columnMap.put("colMappingCode", "COL_MAPPING_CODE_");
        columnMap.put("colMappingName", "COL_MAPPING_NAME_");
        columnMap.put("colMappingTitle", "COL_MAPPING_TITLE_");
        columnMap.put("colMappingDtype", "COL_MAPPING_DTYPE_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDatetime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDatetime", "MODIFYDATETIME_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("dataSetMappingId", "String");
        javaTypeMap.put("colCode", "String");
        javaTypeMap.put("colName", "String");
        javaTypeMap.put("colTitle", "String");
        javaTypeMap.put("colDtype", "String");
        javaTypeMap.put("colMappingCode", "String");
        javaTypeMap.put("colMappingName", "String");
        javaTypeMap.put("colMappingTitle", "String");
        javaTypeMap.put("colMappingDtype", "String");
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
        tableDefinition.setName("ReportTmpColMapping");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(64);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition dataSetMappingId = new ColumnDefinition();
        dataSetMappingId.setName("dataSetMappingId");
        dataSetMappingId.setColumnName("DATASET_MAPPING_ID_");
        dataSetMappingId.setJavaType("String");
        dataSetMappingId.setLength(64);
        tableDefinition.addColumn(dataSetMappingId);

        ColumnDefinition colCode = new ColumnDefinition();
        colCode.setName("colCode");
        colCode.setColumnName("COL_CODE_");
        colCode.setJavaType("String");
        colCode.setLength(50);
        tableDefinition.addColumn(colCode);

        ColumnDefinition colName = new ColumnDefinition();
        colName.setName("colName");
        colName.setColumnName("COL_NAME_");
        colName.setJavaType("String");
        colName.setLength(100);
        tableDefinition.addColumn(colName);

        ColumnDefinition colTitle = new ColumnDefinition();
        colTitle.setName("colTitle");
        colTitle.setColumnName("COL_TITLE_");
        colTitle.setJavaType("String");
        colTitle.setLength(100);
        tableDefinition.addColumn(colTitle);

        ColumnDefinition colDtype = new ColumnDefinition();
        colDtype.setName("colDtype");
        colDtype.setColumnName("COL_DTYPE_");
        colDtype.setJavaType("String");
        colDtype.setLength(20);
        tableDefinition.addColumn(colDtype);

        ColumnDefinition colMappingCode = new ColumnDefinition();
        colMappingCode.setName("colMappingCode");
        colMappingCode.setColumnName("COL_MAPPING_CODE_");
        colMappingCode.setJavaType("String");
        colMappingCode.setLength(50);
        tableDefinition.addColumn(colMappingCode);

        ColumnDefinition colMappingName = new ColumnDefinition();
        colMappingName.setName("colMappingName");
        colMappingName.setColumnName("COL_MAPPING_NAME_");
        colMappingName.setJavaType("String");
        colMappingName.setLength(100);
        tableDefinition.addColumn(colMappingName);

        ColumnDefinition colMappingTitle = new ColumnDefinition();
        colMappingTitle.setName("colMappingTitle");
        colMappingTitle.setColumnName("COL_MAPPING_TITLE_");
        colMappingTitle.setJavaType("String");
        colMappingTitle.setLength(100);
        tableDefinition.addColumn(colMappingTitle);

        ColumnDefinition colMappingDtype = new ColumnDefinition();
        colMappingDtype.setName("colMappingDtype");
        colMappingDtype.setColumnName("COL_MAPPING_DTYPE_");
        colMappingDtype.setJavaType("String");
        colMappingDtype.setLength(20);
        tableDefinition.addColumn(colMappingDtype);

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

    private ReportTmpColMappingDomainFactory() {

    }

}
