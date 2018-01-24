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
public class ParameterLogJsonFactory {

	public static ParameterLog jsonToObject(JSONObject jsonObject) {
		ParameterLog model = new ParameterLog();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("businessKey")) {
			model.setBusinessKey(jsonObject.getString("businessKey"));
		}
		if (jsonObject.containsKey("jobNo")) {
			model.setJobNo(jsonObject.getString("jobNo"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("startTime")) {
			model.setStartTime(jsonObject.getDate("startTime"));
		}
		if (jsonObject.containsKey("endTime")) {
			model.setEndTime(jsonObject.getDate("endTime"));
		}
		 
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ParameterLog model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getBusinessKey() != null) {
			jsonObject.put("businessKey", model.getBusinessKey());
		}
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
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
		 
		jsonObject.put("status", model.getStatus());
		
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

	public static ObjectNode toObjectNode(ParameterLog model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getBusinessKey() != null) {
			jsonObject.put("businessKey", model.getBusinessKey());
		}
		if (model.getJobNo() != null) {
			jsonObject.put("jobNo", model.getJobNo());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
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
		 
		jsonObject.put("status", model.getStatus());
		
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

	public static JSONArray listToArray(java.util.List<ParameterLog> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ParameterLog model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ParameterLog> arrayToList(JSONArray array) {
		java.util.List<ParameterLog> list = new java.util.ArrayList<ParameterLog>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ParameterLog model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ParameterLogJsonFactory() {

	}

}
