package com.glaf.sys.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.sys.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PROJ_MUIPROJLIST")
public class ProjMuiprojlist implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "INDEX_ID", nullable = false)
        protected Integer indexId;

        /**
         * iD
         */
        @Column(name = "ID", length=100) 
        protected String id;

        /**
         * iNTFLAG
         */
        @Column(name = "INTFLAG")
        protected Integer intFlag;

        /**
         * sYSID
         */
        @Column(name = "SYS_ID", length=50) 
        protected String sysId;

        /**
         * pROJNAME
         */
        @Column(name = "PROJNAME", length=250) 
        protected String projName;

        /**
         * nUM
         */
        @Column(name = "NUM", length=50) 
        protected String num;

        /**
         * cTIME
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CTIME")	
        protected Date cTime;

        /**
         * cONTENT
         */
        @Column(name = "CONTENT", length=200) 
        protected String content;

        /**
         * sDBNAME
         */
        @Column(name = "SDBNAME", length=100) 
        protected String sDbName;

        /**
         * sSERVERNAME
         */
        @Column(name = "SSERVERNAME", length=150) 
        protected String sServerName;

        /**
         * sUSER
         */
        @Column(name = "SUSER", length=100) 
        protected String sUser;

        /**
         * sPASSWORD
         */
        @Column(name = "SPASSWORD", length=50) 
        protected String spassword;

        /**
         * lISTNO
         */
        @Column(name = "LISTNO")
        protected Integer listNo;

        /**
         * eMAIL
         */
        @Column(name = "EMAIL", length=100) 
        protected String email;

        /**
         * pARENTID
         */
        @Column(name = "PARENT_ID")
        protected Integer iParentId;

        /**
         * nODEICO
         */
        @Column(name = "NODEICO")
        protected Integer nodeIco;

        /**
         * iNTLINE
         */
        @Column(name = "INTLINE")
        protected Integer intLine;

        /**
         * dOMAININDEX
         */
        @Column(name = "DOMAIN_INDEX")
        protected Integer domainIndex;

        /**
         * iNTLOCAL
         */
        @Column(name = "INTLOCAL")
        protected Integer inLocal;

        /**
         * eMAILPSW
         */
        @Column(name = "EMAIL_PSW", length=50) 
        protected String emailPsw;

        /**
         * iNTCONNECTED
         */
        @Column(name = "INTCONNECTED")
        protected Integer intConnected;

        /**
         * eMAILS
         */
        @Column(name = "EMAIL_S", length=100) 
        protected String emailsStr;

        /**
         * iNTORGLEVEL
         */
        @Column(name = "INTORGLEVEL")
        protected Integer intOrgLevel;

        /**
         * iNTSENDTYPE
         */
        @Column(name = "INTSENDTYPE")
        protected Integer intSendType;

        /**
         * eMAILBACKUP
         */
        @Column(name = "EMAIL_BACKUP", length=50) 
        protected String emailBaskUp;

        /**
         * eMAILIMPLEMENT
         */
        @Column(name = "EMAIL_IMPLEMENT", length=50) 
        protected String emailImplement;

        /**
         * cREATEDATE
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREATEDATE")	
        protected Date createDate;

        /**
         * uPDATEDATE
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "UPDATEDATE")	
        protected Date updateDate;

        /**
         * cREATEBY
         */
        @Column(name = "CREATEBY", length=50) 
        protected String createBy;

        /**
         * uPDATEBY
         */
        @Column(name = "UPDATEBY", length=50) 
        protected String updateBy;

        /**
         * sMSURL
         */
        @Column(name = "SMS_URL", length=250) 
        protected String smsUrl;

        /**
         * tODBNAME
         */
        @Column(name = "TODBNAME", length=100) 
        protected String toDbName;

        /**
         * tOSERVERNAME
         */
        @Column(name = "TOSERVERNAME", length=150) 
        protected String toServerName;

        /**
         * tOUSER
         */
        @Column(name = "TOUSER", length=100) 
        protected String toUser;

        /**
         * tOPASSWORD
         */
        @Column(name = "TOPASSWORD", length=50) 
        protected String toPassword;


         
	public ProjMuiprojlist() {

	}

	public Integer getIndexId(){
	    return this.indexId;
	}

	public void setIndexId(Integer indexId) {
	    this.indexId = indexId; 
	}


        public String getId(){
	    return this.id;
	}
        public Integer getIntFlag(){
	    return this.intFlag;
	}
        public String getSysId(){
	    return this.sysId;
	}
        public String getProjName(){
	    return this.projName;
	}
        public String getNum(){
	    return this.num;
	}
	public Date getCTime(){
	    return this.cTime;
	}
	public String getCTimeString(){
	    if(this.cTime != null){
	        return DateUtils.getDateTime(this.cTime);
	    }
	    return "";
	}
        public String getContent(){
	    return this.content;
	}
        public String getSDbName(){
	    return this.sDbName;
	}
        public String getSServerName(){
	    return this.sServerName;
	}
        public String getSUser(){
	    return this.sUser;
	}
        public String getSpassword(){
	    return this.spassword;
	}
        public Integer getListNo(){
	    return this.listNo;
	}
        public String getEmail(){
	    return this.email;
	}
        public Integer getIParentId(){
	    return this.iParentId;
	}
        public Integer getNodeIco(){
	    return this.nodeIco;
	}
        public Integer getIntLine(){
	    return this.intLine;
	}
        public Integer getDomainIndex(){
	    return this.domainIndex;
	}
        public Integer getInLocal(){
	    return this.inLocal;
	}
        public String getEmailPsw(){
	    return this.emailPsw;
	}
        public Integer getIntConnected(){
	    return this.intConnected;
	}
        public String getEmailsStr(){
	    return this.emailsStr;
	}
        public Integer getIntOrgLevel(){
	    return this.intOrgLevel;
	}
        public Integer getIntSendType(){
	    return this.intSendType;
	}
        public String getEmailBaskUp(){
	    return this.emailBaskUp;
	}
        public String getEmailImplement(){
	    return this.emailImplement;
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
        public String getSmsUrl(){
	    return this.smsUrl;
	}
        public String getToDbName(){
	    return this.toDbName;
	}
        public String getToServerName(){
	    return this.toServerName;
	}
        public String getToUser(){
	    return this.toUser;
	}
        public String getToPassword(){
	    return this.toPassword;
	}


        public void setId(String id) {
	    this.id = id; 
	}
        public void setIntFlag(Integer intFlag) {
	    this.intFlag = intFlag; 
	}
        public void setSysId(String sysId) {
	    this.sysId = sysId; 
	}
        public void setProjName(String projName) {
	    this.projName = projName; 
	}
        public void setNum(String num) {
	    this.num = num; 
	}
        public void setCTime(Date cTime) {
	    this.cTime = cTime; 
	}
        public void setContent(String content) {
	    this.content = content; 
	}
        public void setSDbName(String sDbName) {
	    this.sDbName = sDbName; 
	}
        public void setSServerName(String sServerName) {
	    this.sServerName = sServerName; 
	}
        public void setSUser(String sUser) {
	    this.sUser = sUser; 
	}
        public void setSpassword(String spassword) {
	    this.spassword = spassword; 
	}
        public void setListNo(Integer listNo) {
	    this.listNo = listNo; 
	}
        public void setEmail(String email) {
	    this.email = email; 
	}
        public void setIParentId(Integer iParentId) {
	    this.iParentId = iParentId; 
	}
        public void setNodeIco(Integer nodeIco) {
	    this.nodeIco = nodeIco; 
	}
        public void setIntLine(Integer intLine) {
	    this.intLine = intLine; 
	}
        public void setDomainIndex(Integer domainIndex) {
	    this.domainIndex = domainIndex; 
	}
        public void setInLocal(Integer inLocal) {
	    this.inLocal = inLocal; 
	}
        public void setEmailPsw(String emailPsw) {
	    this.emailPsw = emailPsw; 
	}
        public void setIntConnected(Integer intConnected) {
	    this.intConnected = intConnected; 
	}
        public void setEmailsStr(String emailsStr) {
	    this.emailsStr = emailsStr; 
	}
        public void setIntOrgLevel(Integer intOrgLevel) {
	    this.intOrgLevel = intOrgLevel; 
	}
        public void setIntSendType(Integer intSendType) {
	    this.intSendType = intSendType; 
	}
        public void setEmailBaskUp(String emailBaskUp) {
	    this.emailBaskUp = emailBaskUp; 
	}
        public void setEmailImplement(String emailImplement) {
	    this.emailImplement = emailImplement; 
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
        public void setSmsUrl(String smsUrl) {
	    this.smsUrl = smsUrl; 
	}
        public void setToDbName(String toDbName) {
	    this.toDbName = toDbName; 
	}
        public void setToServerName(String toServerName) {
	    this.toServerName = toServerName; 
	}
        public void setToUser(String toUser) {
	    this.toUser = toUser; 
	}
        public void setToPassword(String toPassword) {
	    this.toPassword = toPassword; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjMuiprojlist other = (ProjMuiprojlist) obj;
		if (indexId == null) {
			if (other.indexId != null)
				return false;
		} else if (!indexId.equals(other.indexId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((indexId == null) ? 0 : indexId.hashCode());
		return result;
	}


	public ProjMuiprojlist jsonToObject(JSONObject jsonObject) {
            return ProjMuiprojlistJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ProjMuiprojlistJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ProjMuiprojlistJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
