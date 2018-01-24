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
public class UisAppRegisterJsonFactory {

	public static UisAppRegister jsonToObject(JSONObject jsonObject) {
            UisAppRegister model = new UisAppRegister();
            if (jsonObject.containsKey("appId")) {
		    model.setAppId(jsonObject.getString("appId"));
		}
		if (jsonObject.containsKey("appName")) {
			model.setAppName(jsonObject.getString("appName"));
		}
		if (jsonObject.containsKey("loginAddress")) {
			model.setLoginAddress(jsonObject.getString("loginAddress"));
		}
		if (jsonObject.containsKey("ssoServiceId")) {
			model.setSsoServiceId(jsonObject.getString("ssoServiceId"));
		}
		if (jsonObject.containsKey("desc")) {
			model.setDesc(jsonObject.getString("desc"));
		}
		if (jsonObject.containsKey("status")) {
			model.setStatus(jsonObject.getInteger("status"));
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

	public static JSONObject toJsonObject(UisAppRegister model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("_appId_", model.getAppId());
		if (model.getAppName() != null) {
			jsonObject.put("appName", model.getAppName());
		} 
		if (model.getLoginAddress() != null) {
			jsonObject.put("loginAddress", model.getLoginAddress());
		} 
		if (model.getSsoServiceId() != null) {
			jsonObject.put("ssoServiceId", model.getSsoServiceId());
		} 
		if (model.getDesc() != null) {
			jsonObject.put("desc", model.getDesc());
		} 
		if (model.getLogoPic() != null) {
			jsonObject.put("logoPic", model.getLogoPic());
		} 
        jsonObject.put("status", model.getStatus());
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


	public static ObjectNode toObjectNode(UisAppRegister model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("appId", model.getAppId());
		jsonObject.put("_appId_", model.getAppId());
                if (model.getAppName() != null) {
                     jsonObject.put("appName", model.getAppName());
                } 
                if (model.getLoginAddress() != null) {
                     jsonObject.put("loginAddress", model.getLoginAddress());
                } 
                if (model.getSsoServiceId() != null) {
                     jsonObject.put("ssoServiceId", model.getSsoServiceId());
                } 
                if (model.getDesc() != null) {
                     jsonObject.put("desc", model.getDesc());
                } 
                jsonObject.put("status", model.getStatus());
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

	
	public static JSONArray listToArray(java.util.List<UisAppRegister> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (UisAppRegister model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<UisAppRegister> arrayToList(JSONArray array) {
		java.util.List<UisAppRegister> list = new java.util.ArrayList<UisAppRegister>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			UisAppRegister model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private UisAppRegisterJsonFactory() {

	}

}
