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
@Table(name = "SYS_TREETABLE_SYNTHETIC_RULE")
public class TreeTableSyntheticRule implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	/**
	 * 树表合成编号
	 */
	@Column(name = "SYNTHETICID_")
	protected long syntheticId;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 目标表字段名
	 */
	@Column(name = "COLUMNNAME_", length = 500)
	protected String columnName;

	/**
	 * 列标签
	 */
	@Column(name = "COLUMNLABEL_", length = 500)
	protected String columnLabel;

	/**
	 * 列中文名称
	 */
	@Column(name = "COLUMNTITLE_", length = 200)
	protected String columnTitle;

	/**
	 * 列字段长度
	 */
	@Column(name = "COLUMNSIZE_")
	protected int columnSize;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	/**
	 * 映射到源树表Id字段
	 */
	@Column(name = "MAPPINGTOSOURCEIDCOLUMN_", length = 500)
	protected String mappingToSourceIdColumn;

	/**
	 * 映射到目标表字段
	 */
	@Column(name = "MAPPINGTOTARGETCOLUMN_", length = 500)
	protected String mappingToTargetColumn;

	/**
	 * 映射到目标表别名
	 */
	@Column(name = "MAPPINGTOTARGETALIAS_", length = 500)
	protected String mappingToTargetAlias;

	/**
	 * 数据集编号
	 */
	@Column(name = "DATASETID_", length = 50)
	protected String datasetId;

	@javax.persistence.Transient
	protected String datasetName;

	/**
	 * SQL语句定义编号
	 */
	@Column(name = "SQLDEFID_")
	protected long sqlDefId;

	@javax.persistence.Transient
	protected String sqlDefName;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	public TreeTableSyntheticRule() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeTableSyntheticRule other = (TreeTableSyntheticRule) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getColumnLabel() {
		return columnLabel;
	}

	public String getColumnName() {
		if (columnName != null) {
			columnName = columnName.toLowerCase();
		}
		return columnName;
	}

	public int getColumnSize() {
		return columnSize;
	}

	public String getColumnTitle() {
		return columnTitle;
	}

	public String getDatasetId() {
		return this.datasetId;
	}

	public String getDatasetName() {
		return datasetName;
	}

	public long getId() {
		return this.id;
	}

	public int getLocked() {
		return locked;
	}

	public String getMappingToSourceIdColumn() {
		if (mappingToSourceIdColumn != null) {
			mappingToSourceIdColumn = mappingToSourceIdColumn.toLowerCase();
		}
		return this.mappingToSourceIdColumn;
	}

	public String getMappingToTargetAlias() {
		return mappingToTargetAlias;
	}

	public String getMappingToTargetColumn() {
		if (mappingToTargetColumn != null) {
			mappingToTargetColumn = mappingToTargetColumn.toLowerCase();
		}
		return this.mappingToTargetColumn;
	}

	public String getName() {
		return this.name;
	}

	public long getSqlDefId() {
		return this.sqlDefId;
	}

	public String getSqlDefName() {
		return sqlDefName;
	}

	public long getSyntheticId() {
		return this.syntheticId;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public TreeTableSyntheticRule jsonToObject(JSONObject jsonObject) {
		return TreeTableSyntheticRuleJsonFactory.jsonToObject(jsonObject);
	}

	public void setColumnLabel(String columnLabel) {
		this.columnLabel = columnLabel;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnSize(int columnSize) {
		this.columnSize = columnSize;
	}

	public void setColumnTitle(String columnTitle) {
		this.columnTitle = columnTitle;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public void setDatasetName(String datasetName) {
		this.datasetName = datasetName;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setMappingToSourceIdColumn(String mappingToSourceIdColumn) {
		this.mappingToSourceIdColumn = mappingToSourceIdColumn;
	}

	public void setMappingToTargetAlias(String mappingToTargetAlias) {
		this.mappingToTargetAlias = mappingToTargetAlias;
	}

	public void setMappingToTargetColumn(String mappingToTargetColumn) {
		this.mappingToTargetColumn = mappingToTargetColumn;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSqlDefId(long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setSqlDefName(String sqlDefName) {
		this.sqlDefName = sqlDefName;
	}

	public void setSyntheticId(long syntheticId) {
		this.syntheticId = syntheticId;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		return TreeTableSyntheticRuleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TreeTableSyntheticRuleJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
