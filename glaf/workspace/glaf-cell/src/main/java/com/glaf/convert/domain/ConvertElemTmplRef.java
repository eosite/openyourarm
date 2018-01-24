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
@Table(name = "CVT_ELEM_TMPL_REF")
public class ConvertElemTmplRef implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "REF_RULE_ID_", nullable = false)
        protected Long refRuleId;

        /**
         * cvtElemId
         */
        @Column(name = "CVT_ELEM_ID_")	 
        protected Long cvtElemId;

        /**
         * refType
         */
        @Column(name = "REF_TYPE_", length=10) 
        protected String refType;

        /**
         * refContent
         */
        @Column(name = "REF_CONTENT_", length=4000) 
        protected String refContent;

        /**
         * refCondition
         */
        @Column(name = "REF_CONDITON_", length=150) 
        protected String refCondition;

        /**
         * refFieldId
         */
        @Column(name = "REF_FIELD_ID_", length=30) 
        protected String refFieldId;

        /**
         * useCondition
         */
        @Column(name = "USECONDITION_", length=150) 
        protected String useCondition;

        /**
         * transtionFlag
         */
        @Column(name = "TRANSTION_FLAG_", length=1) 
        protected String transtionFlag;

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


         
	public ConvertElemTmplRef() {

	}

	public Long getRefRuleId(){
	    return this.refRuleId;
	}

	public void setRefRuleId(Long refRuleId) {
	    this.refRuleId = refRuleId; 
	}


        public Long getCvtElemId(){
	    return this.cvtElemId;
	}
        public String getRefType(){
	    return this.refType;
	}
        public String getRefContent(){
	    return this.refContent;
	}
        public String getRefCondition(){
	    return this.refCondition;
	}
        public String getRefFieldId(){
	    return this.refFieldId;
	}
        public String getUseCondition(){
	    return this.useCondition;
	}
        public String getTranstionFlag(){
	    return this.transtionFlag;
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


        public void setCvtElemId(Long cvtElemId) {
	    this.cvtElemId = cvtElemId; 
	}
        public void setRefType(String refType) {
	    this.refType = refType; 
	}
        public void setRefContent(String refContent) {
	    this.refContent = refContent; 
	}
        public void setRefCondition(String refCondition) {
	    this.refCondition = refCondition; 
	}
        public void setRefFieldId(String refFieldId) {
	    this.refFieldId = refFieldId; 
	}
        public void setUseCondition(String useCondition) {
	    this.useCondition = useCondition; 
	}
        public void setTranstionFlag(String transtionFlag) {
	    this.transtionFlag = transtionFlag; 
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
		ConvertElemTmplRef other = (ConvertElemTmplRef) obj;
		if (refRuleId == null) {
			if (other.refRuleId != null)
				return false;
		} else if (!refRuleId.equals(other.refRuleId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((refRuleId == null) ? 0 : refRuleId.hashCode());
		return result;
	}


	public ConvertElemTmplRef jsonToObject(JSONObject jsonObject) {
            return ConvertElemTmplRefJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ConvertElemTmplRefJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ConvertElemTmplRefJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
