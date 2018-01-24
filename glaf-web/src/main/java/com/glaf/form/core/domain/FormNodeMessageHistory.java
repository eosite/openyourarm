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
@Table(name = "FORM_NODE_MESSAGE_HISTORY")
public class FormNodeMessageHistory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected Long id;

        /**
         * 号码
         */
        @Column(name = "TELEPHONE_", length=30) 
        protected String telephone;

        /**
         * 短信信息
         */
        @Column(name = "MSG_", length=500) 
        protected String msg;

        /**
         * 状态
         */
        @Column(name = "STATE_")
        protected Integer state;

        /**
         * 创建人
         */
        @Column(name = "CREATOR_", length=50) 
        protected String creator;

        /**
         * 创建时间
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE_")	
        protected Date createDate;

        /**
         * 执行信息
         */
        @Column(name = "RESULT_", length=100) 
        protected String result;


         
	public FormNodeMessageHistory() {

	}

	public Long getId(){
	    return this.id;
	}

	public void setId(Long id) {
	    this.id = id; 
	}


        public String getTelephone(){
	    return this.telephone;
	}
        public String getMsg(){
	    return this.msg;
	}
        public Integer getState(){
	    return this.state;
	}
        public String getCreator(){
	    return this.creator;
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
        public String getResult(){
	    return this.result;
	}


        public void setTelephone(String telephone) {
	    this.telephone = telephone; 
	}
        public void setMsg(String msg) {
	    this.msg = msg; 
	}
        public void setState(Integer state) {
	    this.state = state; 
	}
        public void setCreator(String creator) {
	    this.creator = creator; 
	}
        public void setCreateDate(Date createDate) {
	    this.createDate = createDate; 
	}
        public void setResult(String result) {
	    this.result = result; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormNodeMessageHistory other = (FormNodeMessageHistory) obj;
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


	public FormNodeMessageHistory jsonToObject(JSONObject jsonObject) {
            return FormNodeMessageHistoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return FormNodeMessageHistoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return FormNodeMessageHistoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
