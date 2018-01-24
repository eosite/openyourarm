package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SQL_TRANS_LOG")
public class SqlTransportLog implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "DATABASEID_")
	protected Long databaseId;

	@Column(name = "SQLDEFID_")
	protected Long sqlDefId;

	@Column(name = "PROJECTID_")
	protected Long projectId;

	@Column(name = "STATUS_")
	protected Integer status;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "RUNYEAR_")
	protected Integer runYear;

	@Column(name = "RUNMONTH_")
	protected Integer runMonth;

	@Column(name = "RUNWEEK_")
	protected Integer runWeek;

	@Column(name = "RUNQUARTER_")
	protected Integer runQuarter;

	@Column(name = "RUNDAY_")
	protected Integer runDay;

	@Column(name = "JOBNO_", length = 50)
	protected String jobNo;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	public SqlTransportLog() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SqlTransportLog other = (SqlTransportLog) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
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

	public Long getDatabaseId() {
		return this.databaseId;
	}

	public Long getId() {
		return this.id;
	}

	public String getJobNo() {
		return this.jobNo;
	}

	public Long getProjectId() {
		return projectId;
	}

	public Integer getRunDay() {
		return this.runDay;
	}

	public Integer getRunMonth() {
		return this.runMonth;
	}

	public Integer getRunQuarter() {
		return this.runQuarter;
	}

	public Integer getRunWeek() {
		return this.runWeek;
	}

	public Integer getRunYear() {
		return this.runYear;
	}

	public Long getSqlDefId() {
		return this.sqlDefId;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getType() {
		return this.type;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public SqlTransportLog jsonToObject(JSONObject jsonObject) {
		return SqlTransportLogJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setJobNo(String jobNo) {
		this.jobNo = jobNo;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public void setRunDay(Integer runDay) {
		this.runDay = runDay;
	}

	public void setRunMonth(Integer runMonth) {
		this.runMonth = runMonth;
	}

	public void setRunQuarter(Integer runQuarter) {
		this.runQuarter = runQuarter;
	}

	public void setRunWeek(Integer runWeek) {
		this.runWeek = runWeek;
	}

	public void setRunYear(Integer runYear) {
		this.runYear = runYear;
	}

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public JSONObject toJsonObject() {
		return SqlTransportLogJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return SqlTransportLogJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
