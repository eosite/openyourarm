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
@Table(name = "OBJ_TEMPLATE_STYLE")
public class ObjectTemplateStyle implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "STYLE_ID_", nullable = false)
        protected Long styleId;

        /**
         * ruleContent
         */
        @Column(name = "RULE_CONTENT_")  
        protected byte[] ruleContent;

        /**
         * styleContent
         */
        @Column(name = "STYLE_CONTENT_")  
        protected byte[] styleContent;

        /**
         * delFlag
         */
        @Column(name = "DELFLAG_")
        protected Integer delFlag;

        /**
         * creator
         */
        @Column(name = "CREATOR_", length=20) 
        protected String creator;

        /**
         * createTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * modifier
         */
        @Column(name = "MODIFIER_", length=20) 
        protected String modifier;

        /**
         * updateTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;


         
	public ObjectTemplateStyle() {

	}

	public Long getStyleId(){
	    return this.styleId;
	}

	public void setStyleId(Long styleId) {
	    this.styleId = styleId; 
	}


        public byte[] getRuleContent(){
	    return this.ruleContent;
	}
        public byte[] getStyleContent(){
	    return this.styleContent;
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


        public void setRuleContent(byte[] ruleContent) {
	    this.ruleContent = ruleContent; 
	}
        public void setStyleContent(byte[] styleContent) {
	    this.styleContent = styleContent; 
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
		ObjectTemplateStyle other = (ObjectTemplateStyle) obj;
		if (styleId == null) {
			if (other.styleId != null)
				return false;
		} else if (!styleId.equals(other.styleId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((styleId == null) ? 0 : styleId.hashCode());
		return result;
	}


	public ObjectTemplateStyle jsonToObject(JSONObject jsonObject) {
            return ObjectTemplateStyleJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ObjectTemplateStyleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ObjectTemplateStyleJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
