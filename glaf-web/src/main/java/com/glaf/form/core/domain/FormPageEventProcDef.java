package com.glaf.form.core.domain;

import java.io.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_PAGE_EVENT_PROCDEF")
public class FormPageEventProcDef implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * pageId
         */
        @Column(name = "PAGEID_", length=255) 
        protected String pageId;

        /**
         * compId
         */
        @Column(name = "COMPID_", length=50) 
        protected String compId;

        /**
         * event_
         */
        @Column(name = "EVENT_", length=50) 
        protected String event_;

        /**
         * procDefKey
         */
        @Column(name = "PROCDEF_KEY_", length=255) 
        protected String procDefKey;

        /**
         * procDefId
         */
        @Column(name = "PROCDEF_ID_", length=64) 
        protected String procDefId;

        /**
         * procModelId
         */
        @Column(name = "PROCMODEL_ID_", length=64) 
        protected String procModelId;
        
        /**
         * procDeployId
         */
        @Column(name = "PROCDEPLOY_ID_", length=64) 
        protected String procDeployId;
        
        @Column(name = "PROCDEPLOY_STATUS_", length=1) 
        protected String procDeployStatus;

         
	public FormPageEventProcDef() {

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
        public String getCompId(){
	    return this.compId;
	}
        public String getEvent_(){
	    return this.event_;
	}
        public String getProcDefKey(){
	    return this.procDefKey;
	}
        public String getProcDefId(){
	    return this.procDefId;
	}
        public String getProcModelId(){
	    return this.procModelId;
	}


        public String getProcDeployId() {
			return procDeployId;
		}

		public void setProcDeployId(String procDeployId) {
			this.procDeployId = procDeployId;
		}

		public void setPageId(String pageId) {
	    this.pageId = pageId; 
	}
        public void setCompId(String compId) {
	    this.compId = compId; 
	}
        public void setEvent_(String event_) {
	    this.event_ = event_; 
	}
        public void setProcDefKey(String procDefKey) {
	    this.procDefKey = procDefKey; 
	}
        public void setProcDefId(String procDefId) {
	    this.procDefId = procDefId; 
	}
        public void setProcModelId(String procModelId) {
	    this.procModelId = procModelId; 
	}


	public String getProcDeployStatus() {
			return procDeployStatus;
		}

		public void setProcDeployStatus(String procDeployStatus) {
			this.procDeployStatus = procDeployStatus;
		}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormPageEventProcDef other = (FormPageEventProcDef) obj;
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


	public FormPageEventProcDef jsonToObject(JSONObject jsonObject) {
            return FormPageEventProcDefJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormPageEventProcDefJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormPageEventProcDefJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
