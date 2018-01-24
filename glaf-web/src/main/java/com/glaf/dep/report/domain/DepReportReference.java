package com.glaf.dep.report.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.report.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DEP_REPORT_REFERENCE")
public class DepReportReference implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * 报表查询数据集唯一标识
         */
        @Column(name = "REPDATASET_ID_")	 
        protected Long repDataSetId;

        /**
         * 报表录入唯一标识
         */
        @Column(name = "REPDATA_ID_")	 
        protected Long repDataId;

        /**
         * 引用类型
         */
        @Column(name = "REFTYPE_", length=30) 
        protected String refType;

        /**
         * 引用方式
         */
        @Column(name = "REFMODE_", length=20) 
        protected String refMode;

        /**
         * 规则启用条件
         */
        @Column(name = "ENCONDITON_", length=150) 
        protected String enCondition;

        /**
         * 引用物理字段字段名
         */
        @Column(name = "COLUMNNAME_", length=50) 
        protected String columnName;

        /**
         * 引用物理表名
         */
        @Column(name = "TABLENAME_", length=50) 
        protected String tableName;

        /**
         * 引用报表模板代码
         */
        @Column(name = "REPORT_ID_", length=50) 
        protected String reportId;

        /**
         * 引用报表元素代码
         */
        @Column(name = "REPORT_CELL_ID_", length=50) 
        protected String reportCellId;

        /**
         * 引用规则JSON
         */
        @Column(name = "RULEJSON_", length=255) 
        protected String ruleJson;

        /**
         * 创建人
         */
        @Column(name = "CREATOR_", length=20) 
        protected String creator;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATETIME_")	
        protected Date createDateTime;

        /**
         * 修改人
         */
        @Column(name = "MODIFIER_", length=20) 
        protected String modifier;

        /**
         * 修改时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "MODIFYDATETIME_")	
        protected Date modifyDateTime;


         
	public DepReportReference() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public Long getRepDataSetId(){
	    return this.repDataSetId;
	}
        public Long getRepDataId(){
	    return this.repDataId;
	}
        public String getRefType(){
	    return this.refType;
	}
        public String getRefMode(){
	    return this.refMode;
	}
        public String getEnCondition(){
	    return this.enCondition;
	}
        public String getColumnName(){
	    return this.columnName;
	}
        public String getTableName(){
	    return this.tableName;
	}
        public String getReportId(){
	    return this.reportId;
	}
        public String getReportCellId(){
	    return this.reportCellId;
	}
        public String getRuleJson(){
	    return this.ruleJson;
	}
        public String getCreator(){
	    return this.creator;
	}
	public Date getCreateDateTime(){
	    return this.createDateTime;
	}
	public String getCreateDateTimeString(){
	    if(this.createDateTime != null){
	        return DateUtils.getDateTime(this.createDateTime);
	    }
	    return "";
	}
        public String getModifier(){
	    return this.modifier;
	}
	public Date getModifyDateTime(){
	    return this.modifyDateTime;
	}
	public String getModifyDateTimeString(){
	    if(this.modifyDateTime != null){
	        return DateUtils.getDateTime(this.modifyDateTime);
	    }
	    return "";
	}


        public void setRepDataSetId(Long repDataSetId) {
	    this.repDataSetId = repDataSetId; 
	}
        public void setRepDataId(Long repDataId) {
	    this.repDataId = repDataId; 
	}
        public void setRefType(String refType) {
	    this.refType = refType; 
	}
        public void setRefMode(String refMode) {
	    this.refMode = refMode; 
	}
        public void setEnCondition(String enCondition) {
	    this.enCondition = enCondition; 
	}
        public void setColumnName(String columnName) {
	    this.columnName = columnName; 
	}
        public void setTableName(String tableName) {
	    this.tableName = tableName; 
	}
        public void setReportId(String reportId) {
	    this.reportId = reportId; 
	}
        public void setReportCellId(String reportCellId) {
	    this.reportCellId = reportCellId; 
	}
        public void setRuleJson(String ruleJson) {
	    this.ruleJson = ruleJson; 
	}
        public void setCreator(String creator) {
	    this.creator = creator; 
	}
        public void setCreateDateTime(Date createDateTime) {
	    this.createDateTime = createDateTime; 
	}
        public void setModifier(String modifier) {
	    this.modifier = modifier; 
	}
        public void setModifyDateTime(Date modifyDateTime) {
	    this.modifyDateTime = modifyDateTime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DepReportReference other = (DepReportReference) obj;
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


	public DepReportReference jsonToObject(JSONObject jsonObject) {
            return DepReportReferenceJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return DepReportReferenceJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return DepReportReferenceJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
