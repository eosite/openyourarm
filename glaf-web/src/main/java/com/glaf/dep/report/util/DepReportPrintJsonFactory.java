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
public class DepReportPrintJsonFactory {

	public static DepReportPrint jsonToObject(JSONObject jsonObject) {
            DepReportPrint model = new DepReportPrint();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("cellId")) {
			model.setCellId(jsonObject.getLong("cellId"));
		}
		if (jsonObject.containsKey("printFlag")) {
			model.setPrintFlag(jsonObject.getString("printFlag"));
		}
		if (jsonObject.containsKey("pageCells")) {
			model.setPageCells(jsonObject.getInteger("pageCells"));
		}
		if (jsonObject.containsKey("loopFlag")) {
			model.setLoopFlag(jsonObject.getString("loopFlag"));
		}
		if (jsonObject.containsKey("enCondition")) {
			model.setEnCondition(jsonObject.getString("enCondition"));
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

	public static JSONObject toJsonObject(DepReportPrint model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("cellId", model.getCellId());
		if (model.getPrintFlag() != null) {
			jsonObject.put("printFlag", model.getPrintFlag());
		} 
        jsonObject.put("pageCells", model.getPageCells());
		if (model.getLoopFlag() != null) {
			jsonObject.put("loopFlag", model.getLoopFlag());
		} 
		if (model.getEnCondition() != null) {
			jsonObject.put("enCondition", model.getEnCondition());
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


	public static ObjectNode toObjectNode(DepReportPrint model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("cellId", model.getCellId());
                if (model.getPrintFlag() != null) {
                     jsonObject.put("printFlag", model.getPrintFlag());
                } 
                jsonObject.put("pageCells", model.getPageCells());
                if (model.getLoopFlag() != null) {
                     jsonObject.put("loopFlag", model.getLoopFlag());
                } 
                if (model.getEnCondition() != null) {
                     jsonObject.put("enCondition", model.getEnCondition());
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

	
	public static JSONArray listToArray(java.util.List<DepReportPrint> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportPrint model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportPrint> arrayToList(JSONArray array) {
		java.util.List<DepReportPrint> list = new java.util.ArrayList<DepReportPrint>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportPrint model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportPrintJsonFactory() {

	}

}
