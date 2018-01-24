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
@Table(name = "DOWNLOAD_HISTORY")
public class DownloadHistory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	/**
	 * 文件编号
	 */
	@Column(name = "FILEID_", length = 50)
	protected String fileId;

	/**
	 * 文件名称
	 */
	@Column(name = "FILENAME_", length = 250)
	protected String filename;

	/**
	 * 下载地址
	 */
	@Column(name = "IP_", length = 100)
	protected String ip;

	/**
	 * 下载用户
	 */
	@Column(name = "USERID_", length = 50)
	protected String userId;

	/**
	 * 下载时间
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DOWNLOADTIME_")
	protected Date downloadTime;

	@Column(name = "STATUS_")
	protected int status;

	public DownloadHistory() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DownloadHistory other = (DownloadHistory) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public Date getDownloadTime() {
		return this.downloadTime;
	}

	public String getDownloadTimeString() {
		if (this.downloadTime != null) {
			return DateUtils.getDateTime(this.downloadTime);
		}
		return "";
	}

	public String getFileId() {
		return this.fileId;
	}

	public String getFilename() {
		return this.filename;
	}

	public String getId() {
		return this.id;
	}

	public String getIp() {
		return this.ip;
	}

	public int getStatus() {
		return status;
	}

	public String getUserId() {
		return this.userId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public DownloadHistory jsonToObject(JSONObject jsonObject) {
		return DownloadHistoryJsonFactory.jsonToObject(jsonObject);
	}

	public void setDownloadTime(Date downloadTime) {
		this.downloadTime = downloadTime;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public JSONObject toJsonObject() {
		return DownloadHistoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return DownloadHistoryJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
