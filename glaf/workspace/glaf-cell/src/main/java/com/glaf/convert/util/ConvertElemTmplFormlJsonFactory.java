package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.ConvertElemTmplForml;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class ConvertElemTmplFormlJsonFactory {

	public static ConvertElemTmplForml jsonToObject(JSONObject jsonObject) {
            ConvertElemTmplForml model = new ConvertElemTmplForml();
            if (jsonObject.containsKey("formlRuleId")) {
		    model.setFormlRuleId(jsonObject.getLong("formlRuleId"));
		}
		if (jsonObject.containsKey("cvtElemId")) {
			model.setCvtElemId(jsonObject.getLong("cvtElemId"));
		}
		if (jsonObject.containsKey("formlName")) {
			model.setFormlName(jsonObject.getString("formlName"));
		}
		if (jsonObject.containsKey("formlContent")) {
			model.setFormlContent(jsonObject.getString("formlContent"));
		}
		if (jsonObject.containsKey("useConditon")) {
			model.setUseConditon(jsonObject.getString("useConditon"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ConvertElemTmplForml model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("formlRuleId", model.getFormlRuleId());
		jsonObject.put("_formlRuleId_", model.getFormlRuleId());
        jsonObject.put("cvtElemId", model.getCvtElemId());
		if (model.getFormlName() != null) {
			jsonObject.put("formlName", model.getFormlName());
		} 
		if (model.getFormlContent() != null) {
			jsonObject.put("formlContent", model.getFormlContent());
		} 
		if (model.getUseConditon() != null) {
			jsonObject.put("useConditon", model.getUseConditon());
		} 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(ConvertElemTmplForml model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("formlRuleId", model.getFormlRuleId());
		jsonObject.put("_formlRuleId_", model.getFormlRuleId());
                jsonObject.put("cvtElemId", model.getCvtElemId());
                if (model.getFormlName() != null) {
                     jsonObject.put("formlName", model.getFormlName());
                } 
                if (model.getFormlContent() != null) {
                     jsonObject.put("formlContent", model.getFormlContent());
                } 
                if (model.getUseConditon() != null) {
                     jsonObject.put("useConditon", model.getUseConditon());
                } 
                if (model.getCreateDatetime() != null) {
                      jsonObject.put("createDatetime", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_date", DateUtils.getDate(model.getCreateDatetime()));
		      jsonObject.put("createDatetime_datetime", DateUtils.getDateTime(model.getCreateDatetime()));
                }
                if (model.getModifyDatetime() != null) {
                      jsonObject.put("modifyDatetime", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_date", DateUtils.getDate(model.getModifyDatetime()));
		      jsonObject.put("modifyDatetime_datetime", DateUtils.getDateTime(model.getModifyDatetime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<ConvertElemTmplForml> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConvertElemTmplForml model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConvertElemTmplForml> arrayToList(JSONArray array) {
		java.util.List<ConvertElemTmplForml> list = new java.util.ArrayList<ConvertElemTmplForml>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConvertElemTmplForml model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ConvertElemTmplFormlJsonFactory() {

	}

}
