package com.glaf.model.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.model.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SUB_SYSTEM_DEF_")
public class SubSystemDef implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "SUB_SYS_ID_", length = 50, nullable = false)
	protected String subSysId;
	/**
	 * oId
	 */
	@Column(name = "OID_")
	protected Long oId;
	/**
	 * sysId
	 */
	@Column(name = "SYS_ID_", length = 50)
	protected String sysId;

	/**
	 * level
	 */
	@Column(name = "LEVEL_")
	protected Integer level;

	/**
	 * code
	 */
	@Column(name = "CODE_", length = 50)
	protected String code;

	/**
	 * name
	 */
	@Column(name = "NAME_", length = 60)
	protected String name;

	/**
	 * desc
	 */
	@Column(name = "DESC_", length = 500)
	protected String desc;

	/**
	 * parentId
	 */
	@Column(name = "PARENT_ID_", length = 50)
	protected String parentId_;

	/**
	 * treeId
	 */
	@Column(name = "TREE_ID_", length = 100)
	protected String treeId;

	/**
	 * createBy
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * createTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * updateBy
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * updateTime
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	/**
	 * deleteFlag
	 */
	@Column(name = "DELETE_FLAG_")
	protected Integer deleteFlag;
    @javax.persistence.Transient
	protected String procModelId;
    @javax.persistence.Transient
	protected String procDefId;
    @javax.persistence.Transient
	protected String procDeployId;
    @javax.persistence.Transient
	protected String currProcModelId;
    @javax.persistence.Transient
	protected String currProcDefId;
    @javax.persistence.Transient
	protected String currProcDeployId;
    @javax.persistence.Transient
	protected String eleType;
    @javax.persistence.Transient
	protected String systemProcDefId;
    @javax.persistence.Transient
	protected Integer dataObjType;

	public SubSystemDef() {

	}

	public String getSubSysId() {
		return this.subSysId;
	}

	public Long getOId() {
		return this.oId;
	}

	public void setSubSysId(String subSysId) {
		this.subSysId = subSysId;
	}

	public String getSysId() {
		return this.sysId;
	}

	public Integer getLevel() {
		return this.level;
	}

	public String getCode() {
		return this.code;
	}

	public String getName() {
		return this.name;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getParentId_() {
		return this.parentId_;
	}

	public String getTreeId() {
		return this.treeId;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateTimeString() {
		if (this.updateTime != null) {
			return DateUtils.getDateTime(this.updateTime);
		}
		return "";
	}

	public Integer getDeleteFlag() {
		return this.deleteFlag;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setOId(Long oId) {
		this.oId = oId;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setParentId_(String parentId_) {
		this.parentId_ = parentId_;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public String getSystemProcDefId() {
		return systemProcDefId;
	}

	public void setSystemProcDefId(String systemProcDefId) {
		this.systemProcDefId = systemProcDefId;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public String getProcModelId() {
		return procModelId;
	}

	public void setProcModelId(String procModelId) {
		this.procModelId = procModelId;
	}

	public String getProcDefId() {
		return procDefId;
	}

	public void setProcDefId(String procDefId) {
		this.procDefId = procDefId;
	}

	public String getProcDeployId() {
		return procDeployId;
	}

	public void setProcDeployId(String procDeployId) {
		this.procDeployId = procDeployId;
	}

	public String getCurrProcModelId() {
		return currProcModelId;
	}

	public void setCurrProcModelId(String currProcModelId) {
		this.currProcModelId = currProcModelId;
	}

	public String getCurrProcDefId() {
		return currProcDefId;
	}

	public void setCurrProcDefId(String currProcDefId) {
		this.currProcDefId = currProcDefId;
	}

	public String getCurrProcDeployId() {
		return currProcDeployId;
	}

	public void setCurrProcDeployId(String currProcDeployId) {
		this.currProcDeployId = currProcDeployId;
	}

	public String getEleType() {
		return eleType;
	}

	public void setEleType(String eleType) {
		this.eleType = eleType;
	}

	public Integer getDataObjType() {
		return dataObjType;
	}

	public void setDataObjType(Integer dataObjType) {
		this.dataObjType = dataObjType;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SubSystemDef other = (SubSystemDef) obj;
		if (subSysId == null) {
			if (other.subSysId != null)
				return false;
		} else if (!subSysId.equals(other.subSysId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((subSysId == null) ? 0 : subSysId.hashCode());
		return result;
	}

	public SubSystemDef jsonToObject(JSONObject jsonObject) {
		return SubSystemDefJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SubSystemDefJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SubSystemDefJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
