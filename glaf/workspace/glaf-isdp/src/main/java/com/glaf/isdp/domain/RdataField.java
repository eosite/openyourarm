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
@Table(name = "R_DATA_FIELD")
public class RdataField implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	/**
	 * tableid
	 */
	@Column(name = "TABLEID", length = 50)
	protected String tableid;

	/**
	 * fieldname
	 */
	@Column(name = "FIELDNAME", length = 50)
	protected String fieldname;

	/**
	 * userid
	 */
	@Column(name = "USERID", length = 50)
	protected String userid;

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
	 * ctime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	/**
	 * sysnum
	 */
	@Column(name = "SYSNUM", length = 100)
	protected String sysnum;

	/**
	 * tablename
	 */
	@Column(name = "TABLENAME", length = 50)
	protected String tablename;

	/**
	 * dname
	 */
	@Column(name = "DNAME", length = 50)
	protected String dname;

	/**
	 * userindex
	 */
	@Column(name = "USERINDEX", length = 50)
	protected String userindex;

	/**
	 * treetablenameB
	 */
	@Column(name = "TREETABLENAME_B", length = 50)
	protected String treetablenameB;

	/**
	 * formula
	 */
	@Column(name = "FORMULA", length = 1000)
	protected String formula;

	/**
	 * lgcexpress
	 */
	@Column(name = "LGCEXPRESS", length = 1000)
	protected String lgcexpress;

	public RdataField() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableid() {
		return this.tableid;
	}

	public String getFieldname() {
		return this.fieldname;
	}

	public String getUserid() {
		return this.userid;
	}

	public Integer getMaxuser() {
		return this.maxuser;
	}

	public Integer getMaxsys() {
		return this.maxsys;
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

	public String getSysnum() {
		return this.sysnum;
	}

	public String getTablename() {
		return this.tablename;
	}

	public String getDname() {
		return this.dname;
	}

	public String getUserindex() {
		return this.userindex;
	}

	public String getTreetablenameB() {
		return this.treetablenameB;
	}

	public String getFormula() {
		return this.formula;
	}

	public String getLgcexpress() {
		return this.lgcexpress;
	}

	public void setTableid(String tableid) {
		this.tableid = tableid;
	}

	public void setFieldname(String fieldname) {
		this.fieldname = fieldname;
	}

	public void setUserid(String userid) {
		this.userid = userid;
	}

	public void setMaxuser(Integer maxuser) {
		this.maxuser = maxuser;
	}

	public void setMaxsys(Integer maxsys) {
		this.maxsys = maxsys;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setSysnum(String sysnum) {
		this.sysnum = sysnum;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public void setUserindex(String userindex) {
		this.userindex = userindex;
	}

	public void setTreetablenameB(String treetablenameB) {
		this.treetablenameB = treetablenameB;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public void setLgcexpress(String lgcexpress) {
		this.lgcexpress = lgcexpress;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RdataField other = (RdataField) obj;
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

	public RdataField jsonToObject(JSONObject jsonObject) {
		return RdataFieldJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return RdataFieldJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return RdataFieldJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
