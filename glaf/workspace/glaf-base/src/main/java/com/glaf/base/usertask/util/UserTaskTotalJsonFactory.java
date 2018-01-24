package com.glaf.base.usertask.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.base.usertask.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class UserTaskTotalJsonFactory {

	public static UserTaskTotal jsonToObject(JSONObject jsonObject) {
		UserTaskTotal model = new UserTaskTotal();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("databaseId")) {
			model.setDatabaseId(jsonObject.getLong("databaseId"));
		}
		if (jsonObject.containsKey("total")) {
			model.setTotal(jsonObject.getInteger("total"));
		}
		if (jsonObject.containsKey("finished")) {
			model.setFinished(jsonObject.getInteger("finished"));
		}
		if (jsonObject.containsKey("pending")) {
			model.setPending(jsonObject.getInteger("pending"));
		}
		if (jsonObject.containsKey("quantity1")) {
			model.setQuantity1(jsonObject.getInteger("quantity1"));
		}
		if (jsonObject.containsKey("quantity2")) {
			model.setQuantity2(jsonObject.getInteger("quantity2"));
		}
		if (jsonObject.containsKey("quantity3")) {
			model.setQuantity3(jsonObject.getInteger("quantity3"));
		}
		if (jsonObject.containsKey("quantity4")) {
			model.setQuantity4(jsonObject.getInteger("quantity4"));
		}
		if (jsonObject.containsKey("quantity5")) {
			model.setQuantity5(jsonObject.getInteger("quantity5"));
		}
		if (jsonObject.containsKey("quantity6")) {
			model.setQuantity6(jsonObject.getInteger("quantity6"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

		return model;
	}

	public static JSONObject toJsonObject(UserTaskTotal model) {
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
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("total", model.getTotal());
		jsonObject.put("finished", model.getFinished());
		jsonObject.put("pending", model.getPending());
		jsonObject.put("quantity1", model.getQuantity1());
		jsonObject.put("quantity2", model.getQuantity2());
		jsonObject.put("quantity3", model.getQuantity3());
		jsonObject.put("quantity4", model.getQuantity4());
		jsonObject.put("quantity5", model.getQuantity5());
		jsonObject.put("quantity6", model.getQuantity6());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(UserTaskTotal model) {
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
		jsonObject.put("databaseId", model.getDatabaseId());
		jsonObject.put("total", model.getTotal());
		jsonObject.put("finished", model.getFinished());
		jsonObject.put("pending", model.getPending());
		jsonObject.put("quantity1", model.getQuantity1());
		jsonObject.put("quantity2", model.getQuantity2());
		jsonObject.put("quantity3", model.getQuantity3());
		jsonObject.put("quantity4", model.getQuantity4());
		jsonObject.put("quantity5", model.getQuantity5());
		jsonObject.put("quantity6", model.getQuantity6());
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		}
		if (model.getCreateTime() != null) {
			jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
			jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
		}
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		}
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<UserTaskTotal> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (UserTaskTotal model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<UserTaskTotal> arrayToList(JSONArray array) {
		java.util.List<UserTaskTotal> list = new java.util.ArrayList<UserTaskTotal>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			UserTaskTotal model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private UserTaskTotalJsonFactory() {

	}

}
