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
public class FormPageHistoryJsonFactory {

	public static FormPageHistory jsonToObject(JSONObject jsonObject) {
		FormPageHistory model = new FormPageHistory();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("deploymentId")) {
			model.setDeploymentId(jsonObject.getString("deploymentId"));
		}
		if (jsonObject.containsKey("formHtml")) {
			model.setFormHtml(jsonObject.getString("formHtml"));
		}
		if (jsonObject.containsKey("formConfig")) {
			model.setFormConfig(jsonObject.getString("formConfig"));
		}
		if (jsonObject.containsKey("outputHtml")) {
			model.setOutputHtml(jsonObject.getString("outputHtml"));
		}
		if (jsonObject.containsKey("formType")) {
			model.setFormType(jsonObject.getString("formType"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormPageHistory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getFormHtml() != null) {
			jsonObject.put("formHtml", model.getFormHtml());
		}
		if (model.getFormConfig() != null) {
			jsonObject.put("formConfig", model.getFormConfig());
		}
		if (model.getOutputHtml() != null) {
			jsonObject.put("outputHtml", model.getOutputHtml());
		}
		if (model.getFormType() != null) {
			jsonObject.put("formType", model.getFormType());
		}
		jsonObject.put("version", model.getVersion());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormPageHistory model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getDeploymentId() != null) {
			jsonObject.put("deploymentId", model.getDeploymentId());
		}
		if (model.getFormHtml() != null) {
			jsonObject.put("formHtml", model.getFormHtml());
		}
		if (model.getFormConfig() != null) {
			jsonObject.put("formConfig", model.getFormConfig());
		}
		if (model.getOutputHtml() != null) {
			jsonObject.put("outputHtml", model.getOutputHtml());
		}
		if (model.getFormType() != null) {
			jsonObject.put("formType", model.getFormType());
		}
		jsonObject.put("version", model.getVersion());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormPageHistory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormPageHistory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormPageHistory> arrayToList(JSONArray array) {
		java.util.List<FormPageHistory> list = new java.util.ArrayList<FormPageHistory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormPageHistory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormPageHistoryJsonFactory() {

	}

}
