package com.glaf.model.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.model.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class SystemProcDefJsonFactory {

	public static SystemProcDef jsonToObject(JSONObject jsonObject) {
            SystemProcDef model = new SystemProcDef();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("funcId")) {
			model.setFuncId(jsonObject.getString("funcId"));
		}
		if (jsonObject.containsKey("sysId")) {
			model.setSysId(jsonObject.getString("sysId"));
		}
		if (jsonObject.containsKey("subSysId")) {
			model.setSubSysId(jsonObject.getString("subSysId"));
		}
		if (jsonObject.containsKey("currProcDefKey")) {
			model.setCurrProcDefKey(jsonObject.getString("currProcDefKey"));
		}
		if (jsonObject.containsKey("currProcDefId")) {
			model.setCurrProcDefId(jsonObject.getString("currProcDefId"));
		}
		if (jsonObject.containsKey("currProcModelId")) {
			model.setCurrProcModelId(jsonObject.getString("currProcModelId"));
		}
		if (jsonObject.containsKey("currProcDeployId")) {
			model.setCurrProcDeployId(jsonObject.getString("currProcDeployId"));
		}
		if (jsonObject.containsKey("currProcDeployStatus")) {
			model.setCurrProcDeployStatus(jsonObject.getString("currProcDeployStatus"));
		}
		if (jsonObject.containsKey("procDefKey")) {
			model.setProcDefKey(jsonObject.getString("procDefKey"));
		}
		if (jsonObject.containsKey("procDefId")) {
			model.setProcDefId(jsonObject.getString("procDefId"));
		}
		if (jsonObject.containsKey("procModelId")) {
			model.setProcModelId(jsonObject.getString("procModelId"));
		}
		if (jsonObject.containsKey("procDeployId")) {
			model.setProcDeployId(jsonObject.getString("procDeployId"));
		}
		if (jsonObject.containsKey("procDeployStatus")) {
			model.setProcDeployStatus(jsonObject.getString("procDeployStatus"));
		}
		if (jsonObject.containsKey("eleType")) {
			model.setEleType(jsonObject.getString("eleType"));
		}
		if (jsonObject.containsKey("eleResourceId")) {
			model.setEleResourceId(jsonObject.getString("eleResourceId"));
		}
		if (jsonObject.containsKey("eleId")) {
			model.setEleId(jsonObject.getString("eleId"));
		}
		if (jsonObject.containsKey("eleName")) {
			model.setEleName(jsonObject.getString("eleName"));
		}
		if (jsonObject.containsKey("eleDesc")) {
			model.setEleDesc(jsonObject.getString("eleDesc"));
		}
		if (jsonObject.containsKey("createBy")) {
			model.setCreateBy(jsonObject.getString("createBy"));
		}
		if (jsonObject.containsKey("createTime")) {
			model.setCreateTime(jsonObject.getDate("createTime"));
		}
		if (jsonObject.containsKey("updateBy")) {
			model.setUpdateBy(jsonObject.getString("updateBy"));
		}
		if (jsonObject.containsKey("updateTime")) {
			model.setUpdateTime(jsonObject.getDate("updateTime"));
		}
		if (jsonObject.containsKey("deleteFlag")) {
			model.setDeleteFlag(jsonObject.getInteger("deleteFlag"));
		}

            return model;
	}

	public static JSONObject toJsonObject(SystemProcDef model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
        jsonObject.put("funcId", model.getFuncId());
        jsonObject.put("sysId", model.getSysId());
        jsonObject.put("subSysId", model.getSubSysId());
		if (model.getCurrProcDefKey() != null) {
			jsonObject.put("currProcDefKey", model.getCurrProcDefKey());
		} 
		if (model.getCurrProcDefId() != null) {
			jsonObject.put("currProcDefId", model.getCurrProcDefId());
		} 
		if (model.getCurrProcModelId() != null) {
			jsonObject.put("currProcModelId", model.getCurrProcModelId());
		} 
		if (model.getCurrProcDeployId() != null) {
			jsonObject.put("currProcDeployId", model.getCurrProcDeployId());
		} 
		if (model.getCurrProcDeployStatus() != null) {
			jsonObject.put("currProcDeployStatus", model.getCurrProcDeployStatus());
		} 
		if (model.getProcDefKey() != null) {
			jsonObject.put("procDefKey", model.getProcDefKey());
		} 
		if (model.getProcDefId() != null) {
			jsonObject.put("procDefId", model.getProcDefId());
		} 
		if (model.getProcModelId() != null) {
			jsonObject.put("procModelId", model.getProcModelId());
		} 
		if (model.getProcDeployId() != null) {
			jsonObject.put("procDeployId", model.getProcDeployId());
		} 
		if (model.getProcDeployStatus() != null) {
			jsonObject.put("procDeployStatus", model.getProcDeployStatus());
		} 
		if (model.getEleType() != null) {
			jsonObject.put("eleType", model.getEleType());
		} 
		if (model.getEleResourceId() != null) {
			jsonObject.put("eleResourceId", model.getEleResourceId());
		} 
		if (model.getEleId() != null) {
			jsonObject.put("eleId", model.getEleId());
		} 
		if (model.getEleName() != null) {
			jsonObject.put("eleName", model.getEleName());
		} 
		if (model.getEleDesc() != null) {
			jsonObject.put("eleDesc", model.getEleDesc());
		} 
		if (model.getCreateBy() != null) {
			jsonObject.put("createBy", model.getCreateBy());
		} 
                if (model.getCreateTime() != null) {
                      jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
                }
		if (model.getUpdateBy() != null) {
			jsonObject.put("updateBy", model.getUpdateBy());
		} 
                if (model.getUpdateTime() != null) {
                      jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
                }
        jsonObject.put("deleteFlag", model.getDeleteFlag());
		return jsonObject;
	}


	public static ObjectNode toObjectNode(SystemProcDef model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                jsonObject.put("funcId", model.getFuncId());
                jsonObject.put("sysId", model.getSysId());
                jsonObject.put("subSysId", model.getSubSysId());
                if (model.getCurrProcDefKey() != null) {
                     jsonObject.put("currProcDefKey", model.getCurrProcDefKey());
                } 
                if (model.getCurrProcDefId() != null) {
                     jsonObject.put("currProcDefId", model.getCurrProcDefId());
                } 
                if (model.getCurrProcModelId() != null) {
                     jsonObject.put("currProcModelId", model.getCurrProcModelId());
                } 
                if (model.getCurrProcDeployId() != null) {
                     jsonObject.put("currProcDeployId", model.getCurrProcDeployId());
                } 
                if (model.getCurrProcDeployStatus() != null) {
                     jsonObject.put("currProcDeployStatus", model.getCurrProcDeployStatus());
                } 
                if (model.getProcDefKey() != null) {
                     jsonObject.put("procDefKey", model.getProcDefKey());
                } 
                if (model.getProcDefId() != null) {
                     jsonObject.put("procDefId", model.getProcDefId());
                } 
                if (model.getProcModelId() != null) {
                     jsonObject.put("procModelId", model.getProcModelId());
                } 
                if (model.getProcDeployId() != null) {
                     jsonObject.put("procDeployId", model.getProcDeployId());
                } 
                if (model.getProcDeployStatus() != null) {
                     jsonObject.put("procDeployStatus", model.getProcDeployStatus());
                } 
                if (model.getEleType() != null) {
                     jsonObject.put("eleType", model.getEleType());
                } 
                if (model.getEleResourceId() != null) {
                     jsonObject.put("eleResourceId", model.getEleResourceId());
                } 
                if (model.getEleId() != null) {
                     jsonObject.put("eleId", model.getEleId());
                } 
                if (model.getEleName() != null) {
                     jsonObject.put("eleName", model.getEleName());
                } 
                if (model.getEleDesc() != null) {
                     jsonObject.put("eleDesc", model.getEleDesc());
                } 
                if (model.getCreateBy() != null) {
                     jsonObject.put("createBy", model.getCreateBy());
                } 
                if (model.getCreateTime() != null) {
                      jsonObject.put("createTime", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_date", DateUtils.getDate(model.getCreateTime()));
		      jsonObject.put("createTime_datetime", DateUtils.getDateTime(model.getCreateTime()));
                }
                if (model.getUpdateBy() != null) {
                     jsonObject.put("updateBy", model.getUpdateBy());
                } 
                if (model.getUpdateTime() != null) {
                      jsonObject.put("updateTime", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_date", DateUtils.getDate(model.getUpdateTime()));
		      jsonObject.put("updateTime_datetime", DateUtils.getDateTime(model.getUpdateTime()));
                }
                jsonObject.put("deleteFlag", model.getDeleteFlag());
                return jsonObject;
	}

	
	public static JSONArray listToArray(java.util.List<SystemProcDef> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SystemProcDef model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SystemProcDef> arrayToList(JSONArray array) {
		java.util.List<SystemProcDef> list = new java.util.ArrayList<SystemProcDef>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SystemProcDef model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SystemProcDefJsonFactory() {

	}

}
