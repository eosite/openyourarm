package com.glaf.theme.domain;

import java.io.*;
import java.sql.Blob;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.theme.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_THEME_TMP_")
public class SysThemeTmp implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "THEME_TMP_ID_", nullable = false)
        protected String themeTmpId;

        /**
         * 模板名称
         */
        @Column(name = "THEME_TMP_NAME_", length=50) 
        protected String themeTmpName;

        /**
         * 模板代码
         */
        @Column(name = "THEME_TMP_CODE_", length=30) 
        protected String themeTmpCode;

        /**
         * 缩略图
         */
        @Lob
        @Column(name = "THUMBNAIL_")  
        protected byte[] thumbnail;

        /**
         * UI
         */
        @Column(name = "UI_", length=20) 
        protected String ui;

        /**
         * 创建人
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createBy;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATETIME_")	
        protected Date createTime;

        /**
         * 修改人
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateBy;

        /**
         * 修改时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATETIME_")	
        protected Date updateTime;

        /**
         * 删除标识
         */
        @Column(name = "DELETE_FLAG_")
        protected Integer deleteFlag;

        /**
         * 发布者
         */
        @Column(name = "PUBLISHER_", length=30) 
        protected String publisher;

        /**
         * 发布时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "PUBLISH_TIME_")	
        protected Date publishTime;

        /**
         * 状态
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * 版本
         */
        @Column(name = "VER_")
        protected Integer ver;

        /**
         * 默认主题
         */
        @Column(name = "DEFAULT_FLAG_")
        protected Integer defaultFlag;
        
	public SysThemeTmp() {

	}

	public Integer getDefaultFlag() {
		return defaultFlag;
	}

	public void setDefaultFlag(Integer defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getThemeTmpId(){
	    return this.themeTmpId;
	}

	public void setThemeTmpId(String themeTmpId) {
	    this.themeTmpId = themeTmpId; 
	}


        public String getThemeTmpName(){
	    return this.themeTmpName;
	}
        public String getThemeTmpCode(){
	    return this.themeTmpCode;
	}
        public byte[] getThumbnail(){
	    return this.thumbnail;
	}
        public String getUi(){
	    return this.ui;
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
        public String getPublisher(){
	    return this.publisher;
	}
	public Date getPublishTime(){
	    return this.publishTime;
	}
	public String getPublishTimeString(){
	    if(this.publishTime != null){
	        return DateUtils.getDateTime(this.publishTime);
	    }
	    return "";
	}
        public Integer getStatus(){
	    return this.status;
	}
        public Integer getVer(){
	    return this.ver;
	}


        public void setThemeTmpName(String themeTmpName) {
	    this.themeTmpName = themeTmpName; 
	}
        public void setThemeTmpCode(String themeTmpCode) {
	    this.themeTmpCode = themeTmpCode; 
	}
        public void setThumbnail(byte[] thumbnail) {
	    this.thumbnail = thumbnail; 
	}
        public void setUi(String ui) {
	    this.ui = ui; 
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
        public void setPublisher(String publisher) {
	    this.publisher = publisher; 
	}
        public void setPublishTime(Date publishTime) {
	    this.publishTime = publishTime; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setVer(Integer ver) {
	    this.ver = ver; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SysThemeTmp other = (SysThemeTmp) obj;
		if (themeTmpId == null) {
			if (other.themeTmpId != null)
				return false;
		} else if (!themeTmpId.equals(other.themeTmpId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((themeTmpId == null) ? 0 : themeTmpId.hashCode());
		return result;
	}


	public SysThemeTmp jsonToObject(JSONObject jsonObject) {
            return SysThemeTmpJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return SysThemeTmpJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SysThemeTmpJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
