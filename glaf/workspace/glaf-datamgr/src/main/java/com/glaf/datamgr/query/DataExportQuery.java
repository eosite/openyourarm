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

package com.glaf.datamgr.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class DataExportQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String name;
	protected String nameLike;
	protected String titleLike;
	protected String type;
	protected String exportFlag;
	protected Date lastExportTimeGreaterThanOrEqual;
	protected Date lastExportTimeLessThanOrEqual;
	protected String scheduleFlag;
	protected String publicFlag;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;

	public DataExportQuery() {

	}

	public DataExportQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public DataExportQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public DataExportQuery exportFlag(String exportFlag) {
		if (exportFlag == null) {
			throw new RuntimeException("exportFlag is null");
		}
		this.exportFlag = exportFlag;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getExportFlag() {
		return exportFlag;
	}

	public Date getLastExportTimeGreaterThanOrEqual() {
		return lastExportTimeGreaterThanOrEqual;
	}

	public Date getLastExportTimeLessThanOrEqual() {
		return lastExportTimeLessThanOrEqual;
	}

	public String getName() {
		return name;
	}

	public String getNameLike() {
		if (nameLike != null && nameLike.trim().length() > 0) {
			if (!nameLike.startsWith("%")) {
				nameLike = "%" + nameLike;
			}
			if (!nameLike.endsWith("%")) {
				nameLike = nameLike + "%";
			}
		}
		return nameLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("operation".equals(sortColumn)) {
				orderBy = "E.OPERATION_" + a_x;
			}

			if ("exportFlag".equals(sortColumn)) {
				orderBy = "E.EXPORTFLAG_" + a_x;
			}

			if ("exportDBName".equals(sortColumn)) {
				orderBy = "E.EXPORTDBNAME_" + a_x;
			}

			if ("lastExportTime".equals(sortColumn)) {
				orderBy = "E.LASTEXPORTTIME_" + a_x;
			}

			if ("sqlDefIds".equals(sortColumn)) {
				orderBy = "E.SQLDEFIDS_" + a_x;
			}

			if ("scheduleFlag".equals(sortColumn)) {
				orderBy = "E.SCHEDULEFLAG_" + a_x;
			}

			if ("deleteFetch".equals(sortColumn)) {
				orderBy = "E.DELETEFETCH_" + a_x;
			}

			if ("publicFlag".equals(sortColumn)) {
				orderBy = "E.PUBLICFLAG_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
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

	public String getPublicFlag() {
		return publicFlag;
	}

	public String getScheduleFlag() {
		return scheduleFlag;
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
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("type", "TYPE_");
		addColumn("operation", "OPERATION_");
		addColumn("exportFlag", "EXPORTFLAG_");
		addColumn("exportDBName", "EXPORTDBNAME_");
		addColumn("lastExportTime", "LASTEXPORTTIME_");
		addColumn("sqlDefIds", "SQLDEFIDS_");
		addColumn("scheduleFlag", "SCHEDULEFLAG_");
		addColumn("deleteFetch", "DELETEFETCH_");
		addColumn("publicFlag", "PUBLICFLAG_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public DataExportQuery lastExportTimeGreaterThanOrEqual(Date lastExportTimeGreaterThanOrEqual) {
		if (lastExportTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("lastExportTime is null");
		}
		this.lastExportTimeGreaterThanOrEqual = lastExportTimeGreaterThanOrEqual;
		return this;
	}

	public DataExportQuery lastExportTimeLessThanOrEqual(Date lastExportTimeLessThanOrEqual) {
		if (lastExportTimeLessThanOrEqual == null) {
			throw new RuntimeException("lastExportTime is null");
		}
		this.lastExportTimeLessThanOrEqual = lastExportTimeLessThanOrEqual;
		return this;
	}

	public DataExportQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DataExportQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DataExportQuery publicFlag(String publicFlag) {
		if (publicFlag == null) {
			throw new RuntimeException("publicFlag is null");
		}
		this.publicFlag = publicFlag;
		return this;
	}

	public DataExportQuery scheduleFlag(String scheduleFlag) {
		if (scheduleFlag == null) {
			throw new RuntimeException("scheduleFlag is null");
		}
		this.scheduleFlag = scheduleFlag;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setExportFlag(String exportFlag) {
		this.exportFlag = exportFlag;
	}

	public void setLastExportTimeGreaterThanOrEqual(Date lastExportTimeGreaterThanOrEqual) {
		this.lastExportTimeGreaterThanOrEqual = lastExportTimeGreaterThanOrEqual;
	}

	public void setLastExportTimeLessThanOrEqual(Date lastExportTimeLessThanOrEqual) {
		this.lastExportTimeLessThanOrEqual = lastExportTimeLessThanOrEqual;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
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

	public DataExportQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public DataExportQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DataExportQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		if (updateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
		return this;
	}

	public DataExportQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		if (updateTimeLessThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
		return this;
	}

}