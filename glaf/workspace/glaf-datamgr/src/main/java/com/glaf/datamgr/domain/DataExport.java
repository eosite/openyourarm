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

package com.glaf.datamgr.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "SYS_DATA_EXPORT")
public class DataExport implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected Long id;

	@Column(name = "DATABASEID_")
	protected Long databaseId;

	/**
	 * 名称
	 */
	@Column(name = "NAME_", length = 50)
	protected String name;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 20)
	protected String type;

	/**
	 * 服务标识
	 */
	@Column(name = "SERVICEKEY_", length = 50)
	protected String serviceKey;

	/**
	 * 交换用户
	 */
	@Column(name = "USERID_", length = 50)
	protected String userId;

	/**
	 * 操作
	 */
	@Column(name = "OPERATION_", length = 50)
	protected String operation;

	/**
	 * 是否导出
	 */
	@Column(name = "EXPORTFLAG_", length = 1)
	protected String exportFlag;

	/**
	 * 导出库名
	 */
	@Column(name = "EXPORTNAME_", length = 50)
	protected String exportName;

	/**
	 * 导出类型
	 */
	@Column(name = "EXPORTTYPE_", length = 20)
	protected String exportType;

	/**
	 * 最后导出时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "LASTEXPORTTIME_")
	protected Date lastExportTime;

	/**
	 * 最后导出标记
	 */
	@Column(name = "LASTEXPORTSTATUS_")
	protected Integer lastExportStatus;

	/**
	 * 表定义
	 */
	@Lob
	@Column(name = "TABLES_", length = 2000)
	protected String tables;

	/**
	 * 是否调度
	 */
	@Column(name = "SCHEDULEFLAG_", length = 1)
	protected String scheduleFlag;

	/**
	 * 每次抓取前删除
	 */
	@Column(name = "DELETEFETCH_", length = 1)
	protected String deleteFetch;

	/**
	 * 是否公开
	 */
	@Column(name = "PUBLICFLAG_", length = 1)
	protected String publicFlag;

	/**
	 * 是否需要建表语句
	 */
	@Column(name = "CREATETABLEFLAG_", length = 1)
	protected String createTableFlag;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected Integer locked;

	/**
	 * 删除标记
	 */
	@Column(name = "DELETEFLAG_")
	protected Integer deleteFlag;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	/**
	 * 修改人
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	/**
	 * 修改时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATETIME_")
	protected Date updateTime;

	public DataExport() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DataExport other = (DataExport) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public String getCreateTableFlag() {
		return createTableFlag;
	}

	public Date getCreateTime() {
		return this.createTime;
	}

	public String getCreateTimeString() {
		if (this.createTime != null) {
			return DateUtils.getDateTime(this.createTime);
		}
		return "";
	}

	public Long getDatabaseId() {
		return databaseId;
	}

	public String getDeleteFetch() {
		return this.deleteFetch;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public String getExportFlag() {
		return this.exportFlag;
	}

	public String getExportName() {
		return this.exportName;
	}

	public String getExportType() {
		return exportType;
	}

	public Long getId() {
		return this.id;
	}

	public Integer getLastExportStatus() {
		return lastExportStatus;
	}

	public Date getLastExportTime() {
		return this.lastExportTime;
	}

	public String getLastExportTimeString() {
		if (this.lastExportTime != null) {
			return DateUtils.getDateTime(this.lastExportTime);
		}
		return "";
	}

	public Integer getLocked() {
		return this.locked;
	}

	public String getName() {
		return this.name;
	}

	public String getOperation() {
		return this.operation;
	}

	public String getPublicFlag() {
		return this.publicFlag;
	}

	public String getScheduleFlag() {
		return this.scheduleFlag;
	}

	public String getServiceKey() {
		return serviceKey;
	}

	public String getTables() {
		return tables;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateTime() {
		return this.updateTime;
	}

	public String getUpdateTimeString() {
		if (this.updateTime != null) {
			return DateUtils.getDateTime(this.updateTime);
		}
		return "";
	}

	public String getUserId() {
		return userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public DataExport jsonToObject(JSONObject jsonObject) {
		return DataExportJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTableFlag(String createTableFlag) {
		this.createTableFlag = createTableFlag;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDatabaseId(Long databaseId) {
		this.databaseId = databaseId;
	}

	public void setDeleteFetch(String deleteFetch) {
		this.deleteFetch = deleteFetch;
	}

	public void setDeleteFlag(Integer deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setExportFlag(String exportFlag) {
		this.exportFlag = exportFlag;
	}

	public void setExportName(String exportName) {
		this.exportName = exportName;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setLastExportStatus(Integer lastExportStatus) {
		this.lastExportStatus = lastExportStatus;
	}

	public void setLastExportTime(Date lastExportTime) {
		this.lastExportTime = lastExportTime;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setPublicFlag(String publicFlag) {
		this.publicFlag = publicFlag;
	}

	public void setScheduleFlag(String scheduleFlag) {
		this.scheduleFlag = scheduleFlag;
	}

	public void setServiceKey(String serviceKey) {
		this.serviceKey = serviceKey;
	}

	public void setTables(String tables) {
		this.tables = tables;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public JSONObject toJsonObject() {
		return DataExportJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DataExportJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
