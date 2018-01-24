package com.glaf.isdp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.DotUseJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DOTUSE")
public class DotUse implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FILEID", nullable = false)
	protected String fileID;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "PROJID")
	protected Integer projId;

	@Column(name = "PID")
	protected Integer pid;

	@Column(name = "DOTID", length = 50)
	protected String dotId;

	@Column(name = "NUM", length = 10)
	protected String num;

	@Column(name = "NAME", length = 50)
	protected String name;

	@Column(name = "CMAN", length = 20)
	protected String cman;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "FILE_NAME", length = 255)
	protected String fileName;

	@Column(name = "FILE_CONTENT")
	protected byte[] fileContent;

	@Column(name = "FILESIZE")
	protected Integer fileSize;

	@Column(name = "VISION", length = 100)
	protected String vision;

	@Column(name = "SAVETIME", length = 100)
	protected String saveTime;

	@Column(name = "REMARK", length = 200)
	protected String remark;

	@Column(name = "DWID")
	protected Integer dwid;

	@Column(name = "FBID")
	protected Integer fbid;

	@Column(name = "FXID")
	protected Integer fxid;

	@Column(name = "JID", length = 50)
	protected String jid;

	@Column(name = "FLID", length = 50)
	protected String flid;

	@Column(name = "TOPNODE", length = 250)
	protected String topNode;

	@Column(name = "TOPID", length = 50)
	protected String topId;

	@Column(name = "TYPE")
	protected Integer type;

	@Column(name = "FNAME", length = 250)
	protected String fname;

	@Column(name = "ISINK", length = 1)
	protected String isInk;

	@Column(name = "OLD_ID", length = 50)
	protected String oldId;

	@Column(name = "TASK_ID", length = 50)
	protected String taskId;

	@Column(name = "ISCHECK")
	protected Integer isCheck;

	public DotUse() {

	}

	public String getFileID() {
		return this.fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public Integer getProjId() {
		return this.projId;
	}

	public Integer getPid() {
		return this.pid;
	}

	public String getDotId() {
		return this.dotId;
	}

	public String getNum() {
		return this.num;
	}

	public String getName() {
		return this.name;
	}

	public String getCman() {
		return this.cman;
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

	public String getFileName() {
		return this.fileName;
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public Integer getFileSize() {
		return this.fileSize;
	}

	public String getVision() {
		return this.vision;
	}

	public String getSaveTime() {
		return this.saveTime;
	}

	public String getRemark() {
		return this.remark;
	}

	public Integer getDwid() {
		return this.dwid;
	}

	public Integer getFbid() {
		return this.fbid;
	}

	public Integer getFxid() {
		return this.fxid;
	}

	public String getJid() {
		return this.jid;
	}

	public String getFlid() {
		return this.flid;
	}

	public String getTopNode() {
		return this.topNode;
	}

	public String getTopId() {
		return this.topId;
	}

	public Integer getType() {
		return this.type;
	}

	public String getFname() {
		return this.fname;
	}

	public String getIsInk() {
		return this.isInk;
	}

	public String getOldId() {
		return this.oldId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public Integer getIsCheck() {
		return this.isCheck;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setProjId(Integer projId) {
		this.projId = projId;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setDotId(String dotId) {
		this.dotId = dotId;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCman(String cman) {
		this.cman = cman;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public void setSaveTime(String saveTime) {
		this.saveTime = saveTime;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public void setFbid(Integer fbid) {
		this.fbid = fbid;
	}

	public void setFxid(Integer fxid) {
		this.fxid = fxid;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public void setTopNode(String topNode) {
		this.topNode = topNode;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setIsInk(String isInk) {
		this.isInk = isInk;
	}

	public void setOldId(String oldId) {
		this.oldId = oldId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setIsCheck(Integer isCheck) {
		this.isCheck = isCheck;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DotUse other = (DotUse) obj;
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

	public DotUse jsonToObject(JSONObject jsonObject) {
		return DotUseJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DotUseJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DotUseJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
