package com.glaf.isdp.domain;

import java.io.Serializable;
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
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.FlowActivJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FLOW_ACTIV")
public class FlowActiv implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Column(name = "ACTIV_D_ID", length = 50)
	protected String activDId;

	@Column(name = "PROCESS_ID", length = 50)
	protected String processId;

	@Column(name = "TYPEOFACT", length = 20)
	protected String typeOfAct;

	@Column(name = "NAME", length = 255)
	protected String name;

	@Column(name = "CONTENT", length = 100)
	protected String content;

	@Column(name = "STRFUNTION", length = 200)
	protected String strFuntion;

	@Column(name = "NETROLEID", length = 50)
	protected String netRoleId;

	@Column(name = "USERID", length = 50)
	protected String userId;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "TIMELIMIT")
	protected Double timeLimit;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME_START")
	protected Date ctimeStart;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME_END")
	protected Date ctimeEnd;

	@Column(name = "STATE")
	protected Integer state;

	@Column(name = "INTBACK")
	protected Integer intBack;

	@Column(name = "SYS_ID", length = 50)
	protected String sysId;

	public FlowActiv() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getActivDId() {
		return this.activDId;
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

	public String getUserId() {
		return this.userId;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Double getTimeLimit() {
		return this.timeLimit;
	}

	public Date getCtimeStart() {
		return this.ctimeStart;
	}

	public String getCtimeStartString() {
		if (this.ctimeStart != null) {
			return DateUtils.getDateTime(this.ctimeStart);
		}
		return "";
	}

	public Date getCtimeEnd() {
		return this.ctimeEnd;
	}

	public String getCtimeEndString() {
		if (this.ctimeEnd != null) {
			return DateUtils.getDateTime(this.ctimeEnd);
		}
		return "";
	}

	public Integer getState() {
		return this.state;
	}

	public Integer getIntBack() {
		return this.intBack;
	}

	public String getSysId() {
		return this.sysId;
	}

	public void setActivDId(String activDId) {
		this.activDId = activDId;
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

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setTimeLimit(Double timeLimit) {
		this.timeLimit = timeLimit;
	}

	public void setCtimeStart(Date ctimeStart) {
		this.ctimeStart = ctimeStart;
	}

	public void setCtimeEnd(Date ctimeEnd) {
		this.ctimeEnd = ctimeEnd;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setIntBack(Integer intBack) {
		this.intBack = intBack;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FlowActiv other = (FlowActiv) obj;
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

	public FlowActiv jsonToObject(JSONObject jsonObject) {
		return FlowActivJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FlowActivJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FlowActivJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
