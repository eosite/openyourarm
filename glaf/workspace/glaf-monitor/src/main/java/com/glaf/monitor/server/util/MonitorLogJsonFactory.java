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
public class MonitorLogJsonFactory {

	public static MonitorLog jsonToObject(JSONObject jsonObject) {
            MonitorLog model = new MonitorLog();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("logTime")) {
			model.setLogTime(jsonObject.getDate("logTime"));
		}
		if (jsonObject.containsKey("logPath")) {
			model.setLogPath(jsonObject.getString("logPath"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectType")) {
			model.setObjectType(jsonObject.getString("objectType"));
		}
		if (jsonObject.containsKey("createby")) {
			model.setCreateby(jsonObject.getString("createby"));
		}
		if (jsonObject.containsKey("createtime")) {
			model.setCreatetime(jsonObject.getDate("createtime"));
		}
		if (jsonObject.containsKey("updateby")) {
			model.setUpdateby(jsonObject.getString("updateby"));
		}
		if (jsonObject.containsKey("updatetime")) {
			model.setUpdatetime(jsonObject.getDate("updatetime"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(MonitorLog model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getLogTime() != null) {
                      jsonObject.put("logTime", DateUtils.getDate(model.getLogTime()));
		      jsonObject.put("logTime_date", DateUtils.getDate(model.getLogTime()));
		      jsonObject.put("logTime_datetime", DateUtils.getDateTime(model.getLogTime()));
                }
		if (model.getLogPath() != null) {
			jsonObject.put("logPath", model.getLogPath());
		} 
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		} 
		if (model.getObjectType() != null) {
			jsonObject.put("objectType", model.getObjectType());
		} 
		if (model.getCreateby() != null) {
			jsonObject.put("createby", model.getCreateby());
		} 
                if (model.getCreatetime() != null) {
                      jsonObject.put("createtime", DateUtils.getDate(model.getCreatetime()));
		      jsonObject.put("createtime_date", DateUtils.getDate(model.getCreatetime()));
		      jsonObject.put("createtime_datetime", DateUtils.getDateTime(model.getCreatetime()));
                }
		if (model.getUpdateby() != null) {
			jsonObject.put("updateby", model.getUpdateby());
		} 
                if (model.getUpdatetime() != null) {
                      jsonObject.put("updatetime", DateUtils.getDate(model.getUpdatetime()));
		      jsonObject.put("updatetime_date", DateUtils.getDate(model.getUpdatetime()));
		      jsonObject.put("updatetime_datetime", DateUtils.getDateTime(model.getUpdatetime()));
                }
        jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(MonitorLog model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getLogTime() != null) {
                      jsonObject.put("logTime", DateUtils.getDate(model.getLogTime()));
		      jsonObject.put("logTime_date", DateUtils.getDate(model.getLogTime()));
		      jsonObject.put("logTime_datetime", DateUtils.getDateTime(model.getLogTime()));
                }
                if (model.getLogPath() != null) {
                     jsonObject.put("logPath", model.getLogPath());
                } 
                if (model.getObjectId() != null) {
                     jsonObject.put("objectId", model.getObjectId());
                } 
                if (model.getObjectType() != null) {
                     jsonObject.put("objectType", model.getObjectType());
                } 
                if (model.getCreateby() != null) {
                     jsonObject.put("createby", model.getCreateby());
                } 
                if (model.getCreatetime() != null) {
                      jsonObject.put("createtime", DateUtils.getDate(model.getCreatetime()));
		      jsonObject.put("createtime_date", DateUtils.getDate(model.getCreatetime()));
		      jsonObject.put("createtime_datetime", DateUtils.getDateTime(model.getCreatetime()));
                }
                if (model.getUpdateby() != null) {
                     jsonObject.put("updateby", model.getUpdateby());
                } 
                if (model.getUpdatetime() != null) {
                      jsonObject.put("updatetime", DateUtils.getDate(model.getUpdatetime()));
		      jsonObject.put("updatetime_date", DateUtils.getDate(model.getUpdatetime()));
		      jsonObject.put("updatetime_datetime", DateUtils.getDateTime(model.getUpdatetime()));
                }
                jsonObject.put("deleteFlag", model.getDeleteFlag());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<MonitorLog> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MonitorLog model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MonitorLog> arrayToList(JSONArray array) {
		java.util.List<MonitorLog> list = new java.util.ArrayList<MonitorLog>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MonitorLog model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private MonitorLogJsonFactory() {

	}

}
