package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FormPageHistoryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String pageId;

	protected String deploymentId;

	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	public FormPageHistoryQuery() {

	}

	public FormPageHistoryQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormPageHistoryQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormPageHistoryQuery deploymentId(String deploymentId) {
		if (deploymentId == null) {
			throw new RuntimeException("deploymentId is null");
		}
		this.deploymentId = deploymentId;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
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

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("pageId".equals(sortColumn)) {
				orderBy = "E.PAGEID_" + a_x;
			}

			if ("deploymentId".equals(sortColumn)) {
				orderBy = "E.DEPLOYMENTID_" + a_x;
			}

			if ("formHtml".equals(sortColumn)) {
				orderBy = "E.FORMHTML_" + a_x;
			}

			if ("formConfig".equals(sortColumn)) {
				orderBy = "E.FORMCONFIG_" + a_x;
			}

			if ("outputHtml".equals(sortColumn)) {
				orderBy = "E.OUTPUTHTML_" + a_x;
			}

			if ("formType".equals(sortColumn)) {
				orderBy = "E.FORMTYPE_" + a_x;
			}

			if ("version".equals(sortColumn)) {
				orderBy = "E.VERSION_" + a_x;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("pageId", "PAGEID_");
		addColumn("deploymentId", "DEPLOYMENTID_");
		addColumn("formHtml", "FORMHTML_");
		addColumn("formConfig", "FORMCONFIG_");
		addColumn("outputHtml", "OUTPUTHTML_");
		addColumn("formType", "FORMTYPE_");
		addColumn("version", "VERSION_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
	}

	public FormPageHistoryQuery pageId(String pageId) {
		if (pageId == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageId = pageId;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

}