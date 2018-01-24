package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.form.core.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class FormPageEventProcDefJsonFactory {

	public static FormPageEventProcDef jsonToObject(JSONObject jsonObject) {
            FormPageEventProcDef model = new FormPageEventProcDef();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("pageId")) {
			model.setPageId(jsonObject.getString("pageId"));
		}
		if (jsonObject.containsKey("compId")) {
			model.setCompId(jsonObject.getString("compId"));
		}
		if (jsonObject.containsKey("event_")) {
			model.setEvent_(jsonObject.getString("event_"));
		}
		if (jsonObject.containsKey("procDefKey")) {
			model.setProcDefKey(jsonObject.getString("procDefKey"));
		}
		if (jsonObject.containsKey("procDefId")) {
			model.setProcDefId(jsonObject.getString("procDefId"));
		}
		if (jsonObject.containsKey("procModelId")) {
			model.setProcModelId(jsonObject.getString("procModelId"));
		}
		if (jsonObject.containsKey("procDeployStatus")) {
			model.setProcDeployStatus(jsonObject.getString("procDeployStatus"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormPageEventProcDef model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getPageId() != null) {
			jsonObject.put("pageId", model.getPageId());
		} 
		if (model.getCompId() != null) {
			jsonObject.put("compId", model.getCompId());
		} 
		if (model.getEvent_() != null) {
			jsonObject.put("event_", model.getEvent_());
		} 
		if (model.getProcDefKey() != null) {
			jsonObject.put("procDefKey", model.getProcDefKey());
		} 
		if (model.getProcDefId() != null) {
			jsonObject.put("procDefId", model.getProcDefId());
		} 
		if (model.getProcModelId() != null) {
			jsonObject.put("procModelId", model.getProcModelId());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormPageEventProcDef model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getPageId() != null) {
                     jsonObject.put("pageId", model.getPageId());
                } 
                if (model.getCompId() != null) {
                     jsonObject.put("compId", model.getCompId());
                } 
                if (model.getEvent_() != null) {
                     jsonObject.put("event_", model.getEvent_());
                } 
                if (model.getProcDefKey() != null) {
                     jsonObject.put("procDefKey", model.getProcDefKey());
                } 
                if (model.getProcDefId() != null) {
                     jsonObject.put("procDefId", model.getProcDefId());
                } 
                if (model.getProcModelId() != null) {
                     jsonObject.put("procModelId", model.getProcModelId());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormPageEventProcDef> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormPageEventProcDef model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormPageEventProcDef> arrayToList(JSONArray array) {
		java.util.List<FormPageEventProcDef> list = new java.util.ArrayList<FormPageEventProcDef>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormPageEventProcDef model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormPageEventProcDefJsonFactory() {

	}

}
