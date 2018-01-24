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
@Table(name = "ETL_TRANSFER_TASK_INST_")
public class EtlTransferTaskInst implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id_;

        /**
         * taskId
         */
        @Column(name = "TASK_ID_", length=64) 
        protected String taskId_;

        /**
         * startTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "START_TIME_")	
        protected Date startTime_;

        /**
         * endTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "END_TIME_")	
        protected Date endTime_;

        /**
         * succeed
         */
        @Column(name = "SUCCEED_")
        protected Integer succeed_;

        /**
         * srcSuccessRows
         */
        @Column(name = "SRC_SUCCESS_ROWS_")	 
        protected Long srcSuccessRows_;

        /**
         * srcFailedRows
         */
        @Column(name = "SRC_FAILED_ROWS_")	 
        protected Long srcFailedRows_;

        /**
         * targetSuccessRows
         */
        @Column(name = "TARGET_SUCCESS_ROWS_")	 
        protected Long targetSuccessRows_;

        /**
         * targetFailedRows
         */
        @Column(name = "TARGET_FAILED_ROWS_")	 
        protected Long targetFailedRows_;


         
	public EtlTransferTaskInst() {

	}

	public String getId_(){
	    return this.id_;
	}

	public void setId_(String id_) {
	    this.id_ = id_; 
	}


        public String getTaskId_(){
	    return this.taskId_;
	}
	public Date getStartTime_(){
	    return this.startTime_;
	}
	public String getStartTime_String(){
	    if(this.startTime_ != null){
	        return DateUtils.getDateTime(this.startTime_);
	    }
	    return "";
	}
	public Date getEndTime_(){
	    return this.endTime_;
	}
	public String getEndTime_String(){
	    if(this.endTime_ != null){
	        return DateUtils.getDateTime(this.endTime_);
	    }
	    return "";
	}
        public Integer getSucceed_(){
	    return this.succeed_;
	}
        public Long getSrcSuccessRows_(){
	    return this.srcSuccessRows_;
	}
        public Long getSrcFailedRows_(){
	    return this.srcFailedRows_;
	}
        public Long getTargetSuccessRows_(){
	    return this.targetSuccessRows_;
	}
        public Long getTargetFailedRows_(){
	    return this.targetFailedRows_;
	}


        public void setTaskId_(String taskId_) {
	    this.taskId_ = taskId_; 
	}
        public void setStartTime_(Date startTime_) {
	    this.startTime_ = startTime_; 
	}
        public void setEndTime_(Date endTime_) {
	    this.endTime_ = endTime_; 
	}
        public void setSucceed_(Integer succeed_) {
	    this.succeed_ = succeed_; 
	}
        public void setSrcSuccessRows_(Long srcSuccessRows_) {
	    this.srcSuccessRows_ = srcSuccessRows_; 
	}
        public void setSrcFailedRows_(Long srcFailedRows_) {
	    this.srcFailedRows_ = srcFailedRows_; 
	}
        public void setTargetSuccessRows_(Long targetSuccessRows_) {
	    this.targetSuccessRows_ = targetSuccessRows_; 
	}
        public void setTargetFailedRows_(Long targetFailedRows_) {
	    this.targetFailedRows_ = targetFailedRows_; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EtlTransferTaskInst other = (EtlTransferTaskInst) obj;
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


	public EtlTransferTaskInst jsonToObject(JSONObject jsonObject) {
            return EtlTransferTaskInstJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return EtlTransferTaskInstJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return EtlTransferTaskInstJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
