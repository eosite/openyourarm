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
@Table(name = "CVT_ELEM_TMPL_VALID")
public class ConvertElemTmplValid implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "VALID_RULE_ID_", nullable = false)
        protected Long validRuleId;

        /**
         * cvtElemId
         */
        @Column(name = "CVT_ELEM_ID_")	 
        protected Long cvtElemId;

        /**
         * validType
         */
        @Column(name = "VALID_TYPE_", length=10) 
        protected String validType;

        /**
         * expression
         */
        @Column(name = "EXPRESSION_", length=150) 
        protected String expression;

        /**
         * dType
         */
        @Column(name = "DTYPE_", length=50) 
        protected String dType;

        /**
         * len
         */
        @Column(name = "LEN_")
        protected Integer len;

        /**
         * rangeUpper
         */
        @Column(name = "RANGE_UPPER_", length=30) 
        protected String rangeUpper;

        /**
         * rangeLower
         */
        @Column(name = "RANGE_LOWER_", length=30) 
        protected String rangeLower;

        /**
         * useCondition
         */
        @Column(name = "USECONDITION_", length=100) 
        protected String useCondition;

        /**
         * seq
         */
        @Column(name = "SEQ_")
        protected Integer seq;

        /**
         * parentRuleId
         */
        @Column(name = "PARENT_RULE_ID_")	 
        protected Long parentRuleId;

        /**
         * treeId
         */
        @Column(name = "TREEID_", length=100) 
        protected String treeId;

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


         
	public ConvertElemTmplValid() {

	}

	public Long getValidRuleId(){
	    return this.validRuleId;
	}

	public void setValidRuleId(Long validRuleId) {
	    this.validRuleId = validRuleId; 
	}


        public Long getCvtElemId(){
	    return this.cvtElemId;
	}
        public String getValidType(){
	    return this.validType;
	}
        public String getExpression(){
	    return this.expression;
	}
        public String getDType(){
	    return this.dType;
	}
        public Integer getLen(){
	    return this.len;
	}
        public String getRangeUpper(){
	    return this.rangeUpper;
	}
        public String getRangeLower(){
	    return this.rangeLower;
	}
        public String getUseCondition(){
	    return this.useCondition;
	}
        public Integer getSeq(){
	    return this.seq;
	}
        public Long getParentRuleId(){
	    return this.parentRuleId;
	}
        public String getTreeId(){
	    return this.treeId;
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
        public void setValidType(String validType) {
	    this.validType = validType; 
	}
        public void setExpression(String expression) {
	    this.expression = expression; 
	}
        public void setDType(String dType) {
	    this.dType = dType; 
	}
        public void setLen(Integer len) {
	    this.len = len; 
	}
        public void setRangeUpper(String rangeUpper) {
	    this.rangeUpper = rangeUpper; 
	}
        public void setRangeLower(String rangeLower) {
	    this.rangeLower = rangeLower; 
	}
        public void setUseCondition(String useCondition) {
	    this.useCondition = useCondition; 
	}
        public void setSeq(Integer seq) {
	    this.seq = seq; 
	}
        public void setParentRuleId(Long parentRuleId) {
	    this.parentRuleId = parentRuleId; 
	}
        public void setTreeId(String treeId) {
	    this.treeId = treeId; 
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
		ConvertElemTmplValid other = (ConvertElemTmplValid) obj;
		if (validRuleId == null) {
			if (other.validRuleId != null)
				return false;
		} else if (!validRuleId.equals(other.validRuleId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((validRuleId == null) ? 0 : validRuleId.hashCode());
		return result;
	}


	public ConvertElemTmplValid jsonToObject(JSONObject jsonObject) {
            return ConvertElemTmplValidJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ConvertElemTmplValidJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ConvertElemTmplValidJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
