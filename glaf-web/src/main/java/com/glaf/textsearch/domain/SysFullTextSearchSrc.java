package com.glaf.textsearch.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.textsearch.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_FULLTEXTSEARCH_SRC")
public class SysFullTextSearchSrc implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * serviceName
         */
        @Column(name = "SERVICE_NAME_", length=100) 
        protected String serviceName_;

        /**
         * serviceAddress
         */
        @Column(name = "SERVICE_ADDRESS_", length=200) 
        protected String serviceAddress_;

        /**
         * rule
         */
	@javax.persistence.Lob
        @Column(name = "RULE_") 
	protected String rule_;

        /**
         * fullTextServer
         */
        @Column(name = "FULLTEXT_SERVER_", length=200) 
        protected String fullTextServer_;

        /**
         * indexName
         */
        @Column(name = "INDEX_NAME_", length=100) 
        protected String indexName_;

        /**
         * typeName
         */
        @Column(name = "TYPE_NAME_", length=50) 
        protected String typeName_;

        /**
         * createBy
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createBy_;

        /**
         * createTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime_;

        /**
         * updateBy
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateBy_;

        /**
         * updateTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime_;

        /**
         * deleteFlag
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag_;


         
	public SysFullTextSearchSrc() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getServiceName_(){
	    return this.serviceName_;
	}
        public String getServiceAddress_(){
	    return this.serviceAddress_;
	}
	public String getRule_(){
	    return this.rule_;
	}
        public String getFullTextServer_(){
	    return this.fullTextServer_;
	}
        public String getIndexName_(){
	    return this.indexName_;
	}
        public String getTypeName_(){
	    return this.typeName_;
	}
        public String getCreateBy_(){
	    return this.createBy_;
	}
	public Date getCreateTime_(){
	    return this.createTime_;
	}
	public String getCreateTime_String(){
	    if(this.createTime_ != null){
	        return DateUtils.getDateTime(this.createTime_);
	    }
	    return "";
	}
        public String getUpdateBy_(){
	    return this.updateBy_;
	}
	public Date getUpdateTime_(){
	    return this.updateTime_;
	}
	public String getUpdateTime_String(){
	    if(this.updateTime_ != null){
	        return DateUtils.getDateTime(this.updateTime_);
	    }
	    return "";
	}
        public Integer getDeleteFlag_(){
	    return this.deleteFlag_;
	}


        public void setServiceName_(String serviceName_) {
	    this.serviceName_ = serviceName_; 
	}
        public void setServiceAddress_(String serviceAddress_) {
	    this.serviceAddress_ = serviceAddress_; 
	}
	public void setRule_(String rule_) {
	    this.rule_ = rule_; 
	}
        public void setFullTextServer_(String fullTextServer_) {
	    this.fullTextServer_ = fullTextServer_; 
	}
        public void setIndexName_(String indexName_) {
	    this.indexName_ = indexName_; 
	}
        public void setTypeName_(String typeName_) {
	    this.typeName_ = typeName_; 
	}
        public void setCreateBy_(String createBy_) {
	    this.createBy_ = createBy_; 
	}
        public void setCreateTime_(Date createTime_) {
	    this.createTime_ = createTime_; 
	}
        public void setUpdateBy_(String updateBy_) {
	    this.updateBy_ = updateBy_; 
	}
        public void setUpdateTime_(Date updateTime_) {
	    this.updateTime_ = updateTime_; 
	}
        public void setDeleteFlag_(Integer deleteFlag_) {
	    this.deleteFlag_ = deleteFlag_; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysFullTextSearchSrc other = (SysFullTextSearchSrc) obj;
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


	public SysFullTextSearchSrc jsonToObject(JSONObject jsonObject) {
            return SysFullTextSearchSrcJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return SysFullTextSearchSrcJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SysFullTextSearchSrcJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
