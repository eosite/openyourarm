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
public class FormComponentPropertyJsonFactory {

	public static java.util.List<FormComponentProperty> arrayToList(
			JSONArray array) {
		java.util.List<FormComponentProperty> list = new java.util.ArrayList<FormComponentProperty>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormComponentProperty model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static FormComponentProperty jsonToObject(JSONObject jsonObject) {
		FormComponentProperty model = new FormComponentProperty();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("componentId")) {
			model.setComponentId(jsonObject.getLong("componentId"));
		}
		if (jsonObject.containsKey("deploymentId")) {
			model.setDeploymentId(jsonObject.getString("deploymentId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("dataType")) {
			model.setDataType(jsonObject.getString("dataType"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("category")) {
			model.setCategory(jsonObject.getString("category"));
		}
		if (jsonObject.containsKey("kendoComponent")) {
			model.setKendoComponent(jsonObject.getString("kendoComponent"));
		}
		if (jsonObject.containsKey("kendoMapping")) {
			model.setKendoMapping(jsonObject.getString("kendoMapping"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}
		if (jsonObject.containsKey("enumValue")) {
			model.setEnumValue(jsonObject.getString("enumValue"));
		}
		if (jsonObject.containsKey("editor")) {
			model.setEditor(jsonObject.getString("editor"));
		}
		if (jsonObject.containsKey("validator")) {
			model.setValidator(jsonObject.getString("validator"));
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
		if (jsonObject.containsKey("parentId")) {
			model.setUpdateBy(jsonObject.getString("parentId"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setUpdateBy(jsonObject.getString("listNo"));
		}
		if (jsonObject.containsKey("pValue")) {
			model.setpValue(jsonObject.getString("pValue"));
		}
		if (jsonObject.containsKey("defValue")) {
			model.setDefValue(jsonObject.getString("defValue"));
		}
		if (jsonObject.containsKey("level")) {
			model.setLevel(jsonObject.getString("level"));
		}
		if (jsonObject.containsKey("isSort")) {
			model.setIsSort(jsonObject.getInteger("isSort"));
		}
		if (jsonObject.containsKey("eventFlag")) {
			model.setEventFlag(jsonObject.getInteger("eventFlag"));
		}

		return model;
	}

	public static JSONArray listToArray(
			java.util.List<FormComponentProperty> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormComponentProperty model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(FormComponentProperty model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("componentId", model.getComponentId());
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDataType() != null) {
			jsonObject.put("dataType", model.getDataType());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getKendoComponent() != null) {
			jsonObject.put("kendoComponent", model.getKendoComponent());
		}
		if (model.getKendoMapping() != null) {
			jsonObject.put("kendoMapping", model.getKendoMapping());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		if (model.getEnumValue() != null) {
			jsonObject.put("enumValue", model.getEnumValue());
		}
		if (model.getEditor() != null) {
			jsonObject.put("editor", model.getEditor());
		}
		if (model.getValidator() != null) {
			jsonObject.put("validator", model.getValidator());
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
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getListNo() != null) {
			jsonObject.put("listNo", model.getListNo());
		}
		if (model.getpValue() != null) {
			jsonObject.put("pValue", model.getpValue());
		}
		
		if (model.getDefValue() != null) {
			jsonObject.put("defValue", model.getDefValue());
		}
		
		if (model.getLevel() != null) {
			jsonObject.put("level", model.getLevel());
		}
		if (model.getIsSort() != null) {
			jsonObject.put("isSort", model.getIsSort());
		}
		if (model.getEventFlag() != null) {
			jsonObject.put("eventFlag", model.getEventFlag());
		}
		
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormComponentProperty model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("componentId", model.getComponentId());
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDataType() != null) {
			jsonObject.put("dataType", model.getDataType());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getKendoComponent() != null) {
			jsonObject.put("kendoComponent", model.getKendoComponent());
		}
		if (model.getKendoMapping() != null) {
			jsonObject.put("kendoMapping", model.getKendoMapping());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		if (model.getEnumValue() != null) {
			jsonObject.put("enumValue", model.getEnumValue());
		}
		if (model.getEditor() != null) {
			jsonObject.put("editor", model.getEditor());
		}
		if (model.getValidator() != null) {
			jsonObject.put("validator", model.getValidator());
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
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		
		if (model.getListNo() != null) {
			jsonObject.put("listNo", model.getListNo());
		}
		if (model.getpValue() != null) {
			jsonObject.put("pValue", model.getpValue());
		}
		if (model.getDefValue() != null) {
			jsonObject.put("defValue", model.getDefValue());
		}
		
		if (model.getLevel() != null) {
			jsonObject.put("level", model.getLevel());
		}
		if (model.getIsSort() != null) {
			jsonObject.put("isSort", model.getIsSort());
		}
		if (model.getEventFlag() != null) {
			jsonObject.put("eventFlag", model.getEventFlag());
		}
		return jsonObject;
	}

	private FormComponentPropertyJsonFactory() {

	}

}
