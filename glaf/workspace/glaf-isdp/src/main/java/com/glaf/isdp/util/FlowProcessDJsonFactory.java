package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.FlowProcessD;

/**
 * 
 * JSON工厂类
 *
 */
public class FlowProcessDJsonFactory {

	public static FlowProcessD jsonToObject(JSONObject jsonObject) {
		FlowProcessD model = new FlowProcessD();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("mainId")) {
			model.setMainId(jsonObject.getString("mainId"));
		}
		if (jsonObject.containsKey("fileId")) {
			model.setFileId(jsonObject.getString("fileId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("actor")) {
			model.setActor(jsonObject.getString("actor"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("version")) {
			model.setVersion(jsonObject.getString("version"));
		}
		if (jsonObject.containsKey("tcadFile")) {
			model.setTcadFile(jsonObject.getString("tcadFile"));
		}
		if (jsonObject.containsKey("isSave")) {
			model.setIsSave(jsonObject.getInteger("isSave"));
		}
		if (jsonObject.containsKey("intFlag")) {
			model.setIntFlag(jsonObject.getInteger("intFlag"));
		}

		return model;
	}

	public static JSONObject toJsonObject(FlowProcessD model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getMainId() != null) {
			jsonObject.put("mainId", model.getMainId());
		}
		if (model.getFileId() != null) {
			jsonObject.put("fileId", model.getFileId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getActor() != null) {
			jsonObject.put("actor", model.getActor());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getVersion() != null) {
			jsonObject.put("version", model.getVersion());
		}
		if (model.getTcadFile() != null) {
			jsonObject.put("tcadFile", model.getTcadFile());
		}
		jsonObject.put("isSave", model.getIsSave());
		jsonObject.put("intFlag", model.getIntFlag());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(FlowProcessD model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getMainId() != null) {
			jsonObject.put("mainId", model.getMainId());
		}
		if (model.getFileId() != null) {
			jsonObject.put("fileId", model.getFileId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getActor() != null) {
			jsonObject.put("actor", model.getActor());
		}
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		if (model.getVersion() != null) {
			jsonObject.put("version", model.getVersion());
		}
		if (model.getTcadFile() != null) {
			jsonObject.put("tcadFile", model.getTcadFile());
		}
		jsonObject.put("isSave", model.getIsSave());
		jsonObject.put("intFlag", model.getIntFlag());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<FlowProcessD> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FlowProcessD model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FlowProcessD> arrayToList(JSONArray array) {
		java.util.List<FlowProcessD> list = new java.util.ArrayList<FlowProcessD>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FlowProcessD model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private FlowProcessDJsonFactory() {

	}

}
