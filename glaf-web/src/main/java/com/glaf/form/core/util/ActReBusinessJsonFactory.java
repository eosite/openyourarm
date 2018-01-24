package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class ActReBusinessJsonFactory {

	public static ActReBusiness jsonToObject(JSONObject jsonObject) {
		ActReBusiness model = new ActReBusiness();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("bustbName")) {
			model.setBustbName(jsonObject.getString("bustbName"));
		}
		if (jsonObject.containsKey("bustbId")) {
			model.setBustbId(jsonObject.getString("bustbId"));
		}
		if (jsonObject.containsKey("busValue")) {
			model.setBusValue(jsonObject.getString("busValue"));
		}
		if (jsonObject.containsKey("processId")) {
			model.setProcessId(jsonObject.getString("processId"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("key")) {
			model.setKey(jsonObject.getString("key"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("defId")) {
			model.setDefId(jsonObject.getString("defId"));
		}
		return model;
	}

	public static JSONObject toJsonObject(ActReBusiness model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getBustbName() != null) {
			jsonObject.put("bustbName", model.getBustbName());
		}
		if (model.getBustbId() != null) {
			jsonObject.put("bustbId", model.getBustbId());
		}
		if (model.getBusValue() != null) {
			jsonObject.put("busValue", model.getBusValue());
		}
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getKey() != null) {
			jsonObject.put("key", model.getKey());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ActReBusiness model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getBustbName() != null) {
			jsonObject.put("bustbName", model.getBustbName());
		}
		if (model.getBustbId() != null) {
			jsonObject.put("bustbId", model.getBustbId());
		}
		if (model.getBusValue() != null) {
			jsonObject.put("busValue", model.getBusValue());
		}
		if (model.getProcessId() != null) {
			jsonObject.put("processId", model.getProcessId());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getKey() != null) {
			jsonObject.put("key", model.getKey());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ActReBusiness> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ActReBusiness model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ActReBusiness> arrayToList(JSONArray array) {
		java.util.List<ActReBusiness> list = new java.util.ArrayList<ActReBusiness>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ActReBusiness model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ActReBusinessJsonFactory() {

	}

}
