package com.glaf.convert.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.convert.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CVT_RUNTIME")
public class CvtRuntime implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "TASKCODE_", nullable = false)
        protected String taskCode;

        /**
         * tASKNAME
         */
        @Column(name = "TASKNAME_", length=50) 
        protected String taskName;

        /**
         * rUNTIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "RUNTIME_")	
        protected Date runTime;

        /**
         * rUNFLAG
         */
        @Column(name = "RUNFLAG_")
        protected Integer runFlag;

        /**
         * sUCCCOUNT
         */
        @Column(name = "SUCCCOUNT_")
        protected Integer succCount;

        /**
         * fAILCOUNT
         */
        @Column(name = "FAILCOUNT_")
        protected Integer failCount;

        /**
         * lOG
         */
        @Lob
        @Column(name = "LOG_") 
        protected byte[] log;


         
	public CvtRuntime() {

	}

	public String getTaskCode(){
	    return this.taskCode;
	}

	public void setTaskCode(String taskCode) {
	    this.taskCode = taskCode; 
	}


        public String getTaskName(){
	    return this.taskName;
	}
	public Date getRunTime(){
	    return this.runTime;
	}
	public String getRunTimeString(){
	    if(this.runTime != null){
	        return DateUtils.getDateTime(this.runTime);
	    }
	    return "";
	}
        public Integer getRunFlag(){
	    return this.runFlag;
	}
        public Integer getSuccCount(){
	    return this.succCount;
	}
        public Integer getFailCount(){
	    return this.failCount;
	}
        public byte[] getLog(){
	    return this.log;
	}


        public void setTaskName(String taskName) {
	    this.taskName = taskName; 
	}
        public void setRunTime(Date runTime) {
	    this.runTime = runTime; 
	}
        public void setRunFlag(Integer runFlag) {
	    this.runFlag = runFlag; 
	}
        public void setSuccCount(Integer succCount) {
	    this.succCount = succCount; 
	}
        public void setFailCount(Integer failCount) {
	    this.failCount = failCount; 
	}
        public void setLog(byte[] log) {
	    this.log = log; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CvtRuntime other = (CvtRuntime) obj;
		if (taskCode == null) {
			if (other.taskCode != null)
				return false;
		} else if (!taskCode.equals(other.taskCode))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((taskCode == null) ? 0 : taskCode.hashCode());
		return result;
	}


	public CvtRuntime jsonToObject(JSONObject jsonObject) {
            return CvtRuntimeJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return CvtRuntimeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return CvtRuntimeJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
