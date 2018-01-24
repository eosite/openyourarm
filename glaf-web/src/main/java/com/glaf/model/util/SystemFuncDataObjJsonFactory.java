package com.glaf.model.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.model.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class SystemFuncDataObjJsonFactory {

	public static SystemFuncDataObj jsonToObject(JSONObject jsonObject) {
		SystemFuncDataObj model = new SystemFuncDataObj();
		if (jsonObject.containsKey("sysDataObjId")) {
			model.setSysDataObjId(jsonObject.getString("sysDataObjId"));
		}
		if (jsonObject.containsKey("funcId")) {
			model.setFuncId(jsonObject.getString("funcId"));
		}
		if (jsonObject.containsKey("dataObjId")) {
			model.setDataObjId(jsonObject.getString("dataObjId"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
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

	public static JSONObject toJsonObject(SystemFuncDataObj model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("sysDataObjId", model.getSysDataObjId());
		jsonObject.put("_sysDataObjId_", model.getSysDataObjId());
		jsonObject.put("funcId", model.getFuncId());
		jsonObject.put("dataObjId", model.getDataObjId());
		jsonObject.put("type", model.getType());
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
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}

	public static ObjectNode toObjectNode(SystemFuncDataObj model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("sysDataObjId", model.getSysDataObjId());
		jsonObject.put("_sysDataObjId_", model.getSysDataObjId());
		jsonObject.put("funcId", model.getFuncId());
		jsonObject.put("dataObjId", model.getDataObjId());
		jsonObject.put("type", model.getType());
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
		jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<SystemFuncDataObj> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SystemFuncDataObj model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SystemFuncDataObj> arrayToList(JSONArray array) {
		java.util.List<SystemFuncDataObj> list = new java.util.ArrayList<SystemFuncDataObj>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SystemFuncDataObj model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SystemFuncDataObjJsonFactory() {

	}

}
