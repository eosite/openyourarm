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
public class DepReportReferenceJsonFactory {

	public static DepReportReference jsonToObject(JSONObject jsonObject) {
            DepReportReference model = new DepReportReference();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("repDataSetId")) {
			model.setRepDataSetId(jsonObject.getLong("repDataSetId"));
		}
		if (jsonObject.containsKey("repDataId")) {
			model.setRepDataId(jsonObject.getLong("repDataId"));
		}
		if (jsonObject.containsKey("refType")) {
			model.setRefType(jsonObject.getString("refType"));
		}
		if (jsonObject.containsKey("refMode")) {
			model.setRefMode(jsonObject.getString("refMode"));
		}
		if (jsonObject.containsKey("enCondition")) {
			model.setEnCondition(jsonObject.getString("enCondition"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("reportId")) {
			model.setReportId(jsonObject.getString("reportId"));
		}
		if (jsonObject.containsKey("reportCellId")) {
			model.setReportCellId(jsonObject.getString("reportCellId"));
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

	public static JSONObject toJsonObject(DepReportReference model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("repDataSetId", model.getRepDataSetId());
        jsonObject.put("repDataId", model.getRepDataId());
		if (model.getRefType() != null) {
			jsonObject.put("refType", model.getRefType());
		} 
		if (model.getRefMode() != null) {
			jsonObject.put("refMode", model.getRefMode());
		} 
		if (model.getEnCondition() != null) {
			jsonObject.put("enCondition", model.getEnCondition());
		} 
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		} 
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		} 
		if (model.getReportId() != null) {
			jsonObject.put("reportId", model.getReportId());
		} 
		if (model.getReportCellId() != null) {
			jsonObject.put("reportCellId", model.getReportCellId());
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


	public static ObjectNode toObjectNode(DepReportReference model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("repDataSetId", model.getRepDataSetId());
                jsonObject.put("repDataId", model.getRepDataId());
                if (model.getRefType() != null) {
                     jsonObject.put("refType", model.getRefType());
                } 
                if (model.getRefMode() != null) {
                     jsonObject.put("refMode", model.getRefMode());
                } 
                if (model.getEnCondition() != null) {
                     jsonObject.put("enCondition", model.getEnCondition());
                } 
                if (model.getColumnName() != null) {
                     jsonObject.put("columnName", model.getColumnName());
                } 
                if (model.getTableName() != null) {
                     jsonObject.put("tableName", model.getTableName());
                } 
                if (model.getReportId() != null) {
                     jsonObject.put("reportId", model.getReportId());
                } 
                if (model.getReportCellId() != null) {
                     jsonObject.put("reportCellId", model.getReportCellId());
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

	
	public static JSONArray listToArray(java.util.List<DepReportReference> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportReference model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportReference> arrayToList(JSONArray array) {
		java.util.List<DepReportReference> list = new java.util.ArrayList<DepReportReference>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportReference model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportReferenceJsonFactory() {

	}

}
