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
@Table(name = "FORM_TREE")
public class FormTree implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID", nullable = false)
        protected Long id;

        /**
         * CODE
         */
        @Column(name = "CODE", length=50) 
        protected String code;

        /**
         * CREATEBY
         */
        @Column(name = "CREATEBY", length=50) 
        protected String createBy;

        /**
         * CREATEDATE
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE")	
        protected Date createDate;

        /**
         * NODEDESC
         */
        @Column(name = "NODEDESC", length=500) 
        protected String nodeDesc;

        /**
         * DISCRIMINATOR
         */
        @Column(name = "DISCRIMINATOR", length=10) 
        protected String discriminator;

        /**
         * ICON
         */
        @Column(name = "ICON", length=50) 
        protected String icon;

        /**
         * ICONCLS
         */
        @Column(name = "ICONCLS", length=50) 
        protected String iconCls;

        /**
         * LOCKED
         */
        @Column(name = "LOCKED")
        protected Integer locked;

        /**
         * MOVEABLE
         */
        @Column(name = "MOVEABLE", length=10) 
        protected String moveable;

        /**
         * NAME
         */
        @Column(name = "NAME", length=100) 
        protected String name;

        /**
         * PARENT
         */
        @Column(name = "PARENT")	 
        protected Long parent;

        /**
         * SORT
         */
        @Column(name = "SORT")
        protected Integer sort;

        /**
         * TREEID
         */
        @Column(name = "TREEID", length=500) 
        protected String treeId;

        /**
         * UPDATEBY
         */
        @Column(name = "UPDATEBY", length=50) 
        protected String updateBy;

        /**
         * UPDATEDATE
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE")	
        protected Date updateDate;

        /**
         * URL
         */
        @Column(name = "URL", length=500) 
        protected String url;

        /**
         * CATEGORY
         */
        @Column(name = "CATEGORY", length=10) 
        protected String category;


         
	public FormTree() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public String getCode(){
	    return this.code;
	}
        public String getCreateBy(){
	    return this.createBy;
	}
	public Date getCreateDate(){
	    return this.createDate;
	}
	public String getCreateDateString(){
	    if(this.createDate != null){
	        return DateUtils.getDateTime(this.createDate);
	    }
	    return "";
	}
        public String getNodeDesc(){
	    return this.nodeDesc;
	}
        public String getDiscriminator(){
	    return this.discriminator;
	}
        public String getIcon(){
	    return this.icon;
	}
        public String getIconCls(){
	    return this.iconCls;
	}
        public Integer getLocked(){
	    return this.locked;
	}
        public String getMoveable(){
	    return this.moveable;
	}
        public String getName(){
	    return this.name;
	}
        public Long getParent(){
	    return this.parent;
	}
        public Integer getSort(){
	    return this.sort;
	}
        public String getTreeId(){
	    return this.treeId;
	}
        public String getUpdateBy(){
	    return this.updateBy;
	}
	public Date getUpdateDate(){
	    return this.updateDate;
	}
	public String getUpdateDateString(){
	    if(this.updateDate != null){
	        return DateUtils.getDateTime(this.updateDate);
	    }
	    return "";
	}
        public String getUrl(){
	    return this.url;
	}
        public String getCategory(){
	    return this.category;
	}


        public void setCode(String code) {
	    this.code = code; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setCreateDate(Date createDate) {
	    this.createDate = createDate; 
	}
        public void setNodeDesc(String nodeDesc) {
	    this.nodeDesc = nodeDesc; 
	}
        public void setDiscriminator(String discriminator) {
	    this.discriminator = discriminator; 
	}
        public void setIcon(String icon) {
	    this.icon = icon; 
	}
        public void setIconCls(String iconCls) {
	    this.iconCls = iconCls; 
	}
        public void setLocked(Integer locked) {
	    this.locked = locked; 
	}
        public void setMoveable(String moveable) {
	    this.moveable = moveable; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setParent(Long parent) {
	    this.parent = parent; 
	}
        public void setSort(Integer sort) {
	    this.sort = sort; 
	}
        public void setTreeId(String treeId) {
	    this.treeId = treeId; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}
        public void setUpdateDate(Date updateDate) {
	    this.updateDate = updateDate; 
	}
        public void setUrl(String url) {
	    this.url = url; 
	}
        public void setCategory(String category) {
	    this.category = category; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormTree other = (FormTree) obj;
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


	public FormTree jsonToObject(JSONObject jsonObject) {
            return FormTreeJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormTreeJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
