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
public class WdatasetSqlliteDomainFactory {

    public static final String TABLENAME = "DEP_BASE_WDATASET_SQLLITE_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID_");
        columnMap.put("sqlliteRuleCode", "SQLLITE_RULE_CODE_");
        columnMap.put("sqlliteRuleDesc", "SQLLITE_RULE_DESC_");
        columnMap.put("sqlliteRuleName", "SQLLITE_RULE_NAME_");
        columnMap.put("dataSetsName", "DATASETS_NAME_");
        columnMap.put("delflag", "DELFLAG_");
        columnMap.put("ruleJson", "RULEJSON_");
        columnMap.put("createBy", "CREATEBY");
        columnMap.put("createDate", "CREATEDATE");
        columnMap.put("updateBy", "UPDATEBY");
        columnMap.put("updateDate", "UPDATEDATE");

	javaTypeMap.put("id", "String");
        javaTypeMap.put("sqlliteRuleCode", "String");
        javaTypeMap.put("sqlliteRuleDesc", "String");
        javaTypeMap.put("sqlliteRuleName", "String");
        javaTypeMap.put("dataSetsName", "String");
        javaTypeMap.put("delflag", "String");
        javaTypeMap.put("ruleJson", "String");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("createDate", "Date");
        javaTypeMap.put("updateBy", "String");
        javaTypeMap.put("updateDate", "Date");
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
        tableDefinition.setName("WdatasetSqllite");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(50);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition sqlliteRuleCode = new ColumnDefinition();
        sqlliteRuleCode.setName("sqlliteRuleCode");
        sqlliteRuleCode.setColumnName("SQLLITE_RULE_CODE_");
        sqlliteRuleCode.setJavaType("String");
        sqlliteRuleCode.setLength(50);
        tableDefinition.addColumn(sqlliteRuleCode);

        ColumnDefinition sqlliteRuleDesc = new ColumnDefinition();
        sqlliteRuleDesc.setName("sqlliteRuleDesc");
        sqlliteRuleDesc.setColumnName("SQLLITE_RULE_DESC_");
        sqlliteRuleDesc.setJavaType("String");
        sqlliteRuleDesc.setLength(150);
        tableDefinition.addColumn(sqlliteRuleDesc);

        ColumnDefinition sqlliteRuleName = new ColumnDefinition();
        sqlliteRuleName.setName("sqlliteRuleName");
        sqlliteRuleName.setColumnName("SQLLITE_RULE_NAME_");
        sqlliteRuleName.setJavaType("String");
        sqlliteRuleName.setLength(50);
        tableDefinition.addColumn(sqlliteRuleName);

        ColumnDefinition dataSetsName = new ColumnDefinition();
        dataSetsName.setName("dataSetsName");
        dataSetsName.setColumnName("DATASETS_NAME_");
        dataSetsName.setJavaType("String");
        dataSetsName.setLength(30);
        tableDefinition.addColumn(dataSetsName);

        ColumnDefinition delflag = new ColumnDefinition();
        delflag.setName("delflag");
        delflag.setColumnName("DELFLAG_");
        delflag.setJavaType("String");
        delflag.setLength(1);
        tableDefinition.addColumn(delflag);

        ColumnDefinition ruleJson = new ColumnDefinition();
        ruleJson.setName("ruleJson");
        ruleJson.setColumnName("RULEJSON_");
        ruleJson.setJavaType("String");
        ruleJson.setLength(21747);
        tableDefinition.addColumn(ruleJson);

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

    private WdatasetSqlliteDomainFactory() {

    }

}
