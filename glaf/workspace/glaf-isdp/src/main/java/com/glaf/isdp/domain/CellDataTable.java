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
import com.glaf.isdp.util.CellDataTableJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_DATA_TABLE")
public class CellDataTable implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "TABLENAME", length = 50)
	protected String tableName;

	@Column(name = "NAME", length = 255)
	protected String name;

	@Column(name = "ADDTYPE")
	protected Integer addType;

	@Column(name = "MAXUSER")
	protected Integer maxUser;

	@Column(name = "MAXSYS")
	protected Integer maxSys;

	@Column(name = "USERID", length = 50)
	protected String userId;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "CONTENT", length = 250)
	protected String content;

	@Column(name = "SYSNUM", length = 100)
	protected String sysNum;

	@Column(name = "ISSUBTABLE", length = 1)
	protected String isSubTable;

	@Column(name = "TOPID", length = 50)
	protected String topId;

	@Column(name = "FILEDOT_FILEID", length = 50)
	protected String fileDotFileId;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "WIN_WIDTH")
	protected Integer winWidth;

	@Column(name = "WIN_HEIGHT")
	protected Integer winHeight;

	@Column(name = "INTQUOTE")
	protected Integer intQuote;

	@Column(name = "INTLINEEDIT")
	protected Integer intLineEdit;

	@Column(name = "PRINTFILEID", length = 50)
	protected String printFileId;

	@Column(name = "INTUSESTREEWBS")
	protected Integer intUseSTreeWBS;
	
	@javax.persistence.Transient
	protected Integer fileDotIndexId;

	public CellDataTable() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getName() {
		return this.name;
	}

	public Integer getAddType() {
		return this.addType;
	}

	public Integer getMaxUser() {
		return this.maxUser;
	}

	public Integer getMaxSys() {
		return this.maxSys;
	}

	public String getUserId() {
		return this.userId;
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

	public String getContent() {
		return this.content;
	}

	public String getSysNum() {
		return this.sysNum;
	}

	public String getIsSubTable() {
		return this.isSubTable;
	}

	public String getTopId() {
		return this.topId;
	}

	public String getFileDotFileId() {
		return this.fileDotFileId;
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public Integer getWinWidth() {
		return this.winWidth;
	}

	public Integer getWinHeight() {
		return this.winHeight;
	}

	public Integer getIntQuote() {
		return this.intQuote;
	}

	public Integer getIntLineEdit() {
		return this.intLineEdit;
	}

	public String getPrintFileId() {
		return this.printFileId;
	}

	public Integer getIntUseSTreeWBS() {
		return this.intUseSTreeWBS;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddType(Integer addType) {
		this.addType = addType;
	}

	public void setMaxUser(Integer maxUser) {
		this.maxUser = maxUser;
	}

	public void setMaxSys(Integer maxSys) {
		this.maxSys = maxSys;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSysNum(String sysNum) {
		this.sysNum = sysNum;
	}

	public void setIsSubTable(String isSubTable) {
		this.isSubTable = isSubTable;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setFileDotFileId(String fileDotFileId) {
		this.fileDotFileId = fileDotFileId;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setWinWidth(Integer winWidth) {
		this.winWidth = winWidth;
	}

	public void setWinHeight(Integer winHeight) {
		this.winHeight = winHeight;
	}

	public void setIntQuote(Integer intQuote) {
		this.intQuote = intQuote;
	}

	public void setIntLineEdit(Integer intLineEdit) {
		this.intLineEdit = intLineEdit;
	}

	public void setPrintFileId(String printFileId) {
		this.printFileId = printFileId;
	}

	public void setIntUseSTreeWBS(Integer intUseSTreeWBS) {
		this.intUseSTreeWBS = intUseSTreeWBS;
	}

	public Integer getFileDotIndexId() {
		return fileDotIndexId;
	}

	public void setFileDotIndexId(Integer fileDotIndexId) {
		this.fileDotIndexId = fileDotIndexId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellDataTable other = (CellDataTable) obj;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public CellDataTable jsonToObject(JSONObject jsonObject) {
		return CellDataTableJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellDataTableJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellDataTableJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
