package com.glaf.etl.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.etl.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "ETL_DATASRC")
public class ETLDataSrc implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * 数据库id
	 */
	@Column(name = "DATABASEID_")
	protected Long databaseId;

	/**
	 * 数据库名称
	 */
	@Column(name = "DATABASENAME_", length = 50)
	protected String databaseName;

	/**
	 * 数据源名称
	 */
	@Column(name = "SOURCENAME_", length = 50)
	protected String sourceName;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	/**
	 * 数据列
	 */
	@Column(name = "COLUMNINFOS_")
	protected byte[] columnInfos;

	/**
	 * 查询语句
	 */
	@Column(name = "SQL_")
	protected byte[] sql;

	/**
	 * 辅助字段
	 */
	@Column(name = "RESTOREJSON_")
	protected byte[] restoreJson;

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
	 * 更新人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 更新时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	public ETLDataSrc() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getDatabaseId() {
		return this.databaseId;
	}

	public String getDatabaseName() {
		return this.databaseName;
	}

	public String getSourceName() {
		return this.sourceName;
	}

	public String getTableName() {
		return this.tableName;
	}

	public byte[] getColumnInfos() {
		return this.columnInfos;
	}

	public byte[] getSql() {
		return this.sql;
	}

	public byte[] getRestoreJson() {
		return this.restoreJson;
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

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setColumnInfos(byte[] columnInfos) {
		this.columnInfos = columnInfos;
	}

	public void setSql(byte[] sql) {
		this.sql = sql;
	}

	public void setRestoreJson(byte[] restoreJson) {
		this.restoreJson = restoreJson;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ETLDataSrc other = (ETLDataSrc) obj;
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

	public ETLDataSrc jsonToObject(JSONObject jsonObject) {
		return ETLDataSrcJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ETLDataSrcJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ETLDataSrcJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
