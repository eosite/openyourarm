package com.glaf.model.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.model.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYSTEM_PROCDEF_")
public class SystemProcDef implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * fUNCID
         */
        @Column(name = "FUNC_ID_", length=50)	 
        protected String funcId;

        /**
         * sysId
         */
        @Column(name = "SYS_ID_", length=50)	 
        protected String sysId;

        /**
         * subSysId
         */
        @Column(name = "SUB_SYS_ID_", length=50)	 
        protected String subSysId;

        /**
         * currProcDefKey
         */
        @Column(name = "CURR_PROCDEF_KEY_", length=255) 
        protected String currProcDefKey;

        /**
         * currProcDefId
         */
        @Column(name = "CURR_PROCDEF_ID_", length=64) 
        protected String currProcDefId;

        /**
         * currProcModelId
         */
        @Column(name = "CURR_PROCMODEL_ID_", length=64) 
        protected String currProcModelId;

        /**
         * currProcDeployId
         */
        @Column(name = "CURR_PROCDEPLOY_ID_", length=64) 
        protected String currProcDeployId;

        /**
         * currProcDeployStatus
         */
        @Column(name = "CURR_PROCDEPLOY_STATUS_", length=1) 
        protected String currProcDeployStatus;

        /**
         * procDefKey
         */
        @Column(name = "PROCDEF_KEY_", length=255) 
        protected String procDefKey;

        /**
         * procDefId
         */
        @Column(name = "PROCDEF_ID_", length=64) 
        protected String procDefId;

        /**
         * procModelId
         */
        @Column(name = "PROCMODEL_ID_", length=64) 
        protected String procModelId;

        /**
         * procDeployId
         */
        @Column(name = "PROCDEPLOY_ID_", length=64) 
        protected String procDeployId;

        /**
         * procDeployStatus
         */
        @Column(name = "PROCDEPLOY_STATUS_", length=1) 
        protected String procDeployStatus;

        /**
         * eleType
         */
        @Column(name = "ELE_TYPE_", length=30) 
        protected String eleType;

        /**
         * eleResourceId
         */
        @Column(name = "ELE_RESOURCE_ID_", length=50) 
        protected String eleResourceId;

        /**
         * eleId
         */
        @Column(name = "ELE_ID_", length=50) 
        protected String eleId;

        /**
         * eleName
         */
        @Column(name = "ELE_NAME_", length=100) 
        protected String eleName;

        /**
         * eleDesc
         */
        @Column(name = "ELE_DESC", length=150) 
        protected String eleDesc;

        /**
         * createBy
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createBy;

        /**
         * createTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * updateBy
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateBy;

        /**
         * updateTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;

        /**
         * deleteFlag
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag;


         
	public SystemProcDef() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getFuncId(){
	    return this.funcId;
	}
        public String getSysId(){
	    return this.sysId;
	}
        public String getSubSysId(){
	    return this.subSysId;
	}
        public String getCurrProcDefKey(){
	    return this.currProcDefKey;
	}
        public String getCurrProcDefId(){
	    return this.currProcDefId;
	}
        public String getCurrProcModelId(){
	    return this.currProcModelId;
	}
        public String getCurrProcDeployId(){
	    return this.currProcDeployId;
	}
        public String getCurrProcDeployStatus(){
	    return this.currProcDeployStatus;
	}
        public String getProcDefKey(){
	    return this.procDefKey;
	}
        public String getProcDefId(){
	    return this.procDefId;
	}
        public String getProcModelId(){
	    return this.procModelId;
	}
        public String getProcDeployId(){
	    return this.procDeployId;
	}
        public String getProcDeployStatus(){
	    return this.procDeployStatus;
	}
        public String getEleType(){
	    return this.eleType;
	}
        public String getEleResourceId(){
	    return this.eleResourceId;
	}
        public String getEleId(){
	    return this.eleId;
	}
        public String getEleName(){
	    return this.eleName;
	}
        public String getEleDesc(){
	    return this.eleDesc;
	}
        public String getCreateBy(){
	    return this.createBy;
	}
	public Date getCreateTime(){
	    return this.createTime;
	}
	public String getCreateTimeString(){
	    if(this.createTime != null){
	        return DateUtils.getDateTime(this.createTime);
	    }
	    return "";
	}
        public String getUpdateBy(){
	    return this.updateBy;
	}
	public Date getUpdateTime(){
	    return this.updateTime;
	}
	public String getUpdateTimeString(){
	    if(this.updateTime != null){
	        return DateUtils.getDateTime(this.updateTime);
	    }
	    return "";
	}
        public Integer getDeleteFlag(){
	    return this.deleteFlag;
	}


        public void setFuncId(String funcId) {
	    this.funcId = funcId; 
	}
        public void setSysId(String sysId) {
	    this.sysId = sysId; 
	}
        public void setSubSysId(String subSysId) {
	    this.subSysId = subSysId; 
	}
        public void setCurrProcDefKey(String currProcDefKey) {
	    this.currProcDefKey = currProcDefKey; 
	}
        public void setCurrProcDefId(String currProcDefId) {
	    this.currProcDefId = currProcDefId; 
	}
        public void setCurrProcModelId(String currProcModelId) {
	    this.currProcModelId = currProcModelId; 
	}
        public void setCurrProcDeployId(String currProcDeployId) {
	    this.currProcDeployId = currProcDeployId; 
	}
        public void setCurrProcDeployStatus(String currProcDeployStatus) {
	    this.currProcDeployStatus = currProcDeployStatus; 
	}
        public void setProcDefKey(String procDefKey) {
	    this.procDefKey = procDefKey; 
	}
        public void setProcDefId(String procDefId) {
	    this.procDefId = procDefId; 
	}
        public void setProcModelId(String procModelId) {
	    this.procModelId = procModelId; 
	}
        public void setProcDeployId(String procDeployId) {
	    this.procDeployId = procDeployId; 
	}
        public void setProcDeployStatus(String procDeployStatus) {
	    this.procDeployStatus = procDeployStatus; 
	}
        public void setEleType(String eleType) {
	    this.eleType = eleType; 
	}
        public void setEleResourceId(String eleResourceId) {
	    this.eleResourceId = eleResourceId; 
	}
        public void setEleId(String eleId) {
	    this.eleId = eleId; 
	}
        public void setEleName(String eleName) {
	    this.eleName = eleName; 
	}
        public void setEleDesc(String eleDesc) {
	    this.eleDesc = eleDesc; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setCreateTime(Date createTime) {
	    this.createTime = createTime; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}
        public void setUpdateTime(Date updateTime) {
	    this.updateTime = updateTime; 
	}
        public void setDeleteFlag(Integer deleteFlag) {
	    this.deleteFlag = deleteFlag; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemProcDef other = (SystemProcDef) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}


	public SystemProcDef jsonToObject(JSONObject jsonObject) {
            return SystemProcDefJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return SystemProcDefJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SystemProcDefJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
