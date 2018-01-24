package com.glaf.teim.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.teim.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "EIM_SERVER_CATEGORY")
public class EimServerCategory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * code
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * name
	 */
	@Column(name = "NAME_", length = 150)
	protected String name;

	/**
	 * treeId
	 */
	@Column(name = "TREE_ID_", length = 300)
	protected String treeId;

	/**
	 * parentId
	 */
	@Column(name = "PARENT_ID_")
	protected Long parentId;

	/**
	 * createBy
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * updateBy
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * updateTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	/**
	 * deleteFlag
	 */
	@Column(name = "DELETE_FLAG_")
	protected Integer deleteFlag=0;

	public EimServerCategory() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public String getTreeId() {
		return this.treeId;
	}

	public Long getParentId() {
		return this.parentId;
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

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void Update(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EimServerCategory other = (EimServerCategory) obj;
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

	public EimServerCategory jsonToObject(JSONObject jsonObject) {
		return EimServerCategoryJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return EimServerCategoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EimServerCategoryJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
