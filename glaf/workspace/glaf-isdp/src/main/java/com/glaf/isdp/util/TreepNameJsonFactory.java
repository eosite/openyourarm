package com.glaf.isdp.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.isdp.domain.TreepName;

/**
 * 
 * JSON工厂类
 *
 */
public class TreepNameJsonFactory {

	public static TreepName jsonToObject(JSONObject jsonObject) {
		TreepName model = new TreepName();
		if (jsonObject.containsKey("indexId")) {
			model.setIndexId(jsonObject.getInteger("indexId"));
		}
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("num")) {
			model.setNum(jsonObject.getString("num"));
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
		if (jsonObject.containsKey("showId")) {
			model.setShowId(jsonObject.getInteger("showId"));
		}
		if (jsonObject.containsKey("ruleId")) {
			model.setRuleId(jsonObject.getString("ruleId"));
		}
		if (jsonObject.containsKey("nodeIco")) {
			model.setNodeIco(jsonObject.getInteger("nodeIco"));
		}
		if (jsonObject.containsKey("fruleId")) {
			model.setFruleId(jsonObject.getString("fruleId"));
		}
		if (jsonObject.containsKey("wcompany")) {
			model.setWcompany(jsonObject.getString("wcompany"));
		}
		if (jsonObject.containsKey("listNo")) {
			model.setListNo(jsonObject.getInteger("listNo"));
		}
		if (jsonObject.containsKey("sysId")) {
			model.setSysId(jsonObject.getString("sysId"));
		}
		if (jsonObject.containsKey("domainIndex")) {
			model.setDomainIndex(jsonObject.getInteger("domainIndex"));
		}

		return model;
	}

	public static JSONObject toJsonObject(TreepName model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
			jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		jsonObject.put("parentId", model.getParentId());
		if (model.getIndexName() != null) {
			jsonObject.put("indexName", model.getIndexName());
		}
		jsonObject.put("Level", model.getLevel());
		jsonObject.put("showId", model.getShowId());
		if (model.getRuleId() != null) {
			jsonObject.put("ruleId", model.getRuleId());
		}
		jsonObject.put("nodeIco", model.getNodeIco());
		if (model.getFruleId() != null) {
			jsonObject.put("fruleId", model.getFruleId());
		}
		if (model.getWcompany() != null) {
			jsonObject.put("wcompany", model.getWcompany());
		}
		jsonObject.put("listNo", model.getListNo());
		if (model.getSysId() != null) {
			jsonObject.put("sysId", model.getSysId());
		}
		jsonObject.put("domainIndex", model.getDomainIndex());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(TreepName model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("indexId", model.getIndexId());
		jsonObject.put("_indexId_", model.getIndexId());
		if (model.getId() != null) {
			jsonObject.put("id", model.getId());
			jsonObject.put("_id_", model.getId());
			jsonObject.put("_oid_", model.getId());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		jsonObject.put("parentId", model.getParentId());
		if (model.getIndexName() != null) {
			jsonObject.put("indexName", model.getIndexName());
		}
		jsonObject.put("Level", model.getLevel());
		jsonObject.put("showId", model.getShowId());
		if (model.getRuleId() != null) {
			jsonObject.put("ruleId", model.getRuleId());
		}
		jsonObject.put("nodeIco", model.getNodeIco());
		if (model.getFruleId() != null) {
			jsonObject.put("fruleId", model.getFruleId());
		}
		if (model.getWcompany() != null) {
			jsonObject.put("wcompany", model.getWcompany());
		}
		jsonObject.put("listNo", model.getListNo());
		if (model.getSysId() != null) {
			jsonObject.put("sysId", model.getSysId());
		}
		jsonObject.put("domainIndex", model.getDomainIndex());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<TreepName> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (TreepName model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<TreepName> arrayToList(JSONArray array) {
		java.util.List<TreepName> list = new java.util.ArrayList<TreepName>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			TreepName model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private TreepNameJsonFactory() {

	}

}
