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

public class DataSetQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected Collection<String> appActorIds;
	protected Long databaseId;
	protected List<Long> databaseIds;
	protected List<String> datasetIds;
	protected Long nodeId;
	protected String category;
	protected String name;
	protected String nameLike;
	protected String titleLike;
	protected String descriptionLike;
	protected String accessType;
	protected String perms;
	protected String permsLike;
	protected String addressPerms;
	protected String addressPermsLike;
	protected String type;
	protected String active;
	protected String cacheFlag;
	protected String chartFlag;
	protected String dynamicFlag;
	protected String initFlag;
	protected String shareFlag;
	protected String scheduleFlag;
	protected String publicFlag;
	protected String saveFlag;
	protected String keywordsLike;
	protected String updateFlag;
	protected String verify;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;
	protected String updateBy;
	protected List<String> updateBys;
	protected Date updateTimeGreaterThanOrEqual;
	protected Date updateTimeLessThanOrEqual;
	protected String topId;
	protected String topIdLike;
	protected String pageId;
	protected String dsId;

	public DataSetQuery() {

	}

	public DataSetQuery accessType(String accessType) {
		if (accessType == null) {
			throw new RuntimeException("accessType is null");
		}
		this.accessType = accessType;
		return this;
	}

	public DataSetQuery active(String active) {
		if (active == null) {
			throw new RuntimeException("active is null");
		}
		this.active = active;
		return this;
	}

	public DataSetQuery addressPerms(String addressPerms) {
		if (addressPerms == null) {
			throw new RuntimeException("addressPerms is null");
		}
		this.addressPerms = addressPerms;
		return this;
	}

	public DataSetQuery addressPermsLike(String addressPermsLike) {
		if (addressPermsLike == null) {
			throw new RuntimeException("addressPerms is null");
		}
		this.addressPermsLike = addressPermsLike;
		return this;
	}

	public DataSetQuery cacheFlag(String cacheFlag) {
		if (cacheFlag == null) {
			throw new RuntimeException("cacheFlag is null");
		}
		this.cacheFlag = cacheFlag;
		return this;
	}

	public DataSetQuery category(String category) {
		if (category == null) {
			throw new RuntimeException("category is null");
		}
		this.category = category;
		return this;
	}

	public DataSetQuery chartFlag(String chartFlag) {
		if (chartFlag == null) {
			throw new RuntimeException("chartFlag is null");
		}
		this.chartFlag = chartFlag;
		return this;
	}

	public DataSetQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public DataSetQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public DataSetQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public DataSetQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public DataSetQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public DataSetQuery dynamicFlag(String dynamicFlag) {
		if (dynamicFlag == null) {
			throw new RuntimeException("dynamicFlag is null");
		}
		this.dynamicFlag = dynamicFlag;
		return this;
	}

	public String getAccessType() {
		return accessType;
	}

	public String getActive() {
		return active;
	}

	public String getAddressPerms() {
		return addressPerms;
	}

	public String getAddressPermsLike() {
		if (addressPermsLike != null && addressPermsLike.trim().length() > 0) {
			if (!addressPermsLike.startsWith("%")) {
				addressPermsLike = "%" + addressPermsLike;
			}
			if (!addressPermsLike.endsWith("%")) {
				addressPermsLike = addressPermsLike + "%";
			}
		}
		return addressPermsLike;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public String getCacheFlag() {
		return cacheFlag;
	}

	public String getCategory() {
		return category;
	}

	public String getChartFlag() {
		return chartFlag;
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

	public Long getDatabaseId() {
		return databaseId;
	}

	public List<Long> getDatabaseIds() {
		return databaseIds;
	}

	public List<String> getDatasetIds() {
		return datasetIds;
	}

	public String getDescriptionLike() {
		if (descriptionLike != null && descriptionLike.trim().length() > 0) {
			if (!descriptionLike.startsWith("%")) {
				descriptionLike = "%" + descriptionLike;
			}
			if (!descriptionLike.endsWith("%")) {
				descriptionLike = descriptionLike + "%";
			}
		}
		return descriptionLike;
	}

	public String getDynamicFlag() {
		return dynamicFlag;
	}

	public String getInitFlag() {
		return initFlag;
	}

	public String getKeywordsLike() {
		if (keywordsLike != null && keywordsLike.trim().length() > 0) {
			if (!keywordsLike.startsWith("%")) {
				keywordsLike = "%" + keywordsLike;
			}
			if (!keywordsLike.endsWith("%")) {
				keywordsLike = keywordsLike + "%";
			}
		}
		return keywordsLike;
	}

	public Integer getLocked() {
		return locked;
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

	public Long getNodeId() {
		return nodeId;
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

			if ("description".equals(sortColumn)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("sql".equals(sortColumn)) {
				orderBy = "E.SQL_" + a_x;
			}

			if ("accessType".equals(sortColumn)) {
				orderBy = "E.ACCESSTYPE_" + a_x;
			}

			if ("perms".equals(sortColumn)) {
				orderBy = "E.PERMS_" + a_x;
			}

			if ("addressPerms".equals(sortColumn)) {
				orderBy = "E.ADDRESSPERMS_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("cacheFlag".equals(sortColumn)) {
				orderBy = "E.CACHEFLAG_" + a_x;
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

	public String getPerms() {
		return perms;
	}

	public String getPermsLike() {
		if (permsLike != null && permsLike.trim().length() > 0) {
			if (!permsLike.startsWith("%")) {
				permsLike = "%" + permsLike;
			}
			if (!permsLike.endsWith("%")) {
				permsLike = permsLike + "%";
			}
		}
		return permsLike;
	}

	public String getPublicFlag() {
		return publicFlag;
	}

	public String getSaveFlag() {
		return saveFlag;
	}

	public String getScheduleFlag() {
		return scheduleFlag;
	}

	public String getShareFlag() {
		return shareFlag;
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

	public String getUpdateBy() {
		return updateBy;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public String getUpdateFlag() {
		return updateFlag;
	}

	public Date getUpdateTimeGreaterThanOrEqual() {
		return updateTimeGreaterThanOrEqual;
	}

	public Date getUpdateTimeLessThanOrEqual() {
		return updateTimeLessThanOrEqual;
	}

	public String getVerify() {
		return verify;
	}

	public DataSetQuery initFlag(String initFlag) {
		if (initFlag == null) {
			throw new RuntimeException("initFlag is null");
		}
		this.initFlag = initFlag;
		return this;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("name", "NAME_");
		addColumn("title", "TITLE_");
		addColumn("description", "DESCRIPTION_");
		addColumn("sql", "SQL_");
		addColumn("accessType", "ACCESSTYPE_");
		addColumn("perms", "PERMS_");
		addColumn("addressPerms", "ADDRESSPERMS_");
		addColumn("type", "TYPE_");
		addColumn("cacheFlag", "CACHEFLAG_");
		addColumn("locked", "LOCKED_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
		addColumn("updateBy", "UPDATEBY_");
		addColumn("updateTime", "UPDATETIME_");
	}

	public DataSetQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public DataSetQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public DataSetQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public DataSetQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public DataSetQuery perms(String perms) {
		if (perms == null) {
			throw new RuntimeException("perms is null");
		}
		this.perms = perms;
		return this;
	}

	public DataSetQuery permsLike(String permsLike) {
		if (permsLike == null) {
			throw new RuntimeException("perms is null");
		}
		this.permsLike = permsLike;
		return this;
	}

	public DataSetQuery publicFlag(String publicFlag) {
		if (publicFlag == null) {
			throw new RuntimeException("publicFlag is null");
		}
		this.publicFlag = publicFlag;
		return this;
	}

	public DataSetQuery saveFlag(String saveFlag) {
		if (saveFlag == null) {
			throw new RuntimeException("saveFlag is null");
		}
		this.saveFlag = saveFlag;
		return this;
	}

	public DataSetQuery scheduleFlag(String scheduleFlag) {
		if (scheduleFlag == null) {
			throw new RuntimeException("scheduleFlag is null");
		}
		this.scheduleFlag = scheduleFlag;
		return this;
	}

	public void setAccessType(String accessType) {
		this.accessType = accessType;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public void setAddressPerms(String addressPerms) {
		this.addressPerms = addressPerms;
	}

	public void setAddressPermsLike(String addressPermsLike) {
		this.addressPermsLike = addressPermsLike;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCacheFlag(String cacheFlag) {
		this.cacheFlag = cacheFlag;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setChartFlag(String chartFlag) {
		this.chartFlag = chartFlag;
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

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDatabaseIds(List<Long> databaseIds) {
		this.databaseIds = databaseIds;
	}

	public void setDatasetIds(List<String> datasetIds) {
		this.datasetIds = datasetIds;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setDynamicFlag(String dynamicFlag) {
		this.dynamicFlag = dynamicFlag;
	}

	public void setInitFlag(String initFlag) {
		this.initFlag = initFlag;
	}

	public void setKeywordsLike(String keywordsLike) {
		this.keywordsLike = keywordsLike;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setPerms(String perms) {
		this.perms = perms;
	}

	public void setPermsLike(String permsLike) {
		this.permsLike = permsLike;
	}

	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

	public void setSaveFlag(String saveFlag) {
		this.saveFlag = saveFlag;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setShareFlag(String shareFlag) {
		this.shareFlag = shareFlag;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setUpdateFlag(String updateFlag) {
		this.updateFlag = updateFlag;
	}

	public void setUpdateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
	}

	public void setUpdateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
	}

	public void setVerify(String verify) {
		this.verify = verify;
	}

	public DataSetQuery shareFlag(String shareFlag) {
		if (shareFlag == null) {
			throw new RuntimeException("shareFlag is null");
		}
		this.shareFlag = shareFlag;
		return this;
	}

	public DataSetQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public DataSetQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public DataSetQuery updateBy(String updateBy) {
		if (updateBy == null) {
			throw new RuntimeException("updateBy is null");
		}
		this.updateBy = updateBy;
		return this;
	}

	public DataSetQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public DataSetQuery updateFlag(String updateFlag) {
		if (updateFlag == null) {
			throw new RuntimeException("updateFlag is null");
		}
		this.updateFlag = updateFlag;
		return this;
	}

	public DataSetQuery updateTimeGreaterThanOrEqual(Date updateTimeGreaterThanOrEqual) {
		if (updateTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeGreaterThanOrEqual = updateTimeGreaterThanOrEqual;
		return this;
	}

	public DataSetQuery updateTimeLessThanOrEqual(Date updateTimeLessThanOrEqual) {
		if (updateTimeLessThanOrEqual == null) {
			throw new RuntimeException("updateTime is null");
		}
		this.updateTimeLessThanOrEqual = updateTimeLessThanOrEqual;
		return this;
	}

	public DataSetQuery verify(String verify) {
		if (verify == null) {
			throw new RuntimeException("verify is null");
		}
		this.verify = verify;
		return this;
	}

	public String getTreeId() {
		return treeId;
	}

	public void setTreeId(String treeId) {
		this.treeId = treeId;
	}

	public String getTopId() {
		return topId;
	}

	public void setTopId(String topId) {
		this.topId = topId;
	}

	public String getTopIdLike() {
		if (topIdLike != null && topIdLike.trim().length() > 0) {
			if (!topIdLike.startsWith("%")) {
				topIdLike = "%" + topIdLike;
			}
			if (!topIdLike.endsWith("%")) {
				topIdLike = topIdLike + "%";
			}
		}
		return topIdLike;
	}

	public void setTopIdLike(String topIdLike) {
		this.topIdLike = topIdLike;
	}

	public String getTreeIdLike() {
		if (treeIdLike != null && treeIdLike.trim().length() > 0) {
			if (!treeIdLike.startsWith("%")) {
				treeIdLike = "%" + treeIdLike;
			}
			if (!treeIdLike.endsWith("%")) {
				treeIdLike = treeIdLike + "%";
			}
		}
		return treeIdLike;
	}

	public void setTreeIdLike(String treeIdLike) {
		this.treeIdLike = treeIdLike;
	}

	public String getPageId() {
		return pageId;
	}

	public void setPageId(String pageId) {
		this.pageId = pageId;
	}

	public String getDsId() {
		return dsId;
	}

	public void setDsId(String dsId) {
		this.dsId = dsId;
	}

}