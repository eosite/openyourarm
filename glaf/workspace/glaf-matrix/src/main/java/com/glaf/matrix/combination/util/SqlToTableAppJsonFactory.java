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
import com.glaf.matrix.combination.domain.SqlToTableApp;

/**
 * 
 * JSON工厂类
 *
 */
public class SqlToTableAppJsonFactory {

	public static SqlToTableApp jsonToObject(JSONObject jsonObject) {
		SqlToTableApp model = new SqlToTableApp();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("deploymentId")) {
			model.setDeploymentId(jsonObject.getString("deploymentId"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("srcDatabaseIds")) {
			model.setSrcDatabaseIds(jsonObject.getString("srcDatabaseIds"));
		}
		if (jsonObject.containsKey("syncFlag")) {
			model.setSyncFlag(jsonObject.getString("syncFlag"));
		}
		if (jsonObject.containsKey("targetDatabaseId")) {
			model.setTargetDatabaseId(jsonObject.getLong("targetDatabaseId"));
		}
		if (jsonObject.containsKey("targetTableName")) {
			model.setTargetTableName(jsonObject.getString("targetTableName"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("active")) {
			model.setActive(jsonObject.getString("active"));
		}
		if (jsonObject.containsKey("autoSyncFlag")) {
			model.setAutoSyncFlag(jsonObject.getString("autoSyncFlag"));
		}
		if (jsonObject.containsKey("externalColumnsFlag")) {
			model.setExternalColumnsFlag(jsonObject.getString("externalColumnsFlag"));
		}
		if (jsonObject.containsKey("uniqueFlag")) {
			model.setUniqueFlag(jsonObject.getString("uniqueFlag"));
		}
		if (jsonObject.containsKey("interval")) {
			model.setInterval(jsonObject.getInteger("interval"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
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

	public static JSONObject toJsonObject(SqlToTableApp model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		jsonObject.put("sortNo", model.getSortNo());

		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		jsonObject.put("srcDatabaseIds", model.getSrcDatabaseIds());
		if (model.getSyncFlag() != null) {
			jsonObject.put("syncFlag", model.getSyncFlag());
		}

		jsonObject.put("targetDatabaseId", model.getTargetDatabaseId());

		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getActive() != null) {
			jsonObject.put("active", model.getActive());
		}
		if (model.getAutoSyncFlag() != null) {
			jsonObject.put("autoSyncFlag", model.getAutoSyncFlag());
		}
		if (model.getExternalColumnsFlag() != null) {
			jsonObject.put("externalColumnsFlag", model.getExternalColumnsFlag());
		}
		if (model.getUniqueFlag() != null) {
			jsonObject.put("uniqueFlag", model.getUniqueFlag());
		}
		jsonObject.put("interval", model.getInterval());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SqlToTableApp model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		jsonObject.put("sortNo", model.getSortNo());

		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		jsonObject.put("srcDatabaseIds", model.getSrcDatabaseIds());
		if (model.getSyncFlag() != null) {
			jsonObject.put("syncFlag", model.getSyncFlag());
		}

		jsonObject.put("targetDatabaseId", model.getTargetDatabaseId());

		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getActive() != null) {
			jsonObject.put("active", model.getActive());
		}
		if (model.getAutoSyncFlag() != null) {
			jsonObject.put("autoSyncFlag", model.getAutoSyncFlag());
		}
		if (model.getExternalColumnsFlag() != null) {
			jsonObject.put("externalColumnsFlag", model.getExternalColumnsFlag());
		}
		if (model.getUniqueFlag() != null) {
			jsonObject.put("uniqueFlag", model.getUniqueFlag());
		}
		jsonObject.put("interval", model.getInterval());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SqlToTableApp> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SqlToTableApp model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SqlToTableApp> arrayToList(JSONArray array) {
		java.util.List<SqlToTableApp> list = new java.util.ArrayList<SqlToTableApp>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SqlToTableApp model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SqlToTableAppJsonFactory() {

	}

}
