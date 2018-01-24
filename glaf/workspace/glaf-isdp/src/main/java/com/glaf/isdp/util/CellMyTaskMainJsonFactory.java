package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.CellMyTaskMain;

/**
 * 
 * JSON工厂类
 *
 */
public class CellMyTaskMainJsonFactory {

	public static CellMyTaskMain jsonToObject(JSONObject jsonObject) {
		CellMyTaskMain model = new CellMyTaskMain();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("ctime")) {
			model.setCtime(jsonObject.getDate("ctime"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("taskId")) {
			model.setTaskId(jsonObject.getString("taskId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("projName")) {
			model.setProjName(jsonObject.getString("projName"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("typeIndexId")) {
			model.setTypeIndexId(jsonObject.getInteger("typeIndexId"));
		}
		if (jsonObject.containsKey("flagInt")) {
			model.setFlagInt(jsonObject.getInteger("flagInt"));
		}
		if (jsonObject.containsKey("myCellTsksId")) {
			model.setMyCellTsksId(jsonObject.getString("myCellTsksId"));
		}
		if (jsonObject.containsKey("fromTasksId")) {
			model.setFromTasksId(jsonObject.getString("fromTasksId"));
		}
		if (jsonObject.containsKey("toTaskId")) {
			model.setToTaskId(jsonObject.getString("toTaskId"));
		}
		if (jsonObject.containsKey("intFinish")) {
			model.setIntFinish(jsonObject.getInteger("intFinish"));
		}
		 
		if (jsonObject.containsKey("typeTableName")) {
			model.setTypeTableName(jsonObject.getString("typeTableName"));
		}
		if (jsonObject.containsKey("typeId")) {
			model.setTypeId(jsonObject.getString("typeId"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("netRoleId")) {
			model.setNetRoleId(jsonObject.getString("netRoleId"));
		}
		if (jsonObject.containsKey("intIsFlow")) {
			model.setIntIsFlow(jsonObject.getInteger("intIsFlow"));
		}
		if (jsonObject.containsKey("intStop")) {
			model.setIntStop(jsonObject.getInteger("intStop"));
		}
		if (jsonObject.containsKey("fileTypeIndex")) {
			model.setFileTypeIndex(jsonObject.getInteger("fileTypeIndex"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellMyTaskMain model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		jsonObject.put("indexId", model.getIndexId());
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getProjName() != null) {
			jsonObject.put("projName", model.getProjName());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("typeIndexId", model.getTypeIndexId());
		jsonObject.put("flagInt", model.getFlagInt());
		if (model.getMyCellTsksId() != null) {
			jsonObject.put("myCellTsksId", model.getMyCellTsksId());
		}
		if (model.getFromTasksId() != null) {
			jsonObject.put("fromTasksId", model.getFromTasksId());
		}
		if (model.getToTaskId() != null) {
			jsonObject.put("toTaskId", model.getToTaskId());
		}
		jsonObject.put("intFinish", model.getIntFinish());
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		if (model.getTypeTableName() != null) {
			jsonObject.put("typeTableName", model.getTypeTableName());
		}
		if (model.getTypeId() != null) {
			jsonObject.put("typeId", model.getTypeId());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getNetRoleId() != null) {
			jsonObject.put("netRoleId", model.getNetRoleId());
		}
		jsonObject.put("intIsFlow", model.getIntIsFlow());
		jsonObject.put("intStop", model.getIntStop());
		jsonObject.put("fileTypeIndex", model.getFileTypeIndex());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellMyTaskMain model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCtime() != null) {
			jsonObject.put("ctime", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_date", DateUtils.getDate(model.getCtime()));
			jsonObject.put("ctime_datetime",
					DateUtils.getDateTime(model.getCtime()));
		}
		jsonObject.put("indexId", model.getIndexId());
		if (model.getTaskId() != null) {
			jsonObject.put("taskId", model.getTaskId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getProjName() != null) {
			jsonObject.put("projName", model.getProjName());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("typeIndexId", model.getTypeIndexId());
		jsonObject.put("flagInt", model.getFlagInt());
		if (model.getMyCellTsksId() != null) {
			jsonObject.put("myCellTsksId", model.getMyCellTsksId());
		}
		if (model.getFromTasksId() != null) {
			jsonObject.put("fromTasksId", model.getFromTasksId());
		}
		if (model.getToTaskId() != null) {
			jsonObject.put("toTaskId", model.getToTaskId());
		}
		jsonObject.put("intFinish", model.getIntFinish());
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		if (model.getTypeTableName() != null) {
			jsonObject.put("typeTableName", model.getTypeTableName());
		}
		if (model.getTypeId() != null) {
			jsonObject.put("typeId", model.getTypeId());
		}
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getNetRoleId() != null) {
			jsonObject.put("netRoleId", model.getNetRoleId());
		}
		jsonObject.put("intIsFlow", model.getIntIsFlow());
		jsonObject.put("intStop", model.getIntStop());
		jsonObject.put("fileTypeIndex", model.getFileTypeIndex());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellMyTaskMain> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellMyTaskMain model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellMyTaskMain> arrayToList(JSONArray array) {
		java.util.List<CellMyTaskMain> list = new java.util.ArrayList<CellMyTaskMain>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellMyTaskMain model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellMyTaskMainJsonFactory() {

	}

}
