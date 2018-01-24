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

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.JSONable;
import com.glaf.form.core.util.FormAttachmentJsonFactory;

@Entity
@Table(name = "FORM_ATTACHMENT")
public class FormAttachment implements Serializable, JSONable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_", nullable = false)
	protected String id;

	/**
	 * 外键
	 */
	@Column(name = "PARENT_", nullable = false, length = 50)
	protected String parent;

	/**
	 * 类型 标记保存至数据库还是服务器目录
	 */
	@Column(name = "TYPE_", length = 1)
	protected String type;

	/**
	 * 文件名称
	 */
	@Column(name = "FILENAME_", length = 200)
	protected String fileName;

	/**
	 * 文件大小
	 */
	@Column(name = "FILESIZE_")
	protected long fileSize;

	/**
	 * 文件内容，保存至数据库时使用
	 */
	@Lob
	@Column(name = "FILECONTENT_")
	protected byte[] fileContent;

	/**
	 * 保存目录地址，保存至服务器时使用
	 */
	@Column(name = "SAVESERVICEPATH_", length = 1000)
	protected String saveServicePath;

	/**
	 * 版本
	 */
	@Column(name = "VISION_", length = 50)
	protected String version;

	/**
	 * 状态
	 */
	@Column(name = "STATUS_")
	protected int status;

	/**
	 * 创建人
	 */
	@Column(name = "CREATEBY", length = 50)
	protected String createBy;

	/**
	 * 创建时间
	 */
	@Column(name = "CREATEDATE")
	protected Date createDate;

	/**
	 * 更新人
	 */
	@Column(name = "UPDATEBY", length = 50)
	protected String updateBy;

	/**
	 * 更新时间
	 */
	@Column(name = "UPDATEDATE")
	protected Date updateDate;

	/**
	 * 业务字段
	 */
	@Column(name = "BUSINESS", length = 50)
	protected String business;

	@Column(name = "FILENAME_THU_", length = 100)
	protected String filenameThu;

	@Column(name = "FILESIZE_THU_")
	protected long filesizeThu;

	/**
	 * 文件内容，保存至数据库时使用
	 */
	@Lob
	@Column(name = "FILECONTENT_THU_")
	protected byte[] filecontentThu;

	/**
	 * 保存目录地址，保存至服务器时使用
	 */
	@Column(name = "FILEPATH_THU_", length = 500)
	protected String filepathThu;

	@javax.persistence.Transient
	private String error;

	public FormAttachment() {

	}

	public String getFilenameThu() {
		return filenameThu;
	}

	public void setFilenameThu(String filenameThu) {
		this.filenameThu = filenameThu;
	}

	public long getFilesizeThu() {
		return filesizeThu;
	}

	public void setFilesizeThu(long filesizeThu) {
		this.filesizeThu = filesizeThu;
	}

	public byte[] getFilecontentThu() {
		return filecontentThu;
	}

	public void setFilecontentThu(byte[] filecontentThu) {
		this.filecontentThu = filecontentThu;
	}

	public String getFilepathThu() {
		return filepathThu;
	}

	public void setFilepathThu(String filepathThu) {
		this.filepathThu = filepathThu;
	}

	public String getCreateBy() {
		return createBy;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public String getError() {
		return error;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public String getFileName() {
		return fileName;
	}

	public long getFileSize() {
		return fileSize;
	}

	public String getId() {
		return id;
	}

	public String getParent() {
		return parent;
	}

	public String getSaveServicePath() {
		return saveServicePath;
	}

	public int getStatus() {
		return status;
	}

	public String getType() {
		return type;
	}

	public String getUpdateBy() {
		return updateBy;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public String getVersion() {
		return version;
	}

	public FormAttachment jsonToObject(JSONObject jsonObject) {
		return FormAttachmentJsonFactory.jsonToObject(jsonObject);
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public void setError(String error) {
		this.error = error;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public void setFileSize(long fileSize) {
		this.fileSize = fileSize;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public void setSaveServicePath(String saveServicePath) {
		this.saveServicePath = saveServicePath;
	}

	public void setStatus(int status) {
		this.status = status;
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

	public void setVersion(String version) {
		this.version = version;
	}

	public String getBusiness() {
		return business;
	}

	public void setBusiness(String business) {
		this.business = business;
	}

	public JSONObject toJsonObject() {
		return FormAttachmentJsonFactory.toJsonObject(this);
	}

	public JSONObject toJsonObjectFull() {
		return FormAttachmentJsonFactory.toJsonObjectFull(this);
	}

	public ObjectNode toObjectNode() {
		return FormAttachmentJsonFactory.toObjectNode(this);
	}
	
	public FormAttachment jsonToObjectFull(JSONObject jsonObject) {
		return FormAttachmentJsonFactory.jsonToObjectFull(jsonObject);
	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

}
