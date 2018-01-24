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
public class SysThemeTmpControlAreaDomainFactory {

    public static final String TABLENAME = "SYS_THEME_TMP_CONTROL_AREA_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("ControlAreaId", "CONTROL_AREA_ID_");
        columnMap.put("controlId", "CONTROL_ID_");
        columnMap.put("areaName", "AREA_NAME_");
        columnMap.put("areaCode", "AREA_CODE_");
        columnMap.put("compType", "COMP_TYPE_");
        columnMap.put("elemCode", "ELEM_CODE_");
        columnMap.put("selectorExp", "SELECTOR_EXP_");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("ControlAreaId", "String");
        javaTypeMap.put("controlId", "String");
        javaTypeMap.put("areaName", "String");
        javaTypeMap.put("areaCode", "String");
        javaTypeMap.put("compType", "String");
        javaTypeMap.put("elemCode", "String");
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
        tableDefinition.setName("SysThemeTmpControlArea");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("ControlAreaId");
        idColumn.setColumnName("CONTROL_AREA_ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition controlId = new ColumnDefinition();
        controlId.setName("controlId");
        controlId.setColumnName("CONTROL_ID_");
        controlId.setJavaType("String");
        controlId.setLength(50);
        tableDefinition.addColumn(controlId);

        ColumnDefinition areaName = new ColumnDefinition();
        areaName.setName("areaName");
        areaName.setColumnName("AREA_NAME_");
        areaName.setJavaType("String");
        areaName.setLength(30);
        tableDefinition.addColumn(areaName);

        ColumnDefinition areaCode = new ColumnDefinition();
        areaCode.setName("areaCode");
        areaCode.setColumnName("AREA_CODE_");
        areaCode.setJavaType("String");
        areaCode.setLength(30);
        tableDefinition.addColumn(areaCode);

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

    private SysThemeTmpControlAreaDomainFactory() {

    }

}
