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
public class MsgValidHistoryJsonFactory {

	public static MsgValidHistory jsonToObject(JSONObject jsonObject) {
            MsgValidHistory model = new MsgValidHistory();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("telephone")) {
			model.setTelephone(jsonObject.getString("telephone"));
		}
		if (jsonObject.containsKey("sendDate")) {
			model.setSendDate(jsonObject.getDate("sendDate"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getInteger("type"));
		}
		if (jsonObject.containsKey("typeName")) {
			model.setTypeName(jsonObject.getString("typeName"));
		}
		if (jsonObject.containsKey("url")) {
			model.setUrl(jsonObject.getString("url"));
		}
		if (jsonObject.containsKey("msg")) {
			model.setMsg(jsonObject.getString("msg"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("statusName")) {
			model.setStatusName(jsonObject.getString("statusName"));
		}
		if (jsonObject.containsKey("result")) {
			model.setResult(jsonObject.getString("result"));
		}

            return model;
	}

	public static JSONObject toJsonObject(MsgValidHistory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTelephone() != null) {
			jsonObject.put("telephone", model.getTelephone());
		} 
                if (model.getSendDate() != null) {
                      jsonObject.put("sendDate", DateUtils.getDate(model.getSendDate()));
		      jsonObject.put("sendDate_date", DateUtils.getDate(model.getSendDate()));
		      jsonObject.put("sendDate_datetime", DateUtils.getDateTime(model.getSendDate()));
                }
        jsonObject.put("type", model.getType());
		if (model.getTypeName() != null) {
			jsonObject.put("typeName", model.getTypeName());
		} 
		if (model.getUrl() != null) {
			jsonObject.put("url", model.getUrl());
		} 
		if (model.getMsg() != null) {
			jsonObject.put("msg", model.getMsg());
		} 
        jsonObject.put("status", model.getStatus());
		if (model.getStatusName() != null) {
			jsonObject.put("statusName", model.getStatusName());
		} 
		if (model.getResult() != null) {
			jsonObject.put("result", model.getResult());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(MsgValidHistory model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getTelephone() != null) {
                     jsonObject.put("telephone", model.getTelephone());
                } 
                if (model.getSendDate() != null) {
                      jsonObject.put("sendDate", DateUtils.getDate(model.getSendDate()));
		      jsonObject.put("sendDate_date", DateUtils.getDate(model.getSendDate()));
		      jsonObject.put("sendDate_datetime", DateUtils.getDateTime(model.getSendDate()));
                }
                jsonObject.put("type", model.getType());
                if (model.getTypeName() != null) {
                     jsonObject.put("typeName", model.getTypeName());
                } 
                if (model.getUrl() != null) {
                     jsonObject.put("url", model.getUrl());
                } 
                if (model.getMsg() != null) {
                     jsonObject.put("msg", model.getMsg());
                } 
                jsonObject.put("status", model.getStatus());
                if (model.getStatusName() != null) {
                     jsonObject.put("statusName", model.getStatusName());
                } 
                if (model.getResult() != null) {
                     jsonObject.put("result", model.getResult());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<MsgValidHistory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MsgValidHistory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MsgValidHistory> arrayToList(JSONArray array) {
		java.util.List<MsgValidHistory> list = new java.util.ArrayList<MsgValidHistory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MsgValidHistory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private MsgValidHistoryJsonFactory() {

	}

}
