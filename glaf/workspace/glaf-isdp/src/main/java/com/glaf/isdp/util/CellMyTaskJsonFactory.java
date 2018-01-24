package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.util.DateUtils;
import com.glaf.isdp.domain.CellMyTask;

/**
 * 
 * JSON工厂类
 *
 */
public class CellMyTaskJsonFactory {

	public static CellMyTask jsonToObject(JSONObject jsonObject) {
		CellMyTask model = new CellMyTask();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getString("topId"));
		}
		if (jsonObject.containsKey("fillFormId")) {
			model.setFillFormId(jsonObject.getString("fillFormId"));
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
		if (jsonObject.containsKey("fileDotFileId")) {
			model.setFileDotFileId(jsonObject.getString("fileDotFileId"));
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
		if (jsonObject.containsKey("page")) {
			model.setPage(jsonObject.getInteger("page"));
		}
		if (jsonObject.containsKey("finishInt")) {
			model.setFinishInt(jsonObject.getInteger("finishInt"));
		}
		if (jsonObject.containsKey("formTypeInt")) {
			model.setFormTypeInt(jsonObject.getInteger("formTypeInt"));
		}
		if (jsonObject.containsKey("flagInt")) {
			model.setFlagInt(jsonObject.getInteger("flagInt"));
		}
		if (jsonObject.containsKey("intInFlow")) {
			model.setIntInFlow(jsonObject.getInteger("intInFlow"));
		}
		if (jsonObject.containsKey("mainId")) {
			model.setMainId(jsonObject.getString("mainId"));
		}
		if (jsonObject.containsKey("intLastPage")) {
			model.setIntLastPage(jsonObject.getInteger("intLastPage"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellMyTask model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getFillFormId() != null) {
			jsonObject.put("fillFormId", model.getFillFormId());
		}
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
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getProjName() != null) {
			jsonObject.put("projName", model.getProjName());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("typeIndexId", model.getTypeIndexId());
		jsonObject.put("page", model.getPage());
		jsonObject.put("finishInt", model.getFinishInt());
		jsonObject.put("formTypeInt", model.getFormTypeInt());
		jsonObject.put("flagInt", model.getFlagInt());
		jsonObject.put("intInFlow", model.getIntInFlow());
		if (model.getMainId() != null) {
			jsonObject.put("mainId", model.getMainId());
		}
		jsonObject.put("intLastPage", model.getIntLastPage());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellMyTask model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTopId() != null) {
			jsonObject.put("topId", model.getTopId());
		}
		if (model.getFillFormId() != null) {
			jsonObject.put("fillFormId", model.getFillFormId());
		}
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
		if (model.getFileDotFileId() != null) {
			jsonObject.put("fileDotFileId", model.getFileDotFileId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getProjName() != null) {
			jsonObject.put("projName", model.getProjName());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("typeIndexId", model.getTypeIndexId());
		jsonObject.put("page", model.getPage());
		jsonObject.put("finishInt", model.getFinishInt());
		jsonObject.put("formTypeInt", model.getFormTypeInt());
		jsonObject.put("flagInt", model.getFlagInt());
		jsonObject.put("intInFlow", model.getIntInFlow());
		if (model.getMainId() != null) {
			jsonObject.put("mainId", model.getMainId());
		}
		jsonObject.put("intLastPage", model.getIntLastPage());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellMyTask> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellMyTask model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellMyTask> arrayToList(JSONArray array) {
		java.util.List<CellMyTask> list = new java.util.ArrayList<CellMyTask>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellMyTask model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellMyTaskJsonFactory() {

	}

}
