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
public class OrderBySegmentJsonFactory {

	public static OrderBySegment jsonToObject(JSONObject jsonObject) {
		OrderBySegment model = new OrderBySegment();
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
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getString("sort"));
		}
		if (jsonObject.containsKey("ordinal")) {
			model.setOrdinal(jsonObject.getInteger("ordinal"));
		}
		
		return model;
	}

	public static JSONObject toJsonObject(OrderBySegment model) {
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
		}
		if (model.getSort() != null) {
			jsonObject.put("sort", model.getSort());
		}
		jsonObject.put("ordinal", model.getOrdinal());

		return jsonObject;
	}

	public static ObjectNode toObjectNode(OrderBySegment model) {
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
		if (model.getSort() != null) {
			jsonObject.put("sort", model.getSort());
		}
		jsonObject.put("ordinal", model.getOrdinal());

		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<OrderBySegment> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (OrderBySegment model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<OrderBySegment> arrayToList(JSONArray array) {
		java.util.List<OrderBySegment> list = new java.util.ArrayList<OrderBySegment>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			OrderBySegment model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private OrderBySegmentJsonFactory() {

	}

}
