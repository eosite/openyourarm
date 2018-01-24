package com.glaf.datamgr.jdbc;

import java.sql.*;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

public class ConnectionInfo {

	protected String id;

	protected String datasetId;

	protected String actorId;

	protected long startTime;

	protected Connection connection;

	public ConnectionInfo() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConnectionInfo other = (ConnectionInfo) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getActorId() {
		return actorId;
	}

	public Connection getConnection() {
		return connection;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public String getId() {
		return id;
	}

	public long getStartTime() {
		return startTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStartTime(long startTime) {
		this.startTime = startTime;
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
