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
@Table(name = "CVT_PAGE_PARAM")
public class ConvertPageParam implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "CVT_PARAM_ID_", nullable = false)
        protected Long cvtParamId;

        /**
         * cvtId
         */
        @Column(name = "CVT_ID_")	 
        protected Long cvtId;

        /**
         * paramName
         */
        @Column(name = "PARAM_NAME_", length=50) 
        protected String paramName;

        /**
         * paramCode
         */
        @Column(name = "PARAM_CODE_", length=30) 
        protected String paramCode;

        /**
         * paramType
         */
        @Column(name = "PARAM_TYPE_", length=10) 
        protected String paramType;

        /**
         * createDatetime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATE_DATETIME_")	
        protected Date createDatetime;

        /**
         * modifyDatetime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "MODIFY_DATETIME_")	
        protected Date modifyDatetime;


         
	public ConvertPageParam() {

	}

	public Long getCvtParamId(){
	    return this.cvtParamId;
	}

	public void setCvtParamId(Long cvtParamId) {
	    this.cvtParamId = cvtParamId; 
	}


        public Long getCvtId(){
	    return this.cvtId;
	}
        public String getParamName(){
	    return this.paramName;
	}
        public String getParamCode(){
	    return this.paramCode;
	}
        public String getParamType(){
	    return this.paramType;
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
	public Date getModifyDatetime(){
	    return this.modifyDatetime;
	}
	public String getModifyDatetimeString(){
	    if(this.modifyDatetime != null){
	        return DateUtils.getDateTime(this.modifyDatetime);
	    }
	    return "";
	}


        public void setCvtId(Long cvtId) {
	    this.cvtId = cvtId; 
	}
        public void setParamName(String paramName) {
	    this.paramName = paramName; 
	}
        public void setParamCode(String paramCode) {
	    this.paramCode = paramCode; 
	}
        public void setParamType(String paramType) {
	    this.paramType = paramType; 
	}
        public void setCreateDatetime(Date createDatetime) {
	    this.createDatetime = createDatetime; 
	}
        public void setModifyDatetime(Date modifyDatetime) {
	    this.modifyDatetime = modifyDatetime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvertPageParam other = (ConvertPageParam) obj;
		if (cvtParamId == null) {
			if (other.cvtParamId != null)
				return false;
		} else if (!cvtParamId.equals(other.cvtParamId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cvtParamId == null) ? 0 : cvtParamId.hashCode());
		return result;
	}


	public ConvertPageParam jsonToObject(JSONObject jsonObject) {
            return ConvertPageParamJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ConvertPageParamJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ConvertPageParamJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
