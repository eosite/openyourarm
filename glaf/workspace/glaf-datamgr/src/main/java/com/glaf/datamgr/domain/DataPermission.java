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
@Table(name = "SYS_DATA_PERMISSION")
public class DataPermission implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	/**
	 * 数据库编号
	 */
	@Column(name = "DATABASEID_")
	protected Long databaseId;

	/**
	 * 业务编号
	 */
	@Column(name = "BUSINESSKEY_", length = 500)
	protected String businessKey;

	/**
	 * 业务类型
	 */
	@Column(name = "BUSINESSTYPE_", length = 50)
	protected String businessType;

	/**
	 * 访问类型
	 */
	@Column(name = "ACCESSTYPE_", length = 50)
	protected String accessType;

	/**
	 * 用户编号
	 */
	@Column(name = "USERID_", length = 50)
	protected String userId;

	/**
	 * 角色编号
	 */
	@Column(name = "ROLEID_", length = 50)
	protected String roleId;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	public DataPermission() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataPermission other = (DataPermission) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getAccessType() {
		return this.accessType;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public String getBusinessType() {
		return this.businessType;
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

	public Long getDatabaseId() {
		return this.databaseId;
	}

	public long getId() {
		return this.id;
	}

	public String getRoleId() {
		return this.roleId;
	}

	public String getUserId() {
		return this.userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public DataPermission jsonToObject(JSONObject jsonObject) {
		return DataPermissionJsonFactory.jsonToObject(jsonObject);
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public JSONObject toJsonObject() {
		return DataPermissionJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataPermissionJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
