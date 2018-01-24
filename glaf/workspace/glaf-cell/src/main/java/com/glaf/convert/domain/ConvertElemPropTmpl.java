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
@Table(name = "CVT_ELEMPROP_TMPL")
public class ConvertElemPropTmpl implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ELEMPROP_ID_", nullable = false)
        protected Long elemPropId;

        /**
         * cvtId
         */
        @Column(name = "CVT_ID_")	 
        protected Long cvtId;

        /**
         * rowIndex
         */
        @Column(name = "ROW_INDEX_")
        protected Integer rowIndex;

        /**
         * colIndex
         */
        @Column(name = "COL_INDEX_")
        protected Integer colIndex;

        /**
         * cELLPROPVAL
         */
        @Column(name = "CELL_PROP_VAL_", length=150) 
        protected String cellPropVal;

        /**
         * cellProp
         */
        @Column(name = "CELL_PROP_", length=30) 
        protected String cellProp;

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


         
	public ConvertElemPropTmpl() {

	}

	public Long getElemPropId(){
	    return this.elemPropId;
	}

	public void setElemPropId(Long elemPropId) {
	    this.elemPropId = elemPropId; 
	}


        public Long getCvtId(){
	    return this.cvtId;
	}
        public Integer getRowIndex(){
	    return this.rowIndex;
	}
        public Integer getColIndex(){
	    return this.colIndex;
	}
        public String getCellPropVal(){
	    return this.cellPropVal;
	}
        public String getCellProp(){
	    return this.cellProp;
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
        public void setRowIndex(Integer rowIndex) {
	    this.rowIndex = rowIndex; 
	}
        public void setColIndex(Integer colIndex) {
	    this.colIndex = colIndex; 
	}
        public void setCellPropVal(String cellPropVal) {
	    this.cellPropVal = cellPropVal; 
	}
        public void setCellProp(String cellProp) {
	    this.cellProp = cellProp; 
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
		ConvertElemPropTmpl other = (ConvertElemPropTmpl) obj;
		if (elemPropId == null) {
			if (other.elemPropId != null)
				return false;
		} else if (!elemPropId.equals(other.elemPropId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((elemPropId == null) ? 0 : elemPropId.hashCode());
		return result;
	}


	public ConvertElemPropTmpl jsonToObject(JSONObject jsonObject) {
            return ConvertElemPropTmplJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ConvertElemPropTmplJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ConvertElemPropTmplJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
