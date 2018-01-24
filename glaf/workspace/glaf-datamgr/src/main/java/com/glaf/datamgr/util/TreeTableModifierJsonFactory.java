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
public class TreeTableModifierJsonFactory {

	public static TreeTableModifier jsonToObject(JSONObject jsonObject) {
		TreeTableModifier model = new TreeTableModifier();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("databaseIds")) {
			model.setDatabaseIds(jsonObject.getString("databaseIds"));
		}
		if (jsonObject.containsKey("primaryKey")) {
			model.setPrimaryKey(jsonObject.getString("primaryKey"));
		}
		if (jsonObject.containsKey("idColumn")) {
			model.setIdColumn(jsonObject.getString("idColumn"));
		}
		if (jsonObject.containsKey("parentIdColumn")) {
			model.setParentIdColumn(jsonObject.getString("parentIdColumn"));
		}
		if (jsonObject.containsKey("treeIdColumn")) {
			model.setTreeIdColumn(jsonObject.getString("treeIdColumn"));
		}
		if (jsonObject.containsKey("levelColumn")) {
			model.setLevelColumn(jsonObject.getString("levelColumn"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TreeTableModifier model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDatabaseIds() != null) {
			jsonObject.put("databaseIds", model.getDatabaseIds());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getIdColumn() != null) {
			jsonObject.put("idColumn", model.getIdColumn());
		}
		if (model.getParentIdColumn() != null) {
			jsonObject.put("parentIdColumn", model.getParentIdColumn());
		}
		if (model.getTreeIdColumn() != null) {
			jsonObject.put("treeIdColumn", model.getTreeIdColumn());
		}
		if (model.getLevelColumn() != null) {
			jsonObject.put("levelColumn", model.getLevelColumn());
		}
		jsonObject.put("locked", model.getLocked());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TreeTableModifier model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDatabaseIds() != null) {
			jsonObject.put("databaseIds", model.getDatabaseIds());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getIdColumn() != null) {
			jsonObject.put("idColumn", model.getIdColumn());
		}
		if (model.getParentIdColumn() != null) {
			jsonObject.put("parentIdColumn", model.getParentIdColumn());
		}
		if (model.getTreeIdColumn() != null) {
			jsonObject.put("treeIdColumn", model.getTreeIdColumn());
		}
		if (model.getLevelColumn() != null) {
			jsonObject.put("levelColumn", model.getLevelColumn());
		}
		jsonObject.put("locked", model.getLocked());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TreeTableModifier> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TreeTableModifier model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TreeTableModifier> arrayToList(JSONArray array) {
		java.util.List<TreeTableModifier> list = new java.util.ArrayList<TreeTableModifier>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TreeTableModifier model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TreeTableModifierJsonFactory() {

	}

}
