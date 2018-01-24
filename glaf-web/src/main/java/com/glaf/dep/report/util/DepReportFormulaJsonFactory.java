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
public class DepReportFormulaJsonFactory {

	public static DepReportFormula jsonToObject(JSONObject jsonObject) {
            DepReportFormula model = new DepReportFormula();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("cellId")) {
			model.setCellId(jsonObject.getLong("cellId"));
		}
		if (jsonObject.containsKey("expression")) {
			model.setExpression(jsonObject.getString("expression"));
		}
		if (jsonObject.containsKey("enCondition")) {
			model.setEnCondition(jsonObject.getString("enCondition"));
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
		if (jsonObject.containsKey("modifyDateTime")) {
			model.setModifyDateTime(jsonObject.getDate("modifyDateTime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(DepReportFormula model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("cellId", model.getCellId());
		if (model.getExpression() != null) {
			jsonObject.put("expression", model.getExpression());
		} 
		if (model.getEnCondition() != null) {
			jsonObject.put("enCondition", model.getEnCondition());
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
                if (model.getModifyDateTime() != null) {
                      jsonObject.put("modifyDateTime", DateUtils.getDate(model.getModifyDateTime()));
		      jsonObject.put("modifyDateTime_date", DateUtils.getDate(model.getModifyDateTime()));
		      jsonObject.put("modifyDateTime_datetime", DateUtils.getDateTime(model.getModifyDateTime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DepReportFormula model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("cellId", model.getCellId());
                if (model.getExpression() != null) {
                     jsonObject.put("expression", model.getExpression());
                } 
                if (model.getEnCondition() != null) {
                     jsonObject.put("enCondition", model.getEnCondition());
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
                if (model.getModifyDateTime() != null) {
                      jsonObject.put("modifyDateTime", DateUtils.getDate(model.getModifyDateTime()));
		      jsonObject.put("modifyDateTime_date", DateUtils.getDate(model.getModifyDateTime()));
		      jsonObject.put("modifyDateTime_datetime", DateUtils.getDateTime(model.getModifyDateTime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DepReportFormula> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportFormula model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportFormula> arrayToList(JSONArray array) {
		java.util.List<DepReportFormula> list = new java.util.ArrayList<DepReportFormula>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportFormula model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportFormulaJsonFactory() {

	}

}
