package com.glaf.convert.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.convert.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CVT_PAGE_TMPL")
public class ConvertPageTmpl implements Serializable, JSONable {
        private static final long serialVersionUID = 1L;

        @Id
        @Column(name = "CVT_ID_", nullable = false)
        protected Long cvtId;

        /**
         * fileDotFieldId
         */
        @Column(name = "FILEDOT_FIELD_ID_", length=50) 
        protected String fileDotFieldId;

        /**
         * cvtType
         */
        @Column(name = "CVT_TYPE_", length=10) 
        protected String cvtType;

        /**
         * cvtSrcContent
         */
        @Lob
        @Column(name = "CVT_SRC_CONTENT_") 
        protected byte[] cvtSrcContent;

        /**
         * cvtSrcFileName
         */
        @Column(name = "CVT_SRC_FILENAME_", length=50) 
        protected String cvtSrcFileName;

        /**
         * cvtSrcName
         */
        @Column(name = "CVT_SRC_NAME_", length=50) 
        protected String cvtSrcName;

        /**
         * cvtXmlContent
         */
        @Lob
        @Column(name = "CVT_XML_CONTENT_") 
        protected byte[] cvtXmlContent;

        /**
         * cvtDesContent
         */
        @Lob
        @Column(name = "CVT_DES_CONTENT_") 
        protected byte[] cvtDesContent;

        /**
         * cvtDesExt
         */
        @Column(name = "CVT_DES_EXT_", length=10) 
        protected String cvtDesExt;

        /**
         * status
         */
        @Column(name = "STATUS_")
        protected Integer status;

        /**
         * effectiveFlag
         */
        @Column(name = "EFFECTIVE_FLAG_")
        protected Integer effectiveFlag;

        /**
         * cvtStatus
         */
        @Column(name = "CVT_STATUS_")
        protected Integer cvtStatus;

        /**
         * createDatetime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "CREAT_DATETIME_")	
        protected Date createDatetime;

        /**
         * modifyDatetime
         */
        @Temporal(TemporalType.TIMESTAMP)
        @Column(name = "MODIFY_DATETIME_")	
        protected Date modifyDatetime;


         
	public ConvertPageTmpl() {

	}

	public Long getCvtId(){
	    return this.cvtId;
	}

	public void setCvtId(Long cvtId) {
	    this.cvtId = cvtId; 
	}


        public String getFileDotFieldId(){
	    return this.fileDotFieldId;
	}
        public String getCvtType(){
	    return this.cvtType;
	}
        public byte[] getCvtSrcContent(){
	    return this.cvtSrcContent;
	}
        public String getCvtSrcFileName(){
	    return this.cvtSrcFileName;
	}
        public String getCvtSrcName(){
	    return this.cvtSrcName;
	}
        public byte[] getCvtXmlContent(){
	    return this.cvtXmlContent;
	}
        public byte[] getCvtDesContent(){
	    return this.cvtDesContent;
	}
        public String getCvtDesExt(){
	    return this.cvtDesExt;
	}
        public Integer getStatus(){
	    return this.status;
	}
        public Integer getEffectiveFlag(){
	    return this.effectiveFlag;
	}
        public Integer getCvtStatus(){
	    return this.cvtStatus;
	}
	public Date getCreateDatetime(){
	    return this.createDatetime;
	}
	public String getCreateDatetimeString(){
	    if(this.createDatetime != null){
	        return DateUtils.getDateTime(this.createDatetime);
	    }
	    return "";
	}
	public Date getModifyDatetime(){
	    return this.modifyDatetime;
	}
	public String getModifyDatetimeString(){
	    if(this.modifyDatetime != null){
	        return DateUtils.getDateTime(this.modifyDatetime);
	    }
	    return "";
	}


        public void setFileDotFieldId(String fileDotFieldId) {
	    this.fileDotFieldId = fileDotFieldId; 
	}
        public void setCvtType(String cvtType) {
	    this.cvtType = cvtType; 
	}
        public void setCvtSrcContent(byte[] cvtSrcContent) {
	    this.cvtSrcContent = cvtSrcContent; 
	}
        public void setCvtSrcFileName(String cvtSrcFileName) {
	    this.cvtSrcFileName = cvtSrcFileName; 
	}
        public void setCvtSrcName(String cvtSrcName) {
	    this.cvtSrcName = cvtSrcName; 
	}
        public void setCvtXmlContent(byte[] cvtXmlContent) {
	    this.cvtXmlContent = cvtXmlContent; 
	}
        public void setCvtDesContent(byte[] cvtDesContent) {
	    this.cvtDesContent = cvtDesContent; 
	}
        public void setCvtDesExt(String cvtDesExt) {
	    this.cvtDesExt = cvtDesExt; 
	}
        public void setStatus(Integer status) {
	    this.status = status; 
	}
        public void setEffectiveFlag(Integer effectiveFlag) {
	    this.effectiveFlag = effectiveFlag; 
	}
        public void setCvtStatus(Integer cvtStatus) {
	    this.cvtStatus = cvtStatus; 
	}
        public void setCreateDatetime(Date createDatetime) {
	    this.createDatetime = createDatetime; 
	}
        public void setModifyDatetime(Date modifyDatetime) {
	    this.modifyDatetime = modifyDatetime; 
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConvertPageTmpl other = (ConvertPageTmpl) obj;
		if (cvtId == null) {
			if (other.cvtId != null)
				return false;
		} else if (!cvtId.equals(other.cvtId))
			return false;
		return true;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((cvtId == null) ? 0 : cvtId.hashCode());
		return result;
	}


	public ConvertPageTmpl jsonToObject(JSONObject jsonObject) {
            return ConvertPageTmplJsonFactory.jsonToObject(jsonObject);
	}


	public JSONObject toJsonObject() {
            return ConvertPageTmplJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode(){
            return ConvertPageTmplJsonFactory.toObjectNode(this);
	}

        @Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
