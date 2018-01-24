package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.FlowForwardD;

/**
 * 
 * JSON工厂类
 *
 */
public class FlowForwardDJsonFactory {

	public static FlowForwardD jsonToObject(JSONObject jsonObject) {
		FlowForwardD model = new FlowForwardD();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("processId")) {
			model.setProcessId(jsonObject.getString("processId"));
		}
		if (jsonObject.containsKey("activId")) {
			model.setActivId(jsonObject.getString("activId"));
		}
		if (jsonObject.containsKey("activPre")) {
			model.setActivPre(jsonObject.getString("activPre"));
		}
		if (jsonObject.containsKey("activNext")) {
			model.setActivNext(jsonObject.getString("activNext"));
		}
		if (jsonObject.containsKey("isSave")) {
			model.setIsSave(jsonObject.getInteger("isSave"));
		}
		if (jsonObject.containsKey("isDel")) {
			model.setIsDel(jsonObject.getInteger("isDel"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FlowForwardD model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		}
		if (model.getActivId() != null) {
			jsonObject.put("activId", model.getActivId());
		}
		if (model.getActivPre() != null) {
			jsonObject.put("activPre", model.getActivPre());
		}
		if (model.getActivNext() != null) {
			jsonObject.put("activNext", model.getActivNext());
		}
		jsonObject.put("isSave", model.getIsSave());
		jsonObject.put("isDel", model.getIsDel());
		jsonObject.put("listNo", model.getListNo());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FlowForwardD model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		}
		if (model.getActivId() != null) {
			jsonObject.put("activId", model.getActivId());
		}
		if (model.getActivPre() != null) {
			jsonObject.put("activPre", model.getActivPre());
		}
		if (model.getActivNext() != null) {
			jsonObject.put("activNext", model.getActivNext());
		}
		jsonObject.put("isSave", model.getIsSave());
		jsonObject.put("isDel", model.getIsDel());
		jsonObject.put("listNo", model.getListNo());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FlowForwardD> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FlowForwardD model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FlowForwardD> arrayToList(JSONArray array) {
		java.util.List<FlowForwardD> list = new java.util.ArrayList<FlowForwardD>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FlowForwardD model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FlowForwardDJsonFactory() {

	}

}
