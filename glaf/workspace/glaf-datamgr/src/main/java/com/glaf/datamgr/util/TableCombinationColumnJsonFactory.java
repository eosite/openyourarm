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

package com.glaf.datamgr.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.datamgr.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class TableCombinationColumnJsonFactory {

	public static TableCombinationColumn jsonToObject(JSONObject jsonObject) {
		TableCombinationColumn model = new TableCombinationColumn();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("combinationId")) {
			model.setCombinationId(jsonObject.getLong("combinationId"));
		}
		if (jsonObject.containsKey("sourceColumnName")) {
			model.setSourceColumnName(jsonObject.getString("sourceColumnName"));
		}
		if (jsonObject.containsKey("datasetId")) {
			model.setDatasetId(jsonObject.getString("datasetId"));
		}
		if (jsonObject.containsKey("targetTableName")) {
			model.setTargetTableName(jsonObject.getString("targetTableName"));
		}
		if (jsonObject.containsKey("targetColumnName")) {
			model.setTargetColumnName(jsonObject.getString("targetColumnName"));
		}
		if (jsonObject.containsKey("targetColumnLabel")) {
			model.setTargetColumnLabel(jsonObject.getString("targetColumnLabel"));
		}
		if (jsonObject.containsKey("initValue")) {
			model.setInitValue(jsonObject.getString("initValue"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TableCombinationColumn model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("combinationId", model.getCombinationId());
		if (model.getSourceColumnName() != null) {
			jsonObject.put("sourceColumnName", model.getSourceColumnName());
		}
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getTargetColumnName() != null) {
			jsonObject.put("targetColumnName", model.getTargetColumnName());
		}
		if (model.getTargetColumnLabel() != null) {
			jsonObject.put("targetColumnLabel", model.getTargetColumnLabel());
		}
		if (model.getInitValue() != null) {
			jsonObject.put("initValue", model.getInitValue());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TableCombinationColumn model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("combinationId", model.getCombinationId());
		if (model.getSourceColumnName() != null) {
			jsonObject.put("sourceColumnName", model.getSourceColumnName());
		}
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getTargetColumnName() != null) {
			jsonObject.put("targetColumnName", model.getTargetColumnName());
		}
		if (model.getTargetColumnLabel() != null) {
			jsonObject.put("targetColumnLabel", model.getTargetColumnLabel());
		}
		if (model.getInitValue() != null) {
			jsonObject.put("initValue", model.getInitValue());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TableCombinationColumn> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TableCombinationColumn model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TableCombinationColumn> arrayToList(JSONArray array) {
		java.util.List<TableCombinationColumn> list = new java.util.ArrayList<TableCombinationColumn>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TableCombinationColumn model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TableCombinationColumnJsonFactory() {

	}

}
