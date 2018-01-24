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
import com.glaf.isdp.util.CellDataFieldJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_DATA_FIELD")
public class CellDataField implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "TABLEID", length = 50)
	protected String tableId;

	@Column(name = "FIELDNAME", length = 50)
	protected String fieldName;

	@Column(name = "USERID", length = 50)
	protected String userId;

	@Column(name = "MAXUSER")
	protected Integer maxUser;

	@Column(name = "MAXSYS")
	protected Integer maxSys;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "SYSNUM", length = 100)
	protected String sysNum;

	@Column(name = "TABLENAME", length = 50)
	protected String tableName;

	@Column(name = "DNAME", length = 50)
	protected String dname;

	@Column(name = "USERINDEX", length = 50)
	protected String userIndex;

	@Column(name = "TREETABLENAME_B", length = 50)
	protected String treeTableNameB;
	
	@Column(name = "LGCEXPRESS", length = 1000)
	protected String lgcexpress;
	
	@Column(name = "FORMULA", length = 1000)
	protected String formula;

	public CellDataField() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTableId() {
		return this.tableId;
	}

	public String getFieldName() {
		return this.fieldName;
	}

	public String getUserId() {
		return this.userId;
	}

	public Integer getMaxUser() {
		return this.maxUser;
	}

	public Integer getMaxSys() {
		return this.maxSys;
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

	public String getSysNum() {
		return this.sysNum;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getDname() {
		return this.dname;
	}

	public String getUserIndex() {
		return this.userIndex;
	}

	public String getTreeTableNameB() {
		return this.treeTableNameB;
	}

	public void setTableId(String tableId) {
		this.tableId = tableId;
	}

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setMaxUser(Integer maxUser) {
		this.maxUser = maxUser;
	}

	public void setMaxSys(Integer maxSys) {
		this.maxSys = maxSys;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setSysNum(String sysNum) {
		this.sysNum = sysNum;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setDname(String dname) {
		this.dname = dname;
	}

	public void setUserIndex(String userIndex) {
		this.userIndex = userIndex;
	}

	public void setTreeTableNameB(String treeTableNameB) {
		this.treeTableNameB = treeTableNameB;
	}

	public String getLgcexpress() {
		return lgcexpress;
	}

	public void setLgcexpress(String lgcexpress) {
		this.lgcexpress = lgcexpress;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellDataField other = (CellDataField) obj;
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

	public CellDataField jsonToObject(JSONObject jsonObject) {
		return CellDataFieldJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellDataFieldJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellDataFieldJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
