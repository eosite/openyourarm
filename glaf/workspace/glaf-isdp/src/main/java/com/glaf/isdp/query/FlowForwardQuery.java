package com.glaf.isdp.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FlowForwardQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String processId;
	protected String processIdLike;
	protected List<String> processIds;
	protected String activDId;
	protected String activDIdLike;
	protected List<String> activDIds;
	protected String activDNext;
	protected String activDNextLike;
	protected List<String> activDNexts;
	protected Integer sendType;
	protected Integer sendTypeGreaterThanOrEqual;
	protected Integer sendTypeLessThanOrEqual;
	protected List<Integer> sendTypes;
	protected Integer sendTimes;
	protected Integer sendTimesGreaterThanOrEqual;
	protected Integer sendTimesLessThanOrEqual;
	protected List<Integer> sendTimess;

	public FlowForwardQuery() {

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

	public String getActivDNext() {
		return activDNext;
	}

	public String getActivDNextLike() {
		if (activDNextLike != null && activDNextLike.trim().length() > 0) {
			if (!activDNextLike.startsWith("%")) {
				activDNextLike = "%" + activDNextLike;
			}
			if (!activDNextLike.endsWith("%")) {
				activDNextLike = activDNextLike + "%";
			}
		}
		return activDNextLike;
	}

	public List<String> getActivDNexts() {
		return activDNexts;
	}

	public Integer getSendType() {
		return sendType;
	}

	public Integer getSendTypeGreaterThanOrEqual() {
		return sendTypeGreaterThanOrEqual;
	}

	public Integer getSendTypeLessThanOrEqual() {
		return sendTypeLessThanOrEqual;
	}

	public List<Integer> getSendTypes() {
		return sendTypes;
	}

	public Integer getSendTimes() {
		return sendTimes;
	}

	public Integer getSendTimesGreaterThanOrEqual() {
		return sendTimesGreaterThanOrEqual;
	}

	public Integer getSendTimesLessThanOrEqual() {
		return sendTimesLessThanOrEqual;
	}

	public List<Integer> getSendTimess() {
		return sendTimess;
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

	public void setActivDNext(String activDNext) {
		this.activDNext = activDNext;
	}

	public void setActivDNextLike(String activDNextLike) {
		this.activDNextLike = activDNextLike;
	}

	public void setActivDNexts(List<String> activDNexts) {
		this.activDNexts = activDNexts;
	}

	public void setSendType(Integer sendType) {
		this.sendType = sendType;
	}

	public void setSendTypeGreaterThanOrEqual(Integer sendTypeGreaterThanOrEqual) {
		this.sendTypeGreaterThanOrEqual = sendTypeGreaterThanOrEqual;
	}

	public void setSendTypeLessThanOrEqual(Integer sendTypeLessThanOrEqual) {
		this.sendTypeLessThanOrEqual = sendTypeLessThanOrEqual;
	}

	public void setSendTypes(List<Integer> sendTypes) {
		this.sendTypes = sendTypes;
	}

	public void setSendTimes(Integer sendTimes) {
		this.sendTimes = sendTimes;
	}

	public void setSendTimesGreaterThanOrEqual(
			Integer sendTimesGreaterThanOrEqual) {
		this.sendTimesGreaterThanOrEqual = sendTimesGreaterThanOrEqual;
	}

	public void setSendTimesLessThanOrEqual(Integer sendTimesLessThanOrEqual) {
		this.sendTimesLessThanOrEqual = sendTimesLessThanOrEqual;
	}

	public void setSendTimess(List<Integer> sendTimess) {
		this.sendTimess = sendTimess;
	}

	public FlowForwardQuery processId(String processId) {
		if (processId == null) {
			throw new RuntimeException("processId is null");
		}
		this.processId = processId;
		return this;
	}

	public FlowForwardQuery processIdLike(String processIdLike) {
		if (processIdLike == null) {
			throw new RuntimeException("processId is null");
		}
		this.processIdLike = processIdLike;
		return this;
	}

	public FlowForwardQuery processIds(List<String> processIds) {
		if (processIds == null) {
			throw new RuntimeException("processIds is empty ");
		}
		this.processIds = processIds;
		return this;
	}

	public FlowForwardQuery activDId(String activDId) {
		if (activDId == null) {
			throw new RuntimeException("activDId is null");
		}
		this.activDId = activDId;
		return this;
	}

	public FlowForwardQuery activDIdLike(String activDIdLike) {
		if (activDIdLike == null) {
			throw new RuntimeException("activDId is null");
		}
		this.activDIdLike = activDIdLike;
		return this;
	}

	public FlowForwardQuery activDIds(List<String> activDIds) {
		if (activDIds == null) {
			throw new RuntimeException("activDIds is empty ");
		}
		this.activDIds = activDIds;
		return this;
	}

	public FlowForwardQuery activDNext(String activDNext) {
		if (activDNext == null) {
			throw new RuntimeException("activDNext is null");
		}
		this.activDNext = activDNext;
		return this;
	}

	public FlowForwardQuery activDNextLike(String activDNextLike) {
		if (activDNextLike == null) {
			throw new RuntimeException("activDNext is null");
		}
		this.activDNextLike = activDNextLike;
		return this;
	}

	public FlowForwardQuery activDNexts(List<String> activDNexts) {
		if (activDNexts == null) {
			throw new RuntimeException("activDNexts is empty ");
		}
		this.activDNexts = activDNexts;
		return this;
	}

	public FlowForwardQuery sendType(Integer sendType) {
		if (sendType == null) {
			throw new RuntimeException("sendType is null");
		}
		this.sendType = sendType;
		return this;
	}

	public FlowForwardQuery sendTypeGreaterThanOrEqual(
			Integer sendTypeGreaterThanOrEqual) {
		if (sendTypeGreaterThanOrEqual == null) {
			throw new RuntimeException("sendType is null");
		}
		this.sendTypeGreaterThanOrEqual = sendTypeGreaterThanOrEqual;
		return this;
	}

	public FlowForwardQuery sendTypeLessThanOrEqual(
			Integer sendTypeLessThanOrEqual) {
		if (sendTypeLessThanOrEqual == null) {
			throw new RuntimeException("sendType is null");
		}
		this.sendTypeLessThanOrEqual = sendTypeLessThanOrEqual;
		return this;
	}

	public FlowForwardQuery sendTypes(List<Integer> sendTypes) {
		if (sendTypes == null) {
			throw new RuntimeException("sendTypes is empty ");
		}
		this.sendTypes = sendTypes;
		return this;
	}

	public FlowForwardQuery sendTimes(Integer sendTimes) {
		if (sendTimes == null) {
			throw new RuntimeException("sendTimes is null");
		}
		this.sendTimes = sendTimes;
		return this;
	}

	public FlowForwardQuery sendTimesGreaterThanOrEqual(
			Integer sendTimesGreaterThanOrEqual) {
		if (sendTimesGreaterThanOrEqual == null) {
			throw new RuntimeException("sendTimes is null");
		}
		this.sendTimesGreaterThanOrEqual = sendTimesGreaterThanOrEqual;
		return this;
	}

	public FlowForwardQuery sendTimesLessThanOrEqual(
			Integer sendTimesLessThanOrEqual) {
		if (sendTimesLessThanOrEqual == null) {
			throw new RuntimeException("sendTimes is null");
		}
		this.sendTimesLessThanOrEqual = sendTimesLessThanOrEqual;
		return this;
	}

	public FlowForwardQuery sendTimess(List<Integer> sendTimess) {
		if (sendTimess == null) {
			throw new RuntimeException("sendTimess is empty ");
		}
		this.sendTimess = sendTimess;
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

			if ("activDNext".equals(sortColumn)) {
				orderBy = "E.ACTIV_D_NEXT" + a_x;
			}

			if ("sendType".equals(sortColumn)) {
				orderBy = "E.SENDTYPE" + a_x;
			}

			if ("sendTimes".equals(sortColumn)) {
				orderBy = "E.SENDTIMES" + a_x;
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
		addColumn("activDNext", "ACTIV_D_NEXT");
		addColumn("sendType", "SENDTYPE");
		addColumn("sendTimes", "SENDTIMES");
	}

}