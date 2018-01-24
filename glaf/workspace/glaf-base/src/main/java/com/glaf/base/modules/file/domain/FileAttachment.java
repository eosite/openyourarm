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

package com.glaf.base.modules.file.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.core.util.FileUtils;
import com.glaf.base.modules.file.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "T_ATTACHMENT")
public class FileAttachment implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false, length = 50)
	protected String id;

	/**
	 * NodeId
	 */
	@Column(name = "NODEID_")
	protected Long nodeId;

	/**
	 * Name
	 */
	@Column(name = "NAME_", nullable = false, length = 200)
	protected String name;

	/**
	 * Filename
	 */
	@Column(name = "FILENAME_", nullable = false, length = 200)
	protected String filename;

	@Column(name = "ORIGINALFILENAME_", nullable = false, length = 200)
	protected String originalFilename;

	/**
	 * ReferId
	 */
	@Column(name = "REFERID_", length = 200)
	protected String referId;

	/**
	 * Type
	 */
	@Column(name = "TYPE_", nullable = false, length = 50)
	protected String type;

	/**
	 * Desc
	 */
	@Column(name = "DESC_", length = 500)
	protected String desc;

	/**
	 * Path
	 */
	@Column(name = "PATH_", length = 500)
	protected String path;

	/**
	 * Size
	 */
	@Column(name = "SIZE_")
	protected int size;

	/**
	 * Width
	 */
	@Column(name = "WIDTH_")
	protected int width;

	/**
	 * Height
	 */
	@Column(name = "HEIGHT_")
	protected int height;

	/**
	 * Locked
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	/**
	 * DeleteFlag
	 */
	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	/**
	 * CreateDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	/**
	 * CreateBy
	 */
	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	/**
	 * UpdateDate
	 */
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	/**
	 * UpdateBy
	 */
	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@javax.persistence.Transient
	protected byte[] bytes;

	@javax.persistence.Transient
	protected String fileExt;

	public FileAttachment() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FileAttachment other = (FileAttachment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public byte[] getBytes() {
		return bytes;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public String getCreateDateString() {
		if (this.createDate != null) {
			return DateUtils.getDateTime(this.createDate);
		}
		return "";
	}

	public int getDeleteFlag() {
		return this.deleteFlag;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getFileExt() {
		if (filename != null) {
			fileExt = FileUtils.getFileExt(filename.toLowerCase());
		}
		return fileExt;
	}

	public String getFilename() {
		return this.filename;
	}

	public int getHeight() {
		return this.height;
	}

	public String getId() {
		return this.id;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getName() {
		return this.name;
	}

	public Long getNodeId() {
		return this.nodeId;
	}

	public String getOriginalFilename() {
		return originalFilename;
	}

	public String getPath() {
		return this.path;
	}

	public String getReferId() {
		return this.referId;
	}

	public int getSize() {
		return this.size;
	}

	public String getType() {
		return this.type;
	}

	public String getUpdateBy() {
		return this.updateBy;
	}

	public Date getUpdateDate() {
		return this.updateDate;
	}

	public String getUpdateDateString() {
		if (this.updateDate != null) {
			return DateUtils.getDateTime(this.updateDate);
		}
		return "";
	}

	public int getWidth() {
		return this.width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FileAttachment jsonToObject(JSONObject jsonObject) {
		return FileAttachmentJsonFactory.jsonToObject(jsonObject);
	}

	public void setBytes(byte[] bytes) {
		this.bytes = bytes;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setFileExt(String fileExt) {
		this.fileExt = fileExt;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNodeId(Long nodeId) {
		this.nodeId = nodeId;
	}

	public void setOriginalFilename(String originalFilename) {
		this.originalFilename = originalFilename;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public JSONObject toJsonObject() {
		return FileAttachmentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FileAttachmentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
