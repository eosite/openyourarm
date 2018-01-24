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
public class EntitySegmentJsonFactory {

	public static java.util.List<EntitySegment> arrayToList(JSONArray array) {
		java.util.List<EntitySegment> list = new java.util.ArrayList<EntitySegment>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EntitySegment model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static EntitySegment jsonToObject(JSONObject jsonObject) {
		EntitySegment model = new EntitySegment();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("operation")) {
			model.setOperation(jsonObject.getString("operation"));
		}
		if (jsonObject.containsKey("namespace")) {
			model.setNamespace(jsonObject.getString("namespace"));
		}
		if (jsonObject.containsKey("parameterType")) {
			model.setParameterType(jsonObject.getString("parameterType"));
		}
		if (jsonObject.containsKey("resultType")) {
			model.setResultType(jsonObject.getString("resultType"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<EntitySegment> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EntitySegment model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(EntitySegment model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getOperation() != null) {
			jsonObject.put("operation", model.getOperation());
		}
		if (model.getNamespace() != null) {
			jsonObject.put("namespace", model.getNamespace());
		}
		if (model.getParameterType() != null) {
			jsonObject.put("parameterType", model.getParameterType());
		}
		if (model.getResultType() != null) {
			jsonObject.put("resultType", model.getResultType());
		}
		jsonObject.put("locked", model.getLocked());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(EntitySegment model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getOperation() != null) {
			jsonObject.put("operation", model.getOperation());
		}
		if (model.getNamespace() != null) {
			jsonObject.put("namespace", model.getNamespace());
		}
		if (model.getParameterType() != null) {
			jsonObject.put("parameterType", model.getParameterType());
		}
		if (model.getResultType() != null) {
			jsonObject.put("resultType", model.getResultType());
		}
		jsonObject.put("locked", model.getLocked());
		return jsonObject;
	}

	private EntitySegmentJsonFactory() {

	}

}
