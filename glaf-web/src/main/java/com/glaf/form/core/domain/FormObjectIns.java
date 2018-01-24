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
@Table(name = "FORM_OBJECT_INS")
public class FormObjectIns implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * NAME
	 */
	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * CODE
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * DESC
	 */
	@Column(name = "DESC_", length = 150)
	protected String desc;

	/**
	 * URL
	 */
	@Column(name = "URL_", length = 100)
	protected String url;

	/**
	 * STATUS
	 */
	@Column(name = "STATUS_")
	protected Integer status;

	/**
	 * PARENTID
	 */
	@Column(name = "PARENT_ID_", length = 50)
	protected String parent_id;

	/**
	 * CREATEBY
	 */
	@Column(name = "CREATE_BY_", length = 30)
	protected String createBy;

	/**
	 * CREATEDATE
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATE_DATE_")
	protected Date createDate;

	/**
	 * UPDATEBY
	 */
	@Column(name = "UPDATE_BY_", length = 30)
	protected String updateBy;

	/**
	 * UPDATEDATE
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATE_DATE_")
	protected Date updateDate;

	public FormObjectIns() {

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

	public String getDesc() {
		return this.desc;
	}

	public String getUrl() {
		return this.url;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getParent_id() {
		return this.parent_id;
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

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setParent_id(String parent_id) {
		this.parent_id = parent_id;
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
		FormObjectIns other = (FormObjectIns) obj;
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

	public FormObjectIns jsonToObject(JSONObject jsonObject) {
		return FormObjectInsJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormObjectInsJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormObjectInsJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
