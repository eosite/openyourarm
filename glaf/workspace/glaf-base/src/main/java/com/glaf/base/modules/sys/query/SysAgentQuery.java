package com.glaf.base.modules.sys.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class SysAgentQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String assignFrom;
	protected String assignFromNameLike;
	protected String assignTo;
	protected String assignToNameLike;
	protected String taskName;
	protected String taskNameLike;
	protected Date startDateGreaterThanOrEqual;
	protected Date startDateLessThanOrEqual;
	protected Date endDateGreaterThanOrEqual;
	protected Date endDateLessThanOrEqual;
	protected Integer agentType;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;

	public SysAgentQuery() {

	}

	public String getAssignFrom() {
		return assignFrom;
	}

	public String getAssignFromNameLike() {
		if (assignFromNameLike != null && assignFromNameLike.trim().length() > 0) {
			if (!assignFromNameLike.startsWith("%")) {
				assignFromNameLike = "%" + assignFromNameLike;
			}
			if (!assignFromNameLike.endsWith("%")) {
				assignFromNameLike = assignFromNameLike + "%";
			}
		}
		return assignFromNameLike;
	}

	public String getAssignTo() {
		return assignTo;
	}

	public String getAssignToNameLike() {
		if (assignToNameLike != null && assignToNameLike.trim().length() > 0) {
			if (!assignToNameLike.startsWith("%")) {
				assignToNameLike = "%" + assignToNameLike;
			}
			if (!assignToNameLike.endsWith("%")) {
				assignToNameLike = assignToNameLike + "%";
			}
		}
		return assignToNameLike;
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

	public String getTaskName() {
		return taskName;
	}

	public String getTaskNameLike() {
		if (taskNameLike != null && taskNameLike.trim().length() > 0) {
			if (!taskNameLike.startsWith("%")) {
				taskNameLike = "%" + taskNameLike;
			}
			if (!taskNameLike.endsWith("%")) {
				taskNameLike = taskNameLike + "%";
			}
		}
		return taskNameLike;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getObjectId() {
		return objectId;
	}

	public String getObjectValue() {
		return objectValue;
	}

	public Date getStartDateGreaterThanOrEqual() {
		return startDateGreaterThanOrEqual;
	}

	public Date getStartDateLessThanOrEqual() {
		return startDateLessThanOrEqual;
	}

	public Date getEndDateGreaterThanOrEqual() {
		return endDateGreaterThanOrEqual;
	}

	public Date getEndDateLessThanOrEqual() {
		return endDateLessThanOrEqual;
	}

	public Integer getAgentType() {
		return agentType;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public void setAssignFrom(String assignFrom) {
		this.assignFrom = assignFrom;
	}

	public void setAssignFromNameLike(String assignFromNameLike) {
		this.assignFromNameLike = assignFromNameLike;
	}

	public void setAssignTo(String assignTo) {
		this.assignTo = assignTo;
	}

	public void setAssignToNameLike(String assignToNameLike) {
		this.assignToNameLike = assignToNameLike;
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

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	public void setTaskNameLike(String taskNameLike) {
		this.taskNameLike = taskNameLike;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setObjectId(String objectId) {
		this.objectId = objectId;
	}

	public void setObjectValue(String objectValue) {
		this.objectValue = objectValue;
	}

	public void setStartDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
	}

	public void setStartDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
	}

	public void setEndDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
	}

	public void setEndDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
	}

	public void setAgentType(Integer agentType) {
		this.agentType = agentType;
	}

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public SysAgentQuery assignFrom(String assignFrom) {
		if (assignFrom == null) {
			throw new RuntimeException("assignFrom is null");
		}
		this.assignFrom = assignFrom;
		return this;
	}

	public SysAgentQuery assignFromNameLike(String assignFromNameLike) {
		if (assignFromNameLike == null) {
			throw new RuntimeException("assignFromName is null");
		}
		this.assignFromNameLike = assignFromNameLike;
		return this;
	}

	public SysAgentQuery assignTo(String assignTo) {
		if (assignTo == null) {
			throw new RuntimeException("assignTo is null");
		}
		this.assignTo = assignTo;
		return this;
	}

	public SysAgentQuery assignToNameLike(String assignToNameLike) {
		if (assignToNameLike == null) {
			throw new RuntimeException("assignToName is null");
		}
		this.assignToNameLike = assignToNameLike;
		return this;
	}

	public SysAgentQuery processName(String processName) {
		if (processName == null) {
			throw new RuntimeException("processName is null");
		}
		this.processName = processName;
		return this;
	}

	public SysAgentQuery processNameLike(String processNameLike) {
		if (processNameLike == null) {
			throw new RuntimeException("processName is null");
		}
		this.processNameLike = processNameLike;
		return this;
	}

	public SysAgentQuery taskName(String taskName) {
		if (taskName == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskName = taskName;
		return this;
	}

	public SysAgentQuery taskNameLike(String taskNameLike) {
		if (taskNameLike == null) {
			throw new RuntimeException("taskName is null");
		}
		this.taskNameLike = taskNameLike;
		return this;
	}

	public SysAgentQuery serviceKey(String serviceKey) {
		if (serviceKey == null) {
			throw new RuntimeException("serviceKey is null");
		}
		this.serviceKey = serviceKey;
		return this;
	}

	public SysAgentQuery objectId(String objectId) {
		if (objectId == null) {
			throw new RuntimeException("objectId is null");
		}
		this.objectId = objectId;
		return this;
	}

	public SysAgentQuery objectValue(String objectValue) {
		if (objectValue == null) {
			throw new RuntimeException("objectValue is null");
		}
		this.objectValue = objectValue;
		return this;
	}

	public SysAgentQuery startDateGreaterThanOrEqual(Date startDateGreaterThanOrEqual) {
		if (startDateGreaterThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateGreaterThanOrEqual = startDateGreaterThanOrEqual;
		return this;
	}

	public SysAgentQuery startDateLessThanOrEqual(Date startDateLessThanOrEqual) {
		if (startDateLessThanOrEqual == null) {
			throw new RuntimeException("startDate is null");
		}
		this.startDateLessThanOrEqual = startDateLessThanOrEqual;
		return this;
	}

	public SysAgentQuery endDateGreaterThanOrEqual(Date endDateGreaterThanOrEqual) {
		if (endDateGreaterThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateGreaterThanOrEqual = endDateGreaterThanOrEqual;
		return this;
	}

	public SysAgentQuery endDateLessThanOrEqual(Date endDateLessThanOrEqual) {
		if (endDateLessThanOrEqual == null) {
			throw new RuntimeException("endDate is null");
		}
		this.endDateLessThanOrEqual = endDateLessThanOrEqual;
		return this;
	}

	public SysAgentQuery agentType(Integer agentType) {
		if (agentType == null) {
			throw new RuntimeException("agentType is null");
		}
		this.agentType = agentType;
		return this;
	}

	public SysAgentQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public SysAgentQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("assignFrom".equals(sortColumn)) {
				orderBy = "E.ASSIGNFROM_" + a_x;
			}

			if ("assignFromName".equals(sortColumn)) {
				orderBy = "E.ASSIGNFROMNAME_" + a_x;
			}

			if ("assignTo".equals(sortColumn)) {
				orderBy = "E.ASSIGNTO_" + a_x;
			}

			if ("assignToName".equals(sortColumn)) {
				orderBy = "E.ASSIGNTONAME_" + a_x;
			}

			if ("processName".equals(sortColumn)) {
				orderBy = "E.PROCESSNAME_" + a_x;
			}

			if ("taskName".equals(sortColumn)) {
				orderBy = "E.TASKNAME_" + a_x;
			}

			if ("serviceKey".equals(sortColumn)) {
				orderBy = "E.SERVICEKEY_" + a_x;
			}

			if ("objectId".equals(sortColumn)) {
				orderBy = "E.OBJECTID_" + a_x;
			}

			if ("objectValue".equals(sortColumn)) {
				orderBy = "E.OBJECTVALUE_" + a_x;
			}

			if ("startDate".equals(sortColumn)) {
				orderBy = "E.STARTDATE_" + a_x;
			}

			if ("endDate".equals(sortColumn)) {
				orderBy = "E.ENDDATE_" + a_x;
			}

			if ("agentType".equals(sortColumn)) {
				orderBy = "E.AGENTTYPE_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

		}
		return orderBy;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("assignFrom", "ASSIGNFROM_");
		addColumn("assignFromName", "ASSIGNFROMNAME_");
		addColumn("assignTo", "ASSIGNTO_");
		addColumn("assignToName", "ASSIGNTONAME_");
		addColumn("processName", "PROCESSNAME_");
		addColumn("taskName", "TASKNAME_");
		addColumn("serviceKey", "SERVICEKEY_");
		addColumn("objectId", "OBJECTID_");
		addColumn("objectValue", "OBJECTVALUE_");
		addColumn("startDate", "STARTDATE_");
		addColumn("endDate", "ENDDATE_");
		addColumn("agentType", "AGENTTYPE_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createDate", "CREATEDATE_");
	}

}