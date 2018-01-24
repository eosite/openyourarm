package com.glaf.etl.util;

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
public class EtlDataTargetDomainFactory {

    public static final String TABLENAME = "ETL_DATATARGET";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("targetId_", "TARGET_ID_");
        columnMap.put("targetName_", "TARGET_NAME_");
        columnMap.put("columns_", "COLUMNS_");
        columnMap.put("databaseId_", "DATABASEID_");
        columnMap.put("databaseName_", "DATABASENAME_");
        columnMap.put("tableName_", "TABLENAME_");
        columnMap.put("createBy_", "CREATEBY_");
        columnMap.put("createTime_", "CREATETIME_");
        columnMap.put("updateBy_", "UPDATEBY_");
        columnMap.put("updateTime_", "UPDATETIME_");

	javaTypeMap.put("targetId_", "String");
        javaTypeMap.put("targetName_", "String");
        javaTypeMap.put("columns_", "byte[]");
        javaTypeMap.put("databaseId_", "String");
        javaTypeMap.put("databaseName_", "String");
        javaTypeMap.put("tableName_", "String");
        javaTypeMap.put("createBy_", "String");
        javaTypeMap.put("createTime_", "Date");
        javaTypeMap.put("updateBy_", "String");
        javaTypeMap.put("updateTime_", "Date");
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
        tableDefinition.setName("EtlDataTarget");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("targetId_");
        idColumn.setColumnName("TARGET_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(64);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition targetName_ = new ColumnDefinition();
        targetName_.setName("targetName_");
        targetName_.setColumnName("TARGET_NAME_");
        targetName_.setJavaType("String");
        targetName_.setLength(50);
        tableDefinition.addColumn(targetName_);

        ColumnDefinition columns_ = new ColumnDefinition();
        columns_.setName("columns_");
        columns_.setColumnName("COLUMNS_");
        columns_.setJavaType("byte[]");
        tableDefinition.addColumn(columns_);

        ColumnDefinition databaseId_ = new ColumnDefinition();
        databaseId_.setName("databaseId_");
        databaseId_.setColumnName("DATABASEID_");
        databaseId_.setJavaType("String");
        databaseId_.setLength(64);
        tableDefinition.addColumn(databaseId_);

        ColumnDefinition databaseName_ = new ColumnDefinition();
        databaseName_.setName("databaseName_");
        databaseName_.setColumnName("DATABASENAME_");
        databaseName_.setJavaType("String");
        databaseName_.setLength(50);
        tableDefinition.addColumn(databaseName_);

        ColumnDefinition tableName_ = new ColumnDefinition();
        tableName_.setName("tableName_");
        tableName_.setColumnName("TABLENAME_");
        tableName_.setJavaType("String");
        tableName_.setLength(50);
        tableDefinition.addColumn(tableName_);

        ColumnDefinition createBy_ = new ColumnDefinition();
        createBy_.setName("createBy_");
        createBy_.setColumnName("CREATEBY_");
        createBy_.setJavaType("String");
        createBy_.setLength(30);
        tableDefinition.addColumn(createBy_);

        ColumnDefinition createTime_ = new ColumnDefinition();
        createTime_.setName("createTime_");
        createTime_.setColumnName("CREATETIME_");
        createTime_.setJavaType("Date");
        tableDefinition.addColumn(createTime_);

        ColumnDefinition updateBy_ = new ColumnDefinition();
        updateBy_.setName("updateBy_");
        updateBy_.setColumnName("UPDATEBY_");
        updateBy_.setJavaType("String");
        updateBy_.setLength(30);
        tableDefinition.addColumn(updateBy_);

        ColumnDefinition updateTime_ = new ColumnDefinition();
        updateTime_.setName("updateTime_");
        updateTime_.setColumnName("UPDATETIME_");
        updateTime_.setJavaType("Date");
        tableDefinition.addColumn(updateTime_);

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
						.getResourceAsStream("com/glaf/etl/domain/EtlDataTarget.mapping.xml");
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

    private EtlDataTargetDomainFactory() {

    }

}
