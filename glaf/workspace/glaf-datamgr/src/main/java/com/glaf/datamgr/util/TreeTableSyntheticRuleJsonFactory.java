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
public class TreeTableSyntheticRuleJsonFactory {

	public static TreeTableSyntheticRule jsonToObject(JSONObject jsonObject) {
		TreeTableSyntheticRule model = new TreeTableSyntheticRule();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("syntheticId")) {
			model.setSyntheticId(jsonObject.getLong("syntheticId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("columnLabel")) {
			model.setColumnLabel(jsonObject.getString("columnLabel"));
		}
		if (jsonObject.containsKey("columnTitle")) {
			model.setColumnTitle(jsonObject.getString("columnTitle"));
		}
		if (jsonObject.containsKey("columnSize")) {
			model.setColumnSize(jsonObject.getInteger("columnSize"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("mappingToSourceIdColumn")) {
			model.setMappingToSourceIdColumn(jsonObject.getString("mappingToSourceIdColumn"));
		}
		if (jsonObject.containsKey("mappingToTargetColumn")) {
			model.setMappingToTargetColumn(jsonObject.getString("mappingToTargetColumn"));
		}
		if (jsonObject.containsKey("mappingToTargetAlias")) {
			model.setMappingToTargetAlias(jsonObject.getString("mappingToTargetAlias"));
		}
		if (jsonObject.containsKey("datasetId")) {
			model.setDatasetId(jsonObject.getString("datasetId"));
		}
		if (jsonObject.containsKey("sqlDefId")) {
			model.setSqlDefId(jsonObject.getLong("sqlDefId"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TreeTableSyntheticRule model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("syntheticId", model.getSyntheticId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getColumnLabel() != null) {
			jsonObject.put("columnLabel", model.getColumnLabel());
		}
		if (model.getColumnTitle() != null) {
			jsonObject.put("columnTitle", model.getColumnTitle());
		}
		jsonObject.put("columnSize", model.getColumnSize());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getMappingToSourceIdColumn() != null) {
			jsonObject.put("mappingToSourceIdColumn", model.getMappingToSourceIdColumn());
		}
		if (model.getMappingToTargetColumn() != null) {
			jsonObject.put("mappingToTargetColumn", model.getMappingToTargetColumn());
		}
		if (model.getMappingToTargetAlias() != null) {
			jsonObject.put("mappingToTargetAlias", model.getMappingToTargetAlias());
		}
		jsonObject.put("datasetId", model.getDatasetId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		jsonObject.put("locked", model.getLocked());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TreeTableSyntheticRule model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("syntheticId", model.getSyntheticId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getColumnLabel() != null) {
			jsonObject.put("columnLabel", model.getColumnLabel());
		}
		if (model.getColumnTitle() != null) {
			jsonObject.put("columnTitle", model.getColumnTitle());
		}
		jsonObject.put("columnSize", model.getColumnSize());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getMappingToSourceIdColumn() != null) {
			jsonObject.put("mappingToSourceIdColumn", model.getMappingToSourceIdColumn());
		}
		if (model.getMappingToTargetColumn() != null) {
			jsonObject.put("mappingToTargetColumn", model.getMappingToTargetColumn());
		}
		if (model.getMappingToTargetAlias() != null) {
			jsonObject.put("mappingToTargetAlias", model.getMappingToTargetAlias());
		}
		jsonObject.put("datasetId", model.getDatasetId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		jsonObject.put("locked", model.getLocked());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TreeTableSyntheticRule> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TreeTableSyntheticRule model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TreeTableSyntheticRule> arrayToList(JSONArray array) {
		java.util.List<TreeTableSyntheticRule> list = new java.util.ArrayList<TreeTableSyntheticRule>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TreeTableSyntheticRule model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TreeTableSyntheticRuleJsonFactory() {

	}

}
