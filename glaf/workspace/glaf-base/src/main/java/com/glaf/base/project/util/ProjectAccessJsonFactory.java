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
import com.glaf.base.project.domain.ProjectAccess;

/**
 * 
 * JSON工厂类
 *
 */
public class ProjectAccessJsonFactory {

	public static ProjectAccess jsonToObject(JSONObject jsonObject) {
		ProjectAccess model = new ProjectAccess();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("projectId")) {
			model.setProjectId(jsonObject.getLong("projectId"));
		}
		if (jsonObject.containsKey("actorId")) {
			model.setActorId(jsonObject.getString("actorId"));
		}
		if (jsonObject.containsKey("dynamic")) {
			model.setDynamic(jsonObject.getString("dynamic"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ProjectAccess model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("projectId", model.getProjectId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		if (model.getDynamic() != null) {
			jsonObject.put("dynamic", model.getDynamic());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ProjectAccess model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("projectId", model.getProjectId());
		if (model.getActorId() != null) {
			jsonObject.put("actorId", model.getActorId());
		}
		if (model.getDynamic() != null) {
			jsonObject.put("dynamic", model.getDynamic());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ProjectAccess> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProjectAccess model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProjectAccess> arrayToList(JSONArray array) {
		java.util.List<ProjectAccess> list = new java.util.ArrayList<ProjectAccess>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProjectAccess model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ProjectAccessJsonFactory() {

	}

}
