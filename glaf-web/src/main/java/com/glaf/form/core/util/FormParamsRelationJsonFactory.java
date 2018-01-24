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
public class FormParamsRelationJsonFactory {

	public static FormParamsRelation jsonToObject(JSONObject jsonObject) {
            FormParamsRelation model = new FormParamsRelation();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("widgetId")) {
			model.setWidgetId(jsonObject.getString("widgetId"));
		}
		if (jsonObject.containsKey("pid")) {
			model.setPid(jsonObject.getString("pid"));
		}
		if (jsonObject.containsKey("attrName")) {
			model.setAttrName(jsonObject.getString("attrName"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormParamsRelation model) {
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
		if (model.getPid() != null) {
			jsonObject.put("pid", model.getPid());
		} 
		if (model.getAttrName() != null) {
			jsonObject.put("attrName", model.getAttrName());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormParamsRelation model){
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
                if (model.getPid() != null) {
                     jsonObject.put("pid", model.getPid());
                } 
                if (model.getAttrName() != null) {
                     jsonObject.put("attrName", model.getAttrName());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormParamsRelation> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormParamsRelation model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormParamsRelation> arrayToList(JSONArray array) {
		java.util.List<FormParamsRelation> list = new java.util.ArrayList<FormParamsRelation>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormParamsRelation model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormParamsRelationJsonFactory() {

	}

}
