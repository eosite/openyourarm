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
import com.glaf.form.core.util.*;

/**
 * 
 * 实体对象
 *
 */

@Entity
@Table(name = "FORM_LAYOUT")
public class FormLayout implements Serializable, JSONable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", length = 50, nullable = false)
	protected String id;

	@Column(name = "DEPLOYMENTID_", length = 100)
	protected String deploymentId;

	@Column(name = "NAME_", length = 100)
	protected String name;

	@Column(name = "TYPE_", length = 50)
	protected String type;

	@Column(name = "HEIGHT_")
	protected Integer height;

	@Column(name = "WIDTH_")
	protected Integer width;

	@Column(name = "JSON_", length = 2000)
	protected String json;

	@Column(name = "IMAGEFILEID_", length = 200)
	protected String imageFileId;

	@Column(name = "IMAGEFILENAME_", length = 200)
	protected String imageFileName;

	@Column(name = "IMAGEPATH_", length = 200)
	protected String imagePath;

	@Column(name = "SMALLIMAGEPATH_", length = 200)
	protected String smallImagePath;

	@Column(name = "MEDIUMIMAGEPATH_", length = 200)
	protected String mediumImagePath;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "CREATEDATE_")
	protected Date createDate;

	@Column(name = "CREATEBY_", length = 50)
	protected String createBy;

	@Column(name = "STATUS_")
	protected Integer status;

	@javax.persistence.Transient
	protected byte[] bytes;

	public FormLayout() {

	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		FormLayout other = (FormLayout) obj;
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

	public String getDeploymentId() {
		return this.deploymentId;
	}

	public Integer getHeight() {
		return height;
	}

	public String getId() {
		return this.id;
	}

	public String getImageFileId() {
		return imageFileId;
	}

	public String getImageFileName() {
		return imageFileName;
	}

	public String getImagePath() {
		return imagePath;
	}

	public String getJson() {
		return this.json;
	}

	public String getMediumImagePath() {
		return mediumImagePath;
	}

	public String getName() {
		return this.name;
	}

	public String getSmallImagePath() {
		return smallImagePath;
	}

	public Integer getStatus() {
		return this.status;
	}

	public String getType() {
		return this.type;
	}

	public Integer getWidth() {
		return width;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	public FormLayout jsonToObject(JSONObject jsonObject) {
		return FormLayoutJsonFactory.jsonToObject(jsonObject);
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

	public void setDeploymentId(String deploymentId) {
		this.deploymentId = deploymentId;
	}

	public void setHeight(Integer height) {
		this.height = height;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setImageFileId(String imageFileId) {
		this.imageFileId = imageFileId;
	}

	public void setImageFileName(String imageFileName) {
		this.imageFileName = imageFileName;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}

	public void setJson(String json) {
		this.json = json;
	}

	public void setMediumImagePath(String mediumImagePath) {
		this.mediumImagePath = mediumImagePath;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setSmallImagePath(String smallImagePath) {
		this.smallImagePath = smallImagePath;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public JSONObject toJsonObject() {
		return FormLayoutJsonFactory.toJsonObject(this);
	}

	public ObjectNode toObjectNode() {
		return FormLayoutJsonFactory.toObjectNode(this);
	}

	@Override
	public String toString() {
		return ToStringBuilder.reflectionToString(this,
				ToStringStyle.MULTI_LINE_STYLE);
	}

}
