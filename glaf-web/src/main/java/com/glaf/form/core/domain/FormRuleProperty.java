package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.form.core.util.*;

/**
 * 
 * 规则属性实例表
 *
 */

@Entity
@Table(name = "FORM_RULE_PROPERTY")
public class FormRuleProperty implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 外键，关联FORM_RULE的ID_
	 * 
	 */
	@Column(name = "RULEID_", length = 50)
	protected String ruleId;

	@Column(name = "NAME_", length = 50)
	protected String name;
	
	@Lob
	@Column(name = "VALUE_")
	protected String value;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public FormRuleProperty() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormRuleProperty other = (FormRuleProperty) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getId() {
		return id;
	}

	public String getName() {
		return this.name;
	}

	public String getRuleId() {
		return ruleId;
	}

	public String getValue() {
		return this.value;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormRuleProperty jsonToObject(JSONObject jsonObject) {
		return FormRulePropertyJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public JSONObject toJsonObject() {
		return FormRulePropertyJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormRulePropertyJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
