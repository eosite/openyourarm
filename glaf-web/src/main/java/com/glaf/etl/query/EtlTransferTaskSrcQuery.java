package com.glaf.etl.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EtlTransferTaskSrcQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> id_s;
	protected Collection<String> appActorIds;
  	protected String taskId_;
  	protected String taskId_Like;
  	protected List<String> taskId_s;
  	protected String srcId_;
  	protected String srcId_Like;
  	protected List<String> srcId_s;
  	protected Integer distinctFlag_;
  	protected Integer distinctFlag_GreaterThanOrEqual;
  	protected Integer distinctFlag_LessThanOrEqual;
  	protected List<Integer> distinctFlag_s;
  	protected String ownerName_;
  	protected String ownerName_Like;
  	protected List<String> ownerName_s;
  	protected Long taskConnId_;
  	protected Long taskConnId_GreaterThanOrEqual;
  	protected Long taskConnId_LessThanOrEqual;
  	protected List<Long> taskConnId_s;
  	protected String taskDatabaseName_;
  	protected String taskDatabaseName_Like;
  	protected List<String> taskDatabaseName_s;
  	protected Integer orderMapping_;
  	protected Integer orderMapping_GreaterThanOrEqual;
  	protected Integer orderMapping_LessThanOrEqual;
  	protected List<Integer> orderMapping_s;
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

    public EtlTransferTaskSrcQuery() {

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


    public String getSrcId_(){
        return srcId_;
    }

    public String getSrcId_Like(){
	    if (srcId_Like != null && srcId_Like.trim().length() > 0) {
		if (!srcId_Like.startsWith("%")) {
		    srcId_Like = "%" + srcId_Like;
		}
		if (!srcId_Like.endsWith("%")) {
		   srcId_Like = srcId_Like + "%";
		}
	    }
	return srcId_Like;
    }

    public List<String> getSrcId_s(){
	return srcId_s;
    }


    public Integer getDistinctFlag_(){
        return distinctFlag_;
    }

    public Integer getDistinctFlag_GreaterThanOrEqual(){
        return distinctFlag_GreaterThanOrEqual;
    }

    public Integer getDistinctFlag_LessThanOrEqual(){
	return distinctFlag_LessThanOrEqual;
    }

    public List<Integer> getDistinctFlag_s(){
	return distinctFlag_s;
    }

    public String getOwnerName_(){
        return ownerName_;
    }

    public String getOwnerName_Like(){
	    if (ownerName_Like != null && ownerName_Like.trim().length() > 0) {
		if (!ownerName_Like.startsWith("%")) {
		    ownerName_Like = "%" + ownerName_Like;
		}
		if (!ownerName_Like.endsWith("%")) {
		   ownerName_Like = ownerName_Like + "%";
		}
	    }
	return ownerName_Like;
    }

    public List<String> getOwnerName_s(){
	return ownerName_s;
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


    public Integer getOrderMapping_(){
        return orderMapping_;
    }

    public Integer getOrderMapping_GreaterThanOrEqual(){
        return orderMapping_GreaterThanOrEqual;
    }

    public Integer getOrderMapping_LessThanOrEqual(){
	return orderMapping_LessThanOrEqual;
    }

    public List<Integer> getOrderMapping_s(){
	return orderMapping_s;
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


    public void setSrcId_(String srcId_){
        this.srcId_ = srcId_;
    }

    public void setSrcId_Like( String srcId_Like){
	this.srcId_Like = srcId_Like;
    }

    public void setSrcId_s(List<String> srcId_s){
        this.srcId_s = srcId_s;
    }


    public void setDistinctFlag_(Integer distinctFlag_){
        this.distinctFlag_ = distinctFlag_;
    }

    public void setDistinctFlag_GreaterThanOrEqual(Integer distinctFlag_GreaterThanOrEqual){
        this.distinctFlag_GreaterThanOrEqual = distinctFlag_GreaterThanOrEqual;
    }

    public void setDistinctFlag_LessThanOrEqual(Integer distinctFlag_LessThanOrEqual){
	this.distinctFlag_LessThanOrEqual = distinctFlag_LessThanOrEqual;
    }

    public void setDistinctFlag_s(List<Integer> distinctFlag_s){
        this.distinctFlag_s = distinctFlag_s;
    }


    public void setOwnerName_(String ownerName_){
        this.ownerName_ = ownerName_;
    }

    public void setOwnerName_Like( String ownerName_Like){
	this.ownerName_Like = ownerName_Like;
    }

    public void setOwnerName_s(List<String> ownerName_s){
        this.ownerName_s = ownerName_s;
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


    public void setOrderMapping_(Integer orderMapping_){
        this.orderMapping_ = orderMapping_;
    }

    public void setOrderMapping_GreaterThanOrEqual(Integer orderMapping_GreaterThanOrEqual){
        this.orderMapping_GreaterThanOrEqual = orderMapping_GreaterThanOrEqual;
    }

    public void setOrderMapping_LessThanOrEqual(Integer orderMapping_LessThanOrEqual){
	this.orderMapping_LessThanOrEqual = orderMapping_LessThanOrEqual;
    }

    public void setOrderMapping_s(List<Integer> orderMapping_s){
        this.orderMapping_s = orderMapping_s;
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




    public EtlTransferTaskSrcQuery taskId_(String taskId_){
	if (taskId_ == null) {
	    throw new RuntimeException("taskId_ is null");
        }         
	this.taskId_ = taskId_;
	return this;
    }

    public EtlTransferTaskSrcQuery taskId_Like( String taskId_Like){
        if (taskId_Like == null) {
            throw new RuntimeException("taskId_ is null");
        }
        this.taskId_Like = taskId_Like;
        return this;
    }

    public EtlTransferTaskSrcQuery taskId_s(List<String> taskId_s){
        if (taskId_s == null) {
            throw new RuntimeException("taskId_s is empty ");
        }
        this.taskId_s = taskId_s;
        return this;
    }


    public EtlTransferTaskSrcQuery srcId_(String srcId_){
	if (srcId_ == null) {
	    throw new RuntimeException("srcId_ is null");
        }         
	this.srcId_ = srcId_;
	return this;
    }

    public EtlTransferTaskSrcQuery srcId_Like( String srcId_Like){
        if (srcId_Like == null) {
            throw new RuntimeException("srcId_ is null");
        }
        this.srcId_Like = srcId_Like;
        return this;
    }

    public EtlTransferTaskSrcQuery srcId_s(List<String> srcId_s){
        if (srcId_s == null) {
            throw new RuntimeException("srcId_s is empty ");
        }
        this.srcId_s = srcId_s;
        return this;
    }


    public EtlTransferTaskSrcQuery distinctFlag_(Integer distinctFlag_){
	if (distinctFlag_ == null) {
            throw new RuntimeException("distinctFlag_ is null");
        }         
	this.distinctFlag_ = distinctFlag_;
	return this;
    }

    public EtlTransferTaskSrcQuery distinctFlag_GreaterThanOrEqual(Integer distinctFlag_GreaterThanOrEqual){
	if (distinctFlag_GreaterThanOrEqual == null) {
	    throw new RuntimeException("distinctFlag_ is null");
        }         
	this.distinctFlag_GreaterThanOrEqual = distinctFlag_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskSrcQuery distinctFlag_LessThanOrEqual(Integer distinctFlag_LessThanOrEqual){
        if (distinctFlag_LessThanOrEqual == null) {
            throw new RuntimeException("distinctFlag_ is null");
        }
        this.distinctFlag_LessThanOrEqual = distinctFlag_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskSrcQuery distinctFlag_s(List<Integer> distinctFlag_s){
        if (distinctFlag_s == null) {
            throw new RuntimeException("distinctFlag_s is empty ");
        }
        this.distinctFlag_s = distinctFlag_s;
        return this;
    }


    public EtlTransferTaskSrcQuery ownerName_(String ownerName_){
	if (ownerName_ == null) {
	    throw new RuntimeException("ownerName_ is null");
        }         
	this.ownerName_ = ownerName_;
	return this;
    }

    public EtlTransferTaskSrcQuery ownerName_Like( String ownerName_Like){
        if (ownerName_Like == null) {
            throw new RuntimeException("ownerName_ is null");
        }
        this.ownerName_Like = ownerName_Like;
        return this;
    }

    public EtlTransferTaskSrcQuery ownerName_s(List<String> ownerName_s){
        if (ownerName_s == null) {
            throw new RuntimeException("ownerName_s is empty ");
        }
        this.ownerName_s = ownerName_s;
        return this;
    }


    public EtlTransferTaskSrcQuery taskConnId_(Long taskConnId_){
	if (taskConnId_ == null) {
            throw new RuntimeException("taskConnId_ is null");
        }         
	this.taskConnId_ = taskConnId_;
	return this;
    }

    public EtlTransferTaskSrcQuery taskConnId_GreaterThanOrEqual(Long taskConnId_GreaterThanOrEqual){
	if (taskConnId_GreaterThanOrEqual == null) {
	    throw new RuntimeException("taskConnId_ is null");
        }         
	this.taskConnId_GreaterThanOrEqual = taskConnId_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskSrcQuery taskConnId_LessThanOrEqual(Long taskConnId_LessThanOrEqual){
        if (taskConnId_LessThanOrEqual == null) {
            throw new RuntimeException("taskConnId_ is null");
        }
        this.taskConnId_LessThanOrEqual = taskConnId_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskSrcQuery taskConnId_s(List<Long> taskConnId_s){
        if (taskConnId_s == null) {
            throw new RuntimeException("taskConnId_s is empty ");
        }
        this.taskConnId_s = taskConnId_s;
        return this;
    }


    public EtlTransferTaskSrcQuery taskDatabaseName_(String taskDatabaseName_){
	if (taskDatabaseName_ == null) {
	    throw new RuntimeException("taskDatabaseName_ is null");
        }         
	this.taskDatabaseName_ = taskDatabaseName_;
	return this;
    }

    public EtlTransferTaskSrcQuery taskDatabaseName_Like( String taskDatabaseName_Like){
        if (taskDatabaseName_Like == null) {
            throw new RuntimeException("taskDatabaseName_ is null");
        }
        this.taskDatabaseName_Like = taskDatabaseName_Like;
        return this;
    }

    public EtlTransferTaskSrcQuery taskDatabaseName_s(List<String> taskDatabaseName_s){
        if (taskDatabaseName_s == null) {
            throw new RuntimeException("taskDatabaseName_s is empty ");
        }
        this.taskDatabaseName_s = taskDatabaseName_s;
        return this;
    }


    public EtlTransferTaskSrcQuery orderMapping_(Integer orderMapping_){
	if (orderMapping_ == null) {
            throw new RuntimeException("orderMapping_ is null");
        }         
	this.orderMapping_ = orderMapping_;
	return this;
    }

    public EtlTransferTaskSrcQuery orderMapping_GreaterThanOrEqual(Integer orderMapping_GreaterThanOrEqual){
	if (orderMapping_GreaterThanOrEqual == null) {
	    throw new RuntimeException("orderMapping_ is null");
        }         
	this.orderMapping_GreaterThanOrEqual = orderMapping_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskSrcQuery orderMapping_LessThanOrEqual(Integer orderMapping_LessThanOrEqual){
        if (orderMapping_LessThanOrEqual == null) {
            throw new RuntimeException("orderMapping_ is null");
        }
        this.orderMapping_LessThanOrEqual = orderMapping_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskSrcQuery orderMapping_s(List<Integer> orderMapping_s){
        if (orderMapping_s == null) {
            throw new RuntimeException("orderMapping_s is empty ");
        }
        this.orderMapping_s = orderMapping_s;
        return this;
    }


    public EtlTransferTaskSrcQuery createBy_(String createBy_){
	if (createBy_ == null) {
	    throw new RuntimeException("createBy_ is null");
        }         
	this.createBy_ = createBy_;
	return this;
    }

    public EtlTransferTaskSrcQuery createBy_Like( String createBy_Like){
        if (createBy_Like == null) {
            throw new RuntimeException("createBy_ is null");
        }
        this.createBy_Like = createBy_Like;
        return this;
    }

    public EtlTransferTaskSrcQuery createBy_s(List<String> createBy_s){
        if (createBy_s == null) {
            throw new RuntimeException("createBy_s is empty ");
        }
        this.createBy_s = createBy_s;
        return this;
    }



    public EtlTransferTaskSrcQuery createTime_GreaterThanOrEqual(Date createTime_GreaterThanOrEqual){
	if (createTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime_ is null");
        }         
	this.createTime_GreaterThanOrEqual = createTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskSrcQuery createTime_LessThanOrEqual(Date createTime_LessThanOrEqual){
        if (createTime_LessThanOrEqual == null) {
            throw new RuntimeException("createTime_ is null");
        }
        this.createTime_LessThanOrEqual = createTime_LessThanOrEqual;
        return this;
    }



    public EtlTransferTaskSrcQuery updateBy_(String updateBy_){
	if (updateBy_ == null) {
	    throw new RuntimeException("updateBy_ is null");
        }         
	this.updateBy_ = updateBy_;
	return this;
    }

    public EtlTransferTaskSrcQuery updateBy_Like( String updateBy_Like){
        if (updateBy_Like == null) {
            throw new RuntimeException("updateBy_ is null");
        }
        this.updateBy_Like = updateBy_Like;
        return this;
    }

    public EtlTransferTaskSrcQuery updateBy_s(List<String> updateBy_s){
        if (updateBy_s == null) {
            throw new RuntimeException("updateBy_s is empty ");
        }
        this.updateBy_s = updateBy_s;
        return this;
    }



    public EtlTransferTaskSrcQuery updateTime_GreaterThanOrEqual(Date updateTime_GreaterThanOrEqual){
	if (updateTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime_ is null");
        }         
	this.updateTime_GreaterThanOrEqual = updateTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskSrcQuery updateTime_LessThanOrEqual(Date updateTime_LessThanOrEqual){
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

            if ("srcId_".equals(sortColumn)) {
                orderBy = "E.SRC_ID_" + a_x;
            } 

            if ("distinctFlag_".equals(sortColumn)) {
                orderBy = "E.DISTINCT_FLAG_" + a_x;
            } 

            if ("ownerName_".equals(sortColumn)) {
                orderBy = "E.OWNER_NAME_" + a_x;
            } 

            if ("taskConnId_".equals(sortColumn)) {
                orderBy = "E.TASK_CONN_ID_" + a_x;
            } 

            if ("taskDatabaseName_".equals(sortColumn)) {
                orderBy = "E.TASK_DATABASENAME_" + a_x;
            } 

            if ("orderMapping_".equals(sortColumn)) {
                orderBy = "E.ORDER_MAPPING_" + a_x;
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
        addColumn("srcId_", "SRC_ID_");
        addColumn("distinctFlag_", "DISTINCT_FLAG_");
        addColumn("ownerName_", "OWNER_NAME_");
        addColumn("taskConnId_", "TASK_CONN_ID_");
        addColumn("taskDatabaseName_", "TASK_DATABASENAME_");
        addColumn("orderMapping_", "ORDER_MAPPING_");
        addColumn("createBy_", "CREATEBY_");
        addColumn("createTime_", "CREATETIME_");
        addColumn("updateBy_", "UPDATEBY_");
        addColumn("updateTime_", "UPDATETIME_");
    }

}