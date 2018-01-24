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
public class DepBaseCategoryJsonFactory {

	public static DepBaseCategory jsonToObject(JSONObject jsonObject) {
		DepBaseCategory model = new DepBaseCategory();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("treeId")) {
			model.setTreeId(jsonObject.getString("treeId"));
		}
		if (jsonObject.containsKey("expandFlag")) {
			model.setExpandFlag(jsonObject.getString("expandFlag"));
		}
		if (jsonObject.containsKey("pid")) {
			model.setPid(jsonObject.getLong("pid"));
		}
		if (jsonObject.containsKey("orderNo")) {
			model.setOrderNo(jsonObject.getInteger("orderNo"));
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
		if (jsonObject.containsKey("childrenNum")) {
			model.setChildrenNum(jsonObject.getInteger("childrenNum"));
		}

		return model;
	}

	public static JSONObject toJsonObject(DepBaseCategory model) {
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
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
		}
		if (model.getExpandFlag() != null) {
			jsonObject.put("expandFlag", model.getExpandFlag());
		}
		jsonObject.put("pid", model.getPid());
		jsonObject.put("orderNo", model.getOrderNo());
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
		if (model.getChildrenNum() != null) {
			jsonObject.put("childrenNum", model.getChildrenNum());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DepBaseCategory model) {
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
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
		}
		if (model.getExpandFlag() != null) {
			jsonObject.put("expandFlag", model.getExpandFlag());
		}
		jsonObject.put("pid", model.getPid());
		jsonObject.put("orderNo", model.getOrderNo());
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
		if (model.getChildrenNum() != null) {
			jsonObject.put("childrenNum", model.getChildrenNum());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DepBaseCategory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBaseCategory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBaseCategory> arrayToList(JSONArray array) {
		java.util.List<DepBaseCategory> list = new java.util.ArrayList<DepBaseCategory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBaseCategory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DepBaseCategoryJsonFactory() {

	}

}
