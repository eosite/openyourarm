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
public class FormOutexpRelationJsonFactory {

	public static FormOutexpRelation jsonToObject(JSONObject jsonObject) {
            FormOutexpRelation model = new FormOutexpRelation();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("widgetId")) {
			model.setWidgetId(jsonObject.getString("widgetId"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormOutexpRelation model) {
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
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		} 
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormOutexpRelation model){
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
                if (model.getType() != null) {
                     jsonObject.put("type", model.getType());
                } 
                if (model.getValue() != null) {
                     jsonObject.put("value", model.getValue());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormOutexpRelation> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormOutexpRelation model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormOutexpRelation> arrayToList(JSONArray array) {
		java.util.List<FormOutexpRelation> list = new java.util.ArrayList<FormOutexpRelation>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormOutexpRelation model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormOutexpRelationJsonFactory() {

	}

}
