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
@Table(name = "SYSTEM_FUNC_DATA_OBJ_")
public class SystemFuncDataObj implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "SYS_DATA_OBJ_ID_", length=50, nullable = false)
        protected String sysDataObjId;

        /**
         * fUNCID
         */
        @Column(name = "FUNC_ID_", length=50)	 
        protected String funcId;

        /**
         * dATAOBJID
         */
        @Column(name = "DATA_OBJ_ID_", length=50)	 
        protected String dataObjId;

        /**
         * tYPE
         */
        @Column(name = "TYPE_")  
        protected int type;

        /**
         * cREATEBY
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createBy;

        /**
         * cREATETIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * uPDATEBY
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateBy;

        /**
         * uPDATETIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;

        /**
         * dELETEFLAG
         */
        @Column(name = "DELETE_FLAG_")  
        protected int deleteFlag;


         
	public SystemFuncDataObj() {

	}

	public String getSysDataObjId(){
	    return this.sysDataObjId;
	}

	public void setSysDataObjId(String sysDataObjId) {
	    this.sysDataObjId = sysDataObjId; 
	}


        public String getFuncId(){
	    return this.funcId;
	}
        public String getDataObjId(){
	    return this.dataObjId;
	}
        public int getType(){
	    return this.type;
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
        public int getDeleteFlag(){
	    return this.deleteFlag;
	}


        public void setFuncId(String funcId) {
	    this.funcId = funcId; 
	}
        public void setDataObjId(String dataObjId) {
	    this.dataObjId = dataObjId; 
	}
        public void setType(int type) {
	    this.type = type; 
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
        public void setDeleteFlag(int deleteFlag) {
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
		SystemFuncDataObj other = (SystemFuncDataObj) obj;
		if (sysDataObjId == null) {
			if (other.sysDataObjId != null)
				return false;
		} else if (!sysDataObjId.equals(other.sysDataObjId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((sysDataObjId == null) ? 0 : sysDataObjId.hashCode());
		return result;
	}


	public SystemFuncDataObj jsonToObject(JSONObject jsonObject) {
            return SystemFuncDataObjJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return SystemFuncDataObjJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SystemFuncDataObjJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
