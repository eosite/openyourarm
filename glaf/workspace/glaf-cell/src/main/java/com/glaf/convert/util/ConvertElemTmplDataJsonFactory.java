package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.ConvertElemTmplData;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class ConvertElemTmplDataJsonFactory {

	public static ConvertElemTmplData jsonToObject(JSONObject jsonObject) {
            ConvertElemTmplData model = new ConvertElemTmplData();
            if (jsonObject.containsKey("dataRuleId")) {
		    model.setDataRuleId(jsonObject.getLong("dataRuleId"));
		}
		if (jsonObject.containsKey("cvtElemId")) {
			model.setCvtElemId(jsonObject.getLong("cvtElemId"));
		}
		if (jsonObject.containsKey("tableName")) {
			model.setTableName(jsonObject.getString("tableName"));
		}
		if (jsonObject.containsKey("fieldName")) {
			model.setFieldName(jsonObject.getString("fieldName"));
		}
		if (jsonObject.containsKey("dataTableId")) {
			model.setDataTableId(jsonObject.getString("dataTableId"));
		}
		if (jsonObject.containsKey("subTable")) {
			model.setSubTable(jsonObject.getString("subTable"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ConvertElemTmplData model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("dataRuleId", model.getDataRuleId());
		jsonObject.put("_dataRuleId_", model.getDataRuleId());
        jsonObject.put("cvtElemId", model.getCvtElemId());
		if (model.getTableName() != null) {
			jsonObject.put("tableName", model.getTableName());
		} 
		if (model.getFieldName() != null) {
			jsonObject.put("fieldName", model.getFieldName());
		} 
		if (model.getDataTableId() != null) {
			jsonObject.put("dataTableId", model.getDataTableId());
		} 
		if (model.getSubTable() != null) {
			jsonObject.put("subTable", model.getSubTable());
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


	public static ObjectNode toObjectNode(ConvertElemTmplData model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("dataRuleId", model.getDataRuleId());
		jsonObject.put("_dataRuleId_", model.getDataRuleId());
                jsonObject.put("cvtElemId", model.getCvtElemId());
                if (model.getTableName() != null) {
                     jsonObject.put("tableName", model.getTableName());
                } 
                if (model.getFieldName() != null) {
                     jsonObject.put("fieldName", model.getFieldName());
                } 
                if (model.getDataTableId() != null) {
                     jsonObject.put("dataTableId", model.getDataTableId());
                } 
                if (model.getSubTable() != null) {
                     jsonObject.put("subTable", model.getSubTable());
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

	
	public static JSONArray listToArray(java.util.List<ConvertElemTmplData> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConvertElemTmplData model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConvertElemTmplData> arrayToList(JSONArray array) {
		java.util.List<ConvertElemTmplData> list = new java.util.ArrayList<ConvertElemTmplData>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConvertElemTmplData model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ConvertElemTmplDataJsonFactory() {

	}

}
