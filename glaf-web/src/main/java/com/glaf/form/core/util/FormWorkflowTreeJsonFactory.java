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
public class FormWorkflowTreeJsonFactory {

	public static FormWorkflowTree jsonToObject(JSONObject jsonObject) {
            FormWorkflowTree model = new FormWorkflowTree();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("defId")) {
			model.setDefId(jsonObject.getString("defId"));
		}
		if (jsonObject.containsKey("p_defId")) {
			model.setP_defId(jsonObject.getString("p_defId"));
		}
		if (jsonObject.containsKey("p_processdefId")) {
			model.setP_processdefId(jsonObject.getString("p_processdefId"));
		}
		if (jsonObject.containsKey("processdefId")) {
			model.setProcessdefId(jsonObject.getString("processdefId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormWorkflowTree model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getDefId() != null) {
			jsonObject.put("defId", model.getDefId());
		} 
		if (model.getP_defId() != null) {
			jsonObject.put("p_defId", model.getP_defId());
		} 
		if (model.getP_processdefId() != null) {
			jsonObject.put("p_processdefId", model.getP_processdefId());
		} 
		if (model.getProcessdefId() != null) {
			jsonObject.put("processdefId", model.getProcessdefId());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormWorkflowTree model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getDefId() != null) {
                     jsonObject.put("defId", model.getDefId());
                } 
                if (model.getP_defId() != null) {
                     jsonObject.put("p_defId", model.getP_defId());
                } 
                if (model.getP_processdefId() != null) {
                     jsonObject.put("p_processdefId", model.getP_processdefId());
                } 
                if (model.getProcessdefId() != null) {
                     jsonObject.put("processdefId", model.getProcessdefId());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormWorkflowTree> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormWorkflowTree model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormWorkflowTree> arrayToList(JSONArray array) {
		java.util.List<FormWorkflowTree> list = new java.util.ArrayList<FormWorkflowTree>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormWorkflowTree model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormWorkflowTreeJsonFactory() {

	}

}
