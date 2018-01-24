package com.glaf.base.modules.uis.domain;

import java.io.Serializable;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.modules.uis.util.UisAppRegisterJsonFactory;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "UIS_APP_REGISTER")
public class UisAppRegister implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "APP_ID_", nullable = false)
        protected String appId;

        /**
         * appName
         */
        @Column(name = "APP_NAME_", length=150) 
        protected String appName;

        /**
         * loginAddress
         */
        @Column(name = "LOGIN_ADDRESS_", length=300) 
        protected String loginAddress;

        /**
         * ssoServiceId
         */
        @Column(name = "SSO_SERVICE_ID_", length=300) 
        protected String ssoServiceId;

        /**
         * desc
         */
	@javax.persistence.Lob
        @Column(name = "DESC_") 
	protected String desc;

        /**
         * logoPic
         */
        @Column(name = "LOGO_PIC_")  
        protected Blob logoPic;

        /**
         * status
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * createBy
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createBy;

        /**
         * createTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * updateBy
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateBy;

        /**
         * updateTime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;

        /**
         * deleteFlag
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag;


         
	public UisAppRegister() {

	}

	public String getAppId(){
	    return this.appId;
	}

	public void setAppId(String appId) {
	    this.appId = appId; 
	}


        public String getAppName(){
	    return this.appName;
	}
        public String getLoginAddress(){
	    return this.loginAddress;
	}
        public String getSsoServiceId(){
	    return this.ssoServiceId;
	}
	public String getDesc(){
	    return this.desc;
	}
        public Blob getLogoPic(){
	    return this.logoPic;
	}
        public Integer getStatus(){
	    return this.status;
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
        public Integer getDeleteFlag(){
	    return this.deleteFlag;
	}


        public void setAppName(String appName) {
	    this.appName = appName; 
	}
        public void setLoginAddress(String loginAddress) {
	    this.loginAddress = loginAddress; 
	}
        public void setSsoServiceId(String ssoServiceId) {
	    this.ssoServiceId = ssoServiceId; 
	}
	public void setDesc(String desc) {
	    this.desc = desc; 
	}
        public void setLogoPic(Blob logoPic) {
	    this.logoPic = logoPic; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
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
		UisAppRegister other = (UisAppRegister) obj;
		if (appId == null) {
			if (other.appId != null)
				return false;
		} else if (!appId.equals(other.appId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((appId == null) ? 0 : appId.hashCode());
		return result;
	}


	public UisAppRegister jsonToObject(JSONObject jsonObject) {
            return UisAppRegisterJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return UisAppRegisterJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return UisAppRegisterJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
