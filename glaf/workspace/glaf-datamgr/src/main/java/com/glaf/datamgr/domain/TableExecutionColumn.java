package com.glaf.datamgr.domain;

import java.io.*;
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
@Table(name = "SYS_TABLE_EXECUTION_COLUMN")
public class TableExecutionColumn implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 主表编号
	 */
	@Column(name = "EXECUTIONID_", length = 50)
	protected String executionId;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 字段名
	 */
	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	/**
	 * 字段类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 值表达式
	 */
	@Column(name = "VALUEEXPRESSION_", length = 500)
	protected String valueExpression;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected Integer locked;

	public TableExecutionColumn() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getExecutionId() {
		return this.executionId;
	}

	public String getTitle() {
		return this.title;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getType() {
		return this.type;
	}

	public String getValueExpression() {
		return this.valueExpression;
	}

	public Integer getLocked() {
		return this.locked;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TableExecutionColumn other = (TableExecutionColumn) obj;
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

	public TableExecutionColumn jsonToObject(JSONObject jsonObject) {
		return TableExecutionColumnJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return TableExecutionColumnJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TableExecutionColumnJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
