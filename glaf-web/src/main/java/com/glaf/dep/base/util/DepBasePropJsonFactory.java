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
public class DepBasePropJsonFactory {

	public static DepBaseProp jsonToObject(JSONObject jsonObject) {
		DepBaseProp model = new DepBaseProp();
		if (jsonObject.containsKey("ruleId")) {
			model.setRuleId(jsonObject.getString("ruleId"));
		}
		if (jsonObject.containsKey("ruleCode")) {
			model.setRuleCode(jsonObject.getString("ruleCode"));
		}
		if (jsonObject.containsKey("ruleName")) {
			model.setRuleName(jsonObject.getString("ruleName"));
		}
		if (jsonObject.containsKey("ruleDesc")) {
			model.setRuleDesc(jsonObject.getString("ruleDesc"));
		}
		if (jsonObject.containsKey("sysCategory")) {
			model.setSysCategory(jsonObject.getString("sysCategory"));
		}
		if (jsonObject.containsKey("useCategory")) {
			model.setUseCategory(jsonObject.getString("useCategory"));
		}
		if (jsonObject.containsKey("openFlag")) {
			model.setOpenFlag(jsonObject.getString("openFlag"));
		}
		if (jsonObject.containsKey("orderNo")) {
			model.setOrderNo(jsonObject.getInteger("orderNo"));
		}
		if (jsonObject.containsKey("readOnly")) {
			model.setReadOnly(jsonObject.getString("readOnly"));
		}
		if (jsonObject.containsKey("repeatFlag")) {
			model.setRepeatFlag(jsonObject.getString("repeatFlag"));
		}
		if (jsonObject.containsKey("notNull")) {
			model.setNotNull(jsonObject.getString("notNull"));
		}
		if (jsonObject.containsKey("inputType")) {
			model.setInputType(jsonObject.getString("inputType"));
		}
		if (jsonObject.containsKey("defaultVal")) {
			model.setDefaultVal(jsonObject.getString("defaultVal"));
		}
		if (jsonObject.containsKey("extJson")) {
			model.setExtJson(jsonObject.getString("extJson"));
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

		return model;
	}

	public static JSONObject toJsonObject(DepBaseProp model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("ruleId", model.getRuleId());
		jsonObject.put("_ruleId_", model.getRuleId());
		if (model.getRuleCode() != null) {
			jsonObject.put("ruleCode", model.getRuleCode());
		}
		if (model.getRuleName() != null) {
			jsonObject.put("ruleName", model.getRuleName());
		}
		if (model.getRuleDesc() != null) {
			jsonObject.put("ruleDesc", model.getRuleDesc());
		}
		if (model.getSysCategory() != null) {
			jsonObject.put("sysCategory", model.getSysCategory());
		}
		if (model.getUseCategory() != null) {
			jsonObject.put("useCategory", model.getUseCategory());
		}
		if (model.getOpenFlag() != null) {
			jsonObject.put("openFlag", model.getOpenFlag());
		}
		jsonObject.put("orderNo", model.getOrderNo());
		if (model.getReadOnly() != null) {
			jsonObject.put("readOnly", model.getReadOnly());
		}
		if (model.getRepeatFlag() != null) {
			jsonObject.put("repeatFlag", model.getRepeatFlag());
		}
		if (model.getNotNull() != null) {
			jsonObject.put("notNull", model.getNotNull());
		}
		if (model.getInputType() != null) {
			jsonObject.put("inputType", model.getInputType());
		}
		if (model.getDefaultVal() != null) {
			jsonObject.put("defaultVal", model.getDefaultVal());
		}
		if (model.getExtJson() != null) {
			jsonObject.put("extJson", model.getExtJson());
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
		return jsonObject;
	}

	public static ObjectNode toObjectNode(DepBaseProp model) {
		ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("ruleId", model.getRuleId());
		jsonObject.put("_ruleId_", model.getRuleId());
		if (model.getRuleCode() != null) {
			jsonObject.put("ruleCode", model.getRuleCode());
		}
		if (model.getRuleName() != null) {
			jsonObject.put("ruleName", model.getRuleName());
		}
		if (model.getRuleDesc() != null) {
			jsonObject.put("ruleDesc", model.getRuleDesc());
		}
		if (model.getSysCategory() != null) {
			jsonObject.put("sysCategory", model.getSysCategory());
		}
		if (model.getUseCategory() != null) {
			jsonObject.put("useCategory", model.getUseCategory());
		}
		if (model.getOpenFlag() != null) {
			jsonObject.put("openFlag", model.getOpenFlag());
		}
		jsonObject.put("orderNo", model.getOrderNo());
		if (model.getReadOnly() != null) {
			jsonObject.put("readOnly", model.getReadOnly());
		}
		if (model.getRepeatFlag() != null) {
			jsonObject.put("repeatFlag", model.getRepeatFlag());
		}
		if (model.getNotNull() != null) {
			jsonObject.put("notNull", model.getNotNull());
		}
		if (model.getInputType() != null) {
			jsonObject.put("inputType", model.getInputType());
		}
		if (model.getDefaultVal() != null) {
			jsonObject.put("defaultVal", model.getDefaultVal());
		}
		if (model.getExtJson() != null) {
			jsonObject.put("extJson", model.getExtJson());
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
		return jsonObject;
	}

	public static JSONArray listToArray(java.util.List<DepBaseProp> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepBaseProp model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepBaseProp> arrayToList(JSONArray array) {
		java.util.List<DepBaseProp> list = new java.util.ArrayList<DepBaseProp>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepBaseProp model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}

	private DepBasePropJsonFactory() {

	}

}
