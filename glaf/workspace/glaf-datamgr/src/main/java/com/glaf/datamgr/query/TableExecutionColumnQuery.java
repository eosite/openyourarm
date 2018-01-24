package com.glaf.datamgr.query;

import com.glaf.core.query.DataQuery;

public class TableExecutionColumnQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String executionId;
	protected String titleLike;
	protected String columnName;
	protected String columnNameLike;

	public TableExecutionColumnQuery() {

	}

	public TableExecutionColumnQuery columnName(String columnName) {
		if (columnName == null) {
			throw new RuntimeException("columnName is null");
		}
		this.columnName = columnName;
		return this;
	}

	public TableExecutionColumnQuery columnNameLike(String columnNameLike) {
		if (columnNameLike == null) {
			throw new RuntimeException("columnName is null");
		}
		this.columnNameLike = columnNameLike;
		return this;
	}

	public TableExecutionColumnQuery executionId(String executionId) {
		if (executionId == null) {
			throw new RuntimeException("executionId is null");
		}
		this.executionId = executionId;
		return this;
	}

	public String getColumnName() {
		return columnName;
	}

	public String getColumnNameLike() {
		if (columnNameLike != null && columnNameLike.trim().length() > 0) {
			if (!columnNameLike.startsWith("%")) {
				columnNameLike = "%" + columnNameLike;
			}
			if (!columnNameLike.endsWith("%")) {
				columnNameLike = columnNameLike + "%";
			}
		}
		return columnNameLike;
	}

	public String getExecutionId() {
		return executionId;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("executionId".equals(sortColumn)) {
				orderBy = "E.EXECUTIONID_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("columnName".equals(sortColumn)) {
				orderBy = "E.COLUMNNAME_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("valueExpression".equals(sortColumn)) {
				orderBy = "E.VALUEEXPRESSION_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("executionId", "EXECUTIONID_");
		addColumn("title", "TITLE_");
		addColumn("columnName", "COLUMNNAME_");
		addColumn("type", "TYPE_");
		addColumn("valueExpression", "VALUEEXPRESSION_");
		addColumn("locked", "LOCKED_");
	}

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnNameLike(String columnNameLike) {
		this.columnNameLike = columnNameLike;
	}

	public void setExecutionId(String executionId) {
		this.executionId = executionId;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public TableExecutionColumnQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}