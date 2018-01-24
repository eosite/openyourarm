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
@Table(name = "FORM_RULE_AUDIT")
public class FormRuleAudit implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 80, nullable = false)
	protected String id;

	@Column(name = "COMPONENTID_")
	protected Long componentId;

	@Column(name = "APPID_", length = 50)
	protected String appId;

	@Column(name = "PAGEID_", length = 50)
	protected String pageId;

	@Column(name = "DEPLOYMENTID_", length = 100)
	protected String deploymentId;

	@Column(name = "NAME_", length = 100)
	protected String name;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Lob
	@Column(name = "VALUE_")
	protected String value;

	@Lob
	@Column(name = "SNAPSHOT_")
	protected String snapshot;

	@Column(name = "VERSION_")
	protected int version;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public FormRuleAudit() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormRuleAudit other = (FormRuleAudit) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getAppId() {
		return this.appId;
	}

	public Long getComponentId() {
		return this.componentId;
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

	public String getDeploymentId() {
		return this.deploymentId;
	}

	public String getId() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public String getPageId() {
		return this.pageId;
	}

	public String getSnapshot() {
		return this.snapshot;
	}

	public String getType() {
		return type;
	}

	public String getValue() {
		return this.value;
	}

	public int getVersion() {
		return this.version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormRuleAudit jsonToObject(JSONObject jsonObject) {
		return FormRuleAuditJsonFactory.jsonToObject(jsonObject);
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setSnapshot(String snapshot) {
		this.snapshot = snapshot;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public JSONObject toJsonObject() {
		return FormRuleAuditJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormRuleAuditJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
