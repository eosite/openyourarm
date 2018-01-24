package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_SQL_PARAM")
public class SqlParameter implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "DATASETID_", length = 50)
	protected String datasetId;

	@Column(name = "SQLDEFID_")
	protected Long sqlDefId;

	@Column(name = "NAME_", length = 50)
	protected String name;

	@Column(name = "MAPPING_", length = 50)
	protected String mapping;

	@Column(name = "TITLE_", length = 200)
	protected String title;

	@Column(name = "DEFAULTVALUE_", length = 500)
	protected String defaultValue;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "COLLECTION_", length = 50)
	protected String collection;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	public SqlParameter() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SqlParameter other = (SqlParameter) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCollection() {
		return this.collection;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public String getDatasetId() {
		return datasetId;
	}

	public String getDefaultValue() {
		return defaultValue;
	}

	public Long getId() {
		return this.id;
	}

	public String getMapping() {
		return mapping;
	}

	public String getName() {
		return this.name;
	}

	public Long getSqlDefId() {
		return this.sqlDefId;
	}

	public String getTitle() {
		return title;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SqlParameter jsonToObject(JSONObject jsonObject) {
		return SqlParameterJsonFactory.jsonToObject(jsonObject);
	}

	public void setCollection(String collection) {
		this.collection = collection;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public void setDefaultValue(String defaultValue) {
		this.defaultValue = defaultValue;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setMapping(String mapping) {
		this.mapping = mapping;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		return SqlParameterJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SqlParameterJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
