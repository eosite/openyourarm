package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.StringTools;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_TASK_TABLE")
public class TaskTable implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 20)
	protected String type;

	/**
	 * 表名
	 */
	@Column(name = "TABLENAME_", length = 50)
	protected String tableName;

	/**
	 * 主键字段
	 */
	@Column(name = "IDCOLUMN_", length = 50)
	protected String idColumn;

	/**
	 * 名称字段
	 */
	@Column(name = "NAMECOLUMN_", length = 250)
	protected String nameColumn;

	/**
	 * 名称表达式
	 */
	@Column(name = "NAMEEXPRESSION_", length = 250)
	protected String nameExpression;

	/**
	 * 值字段
	 */
	@Column(name = "VALUECOLUMN_", length = 250)
	protected String valueColumn;

	/**
	 * 值表达式
	 */
	@Column(name = "VALUEEXPRESSION_", length = 250)
	protected String valueExpression;

	/**
	 * 类型字段
	 */
	@Column(name = "TYPECOLUMN_", length = 250)
	protected String typeColumn;

	/**
	 * 开始日期字段
	 */
	@Column(name = "STARTDATECOLUMN_", length = 50)
	protected String startDateColumn;

	/**
	 * 结束日期字段
	 */
	@Column(name = "ENDDATECOLUMN_", length = 50)
	protected String endDateColumn;

	/**
	 * 数据库编号
	 */
	@Column(name = "DATABASEIDS_", length = 2000)
	protected String databaseIds;

	/**
	 * 同步列
	 */
	@Column(name = "SYNCCOLUMNS_", length = 4000)
	protected String syncColumns;

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
	 * 频率
	 */
	@Column(name = "FREQUENCY_")
	protected int frequency;

	/**
	 * 执行天数
	 */
	@Column(name = "EXECUTEDAY_")
	protected int executeDay;

	/**
	 * 顺序
	 */
	@Column(name = "SORTNO_")
	protected int sortNo;

	/**
	 * DeleteFlag
	 */
	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	public TaskTable() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TaskTable other = (TaskTable) obj;
		if (id != other.id)
			return false;
		return true;
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

	public String getDatabaseIds() {
		return databaseIds;
	}

	public int getDeleteFlag() {
		return this.deleteFlag;
	}

	public String getEndDateColumn() {
		return this.endDateColumn;
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

	public int getExecuteDay() {
		return executeDay;
	}

	public int getFrequency() {
		return this.frequency;
	}

	public long getId() {
		return this.id;
	}

	public String getIdColumn() {
		return this.idColumn;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getNameColumn() {
		return this.nameColumn;
	}

	public String getNameExpression() {
		return this.nameExpression;
	}

	public int getSortNo() {
		return this.sortNo;
	}

	public String getStartDateColumn() {
		return this.startDateColumn;
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

	public String getSyncColumns() {
		if (syncColumns != null) {
			syncColumns = syncColumns.trim();
			syncColumns = syncColumns.toLowerCase();
			List<String> columns = StringTools.split(syncColumns);
			if (columns != null && !columns.isEmpty()) {
				if (StringUtils.isNotEmpty(idColumn)) {
					if (!columns.contains(idColumn.toLowerCase())) {
						syncColumns = idColumn + ", " + syncColumns;
					}
				}
			}
		}
		return syncColumns;
	}

	public String getTableName() {
		return this.tableName;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getTypeColumn() {
		return this.typeColumn;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateTimeString() {
		if (this.updateTime != null) {
			return DateUtils.getDateTime(this.updateTime);
		}
		return "";
	}

	public String getValueColumn() {
		return valueColumn;
	}

	public String getValueExpression() {
		return valueExpression;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public TaskTable jsonToObject(JSONObject jsonObject) {
		return TaskTableJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseIds(String databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setEndDateColumn(String endDateColumn) {
		this.endDateColumn = endDateColumn;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	public void setExecuteDay(int executeDay) {
		this.executeDay = executeDay;
	}

	public void setFrequency(int frequency) {
		this.frequency = frequency;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setIdColumn(String idColumn) {
		this.idColumn = idColumn;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setNameColumn(String nameColumn) {
		this.nameColumn = nameColumn;
	}

	public void setNameExpression(String nameExpression) {
		this.nameExpression = nameExpression;
	}

	public void setSortNo(int sortNo) {
		this.sortNo = sortNo;
	}

	public void setStartDateColumn(String startDateColumn) {
		this.startDateColumn = startDateColumn;
	}

	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	public void setSyncColumns(String syncColumns) {
		this.syncColumns = syncColumns;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypeColumn(String typeColumn) {
		this.typeColumn = typeColumn;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setValueColumn(String valueColumn) {
		this.valueColumn = valueColumn;
	}

	public void setValueExpression(String valueExpression) {
		this.valueExpression = valueExpression;
	}

	public JSONObject toJsonObject() {
		return TaskTableJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return TaskTableJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
