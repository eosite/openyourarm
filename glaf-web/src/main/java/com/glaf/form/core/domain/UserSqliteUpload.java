package com.glaf.form.core.domain;

import java.io.Serializable;
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
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.UserSqliteUploadJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "USER_SQLITE_UPLOAD_")
public class UserSqliteUpload implements Serializable, JSONable {
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
         * 代码
         */
        @Column(name = "FILENAME_", length=50) 
        protected String fileName;

        /**
         * 导入的文件位置
         */
        @Column(name = "FILEPATH_", length=50) 
        protected String filePath;

        /**
         * 日志文件位置
         */
        @Column(name = "LOGFILEPATH_", length=50) 
        protected String logfilePath;

        /**
         * 状态
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * 文件导入时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPLOADDATE_")	
        protected Date uploadDate;


         
	public UserSqliteUpload() {

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
        public String getFileName(){
	    return this.fileName;
	}
        public String getFilePath(){
	    return this.filePath;
	}
        public String getLogfilePath(){
	    return this.logfilePath;
	}
        public Integer getStatus(){
	    return this.status;
	}
	public Date getUploadDate(){
	    return this.uploadDate;
	}
	public String getUploadDateString(){
	    if(this.uploadDate != null){
	        return DateUtils.getDateTime(this.uploadDate);
	    }
	    return "";
	}


        public void setUserId(String userId) {
	    this.userId = userId; 
	}
        public void setFileName(String fileName) {
	    this.fileName = fileName; 
	}
        public void setFilePath(String filePath) {
	    this.filePath = filePath; 
	}
        public void setLogfilePath(String logfilePath) {
	    this.logfilePath = logfilePath; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setUploadDate(Date uploadDate) {
	    this.uploadDate = uploadDate; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserSqliteUpload other = (UserSqliteUpload) obj;
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


	public UserSqliteUpload jsonToObject(JSONObject jsonObject) {
            return UserSqliteUploadJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return UserSqliteUploadJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return UserSqliteUploadJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
