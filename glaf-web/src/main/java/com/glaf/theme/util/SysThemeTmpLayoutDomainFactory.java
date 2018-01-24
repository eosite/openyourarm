package com.glaf.theme.util;

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
public class SysThemeTmpLayoutDomainFactory {

    public static final String TABLENAME = "SYS_THEME_TMP_LAYOUT_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("layoutId", "LAYOUT_ID_");
        columnMap.put("themeTmpId", "THEME_TMP_ID_");
        columnMap.put("layoutName", "LAYOUT_NAME_");
        columnMap.put("layoutPlan", "LAYOUT_PLAN_");
        columnMap.put("layoutCode", "LAYOUT_CODE_");
        columnMap.put("thumbnail", "THUMBNAIL_");
        columnMap.put("selectorExp", "SELECTOR_EXP_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("layoutId", "String");
        javaTypeMap.put("themeTmpId", "String");
        javaTypeMap.put("layoutName", "String");
        javaTypeMap.put("layoutPlan", "String");
        javaTypeMap.put("layoutCode", "String");
        javaTypeMap.put("thumbnail", "Blob");
        javaTypeMap.put("selectorExp", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createTime", "Date");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateTime", "Date");
        javaTypeMap.put("deleteFlag", "Integer");
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
        tableDefinition.setName("SysThemeTmpLayout");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("layoutId");
        idColumn.setColumnName("LAYOUT_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition themeTmpId = new ColumnDefinition();
        themeTmpId.setName("themeTmpId");
        themeTmpId.setColumnName("THEME_TMP_ID_");
        themeTmpId.setJavaType("String");
        themeTmpId.setLength(50);
        tableDefinition.addColumn(themeTmpId);

        ColumnDefinition layoutName = new ColumnDefinition();
        layoutName.setName("layoutName");
        layoutName.setColumnName("LAYOUT_NAME_");
        layoutName.setJavaType("String");
        layoutName.setLength(50);
        tableDefinition.addColumn(layoutName);

        ColumnDefinition layoutPlan = new ColumnDefinition();
        layoutPlan.setName("layoutPlan");
        layoutPlan.setColumnName("LAYOUT_PLAN_");
        layoutPlan.setJavaType("String");
        layoutPlan.setLength(50);
        tableDefinition.addColumn(layoutPlan);

        ColumnDefinition layoutCode = new ColumnDefinition();
        layoutCode.setName("layoutCode");
        layoutCode.setColumnName("LAYOUT_CODE_");
        layoutCode.setJavaType("String");
        layoutCode.setLength(30);
        tableDefinition.addColumn(layoutCode);

        ColumnDefinition thumbnail = new ColumnDefinition();
        thumbnail.setName("thumbnail");
        thumbnail.setColumnName("THUMBNAIL_");
        thumbnail.setJavaType("Blob");
        tableDefinition.addColumn(thumbnail);

        ColumnDefinition selectorExp = new ColumnDefinition();
        selectorExp.setName("selectorExp");
        selectorExp.setColumnName("SELECTOR_EXP_");
        selectorExp.setJavaType("String");
        selectorExp.setLength(300);
        tableDefinition.addColumn(selectorExp);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY_");
        createBy.setJavaType("String");
        createBy.setLength(30);
        tableDefinition.addColumn(createBy);

        ColumnDefinition createTime = new ColumnDefinition();
        createTime.setName("createTime");
        createTime.setColumnName("CREATETIME_");
        createTime.setJavaType("Date");
        tableDefinition.addColumn(createTime);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY_");
        updateBy.setJavaType("String");
        updateBy.setLength(30);
        tableDefinition.addColumn(updateBy);

        ColumnDefinition updateTime = new ColumnDefinition();
        updateTime.setName("updateTime");
        updateTime.setColumnName("UPDATETIME_");
        updateTime.setJavaType("Date");
        tableDefinition.addColumn(updateTime);

        ColumnDefinition deleteFlag = new ColumnDefinition();
        deleteFlag.setName("deleteFlag");
        deleteFlag.setColumnName("DELETE_FLAG_");
        deleteFlag.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag);

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

    private SysThemeTmpLayoutDomainFactory() {

    }

}
