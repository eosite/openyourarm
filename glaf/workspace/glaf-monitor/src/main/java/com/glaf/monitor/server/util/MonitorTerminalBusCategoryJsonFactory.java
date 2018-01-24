package com.glaf.monitor.server.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.monitor.server.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class MonitorTerminalBusCategoryJsonFactory {

	public static MonitorTerminalBusCategory jsonToObject(JSONObject jsonObject) {
            MonitorTerminalBusCategory model = new MonitorTerminalBusCategory();
            if (jsonObject.containsKey("terminalId")) {
		    model.setTerminalId(jsonObject.getString("terminalId"));
		}
		if (jsonObject.containsKey("categoryId")) {
			model.setCategoryId(jsonObject.getInteger("categoryId"));
		}
		if (jsonObject.containsKey("createby")) {
			model.setCreateby(jsonObject.getString("createby"));
		}
		if (jsonObject.containsKey("createtime")) {
			model.setCreatetime(jsonObject.getDate("createtime"));
		}

            return model;
	}

	public static JSONObject toJsonObject(MonitorTerminalBusCategory model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("terminalId", model.getTerminalId());
		jsonObject.put("_terminalId_", model.getTerminalId());
        jsonObject.put("categoryId", model.getCategoryId());
		if (model.getCreateby() != null) {
			jsonObject.put("createby", model.getCreateby());
		} 
                if (model.getCreatetime() != null) {
                      jsonObject.put("createtime", DateUtils.getDate(model.getCreatetime()));
		      jsonObject.put("createtime_date", DateUtils.getDate(model.getCreatetime()));
		      jsonObject.put("createtime_datetime", DateUtils.getDateTime(model.getCreatetime()));
                }
		return jsonObject;
	}


	public static ObjectNode toObjectNode(MonitorTerminalBusCategory model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("terminalId", model.getTerminalId());
		jsonObject.put("_terminalId_", model.getTerminalId());
                jsonObject.put("categoryId", model.getCategoryId());
                if (model.getCreateby() != null) {
                     jsonObject.put("createby", model.getCreateby());
                } 
                if (model.getCreatetime() != null) {
                      jsonObject.put("createtime", DateUtils.getDate(model.getCreatetime()));
		      jsonObject.put("createtime_date", DateUtils.getDate(model.getCreatetime()));
		      jsonObject.put("createtime_datetime", DateUtils.getDateTime(model.getCreatetime()));
                }
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<MonitorTerminalBusCategory> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MonitorTerminalBusCategory model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MonitorTerminalBusCategory> arrayToList(JSONArray array) {
		java.util.List<MonitorTerminalBusCategory> list = new java.util.ArrayList<MonitorTerminalBusCategory>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MonitorTerminalBusCategory model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private MonitorTerminalBusCategoryJsonFactory() {

	}

}
