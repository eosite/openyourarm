package com.glaf.web.util;

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
public class PageResourceDomainFactory {

    public static final String TABLENAME = "PAGE_RESOURCE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("resId", "RES_ID_");
        columnMap.put("resPath", "RES_PATH_");
        columnMap.put("resFileName", "RES_FILENAME_");
        columnMap.put("resName", "RES_NAME_");
        columnMap.put("resContent", "RES_CONTENT_");
        columnMap.put("resType", "RES_TYPE_");
        columnMap.put("resMime", "RES_MIME_");
        columnMap.put("resCrDatetime", "RES_CRDATETIME_");

	javaTypeMap.put("resId", "Long");
        javaTypeMap.put("resPath", "String");
        javaTypeMap.put("resFileName", "String");
        javaTypeMap.put("resName", "String");
        javaTypeMap.put("resContent", "Byte[]");
        javaTypeMap.put("resType", "String");
        javaTypeMap.put("resMime", "String");
        javaTypeMap.put("resCrDatetime", "Date");
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
        tableDefinition.setName("PageResource");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("resId");
        idColumn.setColumnName("RES_ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition resPath = new ColumnDefinition();
        resPath.setName("resPath");
        resPath.setColumnName("RES_PATH_");
        resPath.setJavaType("String");
        resPath.setLength(150);
        tableDefinition.addColumn(resPath);

        ColumnDefinition resFileName = new ColumnDefinition();
        resFileName.setName("resFileName");
        resFileName.setColumnName("RES_FILENAME_");
        resFileName.setJavaType("String");
        resFileName.setLength(150);
        tableDefinition.addColumn(resFileName);

        ColumnDefinition resName = new ColumnDefinition();
        resName.setName("resName");
        resName.setColumnName("RES_NAME_");
        resName.setJavaType("String");
        resName.setLength(150);
        tableDefinition.addColumn(resName);

        ColumnDefinition resContent = new ColumnDefinition();
        resContent.setName("resContent");
        resContent.setColumnName("RES_CONTENT_");
        resContent.setJavaType("Byte[]");
        tableDefinition.addColumn(resContent);

        ColumnDefinition resType = new ColumnDefinition();
        resType.setName("resType");
        resType.setColumnName("RES_TYPE_");
        resType.setJavaType("String");
        resType.setLength(20);
        tableDefinition.addColumn(resType);

        ColumnDefinition resMime = new ColumnDefinition();
        resMime.setName("resMime");
        resMime.setColumnName("RES_MIME_");
        resMime.setJavaType("String");
        resMime.setLength(50);
        tableDefinition.addColumn(resMime);

        ColumnDefinition resCrDatetime = new ColumnDefinition();
        resCrDatetime.setName("resCrDatetime");
        resCrDatetime.setColumnName("RES_CRDATETIME_");
        resCrDatetime.setJavaType("Date");
        tableDefinition.addColumn(resCrDatetime);

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
						.getResourceAsStream("com/glaf/web/domain/PageResource.mapping.xml");
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

    private PageResourceDomainFactory() {

    }

}
