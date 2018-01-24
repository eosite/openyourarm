package com.glaf.form.core.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

public class FormRuleQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Long componentId;
	protected List<Long> componentIds;
	protected String appId;
	protected List<String> appIds;
	protected String pageId;
	protected List<String> pageIds;
	protected String deploymentId;
	protected List<String> deploymentIds;
	protected String name;
	protected String type;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<String> createBys;

	public FormRuleQuery() {

	}

	public FormRuleQuery appId(String appId) {
		if (appId == null) {
			throw new RuntimeException("appId is null");
		}
		this.appId = appId;
		return this;
	}

	public FormRuleQuery appIds(List<String> appIds) {
		if (appIds == null) {
			throw new RuntimeException("appIds is empty ");
		}
		this.appIds = appIds;
		return this;
	}

	public FormRuleQuery componentId(Long componentId) {
		if (componentId == null) {
			throw new RuntimeException("componentId is null");
		}
		this.componentId = componentId;
		return this;
	}

	public FormRuleQuery componentIds(List<Long> componentIds) {
		if (componentIds == null) {
			throw new RuntimeException("componentIds is empty ");
		}
		this.componentIds = componentIds;
		return this;
	}

	public FormRuleQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FormRuleQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormRuleQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormRuleQuery deploymentId(String deploymentId) {
		if (deploymentId == null) {
			throw new RuntimeException("deploymentId is null");
		}
		this.deploymentId = deploymentId;
		return this;
	}

	public FormRuleQuery deploymentIds(List<String> deploymentIds) {
		if (deploymentIds == null) {
			throw new RuntimeException("deploymentIds is empty ");
		}
		this.deploymentIds = deploymentIds;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public String getAppId() {
		return appId;
	}

	public List<String> getAppIds() {
		return appIds;
	}

	public Long getComponentId() {
		return componentId;
	}

	public List<Long> getComponentIds() {
		return componentIds;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public List<String> getDeploymentIds() {
		return deploymentIds;
	}

	public String getName() {
		return name;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("componentId".equals(sortColumn)) {
				orderBy = "E.COMPONENTID_" + a_x;
			}

			if ("appId".equals(sortColumn)) {
				orderBy = "E.APPID_" + a_x;
			}

			if ("pageId".equals(sortColumn)) {
				orderBy = "E.PAGEID_" + a_x;
			}

			if ("deploymentId".equals(sortColumn)) {
				orderBy = "E.DEPLOYMENTID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("value".equals(sortColumn)) {
				orderBy = "E.VALUE_" + a_x;
			}

			if ("snapshot".equals(sortColumn)) {
				orderBy = "E.SNAPSHOT_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

		}
		return orderBy;
	}

	public String getPageId() {
		return pageId;
	}

	public List<String> getPageIds() {
		return pageIds;
	}

	public String getType() {
		return type;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("componentId", "COMPONENTID_");
		addColumn("appId", "APPID_");
		addColumn("pageId", "PAGEID_");
		addColumn("deploymentId", "DEPLOYMENTID_");
		addColumn("name", "NAME_");
		addColumn("value", "VALUE_");
		addColumn("snapshot", "SNAPSHOT_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
	}

	public FormRuleQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormRuleQuery pageId(String pageId) {
		if (pageId == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageId = pageId;
		return this;
	}

	public FormRuleQuery pageIds(List<String> pageIds) {
		if (pageIds == null) {
			throw new RuntimeException("pageIds is empty ");
		}
		this.pageIds = pageIds;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setAppId(String appId) {
		this.appId = appId;
	}

	public void setAppIds(List<String> appIds) {
		this.appIds = appIds;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public void setComponentIds(List<Long> componentIds) {
		this.componentIds = componentIds;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setDeploymentIds(List<String> deploymentIds) {
		this.deploymentIds = deploymentIds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setPageIds(List<String> pageIds) {
		this.pageIds = pageIds;
	}

	public void setType(String type) {
		this.type = type;
	}

	public FormRuleQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}