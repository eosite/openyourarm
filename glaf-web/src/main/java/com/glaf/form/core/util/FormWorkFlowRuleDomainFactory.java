package com.glaf.form.core.util;

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
public class FormWorkFlowRuleDomainFactory {

    public static final String TABLENAME = "FORM_WORKFLOW_RULE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("actResDefId", "ACT_RESDEF_ID_");
        columnMap.put("actResourceId", "ACT_RESOURCE_ID_");
        columnMap.put("actTaskId", "ACT_TASKID_");
        columnMap.put("actTaskName", "ACT_TASKNAME_");
        columnMap.put("bytes", "BYTES_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDatetime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDatetime", "MODIFYDATETIME_");
        columnMap.put("pageId", "PAGE_ID_");
        columnMap.put("version", "VERSION_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("actResDefId", "Long");
        javaTypeMap.put("actResourceId", "String");
        javaTypeMap.put("actTaskId", "String");
        javaTypeMap.put("actTaskName", "String");
        javaTypeMap.put("bytes", "Clob");
        javaTypeMap.put("creator", "String");
        javaTypeMap.put("createDatetime", "Date");
        javaTypeMap.put("modifier", "String");
        javaTypeMap.put("modifyDatetime", "Date");
        javaTypeMap.put("pageId", "String");
        javaTypeMap.put("version", "Integer");
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
        tableDefinition.setName("FormWorkFlowRule");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition actResDefId = new ColumnDefinition();
        actResDefId.setName("actResDefId");
        actResDefId.setColumnName("ACT_RESDEF_ID_");
        actResDefId.setJavaType("Long");
        tableDefinition.addColumn(actResDefId);

        ColumnDefinition actResourceId = new ColumnDefinition();
        actResourceId.setName("actResourceId");
        actResourceId.setColumnName("ACT_RESOURCE_ID_");
        actResourceId.setJavaType("String");
        actResourceId.setLength(50);
        tableDefinition.addColumn(actResourceId);

        ColumnDefinition actTaskId = new ColumnDefinition();
        actTaskId.setName("actTaskId");
        actTaskId.setColumnName("ACT_TASKID_");
        actTaskId.setJavaType("String");
        actTaskId.setLength(50);
        tableDefinition.addColumn(actTaskId);

        ColumnDefinition actTaskName = new ColumnDefinition();
        actTaskName.setName("actTaskName");
        actTaskName.setColumnName("ACT_TASKNAME_");
        actTaskName.setJavaType("String");
        actTaskName.setLength(100);
        tableDefinition.addColumn(actTaskName);

        ColumnDefinition bytes = new ColumnDefinition();
        bytes.setName("bytes");
        bytes.setColumnName("BYTES_");
        bytes.setJavaType("Clob");
        tableDefinition.addColumn(bytes);

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

        ColumnDefinition pageId = new ColumnDefinition();
        pageId.setName("pageId");
        pageId.setColumnName("PAGE_ID_");
        pageId.setJavaType("String");
        pageId.setLength(255);
        tableDefinition.addColumn(pageId);

        ColumnDefinition version = new ColumnDefinition();
        version.setName("version");
        version.setColumnName("VERSION_");
        version.setJavaType("Integer");
        tableDefinition.addColumn(version);

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
						.getResourceAsStream("com/glaf/form/core/domain/FormWorkFlowRule.mapping.xml");
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

    private FormWorkFlowRuleDomainFactory() {

    }

}
