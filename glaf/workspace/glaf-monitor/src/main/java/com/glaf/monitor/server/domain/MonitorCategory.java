package com.glaf.monitor.server.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.monitor.server.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "MONITOR_CATEGORY")
public class MonitorCategory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Integer id;

        /**
         * 分类名称
         */
        @Column(name = "NAME_", length=50) 
        protected String name;

        /**
         * 代码
         */
        @Column(name = "CODE_", length=30) 
        protected String code;

        /**
         * 上级分类
         */
        @Column(name = "PID_")
        protected Integer pid;

        /**
         * TREEID
         */
        @Column(name = "TREEID_", length=300) 
        protected String treeid;

        /**
         * CREATEBY_
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createby;

        /**
         * CREATETIME_
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createtime;

        /**
         * UPDATEBY_
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateby;

        /**
         * UPDATETIME_
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updatetime;

        /**
         * DELETE_FLAG_
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag;


         
	public MonitorCategory() {

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
        public String getCode(){
	    return this.code;
	}
        public Integer getPid(){
	    return this.pid;
	}
        public String getTreeid(){
	    return this.treeid;
	}
        public String getCreateby(){
	    return this.createby;
	}
	public Date getCreatetime(){
	    return this.createtime;
	}
	public String getCreatetimeString(){
	    if(this.createtime != null){
	        return DateUtils.getDateTime(this.createtime);
	    }
	    return "";
	}
        public String getUpdateby(){
	    return this.updateby;
	}
	public Date getUpdatetime(){
	    return this.updatetime;
	}
	public String getUpdatetimeString(){
	    if(this.updatetime != null){
	        return DateUtils.getDateTime(this.updatetime);
	    }
	    return "";
	}
        public Integer getDeleteFlag(){
	    return this.deleteFlag;
	}


        public void setName(String name) {
	    this.name = name; 
	}
        public void setCode(String code) {
	    this.code = code; 
	}
        public void setPid(Integer pid) {
	    this.pid = pid; 
	}
        public void setTreeid(String treeid) {
	    this.treeid = treeid; 
	}
        public void setCreateby(String createby) {
	    this.createby = createby; 
	}
        public void setCreatetime(Date createtime) {
	    this.createtime = createtime; 
	}
        public void setUpdateby(String updateby) {
	    this.updateby = updateby; 
	}
        public void setUpdatetime(Date updatetime) {
	    this.updatetime = updatetime; 
	}
        public void setDeleteFlag(Integer deleteFlag) {
	    this.deleteFlag = deleteFlag; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MonitorCategory other = (MonitorCategory) obj;
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


	public MonitorCategory jsonToObject(JSONObject jsonObject) {
            return MonitorCategoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return MonitorCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return MonitorCategoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
