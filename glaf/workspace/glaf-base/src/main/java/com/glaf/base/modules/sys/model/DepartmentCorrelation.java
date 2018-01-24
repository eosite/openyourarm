package com.glaf.base.modules.sys.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.sys.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_DEPARTMENT_CORRELATION")
public class DepartmentCorrelation implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * DeptId
         */
        @Column(name = "DEPTID_")	 
        protected Long deptId;

        /**
         * NAME
         */
        @Column(name = "NAME_", length=250) 
        protected String name;

        /**
         * Code
         */
        @Column(name = "CODE_", length=250) 
        protected String code;

        /**
         * SysId
         */
        @Column(name = "SYSID_", length=50) 
        protected String sysId;

        /**
         * CreateBy
         */
        @Column(name = "CREATEBY_", length=50) 
        protected String createBy;

        /**
         * CreateTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * UpdateBy
         */
        @Column(name = "UPDATEBY_", length=50) 
        protected String updateBy;

        /**
         * UpdateTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;


         
	public DepartmentCorrelation() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public Long getDeptId(){
	    return this.deptId;
	}
        public String getName(){
	    return this.name;
	}
        public String getCode(){
	    return this.code;
	}
        public String getSysId(){
	    return this.sysId;
	}
        public String getCreateBy(){
	    return this.createBy;
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
        public String getUpdateBy(){
	    return this.updateBy;
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


        public void setDeptId(Long deptId) {
	    this.deptId = deptId; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setCode(String code) {
	    this.code = code; 
	}
        public void setSysId(String sysId) {
	    this.sysId = sysId; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setCreateTime(Date createTime) {
	    this.createTime = createTime; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
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
		DepartmentCorrelation other = (DepartmentCorrelation) obj;
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


	public DepartmentCorrelation jsonToObject(JSONObject jsonObject) {
            return DepartmentCorrelationJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return DepartmentCorrelationJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return DepartmentCorrelationJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
