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
public class DepBaseCompPropJsonFactory {

	public static DepBaseCompProp jsonToObject(JSONObject jsonObject) {
		DepBaseCompProp model = new DepBaseCompProp();
		if (jsonObject.containsKey("Id")) {
			model.setDepBaseComponentId(jsonObject
					.getString("depBaseComponentId"));
		}
		if (jsonObject.containsKey("ruleId")) {
			model.setRuleId(jsonObject.getString("ruleId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DepBaseCompProp model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("Id", model.getDepBaseComponentId());
		jsonObject.put("depBaseComponentId", model.getDepBaseComponentId());
		if (model.getRuleId() != null) {
			jsonObject.put("ruleId", model.getRuleId());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DepBaseCompProp model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("Id", model.getDepBaseComponentId());
		jsonObject.put("depBaseComponentId", model.getDepBaseComponentId());
		if (model.getRuleId() != null) {
			jsonObject.put("ruleId", model.getRuleId());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DepBaseCompProp> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBaseCompProp model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBaseCompProp> arrayToList(JSONArray array) {
		java.util.List<DepBaseCompProp> list = new java.util.ArrayList<DepBaseCompProp>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBaseCompProp model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DepBaseCompPropJsonFactory() {

	}

}
