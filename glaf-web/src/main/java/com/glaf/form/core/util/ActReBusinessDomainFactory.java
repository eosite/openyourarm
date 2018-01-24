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
public class ActReBusinessDomainFactory {

    public static final String TABLENAME = "ACT_RE_BUSINESS";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("bustbName", "BUSTBNAME_");
        columnMap.put("bustbId", "BUSTBID_");
        columnMap.put("busValue", "BUSVALUE_");
        columnMap.put("processId", "PROCESSID_");
        columnMap.put("processName", "PROCESSNAME_");
        columnMap.put("pageId", "PAGEID_");
        columnMap.put("key", "KEY_");
        columnMap.put("url", "URL_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createDate", "CREATEDATE_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateDate", "UPDATEDATE_");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("bustbName", "String");
        javaTypeMap.put("bustbId", "String");
        javaTypeMap.put("busValue", "String");
        javaTypeMap.put("processId", "String");
        javaTypeMap.put("processName", "String");
        javaTypeMap.put("pageId", "String");
        javaTypeMap.put("key", "String");
        javaTypeMap.put("url", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createDate", "Date");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateDate", "Date");
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
        tableDefinition.setName("ActReBusiness");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition bustbName = new ColumnDefinition();
        bustbName.setName("bustbName");
        bustbName.setColumnName("BUSTBNAME_");
        bustbName.setJavaType("String");
        bustbName.setLength(50);
        tableDefinition.addColumn(bustbName);

        ColumnDefinition bustbId = new ColumnDefinition();
        bustbId.setName("bustbId");
        bustbId.setColumnName("BUSTBID_");
        bustbId.setJavaType("String");
        bustbId.setLength(30);
        tableDefinition.addColumn(bustbId);

        ColumnDefinition busValue = new ColumnDefinition();
        busValue.setName("busValue");
        busValue.setColumnName("BUSVALUE_");
        busValue.setJavaType("String");
        busValue.setLength(50);
        tableDefinition.addColumn(busValue);

        ColumnDefinition processId = new ColumnDefinition();
        processId.setName("processId");
        processId.setColumnName("PROCESSID_");
        processId.setJavaType("String");
        processId.setLength(50);
        tableDefinition.addColumn(processId);

        ColumnDefinition processName = new ColumnDefinition();
        processName.setName("processName");
        processName.setColumnName("PROCESSNAME_");
        processName.setJavaType("String");
        processName.setLength(200);
        tableDefinition.addColumn(processName);

        ColumnDefinition pageId = new ColumnDefinition();
        pageId.setName("pageId");
        pageId.setColumnName("PAGEID_");
        pageId.setJavaType("String");
        pageId.setLength(50);
        tableDefinition.addColumn(pageId);

        ColumnDefinition key = new ColumnDefinition();
        key.setName("key");
        key.setColumnName("KEY_");
        key.setJavaType("String");
        key.setLength(100);
        tableDefinition.addColumn(key);

        ColumnDefinition url = new ColumnDefinition();
        url.setName("url");
        url.setColumnName("URL_");
        url.setJavaType("String");
        url.setLength(200);
        tableDefinition.addColumn(url);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY_");
        createBy.setJavaType("String");
        createBy.setLength(30);
        tableDefinition.addColumn(createBy);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE_");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY_");
        updateBy.setJavaType("String");
        updateBy.setLength(30);
        tableDefinition.addColumn(updateBy);

        ColumnDefinition updateDate = new ColumnDefinition();
        updateDate.setName("updateDate");
        updateDate.setColumnName("UPDATEDATE_");
        updateDate.setJavaType("Date");
        tableDefinition.addColumn(updateDate);

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
						.getResourceAsStream("com/glaf/form/core/domain/ActReBusiness.mapping.xml");
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

    private ActReBusinessDomainFactory() {

    }

}
