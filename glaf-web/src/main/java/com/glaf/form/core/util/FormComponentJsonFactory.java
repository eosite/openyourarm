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
public class FormComponentJsonFactory {

	public static FormComponent jsonToObject(JSONObject jsonObject) {
		FormComponent model = new FormComponent();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getLong("parentId"));
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
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("dataRole")) {
			model.setDataRole(jsonObject.getString("dataRole"));
		}
		if (jsonObject.containsKey("kendoComponent")) {
			model.setKendoComponent(jsonObject.getString("kendoComponent"));
		}
		if (jsonObject.containsKey("jsEngine")) {
			model.setJsEngine(jsonObject.getString("jsEngine"));
		}
		if (jsonObject.containsKey("jsPath")) {
			model.setJsPath(jsonObject.getString("jsPath"));
		}
		if (jsonObject.containsKey("imageFileName")) {
			model.setImageFileName(jsonObject.getString("imageFileName"));
		}
		if (jsonObject.containsKey("imagePath")) {
			model.setImagePath(jsonObject.getString("imagePath"));
		}
		if (jsonObject.containsKey("smallImageFileName")) {
			model.setSmallImageFileName(jsonObject
					.getString("smallImageFileName"));
		}
		if (jsonObject.containsKey("smallImagePath")) {
			model.setSmallImagePath(jsonObject.getString("smallImagePath"));
		}
		if (jsonObject.containsKey("mediumImageFileName")) {
			model.setMediumImageFileName(jsonObject
					.getString("mediumImageFileName"));
		}
		if (jsonObject.containsKey("mediumImagePath")) {
			model.setMediumImagePath(jsonObject.getString("mediumImagePath"));
		}
		if (jsonObject.containsKey("category")) {
			model.setCategory(jsonObject.getString("category"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormComponent model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getParentId() > 0) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getDataRole() != null) {
			jsonObject.put("dataRole", model.getDataRole());
		}
		if (model.getKendoComponent() != null) {
			jsonObject.put("kendoComponent", model.getKendoComponent());
		}
		if (model.getJsEngine() != null) {
			jsonObject.put("jsEngine", model.getJsEngine());
		}
		if (model.getJsPath() != null) {
			jsonObject.put("jsPath", model.getJsPath());
		}
		if (model.getImageFileName() != null) {
			jsonObject.put("imageFileName", model.getImageFileName());
		}
		if (model.getImagePath() != null) {
			jsonObject.put("imagePath", model.getImagePath());
		}
		if (model.getSmallImageFileName() != null) {
			jsonObject.put("smallImageFileName", model.getSmallImageFileName());
		}
		if (model.getSmallImagePath() != null) {
			jsonObject.put("smallImagePath", model.getSmallImagePath());
		}
		if (model.getMediumImageFileName() != null) {
			jsonObject.put("mediumImageFileName",
					model.getMediumImageFileName());
		}
		if (model.getMediumImagePath() != null) {
			jsonObject.put("mediumImagePath", model.getMediumImagePath());
		}
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getIntegral() != null) {
			jsonObject.put("integral", model.getIntegral());
		}
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("version", model.getVersion());
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
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if(model.getBaseComp()!=null){
			jsonObject.put("baseComp", model.getBaseComp());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormComponent model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getParentId() > 0) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getDataRole() != null) {
			jsonObject.put("dataRole", model.getDataRole());
		}
		if (model.getKendoComponent() != null) {
			jsonObject.put("kendoComponent", model.getKendoComponent());
		}
		if (model.getJsEngine() != null) {
			jsonObject.put("jsEngine", model.getJsEngine());
		}
		if (model.getJsPath() != null) {
			jsonObject.put("jsPath", model.getJsPath());
		}
		if (model.getImageFileName() != null) {
			jsonObject.put("imageFileName", model.getImageFileName());
		}
		if (model.getImagePath() != null) {
			jsonObject.put("imagePath", model.getImagePath());
		}
		if (model.getSmallImageFileName() != null) {
			jsonObject.put("smallImageFileName", model.getSmallImageFileName());
		}
		if (model.getSmallImagePath() != null) {
			jsonObject.put("smallImagePath", model.getSmallImagePath());
		}
		if (model.getMediumImageFileName() != null) {
			jsonObject.put("mediumImageFileName",
					model.getMediumImageFileName());
		}
		if (model.getMediumImagePath() != null) {
			jsonObject.put("mediumImagePath", model.getMediumImagePath());
		}
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("version", model.getVersion());
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
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date",
					DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime",
					DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if(model.getBaseComp()!=null){
			jsonObject.put("baseComp", model.getBaseComp());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormComponent> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormComponent model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormComponent> arrayToList(JSONArray array) {
		java.util.List<FormComponent> list = new java.util.ArrayList<FormComponent>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormComponent model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormComponentJsonFactory() {

	}

}
