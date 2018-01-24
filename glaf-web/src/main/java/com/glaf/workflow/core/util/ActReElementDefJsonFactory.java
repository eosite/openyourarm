package com.glaf.workflow.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.workflow.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class ActReElementDefJsonFactory {

	public static ActReElementDef jsonToObject(JSONObject jsonObject) {
		ActReElementDef model = new ActReElementDef();
		if (jsonObject.containsKey("ID")) {
			model.setID(jsonObject.getLong("ID"));
		}
		if (jsonObject.containsKey("eleType")) {
			model.setEleType(jsonObject.getString("eleType"));
		}
		if (jsonObject.containsKey("eleResourceId")) {
			model.setEleResourceId(jsonObject.getString("eleResourceId"));
		}
		if (jsonObject.containsKey("eleID")) {
			model.setEleID(jsonObject.getString("eleID"));
		}
		if (jsonObject.containsKey("eleName")) {
			model.setEleName(jsonObject.getString("eleName"));
		}
		if (jsonObject.containsKey("subProcessKey")) {
			model.setSubProcessKey(jsonObject.getString("subProcessKey"));
		}
		if (jsonObject.containsKey("eleDesc")) {
			model.setEleDesc(jsonObject.getString("eleDesc"));
		}
		if (jsonObject.containsKey("modelId")) {
			model.setModelId(jsonObject.getString("modelId"));
		}
		if (jsonObject.containsKey("ProceDefId")) {
			model.setProceDefId(jsonObject.getString("ProceDefId"));
		}
		if (jsonObject.containsKey("bytes")) {
			model.setBytes(jsonObject.getString("bytes"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modify")) {
			model.setModify(jsonObject.getString("modify"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ActReElementDef model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ID", model.getID());
		jsonObject.put("_ID_", model.getID());
		if (model.getEleType() != null) {
			jsonObject.put("eleType", model.getEleType());
		}
		if (model.getEleResourceId() != null) {
			jsonObject.put("eleResourceId", model.getEleResourceId());
		}
		if (model.getEleID() != null) {
			jsonObject.put("eleID", model.getEleID());
		}
		if (model.getEleName() != null) {
			jsonObject.put("eleName", model.getEleName());
		}
		if (model.getEleID() != null) {
			jsonObject.put("eleID", model.getEleID());
		}
		if (model.getSubProcessKey() != null) {
			jsonObject.put("subProcessKey", model.getSubProcessKey());
		}
		if (model.getModelId() != null) {
			jsonObject.put("modelId", model.getModelId());
		}
		if (model.getProceDefId() != null) {
			jsonObject.put("ProceDefId", model.getProceDefId());
		}
		if (model.getBytes() != null) {
			jsonObject.put("bytes", model.getBytes());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDatetime() != null) {
			jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
			jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
			jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
		}
		if (model.getModify() != null) {
			jsonObject.put("modify", model.getModify());
		}
		if (model.getModifyDatetime() != null) {
			jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
			jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
			jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ActReElementDef model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("ID", model.getID());
		jsonObject.put("_ID_", model.getID());
		if (model.getEleType() != null) {
			jsonObject.put("eleType", model.getEleType());
		}
		if (model.getEleResourceId() != null) {
			jsonObject.put("eleResourceId", model.getEleResourceId());
		}
		if (model.getEleID() != null) {
			jsonObject.put("eleID", model.getEleID());
		}
		if (model.getEleName() != null) {
			jsonObject.put("eleName", model.getEleName());
		}
		if (model.getSubProcessKey() != null) {
			jsonObject.put("subProcessKey", model.getSubProcessKey());
		}
		if (model.getEleDesc() != null) {
			jsonObject.put("eleDesc", model.getEleDesc());
		}
		if (model.getModelId() != null) {
			jsonObject.put("modelId", model.getModelId());
		}
		if (model.getProceDefId() != null) {
			jsonObject.put("ProceDefId", model.getProceDefId());
		}
		if (model.getBytes() != null) {
			jsonObject.put("bytes", model.getBytes());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDatetime() != null) {
			jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
			jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
			jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
		}
		if (model.getModify() != null) {
			jsonObject.put("modify", model.getModify());
		}
		if (model.getModifyDatetime() != null) {
			jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
			jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
			jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ActReElementDef> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ActReElementDef model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ActReElementDef> arrayToList(JSONArray array) {
		java.util.List<ActReElementDef> list = new java.util.ArrayList<ActReElementDef>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ActReElementDef model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ActReElementDefJsonFactory() {

	}

}
