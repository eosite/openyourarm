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

public class SqlConditionQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected Long sqlDefId;
	protected String name;
	protected String nameLike;

	public SqlConditionQuery() {

	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public Long getSqlDefId() {
		return sqlDefId;
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

	public void setSqlDefId(Long sqlDefId) {
		this.sqlDefId = sqlDefId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public SqlConditionQuery sqlDefId(Long sqlDefId) {
		if (sqlDefId == null) {
			throw new RuntimeException("sqlDefId is null");
		}
		this.sqlDefId = sqlDefId;
		return this;
	}

	public SqlConditionQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public SqlConditionQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("sqlDefId".equals(sortColumn)) {
				orderBy = "E.SQLDEFID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("required".equals(sortColumn)) {
				orderBy = "E.REQUIRED_" + a_x;
			}

			if ("collection".equals(sortColumn)) {
				orderBy = "E.COLLECTION_" + a_x;
			}

			if ("operator".equals(sortColumn)) {
				orderBy = "E.OPERATOR_" + a_x;
			}

			if ("segment".equals(sortColumn)) {
				orderBy = "E.SEGMENT_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("sqlDefId", "SQLDEFID_");
		addColumn("name", "NAME_");
		addColumn("type", "TYPE_");
		addColumn("required", "REQUIRED_");
		addColumn("collection", "COLLECTION_");
		addColumn("operator", "OPERATOR_");
		addColumn("segment", "SEGMENT_");
		addColumn("locked", "LOCKED_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

}