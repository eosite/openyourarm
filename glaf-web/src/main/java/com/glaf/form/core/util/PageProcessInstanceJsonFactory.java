package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class PageProcessInstanceJsonFactory {

	public static PageProcessInstance jsonToObject(JSONObject jsonObject) {
		PageProcessInstance model = new PageProcessInstance();
		if (jsonObject.containsKey("processInstanceId")) {
			model.setProcessInstanceId(jsonObject.getString("processInstanceId"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("businessTable")) {
			model.setBusinessTable(jsonObject.getString("businessTable"));
		}
		if (jsonObject.containsKey("businessKey")) {
			model.setBusinessKey(jsonObject.getString("businessKey"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("wfStatus")) {
			model.setWfStatus(jsonObject.getInteger("wfStatus"));
		}
		if (jsonObject.containsKey("sortNo")) {
			model.setSortNo(jsonObject.getInteger("sortNo"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}
		if (jsonObject.containsKey("startTime")) {
			model.setStartTime(jsonObject.getDate("startTime"));
		}
		if (jsonObject.containsKey("endTime")) {
			model.setEndTime(jsonObject.getDate("endTime"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(PageProcessInstance model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("processInstanceId", model.getProcessInstanceId());
		jsonObject.put("_processInstanceId_", model.getProcessInstanceId());
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getBusinessTable() != null) {
			jsonObject.put("businessTable", model.getBusinessTable());
		}
		if (model.getBusinessKey() != null) {
			jsonObject.put("businessKey", model.getBusinessKey());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("wfStatus", model.getWfStatus());
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("version", model.getVersion());
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
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(PageProcessInstance model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("processInstanceId", model.getProcessInstanceId());
		jsonObject.put("_processInstanceId_", model.getProcessInstanceId());
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		}
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getBusinessTable() != null) {
			jsonObject.put("businessTable", model.getBusinessTable());
		}
		if (model.getBusinessKey() != null) {
			jsonObject.put("businessKey", model.getBusinessKey());
		}
		jsonObject.put("status", model.getStatus());
		jsonObject.put("wfStatus", model.getWfStatus());
		jsonObject.put("sortNo", model.getSortNo());
		jsonObject.put("locked", model.getLocked());
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("version", model.getVersion());
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
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<PageProcessInstance> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (PageProcessInstance model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<PageProcessInstance> arrayToList(JSONArray array) {
		java.util.List<PageProcessInstance> list = new java.util.ArrayList<PageProcessInstance>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			PageProcessInstance model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private PageProcessInstanceJsonFactory() {

	}

}
