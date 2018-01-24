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

package com.glaf.matrix.data.sync.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class CombineAppQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String titleLike;
	protected String sourceTableName;
	protected String sourceTableNameLike;
	protected String targetTableName;
	protected String targetTableNameLike;
	protected Long srcDatabaseId;
	protected String type;
	protected String autoSyncFlag;
	protected String active;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;

	public CombineAppQuery() {

	}

	public CombineAppQuery active(String active) {
		if (active == null) {
			throw new RuntimeException("active is null");
		}
		this.active = active;
		return this;
	}

	public CombineAppQuery autoSyncFlag(String autoSyncFlag) {
		if (autoSyncFlag == null) {
			throw new RuntimeException("autoSyncFlag is null");
		}
		this.autoSyncFlag = autoSyncFlag;
		return this;
	}

	public CombineAppQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public CombineAppQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public String getActive() {
		return active;
	}

	public String getAutoSyncFlag() {
		return autoSyncFlag;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("sourceTableName".equals(sortColumn)) {
				orderBy = "E.SOURCETABLENAME_" + a_x;
			}

			if ("targetTableName".equals(sortColumn)) {
				orderBy = "E.TARGETTABLENAME_" + a_x;
			}

			if ("srcDatabaseId".equals(sortColumn)) {
				orderBy = "E.SRCDATABASEID_" + a_x;
			}

			if ("targetDatabaseIds".equals(sortColumn)) {
				orderBy = "E.TARGETDATABASEIDS_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("autoSyncFlag".equals(sortColumn)) {
				orderBy = "E.AUTOSYNCFLAG_" + a_x;
			}

			if ("interval".equals(sortColumn)) {
				orderBy = "E.INTERVAL_" + a_x;
			}

			if ("active".equals(sortColumn)) {
				orderBy = "E.ACTIVE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

			if ("updateTime".equals(sortColumn)) {
				orderBy = "E.UPDATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	public String getSourceTableName() {
		return sourceTableName;
	}

	public String getSourceTableNameLike() {
		if (sourceTableNameLike != null && sourceTableNameLike.trim().length() > 0) {
			if (!sourceTableNameLike.startsWith("%")) {
				sourceTableNameLike = "%" + sourceTableNameLike;
			}
			if (!sourceTableNameLike.endsWith("%")) {
				sourceTableNameLike = sourceTableNameLike + "%";
			}
		}
		return sourceTableNameLike;
	}

	public Long getSrcDatabaseId() {
		return srcDatabaseId;
	}

	public String getTargetTableName() {
		return targetTableName;
	}

	public String getTargetTableNameLike() {
		if (targetTableNameLike != null && targetTableNameLike.trim().length() > 0) {
			if (!targetTableNameLike.startsWith("%")) {
				targetTableNameLike = "%" + targetTableNameLike;
			}
			if (!targetTableNameLike.endsWith("%")) {
				targetTableNameLike = targetTableNameLike + "%";
			}
		}
		return targetTableNameLike;
	}

	public String getTitleLike() {
		if (titleLike != null && titleLike.trim().length() > 0) {
			if (!titleLike.startsWith("%")) {
				titleLike = "%" + titleLike;
			}
			if (!titleLike.endsWith("%")) {
				titleLike = titleLike + "%";
			}
		}
		return titleLike;
	}

	public String getType() {
		return type;
	}

	public Date getUpdateTimeGreaterThanOrEqual() {
		return updateTimeGreaterThanOrEqual;
	}

	public Date getUpdateTimeLessThanOrEqual() {
		return updateTimeLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("title", "TITLE_");
		addColumn("sourceTableName", "SOURCETABLENAME_");
		addColumn("targetTableName", "TARGETTABLENAME_");
		addColumn("srcDatabaseId", "SRCDATABASEID_");
		addColumn("targetDatabaseIds", "TARGETDATABASEIDS_");
		addColumn("type", "TYPE_");
		addColumn("autoSyncFlag", "AUTOSYNCFLAG_");
		addColumn("interval", "INTERVAL_");
		addColumn("active", "ACTIVE_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setAutoSyncFlag(String autoSyncFlag) {
		this.autoSyncFlag = autoSyncFlag;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setSourceTableName(String sourceTableName) {
		this.sourceTableName = sourceTableName;
	}

	public void setSourceTableNameLike(String sourceTableNameLike) {
		this.sourceTableNameLike = sourceTableNameLike;
	}

	public void setSrcDatabaseId(Long srcDatabaseId) {
		this.srcDatabaseId = srcDatabaseId;
	}

	public void setTargetTableName(String targetTableName) {
		this.targetTableName = targetTableName;
	}

	public void setTargetTableNameLike(String targetTableNameLike) {
		this.targetTableNameLike = targetTableNameLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
	}

	public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
	}

	public CombineAppQuery sourceTableName(String sourceTableName) {
		if (sourceTableName == null) {
			throw new RuntimeException("sourceTableName is null");
		}
		this.sourceTableName = sourceTableName;
		return this;
	}

	public CombineAppQuery sourceTableNameLike(String sourceTableNameLike) {
		if (sourceTableNameLike == null) {
			throw new RuntimeException("sourceTableName is null");
		}
		this.sourceTableNameLike = sourceTableNameLike;
		return this;
	}

	public CombineAppQuery srcDatabaseId(Long srcDatabaseId) {
		if (srcDatabaseId == null) {
			throw new RuntimeException("srcDatabaseId is null");
		}
		this.srcDatabaseId = srcDatabaseId;
		return this;
	}

	public CombineAppQuery targetTableName(String targetTableName) {
		if (targetTableName == null) {
			throw new RuntimeException("targetTableName is null");
		}
		this.targetTableName = targetTableName;
		return this;
	}

	public CombineAppQuery targetTableNameLike(String targetTableNameLike) {
		if (targetTableNameLike == null) {
			throw new RuntimeException("targetTableName is null");
		}
		this.targetTableNameLike = targetTableNameLike;
		return this;
	}

	public CombineAppQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public CombineAppQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public CombineAppQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		if (updateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
		return this;
	}

	public CombineAppQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		if (updateTimeLessThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
		return this;
	}

}