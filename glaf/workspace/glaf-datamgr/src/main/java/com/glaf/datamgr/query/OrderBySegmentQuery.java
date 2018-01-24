package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class OrderBySegmentQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String datasetId;
	protected List<String> datasetIds;
	protected String tableName;
	protected String tableNameLike;
	protected List<String> tableNames;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected String updateBy;
	protected List<String> updateBys;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;

	public OrderBySegmentQuery() {

	}

	public OrderBySegmentQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public OrderBySegmentQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public OrderBySegmentQuery createTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public OrderBySegmentQuery createTimeLessThanOrEqual(
			Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public OrderBySegmentQuery datasetId(String datasetId) {
		if (datasetId == null) {
			throw new RuntimeException("datasetId is null");
		}
		this.datasetId = datasetId;
		return this;
	}

	public OrderBySegmentQuery datasetIds(List<String> datasetIds) {
		if (datasetIds == null) {
			throw new RuntimeException("datasetIds is empty ");
		}
		this.datasetIds = datasetIds;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public String getCreateBy() {
		return createBy;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public List<String> getDatasetIds() {
		return datasetIds;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("datasetId".equals(sortColumn)) {
				orderBy = "E.DATASETID_" + a_x;
			}

			if ("tableName".equals(sortColumn)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("columnName".equals(sortColumn)) {
				orderBy = "E.COLUMNNAME_" + a_x;
			}

			if ("sortOrder".equals(sortColumn)) {
				orderBy = "E.SORTORDER_" + a_x;
			}

			if ("ordinal".equals(sortColumn)) {
				orderBy = "E.ORDINAL_" + a_x;
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

	public String getTableName() {
		return tableName;
	}

	public String getTableNameLike() {
		if (tableNameLike != null && tableNameLike.trim().length() > 0) {
			if (!tableNameLike.startsWith("%")) {
				tableNameLike = "%" + tableNameLike;
			}
			if (!tableNameLike.endsWith("%")) {
				tableNameLike = tableNameLike + "%";
			}
		}
		return tableNameLike;
	}

	public List<String> getTableNames() {
		return tableNames;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public List<String> getUpdateBys() {
		return updateBys;
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
		addColumn("datasetId", "DATASETID_");
		addColumn("tableName", "TABLENAME_");
		addColumn("columnName", "COLUMNNAME_");
		addColumn("sortOrder", "SORTORDER_");
		addColumn("ordinal", "ORDINAL_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateTimeGreaterThanOrEqual(
			Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public void setDatasetIds(List<String> datasetIds) {
		this.datasetIds = datasetIds;
	}

	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTableNameLike(String tableNameLike) {
		this.tableNameLike = tableNameLike;
	}

	public void setTableNames(List<String> tableNames) {
		this.tableNames = tableNames;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setUpdateTimeGreaterThanOrEqual(
			Date updateTimeGreaterThanOrEqual) {
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
	}

	public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
	}

	public OrderBySegmentQuery tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public OrderBySegmentQuery tableNameLike(String tableNameLike) {
		if (tableNameLike == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableNameLike = tableNameLike;
		return this;
	}

	public OrderBySegmentQuery tableNames(List<String> tableNames) {
		if (tableNames == null) {
			throw new RuntimeException("tableNames is empty ");
		}
		this.tableNames = tableNames;
		return this;
	}

	public OrderBySegmentQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public OrderBySegmentQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public OrderBySegmentQuery updateTimeGreaterThanOrEqual(
			Date updateTimeGreaterThanOrEqual) {
		if (updateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
		return this;
	}

	public OrderBySegmentQuery updateTimeLessThanOrEqual(
			Date updateTimeLessThanOrEqual) {
		if (updateTimeLessThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
		return this;
	}

}