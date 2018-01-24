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
public class DepReportFormulaDomainFactory {

    public static final String TABLENAME = "DEP_REPORT_FORMULA";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("cellId", "CELL_ID_");
        columnMap.put("expression", "EXPRESSION_");
        columnMap.put("enCondition", "ENCONDITION_");
        columnMap.put("ruleJson", "RULEJSON_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDateTime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDateTime", "MODIFYDATETIME_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("cellId", "Long");
        javaTypeMap.put("expression", "String");
        javaTypeMap.put("enCondition", "String");
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
        tableDefinition.setName("DepReportFormula");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition cellId = new ColumnDefinition();
        cellId.setName("cellId");
        cellId.setColumnName("CELL_ID_");
        cellId.setJavaType("Long");
        tableDefinition.addColumn(cellId);

        ColumnDefinition expression = new ColumnDefinition();
        expression.setName("expression");
        expression.setColumnName("EXPRESSION_");
        expression.setJavaType("String");
        expression.setLength(200);
        tableDefinition.addColumn(expression);

        ColumnDefinition enCondition = new ColumnDefinition();
        enCondition.setName("enCondition");
        enCondition.setColumnName("ENCONDITION_");
        enCondition.setJavaType("String");
        enCondition.setLength(150);
        tableDefinition.addColumn(enCondition);

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
						.getResourceAsStream("com/glaf/dep/report/domain/DepReportFormula.mapping.xml");
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

    private DepReportFormulaDomainFactory() {

    }

}
