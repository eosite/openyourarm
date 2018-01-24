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
@Table(name = "FORM_PAGE_CATEGORY")
public class FormPageCategory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Integer id;

        /**
         * name
         */
        @Column(name = "NAME_", length=50) 
        protected String name;

        /**
         * deleteFlag
         */
        @Column(name = "DELETEFLAG_")
        protected Integer deleteFlag;

        /**
         * sortNo
         */
        @Column(name = "SORTNO_")
        protected Integer sortNo;

        /**
         * locked
         */
        @Column(name = "LOCKED_")
        protected Integer locked;

        /**
         * permission
         */
        @Column(name = "PERMISSION_", length=50) 
        protected String permission;

        /**
         * ext1
         */
        @Column(name = "EXT1_", length=50) 
        protected String ext1;

        /**
         * ext2
         */
        @Column(name = "EXT2_", length=50) 
        protected String ext2;

        /**
         * ext3
         */
        @Column(name = "EXT3_", length=50) 
        protected String ext3;

        /**
         * createBy
         */
        @Column(name = "CREATEBY_", length=50) 
        protected String createBy;

        /**
         * createDate
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATA_")	
        protected Date createDate;


         
	public FormPageCategory() {

	}

	public Integer getId(){
	    return this.id;
	}

	public void setId(Integer id) {
	    this.id = id; 
	}


        public String getName(){
	    return this.name;
	}
        public Integer getDeleteFlag(){
	    return this.deleteFlag;
	}
        public Integer getSortNo(){
	    return this.sortNo;
	}
        public Integer getLocked(){
	    return this.locked;
	}
        public String getPermission(){
	    return this.permission;
	}
        public String getExt1(){
	    return this.ext1;
	}
        public String getExt2(){
	    return this.ext2;
	}
        public String getExt3(){
	    return this.ext3;
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


        public void setName(String name) {
	    this.name = name; 
	}
        public void setDeleteFlag(Integer deleteFlag) {
	    this.deleteFlag = deleteFlag; 
	}
        public void setSortNo(Integer sortNo) {
	    this.sortNo = sortNo; 
	}
        public void setLocked(Integer locked) {
	    this.locked = locked; 
	}
        public void setPermission(String permission) {
	    this.permission = permission; 
	}
        public void setExt1(String ext1) {
	    this.ext1 = ext1; 
	}
        public void setExt2(String ext2) {
	    this.ext2 = ext2; 
	}
        public void setExt3(String ext3) {
	    this.ext3 = ext3; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setCreateDate(Date createDate) {
	    this.createDate = createDate; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormPageCategory other = (FormPageCategory) obj;
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


	public FormPageCategory jsonToObject(JSONObject jsonObject) {
            return FormPageCategoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormPageCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormPageCategoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
