package com.glaf.base.modules.uis.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.uis.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "UIS_APP_USER")
public class UisAppUser implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * userId
         */
        @Column(name = "USER_ID_", length=50) 
        protected String userId;

        /**
         * appId
         */
        @Column(name = "APP_ID_", length=50) 
        protected String appId;

        /**
         * userName
         */
        @Column(name = "USER_NAME_", length=50) 
        protected String userName;

        /**
         * email
         */
        @Column(name = "EMAIL_", length=50) 
        protected String email;

        /**
         * tel
         */
        @Column(name = "TEL_", length=30) 
        protected String tel;

        /**
         * mobile
         */
        @Column(name = "MOBILE_", length=30) 
        protected String mobile;

        /**
         * age
         */
        @Column(name = "AGE_")
        protected Integer age;

        /**
         * desc
         */
	@javax.persistence.Lob
        @Column(name = "DESC_") 
	protected String desc;

        /**
         * sex
         */
        @Column(name = "SEX_")
        protected Integer sex;

        /**
         * qq
         */
        @Column(name = "QQ_", length=20) 
        protected String qq;

        /**
         * weq
         */
        @Column(name = "WEQ_", length=50) 
        protected String weq;

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


         
	public UisAppUser() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getUserId(){
	    return this.userId;
	}
        public String getAppId(){
	    return this.appId;
	}
        public String getUserName(){
	    return this.userName;
	}
        public String getEmail(){
	    return this.email;
	}
        public String getTel(){
	    return this.tel;
	}
        public String getMobile(){
	    return this.mobile;
	}
        public Integer getAge(){
	    return this.age;
	}
	public String getDesc(){
	    return this.desc;
	}
        public Integer getSex(){
	    return this.sex;
	}
        public String getQq(){
	    return this.qq;
	}
        public String getWeq(){
	    return this.weq;
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


        public void setUserId(String userId) {
	    this.userId = userId; 
	}
        public void setAppId(String appId) {
	    this.appId = appId; 
	}
        public void setUserName(String userName) {
	    this.userName = userName; 
	}
        public void setEmail(String email) {
	    this.email = email; 
	}
        public void setTel(String tel) {
	    this.tel = tel; 
	}
        public void setMobile(String mobile) {
	    this.mobile = mobile; 
	}
        public void setAge(Integer age) {
	    this.age = age; 
	}
	public void setDesc(String desc) {
	    this.desc = desc; 
	}
        public void setSex(Integer sex) {
	    this.sex = sex; 
	}
        public void setQq(String qq) {
	    this.qq = qq; 
	}
        public void setWeq(String weq) {
	    this.weq = weq; 
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
		UisAppUser other = (UisAppUser) obj;
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


	public UisAppUser jsonToObject(JSONObject jsonObject) {
            return UisAppUserJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return UisAppUserJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return UisAppUserJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
