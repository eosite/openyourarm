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
public class DataExportJsonFactory {

	public static java.util.List<DataExport> arrayToList(JSONArray array) {
		java.util.List<DataExport> list = new java.util.ArrayList<DataExport>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DataExport model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static DataExport jsonToObject(JSONObject jsonObject) {
		DataExport model = new DataExport();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
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
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("operation")) {
			model.setOperation(jsonObject.getString("operation"));
		}
		if (jsonObject.containsKey("exportFlag")) {
			model.setExportFlag(jsonObject.getString("exportFlag"));
		}
		if (jsonObject.containsKey("exportName")) {
			model.setExportName(jsonObject.getString("exportName"));
		}
		if (jsonObject.containsKey("lastExportStatus")) {
			model.setLastExportStatus(jsonObject.getInteger("lastExportStatus"));
		}
		if (jsonObject.containsKey("lastExportTime")) {
			model.setLastExportTime(jsonObject.getDate("lastExportTime"));
		}
		if (jsonObject.containsKey("exportType")) {
			model.setExportType(jsonObject.getString("exportType"));
		}
		if (jsonObject.containsKey("tables")) {
			model.setTables(jsonObject.getString("tables"));
		}
		if (jsonObject.containsKey("scheduleFlag")) {
			model.setScheduleFlag(jsonObject.getString("scheduleFlag"));
		}
		if (jsonObject.containsKey("createTableFlag")) {
			model.setCreateTableFlag(jsonObject.getString("createTableFlag"));
		}
		if (jsonObject.containsKey("deleteFetch")) {
			model.setDeleteFetch(jsonObject.getString("deleteFetch"));
		}
		if (jsonObject.containsKey("publicFlag")) {
			model.setPublicFlag(jsonObject.getString("publicFlag"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		return model;
	}

	public static JSONArray listToArray(java.util.List<DataExport> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DataExport model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(DataExport model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());

		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getOperation() != null) {
			jsonObject.put("operation", model.getOperation());
		}
		if (model.getExportFlag() != null) {
			jsonObject.put("exportFlag", model.getExportFlag());
		}
		if (model.getExportName() != null) {
			jsonObject.put("exportName", model.getExportName());
		}
		jsonObject.put("lastExportStatus", model.getLastExportStatus());
		if (model.getLastExportTime() != null) {
			jsonObject.put("lastExportTime", DateUtils.getDate(model.getLastExportTime()));
			jsonObject.put("lastExportTime_date", DateUtils.getDate(model.getLastExportTime()));
			jsonObject.put("lastExportTime_datetime", DateUtils.getDateTime(model.getLastExportTime()));
		}
		if (model.getExportType() != null) {
			jsonObject.put("exportType", model.getExportType());
		}
		if (model.getTables() != null) {
			jsonObject.put("tables", model.getTables());
		}
		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getPublicFlag() != null) {
			jsonObject.put("publicFlag", model.getPublicFlag());
		}
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

	public static ObjectNode toObjectNode(DataExport model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());

		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getOperation() != null) {
			jsonObject.put("operation", model.getOperation());
		}
		if (model.getExportFlag() != null) {
			jsonObject.put("exportFlag", model.getExportFlag());
		}
		if (model.getExportName() != null) {
			jsonObject.put("exportName", model.getExportName());
		}
		jsonObject.put("lastExportStatus", model.getLastExportStatus());
		if (model.getLastExportTime() != null) {
			jsonObject.put("lastExportTime", DateUtils.getDate(model.getLastExportTime()));
			jsonObject.put("lastExportTime_date", DateUtils.getDate(model.getLastExportTime()));
			jsonObject.put("lastExportTime_datetime", DateUtils.getDateTime(model.getLastExportTime()));
		}
		if (model.getExportType() != null) {
			jsonObject.put("exportType", model.getExportType());
		}
		if (model.getTables() != null) {
			jsonObject.put("tableIds", model.getTables());
		}
		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getPublicFlag() != null) {
			jsonObject.put("publicFlag", model.getPublicFlag());
		}
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

	private DataExportJsonFactory() {

	}

}
