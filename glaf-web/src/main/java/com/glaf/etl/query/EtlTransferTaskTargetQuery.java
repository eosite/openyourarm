package com.glaf.etl.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EtlTransferTaskTargetQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> id_s;
	protected Collection<String> appActorIds;
  	protected String taskId_;
  	protected String taskId_Like;
  	protected List<String> taskId_s;
  	protected String targetId_;
  	protected String targetId_Like;
  	protected List<String> targetId_s;
  	protected Long taskConnId_;
  	protected Long taskConnId_GreaterThanOrEqual;
  	protected Long taskConnId_LessThanOrEqual;
  	protected List<Long> taskConnId_s;
  	protected String taskDatabaseName_;
  	protected String taskDatabaseName_Like;
  	protected List<String> taskDatabaseName_s;
  	protected String taskTableName_;
  	protected String taskTableName_Like;
  	protected List<String> taskTableName_s;
  	protected String tableNamePrefix_;
  	protected String tableNamePrefix_Like;
  	protected List<String> tableNamePrefix_s;
  	protected Integer preTuncateFlag_;
  	protected Integer preTuncateFlag_GreaterThanOrEqual;
  	protected Integer preTuncateFlag_LessThanOrEqual;
  	protected List<Integer> preTuncateFlag_s;
  	protected Integer treatment_methd_;
  	protected Integer treatment_methd_GreaterThanOrEqual;
  	protected Integer treatment_methd_LessThanOrEqual;
  	protected List<Integer> treatment_methd_s;
  	protected String createBy_;
  	protected String createBy_Like;
  	protected List<String> createBy_s;
        protected Date createTime_GreaterThanOrEqual;
  	protected Date createTime_LessThanOrEqual;
  	protected String updateBy_;
  	protected String updateBy_Like;
  	protected List<String> updateBy_s;
        protected Date updateTime_GreaterThanOrEqual;
  	protected Date updateTime_LessThanOrEqual;

    public EtlTransferTaskTargetQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTaskId_(){
        return taskId_;
    }

    public String getTaskId_Like(){
	    if (taskId_Like != null && taskId_Like.trim().length() > 0) {
		if (!taskId_Like.startsWith("%")) {
		    taskId_Like = "%" + taskId_Like;
		}
		if (!taskId_Like.endsWith("%")) {
		   taskId_Like = taskId_Like + "%";
		}
	    }
	return taskId_Like;
    }

    public List<String> getTaskId_s(){
	return taskId_s;
    }


    public String getTargetId_(){
        return targetId_;
    }

    public String getTargetId_Like(){
	    if (targetId_Like != null && targetId_Like.trim().length() > 0) {
		if (!targetId_Like.startsWith("%")) {
		    targetId_Like = "%" + targetId_Like;
		}
		if (!targetId_Like.endsWith("%")) {
		   targetId_Like = targetId_Like + "%";
		}
	    }
	return targetId_Like;
    }

    public List<String> getTargetId_s(){
	return targetId_s;
    }


    public Long getTaskConnId_(){
        return taskConnId_;
    }

    public Long getTaskConnId_GreaterThanOrEqual(){
        return taskConnId_GreaterThanOrEqual;
    }

    public Long getTaskConnId_LessThanOrEqual(){
	return taskConnId_LessThanOrEqual;
    }

    public List<Long> getTaskConnId_s(){
	return taskConnId_s;
    }

    public String getTaskDatabaseName_(){
        return taskDatabaseName_;
    }

    public String getTaskDatabaseName_Like(){
	    if (taskDatabaseName_Like != null && taskDatabaseName_Like.trim().length() > 0) {
		if (!taskDatabaseName_Like.startsWith("%")) {
		    taskDatabaseName_Like = "%" + taskDatabaseName_Like;
		}
		if (!taskDatabaseName_Like.endsWith("%")) {
		   taskDatabaseName_Like = taskDatabaseName_Like + "%";
		}
	    }
	return taskDatabaseName_Like;
    }

    public List<String> getTaskDatabaseName_s(){
	return taskDatabaseName_s;
    }


    public String getTaskTableName_(){
        return taskTableName_;
    }

    public String getTaskTableName_Like(){
	    if (taskTableName_Like != null && taskTableName_Like.trim().length() > 0) {
		if (!taskTableName_Like.startsWith("%")) {
		    taskTableName_Like = "%" + taskTableName_Like;
		}
		if (!taskTableName_Like.endsWith("%")) {
		   taskTableName_Like = taskTableName_Like + "%";
		}
	    }
	return taskTableName_Like;
    }

    public List<String> getTaskTableName_s(){
	return taskTableName_s;
    }


    public String getTableNamePrefix_(){
        return tableNamePrefix_;
    }

    public String getTableNamePrefix_Like(){
	    if (tableNamePrefix_Like != null && tableNamePrefix_Like.trim().length() > 0) {
		if (!tableNamePrefix_Like.startsWith("%")) {
		    tableNamePrefix_Like = "%" + tableNamePrefix_Like;
		}
		if (!tableNamePrefix_Like.endsWith("%")) {
		   tableNamePrefix_Like = tableNamePrefix_Like + "%";
		}
	    }
	return tableNamePrefix_Like;
    }

    public List<String> getTableNamePrefix_s(){
	return tableNamePrefix_s;
    }


    public Integer getPreTuncateFlag_(){
        return preTuncateFlag_;
    }

    public Integer getPreTuncateFlag_GreaterThanOrEqual(){
        return preTuncateFlag_GreaterThanOrEqual;
    }

    public Integer getPreTuncateFlag_LessThanOrEqual(){
	return preTuncateFlag_LessThanOrEqual;
    }

    public List<Integer> getPreTuncateFlag_s(){
	return preTuncateFlag_s;
    }

    public Integer getTreatment_methd_(){
        return treatment_methd_;
    }

    public Integer getTreatment_methd_GreaterThanOrEqual(){
        return treatment_methd_GreaterThanOrEqual;
    }

    public Integer getTreatment_methd_LessThanOrEqual(){
	return treatment_methd_LessThanOrEqual;
    }

    public List<Integer> getTreatment_methd_s(){
	return treatment_methd_s;
    }

    public String getCreateBy_(){
        return createBy_;
    }

    public String getCreateBy_Like(){
	    if (createBy_Like != null && createBy_Like.trim().length() > 0) {
		if (!createBy_Like.startsWith("%")) {
		    createBy_Like = "%" + createBy_Like;
		}
		if (!createBy_Like.endsWith("%")) {
		   createBy_Like = createBy_Like + "%";
		}
	    }
	return createBy_Like;
    }

    public List<String> getCreateBy_s(){
	return createBy_s;
    }


    public Date getCreateTime_GreaterThanOrEqual(){
        return createTime_GreaterThanOrEqual;
    }

    public Date getCreateTime_LessThanOrEqual(){
	return createTime_LessThanOrEqual;
    }


    public String getUpdateBy_(){
        return updateBy_;
    }

    public String getUpdateBy_Like(){
	    if (updateBy_Like != null && updateBy_Like.trim().length() > 0) {
		if (!updateBy_Like.startsWith("%")) {
		    updateBy_Like = "%" + updateBy_Like;
		}
		if (!updateBy_Like.endsWith("%")) {
		   updateBy_Like = updateBy_Like + "%";
		}
	    }
	return updateBy_Like;
    }

    public List<String> getUpdateBy_s(){
	return updateBy_s;
    }


    public Date getUpdateTime_GreaterThanOrEqual(){
        return updateTime_GreaterThanOrEqual;
    }

    public Date getUpdateTime_LessThanOrEqual(){
	return updateTime_LessThanOrEqual;
    }


 

    public void setTaskId_(String taskId_){
        this.taskId_ = taskId_;
    }

    public void setTaskId_Like( String taskId_Like){
	this.taskId_Like = taskId_Like;
    }

    public void setTaskId_s(List<String> taskId_s){
        this.taskId_s = taskId_s;
    }


    public void setTargetId_(String targetId_){
        this.targetId_ = targetId_;
    }

    public void setTargetId_Like( String targetId_Like){
	this.targetId_Like = targetId_Like;
    }

    public void setTargetId_s(List<String> targetId_s){
        this.targetId_s = targetId_s;
    }


    public void setTaskConnId_(Long taskConnId_){
        this.taskConnId_ = taskConnId_;
    }

    public void setTaskConnId_GreaterThanOrEqual(Long taskConnId_GreaterThanOrEqual){
        this.taskConnId_GreaterThanOrEqual = taskConnId_GreaterThanOrEqual;
    }

    public void setTaskConnId_LessThanOrEqual(Long taskConnId_LessThanOrEqual){
	this.taskConnId_LessThanOrEqual = taskConnId_LessThanOrEqual;
    }

    public void setTaskConnId_s(List<Long> taskConnId_s){
        this.taskConnId_s = taskConnId_s;
    }


    public void setTaskDatabaseName_(String taskDatabaseName_){
        this.taskDatabaseName_ = taskDatabaseName_;
    }

    public void setTaskDatabaseName_Like( String taskDatabaseName_Like){
	this.taskDatabaseName_Like = taskDatabaseName_Like;
    }

    public void setTaskDatabaseName_s(List<String> taskDatabaseName_s){
        this.taskDatabaseName_s = taskDatabaseName_s;
    }


    public void setTaskTableName_(String taskTableName_){
        this.taskTableName_ = taskTableName_;
    }

    public void setTaskTableName_Like( String taskTableName_Like){
	this.taskTableName_Like = taskTableName_Like;
    }

    public void setTaskTableName_s(List<String> taskTableName_s){
        this.taskTableName_s = taskTableName_s;
    }


    public void setTableNamePrefix_(String tableNamePrefix_){
        this.tableNamePrefix_ = tableNamePrefix_;
    }

    public void setTableNamePrefix_Like( String tableNamePrefix_Like){
	this.tableNamePrefix_Like = tableNamePrefix_Like;
    }

    public void setTableNamePrefix_s(List<String> tableNamePrefix_s){
        this.tableNamePrefix_s = tableNamePrefix_s;
    }


    public void setPreTuncateFlag_(Integer preTuncateFlag_){
        this.preTuncateFlag_ = preTuncateFlag_;
    }

    public void setPreTuncateFlag_GreaterThanOrEqual(Integer preTuncateFlag_GreaterThanOrEqual){
        this.preTuncateFlag_GreaterThanOrEqual = preTuncateFlag_GreaterThanOrEqual;
    }

    public void setPreTuncateFlag_LessThanOrEqual(Integer preTuncateFlag_LessThanOrEqual){
	this.preTuncateFlag_LessThanOrEqual = preTuncateFlag_LessThanOrEqual;
    }

    public void setPreTuncateFlag_s(List<Integer> preTuncateFlag_s){
        this.preTuncateFlag_s = preTuncateFlag_s;
    }


    public void setTreatment_methd_(Integer treatment_methd_){
        this.treatment_methd_ = treatment_methd_;
    }

    public void setTreatment_methd_GreaterThanOrEqual(Integer treatment_methd_GreaterThanOrEqual){
        this.treatment_methd_GreaterThanOrEqual = treatment_methd_GreaterThanOrEqual;
    }

    public void setTreatment_methd_LessThanOrEqual(Integer treatment_methd_LessThanOrEqual){
	this.treatment_methd_LessThanOrEqual = treatment_methd_LessThanOrEqual;
    }

    public void setTreatment_methd_s(List<Integer> treatment_methd_s){
        this.treatment_methd_s = treatment_methd_s;
    }


    public void setCreateBy_(String createBy_){
        this.createBy_ = createBy_;
    }

    public void setCreateBy_Like( String createBy_Like){
	this.createBy_Like = createBy_Like;
    }

    public void setCreateBy_s(List<String> createBy_s){
        this.createBy_s = createBy_s;
    }


    public void setCreateTime_GreaterThanOrEqual(Date createTime_GreaterThanOrEqual){
        this.createTime_GreaterThanOrEqual = createTime_GreaterThanOrEqual;
    }

    public void setCreateTime_LessThanOrEqual(Date createTime_LessThanOrEqual){
	this.createTime_LessThanOrEqual = createTime_LessThanOrEqual;
    }


    public void setUpdateBy_(String updateBy_){
        this.updateBy_ = updateBy_;
    }

    public void setUpdateBy_Like( String updateBy_Like){
	this.updateBy_Like = updateBy_Like;
    }

    public void setUpdateBy_s(List<String> updateBy_s){
        this.updateBy_s = updateBy_s;
    }


    public void setUpdateTime_GreaterThanOrEqual(Date updateTime_GreaterThanOrEqual){
        this.updateTime_GreaterThanOrEqual = updateTime_GreaterThanOrEqual;
    }

    public void setUpdateTime_LessThanOrEqual(Date updateTime_LessThanOrEqual){
	this.updateTime_LessThanOrEqual = updateTime_LessThanOrEqual;
    }




    public EtlTransferTaskTargetQuery taskId_(String taskId_){
	if (taskId_ == null) {
	    throw new RuntimeException("taskId_ is null");
        }         
	this.taskId_ = taskId_;
	return this;
    }

    public EtlTransferTaskTargetQuery taskId_Like( String taskId_Like){
        if (taskId_Like == null) {
            throw new RuntimeException("taskId_ is null");
        }
        this.taskId_Like = taskId_Like;
        return this;
    }

    public EtlTransferTaskTargetQuery taskId_s(List<String> taskId_s){
        if (taskId_s == null) {
            throw new RuntimeException("taskId_s is empty ");
        }
        this.taskId_s = taskId_s;
        return this;
    }


    public EtlTransferTaskTargetQuery targetId_(String targetId_){
	if (targetId_ == null) {
	    throw new RuntimeException("targetId_ is null");
        }         
	this.targetId_ = targetId_;
	return this;
    }

    public EtlTransferTaskTargetQuery targetId_Like( String targetId_Like){
        if (targetId_Like == null) {
            throw new RuntimeException("targetId_ is null");
        }
        this.targetId_Like = targetId_Like;
        return this;
    }

    public EtlTransferTaskTargetQuery targetId_s(List<String> targetId_s){
        if (targetId_s == null) {
            throw new RuntimeException("targetId_s is empty ");
        }
        this.targetId_s = targetId_s;
        return this;
    }


    public EtlTransferTaskTargetQuery taskConnId_(Long taskConnId_){
	if (taskConnId_ == null) {
            throw new RuntimeException("taskConnId_ is null");
        }         
	this.taskConnId_ = taskConnId_;
	return this;
    }

    public EtlTransferTaskTargetQuery taskConnId_GreaterThanOrEqual(Long taskConnId_GreaterThanOrEqual){
	if (taskConnId_GreaterThanOrEqual == null) {
	    throw new RuntimeException("taskConnId_ is null");
        }         
	this.taskConnId_GreaterThanOrEqual = taskConnId_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskTargetQuery taskConnId_LessThanOrEqual(Long taskConnId_LessThanOrEqual){
        if (taskConnId_LessThanOrEqual == null) {
            throw new RuntimeException("taskConnId_ is null");
        }
        this.taskConnId_LessThanOrEqual = taskConnId_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskTargetQuery taskConnId_s(List<Long> taskConnId_s){
        if (taskConnId_s == null) {
            throw new RuntimeException("taskConnId_s is empty ");
        }
        this.taskConnId_s = taskConnId_s;
        return this;
    }


    public EtlTransferTaskTargetQuery taskDatabaseName_(String taskDatabaseName_){
	if (taskDatabaseName_ == null) {
	    throw new RuntimeException("taskDatabaseName_ is null");
        }         
	this.taskDatabaseName_ = taskDatabaseName_;
	return this;
    }

    public EtlTransferTaskTargetQuery taskDatabaseName_Like( String taskDatabaseName_Like){
        if (taskDatabaseName_Like == null) {
            throw new RuntimeException("taskDatabaseName_ is null");
        }
        this.taskDatabaseName_Like = taskDatabaseName_Like;
        return this;
    }

    public EtlTransferTaskTargetQuery taskDatabaseName_s(List<String> taskDatabaseName_s){
        if (taskDatabaseName_s == null) {
            throw new RuntimeException("taskDatabaseName_s is empty ");
        }
        this.taskDatabaseName_s = taskDatabaseName_s;
        return this;
    }


    public EtlTransferTaskTargetQuery taskTableName_(String taskTableName_){
	if (taskTableName_ == null) {
	    throw new RuntimeException("taskTableName_ is null");
        }         
	this.taskTableName_ = taskTableName_;
	return this;
    }

    public EtlTransferTaskTargetQuery taskTableName_Like( String taskTableName_Like){
        if (taskTableName_Like == null) {
            throw new RuntimeException("taskTableName_ is null");
        }
        this.taskTableName_Like = taskTableName_Like;
        return this;
    }

    public EtlTransferTaskTargetQuery taskTableName_s(List<String> taskTableName_s){
        if (taskTableName_s == null) {
            throw new RuntimeException("taskTableName_s is empty ");
        }
        this.taskTableName_s = taskTableName_s;
        return this;
    }


    public EtlTransferTaskTargetQuery tableNamePrefix_(String tableNamePrefix_){
	if (tableNamePrefix_ == null) {
	    throw new RuntimeException("tableNamePrefix_ is null");
        }         
	this.tableNamePrefix_ = tableNamePrefix_;
	return this;
    }

    public EtlTransferTaskTargetQuery tableNamePrefix_Like( String tableNamePrefix_Like){
        if (tableNamePrefix_Like == null) {
            throw new RuntimeException("tableNamePrefix_ is null");
        }
        this.tableNamePrefix_Like = tableNamePrefix_Like;
        return this;
    }

    public EtlTransferTaskTargetQuery tableNamePrefix_s(List<String> tableNamePrefix_s){
        if (tableNamePrefix_s == null) {
            throw new RuntimeException("tableNamePrefix_s is empty ");
        }
        this.tableNamePrefix_s = tableNamePrefix_s;
        return this;
    }


    public EtlTransferTaskTargetQuery preTuncateFlag_(Integer preTuncateFlag_){
	if (preTuncateFlag_ == null) {
            throw new RuntimeException("preTuncateFlag_ is null");
        }         
	this.preTuncateFlag_ = preTuncateFlag_;
	return this;
    }

    public EtlTransferTaskTargetQuery preTuncateFlag_GreaterThanOrEqual(Integer preTuncateFlag_GreaterThanOrEqual){
	if (preTuncateFlag_GreaterThanOrEqual == null) {
	    throw new RuntimeException("preTuncateFlag_ is null");
        }         
	this.preTuncateFlag_GreaterThanOrEqual = preTuncateFlag_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskTargetQuery preTuncateFlag_LessThanOrEqual(Integer preTuncateFlag_LessThanOrEqual){
        if (preTuncateFlag_LessThanOrEqual == null) {
            throw new RuntimeException("preTuncateFlag_ is null");
        }
        this.preTuncateFlag_LessThanOrEqual = preTuncateFlag_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskTargetQuery preTuncateFlag_s(List<Integer> preTuncateFlag_s){
        if (preTuncateFlag_s == null) {
            throw new RuntimeException("preTuncateFlag_s is empty ");
        }
        this.preTuncateFlag_s = preTuncateFlag_s;
        return this;
    }


    public EtlTransferTaskTargetQuery treatment_methd_(Integer treatment_methd_){
	if (treatment_methd_ == null) {
            throw new RuntimeException("treatment_methd_ is null");
        }         
	this.treatment_methd_ = treatment_methd_;
	return this;
    }

    public EtlTransferTaskTargetQuery treatment_methd_GreaterThanOrEqual(Integer treatment_methd_GreaterThanOrEqual){
	if (treatment_methd_GreaterThanOrEqual == null) {
	    throw new RuntimeException("treatment_methd_ is null");
        }         
	this.treatment_methd_GreaterThanOrEqual = treatment_methd_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskTargetQuery treatment_methd_LessThanOrEqual(Integer treatment_methd_LessThanOrEqual){
        if (treatment_methd_LessThanOrEqual == null) {
            throw new RuntimeException("treatment_methd_ is null");
        }
        this.treatment_methd_LessThanOrEqual = treatment_methd_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskTargetQuery treatment_methd_s(List<Integer> treatment_methd_s){
        if (treatment_methd_s == null) {
            throw new RuntimeException("treatment_methd_s is empty ");
        }
        this.treatment_methd_s = treatment_methd_s;
        return this;
    }


    public EtlTransferTaskTargetQuery createBy_(String createBy_){
	if (createBy_ == null) {
	    throw new RuntimeException("createBy_ is null");
        }         
	this.createBy_ = createBy_;
	return this;
    }

    public EtlTransferTaskTargetQuery createBy_Like( String createBy_Like){
        if (createBy_Like == null) {
            throw new RuntimeException("createBy_ is null");
        }
        this.createBy_Like = createBy_Like;
        return this;
    }

    public EtlTransferTaskTargetQuery createBy_s(List<String> createBy_s){
        if (createBy_s == null) {
            throw new RuntimeException("createBy_s is empty ");
        }
        this.createBy_s = createBy_s;
        return this;
    }



    public EtlTransferTaskTargetQuery createTime_GreaterThanOrEqual(Date createTime_GreaterThanOrEqual){
	if (createTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime_ is null");
        }         
	this.createTime_GreaterThanOrEqual = createTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskTargetQuery createTime_LessThanOrEqual(Date createTime_LessThanOrEqual){
        if (createTime_LessThanOrEqual == null) {
            throw new RuntimeException("createTime_ is null");
        }
        this.createTime_LessThanOrEqual = createTime_LessThanOrEqual;
        return this;
    }



    public EtlTransferTaskTargetQuery updateBy_(String updateBy_){
	if (updateBy_ == null) {
	    throw new RuntimeException("updateBy_ is null");
        }         
	this.updateBy_ = updateBy_;
	return this;
    }

    public EtlTransferTaskTargetQuery updateBy_Like( String updateBy_Like){
        if (updateBy_Like == null) {
            throw new RuntimeException("updateBy_ is null");
        }
        this.updateBy_Like = updateBy_Like;
        return this;
    }

    public EtlTransferTaskTargetQuery updateBy_s(List<String> updateBy_s){
        if (updateBy_s == null) {
            throw new RuntimeException("updateBy_s is empty ");
        }
        this.updateBy_s = updateBy_s;
        return this;
    }



    public EtlTransferTaskTargetQuery updateTime_GreaterThanOrEqual(Date updateTime_GreaterThanOrEqual){
	if (updateTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime_ is null");
        }         
	this.updateTime_GreaterThanOrEqual = updateTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskTargetQuery updateTime_LessThanOrEqual(Date updateTime_LessThanOrEqual){
        if (updateTime_LessThanOrEqual == null) {
            throw new RuntimeException("updateTime_ is null");
        }
        this.updateTime_LessThanOrEqual = updateTime_LessThanOrEqual;
        return this;
    }




    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("taskId_".equals(sortColumn)) {
                orderBy = "E.TASK_ID_" + a_x;
            } 

            if ("targetId_".equals(sortColumn)) {
                orderBy = "E.TARGET_ID_" + a_x;
            } 

            if ("taskConnId_".equals(sortColumn)) {
                orderBy = "E.TASK_CONN_ID_" + a_x;
            } 

            if ("taskDatabaseName_".equals(sortColumn)) {
                orderBy = "E.TASK_DATABASENAME_" + a_x;
            } 

            if ("taskTableName_".equals(sortColumn)) {
                orderBy = "E.TASK_TABLENAME_" + a_x;
            } 

            if ("tableNamePrefix_".equals(sortColumn)) {
                orderBy = "E.TABLENAME_PREFIX_" + a_x;
            } 

            if ("preTuncateFlag_".equals(sortColumn)) {
                orderBy = "E.PRE_TRUNCATE_FLAG_" + a_x;
            } 

            if ("treatment_methd_".equals(sortColumn)) {
                orderBy = "E.TREATMENT_METHD_" + a_x;
            } 

            if ("createBy_".equals(sortColumn)) {
                orderBy = "E.CREATEBY_" + a_x;
            } 

            if ("createTime_".equals(sortColumn)) {
                orderBy = "E.CREATETIME_" + a_x;
            } 

            if ("updateBy_".equals(sortColumn)) {
                orderBy = "E.UPDATEBY_" + a_x;
            } 

            if ("updateTime_".equals(sortColumn)) {
                orderBy = "E.UPDATETIME_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id_", "ID_");
        addColumn("taskId_", "TASK_ID_");
        addColumn("targetId_", "TARGET_ID_");
        addColumn("taskConnId_", "TASK_CONN_ID_");
        addColumn("taskDatabaseName_", "TASK_DATABASENAME_");
        addColumn("taskTableName_", "TASK_TABLENAME_");
        addColumn("tableNamePrefix_", "TABLENAME_PREFIX_");
        addColumn("preTuncateFlag_", "PRE_TRUNCATE_FLAG_");
        addColumn("treatment_methd_", "TREATMENT_METHD_");
        addColumn("createBy_", "CREATEBY_");
        addColumn("createTime_", "CREATETIME_");
        addColumn("updateBy_", "UPDATEBY_");
        addColumn("updateTime_", "UPDATETIME_");
    }

}