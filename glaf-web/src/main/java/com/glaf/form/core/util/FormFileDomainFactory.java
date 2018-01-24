package com.glaf.form.core.util;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import com.glaf.core.base.DataRequest;
import com.glaf.core.base.DataRequest.FilterDescriptor;
import com.glaf.core.domain.ColumnDefinition;
import com.glaf.core.domain.TableDefinition;
import com.glaf.core.util.DBUtils;



/**
 * 
 * 实体数据工厂类
 *
 */
public class FormFileDomainFactory {

    public static final String TABLENAME = "FORM_FILE_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("parent", "PARENT_");
        columnMap.put("type", "TYPE_");
        columnMap.put("fieldType", "FILETYPE_");
        columnMap.put("fileName", "FILENAME_");
        columnMap.put("fileSize", "FILESIZE_");
        columnMap.put("fileContent", "FILECONTENT_");
        columnMap.put("saveServicePath", "SAVESERVICEPATH_");
        columnMap.put("vision", "VISION_");
        columnMap.put("status", "STATUS_");
        columnMap.put("error", "ERROR");
        columnMap.put("business", "BUSINESS");
        columnMap.put("createBy", "CREATEBY");
        columnMap.put("createDate", "CREATEDATE");
        columnMap.put("updateBy", "UPDATEBY");
        columnMap.put("updateDate", "UPDATEDATE");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("parent", "String");
        javaTypeMap.put("type", "String");
        javaTypeMap.put("fieldType", "String");
        javaTypeMap.put("fileName", "String");
        javaTypeMap.put("fileSize", "Integer");
        javaTypeMap.put("fileContent", "String");
        javaTypeMap.put("saveServicePath", "String");
        javaTypeMap.put("vision", "Integer");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("error", "String");
        javaTypeMap.put("business", "String");
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
        tableDefinition.setName("FormFile");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition parent = new ColumnDefinition();
        parent.setName("parent");
        parent.setColumnName("PARENT_");
        parent.setJavaType("String");
        parent.setLength(50);
        tableDefinition.addColumn(parent);

        ColumnDefinition type = new ColumnDefinition();
        type.setName("type");
        type.setColumnName("TYPE_");
        type.setJavaType("String");
        type.setLength(1);
        tableDefinition.addColumn(type);

        ColumnDefinition fieldType = new ColumnDefinition();
        fieldType.setName("fieldType");
        fieldType.setColumnName("FILETYPE_");
        fieldType.setJavaType("String");
        fieldType.setLength(20);
        tableDefinition.addColumn(fieldType);

        ColumnDefinition fileName = new ColumnDefinition();
        fileName.setName("fileName");
        fileName.setColumnName("FILENAME_");
        fileName.setJavaType("String");
        fileName.setLength(100);
        tableDefinition.addColumn(fileName);

        ColumnDefinition fileSize = new ColumnDefinition();
        fileSize.setName("fileSize");
        fileSize.setColumnName("FILESIZE_");
        fileSize.setJavaType("Integer");
        tableDefinition.addColumn(fileSize);

        ColumnDefinition fileContent = new ColumnDefinition();
        fileContent.setName("fileContent");
        fileContent.setColumnName("FILECONTENT_");
        fileContent.setJavaType("String");
        fileContent.setLength(50);
        tableDefinition.addColumn(fileContent);

        ColumnDefinition saveServicePath = new ColumnDefinition();
        saveServicePath.setName("saveServicePath");
        saveServicePath.setColumnName("SAVESERVICEPATH_");
        saveServicePath.setJavaType("String");
        saveServicePath.setLength(500);
        tableDefinition.addColumn(saveServicePath);

        ColumnDefinition vision = new ColumnDefinition();
        vision.setName("vision");
        vision.setColumnName("VISION_");
        vision.setJavaType("Integer");
        tableDefinition.addColumn(vision);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS_");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition error = new ColumnDefinition();
        error.setName("error");
        error.setColumnName("ERROR");
        error.setJavaType("String");
        error.setLength(255);
        tableDefinition.addColumn(error);

        ColumnDefinition business = new ColumnDefinition();
        business.setName("business");
        business.setColumnName("BUSINESS");
        business.setJavaType("String");
        business.setLength(50);
        tableDefinition.addColumn(business);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY");
        createBy.setJavaType("String");
        createBy.setLength(50);
        tableDefinition.addColumn(createBy);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY");
        updateBy.setJavaType("String");
        updateBy.setLength(50);
        tableDefinition.addColumn(updateBy);

        ColumnDefinition updateDate = new ColumnDefinition();
        updateDate.setName("updateDate");
        updateDate.setColumnName("UPDATEDATE");
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

    private FormFileDomainFactory() {

    }

}
