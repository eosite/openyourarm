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
@Table(name = "DEP_REPORT_CELLDATA")
public class DepReportCellData implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * 报表元素唯一标识
         */
        @Column(name = "CELL_ID_")	 
        protected Long cellId;

        /**
         * 录入方式
         */
        @Column(name = "INPUTMODE_", length=50) 
        protected String inputMode;

        /**
         * 只读
         */
        @Column(name = "READONLY_", length=1) 
        protected String readOnly;

        /**
         * 数据类型
         */
        @Column(name = "DTYPE_", length=20) 
        protected String dtype;

        /**
         * 默认值
         */
        @Column(name = "DEFVAL_", length=150) 
        protected String defVal;

        /**
         * 数据规则JSON
         */
        @Column(name = "RULEJSON_", length=255) 
        protected String ruleJson;

        /**
         * 规则启用条件
         */
        @Column(name = "ENCONDITON_", length=150) 
        protected String enCondition;

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


         
	public DepReportCellData() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public Long getCellId(){
	    return this.cellId;
	}
        public String getInputMode(){
	    return this.inputMode;
	}
        public String getReadOnly(){
	    return this.readOnly;
	}
        public String getDtype(){
	    return this.dtype;
	}
        public String getDefVal(){
	    return this.defVal;
	}
        public String getRuleJson(){
	    return this.ruleJson;
	}
        public String getEnCondition(){
	    return this.enCondition;
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


        public void setCellId(Long cellId) {
	    this.cellId = cellId; 
	}
        public void setInputMode(String inputMode) {
	    this.inputMode = inputMode; 
	}
        public void setReadOnly(String readOnly) {
	    this.readOnly = readOnly; 
	}
        public void setDtype(String dtype) {
	    this.dtype = dtype; 
	}
        public void setDefVal(String defVal) {
	    this.defVal = defVal; 
	}
        public void setRuleJson(String ruleJson) {
	    this.ruleJson = ruleJson; 
	}
        public void setEnCondition(String enCondition) {
	    this.enCondition = enCondition; 
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
		DepReportCellData other = (DepReportCellData) obj;
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


	public DepReportCellData jsonToObject(JSONObject jsonObject) {
            return DepReportCellDataJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return DepReportCellDataJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return DepReportCellDataJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
