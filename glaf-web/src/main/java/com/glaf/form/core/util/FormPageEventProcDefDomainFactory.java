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
public class FormPageEventProcDefDomainFactory {

    public static final String TABLENAME = "FORM_PAGE_EVENT_PROCDEF";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("pageId", "PAGEID_");
        columnMap.put("compId", "COMPID_");
        columnMap.put("event_", "EVENT_");
        columnMap.put("procDefKey", "PROCDEF_KEY_");
        columnMap.put("procDefId", "PROCDEF_ID_");
        columnMap.put("procModelId", "PROCMODEL_ID_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("pageId", "String");
        javaTypeMap.put("compId", "String");
        javaTypeMap.put("event_", "String");
        javaTypeMap.put("procDefKey", "String");
        javaTypeMap.put("procDefId", "String");
        javaTypeMap.put("procModelId", "String");
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
        tableDefinition.setName("FormPageEventProcDef");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition pageId = new ColumnDefinition();
        pageId.setName("pageId");
        pageId.setColumnName("PAGEID_");
        pageId.setJavaType("String");
        pageId.setLength(255);
        tableDefinition.addColumn(pageId);

        ColumnDefinition compId = new ColumnDefinition();
        compId.setName("compId");
        compId.setColumnName("COMPID_");
        compId.setJavaType("String");
        compId.setLength(50);
        tableDefinition.addColumn(compId);

        ColumnDefinition event_ = new ColumnDefinition();
        event_.setName("event_");
        event_.setColumnName("EVENT_");
        event_.setJavaType("String");
        event_.setLength(50);
        tableDefinition.addColumn(event_);

        ColumnDefinition procDefKey = new ColumnDefinition();
        procDefKey.setName("procDefKey");
        procDefKey.setColumnName("PROCDEF_KEY_");
        procDefKey.setJavaType("String");
        procDefKey.setLength(255);
        tableDefinition.addColumn(procDefKey);

        ColumnDefinition procDefId = new ColumnDefinition();
        procDefId.setName("procDefId");
        procDefId.setColumnName("PROCDEF_ID_");
        procDefId.setJavaType("String");
        procDefId.setLength(64);
        tableDefinition.addColumn(procDefId);

        ColumnDefinition procModelId = new ColumnDefinition();
        procModelId.setName("procModelId");
        procModelId.setColumnName("PROCMODEL_ID_");
        procModelId.setJavaType("String");
        procModelId.setLength(64);
        tableDefinition.addColumn(procModelId);

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
						.getResourceAsStream("com/glaf/form/core/domain/FormPageEventProcDef.mapping.xml");
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

    private FormPageEventProcDefDomainFactory() {

    }

}
