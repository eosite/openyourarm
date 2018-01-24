package com.glaf.base.modules.sys.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.base.modules.sys.model.*;

/**
 * 
 * JSON工厂类
 *
 */
public class DeptRoleJsonFactory {

	public static java.util.List<DeptRole> arrayToList(JSONArray array) {
		java.util.List<DeptRole> list = new java.util.ArrayList<DeptRole>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DeptRole model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	public static DeptRole jsonToObject(JSONObject jsonObject) {
		DeptRole model = new DeptRole();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("deptId")) {
			model.setDeptId(jsonObject.getLong("deptId"));
		}
		if (jsonObject.containsKey("deptFlag")) {
			model.setDeptFlag(jsonObject.getInteger("deptFlag"));
		}
		if (jsonObject.containsKey("menuFlag")) {
			model.setMenuFlag(jsonObject.getInteger("menuFlag"));
		}
		if (jsonObject.containsKey("roleId")) {
			model.setRoleId(jsonObject.getLong("roleId"));
		}
		if (jsonObject.containsKey("isPropagationAllowed")) {
			model.setIsPropagationAllowed(jsonObject.getString("isPropagationAllowed"));
		}

		return model;
	}

	public static JSONArray listToArray(java.util.List<DeptRole> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DeptRole model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static JSONObject toJsonObject(DeptRole model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("deptId", model.getDeptId());
		jsonObject.put("deptFlag", model.getDeptFlag());
		jsonObject.put("menuFlag", model.getMenuFlag());
		jsonObject.put("roleId", model.getRoleId());
		if (model.getIsPropagationAllowed() != null) {
			jsonObject.put("isPropagationAllowed", model.getIsPropagationAllowed());
		}
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DeptRole model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		jsonObject.put("deptId", model.getDeptId());
		jsonObject.put("deptFlag", model.getDeptFlag());
		jsonObject.put("menuFlag", model.getMenuFlag());
		jsonObject.put("roleId", model.getRoleId());
		if (model.getIsPropagationAllowed() != null) {
			jsonObject.put("isPropagationAllowed", model.getIsPropagationAllowed());
		}
		return jsonObject;
	}

	private DeptRoleJsonFactory() {

	}

}
