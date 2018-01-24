package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.ConvertPageParam;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class ConvertPageParamJsonFactory {

	public static ConvertPageParam jsonToObject(JSONObject jsonObject) {
            ConvertPageParam model = new ConvertPageParam();
            if (jsonObject.containsKey("cvtParamId")) {
		    model.setCvtParamId(jsonObject.getLong("cvtParamId"));
		}
		if (jsonObject.containsKey("cvtId")) {
			model.setCvtId(jsonObject.getLong("cvtId"));
		}
		if (jsonObject.containsKey("paramName")) {
			model.setParamName(jsonObject.getString("paramName"));
		}
		if (jsonObject.containsKey("paramCode")) {
			model.setParamCode(jsonObject.getString("paramCode"));
		}
		if (jsonObject.containsKey("paramType")) {
			model.setParamType(jsonObject.getString("paramType"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ConvertPageParam model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("cvtParamId", model.getCvtParamId());
		jsonObject.put("_cvtParamId_", model.getCvtParamId());
        jsonObject.put("cvtId", model.getCvtId());
		if (model.getParamName() != null) {
			jsonObject.put("paramName", model.getParamName());
		} 
		if (model.getParamCode() != null) {
			jsonObject.put("paramCode", model.getParamCode());
		} 
		if (model.getParamType() != null) {
			jsonObject.put("paramType", model.getParamType());
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


	public static ObjectNode toObjectNode(ConvertPageParam model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("cvtParamId", model.getCvtParamId());
		jsonObject.put("_cvtParamId_", model.getCvtParamId());
                jsonObject.put("cvtId", model.getCvtId());
                if (model.getParamName() != null) {
                     jsonObject.put("paramName", model.getParamName());
                } 
                if (model.getParamCode() != null) {
                     jsonObject.put("paramCode", model.getParamCode());
                } 
                if (model.getParamType() != null) {
                     jsonObject.put("paramType", model.getParamType());
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

	
	public static JSONArray listToArray(java.util.List<ConvertPageParam> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConvertPageParam model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConvertPageParam> arrayToList(JSONArray array) {
		java.util.List<ConvertPageParam> list = new java.util.ArrayList<ConvertPageParam>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConvertPageParam model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ConvertPageParamJsonFactory() {

	}

}
