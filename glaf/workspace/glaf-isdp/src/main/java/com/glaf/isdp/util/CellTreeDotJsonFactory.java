package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.CellTreeDot;

/**
 * 
 * JSON工厂类
 *
 */
public class CellTreeDotJsonFactory {

	public static CellTreeDot jsonToObject(JSONObject jsonObject) {
		CellTreeDot model = new CellTreeDot();
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
			model.setLevel(jsonObject.getInteger("Level"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("nodeIco")) {
			model.setNodeIco(jsonObject.getInteger("nodeIco"));
		}
		if (jsonObject.containsKey("sindexName")) {
			model.setSindexName(jsonObject.getString("sindexName"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("viewType")) {
			model.setViewType(jsonObject.getInteger("viewType"));
		}
		if (jsonObject.containsKey("isMode")) {
			model.setIsMode(jsonObject.getString("isMode"));
		}
		if (jsonObject.containsKey("modeTableId")) {
			model.setModeTableId(jsonObject.getString("modeTableId"));
		}
		if (jsonObject.containsKey("isSystem")) {
			model.setIsSystem(jsonObject.getInteger("isSystem"));
		}
		if (jsonObject.containsKey("customData")) {
			model.setCustomData(jsonObject.getString("customData"));
		}
		if (jsonObject.containsKey("intSystemSelect")) {
			model.setIntSystemSelect(jsonObject.getInteger("intSystemSelect"));
		}
		if (jsonObject.containsKey("intUsed")) {
			model.setIntUsed(jsonObject.getInteger("intUsed"));
		}
		if (jsonObject.containsKey("intDel")) {
			model.setIntDel(jsonObject.getInteger("intDel"));
		}
		if (jsonObject.containsKey("typeTableName")) {
			model.setTypeTableName(jsonObject.getString("typeTableName"));
		}
		if (jsonObject.containsKey("intOperation")) {
			model.setIntOperation(jsonObject.getInteger("intOperation"));
		}
		if (jsonObject.containsKey("picFile")) {
			model.setPicFile(jsonObject.getString("picFile"));
		}
		if (jsonObject.containsKey("fileContent")) {
			//model.setFileContent(jsonObject.getString("fileContent"));
		}
		if (jsonObject.containsKey("intMuiFrm")) {
			model.setIntMuiFrm(jsonObject.getInteger("intMuiFrm"));
		}
		if (jsonObject.containsKey("intNoShow")) {
			model.setIntNoShow(jsonObject.getInteger("intNoShow"));
		}
		if (jsonObject.containsKey("typeBaseTable")) {
			model.setTypeBaseTable(jsonObject.getString("typeBaseTable"));
		}
		if (jsonObject.containsKey("typeIndex")) {
			model.setTypeIndex(jsonObject.getInteger("typeIndex"));
		}
		if (jsonObject.containsKey("gid")) {
			model.setGid(jsonObject.getString("gid"));
		}
		if (jsonObject.containsKey("fileName")) {
			model.setFileName(jsonObject.getString("fileName"));
		}
		if (jsonObject.containsKey("linkFileContent")) {
			model.setLinkFileContent(jsonObject.getString("linkFileContent"));
		}
		if (jsonObject.containsKey("linkFileName")) {
			model.setLinkFileName(jsonObject.getString("linkFileName"));
		}

		return model;
	}

	public static JSONObject toJsonObject(CellTreeDot model) {
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
		jsonObject.put("Level", model.getLevel());
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		jsonObject.put("nodeIco", model.getNodeIco());
		if (model.getSindexName() != null) {
			jsonObject.put("sindexName", model.getSindexName());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("viewType", model.getViewType());
		if (model.getIsMode() != null) {
			jsonObject.put("isMode", model.getIsMode());
		}
		if (model.getModeTableId() != null) {
			jsonObject.put("modeTableId", model.getModeTableId());
		}
		jsonObject.put("isSystem", model.getIsSystem());
		if (model.getCustomData() != null) {
			jsonObject.put("customData", model.getCustomData());
		}
		jsonObject.put("intSystemSelect", model.getIntSystemSelect());
		jsonObject.put("intUsed", model.getIntUsed());
		jsonObject.put("intDel", model.getIntDel());
		if (model.getTypeTableName() != null) {
			jsonObject.put("typeTableName", model.getTypeTableName());
		}
		jsonObject.put("intOperation", model.getIntOperation());
		if (model.getPicFile() != null) {
			jsonObject.put("picFile", model.getPicFile());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("intMuiFrm", model.getIntMuiFrm());
		jsonObject.put("intNoShow", model.getIntNoShow());
		if (model.getTypeBaseTable() != null) {
			jsonObject.put("typeBaseTable", model.getTypeBaseTable());
		}
		jsonObject.put("typeIndex", model.getTypeIndex());
		if (model.getGid() != null) {
			jsonObject.put("gid", model.getGid());
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getLinkFileContent() != null) {
			jsonObject.put("linkFileContent", model.getLinkFileContent());
		}
		if (model.getLinkFileName() != null) {
			jsonObject.put("linkFileName", model.getLinkFileName());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(CellTreeDot model) {
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
		jsonObject.put("Level", model.getLevel());
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		jsonObject.put("nodeIco", model.getNodeIco());
		if (model.getSindexName() != null) {
			jsonObject.put("sindexName", model.getSindexName());
		}
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("viewType", model.getViewType());
		if (model.getIsMode() != null) {
			jsonObject.put("isMode", model.getIsMode());
		}
		if (model.getModeTableId() != null) {
			jsonObject.put("modeTableId", model.getModeTableId());
		}
		jsonObject.put("isSystem", model.getIsSystem());
		if (model.getCustomData() != null) {
			jsonObject.put("customData", model.getCustomData());
		}
		jsonObject.put("intSystemSelect", model.getIntSystemSelect());
		jsonObject.put("intUsed", model.getIntUsed());
		jsonObject.put("intDel", model.getIntDel());
		if (model.getTypeTableName() != null) {
			jsonObject.put("typeTableName", model.getTypeTableName());
		}
		jsonObject.put("intOperation", model.getIntOperation());
		if (model.getPicFile() != null) {
			jsonObject.put("picFile", model.getPicFile());
		}
		if (model.getFileContent() != null) {
			jsonObject.put("fileContent", model.getFileContent());
		}
		jsonObject.put("intMuiFrm", model.getIntMuiFrm());
		jsonObject.put("intNoShow", model.getIntNoShow());
		if (model.getTypeBaseTable() != null) {
			jsonObject.put("typeBaseTable", model.getTypeBaseTable());
		}
		jsonObject.put("typeIndex", model.getTypeIndex());
		if (model.getGid() != null) {
			jsonObject.put("gid", model.getGid());
		}
		if (model.getFileName() != null) {
			jsonObject.put("fileName", model.getFileName());
		}
		if (model.getLinkFileContent() != null) {
			jsonObject.put("linkFileContent", model.getLinkFileContent());
		}
		if (model.getLinkFileName() != null) {
			jsonObject.put("linkFileName", model.getLinkFileName());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<CellTreeDot> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (CellTreeDot model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<CellTreeDot> arrayToList(JSONArray array) {
		java.util.List<CellTreeDot> list = new java.util.ArrayList<CellTreeDot>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			CellTreeDot model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private CellTreeDotJsonFactory() {

	}

}
