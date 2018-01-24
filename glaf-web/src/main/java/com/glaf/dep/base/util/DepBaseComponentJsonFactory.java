package com.glaf.dep.base.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.base.domain.*;

/**
 * 
 * JSON工厂类
 * 
 */
public class DepBaseComponentJsonFactory {

	public static DepBaseComponent jsonToObject(JSONObject jsonObject) {
		DepBaseComponent model = new DepBaseComponent();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDateTime")) {
			model.setCreateDateTime(jsonObject.getDate("createDateTime"));
		}
		if (jsonObject.containsKey("modifier")) {
			model.setModifier(jsonObject.getString("modifier"));
		}
		if (jsonObject.containsKey("modifyDateTime")) {
			model.setModifyDateTime(jsonObject.getDate("modifyDateTime"));
		}
		if (jsonObject.containsKey("delFlag")) {
			model.setDelFlag(jsonObject.getString("delFlag"));
		}
		if (jsonObject.containsKey("htmlTemplate")) {
			model.setHtmlTemplate(jsonObject.getString("htmlTemplate"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DepBaseComponent model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDateTime() != null) {
			jsonObject.put("createDateTime",
					DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_date",
					DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_datetime",
					DateUtils.getDateTime(model.getCreateDateTime()));
		}
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		}
		if (model.getModifyDateTime() != null) {
			jsonObject.put("modifyDateTime",
					DateUtils.getDate(model.getModifyDateTime()));
			jsonObject.put("modifyDateTime_date",
					DateUtils.getDate(model.getModifyDateTime()));
			jsonObject.put("modifyDateTime_datetime",
					DateUtils.getDateTime(model.getModifyDateTime()));
		}
		if (model.getDelFlag() != null) {
			jsonObject.put("delFlag", model.getDelFlag());
		}
		if (model.getHtmlTemplate() != null) {
			jsonObject.put("htmlTemplate", model.getHtmlTemplate());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DepBaseComponent model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		}
		if (model.getCreateDateTime() != null) {
			jsonObject.put("createDateTime",
					DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_date",
					DateUtils.getDate(model.getCreateDateTime()));
			jsonObject.put("createDateTime_datetime",
					DateUtils.getDateTime(model.getCreateDateTime()));
		}
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		}
		if (model.getModifyDateTime() != null) {
			jsonObject.put("modifyDateTime",
					DateUtils.getDate(model.getModifyDateTime()));
			jsonObject.put("modifyDateTime_date",
					DateUtils.getDate(model.getModifyDateTime()));
			jsonObject.put("modifyDateTime_datetime",
					DateUtils.getDateTime(model.getModifyDateTime()));
		}
		if (model.getDelFlag() != null) {
			jsonObject.put("delFlag", model.getDelFlag());
		}
		if (model.getHtmlTemplate() != null) {
			jsonObject.put("htmlTemplate", model.getHtmlTemplate());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DepBaseComponent> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBaseComponent model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBaseComponent> arrayToList(JSONArray array) {
		java.util.List<DepBaseComponent> list = new java.util.ArrayList<DepBaseComponent>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBaseComponent model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DepBaseComponentJsonFactory() {

	}

}
