package com.glaf.dep.base.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.domain.*;

/**
 * 
 * JSON工厂类
 * 
 */
public class DepBasePropCategoryJsonFactory {

	public static DepBasePropCategory jsonToObject(JSONObject jsonObject) {
		DepBasePropCategory model = new DepBasePropCategory();
		if (jsonObject.containsKey("ruleId")) {
			model.setRuleId(jsonObject.getString("ruleId"));
		}
		if (jsonObject.containsKey("id")) {
			model.setDepBaseCategoryId(jsonObject.getLong("depBaseCategoryId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DepBasePropCategory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ruleId", model.getRuleId());
		jsonObject.put("_ruleId_", model.getRuleId());
		jsonObject.put("depBaseCategoryId", model.getDepBaseCategoryId());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DepBasePropCategory model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("ruleId", model.getRuleId());
		jsonObject.put("_ruleId_", model.getRuleId());
		jsonObject.put("depBaseCategoryId", model.getDepBaseCategoryId());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DepBasePropCategory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBasePropCategory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBasePropCategory> arrayToList(
			JSONArray array) {
		java.util.List<DepBasePropCategory> list = new java.util.ArrayList<DepBasePropCategory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBasePropCategory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DepBasePropCategoryJsonFactory() {

	}

}
