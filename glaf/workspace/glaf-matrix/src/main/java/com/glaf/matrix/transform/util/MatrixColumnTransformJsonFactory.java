package com.glaf.matrix.transform.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.matrix.transform.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class MatrixColumnTransformJsonFactory {

	public static MatrixColumnTransform jsonToObject(JSONObject jsonObject) {
		MatrixColumnTransform model = new MatrixColumnTransform();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("transformId")) {
			model.setTransformId(jsonObject.getString("transformId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("targetColumnName")) {
			model.setTargetColumnName(jsonObject.getString("targetColumnName"));
		}
		if (jsonObject.containsKey("targetColumnPrecision")) {
			model.setTargetColumnPrecision(jsonObject.getInteger("targetColumnPrecision"));
		}
		if (jsonObject.containsKey("targetType")) {
			model.setTargetType(jsonObject.getString("targetType"));
		}
		if (jsonObject.containsKey("sqlCriteria")) {
			model.setSqlCriteria(jsonObject.getString("sqlCriteria"));
		}
		if (jsonObject.containsKey("condition")) {
			model.setCondition(jsonObject.getString("condition"));
		}
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("transformIfTargetColumnNotEmpty")) {
			model.setTransformIfTargetColumnNotEmpty(jsonObject.getString("transformIfTargetColumnNotEmpty"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("syncStatus")) {
			model.setSyncStatus(jsonObject.getInteger("syncStatus"));
		}
		if (jsonObject.containsKey("syncTime")) {
			model.setSyncTime(jsonObject.getDate("syncTime"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		return model;
	}

	public static JSONObject toJsonObject(MatrixColumnTransform model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("transformId", model.getTransformId());

		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getTargetColumnName() != null) {
			jsonObject.put("targetColumnName", model.getTargetColumnName());
		}
		jsonObject.put("targetColumnPrecision", model.getTargetColumnPrecision());
		if (model.getTargetType() != null) {
			jsonObject.put("targetType", model.getTargetType());
		}
		if (model.getSqlCriteria() != null) {
			jsonObject.put("sqlCriteria", model.getSqlCriteria());
		}
		if (model.getCondition() != null) {
			jsonObject.put("condition", model.getCondition());
		}
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		}
		if (model.getTransformIfTargetColumnNotEmpty() != null) {
			jsonObject.put("transformIfTargetColumnNotEmpty", model.getTransformIfTargetColumnNotEmpty());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("syncStatus", model.getSyncStatus());
		if (model.getSyncTime() != null) {
			jsonObject.put("syncTime", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_date", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_datetime", DateUtils.getDateTime(model.getSyncTime()));
		}
		jsonObject.put("sort", model.getSort());
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

	public static ObjectNode toObjectNode(MatrixColumnTransform model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("transformId", model.getTransformId());

		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getTargetColumnName() != null) {
			jsonObject.put("targetColumnName", model.getTargetColumnName());
		}
		jsonObject.put("targetColumnPrecision", model.getTargetColumnPrecision());
		if (model.getTargetType() != null) {
			jsonObject.put("targetType", model.getTargetType());
		}
		if (model.getSqlCriteria() != null) {
			jsonObject.put("sqlCriteria", model.getSqlCriteria());
		}
		if (model.getCondition() != null) {
			jsonObject.put("condition", model.getCondition());
		}
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		}
		if (model.getTransformIfTargetColumnNotEmpty() != null) {
			jsonObject.put("transformIfTargetColumnNotEmpty", model.getTransformIfTargetColumnNotEmpty());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("syncStatus", model.getSyncStatus());
		if (model.getSyncTime() != null) {
			jsonObject.put("syncTime", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_date", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_datetime", DateUtils.getDateTime(model.getSyncTime()));
		}
		jsonObject.put("sort", model.getSort());
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

	public static JSONArray listToArray(java.util.List<MatrixColumnTransform> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MatrixColumnTransform model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MatrixColumnTransform> arrayToList(JSONArray array) {
		java.util.List<MatrixColumnTransform> list = new java.util.ArrayList<MatrixColumnTransform>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MatrixColumnTransform model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private MatrixColumnTransformJsonFactory() {

	}

}
