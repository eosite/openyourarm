package com.glaf.maildata.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.maildata.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "EMAIL_SEND")
public class EmailSend implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "ID", nullable = false)
        protected String id;

        /**
         * mSGID
         */
        @Column(name = "MSGID", length=100) 
        protected String msgId;

        /**
         * iNREPLYTO
         */
        @Column(name = "INREPLYTO", length=100) 
        protected String inReplyTo;

        /**
         * fROM
         */
        @Column(name = "FROM", length=100) 
        protected String from;

        /**
         * tO
         */
        @Column(name = "TO", length=100) 
        protected String to;

        /**
         * cC
         */
        @Column(name = "CC", length=100) 
        protected String cc;

        /**
         * dATE
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "DATE")	
        protected Date date;

        /**
         * sUBJECT
         */
        @Column(name = "SUBJECT", length=100) 
        protected String subject;

        /**
         * rEPLYTO
         */
        @Column(name = "REPLYTO", length=100) 
        protected String replyTo;

        /**
         * tEXT
         */
	@javax.persistence.Lob
        @Column(name = "TEXT") 
	protected String text;

        /**
         * hTML
         */
	@javax.persistence.Lob
        @Column(name = "HTML") 
	protected String html;

        /**
         * iNTFLAG
         */
        @Column(name = "INTFLAG")
        protected Integer intFlag;

        /**
         * eMAIL
         */
        @Column(name = "EMAIL", length=50) 
        protected String email;

        /**
         * fROMSYSID
         */
        @Column(name = "FROMSYSID", length=50) 
        protected String fromSysId;

        /**
         * iNTACTION
         */
        @Column(name = "INTACTION")
        protected Integer intAction;

        /**
         * iNTOPERAT
         */
        @Column(name = "INTOPERAT")
        protected Integer intOperat;

        /**
         * lISTNO
         */
        @Column(name = "LISTNO")
        protected Integer listNo;

        /**
         * tOSYSID
         */
        @Column(name = "TOSYSID", length=50) 
        protected String toSysId;

        protected Integer atts;
         
	public EmailSend() {

	}

	public String getId(){
	    return this.id;
	}

	public void setId(String id) {
	    this.id = id; 
	}


        public String getMsgId(){
	    return this.msgId;
	}
        public String getInReplyTo(){
	    return this.inReplyTo;
	}
        public String getFrom(){
	    return this.from;
	}
        public String getTo(){
	    return this.to;
	}
        public String getCc(){
	    return this.cc;
	}
	public Date getDate(){
	    return this.date;
	}
	public String getDateString(){
	    if(this.date != null){
	        return DateUtils.getDateTime(this.date);
	    }
	    return "";
	}
        public String getSubject(){
	    return this.subject;
	}
        public String getReplyTo(){
	    return this.replyTo;
	}
	public String getText(){
	    return this.text;
	}
	public String getHtml(){
	    return this.html;
	}
        public Integer getIntFlag(){
	    return this.intFlag;
	}
        public String getEmail(){
	    return this.email;
	}
        public String getFromSysId(){
	    return this.fromSysId;
	}
        public Integer getIntAction(){
	    return this.intAction;
	}
        public Integer getIntOperat(){
	    return this.intOperat;
	}
        public Integer getListNo(){
	    return this.listNo;
	}
        public String getToSysId(){
	    return this.toSysId;
	}


        public void setMsgId(String msgId) {
	    this.msgId = msgId; 
	}
        public void setInReplyTo(String inReplyTo) {
	    this.inReplyTo = inReplyTo; 
	}
        public void setFrom(String from) {
	    this.from = from; 
	}
        public void setTo(String to) {
	    this.to = to; 
	}
        public void setCc(String cc) {
	    this.cc = cc; 
	}
        public void setDate(Date date) {
	    this.date = date; 
	}
        public void setSubject(String subject) {
	    this.subject = subject; 
	}
        public void setReplyTo(String replyTo) {
	    this.replyTo = replyTo; 
	}
	public void setText(String text) {
	    this.text = text; 
	}
	public void setHtml(String html) {
	    this.html = html; 
	}
        public void setIntFlag(Integer intFlag) {
	    this.intFlag = intFlag; 
	}
        public void setEmail(String email) {
	    this.email = email; 
	}
        public void setFromSysId(String fromSysId) {
	    this.fromSysId = fromSysId; 
	}
        public void setIntAction(Integer intAction) {
	    this.intAction = intAction; 
	}
        public void setIntOperat(Integer intOperat) {
	    this.intOperat = intOperat; 
	}
        public void setListNo(Integer listNo) {
	    this.listNo = listNo; 
	}
        public void setToSysId(String toSysId) {
	    this.toSysId = toSysId; 
	}


	public Integer getAtts() {
			return atts;
		}

		public void setAtts(Integer atts) {
			this.atts = atts;
		}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailSend other = (EmailSend) obj;
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


	public EmailSend jsonToObject(JSONObject jsonObject) {
            return EmailSendJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return EmailSendJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return EmailSendJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
