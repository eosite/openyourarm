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
@Table(name = "FORM_WORKFLOW_TREE")
public class FormWorkflowTree implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * DEFID
         */
        @Column(name = "DEF_ID_", length=50) 
        protected String defId;

        /**
         * PDEFID
         */
        @Column(name = "P_DEFID_", length=50) 
        protected String p_defId;

        /**
         * PPROCESSDEFID
         */
        @Column(name = "P_PROCESSDEF_ID_", length=100) 
        protected String p_processdefId;

        /**
         * PROCESSDEFID
         */
        @Column(name = "PROCESSDEF_ID_", length=100) 
        protected String processdefId;


         
	public FormWorkflowTree() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public String getDefId(){
	    return this.defId;
	}
        public String getP_defId(){
	    return this.p_defId;
	}
        public String getP_processdefId(){
	    return this.p_processdefId;
	}
        public String getProcessdefId(){
	    return this.processdefId;
	}


        public void setDefId(String defId) {
	    this.defId = defId; 
	}
        public void setP_defId(String p_defId) {
	    this.p_defId = p_defId; 
	}
        public void setP_processdefId(String p_processdefId) {
	    this.p_processdefId = p_processdefId; 
	}
        public void setProcessdefId(String processdefId) {
	    this.processdefId = processdefId; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormWorkflowTree other = (FormWorkflowTree) obj;
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


	public FormWorkflowTree jsonToObject(JSONObject jsonObject) {
            return FormWorkflowTreeJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormWorkflowTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormWorkflowTreeJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
