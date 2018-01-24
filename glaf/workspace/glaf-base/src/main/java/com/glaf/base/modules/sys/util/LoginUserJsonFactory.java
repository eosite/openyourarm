package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.sys.model.LoginUser;

/**
 * 
 * JSON工厂类
 *
 */
public class LoginUserJsonFactory {

	public static LoginUser jsonToObject(JSONObject jsonObject) {
		LoginUser model = new LoginUser();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("loginId")) {
			model.setLoginId(jsonObject.getString("loginId"));
		}
		if (jsonObject.containsKey("passwordType")) {
			model.setPasswordType(jsonObject.getString("passwordType"));
		}
		if (jsonObject.containsKey("systemCode")) {
			model.setSystemCode(jsonObject.getString("systemCode"));
		}
		if (jsonObject.containsKey("organization")) {
			model.setOrganization(jsonObject.getString("organization"));
		}
		if (jsonObject.containsKey("department")) {
			model.setDepartment(jsonObject.getString("department"));
		}
		if (jsonObject.containsKey("position")) {
			model.setPosition(jsonObject.getString("position"));
		}
		if (jsonObject.containsKey("mail")) {
			model.setMail(jsonObject.getString("mail"));
		}
		if (jsonObject.containsKey("mobile")) {
			model.setMobile(jsonObject.getString("mobile"));
		}
		if (jsonObject.containsKey("timeLive")) {
			model.setTimeLive(jsonObject.getInteger("timeLive"));
		}
		if (jsonObject.containsKey("loginTime")) {
			model.setLoginTime(jsonObject.getDate("loginTime"));
		}
		if (jsonObject.containsKey("attribute")) {
			model.setAttribute(jsonObject.getString("attribute"));
		}

		return model;
	}

	public static JSONObject toJsonObject(LoginUser model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getLoginId() != null) {
			jsonObject.put("loginId", model.getLoginId());
		}

		if (model.getSystemCode() != null) {
			jsonObject.put("systemCode", model.getSystemCode());
		}
		if (model.getOrganization() != null) {
			jsonObject.put("organization", model.getOrganization());
		}
		if (model.getDepartment() != null) {
			jsonObject.put("department", model.getDepartment());
		}
		if (model.getPosition() != null) {
			jsonObject.put("position", model.getPosition());
		}
		if (model.getMail() != null) {
			jsonObject.put("mail", model.getMail());
		}
		if (model.getMobile() != null) {
			jsonObject.put("mobile", model.getMobile());
		}
		jsonObject.put("timeLive", model.getTimeLive());
		if (model.getLoginTime() != null) {
			jsonObject.put("loginTime", DateUtils.getDate(model.getLoginTime()));
			jsonObject.put("loginTime_date", DateUtils.getDate(model.getLoginTime()));
			jsonObject.put("loginTime_datetime", DateUtils.getDateTime(model.getLoginTime()));
		}
		if (model.getAttribute() != null) {
			jsonObject.put("attribute", model.getAttribute());
		}
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

	public static ObjectNode toObjectNode(LoginUser model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		}
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		}
		if (model.getLoginId() != null) {
			jsonObject.put("loginId", model.getLoginId());
		}

		if (model.getSystemCode() != null) {
			jsonObject.put("systemCode", model.getSystemCode());
		}
		if (model.getOrganization() != null) {
			jsonObject.put("organization", model.getOrganization());
		}
		if (model.getDepartment() != null) {
			jsonObject.put("department", model.getDepartment());
		}
		if (model.getPosition() != null) {
			jsonObject.put("position", model.getPosition());
		}
		if (model.getMail() != null) {
			jsonObject.put("mail", model.getMail());
		}
		if (model.getMobile() != null) {
			jsonObject.put("mobile", model.getMobile());
		}
		jsonObject.put("timeLive", model.getTimeLive());
		if (model.getLoginTime() != null) {
			jsonObject.put("loginTime", DateUtils.getDate(model.getLoginTime()));
			jsonObject.put("loginTime_date", DateUtils.getDate(model.getLoginTime()));
			jsonObject.put("loginTime_datetime", DateUtils.getDateTime(model.getLoginTime()));
		}
		if (model.getAttribute() != null) {
			jsonObject.put("attribute", model.getAttribute());
		}
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

	public static JSONArray listToArray(java.util.List<LoginUser> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (LoginUser model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<LoginUser> arrayToList(JSONArray array) {
		java.util.List<LoginUser> list = new java.util.ArrayList<LoginUser>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			LoginUser model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private LoginUserJsonFactory() {

	}

}
