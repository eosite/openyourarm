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
@Table(name = "FILE_HISTORY")
public class FileHistory implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "FILEID_", length = 50, nullable = false)
	protected String fileId;

	@Column(name = "FILENAME_", length = 200)
	protected String fileName;

	@Column(name = "FILESIZE_")
	protected int fileSize;

	@Lob
	@Column(name = "FILECONTENT_")
	protected byte[] fileContent;

	@Column(name = "LASTMODIFIED_")
	protected long lastModified;

	@Column(name = "MD5_", length = 200)
	protected String md5;

	@Column(name = "PATH_", length = 500)
	protected String path;

	@Column(name = "REFERID_", length = 50)
	protected String referId;

	/**
	 * 类型
	 */
	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "VERSION_")
	protected int version;

	/**
	 * 包状态，成功1或失败-1
	 */
	@Column(name = "PKGSTATUS_", length = 20)
	protected String pkgStatus;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "PKGUPDATETIME_")
	protected Date pkgUpdateTime;

	/**
	 * 删除标记
	 */
	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATETIME_")
	protected Date createTime;

	public FileHistory() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileHistory other = (FileHistory) obj;
		if (fileId == null) {
			if (other.fileId != null)
				return false;
		} else if (!fileId.equals(other.fileId))
			return false;
		return true;
	}

	public String getCreateBy() {
		return this.createBy;
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

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public byte[] getFileContent() {
		return this.fileContent;
	}

	public String getFileId() {
		return this.fileId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public int getFileSize() {
		return this.fileSize;
	}

	public long getLastModified() {
		return this.lastModified;
	}

	public String getMd5() {
		return this.md5;
	}

	public String getPath() {
		return this.path;
	}

	public String getPkgStatus() {
		return pkgStatus;
	}

	public Date getPkgUpdateTime() {
		return pkgUpdateTime;
	}

	public String getReferId() {
		return referId;
	}

	public String getType() {
		return type;
	}

	public int getVersion() {
		return this.version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((fileId == null) ? 0 : fileId.hashCode());
		return result;
	}

	public FileHistory jsonToObject(JSONObject jsonObject) {
		return FileHistoryJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileSize(int fileSize) {
		this.fileSize = fileSize;
	}

	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public void setMd5(String md5) {
		this.md5 = md5;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPkgStatus(String pkgStatus) {
		this.pkgStatus = pkgStatus;
	}

	public void setPkgUpdateTime(Date pkgUpdateTime) {
		this.pkgUpdateTime = pkgUpdateTime;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public JSONObject toJsonObject() {
		return FileHistoryJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FileHistoryJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
