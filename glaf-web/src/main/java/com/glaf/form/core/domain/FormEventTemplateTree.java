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
@Table(name = "FORM_EVENT_TEMPLATE_TREE")
public class FormEventTemplateTree implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * indexId
         */
        @Column(name = "INDEXID_")
        protected Integer indexId;

        /**
         * parentId
         */
        @Column(name = "PARENTID_")
        protected Integer parentId;

        /**
         * treeId
         */
        @Column(name = "TREEID_", length=200) 
        protected String treeId;

        /**
         * 类型
         */
        @Column(name = "TYPE_")
        protected Integer type;

        /**
         * 名称
         */
        @Column(name = "NAME_", length=50) 
        protected String name;

        /**
         * 删除标记
         */
        @Column(name = "DELETEFLAG_")
        protected Integer deleteFlag;

        /**
         * 创建日期
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE_")	
        protected Date createDate;

        /**
         * 创建人
         */
        @Column(name = "CREATEBY_", length=50) 
        protected String createBy;

        /**
         * 修改人
         */
        @Column(name = "UPDATEBY_", length=50) 
        protected String updateBy;

        /**
         * 修改日期
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE_")	
        protected Date updateDate;


         
	public FormEventTemplateTree() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public Integer getIndexId(){
	    return this.indexId;
	}
        public Integer getParentId(){
	    return this.parentId;
	}
        public String getTreeId(){
	    return this.treeId;
	}
        public Integer getType(){
	    return this.type;
	}
        public String getName(){
	    return this.name;
	}
        public Integer getDeleteFlag(){
	    return this.deleteFlag;
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
        public String getCreateBy(){
	    return this.createBy;
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


        public void setIndexId(Integer indexId) {
	    this.indexId = indexId; 
	}
        public void setParentId(Integer parentId) {
	    this.parentId = parentId; 
	}
        public void setTreeId(String treeId) {
	    this.treeId = treeId; 
	}
        public void setType(Integer type) {
	    this.type = type; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setDeleteFlag(Integer deleteFlag) {
	    this.deleteFlag = deleteFlag; 
	}
        public void setCreateDate(Date createDate) {
	    this.createDate = createDate; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}
        public void setUpdateDate(Date updateDate) {
	    this.updateDate = updateDate; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormEventTemplateTree other = (FormEventTemplateTree) obj;
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


	public FormEventTemplateTree jsonToObject(JSONObject jsonObject) {
            return FormEventTemplateTreeJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormEventTemplateTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormEventTemplateTreeJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
