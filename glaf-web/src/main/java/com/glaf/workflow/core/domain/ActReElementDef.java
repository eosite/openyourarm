package com.glaf.workflow.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.workflow.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "ACT_RE_ELEMENTDEF")
public class ActReElementDef implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long ID;

	/**
	 * ELE_TYPE_
	 */
	@Column(name = "ELE_TYPE_", length = 30)
	protected String eleType;

	/**
	 * ELE_RESOURCE_ID_
	 */
	@Column(name = "ELE_RESOURCE_ID_", length = 50)
	protected String eleResourceId;

	/**
	 * ELE_ID_
	 */
	@Column(name = "ELE_ID_", length = 50)
	protected String eleID;

	/**
	 * ELE_NAME_
	 */
	@Column(name = "ELE_NAME_", length = 100)
	protected String eleName;
	
	/**
	 * SUB_PROCESS_KEY_
	 */
	@Column(name = "SUB_PROCESS_KEY_", length = 50)
	protected String subProcessKey;

	/**
	 * ELE_DESC
	 */
	@Column(name = "ELE_DESC", length = 150)
	protected String eleDesc;

	/**
	 * MODEL_ID_
	 */
	@Column(name = "MODEL_ID_", length = 64)
	protected String modelId;

	/**
	 * PROCEDEF_ID_
	 */
	@Column(name = "PROCEDEF_ID_", length = 64)
	protected String ProceDefId;

	/**
	 * BTYES_
	 */
	@Lob
	@Column(name = "BYTES_"/*, length = 4000*/)
	protected String bytes;

	/**
	 * CREATOR_
	 */
	@Column(name = "CREATOR_", length = 50)
	protected String creator;

	/**
	 * CREATEDATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATETIME_")
	protected Date createDatetime;

	/**
	 * MODIFIER_
	 */
	@Column(name = "MODIFIER_", length = 50)
	protected String modify;

	/**
	 * MODIFYDATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "MODIFYDATETIME_")
	protected Date modifyDatetime;
	
	/**
	 * SUBMITTERTASKFLAG_
	 */
	@Column(name = "SUBMITTERTASKFLAG_")
	protected Integer submitterTaskFlag;

	public ActReElementDef() {

	}

	public Long getID() {
		return this.ID;
	}

	public void setID(Long ID) {
		this.ID = ID;
	}

	public String getEleType() {
		return this.eleType;
	}

	public String getEleResourceId() {
		return this.eleResourceId;
	}

	public String getEleID() {
		return this.eleID;
	}

	public String getEleName() {
		return this.eleName;
	}

	public String getEleDesc() {
		return this.eleDesc;
	}

	public String getModelId() {
		return this.modelId;
	}

	public String getProceDefId() {
		return this.ProceDefId;
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

	public String getModify() {
		return this.modify;
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

	public void setEleType(String eleType) {
		this.eleType = eleType;
	}

	public void setEleResourceId(String eleResourceId) {
		this.eleResourceId = eleResourceId;
	}

	public void setEleID(String eleID) {
		this.eleID = eleID;
	}

	public void setEleName(String eleName) {
		this.eleName = eleName;
	}

	public void setEleDesc(String eleDesc) {
		this.eleDesc = eleDesc;
	}

	public void setModelId(String modelId) {
		this.modelId = modelId;
	}

	public void setProceDefId(String ProceDefId) {
		this.ProceDefId = ProceDefId;
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

	public void setModify(String modify) {
		this.modify = modify;
	}

	public void setModifyDatetime(Date modifyDatetime) {
		this.modifyDatetime = modifyDatetime;
	}

	public Integer getSubmitterTaskFlag() {
		return submitterTaskFlag;
	}

	public void setSubmitterTaskFlag(Integer submitterTaskFlag) {
		this.submitterTaskFlag = submitterTaskFlag;
	}

	public String getSubProcessKey() {
		return subProcessKey;
	}

	public void setSubProcessKey(String subProcessKey) {
		this.subProcessKey = subProcessKey;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ActReElementDef other = (ActReElementDef) obj;
		if (ID == null) {
			if (other.ID != null)
				return false;
		} else if (!ID.equals(other.ID))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((ID == null) ? 0 : ID.hashCode());
		return result;
	}

	@Override
	public ActReElementDef jsonToObject(JSONObject jsonObject) {
		return ActReElementDefJsonFactory.jsonToObject(jsonObject);
	}

	@Override
	public JSONObject toJsonObject() {
		return ActReElementDefJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ActReElementDefJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
