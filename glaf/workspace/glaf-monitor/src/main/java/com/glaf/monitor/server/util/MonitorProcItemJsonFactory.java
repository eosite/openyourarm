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
public class MonitorProcItemJsonFactory {

	public static MonitorProcItem jsonToObject(JSONObject jsonObject) {
            MonitorProcItem model = new MonitorProcItem();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("procId")) {
			model.setProcId(jsonObject.getString("procId"));
		}
		if (jsonObject.containsKey("code")) {
			model.setCode(jsonObject.getString("code"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("unit")) {
			model.setUnit(jsonObject.getString("unit"));
		}
		if (jsonObject.containsKey("alarmVal")) {
			model.setAlarmVal(jsonObject.getInteger("alarmVal"));
		}
		if (jsonObject.containsKey("refType")) {
			model.setRefType(jsonObject.getString("refType"));
		}
		if (jsonObject.containsKey("warningType")) {
			model.setWarningType(jsonObject.getString("warningType"));
		}
		if (jsonObject.containsKey("monitorServiceAddress")) {
			model.setMonitorServiceAddress(jsonObject.getString("monitorServiceAddress"));
		}
		if (jsonObject.containsKey("value")) {
			model.setValue(jsonObject.getString("value"));
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

	public static JSONObject toJsonObject(MonitorProcItem model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getProcId() != null) {
			jsonObject.put("procId", model.getProcId());
		} 
		if (model.getCode() != null) {
			jsonObject.put("code", model.getCode());
		} 
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getUnit() != null) {
			jsonObject.put("unit", model.getUnit());
		} 
        jsonObject.put("alarmVal", model.getAlarmVal());
		if (model.getRefType() != null) {
			jsonObject.put("refType", model.getRefType());
		} 
		if (model.getWarningType() != null) {
			jsonObject.put("warningType", model.getWarningType());
		} 
		if (model.getMonitorServiceAddress() != null) {
			jsonObject.put("monitorServiceAddress", model.getMonitorServiceAddress());
		} 
		if (model.getValue() != null) {
			jsonObject.put("value", model.getValue());
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


	public static ObjectNode toObjectNode(MonitorProcItem model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getProcId() != null) {
                     jsonObject.put("procId", model.getProcId());
                } 
                if (model.getCode() != null) {
                     jsonObject.put("code", model.getCode());
                } 
                if (model.getType() != null) {
        			jsonObject.put("type", model.getType());
        		} 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getUnit() != null) {
                     jsonObject.put("unit", model.getUnit());
                } 
                jsonObject.put("alarmVal", model.getAlarmVal());
                if (model.getRefType() != null) {
                     jsonObject.put("refType", model.getRefType());
                } 
                if (model.getWarningType() != null) {
                     jsonObject.put("warningType", model.getWarningType());
                } 
                if (model.getMonitorServiceAddress() != null) {
                     jsonObject.put("monitorServiceAddress", model.getMonitorServiceAddress());
                } 
                if (model.getValue() != null) {
        			jsonObject.put("value", model.getValue());
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

	
	public static JSONArray listToArray(java.util.List<MonitorProcItem> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MonitorProcItem model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MonitorProcItem> arrayToList(JSONArray array) {
		java.util.List<MonitorProcItem> list = new java.util.ArrayList<MonitorProcItem>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MonitorProcItem model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private MonitorProcItemJsonFactory() {

	}

}
