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
public class SynchronizationRuleJsonFactory {

	public static SynchronizationRule jsonToObject(JSONObject jsonObject) {
		SynchronizationRule model = new SynchronizationRule();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("actionName")) {
			model.setActionName(jsonObject.getString("actionName"));
		}
		if (jsonObject.containsKey("formulas")) {
			model.setFormulas(jsonObject.getString("formulas"));
		}
		if (jsonObject.containsKey("targetTable")) {
			model.setTargetTable(jsonObject.getString("targetTable"));
		}
		if (jsonObject.containsKey("souceTable")) {
			model.setSouceTable(jsonObject.getString("souceTable"));
		}
		if (jsonObject.containsKey("wbsTable")) {
			model.setWbsTable(jsonObject.getString("wbsTable"));
		}
		if (jsonObject.containsKey("targetTableModel")) {
			model.setTargetTableModel(jsonObject.getString("targetTableModel"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SynchronizationRule model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getActionName() != null) {
			jsonObject.put("actionName", model.getActionName());
		}
		if (model.getFormulas() != null) {
			jsonObject.put("formulas", model.getFormulas());
		}
		if (model.getTargetTable() != null) {
			jsonObject.put("targetTable", model.getTargetTable());
		}
		if (model.getSouceTable() != null) {
			jsonObject.put("souceTable", model.getSouceTable());
		}
		if (model.getWbsTable() != null) {
			jsonObject.put("wbsTable", model.getWbsTable());
		}
		if (model.getTargetTableModel() != null) {
			jsonObject.put("targetTableModel", model.getTargetTableModel());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SynchronizationRule model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getActionName() != null) {
			jsonObject.put("actionName", model.getActionName());
		}
		if (model.getFormulas() != null) {
			jsonObject.put("formulas", model.getFormulas());
		}
		if (model.getTargetTable() != null) {
			jsonObject.put("targetTable", model.getTargetTable());
		}
		if (model.getSouceTable() != null) {
			jsonObject.put("souceTable", model.getSouceTable());
		}
		if (model.getWbsTable() != null) {
			jsonObject.put("wbsTable", model.getWbsTable());
		}
		if (model.getTargetTableModel() != null) {
			jsonObject.put("targetTableModel", model.getTargetTableModel());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SynchronizationRule> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SynchronizationRule model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SynchronizationRule> arrayToList(JSONArray array) {
		java.util.List<SynchronizationRule> list = new java.util.ArrayList<SynchronizationRule>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SynchronizationRule model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SynchronizationRuleJsonFactory() {

	}

}
