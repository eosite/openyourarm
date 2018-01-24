package com.glaf.base.project.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.base.project.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class ProjectDatabaseJsonFactory {

	public static ProjectDatabase jsonToObject(JSONObject jsonObject) {
		ProjectDatabase model = new ProjectDatabase();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("projectId")) {
			model.setProjectId(jsonObject.getLong("projectId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ProjectDatabase model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("projectId", model.getProjectId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ProjectDatabase model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("projectId", model.getProjectId());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ProjectDatabase> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProjectDatabase model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProjectDatabase> arrayToList(JSONArray array) {
		java.util.List<ProjectDatabase> list = new java.util.ArrayList<ProjectDatabase>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProjectDatabase model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ProjectDatabaseJsonFactory() {

	}

}
