package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 * 
 */

@Entity
@Table(name = "FORM_TASK")
public class FormTask implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 页面id
	 */
	@Column(name = "PAGEID_", length = 50)
	protected String pageId;

	/**
	 * 表单任务主键id
	 */
	@Column(name = "TMID_")
	protected Long tmId;

	/**
	 * 主数据表名
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	/**
	 * 主键值
	 */
	@Column(name = "IDVALUE_", length = 50)
	protected String idValue;

	/**
	 * 主键参数
	 */
	@Column(name = "VARIABLEKEY_", length = 50)
	protected String variableKey;

	/**
	 * 完成状态
	 */
	@Column(name = "STATUS_")
	protected Integer status;

	/**
	 * CREATEBY_
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * CREATEDATE_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * UPDATEBY_
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * UPDATEDATE_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	public FormTask() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPageId() {
		return this.pageId;
	}

	public Long getTmId() {
		return this.tmId;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getIdValue() {
		return this.idValue;
	}

	public String getVariableKey() {
		return this.variableKey;
	}

	public Integer getStatus() {
		return this.status;
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

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setTmId(Long tmId) {
		this.tmId = tmId;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public void setVariableKey(String variableKey) {
		this.variableKey = variableKey;
	}

	public void setStatus(Integer status) {
		this.status = status;
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
		FormTask other = (FormTask) obj;
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

	public FormTask jsonToObject(JSONObject jsonObject) {
		return FormTaskJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormTaskJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormTaskJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
