package com.glaf.workflow.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.workflow.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROCESS_INS_MAPPING")
public class ProcessInsMapping implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String iD;

        /**
         * sRCWBSDEFID
         */
        @Column(name = "SRC_WBS_DEF_ID_")
        protected Integer srcWbsDefId;

        /**
         * sRCWBSINSID
         */
        @Column(name = "SRC_WBS_INS_ID_")
        protected Integer srcWbsInsId;

        /**
         * sRCPROCDEFID
         */
        @Column(name = "SRC_PROC_DEF_ID_", length=50) 
        protected String srcProcDefId;

        /**
         * sRCPROCINSID
         */
        @Column(name = "SRC_PROC_INS_ID_", length=50) 
        protected String srcProcInsId;

        /**
         * dESWBSDEFID
         */
        @Column(name = "DES_WBS_DEF_ID_")
        protected Integer desWbsDefId;

        /**
         * dESWBSINSID
         */
        @Column(name = "DES_WBS_INS_ID_")
        protected Integer desWbsInsId;

        /**
         * dESPROCDEFID
         */
        @Column(name = "DES_PROC_DEF_ID_", length=50) 
        protected String desProcDefId;

        /**
         * dESPROCINSID
         */
        @Column(name = "DES_PROC_INS_ID_", length=50) 
        protected String desProcInsId;

        /**
         * sRCSYSID
         */
        @Column(name = "SRC_SYS_ID_", length=50) 
        protected String srcSysId;

        /**
         * dESSYSID
         */
        @Column(name = "DES_SYS_ID_", length=50) 
        protected String desSysId;

        /**
         * pROCSTATUS
         */
        @Column(name = "PROC_STATUS_")
        protected Integer procStatus;

        /**
         * pROCRESULT
         */
        @Column(name = "PROC_RESULT_")
        protected Integer procResult;

        /**
         * TRANSTYPE
         */
        @Column(name = "TRANSTYPE_")
        protected Integer transType;
        
        /**
         * EXTTRANSTYPE
         */
        @Column(name = "EXT_TRANSTYPE_")
        protected Integer extTransType;

        /**
         * REPLYFLAG
         */
        @Column(name = "REPLYFLAG_")
        protected Integer ReplyFlag;

        /**
         * pROCSTARTTIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "PROC_STARTTIME_")	
        protected Date procStartTime;

        /**
         * pROCCOMPTIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "PROC_COMPTIME_")	
        protected Date procCompTime;

        @Column(name = "PROCESS_INS_NAME_")	        
        protected String processInsName;
         
	public ProcessInsMapping() {

	}

	public String getID(){
	    return this.iD;
	}

	public void setID(String iD) {
	    this.iD = iD; 
	}


        public Integer getSrcWbsDefId(){
	    return this.srcWbsDefId;
	}
        public Integer getSrcWbsInsId(){
	    return this.srcWbsInsId;
	}
        public String getSrcProcDefId(){
	    return this.srcProcDefId;
	}
        public String getSrcProcInsId(){
	    return this.srcProcInsId;
	}
        public Integer getDesWbsDefId(){
	    return this.desWbsDefId;
	}
        public Integer getDesWbsInsId(){
	    return this.desWbsInsId;
	}
        public String getDesProcDefId(){
	    return this.desProcDefId;
	}
        public String getDesProcInsId(){
	    return this.desProcInsId;
	}
        public String getSrcSysId(){
	    return this.srcSysId;
	}
        public String getDesSysId(){
	    return this.desSysId;
	}
        public Integer getProcStatus(){
	    return this.procStatus;
	}
        public Integer getProcResult(){
	    return this.procResult;
	}
        public Integer getTransType(){
	    return this.transType;
	}
        public Integer getReplyFlag(){
	    return this.ReplyFlag;
	}
	public Date getProcStartTime(){
	    return this.procStartTime;
	}
	public String getProcStartTimeString(){
	    if(this.procStartTime != null){
	        return DateUtils.getDateTime(this.procStartTime);
	    }
	    return "";
	}
	public Date getProcCompTime(){
	    return this.procCompTime;
	}
	public String getProcCompTimeString(){
	    if(this.procCompTime != null){
	        return DateUtils.getDateTime(this.procCompTime);
	    }
	    return "";
	}


        public void setSrcWbsDefId(Integer srcWbsDefId) {
	    this.srcWbsDefId = srcWbsDefId; 
	}
        public void setSrcWbsInsId(Integer srcWbsInsId) {
	    this.srcWbsInsId = srcWbsInsId; 
	}
        public void setSrcProcDefId(String srcProcDefId) {
	    this.srcProcDefId = srcProcDefId; 
	}
        public void setSrcProcInsId(String srcProcInsId) {
	    this.srcProcInsId = srcProcInsId; 
	}
        public void setDesWbsDefId(Integer desWbsDefId) {
	    this.desWbsDefId = desWbsDefId; 
	}
        public void setDesWbsInsId(Integer desWbsInsId) {
	    this.desWbsInsId = desWbsInsId; 
	}
        public void setDesProcDefId(String desProcDefId) {
	    this.desProcDefId = desProcDefId; 
	}
        public void setDesProcInsId(String desProcInsId) {
	    this.desProcInsId = desProcInsId; 
	}
        public void setSrcSysId(String srcSysId) {
	    this.srcSysId = srcSysId; 
	}
        public void setDesSysId(String desSysId) {
	    this.desSysId = desSysId; 
	}
        public void setProcStatus(Integer procStatus) {
	    this.procStatus = procStatus; 
	}
        public void setProcResult(Integer procResult) {
	    this.procResult = procResult; 
	}
        public void setTransType(Integer transType) {
	    this.transType = transType; 
	}
        public void setReplyFlag(Integer ReplyFlag) {
	    this.ReplyFlag = ReplyFlag; 
	}
        public void setProcStartTime(Date procStartTime) {
	    this.procStartTime = procStartTime; 
	}
        public void setProcCompTime(Date procCompTime) {
	    this.procCompTime = procCompTime; 
	}


	public String getProcessInsName() {
			return processInsName;
		}

		public void setProcessInsName(String processInsName) {
			this.processInsName = processInsName;
		}

	public Integer getExtTransType() {
			return extTransType;
		}

		public void setExtTransType(Integer extTransType) {
			this.extTransType = extTransType;
		}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessInsMapping other = (ProcessInsMapping) obj;
		if (iD == null) {
			if (other.iD != null)
				return false;
		} else if (!iD.equals(other.iD))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((iD == null) ? 0 : iD.hashCode());
		return result;
	}


	public ProcessInsMapping jsonToObject(JSONObject jsonObject) {
            return ProcessInsMappingJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ProcessInsMappingJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ProcessInsMappingJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
