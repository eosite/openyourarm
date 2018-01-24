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
@Table(name = "EMAIL_RECATT")
public class EmailRecAtt implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FILEID", nullable = false)
	protected String fileId;

	/**
	 * tOPID
	 */
	@Column(name = "TOPID", length = 50)
	protected String topId;

	/**
	 * fILENAME
	 */
	@Column(name = "FILE_NAME", length = 255)
	protected String fileName;

	/**
	 * fILECONTENT
	 */
	@Column(name = "FILE_CONTENT")
	protected byte[] fileContent;

	/**
	 * cTIME
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date cTime;

	/**
	 * fILESIZE
	 */
	@Column(name = "FILESIZE")
	protected Integer fileSize;

	public EmailRecAtt() {

	}

	public String getFileId() {
		return this.fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getTopId() {
		return this.topId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public Date getCTime() {
		return this.cTime;
	}

	public String getCTimeString() {
		if (this.cTime != null) {
			return DateUtils.getDateTime(this.cTime);
		}
		return "";
	}

	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setCTime(Date cTime) {
		this.cTime = cTime;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EmailRecAtt other = (EmailRecAtt) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		return result;
	}

	public EmailRecAtt jsonToObject(JSONObject jsonObject) {
		return EmailRecAttJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return EmailRecAttJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EmailRecAttJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
