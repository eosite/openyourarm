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
@Table(name = "FORM_OUTEXP_RELATION")
public class FormOutexpRelation implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 页面ID
         */
        @Column(name = "PAGEID_", length=50) 
        protected String pageId;

        /**
         * 规则ID
         */
        @Column(name = "WIDGETID_", length=50) 
        protected String widgetId;

        /**
         * 扩展类型
         */
        @Column(name = "TYPE_", length=50) 
        protected String type;

        /**
         * 扩展值
         */
        @Column(name = "VALUE_", length=50) 
        protected String value;


         
	public FormOutexpRelation() {

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
        public String getType(){
	    return this.type;
	}
        public String getValue(){
	    return this.value;
	}


        public void setPageId(String pageId) {
	    this.pageId = pageId; 
	}
        public void setWidgetId(String widgetId) {
	    this.widgetId = widgetId; 
	}
        public void setType(String type) {
	    this.type = type; 
	}
        public void setValue(String value) {
	    this.value = value; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormOutexpRelation other = (FormOutexpRelation) obj;
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


	public FormOutexpRelation jsonToObject(JSONObject jsonObject) {
            return FormOutexpRelationJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormOutexpRelationJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormOutexpRelationJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
