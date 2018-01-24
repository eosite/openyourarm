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
public class ReportTmpDataSetMappingDomainFactory {

    public static final String TABLENAME = "REPORT_TMP_DATASET_MAPPING";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("tmpMappingId", "TMP_MAPPING_ID_");
        columnMap.put("templateId", "TEMPLATE_ID_");
        columnMap.put("dataSetCode", "DATASET_CODE_");
        columnMap.put("dataSetName", "DATASET_NAME_");
        columnMap.put("mappingDataSetId", "MAPPING_DATASET_ID_");
        columnMap.put("mappingDataSetCode", "MAPPING_DATASET_CODE_");
        columnMap.put("mappingDataSetName", "MAPPING_DATASET_NAME_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDatetime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDatetime", "MODIFYDATETIME_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("tmpMappingId", "String");
        javaTypeMap.put("templateId", "String");
        javaTypeMap.put("dataSetCode", "String");
        javaTypeMap.put("dataSetName", "String");
        javaTypeMap.put("mappingDataSetId", "String");
        javaTypeMap.put("mappingDataSetCode", "String");
        javaTypeMap.put("mappingDataSetName", "String");
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
        tableDefinition.setName("ReportTmpDataSetMapping");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(64);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition tmpMappingId = new ColumnDefinition();
        tmpMappingId.setName("tmpMappingId");
        tmpMappingId.setColumnName("TMP_MAPPING_ID_");
        tmpMappingId.setJavaType("String");
        tmpMappingId.setLength(64);
        tableDefinition.addColumn(tmpMappingId);

        ColumnDefinition templateId = new ColumnDefinition();
        templateId.setName("templateId");
        templateId.setColumnName("TEMPLATE_ID_");
        templateId.setJavaType("String");
        templateId.setLength(64);
        tableDefinition.addColumn(templateId);

        ColumnDefinition dataSetCode = new ColumnDefinition();
        dataSetCode.setName("dataSetCode");
        dataSetCode.setColumnName("DATASET_CODE_");
        dataSetCode.setJavaType("String");
        dataSetCode.setLength(50);
        tableDefinition.addColumn(dataSetCode);

        ColumnDefinition dataSetName = new ColumnDefinition();
        dataSetName.setName("dataSetName");
        dataSetName.setColumnName("DATASET_NAME_");
        dataSetName.setJavaType("String");
        dataSetName.setLength(100);
        tableDefinition.addColumn(dataSetName);

        ColumnDefinition mappingDataSetId = new ColumnDefinition();
        mappingDataSetId.setName("mappingDataSetId");
        mappingDataSetId.setColumnName("MAPPING_DATASET_ID_");
        mappingDataSetId.setJavaType("String");
        mappingDataSetId.setLength(64);
        tableDefinition.addColumn(mappingDataSetId);

        ColumnDefinition mappingDataSetCode = new ColumnDefinition();
        mappingDataSetCode.setName("mappingDataSetCode");
        mappingDataSetCode.setColumnName("MAPPING_DATASET_CODE_");
        mappingDataSetCode.setJavaType("String");
        mappingDataSetCode.setLength(64);
        tableDefinition.addColumn(mappingDataSetCode);

        ColumnDefinition mappingDataSetName = new ColumnDefinition();
        mappingDataSetName.setName("mappingDataSetName");
        mappingDataSetName.setColumnName("MAPPING_DATASET_NAME_");
        mappingDataSetName.setJavaType("String");
        mappingDataSetName.setLength(150);
        tableDefinition.addColumn(mappingDataSetName);

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

    private ReportTmpDataSetMappingDomainFactory() {

    }

}
