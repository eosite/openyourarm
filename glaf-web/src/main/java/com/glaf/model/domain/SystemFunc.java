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
@Table(name = "SYSTEM_FUNC_")
public class SystemFunc implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FUNC_ID_", length = 50,nullable = false)
	protected String  funcId;

	/**
	 * sysId
	 */
	@Column(name = "SYS_ID_",length = 50)
	protected String sysId;

	/**
	 * funcCode
	 */
	@Column(name = "FUNC_CODE_", length = 50)
	protected String funcCode;

	/**
	 * funcName
	 */
	@Column(name = "FUNC_NAME_", length = 150)
	protected String funcName;

	/**
	 * funcType
	 */
	@Column(name = "FUNC_TYPE_", length = 20)
	protected String funcType;

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


	public SystemFunc() {

	}

	public String getFuncId() {
		return this.funcId;
	}

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public String getSysId() {
		return this.sysId;
	}

	public String getFuncCode() {
		return this.funcCode;
	}

	public String getFuncName() {
		return this.funcName;
	}

	public String getFuncType() {
		return this.funcType;
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

	public void setFuncCode(String funcCode) {
		this.funcCode = funcCode;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public void setFuncType(String funcType) {
		this.funcType = funcType;
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

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SystemFunc other = (SystemFunc) obj;
		if (funcId == null) {
			if (other.funcId != null)
				return false;
		} else if (!funcId.equals(other.funcId))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((funcId == null) ? 0 : funcId.hashCode());
		return result;
	}

	public SystemFunc jsonToObject(JSONObject jsonObject) {
		return SystemFuncJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SystemFuncJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SystemFuncJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
