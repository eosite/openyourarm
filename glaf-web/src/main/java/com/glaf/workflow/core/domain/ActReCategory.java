package com.glaf.workflow.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.workflow.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "ACT_RE_CATEGORY")
public class ActReCategory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * parentId
         */
        @Column(name = "PARENT_ID_")	 
        protected Long parentId;

        /**
         * code
         */
        @Column(name = "CODE_", length=150) 
        protected String code;

        /**
         * name
         */
        @Column(name = "NAME_", length=150) 
        protected String name;

        /**
         * treeId
         */
        @Column(name = "TREE_ID_", length=300) 
        protected String treeId;

        /**
         * level
         */
        @Column(name = "LEVEL_")
        protected Integer level;

        /**
         * deleteFlag
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag;

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

        /**
         * modifier
         */
        @Column(name = "MODIFIER_", length=20) 
        protected String modifier;

        /**
         * modifyDatetime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "MODIFYDATETIME_")	
        protected Date modifyDatetime;


         
	public ActReCategory() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public Long getParentId(){
	    return this.parentId;
	}
        public String getCode(){
	    return this.code;
	}
        public String getName(){
	    return this.name;
	}
        public String getTreeId(){
	    return this.treeId;
	}
        public Integer getLevel(){
	    return this.level;
	}
        public Integer getDeleteFlag(){
	    return this.deleteFlag;
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
        public String getModifier(){
	    return this.modifier;
	}
	public Date getModifyDatetime(){
	    return this.modifyDatetime;
	}
	public String getModifyDatetimeString(){
	    if(this.modifyDatetime != null){
	        return DateUtils.getDateTime(this.modifyDatetime);
	    }
	    return "";
	}


        public void setParentId(Long parentId) {
	    this.parentId = parentId; 
	}
        public void setCode(String code) {
	    this.code = code; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setTreeId(String treeId) {
	    this.treeId = treeId; 
	}
        public void setLevel(Integer level) {
	    this.level = level; 
	}
        public void setDeleteFlag(Integer deleteFlag) {
	    this.deleteFlag = deleteFlag; 
	}
        public void setCreator(String creator) {
	    this.creator = creator; 
	}
        public void setCreateDatetime(Date createDatetime) {
	    this.createDatetime = createDatetime; 
	}
        public void setModifier(String modifier) {
	    this.modifier = modifier; 
	}
        public void setModifyDatetime(Date modifyDatetime) {
	    this.modifyDatetime = modifyDatetime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActReCategory other = (ActReCategory) obj;
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


	public ActReCategory jsonToObject(JSONObject jsonObject) {
            return ActReCategoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ActReCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ActReCategoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
