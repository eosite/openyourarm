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
public class FormObjectIoDomainFactory {

    public static final String TABLENAME = "FORM_OBJECT_IO";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("name", "NAME_");
        columnMap.put("code", "CODE_");
        columnMap.put("codeMapping", "CODE_MAPPING_");
        columnMap.put("paramType", "PARAM_TYPE_");
        columnMap.put("defValue", "DEFVALUE_");
        columnMap.put("type", "TYPE_");
        columnMap.put("parent_id", "PARENT_ID_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("name", "String");
        javaTypeMap.put("code", "String");
        javaTypeMap.put("codeMapping", "String");
        javaTypeMap.put("paramType", "String");
        javaTypeMap.put("defValue", "String");
        javaTypeMap.put("type", "String");
        javaTypeMap.put("parent_id", "String");
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
        tableDefinition.setName("FormObjectIo");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition name = new ColumnDefinition();
        name.setName("name");
        name.setColumnName("NAME_");
        name.setJavaType("String");
        name.setLength(100);
        tableDefinition.addColumn(name);

        ColumnDefinition code = new ColumnDefinition();
        code.setName("code");
        code.setColumnName("CODE_");
        code.setJavaType("String");
        code.setLength(50);
        tableDefinition.addColumn(code);

        ColumnDefinition codeMapping = new ColumnDefinition();
        codeMapping.setName("codeMapping");
        codeMapping.setColumnName("CODE_MAPPING_");
        codeMapping.setJavaType("String");
        codeMapping.setLength(50);
        tableDefinition.addColumn(codeMapping);

        ColumnDefinition paramType = new ColumnDefinition();
        paramType.setName("paramType");
        paramType.setColumnName("PARAM_TYPE_");
        paramType.setJavaType("String");
        paramType.setLength(10);
        tableDefinition.addColumn(paramType);

        ColumnDefinition defValue = new ColumnDefinition();
        defValue.setName("defValue");
        defValue.setColumnName("DEFVALUE_");
        defValue.setJavaType("String");
        defValue.setLength(50);
        tableDefinition.addColumn(defValue);

        ColumnDefinition type = new ColumnDefinition();
        type.setName("type");
        type.setColumnName("TYPE_");
        type.setJavaType("String");
        type.setLength(10);
        tableDefinition.addColumn(type);

        ColumnDefinition parent_id = new ColumnDefinition();
        parent_id.setName("parent_id");
        parent_id.setColumnName("PARENT_ID_");
        parent_id.setJavaType("String");
        parent_id.setLength(50);
        tableDefinition.addColumn(parent_id);

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

    private FormObjectIoDomainFactory() {

    }

}
