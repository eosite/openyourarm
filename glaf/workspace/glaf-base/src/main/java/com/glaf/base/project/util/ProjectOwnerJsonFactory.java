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
package com.glaf.base.project.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.project.domain.ProjectOwner;

/**
 * 
 * JSON工厂类
 *
 */
public class ProjectOwnerJsonFactory {

	public static ProjectOwner jsonToObject(JSONObject jsonObject) {
		ProjectOwner model = new ProjectOwner();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("projectId")) {
			model.setProjectId(jsonObject.getLong("projectId"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ProjectOwner model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("projectId", model.getProjectId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ProjectOwner model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("projectId", model.getProjectId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ProjectOwner> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProjectOwner model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProjectOwner> arrayToList(JSONArray array) {
		java.util.List<ProjectOwner> list = new java.util.ArrayList<ProjectOwner>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProjectOwner model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ProjectOwnerJsonFactory() {

	}

}
