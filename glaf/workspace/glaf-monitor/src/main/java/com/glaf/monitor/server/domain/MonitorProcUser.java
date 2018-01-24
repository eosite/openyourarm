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
@Table(name = "MONITOR_PROC_USER")
public class MonitorProcUser implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 程序
         */
        @Column(name = "PROC_ID_", length=50) 
        protected String procId;

        /**
         * 角色
         */
        @Column(name = "ROLE_", length=30) 
        protected String role;

        /**
         * 姓名
         */
        @Column(name = "USERNAME_", length=30) 
        protected String username;

        /**
         * 工作电话
         */
        @Column(name = "TEL_", length=30) 
        protected String tel;

        /**
         * 手机号码
         */
        @Column(name = "PHONE_", length=20) 
        protected String phone;

        /**
         * 邮箱地址
         */
        @Column(name = "EMAIL_", length=50) 
        protected String email;

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


         
	public MonitorProcUser() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getProcId(){
	    return this.procId;
	}
        public String getRole(){
	    return this.role;
	}
        public String getUsername(){
	    return this.username;
	}
        public String getTel(){
	    return this.tel;
	}
        public String getPhone(){
	    return this.phone;
	}
        public String getEmail(){
	    return this.email;
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


        public void setProcId(String procId) {
	    this.procId = procId; 
	}
        public void setRole(String role) {
	    this.role = role; 
	}
        public void setUsername(String username) {
	    this.username = username; 
	}
        public void setTel(String tel) {
	    this.tel = tel; 
	}
        public void setPhone(String phone) {
	    this.phone = phone; 
	}
        public void setEmail(String email) {
	    this.email = email; 
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
		MonitorProcUser other = (MonitorProcUser) obj;
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


	public MonitorProcUser jsonToObject(JSONObject jsonObject) {
            return MonitorProcUserJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return MonitorProcUserJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return MonitorProcUserJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
