package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class FormParamsJsonFactory {

	public static FormParams jsonToObject(JSONObject jsonObject) {
            FormParams model = new FormParams();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("widgetId")) {
			model.setWidgetId(jsonObject.getString("widgetId"));
		}
		if (jsonObject.containsKey("paramName")) {
			model.setParamName(jsonObject.getString("paramName"));
		}
		if (jsonObject.containsKey("datasetId")) {
			model.setDatasetId(jsonObject.getString("datasetId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormParams model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		} 
		if (model.getWidgetId() != null) {
			jsonObject.put("widgetId", model.getWidgetId());
		} 
		if (model.getParamName() != null) {
			jsonObject.put("paramName", model.getParamName());
		} 
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormParams model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getPageId() != null) {
                     jsonObject.put("pageId", model.getPageId());
                } 
                if (model.getWidgetId() != null) {
                     jsonObject.put("widgetId", model.getWidgetId());
                } 
                if (model.getParamName() != null) {
                     jsonObject.put("paramName", model.getParamName());
                } 
                if (model.getDatasetId() != null) {
                     jsonObject.put("datasetId", model.getDatasetId());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormParams> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormParams model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormParams> arrayToList(JSONArray array) {
		java.util.List<FormParams> list = new java.util.ArrayList<FormParams>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormParams model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormParamsJsonFactory() {

	}

}
