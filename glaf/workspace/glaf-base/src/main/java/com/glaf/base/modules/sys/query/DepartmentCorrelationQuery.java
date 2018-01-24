package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepartmentCorrelationQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long deptId;
	protected List<Long> deptIds;
	protected String name;
	protected String nameLike;
	protected String code;
	protected String codeLike;
	protected String sysId;
	protected List<String> sysIds;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;

	public DepartmentCorrelationQuery() {

	}

	public DepartmentCorrelationQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public DepartmentCorrelationQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public DepartmentCorrelationQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public DepartmentCorrelationQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public DepartmentCorrelationQuery deptId(Long deptId) {
		if (deptId == null) {
			throw new RuntimeException("deptId is null");
		}
		this.deptId = deptId;
		return this;
	}

	public DepartmentCorrelationQuery deptIds(List<Long> deptIds) {
		if (deptIds == null) {
			throw new RuntimeException("deptIds is empty ");
		}
		this.deptIds = deptIds;
		return this;
	}

	public String getCode() {
		return code;
	}

	public String getCodeLike() {
		if (codeLike != null && codeLike.trim().length() > 0) {
			if (!codeLike.startsWith("%")) {
				codeLike = "%" + codeLike;
			}
			if (!codeLike.endsWith("%")) {
				codeLike = codeLike + "%";
			}
		}
		return codeLike;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Long getDeptId() {
		return deptId;
	}

	public List<Long> getDeptIds() {
		return deptIds;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("deptId".equals(sortColumn)) {
				orderBy = "E.DEPTID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE_" + a_x;
			}

			if ("sysId".equals(sortColumn)) {
				orderBy = "E.SYSID_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("updateTime".equals(sortColumn)) {
				orderBy = "E.UPDATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	public String getSysId() {
		return sysId;
	}

	public List<String> getSysIds() {
		return sysIds;
	}

	public Date getUpdateTimeGreaterThanOrEqual() {
		return updateTimeGreaterThanOrEqual;
	}

	public Date getUpdateTimeLessThanOrEqual() {
		return updateTimeLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("deptId", "DEPTID_");
		addColumn("name", "NAME_");
		addColumn("code", "CODE_");
		addColumn("sysId", "SYSID_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public DepartmentCorrelationQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DepartmentCorrelationQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
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

	public void setDeptId(Long deptId) {
		this.deptId = deptId;
	}

	public void setDeptIds(List<Long> deptIds) {
		this.deptIds = deptIds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setSysIds(List<String> sysIds) {
		this.sysIds = sysIds;
	}

	public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
	}

	public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
	}

	public DepartmentCorrelationQuery sysId(String sysId) {
		if (sysId == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysId = sysId;
		return this;
	}

	public DepartmentCorrelationQuery sysIds(List<String> sysIds) {
		if (sysIds == null) {
			throw new RuntimeException("sysIds is empty ");
		}
		this.sysIds = sysIds;
		return this;
	}

	public DepartmentCorrelationQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		if (updateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
		return this;
	}

	public DepartmentCorrelationQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		if (updateTimeLessThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
		return this;
	}

}