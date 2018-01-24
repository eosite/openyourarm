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
public class FormTemplateJsonFactory {

	public static FormTemplate jsonToObject(JSONObject jsonObject) {
		FormTemplate model = new FormTemplate();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getInteger("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("componentId")) {
			model.setComponentId(jsonObject.getInteger("componentId"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("template")) {
			model.setTemplate(jsonObject.getString("template"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormTemplate model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		jsonObject.put("componentId", model.getComponentId());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getTemplate() != null) {
			jsonObject.put("template", model.getTemplate());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormTemplate model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		jsonObject.put("componentId", model.getComponentId());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getTemplate() != null) {
			jsonObject.put("template", model.getTemplate());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormTemplate> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormTemplate model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormTemplate> arrayToList(JSONArray array) {
		java.util.List<FormTemplate> list = new java.util.ArrayList<FormTemplate>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormTemplate model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormTemplateJsonFactory() {

	}

}
