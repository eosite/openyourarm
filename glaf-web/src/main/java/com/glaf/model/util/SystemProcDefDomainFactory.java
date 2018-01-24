package com.glaf.model.util;

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
public class SystemProcDefDomainFactory {

    public static final String TABLENAME = "SYSTEM_PROCDEF_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("funcId", "FUNC_ID_");
        columnMap.put("sysId", "SYS_ID_");
        columnMap.put("subSysId", "SUB_SYS_ID_");
        columnMap.put("currProcDefKey", "CURR_PROCDEF_KEY_");
        columnMap.put("currProcDefId", "CURR_PROCDEF_ID_");
        columnMap.put("currProcModelId", "CURR_PROCMODEL_ID_");
        columnMap.put("currProcDeployId", "CURR_PROCDEPLOY_ID_");
        columnMap.put("currProcDeployStatus", "CURR_PROCDEPLOY_STATUS_");
        columnMap.put("procDefKey", "PROCDEF_KEY_");
        columnMap.put("procDefId", "PROCDEF_ID_");
        columnMap.put("procModelId", "PROCMODEL_ID_");
        columnMap.put("procDeployId", "PROCDEPLOY_ID_");
        columnMap.put("procDeployStatus", "PROCDEPLOY_STATUS_");
        columnMap.put("eleType", "ELE_TYPE_");
        columnMap.put("eleResourceId", "ELE_RESOURCE_ID_");
        columnMap.put("eleId", "ELE_ID_");
        columnMap.put("eleName", "ELE_NAME_");
        columnMap.put("eleDesc", "ELE_DESC");
        columnMap.put("createBy", "CREATEBY_");
        columnMap.put("createTime", "CREATETIME_");
        columnMap.put("updateBy", "UPDATEBY_");
        columnMap.put("updateTime", "UPDATETIME_");
        columnMap.put("deleteFlag", "DELETE_FLAG_");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("funcId", "String");
        javaTypeMap.put("sysId", "String");
        javaTypeMap.put("subSysId", "String");
        javaTypeMap.put("currProcDefKey", "String");
        javaTypeMap.put("currProcDefId", "String");
        javaTypeMap.put("currProcModelId", "String");
        javaTypeMap.put("currProcDeployId", "String");
        javaTypeMap.put("currProcDeployStatus", "String");
        javaTypeMap.put("procDefKey", "String");
        javaTypeMap.put("procDefId", "String");
        javaTypeMap.put("procModelId", "String");
        javaTypeMap.put("procDeployId", "String");
        javaTypeMap.put("procDeployStatus", "String");
        javaTypeMap.put("eleType", "String");
        javaTypeMap.put("eleResourceId", "String");
        javaTypeMap.put("eleId", "String");
        javaTypeMap.put("eleName", "String");
        javaTypeMap.put("eleDesc", "String");
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
        tableDefinition.setName("SystemProcDef");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition funcId = new ColumnDefinition();
        funcId.setName("funcId");
        funcId.setColumnName("FUNC_ID_");
        funcId.setJavaType("String");
        funcId.setLength(50);
        tableDefinition.addColumn(funcId);

        ColumnDefinition sysId = new ColumnDefinition();
        sysId.setName("sysId");
        sysId.setColumnName("SYS_ID_");
        sysId.setJavaType("String");
        sysId.setLength(50);
        tableDefinition.addColumn(sysId);

        ColumnDefinition subSysId = new ColumnDefinition();
        subSysId.setName("subSysId");
        subSysId.setColumnName("SUB_SYS_ID_");
        subSysId.setJavaType("String");
        subSysId.setLength(50);
        tableDefinition.addColumn(subSysId);

        ColumnDefinition currProcDefKey = new ColumnDefinition();
        currProcDefKey.setName("currProcDefKey");
        currProcDefKey.setColumnName("CURR_PROCDEF_KEY_");
        currProcDefKey.setJavaType("String");
        currProcDefKey.setLength(255);
        tableDefinition.addColumn(currProcDefKey);

        ColumnDefinition currProcDefId = new ColumnDefinition();
        currProcDefId.setName("currProcDefId");
        currProcDefId.setColumnName("CURR_PROCDEF_ID_");
        currProcDefId.setJavaType("String");
        currProcDefId.setLength(64);
        tableDefinition.addColumn(currProcDefId);

