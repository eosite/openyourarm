package com.glaf.dep.base.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DepBasePropCategoryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String ruleId;
	protected List<String> ruleIds;
	protected Collection<String> appActorIds;
	protected Long depBaseCategoryId;
	protected List<Long> depBaseCategoryIds;

	public DepBasePropCategoryQuery() {

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

	public DepBasePropCategoryQuery depBaseCategoryId(Long depBaseCategoryId) {
		if (depBaseCategoryId == null) {
			throw new RuntimeException("depBaseCategoryId is null");
		}
		this.depBaseCategoryId = depBaseCategoryId;
		return this;
	}

	public Long getDepBaseCategoryId() {
		return depBaseCategoryId;
	}

	public void setDepBaseCategoryId(Long depBaseCategoryId) {
		this.depBaseCategoryId = depBaseCategoryId;
	}

	public List<Long> getDepBaseCategoryIds() {
		return depBaseCategoryIds;
	}

	public void setDepBaseCategoryIds(List<Long> depBaseCategoryIds) {
		this.depBaseCategoryIds = depBaseCategoryIds;
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

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("ruleId", "RULEID_");
		addColumn("id", "ID_");
	}

}