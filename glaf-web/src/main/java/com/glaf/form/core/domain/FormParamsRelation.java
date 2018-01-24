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
@Table(name = "FORM_PARAMS_RELATION")
public class FormParamsRelation implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 页面id
         */
        @Column(name = "PAGEID_", length=50) 
        protected String pageId;

        /**
         * 控件id
         */
        @Column(name = "WIDGETID_", length=50) 
        protected String widgetId;

        /**
         * 参数表id
         */
        @Column(name = "PID_", length=50) 
        protected String pid;

        /**
         * 引用属性名称
         */
        @Column(name = "ATTRNAME_", length=50) 
        protected String attrName;


         
	public FormParamsRelation() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getPageId(){
	    return this.pageId;
	}
        public String getWidgetId(){
	    return this.widgetId;
	}
        public String getPid(){
	    return this.pid;
	}
        public String getAttrName(){
	    return this.attrName;
	}


        public void setPageId(String pageId) {
	    this.pageId = pageId; 
	}
        public void setWidgetId(String widgetId) {
	    this.widgetId = widgetId; 
	}
        public void setPid(String pid) {
	    this.pid = pid; 
	}
        public void setAttrName(String attrName) {
	    this.attrName = attrName; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormParamsRelation other = (FormParamsRelation) obj;
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


	public FormParamsRelation jsonToObject(JSONObject jsonObject) {
            return FormParamsRelationJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormParamsRelationJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormParamsRelationJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
