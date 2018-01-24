package com.glaf.etl.util;

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
public class EtlTransferTaskDomainFactory {

    public static final String TABLENAME = "ETL_TRANSFER_TASK_";

    public static final ConcurrentMap<String, String> columnMap = new ConcurrentHashMap<String, String>();

    public static final ConcurrentMap<String, String> javaTypeMap = new ConcurrentHashMap<String, String>();

    static{
        columnMap.put("id_", "ID_");
        columnMap.put("transId_", "TRANS_ID_");
        columnMap.put("taskName_", "TASK_NAME_");
        columnMap.put("taskDesc_", "TASK_DESC_");
        columnMap.put("commitInterval_", "COMMIT_INTERVAL_");
        columnMap.put("rollbackTransFlag_", "ROLLBACK_TRANS_FLAG_");
        columnMap.put("retryFlag_", "RETRY_FLAG_");
        columnMap.put("retryTimes_", "RETRY_TIMES_");
        columnMap.put("onPrePostErrorStop_", "ON_PREPOSTERROR_STOP_");
        columnMap.put("sendMailFlag_", "SENDMAIL_FLAG_");
        columnMap.put("emailAddress_", "EMAIL_ADDRESS_");
        columnMap.put("createBy_", "CREATEBY_");
        columnMap.put("createTime_", "CREATETIME_");
        columnMap.put("updateBy_", "UPDATEBY_");
        columnMap.put("updateTime_", "UPDATETIME_");
        columnMap.put("deleteFlag_", "DELETE_FLAG_");
        columnMap.put("lastStartTime_", "LAST_START_TIME_");
        columnMap.put("lastEndTime_", "LAST_END_TIME_");
        columnMap.put("succeed_", "SUCCEED_");
        columnMap.put("locked_", "LOCKED_");
        columnMap.put("errorStopAutoRun_", "ERROR_STOP_AUTORUN_");

	javaTypeMap.put("id_", "String");
        javaTypeMap.put("transId_", "String");
        javaTypeMap.put("taskName_", "String");
        javaTypeMap.put("taskDesc_", "String");
        javaTypeMap.put("commitInterval_", "Integer");
        javaTypeMap.put("rollbackTransFlag_", "Integer");
        javaTypeMap.put("retryFlag_", "Integer");
        javaTypeMap.put("retryTimes_", "Integer");
        javaTypeMap.put("onPrePostErrorStop_", "Integer");
        javaTypeMap.put("sendMailFlag_", "Integer");
        javaTypeMap.put("emailAddress_", "String");
        javaTypeMap.put("createBy_", "String");
        javaTypeMap.put("createTime_", "Date");
        javaTypeMap.put("updateBy_", "String");
        javaTypeMap.put("updateTime_", "Date");
        javaTypeMap.put("deleteFlag_", "Integer");
        javaTypeMap.put("lastStartTime_", "Date");
        javaTypeMap.put("lastEndTime_", "Date");
        javaTypeMap.put("succeed_", "Integer");
        javaTypeMap.put("locked_", "Integer");
        javaTypeMap.put("errorStopAutoRun_", "Integer");
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
        tableDefinition.setName("EtlTransferTask");

        ColumnDefinition idColumn = new ColumnDefinition();
        idColumn.setName("id_");
        idColumn.setColumnName("ID_");
        idColumn.setJavaType("String");
        idColumn.setLength(64);
        tableDefinition.setIdColumn(idColumn);


        ColumnDefinition transId_ = new ColumnDefinition();
        transId_.setName("transId_");
        transId_.setColumnName("TRANS_ID_");
        transId_.setJavaType("String");
        transId_.setLength(64);
        tableDefinition.addColumn(transId_);

        ColumnDefinition taskName_ = new ColumnDefinition();
        taskName_.setName("taskName_");
        taskName_.setColumnName("TASK_NAME_");
        taskName_.setJavaType("String");
        taskName_.setLength(50);
        tableDefinition.addColumn(taskName_);

        ColumnDefinition taskDesc_ = new ColumnDefinition();
        taskDesc_.setName("taskDesc_");
        taskDesc_.setColumnName("TASK_DESC_");
        taskDesc_.setJavaType("String");
        taskDesc_.setLength(200);
        tableDefinition.addColumn(taskDesc_);

        ColumnDefinition commitInterval_ = new ColumnDefinition();
        commitInterval_.setName("commitInterval_");
        commitInterval_.setColumnName("COMMIT_INTERVAL_");
        commitInterval_.setJavaType("Integer");
        tableDefinition.addColumn(commitInterval_);

        ColumnDefinition rollbackTransFlag_ = new ColumnDefinition();
        rollbackTransFlag_.setName("rollbackTransFlag_");
        rollbackTransFlag_.setColumnName("ROLLBACK_TRANS_FLAG_");
        rollbackTransFlag_.setJavaType("Integer");
        tableDefinition.addColumn(rollbackTransFlag_);

        ColumnDefinition retryFlag_ = new ColumnDefinition();
        retryFlag_.setName("retryFlag_");
        retryFlag_.setColumnName("RETRY_FLAG_");
        retryFlag_.setJavaType("Integer");
        tableDefinition.addColumn(retryFlag_);

        ColumnDefinition retryTimes_ = new ColumnDefinition();
        retryTimes_.setName("retryTimes_");
        retryTimes_.setColumnName("RETRY_TIMES_");
        retryTimes_.setJavaType("Integer");
        tableDefinition.addColumn(retryTimes_);

        ColumnDefinition onPrePostErrorStop_ = new ColumnDefinition();
        onPrePostErrorStop_.setName("onPrePostErrorStop_");
        onPrePostErrorStop_.setColumnName("ON_PREPOSTERROR_STOP_");
        onPrePostErrorStop_.setJavaType("Integer");
        tableDefinition.addColumn(onPrePostErrorStop_);

        ColumnDefinition sendMailFlag_ = new ColumnDefinition();
        sendMailFlag_.setName("sendMailFlag_");
        sendMailFlag_.setColumnName("SENDMAIL_FLAG_");
        sendMailFlag_.setJavaType("Integer");
        tableDefinition.addColumn(sendMailFlag_);

        ColumnDefinition emailAddress_ = new ColumnDefinition();
        emailAddress_.setName("emailAddress_");
        emailAddress_.setColumnName("EMAIL_ADDRESS_");
        emailAddress_.setJavaType("String");
        emailAddress_.setLength(150);
        tableDefinition.addColumn(emailAddress_);

        ColumnDefinition createBy_ = new ColumnDefinition();
        createBy_.setName("createBy_");
        createBy_.setColumnName("CREATEBY_");
        createBy_.setJavaType("String");
        createBy_.setLength(30);
        tableDefinition.addColumn(createBy_);

        ColumnDefinition createTime_ = new ColumnDefinition();
        createTime_.setName("createTime_");
        createTime_.setColumnName("CREATETIME_");
        createTime_.setJavaType("Date");
        tableDefinition.addColumn(createTime_);

        ColumnDefinition updateBy_ = new ColumnDefinition();
        updateBy_.setName("updateBy_");
        updateBy_.setColumnName("UPDATEBY_");
        updateBy_.setJavaType("String");
        updateBy_.setLength(30);
        tableDefinition.addColumn(updateBy_);

        ColumnDefinition updateTime_ = new ColumnDefinition();
        updateTime_.setName("updateTime_");
        updateTime_.setColumnName("UPDATETIME_");
        updateTime_.setJavaType("Date");
        tableDefinition.addColumn(updateTime_);

        ColumnDefinition deleteFlag_ = new ColumnDefinition();
        deleteFlag_.setName("deleteFlag_");
        deleteFlag_.setColumnName("DELETE_FLAG_");
        deleteFlag_.setJavaType("Integer");
        tableDefinition.addColumn(deleteFlag_);

        ColumnDefinition lastStartTime_ = new ColumnDefinition();
        lastStartTime_.setName("lastStartTime_");
        lastStartTime_.setColumnName("LAST_START_TIME_");
        lastStartTime_.setJavaType("Date");
        tableDefinition.addColumn(lastStartTime_);

        ColumnDefinition lastEndTime_ = new ColumnDefinition();
        lastEndTime_.setName("lastEndTime_");
        lastEndTime_.setColumnName("LAST_END_TIME_");
        lastEndTime_.setJavaType("Date");
        tableDefinition.addColumn(lastEndTime_);

        ColumnDefinition succeed_ = new ColumnDefinition();
        succeed_.setName("succeed_");
        succeed_.setColumnName("SUCCEED_");
        succeed_.setJavaType("Integer");
        tableDefinition.addColumn(succeed_);

        ColumnDefinition locked_ = new ColumnDefinition();
        locked_.setName("locked_");
        locked_.setColumnName("LOCKED_");
        locked_.setJavaType("Integer");
        tableDefinition.addColumn(locked_);

        ColumnDefinition errorStopAutoRun_ = new ColumnDefinition();
        errorStopAutoRun_.setName("errorStopAutoRun_");
        errorStopAutoRun_.setColumnName("ERROR_STOP_AUTORUN_");
        errorStopAutoRun_.setJavaType("Integer");
        tableDefinition.addColumn(errorStopAutoRun_);

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

    private EtlTransferTaskDomainFactory() {

    }

}
