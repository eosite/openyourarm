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
public class ReportTmpMappingDomainFactory {

    public static final String TABLENAME = "REPORT_TMP_MAPPING";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("systemId", "SYSTEM_ID_");
        columnMap.put("templateId", "TEMPLATE_ID_");
        columnMap.put("templateCode", "TEMPLATE_CODE_");
        columnMap.put("templateName", "TEMPLATE_NAME_");
        columnMap.put("desc", "DESC_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDatetime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDatetime", "MODIFYDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("systemId", "String");
        javaTypeMap.put("templateId", "String");
        javaTypeMap.put("templateCode", "String");
        javaTypeMap.put("templateName", "String");
        javaTypeMap.put("desc", "String");
        javaTypeMap.put("creator", "String");
        javaTypeMap.put("createDatetime", "Date");
        javaTypeMap.put("modifier", "String");
        javaTypeMap.put("modifyDatetime", "Date");
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
        tableDefinition.setName("ReportTmpMapping");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(64);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition systemId = new ColumnDefinition();
        systemId.setName("systemId");
        systemId.setColumnName("SYSTEM_ID_");
        systemId.setJavaType("String");
        systemId.setLength(64);
        tableDefinition.addColumn(systemId);

        ColumnDefinition templateId = new ColumnDefinition();
        templateId.setName("templateId");
        templateId.setColumnName("TEMPLATE_ID_");
        templateId.setJavaType("String");
        templateId.setLength(64);
        tableDefinition.addColumn(templateId);

        ColumnDefinition templateCode = new ColumnDefinition();
        templateCode.setName("templateCode");
        templateCode.setColumnName("TEMPLATE_CODE_");
        templateCode.setJavaType("String");
        templateCode.setLength(50);
        tableDefinition.addColumn(templateCode);

        ColumnDefinition templateName = new ColumnDefinition();
        templateName.setName("templateName");
        templateName.setColumnName("TEMPLATE_NAME_");
        templateName.setJavaType("String");
        templateName.setLength(100);
        tableDefinition.addColumn(templateName);

        ColumnDefinition desc = new ColumnDefinition();
        desc.setName("desc");
        desc.setColumnName("DESC_");
        desc.setJavaType("String");
        desc.setLength(150);
        tableDefinition.addColumn(desc);

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

    private ReportTmpMappingDomainFactory() {

    }

}
