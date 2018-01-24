package com.glaf.isdp.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.util.CellMyTaskMainJsonFactory;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "CELL_MYTASKMAIN")
public class CellMyTaskMain implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID", nullable = false)
	protected String id;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CTIME")
	protected Date ctime;

	@Column(name = "INDEX_ID")
	protected Integer indexId;

	@Column(name = "TASK_ID", length = 50)
	protected String taskId;

	@Column(name = "NAME", length = 250)
	protected String name;

	@Column(name = "PROJNAME", length = 250)
	protected String projName;

	@Column(name = "LISTNO")
	protected Integer listNo;

	@Column(name = "TYPE_INDEX_ID")
	protected Integer typeIndexId;

	@Column(name = "FLAGINT")
	protected Integer flagInt;

	@Column(name = "MYCELL_TSKS_ID", length = 50)
	protected String myCellTsksId;

	@Column(name = "FROMTASKSID", length = 50)
	protected String fromTasksId;

	@Column(name = "TOTASKID", length = 50)
	protected String toTaskId;

	@Column(name = "INTFINISH")
	protected Integer intFinish;

	@Lob
	@Column(name = "FILE_CONTENT")
	protected byte[] fileContent;

	@Column(name = "TYPE_TABLENAME", length = 50)
	protected String typeTableName;

	@Column(name = "TYPE_ID", length = 50)
	protected String typeId;

	@Column(name = "USERID", length = 20)
	protected String userId;

	@Column(name = "NETROLEID", length = 50)
	protected String netRoleId;

	@Column(name = "INTISFLOW")
	protected Integer intIsFlow;

	@Column(name = "INTSTOP")
	protected Integer intStop;

	@Column(name = "FILETYPE_INDEX")
	protected Integer fileTypeIndex;
	
	@javax.persistence.Transient
	protected String processDId;

	@javax.persistence.Transient
	protected String processId;
	
	@javax.persistence.Transient
	protected String taskName;

	public CellMyTaskMain() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Date getCtime() {
		return this.ctime;
	}

	public String getCtimeString() {
		if (this.ctime != null) {
			return DateUtils.getDateTime(this.ctime);
		}
		return "";
	}

	public Integer getIndexId() {
		return this.indexId;
	}

	public String getTaskId() {
		return this.taskId;
	}

	public String getName() {
		return this.name;
	}

	public String getProjName() {
		return this.projName;
	}

	public Integer getListNo() {
		return this.listNo;
	}

	public Integer getTypeIndexId() {
		return this.typeIndexId;
	}

	public Integer getFlagInt() {
		return this.flagInt;
	}

	public String getMyCellTsksId() {
		return this.myCellTsksId;
	}

	public String getFromTasksId() {
		return this.fromTasksId;
	}

	public String getToTaskId() {
		return this.toTaskId;
	}

	public Integer getIntFinish() {
		return this.intFinish;
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public String getTypeTableName() {
		return this.typeTableName;
	}

	public String getTypeId() {
		return this.typeId;
	}

	public String getUserId() {
		return this.userId;
	}

	public String getNetRoleId() {
		return this.netRoleId;
	}

	public Integer getIntIsFlow() {
		return this.intIsFlow;
	}

	public Integer getIntStop() {
		return this.intStop;
	}

	public Integer getFileTypeIndex() {
		return this.fileTypeIndex;
	}

	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}

	public void setIndexId(Integer indexId) {
		this.indexId = indexId;
	}

	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
	}

	public void setTypeIndexId(Integer typeIndexId) {
		this.typeIndexId = typeIndexId;
	}

	public void setFlagInt(Integer flagInt) {
		this.flagInt = flagInt;
	}

	public void setMyCellTsksId(String myCellTsksId) {
		this.myCellTsksId = myCellTsksId;
	}

	public void setFromTasksId(String fromTasksId) {
		this.fromTasksId = fromTasksId;
	}

	public void setToTaskId(String toTaskId) {
		this.toTaskId = toTaskId;
	}

	public void setIntFinish(Integer intFinish) {
		this.intFinish = intFinish;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setTypeTableName(String typeTableName) {
		this.typeTableName = typeTableName;
	}

	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNetRoleId(String netRoleId) {
		this.netRoleId = netRoleId;
	}

	public void setIntIsFlow(Integer intIsFlow) {
		this.intIsFlow = intIsFlow;
	}

	public void setIntStop(Integer intStop) {
		this.intStop = intStop;
	}

	public void setFileTypeIndex(Integer fileTypeIndex) {
		this.fileTypeIndex = fileTypeIndex;
	}

	public String getProcessDId() {
		return processDId;
	}

	public void setProcessDId(String processDId) {
		this.processDId = processDId;
	}

	public String getProcessId() {
		return processId;
	}

	public void setProcessId(String processId) {
		this.processId = processId;
	}

	public String getTaskName() {
		return taskName;
	}

	public void setTaskName(String taskName) {
		this.taskName = taskName;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CellMyTaskMain other = (CellMyTaskMain) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public CellMyTaskMain jsonToObject(JSONObject jsonObject) {
		return CellMyTaskMainJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return CellMyTaskMainJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return CellMyTaskMainJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
