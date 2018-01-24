package com.glaf.isdp.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "R_DATA_TABLE")
public class RdataTable implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	/**
	 * tablename
	 */
	@Column(name = "TABLENAME", length = 50)
	protected String tablename;

	/**
	 * name
	 */
	@Column(name = "NAME", length = 255)
	protected String name;

	/**
	 * addtype
	 */
	@Column(name = "ADDTYPE")
	protected Integer addtype;

	/**
	 * maxuser
	 */
	@Column(name = "MAXUSER")
	protected Integer maxuser;

	/**
	 * maxsys
	 */
	@Column(name = "MAXSYS")
	protected Integer maxsys;

	/**
	 * userid
	 */
	@Column(name = "USERID", length = 50)
	protected String userid;

	/**
	 * ctime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	/**
	 * content
	 */
	@Column(name = "CONTENT", length = 250)
	protected String content;

	/**
	 * sysnum
	 */
	@Column(name = "SYSNUM", length = 100)
	protected String sysnum;

	/**
	 * issubtable
	 */
	@Column(name = "ISSUBTABLE", length = 1)
	protected String issubtable;

	/**
	 * topid
	 */
	@Column(name = "TOPID", length = 50)
	protected String topid;

	/**
	 * filedotFileid
	 */
	@Column(name = "FILEDOT_FILEID", length = 50)
	protected String filedotFileid;

	/**
	 * indexId
	 */
	@Column(name = "INDEX_ID")
	protected Integer indexId;

	/**
	 * winWidth
	 */
	@Column(name = "WIN_WIDTH")
	protected Integer winWidth;

	/**
	 * winHeight
	 */
	@Column(name = "WIN_HEIGHT")
	protected Integer winHeight;

	/**
	 * intQuote
	 */
	@Column(name = "INTQUOTE")
	protected Integer intQuote;

	/**
	 * intLineEdit
	 */
	@Column(name = "INTLINEEDIT")
	protected Integer intLineEdit;

	/**
	 * printfileid
	 */
	@Column(name = "PRINTFILEID", length = 50)
	protected String printfileid;

	/**
	 * INTUSESTREEWBS
	 */
	@Column(name = "INTUSESTREEWBS")
	protected Integer INTUSESTREEWBS;

	/**
	 * intUseIf
	 */
	@Column(name = "INTUSEIF")
	protected Integer intUseIf;

	/**
	 * codeName
	 */
	@javax.persistence.Transient
	protected String codeName;

	public RdataTable() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTablename() {
		return this.tablename;
	}

	public String getName() {
		return this.name;
	}

	public Integer getAddtype() {
		return this.addtype;
	}

	public Integer getMaxuser() {
		return this.maxuser;
	}

	public Integer getMaxsys() {
		return this.maxsys;
	}

	public String getUserid() {
		return this.userid;
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

	public String getSysnum() {
		return this.sysnum;
	}

	public String getIssubtable() {
		return this.issubtable;
	}

	public String getTopid() {
		return this.topid;
	}

	public String getFiledotFileid() {
		return this.filedotFileid;
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

	public String getPrintfileid() {
		return this.printfileid;
	}

	public Integer getINTUSESTREEWBS() {
		return this.INTUSESTREEWBS;
	}

	public Integer getIntUseIf() {
		return this.intUseIf;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAddtype(Integer addtype) {
		this.addtype = addtype;
	}

	public void setMaxuser(Integer maxuser) {
		this.maxuser = maxuser;
	}

	public void setMaxsys(Integer maxsys) {
		this.maxsys = maxsys;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setSysnum(String sysnum) {
		this.sysnum = sysnum;
	}

	public void setIssubtable(String issubtable) {
		this.issubtable = issubtable;
	}

	public void setTopid(String topid) {
		this.topid = topid;
	}

	public void setFiledotFileid(String filedotFileid) {
		this.filedotFileid = filedotFileid;
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

	public void setPrintfileid(String printfileid) {
		this.printfileid = printfileid;
	}

	public void setINTUSESTREEWBS(Integer INTUSESTREEWBS) {
		this.INTUSESTREEWBS = INTUSESTREEWBS;
	}

	public void setIntUseIf(Integer intUseIf) {
		this.intUseIf = intUseIf;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RdataTable other = (RdataTable) obj;
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

	public RdataTable jsonToObject(JSONObject jsonObject) {
		return RdataTableJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return RdataTableJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return RdataTableJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getCodeName() {
		return codeName;
	}

	public void setCodeName(String codeName) {
		this.codeName = codeName;
	}

}
