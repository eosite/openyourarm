package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SynchronizationRuleQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String actionNameLike;
	protected String formulasLike;
	protected String targetTable;
	protected String targetTableLike;
	protected String souceTableLike;
	protected String wbsTable;
	protected String wbsTableLike;

	public SynchronizationRuleQuery() {

	}

	public SynchronizationRuleQuery actionNameLike(String actionNameLike) {
		if (actionNameLike == null) {
			throw new RuntimeException("actionName is null");
		}
		this.actionNameLike = actionNameLike;
		return this;
	}

	public SynchronizationRuleQuery formulasLike(String formulasLike) {
		if (formulasLike == null) {
			throw new RuntimeException("formulas is null");
		}
		this.formulasLike = formulasLike;
		return this;
	}

	public String getActionNameLike() {
		if (actionNameLike != null && actionNameLike.trim().length() > 0) {
			if (!actionNameLike.startsWith("%")) {
				actionNameLike = "%" + actionNameLike;
			}
			if (!actionNameLike.endsWith("%")) {
				actionNameLike = actionNameLike + "%";
			}
		}
		return actionNameLike;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public String getFormulasLike() {
		if (formulasLike != null && formulasLike.trim().length() > 0) {
			if (!formulasLike.startsWith("%")) {
				formulasLike = "%" + formulasLike;
			}
			if (!formulasLike.endsWith("%")) {
				formulasLike = formulasLike + "%";
			}
		}
		return formulasLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("actionName".equals(sortColumn)) {
				orderBy = "E.ACTIONNAME" + a_x;
			}

			if ("formulas".equals(sortColumn)) {
				orderBy = "E.FORMULAS" + a_x;
			}

			if ("targetTable".equals(sortColumn)) {
				orderBy = "E.TARGETTABLE" + a_x;
			}

			if ("souceTable".equals(sortColumn)) {
				orderBy = "E.SOUCETABLE" + a_x;
			}

			if ("wbsTable".equals(sortColumn)) {
				orderBy = "E.WBSTABLE" + a_x;
			}

			if ("targetTableModel".equals(sortColumn)) {
				orderBy = "E.TARGETTABLEMODEL" + a_x;
			}

		}
		return orderBy;
	}

	public String getSouceTableLike() {
		if (souceTableLike != null && souceTableLike.trim().length() > 0) {
			if (!souceTableLike.startsWith("%")) {
				souceTableLike = "%" + souceTableLike;
			}
			if (!souceTableLike.endsWith("%")) {
				souceTableLike = souceTableLike + "%";
			}
		}
		return souceTableLike;
	}

	public String getTargetTable() {
		return targetTable;
	}

	public String getTargetTableLike() {
		if (targetTableLike != null && targetTableLike.trim().length() > 0) {
			if (!targetTableLike.startsWith("%")) {
				targetTableLike = "%" + targetTableLike;
			}
			if (!targetTableLike.endsWith("%")) {
				targetTableLike = targetTableLike + "%";
			}
		}
		return targetTableLike;
	}

	public String getWbsTable() {
		return wbsTable;
	}

	public String getWbsTableLike() {
		if (wbsTableLike != null && wbsTableLike.trim().length() > 0) {
			if (!wbsTableLike.startsWith("%")) {
				wbsTableLike = "%" + wbsTableLike;
			}
			if (!wbsTableLike.endsWith("%")) {
				wbsTableLike = wbsTableLike + "%";
			}
		}
		return wbsTableLike;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "INTERFACEID");
		addColumn("actionName", "ACTIONNAME");
		addColumn("formulas", "FORMULAS");
		addColumn("targetTable", "TARGETTABLE");
		addColumn("souceTable", "SOUCETABLE");
		addColumn("wbsTable", "WBSTABLE");
		addColumn("targetTableModel", "TARGETTABLEMODEL");
	}

	public void setActionNameLike(String actionNameLike) {
		this.actionNameLike = actionNameLike;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setFormulasLike(String formulasLike) {
		this.formulasLike = formulasLike;
	}

	public void setSouceTableLike(String souceTableLike) {
		this.souceTableLike = souceTableLike;
	}

	public void setTargetTable(String targetTable) {
		this.targetTable = targetTable;
	}

	public void setTargetTableLike(String targetTableLike) {
		this.targetTableLike = targetTableLike;
	}

	public void setWbsTable(String wbsTable) {
		this.wbsTable = wbsTable;
	}

	public void setWbsTableLike(String wbsTableLike) {
		this.wbsTableLike = wbsTableLike;
	}

	public SynchronizationRuleQuery souceTableLike(String souceTableLike) {
		if (souceTableLike == null) {
			throw new RuntimeException("souceTable is null");
		}
		this.souceTableLike = souceTableLike;
		return this;
	}

	public SynchronizationRuleQuery targetTable(String targetTable) {
		if (targetTable == null) {
			throw new RuntimeException("targetTable is null");
		}
		this.targetTable = targetTable;
		return this;
	}

	public SynchronizationRuleQuery targetTableLike(String targetTableLike) {
		if (targetTableLike == null) {
			throw new RuntimeException("targetTable is null");
		}
		this.targetTableLike = targetTableLike;
		return this;
	}

	public SynchronizationRuleQuery wbsTable(String wbsTable) {
		if (wbsTable == null) {
			throw new RuntimeException("wbsTable is null");
		}
		this.wbsTable = wbsTable;
		return this;
	}

	public SynchronizationRuleQuery wbsTableLike(String wbsTableLike) {
		if (wbsTableLike == null) {
			throw new RuntimeException("wbsTable is null");
		}
		this.wbsTableLike = wbsTableLike;
		return this;
	}

}