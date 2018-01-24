package com.glaf.base.modules.sys.model;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.sys.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DOTUSE")
public class FiledotUse implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FILEID", nullable = false)
	protected String fileID;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "PROJID")
	protected Integer projid;

	@Column(name = "PID")
	protected Integer pid;

	@Column(name = "DOTID", length = 50)
	protected String dotid;

	@Column(name = "NUM", length = 10)
	protected String num;

	@Column(name = "NAME", length = 255)
	protected String name;

	@Column(name = "CMAN", length = 20)
	protected String cman;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date createTime;

	@Column(name = "FILE_NAME", length = 255)
	protected String fileName;

	@Lob
	@Column(name = "FILE_CONTENT")
	protected byte[] fileContent;

	@Column(name = "FILESIZE")
	protected Integer filesize;

	@Column(name = "VISION", length = 100)
	protected String vision;

	@Column(name = "SAVETIME", length = 100)
	protected String savetime;

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
	protected String topnode;

	@Column(name = "TOPID", length = 50)
	protected String topid;

	@Column(name = "fname", length = 250)
	protected String fname;

	@Column(name = "ischeck")
	protected Integer ischeck;

	@Column(name = "ISINK", length = 1)
	protected String isink;

	@Column(name = "OLD_ID", length = 50)
	protected String oldid;

	@Column(name = "TASK_ID", length = 50)
	protected String taskid;

	@Column(name = "type")
	protected Integer type;

	public FiledotUse() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FiledotUse other = (FiledotUse) obj;
		if (fileID == null) {
			if (other.fileID != null)
				return false;
		} else if (!fileID.equals(other.fileID))
			return false;
		return true;
	}

	public String getCman() {
		return this.cman;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public String getDotid() {
		return this.dotid;
	}

	public Integer getDwid() {
		return this.dwid;
	}

	public Integer getFbid() {
		return this.fbid;
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public String getFileID() {
		return this.fileID;
	}

	public String getFileName() {
		return this.fileName;
	}

	public Integer getFilesize() {
		return this.filesize;
	}

	public String getFlid() {
		return this.flid;
	}

	public String getFname() {
		return this.fname;
	}

	public Integer getFxid() {
		return this.fxid;
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public Integer getIscheck() {
		return this.ischeck;
	}

	public String getIsink() {
		return isink;
	}

	public String getJid() {
		return this.jid;
	}

	public String getName() {
		return this.name;
	}

	public String getNum() {
		return this.num;
	}

	public String getOldid() {
		return oldid;
	}

	public Integer getPid() {
		return this.pid;
	}

	public Integer getProjid() {
		return this.projid;
	}

	public String getRemark() {
		return this.remark;
	}

	public String getSavetime() {
		return this.savetime;
	}

	public String getTaskid() {
		return taskid;
	}

	public String getTopid() {
		return this.topid;
	}

	public String getTopnode() {
		return this.topnode;
	}

	public Integer getType() {
		return type;
	}

	public String getVision() {
		return this.vision;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileID == null) ? 0 : fileID.hashCode());
		return result;
	}

	public FiledotUse jsonToObject(JSONObject jsonObject) {
		return FiledotUseJsonFactory.jsonToObject(jsonObject);
	}

	public void setCman(String cman) {
		this.cman = cman;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDotid(String dotid) {
		this.dotid = dotid;
	}

	public void setDwid(Integer dwid) {
		this.dwid = dwid;
	}

	public void setFbid(Integer fbid) {
		this.fbid = fbid;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFilesize(Integer filesize) {
		this.filesize = filesize;
	}

	public void setFlid(String flid) {
		this.flid = flid;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setFxid(Integer fxid) {
		this.fxid = fxid;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIscheck(Integer ischeck) {
		this.ischeck = ischeck;
	}

	public void setIsink(String isink) {
		this.isink = isink;
	}

	public void setJid(String jid) {
		this.jid = jid;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setOldid(String oldid) {
		this.oldid = oldid;
	}

	public void setPid(Integer pid) {
		this.pid = pid;
	}

	public void setProjid(Integer projid) {
		this.projid = projid;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setSavetime(String savetime) {
		this.savetime = savetime;
	}

	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}

	public void setTopid(String topid) {
		this.topid = topid;
	}

	public void setTopnode(String topnode) {
		this.topnode = topnode;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setVision(String vision) {
		this.vision = vision;
	}

	public JSONObject toJsonObject() {
		return FiledotUseJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FiledotUseJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
