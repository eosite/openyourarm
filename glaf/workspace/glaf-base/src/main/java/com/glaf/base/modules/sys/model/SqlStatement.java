package com.glaf.base.modules.sys.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "SQL_STATEMENT")
public class SqlStatement implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "TITLE_", length = 50)
	protected String title;

	@Lob
	@Column(name = "SQL_", length = 4000)
	protected String sql;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "EXECUTETIME_")
	protected Date executeTime;

	@Column(name = "STATUS_")
	protected int status;

	@javax.persistence.Transient
	protected String tableExists;

	public SqlStatement() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SqlStatement other = (SqlStatement) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getExecuteTime() {
		return executeTime;
	}

	public String getId() {
		return id;
	}

	public String getSql() {
		return sql;
	}

	public int getStatus() {
		return status;
	}

	public String getTableExists() {
		return tableExists;
	}

	public String getTitle() {
		return title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setExecuteTime(Date executeTime) {
		this.executeTime = executeTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setSql(String sql) {
		this.sql = sql;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setTableExists(String tableExists) {
		this.tableExists = tableExists;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
