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
import com.glaf.datamgr.domain.DownloadHistory;

/**
 * 
 * JSON工厂类
 *
 */
public class DownloadHistoryJsonFactory {

	public static DownloadHistory jsonToObject(JSONObject jsonObject) {
		DownloadHistory model = new DownloadHistory();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("fileId")) {
			model.setFileId(jsonObject.getString("fileId"));
		}
		if (jsonObject.containsKey("filename")) {
			model.setFilename(jsonObject.getString("filename"));
		}
		if (jsonObject.containsKey("ip")) {
			model.setIp(jsonObject.getString("ip"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("downloadTime")) {
			model.setDownloadTime(jsonObject.getDate("downloadTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DownloadHistory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getFileId() != null) {
			jsonObject.put("fileId", model.getFileId());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getDownloadTime() != null) {
			jsonObject.put("downloadTime", DateUtils.getDate(model.getDownloadTime()));
			jsonObject.put("downloadTime_date", DateUtils.getDate(model.getDownloadTime()));
			jsonObject.put("downloadTime_datetime", DateUtils.getDateTime(model.getDownloadTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DownloadHistory model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getFileId() != null) {
			jsonObject.put("fileId", model.getFileId());
		}
		if (model.getFilename() != null) {
			jsonObject.put("filename", model.getFilename());
		}
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getDownloadTime() != null) {
			jsonObject.put("downloadTime", DateUtils.getDate(model.getDownloadTime()));
			jsonObject.put("downloadTime_date", DateUtils.getDate(model.getDownloadTime()));
			jsonObject.put("downloadTime_datetime", DateUtils.getDateTime(model.getDownloadTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DownloadHistory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DownloadHistory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DownloadHistory> arrayToList(JSONArray array) {
		java.util.List<DownloadHistory> list = new java.util.ArrayList<DownloadHistory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DownloadHistory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DownloadHistoryJsonFactory() {

	}

}
