package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FieldSetQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Long datasetId;
	protected List<Long> datasetIds;
	protected String name;
	protected String nameLike;
	protected String code;
	protected String codeLike;
	protected String fieldTable;
	protected String fieldTableLike;
	protected String tableNameCN;
	protected String tableNameCNLike;
	protected String columnName;
	protected String columnNameLike;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected List<String> updateBys;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;

	public FieldSetQuery() {

	}

	public FieldSetQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public FieldSetQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public FieldSetQuery columnName(String columnName) {
		if (columnName == null) {
			throw new RuntimeException("columnName is null");
		}
		this.columnName = columnName;
		return this;
	}

	public FieldSetQuery columnNameLike(String columnNameLike) {
		if (columnNameLike == null) {
			throw new RuntimeException("columnName is null");
		}
		this.columnNameLike = columnNameLike;
		return this;
	}

	public FieldSetQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FieldSetQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public FieldSetQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public FieldSetQuery datasetId(Long datasetId) {
		if (datasetId == null) {
			throw new RuntimeException("datasetId is null");
		}
		this.datasetId = datasetId;
		return this;
	}

	public FieldSetQuery datasetIds(List<Long> datasetIds) {
		if (datasetIds == null) {
			throw new RuntimeException("datasetIds is empty ");
		}
		this.datasetIds = datasetIds;
		return this;
	}

	public FieldSetQuery fieldTable(String fieldTable) {
		if (fieldTable == null) {
			throw new RuntimeException("fieldTable is null");
		}
		this.fieldTable = fieldTable;
		return this;
	}

	public FieldSetQuery fieldTableLike(String fieldTableLike) {
		if (fieldTableLike == null) {
			throw new RuntimeException("fieldTable is null");
		}
		this.fieldTableLike = fieldTableLike;
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

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Long getDatasetId() {
		return datasetId;
	}

	public List<Long> getDatasetIds() {
		return datasetIds;
	}

	public String getFieldTable() {
		return fieldTable;
	}

	public String getFieldTableLike() {
		if (fieldTableLike != null && fieldTableLike.trim().length() > 0) {
			if (!fieldTableLike.startsWith("%")) {
				fieldTableLike = "%" + fieldTableLike;
			}
			if (!fieldTableLike.endsWith("%")) {
				fieldTableLike = fieldTableLike + "%";
			}
		}
		return fieldTableLike;
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

			if ("datasetId".equals(sortColumn)) {
				orderBy = "E.DATASETID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE_" + a_x;
			}

			if ("fieldTable".equals(sortColumn)) {
				orderBy = "E.FIELDTABLE_" + a_x;
			}

			if ("tableNameCN".equals(sortColumn)) {
				orderBy = "E.TABLENAMECN_" + a_x;
			}

			if ("columnName".equals(sortColumn)) {
				orderBy = "E.COLUMNNAME_" + a_x;
			}

			if ("columnWidth".equals(sortColumn)) {
				orderBy = "E.COLUMNWIDTH_" + a_x;
			}

			if ("text".equals(sortColumn)) {
				orderBy = "E.TEXT_" + a_x;
			}

			if ("description".equals(sortColumn)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("fieldId".equals(sortColumn)) {
				orderBy = "E.FIELDID_" + a_x;
			}

			if ("fieldLength".equals(sortColumn)) {
				orderBy = "E.FIELDLENGTH_" + a_x;
			}

			if ("fieldType".equals(sortColumn)) {
				orderBy = "E.FIELDTYPE_" + a_x;
			}

			if ("isShowList".equals(sortColumn)) {
				orderBy = "E.ISSHOWLIST_" + a_x;
			}

			if ("isShowTooltip".equals(sortColumn)) {
				orderBy = "E.ISSHOWTOOLTIP_" + a_x;
			}

			if ("isEditor".equals(sortColumn)) {
				orderBy = "E.ISEDITOR_" + a_x;
			}

			if ("editor".equals(sortColumn)) {
				orderBy = "E.EDITOR_" + a_x;
			}

			if ("state".equals(sortColumn)) {
				orderBy = "E.STATE_" + a_x;
			}

			if ("checked".equals(sortColumn)) {
				orderBy = "E.CHECKED_" + a_x;
			}

			if ("alignment".equals(sortColumn)) {
				orderBy = "E.ALIGNMENT_" + a_x;
			}

			if ("domId".equals(sortColumn)) {
				orderBy = "E.DOMID_" + a_x;
			}

			if ("target".equals(sortColumn)) {
				orderBy = "E.TARGET_" + a_x;
			}

			if ("url".equals(sortColumn)) {
				orderBy = "E.URL_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
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

	public String getTableNameCN() {
		return tableNameCN;
	}

	public String getTableNameCNLike() {
		if (tableNameCNLike != null && tableNameCNLike.trim().length() > 0) {
			if (!tableNameCNLike.startsWith("%")) {
				tableNameCNLike = "%" + tableNameCNLike;
			}
			if (!tableNameCNLike.endsWith("%")) {
				tableNameCNLike = tableNameCNLike + "%";
			}
		}
		return tableNameCNLike;
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
		addColumn("name", "NAME_");
		addColumn("code", "CODE_");
		addColumn("fieldTable", "FIELDTABLE_");
		addColumn("tableNameCN", "TABLENAMECN_");
		addColumn("columnName", "COLUMNNAME_");
		addColumn("columnWidth", "COLUMNWIDTH_");
		addColumn("text", "TEXT_");
		addColumn("description", "DESCRIPTION_");
		addColumn("fieldId", "FIELDID_");
		addColumn("fieldLength", "FIELDLENGTH_");
		addColumn("fieldType", "FIELDTYPE_");
		addColumn("isShowList", "ISSHOWLIST_");
		addColumn("isShowTooltip", "ISSHOWTOOLTIP_");
		addColumn("isEditor", "ISEDITOR_");
		addColumn("editor", "EDITOR_");
		addColumn("state", "STATE_");
		addColumn("checked", "CHECKED_");
		addColumn("alignment", "ALIGNMENT_");
		addColumn("domId", "DOMID_");
		addColumn("target", "TARGET_");
		addColumn("url", "URL_");
		addColumn("type", "TYPE_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public FieldSetQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public FieldSetQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FieldSetQuery nameLike(String nameLike) {
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

	public void setColumnName(String columnName) {
		this.columnName = columnName;
	}

	public void setColumnNameLike(String columnNameLike) {
		this.columnNameLike = columnNameLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDatasetId(Long datasetId) {
		this.datasetId = datasetId;
	}

	public void setDatasetIds(List<Long> datasetIds) {
		this.datasetIds = datasetIds;
	}

	public void setFieldTable(String fieldTable) {
		this.fieldTable = fieldTable;
	}

	public void setFieldTableLike(String fieldTableLike) {
		this.fieldTableLike = fieldTableLike;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setTableNameCN(String tableNameCN) {
		this.tableNameCN = tableNameCN;
	}

	public void setTableNameCNLike(String tableNameCNLike) {
		this.tableNameCNLike = tableNameCNLike;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
	}

	public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
	}

	public FieldSetQuery tableNameCN(String tableNameCN) {
		if (tableNameCN == null) {
			throw new RuntimeException("tableNameCN is null");
		}
		this.tableNameCN = tableNameCN;
		return this;
	}

	public FieldSetQuery tableNameCNLike(String tableNameCNLike) {
		if (tableNameCNLike == null) {
			throw new RuntimeException("tableNameCN is null");
		}
		this.tableNameCNLike = tableNameCNLike;
		return this;
	}

	public FieldSetQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public FieldSetQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		if (updateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
		return this;
	}

	public FieldSetQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		if (updateTimeLessThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
		return this;
	}

}