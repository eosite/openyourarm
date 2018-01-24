package com.glaf.isdp.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.isdp.util.FlowForwardJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FLOW_FORWARD")
public class FlowForward implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "PROCESS_ID", length = 50)
	protected String processId;

	@Column(name = "ACTIV_D_ID", length = 50)
	protected String activDId;

	@Column(name = "ACTIV_D_NEXT", length = 50)
	protected String activDNext;

	@Column(name = "SENDTYPE")
	protected Integer sendType;

	@Column(name = "SENDTIMES")
	protected Integer sendTimes;

	public FlowForward() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getProcessId() {
		return this.processId;
	}

	public String getActivDId() {
		return this.activDId;
	}

	public String getActivDNext() {
		return this.activDNext;
	}

	public Integer getSendType() {
		return this.sendType;
	}

	public Integer getSendTimes() {
		return this.sendTimes;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setActivDId(String activDId) {
		this.activDId = activDId;
	}

	public void setActivDNext(String activDNext) {
		this.activDNext = activDNext;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowForward other = (FlowForward) obj;
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

	public FlowForward jsonToObject(JSONObject jsonObject) {
		return FlowForwardJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FlowForwardJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FlowForwardJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
