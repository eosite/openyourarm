package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.FlowActivD;

/**
 * 
 * JSON工厂类
 *
 */
public class FlowActivDJsonFactory {

	public static FlowActivD jsonToObject(JSONObject jsonObject) {
		FlowActivD model = new FlowActivD();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("processId")) {
			model.setProcessId(jsonObject.getString("processId"));
		}
		if (jsonObject.containsKey("typeOfAct")) {
			model.setTypeOfAct(jsonObject.getString("typeOfAct"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("strFuntion")) {
			model.setStrFuntion(jsonObject.getString("strFuntion"));
		}
		if (jsonObject.containsKey("netRoleId")) {
			model.setNetRoleId(jsonObject.getString("netRoleId"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("timeLimit")) {
			model.setTimeLimit(jsonObject.getDouble("timeLimit"));
		}
		if (jsonObject.containsKey("isSave")) {
			model.setIsSave(jsonObject.getInteger("isSave"));
		}
		if (jsonObject.containsKey("isDel")) {
			model.setIsDel(jsonObject.getInteger("isDel"));
		}
		if (jsonObject.containsKey("intSelectUser")) {
			model.setIntSelectUser(jsonObject.getInteger("intSelectUser"));
		}
		if (jsonObject.containsKey("intUseDomain")) {
			model.setIntUseDomain(jsonObject.getInteger("intUseDomain"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FlowActivD model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		}
		if (model.getTypeOfAct() != null) {
			jsonObject.put("typeOfAct", model.getTypeOfAct());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getStrFuntion() != null) {
			jsonObject.put("strFuntion", model.getStrFuntion());
		}
		if (model.getNetRoleId() != null) {
			jsonObject.put("netRoleId", model.getNetRoleId());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("timeLimit", model.getTimeLimit());
		jsonObject.put("isSave", model.getIsSave());
		jsonObject.put("isDel", model.getIsDel());
		jsonObject.put("intSelectUser", model.getIntSelectUser());
		jsonObject.put("intUseDomain", model.getIntUseDomain());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FlowActivD model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		}
		if (model.getTypeOfAct() != null) {
			jsonObject.put("typeOfAct", model.getTypeOfAct());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getStrFuntion() != null) {
			jsonObject.put("strFuntion", model.getStrFuntion());
		}
		if (model.getNetRoleId() != null) {
			jsonObject.put("netRoleId", model.getNetRoleId());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("timeLimit", model.getTimeLimit());
		jsonObject.put("isSave", model.getIsSave());
		jsonObject.put("isDel", model.getIsDel());
		jsonObject.put("intSelectUser", model.getIntSelectUser());
		jsonObject.put("intUseDomain", model.getIntUseDomain());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FlowActivD> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FlowActivD model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FlowActivD> arrayToList(JSONArray array) {
		java.util.List<FlowActivD> list = new java.util.ArrayList<FlowActivD>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FlowActivD model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FlowActivDJsonFactory() {

	}

}
