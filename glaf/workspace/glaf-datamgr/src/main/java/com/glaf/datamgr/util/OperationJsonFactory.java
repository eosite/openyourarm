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
public class OperationJsonFactory {

	public static Operation jsonToObject(JSONObject jsonObject) {
		Operation model = new Operation();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("method")) {
			model.setMethod(jsonObject.getString("method"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("tablename")) {
			model.setTablename(jsonObject.getString("tablename"));
		}
		if (jsonObject.containsKey("idField")) {
			model.setIdField(jsonObject.getString("idField"));
		}
		if (jsonObject.containsKey("idColumn")) {
			model.setIdColumn(jsonObject.getString("idColumn"));
		}
		if (jsonObject.containsKey("idJavaType")) {
			model.setIdJavaType(jsonObject.getString("idJavaType"));
		}
		if (jsonObject.containsKey("sqlDefId")) {
			model.setSqlDefId(jsonObject.getLong("sqlDefId"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}

		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(Operation model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getMethod() != null) {
			jsonObject.put("method", model.getMethod());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getTablename() != null) {
			jsonObject.put("tablename", model.getTablename());
		}
		if (model.getIdField() != null) {
			jsonObject.put("idField", model.getIdField());
		}
		if (model.getIdColumn() != null) {
			jsonObject.put("idColumn", model.getIdColumn());
		}
		if (model.getIdJavaType() != null) {
			jsonObject.put("idJavaType", model.getIdJavaType());
		}
		jsonObject.put("sqlDefId", model.getSqlDefId());
		jsonObject.put("sort", model.getSort());

		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}

		return jsonObject;
	}

	public static ObjectNode toObjectNode(Operation model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getMethod() != null) {
			jsonObject.put("method", model.getMethod());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getTablename() != null) {
			jsonObject.put("tablename", model.getTablename());
		}
		if (model.getIdField() != null) {
			jsonObject.put("idField", model.getIdField());
		}
		if (model.getIdColumn() != null) {
			jsonObject.put("idColumn", model.getIdColumn());
		}
		if (model.getIdJavaType() != null) {
			jsonObject.put("idJavaType", model.getIdJavaType());
		}
		jsonObject.put("sqlDefId", model.getSqlDefId());
		jsonObject.put("sort", model.getSort());

		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date",
					DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime",
					DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<Operation> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (Operation model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<Operation> arrayToList(JSONArray array) {
		java.util.List<Operation> list = new java.util.ArrayList<Operation>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			Operation model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private OperationJsonFactory() {

	}

}
