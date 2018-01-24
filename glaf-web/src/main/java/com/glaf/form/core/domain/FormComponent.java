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
@Table(name = "FORM_COMPONENT")
public class FormComponent implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected long id;

	@Column(name = "PARENTID_")
	protected long parentId;

	@Column(name = "DEPLOYMENTID_", length = 100)
	protected String deploymentId;

	@Column(name = "NAME_", length = 100)
	protected String name;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "DATAROLE_", length = 50)
	protected String dataRole;

	@Column(name = "KENDOCOMPONENT_", length = 200)
	protected String kendoComponent;
	
	@Column(name = "INTEGRAL_", length = 200)
	protected Integer integral;

	/**
	 * 描述
	 */
	@Column(name = "DESC_", length = 2000)
	protected String desc;

	@Column(name = "JSENGINE_", length = 50)
	protected String jsEngine;

	@Column(name = "JSPATH_", length = 200)
	protected String jsPath;

	@javax.persistence.Transient
	protected byte[] jsBytes;

	@Column(name = "IMAGEFILENAME_", length = 200)
	protected String imageFileName;

	@Column(name = "IMAGEPATH_", length = 200)
	protected String imagePath;

	@javax.persistence.Transient
	protected byte[] imageBytes;

	@Column(name = "SMALLIMAGEFILENAME_", length = 200)
	protected String smallImageFileName;

	@Column(name = "SMALLIMAGEPATH_", length = 200)
	protected String smallImagePath;

	@javax.persistence.Transient
	protected byte[] smallImageBytes;

	@Column(name = "MEDIUMIMAGEFILENAME_", length = 200)
	protected String mediumImageFileName;

	@Column(name = "MEDIUMIMAGEPATH_", length = 200)
	protected String mediumImagePath;

	@javax.persistence.Transient
	protected byte[] mediumImageBytes;

	@Column(name = "CATEGORY_", length = 200)
	protected String category;

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
	
	@Column(name = "BASECOMP_")
	protected Integer baseComp;

	@javax.persistence.Transient
	protected List<FormComponentProperty> properties = new ArrayList<FormComponentProperty>();
	@javax.persistence.Transient
	protected List<FormTemplate> templates = new ArrayList<FormTemplate>();

	public FormComponent() {

	}

	public void addProperty(FormComponentProperty property) {
		if (properties == null) {
			properties = new ArrayList<FormComponentProperty>();
		}
		properties.add(property);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormComponent other = (FormComponent) obj;
		if (id != other.id)
			return false;
		return true;
	}

	public String getCategory() {
		return category;
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

	public String getDataRole() {
		return dataRole;
	}

	public int getDeleteFlag() {
		return deleteFlag;
	}

	public String getDeploymentId() {
		return this.deploymentId;
	}

	public String getDesc() {
		return desc;
	}

	public long getId() {
		return this.id;
	}

	public byte[] getImageBytes() {
		return imageBytes;
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	public String getImagePath() {
		return this.imagePath;
	}

	public byte[] getJsBytes() {
		return jsBytes;
	}

	public String getJsEngine() {
		return this.jsEngine;
	}

	public String getJsPath() {
		return this.jsPath;
	}

	public String getKendoComponent() {
		return kendoComponent;
	}

	public int getLocked() {
		return locked;
	}

	public byte[] getMediumImageBytes() {
		return mediumImageBytes;
	}

	public String getMediumImageFileName() {
		return mediumImageFileName;
	}

	public String getMediumImagePath() {
		return this.mediumImagePath;
	}

	public String getName() {
		return this.name;
	}

	public long getParentId() {
		return this.parentId;
	}

	public List<FormComponentProperty> getProperties() {
		return properties;
	}

	public byte[] getSmallImageBytes() {
		return smallImageBytes;
	}

	public String getSmallImageFileName() {
		return smallImageFileName;
	}

	public String getSmallImagePath() {
		return this.smallImagePath;
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

	public FormComponent jsonToObject(JSONObject jsonObject) {
		return FormComponentJsonFactory.jsonToObject(jsonObject);
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setDataRole(String dataRole) {
		this.dataRole = dataRole;
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

	public void setId(long id) {
		this.id = id;
	}

	public void setImageBytes(byte[] imageBytes) {
		this.imageBytes = imageBytes;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setJsBytes(byte[] jsBytes) {
		this.jsBytes = jsBytes;
	}

	public void setJsEngine(String jsEngine) {
		this.jsEngine = jsEngine;
	}

	public void setJsPath(String jsPath) {
		this.jsPath = jsPath;
	}

	public void setKendoComponent(String kendoComponent) {
		this.kendoComponent = kendoComponent;
	}

	public void setLocked(int locked) {
		this.locked = locked;
	}

	public void setMediumImageBytes(byte[] mediumImageBytes) {
		this.mediumImageBytes = mediumImageBytes;
	}

	public void setMediumImageFileName(String mediumImageFileName) {
		this.mediumImageFileName = mediumImageFileName;
	}

	public void setMediumImagePath(String mediumImagePath) {
		this.mediumImagePath = mediumImagePath;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(long parentId) {
		this.parentId = parentId;
	}

	public void setProperties(List<FormComponentProperty> properties) {
		this.properties = properties;
	}

	public void setSmallImageBytes(byte[] smallImageBytes) {
		this.smallImageBytes = smallImageBytes;
	}

	public void setSmallImageFileName(String smallImageFileName) {
		this.smallImageFileName = smallImageFileName;
	}

	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
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

	public void setVersion(int version) {
		this.version = version;
	}
	
	public Integer getIntegral() {
		return integral;
	}

	public void setIntegral(Integer integral) {
		this.integral = integral;
	}

	public Integer getBaseComp() {
		return baseComp;
	}

	public void setBaseComp(Integer baseComp) {
		this.baseComp = baseComp;
	}

	public JSONObject toJsonObject() {
		return FormComponentJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormComponentJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

	public List<FormTemplate> getTemplates() {
		return templates;
	}

	public void setTemplates(List<FormTemplate> templates) {
		this.templates = templates;
	}
	
	
}
