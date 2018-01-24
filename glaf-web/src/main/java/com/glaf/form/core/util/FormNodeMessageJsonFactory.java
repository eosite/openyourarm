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
public class FormNodeMessageJsonFactory {

	public static FormNodeMessage jsonToObject(JSONObject jsonObject) {
            FormNodeMessage model = new FormNodeMessage();
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
		if (jsonObject.containsKey("subject")) {
			model.setSubject(jsonObject.getString("subject"));
		}
		if (jsonObject.containsKey("sendLaterTime")) {
			model.setSendLaterTime(jsonObject.getDate("sendLaterTime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormNodeMessage model) {
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
		if (model.getSubject() != null) {
			jsonObject.put("subject", model.getSubject());
		} 
                if (model.getSendLaterTime() != null) {
                      jsonObject.put("sendLaterTime", DateUtils.getDate(model.getSendLaterTime()));
		      jsonObject.put("sendLaterTime_date", DateUtils.getDate(model.getSendLaterTime()));
		      jsonObject.put("sendLaterTime_datetime", DateUtils.getDateTime(model.getSendLaterTime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormNodeMessage model){
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
                if (model.getSubject() != null) {
        			jsonObject.put("subject", model.getSubject());
        		} 
                        if (model.getSendLaterTime() != null) {
                              jsonObject.put("sendLaterTime", DateUtils.getDate(model.getSendLaterTime()));
        		      jsonObject.put("sendLaterTime_date", DateUtils.getDate(model.getSendLaterTime()));
        		      jsonObject.put("sendLaterTime_datetime", DateUtils.getDateTime(model.getSendLaterTime()));
                        }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormNodeMessage> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormNodeMessage model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormNodeMessage> arrayToList(JSONArray array) {
		java.util.List<FormNodeMessage> list = new java.util.ArrayList<FormNodeMessage>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormNodeMessage model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormNodeMessageJsonFactory() {

	}

}
