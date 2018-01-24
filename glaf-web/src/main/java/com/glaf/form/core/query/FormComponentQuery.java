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
package com.glaf.form.core.query;

import java.util.*;

import com.glaf.core.query.DataQuery;

/**
 * @author root
 *
 */
public class FormComponentQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected String deploymentId;
	protected List<String> deploymentIds;
	protected String dataRole;
	protected String name;
	protected String nameLike;
	protected String type;
	protected List<String> types;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<String> createBys;
	protected Long parentIdNotEqual;
	protected String categoryLike;

	public FormComponentQuery() {

	}

	public FormComponentQuery createBys(List<String> createBys) {
		if (createBys == null) {
			throw new RuntimeException("createBys is empty ");
		}
		this.createBys = createBys;
		return this;
	}

	public FormComponentQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormComponentQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormComponentQuery dataRole(String dataRole) {
		if (dataRole == null) {
			throw new RuntimeException("data role is null");
		}
		this.dataRole = dataRole;
		return this;
	}

	public FormComponentQuery deploymentId(String deploymentId) {
		if (deploymentId == null) {
			throw new RuntimeException("deploymentId is null");
		}
		this.deploymentId = deploymentId;
		return this;
	}

	public FormComponentQuery deploymentIds(List<String> deploymentIds) {
		if (deploymentIds == null) {
			throw new RuntimeException("deploymentIds is empty ");
		}
		this.deploymentIds = deploymentIds;
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

	public String getDataRole() {
		return dataRole;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public List<String> getDeploymentIds() {
		return deploymentIds;
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

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("parentId".equals(sortColumn)) {
				orderBy = "E.PARENTID_" + a_x;
			}

			if ("deploymentId".equals(sortColumn)) {
				orderBy = "E.DEPLOYMENTID_" + a_x;
			}

			if ("name".equals(sortColumn)) {
				orderBy = "E.NAME_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("jsEngine".equals(sortColumn)) {
				orderBy = "E.JSENGINE_" + a_x;
			}

			if ("jsPath".equals(sortColumn)) {
				orderBy = "E.JSPATH_" + a_x;
			}

			if ("imageFileName".equals(sortColumn)) {
				orderBy = "E.IMAGEFILENAME_" + a_x;
			}

			if ("imagePath".equals(sortColumn)) {
				orderBy = "E.IMAGEPATH_" + a_x;
			}

			if ("smallImagePath".equals(sortColumn)) {
				orderBy = "E.SMALLIMAGEPATH_" + a_x;
			}

			if ("mediumImagePath".equals(sortColumn)) {
				orderBy = "E.MEDIUMIMAGEPATH_" + a_x;
			}

			if ("createDate".equals(sortColumn)) {
				orderBy = "E.CREATEDATE_" + a_x;
			}

			if ("createBy".equals(sortColumn)) {
				orderBy = "E.CREATEBY_" + a_x;
			}

		}
		return orderBy;
	}

	public Long getParentId() {
		return parentId;
	}

	public List<Long> getParentIds() {
		return parentIds;
	}

	public String getType() {
		return type;
	}

	public List<String> getTypes() {
		return types;
	}

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("parentId", "PARENTID_");
		addColumn("deploymentId", "DEPLOYMENTID_");
		addColumn("name", "NAME_");
		addColumn("type", "TYPE_");
		addColumn("jsEngine", "JSENGINE_");
		addColumn("jsPath", "JSPATH_");
		addColumn("imageFileName", "IMAGEFILENAME_");
		addColumn("imagePath", "IMAGEPATH_");
		addColumn("smallImagePath", "SMALLIMAGEPATH_");
		addColumn("mediumImagePath", "MEDIUMIMAGEPATH_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
	}

	public FormComponentQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormComponentQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public FormComponentQuery parentId(Long parentId) {
		if (parentId == null) {
			throw new RuntimeException("parentId is null");
		}
		this.parentId = parentId;
		return this;
	}

	public FormComponentQuery parentIds(List<Long> parentIds) {
		if (parentIds == null) {
			throw new RuntimeException("parentIds is empty ");
		}
		this.parentIds = parentIds;
		return this;
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

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDataRole(String dataRole) {
		this.dataRole = dataRole;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setDeploymentIds(List<String> deploymentIds) {
		this.deploymentIds = deploymentIds;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}

	public void setParentIds(List<Long> parentIds) {
		this.parentIds = parentIds;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setTypes(List<String> types) {
		this.types = types;
	}

	public FormComponentQuery type(String type) {
		if (type == null) {
			throw new RuntimeException("type is null");
		}
		this.type = type;
		return this;
	}

	public FormComponentQuery types(List<String> types) {
		if (types == null) {
			throw new RuntimeException("types is empty ");
		}
		this.types = types;
		return this;
	}

	public Long getParentIdNotEqual() {
		return parentIdNotEqual;
	}

	public void setParentIdNotEqual(Long parentIdNotEqual) {
		this.parentIdNotEqual = parentIdNotEqual;
	}

	public String getCategoryLike() {
		if (categoryLike != null && categoryLike.trim().length() > 0) {
			if (!categoryLike.startsWith("%")) {
				categoryLike = "%" + categoryLike;
			}
			if (!categoryLike.endsWith("%")) {
				categoryLike = categoryLike + "%";
			}
		}
		return categoryLike;
	}

	public void setCategoryLike(String categoryLike) {
		this.categoryLike = categoryLike;
	}

}