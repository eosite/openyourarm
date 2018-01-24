package com.glaf.workflow.core.domain;

public class ProcessActivityInstance {

	protected String processInstId;
	protected String processStatus;
	protected Integer processResult;
	protected String activityName;
	protected String assigner;
	protected String startTime;
	protected String endTime;
	protected Long durationInMillis;
	protected Integer replyFlag;

	public String getActivityName() {
		return activityName;
	}

	public String getProcessInstId() {
		return processInstId;
	}

	public void setProcessInstId(String processInstId) {
		this.processInstId = processInstId;
	}

	public String getAssigner() {
		return assigner;
	}

	public String getStartTime() {
		return startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public Long getDurationInMillis() {
		return durationInMillis;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public void setAssigner(String assigner) {
		this.assigner = assigner;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public void setDurationInMillis(Long durationInMillis) {
		this.durationInMillis = durationInMillis;
	}

	public Integer getReplyFlag() {
		return replyFlag;
	}

	public void setReplyFlag(Integer replyFlag) {
		this.replyFlag = replyFlag;
	}

	public String getProcessStatus() {
		return processStatus;
	}

	public Integer getProcessResult() {
		return processResult;
	}

	public void setProcessStatus(String processStatus) {
		this.processStatus = processStatus;
	}

	public void setProcessResult(Integer processResult) {
		this.processResult = processResult;
	}
}
