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

package com.glaf.matrix.data.sync.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.matrix.data.sync.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class CombineHistoryJsonFactory {

	public static CombineHistory jsonToObject(JSONObject jsonObject) {
		CombineHistory model = new CombineHistory();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("combineId")) {
			model.setCombineId(jsonObject.getLong("combineId"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("databaseName")) {
			model.setDatabaseName(jsonObject.getString("databaseName"));
		}
		if (jsonObject.containsKey("jobNo")) {
			model.setJobNo(jsonObject.getString("jobNo"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("total")) {
			model.setTotal(jsonObject.getInteger("total"));
		}
		if (jsonObject.containsKey("totalTime")) {
			model.setTotalTime(jsonObject.getInteger("totalTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CombineHistory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("combineId", model.getCombineId());
		jsonObject.put("databaseId", model.getDatabaseId());
		if (model.getDatabaseName() != null) {
			jsonObject.put("databaseName", model.getDatabaseName());
		}
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("total", model.getTotal());
		jsonObject.put("totalTime", model.getTotalTime());
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

	public static ObjectNode toObjectNode(CombineHistory model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("combineId", model.getCombineId());
		jsonObject.put("databaseId", model.getDatabaseId());
		if (model.getDatabaseName() != null) {
			jsonObject.put("databaseName", model.getDatabaseName());
		}
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("total", model.getTotal());
		jsonObject.put("totalTime", model.getTotalTime());
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

	public static JSONArray listToArray(java.util.List<CombineHistory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CombineHistory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CombineHistory> arrayToList(JSONArray array) {
		java.util.List<CombineHistory> list = new java.util.ArrayList<CombineHistory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CombineHistory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CombineHistoryJsonFactory() {

	}

}
