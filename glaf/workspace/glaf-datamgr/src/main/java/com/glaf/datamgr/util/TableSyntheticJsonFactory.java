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
public class TableSyntheticJsonFactory {

	public static java.util.List<TableSynthetic> arrayToList(JSONArray array) {
		java.util.List<TableSynthetic> list = new java.util.ArrayList<TableSynthetic>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TableSynthetic model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static TableSynthetic jsonToObject(JSONObject jsonObject) {
		TableSynthetic model = new TableSynthetic();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
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
		if (jsonObject.containsKey("sourceDatabaseIds")) {
			model.setSourceDatabaseIds(jsonObject.getString("sourceDatabaseIds"));
		}
		if (jsonObject.containsKey("sourceTableName")) {
			model.setSourceTableName(jsonObject.getString("sourceTableName"));
		}
		if (jsonObject.containsKey("primaryKey")) {
			model.setPrimaryKey(jsonObject.getString("primaryKey"));
		}
		if (jsonObject.containsKey("syncColumns")) {
			model.setSyncColumns(jsonObject.getString("syncColumns"));
		}
		if (jsonObject.containsKey("targetTableName")) {
			model.setTargetTableName(jsonObject.getString("targetTableName"));
		}
		if (jsonObject.containsKey("targetDatabaseId")) {
			model.setTargetDatabaseId(jsonObject.getLong("targetDatabaseId"));
		}
		if (jsonObject.containsKey("scheduleFlag")) {
			model.setScheduleFlag(jsonObject.getString("scheduleFlag"));
		}
		if (jsonObject.containsKey("createTableFlag")) {
			model.setCreateTableFlag(jsonObject.getString("createTableFlag"));
		}
		if (jsonObject.containsKey("deleteFetch")) {
			model.setDeleteFetch(jsonObject.getString("deleteFetch"));
		}
		if (jsonObject.containsKey("insertOnly")) {
			model.setInsertOnly(jsonObject.getString("insertOnly"));
		}
		if (jsonObject.containsKey("jobNo")) {
			model.setJobNo(jsonObject.getString("jobNo"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<TableSynthetic> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TableSynthetic model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(TableSynthetic model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
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
		jsonObject.put("sourceDatabaseIds", model.getSourceDatabaseIds());
		if (model.getSourceTableName() != null) {
			jsonObject.put("sourceTableName", model.getSourceTableName());
		}

		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}

		jsonObject.put("targetDatabaseId", model.getTargetDatabaseId());

		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getInsertOnly() != null) {
			jsonObject.put("insertOnly", model.getInsertOnly());
		}
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getSyncColumns() != null) {
			jsonObject.put("syncColumns", model.getSyncColumns());
		}
		jsonObject.put("sortNo", model.getSortNo());
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

	public static ObjectNode toObjectNode(TableSynthetic model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
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
		jsonObject.put("sourceDatabaseIds", model.getSourceDatabaseIds());
		if (model.getSourceTableName() != null) {
			jsonObject.put("sourceTableName", model.getSourceTableName());
		}

		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}

		jsonObject.put("targetDatabaseId", model.getTargetDatabaseId());

		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getInsertOnly() != null) {
			jsonObject.put("insertOnly", model.getInsertOnly());
		}
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		if (model.getPrimaryKey() != null) {
			jsonObject.put("primaryKey", model.getPrimaryKey());
		}
		if (model.getSyncColumns() != null) {
			jsonObject.put("syncColumns", model.getSyncColumns());
		}
		jsonObject.put("sortNo", model.getSortNo());
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

	private TableSyntheticJsonFactory() {

	}

}
