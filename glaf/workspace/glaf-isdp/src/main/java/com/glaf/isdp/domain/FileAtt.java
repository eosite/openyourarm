package com.glaf.isdp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.FileAttJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FILEATT")
public class FileAtt implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FILEID", nullable = false)
	protected String fileID;

	@Column(name = "TOPID", length = 50)
	protected String topId;

	@Column(name = "NUM", length = 50)
	protected String num;

	@Column(name = "ACTOR", length = 20)
	protected String actor;

	@Column(name = "FNAME", length = 255)
	protected String fname;

	@Column(name = "FILE_NAME", length = 255)
	protected String fileName;

	@Lob
	@Column(name = "FILE_CONTENT")
	protected String fileContent;

	@Column(name = "VISION", length = 50)
	protected String vision;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "FILESIZE")
	protected Integer fileSize;

	@Column(name = "VISID", length = 50)
	protected String visID;

	@Column(name = "ATTID", length = 50)
	protected String attID;

	@Column(name = "ISTEXTCONTENT")
	protected Integer isTextContent;

	@Lob
	@Column(name = "TEXTCONTENT")
	protected String textContent;

	public FileAtt() {

	}

	public String getFileID() {
		return this.fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public String getTopId() {
		return this.topId;
	}

	public String getNum() {
		return this.num;
	}

	public String getActor() {
		return this.actor;
	}

	public String getFname() {
		return this.fname;
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getFileContent() {
		return this.fileContent;
	}

	public String getVision() {
		return this.vision;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCtimeString() {
		if (this.ctime != null) {
			return DateUtils.getDateTime(this.ctime);
		}
		return "";
	}

	public Integer getFileSize() {
		return this.fileSize;
	}

	public String getVisID() {
		return this.visID;
	}

	public String getAttID() {
		return this.attID;
	}

	public Integer getIsTextContent() {
		return this.isTextContent;
	}

	public String getTextContent() {
		return this.textContent;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setActor(String actor) {
		this.actor = actor;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileContent(String fileContent) {
		this.fileContent = fileContent;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public void setVisID(String visID) {
		this.visID = visID;
	}

	public void setAttID(String attID) {
		this.attID = attID;
	}

	public void setIsTextContent(Integer isTextContent) {
		this.isTextContent = isTextContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileAtt other = (FileAtt) obj;
		if (fileID == null) {
			if (other.fileID != null)
				return false;
		} else if (!fileID.equals(other.fileID))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileID == null) ? 0 : fileID.hashCode());
		return result;
	}

	public FileAtt jsonToObject(JSONObject jsonObject) {
		return FileAttJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FileAttJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FileAttJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
