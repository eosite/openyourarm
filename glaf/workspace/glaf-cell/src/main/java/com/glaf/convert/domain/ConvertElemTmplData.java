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
@Table(name = "CVT_ELEM_TMPL_DATA")
public class ConvertElemTmplData implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "DATA_RULE_ID_", nullable = false)
        protected Long dataRuleId;

        /**
         * cvtElemId
         */
        @Column(name = "CVT_ELEM_ID_")	 
        protected Long cvtElemId;

        /**
         * tableName
         */
        @Column(name = "TABLE_NAME_", length=30) 
        protected String tableName;

        /**
         * fieldName
         */
        @Column(name = "FIELD_NAME_", length=30) 
        protected String fieldName;

        /**
         * dataTableId
         */
        @Column(name = "DATA_TABLE_ID_", length=50) 
        protected String dataTableId;

        /**
         * subTable
         */
        @Column(name = "SUB_TABLE_", length=1) 
        protected String subTable;

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


         
	public ConvertElemTmplData() {

	}

	public Long getDataRuleId(){
	    return this.dataRuleId;
	}

	public void setDataRuleId(Long dataRuleId) {
	    this.dataRuleId = dataRuleId; 
	}


        public Long getCvtElemId(){
	    return this.cvtElemId;
	}
        public String getTableName(){
	    return this.tableName;
	}
        public String getFieldName(){
	    return this.fieldName;
	}
        public String getDataTableId(){
	    return this.dataTableId;
	}
        public String getSubTable(){
	    return this.subTable;
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
        public void setTableName(String tableName) {
	    this.tableName = tableName; 
	}
        public void setFieldName(String fieldName) {
	    this.fieldName = fieldName; 
	}
        public void setDataTableId(String dataTableId) {
	    this.dataTableId = dataTableId; 
	}
        public void setSubTable(String subTable) {
	    this.subTable = subTable; 
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
		ConvertElemTmplData other = (ConvertElemTmplData) obj;
		if (dataRuleId == null) {
			if (other.dataRuleId != null)
				return false;
		} else if (!dataRuleId.equals(other.dataRuleId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((dataRuleId == null) ? 0 : dataRuleId.hashCode());
		return result;
	}


	public ConvertElemTmplData jsonToObject(JSONObject jsonObject) {
            return ConvertElemTmplDataJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ConvertElemTmplDataJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ConvertElemTmplDataJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
