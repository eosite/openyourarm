package com.glaf.etl.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.etl.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "ETL_DATATRANSFER")
public class ETLDataTransfer implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 数据源ID
         */
        @Column(name = "DATASRCID_", length=64) 
        protected String dataSrcId;

        /**
         * 数据源ID
         */
        @Column(name = "TARGET_ID_", length=64) 
        protected String targetId;

        /**
         * 配置名称
         */
        @Column(name = "TRANSNAME_", length=50) 
        protected String transName;

        /**
         * 分组字段
         */
        @Column(name = "GROUPCOLUMNS_")  
        protected byte[] groupColumns;

        /**
         * 行转列字段
         */
        @Column(name = "TRANSFERCOLUMNS_")  
        protected byte[] transferColumns;

        /**
         * 值字段
         */
        @Column(name = "VALUECOLUMNS_")  
        protected byte[] valueColumns;

        /**
         * 分组字段
         */
        @Column(name = "COLUMN_MAPPING_")  
        protected byte[] columnsMapping;

        /**
         * 创建人
         */
        @Column(name = "CREATEBY_", length=50) 
        protected String createBy;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * 更新人
         */
        @Column(name = "UPDATEBY_", length=50) 
        protected String updateBy;

        /**
         * 更新时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;

        @Column(name = "ROUTRULE_") 
        protected byte[] routRule;
         
	public ETLDataTransfer() {

	}

	

	public byte[] getRoutRule() {
		return routRule;
	}

	public void setRoutRule(byte[] routRule) {
		this.routRule = routRule;
	}





	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getDataSrcId(){
	    return this.dataSrcId;
	}
        public String getTargetId(){
	    return this.targetId;
	}
        public String getTransName(){
	    return this.transName;
	}
        public byte[] getGroupColumns(){
	    return this.groupColumns;
	}
        public byte[] getTransferColumns(){
	    return this.transferColumns;
	}
        public byte[] getValueColumns(){
	    return this.valueColumns;
	}
        public byte[] getColumnsMapping(){
	    return this.columnsMapping;
	}
        public String getCreateBy(){
	    return this.createBy;
	}
	public Date getCreateTime(){
	    return this.createTime;
	}
	public String getCreateTimeString(){
	    if(this.createTime != null){
	        return DateUtils.getDateTime(this.createTime);
	    }
	    return "";
	}
        public String getUpdateBy(){
	    return this.updateBy;
	}
	public Date getUpdateTime(){
	    return this.updateTime;
	}
	public String getUpdateTimeString(){
	    if(this.updateTime != null){
	        return DateUtils.getDateTime(this.updateTime);
	    }
	    return "";
	}


        public void setDataSrcId(String dataSrcId) {
	    this.dataSrcId = dataSrcId; 
	}
        public void setTargetId(String targetId) {
	    this.targetId = targetId; 
	}
        public void setTransName(String transName) {
	    this.transName = transName; 
	}
        public void setGroupColumns(byte[] groupColumns) {
	    this.groupColumns = groupColumns; 
	}
        public void setTransferColumns(byte[] transferColumns) {
	    this.transferColumns = transferColumns; 
	}
        public void setValueColumns(byte[] valueColumns) {
	    this.valueColumns = valueColumns; 
	}
        public void setColumnsMapping(byte[] columnsMapping) {
	    this.columnsMapping = columnsMapping; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setCreateTime(Date createTime) {
	    this.createTime = createTime; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}
        public void setUpdateTime(Date updateTime) {
	    this.updateTime = updateTime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ETLDataTransfer other = (ETLDataTransfer) obj;
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


	public ETLDataTransfer jsonToObject(JSONObject jsonObject) {
            return ETLDataTransferJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ETLDataTransferJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ETLDataTransferJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
