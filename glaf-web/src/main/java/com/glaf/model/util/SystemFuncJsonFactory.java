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
public class SystemFuncJsonFactory {

	public static SystemFunc jsonToObject(JSONObject jsonObject) {
            SystemFunc model = new SystemFunc();
            if (jsonObject.containsKey("funcId")) {
		    model.setFuncId(jsonObject.getString("funcId"));
		}
		if (jsonObject.containsKey("sysId")) {
			model.setSysId(jsonObject.getString("sysId"));
		}
		if (jsonObject.containsKey("funcCode")) {
			model.setFuncCode(jsonObject.getString("funcCode"));
		}
		if (jsonObject.containsKey("funcName")) {
			model.setFuncName(jsonObject.getString("funcName"));
		}
		if (jsonObject.containsKey("funcType")) {
			model.setFuncType(jsonObject.getString("funcType"));
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

	public static JSONObject toJsonObject(SystemFunc model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("funcId", model.getFuncId());
		jsonObject.put("_funcId_", model.getFuncId());
        jsonObject.put("sysId", model.getSysId());
		if (model.getFuncCode() != null) {
			jsonObject.put("funcCode", model.getFuncCode());
		} 
		if (model.getFuncName() != null) {
			jsonObject.put("funcName", model.getFuncName());
		} 
		if (model.getFuncType() != null) {
			jsonObject.put("funcType", model.getFuncType());
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


	public static ObjectNode toObjectNode(SystemFunc model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("funcId", model.getFuncId());
		jsonObject.put("_funcId_", model.getFuncId());
                jsonObject.put("sysId", model.getSysId());
                if (model.getFuncCode() != null) {
                     jsonObject.put("funcCode", model.getFuncCode());
                } 
                if (model.getFuncName() != null) {
                     jsonObject.put("funcName", model.getFuncName());
                } 
                if (model.getFuncType() != null) {
                     jsonObject.put("funcType", model.getFuncType());
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

	
	public static JSONArray listToArray(java.util.List<SystemFunc> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SystemFunc model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SystemFunc> arrayToList(JSONArray array) {
		java.util.List<SystemFunc> list = new java.util.ArrayList<SystemFunc>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SystemFunc model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SystemFuncJsonFactory() {

	}

}
