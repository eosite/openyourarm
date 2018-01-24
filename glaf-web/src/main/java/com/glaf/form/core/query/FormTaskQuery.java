package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormTaskQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String pageId;
	protected String pageIdIn;
	protected String pageIdLike;
	protected List<String> pageIds;
	protected Long tmId;
	protected Long tmIdGreaterThanOrEqual;
	protected Long tmIdLessThanOrEqual;
	protected List<Long> tmIds;
	protected String tableName;
	protected String tableNameLike;
	protected List<String> tableNames;
	protected String idValue;
	protected String idValueLike;
	protected List<String> idValues;
	protected String variableKey;
	protected String variableKeyLike;
	protected List<String> variableKeys;
	protected Integer status;
	protected Integer statusGreaterThanOrEqual;
	protected Integer statusLessThanOrEqual;
	protected List<Integer> statuss;
	protected String createBy;
	protected String createByLike;
	protected List<String> createBys;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;

	public FormTaskQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getPageId() {
		return pageId;
	}

	public String getPageIdLike() {
		if (pageIdLike != null && pageIdLike.trim().length() > 0) {
			if (!pageIdLike.startsWith("%")) {
				pageIdLike = "%" + pageIdLike;
			}
			if (!pageIdLike.endsWith("%")) {
				pageIdLike = pageIdLike + "%";
			}
		}
		return pageIdLike;
	}

	public List<String> getPageIds() {
		return pageIds;
	}

	public Long getTmId() {
		return tmId;
	}

	public Long getTmIdGreaterThanOrEqual() {
		return tmIdGreaterThanOrEqual;
	}

	public Long getTmIdLessThanOrEqual() {
		return tmIdLessThanOrEqual;
	}

	public List<Long> getTmIds() {
		return tmIds;
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

	public String getIdValue() {
		return idValue;
	}

	public String getIdValueLike() {
		if (idValueLike != null && idValueLike.trim().length() > 0) {
			if (!idValueLike.startsWith("%")) {
				idValueLike = "%" + idValueLike;
			}
			if (!idValueLike.endsWith("%")) {
				idValueLike = idValueLike + "%";
			}
		}
		return idValueLike;
	}

	public List<String> getIdValues() {
		return idValues;
	}

	public String getVariableKey() {
		return variableKey;
	}

	public String getVariableKeyLike() {
		if (variableKeyLike != null && variableKeyLike.trim().length() > 0) {
			if (!variableKeyLike.startsWith("%")) {
				variableKeyLike = "%" + variableKeyLike;
			}
			if (!variableKeyLike.endsWith("%")) {
				variableKeyLike = variableKeyLike + "%";
			}
		}
		return variableKeyLike;
	}

	public List<String> getVariableKeys() {
		return variableKeys;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
	}

	public List<Integer> getStatuss() {
		return statuss;
	}

	public String getCreateBy() {
		return createBy;
	}

	public String getCreateByLike() {
		if (createByLike != null && createByLike.trim().length() > 0) {
			if (!createByLike.startsWith("%")) {
				createByLike = "%" + createByLike;
			}
			if (!createByLike.endsWith("%")) {
				createByLike = createByLike + "%";
			}
		}
		return createByLike;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public String getUpdateByLike() {
		if (updateByLike != null && updateByLike.trim().length() > 0) {
			if (!updateByLike.startsWith("%")) {
				updateByLike = "%" + updateByLike;
			}
			if (!updateByLike.endsWith("%")) {
				updateByLike = updateByLike + "%";
			}
		}
		return updateByLike;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setPageIdLike(String pageIdLike) {
		this.pageIdLike = pageIdLike;
	}

	public void setPageIds(List<String> pageIds) {
		this.pageIds = pageIds;
	}

	public void setTmId(Long tmId) {
		this.tmId = tmId;
	}

	public void setTmIdGreaterThanOrEqual(Long tmIdGreaterThanOrEqual) {
		this.tmIdGreaterThanOrEqual = tmIdGreaterThanOrEqual;
	}

	public void setTmIdLessThanOrEqual(Long tmIdLessThanOrEqual) {
		this.tmIdLessThanOrEqual = tmIdLessThanOrEqual;
	}

	public void setTmIds(List<Long> tmIds) {
		this.tmIds = tmIds;
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

	public void setIdValue(String idValue) {
		this.idValue = idValue;
	}

	public void setIdValueLike(String idValueLike) {
		this.idValueLike = idValueLike;
	}

	public void setIdValues(List<String> idValues) {
		this.idValues = idValues;
	}

	public void setVariableKey(String variableKey) {
		this.variableKey = variableKey;
	}

	public void setVariableKeyLike(String variableKeyLike) {
		this.variableKeyLike = variableKeyLike;
	}

	public void setVariableKeys(List<String> variableKeys) {
		this.variableKeys = variableKeys;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
	}

	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual) {
		this.statusLessThanOrEqual = statusLessThanOrEqual;
	}

	public void setStatuss(List<Integer> statuss) {
		this.statuss = statuss;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateByLike(String updateByLike) {
		this.updateByLike = updateByLike;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public FormTaskQuery pageId(String pageId) {
		if (pageId == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageId = pageId;
		return this;
	}

	public FormTaskQuery pageIdLike(String pageIdLike) {
		if (pageIdLike == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageIdLike = pageIdLike;
		return this;
	}

	public FormTaskQuery pageIds(List<String> pageIds) {
		if (pageIds == null) {
			throw new RuntimeException("pageIds is empty ");
		}
		this.pageIds = pageIds;
		return this;
	}

	public FormTaskQuery tmId(Long tmId) {
		if (tmId == null) {
			throw new RuntimeException("tmId is null");
		}
		this.tmId = tmId;
		return this;
	}

	public FormTaskQuery tmIdGreaterThanOrEqual(Long tmIdGreaterThanOrEqual) {
		if (tmIdGreaterThanOrEqual == null) {
			throw new RuntimeException("tmId is null");
		}
		this.tmIdGreaterThanOrEqual = tmIdGreaterThanOrEqual;
		return this;
	}

	public FormTaskQuery tmIdLessThanOrEqual(Long tmIdLessThanOrEqual) {
		if (tmIdLessThanOrEqual == null) {
			throw new RuntimeException("tmId is null");
		}
		this.tmIdLessThanOrEqual = tmIdLessThanOrEqual;
		return this;
	}

	public FormTaskQuery tmIds(List<Long> tmIds) {
		if (tmIds == null) {
			throw new RuntimeException("tmIds is empty ");
		}
		this.tmIds = tmIds;
		return this;
	}

	public FormTaskQuery tableName(String tableName) {
		if (tableName == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableName = tableName;
		return this;
	}

	public FormTaskQuery tableNameLike(String tableNameLike) {
		if (tableNameLike == null) {
			throw new RuntimeException("tableName is null");
		}
		this.tableNameLike = tableNameLike;
		return this;
	}

	public FormTaskQuery tableNames(List<String> tableNames) {
		if (tableNames == null) {
			throw new RuntimeException("tableNames is empty ");
		}
		this.tableNames = tableNames;
		return this;
	}

	public FormTaskQuery idValue(String idValue) {
		if (idValue == null) {
			throw new RuntimeException("idValue is null");
		}
		this.idValue = idValue;
		return this;
	}

	public FormTaskQuery idValueLike(String idValueLike) {
		if (idValueLike == null) {
			throw new RuntimeException("idValue is null");
		}
		this.idValueLike = idValueLike;
		return this;
	}

	public FormTaskQuery idValues(List<String> idValues) {
		if (idValues == null) {
			throw new RuntimeException("idValues is empty ");
		}
		this.idValues = idValues;
		return this;
	}

	public FormTaskQuery variableKey(String variableKey) {
		if (variableKey == null) {
			throw new RuntimeException("variableKey is null");
		}
		this.variableKey = variableKey;
		return this;
	}

	public FormTaskQuery variableKeyLike(String variableKeyLike) {
		if (variableKeyLike == null) {
			throw new RuntimeException("variableKey is null");
		}
		this.variableKeyLike = variableKeyLike;
		return this;
	}

	public FormTaskQuery variableKeys(List<String> variableKeys) {
		if (variableKeys == null) {
			throw new RuntimeException("variableKeys is empty ");
		}
		this.variableKeys = variableKeys;
		return this;
	}

	public FormTaskQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public FormTaskQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public FormTaskQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public FormTaskQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public FormTaskQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public FormTaskQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public FormTaskQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FormTaskQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormTaskQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormTaskQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public FormTaskQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public FormTaskQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public FormTaskQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public FormTaskQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("pageId".equals(sortColumn)) {
				orderBy = "E.PAGEID_" + a_x;
			}

			if ("tmId".equals(sortColumn)) {
				orderBy = "E.TMID_" + a_x;
			}

			if ("tableName".equals(sortColumn)) {
				orderBy = "E.TABLENAME_" + a_x;
			}

			if ("idValue".equals(sortColumn)) {
				orderBy = "E.IDVALUE_" + a_x;
			}

			if ("variableKey".equals(sortColumn)) {
				orderBy = "E.VARIABLEKEY_" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("pageId", "PAGEID_");
		addColumn("tmId", "TMID_");
		addColumn("tableName", "TABLENAME_");
		addColumn("idValue", "IDVALUE_");
		addColumn("variableKey", "VARIABLEKEY_");
		addColumn("status", "STATUS_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateDate", "UPDATEDATE_");
	}

	public String getPageIdIn() {
		return pageIdIn;
	}

	public void setPageIdIn(String pageIdIn) {
		this.pageIdIn = pageIdIn;
	}

}