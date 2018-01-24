package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.TreeProj;

/**
 * 
 * JSON工厂类
 *
 */
public class TreeProjJsonFactory {

	public static TreeProj jsonToObject(JSONObject jsonObject) {
		TreeProj model = new TreeProj();
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("projType")) {
			model.setProjType(jsonObject.getString("projType"));
		}
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("topId")) {
			model.setTopId(jsonObject.getInteger("topId"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getInteger("parentId"));
		}
		if (jsonObject.containsKey("indexName")) {
			model.setIndexName(jsonObject.getString("indexName"));
		}
		if (jsonObject.containsKey("Level")) {
			model.setLevel(jsonObject.getInteger("Level"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("useId")) {
			model.setUseId(jsonObject.getString("useId"));
		}
		if (jsonObject.containsKey("sindexName")) {
			model.setSindexName(jsonObject.getString("sindexName"));
		}
		if (jsonObject.containsKey("content2")) {
			model.setContent2(jsonObject.getString("content2"));
		}
		if (jsonObject.containsKey("topNode")) {
			model.setTopNode(jsonObject.getString("topNode"));
		}
		if (jsonObject.containsKey("nodeIco")) {
			model.setNodeIco(jsonObject.getInteger("nodeIco"));
		}
		if (jsonObject.containsKey("unitNum")) {
			model.setUnitNum(jsonObject.getString("unitNum"));
		}
		if (jsonObject.containsKey("showId")) {
			model.setShowId(jsonObject.getInteger("showId"));
		}
		if (jsonObject.containsKey("scaleQ")) {
			model.setScaleQ(jsonObject.getDouble("scaleQ"));
		}
		if (jsonObject.containsKey("isPegWork")) {
			model.setIsPegWork(jsonObject.getString("isPegWork"));
		}
		if (jsonObject.containsKey("treeProjUser2")) {
			model.setTreeProjUser2(jsonObject.getString("treeProjUser2"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TreeProj model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getProjType() != null) {
			jsonObject.put("projType", model.getProjType());
		}
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
			jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		}
		jsonObject.put("topId", model.getTopId());
		jsonObject.put("parentId", model.getParentId());
		if (model.getIndexName() != null) {
			jsonObject.put("indexName", model.getIndexName());
		}
		jsonObject.put("Level", model.getLevel());
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getUseId() != null) {
			jsonObject.put("useId", model.getUseId());
		}
		if (model.getSindexName() != null) {
			jsonObject.put("sindexName", model.getSindexName());
		}
		if (model.getContent2() != null) {
			jsonObject.put("content2", model.getContent2());
		}
		if (model.getTopNode() != null) {
			jsonObject.put("topNode", model.getTopNode());
		}
		jsonObject.put("nodeIco", model.getNodeIco());
		if (model.getUnitNum() != null) {
			jsonObject.put("unitNum", model.getUnitNum());
		}
		jsonObject.put("showId", model.getShowId());
		jsonObject.put("scaleQ", model.getScaleQ());
		if (model.getIsPegWork() != null) {
			jsonObject.put("isPegWork", model.getIsPegWork());
		}
		if (model.getTreeProjUser2() != null) {
			jsonObject.put("treeProjUser2", model.getTreeProjUser2());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TreeProj model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getProjType() != null) {
			jsonObject.put("projType", model.getProjType());
		}
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
			jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		}
		jsonObject.put("topId", model.getTopId());
		jsonObject.put("parentId", model.getParentId());
		if (model.getIndexName() != null) {
			jsonObject.put("indexName", model.getIndexName());
		}
		jsonObject.put("Level", model.getLevel());
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getUseId() != null) {
			jsonObject.put("useId", model.getUseId());
		}
		if (model.getSindexName() != null) {
			jsonObject.put("sindexName", model.getSindexName());
		}
		if (model.getContent2() != null) {
			jsonObject.put("content2", model.getContent2());
		}
		if (model.getTopNode() != null) {
			jsonObject.put("topNode", model.getTopNode());
		}
		jsonObject.put("nodeIco", model.getNodeIco());
		if (model.getUnitNum() != null) {
			jsonObject.put("unitNum", model.getUnitNum());
		}
		jsonObject.put("showId", model.getShowId());
		jsonObject.put("scaleQ", model.getScaleQ());
		if (model.getIsPegWork() != null) {
			jsonObject.put("isPegWork", model.getIsPegWork());
		}
		if (model.getTreeProjUser2() != null) {
			jsonObject.put("treeProjUser2", model.getTreeProjUser2());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TreeProj> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TreeProj model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TreeProj> arrayToList(JSONArray array) {
		java.util.List<TreeProj> list = new java.util.ArrayList<TreeProj>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TreeProj model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TreeProjJsonFactory() {

	}

}
