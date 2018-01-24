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
import com.glaf.form.core.query.FormTaskQuery;
import com.glaf.form.core.service.FormTaskService;
import com.glaf.form.core.util.FormTaskmainJsonFactory;

/**
 * 
 * 实体对象
 * 
 */

@Entity
@Table(name = "FORM_TASKMAIN")
public class FormTaskmain implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	/**
	 * 方案id
	 */
	@Column(name = "PLANID_")
	protected Long planId;

	/**
	 * 方案定义id
	 */
	@Column(name = "DEFID_", length = 50)
	protected String defId;

	/**
	 * 流程定义id
	 */
	@Column(name = "DEFINEDID_", length = 50)
	protected String definedId;

	/**
	 * 流程实例id
	 */
	@Column(name = "PROCESSID_", length = 50)
	protected String processId;
	
	/**
	 * 父流程实例id
	 */
	@Column(name = "P_PROCESSID_", length = 50)
	protected String p_processId;

	/**
	 * 启动控制参数值
	 */
	@Column(name = "VARIABLEVAL_", length = 100)
	protected String variableVal;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 100)
	protected String name;

	/**
	 * 完成状态 1、完成 -1、退回
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

	/**
	 * 扩展字段
	 */
	@Column(name = "EXT1_", length = 200)
	protected String ext1;

	@javax.persistence.Transient
	protected List<FormTask> formTasks = new ArrayList<FormTask>();

	public FormTaskmain() {

	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getPlanId() {
		return this.planId;
	}

	public String getDefinedId() {
		return this.definedId;
	}

	public String getProcessId() {
		return this.processId;
	}

	public String getVariableVal() {
		return this.variableVal;
	}

	public String getName() {
		return this.name;
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

	public void setPlanId(Long planId) {
		this.planId = planId;
	}

	public void setDefinedId(String definedId) {
		this.definedId = definedId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setVariableVal(String variableVal) {
		this.variableVal = variableVal;
	}

	public void setName(String name) {
		this.name = name;
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
		FormTaskmain other = (FormTaskmain) obj;
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

	public FormTaskmain jsonToObject(JSONObject jsonObject) {
		return FormTaskmainJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormTaskmainJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormTaskmainJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<FormTask> getFormTasks() {

		if (CollectionUtils.isNotEmpty(formTasks) || id == null) {
			return formTasks;
		}

		FormTaskService service = ContextFactory//
				.getBean("com.glaf.form.core.service.formTaskService");

		FormTaskQuery query = new FormTaskQuery();
		query.setTmId(id);
		formTasks = service.list(query);
		
		if(formTasks == null) {
			formTasks = new ArrayList<>();
		}

		return formTasks;
	}

	public void setFormTasks(List<FormTask> formTasks) {
		this.formTasks = formTasks;
	}

	public String getDefId() {
		return defId;
	}

	public void setDefId(String defId) {
		this.defId = defId;
	}

	public String getExt1() {
		return ext1;
	}

	public void setExt1(String ext1) {
		this.ext1 = ext1;
	}

	public String getP_processId() {
		return p_processId;
	}

	public void setP_processId(String p_processId) {
		this.p_processId = p_processId;
	}

}
