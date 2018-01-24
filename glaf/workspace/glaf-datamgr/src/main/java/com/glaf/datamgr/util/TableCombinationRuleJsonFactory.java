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
public class TableCombinationRuleJsonFactory {

	public static TableCombinationRule jsonToObject(JSONObject jsonObject) {
		TableCombinationRule model = new TableCombinationRule();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("combinationId")) {
			model.setCombinationId(jsonObject.getLong("combinationId"));
		}
		if (jsonObject.containsKey("targetTableName")) {
			model.setTargetTableName(jsonObject.getString("targetTableName"));
		}
		if (jsonObject.containsKey("targetTablePrimaryKey")) {
			model.setTargetTablePrimaryKey(jsonObject.getString("targetTablePrimaryKey"));
		}
		if (jsonObject.containsKey("uniquenessColumn")) {
			model.setUniquenessColumn(jsonObject.getString("uniquenessColumn"));
		}
		if (jsonObject.containsKey("sqlCondition")) {
			model.setSqlCondition(jsonObject.getString("sqlCondition"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TableCombinationRule model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("combinationId", model.getCombinationId());
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getTargetTablePrimaryKey() != null) {
			jsonObject.put("targetTablePrimaryKey", model.getTargetTablePrimaryKey());
		}
		if (model.getUniquenessColumn() != null) {
			jsonObject.put("uniquenessColumn", model.getUniquenessColumn());
		}
		if (model.getSqlCondition() != null) {
			jsonObject.put("sqlCondition", model.getSqlCondition());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TableCombinationRule model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("combinationId", model.getCombinationId());
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getTargetTablePrimaryKey() != null) {
			jsonObject.put("targetTablePrimaryKey", model.getTargetTablePrimaryKey());
		}
		if (model.getUniquenessColumn() != null) {
			jsonObject.put("uniquenessColumn", model.getUniquenessColumn());
		}
		if (model.getSqlCondition() != null) {
			jsonObject.put("sqlCondition", model.getSqlCondition());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TableCombinationRule> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TableCombinationRule model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TableCombinationRule> arrayToList(JSONArray array) {
		java.util.List<TableCombinationRule> list = new java.util.ArrayList<TableCombinationRule>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TableCombinationRule model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TableCombinationRuleJsonFactory() {

	}

}
