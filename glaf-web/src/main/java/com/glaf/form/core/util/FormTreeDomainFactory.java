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
public class FormTreeDomainFactory {

    public static final String TABLENAME = "FORM_TREE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID");
        columnMap.put("code", "CODE");
        columnMap.put("createBy", "CREATEBY");
        columnMap.put("createDate", "CREATEDATE");
        columnMap.put("nodeDesc", "NODEDESC");
        columnMap.put("discriminator", "DISCRIMINATOR");
        columnMap.put("icon", "ICON");
        columnMap.put("iconCls", "ICONCLS");
        columnMap.put("locked", "LOCKED");
        columnMap.put("moveable", "MOVEABLE");
        columnMap.put("name", "NAME");
        columnMap.put("parent", "PARENT");
        columnMap.put("sort", "SORT");
        columnMap.put("treeId", "TREEID");
        columnMap.put("updateBy", "UPDATEBY");
        columnMap.put("updateDate", "UPDATEDATE");
        columnMap.put("url", "URL");
        columnMap.put("category", "CATEGORY");

	javaTypeMap.put("id", "Long");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createDate", "Date");
        javaTypeMap.put("nodeDesc", "String");
        javaTypeMap.put("discriminator", "String");
        javaTypeMap.put("icon", "String");
        javaTypeMap.put("iconCls", "String");
        javaTypeMap.put("locked", "Integer");
        javaTypeMap.put("moveable", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("parent", "Long");
        javaTypeMap.put("sort", "Integer");
        javaTypeMap.put("treeId", "String");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateDate", "Date");
        javaTypeMap.put("url", "String");
        javaTypeMap.put("category", "String");
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
        tableDefinition.setName("FormTree");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition code = new ColumnDefinition();
        code.setName("code");
        code.setColumnName("CODE");
        code.setJavaType("String");
        code.setLength(50);
        tableDefinition.addColumn(code);

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

        ColumnDefinition nodeDesc = new ColumnDefinition();
        nodeDesc.setName("nodeDesc");
        nodeDesc.setColumnName("NODEDESC");
        nodeDesc.setJavaType("String");
        nodeDesc.setLength(500);
        tableDefinition.addColumn(nodeDesc);

        ColumnDefinition discriminator = new ColumnDefinition();
        discriminator.setName("discriminator");
        discriminator.setColumnName("DISCRIMINATOR");
        discriminator.setJavaType("String");
        discriminator.setLength(10);
        tableDefinition.addColumn(discriminator);

        ColumnDefinition icon = new ColumnDefinition();
        icon.setName("icon");
        icon.setColumnName("ICON");
        icon.setJavaType("String");
        icon.setLength(50);
        tableDefinition.addColumn(icon);

        ColumnDefinition iconCls = new ColumnDefinition();
        iconCls.setName("iconCls");
        iconCls.setColumnName("ICONCLS");
        iconCls.setJavaType("String");
        iconCls.setLength(50);
        tableDefinition.addColumn(iconCls);

        ColumnDefinition locked = new ColumnDefinition();
        locked.setName("locked");
        locked.setColumnName("LOCKED");
        locked.setJavaType("Integer");
        tableDefinition.addColumn(locked);

        ColumnDefinition moveable = new ColumnDefinition();
        moveable.setName("moveable");
        moveable.setColumnName("MOVEABLE");
        moveable.setJavaType("String");
        moveable.setLength(10);
        tableDefinition.addColumn(moveable);

        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME");
        name.setJavaType("String");
        name.setLength(100);
        tableDefinition.addColumn(name);

        ColumnDefinition parent = new ColumnDefinition();
        parent.setName("parent");
        parent.setColumnName("PARENT");
        parent.setJavaType("Long");
        tableDefinition.addColumn(parent);

        ColumnDefinition sort = new ColumnDefinition();
        sort.setName("sort");
        sort.setColumnName("SORT");
        sort.setJavaType("Integer");
        tableDefinition.addColumn(sort);

        ColumnDefinition treeId = new ColumnDefinition();
        treeId.setName("treeId");
        treeId.setColumnName("TREEID");
        treeId.setJavaType("String");
        treeId.setLength(500);
        tableDefinition.addColumn(treeId);

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

        ColumnDefinition url = new ColumnDefinition();
        url.setName("url");
        url.setColumnName("URL");
        url.setJavaType("String");
        url.setLength(500);
        tableDefinition.addColumn(url);

        ColumnDefinition category = new ColumnDefinition();
        category.setName("category");
        category.setColumnName("CATEGORY");
        category.setJavaType("String");
        category.setLength(10);
        tableDefinition.addColumn(category);

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
						.getResourceAsStream("com/glaf/form/core/domain/FormTree.mapping.xml");
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

    private FormTreeDomainFactory() {

    }

}
