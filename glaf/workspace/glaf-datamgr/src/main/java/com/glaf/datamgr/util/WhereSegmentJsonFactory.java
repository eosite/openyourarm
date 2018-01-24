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
public class WhereSegmentJsonFactory {

	public static WhereSegment jsonToObject(JSONObject jsonObject) {
		WhereSegment model = new WhereSegment();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getLong("parentId"));
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
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("dynamic")) {
			model.setDynamic(jsonObject.getString("dynamic"));
		}
		if (jsonObject.containsKey("parameName")) {
			model.setParameName(jsonObject.getString("parameName"));
		}
		if (jsonObject.containsKey("parameType")) {
			model.setParameType(jsonObject.getString("parameType"));
		}
		if (jsonObject.containsKey("collection")) {
			model.setCollection(jsonObject.getString("collection"));
		}
		if (jsonObject.containsKey("operator")) {
			model.setOperator(jsonObject.getString("operator"));
		}
		if (jsonObject.containsKey("connector")) {
			model.setConnector(jsonObject.getString("connector"));
		}
		if (jsonObject.containsKey("required")) {
			model.setRequired(jsonObject.getString("required"));
		}
		if (jsonObject.containsKey("ordinal")) {
			model.setOrdinal(jsonObject.getInteger("ordinal"));
		}

		return model;
	}

	public static JSONObject toJsonObject(WhereSegment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parentId", model.getParentId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		}
		if (model.getDynamic() != null) {
			jsonObject.put("dynamic", model.getDynamic());
		}
		if (model.getParameName() != null) {
			jsonObject.put("parameName", model.getParameName());
		}
		if (model.getParameType() != null) {
			jsonObject.put("parameType", model.getParameType());
		}
		if (model.getCollection() != null) {
			jsonObject.put("collection", model.getCollection());
		}
		if (model.getOperator() != null) {
			jsonObject.put("operator", model.getOperator());
		}
		if (model.getConnector() != null) {
			jsonObject.put("connector", model.getConnector());
		}
		if (model.getRequired() != null) {
			jsonObject.put("required", model.getRequired());
		}
		jsonObject.put("ordinal", model.getOrdinal());

		return jsonObject;
	}

	public static ObjectNode toObjectNode(WhereSegment model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("parentId", model.getParentId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		}
		if (model.getDynamic() != null) {
			jsonObject.put("dynamic", model.getDynamic());
		}
		if (model.getParameName() != null) {
			jsonObject.put("parameName", model.getParameName());
		}
		if (model.getParameType() != null) {
			jsonObject.put("parameType", model.getParameType());
		}
		if (model.getCollection() != null) {
			jsonObject.put("collection", model.getCollection());
		}
		if (model.getOperator() != null) {
			jsonObject.put("operator", model.getOperator());
		}
		if (model.getConnector() != null) {
			jsonObject.put("connector", model.getConnector());
		}
		if (model.getRequired() != null) {
			jsonObject.put("required", model.getRequired());
		}
		jsonObject.put("ordinal", model.getOrdinal());

		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<WhereSegment> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (WhereSegment model : list) {
				JSONObject jsonObject = model.toJsonObject();
				if (jsonObject.toJSONString().length() > 0) {
					array.add(jsonObject);
				}
			}
		}
		return array;
	}

	public static java.util.List<WhereSegment> arrayToList(JSONArray array) {
		java.util.List<WhereSegment> list = new java.util.ArrayList<WhereSegment>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			if (jsonObject.toJSONString().length() > 0) {
				WhereSegment model = jsonToObject(jsonObject);
				list.add(model);
			}
		}
		return list;
	}

	private WhereSegmentJsonFactory() {

	}

}
