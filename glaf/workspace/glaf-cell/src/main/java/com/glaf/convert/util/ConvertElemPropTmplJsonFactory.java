package com.glaf.convert.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.glaf.convert.domain.ConvertElemPropTmpl;
import com.glaf.core.util.DateUtils;


/**
 * 
 * JSON工厂类
 *
 */
public class ConvertElemPropTmplJsonFactory {

	public static ConvertElemPropTmpl jsonToObject(JSONObject jsonObject) {
            ConvertElemPropTmpl model = new ConvertElemPropTmpl();
            if (jsonObject.containsKey("elemPropId")) {
		    model.setElemPropId(jsonObject.getLong("elemPropId"));
		}
		if (jsonObject.containsKey("cvtId")) {
			model.setCvtId(jsonObject.getLong("cvtId"));
		}
		if (jsonObject.containsKey("rowIndex")) {
			model.setRowIndex(jsonObject.getInteger("rowIndex"));
		}
		if (jsonObject.containsKey("colIndex")) {
			model.setColIndex(jsonObject.getInteger("colIndex"));
		}
		if (jsonObject.containsKey("cellPropVal")) {
			model.setCellPropVal(jsonObject.getString("cellPropVal"));
		}
		if (jsonObject.containsKey("cellProp")) {
			model.setCellProp(jsonObject.getString("cellProp"));
		}
		if (jsonObject.containsKey("createDatetime")) {
			model.setCreateDatetime(jsonObject.getDate("createDatetime"));
		}
		if (jsonObject.containsKey("modifyDatetime")) {
			model.setModifyDatetime(jsonObject.getDate("modifyDatetime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(ConvertElemPropTmpl model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("elemPropId", model.getElemPropId());
		jsonObject.put("_elemPropId_", model.getElemPropId());
        jsonObject.put("cvtId", model.getCvtId());
        jsonObject.put("rowIndex", model.getRowIndex());
        jsonObject.put("colIndex", model.getColIndex());
		if (model.getCellPropVal() != null) {
			jsonObject.put("cellPropVal", model.getCellPropVal());
		} 
		if (model.getCellProp() != null) {
			jsonObject.put("cellProp", model.getCellProp());
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


	public static ObjectNode toObjectNode(ConvertElemPropTmpl model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("elemPropId", model.getElemPropId());
		jsonObject.put("_elemPropId_", model.getElemPropId());
                jsonObject.put("cvtId", model.getCvtId());
                jsonObject.put("rowIndex", model.getRowIndex());
                jsonObject.put("colIndex", model.getColIndex());
                if (model.getCellPropVal() != null) {
                     jsonObject.put("cellPropVal", model.getCellPropVal());
                } 
                if (model.getCellProp() != null) {
                     jsonObject.put("cellProp", model.getCellProp());
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

	
	public static JSONArray listToArray(java.util.List<ConvertElemPropTmpl> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (ConvertElemPropTmpl model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<ConvertElemPropTmpl> arrayToList(JSONArray array) {
		java.util.List<ConvertElemPropTmpl> list = new java.util.ArrayList<ConvertElemPropTmpl>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			ConvertElemPropTmpl model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private ConvertElemPropTmplJsonFactory() {

	}

}
