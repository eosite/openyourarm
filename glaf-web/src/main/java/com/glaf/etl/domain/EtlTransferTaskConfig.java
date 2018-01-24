package com.glaf.etl.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.etl.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "ETL_TRANSFER_TASK_")
public class EtlTransferTaskConfig implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id_;

        /**
         * transId
         */
        @Column(name = "TRANS_ID_", length=64) 
        protected String transId_;

        /**
         * taskName
         */
        @Column(name = "TASK_NAME_", length=50) 
        protected String taskName_;

        /**
         * taskDesc
         */
        @Column(name = "TASK_DESC_", length=200) 
        protected String taskDesc_;

        /**
         * commitInterval
         */
        @Column(name = "COMMIT_INTERVAL_")
        protected Integer commitInterval_;

        /**
         * rollbackTransFlag
         */
        @Column(name = "ROLLBACK_TRANS_FLAG_")
        protected Integer rollbackTransFlag_;

        /**
         * retryFlag
         */
        @Column(name = "RETRY_FLAG_")
        protected Integer retryFlag_;

        /**
         * retryTimes
         */
        @Column(name = "RETRY_TIMES_")
        protected Integer retryTimes_;

        /**
         * onPrePostErrorStop
         */
        @Column(name = "ON_PREPOSTERROR_STOP_")
        protected Integer onPrePostErrorStop_;

        /**
         * sendMailFlag
         */
        @Column(name = "SENDMAIL_FLAG_")
        protected Integer sendMailFlag_;

        /**
         * emailAddress
         */
        @Column(name = "EMAIL_ADDRESS_", length=150) 
        protected String emailAddress_;

        /**
         * createBy
         */
        @Column(name = "CREATEBY_", length=50) 
        protected String createBy_;

        /**
         * createTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime_;

        /**
         * updateBy
         */
        @Column(name = "UPDATEBY_", length=50) 
        protected String updateBy_;

        /**
         * updateTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime_;

        /**
         * deleteFlag
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag_;

        /**
         * lastStartTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "LAST_START_TIME_")	
        protected Date lastStartTime_;

        /**
         * lastEndTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "LAST_END_TIME_")	
        protected Date lastEndTime_;

        /**
         * succeed
         */
        @Column(name = "SUCCEED_")
        protected Integer succeed_;
        
        /**
         * errorStopAutoRun
         */
        @Column(name = "ERROR_STOP_AUTORUN_")
        protected Integer errorStopAutoRun_;

        /**
         * locked
         */
        @Column(name = "LOCKED_")
        protected Integer locked_;
        
        protected String srcDatabaseName_;
        protected String targetDatabaseName_;

         
	public EtlTransferTaskConfig() {

	}


	public String getSrcDatabaseName_() {
		return srcDatabaseName_;
	}

	public void setSrcDatabaseName_(String srcDatabaseName_) {
		this.srcDatabaseName_ = srcDatabaseName_;
	}


	public String getTargetDatabaseName_() {
		return targetDatabaseName_;
	}





	public void setTargetDatabaseName_(String targetDatabaseName_) {
		this.targetDatabaseName_ = targetDatabaseName_;
	}





	public String getId_(){
	    return this.id_;
	}

	public void setId_(String id_) {
	    this.id_ = id_; 
	}


        public String getTransId_(){
	    return this.transId_;
	}
        public String getTaskName_(){
	    return this.taskName_;
	}
        public String getTaskDesc_(){
	    return this.taskDesc_;
	}
        public Integer getCommitInterval_(){
	    return this.commitInterval_;
	}
        public Integer getRollbackTransFlag_(){
	    return this.rollbackTransFlag_;
	}
        public Integer getRetryFlag_(){
	    return this.retryFlag_;
	}
        public Integer getRetryTimes_(){
	    return this.retryTimes_;
	}
        public Integer getOnPrePostErrorStop_(){
	    return this.onPrePostErrorStop_;
	}
        public Integer getSendMailFlag_(){
	    return this.sendMailFlag_;
	}
        public String getEmailAddress_(){
	    return this.emailAddress_;
	}
        public String getCreateBy_(){
	    return this.createBy_;
	}
	public Date getCreateTime_(){
	    return this.createTime_;
	}
	public String getCreateTime_String(){
	    if(this.createTime_ != null){
	        return DateUtils.getDateTime(this.createTime_);
	    }
	    return "";
	}
        public String getUpdateBy_(){
	    return this.updateBy_;
	}
	public Date getUpdateTime_(){
	    return this.updateTime_;
	}
	public String getUpdateTime_String(){
	    if(this.updateTime_ != null){
	        return DateUtils.getDateTime(this.updateTime_);
	    }
	    return "";
	}
        public Integer getDeleteFlag_(){
	    return this.deleteFlag_;
	}
	public Date getLastStartTime_(){
	    return this.lastStartTime_;
	}
	public String getLastStartTime_String(){
	    if(this.lastStartTime_ != null){
	        return DateUtils.getDateTime(this.lastStartTime_);
	    }
	    return "";
	}
	public Date getLastEndTime_(){
	    return this.lastEndTime_;
	}
	public String getLastEndTime_String(){
	    if(this.lastEndTime_ != null){
	        return DateUtils.getDateTime(this.lastEndTime_);
	    }
	    return "";
	}
        public Integer getSucceed_(){
	    return this.succeed_;
	}
        public Integer getLocked_(){
	    return this.locked_;
	}
        public Integer getErrorStopAutoRun_(){
	    return this.errorStopAutoRun_;
	}


        public void setTransId_(String transId_) {
	    this.transId_ = transId_; 
	}
        public void setTaskName_(String taskName_) {
	    this.taskName_ = taskName_; 
	}
        public void setTaskDesc_(String taskDesc_) {
	    this.taskDesc_ = taskDesc_; 
	}
        public void setCommitInterval_(Integer commitInterval_) {
	    this.commitInterval_ = commitInterval_; 
	}
        public void setRollbackTransFlag_(Integer rollbackTransFlag_) {
	    this.rollbackTransFlag_ = rollbackTransFlag_; 
	}
        public void setRetryFlag_(Integer retryFlag_) {
	    this.retryFlag_ = retryFlag_; 
	}
        public void setRetryTimes_(Integer retryTimes_) {
	    this.retryTimes_ = retryTimes_; 
	}
        public void setOnPrePostErrorStop_(Integer onPrePostErrorStop_) {
	    this.onPrePostErrorStop_ = onPrePostErrorStop_; 
	}
        public void setSendMailFlag_(Integer sendMailFlag_) {
	    this.sendMailFlag_ = sendMailFlag_; 
	}
        public void setEmailAddress_(String emailAddress_) {
	    this.emailAddress_ = emailAddress_; 
	}
        public void setCreateBy_(String createBy_) {
	    this.createBy_ = createBy_; 
	}
        public void setCreateTime_(Date createTime_) {
	    this.createTime_ = createTime_; 
	}
        public void setUpdateBy_(String updateBy_) {
	    this.updateBy_ = updateBy_; 
	}
        public void setUpdateTime_(Date updateTime_) {
	    this.updateTime_ = updateTime_; 
	}
        public void setDeleteFlag_(Integer deleteFlag_) {
	    this.deleteFlag_ = deleteFlag_; 
	}
        public void setLastStartTime_(Date lastStartTime_) {
	    this.lastStartTime_ = lastStartTime_; 
	}
        public void setLastEndTime_(Date lastEndTime_) {
	    this.lastEndTime_ = lastEndTime_; 
	}
        public void setSucceed_(Integer succeed_) {
	    this.succeed_ = succeed_; 
	}
        public void setLocked_(Integer locked_) {
	    this.locked_ = locked_; 
	}
        public void setErrorStopAutoRun_(Integer errorStopAutoRun_) {
	    this.errorStopAutoRun_ = errorStopAutoRun_; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EtlTransferTaskConfig other = (EtlTransferTaskConfig) obj;
		if (id_ == null) {
			if (other.id_ != null)
				return false;
		} else if (!id_.equals(other.id_))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id_ == null) ? 0 : id_.hashCode());
		return result;
	}


    @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	@Override
	public Object jsonToObject(JSONObject jsonobject) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public JSONObject toJsonObject() {
		// TODO Auto-generated method stub
		return null;
	}

}
