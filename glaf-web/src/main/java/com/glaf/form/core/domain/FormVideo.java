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
@Table(name = "FORM_VIDEO")
public class FormVideo implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * NAME
         */
        @Column(name = "NAME_", length=30) 
        protected String name;

        /**
         * ip
         */
        @Column(name = "IP_", length=30) 
        protected String ip;

        /**
         * port
         */
        @Column(name = "PORT_", length=20) 
        protected String port;

        /**
         * status
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * valided
         */
        @Column(name = "VALIDED_")
        protected Integer valided;

        /**
         * userName
         */
        @Column(name = "USERNAME_", length=30) 
        protected String userName;

        /**
         * pwd
         */
        @Column(name = "PWD_", length=30) 
        protected String pwd;

        /**
         * uPDATEBY
         */
        @Column(name = "UPDATEBY_", length=30) 
        protected String updateBy;

        /**
         * CREATEDATE
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE_")	
        protected Date createDate;

        /**
         * uPDATEDATE
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE_")	
        protected Date updateDate;

        /**
         * CREATEBY
         */
        @Column(name = "CREATEBY_", length=30) 
        protected String createBy;


         
	public FormVideo() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public String getName(){
	    return this.name;
	}
        public String getIp(){
	    return this.ip;
	}
        public String getPort(){
	    return this.port;
	}
        public Integer getStatus(){
	    return this.status;
	}
        public Integer getValided(){
	    return this.valided;
	}
        public String getUserName(){
	    return this.userName;
	}
        public String getPwd(){
	    return this.pwd;
	}
        public String getUpdateBy(){
	    return this.updateBy;
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
	public Date getUpdateDate(){
	    return this.updateDate;
	}
	public String getUpdateDateString(){
	    if(this.updateDate != null){
	        return DateUtils.getDateTime(this.updateDate);
	    }
	    return "";
	}
        public String getCreateBy(){
	    return this.createBy;
	}


        public void setName(String name) {
	    this.name = name; 
	}
        public void setIp(String ip) {
	    this.ip = ip; 
	}
        public void setPort(String port) {
	    this.port = port; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setValided(Integer valided) {
	    this.valided = valided; 
	}
        public void setUserName(String userName) {
	    this.userName = userName; 
	}
        public void setPwd(String pwd) {
	    this.pwd = pwd; 
	}
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}
        public void setCreateDate(Date createDate) {
	    this.createDate = createDate; 
	}
        public void setUpdateDate(Date updateDate) {
	    this.updateDate = updateDate; 
	}
        public void setCreateBy(String createBy) {
	    this.createBy = createBy; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormVideo other = (FormVideo) obj;
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


	public FormVideo jsonToObject(JSONObject jsonObject) {
            return FormVideoJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormVideoJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormVideoJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
