package com.glaf.form.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.ObjectCategoryJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "OBJ_CATEGORY")
public class ObjectCategory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "CATEGORY_ID_", nullable = false)
        protected Long categoryId;

        /**
         * tREEID
         */
        @Column(name = "TREEID_", length=150) 
        protected String treeID;

        /**
         * cODE
         */
        @Column(name = "CODE_", length=30) 
        protected String code;

        /**
         * nAME
         */
        @Column(name = "NAME_", length=30) 
        protected String name;

        /**
         * cUSTOM
         */
        @Column(name = "CUSTOM_")
        protected Integer custom;

        /**
         * oWNER
         */
        @Column(name = "OWNER_", length=20) 
        protected String owner;

        /**
         * pARENTID
         */
        @Column(name = "PARENTID_")	 
        protected Long parentId;

        /**
         * oRDERNO
         */
        @Column(name = "ORDERNO_")
        protected Integer orderNo;

        /**
         * lEVEL
         */
        @Column(name = "LEVEL_")  
        protected Integer level;

        /**
         * dELFLAG
         */
        @Column(name = "DELFLAG_")
        protected Integer delFlag;

        /**
         * cREATOR
         */
        @Column(name = "CREATOR_", length=20) 
        protected String creator;

        /**
         * cREATETIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * mODIFIER
         */
        @Column(name = "MODIFIER_", length=20) 
        protected String modifier;

        /**
         * uPDATETIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;


         
	public ObjectCategory() {

	}

	public Long getCategoryId(){
	    return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
	    this.categoryId = categoryId; 
	}


        public String getTreeID(){
	    return this.treeID;
	}
        public String getCode(){
	    return this.code;
	}
        public String getName(){
	    return this.name;
	}
        public Integer getCustom(){
	    return this.custom;
	}
        public String getOwner(){
	    return this.owner;
	}
        public Long getParentId(){
	    return this.parentId;
	}
        public Integer getOrderNo(){
	    return this.orderNo;
	}
        public Integer getLevel(){
	    return this.level;
	}
        public Integer getDelFlag(){
	    return this.delFlag;
	}
        public String getCreator(){
	    return this.creator;
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
        public String getModifier(){
	    return this.modifier;
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


        public void setTreeID(String treeID) {
	    this.treeID = treeID; 
	}
        public void setCode(String code) {
	    this.code = code; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setCustom(Integer custom) {
	    this.custom = custom; 
	}
        public void setOwner(String owner) {
	    this.owner = owner; 
	}
        public void setParentId(Long parentId) {
	    this.parentId = parentId; 
	}
        public void setOrderNo(Integer orderNo) {
	    this.orderNo = orderNo; 
	}
        public void setLevel(Integer level) {
	    this.level = level; 
	}
        public void setDelFlag(Integer delFlag) {
	    this.delFlag = delFlag; 
	}
        public void setCreator(String creator) {
	    this.creator = creator; 
	}
        public void setCreateTime(Date createTime) {
	    this.createTime = createTime; 
	}
        public void setModifier(String modifier) {
	    this.modifier = modifier; 
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
		ObjectCategory other = (ObjectCategory) obj;
		if (categoryId == null) {
			if (other.categoryId != null)
				return false;
		} else if (!categoryId.equals(other.categoryId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((categoryId == null) ? 0 : categoryId.hashCode());
		return result;
	}


	public ObjectCategory jsonToObject(JSONObject jsonObject) {
            return ObjectCategoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ObjectCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ObjectCategoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
