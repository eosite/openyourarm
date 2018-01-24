package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "PAGE_PROCESSINSTANCE")
public class PageProcessInstance implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "PROCESSINSTANCEID_", length = 200, nullable = false)
	protected String processInstanceId;

	/**
	 * 页面编号
	 */
	@Column(name = "PAGEID_", length = 50)
	protected String pageId;

	/**
	 * 流程名称
	 */
	@Column(name = "PROCESSNAME_", length = 100)
	protected String processName;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 业务表名
	 */
	@Column(name = "BUSINESSTABLE_", length = 50)
	protected String businessTable;

	/**
	 * 业务主键
	 */
	@Column(name = "BUSINESSKEY_", length = 500)
	protected String businessKey;

	/**
	 * 业务状态
	 */
	@Column(name = "STATUS_")
	protected int status;

	/**
	 * 流程状态
	 */
	@Column(name = "WFSTATUS_")
	protected int wfStatus;

	/**
	 * 顺序号
	 */
	@Column(name = "SORTNO_")
	protected int sortNo;

	/**
	 * 锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 删除标记
	 */
	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	/**
	 * 版本号
	 */
	@Column(name = "VERSION_")
	protected int version;

	/**
	 * 开始时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "STARTTIME_")
	protected Date startTime;

	/**
	 * 结束时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "ENDTIME_")
	protected Date endTime;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public PageProcessInstance() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		PageProcessInstance other = (PageProcessInstance) obj;
		if (processInstanceId == null) {
			if (other.processInstanceId != null)
				return false;
		} else if (!processInstanceId.equals(other.processInstanceId))
			return false;
		return true;
	}

	public String getBusinessKey() {
		return this.businessKey;
	}

	public String getBusinessTable() {
		return this.businessTable;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public int getDeleteFlag() {
		return this.deleteFlag;
	}

	public Date getEndTime() {
		return this.endTime;
	}

	public String getEndTimeString() {
		if (this.endTime != null) {
			return DateUtils.getDateTime(this.endTime);
		}
		return "";
	}

	public int getLocked() {
		return this.locked;
	}

	public String getPageId() {
		return this.pageId;
	}

	public String getProcessInstanceId() {
		return this.processInstanceId;
	}

	public String getProcessName() {
		return this.processName;
	}

	public int getSortNo() {
		return this.sortNo;
	}

	public Date getStartTime() {
		return this.startTime;
	}

	public String getStartTimeString() {
		if (this.startTime != null) {
			return DateUtils.getDateTime(this.startTime);
		}
		return "";
	}

	public int getStatus() {
		return this.status;
	}

	public String getTitle() {
		return title;
	}

	public int getVersion() {
		return this.version;
	}

	public int getWfStatus() {
		return this.wfStatus;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((processInstanceId == null) ? 0 : processInstanceId.hashCode());
		return result;
	}

	public PageProcessInstance jsonToObject(JSONObject jsonObject) {
		return PageProcessInstanceJsonFactory.jsonToObject(jsonObject);
	}

	public void setBusinessKey(String businessKey) {
		this.businessKey = businessKey;
	}

	public void setBusinessTable(String businessTable) {
		this.businessTable = businessTable;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public void setProcessInstanceId(String processInstanceId) {
		this.processInstanceId = processInstanceId;
	}

	public void setProcessName(String processName) {
		this.processName = processName;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public void setWfStatus(int wfStatus) {
		this.wfStatus = wfStatus;
	}

	public JSONObject toJsonObject() {
		return PageProcessInstanceJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return PageProcessInstanceJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
