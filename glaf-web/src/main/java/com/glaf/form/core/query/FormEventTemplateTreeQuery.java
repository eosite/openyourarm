package com.glaf.form.core.query;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class FormEventTemplateTreeQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Integer indexId;
	protected Integer indexIdGreaterThanOrEqual;
	protected Integer indexIdLessThanOrEqual;
	protected List<Integer> indexIds;
	protected Integer parentId;
	protected Integer parentIdGreaterThanOrEqual;
	protected Integer parentIdLessThanOrEqual;
	protected String treeId;
	protected String treeIdLike;
	protected List<String> treeIds;
	protected Integer type;
	protected Integer typeGreaterThanOrEqual;
	protected Integer typeLessThanOrEqual;
	protected List<Integer> types;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected Integer deleteFlag;
	protected Integer deleteFlagGreaterThanOrEqual;
	protected Integer deleteFlagLessThanOrEqual;
	protected List<Integer> deleteFlags;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected String createBy;
	protected String createByLike;
	protected List<String> createBys;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;

	public FormEventTemplateTreeQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Integer getIndexId() {
		return indexId;
	}

	public Integer getIndexIdGreaterThanOrEqual() {
		return indexIdGreaterThanOrEqual;
	}

	public Integer getIndexIdLessThanOrEqual() {
		return indexIdLessThanOrEqual;
	}

	public List<Integer> getIndexIds() {
		return indexIds;
	}


	public Integer getParentIdGreaterThanOrEqual() {
		return parentIdGreaterThanOrEqual;
	}

	public Integer getParentIdLessThanOrEqual() {
		return parentIdLessThanOrEqual;
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

	public Integer getType() {
		return type;
	}

	public Integer getTypeGreaterThanOrEqual() {
		return typeGreaterThanOrEqual;
	}

	public Integer getTypeLessThanOrEqual() {
		return typeLessThanOrEqual;
	}

	public List<Integer> getTypes() {
		return types;
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

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public Integer getDeleteFlagGreaterThanOrEqual() {
		return deleteFlagGreaterThanOrEqual;
	}

	public Integer getDeleteFlagLessThanOrEqual() {
		return deleteFlagLessThanOrEqual;
	}

	public List<Integer> getDeleteFlags() {
		return deleteFlags;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
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

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setIndexIdGreaterThanOrEqual(Integer indexIdGreaterThanOrEqual) {
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
	}

	public void setIndexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
	}

	public void setIndexIds(List<Integer> indexIds) {
		this.indexIds = indexIds;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setParentIdGreaterThanOrEqual(Integer parentIdGreaterThanOrEqual) {
		this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
	}

	public void setParentIdLessThanOrEqual(Integer parentIdLessThanOrEqual) {
		this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
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

	public void setType(Integer type) {
		this.type = type;
	}

	public void setTypeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
	}

	public void setTypeLessThanOrEqual(Integer typeLessThanOrEqual) {
		this.typeLessThanOrEqual = typeLessThanOrEqual;
	}

	public void setTypes(List<Integer> types) {
		this.types = types;
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

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDeleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual) {
		this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
	}

	public void setDeleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual) {
		this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
	}

	public void setDeleteFlags(List<Integer> deleteFlags) {
		this.deleteFlags = deleteFlags;
	}

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
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

	public FormEventTemplateTreeQuery indexId(Integer indexId) {
		if (indexId == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexId = indexId;
		return this;
	}

	public FormEventTemplateTreeQuery indexIdGreaterThanOrEqual(Integer indexIdGreaterThanOrEqual) {
		if (indexIdGreaterThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdGreaterThanOrEqual = indexIdGreaterThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery indexIdLessThanOrEqual(Integer indexIdLessThanOrEqual) {
		if (indexIdLessThanOrEqual == null) {
			throw new RuntimeException("indexId is null");
		}
		this.indexIdLessThanOrEqual = indexIdLessThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery indexIds(List<Integer> indexIds) {
		if (indexIds == null) {
			throw new RuntimeException("indexIds is empty ");
		}
		this.indexIds = indexIds;
		return this;
	}

	public FormEventTemplateTreeQuery parentId(Integer parentId) {
		if (parentId == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentId = parentId;
		return this;
	}

	public FormEventTemplateTreeQuery parentIdGreaterThanOrEqual(Integer parentIdGreaterThanOrEqual) {
		if (parentIdGreaterThanOrEqual == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentIdGreaterThanOrEqual = parentIdGreaterThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery parentIdLessThanOrEqual(Integer parentIdLessThanOrEqual) {
		if (parentIdLessThanOrEqual == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentIdLessThanOrEqual = parentIdLessThanOrEqual;
		return this;
	}


	public FormEventTemplateTreeQuery treeId(String treeId) {
		if (treeId == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeId = treeId;
		return this;
	}

	public FormEventTemplateTreeQuery treeIdLike(String treeIdLike) {
		if (treeIdLike == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeIdLike = treeIdLike;
		return this;
	}

	public FormEventTemplateTreeQuery treeIds(List<String> treeIds) {
		if (treeIds == null) {
			throw new RuntimeException("treeIds is empty ");
		}
		this.treeIds = treeIds;
		return this;
	}

	public FormEventTemplateTreeQuery type(Integer type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public FormEventTemplateTreeQuery typeGreaterThanOrEqual(Integer typeGreaterThanOrEqual) {
		if (typeGreaterThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeGreaterThanOrEqual = typeGreaterThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery typeLessThanOrEqual(Integer typeLessThanOrEqual) {
		if (typeLessThanOrEqual == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLessThanOrEqual = typeLessThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery types(List<Integer> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public FormEventTemplateTreeQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormEventTemplateTreeQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FormEventTemplateTreeQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public FormEventTemplateTreeQuery deleteFlag(Integer deleteFlag) {
		if (deleteFlag == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlag = deleteFlag;
		return this;
	}

	public FormEventTemplateTreeQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual) {
		if (deleteFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual) {
		if (deleteFlagLessThanOrEqual == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery deleteFlags(List<Integer> deleteFlags) {
		if (deleteFlags == null) {
			throw new RuntimeException("deleteFlags is empty ");
		}
		this.deleteFlags = deleteFlags;
		return this;
	}

	public FormEventTemplateTreeQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public FormEventTemplateTreeQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public FormEventTemplateTreeQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FormEventTemplateTreeQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public FormEventTemplateTreeQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public FormEventTemplateTreeQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public FormEventTemplateTreeQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public FormEventTemplateTreeQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
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

			if ("indexId".equals(sortColumn)) {
				orderBy = "E.INDEXID_" + a_x;
			}

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENTID_" + a_x;
			}

			if ("treeId".equals(sortColumn)) {
				orderBy = "E.TREEID_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
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
		addColumn("indexId", "INDEXID_");
		addColumn("parentId", "PARENTID_");
		addColumn("treeId", "TREEID_");
		addColumn("type", "TYPE_");
		addColumn("name", "NAME_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateDate", "UPDATEDATE_");
	}

}