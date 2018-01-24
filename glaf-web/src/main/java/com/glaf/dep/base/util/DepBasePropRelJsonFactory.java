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
public class DepBasePropRelJsonFactory {

	public static DepBasePropRel jsonToObject(JSONObject jsonObject) {
		DepBasePropRel model = new DepBasePropRel();
		if (jsonObject.containsKey("ruleId")) {
			model.setRuleId(jsonObject.getString("ruleId"));
		}
		if (jsonObject.containsKey("relRuleId")) {
			model.setRelRuleId(jsonObject.getString("relRuleId"));
		}
		if (jsonObject.containsKey("relType")) {
			model.setRelType(jsonObject.getString("relType"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDateTime")) {
			model.setCreateDateTime(jsonObject.getDate("createDateTime"));
		}
		if (jsonObject.containsKey("relRuleName")) {
			model.setRelRuleName(jsonObject.getString("relRuleName"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DepBasePropRel model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ruleId", model.getRuleId());
		jsonObject.put("_ruleId_", model.getRuleId());
		if (model.getRelRuleId() != null) {
			jsonObject.put("relRuleId", model.getRelRuleId());
		}
		if (model.getRelType() != null) {
			jsonObject.put("relType", model.getRelType());
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
		if(model.getRelRuleName() != null){
			jsonObject.put("relRuleName", model.getRelRuleName());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DepBasePropRel model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("ruleId", model.getRuleId());
		jsonObject.put("_ruleId_", model.getRuleId());
		if (model.getRelRuleId() != null) {
			jsonObject.put("relRuleId", model.getRelRuleId());
		}
		if (model.getRelType() != null) {
			jsonObject.put("relType", model.getRelType());
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
		if(model.getRelRuleName() != null){
			jsonObject.put("relRuleName", model.getRelRuleName());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DepBasePropRel> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBasePropRel model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBasePropRel> arrayToList(JSONArray array) {
		java.util.List<DepBasePropRel> list = new java.util.ArrayList<DepBasePropRel>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBasePropRel model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DepBasePropRelJsonFactory() {

	}

}
