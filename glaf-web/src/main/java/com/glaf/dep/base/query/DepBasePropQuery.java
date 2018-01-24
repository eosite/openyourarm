package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBasePropQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ruleIds;
	protected Collection<String> appActorIds;
	protected String ruleCode;
	protected String ruleCodeLike;
	protected List<String> ruleCodes;
	protected String ruleName;
	protected String ruleNameLike;
	protected List<String> ruleNames;
	protected String ruleDesc;
	protected String ruleDescLike;
	protected List<String> ruleDescs;
	protected String sysCategory;
	protected String sysCategoryLike;
	protected List<String> sysCategorys;
	protected String useCategory;
	protected String useCategoryLike;
	protected List<String> useCategorys;
	protected String openFlag;
	protected String openFlagLike;
	protected List<String> openFlags;
	protected Integer orderNo;
	protected Integer orderNoGreaterThanOrEqual;
	protected Integer orderNoLessThanOrEqual;
	protected List<Integer> orderNos;
	protected String readOnly;
	protected String readOnlyLike;
	protected List<String> readOnlys;
	protected String repeatFlag;
	protected String repeatFlagLike;
	protected List<String> repeatFlags;
	protected String notNull;
	protected String notNullLike;
	protected List<String> notNulls;
	protected String inputType;
	protected String inputTypeLike;
	protected List<String> inputTypes;
	protected String defaultVal;
	protected String defaultValLike;
	protected List<String> defaultVals;
	protected String extJson;
	protected String extJsonLike;
	protected List<String> extJsons;
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

	public DepBasePropQuery() {

	}

	public List<String> getRuleIds() {
		return ruleIds;
	}

	public void setRuleIds(List<String> ruleIds) {
		this.ruleIds = ruleIds;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getRuleCode() {
		return ruleCode;
	}

	public String getRuleCodeLike() {
		if (ruleCodeLike != null && ruleCodeLike.trim().length() > 0) {
			if (!ruleCodeLike.startsWith("%")) {
				ruleCodeLike = "%" + ruleCodeLike;
			}
			if (!ruleCodeLike.endsWith("%")) {
				ruleCodeLike = ruleCodeLike + "%";
			}
		}
		return ruleCodeLike;
	}

	public List<String> getRuleCodes() {
		return ruleCodes;
	}

	public String getRuleName() {
		return ruleName;
	}

	public String getRuleNameLike() {
		if (ruleNameLike != null && ruleNameLike.trim().length() > 0) {
			if (!ruleNameLike.startsWith("%")) {
				ruleNameLike = "%" + ruleNameLike;
			}
			if (!ruleNameLike.endsWith("%")) {
				ruleNameLike = ruleNameLike + "%";
			}
		}
		return ruleNameLike;
	}

	public List<String> getRuleNames() {
		return ruleNames;
	}

	public String getRuleDesc() {
		return ruleDesc;
	}

	public String getRuleDescLike() {
		if (ruleDescLike != null && ruleDescLike.trim().length() > 0) {
			if (!ruleDescLike.startsWith("%")) {
				ruleDescLike = "%" + ruleDescLike;
			}
			if (!ruleDescLike.endsWith("%")) {
				ruleDescLike = ruleDescLike + "%";
			}
		}
		return ruleDescLike;
	}

	public List<String> getRuleDescs() {
		return ruleDescs;
	}

	public String getSysCategory() {
		return sysCategory;
	}

	public String getSysCategoryLike() {
		if (sysCategoryLike != null && sysCategoryLike.trim().length() > 0) {
			if (!sysCategoryLike.startsWith("%")) {
				sysCategoryLike = "%" + sysCategoryLike;
			}
			if (!sysCategoryLike.endsWith("%")) {
				sysCategoryLike = sysCategoryLike + "%";
			}
		}
		return sysCategoryLike;
	}

	public List<String> getSysCategorys() {
		return sysCategorys;
	}

	public String getUseCategory() {
		return useCategory;
	}

	public String getUseCategoryLike() {
		if (useCategoryLike != null && useCategoryLike.trim().length() > 0) {
			if (!useCategoryLike.startsWith("%")) {
				useCategoryLike = "%" + useCategoryLike;
			}
			if (!useCategoryLike.endsWith("%")) {
				useCategoryLike = useCategoryLike + "%";
			}
		}
		return useCategoryLike;
	}

	public List<String> getUseCategorys() {
		return useCategorys;
	}

	public String getOpenFlag() {
		return openFlag;
	}

	public String getOpenFlagLike() {
		if (openFlagLike != null && openFlagLike.trim().length() > 0) {
			if (!openFlagLike.startsWith("%")) {
				openFlagLike = "%" + openFlagLike;
			}
			if (!openFlagLike.endsWith("%")) {
				openFlagLike = openFlagLike + "%";
			}
		}
		return openFlagLike;
	}

	public List<String> getOpenFlags() {
		return openFlags;
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

	public String getReadOnly() {
		return readOnly;
	}

	public String getReadOnlyLike() {
		if (readOnlyLike != null && readOnlyLike.trim().length() > 0) {
			if (!readOnlyLike.startsWith("%")) {
				readOnlyLike = "%" + readOnlyLike;
			}
			if (!readOnlyLike.endsWith("%")) {
				readOnlyLike = readOnlyLike + "%";
			}
		}
		return readOnlyLike;
	}

	public List<String> getReadOnlys() {
		return readOnlys;
	}

	public String getRepeatFlag() {
		return repeatFlag;
	}

	public String getRepeatFlagLike() {
		if (repeatFlagLike != null && repeatFlagLike.trim().length() > 0) {
			if (!repeatFlagLike.startsWith("%")) {
				repeatFlagLike = "%" + repeatFlagLike;
			}
			if (!repeatFlagLike.endsWith("%")) {
				repeatFlagLike = repeatFlagLike + "%";
			}
		}
		return repeatFlagLike;
	}

	public List<String> getRepeatFlags() {
		return repeatFlags;
	}

	public String getNotNull() {
		return notNull;
	}

	public String getNotNullLike() {
		if (notNullLike != null && notNullLike.trim().length() > 0) {
			if (!notNullLike.startsWith("%")) {
				notNullLike = "%" + notNullLike;
			}
			if (!notNullLike.endsWith("%")) {
				notNullLike = notNullLike + "%";
			}
		}
		return notNullLike;
	}

	public List<String> getNotNulls() {
		return notNulls;
	}

	public String getInputType() {
		return inputType;
	}

	public String getInputTypeLike() {
		if (inputTypeLike != null && inputTypeLike.trim().length() > 0) {
			if (!inputTypeLike.startsWith("%")) {
				inputTypeLike = "%" + inputTypeLike;
			}
			if (!inputTypeLike.endsWith("%")) {
				inputTypeLike = inputTypeLike + "%";
			}
		}
		return inputTypeLike;
	}

	public List<String> getInputTypes() {
		return inputTypes;
	}

	public String getDefaultVal() {
		return defaultVal;
	}

	public String getDefaultValLike() {
		if (defaultValLike != null && defaultValLike.trim().length() > 0) {
			if (!defaultValLike.startsWith("%")) {
				defaultValLike = "%" + defaultValLike;
			}
			if (!defaultValLike.endsWith("%")) {
				defaultValLike = defaultValLike + "%";
			}
		}
		return defaultValLike;
	}

	public List<String> getDefaultVals() {
		return defaultVals;
	}

	public String getExtJson() {
		return extJson;
	}

	public String getExtJsonLike() {
		if (extJsonLike != null && extJsonLike.trim().length() > 0) {
			if (!extJsonLike.startsWith("%")) {
				extJsonLike = "%" + extJsonLike;
			}
			if (!extJsonLike.endsWith("%")) {
				extJsonLike = extJsonLike + "%";
			}
		}
		return extJsonLike;
	}

	public List<String> getExtJsons() {
		return extJsons;
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

	public void setRuleCode(String ruleCode) {
		this.ruleCode = ruleCode;
	}

	public void setRuleCodeLike(String ruleCodeLike) {
		this.ruleCodeLike = ruleCodeLike;
	}

	public void setRuleCodes(List<String> ruleCodes) {
		this.ruleCodes = ruleCodes;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public void setRuleNameLike(String ruleNameLike) {
		this.ruleNameLike = ruleNameLike;
	}

	public void setRuleNames(List<String> ruleNames) {
		this.ruleNames = ruleNames;
	}

	public void setRuleDesc(String ruleDesc) {
		this.ruleDesc = ruleDesc;
	}

	public void setRuleDescLike(String ruleDescLike) {
		this.ruleDescLike = ruleDescLike;
	}

	public void setRuleDescs(List<String> ruleDescs) {
		this.ruleDescs = ruleDescs;
	}

	public void setSysCategory(String sysCategory) {
		this.sysCategory = sysCategory;
	}

	public void setSysCategoryLike(String sysCategoryLike) {
		this.sysCategoryLike = sysCategoryLike;
	}

	public void setSysCategorys(List<String> sysCategorys) {
		this.sysCategorys = sysCategorys;
	}

	public void setUseCategory(String useCategory) {
		this.useCategory = useCategory;
	}

	public void setUseCategoryLike(String useCategoryLike) {
		this.useCategoryLike = useCategoryLike;
	}

	public void setUseCategorys(List<String> useCategorys) {
		this.useCategorys = useCategorys;
	}

	public void setOpenFlag(String openFlag) {
		this.openFlag = openFlag;
	}

	public void setOpenFlagLike(String openFlagLike) {
		this.openFlagLike = openFlagLike;
	}

	public void setOpenFlags(List<String> openFlags) {
		this.openFlags = openFlags;
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

	public void setReadOnly(String readOnly) {
		this.readOnly = readOnly;
	}

	public void setReadOnlyLike(String readOnlyLike) {
		this.readOnlyLike = readOnlyLike;
	}

	public void setReadOnlys(List<String> readOnlys) {
		this.readOnlys = readOnlys;
	}

	public void setRepeatFlag(String repeatFlag) {
		this.repeatFlag = repeatFlag;
	}

	public void setRepeatFlagLike(String repeatFlagLike) {
		this.repeatFlagLike = repeatFlagLike;
	}

	public void setRepeatFlags(List<String> repeatFlags) {
		this.repeatFlags = repeatFlags;
	}

	public void setNotNull(String notNull) {
		this.notNull = notNull;
	}

	public void setNotNullLike(String notNullLike) {
		this.notNullLike = notNullLike;
	}

	public void setNotNulls(List<String> notNulls) {
		this.notNulls = notNulls;
	}

	public void setInputType(String inputType) {
		this.inputType = inputType;
	}

	public void setInputTypeLike(String inputTypeLike) {
		this.inputTypeLike = inputTypeLike;
	}

	public void setInputTypes(List<String> inputTypes) {
		this.inputTypes = inputTypes;
	}

	public void setDefaultVal(String defaultVal) {
		this.defaultVal = defaultVal;
	}

	public void setDefaultValLike(String defaultValLike) {
		this.defaultValLike = defaultValLike;
	}

	public void setDefaultVals(List<String> defaultVals) {
		this.defaultVals = defaultVals;
	}

	public void setExtJson(String extJson) {
		this.extJson = extJson;
	}

	public void setExtJsonLike(String extJsonLike) {
		this.extJsonLike = extJsonLike;
	}

	public void setExtJsons(List<String> extJsons) {
		this.extJsons = extJsons;
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

	public DepBasePropQuery ruleCode(String ruleCode) {
		if (ruleCode == null) {
			throw new RuntimeException("ruleCode is null");
		}
		this.ruleCode = ruleCode;
		return this;
	}

	public DepBasePropQuery ruleCodeLike(String ruleCodeLike) {
		if (ruleCodeLike == null) {
			throw new RuntimeException("ruleCode is null");
		}
		this.ruleCodeLike = ruleCodeLike;
		return this;
	}

	public DepBasePropQuery ruleCodes(List<String> ruleCodes) {
		if (ruleCodes == null) {
			throw new RuntimeException("ruleCodes is empty ");
		}
		this.ruleCodes = ruleCodes;
		return this;
	}

	public DepBasePropQuery ruleName(String ruleName) {
		if (ruleName == null) {
			throw new RuntimeException("ruleName is null");
		}
		this.ruleName = ruleName;
		return this;
	}

	public DepBasePropQuery ruleNameLike(String ruleNameLike) {
		if (ruleNameLike == null) {
			throw new RuntimeException("ruleName is null");
		}
		this.ruleNameLike = ruleNameLike;
		return this;
	}

	public DepBasePropQuery ruleNames(List<String> ruleNames) {
		if (ruleNames == null) {
			throw new RuntimeException("ruleNames is empty ");
		}
		this.ruleNames = ruleNames;
		return this;
	}

	public DepBasePropQuery ruleDesc(String ruleDesc) {
		if (ruleDesc == null) {
			throw new RuntimeException("ruleDesc is null");
		}
		this.ruleDesc = ruleDesc;
		return this;
	}

	public DepBasePropQuery ruleDescLike(String ruleDescLike) {
		if (ruleDescLike == null) {
			throw new RuntimeException("ruleDesc is null");
		}
		this.ruleDescLike = ruleDescLike;
		return this;
	}

	public DepBasePropQuery ruleDescs(List<String> ruleDescs) {
		if (ruleDescs == null) {
			throw new RuntimeException("ruleDescs is empty ");
		}
		this.ruleDescs = ruleDescs;
		return this;
	}

	public DepBasePropQuery sysCategory(String sysCategory) {
		if (sysCategory == null) {
			throw new RuntimeException("sysCategory is null");
		}
		this.sysCategory = sysCategory;
		return this;
	}

	public DepBasePropQuery sysCategoryLike(String sysCategoryLike) {
		if (sysCategoryLike == null) {
			throw new RuntimeException("sysCategory is null");
		}
		this.sysCategoryLike = sysCategoryLike;
		return this;
	}

	public DepBasePropQuery sysCategorys(List<String> sysCategorys) {
		if (sysCategorys == null) {
			throw new RuntimeException("sysCategorys is empty ");
		}
		this.sysCategorys = sysCategorys;
		return this;
	}

	public DepBasePropQuery useCategory(String useCategory) {
		if (useCategory == null) {
			throw new RuntimeException("useCategory is null");
		}
		this.useCategory = useCategory;
		return this;
	}

	public DepBasePropQuery useCategoryLike(String useCategoryLike) {
		if (useCategoryLike == null) {
			throw new RuntimeException("useCategory is null");
		}
		this.useCategoryLike = useCategoryLike;
		return this;
	}

	public DepBasePropQuery useCategorys(List<String> useCategorys) {
		if (useCategorys == null) {
			throw new RuntimeException("useCategorys is empty ");
		}
		this.useCategorys = useCategorys;
		return this;
	}

	public DepBasePropQuery openFlag(String openFlag) {
		if (openFlag == null) {
			throw new RuntimeException("openFlag is null");
		}
		this.openFlag = openFlag;
		return this;
	}

	public DepBasePropQuery openFlagLike(String openFlagLike) {
		if (openFlagLike == null) {
			throw new RuntimeException("openFlag is null");
		}
		this.openFlagLike = openFlagLike;
		return this;
	}

	public DepBasePropQuery openFlags(List<String> openFlags) {
		if (openFlags == null) {
			throw new RuntimeException("openFlags is empty ");
		}
		this.openFlags = openFlags;
		return this;
	}

	public DepBasePropQuery orderNo(Integer orderNo) {
		if (orderNo == null) {
			throw new RuntimeException("orderNo is null");
		}
		this.orderNo = orderNo;
		return this;
	}

	public DepBasePropQuery orderNoGreaterThanOrEqual(
			Integer orderNoGreaterThanOrEqual) {
		if (orderNoGreaterThanOrEqual == null) {
			throw new RuntimeException("orderNo is null");
		}
		this.orderNoGreaterThanOrEqual = orderNoGreaterThanOrEqual;
		return this;
	}

	public DepBasePropQuery orderNoLessThanOrEqual(
			Integer orderNoLessThanOrEqual) {
		if (orderNoLessThanOrEqual == null) {
			throw new RuntimeException("orderNo is null");
		}
		this.orderNoLessThanOrEqual = orderNoLessThanOrEqual;
		return this;
	}

	public DepBasePropQuery orderNos(List<Integer> orderNos) {
		if (orderNos == null) {
			throw new RuntimeException("orderNos is empty ");
		}
		this.orderNos = orderNos;
		return this;
	}

	public DepBasePropQuery readOnly(String readOnly) {
		if (readOnly == null) {
			throw new RuntimeException("readOnly is null");
		}
		this.readOnly = readOnly;
		return this;
	}

	public DepBasePropQuery readOnlyLike(String readOnlyLike) {
		if (readOnlyLike == null) {
			throw new RuntimeException("readOnly is null");
		}
		this.readOnlyLike = readOnlyLike;
		return this;
	}

	public DepBasePropQuery readOnlys(List<String> readOnlys) {
		if (readOnlys == null) {
			throw new RuntimeException("readOnlys is empty ");
		}
		this.readOnlys = readOnlys;
		return this;
	}

	public DepBasePropQuery repeatFlag(String repeatFlag) {
		if (repeatFlag == null) {
			throw new RuntimeException("repeatFlag is null");
		}
		this.repeatFlag = repeatFlag;
		return this;
	}

	public DepBasePropQuery repeatFlagLike(String repeatFlagLike) {
		if (repeatFlagLike == null) {
			throw new RuntimeException("repeatFlag is null");
		}
		this.repeatFlagLike = repeatFlagLike;
		return this;
	}

	public DepBasePropQuery repeatFlags(List<String> repeatFlags) {
		if (repeatFlags == null) {
			throw new RuntimeException("repeatFlags is empty ");
		}
		this.repeatFlags = repeatFlags;
		return this;
	}

	public DepBasePropQuery notNull(String notNull) {
		if (notNull == null) {
			throw new RuntimeException("notNull is null");
		}
		this.notNull = notNull;
		return this;
	}

	public DepBasePropQuery notNullLike(String notNullLike) {
		if (notNullLike == null) {
			throw new RuntimeException("notNull is null");
		}
		this.notNullLike = notNullLike;
		return this;
	}

	public DepBasePropQuery notNulls(List<String> notNulls) {
		if (notNulls == null) {
			throw new RuntimeException("notNulls is empty ");
		}
		this.notNulls = notNulls;
		return this;
	}

	public DepBasePropQuery inputType(String inputType) {
		if (inputType == null) {
			throw new RuntimeException("inputType is null");
		}
		this.inputType = inputType;
		return this;
	}

	public DepBasePropQuery inputTypeLike(String inputTypeLike) {
		if (inputTypeLike == null) {
			throw new RuntimeException("inputType is null");
		}
		this.inputTypeLike = inputTypeLike;
		return this;
	}

	public DepBasePropQuery inputTypes(List<String> inputTypes) {
		if (inputTypes == null) {
			throw new RuntimeException("inputTypes is empty ");
		}
		this.inputTypes = inputTypes;
		return this;
	}

	public DepBasePropQuery defaultVal(String defaultVal) {
		if (defaultVal == null) {
			throw new RuntimeException("defaultVal is null");
		}
		this.defaultVal = defaultVal;
		return this;
	}

	public DepBasePropQuery defaultValLike(String defaultValLike) {
		if (defaultValLike == null) {
			throw new RuntimeException("defaultVal is null");
		}
		this.defaultValLike = defaultValLike;
		return this;
	}

	public DepBasePropQuery defaultVals(List<String> defaultVals) {
		if (defaultVals == null) {
			throw new RuntimeException("defaultVals is empty ");
		}
		this.defaultVals = defaultVals;
		return this;
	}

	public DepBasePropQuery extJson(String extJson) {
		if (extJson == null) {
			throw new RuntimeException("extJson is null");
		}
		this.extJson = extJson;
		return this;
	}

	public DepBasePropQuery extJsonLike(String extJsonLike) {
		if (extJsonLike == null) {
			throw new RuntimeException("extJson is null");
		}
		this.extJsonLike = extJsonLike;
		return this;
	}

	public DepBasePropQuery extJsons(List<String> extJsons) {
		if (extJsons == null) {
			throw new RuntimeException("extJsons is empty ");
		}
		this.extJsons = extJsons;
		return this;
	}

	public DepBasePropQuery creator(String creator) {
		if (creator == null) {
			throw new RuntimeException("creator is null");
		}
		this.creator = creator;
		return this;
	}

	public DepBasePropQuery creatorLike(String creatorLike) {
		if (creatorLike == null) {
			throw new RuntimeException("creator is null");
		}
		this.creatorLike = creatorLike;
		return this;
	}

	public DepBasePropQuery creators(List<String> creators) {
		if (creators == null) {
			throw new RuntimeException("creators is empty ");
		}
		this.creators = creators;
		return this;
	}

	public DepBasePropQuery createDateTimeGreaterThanOrEqual(
			Date createDateTimeGreaterThanOrEqual) {
		if (createDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepBasePropQuery createDateTimeLessThanOrEqual(
			Date createDateTimeLessThanOrEqual) {
		if (createDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
		return this;
	}

	public DepBasePropQuery modifier(String modifier) {
		if (modifier == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifier = modifier;
		return this;
	}

	public DepBasePropQuery modifierLike(String modifierLike) {
		if (modifierLike == null) {
			throw new RuntimeException("modifier is null");
		}
		this.modifierLike = modifierLike;
		return this;
	}

	public DepBasePropQuery modifiers(List<String> modifiers) {
		if (modifiers == null) {
			throw new RuntimeException("modifiers is empty ");
		}
		this.modifiers = modifiers;
		return this;
	}

	public DepBasePropQuery modifyDateTimeGreaterThanOrEqual(
			Date modifyDateTimeGreaterThanOrEqual) {
		if (modifyDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeGreaterThanOrEqual = modifyDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepBasePropQuery modifyDateTimeLessThanOrEqual(
			Date modifyDateTimeLessThanOrEqual) {
		if (modifyDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("modifyDateTime is null");
		}
		this.modifyDateTimeLessThanOrEqual = modifyDateTimeLessThanOrEqual;
		return this;
	}

	public DepBasePropQuery delFlag(String delFlag) {
		if (delFlag == null) {
			throw new RuntimeException("delFlag is null");
		}
		this.delFlag = delFlag;
		return this;
	}

	public DepBasePropQuery delFlagLike(String delFlagLike) {
		if (delFlagLike == null) {
			throw new RuntimeException("delFlag is null");
		}
		this.delFlagLike = delFlagLike;
		return this;
	}

	public DepBasePropQuery delFlags(List<String> delFlags) {
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

			if ("ruleCode".equals(sortColumn)) {
				orderBy = "E.RULECODE" + a_x;
			}

			if ("ruleName".equals(sortColumn)) {
				orderBy = "E.RULENAME_" + a_x;
			}

			if ("ruleDesc".equals(sortColumn)) {
				orderBy = "E.RULEDESC" + a_x;
			}

			if ("sysCategory".equals(sortColumn)) {
				orderBy = "E.SYSCATEGORY_" + a_x;
			}

			if ("useCategory".equals(sortColumn)) {
				orderBy = "E.USECATEGORY_" + a_x;
			}

			if ("openFlag".equals(sortColumn)) {
				orderBy = "E.OPENFLAG_" + a_x;
			}

			if ("orderNo".equals(sortColumn)) {
				orderBy = "E.ORDERNO_" + a_x;
			}

			if ("readOnly".equals(sortColumn)) {
				orderBy = "E.READONLY_" + a_x;
			}

			if ("repeatFlag".equals(sortColumn)) {
				orderBy = "E.REPEATFLAG_" + a_x;
			}

			if ("notNull".equals(sortColumn)) {
				orderBy = "E.NOTNULL_" + a_x;
			}

			if ("inputType".equals(sortColumn)) {
				orderBy = "E.INPUTTYPE_" + a_x;
			}

			if ("defaultVal".equals(sortColumn)) {
				orderBy = "E.DEFAULTVAL_" + a_x;
			}

			if ("extJson".equals(sortColumn)) {
				orderBy = "E.EXTJSON_" + a_x;
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
		addColumn("ruleId", "RULEID_");
		addColumn("ruleCode", "RULECODE");
		addColumn("ruleName", "RULENAME_");
		addColumn("ruleDesc", "RULEDESC");
		addColumn("sysCategory", "SYSCATEGORY_");
		addColumn("useCategory", "USECATEGORY_");
		addColumn("openFlag", "OPENFLAG_");
		addColumn("orderNo", "ORDERNO_");
		addColumn("readOnly", "READONLY_");
		addColumn("repeatFlag", "REPEATFLAG_");
		addColumn("notNull", "NOTNULL_");
		addColumn("inputType", "INPUTTYPE_");
		addColumn("defaultVal", "DEFAULTVAL_");
		addColumn("extJson", "EXTJSON_");
		addColumn("creator", "CREATOR_");
		addColumn("createDateTime", "CREATEDATETIME_");
		addColumn("modifier", "MODIFIER_");
		addColumn("modifyDateTime", "MODIFYDATETIME_");
		addColumn("delFlag", "DELFLAG_");
	}

}