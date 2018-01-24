package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.FlowActiv;

/**
 * 
 * JSON工厂类
 *
 */
public class FlowActivJsonFactory {

	public static FlowActiv jsonToObject(JSONObject jsonObject) {
		FlowActiv model = new FlowActiv();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("activDId")) {
			model.setActivDId(jsonObject.getString("activDId"));
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
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("timeLimit")) {
			model.setTimeLimit(jsonObject.getDouble("timeLimit"));
		}
		if (jsonObject.containsKey("ctimeStart")) {
			model.setCtimeStart(jsonObject.getDate("ctimeStart"));
		}
		if (jsonObject.containsKey("ctimeEnd")) {
			model.setCtimeEnd(jsonObject.getDate("ctimeEnd"));
		}
		if (jsonObject.containsKey("state")) {
			model.setState(jsonObject.getInteger("state"));
		}
		if (jsonObject.containsKey("intBack")) {
			model.setIntBack(jsonObject.getInteger("intBack"));
		}
		if (jsonObject.containsKey("sysId")) {
			model.setSysId(jsonObject.getString("sysId"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FlowActiv model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getActivDId() != null) {
			jsonObject.put("activDId", model.getActivDId());
		}
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
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("timeLimit", model.getTimeLimit());
		if (model.getCtimeStart() != null) {
			jsonObject.put("ctimeStart",
					DateUtils.getDate(model.getCtimeStart()));
			jsonObject.put("ctimeStart_date",
					DateUtils.getDate(model.getCtimeStart()));
			jsonObject.put("ctimeStart_datetime",
					DateUtils.getDateTime(model.getCtimeStart()));
		}
		if (model.getCtimeEnd() != null) {
			jsonObject.put("ctimeEnd", DateUtils.getDate(model.getCtimeEnd()));
			jsonObject.put("ctimeEnd_date",
					DateUtils.getDate(model.getCtimeEnd()));
			jsonObject.put("ctimeEnd_datetime",
					DateUtils.getDateTime(model.getCtimeEnd()));
		}
		jsonObject.put("state", model.getState());
		jsonObject.put("intBack", model.getIntBack());
		if (model.getSysId() != null) {
			jsonObject.put("sysId", model.getSysId());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FlowActiv model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getActivDId() != null) {
			jsonObject.put("activDId", model.getActivDId());
		}
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
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("timeLimit", model.getTimeLimit());
		if (model.getCtimeStart() != null) {
			jsonObject.put("ctimeStart",
					DateUtils.getDate(model.getCtimeStart()));
			jsonObject.put("ctimeStart_date",
					DateUtils.getDate(model.getCtimeStart()));
			jsonObject.put("ctimeStart_datetime",
					DateUtils.getDateTime(model.getCtimeStart()));
		}
		if (model.getCtimeEnd() != null) {
			jsonObject.put("ctimeEnd", DateUtils.getDate(model.getCtimeEnd()));
			jsonObject.put("ctimeEnd_date",
					DateUtils.getDate(model.getCtimeEnd()));
			jsonObject.put("ctimeEnd_datetime",
					DateUtils.getDateTime(model.getCtimeEnd()));
		}
		jsonObject.put("state", model.getState());
		jsonObject.put("intBack", model.getIntBack());
		if (model.getSysId() != null) {
			jsonObject.put("sysId", model.getSysId());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FlowActiv> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FlowActiv model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FlowActiv> arrayToList(JSONArray array) {
		java.util.List<FlowActiv> list = new java.util.ArrayList<FlowActiv>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FlowActiv model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FlowActivJsonFactory() {

	}

}
