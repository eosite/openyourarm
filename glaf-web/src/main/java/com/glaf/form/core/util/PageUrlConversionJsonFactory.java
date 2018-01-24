package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.form.core.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class PageUrlConversionJsonFactory {

	public static PageUrlConversion jsonToObject(JSONObject jsonObject) {
		PageUrlConversion model = new PageUrlConversion();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("srcUrl")) {
			model.setSrcUrl(jsonObject.getString("srcUrl"));
		}
		if (jsonObject.containsKey("destUrl")) {
			model.setDestUrl(jsonObject.getString("destUrl"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		return model;
	}

	public static JSONObject toJsonObject(PageUrlConversion model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getSrcUrl() != null) {
			jsonObject.put("srcUrl", model.getSrcUrl());
		}
		if (model.getDestUrl() != null) {
			jsonObject.put("destUrl", model.getDestUrl());
		}
		jsonObject.put("locked", model.getLocked());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(PageUrlConversion model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getSrcUrl() != null) {
			jsonObject.put("srcUrl", model.getSrcUrl());
		}
		if (model.getDestUrl() != null) {
			jsonObject.put("destUrl", model.getDestUrl());
		}
		jsonObject.put("locked", model.getLocked());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<PageUrlConversion> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (PageUrlConversion model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<PageUrlConversion> arrayToList(JSONArray array) {
		java.util.List<PageUrlConversion> list = new java.util.ArrayList<PageUrlConversion>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			PageUrlConversion model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private PageUrlConversionJsonFactory() {

	}

}
