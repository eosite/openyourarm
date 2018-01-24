package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBasePropRelQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String ruleId;
	protected List<String> ruleIds;
	protected Collection<String> appActorIds;
	protected String relRuleId;
	protected String relRuleIdLike;
	protected List<String> relRuleIds;
	protected String relType;
	protected String relTypeLike;
	protected List<String> relTypes;
	protected String creator;
	protected String creatorLike;
	protected List<String> creators;
	protected Date createDateTimeGreaterThanOrEqual;
	protected Date createDateTimeLessThanOrEqual;

	public DepBasePropRelQuery() {

	}

	public String getRuleId() {
		return ruleId;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
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

	public String getRelRuleId() {
		return relRuleId;
	}

	public String getRelRuleIdLike() {
		if (relRuleIdLike != null && relRuleIdLike.trim().length() > 0) {
			if (!relRuleIdLike.startsWith("%")) {
				relRuleIdLike = "%" + relRuleIdLike;
			}
			if (!relRuleIdLike.endsWith("%")) {
				relRuleIdLike = relRuleIdLike + "%";
			}
		}
		return relRuleIdLike;
	}

	public List<String> getRelRuleIds() {
		return relRuleIds;
	}

	public String getRelType() {
		return relType;
	}

	public String getRelTypeLike() {
		if (relTypeLike != null && relTypeLike.trim().length() > 0) {
			if (!relTypeLike.startsWith("%")) {
				relTypeLike = "%" + relTypeLike;
			}
			if (!relTypeLike.endsWith("%")) {
				relTypeLike = relTypeLike + "%";
			}
		}
		return relTypeLike;
	}

	public List<String> getRelTypes() {
		return relTypes;
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

	public void setRelRuleId(String relRuleId) {
		this.relRuleId = relRuleId;
	}

	public void setRelRuleIdLike(String relRuleIdLike) {
		this.relRuleIdLike = relRuleIdLike;
	}

	public void setRelRuleIds(List<String> relRuleIds) {
		this.relRuleIds = relRuleIds;
	}

	public void setRelType(String relType) {
		this.relType = relType;
	}

	public void setRelTypeLike(String relTypeLike) {
		this.relTypeLike = relTypeLike;
	}

	public void setRelTypes(List<String> relTypes) {
		this.relTypes = relTypes;
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

	public DepBasePropRelQuery relRuleId(String relRuleId) {
		if (relRuleId == null) {
			throw new RuntimeException("relRuleId is null");
		}
		this.relRuleId = relRuleId;
		return this;
	}

	public DepBasePropRelQuery relRuleIdLike(String relRuleIdLike) {
		if (relRuleIdLike == null) {
			throw new RuntimeException("relRuleId is null");
		}
		this.relRuleIdLike = relRuleIdLike;
		return this;
	}

	public DepBasePropRelQuery relRuleIds(List<String> relRuleIds) {
		if (relRuleIds == null) {
			throw new RuntimeException("relRuleIds is empty ");
		}
		this.relRuleIds = relRuleIds;
		return this;
	}

	public DepBasePropRelQuery relType(String relType) {
		if (relType == null) {
			throw new RuntimeException("relType is null");
		}
		this.relType = relType;
		return this;
	}

	public DepBasePropRelQuery relTypeLike(String relTypeLike) {
		if (relTypeLike == null) {
			throw new RuntimeException("relType is null");
		}
		this.relTypeLike = relTypeLike;
		return this;
	}

	public DepBasePropRelQuery relTypes(List<String> relTypes) {
		if (relTypes == null) {
			throw new RuntimeException("relTypes is empty ");
		}
		this.relTypes = relTypes;
		return this;
	}

	public DepBasePropRelQuery creator(String creator) {
		if (creator == null) {
			throw new RuntimeException("creator is null");
		}
		this.creator = creator;
		return this;
	}

	public DepBasePropRelQuery creatorLike(String creatorLike) {
		if (creatorLike == null) {
			throw new RuntimeException("creator is null");
		}
		this.creatorLike = creatorLike;
		return this;
	}

	public DepBasePropRelQuery creators(List<String> creators) {
		if (creators == null) {
			throw new RuntimeException("creators is empty ");
		}
		this.creators = creators;
		return this;
	}

	public DepBasePropRelQuery createDateTimeGreaterThanOrEqual(
			Date createDateTimeGreaterThanOrEqual) {
		if (createDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepBasePropRelQuery createDateTimeLessThanOrEqual(
			Date createDateTimeLessThanOrEqual) {
		if (createDateTimeLessThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeLessThanOrEqual = createDateTimeLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("relRuleId".equals(sortColumn)) {
				orderBy = "E.RELRULEID_" + a_x;
			}

			if ("relType".equals(sortColumn)) {
				orderBy = "E.RELTYPE_" + a_x;
			}

			if ("creator".equals(sortColumn)) {
				orderBy = "E.CREATOR_" + a_x;
			}

			if ("createDateTime".equals(sortColumn)) {
				orderBy = "E.CREATEDATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("ruleId", "RULEID_");
		addColumn("relRuleId", "RELRULEID_");
		addColumn("relType", "RELTYPE_");
		addColumn("creator", "CREATOR_");
		addColumn("createDateTime", "CREATEDATETIME_");
	}

}