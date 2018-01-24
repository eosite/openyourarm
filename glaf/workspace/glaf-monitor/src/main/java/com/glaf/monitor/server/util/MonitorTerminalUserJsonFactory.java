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
public class MonitorTerminalUserJsonFactory {

	public static MonitorTerminalUser jsonToObject(JSONObject jsonObject) {
            MonitorTerminalUser model = new MonitorTerminalUser();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("terminalId")) {
			model.setTerminalId(jsonObject.getString("terminalId"));
		}
		if (jsonObject.containsKey("role")) {
			model.setRole(jsonObject.getString("role"));
		}
		if (jsonObject.containsKey("username")) {
			model.setUsername(jsonObject.getString("username"));
		}
		if (jsonObject.containsKey("tel")) {
			model.setTel(jsonObject.getString("tel"));
		}
		if (jsonObject.containsKey("phone")) {
			model.setPhone(jsonObject.getString("phone"));
		}
		if (jsonObject.containsKey("email")) {
			model.setEmail(jsonObject.getString("email"));
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

	public static JSONObject toJsonObject(MonitorTerminalUser model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTerminalId() != null) {
			jsonObject.put("terminalId", model.getTerminalId());
		} 
		if (model.getRole() != null) {
			jsonObject.put("role", model.getRole());
		} 
		if (model.getUsername() != null) {
			jsonObject.put("username", model.getUsername());
		} 
		if (model.getTel() != null) {
			jsonObject.put("tel", model.getTel());
		} 
		if (model.getPhone() != null) {
			jsonObject.put("phone", model.getPhone());
		} 
		if (model.getEmail() != null) {
			jsonObject.put("email", model.getEmail());
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


	public static ObjectNode toObjectNode(MonitorTerminalUser model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getTerminalId() != null) {
                     jsonObject.put("terminalId", model.getTerminalId());
                } 
                if (model.getRole() != null) {
                     jsonObject.put("role", model.getRole());
                } 
                if (model.getUsername() != null) {
                     jsonObject.put("username", model.getUsername());
                } 
                if (model.getTel() != null) {
                     jsonObject.put("tel", model.getTel());
                } 
                if (model.getPhone() != null) {
                     jsonObject.put("phone", model.getPhone());
                } 
                if (model.getEmail() != null) {
                     jsonObject.put("email", model.getEmail());
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

	
	public static JSONArray listToArray(java.util.List<MonitorTerminalUser> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MonitorTerminalUser model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MonitorTerminalUser> arrayToList(JSONArray array) {
		java.util.List<MonitorTerminalUser> list = new java.util.ArrayList<MonitorTerminalUser>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MonitorTerminalUser model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private MonitorTerminalUserJsonFactory() {

	}

}
