package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.CellUTableTree;

/**
 * 
 * JSON工厂类
 *
 */
public class CellUTableTreeJsonFactory {

	public static CellUTableTree jsonToObject(JSONObject jsonObject) {
		CellUTableTree model = new CellUTableTree();
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("parentId")) {
			model.setParentId(jsonObject.getInteger("parentId"));
		}
		if (jsonObject.containsKey("indexName")) {
			model.setIndexName(jsonObject.getString("indexName"));
		}
		if (jsonObject.containsKey("Level")) {
			model.setNlevel(jsonObject.getInteger("nlevel"));
		}
		if (jsonObject.containsKey("nodeIco")) {
			model.setNodeIco(jsonObject.getInteger("nodeIco"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("tableType")) {
			model.setTableType(jsonObject.getInteger("tableType"));
		}
		if (jsonObject.containsKey("intDel")) {
			model.setIntDel(jsonObject.getInteger("intDel"));
		}
		if (jsonObject.containsKey("busiessId")) {
			model.setBusiessId(jsonObject.getString("busiessId"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("menuIndex")) {
			model.setMenuIndex(jsonObject.getInteger("menuIndex"));
		}
		if (jsonObject.containsKey("domainIndex")) {
			model.setDomainIndex(jsonObject.getInteger("domainIndex"));
		}
		if (jsonObject.containsKey("winWidth")) {
			model.setWinWidth(jsonObject.getInteger("winWidth"));
		}
		if (jsonObject.containsKey("winHeight")) {
			model.setWinHeight(jsonObject.getInteger("winHeight"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellUTableTree model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
			jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		}
		jsonObject.put("parentId", model.getParentId());
		if (model.getIndexName() != null) {
			jsonObject.put("indexName", model.getIndexName());
		}
		jsonObject.put("nlevel", model.getNlevel());
		jsonObject.put("nodeIco", model.getNodeIco());
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("tableType", model.getTableType());
		jsonObject.put("intDel", model.getIntDel());
		if (model.getBusiessId() != null) {
			jsonObject.put("busiessId", model.getBusiessId());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		jsonObject.put("menuIndex", model.getMenuIndex());
		jsonObject.put("domainIndex", model.getDomainIndex());
		jsonObject.put("winWidth", model.getWinWidth());
		jsonObject.put("winHeight", model.getWinHeight());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellUTableTree model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
			jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		}
		jsonObject.put("parentId", model.getParentId());
		if (model.getIndexName() != null) {
			jsonObject.put("indexName", model.getIndexName());
		}
		jsonObject.put("nlevel", model.getNlevel());
		jsonObject.put("nodeIco", model.getNodeIco());
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("tableType", model.getTableType());
		jsonObject.put("intDel", model.getIntDel());
		if (model.getBusiessId() != null) {
			jsonObject.put("busiessId", model.getBusiessId());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		jsonObject.put("menuIndex", model.getMenuIndex());
		jsonObject.put("domainIndex", model.getDomainIndex());
		jsonObject.put("winWidth", model.getWinWidth());
		jsonObject.put("winHeight", model.getWinHeight());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellUTableTree> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellUTableTree model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellUTableTree> arrayToList(JSONArray array) {
		java.util.List<CellUTableTree> list = new java.util.ArrayList<CellUTableTree>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellUTableTree model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellUTableTreeJsonFactory() {

	}

}
