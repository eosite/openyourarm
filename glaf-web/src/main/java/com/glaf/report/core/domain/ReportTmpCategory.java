package com.glaf.report.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.report.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "REPORT_TMP_CATEGORY")
public class ReportTmpCategory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * templateId
         */
        @Column(name = "TEMPLATE_ID_", length=64) 
        protected String templateId;

        /**
         * categoryId
         */
        @Column(name = "CATEGORY_ID_")	 
        protected Long categoryId;

        /**
         * creator
         */
        @Column(name = "CREATOR_", length=20) 
        protected String creator;

        /**
         * createDatetime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATETIME_")	
        protected Date createDatetime;


         
	public ReportTmpCategory() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public String getTemplateId(){
	    return this.templateId;
	}
        public Long getCategoryId(){
	    return this.categoryId;
	}
        public String getCreator(){
	    return this.creator;
	}
	public Date getCreateDatetime(){
	    return this.createDatetime;
	}
	public String getCreateDatetimeString(){
	    if(this.createDatetime != null){
	        return DateUtils.getDateTime(this.createDatetime);
	    }
	    return "";
	}


        public void setTemplateId(String templateId) {
	    this.templateId = templateId; 
	}
        public void setCategoryId(Long categoryId) {
	    this.categoryId = categoryId; 
	}
        public void setCreator(String creator) {
	    this.creator = creator; 
	}
        public void setCreateDatetime(Date createDatetime) {
	    this.createDatetime = createDatetime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReportTmpCategory other = (ReportTmpCategory) obj;
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


	public ReportTmpCategory jsonToObject(JSONObject jsonObject) {
            return ReportTmpCategoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ReportTmpCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ReportTmpCategoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
