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
public class ObjTemplateDomainFactory {

    public static final String TABLENAME = "OBJ_TEMPLATE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("templateId", "TEMPLATE_ID_");
        columnMap.put("tmpName", "TMP_NAME_");
        columnMap.put("objType", "OBJ_TYPE_");
        columnMap.put("tmpContent", "TMP_CONTENT_");
        columnMap.put("thumbnail", "THUMBNAIL_");
        columnMap.put("owner", "OWNER_");
        columnMap.put("delFlag", "DELFLAG_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("updateTime", "UPDATETIME_");

	javaTypeMap.put("templateId", "Long");
        javaTypeMap.put("tmpName", "String");
        javaTypeMap.put("objType", "String");
        javaTypeMap.put("tmpContent", "Blob");
        javaTypeMap.put("thumbnail", "Blob");
        javaTypeMap.put("owner", "String");
        javaTypeMap.put("delFlag", "Integer");
        javaTypeMap.put("creator", "String");
        javaTypeMap.put("createTime", "Date");
        javaTypeMap.put("modifier", "String");
        javaTypeMap.put("updateTime", "Date");
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
        tableDefinition.setName("ObjTemplate");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("templateId");
        idColumn.setColumnName("TEMPLATE_ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition tmpName = new ColumnDefinition();
        tmpName.setName("tmpName");
        tmpName.setColumnName("TMP_NAME_");
        tmpName.setJavaType("String");
        tmpName.setLength(50);
        tableDefinition.addColumn(tmpName);

        ColumnDefinition objType = new ColumnDefinition();
        objType.setName("objType");
        objType.setColumnName("OBJ_TYPE_");
        objType.setJavaType("String");
        objType.setLength(60);
        tableDefinition.addColumn(objType);

        ColumnDefinition tmpContent = new ColumnDefinition();
        tmpContent.setName("tmpContent");
        tmpContent.setColumnName("TMP_CONTENT_");
        tmpContent.setJavaType("Blob");
        tableDefinition.addColumn(tmpContent);

        ColumnDefinition thumbnail = new ColumnDefinition();
        thumbnail.setName("thumbnail");
        thumbnail.setColumnName("THUMBNAIL_");
        thumbnail.setJavaType("Blob");
        tableDefinition.addColumn(thumbnail);

        ColumnDefinition owner = new ColumnDefinition();
        owner.setName("owner");
        owner.setColumnName("OWNER_");
        owner.setJavaType("String");
        owner.setLength(20);
        tableDefinition.addColumn(owner);

        ColumnDefinition delFlag = new ColumnDefinition();
        delFlag.setName("delFlag");
        delFlag.setColumnName("DELFLAG_");
        delFlag.setJavaType("Integer");
        tableDefinition.addColumn(delFlag);

        ColumnDefinition creator = new ColumnDefinition();
        creator.setName("creator");
        creator.setColumnName("CREATOR_");
        creator.setJavaType("String");
        creator.setLength(20);
        tableDefinition.addColumn(creator);

        ColumnDefinition createTime = new ColumnDefinition();
        createTime.setName("createTime");
        createTime.setColumnName("CREATETIME_");
        createTime.setJavaType("Date");
        tableDefinition.addColumn(createTime);

        ColumnDefinition modifier = new ColumnDefinition();
        modifier.setName("modifier");
        modifier.setColumnName("MODIFIER_");
        modifier.setJavaType("String");
        modifier.setLength(20);
        tableDefinition.addColumn(modifier);

        ColumnDefinition updateTime = new ColumnDefinition();
        updateTime.setName("updateTime");
        updateTime.setColumnName("UPDATETIME_");
        updateTime.setJavaType("Date");
        tableDefinition.addColumn(updateTime);

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
						.getResourceAsStream("com/glaf/form/domain/ObjTemplate.mapping.xml");
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

    private ObjTemplateDomainFactory() {

    }

}
