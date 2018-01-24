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
@Table(name = "SYSTEM_DEF_")
public class SystemDef implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "SYS_ID_",length=50, nullable = false)
        protected String sysId;

        /**
         * sysName
         */
        @Column(name = "SYS_NAME_", length=150) 
        protected String sysName;

        /**
         * sysCode
         */
        @Column(name = "SYS_CODE_", length=50) 
        protected String sysCode;

        /**
         * sysDesc
         */
        @Column(name = "SYS_DESC_", length=500) 
        protected String sysDesc;

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

        /**
         * version
         */
        @Column(name = "VERSION_", length=30) 
        protected String version;

        /**
         * publisher
         */
        @Column(name = "PUBLISHER_", length=30) 
        protected String publisher;

        /**
         * publishTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "PUBLISHTIME_")	
        protected Date publishTime;


         
	public SystemDef() {

	}

	public String getSysId(){
	    return this.sysId;
	}

	public void setSysId(String sysId) {
	    this.sysId = sysId; 
	}


        public String getSysName(){
	    return this.sysName;
	}
        public String getSysCode(){
	    return this.sysCode;
	}
        public String getSysDesc(){
	    return this.sysDesc;
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
        public String getVersion(){
	    return this.version;
	}
        public String getPublisher(){
	    return this.publisher;
	}
	public Date getPublishTime(){
	    return this.publishTime;
	}
	public String getPublishTimeString(){
	    if(this.publishTime != null){
	        return DateUtils.getDateTime(this.publishTime);
	    }
	    return "";
	}


        public void setSysName(String sysName) {
	    this.sysName = sysName; 
	}
        public void setSysCode(String sysCode) {
	    this.sysCode = sysCode; 
	}
        public void setSysDesc(String sysDesc) {
	    this.sysDesc = sysDesc; 
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
        public void setVersion(String version) {
	    this.version = version; 
	}
        public void setPublisher(String publisher) {
	    this.publisher = publisher; 
	}
        public void setPublishTime(Date publishTime) {
	    this.publishTime = publishTime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemDef other = (SystemDef) obj;
		if (sysId == null) {
			if (other.sysId != null)
				return false;
		} else if (!sysId.equals(other.sysId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sysId == null) ? 0 : sysId.hashCode());
		return result;
	}


	public SystemDef jsonToObject(JSONObject jsonObject) {
            return SystemDefJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return SystemDefJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SystemDefJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
