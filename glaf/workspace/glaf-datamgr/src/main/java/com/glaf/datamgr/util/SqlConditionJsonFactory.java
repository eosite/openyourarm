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

import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class SqlConditionJsonFactory {

	public static SqlCondition jsonToObject(JSONObject jsonObject) {
		SqlCondition model = new SqlCondition();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("sqlDefId")) {
			model.setSqlDefId(jsonObject.getLong("sqlDefId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("required")) {
			model.setRequired(jsonObject.getString("required"));
		}
		if (jsonObject.containsKey("collection")) {
			model.setCollection(jsonObject.getString("collection"));
		}
		if (jsonObject.containsKey("operator")) {
			model.setOperator(jsonObject.getString("operator"));
		}
		if (jsonObject.containsKey("segment")) {
			model.setSegment(jsonObject.getString("segment"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SqlCondition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getRequired() != null) {
			jsonObject.put("required", model.getRequired());
		}
		if (model.getCollection() != null) {
			jsonObject.put("collection", model.getCollection());
		}
		if (model.getOperator() != null) {
			jsonObject.put("operator", model.getOperator());
		}
		if (model.getSegment() != null) {
			jsonObject.put("segment", model.getSegment());
		}
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime",
					DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SqlCondition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getRequired() != null) {
			jsonObject.put("required", model.getRequired());
		}
		if (model.getCollection() != null) {
			jsonObject.put("collection", model.getCollection());
		}
		if (model.getOperator() != null) {
			jsonObject.put("operator", model.getOperator());
		}
		if (model.getSegment() != null) {
			jsonObject.put("segment", model.getSegment());
		}
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date",
					DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime",
					DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SqlCondition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SqlCondition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SqlCondition> arrayToList(JSONArray array) {
		java.util.List<SqlCondition> list = new java.util.ArrayList<SqlCondition>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SqlCondition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SqlConditionJsonFactory() {

	}

}
