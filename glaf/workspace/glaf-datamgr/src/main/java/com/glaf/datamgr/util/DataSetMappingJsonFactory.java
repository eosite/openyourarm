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
public class DataSetMappingJsonFactory {

	public static DataSetMapping jsonToObject(JSONObject jsonObject) {
		DataSetMapping model = new DataSetMapping();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("dsName")) {
			model.setDsName(jsonObject.getString("dsName"));
		}
		if (jsonObject.containsKey("dsmId")) {
			model.setDsmId(jsonObject.getString("dsmId"));
		}
		if (jsonObject.containsKey("dsmName")) {
			model.setDsmName(jsonObject.getString("dsmName"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("treeId")) {
			model.setTreeId(jsonObject.getString("treeId"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getString("parentId"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DataSetMapping model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDsName() != null) {
			jsonObject.put("dsName", model.getDsName());
		}
		if (model.getDsmId() != null) {
			jsonObject.put("dsmId", model.getDsmId());
		}
		if (model.getDsmName() != null) {
			jsonObject.put("dsmName", model.getDsmName());
		}
		jsonObject.put("status", model.getStatus());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
		}
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DataSetMapping model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDsName() != null) {
			jsonObject.put("dsName", model.getDsName());
		}
		if (model.getDsmId() != null) {
			jsonObject.put("dsmId", model.getDsmId());
		}
		if (model.getDsmName() != null) {
			jsonObject.put("dsmName", model.getDsmName());
		}
		jsonObject.put("status", model.getStatus());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
		}
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getParentId() != null) {
			jsonObject.put("parentId", model.getParentId());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateDate() != null) {
			jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
			jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateDate() != null) {
			jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
			jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DataSetMapping> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DataSetMapping model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DataSetMapping> arrayToList(JSONArray array) {
		java.util.List<DataSetMapping> list = new java.util.ArrayList<DataSetMapping>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DataSetMapping model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DataSetMappingJsonFactory() {

	}

}
