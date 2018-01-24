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

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.glaf.core.query.DataQuery;

public class FormComponentPropertyQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<Long> ids;
	protected Collection<String> appActorIds;
	protected Long componentId;
	protected String deploymentId;
	protected List<String> deploymentIds;
	protected String name;
	protected String nameLike;
	protected String pValue;
	protected String pValueLike;
	protected String defValue;
	protected String defValueLike;
	protected String level;
	protected String levelLike;
	protected Integer isSort;
	protected String kendoComponent;
	protected String titleLike;
	protected String descLike;
	protected Date createDateGreaterThanOrEqual;
	protected Date createDateLessThanOrEqual;
	protected List<String> names ;
	//触发事件标识
	protected Integer eventFlag;

	public Integer getEventFlag() {
		return eventFlag;
	}

	public void setEventFlag(Integer eventFlag) {
		this.eventFlag = eventFlag;
	}

	public FormComponentPropertyQuery() {

	}

	public FormComponentPropertyQuery createDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		if (createDateGreaterThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
		return this;
	}

	public FormComponentPropertyQuery createDateLessThanOrEqual(
			Date createDateLessThanOrEqual) {
		if (createDateLessThanOrEqual == null) {
			throw new RuntimeException("createDate is null");
		}
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
		return this;
	}

	public FormComponentPropertyQuery deploymentId(String deploymentId) {
		if (deploymentId == null) {
			throw new RuntimeException("deploymentId is null");
		}
		this.deploymentId = deploymentId;
		return this;
	}

	public FormComponentPropertyQuery deploymentIds(List<String> deploymentIds) {
		if (deploymentIds == null) {
			throw new RuntimeException("deploymentIds is empty ");
		}
		this.deploymentIds = deploymentIds;
		return this;
	}

	public FormComponentPropertyQuery descLike(String descLike) {
		if (descLike == null) {
			throw new RuntimeException("desc is null");
		}
		this.descLike = descLike;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
	}

	public Long getComponentId() {
		return componentId;
	}

	public Date getCreateDateGreaterThanOrEqual() {
		return createDateGreaterThanOrEqual;
	}

	public Date getCreateDateLessThanOrEqual() {
		return createDateLessThanOrEqual;
	}

	public String getDeploymentId() {
		return deploymentId;
	}

	public List<String> getDeploymentIds() {
		return deploymentIds;
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

	public String getKendoComponent() {
		return kendoComponent;
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

			if ("dataType".equals(sortColumn)) {
				orderBy = "E.DATATYPE_" + a_x;
			}

			if ("type".equals(sortColumn)) {
				orderBy = "E.TYPE_" + a_x;
			}

			if ("kendoComponent".equals(sortColumn)) {
				orderBy = "E.KENDOCOMPONENT_" + a_x;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("desc".equals(sortColumn)) {
				orderBy = "E.DESC_" + a_x;
			}

			if ("value".equals(sortColumn)) {
				orderBy = "E.VALUE_" + a_x;
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

	@Override
	public void initQueryColumns() {
		super.initQueryColumns();
		addColumn("id", "ID_");
		addColumn("parentId", "PARENTID_");
		addColumn("deploymentId", "DEPLOYMENTID_");
		addColumn("name", "NAME_");
		addColumn("dataType", "DATATYPE_");
		addColumn("type", "TYPE_");
		addColumn("kendoComponent", "KENDOCOMPONENT_");
		addColumn("title", "TITLE_");
		addColumn("desc", "DESC_");
		addColumn("value", "VALUE_");
		addColumn("createDate", "CREATEDATE_");
		addColumn("createBy", "CREATEBY_");
	}

	public FormComponentPropertyQuery kendoComponent(String kendoComponent) {
		if (kendoComponent == null) {
			throw new RuntimeException("kendoComponent is null");
		}
		this.kendoComponent = kendoComponent;
		return this;
	}

	public FormComponentPropertyQuery name(String name) {
		if (name == null) {
			throw new RuntimeException("name is null");
		}
		this.name = name;
		return this;
	}

	public FormComponentPropertyQuery nameLike(String nameLike) {
		if (nameLike == null) {
			throw new RuntimeException("name is null");
		}
		this.nameLike = nameLike;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setComponentId(Long componentId) {
		this.componentId = componentId;
	}

	public void setCreateDateGreaterThanOrEqual(
			Date createDateGreaterThanOrEqual) {
		this.createDateGreaterThanOrEqual = createDateGreaterThanOrEqual;
	}

	public void setCreateDateLessThanOrEqual(Date createDateLessThanOrEqual) {
		this.createDateLessThanOrEqual = createDateLessThanOrEqual;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setDeploymentIds(List<String> deploymentIds) {
		this.deploymentIds = deploymentIds;
	}

	public void setDescLike(String descLike) {
		this.descLike = descLike;
	}

	public void setKendoComponent(String kendoComponent) {
		this.kendoComponent = kendoComponent;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public FormComponentPropertyQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

	public String getpValue() {
		return pValue;
	}

	public void setpValue(String pValue) {
		this.pValue = pValue;
	}

	public String getpValueLike() {
		if (pValueLike != null && pValueLike.trim().length() > 0) {
			if (!pValueLike.startsWith("%")) {
				pValueLike = "%" + pValueLike;
			}
			if (!pValueLike.endsWith("%")) {
				pValueLike = pValueLike + "%";
			}
		}
		return pValueLike;
	}

	public void setpValueLike(String pValueLike) {
		this.pValueLike = pValueLike;
	}

	public List<Long> getIds() {
		return ids;
	}

	public void setIds(List<Long> ids) {
		this.ids = ids;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public Integer getIsSort() {
		return isSort;
	}

	public void setIsSort(Integer isSort) {
		this.isSort = isSort;
	}

	public String getLevelLike() {
		if (levelLike != null && levelLike.trim().length() > 0) {
			if (!levelLike.startsWith("%")) {
				levelLike = "%" + levelLike;
			}
			if (!levelLike.endsWith("%")) {
				levelLike = levelLike + "%";
			}
		}
		return levelLike;
	}

	public void setLevelLike(String levelLike) {
		this.levelLike = levelLike;
	}

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

	public String getDefValueLike() {
		if (defValueLike != null && defValueLike.trim().length() > 0) {
			if (!defValueLike.startsWith("%")) {
				defValueLike = "%" + defValueLike;
			}
			if (!defValueLike.endsWith("%")) {
				defValueLike = defValueLike + "%";
			}
		}
		return defValueLike;
	}

	public void setDefValueLike(String defValueLike) {
		this.defValueLike = defValueLike;
	}
	
	
	protected String type ;

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<String> getNames() {
		return names;
	}

	public void setNames(List<String> names) {
		this.names = names;
	}

}