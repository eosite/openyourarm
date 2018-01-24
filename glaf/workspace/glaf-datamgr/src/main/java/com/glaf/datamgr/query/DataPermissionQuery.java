package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DataPermissionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long databaseId;
	protected List<Long> databaseIds;
	protected String businessType;
	protected String accessType;
	protected String userId;
	protected List<String> userIds;
	protected String roleId;
	protected List<String> roleIds;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public DataPermissionQuery() {

	}

	public DataPermissionQuery accessType(String accessType) {
		if (accessType == null) {
			throw new RuntimeException("accessType is null");
		}
		this.accessType = accessType;
		return this;
	}

	public DataPermissionQuery businessType(String businessType) {
		if (businessType == null) {
			throw new RuntimeException("businessType is null");
		}
		this.businessType = businessType;
		return this;
	}

	public DataPermissionQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public DataPermissionQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public DataPermissionQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public DataPermissionQuery databaseId(Long databaseId) {
		if (databaseId == null) {
			throw new RuntimeException("databaseId is null");
		}
		this.databaseId = databaseId;
		return this;
	}

	public DataPermissionQuery databaseIds(List<Long> databaseIds) {
		if (databaseIds == null) {
			throw new RuntimeException("databaseIds is empty ");
		}
		this.databaseIds = databaseIds;
		return this;
	}

	public String getAccessType() {
		return accessType;
	}

	public String getBusinessType() {
		return businessType;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public List<Long> getDatabaseIds() {
		return databaseIds;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("databaseId".equals(sortColumn)) {
				orderBy = "E.DATABASEID_" + a_x;
			}

			if ("businessKey".equals(sortColumn)) {
				orderBy = "E.BUSINESSKEY_" + a_x;
			}

			if ("businessType".equals(sortColumn)) {
				orderBy = "E.BUSINESSTYPE_" + a_x;
			}

			if ("accessType".equals(sortColumn)) {
				orderBy = "E.ACCESSTYPE_" + a_x;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID_" + a_x;
			}

			if ("roleId".equals(sortColumn)) {
				orderBy = "E.ROLEID_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	public String getRoleId() {
		return roleId;
	}

	public List<String> getRoleIds() {
		return roleIds;
	}

	public String getUserId() {
		return userId;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("databaseId", "DATABASEID_");
		addColumn("businessKey", "BUSINESSKEY_");
		addColumn("businessType", "BUSINESSTYPE_");
		addColumn("accessType", "ACCESSTYPE_");
		addColumn("userId", "USERID_");
		addColumn("roleId", "ROLEID_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
	}

	public DataPermissionQuery roleId(String roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public DataPermissionQuery roleIds(List<String> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDatabaseIds(List<Long> databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<String> roleIds) {
		this.roleIds = roleIds;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public DataPermissionQuery userId(String userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public DataPermissionQuery userIds(List<String> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

}