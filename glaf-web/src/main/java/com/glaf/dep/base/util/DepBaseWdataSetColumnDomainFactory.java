package com.glaf.dep.base.util;

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
public class DepBaseWdataSetColumnDomainFactory {

    public static final String TABLENAME = "DEP_BASE_WDATASET_COLUMN_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("wdataSetId", "WDATASET_ID_");
        columnMap.put("columnName", "COLUMN_NAME_");
        columnMap.put("dataColumnName", "DATACOLUMN_NAME_");
        columnMap.put("defaultVal", "DEFAULTVAL_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDatetime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDatetime", "MODIFYDATETIME_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("wdataSetId", "Long");
        javaTypeMap.put("columnName", "String");
        javaTypeMap.put("dataColumnName", "String");
        javaTypeMap.put("defaultVal", "String");
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
        tableDefinition.setName("DepBaseWdataSetColumn");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition wdataSetId = new ColumnDefinition();
        wdataSetId.setName("wdataSetId");
        wdataSetId.setColumnName("WDATASET_ID_");
        wdataSetId.setJavaType("Long");
        tableDefinition.addColumn(wdataSetId);

        ColumnDefinition columnName = new ColumnDefinition();
        columnName.setName("columnName");
        columnName.setColumnName("COLUMN_NAME_");
        columnName.setJavaType("String");
        columnName.setLength(50);
        tableDefinition.addColumn(columnName);

        ColumnDefinition dataColumnName = new ColumnDefinition();
        dataColumnName.setName("dataColumnName");
        dataColumnName.setColumnName("DATACOLUMN_NAME_");
        dataColumnName.setJavaType("String");
        dataColumnName.setLength(50);
        tableDefinition.addColumn(dataColumnName);

        ColumnDefinition defaultVal = new ColumnDefinition();
        defaultVal.setName("defaultVal");
        defaultVal.setColumnName("DEFAULTVAL_");
        defaultVal.setJavaType("String");
        defaultVal.setLength(150);
        tableDefinition.addColumn(defaultVal);

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

    public static TableModel readTableModel() {
		if (tableModel == null) {
			XmlMappingReader reader = new XmlMappingReader();
			InputStream inputStream = null;
			try {
				inputStream = Resources
						.getResourceAsStream("com/glaf/dep/base/domain/DepBaseWdataSetColumn.mapping.xml");
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

    private DepBaseWdataSetColumnDomainFactory() {

    }

}
