package com.glaf.chinaiss.data.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.chinaiss.data.util.SysDataTmplJsonFactory;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_DATA_TMPL")
public class SysDataTmpl implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * 模版名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 100)
	protected String description;

	/**
	 * 模版JSON
	 */
	@Lob
	@Column(name = "JSON_")
	protected String json;

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

	public SysDataTmpl() {

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

	public String getDescription() {
		return this.description;
	}

	public String getJson() {
		return this.json;
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

	public void setDescription(String description) {
		this.description = description;
	}

	public void setJson(String json) {
		this.json = json;
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
		SysDataTmpl other = (SysDataTmpl) obj;
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

	public SysDataTmpl jsonToObject(JSONObject jsonObject) {
		return SysDataTmplJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SysDataTmplJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SysDataTmplJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
