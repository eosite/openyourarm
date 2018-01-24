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
@Table(name = "SYS_TREETABLE_MODIFIER")
public class TreeTableModifier implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 100)
	protected String tableName;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 数据库编号
	 */
	@Column(name = "DATABASEIDS_", length = 2000)
	protected String databaseIds;

	/**
	 * 主键列
	 */
	@Column(name = "PRIMARYKEY_", length = 50)
	protected String primaryKey;

	/**
	 * Id列
	 */
	@Column(name = "IDCOLUMN_", length = 50)
	protected String idColumn;

	/**
	 * 父ParentId列
	 */
	@Column(name = "PARENTIDCOLUMN_", length = 50)
	protected String parentIdColumn;

	/**
	 * TreeId列
	 */
	@Column(name = "TREEIDCOLUMN_", length = 50)
	protected String treeIdColumn;

	/**
	 * 层级列
	 */
	@Column(name = "LEVELCOLUMN_", length = 50)
	protected String levelColumn;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	public TreeTableModifier() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TreeTableModifier other = (TreeTableModifier) obj;
		if (id != other.id)
			return false;
		return true;
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

	public String getDatabaseIds() {
		return this.databaseIds;
	}

	public long getId() {
		return id;
	}

	public String getIdColumn() {
		return this.idColumn;
	}

	public String getLevelColumn() {
		return this.levelColumn;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getParentIdColumn() {
		return this.parentIdColumn;
	}

	public String getPrimaryKey() {
		return this.primaryKey;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getTitle() {
		return this.title;
	}

	public String getTreeIdColumn() {
		return this.treeIdColumn;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateTimeString() {
		if (this.updateTime != null) {
			return DateUtils.getDateTime(this.updateTime);
		}
		return "";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public TreeTableModifier jsonToObject(JSONObject jsonObject) {
		return TreeTableModifierJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseIds(String databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public void setLevelColumn(String levelColumn) {
		this.levelColumn = levelColumn;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setParentIdColumn(String parentIdColumn) {
		this.parentIdColumn = parentIdColumn;
	}

	public void setPrimaryKey(String primaryKey) {
		this.primaryKey = primaryKey;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setTreeIdColumn(String treeIdColumn) {
		this.treeIdColumn = treeIdColumn;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public JSONObject toJsonObject() {
		return TreeTableModifierJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TreeTableModifierJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
