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
public class TaskTableJsonFactory {

	public static TaskTable jsonToObject(JSONObject jsonObject) {
		TaskTable model = new TaskTable();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("idColumn")) {
			model.setIdColumn(jsonObject.getString("idColumn"));
		}
		if (jsonObject.containsKey("nameColumn")) {
			model.setNameColumn(jsonObject.getString("nameColumn"));
		}
		if (jsonObject.containsKey("nameExpression")) {
			model.setNameExpression(jsonObject.getString("nameExpression"));
		}
		if (jsonObject.containsKey("valueColumn")) {
			model.setValueColumn(jsonObject.getString("valueColumn"));
		}
		if (jsonObject.containsKey("valueExpression")) {
			model.setValueExpression(jsonObject.getString("valueExpression"));
		}
		if (jsonObject.containsKey("typeColumn")) {
			model.setTypeColumn(jsonObject.getString("typeColumn"));
		}
		if (jsonObject.containsKey("startDateColumn")) {
			model.setStartDateColumn(jsonObject.getString("startDateColumn"));
		}
		if (jsonObject.containsKey("endDateColumn")) {
			model.setEndDateColumn(jsonObject.getString("endDateColumn"));
		}
		if (jsonObject.containsKey("databaseIds")) {
			model.setDatabaseIds(jsonObject.getString("databaseIds"));
		}
		if (jsonObject.containsKey("syncColumns")) {
			model.setSyncColumns(jsonObject.getString("syncColumns"));
		}
		if (jsonObject.containsKey("startTime")) {
			model.setStartTime(jsonObject.getDate("startTime"));
		}
		if (jsonObject.containsKey("endTime")) {
			model.setEndTime(jsonObject.getDate("endTime"));
		}
		if (jsonObject.containsKey("frequency")) {
			model.setFrequency(jsonObject.getInteger("frequency"));
		}
		if (jsonObject.containsKey("executeDay")) {
			model.setExecuteDay(jsonObject.getInteger("executeDay"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
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

	public static JSONObject toJsonObject(TaskTable model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getIdColumn() != null) {
			jsonObject.put("idColumn", model.getIdColumn());
		}
		if (model.getNameColumn() != null) {
			jsonObject.put("nameColumn", model.getNameColumn());
		}
		if (model.getNameExpression() != null) {
			jsonObject.put("nameExpression", model.getNameExpression());
		}
		if (model.getValueColumn() != null) {
			jsonObject.put("valueColumn", model.getValueColumn());
		}
		if (model.getValueExpression() != null) {
			jsonObject.put("valueExpression", model.getValueExpression());
		}
		if (model.getTypeColumn() != null) {
			jsonObject.put("typeColumn", model.getTypeColumn());
		}
		if (model.getStartDateColumn() != null) {
			jsonObject.put("startDateColumn", model.getStartDateColumn());
		}
		if (model.getEndDateColumn() != null) {
			jsonObject.put("endDateColumn", model.getEndDateColumn());
		}
		if (model.getDatabaseIds() != null) {
			jsonObject.put("databaseIds", model.getDatabaseIds());
		}
		if (model.getSyncColumns() != null) {
			jsonObject.put("syncColumns", model.getSyncColumns());
		}
		if (model.getStartTime() != null) {
			jsonObject.put("startTime", DateUtils.getDate(model.getStartTime()));
			jsonObject.put("startTime_date", DateUtils.getDate(model.getStartTime()));
			jsonObject.put("startTime_datetime", DateUtils.getDateTime(model.getStartTime()));
		}
		if (model.getEndTime() != null) {
			jsonObject.put("endTime", DateUtils.getDate(model.getEndTime()));
			jsonObject.put("endTime_date", DateUtils.getDate(model.getEndTime()));
			jsonObject.put("endTime_datetime", DateUtils.getDateTime(model.getEndTime()));
		}
		jsonObject.put("frequency", model.getFrequency());
		jsonObject.put("executeDay", model.getExecuteDay());
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
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

	public static ObjectNode toObjectNode(TaskTable model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getIdColumn() != null) {
			jsonObject.put("idColumn", model.getIdColumn());
		}
		if (model.getNameColumn() != null) {
			jsonObject.put("nameColumn", model.getNameColumn());
		}
		if (model.getNameExpression() != null) {
			jsonObject.put("nameExpression", model.getNameExpression());
		}
		if (model.getValueColumn() != null) {
			jsonObject.put("valueColumn", model.getValueColumn());
		}
		if (model.getValueExpression() != null) {
			jsonObject.put("valueExpression", model.getValueExpression());
		}
		if (model.getTypeColumn() != null) {
			jsonObject.put("typeColumn", model.getTypeColumn());
		}
		if (model.getStartDateColumn() != null) {
			jsonObject.put("startDateColumn", model.getStartDateColumn());
		}
		if (model.getEndDateColumn() != null) {
			jsonObject.put("endDateColumn", model.getEndDateColumn());
		}
		if (model.getDatabaseIds() != null) {
			jsonObject.put("databaseIds", model.getDatabaseIds());
		}
		if (model.getSyncColumns() != null) {
			jsonObject.put("syncColumns", model.getSyncColumns());
		}
		if (model.getStartTime() != null) {
			jsonObject.put("startTime", DateUtils.getDate(model.getStartTime()));
			jsonObject.put("startTime_date", DateUtils.getDate(model.getStartTime()));
			jsonObject.put("startTime_datetime", DateUtils.getDateTime(model.getStartTime()));
		}
		if (model.getEndTime() != null) {
			jsonObject.put("endTime", DateUtils.getDate(model.getEndTime()));
			jsonObject.put("endTime_date", DateUtils.getDate(model.getEndTime()));
			jsonObject.put("endTime_datetime", DateUtils.getDateTime(model.getEndTime()));
		}
		jsonObject.put("frequency", model.getFrequency());
		jsonObject.put("executeDay", model.getExecuteDay());
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
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

	public static JSONArray listToArray(java.util.List<TaskTable> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TaskTable model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TaskTable> arrayToList(JSONArray array) {
		java.util.List<TaskTable> list = new java.util.ArrayList<TaskTable>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TaskTable model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TaskTableJsonFactory() {

	}

}
