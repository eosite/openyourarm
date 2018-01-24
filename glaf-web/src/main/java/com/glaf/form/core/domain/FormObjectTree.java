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
@Table(name = "FORM_OBJECT_TREE")
public class FormObjectTree implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * NAME
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * CODE
	 */
	@Column(name = "CODE_", length = 30)
	protected String code;

	/**
	 * DESC
	 */
	@Column(name = "DESC_", length = 150)
	protected String desc;

	/**
	 * TOPID
	 */
	@Column(name = "TOP_ID_", length = 20)
	protected String topId;

	/**
	 * TREEID
	 */
	@Column(name = "TREE_ID_", length = 100)
	protected String treeId;

	/**
	 * LEVEL
	 */
	@Column(name = "LEVEL_")
	protected Integer level;

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

	public FormObjectTree() {

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

	public String getTopId() {
		return this.topId;
	}

	public String getTreeId() {
		return this.treeId;
	}

	public Integer getLevel() {
		return this.level;
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

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setLevel(Integer level) {
		this.level = level;
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
		FormObjectTree other = (FormObjectTree) obj;
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

	public FormObjectTree jsonToObject(JSONObject jsonObject) {
		return FormObjectTreeJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormObjectTreeJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormObjectTreeJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
