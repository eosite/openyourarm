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
public class MonitorProcJsonFactory {

	public static MonitorProc jsonToObject(JSONObject jsonObject) {
            MonitorProc model = new MonitorProc();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("terminalId")) {
			model.setTerminalId(jsonObject.getString("terminalId"));
		}
		if (jsonObject.containsKey("level")) {
			model.setLevel(jsonObject.getString("level"));
		}
		if (jsonObject.containsKey("processName")) {
			model.setProcessName(jsonObject.getString("processName"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("prod")) {
			model.setProd(jsonObject.getString("prod"));
		}
		if (jsonObject.containsKey("ver")) {
			model.setVer(jsonObject.getString("ver"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("port")) {
			model.setPort(jsonObject.getInteger("port"));
		}
		if (jsonObject.containsKey("monitorServiceAddress")) {
			model.setMonitorServiceAddress(jsonObject.getString("monitorServiceAddress"));
		}
		if (jsonObject.containsKey("startAddress")) {
			model.setStartAddress(jsonObject.getString("startAddress"));
		}
		if (jsonObject.containsKey("stopAddress")) {
			model.setStopAddress(jsonObject.getString("stopAddress"));
		}
		if (jsonObject.containsKey("terminateAddress")) {
			model.setTerminateAddress(jsonObject.getString("terminateAddress"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("parentProcId")) {
			model.setParentProcId(jsonObject.getString("parentProcId"));
		}
		if (jsonObject.containsKey("otherItems")) {
			model.setOtherItems(jsonObject.getString("otherItems"));
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
		
		if (jsonObject.containsKey("startCommand")) {
			model.setStartCommand(jsonObject.getString("startCommand"));
		}
		if (jsonObject.containsKey("stopCommand")) {
			model.setStopCommand(jsonObject.getString("stopCommand"));
		}
		if (jsonObject.containsKey("terminateCommand")) {
			model.setTerminateCommand(jsonObject.getString("terminateCommand"));
		}

            return model;
	}

	public static JSONObject toJsonObject(MonitorProc model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getTerminalId() != null) {
			jsonObject.put("terminalId", model.getTerminalId());
		} 
		if (model.getLevel() != null) {
			jsonObject.put("level", model.getLevel());
		} 
		if (model.getProcessName() != null) {
			jsonObject.put("processName", model.getProcessName());
		} 
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getProd() != null) {
			jsonObject.put("prod", model.getProd());
		} 
		if (model.getVer() != null) {
			jsonObject.put("ver", model.getVer());
		} 
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		} 
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		} 
        jsonObject.put("port", model.getPort());
		if (model.getMonitorServiceAddress() != null) {
			jsonObject.put("monitorServiceAddress", model.getMonitorServiceAddress());
		} 
		if (model.getStartAddress() != null) {
			jsonObject.put("startAddress", model.getStartAddress());
		} 
		if (model.getStopAddress() != null) {
			jsonObject.put("stopAddress", model.getStopAddress());
		} 
		if (model.getTerminateAddress() != null) {
			jsonObject.put("terminateAddress", model.getTerminateAddress());
		} 
        jsonObject.put("status", model.getStatus());
		if (model.getParentProcId() != null) {
			jsonObject.put("parentProcId", model.getParentProcId());
		} 
		if (model.getOtherItems() != null) {
			jsonObject.put("otherItems", model.getOtherItems());
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
        
        
		if (model.getStartCommand() != null) {
			jsonObject.put("startCommand", model.getStartCommand());
		} 
		if (model.getStopCommand() != null) {
			jsonObject.put("stopCommand", model.getStopCommand());
		} 
		if (model.getTerminateCommand() != null) {
			jsonObject.put("terminateCommand", model.getTerminateCommand());
		} 
		
		return jsonObject;
	}


	public static ObjectNode toObjectNode(MonitorProc model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getTerminalId() != null) {
                     jsonObject.put("terminalId", model.getTerminalId());
                } 
                if (model.getLevel() != null) {
                     jsonObject.put("level", model.getLevel());
                } 
                if (model.getProcessName() != null) {
                     jsonObject.put("processName", model.getProcessName());
                } 
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getProd() != null) {
                     jsonObject.put("prod", model.getProd());
                } 
                if (model.getVer() != null) {
                     jsonObject.put("ver", model.getVer());
                } 
                if (model.getType() != null) {
                     jsonObject.put("type", model.getType());
                } 
                if (model.getDesc() != null) {
                     jsonObject.put("desc", model.getDesc());
                } 
                jsonObject.put("port", model.getPort());
                if (model.getMonitorServiceAddress() != null) {
                     jsonObject.put("monitorServiceAddress", model.getMonitorServiceAddress());
                } 
                if (model.getStartAddress() != null) {
                     jsonObject.put("startAddress", model.getStartAddress());
                } 
                if (model.getStopAddress() != null) {
                     jsonObject.put("stopAddress", model.getStopAddress());
                } 
                if (model.getTerminateAddress() != null) {
                     jsonObject.put("terminateAddress", model.getTerminateAddress());
                } 
                jsonObject.put("status", model.getStatus());
                if (model.getParentProcId() != null) {
                     jsonObject.put("parentProcId", model.getParentProcId());
                } 
                if (model.getOtherItems() != null) {
                     jsonObject.put("otherItems", model.getOtherItems());
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
                
                if (model.getStartCommand() != null) {
        			jsonObject.put("startCommand", model.getStartCommand());
        		} 
        		if (model.getStopCommand() != null) {
        			jsonObject.put("stopCommand", model.getStopCommand());
        		} 
        		if (model.getTerminateCommand() != null) {
        			jsonObject.put("terminateCommand", model.getTerminateCommand());
        		} 
        		
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<MonitorProc> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MonitorProc model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MonitorProc> arrayToList(JSONArray array) {
		java.util.List<MonitorProc> list = new java.util.ArrayList<MonitorProc>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MonitorProc model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private MonitorProcJsonFactory() {

	}

}
