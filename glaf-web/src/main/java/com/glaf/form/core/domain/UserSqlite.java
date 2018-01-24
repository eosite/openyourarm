package com.glaf.form.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.UserSqliteJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "USER_SQLITE_")
public class UserSqlite implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 代码
         */
        @Column(name = "USERID_", length=50) 
        protected String userId;

        /**
         * 名称
         */
        @Column(name = "NAME_", length=50) 
        protected String name;

        /**
         * 描述
         */
        @Column(name = "DESC_", length=100) 
        protected String desc;

        /**
         * 代码
         */
        @Column(name = "SQLITECODE_", length=50) 
        protected String sqliteCode;

        /**
         * 规则信息
         */
        @Lob
        @Column(name = "RULEJSON_") 
        protected String ruleJson;

        /**
         * 创建人
         */
        @Column(name = "CREATEBY_", length=50) 
        protected String createBy;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE_")	
        protected Date createDate;

        /**
         * 修改人
         */
        @Column(name = "UPDATEBY_", length=50) 
        protected String updateBy;

        /**
         * 修改时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE_")	
        protected Date updateDate;

        /**
         * 文件生成时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "FILEDATE_")	
        protected Date fileDate;

        /**
         * 下载数量
         */
        @Column(name = "DOWNLOADNUM_")
        protected Integer downloadNum;

        /**
         * 状态
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * 错误信息
         */
        @Lob
        @Column(name = "ERROR_MESSAGE_") 
        protected String errorMessage;


         
	public UserSqlite() {

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
        public String getName(){
	    return this.name;
	}
        public String getDesc(){
	    return this.desc;
	}
        public String getSqliteCode(){
	    return this.sqliteCode;
	}
        public String getRuleJson(){
	    return this.ruleJson;
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
	public Date getFileDate(){
	    return this.fileDate;
	}
	public String getFileDateString(){
	    if(this.fileDate != null){
	        return DateUtils.getDateTime(this.fileDate);
	    }
	    return "";
	}
        public Integer getDownloadNum(){
	    return this.downloadNum;
	}
        public Integer getStatus(){
	    return this.status;
	}
        public String getErrorMessage(){
	    return this.errorMessage;
	}


        public void setUserId(String userId) {
	    this.userId = userId; 
	}
        public void setName(String name) {
	    this.name = name; 
	}
        public void setDesc(String desc) {
	    this.desc = desc; 
	}
        public void setSqliteCode(String sqliteCode) {
	    this.sqliteCode = sqliteCode; 
	}
        public void setRuleJson(String ruleJson) {
	    this.ruleJson = ruleJson; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}
        public void setCreateDate(Date createDate) {
	    this.createDate = createDate; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}
        public void setUpdateDate(Date updateDate) {
	    this.updateDate = updateDate; 
	}
        public void setFileDate(Date fileDate) {
	    this.fileDate = fileDate; 
	}
        public void setDownloadNum(Integer downloadNum) {
	    this.downloadNum = downloadNum; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setErrorMessage(String errorMessage) {
	    this.errorMessage = errorMessage; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSqlite other = (UserSqlite) obj;
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


	public UserSqlite jsonToObject(JSONObject jsonObject) {
            return UserSqliteJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return UserSqliteJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return UserSqliteJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
