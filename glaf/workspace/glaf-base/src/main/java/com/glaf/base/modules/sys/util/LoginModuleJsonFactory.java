package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.sys.model.LoginModule;

/**
 * 
 * JSON工厂类
 *
 */
public class LoginModuleJsonFactory {

	public static LoginModule jsonToObject(JSONObject jsonObject) {
		LoginModule model = new LoginModule();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("title")) {
			model.setTitle(jsonObject.getString("title"));
		}
		if (jsonObject.containsKey("content")) {
			model.setContent(jsonObject.getString("content"));
		}
		if (jsonObject.containsKey("token")) {
			model.setToken(jsonObject.getString("token"));
		}
		if (jsonObject.containsKey("loginUrl")) {
			model.setLoginUrl(jsonObject.getString("loginUrl"));
		}
		if (jsonObject.containsKey("loginUserId")) {
			model.setLoginUserId(jsonObject.getString("loginUserId"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("systemCode")) {
			model.setSystemCode(jsonObject.getString("systemCode"));
		}
		if (jsonObject.containsKey("serverId")) {
			model.setServerId(jsonObject.getLong("serverId"));
		}
		if (jsonObject.containsKey("timeLive")) {
			model.setTimeLive(jsonObject.getInteger("timeLive"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("locked")) {
			model.setLocked(jsonObject.getInteger("locked"));
		}

		return model;
	}

	public static JSONObject toJsonObject(LoginModule model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getToken() != null) {
			jsonObject.put("token", model.getToken());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getSystemCode() != null) {
			jsonObject.put("systemCode", model.getSystemCode());
		}
		if (model.getLoginUrl() != null) {
			jsonObject.put("loginUrl", model.getLoginUrl());
		}
		if (model.getLoginUserId() != null) {
			jsonObject.put("loginUserId", model.getLoginUserId());
		}
		if (model.getServerId() != null) {
			jsonObject.put("serverId", model.getServerId());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("timeLive", model.getTimeLive());
		jsonObject.put("locked", model.getLocked());

		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(LoginModule model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTitle() != null) {
			jsonObject.put("title", model.getTitle());
		}
		if (model.getContent() != null) {
			jsonObject.put("content", model.getContent());
		}
		if (model.getToken() != null) {
			jsonObject.put("token", model.getToken());
		}
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		}
		if (model.getSystemCode() != null) {
			jsonObject.put("systemCode", model.getSystemCode());
		}
		if (model.getLoginUrl() != null) {
			jsonObject.put("loginUrl", model.getLoginUrl());
		}
		if (model.getLoginUserId() != null) {
			jsonObject.put("loginUserId", model.getLoginUserId());
		}
		if (model.getServerId() != null) {
			jsonObject.put("serverId", model.getServerId());
		}
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		jsonObject.put("timeLive", model.getTimeLive());
		jsonObject.put("locked", model.getLocked());

		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<LoginModule> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (LoginModule model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<LoginModule> arrayToList(JSONArray array) {
		java.util.List<LoginModule> list = new java.util.ArrayList<LoginModule>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			LoginModule model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private LoginModuleJsonFactory() {

	}

}
