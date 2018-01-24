package com.glaf.chinaiss.data.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.chinaiss.data.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DATA_MODEL_COLUMNS")
public class DataModelColumns implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * ColumnName
	 */
	@Column(name = "COLUMNNAME_", length = 50)
	protected String columnName;

	/**
	 * Name
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * tableName
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	/**
	 * Description
	 */
	@Column(name = "DESCRIPTION_", length = 100)
	protected String description;

	/**
	 * Type
	 */
	@Column(name = "TYPE_", length = 10)
	protected String type;

	/**
	 * Length
	 */
	@Column(name = "LENGTH_")
	protected Integer length;

	/**
	 * TopId
	 */
	@Column(name = "TOPID_", length = 50)
	protected String topId;

	/**
	 * ParentId
	 */
	@Column(name = "PARENTID_", length = 50)
	protected String parentId;

	/**
	 * CreateBy
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * CreateDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * UpdateBy
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * UpdateDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	/**
	 * ListNo
	 */
	@Column(name = "LISTNO_")
	protected Integer listNo;

	public DataModelColumns() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getColumnName() {
		return this.columnName;
	}

	public String getDescription() {
		return this.description;
	}

	public String getType() {
		return this.type;
	}

	public Integer getLength() {
		return this.length;
	}

	public String getTopId() {
		return this.topId;
	}

	public String getParentId() {
		return this.parentId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateDateString() {
		if (this.createDate != null) {
			return DateUtils.getDateTime(this.createDate);
		}
		return "";
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateDateString() {
		if (this.updateDate != null) {
			return DateUtils.getDateTime(this.updateDate);
		}
		return "";
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setLength(Integer length) {
		this.length = length;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataModelColumns other = (DataModelColumns) obj;
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

	public DataModelColumns jsonToObject(JSONObject jsonObject) {
		return DataModelColumnsJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DataModelColumnsJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataModelColumnsJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

}
