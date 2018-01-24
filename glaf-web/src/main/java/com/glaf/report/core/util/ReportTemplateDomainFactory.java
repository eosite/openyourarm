package com.glaf.report.core.util;

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
public class ReportTemplateDomainFactory {

    public static final String TABLENAME = "REPORT_TEMPLATE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("rev", "REV_");
        columnMap.put("name", "NAME_");
        columnMap.put("bytes", "BYTES_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createDatatime", "CREATEDATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("modifyDatatime", "MODIFYDATETIME_");
        columnMap.put("status", "STATUS");
        columnMap.put("publish", "PUBLISH_");
        columnMap.put("publishUser", "PUBLISH_USER_");
        columnMap.put("publishDatetime", "PUBLISHDATETIME");
        columnMap.put("fileName", "FILENAME_");
        columnMap.put("ext", "EXT_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("rev", "Integer");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("bytes", "String");
        javaTypeMap.put("creator", "String");
        javaTypeMap.put("createDatatime", "Date");
        javaTypeMap.put("modifier", "String");
        javaTypeMap.put("modifyDatatime", "Date");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("publish", "Integer");
        javaTypeMap.put("publishUser", "String");
        javaTypeMap.put("publishDatetime", "Date");
        javaTypeMap.put("fileName", "String");
        javaTypeMap.put("ext", "String");
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
        tableDefinition.setName("ReportTemplate");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(64);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition rev = new ColumnDefinition();
        rev.setName("rev");
        rev.setColumnName("REV_");
        rev.setJavaType("Integer");
        tableDefinition.addColumn(rev);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition code = new ColumnDefinition();
        code.setName("code");
        code.setColumnName("CODE_");
        code.setJavaType("String");
        code.setLength(50);
        tableDefinition.addColumn(code);

        ColumnDefinition creator = new ColumnDefinition();
        creator.setName("creator");
        creator.setColumnName("CREATOR_");
        creator.setJavaType("String");
        creator.setLength(20);
        tableDefinition.addColumn(creator);

        ColumnDefinition createDatatime = new ColumnDefinition();
        createDatatime.setName("createDatatime");
        createDatatime.setColumnName("CREATEDATETIME_");
        createDatatime.setJavaType("Date");
        tableDefinition.addColumn(createDatatime);

        ColumnDefinition modifier = new ColumnDefinition();
        modifier.setName("modifier");
        modifier.setColumnName("MODIFIER_");
        modifier.setJavaType("String");
        modifier.setLength(20);
        tableDefinition.addColumn(modifier);

        ColumnDefinition modifyDatatime = new ColumnDefinition();
        modifyDatatime.setName("modifyDatatime");
        modifyDatatime.setColumnName("MODIFYDATETIME_");
        modifyDatatime.setJavaType("Date");
        tableDefinition.addColumn(modifyDatatime);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition publish = new ColumnDefinition();
        publish.setName("publish");
        publish.setColumnName("PUBLISH_");
        publish.setJavaType("Integer");
        tableDefinition.addColumn(publish);

        ColumnDefinition publishUser = new ColumnDefinition();
        publishUser.setName("publishUser");
        publishUser.setColumnName("PUBLISH_USER_");
        publishUser.setJavaType("String");
        publishUser.setLength(20);
        tableDefinition.addColumn(publishUser);

        ColumnDefinition publishDatetime = new ColumnDefinition();
        publishDatetime.setName("publishDatetime");
        publishDatetime.setColumnName("PUBLISHDATETIME");
        publishDatetime.setJavaType("Date");
        tableDefinition.addColumn(publishDatetime);

        ColumnDefinition fileName = new ColumnDefinition();
        fileName.setName("fileName");
        fileName.setColumnName("FILENAME_");
        fileName.setJavaType("String");
        fileName.setLength(50);
        tableDefinition.addColumn(fileName);

        ColumnDefinition ext = new ColumnDefinition();
        ext.setName("ext");
        ext.setColumnName("EXT_");
        ext.setJavaType("String");
        ext.setLength(10);
        tableDefinition.addColumn(ext);

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
						.getResourceAsStream("com/glaf/report/domain/ReportTemplate.mapping.xml");
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

    private ReportTemplateDomainFactory() {

    }

}
