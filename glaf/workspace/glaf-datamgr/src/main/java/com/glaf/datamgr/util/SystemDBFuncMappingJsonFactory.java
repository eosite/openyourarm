package com.glaf.datamgr.util;

import com.alibaba.fastjson.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode; 
import com.glaf.core.base.*;
import com.glaf.core.util.DateUtils;
import com.glaf.datamgr.domain.*;


/**
 * 
 * JSON工厂类
 *
 */
public class SystemDBFuncMappingJsonFactory {

	public static SystemDBFuncMapping jsonToObject(JSONObject jsonObject) {
            SystemDBFuncMapping model = new SystemDBFuncMapping();
            if (jsonObject.containsKey("id")) {
		    model.setId(jsonObject.getString("id"));
		}
		if (jsonObject.containsKey("funcId")) {
			model.setFuncId(jsonObject.getString("funcId"));
		}
		if (jsonObject.containsKey("dtype")) {
			model.setDtype(jsonObject.getString("dtype"));
		}
		if (jsonObject.containsKey("funcName")) {
			model.setFuncName(jsonObject.getString("funcName"));
		}
		if (jsonObject.containsKey("params")) {
			model.setParams(jsonObject.getString("params"));
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

	public static JSONObject toJsonObject(SystemDBFuncMapping model) {
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
		if (model.getFuncId() != null) {
			jsonObject.put("funcId", model.getFuncId());
		} 
		if (model.getDtype() != null) {
			jsonObject.put("dtype", model.getDtype());
		} 
		if (model.getFuncName() != null) {
			jsonObject.put("funcName", model.getFuncName());
		} 
		if (model.getParams() != null) {
			jsonObject.put("params", model.getParams());
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


	public static ObjectNode toObjectNode(SystemDBFuncMapping model){
                ObjectNode jsonObject = new ObjectMapper().createObjectNode();
		jsonObject.put("id", model.getId());
		jsonObject.put("_id_", model.getId());
		jsonObject.put("_oid_", model.getId());
                if (model.getFuncId() != null) {
                     jsonObject.put("funcId", model.getFuncId());
                } 
                if (model.getDtype() != null) {
                     jsonObject.put("dtype", model.getDtype());
                } 
                if (model.getFuncName() != null) {
                     jsonObject.put("funcName", model.getFuncName());
                } 
                if (model.getParams() != null) {
                     jsonObject.put("params", model.getParams());
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

	
	public static JSONArray listToArray(java.util.List<SystemDBFuncMapping> list) {
		JSONArray array = new JSONArray();
		if (list != null && !list.isEmpty()) {
			for (SystemDBFuncMapping model : list) {
				JSONObject jsonObject = model.toJsonObject();
				array.add(jsonObject);
			}
		}
		return array;
	}

	public static java.util.List<SystemDBFuncMapping> arrayToList(JSONArray array) {
		java.util.List<SystemDBFuncMapping> list = new java.util.ArrayList<SystemDBFuncMapping>();
		for (int i = 0, len = array.size(); i < len; i++) {
			JSONObject jsonObject = array.getJSONObject(i);
			SystemDBFuncMapping model = jsonToObject(jsonObject);
			list.add(model);
		}
		return list;
	}


	private SystemDBFuncMappingJsonFactory() {

	}

}
