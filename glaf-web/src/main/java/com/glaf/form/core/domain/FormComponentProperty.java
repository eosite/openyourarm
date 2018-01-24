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
@Table(name = "FORM_COMPONENT_PROPERTY")
public class FormComponentProperty implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	@Column(name = "COMPONENTID_")
	protected long componentId;

	@Column(name = "DEPLOYMENTID_", length = 100)
	protected String deploymentId;

	@Column(name = "NAME_", length = 100)
	protected String name;

	@Column(name = "DATATYPE_", length = 50)
	protected String dataType;

	@Column(name = "TYPE_", length = 200)
	protected String type;

	@Column(name = "KENDOCOMPONENT_", length = 200)
	protected String kendoComponent;

	@Column(name = "KENDOMAPPING_", length = 200)
	protected String kendoMapping;

	@Column(name = "CATEGORY_", length = 50)
	protected String category;

	@Column(name = "TITLE_", length = 100)
	protected String title;

	@Lob
	@Column(name = "DESC_")
	protected String desc;

	@Column(name = "VALUE_", length = 200)
	protected String value;

	@Column(name = "ENUMVALUE_", length = 200)
	protected String enumValue;

	@Column(name = "EDITOR_", length = 200)
	protected String editor;

	@Column(name = "VALIDATOR_", length = 200)
	protected String validator;

	@Column(name = "LOCKED_")
	protected int locked;

	@Column(name = "DELETEFLAG_")
	protected int deleteFlag;

	@Column(name = "VERSION_")
	protected int version;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "UPDATEDATE_")
	protected Date updateDate;

	@Column(name = "UPDATEBY_", length = 50)
	protected String updateBy;

	@Column(name = "PARENTID_")
	protected Integer parentId;

	@Column(name = "LISTNO_")
	protected Integer listNo;

	@Column(name = "PVALUE_", length = 300)
	protected String pValue;

	@Column(name = "DEFVALUE_", length = 300)
	protected String defValue;// 默认值

	@Column(name = "LEVEL_", length = 200)
	protected String level;

	@Column(name = "ISSORT_")
	protected Integer isSort;
	
	@Column(name = "EVENTFLAG_")
	protected Integer eventFlag;

	public FormComponentProperty() {

	}

	public Integer getEventFlag() {
		return eventFlag;
	}

	public void setEventFlag(Integer eventFlag) {
		this.eventFlag = eventFlag;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormComponentProperty other = (FormComponentProperty) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getCategory() {
		return category;
	}

	public long getComponentId() {
		return componentId;
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

	public String getDataType() {
		return this.dataType;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public String getDeploymentId() {
		return this.deploymentId;
	}

	public String getDesc() {
		return this.desc;
	}

	public String getEditor() {
		return editor;
	}

	public String getEnumValue() {
		return enumValue;
	}

	public long getId() {
		return this.id;
	}

	public String getKendoComponent() {
		return this.kendoComponent;
	}

	public String getKendoMapping() {
		return kendoMapping;
	}

	public int getLocked() {
		return locked;
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

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public String getValidator() {
		return validator;
	}

	public String getValue() {
		return this.value;
	}

	public int getVersion() {
		return version;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}

	public FormComponentProperty jsonToObject(JSONObject jsonObject) {
		return FormComponentPropertyJsonFactory.jsonToObject(jsonObject);
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setComponentId(long componentId) {
		this.componentId = componentId;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public void setDeleteFlag(int deleteFlag) {
		this.deleteFlag = deleteFlag;
	}

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public void setEditor(String editor) {
		this.editor = editor;
	}

	public void setEnumValue(String enumValue) {
		this.enumValue = enumValue;
	}

	public void setId(long id) {
		this.id = id;
	}

	public void setKendoComponent(String kendoComponent) {
		this.kendoComponent = kendoComponent;
	}

	public void setKendoMapping(String kendoMapping) {
		this.kendoMapping = kendoMapping;
	}

	public void setLocked(int locked) {
		this.locked = locked;
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

	public void setUpdateBy(String updateBy) {
		this.updateBy = updateBy;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public void setValidator(String validator) {
		this.validator = validator;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public JSONObject toJsonObject() {
		return FormComponentPropertyJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormComponentPropertyJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getpValue() {
		return pValue;
	}

	public void setpValue(String pValue) {
		this.pValue = pValue;
	}

	public Integer getListNo() {
		return listNo;
	}

	public void setListNo(Integer listNo) {
		this.listNo = listNo;
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

	public String getDefValue() {
		return defValue;
	}

	public void setDefValue(String defValue) {
		this.defValue = defValue;
	}

}
