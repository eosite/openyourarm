package com.glaf.convert.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ConvertElemTmplDataQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> dataRuleIds;
	protected Collection<String> appActorIds;
	protected Long cvtElemId;
	protected Long cvtElemIdGreaterThanOrEqual;
	protected Long cvtElemIdLessThanOrEqual;
	protected List<Long> cvtElemIds;
	protected String tableName;
	protected String tableNameLike;
	protected List<String> tableNames;
	protected String fieldName;
	protected String fieldNameLike;
	protected List<String> fieldNames;
	protected String dataTableId;
	protected String dataTableIdLike;
	protected List<String> dataTableIds;
	protected String subTable;
	protected String subTableLike;
	protected List<String> subTables;
	protected Date createDatetimeGreaterThanOrEqual;
	protected Date createDatetimeLessThanOrEqual;
	protected Date modifyDatetimeGreaterThanOrEqual;
	protected Date modifyDatetimeLessThanOrEqual;

	public ConvertElemTmplDataQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getCvtElemId() {
		return cvtElemId;
	}

	public Long getCvtElemIdGreaterThanOrEqual() {
		return cvtElemIdGreaterThanOrEqual;
	}

	public Long getCvtElemIdLessThanOrEqual() {
		return cvtElemIdLessThanOrEqual;
	}

	public List<Long> getCvtElemIds() {
		return cvtElemIds;
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

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldNameLike() {
		if (fieldNameLike != null && fieldNameLike.trim().length() > 0) {
			if (!fieldNameLike.startsWith("%")) {
				fieldNameLike = "%" + fieldNameLike;
			}
			if (!fieldNameLike.endsWith("%")) {
				fieldNameLike = fieldNameLike + "%";
			}
		}
		return fieldNameLike;
	}

	public List<String> getFieldNames() {
		return fieldNames;
	}

	public String getDataTableId() {
		return dataTableId;
	}

	public String getDataTableIdLike() {
		if (dataTableIdLike != null && dataTableIdLike.trim().length() > 0) {
			if (!dataTableIdLike.startsWith("%")) {
				dataTableIdLike = "%" + dataTableIdLike;
			}
			if (!dataTableIdLike.endsWith("%")) {
				dataTableIdLike = dataTableIdLike + "%";
			}
		}
		return dataTableIdLike;
	}

	public List<String> getDataTableIds() {
		return dataTableIds;
	}

	public String getSubTable() {
		return subTable;
	}

	public String getSubTableLike() {
		if (subTableLike != null && subTableLike.trim().length() > 0) {
			if (!subTableLike.startsWith("%")) {
				subTableLike = "%" + subTableLike;
			}
			if (!subTableLike.endsWith("%")) {
				subTableLike = subTableLike + "%";
			}
		}
		return subTableLike;
	}

	public List<String> getSubTables() {
		return subTables;
	}

	public Date getCreateDatetimeGreaterThanOrEqual() {
		return createDatetimeGreaterThanOrEqual;
	}

	public Date getCreateDatetimeLessThanOrEqual() {
		return createDatetimeLessThanOrEqual;
	}

	public Date getModifyDatetimeGreaterThanOrEqual() {
		return modifyDatetimeGreaterThanOrEqual;
	}

	public Date getModifyDatetimeLessThanOrEqual() {
		return modifyDatetimeLessThanOrEqual;
	}

	public void setCvtElemId(Long cvtElemId) {
		this.cvtElemId = cvtElemId;
	}

	public void setCvtElemIdGreaterThanOrEqual(Long cvtElemIdGreaterThanOrEqual) {
		this.cvtElemIdGreaterThanOrEqual = cvtElemIdGreaterThanOrEqual;
	}

	public void setCvtElemIdLessThanOrEqual(Long cvtElemIdLessThanOrEqual) {
		this.cvtElemIdLessThanOrEqual = cvtElemIdLessThanOrEqual;
	}

	public void setCvtElemIds(List<Long> cvtElemIds) {
		this.cvtElemIds = cvtElemIds;
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

	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}

	public void setFieldNameLike(String fieldNameLike) {
		this.fieldNameLike = fieldNameLike;
	}

	public void setFieldNames(List<String> fieldNames) {
		this.fieldNames = fieldNames;
	}

	public void setDataTableId(String dataTableId) {
		this.dataTableId = dataTableId;
	}

	public void setDataTableIdLike(String dataTableIdLike) {
		this.dataTableIdLike = dataTableIdLike;
	}

	public void setDataTableIds(List<String> dataTableIds) {
		this.dataTableIds = dataTableIds;
	}

	public void setSubTable(String subTable) {
		this.subTable = subTable;
	}

	public void setSubTableLike(String subTableLike) {
		this.subTableLike = subTableLike;
	}

	public void setSubTables(List<String> subTables) {
		this.subTables = subTables;
	}

	public void setCreateDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
	}

	public void setCreateDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
	}

	public void setModifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
	}

	public void setModifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
	}

	public ConvertElemTmplDataQuery cvtElemId(Long cvtElemId) {
		if (cvtElemId == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemId = cvtElemId;
		return this;
	}

	public ConvertElemTmplDataQuery cvtElemIdGreaterThanOrEqual(Long cvtElemIdGreaterThanOrEqual) {
		if (cvtElemIdGreaterThanOrEqual == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemIdGreaterThanOrEqual = cvtElemIdGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplDataQuery cvtElemIdLessThanOrEqual(Long cvtElemIdLessThanOrEqual) {
		if (cvtElemIdLessThanOrEqual == null) {
			throw new RuntimeException("cvtElemId is null");
		}
		this.cvtElemIdLessThanOrEqual = cvtElemIdLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplDataQuery cvtElemIds(List<Long> cvtElemIds) {
		if (cvtElemIds == null) {
			throw new RuntimeException("cvtElemIds is empty ");
		}
		this.cvtElemIds = cvtElemIds;
		return this;
	}

	public ConvertElemTmplDataQuery tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public ConvertElemTmplDataQuery tableNameLike(String tableNameLike) {
		if (tableNameLike == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableNameLike = tableNameLike;
		return this;
	}

	public ConvertElemTmplDataQuery tableNames(List<String> tableNames) {
		if (tableNames == null) {
			throw new RuntimeException("tableNames is empty ");
		}
		this.tableNames = tableNames;
		return this;
	}

	public ConvertElemTmplDataQuery fieldName(String fieldName) {
		if (fieldName == null) {
			throw new RuntimeException("fieldName is null");
		}
		this.fieldName = fieldName;
		return this;
	}

	public ConvertElemTmplDataQuery fieldNameLike(String fieldNameLike) {
		if (fieldNameLike == null) {
			throw new RuntimeException("fieldName is null");
		}
		this.fieldNameLike = fieldNameLike;
		return this;
	}

	public ConvertElemTmplDataQuery fieldNames(List<String> fieldNames) {
		if (fieldNames == null) {
			throw new RuntimeException("fieldNames is empty ");
		}
		this.fieldNames = fieldNames;
		return this;
	}

	public ConvertElemTmplDataQuery dataTableId(String dataTableId) {
		if (dataTableId == null) {
			throw new RuntimeException("dataTableId is null");
		}
		this.dataTableId = dataTableId;
		return this;
	}

	public ConvertElemTmplDataQuery dataTableIdLike(String dataTableIdLike) {
		if (dataTableIdLike == null) {
			throw new RuntimeException("dataTableId is null");
		}
		this.dataTableIdLike = dataTableIdLike;
		return this;
	}

	public ConvertElemTmplDataQuery dataTableIds(List<String> dataTableIds) {
		if (dataTableIds == null) {
			throw new RuntimeException("dataTableIds is empty ");
		}
		this.dataTableIds = dataTableIds;
		return this;
	}

	public ConvertElemTmplDataQuery subTable(String subTable) {
		if (subTable == null) {
			throw new RuntimeException("subTable is null");
		}
		this.subTable = subTable;
		return this;
	}

	public ConvertElemTmplDataQuery subTableLike(String subTableLike) {
		if (subTableLike == null) {
			throw new RuntimeException("subTable is null");
		}
		this.subTableLike = subTableLike;
		return this;
	}

	public ConvertElemTmplDataQuery subTables(List<String> subTables) {
		if (subTables == null) {
			throw new RuntimeException("subTables is empty ");
		}
		this.subTables = subTables;
		return this;
	}

	public ConvertElemTmplDataQuery createDatetimeGreaterThanOrEqual(Date createDatetimeGreaterThanOrEqual) {
		if (createDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeGreaterThanOrEqual = createDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplDataQuery createDatetimeLessThanOrEqual(Date createDatetimeLessThanOrEqual) {
		if (createDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("createDatetime is null");
		}
		this.createDatetimeLessThanOrEqual = createDatetimeLessThanOrEqual;
		return this;
	}

	public ConvertElemTmplDataQuery modifyDatetimeGreaterThanOrEqual(Date modifyDatetimeGreaterThanOrEqual) {
		if (modifyDatetimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeGreaterThanOrEqual = modifyDatetimeGreaterThanOrEqual;
		return this;
	}

	public ConvertElemTmplDataQuery modifyDatetimeLessThanOrEqual(Date modifyDatetimeLessThanOrEqual) {
		if (modifyDatetimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDatetime is null");
		}
		this.modifyDatetimeLessThanOrEqual = modifyDatetimeLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("cvtElemId".equals(sortColumn)) {
				orderBy = "E.CVT_ELEM_ID_" + a_x;
			}

			if ("tableName".equals(sortColumn)) {
				orderBy = "E.TABLE_NAME_" + a_x;
			}

			if ("fieldName".equals(sortColumn)) {
				orderBy = "E.FIELD_NAME_" + a_x;
			}

			if ("dataTableId".equals(sortColumn)) {
				orderBy = "E.DATA_TABLE_ID_" + a_x;
			}

			if ("subTable".equals(sortColumn)) {
				orderBy = "E.SUB_TABLE_" + a_x;
			}

			if ("createDatetime".equals(sortColumn)) {
				orderBy = "E.CREATE_DATETIME_" + a_x;
			}

			if ("modifyDatetime".equals(sortColumn)) {
				orderBy = "E.MODIFY_DATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("dataRuleId", "DATA_RULE_ID_");
		addColumn("cvtElemId", "CVT_ELEM_ID_");
		addColumn("tableName", "TABLE_NAME_");
		addColumn("fieldName", "FIELD_NAME_");
		addColumn("dataTableId", "DATA_TABLE_ID_");
		addColumn("subTable", "SUB_TABLE_");
		addColumn("createDatetime", "CREATE_DATETIME_");
		addColumn("modifyDatetime", "MODIFY_DATETIME_");
	}

}