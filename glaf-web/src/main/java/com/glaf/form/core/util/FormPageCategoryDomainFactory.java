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
public class FormPageCategoryDomainFactory {

    public static final String TABLENAME = "FORM_PAGE_CATEGORY";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    protected static TableModel tableModel;

    static{
        columnMap.put("id", "ID_");
        columnMap.put("name", "NAME_");
        columnMap.put("deleteFlag", "DELETEFLAG_");
        columnMap.put("sortNo", "SORTNO_");
        columnMap.put("locked", "LOCKED_");
        columnMap.put("permission", "PERMISSION_");
        columnMap.put("ext1", "EXT1_");
        columnMap.put("ext2", "EXT2_");
        columnMap.put("ext3", "EXT3_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createDate", "CREATEDATA_");

	javaTypeMap.put("id", "Integer");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("deleteFlag", "Integer");
        javaTypeMap.put("sortNo", "Integer");
        javaTypeMap.put("locked", "Integer");
        javaTypeMap.put("permission", "String");
        javaTypeMap.put("ext1", "String");
        javaTypeMap.put("ext2", "String");
        javaTypeMap.put("ext3", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createDate", "Date");
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
        tableDefinition.setName("FormPageCategory");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Integer");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(50);
        tableDefinition.addColumn(name);

        ColumnDefinition deleteFlag = new ColumnDefinition();
        deleteFlag.setName("deleteFlag");
        deleteFlag.setColumnName("DELETEFLAG_");
        deleteFlag.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag);

        ColumnDefinition sortNo = new ColumnDefinition();
        sortNo.setName("sortNo");
        sortNo.setColumnName("SORTNO_");
        sortNo.setJavaType("Integer");
        tableDefinition.addColumn(sortNo);

        ColumnDefinition locked = new ColumnDefinition();
        locked.setName("locked");
        locked.setColumnName("LOCKED_");
        locked.setJavaType("Integer");
        tableDefinition.addColumn(locked);

        ColumnDefinition permission = new ColumnDefinition();
        permission.setName("permission");
        permission.setColumnName("PERMISSION_");
        permission.setJavaType("String");
        permission.setLength(50);
        tableDefinition.addColumn(permission);

        ColumnDefinition ext1 = new ColumnDefinition();
        ext1.setName("ext1");
        ext1.setColumnName("EXT1_");
        ext1.setJavaType("String");
        ext1.setLength(50);
        tableDefinition.addColumn(ext1);

        ColumnDefinition ext2 = new ColumnDefinition();
        ext2.setName("ext2");
        ext2.setColumnName("EXT2_");
        ext2.setJavaType("String");
        ext2.setLength(50);
        tableDefinition.addColumn(ext2);

        ColumnDefinition ext3 = new ColumnDefinition();
        ext3.setName("ext3");
        ext3.setColumnName("EXT3_");
        ext3.setJavaType("String");
        ext3.setLength(50);
        tableDefinition.addColumn(ext3);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY_");
        createBy.setJavaType("String");
        createBy.setLength(50);
        tableDefinition.addColumn(createBy);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATA_");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

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
						.getResourceAsStream("com/glaf/form/core/domain/FormPageCategory.mapping.xml");
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

    private FormPageCategoryDomainFactory() {

    }

}
