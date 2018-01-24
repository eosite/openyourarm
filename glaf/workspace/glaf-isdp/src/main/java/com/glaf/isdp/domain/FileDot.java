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
import com.glaf.isdp.util.FileDotJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FILEDOT")
public class FileDot implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FILEID", nullable = false)
	protected String fileID;

	@Column(name = "LISTID", length = 50)
	protected String listId;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "FBELONG", length = 250)
	protected String fbelong;

	@Column(name = "FNUM", length = 50)
	protected String fnum;

	@Column(name = "PBELONG", length = 200)
	protected String pbelong;

	@Column(name = "NUM", length = 50)
	protected String num;

	@Column(name = "FNAME", length = 255)
	protected String fname;

	@Column(name = "DOTNAME", length = 255)
	protected String dotName;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DTIME")
	protected Date dtime;

	@Column(name = "FILE_NAME", length = 255)
	protected String fileName;

	@Column(name = "FILE_CONTENT")
	protected byte[] fileContent;

	@Column(name = "FILESIZE")
	protected Integer fileSize;

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

	@Column(name = "CMAN", length = 50)
	protected String cman;

	@Column(name = "CONTENT", length = 250)
	protected String content;

	@Column(name = "LISTFLAG", length = 1)
	protected String listFlag;

	@Column(name = "TOFILE")
	protected Integer toFile;

	@Column(name = "ISINK", length = 1)
	protected String isInk;

	@Column(name = "DOTTYPE")
	protected Integer dotType;

	@Column(name = "CTIMEDNAME", length = 50)
	protected String ctimeDName;

	@Column(name = "TYPE")
	protected Integer type;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "UTREE_INDEX")
	protected Integer utreeIndex;

	@Column(name = "ISQUAN", length = 1)
	protected String isQuan;

	@Column(name = "INTSYSFORM")
	protected Integer intSysForm;

	public FileDot() {

	}

	public String getFileID() {
		return this.fileID;
	}

	public void setFileID(String fileID) {
		this.fileID = fileID;
	}

	public String getListId() {
		return this.listId;
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public String getFbelong() {
		return this.fbelong;
	}

	public String getFnum() {
		return this.fnum;
	}

	public String getPbelong() {
		return this.pbelong;
	}

	public String getNum() {
		return this.num;
	}

	public String getFname() {
		return this.fname;
	}

	public String getDotName() {
		return this.dotName;
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

	public Date getDtime() {
		return this.dtime;
	}

	public String getDtimeString() {
		if (this.dtime != null) {
			return DateUtils.getDateTime(this.dtime);
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

	public String getCman() {
		return this.cman;
	}

	public String getContent() {
		return this.content;
	}

	public String getListFlag() {
		return this.listFlag;
	}

	public Integer getToFile() {
		return this.toFile;
	}

	public String getIsInk() {
		return this.isInk;
	}

	public Integer getDotType() {
		return this.dotType;
	}

	public String getCtimeDName() {
		return this.ctimeDName;
	}

	public Integer getType() {
		return this.type;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getUtreeIndex() {
		return this.utreeIndex;
	}

	public String getIsQuan() {
		return this.isQuan;
	}

	public Integer getIntSysForm() {
		return this.intSysForm;
	}

	public void setListId(String listId) {
		this.listId = listId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setFbelong(String fbelong) {
		this.fbelong = fbelong;
	}

	public void setFnum(String fnum) {
		this.fnum = fnum;
	}

	public void setPbelong(String pbelong) {
		this.pbelong = pbelong;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public void setDotName(String dotName) {
		this.dotName = dotName;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setDtime(Date dtime) {
		this.dtime = dtime;
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

	public void setCman(String cman) {
		this.cman = cman;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setListFlag(String listFlag) {
		this.listFlag = listFlag;
	}

	public void setToFile(Integer toFile) {
		this.toFile = toFile;
	}

	public void setIsInk(String isInk) {
		this.isInk = isInk;
	}

	public void setDotType(Integer dotType) {
		this.dotType = dotType;
	}

	public void setCtimeDName(String ctimeDName) {
		this.ctimeDName = ctimeDName;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setUtreeIndex(Integer utreeIndex) {
		this.utreeIndex = utreeIndex;
	}

	public void setIsQuan(String isQuan) {
		this.isQuan = isQuan;
	}

	public void setIntSysForm(Integer intSysForm) {
		this.intSysForm = intSysForm;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileDot other = (FileDot) obj;
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

	public FileDot jsonToObject(JSONObject jsonObject) {
		return FileDotJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FileDotJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FileDotJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
