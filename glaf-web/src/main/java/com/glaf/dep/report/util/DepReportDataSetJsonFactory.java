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
public class DepReportDataSetJsonFactory {

	public static DepReportDataSet jsonToObject(JSONObject jsonObject) {
            DepReportDataSet model = new DepReportDataSet();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("repTemplateId")) {
			model.setRepTemplateId(jsonObject.getLong("repTemplateId"));
		}
		if (jsonObject.containsKey("dataSetId")) {
			model.setDataSetId(jsonObject.getLong("dataSetId"));
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

	public static JSONObject toJsonObject(DepReportDataSet model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("repTemplateId", model.getRepTemplateId());
        jsonObject.put("dataSetId", model.getDataSetId());
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


	public static ObjectNode toObjectNode(DepReportDataSet model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("repTemplateId", model.getRepTemplateId());
                jsonObject.put("dataSetId", model.getDataSetId());
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

	
	public static JSONArray listToArray(java.util.List<DepReportDataSet> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportDataSet model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportDataSet> arrayToList(JSONArray array) {
		java.util.List<DepReportDataSet> list = new java.util.ArrayList<DepReportDataSet>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportDataSet model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportDataSetJsonFactory() {

	}

}
