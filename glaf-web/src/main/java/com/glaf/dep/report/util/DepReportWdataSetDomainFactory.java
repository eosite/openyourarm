package com.glaf.dep.report.util;

import java.io.InputStream;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.IOUtils;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.base.TableModel;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;
import com.glaf.core.util.Resources;
import com.glaf.core.xml.XmlMappingReader;


/**
 * 
 * 实体数据工厂类
 *
 */
public class DepReportWdataSetDomainFactory {

    public static final String TABLENAME = "DEP_REPORT_WDATASET";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("wdatasetId", "WDATASET_ID_");
        columnMap.put("repTemplateId", "REPTEMPLATE_ID_");
        columnMap.put("code", "CODE_");
        columnMap.put("name", "NAME_");
        columnMap.put("tableName", "TABLE_NAME_");
        columnMap.put("dataTableName", "DATATABLE_NAME_");
        columnMap.put("enConditon", "ENCONDITON_");
        columnMap.put("order", "ORDER_");
        columnMap.put("ruleJson", "RULEJSON_");
        columnMap.put("psql", "PSQL_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDateTime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDateTime", "MODIFYDATETIME_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("wdatasetId", "Long");
        javaTypeMap.put("repTemplateId", "Long");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("tableName", "String");
        javaTypeMap.put("dataTableName", "String");
        javaTypeMap.put("enConditon", "String");
        javaTypeMap.put("order", "Integer");
        javaTypeMap.put("ruleJson", "String");
        javaTypeMap.put("psql", "String");
        javaTypeMap.put("creator", "String");
        javaTypeMap.put("createDateTime", "Date");
        javaTypeMap.put("modifier", "String");
        javaTypeMap.put("modifyDateTime", "Date");
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
        tableDefinition.setName("DepReportWdataSet");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition wdatasetId = new ColumnDefinition();
        wdatasetId.setName("wdatasetId");
        wdatasetId.setColumnName("WDATASET_ID_");
        wdatasetId.setJavaType("Long");
        tableDefinition.addColumn(wdatasetId);

        ColumnDefinition repTemplateId = new ColumnDefinition();
        repTemplateId.setName("repTemplateId");
        repTemplateId.setColumnName("REPTEMPLATE_ID_");
        repTemplateId.setJavaType("Long");
        tableDefinition.addColumn(repTemplateId);

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
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition TN = new ColumnDefinition();
        TN.setName("tableName");
        TN.setColumnName("TABLE_NAME_");
        TN.setJavaType("String");
        TN.setLength(50);
        tableDefinition.addColumn(TN);

        ColumnDefinition dataTableName = new ColumnDefinition();
        dataTableName.setName("dataTableName");
        dataTableName.setColumnName("DATATABLE_NAME_");
        dataTableName.setJavaType("String");
        dataTableName.setLength(30);
        tableDefinition.addColumn(dataTableName);

        ColumnDefinition enConditon = new ColumnDefinition();
        enConditon.setName("enConditon");
        enConditon.setColumnName("ENCONDITON_");
        enConditon.setJavaType("String");
        enConditon.setLength(150);
        tableDefinition.addColumn(enConditon);

        ColumnDefinition order = new ColumnDefinition();
        order.setName("order");
        order.setColumnName("ORDER_");
        order.setJavaType("Integer");
        tableDefinition.addColumn(order);

        ColumnDefinition ruleJson = new ColumnDefinition();
        ruleJson.setName("ruleJson");
        ruleJson.setColumnName("RULEJSON_");
        ruleJson.setJavaType("String");
        ruleJson.setLength(255);
        tableDefinition.addColumn(ruleJson);

        ColumnDefinition psql = new ColumnDefinition();
        psql.setName("psql");
        psql.setColumnName("PSQL_");
        psql.setJavaType("String");
        psql.setLength(255);
        tableDefinition.addColumn(psql);

        ColumnDefinition creator = new ColumnDefinition();
        creator.setName("creator");
        creator.setColumnName("CREATOR_");
        creator.setJavaType("String");
        creator.setLength(20);
        tableDefinition.addColumn(creator);

        ColumnDefinition createDateTime = new ColumnDefinition();
        createDateTime.setName("createDateTime");
        createDateTime.setColumnName("CREATEDATETIME_");
        createDateTime.setJavaType("Date");
        tableDefinition.addColumn(createDateTime);

        ColumnDefinition modifier = new ColumnDefinition();
        modifier.setName("modifier");
        modifier.setColumnName("MODIFIER_");
        modifier.setJavaType("String");
        modifier.setLength(20);
        tableDefinition.addColumn(modifier);

        ColumnDefinition modifyDateTime = new ColumnDefinition();
        modifyDateTime.setName("modifyDateTime");
        modifyDateTime.setColumnName("MODIFYDATETIME_");
        modifyDateTime.setJavaType("Date");
        tableDefinition.addColumn(modifyDateTime);

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

    public static TableModel readTableModel() {
		if (tableModel == null) {
			XmlMappingReader reader = new XmlMappingReader();
			InputStream inputStream = null;
			try {
				inputStream = Resources
						.getResourceAsStream("com/glaf/dep/report/domain/DepReportWdataSet.mapping.xml");
				tableModel = reader.read(inputStream);
			} catch (Exception ex) {
				ex.printStackTrace();
			} finally {
				IOUtils.closeQuietly(inputStream);
			}
		}
		TableModel bean = new TableModel();
		try {
			BeanUtils.copyProperties(bean, tableModel);
		} catch (Exception ex) {
			org.springframework.beans.BeanUtils
					.copyProperties(tableModel, bean);
		}
		return bean;
	}

    private DepReportWdataSetDomainFactory() {

    }

}
