package com.glaf.etl.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EtlTransferTaskInstQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> id_s;
	protected Collection<String> appActorIds;
  	protected String taskId_;
  	protected String taskId_Like;
  	protected List<String> taskId_s;
        protected Date startTime_GreaterThanOrEqual;
  	protected Date startTime_LessThanOrEqual;
        protected Date endTime_GreaterThanOrEqual;
  	protected Date endTime_LessThanOrEqual;
  	protected Integer succeed_;
  	protected Integer succeed_GreaterThanOrEqual;
  	protected Integer succeed_LessThanOrEqual;
  	protected List<Integer> succeed_s;
  	protected Long srcSuccessRows_;
  	protected Long srcSuccessRows_GreaterThanOrEqual;
  	protected Long srcSuccessRows_LessThanOrEqual;
  	protected List<Long> srcSuccessRows_s;
  	protected Long srcFailedRows_;
  	protected Long srcFailedRows_GreaterThanOrEqual;
  	protected Long srcFailedRows_LessThanOrEqual;
  	protected List<Long> srcFailedRows_s;
  	protected Long targetSuccessRows_;
  	protected Long targetSuccessRows_GreaterThanOrEqual;
  	protected Long targetSuccessRows_LessThanOrEqual;
  	protected List<Long> targetSuccessRows_s;
  	protected Long targetFailedRows_;
  	protected Long targetFailedRows_GreaterThanOrEqual;
  	protected Long targetFailedRows_LessThanOrEqual;
  	protected List<Long> targetFailedRows_s;

    public EtlTransferTaskInstQuery() {

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


    public Date getStartTime_GreaterThanOrEqual(){
        return startTime_GreaterThanOrEqual;
    }

    public Date getStartTime_LessThanOrEqual(){
	return startTime_LessThanOrEqual;
    }


    public Date getEndTime_GreaterThanOrEqual(){
        return endTime_GreaterThanOrEqual;
    }

    public Date getEndTime_LessThanOrEqual(){
	return endTime_LessThanOrEqual;
    }


    public Integer getSucceed_(){
        return succeed_;
    }

    public Integer getSucceed_GreaterThanOrEqual(){
        return succeed_GreaterThanOrEqual;
    }

    public Integer getSucceed_LessThanOrEqual(){
	return succeed_LessThanOrEqual;
    }

    public List<Integer> getSucceed_s(){
	return succeed_s;
    }

    public Long getSrcSuccessRows_(){
        return srcSuccessRows_;
    }

    public Long getSrcSuccessRows_GreaterThanOrEqual(){
        return srcSuccessRows_GreaterThanOrEqual;
    }

    public Long getSrcSuccessRows_LessThanOrEqual(){
	return srcSuccessRows_LessThanOrEqual;
    }

    public List<Long> getSrcSuccessRows_s(){
	return srcSuccessRows_s;
    }

    public Long getSrcFailedRows_(){
        return srcFailedRows_;
    }

    public Long getSrcFailedRows_GreaterThanOrEqual(){
        return srcFailedRows_GreaterThanOrEqual;
    }

    public Long getSrcFailedRows_LessThanOrEqual(){
	return srcFailedRows_LessThanOrEqual;
    }

    public List<Long> getSrcFailedRows_s(){
	return srcFailedRows_s;
    }

    public Long getTargetSuccessRows_(){
        return targetSuccessRows_;
    }

    public Long getTargetSuccessRows_GreaterThanOrEqual(){
        return targetSuccessRows_GreaterThanOrEqual;
    }

    public Long getTargetSuccessRows_LessThanOrEqual(){
	return targetSuccessRows_LessThanOrEqual;
    }

    public List<Long> getTargetSuccessRows_s(){
	return targetSuccessRows_s;
    }

    public Long getTargetFailedRows_(){
        return targetFailedRows_;
    }

    public Long getTargetFailedRows_GreaterThanOrEqual(){
        return targetFailedRows_GreaterThanOrEqual;
    }

    public Long getTargetFailedRows_LessThanOrEqual(){
	return targetFailedRows_LessThanOrEqual;
    }

    public List<Long> getTargetFailedRows_s(){
	return targetFailedRows_s;
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


    public void setStartTime_GreaterThanOrEqual(Date startTime_GreaterThanOrEqual){
        this.startTime_GreaterThanOrEqual = startTime_GreaterThanOrEqual;
    }

    public void setStartTime_LessThanOrEqual(Date startTime_LessThanOrEqual){
	this.startTime_LessThanOrEqual = startTime_LessThanOrEqual;
    }


    public void setEndTime_GreaterThanOrEqual(Date endTime_GreaterThanOrEqual){
        this.endTime_GreaterThanOrEqual = endTime_GreaterThanOrEqual;
    }

    public void setEndTime_LessThanOrEqual(Date endTime_LessThanOrEqual){
	this.endTime_LessThanOrEqual = endTime_LessThanOrEqual;
    }


    public void setSucceed_(Integer succeed_){
        this.succeed_ = succeed_;
    }

    public void setSucceed_GreaterThanOrEqual(Integer succeed_GreaterThanOrEqual){
        this.succeed_GreaterThanOrEqual = succeed_GreaterThanOrEqual;
    }

    public void setSucceed_LessThanOrEqual(Integer succeed_LessThanOrEqual){
	this.succeed_LessThanOrEqual = succeed_LessThanOrEqual;
    }

    public void setSucceed_s(List<Integer> succeed_s){
        this.succeed_s = succeed_s;
    }


    public void setSrcSuccessRows_(Long srcSuccessRows_){
        this.srcSuccessRows_ = srcSuccessRows_;
    }

    public void setSrcSuccessRows_GreaterThanOrEqual(Long srcSuccessRows_GreaterThanOrEqual){
        this.srcSuccessRows_GreaterThanOrEqual = srcSuccessRows_GreaterThanOrEqual;
    }

    public void setSrcSuccessRows_LessThanOrEqual(Long srcSuccessRows_LessThanOrEqual){
	this.srcSuccessRows_LessThanOrEqual = srcSuccessRows_LessThanOrEqual;
    }

    public void setSrcSuccessRows_s(List<Long> srcSuccessRows_s){
        this.srcSuccessRows_s = srcSuccessRows_s;
    }


    public void setSrcFailedRows_(Long srcFailedRows_){
        this.srcFailedRows_ = srcFailedRows_;
    }

    public void setSrcFailedRows_GreaterThanOrEqual(Long srcFailedRows_GreaterThanOrEqual){
        this.srcFailedRows_GreaterThanOrEqual = srcFailedRows_GreaterThanOrEqual;
    }

    public void setSrcFailedRows_LessThanOrEqual(Long srcFailedRows_LessThanOrEqual){
	this.srcFailedRows_LessThanOrEqual = srcFailedRows_LessThanOrEqual;
    }

    public void setSrcFailedRows_s(List<Long> srcFailedRows_s){
        this.srcFailedRows_s = srcFailedRows_s;
    }


    public void setTargetSuccessRows_(Long targetSuccessRows_){
        this.targetSuccessRows_ = targetSuccessRows_;
    }

    public void setTargetSuccessRows_GreaterThanOrEqual(Long targetSuccessRows_GreaterThanOrEqual){
        this.targetSuccessRows_GreaterThanOrEqual = targetSuccessRows_GreaterThanOrEqual;
    }

    public void setTargetSuccessRows_LessThanOrEqual(Long targetSuccessRows_LessThanOrEqual){
	this.targetSuccessRows_LessThanOrEqual = targetSuccessRows_LessThanOrEqual;
    }

    public void setTargetSuccessRows_s(List<Long> targetSuccessRows_s){
        this.targetSuccessRows_s = targetSuccessRows_s;
    }


    public void setTargetFailedRows_(Long targetFailedRows_){
        this.targetFailedRows_ = targetFailedRows_;
    }

    public void setTargetFailedRows_GreaterThanOrEqual(Long targetFailedRows_GreaterThanOrEqual){
        this.targetFailedRows_GreaterThanOrEqual = targetFailedRows_GreaterThanOrEqual;
    }

    public void setTargetFailedRows_LessThanOrEqual(Long targetFailedRows_LessThanOrEqual){
	this.targetFailedRows_LessThanOrEqual = targetFailedRows_LessThanOrEqual;
    }

    public void setTargetFailedRows_s(List<Long> targetFailedRows_s){
        this.targetFailedRows_s = targetFailedRows_s;
    }




    public EtlTransferTaskInstQuery taskId_(String taskId_){
	if (taskId_ == null) {
	    throw new RuntimeException("taskId_ is null");
        }         
	this.taskId_ = taskId_;
	return this;
    }

    public EtlTransferTaskInstQuery taskId_Like( String taskId_Like){
        if (taskId_Like == null) {
            throw new RuntimeException("taskId_ is null");
        }
        this.taskId_Like = taskId_Like;
        return this;
    }

    public EtlTransferTaskInstQuery taskId_s(List<String> taskId_s){
        if (taskId_s == null) {
            throw new RuntimeException("taskId_s is empty ");
        }
        this.taskId_s = taskId_s;
        return this;
    }



    public EtlTransferTaskInstQuery startTime_GreaterThanOrEqual(Date startTime_GreaterThanOrEqual){
	if (startTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("startTime_ is null");
        }         
	this.startTime_GreaterThanOrEqual = startTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery startTime_LessThanOrEqual(Date startTime_LessThanOrEqual){
        if (startTime_LessThanOrEqual == null) {
            throw new RuntimeException("startTime_ is null");
        }
        this.startTime_LessThanOrEqual = startTime_LessThanOrEqual;
        return this;
    }




    public EtlTransferTaskInstQuery endTime_GreaterThanOrEqual(Date endTime_GreaterThanOrEqual){
	if (endTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("endTime_ is null");
        }         
	this.endTime_GreaterThanOrEqual = endTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery endTime_LessThanOrEqual(Date endTime_LessThanOrEqual){
        if (endTime_LessThanOrEqual == null) {
            throw new RuntimeException("endTime_ is null");
        }
        this.endTime_LessThanOrEqual = endTime_LessThanOrEqual;
        return this;
    }



    public EtlTransferTaskInstQuery succeed_(Integer succeed_){
	if (succeed_ == null) {
            throw new RuntimeException("succeed_ is null");
        }         
	this.succeed_ = succeed_;
	return this;
    }

    public EtlTransferTaskInstQuery succeed_GreaterThanOrEqual(Integer succeed_GreaterThanOrEqual){
	if (succeed_GreaterThanOrEqual == null) {
	    throw new RuntimeException("succeed_ is null");
        }         
	this.succeed_GreaterThanOrEqual = succeed_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery succeed_LessThanOrEqual(Integer succeed_LessThanOrEqual){
        if (succeed_LessThanOrEqual == null) {
            throw new RuntimeException("succeed_ is null");
        }
        this.succeed_LessThanOrEqual = succeed_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery succeed_s(List<Integer> succeed_s){
        if (succeed_s == null) {
            throw new RuntimeException("succeed_s is empty ");
        }
        this.succeed_s = succeed_s;
        return this;
    }


    public EtlTransferTaskInstQuery srcSuccessRows_(Long srcSuccessRows_){
	if (srcSuccessRows_ == null) {
            throw new RuntimeException("srcSuccessRows_ is null");
        }         
	this.srcSuccessRows_ = srcSuccessRows_;
	return this;
    }

    public EtlTransferTaskInstQuery srcSuccessRows_GreaterThanOrEqual(Long srcSuccessRows_GreaterThanOrEqual){
	if (srcSuccessRows_GreaterThanOrEqual == null) {
	    throw new RuntimeException("srcSuccessRows_ is null");
        }         
	this.srcSuccessRows_GreaterThanOrEqual = srcSuccessRows_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery srcSuccessRows_LessThanOrEqual(Long srcSuccessRows_LessThanOrEqual){
        if (srcSuccessRows_LessThanOrEqual == null) {
            throw new RuntimeException("srcSuccessRows_ is null");
        }
        this.srcSuccessRows_LessThanOrEqual = srcSuccessRows_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery srcSuccessRows_s(List<Long> srcSuccessRows_s){
        if (srcSuccessRows_s == null) {
            throw new RuntimeException("srcSuccessRows_s is empty ");
        }
        this.srcSuccessRows_s = srcSuccessRows_s;
        return this;
    }


    public EtlTransferTaskInstQuery srcFailedRows_(Long srcFailedRows_){
	if (srcFailedRows_ == null) {
            throw new RuntimeException("srcFailedRows_ is null");
        }         
	this.srcFailedRows_ = srcFailedRows_;
	return this;
    }

    public EtlTransferTaskInstQuery srcFailedRows_GreaterThanOrEqual(Long srcFailedRows_GreaterThanOrEqual){
	if (srcFailedRows_GreaterThanOrEqual == null) {
	    throw new RuntimeException("srcFailedRows_ is null");
        }         
	this.srcFailedRows_GreaterThanOrEqual = srcFailedRows_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery srcFailedRows_LessThanOrEqual(Long srcFailedRows_LessThanOrEqual){
        if (srcFailedRows_LessThanOrEqual == null) {
            throw new RuntimeException("srcFailedRows_ is null");
        }
        this.srcFailedRows_LessThanOrEqual = srcFailedRows_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery srcFailedRows_s(List<Long> srcFailedRows_s){
        if (srcFailedRows_s == null) {
            throw new RuntimeException("srcFailedRows_s is empty ");
        }
        this.srcFailedRows_s = srcFailedRows_s;
        return this;
    }


    public EtlTransferTaskInstQuery targetSuccessRows_(Long targetSuccessRows_){
	if (targetSuccessRows_ == null) {
            throw new RuntimeException("targetSuccessRows_ is null");
        }         
	this.targetSuccessRows_ = targetSuccessRows_;
	return this;
    }

    public EtlTransferTaskInstQuery targetSuccessRows_GreaterThanOrEqual(Long targetSuccessRows_GreaterThanOrEqual){
	if (targetSuccessRows_GreaterThanOrEqual == null) {
	    throw new RuntimeException("targetSuccessRows_ is null");
        }         
	this.targetSuccessRows_GreaterThanOrEqual = targetSuccessRows_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery targetSuccessRows_LessThanOrEqual(Long targetSuccessRows_LessThanOrEqual){
        if (targetSuccessRows_LessThanOrEqual == null) {
            throw new RuntimeException("targetSuccessRows_ is null");
        }
        this.targetSuccessRows_LessThanOrEqual = targetSuccessRows_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery targetSuccessRows_s(List<Long> targetSuccessRows_s){
        if (targetSuccessRows_s == null) {
            throw new RuntimeException("targetSuccessRows_s is empty ");
        }
        this.targetSuccessRows_s = targetSuccessRows_s;
        return this;
    }


    public EtlTransferTaskInstQuery targetFailedRows_(Long targetFailedRows_){
	if (targetFailedRows_ == null) {
            throw new RuntimeException("targetFailedRows_ is null");
        }         
	this.targetFailedRows_ = targetFailedRows_;
	return this;
    }

    public EtlTransferTaskInstQuery targetFailedRows_GreaterThanOrEqual(Long targetFailedRows_GreaterThanOrEqual){
	if (targetFailedRows_GreaterThanOrEqual == null) {
	    throw new RuntimeException("targetFailedRows_ is null");
        }         
	this.targetFailedRows_GreaterThanOrEqual = targetFailedRows_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery targetFailedRows_LessThanOrEqual(Long targetFailedRows_LessThanOrEqual){
        if (targetFailedRows_LessThanOrEqual == null) {
            throw new RuntimeException("targetFailedRows_ is null");
        }
        this.targetFailedRows_LessThanOrEqual = targetFailedRows_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskInstQuery targetFailedRows_s(List<Long> targetFailedRows_s){
        if (targetFailedRows_s == null) {
            throw new RuntimeException("targetFailedRows_s is empty ");
        }
        this.targetFailedRows_s = targetFailedRows_s;
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

            if ("startTime_".equals(sortColumn)) {
                orderBy = "E.START_TIME_" + a_x;
            } 

            if ("endTime_".equals(sortColumn)) {
                orderBy = "E.END_TIME_" + a_x;
            } 

            if ("succeed_".equals(sortColumn)) {
                orderBy = "E.SUCCEED_" + a_x;
            } 

            if ("srcSuccessRows_".equals(sortColumn)) {
                orderBy = "E.SRC_SUCCESS_ROWS_" + a_x;
            } 

            if ("srcFailedRows_".equals(sortColumn)) {
                orderBy = "E.SRC_FAILED_ROWS_" + a_x;
            } 

            if ("targetSuccessRows_".equals(sortColumn)) {
                orderBy = "E.TARGET_SUCCESS_ROWS_" + a_x;
            } 

            if ("targetFailedRows_".equals(sortColumn)) {
                orderBy = "E.TARGET_FAILED_ROWS_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id_", "ID_");
        addColumn("taskId_", "TASK_ID_");
        addColumn("startTime_", "START_TIME_");
        addColumn("endTime_", "END_TIME_");
        addColumn("succeed_", "SUCCEED_");
        addColumn("srcSuccessRows_", "SRC_SUCCESS_ROWS_");
        addColumn("srcFailedRows_", "SRC_FAILED_ROWS_");
        addColumn("targetSuccessRows_", "TARGET_SUCCESS_ROWS_");
        addColumn("targetFailedRows_", "TARGET_FAILED_ROWS_");
    }

}