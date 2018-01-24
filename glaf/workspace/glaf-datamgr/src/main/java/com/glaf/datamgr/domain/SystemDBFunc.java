package com.glaf.datamgr.domain;

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
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.SystemDBFuncJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYSTEM_DB_FUNC_")
public class SystemDBFunc implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * 代码
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 公共参数说明
	 */
	@Lob
	@Column(name = "PUBLIC_PARAMS_")
	protected String publicParams;

	/**
	 * 描述
	 */
	@Column(name = "DESC_", length = 150)
	protected String desc;

	/**
	 * CREATEBY_
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * CREATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * UPDATEBY_
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * UPDATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	/**
	 * DELETE_FLAG_
	 */
	@Column(name = "DELETE_FLAG_")
	protected Integer deleteFlag;

	public SystemDBFunc() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public String getPublicParams() {
		return this.publicParams;
	}

	public String getDesc() {
		return this.desc;
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

	public void setPublicParams(String publicParams) {
		this.publicParams = publicParams;
	}

	public void setDesc(String desc) {
		this.desc = desc;
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
		SystemDBFunc other = (SystemDBFunc) obj;
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

	public SystemDBFunc jsonToObject(JSONObject jsonObject) {
		return SystemDBFuncJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SystemDBFuncJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SystemDBFuncJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
