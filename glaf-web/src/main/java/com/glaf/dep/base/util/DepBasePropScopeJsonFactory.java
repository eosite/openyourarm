package com.glaf.dep.base.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.domain.DepBasePropScope;

/**
 * 
 * JSON工厂类
 * 
 */
public class DepBasePropScopeJsonFactory {

	public static DepBasePropScope jsonToObject(JSONObject jsonObject) {
		DepBasePropScope model = new DepBasePropScope();
		if (jsonObject.containsKey("ruleId")) {
			model.setRuleId(jsonObject.getString("ruleId"));
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

	public static JSONObject toJsonObject(DepBasePropScope model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ruleId", model.getRuleId());
		jsonObject.put("_ruleId_", model.getRuleId());
		if (model.getDepBaseUIId() != null) {
			jsonObject.put("depBaseUIId", model.getDepBaseUIId());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDateTime() != null) {
			jsonObject.put("createDateTime", DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_date", DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_datetime", DateUtils.getDateTime(model.getCreateDateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DepBasePropScope model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("ruleId", model.getRuleId());
		jsonObject.put("_ruleId_", model.getRuleId());
		if (model.getDepBaseUIId() != null) {
			jsonObject.put("gdepBaseUIId", model.getDepBaseUIId());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDateTime() != null) {
			jsonObject.put("createDateTime", DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_date", DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_datetime", DateUtils.getDateTime(model.getCreateDateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DepBasePropScope> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBasePropScope model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBasePropScope> arrayToList(JSONArray array) {
		java.util.List<DepBasePropScope> list = new java.util.ArrayList<DepBasePropScope>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBasePropScope model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DepBasePropScopeJsonFactory() {

	}

}
