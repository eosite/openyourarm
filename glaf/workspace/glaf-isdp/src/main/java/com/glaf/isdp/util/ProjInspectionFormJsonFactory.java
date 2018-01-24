package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.ProjInspectionForm;

/**
 * 
 * JSON工厂类
 *
 */
public class ProjInspectionFormJsonFactory {

	public static ProjInspectionForm jsonToObject(JSONObject jsonObject) {
		ProjInspectionForm model = new ProjInspectionForm();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("projInspectionId")) {
			model.setProjInspectionId(jsonObject.getString("projInspectionId"));
		}
		if (jsonObject.containsKey("cellFormId")) {
			model.setCellFormId(jsonObject.getString("cellFormId"));
		}
		if (jsonObject.containsKey("dotUseId")) {
			model.setDotUseId(jsonObject.getString("dotUseId"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("intIsCheck")) {
			model.setIntIsCheck(jsonObject.getInteger("intIsCheck"));
		}
		if (jsonObject.containsKey("intMust")) {
			model.setIntMust(jsonObject.getInteger("intMust"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ProjInspectionForm model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProjInspectionId() != null) {
			jsonObject.put("projInspectionId", model.getProjInspectionId());
		}
		if (model.getCellFormId() != null) {
			jsonObject.put("cellFormId", model.getCellFormId());
		}
		if (model.getDotUseId() != null) {
			jsonObject.put("dotUseId", model.getDotUseId());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("intIsCheck", model.getIntIsCheck());
		jsonObject.put("intMust", model.getIntMust());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ProjInspectionForm model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProjInspectionId() != null) {
			jsonObject.put("projInspectionId", model.getProjInspectionId());
		}
		if (model.getCellFormId() != null) {
			jsonObject.put("cellFormId", model.getCellFormId());
		}
		if (model.getDotUseId() != null) {
			jsonObject.put("dotUseId", model.getDotUseId());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("intIsCheck", model.getIntIsCheck());
		jsonObject.put("intMust", model.getIntMust());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ProjInspectionForm> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProjInspectionForm model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProjInspectionForm> arrayToList(JSONArray array) {
		java.util.List<ProjInspectionForm> list = new java.util.ArrayList<ProjInspectionForm>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProjInspectionForm model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ProjInspectionFormJsonFactory() {

	}

}
