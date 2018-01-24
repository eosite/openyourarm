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
public class TableCombinationJsonFactory {

	public static TableCombination jsonToObject(JSONObject jsonObject) {
		TableCombination model = new TableCombination();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("databaseIds")) {
			model.setDatabaseIds(jsonObject.getString("databaseIds"));
		}
		if (jsonObject.containsKey("templateTableName")) {
			model.setTemplateTableName(jsonObject.getString("templateTableName"));
		}
		if (jsonObject.containsKey("primaryKey")) {
			model.setPrimaryKey(jsonObject.getString("primaryKey"));
		}
		if (jsonObject.containsKey("uniquenessKey")) {
			model.setUniquenessKey(jsonObject.getString("uniquenessKey"));
		}
		if (jsonObject.containsKey("syncColumns")) {
			model.setSyncColumns(jsonObject.getString("syncColumns"));
		}
		if (jsonObject.containsKey("syncTableNames")) {
			model.setSyncTableNames(jsonObject.getString("syncTableNames"));
		}
		if (jsonObject.containsKey("datasetIds")) {
			model.setDatasetIds(jsonObject.getString("datasetIds"));
		}
		if (jsonObject.containsKey("targetDatabaseId")) {
			model.setTargetDatabaseId(jsonObject.getLong("targetDatabaseId"));
		}
		if (jsonObject.containsKey("targetTableName")) {
			model.setTargetTableName(jsonObject.getString("targetTableName"));
		}
		if (jsonObject.containsKey("targetTableStruct")) {
			model.setTargetTableStruct(jsonObject.getString("targetTableStruct"));
		}
		if (jsonObject.containsKey("currentUserFlag")) {
			model.setCurrentUserFlag(jsonObject.getString("currentUserFlag"));
		}
		if (jsonObject.containsKey("createTableFlag")) {
			model.setCreateTableFlag(jsonObject.getString("createTableFlag"));
		}
		if (jsonObject.containsKey("scheduleFlag")) {
			model.setScheduleFlag(jsonObject.getString("scheduleFlag"));
		}
		if (jsonObject.containsKey("deleteFetch")) {
			model.setDeleteFetch(jsonObject.getString("deleteFetch"));
		}
		if (jsonObject.containsKey("syncStatus")) {
			model.setSyncStatus(jsonObject.getInteger("syncStatus"));
		}
		if (jsonObject.containsKey("syncTime")) {
			model.setSyncTime(jsonObject.getDate("syncTime"));
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
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TableCombination model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDatabaseIds() != null) {
			jsonObject.put("databaseIds", model.getDatabaseIds());
		}
		if (model.getTemplateTableName() != null) {
			jsonObject.put("templateTableName", model.getTemplateTableName());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getUniquenessKey() != null) {
			jsonObject.put("uniquenessKey", model.getUniquenessKey());
		}
		if (model.getSyncColumns() != null) {
			jsonObject.put("syncColumns", model.getSyncColumns());
		}
		if (model.getSyncTableNames() != null) {
			jsonObject.put("syncTableNames", model.getSyncTableNames());
		}
		if (model.getDatasetIds() != null) {
			jsonObject.put("datasetIds", model.getDatasetIds());
		}
		jsonObject.put("targetDatabaseId", model.getTargetDatabaseId());
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getTargetTableStruct() != null) {
			jsonObject.put("targetTableStruct", model.getTargetTableStruct());
		}
		if (model.getCurrentUserFlag() != null) {
			jsonObject.put("currentUserFlag", model.getCurrentUserFlag());
		}
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		jsonObject.put("syncStatus", model.getSyncStatus());
		if (model.getSyncTime() != null) {
			jsonObject.put("syncTime", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_date", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_datetime", DateUtils.getDateTime(model.getSyncTime()));
		}
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("locked", model.getLocked());
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

	public static ObjectNode toObjectNode(TableCombination model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getDatabaseIds() != null) {
			jsonObject.put("databaseIds", model.getDatabaseIds());
		}
		if (model.getTemplateTableName() != null) {
			jsonObject.put("templateTableName", model.getTemplateTableName());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getUniquenessKey() != null) {
			jsonObject.put("uniquenessKey", model.getUniquenessKey());
		}
		if (model.getSyncColumns() != null) {
			jsonObject.put("syncColumns", model.getSyncColumns());
		}
		if (model.getSyncTableNames() != null) {
			jsonObject.put("syncTableNames", model.getSyncTableNames());
		}
		if (model.getDatasetIds() != null) {
			jsonObject.put("datasetIds", model.getDatasetIds());
		}
		jsonObject.put("targetDatabaseId", model.getTargetDatabaseId());
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		if (model.getTargetTableStruct() != null) {
			jsonObject.put("targetTableStruct", model.getTargetTableStruct());
		}
		if (model.getCurrentUserFlag() != null) {
			jsonObject.put("currentUserFlag", model.getCurrentUserFlag());
		}
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		jsonObject.put("syncStatus", model.getSyncStatus());
		if (model.getSyncTime() != null) {
			jsonObject.put("syncTime", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_date", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_datetime", DateUtils.getDateTime(model.getSyncTime()));
		}
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("locked", model.getLocked());
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

	public static JSONArray listToArray(java.util.List<TableCombination> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TableCombination model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TableCombination> arrayToList(JSONArray array) {
		java.util.List<TableCombination> list = new java.util.ArrayList<TableCombination>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TableCombination model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TableCombinationJsonFactory() {

	}

}
