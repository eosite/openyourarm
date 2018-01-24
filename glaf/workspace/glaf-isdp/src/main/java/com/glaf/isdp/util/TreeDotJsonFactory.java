package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.TreeDot;

/**
 * 
 * JSON工厂类
 *
 */
public class TreeDotJsonFactory {

	public static TreeDot jsonToObject(JSONObject jsonObject) {
		TreeDot model = new TreeDot();
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
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("sindexName")) {
			model.setSindexName(jsonObject.getString("sindexName"));
		}
		if (jsonObject.containsKey("nodeIco")) {
			model.setNodeIco(jsonObject.getInteger("nodeIco"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("menuId")) {
			model.setMenuId(jsonObject.getInteger("menuId"));
		}
		if (jsonObject.containsKey("isEnd")) {
			model.setIsEnd(jsonObject.getInteger("isEnd"));
		}
		if (jsonObject.containsKey("sysMenuId")) {
			model.setSysMenuId(jsonObject.getString("sysMenuId"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		if (jsonObject.containsKey("fileNumId")) {
			model.setFileNumId(jsonObject.getString("fileNumId"));
		}
		if (jsonObject.containsKey("fileNumId2")) {
			model.setFileNumId2(jsonObject.getString("fileNumId2"));
		}
		if (jsonObject.containsKey("projIndex")) {
			model.setProjIndex(jsonObject.getInteger("projIndex"));
		}
		if (jsonObject.containsKey("domainIndex")) {
			model.setDomainIndex(jsonObject.getInteger("domainIndex"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TreeDot model) {
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
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getSindexName() != null) {
			jsonObject.put("sindexName", model.getSindexName());
		}
		jsonObject.put("nodeIco", model.getNodeIco());
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("menuId", model.getMenuId());
		jsonObject.put("isEnd", model.getIsEnd());
		if (model.getSysMenuId() != null) {
			jsonObject.put("sysMenuId", model.getSysMenuId());
		}
		jsonObject.put("type", model.getType());
		if (model.getFileNumId() != null) {
			jsonObject.put("fileNumId", model.getFileNumId());
		}
		if (model.getFileNumId2() != null) {
			jsonObject.put("fileNumId2", model.getFileNumId2());
		}
		jsonObject.put("projIndex", model.getProjIndex());
		jsonObject.put("domainIndex", model.getDomainIndex());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TreeDot model) {
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
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getSindexName() != null) {
			jsonObject.put("sindexName", model.getSindexName());
		}
		jsonObject.put("nodeIco", model.getNodeIco());
		jsonObject.put("listNo", model.getListNo());
		jsonObject.put("menuId", model.getMenuId());
		jsonObject.put("isEnd", model.getIsEnd());
		if (model.getSysMenuId() != null) {
			jsonObject.put("sysMenuId", model.getSysMenuId());
		}
		jsonObject.put("type", model.getType());
		if (model.getFileNumId() != null) {
			jsonObject.put("fileNumId", model.getFileNumId());
		}
		if (model.getFileNumId2() != null) {
			jsonObject.put("fileNumId2", model.getFileNumId2());
		}
		jsonObject.put("projIndex", model.getProjIndex());
		jsonObject.put("domainIndex", model.getDomainIndex());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TreeDot> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TreeDot model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TreeDot> arrayToList(JSONArray array) {
		java.util.List<TreeDot> list = new java.util.ArrayList<TreeDot>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TreeDot model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TreeDotJsonFactory() {

	}

}
