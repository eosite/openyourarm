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
public class SqlParameterJsonFactory {

	public static SqlParameter jsonToObject(JSONObject jsonObject) {
		SqlParameter model = new SqlParameter();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("datasetId")) {
			model.setDatasetId(jsonObject.getString("datasetId"));
		}
		if (jsonObject.containsKey("sqlDefId")) {
			model.setSqlDefId(jsonObject.getLong("sqlDefId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("mapping")) {
			model.setMapping(jsonObject.getString("mapping"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("defaultValue")) {
			model.setDefaultValue(jsonObject.getString("defaultValue"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("collection")) {
			model.setCollection(jsonObject.getString("collection"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SqlParameter model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDefaultValue() != null) {
			jsonObject.put("defaultValue", model.getDefaultValue());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCollection() != null) {
			jsonObject.put("collection", model.getCollection());
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

	public static ObjectNode toObjectNode(SqlParameter model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("sqlDefId", model.getSqlDefId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getMapping() != null) {
			jsonObject.put("mapping", model.getMapping());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getDefaultValue() != null) {
			jsonObject.put("defaultValue", model.getDefaultValue());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCollection() != null) {
			jsonObject.put("collection", model.getCollection());
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

	public static JSONArray listToArray(java.util.List<SqlParameter> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SqlParameter model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SqlParameter> arrayToList(JSONArray array) {
		java.util.List<SqlParameter> list = new java.util.ArrayList<SqlParameter>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SqlParameter model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SqlParameterJsonFactory() {

	}

}
