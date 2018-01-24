package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class OperationQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String name;
	protected String nameLike;
	protected String code;
	protected String codeLike;
	protected String descriptionLike;
	protected String method;
	protected String tablename;
	protected String tablenameLike;
	protected Long sqlDefId;
	protected Integer sortGreaterThanOrEqual;
	protected Integer sortLessThanOrEqual;

	public OperationQuery() {

	}

	public OperationQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public OperationQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public OperationQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
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

	public String getDescriptionLike() {
		if (descriptionLike != null && descriptionLike.trim().length() > 0) {
			if (!descriptionLike.startsWith("%")) {
				descriptionLike = "%" + descriptionLike;
			}
			if (!descriptionLike.endsWith("%")) {
				descriptionLike = descriptionLike + "%";
			}
		}
		return descriptionLike;
	}

	public String getMethod() {
		return method;
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

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE_" + a_x;
			}

			if ("description".equals(sortColumn)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("method".equals(sortColumn)) {
				orderBy = "E.METHOD_" + a_x;
			}

			if ("tablename".equals(sortColumn)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("idField".equals(sortColumn)) {
				orderBy = "E.IDFIELD_" + a_x;
			}

			if ("idColumn".equals(sortColumn)) {
				orderBy = "E.IDCOLUMN_" + a_x;
			}

			if ("idJavaType".equals(sortColumn)) {
				orderBy = "E.IDJAVATYPE_" + a_x;
			}

			if ("sqlDefId".equals(sortColumn)) {
				orderBy = "E.SQLDEFID_" + a_x;
			}

			if ("sort".equals(sortColumn)) {
				orderBy = "E.SORT_" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getSortGreaterThanOrEqual() {
		return sortGreaterThanOrEqual;
	}

	public Integer getSortLessThanOrEqual() {
		return sortLessThanOrEqual;
	}

	public Long getSqlDefId() {
		return sqlDefId;
	}

	public String getTablename() {
		return tablename;
	}

	public String getTablenameLike() {
		if (tablenameLike != null && tablenameLike.trim().length() > 0) {
			if (!tablenameLike.startsWith("%")) {
				tablenameLike = "%" + tablenameLike;
			}
			if (!tablenameLike.endsWith("%")) {
				tablenameLike = tablenameLike + "%";
			}
		}
		return tablenameLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("code", "CODE_");
		addColumn("description", "DESCRIPTION_");
		addColumn("method", "METHOD_");
		addColumn("tablename", "TABLENAME_");
		addColumn("idField", "IDFIELD_");
		addColumn("idColumn", "IDCOLUMN_");
		addColumn("idJavaType", "IDJAVATYPE_");
		addColumn("sqlDefId", "SQLDEFID_");
		addColumn("sort", "SORT_");
	}

	public OperationQuery method(String method) {
		if (method == null) {
			throw new RuntimeException("method is null");
		}
		this.method = method;
		return this;
	}

	public OperationQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public OperationQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setSortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
	}

	public void setSortLessThanOrEqual(Integer sortLessThanOrEqual) {
		this.sortLessThanOrEqual = sortLessThanOrEqual;
	}

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setTablename(String tablename) {
		this.tablename = tablename;
	}

	public void setTablenameLike(String tablenameLike) {
		this.tablenameLike = tablenameLike;
	}

	public OperationQuery sortGreaterThanOrEqual(Integer sortGreaterThanOrEqual) {
		if (sortGreaterThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortGreaterThanOrEqual = sortGreaterThanOrEqual;
		return this;
	}

	public OperationQuery sortLessThanOrEqual(Integer sortLessThanOrEqual) {
		if (sortLessThanOrEqual == null) {
			throw new RuntimeException("sort is null");
		}
		this.sortLessThanOrEqual = sortLessThanOrEqual;
		return this;
	}

	public OperationQuery sqlDefId(Long sqlDefId) {
		if (sqlDefId == null) {
			throw new RuntimeException("sqlDefId is null");
		}
		this.sqlDefId = sqlDefId;
		return this;
	}

	public OperationQuery tablename(String tablename) {
		if (tablename == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablename = tablename;
		return this;
	}

	public OperationQuery tablenameLike(String tablenameLike) {
		if (tablenameLike == null) {
			throw new RuntimeException("tablename is null");
		}
		this.tablenameLike = tablenameLike;
		return this;
	}

}