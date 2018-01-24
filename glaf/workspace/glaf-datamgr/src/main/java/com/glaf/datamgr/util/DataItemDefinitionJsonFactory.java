package com.glaf.datamgr.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class DataItemDefinitionJsonFactory {

	public static java.util.List<DataItemDefinition> arrayToList(JSONArray array) {
		java.util.List<DataItemDefinition> list = new java.util.ArrayList<DataItemDefinition>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DataItemDefinition model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static DataItemDefinition jsonToObject(JSONObject jsonObject) {
		DataItemDefinition model = new DataItemDefinition();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("nodeId")) {
			model.setNodeId(jsonObject.getLong("nodeId"));
		}
		if (jsonObject.containsKey("category")) {
			model.setCategory(jsonObject.getString("category"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("keyColumn")) {
			model.setKeyColumn(jsonObject.getString("keyColumn"));
		}
		if (jsonObject.containsKey("valueColumn")) {
			model.setValueColumn(jsonObject.getString("valueColumn"));
		}
		if (jsonObject.containsKey("sql")) {
			model.setSql(jsonObject.getString("sql"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<DataItemDefinition> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DataItemDefinition model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(DataItemDefinition model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getKeyColumn() != null) {
			jsonObject.put("keyColumn", model.getKeyColumn());
		}
		if (model.getValueColumn() != null) {
			jsonObject.put("valueColumn", model.getValueColumn());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DataItemDefinition model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("nodeId", model.getNodeId());
		if (model.getCategory() != null) {
			jsonObject.put("category", model.getCategory());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getKeyColumn() != null) {
			jsonObject.put("keyColumn", model.getKeyColumn());
		}
		if (model.getValueColumn() != null) {
			jsonObject.put("valueColumn", model.getValueColumn());
		}
		if (model.getSql() != null) {
			jsonObject.put("sql", model.getSql());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	private DataItemDefinitionJsonFactory() {

	}

}
