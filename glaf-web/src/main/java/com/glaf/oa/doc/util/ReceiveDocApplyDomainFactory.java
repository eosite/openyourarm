package com.glaf.oa.doc.util;

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
public class ReceiveDocApplyDomainFactory {

    public static final String TABLENAME = "RECEIVEDOCAPPLY";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id", "ID");
        columnMap.put("subject", "SUBJECT");
        columnMap.put("securityLevel", "SECURITYLEVEL");
        columnMap.put("urgencyLevel", "URGENCYLEVEL");
        columnMap.put("receiveDocTime", "RECEIVEDOCTIME");
        columnMap.put("docType", "DOCTYPE");
        columnMap.put("fromCompany", "FROMCOMPANY");
        columnMap.put("serialNumber", "SERIALNUMBER");
        columnMap.put("fromDocNo", "FROMDOCNO");
        columnMap.put("receiveDocNo", "RECEIVEDOCNO");
        columnMap.put("distributeCompany", "DISTRIBUTECOMPANY");
        columnMap.put("nibanOption", "NIBANOPTION");
        columnMap.put("leadOption", "LEADOPTION");
        columnMap.put("chengbanOption", "CHENGBANOPTION");
        columnMap.put("remark", "REMARK");
        columnMap.put("status", "STATUS");
        columnMap.put("createDate", "CREATEDATE");
        columnMap.put("updateDate", "UPDATEDATE");
        columnMap.put("createBy", "CREATEBY");
        columnMap.put("updateBy", "UPDATEBY");

	javaTypeMap.put("id", "Integer");
        javaTypeMap.put("subject", "String");
        javaTypeMap.put("securityLevel", "Integer");
        javaTypeMap.put("urgencyLevel", "Integer");
        javaTypeMap.put("receiveDocTime", "Date");
        javaTypeMap.put("docType", "String");
        javaTypeMap.put("fromCompany", "String");
        javaTypeMap.put("serialNumber", "String");
        javaTypeMap.put("fromDocNo", "String");
        javaTypeMap.put("receiveDocNo", "String");
        javaTypeMap.put("distributeCompany", "String");
        javaTypeMap.put("nibanOption", "String");
        javaTypeMap.put("leadOption", "String");
        javaTypeMap.put("chengbanOption", "String");
        javaTypeMap.put("remark", "String");
        javaTypeMap.put("status", "Integer");
        javaTypeMap.put("createDate", "Date");
        javaTypeMap.put("updateDate", "Date");
        javaTypeMap.put("createBy", "String");
        javaTypeMap.put("updateBy", "String");
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
        tableDefinition.setName("ReceiveDocApply");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id");
        idColumn.setColumnName("ID");
        idColumn.setJavaType("Integer");
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition subject = new ColumnDefinition();
        subject.setName("subject");
        subject.setColumnName("SUBJECT");
        subject.setJavaType("String");
        subject.setLength(50);
        tableDefinition.addColumn(subject);

        ColumnDefinition securityLevel = new ColumnDefinition();
        securityLevel.setName("securityLevel");
        securityLevel.setColumnName("SECURITYLEVEL");
        securityLevel.setJavaType("Integer");
        tableDefinition.addColumn(securityLevel);

        ColumnDefinition urgencyLevel = new ColumnDefinition();
        urgencyLevel.setName("urgencyLevel");
        urgencyLevel.setColumnName("URGENCYLEVEL");
        urgencyLevel.setJavaType("Integer");
        tableDefinition.addColumn(urgencyLevel);

        ColumnDefinition receiveDocTime = new ColumnDefinition();
        receiveDocTime.setName("receiveDocTime");
        receiveDocTime.setColumnName("RECEIVEDOCTIME");
        receiveDocTime.setJavaType("Date");
        tableDefinition.addColumn(receiveDocTime);

        ColumnDefinition docType = new ColumnDefinition();
        docType.setName("docType");
        docType.setColumnName("DOCTYPE");
        docType.setJavaType("String");
        docType.setLength(20);
        tableDefinition.addColumn(docType);

