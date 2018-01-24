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
public class FormNodeMessageHistoryJsonFactory {

	public static FormNodeMessageHistory jsonToObject(JSONObject jsonObject) {
            FormNodeMessageHistory model = new FormNodeMessageHistory();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("telephone")) {
			model.setTelephone(jsonObject.getString("telephone"));
		}
		if (jsonObject.containsKey("msg")) {
			model.setMsg(jsonObject.getString("msg"));
		}
		if (jsonObject.containsKey("state")) {
			model.setState(jsonObject.getInteger("state"));
		}
		if (jsonObject.containsKey("creator")) {
			model.setCreator(jsonObject.getString("creator"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("result")) {
			model.setResult(jsonObject.getString("result"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormNodeMessageHistory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTelephone() != null) {
			jsonObject.put("telephone", model.getTelephone());
		} 
		if (model.getMsg() != null) {
			jsonObject.put("msg", model.getMsg());
		} 
        jsonObject.put("state", model.getState());
		if (model.getCreator() != null) {
			jsonObject.put("creator", model.getCreator());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
		if (model.getResult() != null) {
			jsonObject.put("result", model.getResult());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormNodeMessageHistory model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getTelephone() != null) {
                     jsonObject.put("telephone", model.getTelephone());
                } 
                if (model.getMsg() != null) {
                     jsonObject.put("msg", model.getMsg());
                } 
                jsonObject.put("state", model.getState());
                if (model.getCreator() != null) {
                     jsonObject.put("creator", model.getCreator());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getResult() != null) {
                     jsonObject.put("result", model.getResult());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormNodeMessageHistory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormNodeMessageHistory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormNodeMessageHistory> arrayToList(JSONArray array) {
		java.util.List<FormNodeMessageHistory> list = new java.util.ArrayList<FormNodeMessageHistory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormNodeMessageHistory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormNodeMessageHistoryJsonFactory() {

	}

}
