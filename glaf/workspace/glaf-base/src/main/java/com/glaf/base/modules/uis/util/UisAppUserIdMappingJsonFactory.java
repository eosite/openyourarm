package com.glaf.base.modules.uis.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.base.modules.uis.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class UisAppUserIdMappingJsonFactory {

	public static UisAppUserIdMapping jsonToObject(JSONObject jsonObject) {
            UisAppUserIdMapping model = new UisAppUserIdMapping();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getLong("id"));
		}
		if (jsonObject.containsKey("appUserId")) {
			model.setAppUserId(jsonObject.getString("appUserId"));
		}
		if (jsonObject.containsKey("userId")) {
			model.setUserId(jsonObject.getString("userId"));
		}
		if (jsonObject.containsKey("appId")) {
			model.setAppId(jsonObject.getString("appId"));
		}

            return model;
	}

	public static JSONObject toJsonObject(UisAppUserIdMapping model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getAppUserId() != null) {
			jsonObject.put("appUserId", model.getAppUserId());
		} 
		if (model.getUserId() != null) {
			jsonObject.put("userId", model.getUserId());
		} 
		if (model.getAppId() != null) {
			jsonObject.put("appId", model.getAppId());
		} 
		return jsonObject;
	}


	public static ObjectNode toObjectNode(UisAppUserIdMapping model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getAppUserId() != null) {
                     jsonObject.put("appUserId", model.getAppUserId());
                } 
                if (model.getUserId() != null) {
                     jsonObject.put("userId", model.getUserId());
                } 
                if (model.getAppId() != null) {
                     jsonObject.put("appId", model.getAppId());
                } 
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<UisAppUserIdMapping> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (UisAppUserIdMapping model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<UisAppUserIdMapping> arrayToList(JSONArray array) {
		java.util.List<UisAppUserIdMapping> list = new java.util.ArrayList<UisAppUserIdMapping>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			UisAppUserIdMapping model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private UisAppUserIdMappingJsonFactory() {

	}

}
