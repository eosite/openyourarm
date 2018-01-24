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
@Table(name = "FORM_WORKFLOW_RULE")
public class FormWorkFlowRule implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * actResDefId
	 */
	@Column(name = "ACT_RESDEF_ID_")
	protected Long actResDefId;

	/**
	 * actResourceId
	 */
	@Column(name = "ACT_RESOURCE_ID_", length = 50)
	protected String actResourceId;

	/**
	 * actTaskId
	 */
	@Column(name = "ACT_TASKID_", length = 50)
	protected String actTaskId;

	/**
	 * actTaskName
	 */
	@Column(name = "ACT_TASKNAME_", length = 100)
	protected String actTaskName;

	/**
	 * bytes
	 */
	@javax.persistence.Lob
	@Column(name = "BYTES_")
	protected String bytes;

	/**
	 * creator
	 */
	@Column(name = "CREATOR_", length = 20)
	protected String creator;

	/**
	 * createDatetime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME_")
	protected Date createDatetime;

	/**
	 * modifier
	 */
	@Column(name = "MODIFIER_", length = 20)
	protected String modifier;

	/**
	 * modifyDatetime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME_")
	protected Date modifyDatetime;

	/**
	 * pageId
	 */
	@Column(name = "PAGE_ID_", length = 255)
	protected String pageId;

	/**
	 * version
	 */
	@Column(name = "VERSION_")
	protected Integer version;

	/**
	 * defId 表单+流程 方案ID
	 */
	@Column(name = "DEF_ID_", length = 50)
	protected String defId;

	public FormWorkFlowRule() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getActResDefId() {
		return this.actResDefId;
	}

	public String getActResourceId() {
		return this.actResourceId;
	}

	public String getActTaskId() {
		return this.actTaskId;
	}

	public String getActTaskName() {
		return this.actTaskName;
	}

	public String getBytes() {
		return this.bytes;
	}

	public String getCreator() {
		return this.creator;
	}

	public Date getCreateDatetime() {
		return this.createDatetime;
	}

	public String getCreateDatetimeString() {
		if (this.createDatetime != null) {
			return DateUtils.getDateTime(this.createDatetime);
		}
		return "";
	}

	public String getModifier() {
		return this.modifier;
	}

	public Date getModifyDatetime() {
		return this.modifyDatetime;
	}

	public String getModifyDatetimeString() {
		if (this.modifyDatetime != null) {
			return DateUtils.getDateTime(this.modifyDatetime);
		}
		return "";
	}

	public String getPageId() {
		return this.pageId;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setActResDefId(Long actResDefId) {
		this.actResDefId = actResDefId;
	}

	public void setActResourceId(String actResourceId) {
		this.actResourceId = actResourceId;
	}

	public void setActTaskId(String actTaskId) {
		this.actTaskId = actTaskId;
	}

	public void setActTaskName(String actTaskName) {
		this.actTaskName = actTaskName;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDatetime(Date createDatetime) {
		this.createDatetime = createDatetime;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormWorkFlowRule other = (FormWorkFlowRule) obj;
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

	public FormWorkFlowRule jsonToObject(JSONObject jsonObject) {
		return FormWorkFlowRuleJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormWorkFlowRuleJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormWorkFlowRuleJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getDefId() {
		return defId;
	} 

	public void setDefId(String defId) {
		this.defId = defId;
	}

}
