package com.glaf.model.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SubSystemDefQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> subSysIds;
	protected Collection<String> appActorIds;
	protected String sysId;
	protected List<String> sysIds;
	protected Integer level;
	protected Integer levelGreaterThanOrEqual;
	protected Integer levelLessThanOrEqual;
	protected List<Integer> levels;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String desc;
	protected String descLike;
	protected List<String> descs;
	protected String parentId_;
	protected List<String> parentIds_;
	protected String treeId;
	protected String treeIdLike;
	protected List<String> treeIds;
	protected String createBy;
	protected String createByLike;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected String updateBy;
	protected String updateByLike;
	protected List<String> updateBys;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;
	protected Integer deleteFlag;
	protected Integer deleteFlagGreaterThanOrEqual;
	protected Integer deleteFlagLessThanOrEqual;
	protected List<Integer> deleteFlags;

	public SubSystemDefQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getSysId() {
		return sysId;
	}

	public List<String> getSysIds() {
		return sysIds;
	}

	public Integer getLevel() {
		return level;
	}

	public Integer getLevelGreaterThanOrEqual() {
		return levelGreaterThanOrEqual;
	}

	public Integer getLevelLessThanOrEqual() {
		return levelLessThanOrEqual;
	}

	public List<Integer> getLevels() {
		return levels;
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

	public String getDesc() {
		return desc;
	}

	public String getDescLike() {
		if (descLike != null && descLike.trim().length() > 0) {
			if (!descLike.startsWith("%")) {
				descLike = "%" + descLike;
			}
			if (!descLike.endsWith("%")) {
				descLike = descLike + "%";
			}
		}
		return descLike;
	}

	public List<String> getDescs() {
		return descs;
	}

	public String getParentId_() {
		return parentId_;
	}

	public List<String> getParentIds_() {
		return parentIds_;
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

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
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

	public Date getUpdateTimeGreaterThanOrEqual() {
		return updateTimeGreaterThanOrEqual;
	}

	public Date getUpdateTimeLessThanOrEqual() {
		return updateTimeLessThanOrEqual;
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

	public void setSysId(String sysId) {
		this.sysId = sysId;
	}

	public void setSysIds(List<String> sysIds) {
		this.sysIds = sysIds;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	public void setLevelGreaterThanOrEqual(Integer levelGreaterThanOrEqual) {
		this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
	}

	public void setLevelLessThanOrEqual(Integer levelLessThanOrEqual) {
		this.levelLessThanOrEqual = levelLessThanOrEqual;
	}

	public void setLevels(List<Integer> levels) {
		this.levels = levels;
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

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}

	public void setDescs(List<String> descs) {
		this.descs = descs;
	}

	public void setParentId_(String parentId_) {
		this.parentId_ = parentId_;
	}

	public void setParentIds_(List<String> parentIds_) {
		this.parentIds_ = parentIds_;
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

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateByLike(String createByLike) {
		this.createByLike = createByLike;
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

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateByLike(String updateByLike) {
		this.updateByLike = updateByLike;
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

	public SubSystemDefQuery sysId(String sysId) {
		if (sysId == null) {
			throw new RuntimeException("sysId is null");
		}
		this.sysId = sysId;
		return this;
	}

	public SubSystemDefQuery sysIds(List<String> sysIds) {
		if (sysIds == null) {
			throw new RuntimeException("sysIds is empty ");
		}
		this.sysIds = sysIds;
		return this;
	}

	public SubSystemDefQuery level(Integer level) {
		if (level == null) {
			throw new RuntimeException("level is null");
		}
		this.level = level;
		return this;
	}

	public SubSystemDefQuery levelGreaterThanOrEqual(Integer levelGreaterThanOrEqual) {
		if (levelGreaterThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelGreaterThanOrEqual = levelGreaterThanOrEqual;
		return this;
	}

	public SubSystemDefQuery levelLessThanOrEqual(Integer levelLessThanOrEqual) {
		if (levelLessThanOrEqual == null) {
			throw new RuntimeException("level is null");
		}
		this.levelLessThanOrEqual = levelLessThanOrEqual;
		return this;
	}

	public SubSystemDefQuery levels(List<Integer> levels) {
		if (levels == null) {
			throw new RuntimeException("levels is empty ");
		}
		this.levels = levels;
		return this;
	}

	public SubSystemDefQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public SubSystemDefQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public SubSystemDefQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public SubSystemDefQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SubSystemDefQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public SubSystemDefQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public SubSystemDefQuery desc(String desc) {
		if (desc == null) {
			throw new RuntimeException("desc is null");
		}
		this.desc = desc;
		return this;
	}

	public SubSystemDefQuery descLike(String descLike) {
		if (descLike == null) {
			throw new RuntimeException("desc is null");
		}
		this.descLike = descLike;
		return this;
	}

	public SubSystemDefQuery descs(List<String> descs) {
		if (descs == null) {
			throw new RuntimeException("descs is empty ");
		}
		this.descs = descs;
		return this;
	}

	public SubSystemDefQuery parentId_(String parentId_) {
		if (parentId == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentId_ = parentId_;
		return this;
	}

	public SubSystemDefQuery parentIds_(List<String> parentIds_) {
		if (parentIds_ == null) {
			throw new RuntimeException("parentIds is empty ");
		}
		this.parentIds_ = parentIds_;
		return this;
	}

	public SubSystemDefQuery treeId(String treeId) {
		if (treeId == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeId = treeId;
		return this;
	}

	public SubSystemDefQuery treeIdLike(String treeIdLike) {
		if (treeIdLike == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeIdLike = treeIdLike;
		return this;
	}

	public SubSystemDefQuery treeIds(List<String> treeIds) {
		if (treeIds == null) {
			throw new RuntimeException("treeIds is empty ");
		}
		this.treeIds = treeIds;
		return this;
	}

	public SubSystemDefQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public SubSystemDefQuery createByLike(String createByLike) {
		if (createByLike == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createByLike = createByLike;
		return this;
	}

	public SubSystemDefQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public SubSystemDefQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public SubSystemDefQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public SubSystemDefQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public SubSystemDefQuery updateByLike(String updateByLike) {
		if (updateByLike == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateByLike = updateByLike;
		return this;
	}

	public SubSystemDefQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public SubSystemDefQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		if (updateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
		return this;
	}

	public SubSystemDefQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		if (updateTimeLessThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
		return this;
	}

	public SubSystemDefQuery deleteFlag(Integer deleteFlag) {
		if (deleteFlag == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlag = deleteFlag;
		return this;
	}

	public SubSystemDefQuery deleteFlagGreaterThanOrEqual(Integer deleteFlagGreaterThanOrEqual) {
		if (deleteFlagGreaterThanOrEqual == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlagGreaterThanOrEqual = deleteFlagGreaterThanOrEqual;
		return this;
	}

	public SubSystemDefQuery deleteFlagLessThanOrEqual(Integer deleteFlagLessThanOrEqual) {
		if (deleteFlagLessThanOrEqual == null) {
			throw new RuntimeException("deleteFlag is null");
		}
		this.deleteFlagLessThanOrEqual = deleteFlagLessThanOrEqual;
		return this;
	}

	public SubSystemDefQuery deleteFlags(List<Integer> deleteFlags) {
		if (deleteFlags == null) {
			throw new RuntimeException("deleteFlags is empty ");
		}
		this.deleteFlags = deleteFlags;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("sysId".equals(sortColumn)) {
				orderBy = "E.SYS_ID_" + a_x;
			}

			if ("level".equals(sortColumn)) {
				orderBy = "E.LEVEL_" + a_x;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("desc".equals(sortColumn)) {
				orderBy = "E.DESC_" + a_x;
			}

			if ("parentId_".equals(sortColumn)) {
				orderBy = "E.PARENT_ID_" + a_x;
			}

			if ("treeId".equals(sortColumn)) {
				orderBy = "E.TREE_ID_" + a_x;
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

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETE_FLAG_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("subSysId", "SUB_SYS_ID_");
		addColumn("sysId", "SYS_ID_");
		addColumn("level", "LEVEL_");
		addColumn("code", "CODE_");
		addColumn("name", "NAME_");
		addColumn("desc", "DESC_");
		addColumn("parentId_", "PARENT_ID_");
		addColumn("treeId", "TREE_ID_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
		addColumn("deleteFlag", "DELETE_FLAG_");
	}

}