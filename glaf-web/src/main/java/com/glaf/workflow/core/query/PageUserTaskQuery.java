package com.glaf.workflow.core.query;

import java.util.Date;

import com.glaf.core.query.DataQuery;

public class PageUserTaskQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String task;
	protected String action;
	protected String taskType;
	protected String taskNo;
	protected String activityCode;
	protected String activityName;
	protected String prevOperator;
	protected String nextOperator;
	protected String sender;
	protected String processPromoter;
	protected String title;
	protected String href;
	//父类中有isOwner属性，引起方法冲突
	//protected String owner;
	protected String priority;
	protected String processCode;
	protected String processName;
	protected Date expired;
	protected String isSendMsg;
	protected String sendMsgType;
	protected String msgContent;
	protected Integer isSync;
	protected Date syncDate;
	protected Integer syncIsSuccess;
	protected Date prevTaskEndTime;
	protected Date taskStartTime;
	protected Date taskEndTime;

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getTaskType() {
		return taskType;
	}

	public void setTaskType(String taskType) {
		this.taskType = taskType;
	}

	public String getTaskNo() {
		return taskNo;
	}

	public void setTaskNo(String taskNo) {
		this.taskNo = taskNo;
	}

	public String getActivityCode() {
		return activityCode;
	}

	public void setActivityCode(String activityCode) {
		this.activityCode = activityCode;
	}

	public String getActivityName() {
		return activityName;
	}

	public void setActivityName(String activityName) {
		this.activityName = activityName;
	}

	public String getPrevOperator() {
		return prevOperator;
	}

	public void setPrevOperator(String prevOperator) {
		this.prevOperator = prevOperator;
	}

	public String getNextOperator() {
		return nextOperator;
	}

	public void setNextOperator(String nextOperator) {
		this.nextOperator = nextOperator;
	}

	public String getSender() {
		return sender;
	}

	public void setSender(String sender) {
		this.sender = sender;
	}

	public String getProcessPromoter() {
		return processPromoter;
	}

	public void setProcessPromoter(String processPromoter) {
		this.processPromoter = processPromoter;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

//	public String getOwner() {
//		return owner;
//	}
//
//	public void setOwner(String owner) {
//		this.owner = owner;
//	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public String getProcessCode() {
		return processCode;
	}

	public void setProcessCode(String processCode) {
		this.processCode = processCode;
	}

	@Override
	public String getProcessName() {
		return processName;
	}

	@Override
	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public Date getExpired() {
		return expired;
	}

	public void setExpired(Date expired) {
		this.expired = expired;
	}

	public String getIsSendMsg() {
		return isSendMsg;
	}

	public void setIsSendMsg(String isSendMsg) {
		this.isSendMsg = isSendMsg;
	}

	public String getSendMsgType() {
		return sendMsgType;
	}

	public void setSendMsgType(String sendMsgType) {
		this.sendMsgType = sendMsgType;
	}

	public String getMsgContent() {
		return msgContent;
	}

	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}

	public Integer getIsSync() {
		return isSync;
	}

	public void setIsSync(Integer isSync) {
		this.isSync = isSync;
	}

	public Date getSyncDate() {
		return syncDate;
	}

	public void setSyncDate(Date syncDate) {
		this.syncDate = syncDate;
	}

	public Integer getSyncIsSuccess() {
		return syncIsSuccess;
	}

	public void setSyncIsSuccess(Integer syncIsSuccess) {
		this.syncIsSuccess = syncIsSuccess;
	}

	public Date getPrevTaskEndTime() {
		return prevTaskEndTime;
	}

	public void setPrevTaskEndTime(Date prevTaskEndTime) {
		this.prevTaskEndTime = prevTaskEndTime;
	}

	public Date getTaskStartTime() {
		return taskStartTime;
	}

	public void setTaskStartTime(Date taskStartTime) {
		this.taskStartTime = taskStartTime;
	}

	public Date getTaskEndTime() {
		return taskEndTime;
	}

	public void setTaskEndTime(Date taskEndTime) {
		this.taskEndTime = taskEndTime;
	}

}
