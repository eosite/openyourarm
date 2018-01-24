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
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.datamgr.util.*;

/**
 * 
 * 实体对象
 *
 */
@Entity
@Table(name = "SYS_ENTITY_SEGMENT")
public class EntitySegment implements Cloneable, Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 200, nullable = false)
	protected String id;

	/**
	 * 标题
	 */
	@Column(name = "TITLE_", length = 200)
	protected String title;

	/**
	 * 描述
	 */
	@Column(name = "DESCRIPTION_", length = 500)
	protected String description;

	/**
	 * 操作
	 */
	@Column(name = "OPERATION_", length = 50)
	protected String operation;

	/**
	 * 命名空间
	 */
	@Column(name = "NAMESPACE_", length = 200)
	protected String namespace;

	/**
	 * 参数类型
	 */
	@Column(name = "PARAMETERTYPE_", length = 200)
	protected String parameterType;

	/**
	 * 返回值类型
	 */
	@Column(name = "RESULTTYPE_", length = 200)
	protected String resultType;

	/**
	 * 是否锁定
	 */
	@Column(name = "LOCKED_")
	protected int locked;

	public EntitySegment() {

	}

	@Override
	public EntitySegment clone() {
		EntitySegment model = new EntitySegment();
		model.setId(id);
		model.setDescription(description);
		model.setLocked(locked);
		model.setNamespace(namespace);
		model.setOperation(operation);
		model.setParameterType(parameterType);
		model.setResultType(resultType);
		model.setTitle(title);
		return model;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		EntitySegment other = (EntitySegment) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	public String getDescription() {
		return this.description;
	}

	public String getId() {
		return this.id;
	}

	public int getLocked() {
		return this.locked;
	}

	public String getNamespace() {
		return this.namespace;
	}

	public String getOperation() {
		return this.operation;
	}

	public String getParameterType() {
		return this.parameterType;
	}

	public String getResultType() {
		return this.resultType;
	}

	public String getTitle() {
		return this.title;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public EntitySegment jsonToObject(JSONObject jsonObject) {
		return EntitySegmentJsonFactory.jsonToObject(jsonObject);
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}

	public void setOperation(String operation) {
		this.operation = operation;
	}

	public void setParameterType(String parameterType) {
		this.parameterType = parameterType;
	}

	public void setResultType(String resultType) {
		this.resultType = resultType;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public JSONObject toJsonObject() {
		return EntitySegmentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return EntitySegmentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
