package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DataSetMappingQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String dsName;
	protected String dsNameLike;
	protected List<String> dsNames;
	protected String dsmId;
	protected String dsmIdLike;
	protected List<String> dsmIds;
	protected String dsmName;
	protected String dsmNameLike;
	protected List<String> dsmNames;
	protected List<Integer> statuss;
	protected String type;
	protected String typeLike;
	protected List<String> types;
	protected List<String> treeIds;
	protected String topId;
	protected String topIdLike;
	protected List<String> topIds;
	protected String parentId_;
	protected String parentIdLike;
	protected String createByLike;
	protected List<String> createBys;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;

	public DataSetMappingQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getDsName() {
		return dsName;
	}

	public String getDsNameLike() {
		if (dsNameLike != null && dsNameLike.trim().length() > 0) {
			if (!dsNameLike.startsWith("%")) {
				dsNameLike = "%" + dsNameLike;
			}
			if (!dsNameLike.endsWith("%")) {
				dsNameLike = dsNameLike + "%";
			}
		}
		return dsNameLike;
	}

	public List<String> getDsNames() {
		return dsNames;
	}

	public String getDsmId() {
		return dsmId;
	}

	public String getDsmIdLike() {
		if (dsmIdLike != null && dsmIdLike.trim().length() > 0) {
			if (!dsmIdLike.startsWith("%")) {
				dsmIdLike = "%" + dsmIdLike;
			}
			if (!dsmIdLike.endsWith("%")) {
				dsmIdLike = dsmIdLike + "%";
			}
		}
		return dsmIdLike;
	}

	public List<String> getDsmIds() {
		return dsmIds;
	}

	public String getDsmName() {
		return dsmName;
	}

	public String getDsmNameLike() {
		if (dsmNameLike != null && dsmNameLike.trim().length() > 0) {
			if (!dsmNameLike.startsWith("%")) {
				dsmNameLike = "%" + dsmNameLike;
			}
			if (!dsmNameLike.endsWith("%")) {
				dsmNameLike = dsmNameLike + "%";
			}
		}
		return dsmNameLike;
	}

	public List<String> getDsmNames() {
		return dsmNames;
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

	public String getType() {
		return type;
	}

	public String getTypeLike() {
		if (typeLike != null && typeLike.trim().length() > 0) {
			if (!typeLike.startsWith("%")) {
				typeLike = "%" + typeLike;
			}
			if (!typeLike.endsWith("%")) {
				typeLike = typeLike + "%";
			}
		}
		return typeLike;
	}

	public List<String> getTypes() {
		return types;
	}

	public String getTreeId() {
		return treeId;
	}

	public String getTreeIdLike() {
		if (treeIdLike != null && treeIdLike.trim().length() > 0) {
			if (!treeIdLike.startsWith("%")) {
				treeIdLike = "%" + treeIdLike;
			}
			if (!treeIdLike.endsWith("%")) {
				treeIdLike = treeIdLike + "%";
			}
		}
		return treeIdLike;
	}

	public List<String> getTreeIds() {
		return treeIds;
	}

	public String getTopId() {
		return topId;
	}

	public String getTopIdLike() {
		if (topIdLike != null && topIdLike.trim().length() > 0) {
			if (!topIdLike.startsWith("%")) {
				topIdLike = "%" + topIdLike;
			}
			if (!topIdLike.endsWith("%")) {
				topIdLike = topIdLike + "%";
			}
		}
		return topIdLike;
	}

	public List<String> getTopIds() {
		return topIds;
	}

	public String getParentIdLike() {
		if (parentIdLike != null && parentIdLike.trim().length() > 0) {
			if (!parentIdLike.startsWith("%")) {
				parentIdLike = "%" + parentIdLike;
			}
			if (!parentIdLike.endsWith("%")) {
				parentIdLike = parentIdLike + "%";
			}
		}
		return parentIdLike;
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

	public void setDsName(String dsName) {
		this.dsName = dsName;
	}

	public void setDsNameLike(String dsNameLike) {
		this.dsNameLike = dsNameLike;
	}

	public void setDsNames(List<String> dsNames) {
		this.dsNames = dsNames;
	}

	public void setDsmId(String dsmId) {
		this.dsmId = dsmId;
	}

	public void setDsmIdLike(String dsmIdLike) {
		this.dsmIdLike = dsmIdLike;
	}

	public void setDsmIds(List<String> dsmIds) {
		this.dsmIds = dsmIds;
	}

	public void setDsmName(String dsmName) {
		this.dsmName = dsmName;
	}

	public void setDsmNameLike(String dsmNameLike) {
		this.dsmNameLike = dsmNameLike;
	}

	public void setDsmNames(List<String> dsmNames) {
		this.dsmNames = dsmNames;
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

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeLike(String typeLike) {
		this.typeLike = typeLike;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setTreeIdLike(String treeIdLike) {
		this.treeIdLike = treeIdLike;
	}

	public void setTreeIds(List<String> treeIds) {
		this.treeIds = treeIds;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public void setTopIdLike(String topIdLike) {
		this.topIdLike = topIdLike;
	}

	public void setTopIds(List<String> topIds) {
		this.topIds = topIds;
	}

	public void setParentIdLike(String parentIdLike) {
		this.parentIdLike = parentIdLike;
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

	public DataSetMappingQuery dsName(String dsName) {
		if (dsName == null) {
			throw new RuntimeException("dsName is null");
		}
		this.dsName = dsName;
		return this;
	}

	public DataSetMappingQuery dsNameLike(String dsNameLike) {
		if (dsNameLike == null) {
			throw new RuntimeException("dsName is null");
		}
		this.dsNameLike = dsNameLike;
		return this;
	}

	public DataSetMappingQuery dsNames(List<String> dsNames) {
		if (dsNames == null) {
			throw new RuntimeException("dsNames is empty ");
		}
		this.dsNames = dsNames;
		return this;
	}

	public DataSetMappingQuery dsmId(String dsmId) {
		if (dsmId == null) {
			throw new RuntimeException("dsmId is null");
		}
		this.dsmId = dsmId;
		return this;
	}

	public DataSetMappingQuery dsmIdLike(String dsmIdLike) {
		if (dsmIdLike == null) {
			throw new RuntimeException("dsmId is null");
		}
		this.dsmIdLike = dsmIdLike;
		return this;
	}

	public DataSetMappingQuery dsmIds(List<String> dsmIds) {
		if (dsmIds == null) {
			throw new RuntimeException("dsmIds is empty ");
		}
		this.dsmIds = dsmIds;
		return this;
	}

	public DataSetMappingQuery dsmName(String dsmName) {
		if (dsmName == null) {
			throw new RuntimeException("dsmName is null");
		}
		this.dsmName = dsmName;
		return this;
	}

	public DataSetMappingQuery dsmNameLike(String dsmNameLike) {
		if (dsmNameLike == null) {
			throw new RuntimeException("dsmName is null");
		}
		this.dsmNameLike = dsmNameLike;
		return this;
	}

	public DataSetMappingQuery dsmNames(List<String> dsmNames) {
		if (dsmNames == null) {
			throw new RuntimeException("dsmNames is empty ");
		}
		this.dsmNames = dsmNames;
		return this;
	}

	public DataSetMappingQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public DataSetMappingQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public DataSetMappingQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public DataSetMappingQuery statuss(List<Integer> statuss) {
		if (statuss == null) {
			throw new RuntimeException("statuss is empty ");
		}
		this.statuss = statuss;
		return this;
	}

	public DataSetMappingQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DataSetMappingQuery typeLike(String typeLike) {
		if (typeLike == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLike = typeLike;
		return this;
	}

	public DataSetMappingQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public DataSetMappingQuery treeId(String treeId) {
		if (treeId == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeId = treeId;
		return this;
	}

	public DataSetMappingQuery treeIdLike(String treeIdLike) {
		if (treeIdLike == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeIdLike = treeIdLike;
		return this;
	}

	public DataSetMappingQuery treeIds(List<String> treeIds) {
		if (treeIds == null) {
			throw new RuntimeException("treeIds is empty ");
		}
		this.treeIds = treeIds;
		return this;
	}

	public DataSetMappingQuery topId(String topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public DataSetMappingQuery topIdLike(String topIdLike) {
		if (topIdLike == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLike = topIdLike;
		return this;
	}

	public DataSetMappingQuery topIds(List<String> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	public DataSetMappingQuery parentIdLike(String parentIdLike) {
		if (parentIdLike == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentIdLike = parentIdLike;
		return this;
	}

	public DataSetMappingQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public DataSetMappingQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public DataSetMappingQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public DataSetMappingQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public DataSetMappingQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public DataSetMappingQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public DataSetMappingQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public DataSetMappingQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public DataSetMappingQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public DataSetMappingQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
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

			if ("dsName".equals(sortColumn)) {
				orderBy = "E.DSNAME_" + a_x;
			}

			if ("dsmId".equals(sortColumn)) {
				orderBy = "E.DSID_" + a_x;
			}

			if ("dsmName".equals(sortColumn)) {
				orderBy = "E.DSMNAME_" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("treeId".equals(sortColumn)) {
				orderBy = "E.TREEID_" + a_x;
			}

			if ("topId".equals(sortColumn)) {
				orderBy = "E.TOPID_" + a_x;
			}

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENTID_" + a_x;
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
		addColumn("dsName", "DSNAME_");
		addColumn("dsmId", "DSID_");
		addColumn("dsmName", "DSMNAME_");
		addColumn("status", "STATUS_");
		addColumn("type", "TYPE_");
		addColumn("treeId", "TREEID_");
		addColumn("topId", "TOPID_");
		addColumn("parentId", "PARENTID_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateDate", "UPDATEDATE_");
	}

	public String getParentId_() {
		return parentId_;
	}

	public void setParentId_(String parentId_) {
		this.parentId_ = parentId_;
	}

}