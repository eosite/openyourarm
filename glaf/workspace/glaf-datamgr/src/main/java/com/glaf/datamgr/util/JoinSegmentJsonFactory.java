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
public class JoinSegmentJsonFactory {

	public static JoinSegment jsonToObject(JSONObject jsonObject) {
		JoinSegment model = new JoinSegment();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("datasetId")) {
			model.setDatasetId(jsonObject.getString("datasetId"));
		}
		if (jsonObject.containsKey("sourceTable")) {
			model.setSourceTable(jsonObject.getString("sourceTable"));
		}
		if (jsonObject.containsKey("sourceColumn")) {
			model.setSourceColumn(jsonObject.getString("sourceColumn"));
		}
		if (jsonObject.containsKey("targetTable")) {
			model.setTargetTable(jsonObject.getString("targetTable"));
		}
		if (jsonObject.containsKey("targetColumn")) {
			model.setTargetColumn(jsonObject.getString("targetColumn"));
		}
		if (jsonObject.containsKey("connector")) {
			model.setConnector(jsonObject.getString("connector"));
		}
		if (jsonObject.containsKey("ordinal")) {
			model.setOrdinal(jsonObject.getInteger("ordinal"));
		}

		return model;
	}

	public static JSONObject toJsonObject(JoinSegment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getSourceTable() != null) {
			jsonObject.put("sourceTable", model.getSourceTable());
		}
		if (model.getSourceColumn() != null) {
			jsonObject.put("sourceColumn", model.getSourceColumn());
		}
		if (model.getTargetTable() != null) {
			jsonObject.put("targetTable", model.getTargetTable());
		}
		if (model.getTargetColumn() != null) {
			jsonObject.put("targetColumn", model.getTargetColumn());
		}
		if (model.getConnector() != null) {
			jsonObject.put("connector", model.getConnector());
		}
		jsonObject.put("ordinal", model.getOrdinal());

		return jsonObject;
	}

	public static ObjectNode toObjectNode(JoinSegment model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getSourceTable() != null) {
			jsonObject.put("sourceTable", model.getSourceTable());
		}
		if (model.getSourceColumn() != null) {
			jsonObject.put("sourceColumn", model.getSourceColumn());
		}
		if (model.getTargetTable() != null) {
			jsonObject.put("targetTable", model.getTargetTable());
		}
		if (model.getTargetColumn() != null) {
			jsonObject.put("targetColumn", model.getTargetColumn());
		}
		if (model.getConnector() != null) {
			jsonObject.put("connector", model.getConnector());
		}
		jsonObject.put("ordinal", model.getOrdinal());

		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<JoinSegment> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (JoinSegment model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<JoinSegment> arrayToList(JSONArray array) {
		java.util.List<JoinSegment> list = new java.util.ArrayList<JoinSegment>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			JoinSegment model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private JoinSegmentJsonFactory() {

	}

}
