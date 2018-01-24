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
import com.glaf.form.core.util.ObjTemplateCategoryJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "OBJ_TEMPLATE_CATEGORY")
public class ObjTemplateCategory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "CATEGORY_ID_", nullable = false)
        protected Long categoryId;

        /**
         * tEMPLATEID
         */
        @Column(name = "TEMPLATE_ID_")	 
        protected Long templateId;

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


         
	public ObjTemplateCategory() {

	}

	public Long getCategoryId(){
	    return this.categoryId;
	}

	public void setCategoryId(Long categoryId) {
	    this.categoryId = categoryId; 
	}


        public Long getTemplateId(){
	    return this.templateId;
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


        public void setTemplateId(Long templateId) {
	    this.templateId = templateId; 
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
		ObjTemplateCategory other = (ObjTemplateCategory) obj;
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


	public ObjTemplateCategory jsonToObject(JSONObject jsonObject) {
            return ObjTemplateCategoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ObjTemplateCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ObjTemplateCategoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
