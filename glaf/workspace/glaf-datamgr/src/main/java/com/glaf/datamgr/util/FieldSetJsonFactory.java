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
public class FieldSetJsonFactory {

	public static FieldSet jsonToObject(JSONObject jsonObject) {
		FieldSet model = new FieldSet();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("datasetId")) {
			model.setDatasetId(jsonObject.getLong("datasetId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("fieldTable")) {
			model.setFieldTable(jsonObject.getString("fieldTable"));
		}
		if (jsonObject.containsKey("tableNameCN")) {
			model.setTableNameCN(jsonObject.getString("tableNameCN"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("columnWidth")) {
			model.setColumnWidth(jsonObject.getString("columnWidth"));
		}
		if (jsonObject.containsKey("text")) {
			model.setText(jsonObject.getString("text"));
		}
		if (jsonObject.containsKey("description")) {
			model.setDescription(jsonObject.getString("description"));
		}
		if (jsonObject.containsKey("fieldId")) {
			model.setFieldId(jsonObject.getString("fieldId"));
		}
		if (jsonObject.containsKey("fieldLength")) {
			model.setFieldLength(jsonObject.getInteger("fieldLength"));
		}
		if (jsonObject.containsKey("fieldType")) {
			model.setFieldType(jsonObject.getString("fieldType"));
		}
		if (jsonObject.containsKey("isShowList")) {
			model.setIsShowList(jsonObject.getString("isShowList"));
		}
		if (jsonObject.containsKey("isShowTooltip")) {
			model.setIsShowTooltip(jsonObject.getString("isShowTooltip"));
		}
		if (jsonObject.containsKey("isEditor")) {
			model.setIsEditor(jsonObject.getString("isEditor"));
		}
		if (jsonObject.containsKey("editor")) {
			model.setEditor(jsonObject.getString("editor"));
		}
		if (jsonObject.containsKey("state")) {
			model.setState(jsonObject.getString("state"));
		}
		if (jsonObject.containsKey("checked")) {
			model.setChecked(jsonObject.getString("checked"));
		}
		if (jsonObject.containsKey("alignment")) {
			model.setAlignment(jsonObject.getString("alignment"));
		}
		if (jsonObject.containsKey("domId")) {
			model.setDomId(jsonObject.getString("domId"));
		}
		if (jsonObject.containsKey("target")) {
			model.setTarget(jsonObject.getString("target"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FieldSet model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("datasetId", model.getDatasetId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getFieldTable() != null) {
			jsonObject.put("fieldTable", model.getFieldTable());
		}
		if (model.getTableNameCN() != null) {
			jsonObject.put("tableNameCN", model.getTableNameCN());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getColumnWidth() != null) {
			jsonObject.put("columnWidth", model.getColumnWidth());
		}
		if (model.getText() != null) {
			jsonObject.put("text", model.getText());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getFieldId() != null) {
			jsonObject.put("fieldId", model.getFieldId());
		}
		jsonObject.put("fieldLength", model.getFieldLength());
		if (model.getFieldType() != null) {
			jsonObject.put("fieldType", model.getFieldType());
		}
		if (model.getIsShowList() != null) {
			jsonObject.put("isShowList", model.getIsShowList());
		}
		if (model.getIsShowTooltip() != null) {
			jsonObject.put("isShowTooltip", model.getIsShowTooltip());
		}
		if (model.getIsEditor() != null) {
			jsonObject.put("isEditor", model.getIsEditor());
		}
		if (model.getEditor() != null) {
			jsonObject.put("editor", model.getEditor());
		}
		if (model.getState() != null) {
			jsonObject.put("state", model.getState());
		}
		if (model.getChecked() != null) {
			jsonObject.put("checked", model.getChecked());
		}
		if (model.getAlignment() != null) {
			jsonObject.put("alignment", model.getAlignment());
		}
		if (model.getDomId() != null) {
			jsonObject.put("domId", model.getDomId());
		}
		if (model.getTarget() != null) {
			jsonObject.put("target", model.getTarget());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("locked", model.getLocked());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FieldSet model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("datasetId", model.getDatasetId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getFieldTable() != null) {
			jsonObject.put("fieldTable", model.getFieldTable());
		}
		if (model.getTableNameCN() != null) {
			jsonObject.put("tableNameCN", model.getTableNameCN());
		}
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		}
		if (model.getColumnWidth() != null) {
			jsonObject.put("columnWidth", model.getColumnWidth());
		}
		if (model.getText() != null) {
			jsonObject.put("text", model.getText());
		}
		if (model.getDescription() != null) {
			jsonObject.put("description", model.getDescription());
		}
		if (model.getFieldId() != null) {
			jsonObject.put("fieldId", model.getFieldId());
		}
		jsonObject.put("fieldLength", model.getFieldLength());
		if (model.getFieldType() != null) {
			jsonObject.put("fieldType", model.getFieldType());
		}
		if (model.getIsShowList() != null) {
			jsonObject.put("isShowList", model.getIsShowList());
		}
		if (model.getIsShowTooltip() != null) {
			jsonObject.put("isShowTooltip", model.getIsShowTooltip());
		}
		if (model.getIsEditor() != null) {
			jsonObject.put("isEditor", model.getIsEditor());
		}
		if (model.getEditor() != null) {
			jsonObject.put("editor", model.getEditor());
		}
		if (model.getState() != null) {
			jsonObject.put("state", model.getState());
		}
		if (model.getChecked() != null) {
			jsonObject.put("checked", model.getChecked());
		}
		if (model.getAlignment() != null) {
			jsonObject.put("alignment", model.getAlignment());
		}
		if (model.getDomId() != null) {
			jsonObject.put("domId", model.getDomId());
		}
		if (model.getTarget() != null) {
			jsonObject.put("target", model.getTarget());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		jsonObject.put("locked", model.getLocked());
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FieldSet> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FieldSet model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FieldSet> arrayToList(JSONArray array) {
		java.util.List<FieldSet> list = new java.util.ArrayList<FieldSet>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FieldSet model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FieldSetJsonFactory() {

	}

}
