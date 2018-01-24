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
import com.glaf.isdp.util.FlowActivDJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FLOW_ACTIV_D")
public class FlowActivD implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "PROCESS_ID", length = 50)
	protected String processId;

	@Column(name = "TYPEOFACT", length = 20)
	protected String typeOfAct;

	@Column(name = "NAME", length = 100)
	protected String name;

	@Column(name = "CONTENT", length = 100)
	protected String content;

	@Column(name = "STRFUNTION", length = 1000)
	protected String strFuntion;

	@Column(name = "NETROLEID", length = 50)
	protected String netRoleId;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "TIMELIMIT")
	protected Double timeLimit;

	@Column(name = "ISSAVE")
	protected Integer isSave;

	@Column(name = "ISDEL")
	protected Integer isDel;

	@Column(name = "INTSELECTUSER")
	protected Integer intSelectUser;

	@Column(name = "INTUSEDOMAIN")
	protected Integer intUseDomain;
	
	@javax.persistence.Transient
	protected String activDPre;

	public FlowActivD() {

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

	public String getTypeOfAct() {
		return this.typeOfAct;
	}

	public String getName() {
		return this.name;
	}

	public String getContent() {
		return this.content;
	}

	public String getStrFuntion() {
		return this.strFuntion;
	}

	public String getNetRoleId() {
		return this.netRoleId;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Double getTimeLimit() {
		return this.timeLimit;
	}

	public Integer getIsSave() {
		return this.isSave;
	}

	public Integer getIsDel() {
		return this.isDel;
	}

	public Integer getIntSelectUser() {
		return this.intSelectUser;
	}

	public Integer getIntUseDomain() {
		return this.intUseDomain;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setTypeOfAct(String typeOfAct) {
		this.typeOfAct = typeOfAct;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setStrFuntion(String strFuntion) {
		this.strFuntion = strFuntion;
	}

	public void setNetRoleId(String netRoleId) {
		this.netRoleId = netRoleId;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setTimeLimit(Double timeLimit) {
		this.timeLimit = timeLimit;
	}

	public void setIsSave(Integer isSave) {
		this.isSave = isSave;
	}

	public void setIsDel(Integer isDel) {
		this.isDel = isDel;
	}

	public void setIntSelectUser(Integer intSelectUser) {
		this.intSelectUser = intSelectUser;
	}

	public void setIntUseDomain(Integer intUseDomain) {
		this.intUseDomain = intUseDomain;
	}

	public String getActivDPre() {
		return activDPre;
	}

	public void setActivDPre(String activDPre) {
		this.activDPre = activDPre;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowActivD other = (FlowActivD) obj;
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

	public FlowActivD jsonToObject(JSONObject jsonObject) {
		return FlowActivDJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FlowActivDJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FlowActivDJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