        ColumnDefinition fromCompany = new ColumnDefinition();
        fromCompany.setName("fromCompany");
        fromCompany.setColumnName("FROMCOMPANY");
        fromCompany.setJavaType("String");
        fromCompany.setLength(50);
        tableDefinition.addColumn(fromCompany);

        ColumnDefinition serialNumber = new ColumnDefinition();
        serialNumber.setName("serialNumber");
        serialNumber.setColumnName("SERIALNUMBER");
        serialNumber.setJavaType("String");
        serialNumber.setLength(20);
        tableDefinition.addColumn(serialNumber);

        ColumnDefinition fromDocNo = new ColumnDefinition();
        fromDocNo.setName("fromDocNo");
        fromDocNo.setColumnName("FROMDOCNO");
        fromDocNo.setJavaType("String");
        fromDocNo.setLength(20);
        tableDefinition.addColumn(fromDocNo);

        ColumnDefinition receiveDocNo = new ColumnDefinition();
        receiveDocNo.setName("receiveDocNo");
        receiveDocNo.setColumnName("RECEIVEDOCNO");
        receiveDocNo.setJavaType("String");
        receiveDocNo.setLength(20);
        tableDefinition.addColumn(receiveDocNo);

        ColumnDefinition distributeCompany = new ColumnDefinition();
        distributeCompany.setName("distributeCompany");
        distributeCompany.setColumnName("DISTRIBUTECOMPANY");
        distributeCompany.setJavaType("String");
        distributeCompany.setLength(50);
        tableDefinition.addColumn(distributeCompany);

        ColumnDefinition nibanOption = new ColumnDefinition();
        nibanOption.setName("nibanOption");
        nibanOption.setColumnName("NIBANOPTION");
        nibanOption.setJavaType("String");
        nibanOption.setLength(2000);
        tableDefinition.addColumn(nibanOption);

        ColumnDefinition leadOption = new ColumnDefinition();
        leadOption.setName("leadOption");
        leadOption.setColumnName("LEADOPTION");
        leadOption.setJavaType("String");
        leadOption.setLength(2000);
        tableDefinition.addColumn(leadOption);

        ColumnDefinition chengbanOption = new ColumnDefinition();
        chengbanOption.setName("chengbanOption");
        chengbanOption.setColumnName("CHENGBANOPTION");
        chengbanOption.setJavaType("String");
        chengbanOption.setLength(2000);
        tableDefinition.addColumn(chengbanOption);

        ColumnDefinition remark = new ColumnDefinition();
        remark.setName("remark");
        remark.setColumnName("REMARK");
        remark.setJavaType("String");
        remark.setLength(2000);
        tableDefinition.addColumn(remark);

        ColumnDefinition status = new ColumnDefinition();
        status.setName("status");
        status.setColumnName("STATUS");
        status.setJavaType("Integer");
        tableDefinition.addColumn(status);

        ColumnDefinition createDate = new ColumnDefinition();
        createDate.setName("createDate");
        createDate.setColumnName("CREATEDATE");
        createDate.setJavaType("Date");
        tableDefinition.addColumn(createDate);

        ColumnDefinition updateDate = new ColumnDefinition();
        updateDate.setName("updateDate");
        updateDate.setColumnName("UPDATEDATE");
        updateDate.setJavaType("Date");
        tableDefinition.addColumn(updateDate);

        ColumnDefinition createBy = new ColumnDefinition();
        createBy.setName("createBy");
        createBy.setColumnName("CREATEBY");
        createBy.setJavaType("String");
        createBy.setLength(50);
        tableDefinition.addColumn(createBy);

        ColumnDefinition updateBy = new ColumnDefinition();
        updateBy.setName("updateBy");
        updateBy.setColumnName("UPDATEBY");
        updateBy.setJavaType("String");
        updateBy.setLength(50);
        tableDefinition.addColumn(updateBy);

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

    private ReceiveDocApplyDomainFactory() {

    }

}
