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
@Table(name = "DEP_REPORT_WDATASET")
public class DepReportWdataSet implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * 更新数据集唯一标识
         */
        @Column(name = "WDATASET_ID_")	 
        protected Long wdatasetId;

        /**
         * 报表模板唯一标识
         */
        @Column(name = "REPTEMPLATE_ID_")	 
        protected Long repTemplateId;

        /**
         * 写入集代码
         */
        @Column(name = "CODE_", length=50) 
        protected String code;

        /**
         * 写入集名称
         */
        @Column(name = "NAME_", length=50) 
        protected String name;

        /**
         * 数据表名
         */
        @Column(name = "TABLE_NAME_", length=50) 
        protected String tableName;

        /**
         * 数据表物理表名
         */
        @Column(name = "DATATABLE_NAME_", length=30) 
        protected String dataTableName;

        /**
         * 规则启用条件
         */
        @Column(name = "ENCONDITON_", length=150) 
        protected String enConditon;

        /**
         * 执行顺序
         */
        @Column(name = "ORDER_")
        protected Integer order;

        /**
         * 写入规则JSON
         */
        @Column(name = "RULEJSON_", length=255) 
        protected String ruleJson;

        /**
         * 预编译SQL
         */
        @Column(name = "PSQL_", length=255) 
        protected String psql;

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


         
	public DepReportWdataSet() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public Long getWdatasetId(){
	    return this.wdatasetId;
	}
        public Long getRepTemplateId(){
	    return this.repTemplateId;
	}
        public String getCode(){
	    return this.code;
	}
        public String getName(){
	    return this.name;
	}
        public String getTableName(){
	    return this.tableName;
	}
        public String getDataTableName(){
	    return this.dataTableName;
	}
        public String getEnConditon(){
	    return this.enConditon;
	}
        public Integer getOrder(){
	    return this.order;
	}
        public String getRuleJson(){
	    return this.ruleJson;
	}
        public String getPsql(){
	    return this.psql;
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


        public void setWdatasetId(Long wdatasetId) {
	    this.wdatasetId = wdatasetId; 
	}
        public void setRepTemplateId(Long repTemplateId) {
	    this.repTemplateId = repTemplateId; 
	}
        public void setCode(String code) {
	    this.code = code; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setTableName(String tableName) {
	    this.tableName = tableName; 
	}
        public void setDataTableName(String dataTableName) {
	    this.dataTableName = dataTableName; 
	}
        public void setEnConditon(String enConditon) {
	    this.enConditon = enConditon; 
	}
        public void setOrder(Integer order) {
	    this.order = order; 
	}
        public void setRuleJson(String ruleJson) {
	    this.ruleJson = ruleJson; 
	}
        public void setPsql(String psql) {
	    this.psql = psql; 
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
		DepReportWdataSet other = (DepReportWdataSet) obj;
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


	public DepReportWdataSet jsonToObject(JSONObject jsonObject) {
            return DepReportWdataSetJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return DepReportWdataSetJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return DepReportWdataSetJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
