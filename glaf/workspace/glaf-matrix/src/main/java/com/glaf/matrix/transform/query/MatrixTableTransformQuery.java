package com.glaf.matrix.transform.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class MatrixTableTransformQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> tableNames;
	protected String titleLike;
	protected Integer transformStatus;
	protected Date transformTimeGreaterThanOrEqual;
	protected Date transformTimeLessThanOrEqual;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public MatrixTableTransformQuery() {

	}

	public MatrixTableTransformQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public MatrixTableTransformQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("databaseIds".equals(sortColumn)) {
				orderBy = "E.DATABASEIDS_" + a_x;
			}

			if ("primaryKey".equals(sortColumn)) {
				orderBy = "E.PRIMARYKEY_" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT_" + a_x;
			}

			if ("transformStatus".equals(sortColumn)) {
				orderBy = "E.TRANSFORMSTATUS_" + a_x;
			}

			if ("transformTime".equals(sortColumn)) {
				orderBy = "E.TRANSFORMTIME_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
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

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public Integer getTransformStatus() {
		return transformStatus;
	}

	public Date getTransformTimeGreaterThanOrEqual() {
		return transformTimeGreaterThanOrEqual;
	}

	public Date getTransformTimeLessThanOrEqual() {
		return transformTimeLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("tableName", "TABLENAME_");
		addColumn("title", "TITLE_");
		addColumn("databaseIds", "DATABASEIDS_");
		addColumn("primaryKey", "PRIMARYKEY_");
		addColumn("sort", "SORT_");
		addColumn("transformStatus", "TRANSFORMSTATUS_");
		addColumn("transformTime", "TRANSFORMTIME_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setTransformStatus(Integer transformStatus) {
		this.transformStatus = transformStatus;
	}

	public void setTransformTimeGreaterThanOrEqual(Date transformTimeGreaterThanOrEqual) {
		this.transformTimeGreaterThanOrEqual = transformTimeGreaterThanOrEqual;
	}

	public void setTransformTimeLessThanOrEqual(Date transformTimeLessThanOrEqual) {
		this.transformTimeLessThanOrEqual = transformTimeLessThanOrEqual;
	}

	public MatrixTableTransformQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public MatrixTableTransformQuery transformStatus(Integer transformStatus) {
		if (transformStatus == null) {
			throw new RuntimeException("transformStatus is null");
		}
		this.transformStatus = transformStatus;
		return this;
	}

	public MatrixTableTransformQuery transformTimeGreaterThanOrEqual(Date transformTimeGreaterThanOrEqual) {
		if (transformTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("transformTime is null");
		}
		this.transformTimeGreaterThanOrEqual = transformTimeGreaterThanOrEqual;
		return this;
	}

	public MatrixTableTransformQuery transformTimeLessThanOrEqual(Date transformTimeLessThanOrEqual) {
		if (transformTimeLessThanOrEqual == null) {
			throw new RuntimeException("transformTime is null");
		}
		this.transformTimeLessThanOrEqual = transformTimeLessThanOrEqual;
		return this;
	}

}