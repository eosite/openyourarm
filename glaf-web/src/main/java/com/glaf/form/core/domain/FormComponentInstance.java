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
package com.glaf.form.core.domain;

import java.io.*;
import java.util.*;
import javax.persistence.*;
import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_COMPONENT_INSTANCE")
public class FormComponentInstance implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	@Column(name = "FORMDESIGNID_", length = 100)
	protected String formDesignId;

	@Column(name = "DEPLOYMENTID_", length = 100)
	protected String deploymentId;

	@Column(name = "COMPONENTID_", length = 100)
	protected String componentId;

	@Column(name = "NAME_", length = 100)
	protected String name;

	@Column(name = "TITLE_", length = 100)
	protected String title;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "DATATYPE_", length = 50)
	protected String dataType;

	@Column(name = "KENDOCOMPONENT_", length = 200)
	protected String kendoComponent;

	@Column(name = "VALUE_", length = 2000)
	protected String value;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	public FormComponentInstance() {

	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFormDesignId() {
		return this.formDesignId;
	}

	public String getDeploymentId() {
		return this.deploymentId;
	}

	public String getComponentId() {
		return this.componentId;
	}

	public String getName() {
		return this.name;
	}

	public String getTitle() {
		return this.title;
	}

	public String getType() {
		return this.type;
	}

	public String getDataType() {
		return this.dataType;
	}

	public String getKendoComponent() {
		return this.kendoComponent;
	}

	public String getValue() {
		return this.value;
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

	public String getCreateBy() {
		return this.createBy;
	}

	public void setFormDesignId(String formDesignId) {
		this.formDesignId = formDesignId;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setComponentId(String componentId) {
		this.componentId = componentId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setKendoComponent(String kendoComponent) {
		this.kendoComponent = kendoComponent;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormComponentInstance other = (FormComponentInstance) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormComponentInstance jsonToObject(JSONObject jsonObject) {
		return FormComponentInstanceJsonFactory.jsonToObject(jsonObject);
	}

	public JSONObject toJsonObject() {
		return FormComponentInstanceJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormComponentInstanceJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
