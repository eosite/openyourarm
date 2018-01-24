package com.glaf.chinaiss.data.domain;

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
import com.glaf.chinaiss.data.util.DataModelTreeJsonFactory;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "DATA_MODEL_TREE")
public class DataModelTree implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * 分类名称
	 */
	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * 模型分类代码
	 */
	@Column(name = "CODE_", length = 30)
	protected String code;

	/**
	 * 层级代码
	 */
	@Column(name = "TREEID_", length = 50)
	protected String treeId;

	/**
	 * 上级主键
	 */
	@Column(name = "TOPID_", length = 50)
	protected String topId;

	/**
	 * 父表主键
	 */
	@Column(name = "PARENTID_", length = 50)
	protected String parentId;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	public DataModelTree() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return this.name;
	}

	public String getCode() {
		return this.code;
	}

	public String getTreeId() {
		return this.treeId;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
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

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataModelTree other = (DataModelTree) obj;
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

	public DataModelTree jsonToObject(JSONObject jsonObject) {
		return DataModelTreeJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DataModelTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataModelTreeJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
