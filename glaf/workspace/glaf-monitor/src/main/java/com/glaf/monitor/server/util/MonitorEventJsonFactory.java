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
public class MonitorEventJsonFactory {

	public static MonitorEvent jsonToObject(JSONObject jsonObject) {
            MonitorEvent model = new MonitorEvent();
            if (jsonObject.containsKey("eventId")) {
		    model.setEventId(jsonObject.getString("eventId"));
		}
		if (jsonObject.containsKey("objectId")) {
			model.setObjectId(jsonObject.getString("objectId"));
		}
		if (jsonObject.containsKey("objectType")) {
			model.setObjectType(jsonObject.getString("objectType"));
		}
		if (jsonObject.containsKey("eventType")) {
			model.setEventType(jsonObject.getString("eventType"));
		}
		if (jsonObject.containsKey("eventMonitorItem")) {
			model.setEventMonitorItem(jsonObject.getString("eventMonitorItem"));
		}
		if (jsonObject.containsKey("happenTime")) {
			model.setHappenTime(jsonObject.getDate("happenTime"));
		}
		if (jsonObject.containsKey("snapshot")) {
			model.setSnapshot(jsonObject.getString("snapshot"));
		}
		if (jsonObject.containsKey("recoveryTime")) {
			model.setRecoveryTime(jsonObject.getDate("recoveryTime"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
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

	public static JSONObject toJsonObject(MonitorEvent model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("eventId", model.getEventId());
		jsonObject.put("_eventId_", model.getEventId());
		if (model.getObjectId() != null) {
			jsonObject.put("objectId", model.getObjectId());
		} 
		if (model.getObjectType() != null) {
			jsonObject.put("objectType", model.getObjectType());
		} 
		if (model.getEventType() != null) {
			jsonObject.put("eventType", model.getEventType());
		} 
		if (model.getEventMonitorItem() != null) {
			jsonObject.put("eventMonitorItem", model.getEventMonitorItem());
		} 
                if (model.getHappenTime() != null) {
                      jsonObject.put("happenTime", DateUtils.getDate(model.getHappenTime()));
		      jsonObject.put("happenTime_date", DateUtils.getDate(model.getHappenTime()));
		      jsonObject.put("happenTime_datetime", DateUtils.getDateTime(model.getHappenTime()));
                }
		if (model.getSnapshot() != null) {
			jsonObject.put("snapshot", model.getSnapshot());
		} 
                if (model.getRecoveryTime() != null) {
                      jsonObject.put("recoveryTime", DateUtils.getDate(model.getRecoveryTime()));
		      jsonObject.put("recoveryTime_date", DateUtils.getDate(model.getRecoveryTime()));
		      jsonObject.put("recoveryTime_datetime", DateUtils.getDateTime(model.getRecoveryTime()));
                }
        jsonObject.put("status", model.getStatus());
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


	public static ObjectNode toObjectNode(MonitorEvent model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("eventId", model.getEventId());
		jsonObject.put("_eventId_", model.getEventId());
                if (model.getObjectId() != null) {
                     jsonObject.put("objectId", model.getObjectId());
                } 
                if (model.getObjectType() != null) {
                     jsonObject.put("objectType", model.getObjectType());
                } 
                if (model.getEventType() != null) {
                     jsonObject.put("eventType", model.getEventType());
                } 
                if (model.getEventMonitorItem() != null) {
                     jsonObject.put("eventMonitorItem", model.getEventMonitorItem());
                } 
                if (model.getHappenTime() != null) {
                      jsonObject.put("happenTime", DateUtils.getDate(model.getHappenTime()));
		      jsonObject.put("happenTime_date", DateUtils.getDate(model.getHappenTime()));
		      jsonObject.put("happenTime_datetime", DateUtils.getDateTime(model.getHappenTime()));
                }
                if (model.getSnapshot() != null) {
                     jsonObject.put("snapshot", model.getSnapshot());
                } 
                if (model.getRecoveryTime() != null) {
                      jsonObject.put("recoveryTime", DateUtils.getDate(model.getRecoveryTime()));
		      jsonObject.put("recoveryTime_date", DateUtils.getDate(model.getRecoveryTime()));
		      jsonObject.put("recoveryTime_datetime", DateUtils.getDateTime(model.getRecoveryTime()));
                }
                jsonObject.put("status", model.getStatus());
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

	
	public static JSONArray listToArray(java.util.List<MonitorEvent> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MonitorEvent model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MonitorEvent> arrayToList(JSONArray array) {
		java.util.List<MonitorEvent> list = new java.util.ArrayList<MonitorEvent>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MonitorEvent model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private MonitorEventJsonFactory() {

	}

}
