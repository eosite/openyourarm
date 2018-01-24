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
@Table(name = "DEP_REPORT_DATASET")
public class DepReportDataSet implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * 模板唯一标识
         */
        @Column(name = "REPTEMPLATE_ID_")	 
        protected Long repTemplateId;

        /**
         * 查询集唯一标识
         */
        @Column(name = "DATASET_ID_")	 
        protected Long dataSetId;

        /**
         * 启用条件
         */
        @Column(name = "ENCONDITON_", length=150) 
        protected String enCondition;

        /**
         * 规则JSON
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


         
	public DepReportDataSet() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public Long getRepTemplateId(){
	    return this.repTemplateId;
	}
        public Long getDataSetId(){
	    return this.dataSetId;
	}
        public String getEnCondition(){
	    return this.enCondition;
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


        public void setRepTemplateId(Long repTemplateId) {
	    this.repTemplateId = repTemplateId; 
	}
        public void setDataSetId(Long dataSetId) {
	    this.dataSetId = dataSetId; 
	}
        public void setEnCondition(String enCondition) {
	    this.enCondition = enCondition; 
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
		DepReportDataSet other = (DepReportDataSet) obj;
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


	public DepReportDataSet jsonToObject(JSONObject jsonObject) {
            return DepReportDataSetJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return DepReportDataSetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return DepReportDataSetJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
