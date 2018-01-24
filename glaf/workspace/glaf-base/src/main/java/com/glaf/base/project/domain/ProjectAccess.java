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
package com.glaf.base.project.domain;

import java.io.*;

import javax.persistence.*;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.glaf.core.base.*;
import com.glaf.base.project.util.ProjectAccessJsonFactory;

/**
 * 
 * 项目权限表
 *
 */
@Entity
@Table(name = "PROJECT_ACCESS")
public class ProjectAccess implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	@Column(name = "PROJECTID_")
	protected long projectId;

	@Column(name = "ACTORID_", length = 50)
	protected String actorId;

	@Column(name = "DYNAMIC_", length = 1)
	protected String dynamic;

	public ProjectAccess() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ProjectAccess other = (ProjectAccess) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getActorId() {
		return this.actorId;
	}

	public String getDynamic() {
		return dynamic;
	}

	public long getId() {
		return this.id;
	}

	public long getProjectId() {
		return this.projectId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public ProjectAccess jsonToObject(JSONObject jsonObject) {
		return ProjectAccessJsonFactory.jsonToObject(jsonObject);
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}

	public void setDynamic(String dynamic) {
		this.dynamic = dynamic;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public JSONObject toJsonObject() {
		return ProjectAccessJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return ProjectAccessJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
