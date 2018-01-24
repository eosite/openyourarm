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

package com.glaf.base.modules.file.query;

import java.util.*;
import com.glaf.core.query.DataQuery;

public class FileAttachmentQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected Long nodeId;
	protected List<Long> nodeIds;
	protected String name;
	protected String nameLike;
	protected String filename;
	protected String filenameLike;
	protected String referId;
	protected List<String> referIds;
	protected String type;
	protected List<String> types;
	protected String descLike;
	protected String path;
	protected String pathLike;
	protected Integer sizeGreaterThanOrEqual;
	protected Integer sizeLessThanOrEqual;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<String> createBys;
	protected Date updateDateGreaterThanOrEqual;
	protected Date updateDateLessThanOrEqual;
	protected String updateBy;
	protected List<String> updateBys;

	public FileAttachmentQuery() {

	}

	public FileAttachmentQuery createBy(String createBy) {
		if (createBy == null) {
			throw new RuntimeException("createBy is null");
		}
		this.createBy = createBy;
		return this;
	}

	public FileAttachmentQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FileAttachmentQuery createDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FileAttachmentQuery createDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FileAttachmentQuery descLike(String descLike) {
		if (descLike == null) {
			throw new RuntimeException("desc is null");
		}
		this.descLike = descLike;
		return this;
	}

	public FileAttachmentQuery filename(String filename) {
		if (filename == null) {
			throw new RuntimeException("filename is null");
		}
		this.filename = filename;
		return this;
	}

	public FileAttachmentQuery filenameLike(String filenameLike) {
		if (filenameLike == null) {
			throw new RuntimeException("filename is null");
		}
		this.filenameLike = filenameLike;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public List<String> getCreateBys() {
		return createBys;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public Integer getDeleteFlag() {
		return deleteFlag;
	}

	public String getDescLike() {
		if (descLike != null && descLike.trim().length() > 0) {
			if (!descLike.startsWith("%")) {
				descLike = "%" + descLike;
			}
			if (!descLike.endsWith("%")) {
				descLike = descLike + "%";
			}
		}
		return descLike;
	}

	public String getFilename() {
		return filename;
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

	public List<Long> getNodeIds() {
		return nodeIds;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("nodeId".equals(sortColumn)) {
				orderBy = "E.NODEID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("filename".equals(sortColumn)) {
				orderBy = "E.FILENAME_" + a_x;
			}

			if ("referId".equals(sortColumn)) {
				orderBy = "E.REFERID_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("desc".equals(sortColumn)) {
				orderBy = "E.DESC_" + a_x;
			}

			if ("path".equals(sortColumn)) {
				orderBy = "E.PATH_" + a_x;
			}

			if ("size".equals(sortColumn)) {
				orderBy = "E.SIZE_" + a_x;
			}

			if ("width".equals(sortColumn)) {
				orderBy = "E.WIDTH_" + a_x;
			}

			if ("height".equals(sortColumn)) {
				orderBy = "E.HEIGHT_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

			if ("deleteFlag".equals(sortColumn)) {
				orderBy = "E.DELETEFLAG_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

			if ("updateDate".equals(sortColumn)) {
				orderBy = "E.UPDATEDATE_" + a_x;
			}

			if ("updateBy".equals(sortColumn)) {
				orderBy = "E.UPDATEBY_" + a_x;
			}

		}
		return orderBy;
	}

	public String getPath() {
		return path;
	}

	public String getPathLike() {
		if (pathLike != null && pathLike.trim().length() > 0) {
			if (!pathLike.startsWith("%")) {
				pathLike = "%" + pathLike;
			}
		}
		return pathLike;
	}

	public String getReferId() {
		return referId;
	}

	public List<String> getReferIds() {
		return referIds;
	}

	public Integer getSizeGreaterThanOrEqual() {
		return sizeGreaterThanOrEqual;
	}

	public Integer getSizeLessThanOrEqual() {
		return sizeLessThanOrEqual;
	}

	public String getType() {
		return type;
	}

	public List<String> getTypes() {
		return types;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public List<String> getUpdateBys() {
		return updateBys;
	}

	public Date getUpdateDateGreaterThanOrEqual() {
		return updateDateGreaterThanOrEqual;
	}

	public Date getUpdateDateLessThanOrEqual() {
		return updateDateLessThanOrEqual;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("nodeId", "NODEID_");
		addColumn("name", "NAME_");
		addColumn("filename", "FILENAME_");
		addColumn("referId", "REFERID_");
		addColumn("type", "TYPE_");
		addColumn("desc", "DESC_");
		addColumn("path", "PATH_");
		addColumn("size", "SIZE_");
		addColumn("width", "WIDTH_");
		addColumn("height", "HEIGHT_");
		addColumn("locked", "LOCKED_");
		addColumn("deleteFlag", "DELETEFLAG_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
		addColumn("updateDate", "UPDATEDATE_");
		addColumn("updateBy", "UPDATEBY_");
	}

	public FileAttachmentQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FileAttachmentQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FileAttachmentQuery nodeId(Long nodeId) {
		if (nodeId == null) {
			throw new RuntimeException("nodeId is null");
		}
		this.nodeId = nodeId;
		return this;
	}

	public FileAttachmentQuery nodeIds(List<Long> nodeIds) {
		if (nodeIds == null) {
			throw new RuntimeException("nodeIds is empty ");
		}
		this.nodeIds = nodeIds;
		return this;
	}

	public FileAttachmentQuery path(String path) {
		if (path == null) {
			throw new RuntimeException("path is null");
		}
		this.path = path;
		return this;
	}

	public FileAttachmentQuery pathLike(String pathLike) {
		if (pathLike == null) {
			throw new RuntimeException("path is null");
		}
		this.pathLike = pathLike;
		return this;
	}

	public FileAttachmentQuery referId(String referId) {
		if (referId == null) {
			throw new RuntimeException("referId is null");
		}
		this.referId = referId;
		return this;
	}

	public FileAttachmentQuery referIds(List<String> referIds) {
		if (referIds == null) {
			throw new RuntimeException("referIds is empty ");
		}
		this.referIds = referIds;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setCreateBys(List<String> createBys) {
		this.createBys = createBys;
	}

	public void setCreateDateGreaterThanOrEqual(Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public void setFilenameLike(String filenameLike) {
		this.filenameLike = filenameLike;
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

	public void setNodeIds(List<Long> nodeIds) {
		this.nodeIds = nodeIds;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public void setPathLike(String pathLike) {
		this.pathLike = pathLike;
	}

	public void setReferId(String referId) {
		this.referId = referId;
	}

	public void setReferIds(List<String> referIds) {
		this.referIds = referIds;
	}

	public void setSizeGreaterThanOrEqual(Integer sizeGreaterThanOrEqual) {
		this.sizeGreaterThanOrEqual = sizeGreaterThanOrEqual;
	}

	public void setSizeLessThanOrEqual(Integer sizeLessThanOrEqual) {
		this.sizeLessThanOrEqual = sizeLessThanOrEqual;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateBys(List<String> updateBys) {
		this.updateBys = updateBys;
	}

	public void setUpdateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
	}

	public void setUpdateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
	}

	public FileAttachmentQuery sizeGreaterThanOrEqual(Integer sizeGreaterThanOrEqual) {
		if (sizeGreaterThanOrEqual == null) {
			throw new RuntimeException("size is null");
		}
		this.sizeGreaterThanOrEqual = sizeGreaterThanOrEqual;
		return this;
	}

	public FileAttachmentQuery sizeLessThanOrEqual(Integer sizeLessThanOrEqual) {
		if (sizeLessThanOrEqual == null) {
			throw new RuntimeException("size is null");
		}
		this.sizeLessThanOrEqual = sizeLessThanOrEqual;
		return this;
	}

	public FileAttachmentQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public FileAttachmentQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public FileAttachmentQuery updateBys(List<String> updateBys) {
		if (updateBys == null) {
			throw new RuntimeException("updateBys is empty ");
		}
		this.updateBys = updateBys;
		return this;
	}

	public FileAttachmentQuery updateDateGreaterThanOrEqual(Date updateDateGreaterThanOrEqual) {
		if (updateDateGreaterThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateGreaterThanOrEqual = updateDateGreaterThanOrEqual;
		return this;
	}

	public FileAttachmentQuery updateDateLessThanOrEqual(Date updateDateLessThanOrEqual) {
		if (updateDateLessThanOrEqual == null) {
			throw new RuntimeException("updateDate is null");
		}
		this.updateDateLessThanOrEqual = updateDateLessThanOrEqual;
		return this;
	}

}