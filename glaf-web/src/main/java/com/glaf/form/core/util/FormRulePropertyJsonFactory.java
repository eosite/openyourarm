package com.glaf.form.core.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.form.core.domain.FormRuleProperty;

/**
 * 
 * JSON工厂类
 * 
 */
public class FormRulePropertyJsonFactory {

	public static FormRuleProperty jsonToObject(JSONObject jsonObject) {
		FormRuleProperty model = new FormRuleProperty();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("ruleId")) {
			model.setRuleId(jsonObject.getString("ruleId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormRuleProperty model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getRuleId() != null) {
			jsonObject.put("ruleId", model.getRuleId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormRuleProperty model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getRuleId() != null) {
			jsonObject.put("ruleId", model.getRuleId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormRuleProperty> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormRuleProperty model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormRuleProperty> arrayToList(JSONArray array) {
		java.util.List<FormRuleProperty> list = new java.util.ArrayList<FormRuleProperty>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormRuleProperty model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormRulePropertyJsonFactory() {

	}

}
