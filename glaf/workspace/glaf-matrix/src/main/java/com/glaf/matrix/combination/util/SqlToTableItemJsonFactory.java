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

package com.glaf.matrix.combination.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.matrix.combination.domain.SqlToTableItem;

/**
 * 
 * JSON工厂类
 *
 */
public class SqlToTableItemJsonFactory {

	public static SqlToTableItem jsonToObject(JSONObject jsonObject) {
		SqlToTableItem model = new SqlToTableItem();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("syncId")) {
			model.setSyncId(jsonObject.getLong("syncId"));
		}
		if (jsonObject.containsKey("deploymentId")) {
			model.setDeploymentId(jsonObject.getString("deploymentId"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("sql")) {
			model.setSql(jsonObject.getString("sql"));
		}
		if (jsonObject.containsKey("recursionSql")) {
			model.setRecursionSql(jsonObject.getString("recursionSql"));
		}
		if (jsonObject.containsKey("recursionColumns")) {
			model.setRecursionColumns(jsonObject.getString("recursionColumns"));
		}
		if (jsonObject.containsKey("primaryKey")) {
			model.setPrimaryKey(jsonObject.getString("primaryKey"));
		}
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("createTableFlag")) {
			model.setCreateTableFlag(jsonObject.getString("createTableFlag"));
		}
		if (jsonObject.containsKey("deleteFetch")) {
			model.setDeleteFetch(jsonObject.getString("deleteFetch"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SqlToTableItem model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("syncId", model.getSyncId());
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("locked", model.getLocked());

		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		if (model.getRecursionSql() != null) {
			jsonObject.put("recursionSql", model.getRecursionSql());
		}
		if (model.getRecursionColumns() != null) {
			jsonObject.put("recursionColumns", model.getRecursionColumns());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		}
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SqlToTableItem model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("syncId", model.getSyncId());
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("locked", model.getLocked());

		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		if (model.getRecursionSql() != null) {
			jsonObject.put("recursionSql", model.getRecursionSql());
		}
		if (model.getRecursionColumns() != null) {
			jsonObject.put("recursionColumns", model.getRecursionColumns());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		}
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SqlToTableItem> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SqlToTableItem model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SqlToTableItem> arrayToList(JSONArray array) {
		java.util.List<SqlToTableItem> list = new java.util.ArrayList<SqlToTableItem>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SqlToTableItem model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SqlToTableItemJsonFactory() {

	}

}
