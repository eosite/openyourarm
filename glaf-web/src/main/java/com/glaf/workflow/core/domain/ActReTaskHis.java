package com.glaf.workflow.core.domain;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.workflow.core.util.ActReTaskHisJsonFactory;

/**
 * 流程任务历史表
 */
@Entity
@Table(name = "ACT_RE_TASK_HIS")
public class ActReTaskHis implements java.io.Serializable, JSONable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Integer id;

	/**
	 * 操作人
	 */
	@Column(name = "ACTORID_", length = 50)
	protected String actorId;

	/**
	 * 任务id
	 */
	@Column(name = "TASKID_", length = 10)
	protected String taskId;

	/**
	 * 任务名称
	 */
	@Column(name = "TASKNAME_", length = 50)
	protected String taskName;

	/**
	 * 任务key
	 */
	@Column(name = "TASKKEY_", length = 50)
	protected String taskKey;

	/**
	 * 实例id
	 */
	@Column(name = "PROCESSID_", length = 10)
	protected String processId;

	/**
	 * 来源任务key
	 */
	@Column(name = "FROMKEY_", length = 50)
	protected String fromKey;

	/**
	 * 来源任务id
	 */
	@Column(name = "FROMID_", length = 10)
	protected String fromId;

	/**
	 * 来源任务名称
	 */
	@Column(name = "FROMNAME_", length = 50)
	protected String fromName;

	/**
	 * 来源任务名称
	 */
	@Column(name = "FROMACTORID_", length = 50)
	protected String fromActorId;

	/**
	 * 参数json
	 */
	@Column(name = "VARIABLES_")
	protected String variables;

	/**
	 * 提交类型
	 */
	@Column(name = "SUBTYPE_")
	protected String subType;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public String getTaskId() {
		return taskId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTaskKey() {
		return taskKey;
	}

	public void setTaskKey(String taskKey) {
		this.taskKey = taskKey;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getFromKey() {
		return fromKey;
	}

	public void setFromKey(String fromKey) {
		this.fromKey = fromKey;
	}

	public String getFromId() {
		return fromId;
	}

	public void setFromId(String fromId) {
		this.fromId = fromId;
	}

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActReTaskHis other = (ActReTaskHis) obj;
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

	public ActReTaskHis jsonToObject(JSONObject jsonObject) {
		return ActReTaskHisJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return ActReTaskHisJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ActReTaskHisJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getFromActorId() {
		return fromActorId;
	}

	public void setFromActorId(String fromActorId) {
		this.fromActorId = fromActorId;
	}
}
