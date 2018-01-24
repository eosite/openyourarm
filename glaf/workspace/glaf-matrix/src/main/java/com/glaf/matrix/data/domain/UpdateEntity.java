/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.glaf.matrix.data.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name = "SYS_UPDATE_ENTITY")
public class UpdateEntity implements java.io.Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "SYSTEMNAME_", length = 50)
	protected String systemName;

	@Lob
	@Column(name = "UPDATESQL_", length = 4000, nullable = false)
	protected String updateSql;

	@Column(name = "INTERVALSECONDS_")
	protected int intervalSeconds;

	@Column(name = "LASTEXECUTETIME_")
	protected long lastExecuteTime;

	public UpdateEntity() {

	}

	public String getId() {
		return id;
	}

	public int getIntervalSeconds() {
		return intervalSeconds;
	}

	public long getLastExecuteTime() {
		return lastExecuteTime;
	}

	public String getSystemName() {
		return systemName;
	}

	public String getUpdateSql() {
		return updateSql;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIntervalSeconds(int intervalSeconds) {
		this.intervalSeconds = intervalSeconds;
	}

	public void setLastExecuteTime(long lastExecuteTime) {
		this.lastExecuteTime = lastExecuteTime;
	}

	public void setSystemName(String systemName) {
		this.systemName = systemName;
	}

	public void setUpdateSql(String updateSql) {
		this.updateSql = updateSql;
	}

}
