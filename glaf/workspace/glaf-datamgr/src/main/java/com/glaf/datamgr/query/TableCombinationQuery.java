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

public class TableCombinationQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected String nameLike;
	protected String titleLike;
	protected String type;
	protected String scheduleFlag;
	protected Integer syncStatus;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public TableCombinationQuery() {

	}

	public TableCombinationQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public TableCombinationQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
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

			if ("databaseIds".equals(sortColumn)) {
				orderBy = "E.DATABASEIDS_" + a_x;
			}

			if ("templateTableName".equals(sortColumn)) {
				orderBy = "E.TEMPLATETABLENAME_" + a_x;
			}

			if ("primaryKey".equals(sortColumn)) {
				orderBy = "E.PRIMARYKEY_" + a_x;
			}

			if ("targetTableName".equals(sortColumn)) {
				orderBy = "E.TARGETTABLENAME_" + a_x;
			}

			if ("createTableFlag".equals(sortColumn)) {
				orderBy = "E.CREATETABLEFLAG_" + a_x;
			}

			if ("deleteFetch".equals(sortColumn)) {
				orderBy = "E.DELETEFETCH_" + a_x;
			}

			if ("syncStatus".equals(sortColumn)) {
				orderBy = "E.SYNCSTATUS_" + a_x;
			}

			if ("syncTime".equals(sortColumn)) {
				orderBy = "E.SYNCTIME_" + a_x;
			}

			if ("sortNo".equals(sortColumn)) {
				orderBy = "E.SORTNO_" + a_x;
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

	public String getScheduleFlag() {
		return scheduleFlag;
	}

	public Integer getSyncStatus() {
		return syncStatus;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("type", "TYPE_");
		addColumn("databaseIds", "DATABASEIDS_");
		addColumn("templateTableName", "TEMPLATETABLENAME_");
		addColumn("primaryKey", "PRIMARYKEY_");
		addColumn("targetTableName", "TARGETTABLENAME_");
		addColumn("createTableFlag", "CREATETABLEFLAG_");
		addColumn("deleteFetch", "DELETEFETCH_");
		addColumn("syncStatus", "SYNCSTATUS_");
		addColumn("syncTime", "SYNCTIME_");
		addColumn("sortNo", "SORTNO_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public TableCombinationQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setSyncStatus(Integer syncStatus) {
		this.syncStatus = syncStatus;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public TableCombinationQuery syncStatus(Integer syncStatus) {
		if (syncStatus == null) {
			throw new RuntimeException("syncStatus is null");
		}
		this.syncStatus = syncStatus;
		return this;
	}

	public TableCombinationQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public TableCombinationQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

}