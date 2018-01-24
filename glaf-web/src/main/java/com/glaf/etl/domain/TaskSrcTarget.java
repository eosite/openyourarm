package com.glaf.etl.domain;

import java.io.Serializable;

public class TaskSrcTarget implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 8935515812203842969L;
	protected String taskId;
	protected String srcId;
	protected String targetId;
	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}
	public String getSrcId() {
		return srcId;
	}
	public void setSrcId(String srcId) {
		this.srcId = srcId;
	}
	public String getTargetId() {
		return targetId;
	}
	public void setTargetId(String targetId) {
		this.targetId = targetId;
	}
}
