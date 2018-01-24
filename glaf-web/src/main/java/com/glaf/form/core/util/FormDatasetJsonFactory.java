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
public class FormDatasetJsonFactory {

	public static FormDataset jsonToObject(JSONObject jsonObject) {
            FormDataset model = new FormDataset();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("datasetId")) {
			model.setDatasetId(jsonObject.getString("datasetId"));
		}
		if (jsonObject.containsKey("columnName")) {
			model.setColumnName(jsonObject.getString("columnName"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("widgetId")) {
			model.setWidgetId(jsonObject.getString("widgetId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormDataset model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDatasetId() != null) {
			jsonObject.put("datasetId", model.getDatasetId());
		} 
		if (model.getColumnName() != null) {
			jsonObject.put("columnName", model.getColumnName());
		} 
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		} 
		if (model.getWidgetId() != null) {
			jsonObject.put("widgetId", model.getWidgetId());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormDataset model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getDatasetId() != null) {
                     jsonObject.put("datasetId", model.getDatasetId());
                } 
                if (model.getColumnName() != null) {
                     jsonObject.put("columnName", model.getColumnName());
                } 
                if (model.getPageId() != null) {
                     jsonObject.put("pageId", model.getPageId());
                } 
                if (model.getWidgetId() != null) {
                     jsonObject.put("widgetId", model.getWidgetId());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormDataset> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormDataset model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormDataset> arrayToList(JSONArray array) {
		java.util.List<FormDataset> list = new java.util.ArrayList<FormDataset>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormDataset model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormDatasetJsonFactory() {

	}

}
