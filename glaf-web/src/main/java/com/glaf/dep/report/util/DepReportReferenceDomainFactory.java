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
public class DepReportReferenceDomainFactory {

    public static final String TABLENAME = "DEP_REPORT_REFERENCE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("repDataSetId", "REPDATASET_ID_");
        columnMap.put("repDataId", "REPDATA_ID_");
        columnMap.put("refType", "REFTYPE_");
        columnMap.put("refMode", "REFMODE_");
        columnMap.put("enCondition", "ENCONDITON_");
        columnMap.put("columnName", "COLUMNNAME_");
        columnMap.put("tableName", "TABLENAME_");
        columnMap.put("reportId", "REPORT_ID_");
        columnMap.put("reportCellId", "REPORT_CELL_ID_");
        columnMap.put("ruleJson", "RULEJSON_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDateTime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDateTime", "MODIFYDATETIME_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("repDataSetId", "Long");
        javaTypeMap.put("repDataId", "Long");
        javaTypeMap.put("refType", "String");
        javaTypeMap.put("refMode", "String");
        javaTypeMap.put("enCondition", "String");
        javaTypeMap.put("columnName", "String");
        javaTypeMap.put("tableName", "String");
        javaTypeMap.put("reportId", "String");
        javaTypeMap.put("reportCellId", "String");
        javaTypeMap.put("ruleJson", "String");
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
        tableDefinition.setName("DepReportReference");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition repDataSetId = new ColumnDefinition();
        repDataSetId.setName("repDataSetId");
        repDataSetId.setColumnName("REPDATASET_ID_");
        repDataSetId.setJavaType("Long");
        tableDefinition.addColumn(repDataSetId);

        ColumnDefinition repDataId = new ColumnDefinition();
        repDataId.setName("repDataId");
        repDataId.setColumnName("REPDATA_ID_");
        repDataId.setJavaType("Long");
        tableDefinition.addColumn(repDataId);

        ColumnDefinition refType = new ColumnDefinition();
        refType.setName("refType");
        refType.setColumnName("REFTYPE_");
        refType.setJavaType("String");
        refType.setLength(30);
        tableDefinition.addColumn(refType);

        ColumnDefinition refMode = new ColumnDefinition();
        refMode.setName("refMode");
        refMode.setColumnName("REFMODE_");
        refMode.setJavaType("String");
        refMode.setLength(20);
        tableDefinition.addColumn(refMode);

        ColumnDefinition enCondition = new ColumnDefinition();
        enCondition.setName("enCondition");
        enCondition.setColumnName("ENCONDITON_");
        enCondition.setJavaType("String");
        enCondition.setLength(150);
        tableDefinition.addColumn(enCondition);

        ColumnDefinition columnName = new ColumnDefinition();
        columnName.setName("columnName");
        columnName.setColumnName("COLUMNNAME_");
        columnName.setJavaType("String");
        columnName.setLength(50);
        tableDefinition.addColumn(columnName);

        ColumnDefinition TN = new ColumnDefinition();
        TN.setName("tableName");
        TN.setColumnName("TABLENAME_");
        TN.setJavaType("String");
        TN.setLength(50);
        tableDefinition.addColumn(TN);

        ColumnDefinition reportId = new ColumnDefinition();
        reportId.setName("reportId");
        reportId.setColumnName("REPORT_ID_");
        reportId.setJavaType("String");
        reportId.setLength(50);
        tableDefinition.addColumn(reportId);

        ColumnDefinition reportCellId = new ColumnDefinition();
        reportCellId.setName("reportCellId");
        reportCellId.setColumnName("REPORT_CELL_ID_");
        reportCellId.setJavaType("String");
        reportCellId.setLength(50);
        tableDefinition.addColumn(reportCellId);

        ColumnDefinition ruleJson = new ColumnDefinition();
        ruleJson.setName("ruleJson");
        ruleJson.setColumnName("RULEJSON_");
        ruleJson.setJavaType("String");
        ruleJson.setLength(255);
        tableDefinition.addColumn(ruleJson);

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
						.getResourceAsStream("com/glaf/dep/report/domain/DepReportReference.mapping.xml");
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

    private DepReportReferenceDomainFactory() {

    }

}
