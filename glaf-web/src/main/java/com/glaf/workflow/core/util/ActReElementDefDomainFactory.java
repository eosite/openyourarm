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
public class ActReElementDefDomainFactory {

    public static final String TABLENAME = "ACT_RE_ELEMENTDEF";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("ID", "ID_");
        columnMap.put("eleType", "ELE_TYPE_");
        columnMap.put("eleResourceId", "ELE_RESOURCE_ID_");
        columnMap.put("eleID", "ELE_ID_");
        columnMap.put("eleName", "ELE_NAME_");
        columnMap.put("eleDesc", "ELE_DESC");
        columnMap.put("modelId", "MODEL_ID_");
        columnMap.put("ProceDefId", "PROCEDEF_ID_");
        columnMap.put("bytes", "BTYES_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDatetime", "CREATEDATETIME_");
        columnMap.put("modify", "MODIFIER_");
        columnMap.put("modifyDatetime", "MODIFYDATETIME_");

	javaTypeMap.put("ID", "Long");
        javaTypeMap.put("eleType", "String");
        javaTypeMap.put("eleResourceId", "String");
        javaTypeMap.put("eleID", "String");
        javaTypeMap.put("eleName", "String");
        javaTypeMap.put("eleDesc", "String");
        javaTypeMap.put("modelId", "String");
        javaTypeMap.put("ProceDefId", "String");
        javaTypeMap.put("bytes", "String");
        javaTypeMap.put("creator", "String");
        javaTypeMap.put("createDatetime", "Date");
        javaTypeMap.put("modify", "String");
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
        tableDefinition.setName("ActReElementDef");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("ID");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition eleType = new ColumnDefinition();
        eleType.setName("eleType");
        eleType.setColumnName("ELE_TYPE_");
        eleType.setJavaType("String");
        eleType.setLength(30);
        tableDefinition.addColumn(eleType);

        ColumnDefinition eleResourceId = new ColumnDefinition();
        eleResourceId.setName("eleResourceId");
        eleResourceId.setColumnName("ELE_RESOURCE_ID_");
        eleResourceId.setJavaType("String");
        eleResourceId.setLength(50);
        tableDefinition.addColumn(eleResourceId);

        ColumnDefinition eleID = new ColumnDefinition();
        eleID.setName("eleID");
        eleID.setColumnName("ELE_ID_");
        eleID.setJavaType("String");
        eleID.setLength(50);
        tableDefinition.addColumn(eleID);

        ColumnDefinition eleName = new ColumnDefinition();
        eleName.setName("eleName");
        eleName.setColumnName("ELE_NAME_");
        eleName.setJavaType("String");
        eleName.setLength(100);
        tableDefinition.addColumn(eleName);

        ColumnDefinition eleDesc = new ColumnDefinition();
        eleDesc.setName("eleDesc");
        eleDesc.setColumnName("ELE_DESC");
        eleDesc.setJavaType("String");
        eleDesc.setLength(150);
        tableDefinition.addColumn(eleDesc);

        ColumnDefinition modelId = new ColumnDefinition();
        modelId.setName("modelId");
        modelId.setColumnName("MODEL_ID_");
        modelId.setJavaType("String");
        modelId.setLength(64);
        tableDefinition.addColumn(modelId);

        ColumnDefinition ProceDefId = new ColumnDefinition();
        ProceDefId.setName("ProceDefId");
        ProceDefId.setColumnName("PROCEDEF_ID_");
        ProceDefId.setJavaType("String");
        ProceDefId.setLength(64);
        tableDefinition.addColumn(ProceDefId);

        ColumnDefinition bytes = new ColumnDefinition();
        bytes.setName("bytes");
        bytes.setColumnName("BTYES_");
        bytes.setJavaType("String");
        bytes.setLength(0);
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

        ColumnDefinition modify = new ColumnDefinition();
        modify.setName("modify");
        modify.setColumnName("MODIFIER_");
        modify.setJavaType("String");
        modify.setLength(20);
        tableDefinition.addColumn(modify);

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
						.getResourceAsStream("com/glaf/workflow/core/domain/ActReElementDef.mapping.xml");
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

    private ActReElementDefDomainFactory() {

    }

}
