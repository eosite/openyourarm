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
public class SelectSegmentJsonFactory {

	public static SelectSegment jsonToObject(JSONObject jsonObject) {
		SelectSegment model = new SelectSegment();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("datasetId")) {
			model.setDatasetId(jsonObject.getString("datasetId"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("columnLabel")) {
			model.setColumnLabel(jsonObject.getString("columnLabel"));
		}
		if (jsonObject.containsKey("columnMapping")) {
			model.setColumnMapping(jsonObject.getString("columnMapping"));
		}
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("aggregate")) {
			model.setAggregate(jsonObject.getString("aggregate"));
		}
		if (jsonObject.containsKey("output")) {
			model.setOutput(jsonObject.getString("output"));
		}
		if (jsonObject.containsKey("mapping")) {
			model.setMapping(jsonObject.getString("mapping"));
		}
		if (jsonObject.containsKey("chartCoord")) {
			model.setChartCoord(jsonObject.getString("chartCoord"));
		}
		if (jsonObject.containsKey("ordinal")) {
			model.setOrdinal(jsonObject.getInteger("ordinal"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SelectSegment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
			jsonObject.put("columnName_lowercase", model.getColumnName().toLowerCase());
			jsonObject.put("columnName_uppercase", model.getColumnName().toUpperCase());
		}
		if (model.getColumnLabel() != null) {
			jsonObject.put("columnLabel", model.getColumnLabel());
			jsonObject.put("columnLabel_lowercase", model.getColumnLabel().toLowerCase());
			jsonObject.put("columnLabel_uppercase", model.getColumnLabel().toUpperCase());
		}
		
		if (model.getColumnMapping() != null) {
			jsonObject.put("columnMapping", model.getColumnMapping());
			jsonObject.put("columnMapping_lowercase", model.getColumnMapping().toLowerCase());
			jsonObject.put("columnMapping_uppercase", model.getColumnMapping().toUpperCase());
		}
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}

		if (model.getAggregate() != null) {
			jsonObject.put("aggregate", model.getAggregate());
		}

		if (model.getOutput() != null) {
			jsonObject.put("output", model.getOutput());
		}

		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}

		if (model.getChartCoord() != null) {
			jsonObject.put("chartCoord", model.getChartCoord());
		}

		jsonObject.put("ordinal", model.getOrdinal());

		return jsonObject;
	}

	public static ObjectNode toObjectNode(SelectSegment model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getColumnLabel() != null) {
			jsonObject.put("columnLabel", model.getColumnLabel());
		}
		if (model.getColumnMapping() != null) {
			jsonObject.put("columnMapping", model.getColumnMapping());
		}
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}

		if (model.getAggregate() != null) {
			jsonObject.put("aggregate", model.getAggregate());
		}

		if (model.getOutput() != null) {
			jsonObject.put("output", model.getOutput());
		}

		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}

		if (model.getChartCoord() != null) {
			jsonObject.put("chartCoord", model.getChartCoord());
		}

		jsonObject.put("ordinal", model.getOrdinal());

		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SelectSegment> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SelectSegment model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SelectSegment> arrayToList(JSONArray array) {
		java.util.List<SelectSegment> list = new java.util.ArrayList<SelectSegment>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SelectSegment model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SelectSegmentJsonFactory() {

	}

}
