package com.glaf.form.core.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
 
import com.glaf.core.util.DateUtils;
import com.glaf.form.core.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class FormVideoJsonFactory {

	public static FormVideo jsonToObject(JSONObject jsonObject) {
            FormVideo model = new FormVideo();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("ip")) {
			model.setIp(jsonObject.getString("ip"));
		}
		if (jsonObject.containsKey("port")) {
			model.setPort(jsonObject.getString("port"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("valided")) {
			model.setValided(jsonObject.getInteger("valided"));
		}
		if (jsonObject.containsKey("userName")) {
			model.setUserName(jsonObject.getString("userName"));
		}
		if (jsonObject.containsKey("pwd")) {
			model.setPwd(jsonObject.getString("pwd"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("createDate")) {
			model.setCreateDate(jsonObject.getDate("createDate"));
		}
		if (jsonObject.containsKey("updateDate")) {
			model.setUpdateDate(jsonObject.getDate("updateDate"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}

            return model;
	}

	public static JSONObject toJsonObject(FormVideo model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getIp() != null) {
			jsonObject.put("ip", model.getIp());
		} 
		if (model.getPort() != null) {
			jsonObject.put("port", model.getPort());
		} 
        jsonObject.put("status", model.getStatus());
        jsonObject.put("valided", model.getValided());
		if (model.getUserName() != null) {
			jsonObject.put("userName", model.getUserName());
		} 
		if (model.getPwd() != null) {
			jsonObject.put("pwd", model.getPwd());
		} 
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		} 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(FormVideo model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getIp() != null) {
                     jsonObject.put("ip", model.getIp());
                } 
                if (model.getPort() != null) {
                     jsonObject.put("port", model.getPort());
                } 
                jsonObject.put("status", model.getStatus());
                jsonObject.put("valided", model.getValided());
                if (model.getUserName() != null) {
                     jsonObject.put("userName", model.getUserName());
                } 
                if (model.getPwd() != null) {
                     jsonObject.put("pwd", model.getPwd());
                } 
                if (model.getUpdateBy() != null) {
                     jsonObject.put("updateBy", model.getUpdateBy());
                } 
                if (model.getCreateDate() != null) {
                      jsonObject.put("createDate", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_date", DateUtils.getDate(model.getCreateDate()));
		      jsonObject.put("createDate_datetime", DateUtils.getDateTime(model.getCreateDate()));
                }
                if (model.getUpdateDate() != null) {
                      jsonObject.put("updateDate", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_date", DateUtils.getDate(model.getUpdateDate()));
		      jsonObject.put("updateDate_datetime", DateUtils.getDateTime(model.getUpdateDate()));
                }
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<FormVideo> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (FormVideo model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<FormVideo> arrayToList(JSONArray array) {
		java.util.List<FormVideo> list = new java.util.ArrayList<FormVideo>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			FormVideo model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private FormVideoJsonFactory() {

	}

}
