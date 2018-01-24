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
public class FormEventTemplateJsonFactory {

	public static FormEventTemplate jsonToObject(JSONObject jsonObject) {
		FormEventTemplate model = new FormEventTemplate();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("pId")) {
			model.setPId(jsonObject.getString("pId"));
		}
		if (jsonObject.containsKey("complexId")) {
			model.setComplexId(jsonObject.getString("complexId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("remark")) {
			model.setRemark(jsonObject.getString("remark"));
		}
		if (jsonObject.containsKey("diagram")) {
			model.setDiagram(jsonObject.getString("diagram"));
		}
		if (jsonObject.containsKey("rule")) {
			model.setRule(jsonObject.getString("rule"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("complexRule")) {
			model.setComplexRule(jsonObject.getString("complexRule"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("viewType")) {
			model.setViewType(jsonObject.getString("viewType"));
		}
		if (jsonObject.containsKey("tableRule")) {
			model.setTableRule(jsonObject.getString("tableRule"));
		}
		if (jsonObject.containsKey("complexTableRule")) {
			model.setComplexTableRule(jsonObject.getString("complexTableRule"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FormEventTemplate model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getPId() != null) {
			jsonObject.put("pId", model.getPId());
		}
		if (model.getComplexId() != null) {
			jsonObject.put("complexId", model.getComplexId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
		if (model.getDiagram() != null) {
			jsonObject.put("diagram", model.getDiagram());
		}
		if (model.getRule() != null) {
			jsonObject.put("rule", model.getRule());
		}
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getComplexRule() != null) {
			jsonObject.put("complexRule", model.getComplexRule());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}

		if (model.getViewType() != null) {
			jsonObject.put("viewType", model.getViewType());
		}
		if (model.getTableRule() != null) {
			jsonObject.put("tableRule", model.getTableRule());
		}
		if (model.getComplexTableRule() != null) {
			jsonObject.put("complexTableRule", model.getComplexTableRule());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FormEventTemplate model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getPId() != null) {
			jsonObject.put("pId", model.getPId());
		}
		if (model.getComplexId() != null) {
			jsonObject.put("complexId", model.getComplexId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getRemark() != null) {
			jsonObject.put("remark", model.getRemark());
		}
		if (model.getDiagram() != null) {
			jsonObject.put("diagram", model.getDiagram());
		}
		if (model.getRule() != null) {
			jsonObject.put("rule", model.getRule());
		}
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		}
		if (model.getComplexRule() != null) {
			jsonObject.put("complexRule", model.getComplexRule());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}

		if (model.getViewType() != null) {
			jsonObject.put("viewType", model.getViewType());
		}
		if (model.getTableRule() != null) {
			jsonObject.put("tableRule", model.getTableRule());
		}
		if (model.getComplexTableRule() != null) {
			jsonObject.put("complexTableRule", model.getComplexTableRule());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FormEventTemplate> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormEventTemplate model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormEventTemplate> arrayToList(JSONArray array) {
		java.util.List<FormEventTemplate> list = new java.util.ArrayList<FormEventTemplate>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormEventTemplate model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FormEventTemplateJsonFactory() {

	}

}
