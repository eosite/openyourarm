package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DEP_BASE_WDATASET_SQLLITE_")
public class WdatasetSqllite implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * 代码
         */
        @Column(name = "SQLLITE_RULE_CODE_", length=50) 
        protected String sqlliteRuleCode;

        /**
         * 描述
         */
        @Column(name = "SQLLITE_RULE_DESC_", length=150) 
        protected String sqlliteRuleDesc;

        /**
         * 名称
         */
        @Column(name = "SQLLITE_RULE_NAME_", length=50) 
        protected String sqlliteRuleName;

        /**
         * 数据集合名称
         */
        @Column(name = "DATASETS_NAME_", length=30) 
        protected String dataSetsName;

        /**
         * 删除标识
         */
        @Column(name = "DELFLAG_", length=1) 
        protected String delflag;

        /**
         * 规则信息
         */
        @Column(name = "RULEJSON_") 
        protected String ruleJson;

        /**
         * 创建人
         */
        @Column(name = "CREATEBY", length=50) 
        protected String createBy;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE")	
        protected Date createDate;

        /**
         * 修改人
         */
        @Column(name = "UPDATEBY", length=50) 
        protected String updateBy;

        /**
         * 修改时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE")	
        protected Date updateDate;


         
	public WdatasetSqllite() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public String getSqlliteRuleCode(){
	    return this.sqlliteRuleCode;
	}
        public String getSqlliteRuleDesc(){
	    return this.sqlliteRuleDesc;
	}
        public String getSqlliteRuleName(){
	    return this.sqlliteRuleName;
	}
        public String getDataSetsName(){
	    return this.dataSetsName;
	}
        public String getDelflag(){
	    return this.delflag;
	}
        public String getRuleJson(){
	    return this.ruleJson;
	}
        public String getCreateBy(){
	    return this.createBy;
	}
	public Date getCreateDate(){
	    return this.createDate;
	}
	public String getCreateDateString(){
	    if(this.createDate != null){
	        return DateUtils.getDateTime(this.createDate);
	    }
	    return "";
	}
        public String getUpdateBy(){
	    return this.updateBy;
	}
	public Date getUpdateDate(){
	    return this.updateDate;
	}
	public String getUpdateDateString(){
	    if(this.updateDate != null){
	        return DateUtils.getDateTime(this.updateDate);
	    }
	    return "";
	}


        public void setSqlliteRuleCode(String sqlliteRuleCode) {
	    this.sqlliteRuleCode = sqlliteRuleCode; 
	}
        public void setSqlliteRuleDesc(String sqlliteRuleDesc) {
	    this.sqlliteRuleDesc = sqlliteRuleDesc; 
	}
        public void setSqlliteRuleName(String sqlliteRuleName) {
	    this.sqlliteRuleName = sqlliteRuleName; 
	}
        public void setDataSetsName(String dataSetsName) {
	    this.dataSetsName = dataSetsName; 
	}
        public void setDelflag(String delflag) {
	    this.delflag = delflag; 
	}
        public void setRuleJson(String ruleJson) {
	    this.ruleJson = ruleJson; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setCreateDate(Date createDate) {
	    this.createDate = createDate; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}
        public void setUpdateDate(Date updateDate) {
	    this.updateDate = updateDate; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		WdatasetSqllite other = (WdatasetSqllite) obj;
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


	public WdatasetSqllite jsonToObject(JSONObject jsonObject) {
            return WdatasetSqlliteJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return WdatasetSqlliteJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return WdatasetSqlliteJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
