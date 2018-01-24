package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.ConvertElemTmplRef;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class ConvertElemTmplRefJsonFactory {

	public static ConvertElemTmplRef jsonToObject(JSONObject jsonObject) {
            ConvertElemTmplRef model = new ConvertElemTmplRef();
            if (jsonObject.containsKey("refRuleId")) {
		    model.setRefRuleId(jsonObject.getLong("refRuleId"));
		}
		if (jsonObject.containsKey("cvtElemId")) {
			model.setCvtElemId(jsonObject.getLong("cvtElemId"));
		}
		if (jsonObject.containsKey("refType")) {
			model.setRefType(jsonObject.getString("refType"));
		}
		if (jsonObject.containsKey("refContent")) {
			model.setRefContent(jsonObject.getString("refContent"));
		}
		if (jsonObject.containsKey("refCondition")) {
			model.setRefCondition(jsonObject.getString("refCondition"));
		}
		if (jsonObject.containsKey("refFieldId")) {
			model.setRefFieldId(jsonObject.getString("refFieldId"));
		}
		if (jsonObject.containsKey("useCondition")) {
			model.setUseCondition(jsonObject.getString("useCondition"));
		}
		if (jsonObject.containsKey("transtionFlag")) {
			model.setTranstionFlag(jsonObject.getString("transtionFlag"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ConvertElemTmplRef model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("refRuleId", model.getRefRuleId());
		jsonObject.put("_refRuleId_", model.getRefRuleId());
        jsonObject.put("cvtElemId", model.getCvtElemId());
		if (model.getRefType() != null) {
			jsonObject.put("refType", model.getRefType());
		} 
		if (model.getRefContent() != null) {
			jsonObject.put("refContent", model.getRefContent());
		} 
		if (model.getRefCondition() != null) {
			jsonObject.put("refCondition", model.getRefCondition());
		} 
		if (model.getRefFieldId() != null) {
			jsonObject.put("refFieldId", model.getRefFieldId());
		} 
		if (model.getUseCondition() != null) {
			jsonObject.put("useCondition", model.getUseCondition());
		} 
		if (model.getTranstionFlag() != null) {
			jsonObject.put("transtionFlag", model.getTranstionFlag());
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


	public static ObjectNode toObjectNode(ConvertElemTmplRef model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("refRuleId", model.getRefRuleId());
		jsonObject.put("_refRuleId_", model.getRefRuleId());
                jsonObject.put("cvtElemId", model.getCvtElemId());
                if (model.getRefType() != null) {
                     jsonObject.put("refType", model.getRefType());
                } 
                if (model.getRefContent() != null) {
                     jsonObject.put("refContent", model.getRefContent());
                } 
                if (model.getRefCondition() != null) {
                     jsonObject.put("refCondition", model.getRefCondition());
                } 
                if (model.getRefFieldId() != null) {
                     jsonObject.put("refFieldId", model.getRefFieldId());
                } 
                if (model.getUseCondition() != null) {
                     jsonObject.put("useCondition", model.getUseCondition());
                } 
                if (model.getTranstionFlag() != null) {
                     jsonObject.put("transtionFlag", model.getTranstionFlag());
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

	
	public static JSONArray listToArray(java.util.List<ConvertElemTmplRef> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConvertElemTmplRef model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConvertElemTmplRef> arrayToList(JSONArray array) {
		java.util.List<ConvertElemTmplRef> list = new java.util.ArrayList<ConvertElemTmplRef>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConvertElemTmplRef model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ConvertElemTmplRefJsonFactory() {

	}

}
