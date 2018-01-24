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
@Table(name = "MSGVALIDHISTORY_")
public class MsgValidHistory implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID_", nullable = false)
        protected String id;

        /**
         * 电话号码
         */
        @Column(name = "TELEPHONE_", length=50) 
        protected String telephone;

        /**
         * SENDDATE_
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "SENDDATE_")	
        protected Date sendDate;

        /**
         * 类型
         */
        @Column(name = "TYPE_")
        protected Integer type;

        /**
         * 类型名称
         */
        @Column(name = "TYPENAME_", length=50) 
        protected String typeName;

        /**
         * 发送url
         */
        @Column(name = "URL_", length=600) 
        protected String url;

        /**
         * 短信内容
         */
        @Column(name = "MSG_", length=500) 
        protected String msg;

        /**
         * 发送状态
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * 状态名称
         */
        @Column(name = "STATUSNAME_", length=500) 
        protected String statusName;

        /**
         * 发送结果
         */
        @Column(name = "RESULT_", length=100) 
        protected String result;


         
	public MsgValidHistory() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getTelephone(){
	    return this.telephone;
	}
	public Date getSendDate(){
	    return this.sendDate;
	}
	public String getSendDateString(){
	    if(this.sendDate != null){
	        return DateUtils.getDateTime(this.sendDate);
	    }
	    return "";
	}
        public Integer getType(){
	    return this.type;
	}
        public String getTypeName(){
	    return this.typeName;
	}
        public String getUrl(){
	    return this.url;
	}
        public String getMsg(){
	    return this.msg;
	}
        public Integer getStatus(){
	    return this.status;
	}
        public String getStatusName(){
	    return this.statusName;
	}
        public String getResult(){
	    return this.result;
	}


        public void setTelephone(String telephone) {
	    this.telephone = telephone; 
	}
        public void setSendDate(Date sendDate) {
	    this.sendDate = sendDate; 
	}
        public void setType(Integer type) {
	    this.type = type; 
	}
        public void setTypeName(String typeName) {
	    this.typeName = typeName; 
	}
        public void setUrl(String url) {
	    this.url = url; 
	}
        public void setMsg(String msg) {
	    this.msg = msg; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setStatusName(String statusName) {
	    this.statusName = statusName; 
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
		MsgValidHistory other = (MsgValidHistory) obj;
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


	public MsgValidHistory jsonToObject(JSONObject jsonObject) {
            return MsgValidHistoryJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return MsgValidHistoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return MsgValidHistoryJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
