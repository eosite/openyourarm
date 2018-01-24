package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormRulePropertyQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String ruleId;
	protected List<String> ruleIds;
	protected String name;
	protected String nameLike;
	protected List<String> names;
	protected String value;
	protected String valueLike;
	protected List<String> values;

	public FormRulePropertyQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public List<Long> getIds() {
		return ids;
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

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("rid".equals(sortColumn)) {
				orderBy = "E.RULEID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("value".equals(sortColumn)) {
				orderBy = "E.VALUE_" + a_x;
			}

		}
		return orderBy;
	}

	public String getRuleId() {
		return ruleId;
	}

	public List<String> getRuleIds() {
		return ruleIds;
	}

	public String getValue() {
		return value;
	}

	public String getValueLike() {
		if (valueLike != null && valueLike.trim().length() > 0) {
			if (!valueLike.startsWith("%")) {
				valueLike = "%" + valueLike;
			}
			if (!valueLike.endsWith("%")) {
				valueLike = valueLike + "%";
			}
		}
		return valueLike;
	}

	public List<String> getValues() {
		return values;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("rid", "RULEID_");
		addColumn("name", "NAME_");
		addColumn("value", "VALUE_");
	}

	public FormRulePropertyQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormRulePropertyQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FormRulePropertyQuery names(List<String> names) {
		if (names == null) {
			throw new RuntimeException("names is empty ");
		}
		this.names = names;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
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

	public void setRuleId(String ruleId) {
		this.ruleId = ruleId;
	}

	public void setRuleIds(List<String> ruleIds) {
		this.ruleIds = ruleIds;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setValueLike(String valueLike) {
		this.valueLike = valueLike;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public FormRulePropertyQuery value(String value) {
		if (value == null) {
			throw new RuntimeException("value is null");
		}
		this.value = value;
		return this;
	}

	public FormRulePropertyQuery valueLike(String valueLike) {
		if (valueLike == null) {
			throw new RuntimeException("value is null");
		}
		this.valueLike = valueLike;
		return this;
	}

	public FormRulePropertyQuery values(List<String> values) {
		if (values == null) {
			throw new RuntimeException("values is empty ");
		}
		this.values = values;
		return this;
	}

}