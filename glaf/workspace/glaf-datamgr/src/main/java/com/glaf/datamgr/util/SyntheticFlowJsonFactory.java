package com.glaf.datamgr.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.*;

/**
 * 
 * JSON工厂类
 *
 */
public class SyntheticFlowJsonFactory {

	public static SyntheticFlow jsonToObject(JSONObject jsonObject) {
		SyntheticFlow model = new SyntheticFlow();
		if (jsonObject.containsKey("id")) {
			model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("currentStep")) {
			model.setCurrentStep(jsonObject.getString("currentStep"));
		}
		if (jsonObject.containsKey("currentType")) {
			model.setCurrentType(jsonObject.getString("currentType"));
		}
		if (jsonObject.containsKey("previousStep")) {
			model.setPreviousStep(jsonObject.getString("previousStep"));
		}
		if (jsonObject.containsKey("previousType")) {
			model.setPreviousType(jsonObject.getString("previousType"));
		}
		if (jsonObject.containsKey("nextStep")) {
			model.setNextStep(jsonObject.getString("nextStep"));
		}
		if (jsonObject.containsKey("nextType")) {
			model.setNextType(jsonObject.getString("nextType"));
		}
		if (jsonObject.containsKey("sort")) {
			model.setSort(jsonObject.getInteger("sort"));
		}

		return model;
	}

	public static JSONObject toJsonObject(SyntheticFlow model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCurrentStep() != null) {
			jsonObject.put("currentStep", model.getCurrentStep());
		}
		if (model.getCurrentType() != null) {
			jsonObject.put("currentType", model.getCurrentType());
		}
		if (model.getPreviousStep() != null) {
			jsonObject.put("previousStep", model.getPreviousStep());
		}
		if (model.getPreviousType() != null) {
			jsonObject.put("previousType", model.getPreviousType());
		}
		if (model.getNextStep() != null) {
			jsonObject.put("nextStep", model.getNextStep());
		}
		if (model.getNextType() != null) {
			jsonObject.put("nextType", model.getNextType());
		}
		jsonObject.put("sort", model.getSort());
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

	public static ObjectNode toObjectNode(SyntheticFlow model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getCurrentStep() != null) {
			jsonObject.put("currentStep", model.getCurrentStep());
		}
		if (model.getCurrentType() != null) {
			jsonObject.put("currentType", model.getCurrentType());
		}
		if (model.getPreviousStep() != null) {
			jsonObject.put("previousStep", model.getPreviousStep());
		}
		if (model.getPreviousType() != null) {
			jsonObject.put("previousType", model.getPreviousType());
		}
		if (model.getNextStep() != null) {
			jsonObject.put("nextStep", model.getNextStep());
		}
		if (model.getNextType() != null) {
			jsonObject.put("nextType", model.getNextType());
		}
		jsonObject.put("sort", model.getSort());
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

	public static JSONArray listToArray(java.util.List<SyntheticFlow> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SyntheticFlow model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SyntheticFlow> arrayToList(JSONArray array) {
		java.util.List<SyntheticFlow> list = new java.util.ArrayList<SyntheticFlow>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SyntheticFlow model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private SyntheticFlowJsonFactory() {

	}

}
