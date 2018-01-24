package com.glaf.workflow.core.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.alibaba.fastjson.JSONObject;
import com.glaf.core.base.JSONable;

@Entity
@Table(name = "PAGE_USER_TASK_HISTORY")
public class PageUserTaskHistory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;
	
	@Column(name="PARENTID_",length=36)
	protected String pid;

	/**
	 * 标记是待办、已办等。1：待办；2：已办；3待阅；4：已阅；5：流程结束；6：废单
	 */
	@Column(name = "TASK_", length = 2)
	protected String task;

	/**
	 * 操作。1：新增；2：删除；3：强制进入下一流程
	 */
	@Column(name = "ACTION_", length = 2)
	protected String action;

	/**
	 * 待办类型
	 */
	@Column(name = "TASKTYPE_", length = 3)
	protected String taskType;

	/**
	 * 待办任务编号
	 */
	@Column(name = "TASKNO_", length = 32)
	protected String taskNo;

	/**
	 * 流程活动编号
	 */
	@Column(name = "ACTIVITYCODE_", length = 32)
	protected String activityCode;

	/**
	 * 流程活动名称
	 */
	@Column(name = "ACTIVITYNAME_", length = 128)
	protected String activityName;

	/**
	 * 上一流程处理人
	 */
	@Column(name = "PREVOPERATOR_", length = 32)
	protected String prevOperator;

	/**
	 * 下一流程处理人
	 */
	@Column(name = "NEXTOPERATOR_", length = 32)
	protected String nextOperator;

	/**
	 * 提交人，即当前流程处理人
	 */
	@Column(name = "SENDER_", length = 32)
	protected String sender;

	/**
	 * 流程发起人
	 */
	@Column(name = "PROCESSPROMOTER_", length = 32)
	protected String processPromoter;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 1024)
	protected String title;

	/**
	 * 流程查看链接
	 */
	@Column(name = "HREF_", length = 1024)
	protected String href;

	/**
	 * 所有者
	 */
	@Column(name = "OWNER_", length = 32)
	protected String owner;

	/**
	 * 紧急程度。1：正常；2：急；3：紧急
	 */
	@Column(name = "PRIORITY_", length = 2)
	protected String priority;

	/**
	 * 流程编号
	 */
	@Column(name = "PROCESSCODE_", length = 32)
	protected String processCode;

	/**
	 * 流程名称
	 */
	@Column(name = "PROCESSNAME_", length = 32)
	protected String processName;

	/**
	 * 过期时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXPIRED_")
	protected Date expired;

	/**
	 * 是否发送消息。0：否; 1:是
	 */
	@Column(name = "ISSENDMSG_", length = 2)
	protected String isSendMsg;

	/**
	 * 发送方式。1：短信；2：邮件
	 */
	@Column(name = "SENDMSGTYPE_", length = 2)
	protected String sendMsgType;

	/**
	 * 发送内容
	 */
	@Column(name = "MSGCONTENT_", length = 4096)
	protected String msgContent;

	/**
	 * 是否已同步。0：否；1：是
	 */
	@Column(name = "ISSYNC_")
	protected Integer isSync;

	/**
	 * 同步时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "SYNCDATE_")
	protected Date syncDate;

	/**
	 * 同步成功标记 0：失败；1：成功
	 */
	@Column(name = "SYNC_IS_SUCCESS")
	protected Integer syncIsSuccess;

	/**
	 * 同步完成消息
	 */
	@Column(name = "RESULT_MSG")
	protected String resultmsg;

	/**
	 * 上一个任务结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PREV_TASK_END_TIME")
	protected Date prevTaskEndTime;

	/**
	 * 当前任务开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TASK_START_TIME")
	protected Date taskStartTime;

	/**
	 * 当前任务结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "TASK_END_TIME")
	protected Date taskEndTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPid() {
		return pid;
	}

	public void setPid(String pid) {
		this.pid = pid;
	}

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

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

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

	public String getProcessName() {
		return processName;
	}

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

	public String getResultmsg() {
		return resultmsg;
	}

	public void setResultmsg(String resultmsg) {
		this.resultmsg = resultmsg;
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

	@Override
	public Object jsonToObject(JSONObject jsonObject) {
		this.id = jsonObject.getString("id");
		if(jsonObject.getString("pid")!=null){
			this.pid = jsonObject.getString("pid");
		}
		if (jsonObject.getString("task") != null) {
			this.task = jsonObject.getString("task");
		}
		if (jsonObject.getString("action") != null) {
			this.action = jsonObject.getString("action");
		}
		if (jsonObject.getString("taskType") != null) {
			this.taskType = jsonObject.getString("taskType");
		}
		if (jsonObject.getString("taskNo") != null) {
			this.taskNo = jsonObject.getString("taskNo");
		}
		if (jsonObject.getString("activityCode") != null) {
			this.activityCode = jsonObject.getString("activityCode");
		}
		if (jsonObject.getString("activityName") != null) {
			this.activityName = jsonObject.getString("activityName");
		}
		if (jsonObject.getString("prevOperator") != null) {
			this.prevOperator = jsonObject.getString("prevOperator");
		}
		if (jsonObject.getString("nextOperator") != null) {
			this.nextOperator = jsonObject.getString("nextOperator");
		}
		if (jsonObject.getString("sender") != null) {
			this.sender = jsonObject.getString("sender");
		}
		if (jsonObject.getString("processPromoter") != null) {
			this.processPromoter = jsonObject.getString("processPromoter");
		}
		if (jsonObject.getString("title") != null) {
			this.title = jsonObject.getString("title");
		}
		if (jsonObject.getString("href") != null) {
			this.href = jsonObject.getString("href");
		}
		if (jsonObject.getString("owner") != null) {
			this.owner = jsonObject.getString("owner");
		}
		if (jsonObject.getString("processCode") != null) {
			this.processCode = jsonObject.getString("processCode");
		}
		if (jsonObject.getString("processName") != null) {
			this.processName = jsonObject.getString("processName");
		}
		if (jsonObject.getString("expired") != null) {
			this.expired = jsonObject.getDate("expired");
		}
		if (jsonObject.getString("isSendMsg") != null) {
			this.isSendMsg = jsonObject.getString("isSendMsg");
		}
		if (jsonObject.getString("sendMsgType") != null) {
			this.sendMsgType = jsonObject.getString("sendMsgType");
		}
		if (jsonObject.getString("msgContent") != null) {
			this.msgContent = jsonObject.getString("msgContent");
		}
		if (jsonObject.getInteger("isSync") != null) {
			this.isSync = jsonObject.getInteger("isSync");
		}
		if (jsonObject.getString("syncDate") != null) {
			this.syncDate = jsonObject.getDate("syncDate");
		}
		if (jsonObject.getInteger("syncIsSuccess") != null) {
			this.syncIsSuccess = jsonObject.getInteger("syncIsSuccess");
		}
		if (jsonObject.getString("resultmsg") != null) {
			this.resultmsg = jsonObject.getString("resultmsg");
		}
		if (jsonObject.getString("prevTaskEndTime") != null) {
			this.prevTaskEndTime = jsonObject.getDate("prevTaskEndTime");
		}
		if (jsonObject.getString("taskEndTime") != null) {
			this.taskEndTime = jsonObject.getDate("taskEndTime");
		}
		if (jsonObject.getString("taskStartTime") != null) {
			this.taskStartTime = jsonObject.getDate("taskStartTime");
		}
		return this;
	}

	@Override
	public JSONObject toJsonObject() {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", this.id);
		jsonObject.put("pid", this.pid);
		if (this.task != null) {
			jsonObject.put("task", this.task);
		}

		if (this.action != null) {
			jsonObject.put("action", this.action);
		}
		if (this.taskType != null) {
			jsonObject.put("taskType", this.taskType);
		}
		if (this.taskNo != null) {
			jsonObject.put("taskNo", this.taskNo);
		}
		if (this.activityCode != null) {
			jsonObject.put("activityCode", this.activityCode);
		}
		if (this.activityName != null) {
			jsonObject.put("activityName", this.activityName);
		}
		if (this.prevOperator != null) {
			jsonObject.put("prevOperator", this.prevOperator);
		}
		if (this.nextOperator != null) {
			jsonObject.put("nextOperator", this.nextOperator);
		}
		if (this.sender != null) {
			jsonObject.put("sender", this.sender);
		}
		if (this.processPromoter != null) {
			jsonObject.put("processPromoter", this.processPromoter);
		}
		if (this.title != null) {
			jsonObject.put("title", this.title);
		}
		if (this.href != null) {
			jsonObject.put("href", this.href);
		}
		if (this.owner != null) {
			jsonObject.put("owner", this.owner);
		}
		if (this.priority != null) {
			jsonObject.put("priority", this.priority);
		}
		if (this.processCode != null) {
			jsonObject.put("processCode", this.processCode);
		}
		if (this.processName != null) {
			jsonObject.put("processName", this.processName);
		}
		if (this.expired != null) {
			jsonObject.put("expired", this.expired);
		}
		if (this.isSendMsg != null) {
			jsonObject.put("isSendMsg", this.isSendMsg);
		}
		if (this.sendMsgType != null) {
			jsonObject.put("sendMsgType", this.sendMsgType);
		}
		if (this.msgContent != null) {
			jsonObject.put("msgContent", this.msgContent);
		}
		if (this.isSync != null) {
			jsonObject.put("isSync", this.isSync);
		}
		if (this.syncDate != null) {
			jsonObject.put("syncDate", this.syncDate);
		}
		if (this.syncIsSuccess != null) {
			jsonObject.put("syncIsSuccess", this.syncIsSuccess);
		}
		if (this.resultmsg != null) {
			jsonObject.put("resultmsg", this.resultmsg);
		}
		if (this.prevTaskEndTime != null) {
			jsonObject.put("prevTaskEndTime", this.prevTaskEndTime);
		}
		if (this.taskEndTime != null) {
			jsonObject.put("taskEndTime", this.taskEndTime);
		}
		if (this.taskStartTime != null) {
			jsonObject.put("taskStartTime", this.taskStartTime);
		}
		return jsonObject;
	}
}
