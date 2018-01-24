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
@Table(name = "CVT_ELEM_TMPL_FORML")
public class ConvertElemTmplForml implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "FORML_RULE_ID_", nullable = false)
        protected Long formlRuleId;

        /**
         * cvtElemId
         */
        @Column(name = "CVT_ELEM_ID_")	 
        protected Long cvtElemId;

        /**
         * formlName
         */
        @Column(name = "FORML_NAME_", length=200) 
        protected String formlName;

        /**
         * formlContent
         */
        @Column(name = "FORML_CONTENT_", length=200) 
        protected String formlContent;

        /**
         * cvtFormlContent
         */
        @Column(name = "CVT_FORML_CONTENT_", length=4000) 
        protected String cvtFormlContent;

        /**
         * useConditon
         */
        @Column(name = "USECONDITION_", length=100) 
        protected String useConditon;

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


         
	public ConvertElemTmplForml() {

	}

	public Long getFormlRuleId(){
	    return this.formlRuleId;
	}

	public void setFormlRuleId(Long formlRuleId) {
	    this.formlRuleId = formlRuleId; 
	}


        public Long getCvtElemId(){
	    return this.cvtElemId;
	}
        public String getFormlName(){
	    return this.formlName;
	}
        public String getFormlContent(){
	    return this.formlContent;
	}
        public String getCvtFormlContent(){
	    return this.cvtFormlContent;
	}
        public String getUseConditon(){
	    return this.useConditon;
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
        public void setFormlName(String formlName) {
	    this.formlName = formlName; 
	}
        public void setFormlContent(String formlContent) {
	    this.formlContent = formlContent; 
	}
        public void setCvtFormlContent(String cvtFormlContent) {
	    this.cvtFormlContent = cvtFormlContent; 
	}
        public void setUseConditon(String useConditon) {
	    this.useConditon = useConditon; 
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
		ConvertElemTmplForml other = (ConvertElemTmplForml) obj;
		if (formlRuleId == null) {
			if (other.formlRuleId != null)
				return false;
		} else if (!formlRuleId.equals(other.formlRuleId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((formlRuleId == null) ? 0 : formlRuleId.hashCode());
		return result;
	}


	public ConvertElemTmplForml jsonToObject(JSONObject jsonObject) {
            return ConvertElemTmplFormlJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ConvertElemTmplFormlJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ConvertElemTmplFormlJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
