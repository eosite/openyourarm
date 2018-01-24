package com.glaf.report.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.report.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "REPORT_TMP_COL_MAPPING")
public class ReportTmpColMapping implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * DATASET_MAPPING_ID_
         */
        @Column(name = "DATASET_MAPPING_ID_", length=64) 
        protected String dataSetMappingId;

        /**
         * COL_CODE_
         */
        @Column(name = "COL_CODE_", length=50) 
        protected String colCode;

        /**
         * COL_NAME_
         */
        @Column(name = "COL_NAME_", length=100) 
        protected String colName;

        /**
         * COL_TITLE_
         */
        @Column(name = "COL_TITLE_", length=100) 
        protected String colTitle;

        /**
         * COL_DTYPE_
         */
        @Column(name = "COL_DTYPE_", length=20) 
        protected String colDtype;

        /**
         * COL_MAPPING_CODE_
         */
        @Column(name = "COL_MAPPING_CODE_", length=50) 
        protected String colMappingCode;

        /**
         * COL_MAPPING_NAME_
         */
        @Column(name = "COL_MAPPING_NAME_", length=100) 
        protected String colMappingName;

        /**
         * COL_MAPPING_TITLE_
         */
        @Column(name = "COL_MAPPING_TITLE_", length=100) 
        protected String colMappingTitle;

        /**
         * COL_MAPPING_DTYPE_
         */
        @Column(name = "COL_MAPPING_DTYPE_", length=20) 
        protected String colMappingDtype;

        /**
         * CREATOR_
         */
        @Column(name = "CREATOR_", length=20) 
        protected String creator;

        /**
         * CREATEDATETIME_
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATETIME_")	
        protected Date createDatetime;

        /**
         * MODIFIER_
         */
        @Column(name = "MODIFIER_", length=20) 
        protected String modifier;

        /**
         * MODIFYDATETIME_
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "MODIFYDATETIME_")	
        protected Date modifyDatetime;
        
        
        protected String dataSetCode;


         
	public ReportTmpColMapping() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getDataSetMappingId(){
	    return this.dataSetMappingId;
	}
        public String getColCode(){
	    return this.colCode;
	}
        public String getColName(){
	    return this.colName;
	}
        public String getColTitle(){
	    return this.colTitle;
	}
        public String getColDtype(){
	    return this.colDtype;
	}
        public String getColMappingCode(){
	    return this.colMappingCode;
	}
        public String getColMappingName(){
	    return this.colMappingName;
	}
        public String getColMappingTitle(){
	    return this.colMappingTitle;
	}
        public String getColMappingDtype(){
	    return this.colMappingDtype;
	}
        public String getCreator(){
	    return this.creator;
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
        public String getModifier(){
	    return this.modifier;
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


        public void setDataSetMappingId(String dataSetMappingId) {
	    this.dataSetMappingId = dataSetMappingId; 
	}
        public void setColCode(String colCode) {
	    this.colCode = colCode; 
	}
        public void setColName(String colName) {
	    this.colName = colName; 
	}
        public void setColTitle(String colTitle) {
	    this.colTitle = colTitle; 
	}
        public void setColDtype(String colDtype) {
	    this.colDtype = colDtype; 
	}
        public void setColMappingCode(String colMappingCode) {
	    this.colMappingCode = colMappingCode; 
	}
        public void setColMappingName(String colMappingName) {
	    this.colMappingName = colMappingName; 
	}
        public void setColMappingTitle(String colMappingTitle) {
	    this.colMappingTitle = colMappingTitle; 
	}
        public void setColMappingDtype(String colMappingDtype) {
	    this.colMappingDtype = colMappingDtype; 
	}
        public void setCreator(String creator) {
	    this.creator = creator; 
	}
        public void setCreateDatetime(Date createDatetime) {
	    this.createDatetime = createDatetime; 
	}
        public void setModifier(String modifier) {
	    this.modifier = modifier; 
	}
        public void setModifyDatetime(Date modifyDatetime) {
	    this.modifyDatetime = modifyDatetime; 
	}


	public String getDataSetCode() {
			return dataSetCode;
		}

		public void setDataSetCode(String dataSetCode) {
			this.dataSetCode = dataSetCode;
		}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportTmpColMapping other = (ReportTmpColMapping) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((id == null) ? 0 : id.hashCode());
		return result;
	}


	public ReportTmpColMapping jsonToObject(JSONObject jsonObject) {
            return ReportTmpColMappingJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ReportTmpColMappingJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ReportTmpColMappingJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
