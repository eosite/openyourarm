package com.glaf.datamgr.domain;

import java.io.*;
import java.util.Date;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_OPERATION")
public class Operation implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	@Column(name = "NAME_", length = 200)
	protected String name;

	@Column(name = "CODE_", length = 200)
	protected String code;

	@Column(name = "DESCRIPTION_", length = 2000)
	protected String description;

	@Column(name = "METHOD_", length = 50)
	protected String method;

	@Column(name = "URL_", length = 500)
	protected String url;

	@Column(name = "TABLENAME_", length = 50)
	protected String tablename;

	@Column(name = "IDFIELD_", length = 50)
	protected String idField;

	@Column(name = "IDCOLUMN_", length = 50)
	protected String idColumn;

	@Column(name = "IDJAVATYPE_", length = 50)
	protected String idJavaType;

	@Column(name = "SQLDEFID_")
	protected Long sqlDefId;

	@Column(name = "SORT_")
	protected Integer sort;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	public Operation() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Operation other = (Operation) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getCode() {
		return this.code;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public String getDescription() {
		return this.description;
	}

	public long getId() {
		return this.id;
	}

	public String getIdColumn() {
		return this.idColumn;
	}

	public String getIdField() {
		return this.idField;
	}

	public String getIdJavaType() {
		return this.idJavaType;
	}

	public String getMethod() {
		return this.method;
	}

	public String getName() {
		return this.name;
	}

	public Integer getSort() {
		return this.sort;
	}

	public Long getSqlDefId() {
		return this.sqlDefId;
	}

	public String getTablename() {
		return this.tablename;
	}

	public String getUrl() {
		return url;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public Operation jsonToObject(JSONObject jsonObject) {
		return OperationJsonFactory.jsonToObject(jsonObject);
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public void setIdField(String idField) {
		this.idField = idField;
	}

	public void setIdJavaType(String idJavaType) {
		this.idJavaType = idJavaType;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSort(Integer sort) {
		this.sort = sort;
	}

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public JSONObject toJsonObject() {
		return OperationJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return OperationJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
