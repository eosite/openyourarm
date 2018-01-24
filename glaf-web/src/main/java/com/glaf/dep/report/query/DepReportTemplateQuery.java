package com.glaf.dep.report.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepReportTemplateQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids = new ArrayList<Long>();
	protected Collection<String> appActorIds;
	protected String code;
	protected String codeLike;
	protected List<String> codes;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String tmpJson;
	protected String tmpJsonLike;
	protected List<String> tmpJsons;
	protected String ruleJson;
	protected String ruleJsonLike;
	protected List<String> ruleJsons;
	protected Integer ver;
	protected Integer verGreaterThanOrEqual;
	protected Integer verLessThanOrEqual;
	protected List<Integer> vers;
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

	public DepReportTemplateQuery() {

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

	public String getTmpJson() {
		return tmpJson;
	}

	public String getTmpJsonLike() {
		if (tmpJsonLike != null && tmpJsonLike.trim().length() > 0) {
			if (!tmpJsonLike.startsWith("%")) {
				tmpJsonLike = "%" + tmpJsonLike;
			}
			if (!tmpJsonLike.endsWith("%")) {
				tmpJsonLike = tmpJsonLike + "%";
			}
		}
		return tmpJsonLike;
	}

	public List<String> getTmpJsons() {
		return tmpJsons;
	}

	public String getRuleJson() {
		return ruleJson;
	}

	public String getRuleJsonLike() {
		if (ruleJsonLike != null && ruleJsonLike.trim().length() > 0) {
			if (!ruleJsonLike.startsWith("%")) {
				ruleJsonLike = "%" + ruleJsonLike;
			}
			if (!ruleJsonLike.endsWith("%")) {
				ruleJsonLike = ruleJsonLike + "%";
			}
		}
		return ruleJsonLike;
	}

	public List<String> getRuleJsons() {
		return ruleJsons;
	}

	public Integer getVer() {
		return ver;
	}

	public Integer getVerGreaterThanOrEqual() {
		return verGreaterThanOrEqual;
	}

	public Integer getVerLessThanOrEqual() {
		return verLessThanOrEqual;
	}

	public List<Integer> getVers() {
		return vers;
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

	public void setTmpJson(String tmpJson) {
		this.tmpJson = tmpJson;
	}

	public void setTmpJsonLike(String tmpJsonLike) {
		this.tmpJsonLike = tmpJsonLike;
	}

	public void setTmpJsons(List<String> tmpJsons) {
		this.tmpJsons = tmpJsons;
	}

	public void setRuleJson(String ruleJson) {
		this.ruleJson = ruleJson;
	}

	public void setRuleJsonLike(String ruleJsonLike) {
		this.ruleJsonLike = ruleJsonLike;
	}

	public void setRuleJsons(List<String> ruleJsons) {
		this.ruleJsons = ruleJsons;
	}

	public void setVer(Integer ver) {
		this.ver = ver;
	}

	public void setVerGreaterThanOrEqual(Integer verGreaterThanOrEqual) {
		this.verGreaterThanOrEqual = verGreaterThanOrEqual;
	}

	public void setVerLessThanOrEqual(Integer verLessThanOrEqual) {
		this.verLessThanOrEqual = verLessThanOrEqual;
	}

	public void setVers(List<Integer> vers) {
		this.vers = vers;
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

	public void setCreateDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual) {
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
	}

	public void setCreateDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual) {
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

	public void setModifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual) {
		this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
	}

	public void setModifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual) {
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

	public DepReportTemplateQuery code(String code) {
		if (code == null) {
			throw new RuntimeException("code is null");
		}
		this.code = code;
		return this;
	}

	public DepReportTemplateQuery codeLike(String codeLike) {
		if (codeLike == null) {
			throw new RuntimeException("code is null");
		}
		this.codeLike = codeLike;
		return this;
	}

	public DepReportTemplateQuery codes(List<String> codes) {
		if (codes == null) {
			throw new RuntimeException("codes is empty ");
		}
		this.codes = codes;
		return this;
	}

	public DepReportTemplateQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DepReportTemplateQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DepReportTemplateQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public DepReportTemplateQuery tmpJson(String tmpJson) {
		if (tmpJson == null) {
			throw new RuntimeException("tmpJson is null");
		}
		this.tmpJson = tmpJson;
		return this;
	}

	public DepReportTemplateQuery tmpJsonLike(String tmpJsonLike) {
		if (tmpJsonLike == null) {
			throw new RuntimeException("tmpJson is null");
		}
		this.tmpJsonLike = tmpJsonLike;
		return this;
	}

	public DepReportTemplateQuery tmpJsons(List<String> tmpJsons) {
		if (tmpJsons == null) {
			throw new RuntimeException("tmpJsons is empty ");
		}
		this.tmpJsons = tmpJsons;
		return this;
	}

	public DepReportTemplateQuery ruleJson(String ruleJson) {
		if (ruleJson == null) {
			throw new RuntimeException("ruleJson is null");
		}
		this.ruleJson = ruleJson;
		return this;
	}

	public DepReportTemplateQuery ruleJsonLike(String ruleJsonLike) {
		if (ruleJsonLike == null) {
			throw new RuntimeException("ruleJson is null");
		}
		this.ruleJsonLike = ruleJsonLike;
		return this;
	}

	public DepReportTemplateQuery ruleJsons(List<String> ruleJsons) {
		if (ruleJsons == null) {
			throw new RuntimeException("ruleJsons is empty ");
		}
		this.ruleJsons = ruleJsons;
		return this;
	}

	public DepReportTemplateQuery ver(Integer ver) {
		if (ver == null) {
			throw new RuntimeException("ver is null");
		}
		this.ver = ver;
		return this;
	}

	public DepReportTemplateQuery verGreaterThanOrEqual(Integer verGreaterThanOrEqual) {
		if (verGreaterThanOrEqual == null) {
			throw new RuntimeException("ver is null");
		}
		this.verGreaterThanOrEqual = verGreaterThanOrEqual;
		return this;
	}

	public DepReportTemplateQuery verLessThanOrEqual(Integer verLessThanOrEqual) {
		if (verLessThanOrEqual == null) {
			throw new RuntimeException("ver is null");
		}
		this.verLessThanOrEqual = verLessThanOrEqual;
		return this;
	}

	public DepReportTemplateQuery vers(List<Integer> vers) {
		if (vers == null) {
			throw new RuntimeException("vers is empty ");
		}
		this.vers = vers;
		return this;
	}

	public DepReportTemplateQuery creator(String creator) {
		if (creator == null) {
			throw new RuntimeException("creator is null");
		}
		this.creator = creator;
		return this;
	}

	public DepReportTemplateQuery creatorLike(String creatorLike) {
		if (creatorLike == null) {
			throw new RuntimeException("creator is null");
		}
		this.creatorLike = creatorLike;
		return this;
	}

	public DepReportTemplateQuery creators(List<String> creators) {
		if (creators == null) {
			throw new RuntimeException("creators is empty ");
		}
		this.creators = creators;
		return this;
	}

	public DepReportTemplateQuery createDateTimeGreaterThanOrEqual(Date createDateTimeGreaterThanOrEqual) {
		if (createDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepReportTemplateQuery createDateTimeLessThanOrEqual(Date createDateTimeLessThanOrEqual) {
		if (createDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
		return this;
	}

	public DepReportTemplateQuery modifier(String modifier) {
		if (modifier == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifier = modifier;
		return this;
	}

	public DepReportTemplateQuery modifierLike(String modifierLike) {
		if (modifierLike == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifierLike = modifierLike;
		return this;
	}

	public DepReportTemplateQuery modifiers(List<String> modifiers) {
		if (modifiers == null) {
			throw new RuntimeException("modifiers is empty ");
		}
		this.modifiers = modifiers;
		return this;
	}

	public DepReportTemplateQuery modifyDateTimeGreaterThanOrEqual(Date modifyDateTimeGreaterThanOrEqual) {
		if (modifyDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepReportTemplateQuery modifyDateTimeLessThanOrEqual(Date modifyDateTimeLessThanOrEqual) {
		if (modifyDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
		return this;
	}

	public DepReportTemplateQuery delFlag(String delFlag) {
		if (delFlag == null) {
			throw new RuntimeException("delFlag is null");
		}
		this.delFlag = delFlag;
		return this;
	}

	public DepReportTemplateQuery delFlagLike(String delFlagLike) {
		if (delFlagLike == null) {
			throw new RuntimeException("delFlag is null");
		}
		this.delFlagLike = delFlagLike;
		return this;
	}

	public DepReportTemplateQuery delFlags(List<String> delFlags) {
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

			if ("tmpJson".equals(sortColumn)) {
				orderBy = "E.TMPJSON_" + a_x;
			}

			if ("ruleJson".equals(sortColumn)) {
				orderBy = "E.RULEJSON_" + a_x;
			}

			if ("ver".equals(sortColumn)) {
				orderBy = "E.VER_" + a_x;
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
		addColumn("tmpJson", "TMPJSON_");
		addColumn("ruleJson", "RULEJSON_");
		addColumn("ver", "VER_");
		addColumn("creator", "CREATOR_");
		addColumn("createDateTime", "CREATEDATETIME_");
		addColumn("modifier", "MODIFIER_");
		addColumn("modifyDateTime", "MODIFYDATETIME_");
		addColumn("delFlag", "DELFLAG_");
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public void addId(Long id) {
		this.ids.add(id);
	}

}