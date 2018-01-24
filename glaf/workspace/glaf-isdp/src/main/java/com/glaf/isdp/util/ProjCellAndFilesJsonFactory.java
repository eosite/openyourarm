package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.ProjCellAndFiles;

/**
 * 
 * JSON工厂类
 *
 */
public class ProjCellAndFilesJsonFactory {

	public static ProjCellAndFiles jsonToObject(JSONObject jsonObject) {
		ProjCellAndFiles model = new ProjCellAndFiles();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("intType")) {
			model.setIntType(jsonObject.getInteger("intType"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("defId")) {
			model.setDefId(jsonObject.getString("defId"));
		}
		if (jsonObject.containsKey("useId")) {
			model.setUseId(jsonObject.getString("useId"));
		}
		if (jsonObject.containsKey("intPage0")) {
			model.setIntPage0(jsonObject.getInteger("intPage0"));
		}
		if (jsonObject.containsKey("intPage1")) {
			model.setIntPage1(jsonObject.getInteger("intPage1"));
		}
		if (jsonObject.containsKey("intPage2")) {
			model.setIntPage2(jsonObject.getInteger("intPage2"));
		}
		if (jsonObject.containsKey("intFinish")) {
			model.setIntFinish(jsonObject.getInteger("intFinish"));
		}
		if (jsonObject.containsKey("fileDotNum")) {
			model.setFileDotNum(jsonObject.getString("fileDotNum"));
		}

		return model;
	}

	public static JSONObject toJsonObject(ProjCellAndFiles model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("intType", model.getIntType());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		}
		if (model.getUseId() != null) {
			jsonObject.put("useId", model.getUseId());
		}
		jsonObject.put("intPage0", model.getIntPage0());
		jsonObject.put("intPage1", model.getIntPage1());
		jsonObject.put("intPage2", model.getIntPage2());
		jsonObject.put("intFinish", model.getIntFinish());
		if(model.getFileDotNum() != null){
			jsonObject.put("fileDotNum", model.getFileDotNum());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(ProjCellAndFiles model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("intType", model.getIntType());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		}
		if (model.getUseId() != null) {
			jsonObject.put("useId", model.getUseId());
		}
		jsonObject.put("intPage0", model.getIntPage0());
		jsonObject.put("intPage1", model.getIntPage1());
		jsonObject.put("intPage2", model.getIntPage2());
		jsonObject.put("intFinish", model.getIntFinish());
		if(model.getFileDotNum() != null){
			jsonObject.put("fileDotNum", model.getFileDotNum());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<ProjCellAndFiles> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ProjCellAndFiles model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ProjCellAndFiles> arrayToList(JSONArray array) {
		java.util.List<ProjCellAndFiles> list = new java.util.ArrayList<ProjCellAndFiles>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ProjCellAndFiles model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private ProjCellAndFilesJsonFactory() {

	}

}
