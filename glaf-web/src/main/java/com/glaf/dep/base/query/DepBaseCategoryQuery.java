package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBaseCategoryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long id;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String treeId;
	protected String treeIdLike;
	protected List<String> treeIds;
	protected String expandFlag;
	protected String expandFlagLike;
	protected List<String> expandFlags;
	protected Long pid;
	protected Long pidGreaterThanOrEqual;
	protected Long pidLessThanOrEqual;
	protected List<Long> pids;
	protected Integer orderNo;
	protected Integer orderNoGreaterThanOrEqual;
	protected Integer orderNoLessThanOrEqual;
	protected List<Integer> orderNos;
	protected String creator;
	protected String creatorLike;
	protected List<String> creators;
	protected Date createDateTimeGreaterThanOrEqual;
	protected Date createDateTimeLessThanOrEqual;
	protected String modifier;
	protected String modifierLike;
	protected List<String> modifiers;
	protected Date modifyDateTimeGreaterThanOrEqual;
	protected Date modifyDateTimeLessThanOrEqual;
	protected String delFlag;
	protected String delFlagLike;
	protected List<String> delFlags;
	protected String sqlCondition;

	public DepBaseCategoryQuery() {

	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getSqlCondition() {
		return sqlCondition;
	}

	public void setSqlCondition(String sqlCondition) {
		this.sqlCondition = sqlCondition;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTreeId() {
		return treeId;
	}

	public String getTreeIdLike() {
		if (treeIdLike != null && treeIdLike.trim().length() > 0) {
//			if (!treeIdLike.startsWith("%")) {
//				treeIdLike = "%" + treeIdLike;
//			}
			if (!treeIdLike.endsWith("%")) {
				treeIdLike = treeIdLike + "%";
			}
		}
		return treeIdLike;
	}

	public List<String> getTreeIds() {
		return treeIds;
	}

	public String getExpandFlag() {
		return expandFlag;
	}

	public String getExpandFlagLike() {
		if (expandFlagLike != null && expandFlagLike.trim().length() > 0) {
			if (!expandFlagLike.startsWith("%")) {
				expandFlagLike = "%" + expandFlagLike;
			}
			if (!expandFlagLike.endsWith("%")) {
				expandFlagLike = expandFlagLike + "%";
			}
		}
		return expandFlagLike;
	}

	public List<String> getExpandFlags() {
		return expandFlags;
	}

	public Long getPid() {
		return pid;
	}

	public Long getPidGreaterThanOrEqual() {
		return pidGreaterThanOrEqual;
	}

	public Long getPidLessThanOrEqual() {
		return pidLessThanOrEqual;
	}

	public List<Long> getPids() {
		return pids;
	}

	public Integer getOrderNo() {
		return orderNo;
	}

	public Integer getOrderNoGreaterThanOrEqual() {
		return orderNoGreaterThanOrEqual;
	}

	public Integer getOrderNoLessThanOrEqual() {
		return orderNoLessThanOrEqual;
	}

	public List<Integer> getOrderNos() {
		return orderNos;
	}

	public String getCreator() {
		return creator;
	}

	public String getCreatorLike() {
		if (creatorLike != null && creatorLike.trim().length() > 0) {
			if (!creatorLike.startsWith("%")) {
				creatorLike = "%" + creatorLike;
			}
			if (!creatorLike.endsWith("%")) {
				creatorLike = creatorLike + "%";
			}
		}
		return creatorLike;
	}

	public List<String> getCreators() {
		return creators;
	}

	public Date getCreateDateTimeGreaterThanOrEqual() {
		return createDateTimeGreaterThanOrEqual;
	}

	public Date getCreateDateTimeLessThanOrEqual() {
		return createDateTimeLessThanOrEqual;
	}

	public String getModifier() {
		return modifier;
	}

	public String getModifierLike() {
		if (modifierLike != null && modifierLike.trim().length() > 0) {
			if (!modifierLike.startsWith("%")) {
				modifierLike = "%" + modifierLike;
			}
			if (!modifierLike.endsWith("%")) {
				modifierLike = modifierLike + "%";
			}
		}
		return modifierLike;
	}

	public List<String> getModifiers() {
		return modifiers;
	}

	public Date getModifyDateTimeGreaterThanOrEqual() {
		return modifyDateTimeGreaterThanOrEqual;
	}

	public Date getModifyDateTimeLessThanOrEqual() {
		return modifyDateTimeLessThanOrEqual;
	}

	public String getDelFlag() {
		return delFlag;
	}

	public String getDelFlagLike() {
		if (delFlagLike != null && delFlagLike.trim().length() > 0) {
			if (!delFlagLike.startsWith("%")) {
				delFlagLike = "%" + delFlagLike;
			}
			if (!delFlagLike.endsWith("%")) {
				delFlagLike = delFlagLike + "%";
			}
		}
		return delFlagLike;
	}

	public List<String> getDelFlags() {
		return delFlags;
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

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public void setTreeIdLike(String treeIdLike) {
		this.treeIdLike = treeIdLike;
	}

	public void setTreeIds(List<String> treeIds) {
		this.treeIds = treeIds;
	}

	public void setExpandFlag(String expandFlag) {
		this.expandFlag = expandFlag;
	}

	public void setExpandFlagLike(String expandFlagLike) {
		this.expandFlagLike = expandFlagLike;
	}

	public void setExpandFlags(List<String> expandFlags) {
		this.expandFlags = expandFlags;
	}

	public void setPid(Long pid) {
		this.pid = pid;
	}

	public void setPidGreaterThanOrEqual(Long pidGreaterThanOrEqual) {
		this.pidGreaterThanOrEqual = pidGreaterThanOrEqual;
	}

	public void setPidLessThanOrEqual(Long pidLessThanOrEqual) {
		this.pidLessThanOrEqual = pidLessThanOrEqual;
	}

	public void setPids(List<Long> pids) {
		this.pids = pids;
	}

	public void setOrderNo(Integer orderNo) {
		this.orderNo = orderNo;
	}

	public void setOrderNoGreaterThanOrEqual(Integer orderNoGreaterThanOrEqual) {
		this.orderNoGreaterThanOrEqual = orderNoGreaterThanOrEqual;
	}

	public void setOrderNoLessThanOrEqual(Integer orderNoLessThanOrEqual) {
		this.orderNoLessThanOrEqual = orderNoLessThanOrEqual;
	}

	public void setOrderNos(List<Integer> orderNos) {
		this.orderNos = orderNos;
	}

	public void setCreator(String creator) {
		this.creator = creator;
	}

	public void setCreatorLike(String creatorLike) {
		this.creatorLike = creatorLike;
	}

	public void setCreators(List<String> creators) {
		this.creators = creators;
	}

	public void setCreateDateTimeGreaterThanOrEqual(
			Date createDateTimeGreaterThanOrEqual) {
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
	}

	public void setCreateDateTimeLessThanOrEqual(
			Date createDateTimeLessThanOrEqual) {
		this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
	}

	public void setModifier(String modifier) {
		this.modifier = modifier;
	}

	public void setModifierLike(String modifierLike) {
		this.modifierLike = modifierLike;
	}

	public void setModifiers(List<String> modifiers) {
		this.modifiers = modifiers;
	}

	public void setModifyDateTimeGreaterThanOrEqual(
			Date modifyDateTimeGreaterThanOrEqual) {
		this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
	}

	public void setModifyDateTimeLessThanOrEqual(
			Date modifyDateTimeLessThanOrEqual) {
		this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
	}

	public void setDelFlag(String delFlag) {
		this.delFlag = delFlag;
	}

	public void setDelFlagLike(String delFlagLike) {
		this.delFlagLike = delFlagLike;
	}

	public void setDelFlags(List<String> delFlags) {
		this.delFlags = delFlags;
	}

	public DepBaseCategoryQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public DepBaseCategoryQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public DepBaseCategoryQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public DepBaseCategoryQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DepBaseCategoryQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DepBaseCategoryQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public DepBaseCategoryQuery treeId(String treeId) {
		if (treeId == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeId = treeId;
		return this;
	}

	public DepBaseCategoryQuery treeIdLike(String treeIdLike) {
		if (treeIdLike == null) {
			throw new RuntimeException("treeId is null");
		}
		this.treeIdLike = treeIdLike;
		return this;
	}

	public DepBaseCategoryQuery treeIds(List<String> treeIds) {
		if (treeIds == null) {
			throw new RuntimeException("treeIds is empty ");
		}
		this.treeIds = treeIds;
		return this;
	}

	public DepBaseCategoryQuery expandFlag(String expandFlag) {
		if (expandFlag == null) {
			throw new RuntimeException("expandFlag is null");
		}
		this.expandFlag = expandFlag;
		return this;
	}

	public DepBaseCategoryQuery expandFlagLike(String expandFlagLike) {
		if (expandFlagLike == null) {
			throw new RuntimeException("expandFlag is null");
		}
		this.expandFlagLike = expandFlagLike;
		return this;
	}

	public DepBaseCategoryQuery expandFlags(List<String> expandFlags) {
		if (expandFlags == null) {
			throw new RuntimeException("expandFlags is empty ");
		}
		this.expandFlags = expandFlags;
		return this;
	}

	public DepBaseCategoryQuery pid(Long pid) {
		if (pid == null) {
			throw new RuntimeException("pid is null");
		}
		this.pid = pid;
		return this;
	}

	public DepBaseCategoryQuery pidGreaterThanOrEqual(Long pidGreaterThanOrEqual) {
		if (pidGreaterThanOrEqual == null) {
			throw new RuntimeException("pid is null");
		}
		this.pidGreaterThanOrEqual = pidGreaterThanOrEqual;
		return this;
	}

	public DepBaseCategoryQuery pidLessThanOrEqual(Long pidLessThanOrEqual) {
		if (pidLessThanOrEqual == null) {
			throw new RuntimeException("pid is null");
		}
		this.pidLessThanOrEqual = pidLessThanOrEqual;
		return this;
	}

	public DepBaseCategoryQuery pids(List<Long> pids) {
		if (pids == null) {
			throw new RuntimeException("pids is empty ");
		}
		this.pids = pids;
		return this;
	}

	public DepBaseCategoryQuery orderNo(Integer orderNo) {
		if (orderNo == null) {
			throw new RuntimeException("orderNo is null");
		}
		this.orderNo = orderNo;
		return this;
	}

	public DepBaseCategoryQuery orderNoGreaterThanOrEqual(
			Integer orderNoGreaterThanOrEqual) {
		if (orderNoGreaterThanOrEqual == null) {
			throw new RuntimeException("orderNo is null");
		}
		this.orderNoGreaterThanOrEqual = orderNoGreaterThanOrEqual;
		return this;
	}

	public DepBaseCategoryQuery orderNoLessThanOrEqual(
			Integer orderNoLessThanOrEqual) {
		if (orderNoLessThanOrEqual == null) {
			throw new RuntimeException("orderNo is null");
		}
		this.orderNoLessThanOrEqual = orderNoLessThanOrEqual;
		return this;
	}

	public DepBaseCategoryQuery orderNos(List<Integer> orderNos) {
		if (orderNos == null) {
			throw new RuntimeException("orderNos is empty ");
		}
		this.orderNos = orderNos;
		return this;
	}

	public DepBaseCategoryQuery creator(String creator) {
		if (creator == null) {
			throw new RuntimeException("creator is null");
		}
		this.creator = creator;
		return this;
	}

	public DepBaseCategoryQuery creatorLike(String creatorLike) {
		if (creatorLike == null) {
			throw new RuntimeException("creator is null");
		}
		this.creatorLike = creatorLike;
		return this;
	}

	public DepBaseCategoryQuery creators(List<String> creators) {
		if (creators == null) {
			throw new RuntimeException("creators is empty ");
		}
		this.creators = creators;
		return this;
	}

	public DepBaseCategoryQuery createDateTimeGreaterThanOrEqual(
			Date createDateTimeGreaterThanOrEqual) {
		if (createDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepBaseCategoryQuery createDateTimeLessThanOrEqual(
			Date createDateTimeLessThanOrEqual) {
		if (createDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
		return this;
	}

	public DepBaseCategoryQuery modifier(String modifier) {
		if (modifier == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifier = modifier;
		return this;
	}

	public DepBaseCategoryQuery modifierLike(String modifierLike) {
		if (modifierLike == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifierLike = modifierLike;
		return this;
	}

	public DepBaseCategoryQuery modifiers(List<String> modifiers) {
		if (modifiers == null) {
			throw new RuntimeException("modifiers is empty ");
		}
		this.modifiers = modifiers;
		return this;
	}

	public DepBaseCategoryQuery modifyDateTimeGreaterThanOrEqual(
			Date modifyDateTimeGreaterThanOrEqual) {
		if (modifyDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepBaseCategoryQuery modifyDateTimeLessThanOrEqual(
			Date modifyDateTimeLessThanOrEqual) {
		if (modifyDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
		return this;
	}

	public DepBaseCategoryQuery delFlag(String delFlag) {
		if (delFlag == null) {
			throw new RuntimeException("delFlag is null");
		}
		this.delFlag = delFlag;
		return this;
	}

	public DepBaseCategoryQuery delFlagLike(String delFlagLike) {
		if (delFlagLike == null) {
			throw new RuntimeException("delFlag is null");
		}
		this.delFlagLike = delFlagLike;
		return this;
	}

	public DepBaseCategoryQuery delFlags(List<String> delFlags) {
		if (delFlags == null) {
			throw new RuntimeException("delFlags is empty ");
		}
		this.delFlags = delFlags;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("code".equals(sortColumn)) {
				orderBy = "E.CODE_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("treeId".equals(sortColumn)) {
				orderBy = "E.TREEID_" + a_x;
			}

			if ("expandFlag".equals(sortColumn)) {
				orderBy = "E.EXPANDFLAG_" + a_x;
			}

			if ("pid".equals(sortColumn)) {
				orderBy = "E.PID_" + a_x;
			}

			if ("orderNo".equals(sortColumn)) {
				orderBy = "E.ORDERNO_" + a_x;
			}

			if ("creator".equals(sortColumn)) {
				orderBy = "E.CREATOR_" + a_x;
			}

			if ("createDateTime".equals(sortColumn)) {
				orderBy = "E.CREATEDATETIME_" + a_x;
			}

			if ("modifier".equals(sortColumn)) {
				orderBy = "E.MODIFIER_" + a_x;
			}

			if ("modifyDateTime".equals(sortColumn)) {
				orderBy = "E.MODIFYDATETIME_" + a_x;
			}

			if ("delFlag".equals(sortColumn)) {
				orderBy = "E.DELFLAG_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("code", "CODE_");
		addColumn("name", "NAME_");
		addColumn("treeId", "TREEID_");
		addColumn("expandFlag", "EXPANDFLAG_");
		addColumn("pid", "PID_");
		addColumn("orderNo", "ORDERNO_");
		addColumn("creator", "CREATOR_");
		addColumn("createDateTime", "CREATEDATETIME_");
		addColumn("modifier", "MODIFIER_");
		addColumn("modifyDateTime", "MODIFYDATETIME_");
		addColumn("delFlag", "DELFLAG_");
	}

}