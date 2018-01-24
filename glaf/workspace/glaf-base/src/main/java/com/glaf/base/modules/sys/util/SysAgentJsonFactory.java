package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.sys.model.*;

/**
 * 
 * JSON工厂类
 *
 */
public class SysAgentJsonFactory {

	public static SysAgent jsonToObject(JSONObject jsonObject) {
		SysAgent model = new SysAgent();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("assignFrom")) {
			model.setAssignFrom(jsonObject.getString("assignFrom"));
		}
		if (jsonObject.containsKey("assignFromName")) {
			model.setAssignFromName(jsonObject.getString("assignFromName"));
		}
		if (jsonObject.containsKey("assignTo")) {
			model.setAssignTo(jsonObject.getString("assignTo"));
		}
		if (jsonObject.containsKey("assignToName")) {
			model.setAssignToName(jsonObject.getString("assignToName"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("taskName")) {
			model.setTaskName(jsonObject.getString("taskName"));
		}
		if (jsonObject.containsKey("serviceKey")) {
			model.setServiceKey(jsonObject.getString("serviceKey"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectValue")) {
			model.setObjectValue(jsonObject.getString("objectValue"));
		}
		if (jsonObject.containsKey("startDate")) {
			model.setStartDate(jsonObject.getDate("startDate"));
		}
		if (jsonObject.containsKey("endDate")) {
			model.setEndDate(jsonObject.getDate("endDate"));
		}
		if (jsonObject.containsKey("agentType")) {
			model.setAgentType(jsonObject.getInteger("agentType"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SysAgent model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAssignFrom() != null) {
			jsonObject.put("assignFrom", model.getAssignFrom());
		}
		if (model.getAssignFromName() != null) {
			jsonObject.put("assignFromName", model.getAssignFromName());
		}
		if (model.getAssignTo() != null) {
			jsonObject.put("assignTo", model.getAssignTo());
		}
		if (model.getAssignToName() != null) {
			jsonObject.put("assignToName", model.getAssignToName());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getStartDate() != null) {
			jsonObject.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime", DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(model.getEndDate()));
		}
		jsonObject.put("agentType", model.getAgentType());
		jsonObject.put("locked", model.getLocked());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SysAgent model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAssignFrom() != null) {
			jsonObject.put("assignFrom", model.getAssignFrom());
		}
		if (model.getAssignFromName() != null) {
			jsonObject.put("assignFromName", model.getAssignFromName());
		}
		if (model.getAssignTo() != null) {
			jsonObject.put("assignTo", model.getAssignTo());
		}
		if (model.getAssignToName() != null) {
			jsonObject.put("assignToName", model.getAssignToName());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTaskName() != null) {
			jsonObject.put("taskName", model.getTaskName());
		}
		if (model.getServiceKey() != null) {
			jsonObject.put("serviceKey", model.getServiceKey());
		}
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		}
		if (model.getObjectValue() != null) {
			jsonObject.put("objectValue", model.getObjectValue());
		}
		if (model.getStartDate() != null) {
			jsonObject.put("startDate", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_date", DateUtils.getDate(model.getStartDate()));
			jsonObject.put("startDate_datetime", DateUtils.getDateTime(model.getStartDate()));
		}
		if (model.getEndDate() != null) {
			jsonObject.put("endDate", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_date", DateUtils.getDate(model.getEndDate()));
			jsonObject.put("endDate_datetime", DateUtils.getDateTime(model.getEndDate()));
		}
		jsonObject.put("agentType", model.getAgentType());
		jsonObject.put("locked", model.getLocked());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SysAgent> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SysAgent model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SysAgent> arrayToList(JSONArray array) {
		java.util.List<SysAgent> list = new java.util.ArrayList<SysAgent>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SysAgent model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SysAgentJsonFactory() {

	}

}
