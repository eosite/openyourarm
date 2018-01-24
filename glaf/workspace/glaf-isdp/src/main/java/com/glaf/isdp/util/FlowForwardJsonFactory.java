package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.FlowForward;

/**
 * 
 * JSON工厂类
 *
 */
public class FlowForwardJsonFactory {

	public static FlowForward jsonToObject(JSONObject jsonObject) {
		FlowForward model = new FlowForward();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("processId")) {
			model.setProcessId(jsonObject.getString("processId"));
		}
		if (jsonObject.containsKey("activDId")) {
			model.setActivDId(jsonObject.getString("activDId"));
		}
		if (jsonObject.containsKey("activDNext")) {
			model.setActivDNext(jsonObject.getString("activDNext"));
		}
		if (jsonObject.containsKey("sendType")) {
			model.setSendType(jsonObject.getInteger("sendType"));
		}
		if (jsonObject.containsKey("sendTimes")) {
			model.setSendTimes(jsonObject.getInteger("sendTimes"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FlowForward model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		}
		if (model.getActivDId() != null) {
			jsonObject.put("activDId", model.getActivDId());
		}
		if (model.getActivDNext() != null) {
			jsonObject.put("activDNext", model.getActivDNext());
		}
		jsonObject.put("sendType", model.getSendType());
		jsonObject.put("sendTimes", model.getSendTimes());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FlowForward model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		}
		if (model.getActivDId() != null) {
			jsonObject.put("activDId", model.getActivDId());
		}
		if (model.getActivDNext() != null) {
			jsonObject.put("activDNext", model.getActivDNext());
		}
		jsonObject.put("sendType", model.getSendType());
		jsonObject.put("sendTimes", model.getSendTimes());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FlowForward> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FlowForward model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FlowForward> arrayToList(JSONArray array) {
		java.util.List<FlowForward> list = new java.util.ArrayList<FlowForward>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FlowForward model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FlowForwardJsonFactory() {

	}

}
