package com.glaf.datamgr.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.datamgr.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class TableExecutionColumnJsonFactory {

	public static TableExecutionColumn jsonToObject(JSONObject jsonObject) {
		TableExecutionColumn model = new TableExecutionColumn();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("executionId")) {
			model.setExecutionId(jsonObject.getString("executionId"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("valueExpression")) {
			model.setValueExpression(jsonObject.getString("valueExpression"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TableExecutionColumn model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getExecutionId() != null) {
			jsonObject.put("executionId", model.getExecutionId());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getValueExpression() != null) {
			jsonObject.put("valueExpression", model.getValueExpression());
		}
		jsonObject.put("locked", model.getLocked());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TableExecutionColumn model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getExecutionId() != null) {
			jsonObject.put("executionId", model.getExecutionId());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getValueExpression() != null) {
			jsonObject.put("valueExpression", model.getValueExpression());
		}
		jsonObject.put("locked", model.getLocked());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TableExecutionColumn> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TableExecutionColumn model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TableExecutionColumn> arrayToList(JSONArray array) {
		java.util.List<TableExecutionColumn> list = new java.util.ArrayList<TableExecutionColumn>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TableExecutionColumn model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TableExecutionColumnJsonFactory() {

	}

}
