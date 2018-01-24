package com.glaf.oa.doc.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.oa.doc.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "RECEIVEDOCAPPLY")
public class ReceiveDocApply implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID", nullable = false)
        protected Integer id;

        @Column(name = "SUBJECT", length=50) 
        protected String subject;

        @Column(name = "SECURITYLEVEL")
        protected Integer securityLevel;

        @Column(name = "URGENCYLEVEL")
        protected Integer urgencyLevel;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "RECEIVEDOCTIME")	
        protected Date receiveDocTime;

        @Column(name = "DOCTYPE", length=20) 
        protected String docType;

        @Column(name = "FROMCOMPANY", length=50) 
        protected String fromCompany;

        @Column(name = "SERIALNUMBER", length=20) 
        protected String serialNumber;

        @Column(name = "FROMDOCNO", length=20) 
        protected String fromDocNo;

        @Column(name = "RECEIVEDOCNO", length=20) 
        protected String receiveDocNo;

        @Column(name = "DISTRIBUTECOMPANY", length=50) 
        protected String distributeCompany;

        @Column(name = "NIBANOPTION", length=2000) 
        protected String nibanOption;

        @Column(name = "LEADOPTION", length=2000) 
        protected String leadOption;

        @Column(name = "CHENGBANOPTION", length=2000) 
        protected String chengbanOption;

        @Column(name = "REMARK", length=2000) 
        protected String remark;

        @Column(name = "STATUS")
        protected Integer status;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE")	
        protected Date createDate;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE")	
        protected Date updateDate;

        @Column(name = "CREATEBY", length=50) 
        protected String createBy;

        @Column(name = "UPDATEBY", length=50) 
        protected String updateBy;


         
	public ReceiveDocApply() {

	}

	public Integer getId(){
	    return this.id;
	}

	public void setId(Integer id) {
	    this.id = id; 
	}


        public String getSubject(){
	    return this.subject;
	}
        public Integer getSecurityLevel(){
	    return this.securityLevel;
	}
        public Integer getUrgencyLevel(){
	    return this.urgencyLevel;
	}
	public Date getReceiveDocTime(){
	    return this.receiveDocTime;
	}
	public String getReceiveDocTimeString(){
	    if(this.receiveDocTime != null){
	        return DateUtils.getDateTime(this.receiveDocTime);
	    }
	    return "";
	}
        public String getDocType(){
	    return this.docType;
	}
        public String getFromCompany(){
	    return this.fromCompany;
	}
        public String getSerialNumber(){
	    return this.serialNumber;
	}
        public String getFromDocNo(){
	    return this.fromDocNo;
	}
        public String getReceiveDocNo(){
	    return this.receiveDocNo;
	}
        public String getDistributeCompany(){
	    return this.distributeCompany;
	}
        public String getNibanOption(){
	    return this.nibanOption;
	}
        public String getLeadOption(){
	    return this.leadOption;
	}
        public String getChengbanOption(){
	    return this.chengbanOption;
	}
        public String getRemark(){
	    return this.remark;
	}
        public Integer getStatus(){
	    return this.status;
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
        public String getUpdateBy(){
	    return this.updateBy;
	}


        public void setSubject(String subject) {
	    this.subject = subject; 
	}
        public void setSecurityLevel(Integer securityLevel) {
	    this.securityLevel = securityLevel; 
	}
        public void setUrgencyLevel(Integer urgencyLevel) {
	    this.urgencyLevel = urgencyLevel; 
	}
        public void setReceiveDocTime(Date receiveDocTime) {
	    this.receiveDocTime = receiveDocTime; 
	}
        public void setDocType(String docType) {
	    this.docType = docType; 
	}
        public void setFromCompany(String fromCompany) {
	    this.fromCompany = fromCompany; 
	}
        public void setSerialNumber(String serialNumber) {
	    this.serialNumber = serialNumber; 
	}
        public void setFromDocNo(String fromDocNo) {
	    this.fromDocNo = fromDocNo; 
	}
        public void setReceiveDocNo(String receiveDocNo) {
	    this.receiveDocNo = receiveDocNo; 
	}
        public void setDistributeCompany(String distributeCompany) {
	    this.distributeCompany = distributeCompany; 
	}
        public void setNibanOption(String nibanOption) {
	    this.nibanOption = nibanOption; 
	}
        public void setLeadOption(String leadOption) {
	    this.leadOption = leadOption; 
	}
        public void setChengbanOption(String chengbanOption) {
	    this.chengbanOption = chengbanOption; 
	}
        public void setRemark(String remark) {
	    this.remark = remark; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
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
        public void setUpdateBy(String updateBy) {
	    this.updateBy = updateBy; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ReceiveDocApply other = (ReceiveDocApply) obj;
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


	@Override
	public ReceiveDocApply jsonToObject(JSONObject jsonObject) {
            return ReceiveDocApplyJsonFactory.jsonToObject(jsonObject);
	}


	@Override
	public JSONObject toJsonObject() {
            return ReceiveDocApplyJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ReceiveDocApplyJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
