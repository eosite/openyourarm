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
public class SqlTransportLogJsonFactory {

	public static SqlTransportLog jsonToObject(JSONObject jsonObject) {
		SqlTransportLog model = new SqlTransportLog();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("projectId")) {
			model.setProjectId(jsonObject.getLong("projectId"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("sqlDefId")) {
			model.setSqlDefId(jsonObject.getLong("sqlDefId"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("runYear")) {
			model.setRunYear(jsonObject.getInteger("runYear"));
		}
		if (jsonObject.containsKey("runMonth")) {
			model.setRunMonth(jsonObject.getInteger("runMonth"));
		}
		if (jsonObject.containsKey("runWeek")) {
			model.setRunWeek(jsonObject.getInteger("runWeek"));
		}
		if (jsonObject.containsKey("runQuarter")) {
			model.setRunQuarter(jsonObject.getInteger("runQuarter"));
		}
		if (jsonObject.containsKey("runDay")) {
			model.setRunDay(jsonObject.getInteger("runDay"));
		}
		if (jsonObject.containsKey("jobNo")) {
			model.setJobNo(jsonObject.getString("jobNo"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SqlTransportLog model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("projectId", model.getProjectId());
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		jsonObject.put("status", model.getStatus());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("runYear", model.getRunYear());
		jsonObject.put("runMonth", model.getRunMonth());
		jsonObject.put("runWeek", model.getRunWeek());
		jsonObject.put("runQuarter", model.getRunQuarter());
		jsonObject.put("runDay", model.getRunDay());
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
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

	public static ObjectNode toObjectNode(SqlTransportLog model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("projectId", model.getProjectId());
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		jsonObject.put("status", model.getStatus());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("runYear", model.getRunYear());
		jsonObject.put("runMonth", model.getRunMonth());
		jsonObject.put("runWeek", model.getRunWeek());
		jsonObject.put("runQuarter", model.getRunQuarter());
		jsonObject.put("runDay", model.getRunDay());
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
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

	public static JSONArray listToArray(java.util.List<SqlTransportLog> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SqlTransportLog model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SqlTransportLog> arrayToList(JSONArray array) {
		java.util.List<SqlTransportLog> list = new java.util.ArrayList<SqlTransportLog>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SqlTransportLog model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SqlTransportLogJsonFactory() {

	}

}
