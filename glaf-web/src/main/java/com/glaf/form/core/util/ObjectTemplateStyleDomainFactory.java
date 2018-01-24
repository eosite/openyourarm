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
public class ObjectTemplateStyleDomainFactory {

    public static final String TABLENAME = "OBJ_TEMPLATE_STYLE";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("styleId", "STYLE_ID_");
        columnMap.put("ruleContent", "RULE_CONTENT_");
        columnMap.put("styleContent", "STYLE_CONTENT_");
        columnMap.put("delFlag", "DELFLAG_");
        columnMap.put("creator", "CREATOR_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("modifier", "MODIFIER_");
        columnMap.put("updateTime", "UPDATETIME_");

	javaTypeMap.put("styleId", "Long");
        javaTypeMap.put("ruleContent", "byte[]");
        javaTypeMap.put("styleContent", "byte[]");
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
        tableDefinition.setName("ObjectTemplateStyle");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("styleId");
        idColumn.setColumnName("STYLE_ID_");
        idColumn.setJavaType("Long");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition ruleContent = new ColumnDefinition();
        ruleContent.setName("ruleContent");
        ruleContent.setColumnName("RULE_CONTENT_");
        ruleContent.setJavaType("byte[]");
        tableDefinition.addColumn(ruleContent);

        ColumnDefinition styleContent = new ColumnDefinition();
        styleContent.setName("styleContent");
        styleContent.setColumnName("STYLE_CONTENT_");
        styleContent.setJavaType("byte[]");
        tableDefinition.addColumn(styleContent);

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

    private ObjectTemplateStyleDomainFactory() {

    }

}
