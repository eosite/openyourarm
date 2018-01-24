package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class FormWorkFlowRuleJsonFactory {

	public static FormWorkFlowRule jsonToObject(JSONObject jsonObject) {
		FormWorkFlowRule model = new FormWorkFlowRule();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("actResDefId")) {
			model.setActResDefId(jsonObject.getLong("actResDefId"));
		}
		if (jsonObject.containsKey("actResourceId")) {
			model.setActResourceId(jsonObject.getString("actResourceId"));
		}
		if (jsonObject.containsKey("actTaskId")) {
			model.setActTaskId(jsonObject.getString("actTaskId"));
		}
		if (jsonObject.containsKey("actTaskName")) {
			model.setActTaskName(jsonObject.getString("actTaskName"));
		}
		if (jsonObject.containsKey("bytes")) {
			model.setBytes(jsonObject.getString("bytes"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("defId")) {
			model.setPageId(jsonObject.getString("defId"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormWorkFlowRule model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("actResDefId", model.getActResDefId());
		if (model.getActResourceId() != null) {
			jsonObject.put("actResourceId", model.getActResourceId());
		}
		if (model.getActTaskId() != null) {
			jsonObject.put("actTaskId", model.getActTaskId());
		}
		if (model.getActTaskName() != null) {
			jsonObject.put("actTaskName", model.getActTaskName());
		}
		if (model.getBytes() != null) {
			jsonObject.put("bytes", model.getBytes());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDatetime() != null) {
			jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
			jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
			jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
		}
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		}
		if (model.getModifyDatetime() != null) {
			jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
			jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
			jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
		}
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		}
		jsonObject.put("version", model.getVersion());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormWorkFlowRule model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("actResDefId", model.getActResDefId());
		if (model.getActResourceId() != null) {
			jsonObject.put("actResourceId", model.getActResourceId());
		}
		if (model.getActTaskId() != null) {
			jsonObject.put("actTaskId", model.getActTaskId());
		}
		if (model.getActTaskName() != null) {
			jsonObject.put("actTaskName", model.getActTaskName());
		}
		if (model.getBytes() != null) {
			jsonObject.put("bytes", model.getBytes());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDatetime() != null) {
			jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
			jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
			jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
		}
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		}
		if (model.getModifyDatetime() != null) {
			jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
			jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
			jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
		}
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		}
		jsonObject.put("version", model.getVersion());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormWorkFlowRule> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormWorkFlowRule model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormWorkFlowRule> arrayToList(JSONArray array) {
		java.util.List<FormWorkFlowRule> list = new java.util.ArrayList<FormWorkFlowRule>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormWorkFlowRule model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormWorkFlowRuleJsonFactory() {

	}

}
