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
@Table(name = "SENDDOCAPPLY")
public class SendDocApply implements Serializable, JSONable {
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

        @Column(name = "DOCNO", length=20) 
        protected String docNo;

        @Column(name = "DOCTYPE", length=20) 
        protected String docType;

        @Column(name = "DRAFTNAME", length=30) 
        protected String draftName;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "DRAFTDATE")	
        protected Date draftDate;

        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "SENDDOCDATE")	
        protected Date sendDocDate;

        @Column(name = "SERIALNUMBER", length=20) 
        protected String serialNumber;

        @Column(name = "FROMCOMPANY", length=50) 
        protected String fromCompany;

        @Column(name = "KEYWORDS", length=50) 
        protected String keywords;

        @Column(name = "DOCTOCOMPANY", length=30) 
        protected String docToCompany;

        @Column(name = "DOCCCCOMPANY", length=30) 
        protected String docCCCompany;

        @Column(name = "CHECKOPINION", length=2000) 
        protected String checkOpinion;

        @Column(name = "COUNTERSIGNOPINION", length=2000) 
        protected String countersignOpinion;

        @Column(name = "SIGNANDISSUEOPINION", length=2000) 
        protected String signAndIssueOpinion;

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


         
	public SendDocApply() {

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
        public String getDocNo(){
	    return this.docNo;
	}
        public String getDocType(){
	    return this.docType;
	}
        public String getDraftName(){
	    return this.draftName;
	}
	public Date getDraftDate(){
	    return this.draftDate;
	}
	public String getDraftDateString(){
	    if(this.draftDate != null){
	        return DateUtils.getDateTime(this.draftDate);
	    }
	    return "";
	}
	public Date getSendDocDate(){
	    return this.sendDocDate;
	}
	public String getSendDocDateString(){
	    if(this.sendDocDate != null){
	        return DateUtils.getDateTime(this.sendDocDate);
	    }
	    return "";
	}
        public String getSerialNumber(){
	    return this.serialNumber;
	}
        public String getFromCompany(){
	    return this.fromCompany;
	}
        public String getKeywords(){
	    return this.keywords;
	}
        public String getDocToCompany(){
	    return this.docToCompany;
	}
        public String getDocCCCompany(){
	    return this.docCCCompany;
	}
        public String getCheckOpinion(){
	    return this.checkOpinion;
	}
        public String getCountersignOpinion(){
	    return this.countersignOpinion;
	}
        public String getSignAndIssueOpinion(){
	    return this.signAndIssueOpinion;
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
        public void setDocNo(String docNo) {
	    this.docNo = docNo; 
	}
        public void setDocType(String docType) {
	    this.docType = docType; 
	}
        public void setDraftName(String draftName) {
	    this.draftName = draftName; 
	}
        public void setDraftDate(Date draftDate) {
	    this.draftDate = draftDate; 
	}
        public void setSendDocDate(Date sendDocDate) {
	    this.sendDocDate = sendDocDate; 
	}
        public void setSerialNumber(String serialNumber) {
	    this.serialNumber = serialNumber; 
	}
        public void setFromCompany(String fromCompany) {
	    this.fromCompany = fromCompany; 
	}
        public void setKeywords(String keywords) {
	    this.keywords = keywords; 
	}
        public void setDocToCompany(String docToCompany) {
	    this.docToCompany = docToCompany; 
	}
        public void setDocCCCompany(String docCCCompany) {
	    this.docCCCompany = docCCCompany; 
	}
        public void setCheckOpinion(String checkOpinion) {
	    this.checkOpinion = checkOpinion; 
	}
        public void setCountersignOpinion(String countersignOpinion) {
	    this.countersignOpinion = countersignOpinion; 
	}
        public void setSignAndIssueOpinion(String signAndIssueOpinion) {
	    this.signAndIssueOpinion = signAndIssueOpinion; 
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
		SendDocApply other = (SendDocApply) obj;
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
	public SendDocApply jsonToObject(JSONObject jsonObject) {
            return SendDocApplyJsonFactory.jsonToObject(jsonObject);
	}


	@Override
	public JSONObject toJsonObject() {
            return SendDocApplyJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return SendDocApplyJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
