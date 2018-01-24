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

public class CombineHistoryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Long combineId;
	protected Long databaseId;
	protected List<Long> databaseIds;
	protected String databaseNameLike;
	protected Integer totalGreaterThanOrEqual;
	protected Integer totalLessThanOrEqual;
	protected Integer totalTimeGreaterThanOrEqual;
	protected Integer totalTimeLessThanOrEqual;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public CombineHistoryQuery() {

	}

	public CombineHistoryQuery combineId(Long combineId) {
		if (combineId == null) {
			throw new RuntimeException("combineId is null");
		}
		this.combineId = combineId;
		return this;
	}

	public CombineHistoryQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public CombineHistoryQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public CombineHistoryQuery databaseId(Long databaseId) {
		if (databaseId == null) {
			throw new RuntimeException("databaseId is null");
		}
		this.databaseId = databaseId;
		return this;
	}

	public CombineHistoryQuery databaseIds(List<Long> databaseIds) {
		if (databaseIds == null) {
			throw new RuntimeException("databaseIds is empty ");
		}
		this.databaseIds = databaseIds;
		return this;
	}

	public CombineHistoryQuery databaseNameLike(String databaseNameLike) {
		if (databaseNameLike == null) {
			throw new RuntimeException("databaseName is null");
		}
		this.databaseNameLike = databaseNameLike;
		return this;
	}

	public Long getCombineId() {
		return combineId;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public List<Long> getDatabaseIds() {
		return databaseIds;
	}

	public String getDatabaseNameLike() {
		if (databaseNameLike != null && databaseNameLike.trim().length() > 0) {
			if (!databaseNameLike.startsWith("%")) {
				databaseNameLike = "%" + databaseNameLike;
			}
			if (!databaseNameLike.endsWith("%")) {
				databaseNameLike = databaseNameLike + "%";
			}
		}
		return databaseNameLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("combineId".equals(sortColumn)) {
				orderBy = "E.COMBINEID_" + a_x;
			}

			if ("databaseId".equals(sortColumn)) {
				orderBy = "E.DATABASEID_" + a_x;
			}

			if ("databaseName".equals(sortColumn)) {
				orderBy = "E.DATABASENAME_" + a_x;
			}

			if ("status".equals(sortColumn)) {
				orderBy = "E.STATUS_" + a_x;
			}

			if ("total".equals(sortColumn)) {
				orderBy = "E.TOTAL_" + a_x;
			}

			if ("totalTime".equals(sortColumn)) {
				orderBy = "E.TOTALTIME_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("createTime".equals(sortColumn)) {
				orderBy = "E.CREATETIME_" + a_x;
			}

		}
		return orderBy;
	}

	public Integer getTotalGreaterThanOrEqual() {
		return totalGreaterThanOrEqual;
	}

	public Integer getTotalLessThanOrEqual() {
		return totalLessThanOrEqual;
	}

	public Integer getTotalTimeGreaterThanOrEqual() {
		return totalTimeGreaterThanOrEqual;
	}

	public Integer getTotalTimeLessThanOrEqual() {
		return totalTimeLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("combineId", "COMBINEID_");
		addColumn("databaseId", "DATABASEID_");
		addColumn("databaseName", "DATABASENAME_");
		addColumn("status", "STATUS_");
		addColumn("total", "TOTAL_");
		addColumn("totalTime", "TOTALTIME_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
	}

	public void setCombineId(Long combineId) {
		this.combineId = combineId;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDatabaseIds(List<Long> databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setDatabaseNameLike(String databaseNameLike) {
		this.databaseNameLike = databaseNameLike;
	}

	public void setTotalGreaterThanOrEqual(Integer totalGreaterThanOrEqual) {
		this.totalGreaterThanOrEqual = totalGreaterThanOrEqual;
	}

	public void setTotalLessThanOrEqual(Integer totalLessThanOrEqual) {
		this.totalLessThanOrEqual = totalLessThanOrEqual;
	}

	public void setTotalTimeGreaterThanOrEqual(Integer totalTimeGreaterThanOrEqual) {
		this.totalTimeGreaterThanOrEqual = totalTimeGreaterThanOrEqual;
	}

	public void setTotalTimeLessThanOrEqual(Integer totalTimeLessThanOrEqual) {
		this.totalTimeLessThanOrEqual = totalTimeLessThanOrEqual;
	}

	public CombineHistoryQuery totalGreaterThanOrEqual(Integer totalGreaterThanOrEqual) {
		if (totalGreaterThanOrEqual == null) {
			throw new RuntimeException("total is null");
		}
		this.totalGreaterThanOrEqual = totalGreaterThanOrEqual;
		return this;
	}

	public CombineHistoryQuery totalLessThanOrEqual(Integer totalLessThanOrEqual) {
		if (totalLessThanOrEqual == null) {
			throw new RuntimeException("total is null");
		}
		this.totalLessThanOrEqual = totalLessThanOrEqual;
		return this;
	}

	public CombineHistoryQuery totalTimeGreaterThanOrEqual(Integer totalTimeGreaterThanOrEqual) {
		if (totalTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("totalTime is null");
		}
		this.totalTimeGreaterThanOrEqual = totalTimeGreaterThanOrEqual;
		return this;
	}

	public CombineHistoryQuery totalTimeLessThanOrEqual(Integer totalTimeLessThanOrEqual) {
		if (totalTimeLessThanOrEqual == null) {
			throw new RuntimeException("totalTime is null");
		}
		this.totalTimeLessThanOrEqual = totalTimeLessThanOrEqual;
		return this;
	}

}