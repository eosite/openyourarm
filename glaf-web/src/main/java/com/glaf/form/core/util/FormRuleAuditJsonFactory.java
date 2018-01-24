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
public class FormRuleAuditJsonFactory {

	public static FormRuleAudit jsonToObject(JSONObject jsonObject) {
		FormRuleAudit model = new FormRuleAudit();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}

		if (jsonObject.containsKey("componentId")) {
			model.setComponentId(jsonObject.getLong("componentId"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getString("appId"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("deploymentId")) {
			model.setDeploymentId(jsonObject.getString("deploymentId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}
		if (jsonObject.containsKey("snapshot")) {
			model.setSnapshot(jsonObject.getString("snapshot"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormRuleAudit model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());

		jsonObject.put("componentId", model.getComponentId());
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		}
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		if (model.getSnapshot() != null) {
			jsonObject.put("snapshot", model.getSnapshot());
		}
		jsonObject.put("version", model.getVersion());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormRuleAudit model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());

		jsonObject.put("componentId", model.getComponentId());
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		}
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		}
		if (model.getSnapshot() != null) {
			jsonObject.put("snapshot", model.getSnapshot());
		}
		jsonObject.put("version", model.getVersion());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date",
					DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime",
					DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormRuleAudit> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormRuleAudit model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormRuleAudit> arrayToList(JSONArray array) {
		java.util.List<FormRuleAudit> list = new java.util.ArrayList<FormRuleAudit>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormRuleAudit model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormRuleAuditJsonFactory() {

	}

}
