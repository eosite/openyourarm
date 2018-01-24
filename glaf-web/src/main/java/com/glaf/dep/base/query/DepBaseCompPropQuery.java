package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBaseCompPropQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String depBaseComponentId;
	protected List<String> depBaseComponentIds;
	protected Collection<String> appActorIds;
	protected String ruleId;
	protected String ruleIdLike;
	protected List<String> ruleIds;

	public DepBaseCompPropQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getDepBaseComponentId() {
		return depBaseComponentId;
	}

	public void setDepBaseComponentId(String depBaseComponentId) {
		this.depBaseComponentId = depBaseComponentId;
	}

	public List<String> getDepBaseComponentIds() {
		return depBaseComponentIds;
	}

	public void setDepBaseComponentIds(List<String> depBaseComponentIds) {
		this.depBaseComponentIds = depBaseComponentIds;
	}

	public String getRuleId() {
		return ruleId;
	}

	public String getRuleIdLike() {
		if (ruleIdLike != null && ruleIdLike.trim().length() > 0) {
			if (!ruleIdLike.startsWith("%")) {
				ruleIdLike = "%" + ruleIdLike;
			}
			if (!ruleIdLike.endsWith("%")) {
				ruleIdLike = ruleIdLike + "%";
			}
		}
		return ruleIdLike;
	}

	public List<String> getRuleIds() {
		return ruleIds;
	}

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setRuleIdLike(String ruleIdLike) {
		this.ruleIdLike = ruleIdLike;
	}

	public void setRuleIds(List<String> ruleIds) {
		this.ruleIds = ruleIds;
	}

	public DepBaseCompPropQuery ruleId(String ruleId) {
		if (ruleId == null) {
			throw new RuntimeException("ruleId is null");
		}
		this.ruleId = ruleId;
		return this;
	}

	public DepBaseCompPropQuery ruleIdLike(String ruleIdLike) {
		if (ruleIdLike == null) {
			throw new RuntimeException("ruleId is null");
		}
		this.ruleIdLike = ruleIdLike;
		return this;
	}

	public DepBaseCompPropQuery ruleIds(List<String> ruleIds) {
		if (ruleIds == null) {
			throw new RuntimeException("ruleIds is empty ");
		}
		this.ruleIds = ruleIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("ruleId".equals(sortColumn)) {
				orderBy = "E.RULEID_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("Id", "ID_");
		addColumn("ruleId", "RULEID_");
	}

}