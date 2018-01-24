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
@Table(name = "SYS_DATA_ORDERBY")
public class OrderBySegment implements java.lang.Comparable<OrderBySegment>, Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 数据集编号
	 */
	@Column(name = "DATASETID_", length = 50)
	protected String datasetId;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	/**
	 * 列名
	 */
	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	/**
	 * 排序（ asc或desc）
	 */
	@Column(name = "SORT_", length = 5)
	protected String sort;

	/**
	 * 顺序
	 */
	@Column(name = "ORDINAL_")
	protected Integer ordinal;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	public OrderBySegment() {

	}

	public int compareTo(OrderBySegment o) {
		if (o == null) {
			return -1;
		}

		OrderBySegment field = o;

		int l = this.ordinal - field.getOrdinal();

		int ret = 0;

		if (l > 0) {
			ret = 1;
		} else if (l < 0) {
			ret = -1;
		}
		return ret;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		OrderBySegment other = (OrderBySegment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getColumnName() {
		return this.columnName;
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
		return this.datasetId;
	}

	public Long getId() {
		return this.id;
	}

	public Integer getOrdinal() {
		return this.ordinal;
	}

	public String getSort() {
		return this.sort;
	}

	public String getTableName() {
		return this.tableName;
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
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public OrderBySegment jsonToObject(JSONObject jsonObject) {
		return OrderBySegmentJsonFactory.jsonToObject(jsonObject);
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
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

	public void setId(Long id) {
		this.id = id;
	}

	public void setOrdinal(Integer ordinal) {
		this.ordinal = ordinal;
	}

	public void setSort(String sort) {
		this.sort = sort;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public JSONObject toJsonObject() {
		return OrderBySegmentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return OrderBySegmentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
