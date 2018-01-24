package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBaseUIQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String type;
	protected String typeLike;
	protected List<String> types;
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

	public DepBaseUIQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
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

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeLike(String typeLike) {
		this.typeLike = typeLike;
	}

	public void setTypes(List<String> types) {
		this.types = types;
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

	public DepBaseUIQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public DepBaseUIQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public DepBaseUIQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public DepBaseUIQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DepBaseUIQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DepBaseUIQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public DepBaseUIQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DepBaseUIQuery typeLike(String typeLike) {
		if (typeLike == null) {
			throw new RuntimeException("type is null");
		}
		this.typeLike = typeLike;
		return this;
	}

	public DepBaseUIQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public DepBaseUIQuery creator(String creator) {
		if (creator == null) {
			throw new RuntimeException("creator is null");
		}
		this.creator = creator;
		return this;
	}

	public DepBaseUIQuery creatorLike(String creatorLike) {
		if (creatorLike == null) {
			throw new RuntimeException("creator is null");
		}
		this.creatorLike = creatorLike;
		return this;
	}

	public DepBaseUIQuery creators(List<String> creators) {
		if (creators == null) {
			throw new RuntimeException("creators is empty ");
		}
		this.creators = creators;
		return this;
	}

	public DepBaseUIQuery createDateTimeGreaterThanOrEqual(
			Date createDateTimeGreaterThanOrEqual) {
		if (createDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepBaseUIQuery createDateTimeLessThanOrEqual(
			Date createDateTimeLessThanOrEqual) {
		if (createDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
		return this;
	}

	public DepBaseUIQuery modifier(String modifier) {
		if (modifier == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifier = modifier;
		return this;
	}

	public DepBaseUIQuery modifierLike(String modifierLike) {
		if (modifierLike == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifierLike = modifierLike;
		return this;
	}

	public DepBaseUIQuery modifiers(List<String> modifiers) {
		if (modifiers == null) {
			throw new RuntimeException("modifiers is empty ");
		}
		this.modifiers = modifiers;
		return this;
	}

	public DepBaseUIQuery modifyDateTimeGreaterThanOrEqual(
			Date modifyDateTimeGreaterThanOrEqual) {
		if (modifyDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepBaseUIQuery modifyDateTimeLessThanOrEqual(
			Date modifyDateTimeLessThanOrEqual) {
		if (modifyDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
		return this;
	}

	public DepBaseUIQuery delFlag(String delFlag) {
		if (delFlag == null) {
			throw new RuntimeException("delFlag is null");
		}
		this.delFlag = delFlag;
		return this;
	}

	public DepBaseUIQuery delFlagLike(String delFlagLike) {
		if (delFlagLike == null) {
			throw new RuntimeException("delFlag is null");
		}
		this.delFlagLike = delFlagLike;
		return this;
	}

	public DepBaseUIQuery delFlags(List<String> delFlags) {
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

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
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
		addColumn("type", "TYPE_");
		addColumn("creator", "CREATOR_");
		addColumn("createDateTime", "CREATEDATETIME_");
		addColumn("modifier", "MODIFIER_");
		addColumn("modifyDateTime", "MODIFYDATETIME_");
		addColumn("delFlag", "DELFLAG_");
	}

}