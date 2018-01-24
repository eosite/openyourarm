package com.glaf.dep.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.domain.DepBaseCompScope;

/**
 * 
 * JSON工厂类
 * 
 */
public class DepBaseCompScopeJsonFactory {

	public static DepBaseCompScope jsonToObject(JSONObject jsonObject) {
		DepBaseCompScope model = new DepBaseCompScope();
		if (jsonObject.containsKey("id")) {
			model.setDepBaseComponentId(jsonObject
					.getString("depBaseComponentId"));
		}
		if (jsonObject.containsKey("depBaseUIId")) {
			model.setDepBaseUIId(jsonObject.getString("depBaseUIId"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDateTime")) {
			model.setCreateDateTime(jsonObject.getDate("createDateTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DepBaseCompScope model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getDepBaseComponentId());
		jsonObject.put("_id_", model.getDepBaseComponentId());
		jsonObject.put("_oid_", model.getDepBaseComponentId());
		if (model.getDepBaseUIId() != null) {
			jsonObject.put("depBaseUIId", model.getDepBaseUIId());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDateTime() != null) {
			jsonObject.put("createDateTime",
					DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_date",
					DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_datetime",
					DateUtils.getDateTime(model.getCreateDateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DepBaseCompScope model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getDepBaseComponentId());
		jsonObject.put("_id_", model.getDepBaseComponentId());
		jsonObject.put("_oid_", model.getDepBaseComponentId());
		if (model.getDepBaseUIId() != null) {
			jsonObject.put("depBaseUIId", model.getDepBaseUIId());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDateTime() != null) {
			jsonObject.put("createDateTime",
					DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_date",
					DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_datetime",
					DateUtils.getDateTime(model.getCreateDateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DepBaseCompScope> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBaseCompScope model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBaseCompScope> arrayToList(JSONArray array) {
		java.util.List<DepBaseCompScope> list = new java.util.ArrayList<DepBaseCompScope>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBaseCompScope model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DepBaseCompScopeJsonFactory() {

	}

}
