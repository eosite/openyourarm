package com.glaf.etl.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class EtlTransferTaskQuery extends DataQuery {
        private static final long serialVersionUID = 1L;
	protected List<String> id_s;
	protected Collection<String> appActorIds;
  	protected String transId_;
  	protected String transId_Like;
  	protected List<String> transId_s;
  	protected String taskName_;
  	protected String taskName_Like;
  	protected List<String> taskName_s;
  	protected String taskDesc_;
  	protected String taskDesc_Like;
  	protected List<String> taskDesc_s;
  	protected Integer commitInterval_;
  	protected Integer commitInterval_GreaterThanOrEqual;
  	protected Integer commitInterval_LessThanOrEqual;
  	protected List<Integer> commitInterval_s;
  	protected Integer rollbackTransFlag_;
  	protected Integer rollbackTransFlag_GreaterThanOrEqual;
  	protected Integer rollbackTransFlag_LessThanOrEqual;
  	protected List<Integer> rollbackTransFlag_s;
  	protected Integer retryFlag_;
  	protected Integer retryFlag_GreaterThanOrEqual;
  	protected Integer retryFlag_LessThanOrEqual;
  	protected List<Integer> retryFlag_s;
  	protected Integer retryTimes_;
  	protected Integer retryTimes_GreaterThanOrEqual;
  	protected Integer retryTimes_LessThanOrEqual;
  	protected List<Integer> retryTimes_s;
  	protected Integer onPrePostErrorStop_;
  	protected Integer onPrePostErrorStop_GreaterThanOrEqual;
  	protected Integer onPrePostErrorStop_LessThanOrEqual;
  	protected List<Integer> onPrePostErrorStop_s;
  	protected Integer sendMailFlag_;
  	protected Integer sendMailFlag_GreaterThanOrEqual;
  	protected Integer sendMailFlag_LessThanOrEqual;
  	protected List<Integer> sendMailFlag_s;
  	protected String emailAddress_;
  	protected String emailAddress_Like;
  	protected List<String> emailAddress_s;
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
  	protected Integer deleteFlag_;
  	protected Integer deleteFlag_GreaterThanOrEqual;
  	protected Integer deleteFlag_LessThanOrEqual;
  	protected List<Integer> deleteFlag_s;
        protected Date lastStartTime_GreaterThanOrEqual;
  	protected Date lastStartTime_LessThanOrEqual;
        protected Date lastEndTime_GreaterThanOrEqual;
  	protected Date lastEndTime_LessThanOrEqual;
  	protected Integer succeed_;
  	protected Integer succeed_GreaterThanOrEqual;
  	protected Integer succeed_LessThanOrEqual;
  	protected List<Integer> succeed_s;
  	protected Integer locked_;
  	protected Integer locked_GreaterThanOrEqual;
  	protected Integer locked_LessThanOrEqual;
  	protected List<Integer> locked_s;
  	protected Integer errorStopAutoRun_;
  	protected Integer errorStopAutoRun_GreaterThanOrEqual;
  	protected Integer errorStopAutoRun_LessThanOrEqual;
  	protected List<Integer> errorStopAutoRun_s;

    public EtlTransferTaskQuery() {

    }

    public Collection<String> getAppActorIds() {
	return appActorIds;
    }

    public void setAppActorIds(Collection<String> appActorIds) {
	this.appActorIds = appActorIds;
    }


    public String getTransId_(){
        return transId_;
    }

    public String getTransId_Like(){
	    if (transId_Like != null && transId_Like.trim().length() > 0) {
		if (!transId_Like.startsWith("%")) {
		    transId_Like = "%" + transId_Like;
		}
		if (!transId_Like.endsWith("%")) {
		   transId_Like = transId_Like + "%";
		}
	    }
	return transId_Like;
    }

    public List<String> getTransId_s(){
	return transId_s;
    }


    public String getTaskName_(){
        return taskName_;
    }

    public String getTaskName_Like(){
	    if (taskName_Like != null && taskName_Like.trim().length() > 0) {
		if (!taskName_Like.startsWith("%")) {
		    taskName_Like = "%" + taskName_Like;
		}
		if (!taskName_Like.endsWith("%")) {
		   taskName_Like = taskName_Like + "%";
		}
	    }
	return taskName_Like;
    }

    public List<String> getTaskName_s(){
	return taskName_s;
    }


    public String getTaskDesc_(){
        return taskDesc_;
    }

    public String getTaskDesc_Like(){
	    if (taskDesc_Like != null && taskDesc_Like.trim().length() > 0) {
		if (!taskDesc_Like.startsWith("%")) {
		    taskDesc_Like = "%" + taskDesc_Like;
		}
		if (!taskDesc_Like.endsWith("%")) {
		   taskDesc_Like = taskDesc_Like + "%";
		}
	    }
	return taskDesc_Like;
    }

    public List<String> getTaskDesc_s(){
	return taskDesc_s;
    }


    public Integer getCommitInterval_(){
        return commitInterval_;
    }

    public Integer getCommitInterval_GreaterThanOrEqual(){
        return commitInterval_GreaterThanOrEqual;
    }

    public Integer getCommitInterval_LessThanOrEqual(){
	return commitInterval_LessThanOrEqual;
    }

    public List<Integer> getCommitInterval_s(){
	return commitInterval_s;
    }

    public Integer getRollbackTransFlag_(){
        return rollbackTransFlag_;
    }

    public Integer getRollbackTransFlag_GreaterThanOrEqual(){
        return rollbackTransFlag_GreaterThanOrEqual;
    }

    public Integer getRollbackTransFlag_LessThanOrEqual(){
	return rollbackTransFlag_LessThanOrEqual;
    }

    public List<Integer> getRollbackTransFlag_s(){
	return rollbackTransFlag_s;
    }

    public Integer getRetryFlag_(){
        return retryFlag_;
    }

    public Integer getRetryFlag_GreaterThanOrEqual(){
        return retryFlag_GreaterThanOrEqual;
    }

    public Integer getRetryFlag_LessThanOrEqual(){
	return retryFlag_LessThanOrEqual;
    }

    public List<Integer> getRetryFlag_s(){
	return retryFlag_s;
    }

    public Integer getRetryTimes_(){
        return retryTimes_;
    }

    public Integer getRetryTimes_GreaterThanOrEqual(){
        return retryTimes_GreaterThanOrEqual;
    }

    public Integer getRetryTimes_LessThanOrEqual(){
	return retryTimes_LessThanOrEqual;
    }

    public List<Integer> getRetryTimes_s(){
	return retryTimes_s;
    }

    public Integer getOnPrePostErrorStop_(){
        return onPrePostErrorStop_;
    }

    public Integer getOnPrePostErrorStop_GreaterThanOrEqual(){
        return onPrePostErrorStop_GreaterThanOrEqual;
    }

    public Integer getOnPrePostErrorStop_LessThanOrEqual(){
	return onPrePostErrorStop_LessThanOrEqual;
    }

    public List<Integer> getOnPrePostErrorStop_s(){
	return onPrePostErrorStop_s;
    }

    public Integer getSendMailFlag_(){
        return sendMailFlag_;
    }

    public Integer getSendMailFlag_GreaterThanOrEqual(){
        return sendMailFlag_GreaterThanOrEqual;
    }

    public Integer getSendMailFlag_LessThanOrEqual(){
	return sendMailFlag_LessThanOrEqual;
    }

    public List<Integer> getSendMailFlag_s(){
	return sendMailFlag_s;
    }

    public String getEmailAddress_(){
        return emailAddress_;
    }

    public String getEmailAddress_Like(){
	    if (emailAddress_Like != null && emailAddress_Like.trim().length() > 0) {
		if (!emailAddress_Like.startsWith("%")) {
		    emailAddress_Like = "%" + emailAddress_Like;
		}
		if (!emailAddress_Like.endsWith("%")) {
		   emailAddress_Like = emailAddress_Like + "%";
		}
	    }
	return emailAddress_Like;
    }

    public List<String> getEmailAddress_s(){
	return emailAddress_s;
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


    public Integer getDeleteFlag_(){
        return deleteFlag_;
    }

    public Integer getDeleteFlag_GreaterThanOrEqual(){
        return deleteFlag_GreaterThanOrEqual;
    }

    public Integer getDeleteFlag_LessThanOrEqual(){
	return deleteFlag_LessThanOrEqual;
    }

    public List<Integer> getDeleteFlag_s(){
	return deleteFlag_s;
    }

    public Date getLastStartTime_GreaterThanOrEqual(){
        return lastStartTime_GreaterThanOrEqual;
    }

    public Date getLastStartTime_LessThanOrEqual(){
	return lastStartTime_LessThanOrEqual;
    }


    public Date getLastEndTime_GreaterThanOrEqual(){
        return lastEndTime_GreaterThanOrEqual;
    }

    public Date getLastEndTime_LessThanOrEqual(){
	return lastEndTime_LessThanOrEqual;
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

    public Integer getLocked_(){
        return locked_;
    }

    public Integer getLocked_GreaterThanOrEqual(){
        return locked_GreaterThanOrEqual;
    }

    public Integer getLocked_LessThanOrEqual(){
	return locked_LessThanOrEqual;
    }

    public List<Integer> getLocked_s(){
	return locked_s;
    }

    public Integer getErrorStopAutoRun_(){
        return errorStopAutoRun_;
    }

    public Integer getErrorStopAutoRun_GreaterThanOrEqual(){
        return errorStopAutoRun_GreaterThanOrEqual;
    }

    public Integer getErrorStopAutoRun_LessThanOrEqual(){
	return errorStopAutoRun_LessThanOrEqual;
    }

    public List<Integer> getErrorStopAutoRun_s(){
	return errorStopAutoRun_s;
    }

 

    public void setTransId_(String transId_){
        this.transId_ = transId_;
    }

    public void setTransId_Like( String transId_Like){
	this.transId_Like = transId_Like;
    }

    public void setTransId_s(List<String> transId_s){
        this.transId_s = transId_s;
    }


    public void setTaskName_(String taskName_){
        this.taskName_ = taskName_;
    }

    public void setTaskName_Like( String taskName_Like){
	this.taskName_Like = taskName_Like;
    }

    public void setTaskName_s(List<String> taskName_s){
        this.taskName_s = taskName_s;
    }


    public void setTaskDesc_(String taskDesc_){
        this.taskDesc_ = taskDesc_;
    }

    public void setTaskDesc_Like( String taskDesc_Like){
	this.taskDesc_Like = taskDesc_Like;
    }

    public void setTaskDesc_s(List<String> taskDesc_s){
        this.taskDesc_s = taskDesc_s;
    }


    public void setCommitInterval_(Integer commitInterval_){
        this.commitInterval_ = commitInterval_;
    }

    public void setCommitInterval_GreaterThanOrEqual(Integer commitInterval_GreaterThanOrEqual){
        this.commitInterval_GreaterThanOrEqual = commitInterval_GreaterThanOrEqual;
    }

    public void setCommitInterval_LessThanOrEqual(Integer commitInterval_LessThanOrEqual){
	this.commitInterval_LessThanOrEqual = commitInterval_LessThanOrEqual;
    }

    public void setCommitInterval_s(List<Integer> commitInterval_s){
        this.commitInterval_s = commitInterval_s;
    }


    public void setRollbackTransFlag_(Integer rollbackTransFlag_){
        this.rollbackTransFlag_ = rollbackTransFlag_;
    }

    public void setRollbackTransFlag_GreaterThanOrEqual(Integer rollbackTransFlag_GreaterThanOrEqual){
        this.rollbackTransFlag_GreaterThanOrEqual = rollbackTransFlag_GreaterThanOrEqual;
    }

    public void setRollbackTransFlag_LessThanOrEqual(Integer rollbackTransFlag_LessThanOrEqual){
	this.rollbackTransFlag_LessThanOrEqual = rollbackTransFlag_LessThanOrEqual;
    }

    public void setRollbackTransFlag_s(List<Integer> rollbackTransFlag_s){
        this.rollbackTransFlag_s = rollbackTransFlag_s;
    }


    public void setRetryFlag_(Integer retryFlag_){
        this.retryFlag_ = retryFlag_;
    }

    public void setRetryFlag_GreaterThanOrEqual(Integer retryFlag_GreaterThanOrEqual){
        this.retryFlag_GreaterThanOrEqual = retryFlag_GreaterThanOrEqual;
    }

    public void setRetryFlag_LessThanOrEqual(Integer retryFlag_LessThanOrEqual){
	this.retryFlag_LessThanOrEqual = retryFlag_LessThanOrEqual;
    }

    public void setRetryFlag_s(List<Integer> retryFlag_s){
        this.retryFlag_s = retryFlag_s;
    }


    public void setRetryTimes_(Integer retryTimes_){
        this.retryTimes_ = retryTimes_;
    }

    public void setRetryTimes_GreaterThanOrEqual(Integer retryTimes_GreaterThanOrEqual){
        this.retryTimes_GreaterThanOrEqual = retryTimes_GreaterThanOrEqual;
    }

    public void setRetryTimes_LessThanOrEqual(Integer retryTimes_LessThanOrEqual){
	this.retryTimes_LessThanOrEqual = retryTimes_LessThanOrEqual;
    }

    public void setRetryTimes_s(List<Integer> retryTimes_s){
        this.retryTimes_s = retryTimes_s;
    }


    public void setOnPrePostErrorStop_(Integer onPrePostErrorStop_){
        this.onPrePostErrorStop_ = onPrePostErrorStop_;
    }

    public void setOnPrePostErrorStop_GreaterThanOrEqual(Integer onPrePostErrorStop_GreaterThanOrEqual){
        this.onPrePostErrorStop_GreaterThanOrEqual = onPrePostErrorStop_GreaterThanOrEqual;
    }

    public void setOnPrePostErrorStop_LessThanOrEqual(Integer onPrePostErrorStop_LessThanOrEqual){
	this.onPrePostErrorStop_LessThanOrEqual = onPrePostErrorStop_LessThanOrEqual;
    }

    public void setOnPrePostErrorStop_s(List<Integer> onPrePostErrorStop_s){
        this.onPrePostErrorStop_s = onPrePostErrorStop_s;
    }


    public void setSendMailFlag_(Integer sendMailFlag_){
        this.sendMailFlag_ = sendMailFlag_;
    }

    public void setSendMailFlag_GreaterThanOrEqual(Integer sendMailFlag_GreaterThanOrEqual){
        this.sendMailFlag_GreaterThanOrEqual = sendMailFlag_GreaterThanOrEqual;
    }

    public void setSendMailFlag_LessThanOrEqual(Integer sendMailFlag_LessThanOrEqual){
	this.sendMailFlag_LessThanOrEqual = sendMailFlag_LessThanOrEqual;
    }

    public void setSendMailFlag_s(List<Integer> sendMailFlag_s){
        this.sendMailFlag_s = sendMailFlag_s;
    }


    public void setEmailAddress_(String emailAddress_){
        this.emailAddress_ = emailAddress_;
    }

    public void setEmailAddress_Like( String emailAddress_Like){
	this.emailAddress_Like = emailAddress_Like;
    }

    public void setEmailAddress_s(List<String> emailAddress_s){
        this.emailAddress_s = emailAddress_s;
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


    public void setDeleteFlag_(Integer deleteFlag_){
        this.deleteFlag_ = deleteFlag_;
    }

    public void setDeleteFlag_GreaterThanOrEqual(Integer deleteFlag_GreaterThanOrEqual){
        this.deleteFlag_GreaterThanOrEqual = deleteFlag_GreaterThanOrEqual;
    }

    public void setDeleteFlag_LessThanOrEqual(Integer deleteFlag_LessThanOrEqual){
	this.deleteFlag_LessThanOrEqual = deleteFlag_LessThanOrEqual;
    }

    public void setDeleteFlag_s(List<Integer> deleteFlag_s){
        this.deleteFlag_s = deleteFlag_s;
    }


    public void setLastStartTime_GreaterThanOrEqual(Date lastStartTime_GreaterThanOrEqual){
        this.lastStartTime_GreaterThanOrEqual = lastStartTime_GreaterThanOrEqual;
    }

    public void setLastStartTime_LessThanOrEqual(Date lastStartTime_LessThanOrEqual){
	this.lastStartTime_LessThanOrEqual = lastStartTime_LessThanOrEqual;
    }


    public void setLastEndTime_GreaterThanOrEqual(Date lastEndTime_GreaterThanOrEqual){
        this.lastEndTime_GreaterThanOrEqual = lastEndTime_GreaterThanOrEqual;
    }

    public void setLastEndTime_LessThanOrEqual(Date lastEndTime_LessThanOrEqual){
	this.lastEndTime_LessThanOrEqual = lastEndTime_LessThanOrEqual;
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


    public void setLocked_(Integer locked_){
        this.locked_ = locked_;
    }

    public void setLocked_GreaterThanOrEqual(Integer locked_GreaterThanOrEqual){
        this.locked_GreaterThanOrEqual = locked_GreaterThanOrEqual;
    }

    public void setLocked_LessThanOrEqual(Integer locked_LessThanOrEqual){
	this.locked_LessThanOrEqual = locked_LessThanOrEqual;
    }

    public void setLocked_s(List<Integer> locked_s){
        this.locked_s = locked_s;
    }


    public void setErrorStopAutoRun_(Integer errorStopAutoRun_){
        this.errorStopAutoRun_ = errorStopAutoRun_;
    }

    public void setErrorStopAutoRun_GreaterThanOrEqual(Integer errorStopAutoRun_GreaterThanOrEqual){
        this.errorStopAutoRun_GreaterThanOrEqual = errorStopAutoRun_GreaterThanOrEqual;
    }

    public void setErrorStopAutoRun_LessThanOrEqual(Integer errorStopAutoRun_LessThanOrEqual){
	this.errorStopAutoRun_LessThanOrEqual = errorStopAutoRun_LessThanOrEqual;
    }

    public void setErrorStopAutoRun_s(List<Integer> errorStopAutoRun_s){
        this.errorStopAutoRun_s = errorStopAutoRun_s;
    }




    public EtlTransferTaskQuery transId_(String transId_){
	if (transId_ == null) {
	    throw new RuntimeException("transId_ is null");
        }         
	this.transId_ = transId_;
	return this;
    }

    public EtlTransferTaskQuery transId_Like( String transId_Like){
        if (transId_Like == null) {
            throw new RuntimeException("transId_ is null");
        }
        this.transId_Like = transId_Like;
        return this;
    }

    public EtlTransferTaskQuery transId_s(List<String> transId_s){
        if (transId_s == null) {
            throw new RuntimeException("transId_s is empty ");
        }
        this.transId_s = transId_s;
        return this;
    }


    public EtlTransferTaskQuery taskName_(String taskName_){
	if (taskName_ == null) {
	    throw new RuntimeException("taskName_ is null");
        }         
	this.taskName_ = taskName_;
	return this;
    }

    public EtlTransferTaskQuery taskName_Like( String taskName_Like){
        if (taskName_Like == null) {
            throw new RuntimeException("taskName_ is null");
        }
        this.taskName_Like = taskName_Like;
        return this;
    }

    public EtlTransferTaskQuery taskName_s(List<String> taskName_s){
        if (taskName_s == null) {
            throw new RuntimeException("taskName_s is empty ");
        }
        this.taskName_s = taskName_s;
        return this;
    }


    public EtlTransferTaskQuery taskDesc_(String taskDesc_){
	if (taskDesc_ == null) {
	    throw new RuntimeException("taskDesc_ is null");
        }         
	this.taskDesc_ = taskDesc_;
	return this;
    }

    public EtlTransferTaskQuery taskDesc_Like( String taskDesc_Like){
        if (taskDesc_Like == null) {
            throw new RuntimeException("taskDesc_ is null");
        }
        this.taskDesc_Like = taskDesc_Like;
        return this;
    }

    public EtlTransferTaskQuery taskDesc_s(List<String> taskDesc_s){
        if (taskDesc_s == null) {
            throw new RuntimeException("taskDesc_s is empty ");
        }
        this.taskDesc_s = taskDesc_s;
        return this;
    }


    public EtlTransferTaskQuery commitInterval_(Integer commitInterval_){
	if (commitInterval_ == null) {
            throw new RuntimeException("commitInterval_ is null");
        }         
	this.commitInterval_ = commitInterval_;
	return this;
    }

    public EtlTransferTaskQuery commitInterval_GreaterThanOrEqual(Integer commitInterval_GreaterThanOrEqual){
	if (commitInterval_GreaterThanOrEqual == null) {
	    throw new RuntimeException("commitInterval_ is null");
        }         
	this.commitInterval_GreaterThanOrEqual = commitInterval_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery commitInterval_LessThanOrEqual(Integer commitInterval_LessThanOrEqual){
        if (commitInterval_LessThanOrEqual == null) {
            throw new RuntimeException("commitInterval_ is null");
        }
        this.commitInterval_LessThanOrEqual = commitInterval_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery commitInterval_s(List<Integer> commitInterval_s){
        if (commitInterval_s == null) {
            throw new RuntimeException("commitInterval_s is empty ");
        }
        this.commitInterval_s = commitInterval_s;
        return this;
    }


    public EtlTransferTaskQuery rollbackTransFlag_(Integer rollbackTransFlag_){
	if (rollbackTransFlag_ == null) {
            throw new RuntimeException("rollbackTransFlag_ is null");
        }         
	this.rollbackTransFlag_ = rollbackTransFlag_;
	return this;
    }

    public EtlTransferTaskQuery rollbackTransFlag_GreaterThanOrEqual(Integer rollbackTransFlag_GreaterThanOrEqual){
	if (rollbackTransFlag_GreaterThanOrEqual == null) {
	    throw new RuntimeException("rollbackTransFlag_ is null");
        }         
	this.rollbackTransFlag_GreaterThanOrEqual = rollbackTransFlag_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery rollbackTransFlag_LessThanOrEqual(Integer rollbackTransFlag_LessThanOrEqual){
        if (rollbackTransFlag_LessThanOrEqual == null) {
            throw new RuntimeException("rollbackTransFlag_ is null");
        }
        this.rollbackTransFlag_LessThanOrEqual = rollbackTransFlag_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery rollbackTransFlag_s(List<Integer> rollbackTransFlag_s){
        if (rollbackTransFlag_s == null) {
            throw new RuntimeException("rollbackTransFlag_s is empty ");
        }
        this.rollbackTransFlag_s = rollbackTransFlag_s;
        return this;
    }


    public EtlTransferTaskQuery retryFlag_(Integer retryFlag_){
	if (retryFlag_ == null) {
            throw new RuntimeException("retryFlag_ is null");
        }         
	this.retryFlag_ = retryFlag_;
	return this;
    }

    public EtlTransferTaskQuery retryFlag_GreaterThanOrEqual(Integer retryFlag_GreaterThanOrEqual){
	if (retryFlag_GreaterThanOrEqual == null) {
	    throw new RuntimeException("retryFlag_ is null");
        }         
	this.retryFlag_GreaterThanOrEqual = retryFlag_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery retryFlag_LessThanOrEqual(Integer retryFlag_LessThanOrEqual){
        if (retryFlag_LessThanOrEqual == null) {
            throw new RuntimeException("retryFlag_ is null");
        }
        this.retryFlag_LessThanOrEqual = retryFlag_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery retryFlag_s(List<Integer> retryFlag_s){
        if (retryFlag_s == null) {
            throw new RuntimeException("retryFlag_s is empty ");
        }
        this.retryFlag_s = retryFlag_s;
        return this;
    }


    public EtlTransferTaskQuery retryTimes_(Integer retryTimes_){
	if (retryTimes_ == null) {
            throw new RuntimeException("retryTimes_ is null");
        }         
	this.retryTimes_ = retryTimes_;
	return this;
    }

    public EtlTransferTaskQuery retryTimes_GreaterThanOrEqual(Integer retryTimes_GreaterThanOrEqual){
	if (retryTimes_GreaterThanOrEqual == null) {
	    throw new RuntimeException("retryTimes_ is null");
        }         
	this.retryTimes_GreaterThanOrEqual = retryTimes_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery retryTimes_LessThanOrEqual(Integer retryTimes_LessThanOrEqual){
        if (retryTimes_LessThanOrEqual == null) {
            throw new RuntimeException("retryTimes_ is null");
        }
        this.retryTimes_LessThanOrEqual = retryTimes_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery retryTimes_s(List<Integer> retryTimes_s){
        if (retryTimes_s == null) {
            throw new RuntimeException("retryTimes_s is empty ");
        }
        this.retryTimes_s = retryTimes_s;
        return this;
    }


    public EtlTransferTaskQuery onPrePostErrorStop_(Integer onPrePostErrorStop_){
	if (onPrePostErrorStop_ == null) {
            throw new RuntimeException("onPrePostErrorStop_ is null");
        }         
	this.onPrePostErrorStop_ = onPrePostErrorStop_;
	return this;
    }

    public EtlTransferTaskQuery onPrePostErrorStop_GreaterThanOrEqual(Integer onPrePostErrorStop_GreaterThanOrEqual){
	if (onPrePostErrorStop_GreaterThanOrEqual == null) {
	    throw new RuntimeException("onPrePostErrorStop_ is null");
        }         
	this.onPrePostErrorStop_GreaterThanOrEqual = onPrePostErrorStop_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery onPrePostErrorStop_LessThanOrEqual(Integer onPrePostErrorStop_LessThanOrEqual){
        if (onPrePostErrorStop_LessThanOrEqual == null) {
            throw new RuntimeException("onPrePostErrorStop_ is null");
        }
        this.onPrePostErrorStop_LessThanOrEqual = onPrePostErrorStop_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery onPrePostErrorStop_s(List<Integer> onPrePostErrorStop_s){
        if (onPrePostErrorStop_s == null) {
            throw new RuntimeException("onPrePostErrorStop_s is empty ");
        }
        this.onPrePostErrorStop_s = onPrePostErrorStop_s;
        return this;
    }


    public EtlTransferTaskQuery sendMailFlag_(Integer sendMailFlag_){
	if (sendMailFlag_ == null) {
            throw new RuntimeException("sendMailFlag_ is null");
        }         
	this.sendMailFlag_ = sendMailFlag_;
	return this;
    }

    public EtlTransferTaskQuery sendMailFlag_GreaterThanOrEqual(Integer sendMailFlag_GreaterThanOrEqual){
	if (sendMailFlag_GreaterThanOrEqual == null) {
	    throw new RuntimeException("sendMailFlag_ is null");
        }         
	this.sendMailFlag_GreaterThanOrEqual = sendMailFlag_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery sendMailFlag_LessThanOrEqual(Integer sendMailFlag_LessThanOrEqual){
        if (sendMailFlag_LessThanOrEqual == null) {
            throw new RuntimeException("sendMailFlag_ is null");
        }
        this.sendMailFlag_LessThanOrEqual = sendMailFlag_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery sendMailFlag_s(List<Integer> sendMailFlag_s){
        if (sendMailFlag_s == null) {
            throw new RuntimeException("sendMailFlag_s is empty ");
        }
        this.sendMailFlag_s = sendMailFlag_s;
        return this;
    }


    public EtlTransferTaskQuery emailAddress_(String emailAddress_){
	if (emailAddress_ == null) {
	    throw new RuntimeException("emailAddress_ is null");
        }         
	this.emailAddress_ = emailAddress_;
	return this;
    }

    public EtlTransferTaskQuery emailAddress_Like( String emailAddress_Like){
        if (emailAddress_Like == null) {
            throw new RuntimeException("emailAddress_ is null");
        }
        this.emailAddress_Like = emailAddress_Like;
        return this;
    }

    public EtlTransferTaskQuery emailAddress_s(List<String> emailAddress_s){
        if (emailAddress_s == null) {
            throw new RuntimeException("emailAddress_s is empty ");
        }
        this.emailAddress_s = emailAddress_s;
        return this;
    }


    public EtlTransferTaskQuery createBy_(String createBy_){
	if (createBy_ == null) {
	    throw new RuntimeException("createBy_ is null");
        }         
	this.createBy_ = createBy_;
	return this;
    }

    public EtlTransferTaskQuery createBy_Like( String createBy_Like){
        if (createBy_Like == null) {
            throw new RuntimeException("createBy_ is null");
        }
        this.createBy_Like = createBy_Like;
        return this;
    }

    public EtlTransferTaskQuery createBy_s(List<String> createBy_s){
        if (createBy_s == null) {
            throw new RuntimeException("createBy_s is empty ");
        }
        this.createBy_s = createBy_s;
        return this;
    }



    public EtlTransferTaskQuery createTime_GreaterThanOrEqual(Date createTime_GreaterThanOrEqual){
	if (createTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("createTime_ is null");
        }         
	this.createTime_GreaterThanOrEqual = createTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery createTime_LessThanOrEqual(Date createTime_LessThanOrEqual){
        if (createTime_LessThanOrEqual == null) {
            throw new RuntimeException("createTime_ is null");
        }
        this.createTime_LessThanOrEqual = createTime_LessThanOrEqual;
        return this;
    }



    public EtlTransferTaskQuery updateBy_(String updateBy_){
	if (updateBy_ == null) {
	    throw new RuntimeException("updateBy_ is null");
        }         
	this.updateBy_ = updateBy_;
	return this;
    }

    public EtlTransferTaskQuery updateBy_Like( String updateBy_Like){
        if (updateBy_Like == null) {
            throw new RuntimeException("updateBy_ is null");
        }
        this.updateBy_Like = updateBy_Like;
        return this;
    }

    public EtlTransferTaskQuery updateBy_s(List<String> updateBy_s){
        if (updateBy_s == null) {
            throw new RuntimeException("updateBy_s is empty ");
        }
        this.updateBy_s = updateBy_s;
        return this;
    }



    public EtlTransferTaskQuery updateTime_GreaterThanOrEqual(Date updateTime_GreaterThanOrEqual){
	if (updateTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("updateTime_ is null");
        }         
	this.updateTime_GreaterThanOrEqual = updateTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery updateTime_LessThanOrEqual(Date updateTime_LessThanOrEqual){
        if (updateTime_LessThanOrEqual == null) {
            throw new RuntimeException("updateTime_ is null");
        }
        this.updateTime_LessThanOrEqual = updateTime_LessThanOrEqual;
        return this;
    }



    public EtlTransferTaskQuery deleteFlag_(Integer deleteFlag_){
	if (deleteFlag_ == null) {
            throw new RuntimeException("deleteFlag_ is null");
        }         
	this.deleteFlag_ = deleteFlag_;
	return this;
    }

    public EtlTransferTaskQuery deleteFlag_GreaterThanOrEqual(Integer deleteFlag_GreaterThanOrEqual){
	if (deleteFlag_GreaterThanOrEqual == null) {
	    throw new RuntimeException("deleteFlag_ is null");
        }         
	this.deleteFlag_GreaterThanOrEqual = deleteFlag_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery deleteFlag_LessThanOrEqual(Integer deleteFlag_LessThanOrEqual){
        if (deleteFlag_LessThanOrEqual == null) {
            throw new RuntimeException("deleteFlag_ is null");
        }
        this.deleteFlag_LessThanOrEqual = deleteFlag_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery deleteFlag_s(List<Integer> deleteFlag_s){
        if (deleteFlag_s == null) {
            throw new RuntimeException("deleteFlag_s is empty ");
        }
        this.deleteFlag_s = deleteFlag_s;
        return this;
    }



    public EtlTransferTaskQuery lastStartTime_GreaterThanOrEqual(Date lastStartTime_GreaterThanOrEqual){
	if (lastStartTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("lastStartTime_ is null");
        }         
	this.lastStartTime_GreaterThanOrEqual = lastStartTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery lastStartTime_LessThanOrEqual(Date lastStartTime_LessThanOrEqual){
        if (lastStartTime_LessThanOrEqual == null) {
            throw new RuntimeException("lastStartTime_ is null");
        }
        this.lastStartTime_LessThanOrEqual = lastStartTime_LessThanOrEqual;
        return this;
    }




    public EtlTransferTaskQuery lastEndTime_GreaterThanOrEqual(Date lastEndTime_GreaterThanOrEqual){
	if (lastEndTime_GreaterThanOrEqual == null) {
	    throw new RuntimeException("lastEndTime_ is null");
        }         
	this.lastEndTime_GreaterThanOrEqual = lastEndTime_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery lastEndTime_LessThanOrEqual(Date lastEndTime_LessThanOrEqual){
        if (lastEndTime_LessThanOrEqual == null) {
            throw new RuntimeException("lastEndTime_ is null");
        }
        this.lastEndTime_LessThanOrEqual = lastEndTime_LessThanOrEqual;
        return this;
    }



    public EtlTransferTaskQuery succeed_(Integer succeed_){
	if (succeed_ == null) {
            throw new RuntimeException("succeed_ is null");
        }         
	this.succeed_ = succeed_;
	return this;
    }

    public EtlTransferTaskQuery succeed_GreaterThanOrEqual(Integer succeed_GreaterThanOrEqual){
	if (succeed_GreaterThanOrEqual == null) {
	    throw new RuntimeException("succeed_ is null");
        }         
	this.succeed_GreaterThanOrEqual = succeed_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery succeed_LessThanOrEqual(Integer succeed_LessThanOrEqual){
        if (succeed_LessThanOrEqual == null) {
            throw new RuntimeException("succeed_ is null");
        }
        this.succeed_LessThanOrEqual = succeed_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery succeed_s(List<Integer> succeed_s){
        if (succeed_s == null) {
            throw new RuntimeException("succeed_s is empty ");
        }
        this.succeed_s = succeed_s;
        return this;
    }


    public EtlTransferTaskQuery locked_(Integer locked_){
	if (locked_ == null) {
            throw new RuntimeException("locked_ is null");
        }         
	this.locked_ = locked_;
	return this;
    }

    public EtlTransferTaskQuery locked_GreaterThanOrEqual(Integer locked_GreaterThanOrEqual){
	if (locked_GreaterThanOrEqual == null) {
	    throw new RuntimeException("locked_ is null");
        }         
	this.locked_GreaterThanOrEqual = locked_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery locked_LessThanOrEqual(Integer locked_LessThanOrEqual){
        if (locked_LessThanOrEqual == null) {
            throw new RuntimeException("locked_ is null");
        }
        this.locked_LessThanOrEqual = locked_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery locked_s(List<Integer> locked_s){
        if (locked_s == null) {
            throw new RuntimeException("locked_s is empty ");
        }
        this.locked_s = locked_s;
        return this;
    }


    public EtlTransferTaskQuery errorStopAutoRun_(Integer errorStopAutoRun_){
	if (errorStopAutoRun_ == null) {
            throw new RuntimeException("errorStopAutoRun_ is null");
        }         
	this.errorStopAutoRun_ = errorStopAutoRun_;
	return this;
    }

    public EtlTransferTaskQuery errorStopAutoRun_GreaterThanOrEqual(Integer errorStopAutoRun_GreaterThanOrEqual){
	if (errorStopAutoRun_GreaterThanOrEqual == null) {
	    throw new RuntimeException("errorStopAutoRun_ is null");
        }         
	this.errorStopAutoRun_GreaterThanOrEqual = errorStopAutoRun_GreaterThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery errorStopAutoRun_LessThanOrEqual(Integer errorStopAutoRun_LessThanOrEqual){
        if (errorStopAutoRun_LessThanOrEqual == null) {
            throw new RuntimeException("errorStopAutoRun_ is null");
        }
        this.errorStopAutoRun_LessThanOrEqual = errorStopAutoRun_LessThanOrEqual;
        return this;
    }

    public EtlTransferTaskQuery errorStopAutoRun_s(List<Integer> errorStopAutoRun_s){
        if (errorStopAutoRun_s == null) {
            throw new RuntimeException("errorStopAutoRun_s is empty ");
        }
        this.errorStopAutoRun_s = errorStopAutoRun_s;
        return this;
    }



    public String getOrderBy() {
        if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

            if ("transId_".equals(sortColumn)) {
                orderBy = "E.TRANS_ID_" + a_x;
            } 

            if ("taskName_".equals(sortColumn)) {
                orderBy = "E.TASK_NAME_" + a_x;
            } 

            if ("taskDesc_".equals(sortColumn)) {
                orderBy = "E.TASK_DESC_" + a_x;
            } 

            if ("commitInterval_".equals(sortColumn)) {
                orderBy = "E.COMMIT_INTERVAL_" + a_x;
            } 

            if ("rollbackTransFlag_".equals(sortColumn)) {
                orderBy = "E.ROLLBACK_TRANS_FLAG_" + a_x;
            } 

            if ("retryFlag_".equals(sortColumn)) {
                orderBy = "E.RETRY_FLAG_" + a_x;
            } 

            if ("retryTimes_".equals(sortColumn)) {
                orderBy = "E.RETRY_TIMES_" + a_x;
            } 

            if ("onPrePostErrorStop_".equals(sortColumn)) {
                orderBy = "E.ON_PREPOSTERROR_STOP_" + a_x;
            } 

            if ("sendMailFlag_".equals(sortColumn)) {
                orderBy = "E.SENDMAIL_FLAG_" + a_x;
            } 

            if ("emailAddress_".equals(sortColumn)) {
                orderBy = "E.EMAIL_ADDRESS_" + a_x;
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

            if ("deleteFlag_".equals(sortColumn)) {
                orderBy = "E.DELETE_FLAG_" + a_x;
            } 

            if ("lastStartTime_".equals(sortColumn)) {
                orderBy = "E.LAST_START_TIME_" + a_x;
            } 

            if ("lastEndTime_".equals(sortColumn)) {
                orderBy = "E.LAST_END_TIME_" + a_x;
            } 

            if ("succeed_".equals(sortColumn)) {
                orderBy = "E.SUCCEED_" + a_x;
            } 

            if ("locked_".equals(sortColumn)) {
                orderBy = "E.LOCKED_" + a_x;
            } 

            if ("errorStopAutoRun_".equals(sortColumn)) {
                orderBy = "E.ERROR_STOP_AUTORUN_" + a_x;
            } 

        }
        return orderBy;
    }

    @Override
    public void initQueryColumns(){
        super.initQueryColumns();
        addColumn("id_", "ID_");
        addColumn("transId_", "TRANS_ID_");
        addColumn("taskName_", "TASK_NAME_");
        addColumn("taskDesc_", "TASK_DESC_");
        addColumn("commitInterval_", "COMMIT_INTERVAL_");
        addColumn("rollbackTransFlag_", "ROLLBACK_TRANS_FLAG_");
        addColumn("retryFlag_", "RETRY_FLAG_");
        addColumn("retryTimes_", "RETRY_TIMES_");
        addColumn("onPrePostErrorStop_", "ON_PREPOSTERROR_STOP_");
        addColumn("sendMailFlag_", "SENDMAIL_FLAG_");
        addColumn("emailAddress_", "EMAIL_ADDRESS_");
        addColumn("createBy_", "CREATEBY_");
        addColumn("createTime_", "CREATETIME_");
        addColumn("updateBy_", "UPDATEBY_");
        addColumn("updateTime_", "UPDATETIME_");
        addColumn("deleteFlag_", "DELETE_FLAG_");
        addColumn("lastStartTime_", "LAST_START_TIME_");
        addColumn("lastEndTime_", "LAST_END_TIME_");
        addColumn("succeed_", "SUCCEED_");
        addColumn("locked_", "LOCKED_");
        addColumn("errorStopAutoRun_", "ERROR_STOP_AUTORUN_");
    }

}