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
public class SysThemeTmpLayoutAreaControlDomainFactory {

    public static final String TABLENAME = "SYS_THEME_TMP_LAYOUT_AREA_CONTROL_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("controlId", "CONTROL_ID_");
        columnMap.put("themeTmpControlId", "THEME_TMP_CONTROL_ID_");
        columnMap.put("areaId", "AREA_ID_");
        columnMap.put("layoutId", "LAYOUT_ID_");
        columnMap.put("controlName", "CONTROL_NAME_");
        columnMap.put("controlCode", "CONTROL_CODE_");
        columnMap.put("compType", "COMP_TYPE_");
        columnMap.put("elemCode", "ELEM_CODE_");
        columnMap.put("thumbnail", "THUMBNAIL_");
        columnMap.put("selectorExp", "SELECTOR_EXP_");
        columnMap.put("commonFlag", "COMMON_FLAG_");
        columnMap.put("containerFlag", "CONTAINER_FLAG_");
        columnMap.put("pcontrolId", "P_CONTROL_ID_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("controlId", "String");
        javaTypeMap.put("themeTmpControlId", "String");
        javaTypeMap.put("areaId", "String");
        javaTypeMap.put("layoutId", "String");
        javaTypeMap.put("controlName", "String");
        javaTypeMap.put("controlCode", "String");
        javaTypeMap.put("compType", "String");
        javaTypeMap.put("elemCode", "String");
        javaTypeMap.put("thumbnail", "Blob");
        javaTypeMap.put("selectorExp", "String");
        javaTypeMap.put("commonFlag", "Integer");
        javaTypeMap.put("containerFlag", "Integer");
        javaTypeMap.put("pcontrolId", "String");
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
        tableDefinition.setName("SysThemeTmpLayoutAreaControl");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("controlId");
        idColumn.setColumnName("CONTROL_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition themeTmpControlId = new ColumnDefinition();
        themeTmpControlId.setName("themeTmpControlId");
        themeTmpControlId.setColumnName("THEME_TMP_CONTROL_ID_");
        themeTmpControlId.setJavaType("String");
        themeTmpControlId.setLength(50);
        tableDefinition.addColumn(themeTmpControlId);

        ColumnDefinition areaId = new ColumnDefinition();
        areaId.setName("areaId");
        areaId.setColumnName("AREA_ID_");
        areaId.setJavaType("String");
        areaId.setLength(50);
        tableDefinition.addColumn(areaId);

        ColumnDefinition layoutId = new ColumnDefinition();
        layoutId.setName("layoutId");
        layoutId.setColumnName("LAYOUT_ID_");
        layoutId.setJavaType("String");
        layoutId.setLength(50);
        tableDefinition.addColumn(layoutId);

        ColumnDefinition controlName = new ColumnDefinition();
        controlName.setName("controlName");
        controlName.setColumnName("CONTROL_NAME_");
        controlName.setJavaType("String");
        controlName.setLength(50);
        tableDefinition.addColumn(controlName);

        ColumnDefinition controlCode = new ColumnDefinition();
        controlCode.setName("controlCode");
        controlCode.setColumnName("CONTROL_CODE_");
        controlCode.setJavaType("String");
        controlCode.setLength(30);
        tableDefinition.addColumn(controlCode);

        ColumnDefinition compType = new ColumnDefinition();
        compType.setName("compType");
        compType.setColumnName("COMP_TYPE_");
        compType.setJavaType("String");
        compType.setLength(30);
        tableDefinition.addColumn(compType);

        ColumnDefinition elemCode = new ColumnDefinition();
        elemCode.setName("elemCode");
        elemCode.setColumnName("ELEM_CODE_");
        elemCode.setJavaType("String");
        elemCode.setLength(30);
        tableDefinition.addColumn(elemCode);

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

        ColumnDefinition commonFlag = new ColumnDefinition();
        commonFlag.setName("commonFlag");
        commonFlag.setColumnName("COMMON_FLAG_");
        commonFlag.setJavaType("Integer");
        tableDefinition.addColumn(commonFlag);

        ColumnDefinition containerFlag = new ColumnDefinition();
        containerFlag.setName("containerFlag");
        containerFlag.setColumnName("CONTAINER_FLAG_");
        containerFlag.setJavaType("Integer");
        tableDefinition.addColumn(containerFlag);

        ColumnDefinition pcontrolId = new ColumnDefinition();
        pcontrolId.setName("pcontrolId");
        pcontrolId.setColumnName("P_CONTROL_ID_");
        pcontrolId.setJavaType("String");
        pcontrolId.setLength(50);
        tableDefinition.addColumn(pcontrolId);

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

    private SysThemeTmpLayoutAreaControlDomainFactory() {

    }

}
