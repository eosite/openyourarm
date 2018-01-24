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
public class DepReportWdataSetJsonFactory {

	public static DepReportWdataSet jsonToObject(JSONObject jsonObject) {
            DepReportWdataSet model = new DepReportWdataSet();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("wdatasetId")) {
			model.setWdatasetId(jsonObject.getLong("wdatasetId"));
		}
		if (jsonObject.containsKey("repTemplateId")) {
			model.setRepTemplateId(jsonObject.getLong("repTemplateId"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("dataTableName")) {
			model.setDataTableName(jsonObject.getString("dataTableName"));
		}
		if (jsonObject.containsKey("enConditon")) {
			model.setEnConditon(jsonObject.getString("enConditon"));
		}
		if (jsonObject.containsKey("order")) {
			model.setOrder(jsonObject.getInteger("order"));
		}
		if (jsonObject.containsKey("ruleJson")) {
			model.setRuleJson(jsonObject.getString("ruleJson"));
		}
		if (jsonObject.containsKey("psql")) {
			model.setPsql(jsonObject.getString("psql"));
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

	public static JSONObject toJsonObject(DepReportWdataSet model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("wdatasetId", model.getWdatasetId());
        jsonObject.put("repTemplateId", model.getRepTemplateId());
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		} 
		if (model.getDataTableName() != null) {
			jsonObject.put("dataTableName", model.getDataTableName());
		} 
		if (model.getEnConditon() != null) {
			jsonObject.put("enConditon", model.getEnConditon());
		} 
        jsonObject.put("order", model.getOrder());
		if (model.getRuleJson() != null) {
			jsonObject.put("ruleJson", model.getRuleJson());
		} 
		if (model.getPsql() != null) {
			jsonObject.put("psql", model.getPsql());
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


	public static ObjectNode toObjectNode(DepReportWdataSet model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("wdatasetId", model.getWdatasetId());
                jsonObject.put("repTemplateId", model.getRepTemplateId());
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getTableName() != null) {
                     jsonObject.put("tableName", model.getTableName());
                } 
                if (model.getDataTableName() != null) {
                     jsonObject.put("dataTableName", model.getDataTableName());
                } 
                if (model.getEnConditon() != null) {
                     jsonObject.put("enConditon", model.getEnConditon());
                } 
                jsonObject.put("order", model.getOrder());
                if (model.getRuleJson() != null) {
                     jsonObject.put("ruleJson", model.getRuleJson());
                } 
                if (model.getPsql() != null) {
                     jsonObject.put("psql", model.getPsql());
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

	
	public static JSONArray listToArray(java.util.List<DepReportWdataSet> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportWdataSet model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportWdataSet> arrayToList(JSONArray array) {
		java.util.List<DepReportWdataSet> list = new java.util.ArrayList<DepReportWdataSet>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportWdataSet model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportWdataSetJsonFactory() {

	}

}
