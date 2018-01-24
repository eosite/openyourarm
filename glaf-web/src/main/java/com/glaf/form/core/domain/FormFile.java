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
@Table(name = "FORM_FILE_")
public class FormFile implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 附件信息
         */
        @Column(name = "PARENT_", length=50) 
        protected String parent;

        /**
         * 保存类型
         */
        @Column(name = "TYPE_", length=1) 
        protected String type;

        /**
         * 文件类型
         */
        @Column(name = "FILETYPE_", length=20) 
        protected String fieldType;

        /**
         * 文件名称
         */
        @Column(name = "FILENAME_", length=100) 
        protected String fileName;

        /**
         * 文件大小
         */
        @Column(name = "FILESIZE_")
        protected long fileSize;

        /**
         * 文件内容
         */
        @Lob
        @Column(name = "FILECONTENT_") 
        protected byte[] fileContent;

        /**
         * 保存位置
         */
        @Column(name = "SAVESERVICEPATH_", length=1000) 
        protected String saveServicePath;

        /**
         * 版本
         */
        @Column(name = "VISION_", length=50)
        protected String vision;

        /**
         * 状态
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * 错误信息
         */
        @Column(name = "ERROR", length=255) 
        protected String error;

        /**
         * 业务字段
         */
        @Column(name = "BUSINESS", length=50) 
        protected String business;

        /**
         * 创建人
         */
        @Column(name = "CREATEBY", length=50) 
        protected String createBy;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE")	
        protected Date createDate;

        /**
         * 修改人
         */
        @Column(name = "UPDATEBY", length=50) 
        protected String updateBy;

        /**
         * 修改时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE")	
        protected Date updateDate;


         
	public FormFile() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getParent(){
	    return this.parent;
	}
        public String getType(){
	    return this.type;
	}
        public String getFieldType(){
	    return this.fieldType;
	}
        public String getFileName(){
	    return this.fileName;
	}
        public long getFileSize(){
	    return this.fileSize;
	}
        public byte[] getFileContent(){
	    return this.fileContent;
	}
        public String getSaveServicePath(){
	    return this.saveServicePath;
	}
        public String getVision(){
	    return this.vision;
	}
        public Integer getStatus(){
	    return this.status;
	}
        public String getError(){
	    return this.error;
	}
        public String getBusiness(){
	    return this.business;
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


        public void setParent(String parent) {
	    this.parent = parent; 
	}
        public void setType(String type) {
	    this.type = type; 
	}
        public void setFieldType(String fieldType) {
	    this.fieldType = fieldType; 
	}
        public void setFileName(String fileName) {
	    this.fileName = fileName; 
	}
        public void setFileSize(long fileSize) {
	    this.fileSize = fileSize; 
	}
        public void setFileContent(byte[] fileContent) {
	    this.fileContent = fileContent; 
	}
        public void setSaveServicePath(String saveServicePath) {
	    this.saveServicePath = saveServicePath; 
	}
        public void setVision(String vision) {
	    this.vision = vision; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setError(String error) {
	    this.error = error; 
	}
        public void setBusiness(String business) {
	    this.business = business; 
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


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormFile other = (FormFile) obj;
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


	public FormFile jsonToObject(JSONObject jsonObject) {
            return FormFileJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormFileJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormFileJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
