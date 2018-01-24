package com.glaf.workflow.core.util;

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
public class ProcessDefMappingDomainFactory {

    public static final String TABLENAME = "PROCESS_DEF_MAPPING";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("iD", "ID_");
        columnMap.put("srcProcDefId", "SRC_PROC_DEF_ID_");
        columnMap.put("desProcDefId", "DES_PROC_DEF_ID_");
        columnMap.put("srcSysId", "SRC_SYS_ID_");
        columnMap.put("desSysId", "DES_SYS_ID_");
        columnMap.put("createDatetime", "CREAT_DATETIME_");
        columnMap.put("creator", "CREATOR_");

	javaTypeMap.put("iD", "String");
        javaTypeMap.put("srcProcDefId", "String");
        javaTypeMap.put("desProcDefId", "String");
        javaTypeMap.put("srcSysId", "String");
        javaTypeMap.put("desSysId", "String");
        javaTypeMap.put("createDatetime", "Date");
        javaTypeMap.put("creator", "String");
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
        tableDefinition.setName("ProcessDefMapping");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("iD");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition srcProcDefId = new ColumnDefinition();
        srcProcDefId.setName("srcProcDefId");
        srcProcDefId.setColumnName("SRC_PROC_DEF_ID_");
        srcProcDefId.setJavaType("String");
        srcProcDefId.setLength(50);
        tableDefinition.addColumn(srcProcDefId);

        ColumnDefinition desProcDefId = new ColumnDefinition();
        desProcDefId.setName("desProcDefId");
        desProcDefId.setColumnName("DES_PROC_DEF_ID_");
        desProcDefId.setJavaType("String");
        desProcDefId.setLength(50);
        tableDefinition.addColumn(desProcDefId);

        ColumnDefinition srcSysId = new ColumnDefinition();
        srcSysId.setName("srcSysId");
        srcSysId.setColumnName("SRC_SYS_ID_");
        srcSysId.setJavaType("String");
        srcSysId.setLength(50);
        tableDefinition.addColumn(srcSysId);

        ColumnDefinition desSysId = new ColumnDefinition();
        desSysId.setName("desSysId");
        desSysId.setColumnName("DES_SYS_ID_");
        desSysId.setJavaType("String");
        desSysId.setLength(50);
        tableDefinition.addColumn(desSysId);

        ColumnDefinition createDatetime = new ColumnDefinition();
        createDatetime.setName("createDatetime");
        createDatetime.setColumnName("CREAT_DATETIME_");
        createDatetime.setJavaType("Date");
        tableDefinition.addColumn(createDatetime);

        ColumnDefinition creator = new ColumnDefinition();
        creator.setName("creator");
        creator.setColumnName("CREATOR_");
        creator.setJavaType("String");
        creator.setLength(30);
        tableDefinition.addColumn(creator);

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
						.getResourceAsStream("com/glaf/workflow/domain/ProcessDefMapping.mapping.xml");
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

    private ProcessDefMappingDomainFactory() {

    }

}
