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
@Table(name = "SYS_DATASET_MAPPING")
public class DataSetMapping implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 数据集名称
	 */
	@Column(name = "DSNAME_", length = 50)
	protected String dsName;
	/**
	 * 映射数据集ID
	 */
	@Column(name = "DSMID_", length = 50)
	protected String dsmId;

	/**
	 * 映射数据集名称
	 */
	@Column(name = "DSMNAME_", length = 50)
	protected String dsmName;

	/**
	 * 启用状态 1、启用；0:禁用。同一个源数据集只有一个启用
	 */
	@Column(name = "STATUS_")
	protected Integer status;

	/**
	 * 映射类型(备用)
	 */
	@Column(name = "TYPE_", length = 10)
	protected String type;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

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

	public DataSetMapping() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDsName() {
		return this.dsName;
	}

	public String getDsmId() {
		return this.dsmId;
	}

	public String getDsmName() {
		return this.dsmName;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getType() {
		return this.type;
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

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public void setDsmId(String dsmId) {
		this.dsmId = dsmId;
	}

	public void setDsmName(String dsmName) {
		this.dsmName = dsmName;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
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
		DataSetMapping other = (DataSetMapping) obj;
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

	public DataSetMapping jsonToObject(JSONObject jsonObject) {
		return DataSetMappingJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return DataSetMappingJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataSetMappingJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
