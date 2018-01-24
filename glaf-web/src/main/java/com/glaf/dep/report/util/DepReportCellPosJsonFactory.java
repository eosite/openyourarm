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
public class DepReportCellPosJsonFactory {

	public static DepReportCellPos jsonToObject(JSONObject jsonObject) {
            DepReportCellPos model = new DepReportCellPos();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("cellId")) {
			model.setCellId(jsonObject.getLong("cellId"));
		}
		if (jsonObject.containsKey("rowIndex")) {
			model.setRowIndex(jsonObject.getInteger("rowIndex"));
		}
		if (jsonObject.containsKey("colIndex")) {
			model.setColIndex(jsonObject.getInteger("colIndex"));
		}
		if (jsonObject.containsKey("varFalg")) {
			model.setVarFalg(jsonObject.getString("varFalg"));
		}
		if (jsonObject.containsKey("direction")) {
			model.setDirection(jsonObject.getString("direction"));
		}
		if (jsonObject.containsKey("endRowIndex")) {
			model.setEndRowIndex(jsonObject.getInteger("endRowIndex"));
		}
		if (jsonObject.containsKey("endColIndex")) {
			model.setEndColIndex(jsonObject.getInteger("endColIndex"));
		}

            return model;
	}

	public static JSONObject toJsonObject(DepReportCellPos model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("cellId", model.getCellId());
        jsonObject.put("rowIndex", model.getRowIndex());
        jsonObject.put("colIndex", model.getColIndex());
		if (model.getVarFalg() != null) {
			jsonObject.put("varFalg", model.getVarFalg());
		} 
		if (model.getDirection() != null) {
			jsonObject.put("direction", model.getDirection());
		} 
        jsonObject.put("endRowIndex", model.getEndRowIndex());
        jsonObject.put("endColIndex", model.getEndColIndex());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DepReportCellPos model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("cellId", model.getCellId());
                jsonObject.put("rowIndex", model.getRowIndex());
                jsonObject.put("colIndex", model.getColIndex());
                if (model.getVarFalg() != null) {
                     jsonObject.put("varFalg", model.getVarFalg());
                } 
                if (model.getDirection() != null) {
                     jsonObject.put("direction", model.getDirection());
                } 
                jsonObject.put("endRowIndex", model.getEndRowIndex());
                jsonObject.put("endColIndex", model.getEndColIndex());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DepReportCellPos> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportCellPos model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportCellPos> arrayToList(JSONArray array) {
		java.util.List<DepReportCellPos> list = new java.util.ArrayList<DepReportCellPos>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportCellPos model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportCellPosJsonFactory() {

	}

}
