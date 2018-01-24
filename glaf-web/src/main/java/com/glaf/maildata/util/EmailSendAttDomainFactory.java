package com.glaf.maildata.util;

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
public class EmailSendAttDomainFactory {

    public static final String TABLENAME = "EMAIL_SENDATT";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("fileId", "FILEID");
        columnMap.put("topId", "TOPID");
        columnMap.put("fileName", "FILE_NAME");
        columnMap.put("fileContent", "FILE_CONTENT");
        columnMap.put("cTime", "CTIME");
        columnMap.put("fileSize", "FILESIZE");

	javaTypeMap.put("fileId", "String");
        javaTypeMap.put("topId", "String");
        javaTypeMap.put("fileName", "String");
        javaTypeMap.put("fileContent", "Blob");
        javaTypeMap.put("cTime", "Date");
        javaTypeMap.put("fileSize", "Integer");
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
        tableDefinition.setName("EmailSendAtt");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("fileId");
        idColumn.setColumnName("FILEID");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition topId = new ColumnDefinition();
        topId.setName("topId");
        topId.setColumnName("TOPID");
        topId.setJavaType("String");
        topId.setLength(50);
        tableDefinition.addColumn(topId);

        ColumnDefinition fileName = new ColumnDefinition();
        fileName.setName("fileName");
        fileName.setColumnName("FILE_NAME");
        fileName.setJavaType("String");
        fileName.setLength(255);
        tableDefinition.addColumn(fileName);

        ColumnDefinition fileContent = new ColumnDefinition();
        fileContent.setName("fileContent");
        fileContent.setColumnName("FILE_CONTENT");
        fileContent.setJavaType("Blob");
        tableDefinition.addColumn(fileContent);

        ColumnDefinition cTime = new ColumnDefinition();
        cTime.setName("cTime");
        cTime.setColumnName("CTIME");
        cTime.setJavaType("Date");
        tableDefinition.addColumn(cTime);

        ColumnDefinition fileSize = new ColumnDefinition();
        fileSize.setName("fileSize");
        fileSize.setColumnName("FILESIZE");
        fileSize.setJavaType("Integer");
        tableDefinition.addColumn(fileSize);

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
						.getResourceAsStream("com/glaf/maildata/domain/EmailSendAtt.mapping.xml");
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

    private EmailSendAttDomainFactory() {

    }

}
