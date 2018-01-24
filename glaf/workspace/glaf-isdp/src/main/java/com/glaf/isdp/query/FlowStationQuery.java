package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FlowStationQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String processId;
	protected String processIdLike;
	protected List<String> processIds;
	protected String activDId;
	protected String activDIdLike;
	protected List<String> activDIds;

	public FlowStationQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public String getProcessId() {
		return processId;
	}

	public String getProcessIdLike() {
		if (processIdLike != null && processIdLike.trim().length() > 0) {
			if (!processIdLike.startsWith("%")) {
				processIdLike = "%" + processIdLike;
			}
			if (!processIdLike.endsWith("%")) {
				processIdLike = processIdLike + "%";
			}
		}
		return processIdLike;
	}

	public List<String> getProcessIds() {
		return processIds;
	}

	public String getActivDId() {
		return activDId;
	}

	public String getActivDIdLike() {
		if (activDIdLike != null && activDIdLike.trim().length() > 0) {
			if (!activDIdLike.startsWith("%")) {
				activDIdLike = "%" + activDIdLike;
			}
			if (!activDIdLike.endsWith("%")) {
				activDIdLike = activDIdLike + "%";
			}
		}
		return activDIdLike;
	}

	public List<String> getActivDIds() {
		return activDIds;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public void setProcessIdLike(String processIdLike) {
		this.processIdLike = processIdLike;
	}

	public void setProcessIds(List<String> processIds) {
		this.processIds = processIds;
	}

	public void setActivDId(String activDId) {
		this.activDId = activDId;
	}

	public void setActivDIdLike(String activDIdLike) {
		this.activDIdLike = activDIdLike;
	}

	public void setActivDIds(List<String> activDIds) {
		this.activDIds = activDIds;
	}

	public FlowStationQuery processId(String processId) {
		if (processId == null) {
			throw new RuntimeException("processId is null");
		}
		this.processId = processId;
		return this;
	}

	public FlowStationQuery processIdLike(String processIdLike) {
		if (processIdLike == null) {
			throw new RuntimeException("processId is null");
		}
		this.processIdLike = processIdLike;
		return this;
	}

	public FlowStationQuery processIds(List<String> processIds) {
		if (processIds == null) {
			throw new RuntimeException("processIds is empty ");
		}
		this.processIds = processIds;
		return this;
	}

	public FlowStationQuery activDId(String activDId) {
		if (activDId == null) {
			throw new RuntimeException("activDId is null");
		}
		this.activDId = activDId;
		return this;
	}

	public FlowStationQuery activDIdLike(String activDIdLike) {
		if (activDIdLike == null) {
			throw new RuntimeException("activDId is null");
		}
		this.activDIdLike = activDIdLike;
		return this;
	}

	public FlowStationQuery activDIds(List<String> activDIds) {
		if (activDIds == null) {
			throw new RuntimeException("activDIds is empty ");
		}
		this.activDIds = activDIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("processId".equals(sortColumn)) {
				orderBy = "E.PROCESS_ID" + a_x;
			}

			if ("activDId".equals(sortColumn)) {
				orderBy = "E.ACTIV_D_ID" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID");
		addColumn("processId", "PROCESS_ID");
		addColumn("activDId", "ACTIV_D_ID");
	}

}