package com.glaf.isdp.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.isdp.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class RUtabletreeJsonFactory {

	public static RUtabletree jsonToObject(JSONObject jsonObject) {
		RUtabletree model = new RUtabletree();
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
		if (jsonObject.containsKey("nlevel")) {
			model.setNlevel(jsonObject.getInteger("nlevel"));
		}
		if (jsonObject.containsKey("nodeico")) {
			model.setNodeico(jsonObject.getInteger("nodeico"));
		}
		if (jsonObject.containsKey("listno")) {
			model.setListno(jsonObject.getInteger("listno"));
		}
		if (jsonObject.containsKey("tabletype")) {
			model.setTabletype(jsonObject.getInteger("tabletype"));
		}
		if (jsonObject.containsKey("intdel")) {
			model.setIntdel(jsonObject.getInteger("intdel"));
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
		if (jsonObject.containsKey("menuindex")) {
			model.setMenuindex(jsonObject.getInteger("menuindex"));
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

	public static JSONObject toJsonObject(RUtabletree model) {
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
		jsonObject.put("nodeico", model.getNodeico());
		jsonObject.put("listno", model.getListno());
		jsonObject.put("tabletype", model.getTabletype());
		jsonObject.put("intdel", model.getIntdel());
		if (model.getBusiessId() != null) {
			jsonObject.put("busiessId", model.getBusiessId());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		jsonObject.put("menuindex", model.getMenuindex());
		jsonObject.put("domainIndex", model.getDomainIndex());
		jsonObject.put("winWidth", model.getWinWidth());
		jsonObject.put("winHeight", model.getWinHeight());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(RUtabletree model) {
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
		jsonObject.put("nodeico", model.getNodeico());
		jsonObject.put("listno", model.getListno());
		jsonObject.put("tabletype", model.getTabletype());
		jsonObject.put("intdel", model.getIntdel());
		if (model.getBusiessId() != null) {
			jsonObject.put("busiessId", model.getBusiessId());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getNum() != null) {
			jsonObject.put("num", model.getNum());
		}
		jsonObject.put("menuindex", model.getMenuindex());
		jsonObject.put("domainIndex", model.getDomainIndex());
		jsonObject.put("winWidth", model.getWinWidth());
		jsonObject.put("winHeight", model.getWinHeight());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<RUtabletree> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (RUtabletree model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<RUtabletree> arrayToList(JSONArray array) {
		java.util.List<RUtabletree> list = new java.util.ArrayList<RUtabletree>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			RUtabletree model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private RUtabletreeJsonFactory() {

	}

}
