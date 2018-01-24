package com.glaf.form.core.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class PageProcessInstanceQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> processInstanceIds;
	protected Collection<String> appActorIds;
	protected String pageId;
	protected List<String> pageIds;
	protected String processName;
	protected String processNameLike;
	protected List<String> processNames;
	protected String businessTable;
	protected String businessTableLike;
	protected List<String> businessTables;
	protected String businessKey;
	protected List<String> businessKeys;
	protected Integer statusGreaterThanOrEqual;
	protected Integer statusLessThanOrEqual;
	protected Integer wfStatus;
	protected Integer wfStatusGreaterThanOrEqual;
	protected Integer wfStatusLessThanOrEqual;
	protected Date startTimeGreaterThanOrEqual;
	protected Date startTimeLessThanOrEqual;
	protected Date endTimeGreaterThanOrEqual;
	protected Date endTimeLessThanOrEqual;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public PageProcessInstanceQuery() {

	}

	public PageProcessInstanceQuery businessKey(String businessKey) {
		if (businessKey == null) {
			throw new RuntimeException("businessKey is null");
		}
		this.businessKey = businessKey;
		return this;
	}

	public PageProcessInstanceQuery businessKeys(List<String> businessKeys) {
		if (businessKeys == null) {
			throw new RuntimeException("businessKeys is empty ");
		}
		this.businessKeys = businessKeys;
		return this;
	}

	public PageProcessInstanceQuery businessTable(String businessTable) {
		if (businessTable == null) {
			throw new RuntimeException("businessTable is null");
		}
		this.businessTable = businessTable;
		return this;
	}

	public PageProcessInstanceQuery businessTableLike(String businessTableLike) {
		if (businessTableLike == null) {
			throw new RuntimeException("businessTable is null");
		}
		this.businessTableLike = businessTableLike;
		return this;
	}

	public PageProcessInstanceQuery businessTables(List<String> businessTables) {
		if (businessTables == null) {
			throw new RuntimeException("businessTables is empty ");
		}
		this.businessTables = businessTables;
		return this;
	}

	public PageProcessInstanceQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public PageProcessInstanceQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public PageProcessInstanceQuery endTimeGreaterThanOrEqual(Date endTimeGreaterThanOrEqual) {
		if (endTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
		return this;
	}

	public PageProcessInstanceQuery endTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		if (endTimeLessThanOrEqual == null) {
			throw new RuntimeException("endTime is null");
		}
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public String getBusinessKey() {
		return businessKey;
	}

	public List<String> getBusinessKeys() {
		return businessKeys;
	}

	public String getBusinessTable() {
		return businessTable;
	}

	public String getBusinessTableLike() {
		if (businessTableLike != null && businessTableLike.trim().length() > 0) {
			if (!businessTableLike.startsWith("%")) {
				businessTableLike = "%" + businessTableLike;
			}
			if (!businessTableLike.endsWith("%")) {
				businessTableLike = businessTableLike + "%";
			}
		}
		return businessTableLike;
	}

	public List<String> getBusinessTables() {
		return businessTables;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Date getEndTimeGreaterThanOrEqual() {
		return endTimeGreaterThanOrEqual;
	}

	public Date getEndTimeLessThanOrEqual() {
		return endTimeLessThanOrEqual;
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

			if ("processName".equals(sortColumn)) {
				orderBy = "E.PROCESSNAME_" + a_x;
			}

			if ("businessTable".equals(sortColumn)) {
				orderBy = "E.BUSINESSTABLE_" + a_x;
			}

			if ("businessKey".equals(sortColumn)) {
				orderBy = "E.BUSINESSKEY_" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("wfStatus".equals(sortColumn)) {
				orderBy = "E.WFSTATUS_" + a_x;
			}

			if ("sortNo".equals(sortColumn)) {
				orderBy = "E.SORTNO_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
			}

			if ("version".equals(sortColumn)) {
				orderBy = "E.VERSION_" + a_x;
			}

			if ("startTime".equals(sortColumn)) {
				orderBy = "E.STARTTIME_" + a_x;
			}

			if ("endTime".equals(sortColumn)) {
				orderBy = "E.ENDTIME_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
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

	public String getProcessName() {
		return processName;
	}

	public String getProcessNameLike() {
		if (processNameLike != null && processNameLike.trim().length() > 0) {
			if (!processNameLike.startsWith("%")) {
				processNameLike = "%" + processNameLike;
			}
			if (!processNameLike.endsWith("%")) {
				processNameLike = processNameLike + "%";
			}
		}
		return processNameLike;
	}

	public List<String> getProcessNames() {
		return processNames;
	}

	public Date getStartTimeGreaterThanOrEqual() {
		return startTimeGreaterThanOrEqual;
	}

	public Date getStartTimeLessThanOrEqual() {
		return startTimeLessThanOrEqual;
	}

	public Integer getStatus() {
		return status;
	}

	public Integer getStatusGreaterThanOrEqual() {
		return statusGreaterThanOrEqual;
	}

	public Integer getStatusLessThanOrEqual() {
		return statusLessThanOrEqual;
	}

	public Integer getWfStatus() {
		return wfStatus;
	}

	public Integer getWfStatusGreaterThanOrEqual() {
		return wfStatusGreaterThanOrEqual;
	}

	public Integer getWfStatusLessThanOrEqual() {
		return wfStatusLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("processInstanceId", "PROCESSINSTANCEID_");
		addColumn("pageId", "PAGEID_");
		addColumn("processName", "PROCESSNAME_");
		addColumn("businessTable", "BUSINESSTABLE_");
		addColumn("businessKey", "BUSINESSKEY_");
		addColumn("status", "STATUS_");
		addColumn("wfStatus", "WFSTATUS_");
		addColumn("sortNo", "SORTNO_");
		addColumn("locked", "LOCKED_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("version", "VERSION_");
		addColumn("startTime", "STARTTIME_");
		addColumn("endTime", "ENDTIME_");
		addColumn("createTime", "CREATETIME_");
		addColumn("createBy", "CREATEBY_");
	}

	public PageProcessInstanceQuery pageId(String pageId) {
		if (pageId == null) {
			throw new RuntimeException("pageId is null");
		}
		this.pageId = pageId;
		return this;
	}

	public PageProcessInstanceQuery pageIds(List<String> pageIds) {
		if (pageIds == null) {
			throw new RuntimeException("pageIds is empty ");
		}
		this.pageIds = pageIds;
		return this;
	}

	public PageProcessInstanceQuery processName(String processName) {
		if (processName == null) {
			throw new RuntimeException("processName is null");
		}
		this.processName = processName;
		return this;
	}

	public PageProcessInstanceQuery processNameLike(String processNameLike) {
		if (processNameLike == null) {
			throw new RuntimeException("processName is null");
		}
		this.processNameLike = processNameLike;
		return this;
	}

	public PageProcessInstanceQuery processNames(List<String> processNames) {
		if (processNames == null) {
			throw new RuntimeException("processNames is empty ");
		}
		this.processNames = processNames;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setBusinessKeys(List<String> businessKeys) {
		this.businessKeys = businessKeys;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	public void setBusinessTableLike(String businessTableLike) {
		this.businessTableLike = businessTableLike;
	}

	public void setBusinessTables(List<String> businessTables) {
		this.businessTables = businessTables;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setEndTimeGreaterThanOrEqual(Date endTimeGreaterThanOrEqual) {
		this.endTimeGreaterThanOrEqual = endTimeGreaterThanOrEqual;
	}

	public void setEndTimeLessThanOrEqual(Date endTimeLessThanOrEqual) {
		this.endTimeLessThanOrEqual = endTimeLessThanOrEqual;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setPageIds(List<String> pageIds) {
		this.pageIds = pageIds;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setProcessNameLike(String processNameLike) {
		this.processNameLike = processNameLike;
	}

	public void setProcessNames(List<String> processNames) {
		this.processNames = processNames;
	}

	public void setStartTimeGreaterThanOrEqual(Date startTimeGreaterThanOrEqual) {
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
	}

	public void setStartTimeLessThanOrEqual(Date startTimeLessThanOrEqual) {
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setStatusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
	}

	public void setStatusLessThanOrEqual(Integer statusLessThanOrEqual) {
		this.statusLessThanOrEqual = statusLessThanOrEqual;
	}

	public void setWfStatus(Integer wfStatus) {
		this.wfStatus = wfStatus;
	}

	public void setWfStatusGreaterThanOrEqual(Integer wfStatusGreaterThanOrEqual) {
		this.wfStatusGreaterThanOrEqual = wfStatusGreaterThanOrEqual;
	}

	public void setWfStatusLessThanOrEqual(Integer wfStatusLessThanOrEqual) {
		this.wfStatusLessThanOrEqual = wfStatusLessThanOrEqual;
	}

	public PageProcessInstanceQuery startTimeGreaterThanOrEqual(Date startTimeGreaterThanOrEqual) {
		if (startTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeGreaterThanOrEqual = startTimeGreaterThanOrEqual;
		return this;
	}

	public PageProcessInstanceQuery startTimeLessThanOrEqual(Date startTimeLessThanOrEqual) {
		if (startTimeLessThanOrEqual == null) {
			throw new RuntimeException("startTime is null");
		}
		this.startTimeLessThanOrEqual = startTimeLessThanOrEqual;
		return this;
	}

	public PageProcessInstanceQuery status(Integer status) {
		if (status == null) {
			throw new RuntimeException("status is null");
		}
		this.status = status;
		return this;
	}

	public PageProcessInstanceQuery statusGreaterThanOrEqual(Integer statusGreaterThanOrEqual) {
		if (statusGreaterThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusGreaterThanOrEqual = statusGreaterThanOrEqual;
		return this;
	}

	public PageProcessInstanceQuery statusLessThanOrEqual(Integer statusLessThanOrEqual) {
		if (statusLessThanOrEqual == null) {
			throw new RuntimeException("status is null");
		}
		this.statusLessThanOrEqual = statusLessThanOrEqual;
		return this;
	}

	public PageProcessInstanceQuery wfStatus(Integer wfStatus) {
		if (wfStatus == null) {
			throw new RuntimeException("wfStatus is null");
		}
		this.wfStatus = wfStatus;
		return this;
	}

	public PageProcessInstanceQuery wfStatusGreaterThanOrEqual(Integer wfStatusGreaterThanOrEqual) {
		if (wfStatusGreaterThanOrEqual == null) {
			throw new RuntimeException("wfStatus is null");
		}
		this.wfStatusGreaterThanOrEqual = wfStatusGreaterThanOrEqual;
		return this;
	}

	public PageProcessInstanceQuery wfStatusLessThanOrEqual(Integer wfStatusLessThanOrEqual) {
		if (wfStatusLessThanOrEqual == null) {
			throw new RuntimeException("wfStatus is null");
		}
		this.wfStatusLessThanOrEqual = wfStatusLessThanOrEqual;
		return this;
	}

}