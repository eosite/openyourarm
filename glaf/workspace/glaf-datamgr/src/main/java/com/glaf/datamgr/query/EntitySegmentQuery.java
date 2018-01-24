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

public class EntitySegmentQuery extends DataQuery {
	private static final long serialVersionUID = 1L;
	protected List<String> ids;
	protected Collection<String> appActorIds;
	protected String titleLike;
	protected String descriptionLike;
	protected String operation;
	protected String namespaceLike;
	protected String parameterType;
	protected String parameterTypeLike;
	protected String resultType;
	protected String resultTypeLike;

	public EntitySegmentQuery() {

	}

	public EntitySegmentQuery descriptionLike(String descriptionLike) {
		if (descriptionLike == null) {
			throw new RuntimeException("description is null");
		}
		this.descriptionLike = descriptionLike;
		return this;
	}

	public Collection<String> getAppActorIds() {
		return appActorIds;
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

	public Integer getLocked() {
		return locked;
	}

	public String getNamespaceLike() {
		if (namespaceLike != null && namespaceLike.trim().length() > 0) {
			if (!namespaceLike.startsWith("%")) {
				namespaceLike = "%" + namespaceLike;
			}
			if (!namespaceLike.endsWith("%")) {
				namespaceLike = namespaceLike + "%";
			}
		}
		return namespaceLike;
	}

	public String getOperation() {
		return operation;
	}

	public String getOrderBy() {
		if (sortColumn != null) {
			String a_x = " asc ";
			if (sortOrder != null) {
				a_x = sortOrder;
			}

			if ("title".equals(sortColumn)) {
				orderBy = "E.TITLE_" + a_x;
			}

			if ("description".equals(sortColumn)) {
				orderBy = "E.DESCRIPTION_" + a_x;
			}

			if ("operation".equals(sortColumn)) {
				orderBy = "E.OPERATION_" + a_x;
			}

			if ("namespace".equals(sortColumn)) {
				orderBy = "E.NAMESPACE_" + a_x;
			}

			if ("parameterType".equals(sortColumn)) {
				orderBy = "E.PARAMETERTYPE_" + a_x;
			}

			if ("resultType".equals(sortColumn)) {
				orderBy = "E.RESULTTYPE_" + a_x;
			}

			if ("locked".equals(sortColumn)) {
				orderBy = "E.LOCKED_" + a_x;
			}

		}
		return orderBy;
	}

	public String getParameterType() {
		return parameterType;
	}

	public String getParameterTypeLike() {
		if (parameterTypeLike != null && parameterTypeLike.trim().length() > 0) {
			if (!parameterTypeLike.startsWith("%")) {
				parameterTypeLike = "%" + parameterTypeLike;
			}
			if (!parameterTypeLike.endsWith("%")) {
				parameterTypeLike = parameterTypeLike + "%";
			}
		}
		return parameterTypeLike;
	}

	public String getResultType() {
		return resultType;
	}

	public String getResultTypeLike() {
		if (resultTypeLike != null && resultTypeLike.trim().length() > 0) {
			if (!resultTypeLike.startsWith("%")) {
				resultTypeLike = "%" + resultTypeLike;
			}
			if (!resultTypeLike.endsWith("%")) {
				resultTypeLike = resultTypeLike + "%";
			}
		}
		return resultTypeLike;
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
		addColumn("title", "TITLE_");
		addColumn("description", "DESCRIPTION_");
		addColumn("operation", "OPERATION_");
		addColumn("namespace", "NAMESPACE_");
		addColumn("parameterType", "PARAMETERTYPE_");
		addColumn("resultType", "RESULTTYPE_");
		addColumn("locked", "LOCKED_");
	}

	public EntitySegmentQuery locked(Integer locked) {
		if (locked == null) {
			throw new RuntimeException("locked is null");
		}
		this.locked = locked;
		return this;
	}

	public EntitySegmentQuery namespaceLike(String namespaceLike) {
		if (namespaceLike == null) {
			throw new RuntimeException("namespace is null");
		}
		this.namespaceLike = namespaceLike;
		return this;
	}

	public EntitySegmentQuery operation(String operation) {
		if (operation == null) {
			throw new RuntimeException("operation is null");
		}
		this.operation = operation;
		return this;
	}

	public EntitySegmentQuery parameterType(String parameterType) {
		if (parameterType == null) {
			throw new RuntimeException("parameterType is null");
		}
		this.parameterType = parameterType;
		return this;
	}

	public EntitySegmentQuery parameterTypeLike(String parameterTypeLike) {
		if (parameterTypeLike == null) {
			throw new RuntimeException("parameterType is null");
		}
		this.parameterTypeLike = parameterTypeLike;
		return this;
	}

	public EntitySegmentQuery resultType(String resultType) {
		if (resultType == null) {
			throw new RuntimeException("resultType is null");
		}
		this.resultType = resultType;
		return this;
	}

	public EntitySegmentQuery resultTypeLike(String resultTypeLike) {
		if (resultTypeLike == null) {
			throw new RuntimeException("resultType is null");
		}
		this.resultTypeLike = resultTypeLike;
		return this;
	}

	public void setAppActorIds(Collection<String> appActorIds) {
		this.appActorIds = appActorIds;
	}

	public void setDescriptionLike(String descriptionLike) {
		this.descriptionLike = descriptionLike;
	}

	public void setLocked(Integer locked) {
		this.locked = locked;
	}

	public void setNamespaceLike(String namespaceLike) {
		this.namespaceLike = namespaceLike;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public void setParameterTypeLike(String parameterTypeLike) {
		this.parameterTypeLike = parameterTypeLike;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public void setResultTypeLike(String resultTypeLike) {
		this.resultTypeLike = resultTypeLike;
	}

	public void setTitleLike(String titleLike) {
		this.titleLike = titleLike;
	}

	public EntitySegmentQuery titleLike(String titleLike) {
		if (titleLike == null) {
			throw new RuntimeException("title is null");
		}
		this.titleLike = titleLike;
		return this;
	}

}