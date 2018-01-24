package com.glaf.workflow.core.util;

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
public class ActReTaskHisDomainFactory {

    public static final String TABLENAME = "ACT_RE_TASK_HIS";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("actorId", "ACTORID_");
        columnMap.put("taskId", "TASKID_");
        columnMap.put("taskName", "TASKNAME_");
        columnMap.put("taskKey", "TASKKEY_");
        columnMap.put("processId", "PROCESSID_");
        columnMap.put("fromKey", "FROMKEY_");
        columnMap.put("fromId", "FROMID_");
        columnMap.put("fromName", "FROMNAME_");
        columnMap.put("fromActorId", "FROMACTORID_");
        columnMap.put("variables", "VARIABLES_");
        columnMap.put("subType", "SUBTYPE_");
        columnMap.put("createDate", "CREATEDATE_");

	javaTypeMap.put("id", "Integer");
        javaTypeMap.put("actorId", "String");
        javaTypeMap.put("taskId", "String");
        javaTypeMap.put("taskName", "String");
        javaTypeMap.put("taskKey", "String");
        javaTypeMap.put("processId", "String");
        javaTypeMap.put("fromKey", "String");
        javaTypeMap.put("fromId", "String");
        javaTypeMap.put("fromName", "String");
        javaTypeMap.put("fromActorId", "String");
        javaTypeMap.put("variables", "String");
        javaTypeMap.put("subType", "String");
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
        tableDefinition.setName("ActReTaskHis");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("Integer");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition actorId = new ColumnDefinition();
        actorId.setName("actorId");
        actorId.setColumnName("ACTORID_");
        actorId.setJavaType("String");
        actorId.setLength(50);
        tableDefinition.addColumn(actorId);

        ColumnDefinition taskId = new ColumnDefinition();
        taskId.setName("taskId");
        taskId.setColumnName("TASKID_");
        taskId.setJavaType("String");
        taskId.setLength(10);
        tableDefinition.addColumn(taskId);

        ColumnDefinition taskName = new ColumnDefinition();
        taskName.setName("taskName");
        taskName.setColumnName("TASKNAME_");
        taskName.setJavaType("String");
        taskName.setLength(50);
        tableDefinition.addColumn(taskName);

        ColumnDefinition taskKey = new ColumnDefinition();
        taskKey.setName("taskKey");
        taskKey.setColumnName("TASKKEY_");
        taskKey.setJavaType("String");
        taskKey.setLength(50);
        tableDefinition.addColumn(taskKey);

        ColumnDefinition processId = new ColumnDefinition();
        processId.setName("processId");
        processId.setColumnName("PROCESSID_");
        processId.setJavaType("String");
        processId.setLength(10);
        tableDefinition.addColumn(processId);

        ColumnDefinition fromKey = new ColumnDefinition();
        fromKey.setName("fromKey");
        fromKey.setColumnName("FROMKEY_");
        fromKey.setJavaType("String");
        fromKey.setLength(50);
        tableDefinition.addColumn(fromKey);

        ColumnDefinition fromId = new ColumnDefinition();
        fromId.setName("fromId");
        fromId.setColumnName("FROMID_");
        fromId.setJavaType("String");
        fromId.setLength(10);
        tableDefinition.addColumn(fromId);

        ColumnDefinition fromName = new ColumnDefinition();
        fromName.setName("fromName");
        fromName.setColumnName("FROMNAME_");
        fromName.setJavaType("String");
        fromName.setLength(50);
        tableDefinition.addColumn(fromName);

        ColumnDefinition fromActorId = new ColumnDefinition();
        fromActorId.setName("fromActorId");
        fromActorId.setColumnName("FROMACTORID_");
        fromActorId.setJavaType("String");
        fromActorId.setLength(50);
        tableDefinition.addColumn(fromActorId);

        ColumnDefinition variables = new ColumnDefinition();
        variables.setName("variables");
        variables.setColumnName("VARIABLES_");
        variables.setJavaType("String");
        variables.setLength(255);
        tableDefinition.addColumn(variables);

        ColumnDefinition subType = new ColumnDefinition();
        subType.setName("subType");
        subType.setColumnName("SUBTYPE_");
        subType.setJavaType("String");
        subType.setLength(255);
        tableDefinition.addColumn(subType);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE_");
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

    private ActReTaskHisDomainFactory() {

    }

}
