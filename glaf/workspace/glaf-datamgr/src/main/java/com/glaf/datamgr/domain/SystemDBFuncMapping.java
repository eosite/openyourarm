package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYSTEM_DB_FUNC_MAPPING_")
public class SystemDBFuncMapping implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * 函数ID
	 */
	@Column(name = "FUNC_ID_", length = 50)
	protected String funcId;

	/**
	 * 数据库类型
	 */
	@Column(name = "DTYPE_", length = 20)
	protected String dtype;

	/**
	 * 函数名
	 */
	@Column(name = "FUNCNAME_", length = 30)
	protected String funcName;

	/**
	 * 参数说明
	 */
	@Lob
	@Column(name = "PARAMS_")
	protected String params;

	/**
	 * CREATEBY_
	 */
	@Column(name = "CREATEBY_", length = 30)
	protected String createBy;

	/**
	 * CREATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * UPDATEBY_
	 */
	@Column(name = "UPDATEBY_", length = 30)
	protected String updateBy;

	/**
	 * UPDATETIME_
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	/**
	 * DELETE_FLAG_
	 */
	@Column(name = "DELETE_FLAG_")
	protected Integer deleteFlag;

	public SystemDBFuncMapping() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFuncId() {
		return this.funcId;
	}

	public String getDtype() {
		return this.dtype;
	}

	public String getFuncName() {
		return this.funcName;
	}

	public String getParams() {
		return this.params;
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

	public void setFuncId(String funcId) {
		this.funcId = funcId;
	}

	public void setDtype(String dtype) {
		this.dtype = dtype;
	}

	public void setFuncName(String funcName) {
		this.funcName = funcName;
	}

	public void setParams(String params) {
		this.params = params;
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
		SystemDBFuncMapping other = (SystemDBFuncMapping) obj;
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

	public SystemDBFuncMapping jsonToObject(JSONObject jsonObject) {
		return SystemDBFuncMappingJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return SystemDBFuncMappingJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SystemDBFuncMappingJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
