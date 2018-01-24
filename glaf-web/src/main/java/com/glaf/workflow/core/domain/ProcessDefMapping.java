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
@Table(name = "PROCESS_DEF_MAPPING")
public class ProcessDefMapping implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String iD;

        /**
         * sRCPROCDEFID
         */
        @Column(name = "SRC_PROC_DEF_ID_", length=50) 
        protected String srcProcDefId;

        /**
         * dESPROCDEFID
         */
        @Column(name = "DES_PROC_DEF_ID_", length=50) 
        protected String desProcDefId;

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
         * cREATDATETIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREAT_DATETIME_")	
        protected Date createDatetime;

        /**
         * cREATOR
         */
        @Column(name = "CREATOR_", length=30) 
        protected String creator;


         
	public ProcessDefMapping() {

	}

	public String getID(){
	    return this.iD;
	}

	public void setID(String iD) {
	    this.iD = iD; 
	}


        public String getSrcProcDefId(){
	    return this.srcProcDefId;
	}
        public String getDesProcDefId(){
	    return this.desProcDefId;
	}
        public String getSrcSysId(){
	    return this.srcSysId;
	}
        public String getDesSysId(){
	    return this.desSysId;
	}
	public Date getCreateDatetime(){
	    return this.createDatetime;
	}
	public String getCreateDatetimeString(){
	    if(this.createDatetime != null){
	        return DateUtils.getDateTime(this.createDatetime);
	    }
	    return "";
	}
        public String getCreator(){
	    return this.creator;
	}


        public void setSrcProcDefId(String srcProcDefId) {
	    this.srcProcDefId = srcProcDefId; 
	}
        public void setDesProcDefId(String desProcDefId) {
	    this.desProcDefId = desProcDefId; 
	}
        public void setSrcSysId(String srcSysId) {
	    this.srcSysId = srcSysId; 
	}
        public void setDesSysId(String desSysId) {
	    this.desSysId = desSysId; 
	}
        public void setCreateDatetime(Date createDatetime) {
	    this.createDatetime = createDatetime; 
	}
        public void setCreator(String creator) {
	    this.creator = creator; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProcessDefMapping other = (ProcessDefMapping) obj;
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


	public ProcessDefMapping jsonToObject(JSONObject jsonObject) {
            return ProcessDefMappingJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ProcessDefMappingJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ProcessDefMappingJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
