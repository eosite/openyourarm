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
public class MonitorTerminalJsonFactory {

	public static MonitorTerminal jsonToObject(JSONObject jsonObject) {
            MonitorTerminal model = new MonitorTerminal();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("name")) {
			model.setName(jsonObject.getString("name"));
		}
		if (jsonObject.containsKey("type")) {
			model.setType(jsonObject.getString("type"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("level")) {
			model.setLevel(jsonObject.getString("level"));
		}
		if (jsonObject.containsKey("prod")) {
			model.setProd(jsonObject.getString("prod"));
		}
		if (jsonObject.containsKey("domain")) {
			model.setDomain(jsonObject.getString("domain"));
		}
		if (jsonObject.containsKey("address")) {
			model.setAddress(jsonObject.getString("address"));
		}
		if (jsonObject.containsKey("monitorServiceAddress")) {
			model.setMonitorServiceAddress(jsonObject.getString("monitorServiceAddress"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
		}
		if (jsonObject.containsKey("platform")) {
			model.setPlatform(jsonObject.getString("platform"));
		}
		if (jsonObject.containsKey("osName")) {
			model.setOsName(jsonObject.getString("osName"));
		}
		if (jsonObject.containsKey("osFac")) {
			model.setOsFac(jsonObject.getString("osFac"));
		}
		if (jsonObject.containsKey("osVer")) {
			model.setOsVer(jsonObject.getString("osVer"));
		}
		if (jsonObject.containsKey("cpuFac")) {
			model.setCpuFac(jsonObject.getString("cpuFac"));
		}
		if (jsonObject.containsKey("cpuCores")) {
			model.setCpuCores(jsonObject.getInteger("cpuCores"));
		}
		if (jsonObject.containsKey("coreMhz")) {
			model.setCoreMhz(jsonObject.getInteger("coreMhz"));
		}
		if (jsonObject.containsKey("memType")) {
			model.setMemType(jsonObject.getString("memType"));
		}
		if (jsonObject.containsKey("memSize")) {
			model.setMemSize(jsonObject.getLong("memSize"));
		}
		if (jsonObject.containsKey("diskType")) {
			model.setDiskType(jsonObject.getString("diskType"));
		}
		if (jsonObject.containsKey("diskSize")) {
			model.setDiskSize(jsonObject.getLong("diskSize"));
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

            return model;
	}

	public static JSONObject toJsonObject(MonitorTerminal model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getName() != null) {
			jsonObject.put("name", model.getName());
		} 
		if (model.getType() != null) {
			jsonObject.put("type", model.getType());
		} 
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		} 
		if (model.getLevel() != null) {
			jsonObject.put("level", model.getLevel());
		} 
		if (model.getProd() != null) {
			jsonObject.put("prod", model.getProd());
		} 
		if (model.getDomain() != null) {
			jsonObject.put("domain", model.getDomain());
		} 
		if (model.getAddress() != null) {
			jsonObject.put("address", model.getAddress());
		} 
		if (model.getMonitorServiceAddress() != null) {
			jsonObject.put("monitorServiceAddress", model.getMonitorServiceAddress());
		} 
        jsonObject.put("status", model.getStatus());
		if (model.getPlatform() != null) {
			jsonObject.put("platform", model.getPlatform());
		} 
		if (model.getOsName() != null) {
			jsonObject.put("osName", model.getOsName());
		} 
		if (model.getOsFac() != null) {
			jsonObject.put("osFac", model.getOsFac());
		} 
		if (model.getOsVer() != null) {
			jsonObject.put("osVer", model.getOsVer());
		} 
		if (model.getCpuFac() != null) {
			jsonObject.put("cpuFac", model.getCpuFac());
		} 
        jsonObject.put("cpuCores", model.getCpuCores());
        jsonObject.put("coreMhz", model.getCoreMhz());
		if (model.getMemType() != null) {
			jsonObject.put("memType", model.getMemType());
		} 
        jsonObject.put("memSize", model.getMemSize());
		if (model.getDiskType() != null) {
			jsonObject.put("diskType", model.getDiskType());
		} 
        jsonObject.put("diskSize", model.getDiskSize());
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
		return jsonObject;
	}


	public static ObjectNode toObjectNode(MonitorTerminal model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getName() != null) {
                     jsonObject.put("name", model.getName());
                } 
                if (model.getType() != null) {
                     jsonObject.put("type", model.getType());
                } 
                if (model.getDesc() != null) {
                     jsonObject.put("desc", model.getDesc());
                } 
                if (model.getLevel() != null) {
                     jsonObject.put("level", model.getLevel());
                } 
                if (model.getProd() != null) {
                     jsonObject.put("prod", model.getProd());
                } 
                if (model.getDomain() != null) {
                     jsonObject.put("domain", model.getDomain());
                } 
                if (model.getAddress() != null) {
                     jsonObject.put("address", model.getAddress());
                } 
                if (model.getMonitorServiceAddress() != null) {
                     jsonObject.put("monitorServiceAddress", model.getMonitorServiceAddress());
                } 
                jsonObject.put("status", model.getStatus());
                if (model.getPlatform() != null) {
                     jsonObject.put("platform", model.getPlatform());
                } 
                if (model.getOsName() != null) {
                     jsonObject.put("osName", model.getOsName());
                } 
                if (model.getOsFac() != null) {
                     jsonObject.put("osFac", model.getOsFac());
                } 
                if (model.getOsVer() != null) {
                     jsonObject.put("osVer", model.getOsVer());
                } 
                if (model.getCpuFac() != null) {
                     jsonObject.put("cpuFac", model.getCpuFac());
                } 
                jsonObject.put("cpuCores", model.getCpuCores());
                jsonObject.put("coreMhz", model.getCoreMhz());
                if (model.getMemType() != null) {
                     jsonObject.put("memType", model.getMemType());
                } 
                jsonObject.put("memSize", model.getMemSize());
                if (model.getDiskType() != null) {
                     jsonObject.put("diskType", model.getDiskType());
                } 
                jsonObject.put("diskSize", model.getDiskSize());
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
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<MonitorTerminal> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (MonitorTerminal model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<MonitorTerminal> arrayToList(JSONArray array) {
		java.util.List<MonitorTerminal> list = new java.util.ArrayList<MonitorTerminal>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			MonitorTerminal model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private MonitorTerminalJsonFactory() {

	}

}
