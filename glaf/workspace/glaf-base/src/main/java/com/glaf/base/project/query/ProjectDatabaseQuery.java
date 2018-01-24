package com.glaf.base.project.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class ProjectDatabaseQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected Long databaseId;
	protected List<Long> databaseIds;
	protected Long projectId;
	protected List<Long> projectIds;

	public ProjectDatabaseQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public List<Long> getDatabaseIds() {
		return databaseIds;
	}

	public Long getProjectId() {
		return projectId;
	}

	public List<Long> getProjectIds() {
		return projectIds;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDatabaseIds(List<Long> databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setProjectIds(List<Long> projectIds) {
		this.projectIds = projectIds;
	}

	public ProjectDatabaseQuery databaseId(Long databaseId) {
		if (databaseId == null) {
			throw new RuntimeException("databaseId is null");
		}
		this.databaseId = databaseId;
		return this;
	}

	public ProjectDatabaseQuery databaseIds(List<Long> databaseIds) {
		if (databaseIds == null) {
			throw new RuntimeException("databaseIds is empty ");
		}
		this.databaseIds = databaseIds;
		return this;
	}

	public ProjectDatabaseQuery projectId(Long projectId) {
		if (projectId == null) {
			throw new RuntimeException("projectId is null");
		}
		this.projectId = projectId;
		return this;
	}

	public ProjectDatabaseQuery projectIds(List<Long> projectIds) {
		if (projectIds == null) {
			throw new RuntimeException("projectIds is empty ");
		}
		this.projectIds = projectIds;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("databaseId".equals(sortColumn)) {
				orderBy = "E.DATABASEID_" + a_x;
			}

			if ("projectId".equals(sortColumn)) {
				orderBy = "E.PROJECTID_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("databaseId", "DATABASEID_");
		addColumn("projectId", "PROJECTID_");
	}

}