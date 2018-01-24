package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.CellDataField;

/**
 * 
 * JSON工厂类
 *
 */
public class CellDataFieldJsonFactory {

	public static CellDataField jsonToObject(JSONObject jsonObject) {
		CellDataField model = new CellDataField();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("tableId")) {
			model.setTableId(jsonObject.getString("tableId"));
		}
		if (jsonObject.containsKey("fieldName")) {
			model.setFieldName(jsonObject.getString("fieldName"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("maxUser")) {
			model.setMaxUser(jsonObject.getInteger("maxUser"));
		}
		if (jsonObject.containsKey("maxSys")) {
			model.setMaxSys(jsonObject.getInteger("maxSys"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("sysNum")) {
			model.setSysNum(jsonObject.getString("sysNum"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("dname")) {
			model.setDname(jsonObject.getString("dname"));
		}
		if (jsonObject.containsKey("userIndex")) {
			model.setUserIndex(jsonObject.getString("userIndex"));
		}
		if (jsonObject.containsKey("treeTableNameB")) {
			model.setTreeTableNameB(jsonObject.getString("treeTableNameB"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellDataField model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTableId() != null) {
			jsonObject.put("tableId", model.getTableId());
		}
		if (model.getFieldName() != null) {
			jsonObject.put("fieldName", model.getFieldName());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		jsonObject.put("maxUser", model.getMaxUser());
		jsonObject.put("maxSys", model.getMaxSys());
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getSysNum() != null) {
			jsonObject.put("sysNum", model.getSysNum());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getDname() != null) {
			jsonObject.put("dname", model.getDname());
		}
		if (model.getUserIndex() != null) {
			jsonObject.put("userIndex", model.getUserIndex());
		}
		if (model.getTreeTableNameB() != null) {
			jsonObject.put("treeTableNameB", model.getTreeTableNameB());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellDataField model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTableId() != null) {
			jsonObject.put("tableId", model.getTableId());
		}
		if (model.getFieldName() != null) {
			jsonObject.put("fieldName", model.getFieldName());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		jsonObject.put("maxUser", model.getMaxUser());
		jsonObject.put("maxSys", model.getMaxSys());
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getSysNum() != null) {
			jsonObject.put("sysNum", model.getSysNum());
		}
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		}
		if (model.getDname() != null) {
			jsonObject.put("dname", model.getDname());
		}
		if (model.getUserIndex() != null) {
			jsonObject.put("userIndex", model.getUserIndex());
		}
		if (model.getTreeTableNameB() != null) {
			jsonObject.put("treeTableNameB", model.getTreeTableNameB());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellDataField> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellDataField model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellDataField> arrayToList(JSONArray array) {
		java.util.List<CellDataField> list = new java.util.ArrayList<CellDataField>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellDataField model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellDataFieldJsonFactory() {

	}

}
