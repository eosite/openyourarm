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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class DownloadHistoryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String fileId;
	protected List<String> fileIds;
	protected String filenameLike;
	protected String ipLike;
	protected String userId;
	protected List<String> userIds;
	protected Date downloadTimeGreaterThanOrEqual;
	protected Date downloadTimeLessThanOrEqual;

	public DownloadHistoryQuery() {

	}

	public DownloadHistoryQuery downloadTimeGreaterThanOrEqual(Date downloadTimeGreaterThanOrEqual) {
		if (downloadTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("downloadTime is null");
		}
		this.downloadTimeGreaterThanOrEqual = downloadTimeGreaterThanOrEqual;
		return this;
	}

	public DownloadHistoryQuery downloadTimeLessThanOrEqual(Date downloadTimeLessThanOrEqual) {
		if (downloadTimeLessThanOrEqual == null) {
			throw new RuntimeException("downloadTime is null");
		}
		this.downloadTimeLessThanOrEqual = downloadTimeLessThanOrEqual;
		return this;
	}

	public DownloadHistoryQuery fileId(String fileId) {
		if (fileId == null) {
			throw new RuntimeException("fileId is null");
		}
		this.fileId = fileId;
		return this;
	}

	public DownloadHistoryQuery fileIds(List<String> fileIds) {
		if (fileIds == null) {
			throw new RuntimeException("fileIds is empty ");
		}
		this.fileIds = fileIds;
		return this;
	}

	public DownloadHistoryQuery filenameLike(String filenameLike) {
		if (filenameLike == null) {
			throw new RuntimeException("filename is null");
		}
		this.filenameLike = filenameLike;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public Date getDownloadTimeGreaterThanOrEqual() {
		return downloadTimeGreaterThanOrEqual;
	}

	public Date getDownloadTimeLessThanOrEqual() {
		return downloadTimeLessThanOrEqual;
	}

	public String getFileId() {
		return fileId;
	}

	public List<String> getFileIds() {
		return fileIds;
	}

	public String getFilenameLike() {
		if (filenameLike != null && filenameLike.trim().length() > 0) {
			if (!filenameLike.startsWith("%")) {
				filenameLike = "%" + filenameLike;
			}
			if (!filenameLike.endsWith("%")) {
				filenameLike = filenameLike + "%";
			}
		}
		return filenameLike;
	}

	public String getIpLike() {
		if (ipLike != null && ipLike.trim().length() > 0) {
			if (!ipLike.startsWith("%")) {
				ipLike = "%" + ipLike;
			}
			if (!ipLike.endsWith("%")) {
				ipLike = ipLike + "%";
			}
		}
		return ipLike;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("fileId".equals(sortColumn)) {
				orderBy = "E.FILEID_" + a_x;
			}

			if ("filename".equals(sortColumn)) {
				orderBy = "E.FILENAME_" + a_x;
			}

			if ("ip".equals(sortColumn)) {
				orderBy = "E.IP_" + a_x;
			}

			if ("userId".equals(sortColumn)) {
				orderBy = "E.USERID_" + a_x;
			}

			if ("downloadTime".equals(sortColumn)) {
				orderBy = "E.DOWNLOADTIME_" + a_x;
			}

		}
		return orderBy;
	}

	public String getUserId() {
		return userId;
	}

	public List<String> getUserIds() {
		return userIds;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("fileId", "FILEID_");
		addColumn("filename", "FILENAME_");
		addColumn("ip", "IP_");
		addColumn("userId", "USERID_");
		addColumn("downloadTime", "DOWNLOADTIME_");
	}

	public DownloadHistoryQuery ipLike(String ipLike) {
		if (ipLike == null) {
			throw new RuntimeException("ip is null");
		}
		this.ipLike = ipLike;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setDownloadTimeGreaterThanOrEqual(Date downloadTimeGreaterThanOrEqual) {
		this.downloadTimeGreaterThanOrEqual = downloadTimeGreaterThanOrEqual;
	}

	public void setDownloadTimeLessThanOrEqual(Date downloadTimeLessThanOrEqual) {
		this.downloadTimeLessThanOrEqual = downloadTimeLessThanOrEqual;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setFileIds(List<String> fileIds) {
		this.fileIds = fileIds;
	}

	public void setFilenameLike(String filenameLike) {
		this.filenameLike = filenameLike;
	}

	public void setIpLike(String ipLike) {
		this.ipLike = ipLike;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserIds(List<String> userIds) {
		this.userIds = userIds;
	}

	public DownloadHistoryQuery userId(String userId) {
		if (userId == null) {
			throw new RuntimeException("userId is null");
		}
		this.userId = userId;
		return this;
	}

	public DownloadHistoryQuery userIds(List<String> userIds) {
		if (userIds == null) {
			throw new RuntimeException("userIds is empty ");
		}
		this.userIds = userIds;
		return this;
	}

}