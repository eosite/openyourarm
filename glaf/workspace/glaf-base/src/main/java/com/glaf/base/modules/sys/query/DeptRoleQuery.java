package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DeptRoleQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long deptId;
	protected List<Long> deptIds;
	protected Integer deptFlag;
	protected Integer menuFlag;
	protected Long roleId;
	protected List<Long> roleIds;
	protected String isPropagationAllowed;

	public DeptRoleQuery() {

	}

	public DeptRoleQuery deptFlag(Integer deptFlag) {
		if (deptFlag == null) {
			throw new RuntimeException("deptFlag is null");
		}
		this.deptFlag = deptFlag;
		return this;
	}

	public DeptRoleQuery deptId(Long deptId) {
		if (deptId == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptId = deptId;
		return this;
	}

	public DeptRoleQuery deptIds(List<Long> deptIds) {
		if (deptIds == null) {
			throw new RuntimeException("deptIds is empty ");
		}
		this.deptIds = deptIds;
		return this;
	}

	public Integer getDeptFlag() {
		return deptFlag;
	}

	public Long getDeptId() {
		return deptId;
	}

	public List<Long> getDeptIds() {
		return deptIds;
	}

	public String getIsPropagationAllowed() {
		return isPropagationAllowed;
	}

	public Integer getMenuFlag() {
		return menuFlag;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("deptId".equals(sortColumn)) {
				orderBy = "E.DEPTID" + a_x;
			}

			if ("deptFlag".equals(sortColumn)) {
				orderBy = "E.DEPTFLAG" + a_x;
			}

			if ("roleId".equals(sortColumn)) {
				orderBy = "E.ROLEID" + a_x;
			}

			if ("isPropagationAllowed".equals(sortColumn)) {
				orderBy = "E.ISPROPAGATIONALLOWED_" + a_x;
			}

		}
		return orderBy;
	}

	public Long getRoleId() {
		return roleId;
	}

	public List<Long> getRoleIds() {
		return roleIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("deptId", "DEPTID");
		addColumn("deptFlag", "DEPTFLAG");
		addColumn("roleId", "ROLEID");
		addColumn("isPropagationAllowed", "ISPROPAGATIONALLOWED_");
	}

	public DeptRoleQuery isPropagationAllowed(String isPropagationAllowed) {
		if (isPropagationAllowed == null) {
			throw new RuntimeException("isPropagationAllowed is null");
		}
		this.isPropagationAllowed = isPropagationAllowed;
		return this;
	}

	public DeptRoleQuery menuFlag(Integer menuFlag) {
		if (menuFlag == null) {
			throw new RuntimeException("menuFlag is null");
		}
		this.menuFlag = menuFlag;
		return this;
	}

	public DeptRoleQuery roleId(Long roleId) {
		if (roleId == null) {
			throw new RuntimeException("roleId is null");
		}
		this.roleId = roleId;
		return this;
	}

	public DeptRoleQuery roleIds(List<Long> roleIds) {
		if (roleIds == null) {
			throw new RuntimeException("roleIds is empty ");
		}
		this.roleIds = roleIds;
		return this;
	}

	public void setDeptFlag(Integer deptFlag) {
		this.deptFlag = deptFlag;
	}

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public void setIsPropagationAllowed(String isPropagationAllowed) {
		this.isPropagationAllowed = isPropagationAllowed;
	}

	public void setMenuFlag(Integer menuFlag) {
		this.menuFlag = menuFlag;
	}

	public void setRoleId(Long roleId) {
		this.roleId = roleId;
	}

	public void setRoleIds(List<Long> roleIds) {
		this.roleIds = roleIds;
	}

}