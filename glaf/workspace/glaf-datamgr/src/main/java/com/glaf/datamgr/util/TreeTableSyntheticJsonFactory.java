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
public class TreeTableSyntheticJsonFactory {

	public static TreeTableSynthetic jsonToObject(JSONObject jsonObject) {
		TreeTableSynthetic model = new TreeTableSynthetic();
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
		if (jsonObject.containsKey("sourceTableName")) {
			model.setSourceTableName(jsonObject.getString("sourceTableName"));
		}
		if (jsonObject.containsKey("sourceIdColumn")) {
			model.setSourceIdColumn(jsonObject.getString("sourceIdColumn"));
		}
		if (jsonObject.containsKey("sourceIndexIdColumn")) {
			model.setSourceIndexIdColumn(jsonObject.getString("sourceIndexIdColumn"));
		}
		if (jsonObject.containsKey("sourceParentIdColumn")) {
			model.setSourceParentIdColumn(jsonObject.getString("sourceParentIdColumn"));
		}
		if (jsonObject.containsKey("sourceTreeIdColumn")) {
			model.setSourceTreeIdColumn(jsonObject.getString("sourceTreeIdColumn"));
		}
		if (jsonObject.containsKey("sourceTextColumn")) {
			model.setSourceTextColumn(jsonObject.getString("sourceTextColumn"));
		}
		if (jsonObject.containsKey("sourceWbsIndexColumn")) {
			model.setSourceWbsIndexColumn(jsonObject.getString("sourceWbsIndexColumn"));
		}
		if (jsonObject.containsKey("sourceDatabaseIds")) {
			model.setSourceDatabaseIds(jsonObject.getString("sourceDatabaseIds"));
		}
		if (jsonObject.containsKey("sourceTableExecutionIds")) {
			model.setSourceTableExecutionIds(jsonObject.getString("sourceTableExecutionIds"));
		}
		if (jsonObject.containsKey("targetTableName")) {
			model.setTargetTableName(jsonObject.getString("targetTableName"));
		}
		if (jsonObject.containsKey("targetDatabaseId")) {
			model.setTargetDatabaseId(jsonObject.getLong("targetDatabaseId"));
		}
		if (jsonObject.containsKey("targetTableExecutionIds")) {
			model.setTargetTableExecutionIds(jsonObject.getString("targetTableExecutionIds"));
		}
		if (jsonObject.containsKey("createTableFlag")) {
			model.setCreateTableFlag(jsonObject.getString("createTableFlag"));
		}
		if (jsonObject.containsKey("scheduleFlag")) {
			model.setScheduleFlag(jsonObject.getString("scheduleFlag"));
		}
		if (jsonObject.containsKey("genNewPrimaryKey")) {
			model.setGenNewPrimaryKey(jsonObject.getString("genNewPrimaryKey"));
		}
		if (jsonObject.containsKey("genByMonth")) {
			model.setGenByMonth(jsonObject.getString("genByMonth"));
		}
		if (jsonObject.containsKey("syntheticFlag")) {
			model.setSyntheticFlag(jsonObject.getString("syntheticFlag"));
		}
		if (jsonObject.containsKey("deleteFetch")) {
			model.setDeleteFetch(jsonObject.getString("deleteFetch"));
		}
		if (jsonObject.containsKey("jobNo")) {
			model.setJobNo(jsonObject.getString("jobNo"));
		}
		if (jsonObject.containsKey("syncStatus")) {
			model.setSyncStatus(jsonObject.getInteger("syncStatus"));
		}
		if (jsonObject.containsKey("syncTime")) {
			model.setSyncTime(jsonObject.getDate("syncTime"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
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

	public static JSONObject toJsonObject(TreeTableSynthetic model) {
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
		if (model.getSourceTableName() != null) {
			jsonObject.put("sourceTableName", model.getSourceTableName());
		}
		if (model.getSourceIdColumn() != null) {
			jsonObject.put("sourceIdColumn", model.getSourceIdColumn());
		}
		if (model.getSourceIndexIdColumn() != null) {
			jsonObject.put("sourceIndexIdColumn", model.getSourceIndexIdColumn());
		}
		if (model.getSourceParentIdColumn() != null) {
			jsonObject.put("sourceParentIdColumn", model.getSourceParentIdColumn());
		}
		if (model.getSourceTreeIdColumn() != null) {
			jsonObject.put("sourceTreeIdColumn", model.getSourceTreeIdColumn());
		}
		if (model.getSourceTextColumn() != null) {
			jsonObject.put("sourceTextColumn", model.getSourceTextColumn());
		}
		if (model.getSourceWbsIndexColumn() != null) {
			jsonObject.put("sourceWbsIndexColumn", model.getSourceWbsIndexColumn());
		}
		if (model.getSourceDatabaseIds() != null) {
			jsonObject.put("sourceDatabaseIds", model.getSourceDatabaseIds());
		}
		if (model.getSourceTableExecutionIds() != null) {
			jsonObject.put("sourceTableExecutionIds", model.getSourceTableExecutionIds());
		}
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		jsonObject.put("targetDatabaseId", model.getTargetDatabaseId());

		if (model.getTargetTableExecutionIds() != null) {
			jsonObject.put("targetTableExecutionIds", model.getTargetTableExecutionIds());
		}

		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getGenNewPrimaryKey() != null) {
			jsonObject.put("genNewPrimaryKey", model.getGenNewPrimaryKey());
		}
		if (model.getGenByMonth() != null) {
			jsonObject.put("genByMonth", model.getGenByMonth());
		}
		if (model.getSyntheticFlag() != null) {
			jsonObject.put("syntheticFlag", model.getSyntheticFlag());
		}
		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		jsonObject.put("syncStatus", model.getSyncStatus());
		if (model.getSyncTime() != null) {
			jsonObject.put("syncTime", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_date", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_datetime", DateUtils.getDateTime(model.getSyncTime()));
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

	public static ObjectNode toObjectNode(TreeTableSynthetic model) {
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
		if (model.getSourceTableName() != null) {
			jsonObject.put("sourceTableName", model.getSourceTableName());
		}
		if (model.getSourceIdColumn() != null) {
			jsonObject.put("sourceIdColumn", model.getSourceIdColumn());
		}
		if (model.getSourceIndexIdColumn() != null) {
			jsonObject.put("sourceIndexIdColumn", model.getSourceIndexIdColumn());
		}
		if (model.getSourceParentIdColumn() != null) {
			jsonObject.put("sourceParentIdColumn", model.getSourceParentIdColumn());
		}
		if (model.getSourceTreeIdColumn() != null) {
			jsonObject.put("sourceTreeIdColumn", model.getSourceTreeIdColumn());
		}
		if (model.getSourceTextColumn() != null) {
			jsonObject.put("sourceTextColumn", model.getSourceTextColumn());
		}
		if (model.getSourceWbsIndexColumn() != null) {
			jsonObject.put("sourceWbsIndexColumn", model.getSourceWbsIndexColumn());
		}
		if (model.getSourceDatabaseIds() != null) {
			jsonObject.put("sourceDatabaseIds", model.getSourceDatabaseIds());
		}
		if (model.getSourceTableExecutionIds() != null) {
			jsonObject.put("sourceTableExecutionIds", model.getSourceTableExecutionIds());
		}
		if (model.getTargetTableExecutionIds() != null) {
			jsonObject.put("targetTableExecutionIds", model.getTargetTableExecutionIds());
		}
		if (model.getTargetTableName() != null) {
			jsonObject.put("targetTableName", model.getTargetTableName());
		}
		jsonObject.put("targetDatabaseId", model.getTargetDatabaseId());
		if (model.getCreateTableFlag() != null) {
			jsonObject.put("createTableFlag", model.getCreateTableFlag());
		}
		if (model.getGenNewPrimaryKey() != null) {
			jsonObject.put("genNewPrimaryKey", model.getGenNewPrimaryKey());
		}
		if (model.getGenByMonth() != null) {
			jsonObject.put("genByMonth", model.getGenByMonth());
		}
		if (model.getSyntheticFlag() != null) {
			jsonObject.put("syntheticFlag", model.getSyntheticFlag());
		}
		if (model.getScheduleFlag() != null) {
			jsonObject.put("scheduleFlag", model.getScheduleFlag());
		}
		if (model.getDeleteFetch() != null) {
			jsonObject.put("deleteFetch", model.getDeleteFetch());
		}
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		jsonObject.put("syncStatus", model.getSyncStatus());
		if (model.getSyncTime() != null) {
			jsonObject.put("syncTime", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_date", DateUtils.getDate(model.getSyncTime()));
			jsonObject.put("syncTime_datetime", DateUtils.getDateTime(model.getSyncTime()));
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

	public static JSONArray listToArray(java.util.List<TreeTableSynthetic> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TreeTableSynthetic model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TreeTableSynthetic> arrayToList(JSONArray array) {
		java.util.List<TreeTableSynthetic> list = new java.util.ArrayList<TreeTableSynthetic>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TreeTableSynthetic model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TreeTableSyntheticJsonFactory() {

	}

}
