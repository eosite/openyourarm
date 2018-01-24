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

public class DataSetAuditQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Collection<String> appActorIds;
	protected String datasetId;
	protected List<String> datasetIds;
	protected Integer version;
	protected Integer versionGreaterThanOrEqual;
	protected Integer versionLessThanOrEqual;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public DataSetAuditQuery() {

	}

	public DataSetAuditQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public DataSetAuditQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public DataSetAuditQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public DataSetAuditQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public DataSetAuditQuery datasetId(String datasetId) {
		if (datasetId == null) {
			throw new RuntimeException("datasetId is null");
		}
		this.datasetId = datasetId;
		return this;
	}

	public DataSetAuditQuery datasetIds(List<String> datasetIds) {
		if (datasetIds == null) {
			throw new RuntimeException("datasetIds is empty ");
		}
		this.datasetIds = datasetIds;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public String getCreateBy() {
		return createBy;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateTimeGreaterThanOrEqual() {
		return createTimeGreaterThanOrEqual;
	}

	public Date getCreateTimeLessThanOrEqual() {
		return createTimeLessThanOrEqual;
	}

	public String getDatasetId() {
		return datasetId;
	}

	public List<String> getDatasetIds() {
		return datasetIds;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("datasetId".equals(sortColumn)) {
				orderBy = "E.DATASETID_" + a_x;
			}

			if ("content".equals(sortColumn)) {
				orderBy = "E.CONTENT_" + a_x;
			}

			if ("version".equals(sortColumn)) {
				orderBy = "E.VERSION_" + a_x;
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

	public Integer getVersion() {
		return version;
	}

	public Integer getVersionGreaterThanOrEqual() {
		return versionGreaterThanOrEqual;
	}

	public Integer getVersionLessThanOrEqual() {
		return versionLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("datasetId", "DATASETID_");
		addColumn("content", "CONTENT_");
		addColumn("version", "VERSION_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
	}

	public void setCreateTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
	}

	public void setDatasetId(String datasetId) {
		this.datasetId = datasetId;
	}

	public void setDatasetIds(List<String> datasetIds) {
		this.datasetIds = datasetIds;
	}

	public void setVersion(Integer version) {
		this.version = version;
	}

	public void setVersionGreaterThanOrEqual(Integer versionGreaterThanOrEqual) {
		this.versionGreaterThanOrEqual = versionGreaterThanOrEqual;
	}

	public void setVersionLessThanOrEqual(Integer versionLessThanOrEqual) {
		this.versionLessThanOrEqual = versionLessThanOrEqual;
	}

	public DataSetAuditQuery version(Integer version) {
		if (version == null) {
			throw new RuntimeException("version is null");
		}
		this.version = version;
		return this;
	}

	public DataSetAuditQuery versionGreaterThanOrEqual(Integer versionGreaterThanOrEqual) {
		if (versionGreaterThanOrEqual == null) {
			throw new RuntimeException("version is null");
		}
		this.versionGreaterThanOrEqual = versionGreaterThanOrEqual;
		return this;
	}

	public DataSetAuditQuery versionLessThanOrEqual(Integer versionLessThanOrEqual) {
		if (versionLessThanOrEqual == null) {
			throw new RuntimeException("version is null");
		}
		this.versionLessThanOrEqual = versionLessThanOrEqual;
		return this;
	}

}