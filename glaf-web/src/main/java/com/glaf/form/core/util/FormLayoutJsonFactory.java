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
package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class FormLayoutJsonFactory {

	public static FormLayout jsonToObject(JSONObject jsonObject) {
		FormLayout model = new FormLayout();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("deploymentId")) {
			model.setDeploymentId(jsonObject.getString("deploymentId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("json")) {
			model.setJson(jsonObject.getString("json"));
		}
		if (jsonObject.containsKey("imageFileId")) {
			model.setImageFileId(jsonObject.getString("imageFileId"));
		}
		if (jsonObject.containsKey("imageFileName")) {
			model.setImageFileName(jsonObject.getString("imageFileName"));
		}
		if (jsonObject.containsKey("imagePath")) {
			model.setImagePath(jsonObject.getString("imagePath"));
		}
		if (jsonObject.containsKey("smallImagePath")) {
			model.setSmallImagePath(jsonObject.getString("smallImagePath"));
		}
		if (jsonObject.containsKey("mediumImagePath")) {
			model.setMediumImagePath(jsonObject.getString("mediumImagePath"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("height")) {
			model.setHeight(jsonObject.getInteger("height"));
		}
		if (jsonObject.containsKey("width")) {
			model.setWidth(jsonObject.getInteger("width"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormLayout model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getJson() != null) {
			jsonObject.put("json", model.getJson());
		}
		if (model.getImageFileId() != null) {
			jsonObject.put("imageFileId", model.getImageFileId());
		}
		if (model.getImageFileName() != null) {
			jsonObject.put("imageFileName", model.getImageFileName());
		}
		if (model.getImagePath() != null) {
			jsonObject.put("imagePath", model.getImagePath());
		}
		if (model.getSmallImagePath() != null) {
			jsonObject.put("smallImagePath", model.getSmallImagePath());
		}
		if (model.getMediumImagePath() != null) {
			jsonObject.put("mediumImagePath", model.getMediumImagePath());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("height", model.getHeight());
		jsonObject.put("width", model.getWidth());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormLayout model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getJson() != null) {
			jsonObject.put("json", model.getJson());
		}
		if (model.getImageFileId() != null) {
			jsonObject.put("imageFileId", model.getImageFileId());
		}
		if (model.getImageFileName() != null) {
			jsonObject.put("imageFileName", model.getImageFileName());
		}
		if (model.getImagePath() != null) {
			jsonObject.put("imagePath", model.getImagePath());
		}
		if (model.getSmallImagePath() != null) {
			jsonObject.put("smallImagePath", model.getSmallImagePath());
		}
		if (model.getMediumImagePath() != null) {
			jsonObject.put("mediumImagePath", model.getMediumImagePath());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("height", model.getHeight());
		jsonObject.put("width", model.getWidth());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormLayout> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormLayout model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormLayout> arrayToList(JSONArray array) {
		java.util.List<FormLayout> list = new java.util.ArrayList<FormLayout>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormLayout model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormLayoutJsonFactory() {

	}

}