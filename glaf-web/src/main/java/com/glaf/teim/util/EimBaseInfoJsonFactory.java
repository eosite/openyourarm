package com.glaf.teim.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.teim.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class EimBaseInfoJsonFactory {

	public static EimBaseInfo jsonToObject(JSONObject jsonObject) {
		EimBaseInfo model = new EimBaseInfo();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("ip")) {
			model.setIp(jsonObject.getString("ip"));
		}
		if (jsonObject.containsKey("host")) {
			model.setHost(jsonObject.getString("host"));
		}
		if (jsonObject.containsKey("secret")) {
			model.setSecret(jsonObject.getString("secret"));
		}
		if (jsonObject.containsKey("paasId")) {
			model.setPaasId(jsonObject.getString("paasId"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

		return model;
	}

	public static JSONObject toJsonObject(EimBaseInfo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		}
		if (model.getHost() != null) {
			jsonObject.put("host", model.getHost());
		}
		if (model.getSecret() != null) {
			jsonObject.put("secret", model.getSecret());
		}
		if (model.getPaasId() != null) {
			jsonObject.put("paasId", model.getPaasId());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		if (model.getDeleteFlag() != null) {
			jsonObject.put("deleteFlag", model.getDeleteFlag());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(EimBaseInfo model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		}
		if (model.getHost() != null) {
			jsonObject.put("host", model.getHost());
		}
		if (model.getSecret() != null) {
			jsonObject.put("secret", model.getSecret());
		}
		if (model.getPaasId() != null) {
			jsonObject.put("paasId", model.getPaasId());
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		}
		if (model.getUpdateTime() != null) {
			jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
			jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
		}
		if (model.getDeleteFlag() != null) {
			jsonObject.put("deleteFlag", model.getDeleteFlag());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<EimBaseInfo> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (EimBaseInfo model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<EimBaseInfo> arrayToList(JSONArray array) {
		java.util.List<EimBaseInfo> list = new java.util.ArrayList<EimBaseInfo>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			EimBaseInfo model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private EimBaseInfoJsonFactory() {

	}

}