        ColumnDefinition currProcModelId = new ColumnDefinition();
        currProcModelId.setName("currProcModelId");
        currProcModelId.setColumnName("CURR_PROCMODEL_ID_");
        currProcModelId.setJavaType("String");
        currProcModelId.setLength(64);
        tableDefinition.addColumn(currProcModelId);

        ColumnDefinition currProcDeployId = new ColumnDefinition();
        currProcDeployId.setName("currProcDeployId");
        currProcDeployId.setColumnName("CURR_PROCDEPLOY_ID_");
        currProcDeployId.setJavaType("String");
        currProcDeployId.setLength(64);
        tableDefinition.addColumn(currProcDeployId);

        ColumnDefinition currProcDeployStatus = new ColumnDefinition();
        currProcDeployStatus.setName("currProcDeployStatus");
        currProcDeployStatus.setColumnName("CURR_PROCDEPLOY_STATUS_");
        currProcDeployStatus.setJavaType("String");
        currProcDeployStatus.setLength(1);
        tableDefinition.addColumn(currProcDeployStatus);

        ColumnDefinition procDefKey = new ColumnDefinition();
        procDefKey.setName("procDefKey");
        procDefKey.setColumnName("PROCDEF_KEY_");
        procDefKey.setJavaType("String");
        procDefKey.setLength(255);
        tableDefinition.addColumn(procDefKey);

        ColumnDefinition procDefId = new ColumnDefinition();
        procDefId.setName("procDefId");
        procDefId.setColumnName("PROCDEF_ID_");
        procDefId.setJavaType("String");
        procDefId.setLength(64);
        tableDefinition.addColumn(procDefId);

        ColumnDefinition procModelId = new ColumnDefinition();
        procModelId.setName("procModelId");
        procModelId.setColumnName("PROCMODEL_ID_");
        procModelId.setJavaType("String");
        procModelId.setLength(64);
        tableDefinition.addColumn(procModelId);

        ColumnDefinition procDeployId = new ColumnDefinition();
        procDeployId.setName("procDeployId");
        procDeployId.setColumnName("PROCDEPLOY_ID_");
        procDeployId.setJavaType("String");
        procDeployId.setLength(64);
        tableDefinition.addColumn(procDeployId);

        ColumnDefinition procDeployStatus = new ColumnDefinition();
        procDeployStatus.setName("procDeployStatus");
        procDeployStatus.setColumnName("PROCDEPLOY_STATUS_");
        procDeployStatus.setJavaType("String");
        procDeployStatus.setLength(1);
        tableDefinition.addColumn(procDeployStatus);

        ColumnDefinition eleType = new ColumnDefinition();
        eleType.setName("eleType");
        eleType.setColumnName("ELE_TYPE_");
        eleType.setJavaType("String");
        eleType.setLength(30);
        tableDefinition.addColumn(eleType);

        ColumnDefinition eleResourceId = new ColumnDefinition();
        eleResourceId.setName("eleResourceId");
        eleResourceId.setColumnName("ELE_RESOURCE_ID_");
        eleResourceId.setJavaType("String");
        eleResourceId.setLength(50);
        tableDefinition.addColumn(eleResourceId);

        ColumnDefinition eleId = new ColumnDefinition();
        eleId.setName("eleId");
        eleId.setColumnName("ELE_ID_");
        eleId.setJavaType("String");
        eleId.setLength(50);
        tableDefinition.addColumn(eleId);

        ColumnDefinition eleName = new ColumnDefinition();
        eleName.setName("eleName");
        eleName.setColumnName("ELE_NAME_");
        eleName.setJavaType("String");
        eleName.setLength(100);
        tableDefinition.addColumn(eleName);

        ColumnDefinition eleDesc = new ColumnDefinition();
        eleDesc.setName("eleDesc");
        eleDesc.setColumnName("ELE_DESC");
        eleDesc.setJavaType("String");
        eleDesc.setLength(150);
        tableDefinition.addColumn(eleDesc);

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

    private SystemProcDefDomainFactory() {

    }

}
