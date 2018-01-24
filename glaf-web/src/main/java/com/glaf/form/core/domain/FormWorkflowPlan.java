package com.glaf.form.core.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.context.ContextFactory;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.query.FormWorkFlowRuleQuery;
import com.glaf.form.core.service.FormTaskService;
import com.glaf.form.core.service.FormWorkFlowRuleService;
import com.glaf.form.core.util.FormWorkflowPlanJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_WORKFLOW_PLAN")
public class FormWorkflowPlan implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * PROCESSDEFID
	 */
	@Column(name = "PROCESSDEF_ID_", length = 100)
	protected String processDefId;

	/**
	 * KEY
	 */
	@Column(name = "KEY_", length = 100)
	protected String key;

	/**
	 * PAGEID
	 */
	@Column(name = "PAGE_ID_", length = 50)
	protected String pageId;

	/**
	 * PAGEID
	 */
	@Column(name = "PAGE_NAME_", length = 200)
	protected String pageName;

	/**
	 * BYTES
	 */
	@javax.persistence.Lob
	@Column(name = "BYTES_")
	protected String bytes;

	/**
	 * CREATOR
	 */
	@Column(name = "CREATOR_", length = 30)
	protected String creator;

	/**
	 * CREATEDATETIME
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME_")
	protected Date createDateTime;

	/**
	 * MODIFIER
	 */
	@Column(name = "MODIFIER_", length = 30)
	protected String modifier;

	/**
	 * MODIFYDATETIME
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME_")
	protected Date modifyDateTime;

	/**
	 * DEF_ID
	 */
	@Column(name = "DEF_ID_", length = 50)
	protected String defId;

	/**
	 * VERSION
	 */
	@Column(name = "VERSION_")
	protected Integer version;

	@javax.persistence.Transient
	protected List<FormWorkFlowRule> rules;

	public FormWorkflowPlan() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPageId() {
		return this.pageId;
	}

	public String getBytes() {
		return this.bytes;
	}

	public String getCreator() {
		return this.creator;
	}

	public Date getCreateDateTime() {
		return this.createDateTime;
	}

	public String getCreateDateTimeString() {
		if (this.createDateTime != null) {
			return DateUtils.getDateTime(this.createDateTime);
		}
		return "";
	}

	public String getModifier() {
		return this.modifier;
	}

	public Date getModifyDateTime() {
		return this.modifyDateTime;
	}

	public String getModifyDateTimeString() {
		if (this.modifyDateTime != null) {
			return DateUtils.getDateTime(this.modifyDateTime);
		}
		return "";
	}

	public String getDefId() {
		return this.defId;
	}

	public Integer getVersion() {
		return this.version;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setBytes(String bytes) {
		this.bytes = bytes;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreateDateTime(Date createDateTime) {
		this.createDateTime = createDateTime;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifyDateTime(Date modifyDateTime) {
		this.modifyDateTime = modifyDateTime;
	}

	public void setDefId(String defId) {
		this.defId = defId;
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
		FormWorkflowPlan other = (FormWorkflowPlan) obj;
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

	public FormWorkflowPlan jsonToObject(JSONObject jsonObject) {
		return FormWorkflowPlanJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormWorkflowPlanJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormWorkflowPlanJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getProcessDefId() {
		return processDefId;
	}

	public void setProcessDefId(String processDefId) {
		this.processDefId = processDefId;
	}

	public String getPageName() {
		return pageName;
	}

	public void setPageName(String pageName) {
		this.pageName = pageName;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public List<FormWorkFlowRule> getRules() {

		if (CollectionUtils.isNotEmpty(rules) || id == null) {
			return rules;
		}

		FormWorkFlowRuleService service = ContextFactory//
				.getBean("com.glaf.form.core.service.formWorkFlowRuleService");

		FormWorkFlowRuleQuery query = new FormWorkFlowRuleQuery();

		query.setDefId(this.getDefId());
		query.setVersion(this.getVersion());

		rules = service.list(query);

		if (rules == null) {
			rules = new ArrayList<>();
		}

		return rules;
	}

	public void setRules(List<FormWorkFlowRule> rules) {
		this.rules = rules;
	}

}
