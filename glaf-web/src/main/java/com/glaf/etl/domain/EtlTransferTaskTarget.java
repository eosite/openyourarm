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
@Table(name = "ETL_TRANSFER_TASK_TARGET_")
public class EtlTransferTaskTarget implements Serializable, JSONable {
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
         * targetId
         */
        @Column(name = "TARGET_ID_", length=64) 
        protected String targetId_;

        /**
         * preSQL
         */
        @Column(name = "PRE_SQL_")  
        protected byte[] preSQL_;

        /**
         * postSQL
         */
        @Column(name = "POST_SQL_")  
        protected byte[] postSQL_;

        /**
         * taskConnId
         */
        @Column(name = "TASK_CONN_ID_")	 
        protected Long taskConnId_;

        /**
         * taskDatabaseName
         */
        @Column(name = "TASK_DATABASENAME_", length=30) 
        protected String taskDatabaseName_;

        /**
         * taskTableName
         */
        @Column(name = "TASK_TABLENAME_", length=50) 
        protected String taskTableName_;

        /**
         * tableNamePrefix
         */
        @Column(name = "TABLENAME_PREFIX_", length=20) 
        protected String tableNamePrefix_;

        /**
         * preTuncateFlag
         */
        @Column(name = "PRE_TRUNCATE_FLAG_")
        protected Integer preTuncateFlag_;

        /**
         * treatment_methd
         */
        @Column(name = "TREATMENT_METHD_")
        protected Integer treatment_methd_;

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


         
	public EtlTransferTaskTarget() {

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
        public String getTargetId_(){
	    return this.targetId_;
	}
        public byte[] getPreSQL_(){
	    return this.preSQL_;
	}
        public byte[] getPostSQL_(){
	    return this.postSQL_;
	}
        public Long getTaskConnId_(){
	    return this.taskConnId_;
	}
        public String getTaskDatabaseName_(){
	    return this.taskDatabaseName_;
	}
        public String getTaskTableName_(){
	    return this.taskTableName_;
	}
        public String getTableNamePrefix_(){
	    return this.tableNamePrefix_;
	}
        public Integer getPreTuncateFlag_(){
	    return this.preTuncateFlag_;
	}
        public Integer getTreatment_methd_(){
	    return this.treatment_methd_;
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


        public void setTaskId_(String taskId_) {
	    this.taskId_ = taskId_; 
	}
        public void setTargetId_(String targetId_) {
	    this.targetId_ = targetId_; 
	}
        public void setPreSQL_(byte[] preSQL_) {
	    this.preSQL_ = preSQL_; 
	}
        public void setPostSQL_(byte[] postSQL_) {
	    this.postSQL_ = postSQL_; 
	}
        public void setTaskConnId_(Long taskConnId_) {
	    this.taskConnId_ = taskConnId_; 
	}
        public void setTaskDatabaseName_(String taskDatabaseName_) {
	    this.taskDatabaseName_ = taskDatabaseName_; 
	}
        public void setTaskTableName_(String taskTableName_) {
	    this.taskTableName_ = taskTableName_; 
	}
        public void setTableNamePrefix_(String tableNamePrefix_) {
	    this.tableNamePrefix_ = tableNamePrefix_; 
	}
        public void setPreTuncateFlag_(Integer preTuncateFlag_) {
	    this.preTuncateFlag_ = preTuncateFlag_; 
	}
        public void setTreatment_methd_(Integer treatment_methd_) {
	    this.treatment_methd_ = treatment_methd_; 
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


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EtlTransferTaskTarget other = (EtlTransferTaskTarget) obj;
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


	public EtlTransferTaskTarget jsonToObject(JSONObject jsonObject) {
            return EtlTransferTaskTargetJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return EtlTransferTaskTargetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return EtlTransferTaskTargetJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
