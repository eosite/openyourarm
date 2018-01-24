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
public class DepReportTmpCategoryJsonFactory {

	public static DepReportTmpCategory jsonToObject(JSONObject jsonObject) {
            DepReportTmpCategory model = new DepReportTmpCategory();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("depId")) {
			model.setDepId(jsonObject.getLong("depId"));
		}
		if (jsonObject.containsKey("tmpId")) {
			model.setTmpId(jsonObject.getLong("tmpId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(DepReportTmpCategory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("depId", model.getDepId());
        jsonObject.put("tmpId", model.getTmpId());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(DepReportTmpCategory model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("depId", model.getDepId());
                jsonObject.put("tmpId", model.getTmpId());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<DepReportTmpCategory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (DepReportTmpCategory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<DepReportTmpCategory> arrayToList(JSONArray array) {
		java.util.List<DepReportTmpCategory> list = new java.util.ArrayList<DepReportTmpCategory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			DepReportTmpCategory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private DepReportTmpCategoryJsonFactory() {

	}

}
