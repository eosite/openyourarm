package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBasePropScopeQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ruleIds;
	protected Collection<String> appActorIds;
	protected String depBaseUIId;
	protected String depBaseUIIdLike;
	protected List<String> depBaseUIIds;
	protected String creator;
	protected String creatorLike;
	protected List<String> creators;
	protected Date createDateTimeGreaterThanOrEqual;
	protected Date createDateTimeLessThanOrEqual;

	public DepBasePropScopeQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getDepBaseUIId() {
		return depBaseUIId;
	}

	public String getDepBaseUIIdLike() {
		if (depBaseUIIdLike != null && depBaseUIIdLike.trim().length() > 0) {
			if (!depBaseUIIdLike.startsWith("%")) {
				depBaseUIIdLike = "%" + depBaseUIIdLike;
			}
			if (!depBaseUIIdLike.endsWith("%")) {
				depBaseUIIdLike = depBaseUIIdLike + "%";
			}
		}
		return depBaseUIIdLike;
	}

	public List<String> getDepBaseUIIds() {
		return depBaseUIIds;
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

	public void setDepBaseUIId(String depBaseUIId) {
		this.depBaseUIId = depBaseUIId;
	}

	public void setDepBaseUIIdLike(String depBaseUIIdLike) {
		this.depBaseUIIdLike = depBaseUIIdLike;
	}

	public void setDepBaseUIIds(List<String> depBaseUIIds) {
		this.depBaseUIIds = depBaseUIIds;
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

	public DepBasePropScopeQuery depBaseUIId(String depBaseUIId) {
		if (depBaseUIId == null) {
			throw new RuntimeException("depBaseUIId is null");
		}
		this.depBaseUIId = depBaseUIId;
		return this;
	}

	public DepBasePropScopeQuery depBaseUIIdLike(String depBaseUIIdLike) {
		if (depBaseUIIdLike == null) {
			throw new RuntimeException("depBaseUIId is null");
		}
		this.depBaseUIIdLike = depBaseUIIdLike;
		return this;
	}

	public DepBasePropScopeQuery depBaseUIIds(List<String> depBaseUIIds) {
		if (depBaseUIIds == null) {
			throw new RuntimeException("depBaseUIIds is empty ");
		}
		this.depBaseUIIds = depBaseUIIds;
		return this;
	}

	public DepBasePropScopeQuery creator(String creator) {
		if (creator == null) {
			throw new RuntimeException("creator is null");
		}
		this.creator = creator;
		return this;
	}

	public DepBasePropScopeQuery creatorLike(String creatorLike) {
		if (creatorLike == null) {
			throw new RuntimeException("creator is null");
		}
		this.creatorLike = creatorLike;
		return this;
	}

	public DepBasePropScopeQuery creators(List<String> creators) {
		if (creators == null) {
			throw new RuntimeException("creators is empty ");
		}
		this.creators = creators;
		return this;
	}

	public DepBasePropScopeQuery createDateTimeGreaterThanOrEqual(
			Date createDateTimeGreaterThanOrEqual) {
		if (createDateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createDateTime is null");
		}
		this.createDateTimeGreaterThanOrEqual = createDateTimeGreaterThanOrEqual;
		return this;
	}

	public DepBasePropScopeQuery createDateTimeLessThanOrEqual(
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

			if ("id".equals(sortColumn)) {
				orderBy = "E.ID_" + a_x;
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
		addColumn("id", "ID_");
		addColumn("creator", "CREATOR_");
		addColumn("createDateTime", "CREATEDATETIME_");
	}

}