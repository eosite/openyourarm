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
public class DataPermissionJsonFactory {

	public static DataPermission jsonToObject(JSONObject jsonObject) {
		DataPermission model = new DataPermission();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("businessKey")) {
			model.setBusinessKey(jsonObject.getString("businessKey"));
		}
		if (jsonObject.containsKey("businessType")) {
			model.setBusinessType(jsonObject.getString("businessType"));
		}
		if (jsonObject.containsKey("accessType")) {
			model.setAccessType(jsonObject.getString("accessType"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getString("roleId"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DataPermission model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		if (model.getBusinessKey() != null) {
			jsonObject.put("businessKey", model.getBusinessKey());
		}
		if (model.getBusinessType() != null) {
			jsonObject.put("businessType", model.getBusinessType());
		}
		if (model.getAccessType() != null) {
			jsonObject.put("accessType", model.getAccessType());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getRoleId() != null) {
			jsonObject.put("roleId", model.getRoleId());
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

	public static ObjectNode toObjectNode(DataPermission model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("databaseId", model.getDatabaseId());
		if (model.getBusinessKey() != null) {
			jsonObject.put("businessKey", model.getBusinessKey());
		}
		if (model.getBusinessType() != null) {
			jsonObject.put("businessType", model.getBusinessType());
		}
		if (model.getAccessType() != null) {
			jsonObject.put("accessType", model.getAccessType());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getRoleId() != null) {
			jsonObject.put("roleId", model.getRoleId());
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

	public static JSONArray listToArray(java.util.List<DataPermission> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DataPermission model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DataPermission> arrayToList(JSONArray array) {
		java.util.List<DataPermission> list = new java.util.ArrayList<DataPermission>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DataPermission model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DataPermissionJsonFactory() {

	}

}
