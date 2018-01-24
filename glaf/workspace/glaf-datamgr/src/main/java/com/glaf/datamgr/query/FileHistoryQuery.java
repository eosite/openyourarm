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

public class FileHistoryQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> fileIds;
	protected Collection<String> appActorIds;
	protected String fileName;
	protected String fileNameLike;
	protected String path;
	protected String pathLike;
	protected Long lastModifiedGreaterThanOrEqual;
	protected Long lastModifiedLessThanOrEqual;
	protected String type;
	protected String pkgStatus;
	protected String referId;
	protected Integer version;
	protected Integer versionGreaterThanOrEqual;
	protected Integer versionLessThanOrEqual;
	protected List<String> createBys;
	protected Date createTimeGreaterThanOrEqual;
	protected Date createTimeLessThanOrEqual;

	public FileHistoryQuery() {

	}

	public FileHistoryQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FileHistoryQuery createTimeGreaterThanOrEqual(Date createTimeGreaterThanOrEqual) {
		if (createTimeGreaterThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeGreaterThanOrEqual = createTimeGreaterThanOrEqual;
		return this;
	}

	public FileHistoryQuery createTimeLessThanOrEqual(Date createTimeLessThanOrEqual) {
		if (createTimeLessThanOrEqual == null) {
			throw new RuntimeException("createTime is null");
		}
		this.createTimeLessThanOrEqual = createTimeLessThanOrEqual;
		return this;
	}

	public FileHistoryQuery fileName(String fileName) {
		if (fileName == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileName = fileName;
		return this;
	}

	public FileHistoryQuery fileNameLike(String fileNameLike) {
		if (fileNameLike == null) {
			throw new RuntimeException("fileName is null");
		}
		this.fileNameLike = fileNameLike;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
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

	public List<String> getFileIds() {
		return fileIds;
	}

	public String getFileName() {
		return fileName;
	}

	public String getFileNameLike() {
		if (fileNameLike != null && fileNameLike.trim().length() > 0) {
			if (!fileNameLike.startsWith("%")) {
				fileNameLike = "%" + fileNameLike;
			}
			if (!fileNameLike.endsWith("%")) {
				fileNameLike = fileNameLike + "%";
			}
		}
		return fileNameLike;
	}

	public Long getLastModifiedGreaterThanOrEqual() {
		return lastModifiedGreaterThanOrEqual;
	}

	public Long getLastModifiedLessThanOrEqual() {
		return lastModifiedLessThanOrEqual;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("fileName".equals(sortColumn)) {
				orderBy = "E.FILENAME_" + a_x;
			}

			if ("fileSize".equals(sortColumn)) {
				orderBy = "E.FILESIZE_" + a_x;
			}

			if ("lastModified".equals(sortColumn)) {
				orderBy = "E.LASTMODIFIED_" + a_x;
			}

			if ("md5".equals(sortColumn)) {
				orderBy = "E.MD5_" + a_x;
			}

			if ("path".equals(sortColumn)) {
				orderBy = "E.PATH_" + a_x;
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

	public String getPath() {
		return path;
	}

	public String getPathLike() {
		return pathLike;
	}

	public String getPkgStatus() {
		return pkgStatus;
	}

	public String getReferId() {
		return referId;
	}

	public String getType() {
		return type;
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
		addColumn("fileId", "FILEID_");
		addColumn("fileName", "FILENAME_");
		addColumn("fileSize", "FILESIZE_");
		addColumn("lastModified", "LASTMODIFIED_");
		addColumn("md5", "MD5_");
		addColumn("path", "PATH_");
		addColumn("version", "VERSION_");
		addColumn("createBy", "CREATEBY_");
		addColumn("createTime", "CREATETIME_");
	}

	public FileHistoryQuery path(String path) {
		if (path == null) {
			throw new RuntimeException("path is null");
		}
		this.path = path;
		return this;
	}

	public FileHistoryQuery pathLike(String pathLike) {
		if (pathLike == null) {
			throw new RuntimeException("path is null");
		}
		this.pathLike = pathLike;
		return this;
	}

	public FileHistoryQuery pkgStatus(String pkgStatus) {
		if (pkgStatus == null) {
			throw new RuntimeException("pkgStatus is null");
		}
		this.pkgStatus = pkgStatus;
		return this;
	}

	public FileHistoryQuery referId(String referId) {
		if (referId == null) {
			throw new RuntimeException("referId is null");
		}
		this.referId = referId;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
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

	public void setFileIds(List<String> fileIds) {
		this.fileIds = fileIds;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileNameLike(String fileNameLike) {
		this.fileNameLike = fileNameLike;
	}

	public void setLastModifiedGreaterThanOrEqual(Long lastModifiedGreaterThanOrEqual) {
		this.lastModifiedGreaterThanOrEqual = lastModifiedGreaterThanOrEqual;
	}

	public void setLastModifiedLessThanOrEqual(Long lastModifiedLessThanOrEqual) {
		this.lastModifiedLessThanOrEqual = lastModifiedLessThanOrEqual;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPathLike(String pathLike) {
		this.pathLike = pathLike;
	}

	public void setPkgStatus(String pkgStatus) {
		this.pkgStatus = pkgStatus;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public void setType(String type) {
		this.type = type;
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

	public FileHistoryQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public FileHistoryQuery version(Integer version) {
		if (version == null) {
			throw new RuntimeException("version is null");
		}
		this.version = version;
		return this;
	}

	public FileHistoryQuery versionGreaterThanOrEqual(Integer versionGreaterThanOrEqual) {
		if (versionGreaterThanOrEqual == null) {
			throw new RuntimeException("version is null");
		}
		this.versionGreaterThanOrEqual = versionGreaterThanOrEqual;
		return this;
	}

	public FileHistoryQuery versionLessThanOrEqual(Integer versionLessThanOrEqual) {
		if (versionLessThanOrEqual == null) {
			throw new RuntimeException("version is null");
		}
		this.versionLessThanOrEqual = versionLessThanOrEqual;
		return this;
	}

}