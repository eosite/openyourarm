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
import com.glaf.isdp.util.FlowForwardDJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FLOW_FORWARD_D")
public class FlowForwardD implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "PROCESS_ID", length = 50)
	protected String processId;

	@Column(name = "ACTIV_ID", length = 50)
	protected String activId;

	@Column(name = "ACTIV_PRE", length = 50)
	protected String activPre;

	@Column(name = "ACTIV_NEXT", length = 50)
	protected String activNext;

	@Column(name = "ISSAVE")
	protected Integer isSave;

	@Column(name = "ISDEL")
	protected Integer isDel;

	@Column(name = "LISTNO")
	protected Integer listNo;

	public FlowForwardD() {

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

	public String getActivId() {
		return this.activId;
	}

	public String getActivPre() {
		return this.activPre;
	}

	public String getActivNext() {
		return this.activNext;
	}

	public Integer getIsSave() {
		return this.isSave;
	}

	public Integer getIsDel() {
		return this.isDel;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setActivId(String activId) {
		this.activId = activId;
	}

	public void setActivPre(String activPre) {
		this.activPre = activPre;
	}

	public void setActivNext(String activNext) {
		this.activNext = activNext;
	}

	public void setIsSave(Integer isSave) {
		this.isSave = isSave;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowForwardD other = (FlowForwardD) obj;
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

	public FlowForwardD jsonToObject(JSONObject jsonObject) {
		return FlowForwardDJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FlowForwardDJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FlowForwardDJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
