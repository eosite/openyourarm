package com.glaf.dep.report.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.dep.report.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class DepReportValidationJsonFactory {

	public static DepReportValidation jsonToObject(JSONObject jsonObject) {
            DepReportValidation model = new DepReportValidation();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("cellId")) {
			model.setCellId(jsonObject.getLong("cellId"));
		}
		if (jsonObject.containsKey("enConditon")) {
			model.setEnConditon(jsonObject.getString("enConditon"));
		}
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("alertTmp")) {
			model.setAlertTmp(jsonObject.getString("alertTmp"));
		}
		if (jsonObject.containsKey("alertType")) {
			model.setAlertType(jsonObject.getString("alertType"));
		}
		if (jsonObject.containsKey("trrigerType")) {
			model.setTrrigerType(jsonObject.getString("trrigerType"));
		}
		if (jsonObject.containsKey("ruleJson")) {
			model.setRuleJson(jsonObject.getString("ruleJson"));
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
		if (jsonObject.containsKey("modifydateTime")) {
			model.setModifydateTime(jsonObject.getDate("modifydateTime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(DepReportValidation model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("cellId", model.getCellId());
		if (model.getEnConditon() != null) {
			jsonObject.put("enConditon", model.getEnConditon());
		} 
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		} 
		if (model.getAlertTmp() != null) {
			jsonObject.put("alertTmp", model.getAlertTmp());
		} 
		if (model.getAlertType() != null) {
			jsonObject.put("alertType", model.getAlertType());
		} 
		if (model.getTrrigerType() != null) {
			jsonObject.put("trrigerType", model.getTrrigerType());
		} 
		if (model.getRuleJson() != null) {
			jsonObject.put("ruleJson", model.getRuleJson());
		} 
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
                if (model.getCreateDateTime() != null) {
                      jsonObject.put("createDateTime", DateUtils.getDate(model.getCreateDateTime()));
		      jsonObject.put("createDateTime_date", DateUtils.getDate(model.getCreateDateTime()));
		      jsonObject.put("createDateTime_datetime", DateUtils.getDateTime(model.getCreateDateTime()));
                }
		if (model.getModifier() != null) {
			jsonObject.put("modifier", model.getModifier());
		} 
                if (model.getModifydateTime() != null) {
                      jsonObject.put("modifydateTime", DateUtils.getDate(model.getModifydateTime()));
		      jsonObject.put("modifydateTime_date", DateUtils.getDate(model.getModifydateTime()));
		      jsonObject.put("modifydateTime_datetime", DateUtils.getDateTime(model.getModifydateTime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DepReportValidation model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("cellId", model.getCellId());
                if (model.getEnConditon() != null) {
                     jsonObject.put("enConditon", model.getEnConditon());
                } 
                if (model.getExpression() != null) {
                     jsonObject.put("expression", model.getExpression());
                } 
                if (model.getAlertTmp() != null) {
                     jsonObject.put("alertTmp", model.getAlertTmp());
                } 
                if (model.getAlertType() != null) {
                     jsonObject.put("alertType", model.getAlertType());
                } 
                if (model.getTrrigerType() != null) {
                     jsonObject.put("trrigerType", model.getTrrigerType());
                } 
                if (model.getRuleJson() != null) {
                     jsonObject.put("ruleJson", model.getRuleJson());
                } 
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                if (model.getCreateDateTime() != null) {
                      jsonObject.put("createDateTime", DateUtils.getDate(model.getCreateDateTime()));
		      jsonObject.put("createDateTime_date", DateUtils.getDate(model.getCreateDateTime()));
		      jsonObject.put("createDateTime_datetime", DateUtils.getDateTime(model.getCreateDateTime()));
                }
                if (model.getModifier() != null) {
                     jsonObject.put("modifier", model.getModifier());
                } 
                if (model.getModifydateTime() != null) {
                      jsonObject.put("modifydateTime", DateUtils.getDate(model.getModifydateTime()));
		      jsonObject.put("modifydateTime_date", DateUtils.getDate(model.getModifydateTime()));
		      jsonObject.put("modifydateTime_datetime", DateUtils.getDateTime(model.getModifydateTime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DepReportValidation> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportValidation model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportValidation> arrayToList(JSONArray array) {
		java.util.List<DepReportValidation> list = new java.util.ArrayList<DepReportValidation>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportValidation model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportValidationJsonFactory() {

	}

}
