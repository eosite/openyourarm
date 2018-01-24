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
public class ObjectCategoryDomainFactory {

    public static final String TABLENAME = "OBJ_CATEGORY";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("categoryId", "CATEGORY_ID_");
        columnMap.put("treeID", "TREEID_");
        columnMap.put("code", "CODE_");
        columnMap.put("name", "NAME_");
        columnMap.put("custom", "CUSTOM_");
        columnMap.put("owner", "OWNER_");
        columnMap.put("parentId", "PARENTID_");
        columnMap.put("orderNo", "ORDERNO_");
        columnMap.put("level", "LEVEL_");
        columnMap.put("delFlag", "DELFLAG_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("updateTime", "UPDATETIME_");

	javaTypeMap.put("categoryId", "Long");
        javaTypeMap.put("treeID", "String");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("custom", "Integer");
        javaTypeMap.put("owner", "String");
        javaTypeMap.put("parentId", "Long");
        javaTypeMap.put("orderNo", "Integer");
        javaTypeMap.put("level", "Boolean");
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
        tableDefinition.setName("ObjectCategory");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("categoryId");
        idColumn.setColumnName("CATEGORY_ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition treeID = new ColumnDefinition();
        treeID.setName("treeID");
        treeID.setColumnName("TREEID_");
        treeID.setJavaType("String");
        treeID.setLength(150);
        tableDefinition.addColumn(treeID);

        ColumnDefinition code = new ColumnDefinition();
        code.setName("code");
        code.setColumnName("CODE_");
        code.setJavaType("String");
        code.setLength(30);
        tableDefinition.addColumn(code);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(30);
        tableDefinition.addColumn(name);

        ColumnDefinition custom = new ColumnDefinition();
        custom.setName("custom");
        custom.setColumnName("CUSTOM_");
        custom.setJavaType("Integer");
        tableDefinition.addColumn(custom);

        ColumnDefinition owner = new ColumnDefinition();
        owner.setName("owner");
        owner.setColumnName("OWNER_");
        owner.setJavaType("String");
        owner.setLength(20);
        tableDefinition.addColumn(owner);

        ColumnDefinition parentId = new ColumnDefinition();
        parentId.setName("parentId");
        parentId.setColumnName("PARENTID_");
        parentId.setJavaType("Long");
        tableDefinition.addColumn(parentId);

        ColumnDefinition orderNo = new ColumnDefinition();
        orderNo.setName("orderNo");
        orderNo.setColumnName("ORDERNO_");
        orderNo.setJavaType("Integer");
        tableDefinition.addColumn(orderNo);

        ColumnDefinition level = new ColumnDefinition();
        level.setName("level");
        level.setColumnName("LEVEL_");
        level.setJavaType("Boolean");
        tableDefinition.addColumn(level);

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
						.getResourceAsStream("com/glaf/form/domain/ObjectCategory.mapping.xml");
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

    private ObjectCategoryDomainFactory() {

    }

}
