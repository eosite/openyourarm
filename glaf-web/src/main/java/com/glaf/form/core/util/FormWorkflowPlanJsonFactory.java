package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class FormWorkflowPlanJsonFactory {

	public static FormWorkflowPlan jsonToObject(JSONObject jsonObject) {
		FormWorkflowPlan model = new FormWorkflowPlan();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("processDefId")) {
			model.setProcessDefId(jsonObject.getString("processDefId"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("pageName")) {
			model.setPageName(jsonObject.getString("pageName"));
		}
		if (jsonObject.containsKey("bytes")) {
			model.setBytes(jsonObject.getString("bytes"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDateTime")) {
			model.setCreateDateTime(jsonObject.getDate("createDateTime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("modifyDateTime")) {
			model.setModifyDateTime(jsonObject.getDate("modifyDateTime"));
		}
		if (jsonObject.containsKey("defId")) {
			model.setDefId(jsonObject.getString("defId"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getInteger("version"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormWorkflowPlan model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("processDefId", model.getProcessDefId());
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getPageName() != null) {
			jsonObject.put("pageName", model.getPageName());
		}
		if (model.getBytes() != null) {
			jsonObject.put("bytes", model.getBytes());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDateTime() != null) {
			jsonObject.put("createDateTime", DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_date", DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_datetime", DateUtils.getDateTime(model.getCreateDateTime()));
		}
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		}
		if (model.getModifyDateTime() != null) {
			jsonObject.put("modifyDateTime", DateUtils.getDate(model.getModifyDateTime()));
			jsonObject.put("modifyDateTime_date", DateUtils.getDate(model.getModifyDateTime()));
			jsonObject.put("modifyDateTime_datetime", DateUtils.getDateTime(model.getModifyDateTime()));
		}
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		}
		jsonObject.put("version", model.getVersion());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormWorkflowPlan model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("processDefId", model.getProcessDefId());
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getPageName() != null) {
			jsonObject.put("pageName", model.getPageName());
		}
		if (model.getBytes() != null) {
			jsonObject.put("bytes", model.getBytes());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDateTime() != null) {
			jsonObject.put("createDateTime", DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_date", DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_datetime", DateUtils.getDateTime(model.getCreateDateTime()));
		}
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		}
		if (model.getModifyDateTime() != null) {
			jsonObject.put("modifyDateTime", DateUtils.getDate(model.getModifyDateTime()));
			jsonObject.put("modifyDateTime_date", DateUtils.getDate(model.getModifyDateTime()));
			jsonObject.put("modifyDateTime_datetime", DateUtils.getDateTime(model.getModifyDateTime()));
		}
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		}
		jsonObject.put("version", model.getVersion());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormWorkflowPlan> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormWorkflowPlan model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormWorkflowPlan> arrayToList(JSONArray array) {
		java.util.List<FormWorkflowPlan> list = new java.util.ArrayList<FormWorkflowPlan>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormWorkflowPlan model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormWorkflowPlanJsonFactory() {

	}

}
