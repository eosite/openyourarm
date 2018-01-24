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
public class FormObjectIoJsonFactory {

	public static FormObjectIo jsonToObject(JSONObject jsonObject) {
            FormObjectIo model = new FormObjectIo();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("codeMapping")) {
			model.setCodeMapping(jsonObject.getString("codeMapping"));
		}
		if (jsonObject.containsKey("paramType")) {
			model.setParamType(jsonObject.getString("paramType"));
		}
		if (jsonObject.containsKey("defValue")) {
			model.setDefValue(jsonObject.getString("defValue"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("parent_id")) {
			model.setParent_id(jsonObject.getString("parent_id"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormObjectIo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		if (model.getCodeMapping() != null) {
			jsonObject.put("codeMapping", model.getCodeMapping());
		} 
		if (model.getParamType() != null) {
			jsonObject.put("paramType", model.getParamType());
		} 
		if (model.getDefValue() != null) {
			jsonObject.put("defValue", model.getDefValue());
		} 
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		} 
		if (model.getParent_id() != null) {
			jsonObject.put("parent_id", model.getParent_id());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormObjectIo model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getCodeMapping() != null) {
                     jsonObject.put("codeMapping", model.getCodeMapping());
                } 
                if (model.getParamType() != null) {
                     jsonObject.put("paramType", model.getParamType());
                } 
                if (model.getDefValue() != null) {
                     jsonObject.put("defValue", model.getDefValue());
                } 
                if (model.getType() != null) {
                     jsonObject.put("type", model.getType());
                } 
                if (model.getParent_id() != null) {
                     jsonObject.put("parent_id", model.getParent_id());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormObjectIo> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormObjectIo model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormObjectIo> arrayToList(JSONArray array) {
		java.util.List<FormObjectIo> list = new java.util.ArrayList<FormObjectIo>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormObjectIo model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormObjectIoJsonFactory() {

	}

}
