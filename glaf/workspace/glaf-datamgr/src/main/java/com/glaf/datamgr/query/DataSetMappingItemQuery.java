package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DataSetMappingItemQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected String mappingName;
	protected String mappingNameLike;
	protected List<String> mappingNames;
	protected String mappingCode;
	protected String mappingCodeLike;
	protected List<String> mappingCodes;
	protected String mappingType;
	protected String mappingTypeLike;
	protected List<String> mappingTypes;
	protected List<String> treeIds;
	protected String topId;
	protected String topIdLike;
	protected List<String> topIds;
	protected String parentId;
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

	public DataSetMappingItemQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
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

	public List<String> getNames() {
		return names;
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

	public List<String> getCodes() {
		return codes;
	}

	public String getMappingName() {
		return mappingName;
	}

	public String getMappingNameLike() {
		if (mappingNameLike != null && mappingNameLike.trim().length() > 0) {
			if (!mappingNameLike.startsWith("%")) {
				mappingNameLike = "%" + mappingNameLike;
			}
			if (!mappingNameLike.endsWith("%")) {
				mappingNameLike = mappingNameLike + "%";
			}
		}
		return mappingNameLike;
	}

	public List<String> getMappingNames() {
		return mappingNames;
	}

	public String getMappingCode() {
		return mappingCode;
	}

	public String getMappingCodeLike() {
		if (mappingCodeLike != null && mappingCodeLike.trim().length() > 0) {
			if (!mappingCodeLike.startsWith("%")) {
				mappingCodeLike = "%" + mappingCodeLike;
			}
			if (!mappingCodeLike.endsWith("%")) {
				mappingCodeLike = mappingCodeLike + "%";
			}
		}
		return mappingCodeLike;
	}

	public List<String> getMappingCodes() {
		return mappingCodes;
	}

	public String getMappingType() {
		return mappingType;
	}

	public String getMappingTypeLike() {
		if (mappingTypeLike != null && mappingTypeLike.trim().length() > 0) {
			if (!mappingTypeLike.startsWith("%")) {
				mappingTypeLike = "%" + mappingTypeLike;
			}
			if (!mappingTypeLike.endsWith("%")) {
				mappingTypeLike = mappingTypeLike + "%";
			}
		}
		return mappingTypeLike;
	}

	public List<String> getMappingTypes() {
		return mappingTypes;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	public void setCodes(List<String> codes) {
		this.codes = codes;
	}

	public void setMappingName(String mappingName) {
		this.mappingName = mappingName;
	}

	public void setMappingNameLike(String mappingNameLike) {
		this.mappingNameLike = mappingNameLike;
	}

	public void setMappingNames(List<String> mappingNames) {
		this.mappingNames = mappingNames;
	}

	public void setMappingCode(String mappingCode) {
		this.mappingCode = mappingCode;
	}

	public void setMappingCodeLike(String mappingCodeLike) {
		this.mappingCodeLike = mappingCodeLike;
	}

	public void setMappingCodes(List<String> mappingCodes) {
		this.mappingCodes = mappingCodes;
	}

	public void setMappingType(String mappingType) {
		this.mappingType = mappingType;
	}

	public void setMappingTypeLike(String mappingTypeLike) {
		this.mappingTypeLike = mappingTypeLike;
	}

	public void setMappingTypes(List<String> mappingTypes) {
		this.mappingTypes = mappingTypes;
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

	public void setParentId(String parentId) {
		this.parentId = parentId;
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

	public DataSetMappingItemQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DataSetMappingItemQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DataSetMappingItemQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public DataSetMappingItemQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public DataSetMappingItemQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public DataSetMappingItemQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public DataSetMappingItemQuery mappingName(String mappingName) {
		if (mappingName == null) {
			throw new RuntimeException("mappingName is null");
		}
		this.mappingName = mappingName;
		return this;
	}

	public DataSetMappingItemQuery mappingNameLike(String mappingNameLike) {
		if (mappingNameLike == null) {
			throw new RuntimeException("mappingName is null");
		}
		this.mappingNameLike = mappingNameLike;
		return this;
	}

	public DataSetMappingItemQuery mappingNames(List<String> mappingNames) {
		if (mappingNames == null) {
			throw new RuntimeException("mappingNames is empty ");
		}
		this.mappingNames = mappingNames;
		return this;
	}

	public DataSetMappingItemQuery mappingCode(String mappingCode) {
		if (mappingCode == null) {
			throw new RuntimeException("mappingCode is null");
		}
		this.mappingCode = mappingCode;
		return this;
	}

	public DataSetMappingItemQuery mappingCodeLike(String mappingCodeLike) {
		if (mappingCodeLike == null) {
			throw new RuntimeException("mappingCode is null");
		}
		this.mappingCodeLike = mappingCodeLike;
		return this;
	}

	public DataSetMappingItemQuery mappingCodes(List<String> mappingCodes) {
		if (mappingCodes == null) {
			throw new RuntimeException("mappingCodes is empty ");
		}
		this.mappingCodes = mappingCodes;
		return this;
	}

	public DataSetMappingItemQuery mappingType(String mappingType) {
		if (mappingType == null) {
			throw new RuntimeException("mappingType is null");
		}
		this.mappingType = mappingType;
		return this;
	}

	public DataSetMappingItemQuery mappingTypeLike(String mappingTypeLike) {
		if (mappingTypeLike == null) {
			throw new RuntimeException("mappingType is null");
		}
		this.mappingTypeLike = mappingTypeLike;
		return this;
	}

	public DataSetMappingItemQuery mappingTypes(List<String> mappingTypes) {
		if (mappingTypes == null) {
			throw new RuntimeException("mappingTypes is empty ");
		}
		this.mappingTypes = mappingTypes;
		return this;
	}

	public DataSetMappingItemQuery treeId(String treeId) {
		if (treeId == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeId = treeId;
		return this;
	}

	public DataSetMappingItemQuery treeIdLike(String treeIdLike) {
		if (treeIdLike == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeIdLike = treeIdLike;
		return this;
	}

	public DataSetMappingItemQuery treeIds(List<String> treeIds) {
		if (treeIds == null) {
			throw new RuntimeException("treeIds is empty ");
		}
		this.treeIds = treeIds;
		return this;
	}

	public DataSetMappingItemQuery topId(String topId) {
		if (topId == null) {
			throw new RuntimeException("topId is null");
		}
		this.topId = topId;
		return this;
	}

	public DataSetMappingItemQuery topIdLike(String topIdLike) {
		if (topIdLike == null) {
			throw new RuntimeException("topId is null");
		}
		this.topIdLike = topIdLike;
		return this;
	}

	public DataSetMappingItemQuery topIds(List<String> topIds) {
		if (topIds == null) {
			throw new RuntimeException("topIds is empty ");
		}
		this.topIds = topIds;
		return this;
	}

	public DataSetMappingItemQuery parentId(String parentId) {
		if (parentId == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentId = parentId;
		return this;
	}

	public DataSetMappingItemQuery parentIdLike(String parentIdLike) {
		if (parentIdLike == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentIdLike = parentIdLike;
		return this;
	}

	public DataSetMappingItemQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public DataSetMappingItemQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public DataSetMappingItemQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public DataSetMappingItemQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public DataSetMappingItemQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public DataSetMappingItemQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public DataSetMappingItemQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public DataSetMappingItemQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public DataSetMappingItemQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public DataSetMappingItemQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
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

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE_" + a_x;
			}

			if ("mappingName".equals(sortColumn)) {
				orderBy = "E.MAPPINGNAME_" + a_x;
			}

			if ("mappingCode".equals(sortColumn)) {
				orderBy = "E.MAPPINGCODE_" + a_x;
			}

			if ("mappingType".equals(sortColumn)) {
				orderBy = "E.MAPPINGTYPE_" + a_x;
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
		addColumn("name", "NAME_");
		addColumn("code", "CODE_");
		addColumn("mappingName", "MAPPINGNAME_");
		addColumn("mappingCode", "MAPPINGCODE_");
		addColumn("mappingType", "MAPPINGTYPE_");
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