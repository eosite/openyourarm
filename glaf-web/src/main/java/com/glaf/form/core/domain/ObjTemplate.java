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
@Table(name = "OBJ_TEMPLATE")
public class ObjTemplate implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;
        @Id
        @Column(name = "TEMPLATE_ID_", nullable = false)
        protected Long templateId;

        /**
         * tMPNAME
         */
        @Column(name = "TMP_NAME_", length=50) 
        protected String tmpName;

        /**
         * oBJTYPE
         */
        @Column(name = "OBJ_TYPE_", length=60) 
        protected String objType;

        /**
         * tMPCONTENT
         */
        @Lob
        @Column(name = "TMP_CONTENT_")  
        protected byte[] tmpContent;

        /**
         * tHUMBNAIL
         */
        @Lob
        @Column(name = "THUMBNAIL_")  
        protected byte[] thumbnail;

        /**
         * oWNER
         */
        @Column(name = "OWNER_", length=20) 
        protected String owner;

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


         
	public ObjTemplate() {

	}

	public Long getTemplateId(){
	    return this.templateId;
	}

	public void setTemplateId(Long templateId) {
	    this.templateId = templateId; 
	}


        public String getTmpName(){
	    return this.tmpName;
	}
        public String getObjType(){
	    return this.objType;
	}
        public byte[] getTmpContent(){
	    return this.tmpContent;
	}
        public byte[] getThumbnail(){
	    return this.thumbnail;
	}
        public String getOwner(){
	    return this.owner;
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


        public void setTmpName(String tmpName) {
	    this.tmpName = tmpName; 
	}
        public void setObjType(String objType) {
	    this.objType = objType; 
	}
        public void setTmpContent(byte[] tmpContent) {
	    this.tmpContent = tmpContent; 
	}
        public void setThumbnail(byte[] thumbnail) {
	    this.thumbnail = thumbnail; 
	}
        public void setOwner(String owner) {
	    this.owner = owner; 
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
		ObjTemplate other = (ObjTemplate) obj;
		if (templateId == null) {
			if (other.templateId != null)
				return false;
		} else if (!templateId.equals(other.templateId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((templateId == null) ? 0 : templateId.hashCode());
		return result;
	}


	public ObjTemplate jsonToObject(JSONObject jsonObject) {
            return ObjTemplateJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ObjTemplateJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ObjTemplateJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
