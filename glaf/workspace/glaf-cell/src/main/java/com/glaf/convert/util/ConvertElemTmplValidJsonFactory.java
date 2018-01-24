package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.ConvertElemTmplValid;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class ConvertElemTmplValidJsonFactory {

	public static ConvertElemTmplValid jsonToObject(JSONObject jsonObject) {
            ConvertElemTmplValid model = new ConvertElemTmplValid();
            if (jsonObject.containsKey("validRuleId")) {
		    model.setValidRuleId(jsonObject.getLong("validRuleId"));
		}
		if (jsonObject.containsKey("cvtElemId")) {
			model.setCvtElemId(jsonObject.getLong("cvtElemId"));
		}
		if (jsonObject.containsKey("validType")) {
			model.setValidType(jsonObject.getString("validType"));
		}
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("dType")) {
			model.setDType(jsonObject.getString("dType"));
		}
		if (jsonObject.containsKey("len")) {
			model.setLen(jsonObject.getInteger("len"));
		}
		if (jsonObject.containsKey("rangeUpper")) {
			model.setRangeUpper(jsonObject.getString("rangeUpper"));
		}
		if (jsonObject.containsKey("rangeLower")) {
			model.setRangeLower(jsonObject.getString("rangeLower"));
		}
		if (jsonObject.containsKey("useCondition")) {
			model.setUseCondition(jsonObject.getString("useCondition"));
		}
		if (jsonObject.containsKey("seq")) {
			model.setSeq(jsonObject.getInteger("seq"));
		}
		if (jsonObject.containsKey("parentRuleId")) {
			model.setParentRuleId(jsonObject.getLong("parentRuleId"));
		}
		if (jsonObject.containsKey("treeId")) {
			model.setTreeId(jsonObject.getString("treeId"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ConvertElemTmplValid model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("validRuleId", model.getValidRuleId());
		jsonObject.put("_validRuleId_", model.getValidRuleId());
        jsonObject.put("cvtElemId", model.getCvtElemId());
		if (model.getValidType() != null) {
			jsonObject.put("validType", model.getValidType());
		} 
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		} 
		if (model.getDType() != null) {
			jsonObject.put("dType", model.getDType());
		} 
        jsonObject.put("len", model.getLen());
		if (model.getRangeUpper() != null) {
			jsonObject.put("rangeUpper", model.getRangeUpper());
		} 
		if (model.getRangeLower() != null) {
			jsonObject.put("rangeLower", model.getRangeLower());
		} 
		if (model.getUseCondition() != null) {
			jsonObject.put("useCondition", model.getUseCondition());
		} 
        jsonObject.put("seq", model.getSeq());
        jsonObject.put("parentRuleId", model.getParentRuleId());
		if (model.getTreeId() != null) {
			jsonObject.put("treeId", model.getTreeId());
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


	public static ObjectNode toObjectNode(ConvertElemTmplValid model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("validRuleId", model.getValidRuleId());
		jsonObject.put("_validRuleId_", model.getValidRuleId());
                jsonObject.put("cvtElemId", model.getCvtElemId());
                if (model.getValidType() != null) {
                     jsonObject.put("validType", model.getValidType());
                } 
                if (model.getExpression() != null) {
                     jsonObject.put("expression", model.getExpression());
                } 
                if (model.getDType() != null) {
                     jsonObject.put("dType", model.getDType());
                } 
                jsonObject.put("len", model.getLen());
                if (model.getRangeUpper() != null) {
                     jsonObject.put("rangeUpper", model.getRangeUpper());
                } 
                if (model.getRangeLower() != null) {
                     jsonObject.put("rangeLower", model.getRangeLower());
                } 
                if (model.getUseCondition() != null) {
                     jsonObject.put("useCondition", model.getUseCondition());
                } 
                jsonObject.put("seq", model.getSeq());
                jsonObject.put("parentRuleId", model.getParentRuleId());
                if (model.getTreeId() != null) {
                     jsonObject.put("treeId", model.getTreeId());
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

	
	public static JSONArray listToArray(java.util.List<ConvertElemTmplValid> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConvertElemTmplValid model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConvertElemTmplValid> arrayToList(JSONArray array) {
		java.util.List<ConvertElemTmplValid> list = new java.util.ArrayList<ConvertElemTmplValid>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConvertElemTmplValid model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ConvertElemTmplValidJsonFactory() {

	}

}
