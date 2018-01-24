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

package com.glaf.base.modules.file.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.file.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class FileAttachmentJsonFactory {

	public static FileAttachment jsonToObject(JSONObject jsonObject) {
		FileAttachment model = new FileAttachment();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("filename")) {
			model.setFilename(jsonObject.getString("filename"));
		}
		if (jsonObject.containsKey("originalFilename")) {
			model.setOriginalFilename(jsonObject.getString("originalFilename"));
		}
		if (jsonObject.containsKey("referId")) {
			model.setReferId(jsonObject.getString("referId"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("path")) {
			model.setPath(jsonObject.getString("path"));
		}
		if (jsonObject.containsKey("size")) {
			model.setSize(jsonObject.getInteger("size"));
		}
		if (jsonObject.containsKey("width")) {
			model.setWidth(jsonObject.getInteger("width"));
		}
		if (jsonObject.containsKey("height")) {
			model.setHeight(jsonObject.getInteger("height"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FileAttachment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getOriginalFilename() != null) {
			jsonObject.put("originalFilename", model.getOriginalFilename());
		}
		if (model.getReferId() != null) {
			jsonObject.put("referId", model.getReferId());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getPath() != null) {
			jsonObject.put("path", model.getPath());
		}
		jsonObject.put("size", model.getSize());
		jsonObject.put("width", model.getWidth());
		jsonObject.put("height", model.getHeight());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FileAttachment model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getOriginalFilename() != null) {
			jsonObject.put("originalFilename", model.getOriginalFilename());
		}
		if (model.getReferId() != null) {
			jsonObject.put("referId", model.getReferId());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getPath() != null) {
			jsonObject.put("path", model.getPath());
		}
		jsonObject.put("size", model.getSize());
		jsonObject.put("width", model.getWidth());
		jsonObject.put("height", model.getHeight());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FileAttachment> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FileAttachment model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FileAttachment> arrayToList(JSONArray array) {
		java.util.List<FileAttachment> list = new java.util.ArrayList<FileAttachment>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FileAttachment model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FileAttachmentJsonFactory() {

	}

}
